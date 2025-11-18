package kr.wise.meta.ddl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlPartRqstService;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlPartMain;
import kr.wise.meta.ddl.service.WaqDdlPartMainMapper;
import kr.wise.meta.ddl.service.WaqDdlPartMapper;
import kr.wise.meta.ddl.service.WaqDdlPartSub;
import kr.wise.meta.ddl.service.WaqDdlPartSubMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service("ddlPartRqstService")
public class DdlPartRqstServiceImpl implements DdlPartRqstService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDdlPartMapper waqmapper;
	
	@Inject
	private WaqDdlPartMainMapper mainmapper;

	@Inject
	private WaqDdlPartSubMapper submapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
    private DdlScriptService ddlScriptService;
    
    @Inject
	private MessageSource message;

	public WaqDdlPart getDdlPartRqstDetail(WaqDdlPart searchVo) {
		//logger.debug("DDL 파티션 요청 상세정보:{}", searchVo);
		return waqmapper.selectDetail(searchVo);
	}

	public List<WaqDdlPart> getDdlPartRqstList(WaqMstr mstvo) {
		//logger.debug("DDL 파티션 요청 리스트 by mst:{}", mstvo);
		return waqmapper.selectDdlPartList(mstvo);
	}

	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		//logger.debug("DDL 파티션 요청서 저장-mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();
		String ddlTrgDcd = UtilString.null2Blank(mstVo.getDdlTrgDcd());

		int result = 0;

		if(reglist != null) {
			for (WaqDdlPart saveVo : (ArrayList<WaqDdlPart>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
				//개발인 경우에만 일괄 세팅, DDL이관(테스트,운영)인 경우에는 이미 값이 지정되어 있음
				if(ddlTrgDcd.equals("D")){ 
					saveVo.setDdlTrgDcd(ddlTrgDcd); 
				}
				//단건 저장...
				result += saveDdlPartRqst(saveVo);
			}
		}

		//이관일경우 마지막에 한번에 RqstStepCd UPDATE
		if (!"DTT".equals(mstVo.getBizDcd())){
			if (!"DTR".equals(mstVo.getBizDcd())){
				mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
				requestMstService.updateRqstPrcStep(mstVo);
			}
		}

		return result;
	}

	private int saveDdlPartRqst(WaqDdlPart saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		String ddlTrgDcd = UtilString.null2Blank(saveVo.getDdlTrgDcd());
		
		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시에 해당하는 파티션 리스트 추가...
			if(ddlTrgDcd.equals("D")){ //DDL개발
				mainmapper.insertByRqstSno(saveVo);
				submapper.insertByRqstSno(saveVo);
			}else{
				//DDL이관(테스트/운영)
				mainmapper.insertByTsfRqstSno(saveVo);
				//주파티션 삭제대상 추가
				mainmapper.insertByTsfDelRqstSno(saveVo);
				
				submapper.insertByTsfRqstSno(saveVo);
				
				submapper.insertByTsfDelRqstSno(saveVo);
			}

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			
//			waqcolmapper.updateTblNmbyRqstsno(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//DDL파티션 주파티션, 서브파티션 삭제....
			mainmapper.deleteByDdlPart(saveVo);
			submapper.deleteByDdlPart(saveVo);
//			waqcolmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}

	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		//요청서번호 가져온다.
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String isadminyn = user.getIsAdminYn();
		
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();
		
		String ddlTrgDcd = UtilString.null2Blank(mstVo.getDdlTrgDcd());
		//logger.debug("check ddlTrgDcd : " + ddlTrgDcd);

		if(ddlTrgDcd.equals("D")){
			tblnm    = mstVo.getBizInfo().getTblNm();
			bizdtlcd = mstVo.getBizInfo().getBizDtlCd();
		}else{ //TR : DDL이관시
			tblnm    = "WAQ_DDL_PART";
			bizdtlcd = "TSFPART";
		}	
		
		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		if(ddlTrgDcd.equals("D")){
			waqRqstVrfDtls.setBizDtlCd("DDLPARTM");
		}else{ //TR : DDL이관시
			waqRqstVrfDtls.setBizDtlCd("TSFPARTM");
		}	
		
//		waqRqstVrfDtls.setBizDtlCd("DDLPARTM");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		if(ddlTrgDcd.equals("D")){
			waqRqstVrfDtls.setBizDtlCd("DDLPARTS");
		}else{ //TR : DDL이관시
			waqRqstVrfDtls.setBizDtlCd("TSFPARTS");
		}	
