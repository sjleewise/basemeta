
package kr.wise.meta.subjarea.service.impl;

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
import kr.wise.dq.criinfo.bizarea.service.WaqBizAreaVO;
import kr.wise.meta.ddl.service.WaqDdlRel;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.mapping.service.WaqColMap;
import kr.wise.meta.subjarea.service.SubjOwnerRqstService;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.meta.subjarea.service.WaaSubjMapper;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service("subjOwnerRqstService")
public class SubjOwnerRqstServiceImpl implements SubjOwnerRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaSubjMapper waqmapper;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;


	@Override
	public List<WaaSubj> getSubjOwnerRqstList(WaqMstr search) {
		List<WaaSubj> list = waqmapper.selectSubjOwnerRqstList(search);
		return list;
	}

	/**  요청서 저장.. */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		//logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String userNm = user.getName();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaaSubj saveVo : (ArrayList<WaaSubj>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
				
				// 신청자 정보입력, 2019.01.29, hoon
				saveVo.setUserId(userid);
				saveVo.setUserNm(userNm);
				
				//단건 저장...
				result += saveSubjOnwerRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	
	@Override
	public int delSubjOwnerRqstList(WaqMstr reqmst, ArrayList<WaaSubj> list) throws Exception {
		int result = 0;
		for (WaaSubj savevo : list) {
			savevo.setIbsStatus("D");
		}
		result = register(reqmst, list);
		return result;
	}

	
	/** 단건 저장... @return */
	private int saveSubjOnwerRqst(WaaSubj saveVo) {
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			result = waqmapper.insertRqst(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySubjOwner(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}

//	/**  요청서 검증 */
	public int check(WaqMstr mstVo) {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String isadminyn = user.getIsAdminYn();

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizDtlCd    = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteByRqstNo(waqRqstVrfDtls);
		
		//주제영역ID update
		waqmapper.updateSubjId(rqstNo);
		//사용자ID update
		//사용자명 중복 가능성존재 사용자ID 필수 입력으로 변경
//		waqmapper.updateUserId(rqstNo);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
		
		//검증 시작
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizDtlCd);

		//요청서내 중복자료 검증(SOW01)
		checkmap.put("vrfDtlCd", "SOW01");
		waqmapper.checkDup(checkmap);
		
		//미존재 주제영역
		checkmap.put("vrfDtlCd", "SOW02");
		waqmapper.checkSubjExists(checkmap);
		
		//미존재 사용자
		checkmap.put("vrfDtlCd", "SOW03");
		waqmapper.checkUserExists(checkmap);
		
		//사전, PDM, DDL 모든권한이 'N' 경우
//		checkmap.put("vrfDtlCd", "SOW04");
//		waqmapper.checkOwnerYn(checkmap);
		
		//표준사전일 경우 주제영역은 1레벨만 사용 가능
		checkmap.put("vrfDtlCd", "SOW05");
		waqmapper.checkSubjLvlByDic(checkmap);
		//물리모델, DDL 일경우 주제영역은 2레벨만 사용 가능
		checkmap.put("vrfDtlCd", "SOW06");
		waqmapper.checkSubjLvlByModel(checkmap);
		
		//삭제시 존재여부 체크(SOW07)
		checkmap.put("vrfDtlCd", "SOW07");
		waqmapper.checkSubjOwnerExist(checkmap);
		
		//요청사유 10글자 이상, 필수항목 검증
		checkmap.put("vrfDtlCd", "COM01");
		waqRqstVrfDtlsMapper.checkRqstResn(checkmap);

//		변경데이터 없음 (SOW00)
		checkmap.put("vrfDtlCd", "SOW00");
		waqmapper.checkNotChgData(checkmap);

		//등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		waqRqstVrfDtlsMapper.updateNotChgData(checkmap);
		
		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);
		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}
//
	
	/**  요청서 승인 처리 */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaaSubj savevo : (ArrayList<WaaSubj>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);
			result += waqmapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			//logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			//logger.debug(" waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				//logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
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
		
		//신규 대상인 경우 ID 채번한다.
		List<WaaSubj> waqclist =  waqmapper.selectWaqC(rqstno);

		for (WaaSubj savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setSubjOwnerId(id);
			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqmapper.updateidByKey(savevo);
		}
		result += waqmapper.updateWaqCUD(rqstno);
		
		
		result += waqmapper.deleteWAM(rqstno);
		result += waqmapper.insertWAM(rqstno);
		result += waqmapper.updateWAH(rqstno);
		result += waqmapper.insertWAH(rqstno);

		return result ;
	}

	

	/**
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqColMap> list) throws Exception {
		int result = 0;

//		List<WaqColMap> wamlist = waqmapper.selectwamlist(reqmst, list);
//		
//		//logger.debug("regWam2Waq wamlist : {}", wamlist);
//
//		result = register(reqmst, wamlist);

		return result;
	}


	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);

		return result;
	}

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	public int insertSubjOwner(Map<String, Object> map) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
//		map.put("tblnm", "WAQ_DMN");
//		map.put("rqstNo", rqstno);
//		map.put("subjBizDcd", "DIC");
		List<WaaSubj> saveLst = waqmapper.selectSubjOwnerByWaq(map);
		
		for (WaaSubj savevo : saveLst) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setSubjOwnerId(id);
	        result += waqmapper.insertSubjOwnerByWaq(savevo);
		}

		return result;
	}
	
	
	
	

}
