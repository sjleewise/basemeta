/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:42:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.model.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlTblMapper;
import kr.wise.meta.model.service.PdmTblRqstService;
import kr.wise.meta.model.service.WaePdmCol;
import kr.wise.meta.model.service.WaePdmColMapper;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmColMapper;
import kr.wise.meta.model.service.WaqPdmRel;
import kr.wise.meta.model.service.WaqPdmRelMapper;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.model.service.WaqPdmTblMapper;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:42:57
 * </PRE>
 */
@Service("pdmTblRqstService")
public class PdmTblRqstServiceImpl implements PdmTblRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqPdmTblMapper waqmapper;

	@Inject
	private WaqPdmColMapper waqcolmapper;
	
	@Inject
	private WaePdmColMapper waecolmapper; 

	@Inject
	private WaqPdmRelMapper waqPdmRelMapper;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
    private WaqDdlTblMapper waqDdlTblMapper;
    
    @Inject
    private WaqDdlColMapper waqDdlColMapper;
    
    @Inject
    private DdlTblRqstService ddlTblRqstService;
    
    @Inject 
    private DdlIdxRqstService ddlIdxRqstService;
    
    @Inject
    private WaqDdlIdxMapper   waqDdlIdxMapper ;
    
    @Inject
    private WaqDdlIdxColMapper waqDdlIdxColMapper;

	/** 물리모델 요청서 저장.. insomnia */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());
 
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqPdmTbl saveVo : (ArrayList<WaqPdmTbl>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += savePdmTblRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 물리모델 요청서 단건 저장... @return insomnia */
	private int savePdmTblRqst(WaqPdmTbl saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 WAM에 해당하는 컬럼 존재시 추가...
			waqcolmapper.insertByRqstSno(saveVo);
			//초기 등록시 WAM에 해당하는 관계 존재시 추가...
			//waqPdmRelMapper.insertByRqstSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			
//			//테이블 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다.
//			waqcolmapper.deleteByrqstSno(saveVo);
//			
//			//컬럼 등록
//			waqcolmapper.insertByRqstSno(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo);
			//관계 요청 리스트 삭제.....
			//waqPdmRelMapper.deleteByrqstSno(saveVo);
		}

		return result;
	}

	/** 물리모델 요청서 검증 insomnia */
	public int check(WaqMstr mstVo) {
	 
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//		waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("COL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("REL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

	
		//테이블이 삭제요청일 경우 해당 컬럼도 삭제요청으로 변경한다.
		waqcolmapper.updateRqstDcdbyTable(rqstNo);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqcolmapper.updateCheckInit(rqstNo);
		
		//관계 등록유형코드, 검증코드 업데이트
		waqPdmRelMapper.updateCheckInit(rqstNo);
		
		//테이블이 변경/삭제일 경우 컬럼이 없는 경우 삭제대상으로 추가한다.
		//logger.debug("삭제대상추가 시작");
		waqcolmapper.insertnoWaqdelCol(rqstNo);
	//	logger.debug("삭제대상추가 종료");

		//테이블 물리명 업데이트...
		waqcolmapper.updateTblPnmbyRqstSno(rqstNo);
		

		/***************************
		** Data Update & Create start...
		***************************/
		//표준 적용 여부 업데이트
		//시스템 단위 표준 적용 여부를 테이블에 적용한다.
		//테이블의 표준적용여부를 테이블에 적용한다.

		//주제영역 단위 표준 적용 여부, SUBJ_ID 를 테이블에 적용한다.
		waqmapper.updateStndApplybySubj(rqstNo);
		
		//주제영역 DBMS타입 조회
		HashMap<String,String> mapSubj = waqmapper.selectSubjDbmsTypCd(rqstNo); 
		
		//대소문자 상관없이 컬럼명을 키값으로 값 가져오기
//		List<String> lst = new ArrayList<String>();
//		String dbmsTypCd = "";
//		String stndAsrt = "";
//		
//		if(mapSubj != null){
//			mapSubj.forEach((key, value) -> {
//				lst.add(key);
//			});
//			
//			dbmsTypCd = UtilString.null2Blank(mapSubj.get(lst.get(1)));
//			stndAsrt  = UtilString.null2Blank(mapSubj.get(lst.get(0))); 
//		}
		
		String dbmsTypCd = UtilString.null2Blank(mapSubj.get("DBMS_TYP_CD"));
		String stndAsrt  = UtilString.null2Blank(mapSubj.get("STND_ASRT")); 
		
		logger.debug("\n dbmsTypCd:" + dbmsTypCd);
		logger.debug("\n stndAsrt:" + stndAsrt);


		//신규,변경,삭제일 경우 컬럼의 정보 업데이트 (표준일경우)
		//1. DA# ONLY 항목영문명 +"_"+ NUMBER유형에서 사용
//		waqcolmapper.updateObjEnmForDas(rqstNo);
		
		//2. 컬럼영문명 항목영문명 + NUMBER UPDATE
		//waqcolmapper.updatePdmColPnm(rqstNo); //20151106 이상익 주석처리

		//3. ERWIN등 항목영문명 + NUMBER유형에서 사용
		//waqcolmapper.updateObjEnm(rqstNo); 20151106 이상익 주석처리
		
		//신규,변경,삭제일 경우 컬럼의 정보 업데이트 (비표준일 경우)
		//비표준이라도 같은 표준항목과 데이터타입,길이,소수점이 존재하면 항목정보와 표준여부를 'Y'로 업데이트

		//비표준 컬럼일경우 컬럼명과 항목명이 일치할경우 항목정보를 업데이트 해준다.
		//비표준컬럼과 항목의 모든정보를 비교하여 비표준여부를 업데이트 한다.
//		waqcolmapper.updateNonObjEnm(rqstNo);
		//표준용어에서 암호화항목도 같이 가져온다.
		waqcolmapper.updateSditm(rqstNo);
				
		waqcolmapper.updatePkOrd(rqstNo);
		
		// pk여부가 null일 경우 N으로 업데이트
//		waqcolmapper.updatePkYn(rqstNo);
		
		// notnull여부가 null일 경우 N으로 업데이트
//		waqcolmapper.updateNoNulYn(rqstNo);
		
		//암호화여부가 null일경우 N으로 업데이트
		waqcolmapper.updateEncYnisNull(rqstNo);

		//비표준 테이블 중에 속한 컬럼에 비표준이 존재하지 않을 경우 테이블을 표준으로 업데이트
		waqmapper.updateStndApplybyCol(rqstNo);

		//컬럼순서 정비
		waqcolmapper.updateColOrd(rqstNo); 
				
		//관계 주제영역(부모, 자식) Update
		waqPdmRelMapper.updateSubjId(rqstNo);
		
//		//범정부연계항목 업데이트 (표준용어 -> 물리모델컬럼)
//		if(mstVo.getGovYn().equals("Y")){
//			waqcolmapper.updateGovItems(rqstNo, stndAsrt);
//		}
		
		//검증 시작
		//물리모델 테이블 검증
		//물리모델 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(PT001)
		checkmap.put("vrfDtlCd", "PT001");
		waqmapper.checkDupTbl(checkmap);

		//삭제일때 미존재 테이블 체크(PT002)
		checkmap.put("vrfDtlCd", "PT002");
		waqmapper.checkNotExistTbl(checkmap);

		//주제영역 미존재 체크(PT003)
		checkmap.put("vrfDtlCd", "PT003");
		waqmapper.checkNonSubjID(checkmap);

		//요청중인 테이블 (PT004)
		checkmap.put("vrfDtlCd", "PT004");
		waqmapper.checkRequestTbl(checkmap);

		//13. 테이블명 길이 체크를 하는 경우
		checkmap.put("vrfDtlCd", "PT005");
		waqmapper.checkTblNameLength(checkmap);

		//테이블물리명 명명규칙
//		checkmap.put("vrfDtlCd", "PT007");
//		waqmapper.checkTblName(checkmap); 
		
		//주제영역/스키마 미매핑 존재 여부
		checkmap.put("vrfDtlCd", "PT008");
		//waqmapper.checkSubjDbSch(checkmap); 
		
		//설명존재유무
		checkmap.put("vrfDtlCd", "PT009");
		//waqmapper.checkObjdesc(checkmap); 
		
		//설명존재유무
//		checkmap.put("vrfDtlCd", "PT010");
//		waqmapper.checkCommonCol(checkmap); 
 


		//컬럼 존재여부 체크
		checkmap.put("vrfDtlCd", "PT006");
		waqmapper.checkColCnt(checkmap);
		
		
		//백업동기화 마스터 테이블 존재여부
		//checkmap.put("vrfDtlCd", "PT011");
		//waqmapper.checkMstTblExists(checkmap);
		
		//백업동기화 히스토리 테이블 존재여부
		//checkmap.put("vrfDtlCd", "PT012");
		//waqmapper.checkHistTblExists(checkmap);
		
				
		
		//물리모델 컬럼 검증
		//물리모델 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_PDM_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "COL");

		//요청서내 중복(PC001)
		checkmap2.put("vrfDtlCd", "PC001");
		waqcolmapper.checkDupCol(checkmap2);

		//삭제일때 미존재 컬럼 체크(PC002)
		checkmap2.put("vrfDtlCd", "PC002");
		waqcolmapper.checkNotExistCol(checkmap2);

		//요청중인 컬럼 (PC003) 요청중인 테이블에 의해 결정됨...
		checkmap2.put("vrfDtlCd", "PC003");
		waqcolmapper.checkRequestCol(checkmap2);

		//20250728 주석 처리 요청
		//컬럼 길이를 체크(PC004)
//		checkmap2.put("vrfDtlCd", "PC004");
//		waqcolmapper.checkColNameLength(checkmap2);

		//데이터 타입이 없는 경우 오류(PC005)
		checkmap2.put("vrfDtlCd", "PC005");
		waqcolmapper.checkNoDataType(checkmap2);

		//컬럼 물리명 미존재(PC014)
		checkmap2.put("vrfDtlCd", "PC014");
		waqcolmapper.checkNoColPnm(checkmap2);
		
		//컬럼 물리명 기준으로 중복 체크
		checkmap2.put("vrfDtlCd", "PC006");
		waqcolmapper.checkDupColPnm(checkmap2);

		//컬럼 논리명 기준으로 중복 체크
		checkmap2.put("vrfDtlCd", "PC007");
		waqcolmapper.checkDupColLnm(checkmap2);
				
						
		//표준 대상인데 미존재 용어가 존재하는 경우
		checkmap2.put("vrfDtlCd", "PC008");
		waqcolmapper.checkNoSditm(checkmap2);
		
		//표준일 경우 데이터타입 표준으로 업데이트
		checkmap2.put("dbmsTypCd", dbmsTypCd);
		checkmap2.put("stndAsrt", stndAsrt);
//		waqcolmapper.updateStndDateType(checkmap2);		
		
		//타입,길이 표준미준수
		checkmap2.put("vrfDtlCd", "PC015");  
		checkmap2.put("dbmsTypCd", dbmsTypCd);
		waqcolmapper.checkDataLenScal(checkmap2);
		
		//데이터 타입이 없는 경우 오류(PC005)
		checkmap2.put("vrfDtlCd", "PC005");
		waqcolmapper.checkNoDataType(checkmap2);

		//PK여부가 Y일때 NOT NULL여부가 N이면 오류
		checkmap2.put("vrfDtlCd", "PC009");
		waqcolmapper.checkPkNotNull(checkmap2);
		
		//동일한 컬럼순서가 있을경우 오류
		checkmap2.put("vrfDtlCd", "PC010");
		waqcolmapper.checkColOrdDup(checkmap2);
		
		//동일한 PK컬럼순서가 있을경우 오류
		checkmap2.put("vrfDtlCd", "PC011");
		waqcolmapper.checkPKColOrdDup(checkmap2);

		//PK여부가 Y일경우 PK순서, NOTNULL여부 검증
		checkmap2.put("vrfDtlCd", "PC012");
		waqcolmapper.checkPKOrdNonul(checkmap2);
		
		//컬럼순서 공백 여부
		checkmap2.put("vrfDtlCd", "PC013");
		waqcolmapper.checkColOrdNonul(checkmap2);
		
		//표준인 경우 컬럼 논리-물리 불일치 여부
		checkmap2.put("vrfDtlCd", "PC025");
		waqcolmapper.checkColNmMap(checkmap2); 
		
		
//		//범정부연계항목 - 컬럼
//		if(mstVo.getGovYn().equals("Y")){
//			
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_제약조건(PC025)
//			checkmap2.put("vrfDtlCd", "PC026");
//			waqcolmapper.checkconstCnd(checkmap2);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_공개/비공개 여부(PC026)
//			checkmap2.put("vrfDtlCd", "PC027");
//			waqcolmapper.checkopenYn(checkmap2);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_개인정보 여부(PC027)
//			checkmap2.put("vrfDtlCd", "PC028");
//			waqcolmapper.checkprsnInfoYn(checkmap2);
////			//사용 x 주석처리(encTrgYn -> encYn)
////			//범정부연계 항목 중 미입력된 항목이 있는 경우_암호화여부(PC028)
////			checkmap2.put("vrfDtlCd", "PC029");
////			waqcolmapper.checkencTrgYn(checkmap2);
//			//범정부연계 항목 중 공개/비공개 여부가 비공개인 경우 비공개 사유를 입력해야 한다(PC029)
//			checkmap2.put("vrfDtlCd", "PC030");
//			waqcolmapper.checkpriRsn(checkmap2);
//			
//		}
		
		//number인 컬럼 not null,dafault 값 확인  일련번호, 번호(srno, no 제외) ibk신용정보
		//checkmap2.put("vrfDtlCd", "PC015");
        //waqcolmapper.checkNumberLenDef(checkmap2);
        
        //시스템공통컬럼 NOT NULL 체크
		//checkmap2.put("vrfDtlCd", "PC016");
        //waqcolmapper.checkCommonColNotNull(checkmap2); 
        
        //마스터 컬럼 미존재 체크
        //checkmap2.put("vrfDtlCd", "PC017");
        //waqcolmapper.checkMstColExists(checkmap2);
        
        //이력 컬럼 미존재 체크
        //checkmap2.put("vrfDtlCd", "PC018");
        //waqcolmapper.checkHistColExists(checkmap2);
        
        //백업테이블 동기화  데이터타입 상이
        //checkmap2.put("vrfDtlCd", "PC019");
        //waqcolmapper.checkColDataTypeDiff(checkmap2);
        
        //백업테이블 동기화  널여부 상이
        //checkmap2.put("vrfDtlCd", "PC020");
        //waqcolmapper.checkColNullDiff(checkmap2);
        
        //백업테이블 동기화  Default 값 상이
        //checkmap2.put("vrfDtlCd", "PC021");
        //waqcolmapper.checkColDefDiff(checkmap2);


        
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PC000)
		checkmap2.put("vrfDtlCd", "PC000");
		waqcolmapper.checkNotChgData(checkmap2);