//		waqRqstVrfDtls.setBizDtlCd("DDLPARTS");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//파티션테이블 접속대상ID, 스키마ID, DDL테이블ID, 테이블스페이스ID 업데이트
//		waqmapper.updateKeyId(rqstNo);
		//DB_CONN_TRG_ID UPDATE
		waqmapper.updateDbConnTrgId(rqstNo);
		//DB_SCH_ID UPDATE
		waqmapper.updateDbSchId(rqstNo);
		//DDL_TBL_ID UPDATE
		waqmapper.updateDdlTblId(rqstNo);
		//DDL_PART_ID UPDATE
		waqmapper.updateDdlPartId(rqstNo);
		
		//계정계 테이블 스페이스는 따로 관리 된다.
		//계정계 여부는 DBMS 정보에서 관리한다.
		//DDL테이블의 테이블 스페이스 입니다.
		//waqmapper.updateTblSpacIdByAccDbmsYn(rqstNo);
		
		//일반 테이블 스페이스 사용시 
		//DDL테이블의 테이블 스페이스 입니다.
		//waqmapper.updateTblSpacId(rqstNo);
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
		//파티션삭제 일경우 MAIN 파티션리스트 삭제
		waqmapper.updateDelMainPart(rqstNo);		
		//파티션삭제 일경우 MAIN 파티션리스트 삭제
		waqmapper.updateDelSubPart(rqstNo);
		
		/*
		if(ddlTrgDcd.equals("D")){ 
			//DDL테이블 CUD여부 update
			waqmapper.updateCudYn(rqstNo);
		}
		*/
		
		//주파티션 테이블 DDL_PART_ID, DDL_TBL_ID, DDL_TBL_PNM UPDATE
		mainmapper.updateBaseInfo(rqstNo);
		//주파티션 테이블 DDL_MAIN_PART_ID UPDATE
		mainmapper.updateDdlMainPartId(rqstNo);
		
		//주파티션 테이블 스페이스명 업데이트...
		//mainmapper.updateTableSpace(rqstNo);
		//주파티션 계정계 테이블 스페이스는 따로 관리 된다.
		//주파티션 계정계 여부는 DBMS 정보에서 관리한다.
		//mainmapper.updateTblSpacIdByAccDbmsYn(rqstNo);
		//일반 테이블 스페이스 사용시 
		//mainmapper.updateTblSpacId(rqstNo);
		
		//등록유형코드(C/U/D), 검증코드 업데이트
		mainmapper.updateCheckInit(rqstNo);
		
		
		
		//주파티션 테이블 DDL_PART_ID, DDL_TBL_ID, DDL_TBL_PNM UPDATE 전
		//RQST_SNO 리빌딩 합니다.
		submapper.updateRqstSnoByPart(rqstNo);
		submapper.updateRqstSnoByPart2(rqstNo);
		//SUB 파티션 DDL_MAIN_PART_ID,  DDL_PART_ID, DDL_TBL_ID, DDL_TBL_PNM UPDATE
		//주파티션목록에서 추가할경우 파티션명 자동 생성 되는데 동일한 파티션명으로 자동 생성됨 서브 파티션 기본정보 UPDATE 시 MAX 함수 사용 
		submapper.updateBaseInfo(rqstNo);
		//SUB 파티션 DDL_SUB_PART_ID UPDATE
		submapper.updateDdlSubPartId(rqstNo);
		//등록유형코드(C/U/D), 검증코드 업데이트
		submapper.updateCheckInit(rqstNo);
		
		//검증 시작
		//DDL 파티션 검증
		//DDL 파티션 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);
		checkmap.put("ddlTrgDcd", ddlTrgDcd);

		//요청서내 중복자료 검증(DDP01)
		checkmap.put("vrfDtlCd", "DDP01");
		waqmapper.checkDupPart(checkmap);

		//삭제일때 미존재 파티션 체크(DDP02)
		checkmap.put("vrfDtlCd", "DDP02");
		waqmapper.checkNotExistPart(checkmap);

		//요청중인 파텨션 (DDP03)
		checkmap.put("vrfDtlCd", "DDP03");
		waqmapper.checkRequestPart(checkmap);

		//DB접속정보 미존재 (DDP04)
		checkmap.put("vrfDtlCd", "DDP04");
		waqmapper.checkNonDbConnTrg(checkmap);

		//DB스키마정보 미존재 (DDP05)
		checkmap.put("vrfDtlCd", "DDP05");
		waqmapper.checkNonDbSch(checkmap);
		
		//DDL테이블정보 미존재 (DDP06)
		checkmap.put("vrfDtlCd", "DDP06");
		waqmapper.checkNonDdlTbl(checkmap);

		//요청중인 DDL테이블정보 존재 (DDP13)
		checkmap.put("vrfDtlCd", "DDP13");
		waqmapper.checkExistsRqstDdlTbl(checkmap);
		
		//테이블스페이스정보 미존재 (DDP07) 
		//주파티션의 테이블스페이스 정보는 필수요건인가????
		checkmap.put("vrfDtlCd", "DDP07");
		//waqmapper.checkNonTblSpac(checkmap);
		
		//주파티션 존재유무 (DDP08)
		checkmap.put("vrfDtlCd", "DDP08");
		waqmapper.checkNonExistMainPart(checkmap);

		//주파티션 유형 또는 키컬럼 값 체크 (DDP18)
		checkmap.put("vrfDtlCd", "DDP18");
		waqmapper.checkNonMainPartType(checkmap);

		//주파티션 키컬럼 존재여부 (DDP09)
		checkmap.put("vrfDtlCd", "DDP09");		
		waqmapper.checkNonMainPartKeyCol(checkmap);

		//서브파티션 키컬럼 존재여부(DDP10)
		checkmap.put("vrfDtlCd", "DDP10");
		waqmapper.checkNonSubPartKeyCol(checkmap);
		
		//주파티션 vs 서브파티션 키컬럼 중복 체크 (DDP11)
		checkmap.put("vrfDtlCd", "DDP11");
		waqmapper.checkDupPartKeyCol(checkmap);

		//서브파티션 존재여부 체크(DDP12)
		checkmap.put("vrfDtlCd", "DDP12");
		waqmapper.checkNonExistSubPart(checkmap);

		//서브파티션 타입, 키컬럼미존재 체크 (DDP14)
		checkmap.put("vrfDtlCd", "DDP14");
		waqmapper.checkNonSubPartKey(checkmap);

		//주파티션 타입 LIST일 경우  키컬럼1개이상인지 체크 (DDP15)
		checkmap.put("vrfDtlCd", "DDP15");
		waqmapper.checkPartKeybyList(checkmap);

		//서브파티션 타입 LIST일 경우  키컬럼1개이상인지 체크 (DDP16)
		checkmap.put("vrfDtlCd", "DDP16");
		waqmapper.checkSubPartKeybyList(checkmap);
		
		//적용예정일자 오늘날자보다 작은지 체크
		checkmap.put("vrfDtlCd", "DDP17");
		waqmapper.checkAplRegDd(checkmap);
		
		//서브파티션유형 RANGE 작성할수 없음
		checkmap.put("vrfDtlCd", "DDP19");
		waqmapper.checkSubPartTypCd(checkmap); 
		
		
		//운영반영시 SR번호 필수 체크
		checkmap.put("vrfDtlCd", "DT014"); 
		waqmapper.checkRealSrMngNo(checkmap);
		
		//CUD여부는  상품및서비스(계정계) 에 속한 주제영역 만 사용가능
		checkmap.put("vrfDtlCd", "DT017"); 
		waqmapper.checkCudYn(checkmap);
		
		//DDL등록요청시에 엑셀로 테스트나 운영정보가 들어올경우 검증
		if(!UtilString.null2Blank(bizdtlcd).equals("TSFPART")){
					checkmap.put("vrfDtlCd", "DDP20");
					//waqmapper.checkExistsTsfPart(checkmap);
		}
				
