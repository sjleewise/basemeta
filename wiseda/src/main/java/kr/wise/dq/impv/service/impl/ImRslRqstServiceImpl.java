/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ImRslRqstServiceImpl.java
 * 2. Package : kr.wise.dq.impv.service.impl
 * 3. Comment :
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 10. 오후 3:31:06
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 10. :            : 신규 개발.
 */
package kr.wise.dq.impv.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import kr.wise.commons.damgmt.schedule.service.WamShdJobMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.dq.impv.service.ImRslRqstService;
import kr.wise.dq.impv.service.WaqImActMstr;
import kr.wise.dq.impv.service.WaqImActMstrMapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ImRslRqstServiceImpl.java
 * 3. Package  : kr.wise.dq.impv.service.impl
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오후 3:31:06
 * </PRE>
 */
@Service("ImRslRqstService")
public class ImRslRqstServiceImpl implements ImRslRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	WaqImActMstrMapper waqImActMstrMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private WamShdJobMapper wamShdJobMapper;

	/** meta */
	@Override
	public List<WaqImActMstr> getImRslList(WaqImActMstr search) {
		return waqImActMstrMapper.selectImRslList(search);
	}

	/** meta */
	@Override
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
				if( "N".equals(mstVo.getRqstStepCd())) {
					requestMstService.insertWaqMst(mstVo);
				}

		//요청서 번호 확인...
		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		//업무규칙의 개선계획 등록....
		if(reglist != null) {
			for (WaqImActMstr record : (ArrayList<WaqImActMstr>)reglist) {
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				result += saveWaqImActMstrRqst(record);
				//log.debug("saveBizAreaList :: " + res);
				}
		}


		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}

	public int saveWaqImActMstrRqst(WaqImActMstr record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();

		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		logger.debug("USER : {}", user);
		record.setRqstUserId(user.getId());


		//기존 테이블에서 데이터를 변경해 저장하기때문에 tmpstatus는 무조건 U....
		//신규인지 변경인지 확인은 WAQ_CS_ANA_MSTR에 있는 PK값으로 유무를 확인한다...
		boolean isNew = true;

		WaqImActMstr tmpMstr = waqImActMstrMapper.selectIsNew(record);

		if(tmpMstr != null && record.getAnaJobId().equals(tmpMstr.getAnaJobId()) && record.getAnaStrDtm().equals(tmpMstr.getAnaStrDtm())) {
			isNew = false;
		}


		if(isNew) {
			result = waqImActMstrMapper.insertSelective(record);
		} else {
			result = waqImActMstrMapper.updateByPrimaryKeySelective(record);
		}
//		else if ("D".equals(tmpststus)){
//			result = waqCsAnaMstrMapper.deleteByPrimaryKey(record);
//		}

		return result;
	}

	/** meta */
	@Override
	public int check(WaqMstr mstVo) {

		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setRqstNo(rqstNo);
		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizDcd());

		//검증코드  검토상태코드  초기화
		result += waqImActMstrMapper.updateCheckInit(rqstNo);

		//검증테이블 삭제
		//매퍼 2개 이상 사용사용시 트랜잭션 문제 발생 매퍼 1개 사용
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
//		result += waqDqiMapper.deleteRqstVrfDtls(mstVo);

		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqImActMstrMapper.updateObjInfo(rqstNo);

		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//변경된 데이터 없음(IMR00)
		checkmap.put("vrfDtlCd", "IMR00");
		result += waqImActMstrMapper.checkNoChg(checkmap);

		//타 요청서 중복자료 검증(IMR01)
		checkmap.put("vrfDtlCd", "IMR01");
		result += waqImActMstrMapper.checkDupJobId(checkmap);

		//없는 데이터 삭제할시 검증(IMR02)
		checkmap.put("vrfDtlCd", "IMR02");
		result += waqImActMstrMapper.checkNoData(checkmap);

		//등록가능여부(검증코드) 업데이트
		result += waqImActMstrMapper.updateVrfCd(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}

	/** meta */
	@Override
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** meta */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqImActMstr savevo : (ArrayList<WaqImActMstr>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqImActMstrMapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		boolean waq2wam = false;
		if(mstVo.getAprLvl() > 0){
			// S: 임시저장 Q: 결재요청 A : 승인
			waq2wam = requestApproveService.setApproveProcess(mstVo);
		}else{
			//요청 마스터를 업데이트 한다.
			mstVo.setRqstStepCd("A");
			//요청상태 승인으로 UPDATE
			result = requestMstService.updateRqstPrcStep(mstVo);
			//승인자, 승인시간 UPDATE
			result = requestMstService.updateApproveInfo(mstVo);
			waq2wam = true;
		}
		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("waq to wam and wah");

			result = 0;
			result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

			//최종결재가 완료된 내역중, 스케줄작업 삭제관리여부가 Y인 작업이 있을경우 WAM_SHD_JOB 테이블에서 데이터를 삭제한다...
			if (result > 0) {
				int delCnt = 0;
				logger.debug("WAM_SHD_JOB에서 데이터 삭제...");
				for (WaqImActMstr savevo : (ArrayList<WaqImActMstr>)reglist) {
					//스케줄작업 삭제관리여부가 Y이면서 개선결과코드의 값이 03(개선부락)인 경우 스케줄을 삭제한다...
					if (savevo.getShdJobDelRqstYn().equals("Y") && savevo.getImRslCd().equals("03")) {


						delCnt += wamShdJobMapper.deleteShdJob(savevo.getAnaJobId());
					}

				}
				logger.debug("삭제된 스케줄 갯수는 {}개 입니다.", delCnt);
			}



		}

		return result;
	}

	/** @param mstVo insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID 채번한다. --> 키가 이미 기존의 업무규칙ID를 따르므로 채번할 필요 없음.
//		List<WaqCsAnaMstr> waqclist =  waqCsAnaMstrMapper.selectWaqC(rqstno);
//		for (WaqCsAnaMstr savevo : waqclist) {
//			String id = objectIdGnrService.getNextStringId();
//			savevo.setCtqId(id);
//			savevo.setIbsStatus("U");
//			//신규 등록건에 대해 id 업데이트 처리한다....
//			result += waqCsAnaMstrMapper.updateidByKey(savevo);
//		}
		result += waqImActMstrMapper.updateWaqCUD(rqstno);

		result += waqImActMstrMapper.deleteWAM(rqstno);

		result += waqImActMstrMapper.insertWAM(rqstno);

		result += waqImActMstrMapper.updateWAH(rqstno);

		result += waqImActMstrMapper.insertWAH(rqstno);

		return result;

	}

	/** meta */
	@Override
	public List<WaqImActMstr> getVrfImRslListIBS(WaqMstr search) {
		return waqImActMstrMapper.selectImRslRqstList(search);
	}

	/** meta */
	@Override
	public List<WaqImActMstr> getRslList(WaqImActMstr search) {
		return waqImActMstrMapper.selectRsl(search);
	}

	/** kchoi */
	@Override
	public List<WaqImActMstr> getImplHstLst(String anaJobId, String anaStrDtm) {
		
		WaqImActMstr mstr = new WaqImActMstr();
		
		SimpleDateFormat beforeDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");   
		SimpleDateFormat afterDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Timestamp timestamp = null;
		
		try {
			timestamp = Timestamp.valueOf(afterDateFormat.format(beforeDateFormat.parse(anaStrDtm)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug(anaStrDtm);
		logger.debug(timestamp.toString());

		mstr.setAnaJobId(anaJobId);
		mstr.setAnaStrDtm(timestamp);
		
		return waqImActMstrMapper.getImplHstLst(mstr);
	}

	/** kchoi */
	@Override
	public List<WaqImActMstr> getImrslHstLst(String anaJobId, String anaStrDtm) {
		
		WaqImActMstr mstr = new WaqImActMstr();
		
		SimpleDateFormat beforeDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");   
		SimpleDateFormat afterDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Timestamp timestamp = null;
		
		try {
			timestamp = Timestamp.valueOf(afterDateFormat.format(beforeDateFormat.parse(anaStrDtm)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug(anaStrDtm);
		logger.debug(timestamp.toString());

		mstr.setAnaJobId(anaJobId);
		mstr.setAnaStrDtm(timestamp);
		
		return waqImActMstrMapper.getImrslHstLst(mstr);
	}


}
