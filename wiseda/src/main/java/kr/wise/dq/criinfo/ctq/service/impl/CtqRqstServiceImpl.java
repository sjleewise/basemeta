package kr.wise.dq.criinfo.ctq.service.impl;

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
import kr.wise.dq.criinfo.ctq.service.CtqRqstService;
import kr.wise.dq.criinfo.ctq.service.WahCtqMapper;
import kr.wise.dq.criinfo.ctq.service.WamCtqMapper;
import kr.wise.dq.criinfo.ctq.service.WaqCtqMapper;
import kr.wise.dq.criinfo.ctq.service.WaqCtqVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("ctqRqstService")
public class CtqRqstServiceImpl implements CtqRqstService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqMstrMapper waqMstrMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private WaqCtqMapper waqCtqMapper;

	@Inject
	private WahCtqMapper wahCtqMapper;

	@Inject
	private WamCtqMapper wamCtqMapper;

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
	public int register(WaqMstr mstVo, List<?> reglist ) throws Exception {
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
			for (WaqCtqVO record : (ArrayList<WaqCtqVO>)reglist) {
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				result += saveWaqCtqRqst(record);
				//log.debug("saveBizAreaList :: " + res);
				}
		}


		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}



	public int saveWaqCtqRqst(WaqCtqVO record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();

		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		record.setRqstUserId(user.getId());

		if("I".equals(tmpststus)) {
			result = waqCtqMapper.insertSelective(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = waqCtqMapper.updateByPrimaryKeySelective(record);
		} else if ("D".equals(tmpststus)){
			result = waqCtqMapper.deleteByPrimaryKey(record);
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
		result += waqCtqMapper.updateCheckInit(rqstNo);

		//검증테이블 삭제
		//매퍼 2개 이상 사용사용시 트랜잭션 문제 발생 매퍼 1개 사용
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
//		result += waqCtqMapper.deleteRqstVrfDtls(mstVo);

		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqCtqMapper.updateObjInfo(rqstNo);

		//상위중요정보항목ID Clear
		result += waqCtqMapper.updateVrfUppCtqIdClear(mstVo);
		//상위중요정보항목ID UPDATE
		result += waqCtqMapper.updateVrfUppBizAareId(mstVo);

		int lvl = waaBscLvlMapper.selectBscLvl("CTQ");
		
		for(int i=0; i<lvl; i++) {
			//LEVEL UPDATE
			result += waqCtqMapper.updateVrfCtqLvl(mstVo);
		}
		
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//중요정보항목 변경된 데이터 없음(CTQ00)
		//상위중요정보항목ID 이용
		checkmap.put("vrfDtlCd", "CTQ00");
		result += waqCtqMapper.checkNoChg(checkmap);

		//요청서내 중복자료 검증(CTQ01)
		checkmap.put("vrfDtlCd", "CTQ01");
		result += waqCtqMapper.checkDupCtq(checkmap);

		//상위중요정보항목명 미존재(CTQ03)
		checkmap.put("vrfDtlCd", "CTQ03");
		result += waqCtqMapper.checkNotExistUppCtq(checkmap);

		//미존재 중요정보항목(삭제시)(CTQ02)
		checkmap.put("vrfDtlCd", "CTQ02");
		result += waqCtqMapper.checkNotExistCtq(checkmap);

		//사용중 중요정보항목(삭제시)(CTQ004)
		waqCtqMapper.checkUsedCtq(checkmap);

		//삭제시 하위 중요정보항목 존재여부 체크(CTQ05)
		checkmap.put("vrfDtlCd", "CTQ05");
		result += waqCtqMapper.checkLowRankBizAareId(checkmap);

		//요청서내 상위중요정보항목 삭제(CTQ06)
		checkmap.put("vrfDtlCd", "CTQ06");
		result += waqCtqMapper.checkDelUppBizAareId(checkmap);

		//결재로직 완료 후 요청중 중요정보항목(CTQ07)
		checkmap.put("vrfDtlCd", "CTQ07");
		result += waqCtqMapper.checkDupCtqRqst(checkmap);
		
		//기본정보레벨 확인(CTQ08)
		checkmap.put("vrfDtlCd",  "CTQ08");
		result += waqCtqMapper.checkCtqBscLvl(checkmap);

		//등록가능여부(검증코드) 업데이트
		result += waqCtqMapper.updateVrfCd(mstVo);

		//요청서명 업데이트
		mstVo.setBizDtlCd("CTQ");
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}


	@Override
	public Map<String, String> delRegCtqList(ArrayList<WaqCtqVO> list, WaqMstr mstVo) {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int res = 0;
		String sRqstNo = mstVo.getRqstNo();

		for (WaqCtqVO record : list) {
			//WAQ 영역 삭제
			res += delRegCtqList(record);
		}

		resultMap.put("result", Integer.toString(res) );
    	resultMap.put("rqstNo", sRqstNo);

		return resultMap;
	}

	public int delRegCtqList(WaqCtqVO delVO) {
		return waqCtqMapper.deleteByPrimaryKey(delVO);
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
	public List<WaqCtqVO> getVrfCtqListIBS(WaqMstr search) {
		return waqCtqMapper.selectCtqRqstList(search);
	}

	/** meta */
	@Override
	public WaqCtqVO getCtqRqstDetail(WaqCtqVO searchVO) {
		return waqCtqMapper.selectRqstCtq(searchVO);
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
		for (WaqCtqVO savevo : (ArrayList<WaqCtqVO>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqCtqMapper.updatervwStsCd(savevo);

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
		List<WaqCtqVO> waqclist =  waqCtqMapper.selectWaqC(rqstno);
		for (WaqCtqVO savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setCtqId(id);
//			savevo.setIbsStatus("U");
			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqCtqMapper.updateidByKey(savevo);
		}
		
		result += waqCtqMapper.updateVrfUppCtqId(rqstno);

		result += waqCtqMapper.updateWaqCUD(rqstno);

		result += waqCtqMapper.deleteWAM(rqstno);

		result += waqCtqMapper.insertWAM(rqstno);

		result += waqCtqMapper.updateWAH(rqstno);

		result += waqCtqMapper.insertWAH(rqstno);

		result += waqCtqMapper.updateWaqFullPath(rqstno);

		result += waqCtqMapper.updateWamFullPath(rqstno);

		result += waqCtqMapper.updateWahFullPath(rqstno);

		return result;

	}



	/** meta
	 * @throws Exception */
	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqCtqVO> list) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		result = waqCtqMapper.insertwam2waq(reqmst, list);
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
		reqmst.getBizInfo().setBizDtlCd("CTQ");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqCtqMapper.insertWaqRejected(reqmst, oldRqstNo);
		
		//마스터 등록
		register(reqmst,null);
		
		//검증
		check(reqmst);
		
		return result;
	}

}
