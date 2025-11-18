/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ImPlRqstServiceImpl.java
 * 2. Package : kr.wise.dq.impv.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 10. 오후 3:30:58
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 10. :            : 신규 개발.
 */
package kr.wise.dq.impv.service.impl;

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
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.bizrule.service.WamBrMstrMapper;
import kr.wise.dq.criinfo.ctq.service.WaqCtqVO;
import kr.wise.dq.impv.service.ImPlRqstService;
import kr.wise.dq.impv.service.WaqCsAnaMstr;
import kr.wise.dq.impv.service.WaqCsAnaMstrMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ImPlRqstServiceImpl.java
 * 3. Package  : kr.wise.dq.impv.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 10. 오후 3:30:58
 * </PRE>
 */
@Service("ImPlRqstService")
public class ImPlRqstServiceImpl implements ImPlRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamBrMstrMapper wamBrMstrMapper;
	
	@Inject
	private WaqCsAnaMstrMapper waqCsAnaMstrMapper;
		
	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;  
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private RequestApproveService requestApproveService;
	
	/** meta */
	@Override
	public List<WamBrMstr> getErrBizruleList(WamBrMstr search) {
//		return wamBrMstrMapper.selectErrBizrule(search);
		return waqCsAnaMstrMapper.selectErrBizrule(search);
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
			for (WaqCsAnaMstr record : (ArrayList<WaqCsAnaMstr>)reglist) {
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				result += saveWaqCsAnaMstrRqst(record);
				//log.debug("saveBizAreaList :: " + res);
				}
		}
				
		
		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}

	public int saveWaqCsAnaMstrRqst(WaqCsAnaMstr record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();
		
		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		logger.debug("USER : {}", user);
		record.setRqstUserId(user.getId());

		
		//기존 테이블에서 데이터를 변경해 저장하기때문에 tmpstatus는 무조건 U....
		//신규인지 변경인지 확인은 WAQ_CS_ANA_MSTR에 있는 PK값으로 유무를 확인한다...
		boolean isNew = true;
		
		WaqCsAnaMstr tmpMstr = waqCsAnaMstrMapper.selectIsNew(record);
		
		if(tmpMstr != null && record.getAnaJobId().equals(tmpMstr.getAnaJobId()) && record.getAnaStrDtm().equals(tmpMstr.getAnaStrDtm())) {
			isNew = false;
		}
		
		
		if(isNew) {
			result = waqCsAnaMstrMapper.insertSelective(record);
		} else {
			result = waqCsAnaMstrMapper.updateByPrimaryKeySelective(record);
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
		result += waqCsAnaMstrMapper.updateCheckInit(rqstNo);
		
		//검증테이블 삭제
		//매퍼 2개 이상 사용사용시 트랜잭션 문제 발생 매퍼 1개 사용
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
//		result += waqDqiMapper.deleteRqstVrfDtls(mstVo);
		
		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqCsAnaMstrMapper.updateObjInfo(rqstNo);
		
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);
				
		//변경된 데이터 없음(IMP00)
		checkmap.put("vrfDtlCd", "IMP00");
		result += waqCsAnaMstrMapper.checkNoChg(checkmap);
		
		//타 요청서 중복자료 검증(IMP01)
		checkmap.put("vrfDtlCd", "IMP01");
		result += waqCsAnaMstrMapper.checkDupJobId(checkmap);

		//없는 데이터 삭제할시 검증(IMP02)
		checkmap.put("vrfDtlCd", "IMP02");
		result += waqCsAnaMstrMapper.checkNoData(checkmap);
		
		//등록가능여부(검증코드) 업데이트
		result += waqCsAnaMstrMapper.updateVrfCd(mstVo);
		
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
		for (WaqCsAnaMstr savevo : (ArrayList<WaqCsAnaMstr>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqCsAnaMstrMapper.updatervwStsCd(savevo);

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
		result += waqCsAnaMstrMapper.updateWaqCUD(rqstno);

		result += waqCsAnaMstrMapper.deleteWAM(rqstno);

		result += waqCsAnaMstrMapper.insertWAM(rqstno);

		result += waqCsAnaMstrMapper.updateWAH(rqstno);

		result += waqCsAnaMstrMapper.insertWAH(rqstno);

		return result;

	}
	

	/** meta */
	@Override
	public List<WaqCsAnaMstr> getVrfImPlListIBS(WaqMstr search) {
		return waqCsAnaMstrMapper.selectImPlRqstList(search);
	}


	





}
