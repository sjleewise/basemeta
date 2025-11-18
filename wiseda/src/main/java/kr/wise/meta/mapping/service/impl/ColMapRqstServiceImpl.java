/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColMapRqstServiceImpl.java
 * 2. Package : kr.wise.meta.mapping.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 24. 19:36:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열  : 2014. 7. 24. :            : 신규 개발.
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
import kr.wise.commons.util.UtilString;
import kr.wise.meta.mapping.service.ColMapRqstService;
import kr.wise.meta.mapping.service.TblMapRqstService;
import kr.wise.meta.mapping.service.WamColMap;
import kr.wise.meta.mapping.service.WamColMapMapper;
import kr.wise.meta.mapping.service.WamTblMap;
import kr.wise.meta.mapping.service.WamTblMapMapper;
import kr.wise.meta.mapping.service.WaqColMap;
import kr.wise.meta.mapping.service.WaqColMapMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ColMapRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.mapping.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 24. 19:36:00
 * </PRE>
 */
@Service("colMapRqstService")
public class ColMapRqstServiceImpl implements ColMapRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqColMapMapper waqmapper;
	
	@Inject
	private WamColMapMapper wamcolmapper;
	
	@Inject
	private WamTblMapMapper wamtblmapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


	/** 컬럼매핑정의서 요청서 저장.. 유성열 */
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
			for (WaqColMap saveVo : (ArrayList<WaqColMap>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
//				saveVo.setAppCrgpId(userid); //응용담당자ID
//				saveVo.setAppCrgpNm(username); //응용담당자이름
				
				//체크박스 해제되어 있을 경우 N 값 지정
				if(UtilString.isBlank(saveVo.getTgtPkYn()))
				{
					saveVo.setTgtPkYn("N");  //PK여부
				}
				
				if(UtilString.isBlank(saveVo.getTgtNonulYn()))
				{
					saveVo.setTgtNonulYn("N");  //NotNull여부
				}
				

				//단건 저장...
				result += saveColMapRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 컬럼매핑정의서 단건 저장... @return 유성열 */
	private int saveColMapRqst(WaqColMap saveVo) {
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

//	/** 컬럼매핑정의서 요청서 검증 유성열 */
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
		
		//컬럼매핑유형이 신규인 경우 소스(ASIS)정보 공백으로 업데이트
		waqmapper.updateSrcInfotoBlank(rqstNo);
		
		//타겟테이블(TOBE) 주제영역업데이트
		waqmapper.updateTgtSubjInfo(rqstNo);
		
		//타겟테이블(TOBE) 주제영역업데이트(엑셀업로드_신규)
		waqmapper.updateTgtSubjInfoForXlsC(rqstNo);
		
		//타겟테이블(TOBE) 주제영역업데이트(엑셀업로드_변경)
		waqmapper.updateTgtSubjInfoForXlsU(rqstNo);
		
		//타겟테이블(TOBE) DB정보 업데이트
		waqmapper.updateTgtDbInfo(rqstNo);
		
		//타겟테이블(TOBE) 컬럼정보 업데이트
		waqmapper.updateTgtColInfo(rqstNo);
		
		//테이블매핑정의서ID 업데이트
		waqmapper.updateTgtTblId(rqstNo);

		//검증 시작
		//테이블매핑정의서 검증
		//테이블매핑정의서 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(CM001)
		checkmap.put("vrfDtlCd", "CM001");
		waqmapper.checkDupTblMap(checkmap);

		//삭제일때 미존재 컬럼매핑정의서 체크(CM002)
		checkmap.put("vrfDtlCd", "CM002");
		waqmapper.checkNotExistColMap(checkmap);

		//요청중인 테이블매핑정의서 (CM003)
		checkmap.put("vrfDtlCd", "CM003");
		waqmapper.checkRequestTblMap(checkmap);

		//응용담당자외 등록,수정,삭제불가(CM004)
		checkmap.put("vrfDtlCd", "CM004");
//		waqmapper.checkAppCrgp(checkmap);
		
		//미존재 타겟테이블(CM005)
		checkmap.put("vrfDtlCd", "CM005");
		waqmapper.checkNotExistTgtTbl(checkmap);
		
		//미존재 타겟컬럼(CM006)
		checkmap.put("vrfDtlCd", "CM006");
		waqmapper.checkNotExistTgtCol(checkmap);
		
		//누락된 타겟컬럼 존재(CM007)
		checkmap.put("vrfDtlCd", "CM007");
		waqmapper.checkExistOmittedTgtCol(checkmap);
		
		//미존재 매핑정의서ID(CM008)
		checkmap.put("vrfDtlCd", "CM008");
		waqmapper.checkNotExistMapDfId(checkmap);
		
		//미존재 타겟매핑테이블(CM009)
		checkmap.put("vrfDtlCd", "CM009");
		waqmapper.checkNotExistTgtMapTbl(checkmap);
				
		//미존재 소스매핑테이블 (CM010)
		checkmap.put("vrfDtlCd", "CM010");
		waqmapper.checkNotExistAsisMapTbl(checkmap);
		
		//소스정보 누락(CM011)
//		checkmap.put("vrfDtlCd", "CM011");
//		waqmapper.checkExistOmittedSrcInfo(checkmap);
		
		//디폴트값 누락(CM012)
		//컬럼매핑유형이 신규이고, NotNull여부가 'Y'인경우 디폴트값 필수
		checkmap.put("vrfDtlCd", "CM012");
//		waqmapper.checkOmittedDefltVal(checkmap);
	
		//미존재 소스테이블(CM013)
		checkmap.put("vrfDtlCd", "CM013");
		waqmapper.checkNotExistSrcTbl(checkmap);
		
		//미존재 소스컬럼(CM014)
		checkmap.put("vrfDtlCd", "CM014");
		waqmapper.checkNotExistSrcCol(checkmap);
		
		//미존재 매핑조건(CM015)
		checkmap.put("vrfDtlCd", "CM015");
		waqmapper.checkNotExistMapJoin(checkmap);
		

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우(CM000)
		checkmap.put("vrfDtlCd", "CM000");
		waqmapper.checkNotChgData(checkmap);

		//테이블매핑정의서 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}
//
	
	/** 컬럼매핑정의서 요청서 승인 처리 유성열 */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqColMap savevo : (ArrayList<WaqColMap>)reglist) {
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
			logger.debug("컬럼매핑정의서 waq to wam and wah");

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

		List<WaqColMap> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqColMap savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setTblMapId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//result += waqmapper.updateWaqId(rqstno); //SUBJ_ID update이므로 필요없음.

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** 컬럼매핑정의서 요청서 상세정보 조회 유성열 */
	public WaqColMap getColMapRqstDetail(WaqColMap searchVo) {
		return waqmapper.selectColMapDetail(searchVo);
	}

	/** 컬럼매핑정의서 요청서 정보 조회 유성열 */
	public List<WaqColMap> getColMapRqstList(WaqMstr search) {
		return waqmapper.selectColMapListbyMst(search);
	}
	
	/** 컬럼매핑정의서 타겟컬럼 정보 조회 유성열 */
	public List<WaqColMap> getTgtColList(WaqColMap search) {
		return waqmapper.selectTgtColListbyNm(search);
	}
	
	/** 컬럼매핑정의서 변경대상리스트 정보 조회 유성열 */
	public List<WamTblMap> getColMapList(WamTblMap search) {
		return wamtblmapper.selectTblMapListForColMap(search);
	}

	/** 컬럼매핑정의서 요청서 삭제 유성열
	 * @throws Exception */
	public int delColMapRqst(WaqMstr reqmst, ArrayList<WaqColMap> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqColMap savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 유성열
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqColMap> list) throws Exception {
		int result = 0;

		List<WaqColMap> wamlist = waqmapper.selectwamlist(reqmst, list);
		
		logger.debug("regWam2Waq wamlist : {}", wamlist);

		result = register(reqmst, wamlist);

		return result;
	}


}