//		변경 삭제대상일 경우 요청자가 기존 요청자의 주제영역권한을 가지고 있는지 체크(SO099)
		//사용자그룹 관리자 제외
		if(!"Y".equals(isadminyn) && "Y".equals(message.getMessage("subj.owner.yn", null, Locale.getDefault()))){
			checkmap.put("vrfDtlCd", "SO099");
			waqmapper.checkSubjOwner(checkmap);
		}
		
		//운영이관 시 체크카드여부 테이블의 적용요청일자 는 매월둘째주금요일 임
		// DDL_TRG_DCD = 'R' APL_REQ_DT
		checkmap.put("vrfDtlCd", "DT018"); 
		//waqmapper.checkAplReqDtByDdlTrgDcd(checkmap);
		
		//운영이관시 적용요청구분,적용요청일자 필수
		// DDL_TRG_DCD = 'R' APL_REQ_TYP_CD ,APL_REQ_DT
		//201803015 우동원차장 요청으로 개발,테스트, 운영 구분 없이 적용요청일자는 필수로 변경
		checkmap.put("vrfDtlCd", "DT019"); 
		//waqmapper.checkAplInfoByDdlTrgDcd(checkmap);
		
		//계정계이고 운영이관일 경우 적용요청일자는 매주 금요일
		// 만약에 주제영역에서 적용요청일 과 적용요청일검증 기준일을 관리 한다면 매주 금요일 관련 검증은 없어도 될 것 같습니다. 우동원차장(20180529)
		// DT022, DT023 으로 데체
