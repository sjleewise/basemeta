/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisPdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                     : :            : 신규 개발.
 */
package kr.wise.meta.model.service.impl;

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
import kr.wise.meta.model.service.AsisDmnCdValRqstService;
import kr.wise.meta.model.service.WaqAsisDmnCdVal;
import kr.wise.meta.model.service.WaqAsisDmnCdValMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AsisPdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   :
 * 6. 작성일   : 
 * </PRE>
 */
@Service("asisDmnCdValRqstService")
public class AsisDmnCdValRqstServiceImpl implements AsisDmnCdValRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqAsisDmnCdValMapper waqmapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject 
    DdlIdxRqstService ddlIdxRqstService;
    
	/** 물리모델 요청서 저장..  */
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
			for (WaqAsisDmnCdVal saveVo : (ArrayList<WaqAsisDmnCdVal>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
				
				//단건 저장...
				result += saveDmnCdValRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 물리모델 요청서 단건 저장... @return  */
	private int saveDmnCdValRqst(WaqAsisDmnCdVal saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqmapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByPrimaryKey(saveVo);
		}

		return result;
	}

	/** 물리모델 요청서 검증  */
	public int check(WaqMstr mstVo) {
	 
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

	
		/***************************
		** Data Update & Create start...
		***************************/
		//표준 적용 여부 업데이트
		//시스템 단위 표준 적용 여부를 테이블에 적용한다.
		//테이블의 표준적용여부를 테이블에 적용한다.

		//주제영역 단위 표준 적용 여부, SUBJ_ID 를 테이블에 적용한다.
//		waqmapper.updateStndApplybySubj(rqstNo);
		
		//주제영역 DBMS타입 조회
//		String dbmsTypCd = waqmapper.selectSubjDbmsTypCd(rqstNo); 
		
//		logger.debug("\n dbmsTypCd:" + dbmsTypCd);


		//비표준 테이블 중에 속한 컬럼에 비표준이 존재하지 않을 경우 테이블을 표준으로 업데이트
//		waqmapper.updateStndApplybyCol(rqstNo);

		//검증 시작
		//물리모델 테이블 검증
		//물리모델 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, "WAQ_ASIS_DMN_CD_VAL");
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, "DCV");

		//요청서내 중복자료 검증(PT001)
		checkmap.put("vrfDtlCd", "PT001");
//		waqmapper.checkDupTbl(checkmap);

		//삭제일때 미존재 테이블 체크(PT002)
		checkmap.put("vrfDtlCd", "PT002");
//		waqmapper.checkNotExistTbl(checkmap);

		//주제영역 미존재 체크(PT003)
		checkmap.put("vrfDtlCd", "PT003");
//		waqmapper.checkNonSubjID(checkmap);

		//요청중인 테이블 (PT004)
		checkmap.put("vrfDtlCd", "PT004");
//		waqmapper.checkRequestTbl(checkmap);

		//13. 테이블명 길이 체크를 하는 경우
		checkmap.put("vrfDtlCd", "PT005");
		//waqmapper.checkTblNameLength(checkmap);

		//테이블물리명 명명규칙
		checkmap.put("vrfDtlCd", "PT007");
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
//		waqmapper.checkColCnt(checkmap);
		
		
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)  
		checkmap.put("vrfDtlCd", "PT000");
		waqmapper.checkNotChgData(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
	}

	/** 물리모델 요청서 등록요청  */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 물리모델 요청서 승인 처리  */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqAsisDmnCdVal savevo : (ArrayList<WaqAsisDmnCdVal>)reglist) {
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
			logger.debug("waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @return 
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqAsisDmnCdVal> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqAsisDmnCdVal savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDmnId(id);

			waqmapper.updateByPrimaryKeySelective(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** 물리모델 요청서 상세정보 조회  */
	public WaqAsisDmnCdVal getDmnCdValRqstDetail(WaqAsisDmnCdVal searchVo) {
		return waqmapper.selectDmnCdValDetail(searchVo);
	}

	/**  */
	public List<WaqAsisDmnCdVal> getDmnCdValRqstList(WaqMstr search) {
		return waqmapper.selectDmnCdValListbyMst(search);
	}

	/** 
	 * @throws Exception */
	public int delDmnCdValRqst(WaqMstr reqmst, ArrayList<WaqAsisDmnCdVal> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqAsisDmnCdVal savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqAsisDmnCdVal> list) throws Exception {
		int result = 0;

		List<WaqAsisDmnCdVal> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}


		 	/** WAQ에 있는 반려된 건을 재 등록한다. 
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
        //result = waqcolmapper.insertWaqColRejected(reqmst, oldRqstNo);
		
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);

		return result;

	} 
}
