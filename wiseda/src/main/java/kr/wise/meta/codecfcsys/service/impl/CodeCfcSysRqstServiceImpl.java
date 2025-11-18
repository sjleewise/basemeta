/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysRqstImpl.java
 * 2. Package : kr.wise.meta.codecfcsys.service.impl
 * 3. Comment : 
 * 4. 작성자  : shshin
 * 5. 작성일  : 2014. 7. 29. 오후 4:20:54
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    shshin : 2014. 7. 29. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service.impl;

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
import kr.wise.meta.codecfcsys.service.CodeCfcSysRqstService;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItem;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysItemMapper;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSysMapper;
import kr.wise.meta.stnd.service.WaqStwd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysRqstImpl.java
 * 3. Package  : kr.wise.meta.codecfcsys.service.impl
 * 4. Comment  : 
 * 5. 작성자   : shshin
 * 6. 작성일   : 2014. 7. 29. 오후 4:20:41
 * </PRE>
 */
@Service("CodeCfcSysRqstService")
public class CodeCfcSysRqstServiceImpl implements CodeCfcSysRqstService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqCodeCfcSysMapper waqCodeCfcSysMapper;
	
	@Inject
	private WaqCodeCfcSysItemMapper waqCodeCfcSysItemMapper;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    


    /** meta */
    @Override
	public List<WaqCodeCfcSysItem> getCodeCfcSysRqstItemList(WaqMstr search) {
		return waqCodeCfcSysItemMapper.selectCodeCfcSysRqstItemList(search);
	}
	
	/** meta */
	@Override
	/** 코드분류체계 항목 요청서 저장.. meta */
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
			for (WaqCodeCfcSys saveVo : (ArrayList<WaqCodeCfcSys>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveCodeCfcSysRqst(saveVo);
			}
		}
		
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	
	/** 코드분류체계 요청서 단건 저장... @return meta */
	/** @param saveVo meta */
	@Override
	public int saveCodeCfcSysRqst(WaqCodeCfcSys saveVo) {
		
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//				String objid = objectIdGnrService.getNextStringId();
//				saveVo.setStwdId(objid);
			result = waqCodeCfcSysMapper.insertSelective(saveVo);
			//초기 등록시 WAM에 해당하는 항목 존재시 추가...
			waqCodeCfcSysItemMapper.insertByRqstSno(saveVo);
			
		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqCodeCfcSysMapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
				result = waqCodeCfcSysMapper.deleteByPrimaryKey(saveVo.getRqstNo(), saveVo.getRqstSno());
			//항목 요청 리스트 삭제.....
				waqCodeCfcSysItemMapper.deleteWaq(saveVo);
		}
		
		return result;
	}

	/** meta */
	@Override
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		logger.debug("mstVo : {}", mstVo);
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//		waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("CCI");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqCodeCfcSysMapper.updateCheckInit(rqstNo);

		//요청구분 업데이트(항목)
		waqCodeCfcSysItemMapper.updateRqstDcd(rqstNo);
		
		//항목컬럼순서 업데이트...
		//컬럼순서대로 정렬한 다음 ROWNUM으로 업데이트...
		//오라클 전용으로 짜여진 쿼리니, 필요시 rqstSno만큼 For문 돌려서 처리해야할듯..
		waqCodeCfcSysItemMapper.updateSeqOrd(rqstNo);
		
		//등록유형코드(C/U/D), 검증코드 업데이트(항목)
		waqCodeCfcSysItemMapper.updateCheckInit(rqstNo);
		
		//검증 시작
		//코드분류체계 테이블 검증
		//코드분류체계 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		

		//타 요청서 등록요청건(CCS01)
		checkmap.put("vrfDtlCd", "CCS01");
		waqCodeCfcSysMapper.checkRequestCcs(checkmap);
		
		
		
		//삭제대상 미존재(CCS03)
		checkmap.put("vrfDtlCd", "CCS03");
		waqCodeCfcSysMapper.checkNotExistCcs(checkmap);		
		
		//요청서내 동일항목 존재(CCS04)
		checkmap.put("vrfDtlCd", "CCS04");
		waqCodeCfcSysMapper.checkDupCcs(checkmap);
		
		

		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_CODE_CFC_SYS_ITEM");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "CCI");
		
		//항목의 마지막건 구분자 없어야함(CCS02) //미완성
		checkmap2.put("vrfDtlCd", "CCS02");
		waqCodeCfcSysItemMapper.checkLastSpt(checkmap2);
		
		//항목 순서중복(CCS05)
		checkmap2.put("vrfDtlCd", "CCS05");
		waqCodeCfcSysItemMapper.checkDupItmSeq(checkmap2);
		
		//항목명 중복(CCS06)
		checkmap2.put("vrfDtlCd", "CCS06");
		waqCodeCfcSysItemMapper.checkDupItmNm(checkmap2);
		
		//항목형식, 자릿수 불일치(CCS07)
		checkmap2.put("vrfDtlCd", "CCS07");
		waqCodeCfcSysItemMapper.checkNotEqualFrmLen(checkmap2);
		
		//항목 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
		
		
		//항목 오류(CCS99)
		checkmap.put("vrfDtlCd", "CCS99");
		waqCodeCfcSysMapper.checkItemErr(checkmap);
		
		//변경내역 없음(CCS00)
		checkmap.put("vrfDtlCd", "CCS00");
		waqCodeCfcSysMapper.checkNotChgData(checkmap);
		
		
		
		//코드분류체계 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}


	/** 코드분류체계 요청서 승인 처리 meta */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqCodeCfcSys savevo : (ArrayList<WaqCodeCfcSys>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqCodeCfcSysMapper.updatervwStsCd(savevo);
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
			logger.debug("코드분류체계 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamItem(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}
			
		}

		return result;
	}

	/** @return meta
	 * @throws Exception */
	private int regWaq2WamItem(WaqMstr mstVo) throws Exception {
		int result = 0; 

		String rqstno = mstVo.getRqstNo();
		
		//ID는 WaqCodeCfcSys를 따라가므로 필요 없음.
//		List<WaqCodeCfcSysItem> waqclist = waqCodeCfcSysItemMapper.selectWaqC(rqstno);
//
//		for (WaqCodeCfcSysItem savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setPdmColId(id);
//
//			waqcolmapper.updateidByKey(savevo);
//		}

		result += waqCodeCfcSysItemMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqCodeCfcSysItemMapper.updateWaqId(rqstno);

		result += waqCodeCfcSysItemMapper.deleteWAM(rqstno);

		result += waqCodeCfcSysItemMapper.insertWAM(rqstno);

		result += waqCodeCfcSysItemMapper.updateWAH(rqstno);

		result += waqCodeCfcSysItemMapper.insertWAH(rqstno);


		return result ;
	}