//		checkmap.put("vrfDtlCd", "DT020"); 
//		waqmapper.checkAplReqDtByDdlTrgDcdReal(checkmap);
		
		//운영이관시 적용요청일자는 항상 금요일 이다. 화 목도 추가 될수 있다?
		checkmap.put("vrfDtlCd", "DT022"); 
		//waqmapper.checkAplReqDt(checkmap);
		
		//운영이관시 적용요청일자가 수, 목, 금 일경우 차주 금요일이다...
		checkmap.put("vrfDtlCd", "DT023"); 
		//waqmapper.checkAplReqDt2(checkmap);
		
		//오라클만 사용 가능
		checkmap.put("vrfDtlCd", "COM02");
		//waqRqstVrfDtlsMapper.checkDbmsType(checkmap);
		
		
		//주파티션 검증
		//주파티션 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_DDL_PART_MAIN");
		checkmap2.put("rqstNo", rqstNo);
		if(ddlTrgDcd.equals("D")){
			checkmap2.put("bizDtlCd", "DDLPARTM");
		}else{ //TR : DDL이관시
			checkmap2.put("bizDtlCd", "TSFPARTM");
		}	
		

		//요청서내 중복(DDP41)
		checkmap2.put("vrfDtlCd", "DDP41");
		mainmapper.checkDupMainPart(checkmap2);
		
		//테이블스페이스정보 미존재 (DDP07)
		//계정계일경우 테이블스페이스 명은 존재하나 테이블스페이스 ID가 미존재 할 수도 있다.
		checkmap2.put("vrfDtlCd", "DDP07");
//		mainmapper.checkNonTblSpac(checkmap2);  20171123 주석처리 테이블스페이스 입력 안해도 되도록

		//삭제일때 미존재 주파티션 체크(DDP42)
		checkmap2.put("vrfDtlCd", "DDP42");
		mainmapper.checkNotExistMainPart(checkmap2);

		//DDL 서브파티션 미존재 (DDP43) 
		checkmap2.put("vrfDtlCd", "DDP43");
