package kr.wise.dq.criinfo.bizarea.service.impl;

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
import kr.wise.dq.criinfo.bizarea.service.BizAreaRqstService;
import kr.wise.dq.criinfo.bizarea.service.WahBizAreaMapper;
import kr.wise.dq.criinfo.bizarea.service.WamBizAreaMapper;
import kr.wise.dq.criinfo.bizarea.service.WaqBizAreaMapper;
import kr.wise.dq.criinfo.bizarea.service.WaqBizAreaVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("bizAreaRqstService")
public class BizAreaRqstServiceImpl implements BizAreaRqstService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqMstrMapper waqMstrMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private WaqBizAreaMapper waqBizAreaMapper;

	@Inject
	private WahBizAreaMapper wahBizAreaMapper;

	@Inject
	private WamBizAreaMapper wamBizAreaMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
    private EgovIdGnrService objectIdGnrService;

	@Inject
	private WaaBscLvlMapper waaBscLvlMapper;

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

		//업무영역 등록....(WAQ_BIZ_AREA 등록)
		if(reglist != null) {
			for (WaqBizAreaVO record : (ArrayList<WaqBizAreaVO>)reglist) {
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				result += saveWaqBizAreaRqst(record);
				//log.debug("saveBizAreaList :: " + res);
				}
		}

		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;

	}




	public int saveWaqBizAreaRqst(WaqBizAreaVO record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();

		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		record.setRqstUserId(user.getId());
		if("I".equals(tmpststus)) {
			result = waqBizAreaMapper.insertSelective(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = waqBizAreaMapper.updateByPrimaryKeySelective(record);
		} else if ("D".equals(tmpststus)){
			result = waqBizAreaMapper.deleteByPrimaryKey(record);
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
		result += waqBizAreaMapper.updateCheckInit(rqstNo);

		//검증테이블 삭제
		//매퍼 2개 이상 사용사용시 트랜잭션 문제 발생 매퍼 1개 사용
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
//		result += waqBizAreaMapper.deleteRqstVrfDtls(mstVo);

		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqBizAreaMapper.updateObjInfo(rqstNo);

		//상위업무영역ID UPDATE를 위한 ID컬럼 초기화
		result += waqBizAreaMapper.updateVrfUppBizAreaIdClear(mstVo);
		
		//상위업무영역ID UPDATE
		result += waqBizAreaMapper.updateVrfUppBizAareId(mstVo);
		
		//부서명ID UPDATE
		result += waqBizAreaMapper.updateVrfDeptId(mstVo);

		int lvl = waaBscLvlMapper.selectBscLvl("BZA");
		
		for(int i=0; i<lvl; i++) {
			//LEVEL UPDATE
			result += waqBizAreaMapper.updateVrfBizAreaLvl(mstVo);
		}
		
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);


		//업무영역 변경된 데이터 없음(BA000)
		//상위업무영역ID 이용
		checkmap.put("vrfDtlCd", "BA000");
		result += waqBizAreaMapper.checkNoChg(checkmap);

		//요청서내 중복자료 검증(BA001)
		checkmap.put("vrfDtlCd", "BA001");
		result += waqBizAreaMapper.checkDupBizArea(checkmap);

		//상위업무영역명 미존재(BA003)
		checkmap.put("vrfDtlCd", "BA003");
		result += waqBizAreaMapper.checkNotExistUppBizArea(checkmap);

		//미존재 업무영역(삭제시)(BA002)
		checkmap.put("vrfDtlCd", "BA002");
		result += waqBizAreaMapper.checkNotExistBizArea(checkmap);

		//사용중 업무영역(삭제시)(BA0004)
		//업무규칙 테이블 생성 후 구현
		checkmap.put("vrfDtlCd", "BA004");
		waqBizAreaMapper.checkUsedBizArea(checkmap);

		//삭제시 하위 업무영역 존재여부 체크(BA005)
		checkmap.put("vrfDtlCd", "BA005");
		result += waqBizAreaMapper.checkLowRankBizAareId(checkmap);

		//요청서내 상위업무영역 삭제(BA006)
		checkmap.put("vrfDtlCd", "BA006");
		result += waqBizAreaMapper.checkDelUppBizAareId(checkmap);

		//결재로직 완료 후 요청중 업무영역(BA007)
		checkmap.put("vrfDtlCd", "BA007");
		result += waqBizAreaMapper.checkDupBizAreaRqst(checkmap);
		
		//기본정보레벨 불일치 (BA008)
		checkmap.put("vrfDtlCd", "BA008");
		result += waqBizAreaMapper.checkBizAreaBscLvl(checkmap);

		//등록가능여부(검증코드) 업데이트
		result += waqBizAreaMapper.updateVrfCd(mstVo);

		//요청서명 업데이트
		mstVo.setBizDtlCd("BZA");
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}


	@Override
	public int delRegBizAreaList(ArrayList<WaqBizAreaVO> list) {
		int res = 0;

		for (WaqBizAreaVO record : list) {
			//WAQ 영역 삭제
			res += delRegBizAreaList(record);
		}
		return res;
	}

	public int delRegBizAreaList(WaqBizAreaVO delVO) {
		return waqBizAreaMapper.deleteByPrimaryKey(delVO);
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
	public WaqBizAreaVO getBizAreaRqstDetail(WaqBizAreaVO searchVO) {
		return waqBizAreaMapper.selectRqstBizArea(searchVO);
	}





	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqBizAreaVO savevo : (ArrayList<WaqBizAreaVO>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqBizAreaMapper.updatervwStsCd(savevo);

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

		//신규 대상인 경우 ID 채번한다.
		List<WaqBizAreaVO> waqclist =  waqBizAreaMapper.selectWaqC(rqstno);

		for (WaqBizAreaVO savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setBizAreaId(id);
//			savevo.setIbsStatus("U");

			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqBizAreaMapper.updateidByKey(savevo);
		}

		result += waqBizAreaMapper.updateVrfUppBizAreaId(rqstno);
		
		result += waqBizAreaMapper.updateWaqCUD(rqstno);

		result += waqBizAreaMapper.deleteWAM(rqstno);

		result += waqBizAreaMapper.insertWAM(rqstno);

		result += waqBizAreaMapper.updateWAH(rqstno);

		result += waqBizAreaMapper.insertWAH(rqstno);

		result += waqBizAreaMapper.updateVrtFullPath(mstVo);

		result += waqBizAreaMapper.updateWamFullPath(mstVo);

		result += waqBizAreaMapper.updateWahFullPath(mstVo);

		return result;

	}


	@Override
	public List<WaqBizAreaVO> getVrfBizAreaListIBS(WaqMstr search) {
		return waqBizAreaMapper.selectBizAreaRqstList(search);
	}


	/** meta
	 * @throws Exception */
	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqBizAreaVO> list) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		result = waqBizAreaMapper.insertwam2waq(reqmst, list);
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
		reqmst.setRqstUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("BZA");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqBizAreaMapper.insertWaqRejected(reqmst, oldRqstNo);
		
		//마스터 등록
		register(reqmst, null);
		
		//검증
		check(reqmst);
		
		return result;
	}


}
