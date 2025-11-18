/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 9. 24. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 9. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.stnd.service.MsgRqstService;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.stnd.service.WaqDmnMapper;
import kr.wise.meta.stnd.service.WaqMsg;
import kr.wise.meta.stnd.service.WaqMsgMapper;
import kr.wise.meta.stnd.service.WaqSditmMapper;
import kr.wise.meta.stnd.service.WaqStwdCnfgMapper;
import kr.wise.meta.stnd.service.WaqStwdMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 9. 25. 
 * </PRE>
 */
@Service("msgRqstService")
public class MsgRqstServiceImpl implements MsgRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqSditmMapper waqmapper;

	@Inject
	private WaqMsgMapper waqMsgMapper;

	@Inject
	private WaqStwdMapper waqStwdMapper;

	@Inject
	private WaqDmnMapper waqDmnMapper;

	@Inject
	private WapDvCanDicMapper wapDvCanDicMapper;

	@Inject
	private WapDvCanAsmMapper wapDvCanAsmMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqStwdCnfgMapper waqStwdCnfgMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	private CommonVo wapDvCanAsmVo;
	
	
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
			for (WaqMsg saveVo : (ArrayList<WaqMsg>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
   	        
		 
		 	//if(saveVo.getSysDivCd()==null){	
					saveVo.setSysDivCd("M");
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
	public int saveRqstMsg(WaqMsg saveVo) {
		int result = 0;
		try{
		String tmpstatus = saveVo.getIbsStatus();
		logger.debug("ibsstatus" + saveVo.getIbsStatus());
		if("I".equals(tmpstatus)) {
			result = waqMsgMapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);

			result = waqMsgMapper.updateByPrimaryKeySelective(saveVo);

		} else if ("D".equals(tmpstatus)) {
			result = waqMsgMapper.deleteByPrimaryKey(saveVo);

		}
		}catch(Exception e){
		 logger.debug("메시지 목록 저장 중 오류발생 : )"+saveVo.toString());
		 return -1;
		}
		return result;
	}
	
	
	/** 메시지 요청 상세내용 조회 lsi */
	public WaqMsg getMsgRqstDetail(WaqMsg searchVo) {

		return waqMsgMapper.selectMsgRqstDetail(searchVo);
	}

	
	/** 메시지 요청 리스트 조회 lsi */
	public List<WaqMsg> getMsgRqstList(WaqMstr search) {
		return waqMsgMapper.selectMsgRqstListbyMst(search);
	}
	
		/** 메시지 삭제(waq) lsi
	 * @throws Exception */
	public int delMsgRqstList(WaqMstr reqmst, ArrayList<WaqMsg> list) throws Exception {
		int result = 0;
		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);
		for (WaqMsg savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	
	
		/** msg 검증 lsi */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("MSG");
//		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqMsgMapper.updateCheckInit(rqstNo);

		//검증 시작
		//표준항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_MSG");
		//checkmap.put("colnm", "MSG_CD");
//		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "MSG");
//		checkmap.put("bizDtlCd", mstVo.getBizInfo().getBizDtlCd());

		//등록요청중인 메시지 검증 (MS012)
		checkmap.put("vrfDtlCd", "MS012");
		waqMsgMapper.checkRequestMsg(checkmap);

		//요청서내 중복자료 검증(MS001)
		checkmap.put("vrfDtlCd", "MS001");
		waqMsgMapper.checkDupMsg(checkmap);

		//삭제일때 미존재항목 체크(MS002)
		checkmap.put("vrfDtlCd", "MS002");
		waqMsgMapper.checkNotExistMsg(checkmap);

		//메시지코드 길이(8) 체크(MS003)
		checkmap.put("vrfDtlCd", "MS003");
		waqMsgMapper.checkLenMsg(checkmap);
		
		//메시지코드 명명규칙 체크 메시지코드는 8자리이며 형식은 PREFIX[M] + 유형구분[1] + 업무구분[2] + 일련번호[4]입니다.
		checkmap.put("vrfDtlCd", "MS004");
		waqMsgMapper.checkMsgCdName(checkmap);

		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(MS000)
		checkmap.put("vrfDtlCd", "MS000");
		waqMsgMapper.checkNotChgData(checkmap);

		//도메인 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}
	
	
	




	/** 메시지 등록요청 lsi */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 메시지 승인 lsi */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqMsg savevo : (ArrayList<WaqMsg>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqMsgMapper.updatervwStsCd(savevo);
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
	/** @return lsi
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID를 채번한다.
		List<WaqMsg> waqclist = waqMsgMapper.selectWaqC(rqstno);
		for (WaqMsg savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setMsgId(id);

			waqMsgMapper.updateidByKey(savevo);
		}

		result += waqMsgMapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//result += waqMsgMapper.updateWaqId(rqstno);

//		result += waqmapper.updateUppDmnId(rqstno);

		result += waqMsgMapper.deleteWAM(rqstno);

		result += waqMsgMapper.insertWAM(rqstno);

		result += waqMsgMapper.updateWAH(rqstno);

		result += waqMsgMapper.insertWAH(rqstno);

		//표준단어 구성은 C,U에 대해 기존꺼 삭제 후 다시 저장으로...
		//waqStwdCnfgMapper.deleteWAMItem(rqstno);
		//waqStwdCnfgMapper.insertWAMItem(rqstno);


		return result;
	}

	/** 메시지 변경대상 조회 및 추가... lsi * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, List<WaqMsg> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		ArrayList<WaqMsg> wamlist = waqMsgMapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}
	
}