//		mainmapper.checkNonSubPart(checkmap2);
		
		//RANGE 파티션인 경우 키컬럼 갯수와 파티션 구분값 수가 같아야 한다. (DDP44)
		checkmap2.put("vrfDtlCd", "DDP44");
		mainmapper.checkCountPartValbyRange(checkmap2);
		
		//주파티션이 RANGE 일경우 서브파티션은 RANGE 일 수 없다.
		checkmap2.put("vrfDtlCd", "DDP48");
		mainmapper.checksubPartTypCdbyRange(checkmap2);
		
		//주파티션 RANGE, LIST 일경우 구분값은 필수 항목
		checkmap2.put("vrfDtlCd", "DDP49");
		mainmapper.checkMainPartVal(checkmap2);

		//동일한 컬럼순서가 있을경우 오류 (DDC05)
		checkmap2.put("vrfDtlCd", "DDP45");
//		mainmapper.checkColOrdDup(checkmap2);
		
		//컬럼순서 공백 여부 (DDC06)
		checkmap2.put("vrfDtlCd", "DDP46");
//		mainmapper.checkColOrdNonul(checkmap2);
		
		//존재하지 않는 주파티션 여부 (DDC07)
		checkmap2.put("vrfDtlCd", "DDP47");
//		mainmapper.checkNotExistMainPart(checkmap2);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDC00)
		checkmap2.put("vrfDtlCd", "DDP40");
		mainmapper.checkNotChgData(checkmap2);

		//주파티션 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
		waqRqstVrfDtlsMapper.updateNotChgDatabySno(checkmap2);

		//서브파티션 검증
		//서브파티션 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap3 = new HashMap<String, Object>();
		checkmap3.put("tblnm", "WAQ_DDL_PART_SUB");
		checkmap3.put("rqstNo", rqstNo);
		if(ddlTrgDcd.equals("D")){
			checkmap3.put("bizDtlCd", "DDLPARTS");
		}else{ //TR : DDL이관시
			checkmap3.put("bizDtlCd", "TSFPARTS");
		}	
		//요청서내 중복(DDP71)
		checkmap3.put("vrfDtlCd", "DDP71");
		submapper.checkDupSubPart(checkmap3);

		//삭제일때 미존재 서브파티션 체크(DDP72)
		checkmap3.put("vrfDtlCd", "DDP72");
		submapper.checkNotExistSubPart(checkmap3);

		//요청중인 서브파티션 (DDP73) 요청중인 파티션에 의해 결정됨...
		checkmap3.put("vrfDtlCd", "DDP73");
//		submapper.checkRequestMainPart(checkmap3);

		//주파티션명 미존재 체크 (DDP74)
		checkmap3.put("vrfDtlCd", "DDP74");
		submapper.checkNonMainPartPnm(checkmap3);

		//동일한 컬럼순서가 있을경우 오류 (DDP75)
		checkmap3.put("vrfDtlCd", "DDP75");
//				submapper.checkColOrdDup(checkmap3);
		
		//컬럼순서 공백 여부 (DDP76)
		checkmap3.put("vrfDtlCd", "DDP76");
//				submapper.checkColOrdNonul(checkmap3);
		
		//존재하지 않는 주파티션 여부 (DDP77)
		checkmap3.put("vrfDtlCd", "DDP77");
//				submapper.checkNotExistMainPart(checkmap3);
		
		//서브파티션 RANGE, LIST 일경우 구분값은 필수 항목[DDP78]
		checkmap3.put("vrfDtlCd", "DDP78");
		submapper.checkSubPartVal(checkmap3); 

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDP70)
		checkmap3.put("vrfDtlCd", "DDP70");
		submapper.checkNotChgData(checkmap3);
		
		//서브파티션 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap3);
		waqRqstVrfDtlsMapper.updateNotChgDatabySno(checkmap3);
		
		//DDL파티션에 속한 인덱스 컬럼 미존재(DDP14)
		//인덱스컬럼 검증결과가 필요해 여기 위치함
		checkmap.put("vrfDtlCd", "DDP14");
