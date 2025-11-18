/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTsfRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddltsf.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 8. 23. 오후 6:12:25
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 8. 23. :            : 신규 개발.
 */
package kr.wise.meta.ddltsf.service.impl;

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
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.WamDdlTblMapper;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddltsf.service.DdlTsfRqstService;
import kr.wise.meta.ddltsf.service.WaaDbMapVo;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;
import kr.wise.meta.ddltsf.service.WaqDdlTsfCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfColMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxColMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfRel;
import kr.wise.meta.ddltsf.service.WaqDdlTsfRelMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTblMapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTsfRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddltsf.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 8. 23. 오후 6:12:25
 * </PRE>
 */
@Service("ddlTsfRqstService")
public class DdlTsfRqstServiceImpl implements DdlTsfRqstService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDdlTsfTblMapper waqmapper;

	@Inject
	private WaqDdlTsfIdxMapper waqDdlTsfIdxMapper;
	
	@Inject
	private WamDdlTblMapper wammapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
    private DdlScriptService ddlScriptService;

    @Inject
	private WaqDdlTsfColMapper waqcolmapper;

    @Inject
    private WaqDdlTsfRelMapper waqDdlTsfRelMapper;
    
    @Inject
    private WaqDdlTsfIdxColMapper waqDdlTsfIdxColMapper;
    
    @Inject
    private WaqDdlIdxMapper waqDdlIdxMapper;
    
    /** DDL 이관 요청서 저장... yeonho */
	@Override
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
			for (WaqDdlTsfTbl saveVo : (ArrayList<WaqDdlTsfTbl>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				if(saveVo.getObjDcd().equals("TBL")) {
					result += saveDdlTsfTblRqst(saveVo);
				} else if (saveVo.getObjDcd().equals("IDX")) {
					result += saveDdlTsfIdxRqst(saveVo);
				}
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** @param saveVo
	/** @return yeonho */
	private int saveDdlTsfIdxRqst(WaqDdlTsfTbl saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqDdlTsfIdxMapper.insertSelective(saveVo);
			//초기 등록시 WAM_DDL_IDX_COL에 해당하는 컬럼 추가...
			waqDdlTsfIdxColMapper.insertByRqstSnoTsf(saveVo);

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqDdlTsfIdxMapper.updateByPrimaryKeySelective(saveVo);
			//인덱스 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다....(요청구분 변경, 테이블명 변경에 따라...)
			waqDdlTsfIdxColMapper.deleteByrqstSnoTsf(saveVo);
			waqDdlTsfIdxColMapper.insertByRqstSnoTsf(saveVo);
//			waqcolmapper.updateTblNmbyRqstsno(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqDdlTsfIdxMapper.deleteByrqstSno(saveVo);
			//인덱스컬럼 요청 리스트 삭제.....
			waqDdlTsfIdxColMapper.deleteByrqstSnoTsf(saveVo);
		}

		return result;
	}

	/** @param saveVo
	/** @return yeonho */
	/** DDL 테이블 이관요청 단건 저장 insomnia */
	private int saveDdlTsfTblRqst(WaqDdlTsfTbl saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 WAM_DDL_COL에 해당하는 컬럼 추가...
			waqcolmapper.insertByRqstSnoTsf(saveVo);
			//초기 등록시 WAM_DDL_REL에 해당하는 관계 추가..
			waqDdlTsfRelMapper.insertByRqstSnoTsf(saveVo);
			
			//인덱스는 별도로 추가한다.
//			//초기 등록시 WAM_DDL_IDX에 해당하는 컬럼 추가...
//			waqDdlTsfIdxMapper.insertByRqstSnoTsf(saveVo);
//			//초기 등록시 WAM_DDL_IDX_COL에 해당하는 컬럼 추가...
//			waqDdlTsfIdxColMapper.insertByRqstSnoTsf(saveVo);

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			//테이블 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다....(요청구분 변경, 테이블명 변경에 따라...)
			waqcolmapper.deleteByrqstSnoTsf(saveVo);
			waqcolmapper.insertByRqstSnoTsf(saveVo);
//			waqcolmapper.updateTblNmbyRqstsno(saveVo);
			
			//테이블 정보가 변경된 경우 해당 관계를 삭제 후 재적재한다...
			waqDdlTsfRelMapper.deleteByRqstSnoTsf(saveVo);
			waqDdlTsfRelMapper.insertByRqstSnoTsf(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSnoTsf(saveVo);
			//관계 요청 리스트 삭제.....
			waqDdlTsfRelMapper.deleteByRqstSnoTsf(saveVo);
		}

		return result;
	}

	/** yeonho */
	@Override
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//				waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtls.setBizDtlCd("TSFTBL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("TSFIDX");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("TSFCOL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("TSFREL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		waqRqstVrfDtls.setBizDtlCd("TSFIDXCOL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//DBMS 스케마 업데이트
		waqmapper.updateDbSchId(rqstNo);
		waqDdlTsfIdxMapper.updateDbSchId(rqstNo);

		//테이블 스페이스 업데이트...
		waqmapper.updateTblSpaceId(rqstNo);
		waqDdlTsfIdxMapper.updateTblSpaceId(rqstNo);
		
		//DDL테이블, 물리테이블ID 업데이트...
		waqmapper.updateTblId(rqstNo);
		
		//DDLIDX, DDLTBL ID, 인덱스정보 업데이트
		waqDdlTsfIdxMapper.updateDdlIdx(rqstNo);
		
		//관계 주제영역 Update
		waqDdlTsfRelMapper.updateSubjId(rqstNo);
		
		//관계 자식, 부모정보 업데이트
		waqDdlTsfRelMapper.updateEntyAttrId(rqstNo);
		
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
		waqDdlTsfIdxMapper.updateCheckInit(rqstNo);
		waqcolmapper.updateCheckInit(rqstNo);
		waqDdlTsfIdxColMapper.updateCheckInit(rqstNo);
		waqDdlTsfRelMapper.updateCheckInit(rqstNo);

		//DDL에 있고 PDM에 없는 컬럼 삭제 대상으로 추가한다.
//		waqcolmapper.insertDelInit(rqstNo);

		//DDL에 있고 PDM에 없는 관계 삭제 대상으로 추가한다.
//		waqDdlRelMapper.insertDelInit(rqstNo);
		
		//DATA TYPE 업데이트 여부
		waqcolmapper.updateDataTypeYn(rqstNo);
		//NOT NULL 업데이트 여부
		waqcolmapper.updateNonNullYn(rqstNo);
		//Default 업데이트 여부
		waqcolmapper.updateDefaultYn(rqstNo);
		//컬럼 업데이트 여부
		waqcolmapper.updateColUpdateYn(rqstNo);

		
		//테이블 변경 유형 업데이트 (RNT-RENAME, ALT-ALTER, DRP-DROP)
		//1.컬럼 포지션이 변경된 경우...
////		waqmapper.updateDropTableCd(rqstNo);
//		waqmapper.updateDropTableCd2(rqstNo);
//		waqmapper.updateAlterTableCd(rqstNo);

		// TODO :인덱스 ddl문 자동생성기능 사용여부에 따른 등록요청


		//검증 시작
		//DDL 테이블 검증
		//DDL 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, "TSFTBL");

		//요청서내 중복자료 검증(DTT01)
		checkmap.put("vrfDtlCd", "DTT01");
		waqmapper.checkDupTbl(checkmap);

		//삭제일때 미존재 테이블 체크(DTT02)
		checkmap.put("vrfDtlCd", "DTT02");
		waqmapper.checkNotExistTbl(checkmap);

		//소스DB스키마 ID 미존재 체크(DTT03)
		checkmap.put("vrfDtlCd", "DTT03");
		waqmapper.checkNonSrcDbmsID(checkmap);

		//타겟DB스키마 ID 미존재 체크(DTT04)
		checkmap.put("vrfDtlCd", "DTT04");
		waqmapper.checkNonTgtDbmsID(checkmap);

		//DDL테이블 미존재(DTT05)
		checkmap.put("vrfDtlCd", "DTT05");
		waqmapper.checkNonExistDdlTbl(checkmap);
		
		//테이블스페이스 미존재(DTT06)
		checkmap.put("vrfDtlCd", "DTT06");
		waqmapper.checkNonExistTblSpac(checkmap);
		
		//요청중인 테이블 (DTT07)
		checkmap.put("vrfDtlCd", "DTT07");
		waqmapper.checkRequestTbl(checkmap);

		//물리모델테이블 ID 미존재 (DTT08)
		checkmap.put("vrfDtlCd", "DTT08");
		waqmapper.checkNonPdmTbl(checkmap);

		//DDL 컬럼 검증
		//DDL 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_DDL_TSF_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "DDLCOL");

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DTC00)
		checkmap2.put("vrfDtlCd", "DTC00");
		waqcolmapper.checkNotChgData(checkmap2);

		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);

		
		//DDL 인덱스 검증
		//DDL 인덱스 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap3 = new HashMap<String, Object>();
		checkmap3.put("tblnm", "WAQ_DDL_TSF_IDX");
		checkmap3.put("rqstNo", rqstNo);
		checkmap3.put("bizDtlCd", "TSFIDX");

		//요청서내 중복자료 검증(DTX01)
		checkmap3.put("vrfDtlCd", "DTX01");
		waqDdlTsfIdxMapper.checkDupIdx(checkmap3);

		//삭제일때 미존재 인덱스 체크(DTX02)
		checkmap3.put("vrfDtlCd", "DTX02");
		waqDdlTsfIdxMapper.checkNotExistIdx(checkmap3);

		//요청중인 인덱스 (DTX03)
		checkmap3.put("vrfDtlCd", "DTX03");
		waqDdlTsfIdxMapper.checkRequestIdx(checkmap3);

		//소스DB스키마 미존재 (DTX04)
		checkmap3.put("vrfDtlCd", "DTX04");
		waqDdlTsfIdxMapper.checkNonSrcDbSch(checkmap3);

		//타겟DB스키마 미존재 (DTX05)
		checkmap3.put("vrfDtlCd", "DTX05");
		waqDdlTsfIdxMapper.checkNonTgtDbSch(checkmap3);
		
		//DDL테이블정보 미존재 (DTX06)
		checkmap3.put("vrfDtlCd", "DTX06");
		waqDdlTsfIdxMapper.checkNonDdlTbl(checkmap3);
		
		//인덱스스페이스정보 미존재 (DTX07)
		checkmap3.put("vrfDtlCd", "DTX07");
		waqDdlTsfIdxMapper.checkNonIdxSpac(checkmap3);
		
		//인덱스컬럼 존재유무 (DTX08)
		checkmap3.put("vrfDtlCd", "DTX08");
		waqDdlTsfIdxMapper.checkNonExistCol(checkmap3);
		
		
		//DDL 인덱스컬럼 검증
		//DDL 인덱스컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap4 = new HashMap<String, Object>();
		checkmap4.put("tblnm", "WAQ_DDL_TSF_IDX_COL");
		checkmap4.put("rqstNo", rqstNo);
		checkmap4.put("bizDtlCd", "TSFIDXCOL");
		
		//인덱스컬럼 검증쿼리는 이곳에...
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DTXC0)
		checkmap4.put("vrfDtlCd", "DTXC0");
		waqDdlTsfIdxColMapper.checkNotChgData(checkmap4);
		
		
		//인덱스컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap4);
		