//		waqcolmapper.updateNotChgData(checkmap2);
		

		//waqcolmapper.updateRqstDcdNoChgCol(rqstNo);
		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
		
//		waqRqstVrfDtlsMapper.updateNotChgSno(checkmap2);


		
		
		//테이블이 변경/삭제일 경우 컬럼이 없는 경우 삭제대상으로 추가한다.
		//logger.debug("삭제대상추가 시작");
		//waqcolmapper.insertnoWaqdelCol(rqstNo);
		//logger.debug("삭제대상추가 종료");
		
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)  
		checkmap.put("vrfDtlCd", "PT000");
		waqmapper.checkNotChgData(checkmap);

		//관계에 오류가 있는 경우 체크(PR999) - 단 변경사항 없음은 제외한다.
//		checkmap.put("vrfDtlCd", "PR999");
//		waqmapper.checkRelErr(checkmap);
		
		//컬럼, 관계에 오류가 있는 경우 체크(PT999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "PT999");
		waqmapper.checkColErr(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		
		
//		//범정부연계항목 - 테이블
//		if(mstVo.getGovYn().equals("Y")){
//			
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_보존기간(PT028)
//			checkmap.put("vrfDtlCd", "PT028");
//			waqmapper.checkprsvTerm(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_품질진단여부(PT029)
//			checkmap.put("vrfDtlCd", "PT029");
//			waqmapper.checkdqDgnsYn(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_상세관련근거(PT030)
//			checkmap.put("vrfDtlCd", "PT030");
//			waqmapper.checknopenDtlRelBss(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_발생주기(PT032)
//			checkmap.put("vrfDtlCd", "PT032");
//			waqmapper.checkoccrCyl(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_개방데이터목록(PT033)
//			checkmap.put("vrfDtlCd", "PT033");
//			waqmapper.checkopenDataLst(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_공개/비공개여부(PT034)
//			checkmap.put("vrfDtlCd", "PT034");
//			waqmapper.checkopenRsnCd(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_테이블유형(PT035)
//			checkmap.put("vrfDtlCd", "PT035");
//			waqmapper.checktblTypNm(checkmap);
//			//범정부연계 항목 중 미입력된 항목이 있는 경우_테이블볼륨(PT036)
//			checkmap.put("vrfDtlCd", "PT036");
//			waqmapper.checktblVol(checkmap);
//			//범정부연계 항목 중 공개/비공개 여부가 비공개인 경우 비공개 사유를 입력해야 한다(PT037)
//			checkmap.put("vrfDtlCd", "PT037");
//			waqmapper.checknopenRsn(checkmap);
//			
//		}

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
	}

	/** 물리모델 요청서 등록요청 insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 물리모델 요청서 승인 처리 insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
        logger.debug("여기여기");
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqPdmTbl savevo : (ArrayList<WaqPdmTbl>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("물리모델 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamCol(mstVo);
			result += regWaq2WamRel(mstVo);
			
//			//DDL 자동발행 입력 이상익
//			logger.debug("DDL자동발행입력");
//			waqDdlTblMapper.insertWaqAuto(mstVo);
//			waqDdlColMapper.insertWaqColAuto(mstVo);			
//			logger.debug("DDL자동발행입력종료");
//			//c,u,d 업데이트
//			waqDdlTblMapper.updateCheckInit2(rqstNo);
//			//테이블이 변경일 때 컬럼 위치가 바뀌었을 경우 테이블 생성유형 drop&create로 변경
//			//waqDdlTblMapper.updateCheckColPositonChg(rqstNo);  
//			//테이블이 변경일 때 PK정보가 바뀌었을 경우 테이블 생성유형 drop&create로 변경
//			//waqDdlTblMapper.updateCheckPKChg(rqstNo);  
//			//암호화 여부 업데이트
//			waqDdlTblMapper.updateEncYn(rqstNo); //암호화컬럼이 존재할 경우
//			waqDdlTblMapper.updateEncYn2(rqstNo); //암호화컬럼이 존재하지 않을 경우
//			
//			//DDL 테이블의 PDM_TBL_ID 업데이트
//			waqmapper.updatePdmTblIdForDdltbl();
//			//DATA TYPE 업데이트 여부
//		    waqDdlColMapper.updateDataTypeYn(rqstNo);
//		    //NOT NULL 업데이트 여부
//		    waqDdlColMapper.updateNonNullYn(rqstNo);
//		    //Default 업데이트 여부
//		    waqDdlColMapper.updateDefaultYn(rqstNo);
//		    //컬럼 업데이트 여부
//		    waqDdlColMapper.updateColUpdateYn(rqstNo);
//		
//			//ddl자동발행 승인
//    		logger.debug("ddl자동발행 승인");
//            result += ddlTblRqstService.approve(mstVo,null);
//            
//            logger.debug("ddl자동발행 승인완료");
//            
//            //PK인덱스 자동입력
//            waqDdlIdxMapper.insertWaqAutoIdx(mstVo);
//            waqDdlIdxMapper.insertWaqAutoIdxDel(mstVo);
//			waqDdlIdxColMapper.insertWaqAutoIdxCol(mstVo);
//			waqDdlIdxColMapper.insertWaqAutoIdxColDel(mstVo);
//			
//			//pk인덱스 등록유형코드(C/U/D), 검증코드 업데이트
//		    waqDdlIdxMapper.updateCheckInit2(rqstNo);
//		    //삭제 시 DDL_IDX_ID를 업데이트 하기 위함
//		    waqDdlIdxMapper.updateIdxId(rqstNo);
//		    //컬럼구성정보 업데이트
//		    waqDdlIdxMapper.updateColCgrText(rqstNo);
//		    waqDdlIdxColMapper.updateCheckInit2(rqstNo);
//		    //삭제 시 인덱스컬럼ID업데이트
//            waqDdlIdxColMapper.updateIdxColId(rqstNo);
//            
//            result += ddlIdxRqstService.approve(mstVo, null);
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @param mstVo
	/** @return yeonho 
	 * @throws Exception */
	private int regWaq2WamRel(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmRel> waqclist = waqPdmRelMapper.selectWaqC(rqstno);

		for (WaqPdmRel savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setPdmRelId(id);

			waqPdmRelMapper.updateidByKey(savevo);
		}

		result += waqPdmRelMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqPdmRelMapper.updateWaqId(rqstno);

		result += waqPdmRelMapper.deleteWAM(rqstno);

		result += waqPdmRelMapper.insertWAM(rqstno);
  
		result += waqPdmRelMapper.updateWAH(rqstno);

		result += waqPdmRelMapper.insertWAH(rqstno);


		return result ;
	}

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmCol> waqclist = waqcolmapper.selectWaqC(rqstno);

		for (WaqPdmCol savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setPdmColId(id);

			waqcolmapper.updateidByKey(savevo);
		}

		result += waqcolmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqcolmapper.updateWaqId(rqstno);

		result += waqcolmapper.deleteWAM(rqstno);

		result += waqcolmapper.insertWAM(rqstno);

		result += waqcolmapper.updateWAH(rqstno);

		result += waqcolmapper.insertWAH(rqstno);


		return result ;
	}

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmTbl> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqPdmTbl savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setPdmTblId(id);

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

	/** 물리모델 요청서 상세정보 조회 insomnia */
	public WaqPdmTbl getPdmTblRqstDetail(WaqPdmTbl searchVo) {
		return waqmapper.selectPdmTblDetail(searchVo);
	}

	/** insomnia */
	public List<WaqPdmTbl> getPdmTblRqstList(WaqMstr search) {
		return waqmapper.selectPdmTblListbyMst(search);
	}

	/** insomnia
	 * @throws Exception */
	public int delPdmTblRqst(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqPdmTbl savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;

		List<WaqPdmTbl> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

	/** insomnia */
	public WaqPdmCol getPdmColRqstDetail(WaqPdmCol search) {

		return waqcolmapper.selectPdmColDetail(search);
	}

	/** 물리모델 컬럼 리스트 저장... insomnia */
	public int regPdmColList(WaqMstr reqmst, List<WaqPdmCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqPdmCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += savePdmColRqst(savevo);
		}

		return result;
	}

	/** 물리모델 컬럼 단건 저장... @return insomnia */
	private int savePdmColRqst(WaqPdmCol savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
//		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waqcolmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqcolmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqcolmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/** insomnia */
	public List<WaqPdmCol> getPdmColRqstList(WaqMstr search) {
		return waqcolmapper.selectPdmColRqstList(search);
	}

	/** insomnia */
	public int delPdmColRqst(WaqMstr reqmst, ArrayList<WaqPdmCol> list) {
		int result = 0;

		for (WaqPdmCol delvo : list) {
//			if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//			} else {
//				delvo.setIbsStatus("U");
//				delvo.setRqstDcd("DD");
//			}
		}

		result += regPdmColList(reqmst, list);


		return result;
	}

	/** insomnia */
	public HashMap<String, String> regPdmxlsColList(WaqMstr reqmst, List<WaqPdmCol> list)  {
		HashMap<String, String> rtnMap = new HashMap<String, String>();
		int result = 0;

		//요청 테이블 리스트를 가져온다.
		List<WaqPdmTbl> tbllist = waqmapper.selectPdmTblListbyMst(reqmst);
		Map<String, WaqPdmTbl> tblmap = new HashMap<String, WaqPdmTbl>();


		for (WaqPdmTbl tblvo : tbllist) {
			String fullpath = tblvo.getFullPath();
			String tblpnm = tblvo.getPdmTblPnm();
//			String rqstNo = tblvo.getRqstNo();
//			Integer rqstSno = tblvo.getRqstSno();

			String key = fullpath+"|"+tblpnm;
			tblmap.put(key, tblvo);
		}

		//테이블이 비어있을 경우 ...
		if(tblmap.isEmpty()) {
			rtnMap.put("result", "-999");
			rtnMap.put("colKey", "");
			return rtnMap;
		}

		for (WaqPdmCol colvo : list) {
			String comkey = colvo.getFullPath() + "|" + colvo.getPdmTblPnm();
			if (tblmap.containsKey(comkey)) {
				WaqPdmTbl tmptbl = tblmap.get(comkey);

				colvo.setRqstNo(tmptbl.getRqstNo());
				colvo.setRqstSno(tmptbl.getRqstSno());
			}
			
			else {
//				벌크 작업시 어떤 테이블이 없는지 식별하기 힘듬
//				return "-999";
				rtnMap.put("result", "-999");
				rtnMap.put("colKey", comkey);
				return rtnMap;
			}

		}

		result += regPdmColList(reqmst, list);
		rtnMap.put("result",  String.valueOf(result));
		rtnMap.put("colKey", "");

		return rtnMap;
	}
	
	//테이블 컬럼 일괄 저장 
	@Override
	public int regPdmXlsTblColList(WaqMstr reqmst, List<WaePdmCol> list) {
				
		int result = 0;
				
		result += regWaePdmColList(reqmst, list);   
		
		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmst);
		
		
		return result;
	} 

	/** yeonho */
	@Override
	public List<WaqPdmRel> getPdmRelRqstList(WaqMstr search) {
		return waqPdmRelMapper.selectPdmRelRqstList(search);
	}

	/** yeonho */
	@Override
	public WaqPdmRel getPdmRelRqstDetail(WaqPdmRel search) {
		return waqPdmRelMapper.selectPdmRelDetail(search);
	}

	/** yeonho */
	@Override
	public int regPdmRelList(WaqMstr reqmst, List<WaqPdmRel> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqPdmRel savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += savePdmRelRqst(savevo);
		}

		return result;
	}

	/** @param savevo
	/** @return yeonho */
	private int savePdmRelRqst(WaqPdmRel savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();

		if("U".equals(tmpStatus)) {
			//동일한 관계가 존재할 경우 UPDATE, 없을 경우는 신규로 추가하기 위함...
			WaqPdmRel tmpVo = waqPdmRelMapper.selectPdmRelExist(savevo);
			if (tmpVo == null || "".equals(tmpVo.getPdmRelPnm())){
				tmpStatus = "I";
			}
			
		}
		
		if("I".equals(tmpStatus)) {
			result = waqPdmRelMapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqPdmRelMapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqPdmRelMapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/** yeonho */
	@Override
	public int delPdmRelRqst(WaqMstr reqmst, ArrayList<WaqPdmRel> list) {
		int result = 0;

		for (WaqPdmRel delvo : list) {
//			if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//			} else {
//				delvo.setIbsStatus("U");
//				delvo.setRqstDcd("DD");
//			}
		}

		result += regPdmRelList(reqmst, list);


		return result;
	}

	/** yeonho */
	@Override
	public int regPdmxlsRelList(WaqMstr reqmst, List<WaqPdmRel> list) {
		int result = 0;

		//요청 테이블 리스트를 가져온다.
		List<WaqPdmTbl> tbllist = waqmapper.selectPdmTblListbyMst(reqmst);
		Map<String, WaqPdmTbl> tblmap = new HashMap<String, WaqPdmTbl>();


		for (WaqPdmTbl tblvo : tbllist) {
			String chSubjPnm = tblvo.getFullPath();
			String chEntyPnm = tblvo.getPdmTblPnm();
//			String rqstNo = tblvo.getRqstNo();
//			Integer rqstSno = tblvo.getRqstSno();

			String key = chSubjPnm+"|"+chEntyPnm;
			tblmap.put(key, tblvo);
		}

		//테이블이 비어있을 경우 ...
		if(tblmap.isEmpty()) {
			return -999;
		}

		for (WaqPdmRel relvo : list) {
			String comkey = relvo.getChSubjPnm() + "|" + relvo.getChEntyPnm();
			if (tblmap.containsKey(comkey)) {
				WaqPdmTbl tmptbl = tblmap.get(comkey);

				relvo.setRqstNo(tmptbl.getRqstNo());
				relvo.setRqstSno(tmptbl.getRqstSno());
			} else {
				return -999;
			}

		}

		result += regPdmRelList(reqmst, list);


		return result;
	}

	
		 	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
//		reqmst.getBizInfo().setBizDtlCd("TBL");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
//        result = waqcolmapper.insertWaqColRejected(reqmst, oldRqstNo);
		
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);

		return result;

	}

	
	/** WAE_PDM_COL  물리모델 컬럼 리스트 저장... insomnia */
	public int regWaePdmColList(WaqMstr reqmst, List<WaePdmCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmst.getRqstStepCd())) {
			requestMstService.insertWaqMst(reqmst);
		}

		String rqstno = reqmst.getRqstNo();

		//컬럼적재하기 전에 기존에 있던 컬럼 내용 삭제
		result += waecolmapper.deleteByrqstNo(rqstno);
		
		
		for (WaePdmCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveWaePdmColRqst(savevo);
		}
		
		//===WAQ_PDM_TBL insert ==
		WaePdmCol colVo = new WaePdmCol();
		
		colVo.setRqstNo(rqstno);
		colVo.setFrsRqstUserId(userid);
		colVo.setRqstUserId(userid);
				
		result += waecolmapper.insertWaqPdmTblForWae(colVo);
		
		//컬럼 insert 
		result += waecolmapper.insertWaqPdmColForWae(colVo); 
		//=======================
		
		return result;
	}
	
	/** 물리모델 컬럼 단건 저장... @return insomnia */
	private int saveWaePdmColRqst(WaePdmCol savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waecolmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waecolmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waecolmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	public boolean checkEmptyRqst(WaqMstr reqmst) {
		return waqmapper.checkEmptyRqst(reqmst.getRqstNo());
	}

	@Override
	public int changeOwner(WaqPdmTbl regVo) {
		return waqmapper.changeOwner(regVo);
	}
 
	

}