//		waqmapper.checkNotExistMainPart(checkmap);
				
		//주파티션에 오류가 있는 경우 체크(DDX99)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DDP99");
		waqmapper.checkMainPartErr(checkmap);

		//서브파티션에 오류가 있는 경우 체크(DDX99)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DDP98");
		waqmapper.checkSubPartErr(checkmap);
		
		//요청사유 10글자 이상, 필수항목 검증
		checkmap.put("vrfDtlCd", "COM01");
		//waqRqstVrfDtlsMapper.checkRqstResn(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDX00)
		checkmap.put("vrfDtlCd", "DDP00");
		waqmapper.checkNotChgData(checkmap);
		
		if(ddlTrgDcd.equals("D")){
			
		}else
		//DDL이관일경우 변경데이터 없음일경우 삭제 해버려
		{	// 이관.. //DT000
			Map<String, Object> delmap = new HashMap<String, Object>();
			delmap.put("tblnm"	, "WAQ_DDL_PART_SUB");
			delmap.put("rqstNo"	, rqstNo);
			delmap.put("vrfDtlCd"	, "DDP00");
			waqRqstVrfDtlsMapper.delByBrfCd(delmap);
			delmap.put("tblnm"	, "WAQ_DDL_PART_MAIN");
			waqRqstVrfDtlsMapper.delByBrfCd(delmap);
			delmap.put("tblnm"	, "WAQ_DDL_PART");
			waqRqstVrfDtlsMapper.delByBrfCd(delmap);
		}

		//파티션 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		waqRqstVrfDtlsMapper.updateNotChgData(checkmap);
		
		
		//마스터 정보 업데이트...
		//이관일경우 마지막에 한번에 RqstStepCd UPDATE
		if (!"DTT".equals(mstVo.getBizDcd())){
			if (!"DTR".equals(mstVo.getBizDcd())){
				//마스터 정보 업데이트...
				requestMstService.updateRqstPrcStep(mstVo);
			}
		}

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