//		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DTX00)
		checkmap3.put("vrfDtlCd", "DTX00");
		waqDdlTsfIdxMapper.checkNotChgData(checkmap3);
		
		//컬럼에 오류가 있는 경우 체크(DTX99)-단 변경사항 없음은 제외한다.
		checkmap3.put("vrfDtlCd", "DTX99");
		waqDdlTsfIdxMapper.checkColErr(checkmap3);
		
		//인덱스 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap3);
		
		//DDL 관계 검증
		//DDL 관계 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap5 = new HashMap<String, Object>();
		checkmap5.put("tblnm", "WAQ_DDL_TSF_REL");
		checkmap5.put("rqstNo", rqstNo);
		checkmap5.put("bizDtlCd", "DDLREL");
		
		//관계 검증쿼리는 이곳에...

		//부모엔터티 주제영역 미존재 (DTR01)
		checkmap5.put("vrfDtlCd", "DTR01");
		waqDdlTsfRelMapper.checkNotExistPaSubj(checkmap5);
		
		//부모엔터티 미존재 (DTR02)
		checkmap5.put("vrfDtlCd", "DTR02");
		waqDdlTsfRelMapper.checkNotExistPaEnty(checkmap5);
		
		//부모엔터티 속성명 미존재 (DTR03)
		checkmap5.put("vrfDtlCd", "DTR03");
		waqDdlTsfRelMapper.checkNotExistPaAttr(checkmap5);
		
		//자식엔터티 주제영역 미존재 (DTR04)
		checkmap5.put("vrfDtlCd", "DTR04");
		waqDdlTsfRelMapper.checkNotExistChSubj(checkmap5);
		
		//자식엔터티 주제영역 미존재 (DTR05)
		checkmap5.put("vrfDtlCd", "DTR05");
		waqDdlTsfRelMapper.checkNotExistChEnty(checkmap5);
		
		//자식엔터티 속성명 미존재 (DTR06)
		checkmap5.put("vrfDtlCd", "DTR06");
		waqDdlTsfRelMapper.checkNotExistChAttr(checkmap5);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DTR00)
		checkmap5.put("vrfDtlCd", "DTR00");
		waqDdlTsfRelMapper.checkNotChgData(checkmap5);
		
		
		//관계 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap5);
		
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DTT00)
		checkmap.put("vrfDtlCd", "DTT00");
		waqmapper.checkNotChgData(checkmap);

		//컬럼과 관계에 오류가 있는 경우 체크(DTT99)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DTT99");
		waqmapper.checkColErr(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		//DDL 스크립트 업데이트...
//		updateDdlScriptWaq(mstVo);

		return result;
	}

	/** insomnia */
	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** yeonho */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDdlTsfTbl savevo : (ArrayList<WaqDdlTsfTbl>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
			result += waqDdlTsfIdxMapper.updatervwStsCd(savevo);
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
			result += regWaq2WamCol(mstVo);
			result += regWaq2WamRel(mstVo);
			result += regWaq2WamIdx(mstVo);
			result += regWaq2WamIdxCol(mstVo);
		


			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}


	/** @param mstVo
	/** @return yeonho */
	private int regWaq2WamIdxCol(WaqMstr mstVo) {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

//		List<WaqDdlIdxCol> waqclist = waqcolmapper.selectWaqC(rqstno);
//
//		for (WaqDdlIdxCol savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setDdlIdxColId(id);
//
//			waqcolmapper.updateidByKey(savevo);
//		}

		result += waqDdlTsfIdxColMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqDdlTsfIdxColMapper.updateWaqId(rqstno);

		result += waqDdlTsfIdxColMapper.deleteWAM(rqstno);

		result += waqDdlTsfIdxColMapper.insertWAM(rqstno);

		result += waqDdlTsfIdxColMapper.updateWAH(rqstno);

		result += waqDdlTsfIdxColMapper.insertWAH(rqstno);


		return result ;
	}

	/** @param mstVo
	/** @return yeonho */
	private int regWaq2WamIdx(WaqMstr mstVo) {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

//		List<WaqDdlIdx> waqclist = waqmapper.selectWaqC(rqstno);
//
//		for (WaqDdlIdx savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setDdlIdxId(id);
//
//			waqmapper.updateidByKey(savevo);
//		}

		result += waqDdlTsfIdxMapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqmapper.updateWaqId(rqstno);

		result += waqDdlTsfIdxMapper.deleteWAM(rqstno);

		result += waqDdlTsfIdxMapper.insertWAM(rqstno);

		result += waqDdlTsfIdxMapper.updateWAH(rqstno);

		result += waqDdlTsfIdxMapper.insertWAH(rqstno);

		return result;
	}

	/** @param mstVo
	/** @return yeonho 
	 * @throws Exception */
	private int regWaq2WamRel(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

//		List<WaqDdlTsfRel> waqclist = waqDdlTsfRelMapper.selectWaqC(rqstno);
//
//		for (WaqDdlTsfRel savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setDdlRelId(id);
//
//			waqDdlTsfRelMapper.updateidByKey(savevo);
//		}

		result += waqDdlTsfRelMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqDdlTsfRelMapper.updateWaqId(rqstno);

		result += waqDdlTsfRelMapper.deleteWAM(rqstno);

		result += waqDdlTsfRelMapper.insertWAM(rqstno);

		result += waqDdlTsfRelMapper.updateWAH(rqstno);

		result += waqDdlTsfRelMapper.insertWAH(rqstno);


		return result ;
	}

	/** @param mstVo
	/** @return yeonho
	 * @throws Exception */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

