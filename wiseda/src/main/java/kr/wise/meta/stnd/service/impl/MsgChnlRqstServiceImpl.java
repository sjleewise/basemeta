/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgChnlRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

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
import kr.wise.meta.stnd.service.MsgChnlRqstService;
import kr.wise.meta.stnd.service.WaqChnlErrMsg;
import kr.wise.meta.stnd.service.WaqChnlErrMsgMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgChnlRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 2015. 9. 25. 
 * </PRE>
 */
@Service("msgChnlRqstService")
public class MsgChnlRqstServiceImpl implements MsgChnlRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqChnlErrMsgMapper waqmapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

	
	/** 메시지 요청서 리스트 저장 */
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
        logger.debug("메시지 입력 시작");
		if(reglist != null) {
			for (WaqChnlErrMsg saveVo : (ArrayList<WaqChnlErrMsg>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
   	        
		 
		 	//if(saveVo.getSysDivCd()==null){	
//					saveVo.setSysDivCd("M");
			//	}
                
				//단건 저장...
				result += saveRqstMsg(saveVo);
		

			}

		}
		logger.debug("메시지 입력 종료");
		//인포타입이 없는 경우 도메인이름으로 업데이트한다...
		//waqmapper.updateItemInfoType(rqstNo);


		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;

	}
	
	/** @return  */
	public int saveRqstMsg(WaqChnlErrMsg saveVo) {
		int result = 0;
		try{
		String tmpstatus = saveVo.getIbsStatus();
		logger.debug("ibsstatus" + saveVo.getIbsStatus());
		if("I".equals(tmpstatus)) {
			result = waqmapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);

			result = waqmapper.updateByPrimaryKeySelective(saveVo);

		} else if ("D".equals(tmpstatus)) {
			result = waqmapper.deleteByPrimaryKey(saveVo);

		}
		}catch(Exception e){
		 logger.debug("메시지 목록 저장 중 오류발생 : )"+saveVo.toString());
		 return -1;
		}
		return result;
	}
	
	
	/** 메시지 요청 상세내용 조회  */
	public WaqChnlErrMsg getMsgRqstDetail(WaqChnlErrMsg searchVo) {

		return waqmapper.selectMsgRqstDetail(searchVo);
	}

	
	/** 메시지 요청 리스트 조회  */
	public List<WaqChnlErrMsg> getMsgRqstList(WaqMstr search) {
		return waqmapper.selectMsgRqstListbyMst(search);
	}
	
		/** 메시지 삭제(waq) 
	 * @throws Exception */
	public int delMsgRqstList(WaqMstr reqmst, ArrayList<WaqChnlErrMsg> list) throws Exception {
		int result = 0;
		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);
		for (WaqChnlErrMsg savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	
	
		/** msg 검증  */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("CEM");
//		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		//검증 시작
		//표준항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_CHNL_ERR_MSG");
		//checkmap.put("colnm", "MSG_CD");
//		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "CEM");
//		checkmap.put("bizDtlCd", mstVo.getBizInfo().getBizDtlCd());

		//등록요청중인 메시지 검증 (MS012)
		checkmap.put("vrfDtlCd", "MS012");
		waqmapper.checkRequestMsg(checkmap);

		//요청서내 중복자료 검증(MS001)
		checkmap.put("vrfDtlCd", "MS001");
		waqmapper.checkDupMsg(checkmap);

		//삭제일때 미존재항목 체크(MS002)
		checkmap.put("vrfDtlCd", "MS002");
		waqmapper.checkNotExistMsg(checkmap);

		//메시지코드 길이(8) 체크(MS003)
		checkmap.put("vrfDtlCd", "MS003");
		waqmapper.checkLenMsg(checkmap);
		
		//메시지코드 명명규칙 체크 메시지코드는 8자리이며 형식은 PREFIX[M] + 유형구분[1] + 업무구분[2] + 일련번호[4]입니다.
		checkmap.put("vrfDtlCd", "MS004");
//		waqmapper.checkMsgCdName(checkmap);

		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(MS000)
		checkmap.put("vrfDtlCd", "MS000");
		waqmapper.checkNotChgData(checkmap);

		//도메인 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}
	
	
	




	/** 메시지 등록요청  */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 메시지 승인  */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqChnlErrMsg savevo : (ArrayList<WaqChnlErrMsg>)reglist) {
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
			logger.debug("메시지 등록요청 waq to wam and wah");

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

	/** @param mstVo
	/** @return 
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID를 채번한다.
		List<WaqChnlErrMsg> waqclist = waqmapper.selectWaqC(rqstno);
		for (WaqChnlErrMsg savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setChnlMsgId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//result += waqmapper.updateWaqId(rqstno);

//		result += waqmapper.updateUppDmnId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		//표준단어 구성은 C,U에 대해 기존꺼 삭제 후 다시 저장으로...
		//waqStwdCnfgMapper.deleteWAMItem(rqstno);
		//waqStwdCnfgMapper.insertWAMItem(rqstno);


		return result;
	}

	/** 메시지 변경대상 조회 및 추가...  * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, List<WaqChnlErrMsg> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		ArrayList<WaqChnlErrMsg> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}
	
}