//		//DDL 스크립트 업데이트...
		updateDdlPartScriptWaq(mstVo);
		return result;
	}

	public void updateDdlPartScriptWaq(WaqMstr mstVo) throws Exception {
		
		
		List<WaqDdlPart> waqlist = ddlScriptService.updateDdlPartScirptWaq(mstVo);

		for (WaqDdlPart savevo : waqlist) {
			waqmapper.updateDdlPartScriptWaq(savevo);
		}
		
		
	}

	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		if(reglist!=null){
		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		  for (WaqDdlPart savevo : (ArrayList<WaqDdlPart>)reglist) {
		  	savevo.setRqstNo(rqstNo);
		  	savevo.setAprvUserId(userid);
          
		  	result += waqmapper.updatervwStsCd(savevo);
		  }
		}else {
			//요청리스트가 없는 경우 전체승인 처리한다.(자동승인처리)
			result += waqmapper.updatervwStsCdAll(mstVo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			if(reglist!=null){
				//logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
			}else{
				//logger.debug("DDL파티션 자동 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
				return result;
			}
			
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam =true;
		if(reglist!=null){
		   waq2wam = requestApproveService.setApproveProcess(mstVo);
		}else {
			//요청리스트가 없는 경우 전체승인 처리한다.(자동승인처리)
			waq2wam = true;
			mstVo.setRqstStepCd("A");
			requestMstService.updateApproveInfo(mstVo);
		}
		
		
		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			//logger.debug("DDL파티션 waq to wam and wah");
            //if(reglist==null){
            //logger.debug("DDL파티션 스크립트 업데이트");
//			     updateDdlIdxScriptWaq(mstVo);
            //}
			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamMain(mstVo);
			result += regWaq2WamSub(mstVo);


         if(reglist!=null){
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				//logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}
         }

		}

		return result;
	}
	
	/** @param mstVo
	/** @return yeonho
	 * @throws Exception */
	public int regWaq2WamSub(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlPartSub> waqclist = submapper.selectWaqC(rqstno);

		for (WaqDdlPartSub savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlSubPartId(id);

			submapper.updateidByKey(savevo);
		}

		result += submapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += submapper.updateWaqId(rqstno);
		result += submapper.updateWaqId2(rqstno);

		result += submapper.deleteWAM(rqstno);

		result += submapper.insertWAM(rqstno);

		result += submapper.updateWAH(rqstno);

		result += submapper.insertWAH(rqstno);


		return result ;
	}

	/** @param mstVo
	/** @return yeonho
	 * @throws Exception */
	public int regWaq2WamMain(WaqMstr mstVo) throws Exception {
		int result = 0;
		
		String rqstno = mstVo.getRqstNo();
		
		List<WaqDdlPartMain> waqclist = mainmapper.selectWaqC(rqstno);
		
		for (WaqDdlPartMain savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlMainPartId(id);
			
			mainmapper.updateidByKey(savevo);
		}
		
		result += mainmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += mainmapper.updateWaqId(rqstno);
		
		result += mainmapper.deleteWAM(rqstno);
		
		result += mainmapper.insertWAM(rqstno);
		
		result += mainmapper.updateWAH(rqstno);
		
		result += mainmapper.insertWAH(rqstno);
		
		
		return result ;
	}

	/** @return yeonho
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlPart> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlPart savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlPartId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	public int regDdlMainPartList(WaqMstr reqmst, WaqDdlPart waqpart, List<WaqDdlPartMain> list) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String rqstno = reqmst.getRqstNo();
		//대상DBMS구분코드
		String ddlTrgDcd = UtilString.null2Blank(reqmst.getDdlTrgDcd());
		
		//파티션 부터 먼저 저장한다.
		List<WaqDdlPart> partlist =  new ArrayList<WaqDdlPart>();
		partlist.add(waqpart);
		register(reqmst, partlist);
		for (WaqDdlPartMain savevo : list) {
			savevo.setRqstNo(reqmst.getRqstNo());
			savevo.setRqstSno(waqpart.getRqstSno());
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			//개발인 경우에만 일괄 세팅, DDL이관(테스트,운영)인 경우에는 이미 값이 지정되어 있음
			if(ddlTrgDcd.equals("D")){ 
				savevo.setDdlTrgDcd(ddlTrgDcd); 
			}
			
			//여러파티션에 대한 주파티션 리스트가 올경우를 대비해 waq 파티션의 RqstSno를 확인하여 처리한다....
			WaqDdlPart compart =  waqmapper.selectbyMainPart(savevo);
			if(compart != null) {
				savevo.setRqstSno(compart.getRqstSno());
				savevo.setFrsRqstUserId(userid);
				savevo.setRqstUserId(userid);
				result += saveDdlPartMain(savevo);
			}
			
//			result += saveDdlPartMain(savevo);
		}	
		
		return result;
	}

	public int regDdlSubPartList(WaqMstr reqmst, WaqDdlPart waqpart, List<WaqDdlPartSub> list) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//파티션 부터 먼저 저장한다.
		List<WaqDdlPart> partlist =  new ArrayList<WaqDdlPart>();
		partlist.add(waqpart);
		register(reqmst, partlist);
		
		for (WaqDdlPartSub savevo : list) {
			savevo.setRqstNo(reqmst.getRqstNo());
			savevo.setRqstSno(waqpart.getRqstSno());
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			result += saveDdlPartSub(savevo);
		}	
		
		return result;
	}

	private int saveDdlPartMain(WaqDdlPartMain saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = mainmapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = mainmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = mainmapper.deleteByrqstSno(saveVo);
			//서브파티션 삭제 
			submapper.deleteByPartMain(saveVo);
		}

		return result;
	}

	private int saveDdlPartSub(WaqDdlPartSub saveVo) {
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = submapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = submapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = submapper.deleteByrqstSno(saveVo);
		}
		
		return result;
	}


	public List<WaqDdlPartMain> getDdlPartMainRqstList(WaqMstr search) {
		return mainmapper.selectList(search);
	}
	public List<WaqDdlPartSub> getDdlPartSubRqstList(WaqMstr search) {
		return submapper.selectList(search);
	}

	public int delDdlPartRqst(WaqMstr reqmst, ArrayList<WaqDdlPart> list) throws Exception {
		int result = 0;
		
		for (WaqDdlPart savevo : list) {
			savevo.setIbsStatus("D");
		}
		
		result = register(reqmst, list);
		
		return result;
	}

	public int delDdlPartMainRqst(WaqMstr reqmst, ArrayList<WaqDdlPartMain> list) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for (WaqDdlPartMain savevo : list) {
			savevo.setIbsStatus("D");
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			result += saveDdlPartMain(savevo);
		}
		
		return result ;
	}

	public int delDdlPartSubRqst(WaqMstr reqmst, ArrayList<WaqDdlPartSub> list) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for (WaqDdlPartSub savevo : list) {
			savevo.setIbsStatus("D");
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			result += saveDdlPartSub(savevo);
		}
		
		return result;
	}

	public List<WaqDdlPart> getWamPartList(WaqDdlPart search) {
		return waqmapper.selectDdlPartListWam(search);
	}
	
	

	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlPart> list) throws Exception {
		int result = 0 ;

		List<WaqDdlPart> wamlist = waqmapper.selectddlpartlist(reqmst, list);

		result += register(reqmst, wamlist);

		return result;
	}
	
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("DDLPART");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		result += waqmapper.insertWaqMainPartRejected(reqmst, oldRqstNo);
		result += waqmapper.insertWaqSubPartRejected(reqmst, oldRqstNo);
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);
		
		return result;
	}

	public int delDdlPartbyDdlTbl(WaqMstr mstVo) {
		int result = 0 ;
		
		String rqstNo = mstVo.getRqstNo();
		
		//서브파티션 삭제...
		result += submapper.updateWAHbyTbl(rqstNo);
		result += submapper.insertWAHbyTbl(rqstNo);
		result += submapper.deleteWAMbyTbl(rqstNo);
		
		//메인파티션 삭제...
		result += mainmapper.updateWAHbyTbl(rqstNo);
		result += mainmapper.insertWAHbyTbl(rqstNo);
		result += mainmapper.deleteWAMbyTbl(rqstNo);
		
		//파티션 삭제...
		result += waqmapper.updateWAHbyTbl(rqstNo);
		result += waqmapper.insertWAHbyTbl(rqstNo);
		result += waqmapper.deleteWAMbyTbl(rqstNo);
		
		
		return result;
	}

	@Override
	public int ddlTrgCdcUpdateTemp(WaqMstr mstVo) {
		int result = waqmapper.ddlTrgCdcUpdateTemp();
	        result += waqmapper.ddlIdxTrgCdcUpdateTemp();
	        result += waqmapper.ddlIdxHTrgCdcUpdateTemp();
	        result += waqmapper.ddlIdxHSrcDbSchIdUpdateTemp();
	        result += waqmapper.ddlIdxSrcDbSchIdUpdateTemp();	     
		return result;
	}
	
	@Override
	public int ddlTrgCdcUpdateTemp2(WaqMstr mstVo) {
		int result = waqmapper.ddlPartSrcDbSchIdUpdateTemp();
	        result += waqmapper.ddlPartHInsertTemp();
	        result += waqmapper.ddlPartHSrcDbSchIdUpdateTemp();
	       
	     
		return result;
	}
	
	@Override
	public int ddlTrgCdcUpdateTemp3(WaqMstr mstVo) {	       
	        int result = waqmapper.ddlPartMainHInsertTemp();
	        result += waqmapper.ddlPartSubHInsertTemp();
	     
		return result;
	}
	
	@Override 
	public int updateSrMngNoPrjMngNo(WaqMstr mstVo) throws Exception {
		int result = 0;
		List<WaqDdlPart> srlist = waqmapper.selectSrMngNoPrjMngNo(mstVo);
		
		for (WaqDdlPart savevo : srlist) {
			result += waqmapper.updateWamSrMngNoPrjMngNoByKey(savevo);
			result += waqmapper.updateWahSrMngNoPrjMngNoByKey(savevo);
		}
		return result;
	}

	@Override
	public String selectPartColumnLength(WaqDdlPart search) {
		String result = "0";
		
		result = waqmapper.selectPartColumnLength(search);
		logger.debug("/////////////////////////////// : " + result);
		return result;
	}

}