//		List<WaqDdlTsfCol> waqclist = waqcolmapper.selectWaqC(rqstno);
//
//		for (WaqDdlTsfCol savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setDdlColId(id);
//
//			waqcolmapper.updateidByKey(savevo);
//		}

		result += waqcolmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqcolmapper.updateWaqId(rqstno);

		result += waqcolmapper.deleteWAM(rqstno);

		result += waqcolmapper.insertWAM(rqstno);

		result += waqcolmapper.updateWAH(rqstno);

		result += waqcolmapper.insertWAH(rqstno);


		return result ;
	}

	/** @return yeonho
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

//		List<WaqDdlTsfTbl> waqclist = waqmapper.selectWaqC(rqstno);
//
//		for (WaqDdlTsfTbl savevo : waqclist) {
//			String id =  objectIdGnrService.getNextStringId();
//			savevo.setDdlTblId(id);
//
//			waqmapper.updateidByKey(savevo);
//		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** insomnia */
	@Override
	public List<WamDdlTsfObj> getDdlTsfList(WaaDbMapVo search) {
		return waqmapper.selectDdlTsfList(search);
	}

	/** yeonho */
	/** DDL 이관요청서 조회 */
	@Override
	public List<WaqDdlTsfTbl> getDdlTsfTblRqstList(WaqMstr search) {
		return waqmapper.selectDdlListByMst(search);
	}

	/** yeonho */
	@Override
	public WaqDdlTsfTbl getDdlTsfTblRqstDetail(WaqDdlTsfTbl searchVo) {
		return waqmapper.selectDdlTsfTblDetail(searchVo);
		
	}

	/** yeonho */
	@Override
	public WaqDdlIdx getDdlTsfIdxRqstDetail(WaqDdlIdx searchVo) {
		return waqDdlIdxMapper.selectDdlIdxDetail(searchVo);
	}

	/** yeonho 
	 * @throws Exception */
	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlTsfTbl> list, WaqDdlTsfTbl dbmsInfo) throws Exception {
		int result = 0 ;

		List<WaqDdlTsfTbl> wamlist = waqmapper.selectDdlTblList(reqmst, list);

		result += registerWam2Waq(reqmst, wamlist, dbmsInfo);

		//테이블과 연계된 인덱스는 별도로 추출하여 생성...
		List<WaqDdlTsfTbl> wamIdxList = waqmapper.selectDdlIdxList(reqmst, list);
		
		result += registerWam2WaqIdx(reqmst, wamIdxList, dbmsInfo);
		return result;
	}

	/** @param reqmst
	/** @param wamIdxList
	/** @param dbmsInfo
	/** @return yeonho */
	private int registerWam2WaqIdx(WaqMstr mstVo,
			List<WaqDdlTsfTbl> reglist, WaqDdlTsfTbl dbmsInfo) {
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqDdlTsfTbl saveVo : (ArrayList<WaqDdlTsfTbl>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//DBMS정보 셋팅
				saveVo.setSrcDbConnTrgPnm(dbmsInfo.getSrcDbConnTrgPnm());
				saveVo.setSrcDbSchPnm(dbmsInfo.getSrcDbSchPnm());
				saveVo.setTgtDbConnTrgPnm(dbmsInfo.getTgtDbConnTrgPnm());
				saveVo.setTgtDbSchPnm(dbmsInfo.getTgtDbSchPnm());
				
				//단건 저장...
				result += saveDdlTsfIdxRqst(saveVo);
			}
		}


		return result;
	}

	/** @param reqmst
	/** @param wamlist
	/** @param dbmsInfo
	/** @return yeonho */
	private int registerWam2Waq(WaqMstr mstVo, List<WaqDdlTsfTbl> reglist,
			WaqDdlTsfTbl dbmsInfo) {
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
			for (WaqDdlTsfTbl saveVo : (ArrayList<WaqDdlTsfTbl>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//DBMS정보 셋팅
				saveVo.setSrcDbConnTrgPnm(dbmsInfo.getSrcDbConnTrgPnm());
				saveVo.setSrcDbSchPnm(dbmsInfo.getSrcDbSchPnm());
				saveVo.setTgtDbConnTrgPnm(dbmsInfo.getTgtDbConnTrgPnm());
				saveVo.setTgtDbSchPnm(dbmsInfo.getTgtDbSchPnm());
				
				//단건 저장...
				if(saveVo.getObjDcd().equals("TBL")) {
					result += saveDdlTsfTblRqst(saveVo);
				} else if (saveVo.getObjDcd().equals("IDX")) {
					result += saveDdlTsfIdxRqst(saveVo);
				}
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfCol> getDdlTsfColRqstList(WaqMstr search) {
		return waqcolmapper.selectDdlTsfColRqstList(search);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfRel> getDdlTsfRelRqstList(WaqMstr search) {
		return waqDdlTsfRelMapper.selectDdlTsfRelRqstList(search);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfIdxCol> getDdlTsfIdxColRqstList(WaqMstr search) {
		return waqDdlTsfIdxColMapper.selectDdlTsfIdxColRqstList(search);
	}

	/** yeonho 
	 * @throws Exception */
	@Override
	public int delDdlTsfTblRqst(WaqMstr reqmst, ArrayList<WaqDdlTsfTbl> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDdlTsfTbl savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

}
