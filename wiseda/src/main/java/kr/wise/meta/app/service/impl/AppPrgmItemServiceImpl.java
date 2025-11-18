package kr.wise.meta.app.service.impl;

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
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.app.service.AppPrgmItemService;
import kr.wise.meta.app.service.WamAppPrgm;
import kr.wise.meta.app.service.WamAppPrgmMapper;
import kr.wise.meta.app.service.WaqAppPrgm;
import kr.wise.meta.app.service.WaqAppPrgmMapper;

@Service("appPrgmItemService")
public class AppPrgmItemServiceImpl implements AppPrgmItemService {

	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;
    
	private final Logger logger = LoggerFactory.getLogger(getClass());

		@Inject
		private WamAppPrgmMapper mapper;
		@Inject
		private WaqAppPrgmMapper waqmapper;

	@Override
	public List<WamAppPrgm> getPrgmItemList(WamAppPrgm data) {

		logger.debug("searchvo:{}", data);

		return mapper.selectList(data);
	}

	@Override
	public WamAppPrgm selectAppPrgmDetail(String appPrgmId) {
		logger.debug(mapper.selectAppPrgmDetail(appPrgmId).toString());
		return mapper.selectAppPrgmDetail(appPrgmId);
	}
	
	public List<WamAppPrgm> selectAppPrgmChangeList(String appPrgmId) {
		logger.debug("search Id:{}", appPrgmId);

		return mapper.selectAppPrgmChangeList(appPrgmId);
	}

	@Override
	public WaqAppPrgm getAppPrgmRqstDetail(WaqAppPrgm searchVo) {
	
		return waqmapper.getAppPrgmRqstDetail(searchVo);
	}
	
	@Override
	public int register(WaqMstr mstVo, List<WaqAppPrgm> reglist) throws Exception {
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
			for (WaqAppPrgm saveVo : (ArrayList<WaqAppPrgm>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveAppPrgmRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	private int saveAppPrgmRqst(WaqAppPrgm saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			result = waqmapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}
	

	/** 물리모델 요청서 검증 insomnia */
	public int check(WaqMstr mstVo) {
	 
		int result = 0;
		
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = "WAQ_APP_PRGM";
		String bizdtlcd = "APM";

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
		//검증 시작
		//물리모델 테이블 검증
		//물리모델 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(AP001)
		checkmap.put("vrfDtlCd", "AP001");
		waqmapper.checkDupApp(checkmap);

		//논리명 중복 검증(AP002)
		//checkmap.put("vrfDtlCd", "AP002");
		//waqmapper.checkDupAppLnm(checkmap);

		//물리명 중복 검증(AP003)
//		checkmap.put("vrfDtlCd", "AP003");
//		waqmapper.checkDupAppPnm(checkmap);

		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		
		
		//테이블이 변경/삭제일 경우 컬럼이 없는 경우 삭제대상으로 추가한다.
		//logger.debug("삭제대상추가 시작");
		//waqcolmapper.insertnoWaqdelCol(rqstNo);
		//logger.debug("삭제대상추가 종료");
		
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)  
		checkmap.put("vrfDtlCd", "AP000");
		waqmapper.checkNotChgData(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
	}

	@Override
	public List<WaqAppPrgm> getAppPrgmItemRqstList(WaqMstr search) {
		return waqmapper.selectAppPrgmListbyMst(search);
	}

	@Override
	public boolean checkEmptyRqst(WaqMstr reqmst) {
		return waqmapper.checkEmptyRqst(reqmst.getRqstNo());
	}
	
	@Override
	public int delAppPrgmItemRqst(WaqMstr reqmst, ArrayList<WaqAppPrgm> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqAppPrgm savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}


	@Override
	public int approve(WaqMstr mstVo, ArrayList<WaqAppPrgm> reglist) throws Exception {
		int result = 0;
		
		WaaBizInfo bz = requestMstService.getBizInfo(mstVo);
		mstVo.setBizInfo(bz);
		String rqstNo = mstVo.getRqstNo();
		

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
        logger.debug("여기여기");
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqAppPrgm savevo : (ArrayList<WaqAppPrgm>)reglist) {
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
			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqAppPrgm> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqAppPrgm savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setAppPrgmId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqAppPrgm> list) throws Exception {
		int result = 0;

		List<WaqAppPrgm> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

}