//
	/** @return meta
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqCodeCfcSys> waqclist = waqCodeCfcSysMapper.selectWaqC(rqstno);

		for (WaqCodeCfcSys savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setCodeCfcSysId(id);

			waqCodeCfcSysMapper.updateidByKey(savevo);
			waqCodeCfcSysItemMapper.updateidByKey(savevo);
		}

		result += waqCodeCfcSysMapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqCodeCfcSysMapper.updateWaqId(rqstno);

		result += waqCodeCfcSysMapper.deleteWAM(rqstno);

		result += waqCodeCfcSysMapper.insertWAM(rqstno);

		result += waqCodeCfcSysMapper.updateWAH(rqstno);

		result += waqCodeCfcSysMapper.insertWAH(rqstno);

		return result;
	}

	/** shshin
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqCodeCfcSys> list) throws Exception {
		int result = 0;

		List<WaqCodeCfcSys> wamlist = waqCodeCfcSysMapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}


	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



	/** meta */
	@Override
	public List<WaqCodeCfcSys> getRqstList(WaqMstr search) {
		return waqCodeCfcSysMapper.selectCodeCfcSysListbyMst(search);
	}

	/** meta */
	@Override
	public WaqCodeCfcSys getCodeCfcSysDetail(WaqCodeCfcSysItem searchVo) {
		return waqCodeCfcSysMapper.selectByPrimaryKey(searchVo.getRqstNo(), searchVo.getRqstSno());
	}

	/** meta 
	 * @throws Exception */
	@Override
	public int delCodeCfcSysRqst(WaqMstr reqmst, ArrayList<WaqCodeCfcSys> list) throws Exception {
		int result = 0;

		for (WaqCodeCfcSys savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** meta */
	@Override
	public int deleteOldCodeCfcSysInfo(WaqMstr reqmst) {
		return waqCodeCfcSysMapper.deleteOldCodeCfcSysInfo(reqmst);
	}


}
