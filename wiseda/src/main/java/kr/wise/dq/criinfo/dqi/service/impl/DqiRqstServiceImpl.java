package kr.wise.dq.criinfo.dqi.service.impl;

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
import kr.wise.commons.rqstmst.service.WaqMstrMapper;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvlMapper;
import kr.wise.dq.criinfo.dqi.service.DqiRqstService;
import kr.wise.dq.criinfo.dqi.service.WahDqiMapper;
import kr.wise.dq.criinfo.dqi.service.WamDqiMapper;
import kr.wise.dq.criinfo.dqi.service.WaqDqiMapper;
import kr.wise.dq.criinfo.dqi.service.WaqDqiVO;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibatis.common.logging.Log;


@Service("dqiRqstService")
public class DqiRqstServiceImpl implements DqiRqstService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqMstrMapper waqMstrMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private WaqDqiMapper waqDqiMapper;

	@Inject
	private WahDqiMapper wahDqiMapper;

	@Inject
	private WamDqiMapper wamDqiMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
    private EgovIdGnrService objectIdGnrService;

	@Inject
	private WaaBscLvlMapper waaBscLvlMapper;
	
	@Override
	public List<WaqDqiVO> getVrfDqiListIBS(WaqMstr search) {
		return waqDqiMapper.selectDqiRqstList(search);
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

		//데이터품질지표 등록....
		if(reglist != null) {
			for (WaqDqiVO record : (ArrayList<WaqDqiVO>)reglist) {
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				result += saveWaqDqiRqst(record);
				//log.debug("saveBizAreaList :: " + res);
				}
		}


		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}



	public int saveWaqDqiRqst(WaqDqiVO record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();

		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		record.setRqstUserId(user.getId());

		if("I".equals(tmpststus)) {
			result = waqDqiMapper.insertSelective(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = waqDqiMapper.updateByPrimaryKeySelective(record);
		} else if ("D".equals(tmpststus)){
			result = waqDqiMapper.deleteByPrimaryKey(record);
		}

		return result;
	}


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
		result += waqDqiMapper.updateCheckInit(rqstNo);

		//검증테이블 삭제
		//매퍼 2개 이상 사용사용시 트랜잭션 문제 발생 매퍼 1개 사용
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
//		result += waqDqiMapper.deleteRqstVrfDtls(mstVo);

		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqDqiMapper.updateObjInfo(rqstNo);

		//상위데이터품질지표ID 초기화
		result += waqDqiMapper.updateVrfUppDqiIdClear(mstVo);
		
		//상위데이터품질지표ID UPDATE
		result += waqDqiMapper.updateVrfUppBizAareId(mstVo);

		int lvl = waaBscLvlMapper.selectBscLvl("DQI");
		
		for(int i=0; i<lvl; i++) {
			//LEVEL UPDATE
			result += waqDqiMapper.updateVrfDqiLvl(mstVo);
		}
		
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//데이터품질지표 변경된 데이터 없음(DQI00)
		//상위데이터품질지표ID 이용
		checkmap.put("vrfDtlCd", "DQI00");
		result += waqDqiMapper.checkNoChg(checkmap);

		//요청서내 중복자료 검증(DQI01)
		checkmap.put("vrfDtlCd", "DQI01");
		result += waqDqiMapper.checkDupDqi(checkmap);

		//상위데이터품질지표명 미존재(DQI03)
		checkmap.put("vrfDtlCd", "DQI03");
		result += waqDqiMapper.checkNotExistUppDqi(checkmap);

		//미존재 데이터품질지표(삭제시)(DQI02)
		checkmap.put("vrfDtlCd", "DQI02");
		result += waqDqiMapper.checkNotExistDqi(checkmap);

		//사용중 데이터품질지표(삭제시)(DQI004)
		waqDqiMapper.checkUsedDqi(checkmap);

		//삭제시 하위 데이터품질지표 존재여부 체크(DQI05)
		checkmap.put("vrfDtlCd", "DQI05");
		result += waqDqiMapper.checkLowRankBizAareId(checkmap);

		//요청서내 상위데이터품질지표 삭제(DQI06)
		checkmap.put("vrfDtlCd", "DQI06");
		result += waqDqiMapper.checkDelUppBizAareId(checkmap);

		//결재로직 완료 후 요청중 데이터품질지표(DQI07)
		checkmap.put("vrfDtlCd", "DQI07");
		result += waqDqiMapper.checkDupDqiRqst(checkmap);
		
		//기본정보레벨 확인 (DQI08)
		checkmap.put("vrfDtlCd", "DQI08");
		result += waqDqiMapper.checkDqiBscLvl(checkmap);

		//등록가능여부(검증코드) 업데이트
		result += waqDqiMapper.updateVrfCd(mstVo);

		//요청서명 업데이트
		mstVo.setBizDtlCd("DQI");
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}


	@Override
	public Map<String, String> delRegDqiList(ArrayList<WaqDqiVO> list, WaqMstr mstVo) {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int res = 0;
		String sRqstNo = mstVo.getRqstNo();

		for (WaqDqiVO record : list) {
			//WAQ 영역 삭제
			res += delRegDqiList(record);
		}

		resultMap.put("result", Integer.toString(res) );
    	resultMap.put("rqstNo", sRqstNo);

		return resultMap;
	}

	public int delRegDqiList(WaqDqiVO delVO) {
		return waqDqiMapper.deleteByPrimaryKey(delVO);
	}

	/*
	 * 삭제 예정
	 */
	@Override
	public int submit( WaqMstr mstVO) {
		return 0;
	}



	//waq_mstr 테이블 승인정보 update
	public int updateWaqMstr(WaqMstr mstVo){
		int result = 0;
		result = waqMstrMapper.updateWaqMstrAprvInfo(mstVo);
		return result;
	}

	/** meta */
	@Override
	public WaqDqiVO getDqiRqstDetail(WaqDqiVO searchVO) {
		return waqDqiMapper.selectRqstDqi(searchVO);
	}

	/** meta */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqDqiVO savevo : (ArrayList<WaqDqiVO>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqDqiMapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			log.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
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
			log.debug("waq to wam and wah");

			result = 0;
			result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				log.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
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
//		//신규 대상인 경우 ID 채번한다.
		List<WaqDqiVO> waqclist =  waqDqiMapper.selectWaqC(rqstno);
		for (WaqDqiVO savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setDqiId(id);
//			savevo.setIbsStatus("U");
			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqDqiMapper.updateidByKey(savevo);
		}
		
		result += waqDqiMapper.updateVrfUppDqiId(rqstno);
		
		result += waqDqiMapper.updateWaqCUD(rqstno);

		result += waqDqiMapper.deleteWAM(rqstno);

		result += waqDqiMapper.insertWAM(rqstno);

		result += waqDqiMapper.updateWAH(rqstno);

		result += waqDqiMapper.insertWAH(rqstno);

		result += waqDqiMapper.updateWaqFullPath(rqstno);

		result += waqDqiMapper.updateWamFullPath(rqstno);

		result += waqDqiMapper.updateWahFullPath(rqstno);

		return result;

	}

	/** meta
	 * @throws Exception */
	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDqiVO> list) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		
		result = waqDqiMapper.insertwam2waq(reqmst, list);
		register(reqmst, null);
		/*for (WaqStwd saveVo : list) {
			saveVo.setIbsStatus("I");
			saveVo.setRqstDcd("CU");
		}
		return register(reqmst, list);*/

		return result;
	}

	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("DQI");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqDqiMapper.insertWaqRejected(reqmst, oldRqstNo);

		//마스터 등록
		register(reqmst,null);
		
		//검증
		check(reqmst);
		
		return result;
	}
}
