/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : LdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:42:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.ldm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlColMapper;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTblMapper;
import kr.wise.meta.ldm.service.LdmEntyRqstService;
import kr.wise.meta.ldm.service.WaeLdmAttr;
import kr.wise.meta.ldm.service.WaeLdmAttrMapper;
import kr.wise.meta.ldm.service.WaqLdmAttr;
import kr.wise.meta.ldm.service.WaqLdmAttrMapper;

import kr.wise.meta.ldm.service.WaqLdmEnty;
import kr.wise.meta.ldm.service.WaqLdmEntyMapper; 
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxCol;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : LdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:42:57
 * </PRE>
 */
@Service("ldmEntyRqstService")
public class LdmEntyRqstServiceImpl implements LdmEntyRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass()); 

	@Inject
	private WaqLdmEntyMapper waqmapper;  

	@Inject
	private WaqLdmAttrMapper waqcolmapper; 
	
	@Inject
	private WaeLdmAttrMapper waeattrmapper; 

	
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
    DdlIdxRqstService ddlIdxRqstService;
    
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
			for (WaqLdmEnty saveVo : (ArrayList<WaqLdmEnty>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveLdmEntyRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 물리모델 요청서 단건 저장... @return insomnia */
	private int saveLdmEntyRqst(WaqLdmEnty saveVo) {
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
			//waqLdmRelMapper.insertByRqstSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo);
			//관계 요청 리스트 삭제.....
			//waqLdmRelMapper.deleteByrqstSno(saveVo);
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

		
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

	
		//테이블이 삭제요청일 경우 해당 컬럼도 삭제요청으로 변경한다.
		waqcolmapper.updateRqstDcdbyTable(rqstNo);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqcolmapper.updateCheckInit(rqstNo);
		
		
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
		String dbmsTypCd = waqmapper.selectSubjDbmsTypCd(rqstNo); 
		
		logger.debug("\n dbmsTypCd:" + dbmsTypCd);


		
		//비표준 컬럼일경우 컬럼명과 항목명이 일치할경우 항목정보를 업데이트 해준다.		
		waqcolmapper.updateSditm(rqstNo);
				
		waqcolmapper.updatePkOrd(rqstNo);
				
		//비표준 테이블 중에 속한 컬럼에 비표준이 존재하지 않을 경우 테이블을 표준으로 업데이트
		// waqmapper.updateStndApplybyCol(rqstNo);

		//컬럼순서 정비
		waqcolmapper.updateColOrd(rqstNo); 
				
		
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
		//waqmapper.checkTblNameLength(checkmap);

		//테이블물리명 명명규칙
		checkmap.put("vrfDtlCd", "PT007");
		//waqmapper.checkTblName(checkmap); 
		
		//주제영역/스키마 미매핑 존재 여부
		checkmap.put("vrfDtlCd", "PT008");
		//waqmapper.checkSubjDbSch(checkmap); 
		
		//설명존재유무
		checkmap.put("vrfDtlCd", "PT009");
		//waqmapper.checkObjdesc(checkmap); 
		
		
		//컬럼 존재여부 체크
		checkmap.put("vrfDtlCd", "PT006");
		waqmapper.checkColCnt(checkmap);
		
	
		
		//물리모델 컬럼 검증
		//물리모델 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_LDM_ATTR");
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

		//컬럼 길이를 체크(PC004)
		checkmap2.put("vrfDtlCd", "PC004");
		waqcolmapper.checkColNameLength(checkmap2);

		//데이터 타입이 없는 경우 오류(PC005)
		checkmap2.put("vrfDtlCd", "PC005");
		waqcolmapper.checkNoDataType(checkmap2);
		
		//컬럼 논리명 기준으로 중복 체크
		checkmap2.put("vrfDtlCd", "PC007");
		waqcolmapper.checkDupColLnm(checkmap2);
				
						
		//표준 대상인데 미존재 용어가 존재하는 경우
		checkmap2.put("vrfDtlCd", "PC008");
		waqcolmapper.checkNoSditm(checkmap2);
		
		//표준일 경우 데이터타입 표준으로 업데이트
		checkmap2.put("dbmsTypCd", dbmsTypCd);
		waqcolmapper.updateStndDateType(checkmap2);		
		
		//타입,길이 표준미준수
		checkmap2.put("vrfDtlCd", "PC015");  
		checkmap2.put("dbmsTypCd", dbmsTypCd);
		
		waqcolmapper.checkDataLenScal(checkmap2);

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
		
		        
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PC000)
		checkmap2.put("vrfDtlCd", "PC000");
		waqcolmapper.checkNotChgData(checkmap2);
//		waqcolmapper.updateNotChgData(checkmap2);
		

		//waqcolmapper.updateRqstDcdNoChgCol(rqstNo);
		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
		
//		waqRqstVrfDtlsMapper.updateNotChgSno(checkmap2);


		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)  
		checkmap.put("vrfDtlCd", "PT000");
		waqmapper.checkNotChgData(checkmap);

		
		//컬럼 오류가 있는 경우 체크(PT999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "PT999");
		waqmapper.checkColErr(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

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
       
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqLdmEnty savevo : (ArrayList<WaqLdmEnty>)reglist) {
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
//			waqmapper.updateLdmTblIdForDdltbl();
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

	

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqLdmAttr> waqclist = waqcolmapper.selectWaqC(rqstno);

		for (WaqLdmAttr savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setLdmAttrId(id); 

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

		List<WaqLdmEnty> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqLdmEnty savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setLdmEntyId(id);  

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
	public WaqLdmEnty getLdmTblRqstDetail(WaqLdmEnty searchVo) {
		return waqmapper.selectLdmTblDetail(searchVo);
	}

	/** insomnia */
	public List<WaqLdmEnty> getLdmEntyRqstList(WaqMstr search) {
		
		return waqmapper.selectLdmEntyListbyMst(search);
	}

	/** insomnia
	 * @throws Exception */
	public int delLdmTblRqst(WaqMstr reqmst, ArrayList<WaqLdmEnty> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqLdmEnty savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqLdmEnty> list) throws Exception {
		int result = 0;

		List<WaqLdmEnty> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

	/** insomnia */
	public WaqLdmAttr getLdmColRqstDetail(WaqLdmAttr search) {

		return waqcolmapper.selectLdmColDetail(search);  
	}

	/** 물리모델 컬럼 리스트 저장... insomnia */
	public int regLdmColList(WaqMstr reqmst, List<WaqLdmAttr> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqLdmAttr savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveLdmColRqst(savevo);
		}

		return result;
	}

	/** 물리모델 컬럼 단건 저장... @return insomnia */
	private int saveLdmColRqst(WaqLdmAttr savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
		System.out.println("SaveVo : " + savevo.toString());

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
	public List<WaqLdmAttr> getLdmAttrRqstList(WaqMstr search) {

		return waqcolmapper.selectLdmAttrRqstList(search);
	}

	/** insomnia */
	public int delLdmColRqst(WaqMstr reqmst, ArrayList<WaqLdmAttr> list) {
		int result = 0;

		for (WaqLdmAttr delvo : list) {
//			if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//			} else {
//				delvo.setIbsStatus("U");
//				delvo.setRqstDcd("DD");
//			}
		}

		result += regLdmColList(reqmst, list);


		return result;
	}

     	
	//테이블 컬럼 일괄 저장 
	@Override
	public int regLdmXlsTblColList(WaqMstr reqmst, List<WaeLdmAttr> list) {  
				
		int result = 0;
				
		result += regWaeLdmColList(reqmst, list);   
		
		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmst);
		
		
		return result;
	} 

	
	
		 	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		logger.debug("입력1");
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
        //result = waqcolmapper.insertWaqColRejected(reqmst, oldRqstNo);
		
		logger.debug("입력2");
		//마스터 등록
		register(reqmst, null);
        logger.debug("입력3");
		//검증
		check(reqmst);
		logger.debug("체크1");

		return result;

	}

	
	/** WAE_PDM_COL  물리모델 컬럼 리스트 저장... insomnia */
	public int regWaeLdmColList(WaqMstr reqmst, List<WaeLdmAttr> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmst.getRqstStepCd())) {
			requestMstService.insertWaqMst(reqmst);
		}

		String rqstno = reqmst.getRqstNo();

		for (WaeLdmAttr savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveWaeLdmAttrRqst(savevo);
		}
		
		//===WAQ_PDM_TBL insert ==
		WaeLdmAttr colVo = new WaeLdmAttr();
		
		colVo.setRqstNo(rqstno);
		colVo.setFrsRqstUserId(userid);
		colVo.setRqstUserId(userid);
				
		result += waeattrmapper.insertWaqLdmTblForWae(colVo);
		
		//컬럼 insert 
		result += waeattrmapper.insertWaqLdmColForWae(colVo); 
		//=======================
		
		return result;
	}
	
	/** 물리모델 컬럼 단건 저장... @return insomnia */
	private int saveWaeLdmAttrRqst(WaeLdmAttr savevo) { 
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waeattrmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waeattrmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waeattrmapper.deleteByPrimaryKey(savevo);  

		}

		return result;
	}

	
 
	

}
