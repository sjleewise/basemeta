/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : TblMapRqstServiceImpl.java
 * 2. Package : kr.wise.meta.mapping.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 16. 09:55:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열  : 2014. 7. 16. :            : 신규 개발.
 */
package kr.wise.meta.mapping.service.impl;

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
import kr.wise.meta.mapping.service.CodMapRqstService;
import kr.wise.meta.mapping.service.WamCodMap;
import kr.wise.meta.mapping.service.WamCodMapMapper;
import kr.wise.meta.mapping.service.WaqCodMap;
import kr.wise.meta.mapping.service.WaqCodMapMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : TblMapRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.mapping.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 16.
 * </PRE>
 */
@Service("CodMapRqstService")
public class CodMapRqstServiceImpl implements CodMapRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqCodMapMapper waqmapper;
	
	@Inject
	private WamCodMapMapper wammapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	/** 코드매핑정의서 요청서 저장.. 유성열 */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String username = user.getName();
		

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqCodMap saveVo : (ArrayList<WaqCodMap>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
//				saveVo.setAppCrgpId(userid); //응용담당자ID
//				saveVo.setAppCrgpNm(username); //응용담당자이름
				

				//단건 저장...
				result += saveCodMapRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 코드매핑정의서 단건 저장... @return 유성열 */
	private int saveCodMapRqst(WaqCodMap saveVo) {
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqmapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}

	/** 코드매핑정의서 요청서 검증 유성열 */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//		waqRqstVrfDtls.setBizDtlCd("TBLMAP");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);


		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);


		/***************************
		** Data Update & Create start...
		***************************/
		
		
		//타겟(TOBE) 코드도메인정보 업데이트
		waqmapper.updateTgtDmnInfo(rqstNo);
		
		//코드전환유형 2(ASIS비매핑대상코드)인 경우 타겟정보 clear
		//기본메타소스에서도 주석처리 되어 있음.
		//waqmapper.updateTgtInfoByCnvsType(rqstNo);
		
		//코드전환유형 3(TOBE신규코드)인 경우 소스정보 clear
		waqmapper.updateSrcInfoByCnvsType(rqstNo);			

		//검증 시작
		//코드매핑정의서 검증
		//코드매핑정의서 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(CDM01)
		checkmap.put("vrfDtlCd", "CDM01");
		waqmapper.checkDupCodMap(checkmap);

		//삭제일때 미존재 코드매핑정의서 체크(CDM02)
		checkmap.put("vrfDtlCd", "CDM02");
		waqmapper.checkNotExistCodMap(checkmap);

		//요청중인 코드매핑정의서 (CDM03)
		checkmap.put("vrfDtlCd", "CDM03");
		waqmapper.checkRequestCodMap(checkmap);
		
		//타겟도메인 미존재(CDM04)
		checkmap.put("vrfDtlCd", "CDM04");
		waqmapper.checkNotExistTgtDmn(checkmap);
		
		//타겟코드값 미존재(CDM005)
		checkmap.put("vrfDtlCd", "CDM05");
		waqmapper.checkNotExistTgtCdVal(checkmap);
		
		//누락된 소스(ASIS)정보(CDM06)
		checkmap.put("vrfDtlCd", "CDM06");
		waqmapper.checkNotExistSrcInfo(checkmap);
		waqmapper.checkNotExistSrcInfo1(checkmap);
		
		//신청자외 수정,삭제 불가(CDM07)
//		checkmap.put("vrfDtlCd", "CDM07");
//		waqmapper.checkRqstUser(checkmap);
		
		//응용담당자외 등록,수정,삭제불가(CM008)
		checkmap.put("vrfDtlCd", "CDM08");
//		waqmapper.checkAppCrgp(checkmap);

		//소스도메인 미존재(CDM09)
		checkmap.put("vrfDtlCd", "CDM09");
//		waqmapper.checkNotExistSrcDmn(checkmap);
		
		//소스코드값 미존재(CDM010)
		checkmap.put("vrfDtlCd", "CDM10");
//		waqmapper.checkNotExistSrcCdVal(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우(CDM00)
		checkmap.put("vrfDtlCd", "CDM00");
		waqmapper.checkNotChgData(checkmap);

		//테이블매핑정의서 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}



	/** 코드매핑정의서 요청서 승인 처리 유성열 */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqCodMap savevo : (ArrayList<WaqCodMap>)reglist) {
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
			logger.debug("테이블매핑정의서 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			//result += regWaq2WamCol(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @return 유성열
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqCodMap> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqCodMap savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setCdMapId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** 코드매핑정의서 요청서 상세정보 조회 유성열 */
	public WaqCodMap getCodMapRqstDetail(WaqCodMap searchVo) {
		return waqmapper.selectCodMapDetail(searchVo);
	}

	/** 코드매핑정의서 요청서 정보 조회 유성열 */
	public List<WaqCodMap> getCodMapRqstList(WaqMstr search) {
		return waqmapper.selectCodMapListbyMst(search);
	}
	

	
	/** 코드매핑정의서 타겟코드도메인 정보 조회 유성열 */
	public List<WaqCodMap> getTgtCdDmnList(WaqCodMap search) {
		return waqmapper.selectTgtCdDmnListbyNm(search);
	}
	
	/** 코드매핑정의서 변경대상리스트 정보 조회 유성열 */
	public List<WamCodMap> getCodMapList(WamCodMap search) {
		return wammapper.selectCodMapList(search);
	}

	/** 코드매핑정의서 요청서 삭제 유성열
	 * @throws Exception */
	public int delCodMapRqst(WaqMstr reqmst, ArrayList<WaqCodMap> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqCodMap savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 유성열
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqCodMap> list) throws Exception {
		int result = 0;

		List<WaqCodMap> wamlist = waqmapper.selectwamlist(reqmst, list);
		
		logger.debug("regWam2Waq wamlist : {}", wamlist);

		result = register(reqmst, wamlist);

		return result;
	}


}
