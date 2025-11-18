/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 3:10:37
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service.impl;

import java.io.IOException;
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
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlCol;
import kr.wise.meta.ddl.service.WaqDdlColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlRel;
import kr.wise.meta.ddl.service.WaqDdlRelMapper;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTblMapper;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 3:10:37
 * </PRE>
 */
@Service("ddlTblRqstService")
public class DdlTblRqstServiceImpl implements DdlTblRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDdlTblMapper waqmapper;

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
    private DdlIdxRqstService ddlIdxRqstService; 

    @Inject
	private WaqDdlColMapper waqcolmapper;
    
    @Inject
   	private WaqDdlIdxMapper waqidxmapper;
    
    @Inject
   	private WaqDdlIdxColMapper waqidxcolmapper;    
    
    @Inject
    private WaqDdlRelMapper waqDdlRelMapper;

    @Inject
    private DdlScriptMapper ddlmapper;

	/** DDL 테이블 요청서 저장... insomnia */
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
			for (WaqDdlTbl saveVo : (ArrayList<WaqDdlTbl>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlTblRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** DDL 테이블 단건 저장 insomnia */ 
	private int saveDdlTblRqst(WaqDdlTbl saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		logger.debug("#tmpstatus: {}", tmpstatus);

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 WAM_PDM_TBL에 해당하는 컬럼 추가...
			waqcolmapper.insertByRqstSno(saveVo);
			
			//인덱스 등록
			waqidxmapper.insertByRqstSno(saveVo);
			
			//인덱스컬럼 등록
			waqidxcolmapper.insertByRqstSno(saveVo);
			
			//초기 등록시 WAM_PDM_TBL에 해당하는 관계 추가..
			//waqDdlRelMapper.insertByRqstSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			//테이블 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다....(요청구분 변경, 테이블명 변경에 따라...)
			waqcolmapper.deleteByrqstSno(saveVo);  			
			waqidxmapper.deleteByRqstSno(saveVo); 			
			waqidxcolmapper.deleteByRqstSno(saveVo); 
			
			//컬럼등록
			waqcolmapper.insertByRqstSno(saveVo);
			
			//인덱스 등록
			waqidxmapper.insertByRqstSno(saveVo);
			
			//인덱스컬럼 등록
			waqidxcolmapper.insertByRqstSno(saveVo);
			
			
//			waqcolmapper.updateTblNmbyRqstsno(saveVo);
			
			//테이블 정보가 변경된 경우 해당 관계를 삭제 후 재적재한다...
			//waqDdlRelMapper.deleteByRqstSno(saveVo);
			//waqDdlRelMapper.insertByRqstSno(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo); 
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo);
			
			//인데스삭제
			waqidxmapper.deleteByRqstSno(saveVo); 
			
			//인덱스컬럼 삭제 
			waqidxcolmapper.deleteByRqstSno(saveVo); 
			
			//관계 요청 리스트 삭제.....
			waqDdlRelMapper.deleteByRqstSno(saveVo);
		}

		return result;
	}

	/** DDL 테이블 요청서 체크... insomnia
	 * @throws Exception */
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		logger.debug("bizdtlcd:{}", bizdtlcd);
		
		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//				waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("DDLCOL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		waqRqstVrfDtls.setBizDtlCd("DDLIDX");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		waqRqstVrfDtls.setBizDtlCd("DDLIDXCOL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//waqRqstVrfDtls.setBizDtlCd("DDLREL");
		//waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//===========절대순서바꾸면 안됨===================
		//DBMS 스케마 업데이트
		waqmapper.updateDbmsId(rqstNo);
		
		//스키마ID로 명 업데이트
		waqmapper.updateDbmsPnm(rqstNo);
		
		//DDL에 있고 PDM에 없는 컬럼 삭제 대상으로 추가한다.
		waqcolmapper.insertDelInit(rqstNo);
		
		//인데스 테이블ID 업데이트 
		waqidxmapper.updateKeyId(rqstNo); 
		
		//DDL에 있고 PDM에 없는 관계 삭제 대상으로 추가한다.
		// waqDdlRelMapper.insertDelInit(rqstNo);
		//==================================================
		
				
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		waqcolmapper.updateCheckInit(rqstNo); 
		
		if(bizdtlcd.equals("DDLTBL")){		
			waqidxmapper.updateCheckInit(rqstNo);
			
			waqidxcolmapper.updateCheckInit(rqstNo);
			
			//인덱스컬럼테이블 컬럼ID 업데이트...
			waqidxcolmapper.updateColId(rqstNo);
			
			//DDL에 있고 WAQ_DDL_IDX 메 없는 인덱스 삭제 대상으로 추가한다. (이관로직시 필요함) 
	        waqidxmapper.insertDelInit(rqstNo); 
			
			//DDL에 있고 없는 인덱스컬럼 삭제 대상으로 추가한다.
			waqidxcolmapper.insertDelInit(rqstNo);
			
			//컬럼에 없는 인덱스컬럼 인덱스 삭제
			waqidxmapper.updateDelColIdxDel(rqstNo);
			
			//인덱스 삭제 인덱스컬럼 삭제 
			waqidxcolmapper.updateDelColIdxColDel(rqstNo);   
		}
		
		//각종 PDM_TBL_ID 업데이트는 등록시 처리함...
//		waqmapper.updatePdmTblId(rqstNo);
		
		//주제영역별 스키마 매핑 업데이트한다. 스키마 및 테이블 스페이스 정보를 업데이트 후 초기화 작업을 한다.
		//대상은 DBMS명이 없는 경우에 한해서...
		//waqmapper.updateDbschema(rqstNo);
		
		//테이블 스페이스 업데이트...
		waqmapper.updateTblSpaceId(rqstNo);
		
		//인덱스테이블 스페이스 업데이트...
        waqmapper.updateIdxTblSpaceId(rqstNo); 

		
		//======================================
		//테이블 커멘트 업데이트 여부
		waqmapper.updateTblCmmtChgYn(rqstNo);
		
		//DATA TYPE 업데이트 여부
		waqcolmapper.updateInitChgYn(rqstNo);

		//DATA TYPE 업데이트 여부
		waqcolmapper.updateDataTypeYn(rqstNo);
		//NOT NULL 업데이트 여부
		waqcolmapper.updateNonNullYn(rqstNo);
		//Default 업데이트 여부
		waqcolmapper.updateDefaultYn(rqstNo);
		//컬럼 업데이트 여부
		waqcolmapper.updateColUpdateYn(rqstNo);
		//=======================================
		
		// 테이블 암호화여부 업데이트
		waqmapper.updateEncYn(rqstNo);
		
		
		//=====테이블 변경 유형 업데이트 (RNT-RENAME, ALT-ALTER, DRP-DROP)=========
		//테이블 변경유형 초기화
		waqmapper.updateInitTblChgTypCd(rqstNo);
		
		//1.컬럼 포지션이 변경된 경우...
		//waqmapper.updateDropTableCd(rqstNo); 				
		waqmapper.updateDropTableCd2(rqstNo);
		
		waqmapper.updateDropTableCd3(rqstNo);
		
		//컬럼 데이터타입 길이 줄어들면 DROP&CREATE로 변경
//		waqmapper.updateDropTableCd4(rqstNo);

		waqmapper.updateAlterTableCd(rqstNo);
		//=================================================================

		// TODO :인덱스 ddl문 자동생성기능 사용여부에 따른 등록요청


		//검증 시작
		//DDL 테이블 검증
		//DDL 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DT001)
		checkmap.put("vrfDtlCd", "DT001");
		waqmapper.checkDupTbl(checkmap);

		//삭제일때 미존재 테이블 체크(DT002)
		checkmap.put("vrfDtlCd", "DT002");
		waqmapper.checkNotExistTbl(checkmap);

		//DB스키마 ID 미존재 체크(DT003)
		checkmap.put("vrfDtlCd", "DT003");
		waqmapper.checkNonDbmsID(checkmap);

		//요청중인 테이블 (DT004)
		checkmap.put("vrfDtlCd", "DT004");
		waqmapper.checkRequestTbl(checkmap);

		if(bizdtlcd.equals("DDLTBL")){
		
			//PDM TBL ID 미존재 (DT005)
			checkmap.put("vrfDtlCd", "DT005");
			waqmapper.checkNonPdmTbl(checkmap);
		}
		
		//DDL 컬럼 검증
		//DDL 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_DDL_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "DDLCOL");

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DC000)
		checkmap2.put("vrfDtlCd", "DC000");
		waqcolmapper.checkNotChgData(checkmap2);
		
		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
		
		if(!bizdtlcd.equals("DDLTBL")){		
			//인덱스 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
			Map<String, Object> chkidxmap = new HashMap<String, Object>();
			chkidxmap.put("tblnm", "WAQ_DDL_IDX");
			chkidxmap.put("rqstNo", rqstNo);
			chkidxmap.put("bizDtlCd", "DDLIDX");
			
			//인덱스 컬럼 검증
			//인덱스 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
			Map<String, Object> checkidxcolmap = new HashMap<String, Object>();
			checkidxcolmap.put("tblnm", "WAQ_DDL_IDX_COL");
			checkidxcolmap.put("rqstNo", rqstNo);
			checkidxcolmap.put("bizDtlCd", "DDLIDXCOL");
			
			//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDC00)
			checkidxcolmap.put("vrfDtlCd", "DDC00");
			waqidxcolmapper.checkNotChgData(checkidxcolmap);
	
			//컬럼 등록가능 여부 업데이트...
			result += waqRqstVrfDtlsMapper.updateVrfCdDtlsNo(checkidxcolmap);
			
							
			//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDX00)
			chkidxmap.put("vrfDtlCd", "DDX00");
			waqidxmapper.checkNotChgData(chkidxmap);
	
			//인덱스컬럼에 오류가 있는 경우 체크(DDX99)-단 변경사항 없음은 제외한다.
			chkidxmap.put("vrfDtlCd", "DDX99");
			waqidxmapper.checkColErr(chkidxmap); 
	
			//인덱스 등록가능여부(검증코드) 업데이트
			result += waqRqstVrfDtlsMapper.updateVrfCdsNo(chkidxmap);
		}
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)
		checkmap.put("vrfDtlCd", "DT000");
		waqmapper.checkNotChgData(checkmap);

		//컬럼과 관계에 오류가 있는 경우 체크(DT999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DT999");
		waqmapper.checkColErr(checkmap);

		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		
		//===========절대 순서 변경 하지 말것=======
		//PK변경된 경우 (DROP & CREATE)
		waqmapper.updateDropTablePkChg(rqstNo); 
		///=========================================		
		
		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		//DDL 스크립트 업데이트...
		updateDdlScriptWaq(mstVo);

		return result;
	}

	/** @param mstVo insomnia
	 * @throws IOException */
	private void updateDdlScriptWaq(WaqMstr mstVo) throws Exception {

		List<WaqDdlTbl> waqlist = ddlScriptService.updateDdlScirptWaq(mstVo);

		for (WaqDdlTbl savevo : waqlist) {
			waqmapper.updateDdlScriptWaq(savevo);
		}

	}

	/** DDL 테이블 요청서 등록요청... insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** DDL 테이블 요청서 승인... insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDdlTbl savevo : (ArrayList<WaqDdlTbl>)reglist) {
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
			result += regWaq2WamCol(mstVo);
			
			//인덱스 승인여부 업데이트
			result += waqmapper.updatervwStsCdIdx(mstVo);
			
			//인덱스 저장 
			//result += ddlIdxRqstService.regWaq2Wam(mstVo);
			
			//result += ddlIdxRqstService.regWaq2WamCol(mstVo);
			
			
			//result += regWaq2WamRel(mstVo);



			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @param mstVo
	/** @return meta 
	 * @throws Exception */
	private int regWaq2WamRel(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlRel> waqclist = waqDdlRelMapper.selectWaqC(rqstno);

		for (WaqDdlRel savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlRelId(id);

			waqDdlRelMapper.updateidByKey(savevo);
		}

		result += waqDdlRelMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqDdlRelMapper.updateWaqId(rqstno);

		result += waqDdlRelMapper.deleteWAM(rqstno);

		result += waqDdlRelMapper.insertWAM(rqstno);

		result += waqDdlRelMapper.updateWAH(rqstno);

		result += waqDdlRelMapper.insertWAH(rqstno);


		return result ;
	}

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlCol> waqclist = waqcolmapper.selectWaqC(rqstno);

		for (WaqDdlCol savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlColId(id);

			waqcolmapper.updateidByKey(savevo);
		}

		result += waqcolmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqcolmapper.updateWaqId(rqstno);

		result += waqcolmapper.deleteWAM(rqstno);

		result += waqcolmapper.insertWAM(rqstno);

		result += waqcolmapper.updateWAH(rqstno);

		result += waqcolmapper.insertWAH(rqstno);


		return result ;
	}

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlTbl> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlTbl savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlTblId(id);
						

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** DDL 테이블 요청서 조회... insomnia */
	public List<WaqDdlTbl> getDdlTblRqstList(WaqMstr search) {
//			Column testcol =  ddlmapper.testboolean();
//			logger.debug("testcol:{}", testcol.toVerboseString());
		return waqmapper.selectDdlListbyMst(search) ;
	}

	/** insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0 ;
        		
		List<WaqDdlTbl> wamlist = null;
		
		String rqstDcd = UtilString.null2Blank(reqmst.getRqstDcd()); 
		
		if(rqstDcd.equals("CU")) {
			//신규/변경
			wamlist = waqmapper.selectpdmtbllist(reqmst, list);
		}else{
			//삭제
			wamlist = waqmapper.selectdelddltbllist(reqmst, list); 
		}

		result += register(reqmst, wamlist);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delDdlTblRqst(WaqMstr reqmst, ArrayList<WaqDdlTbl> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDdlTbl savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** insomnia */
	public WaqDdlTbl getDdlTblRqstDetail(WaqDdlTbl searchVo) {
		return waqmapper.selectDdlTblDetail(searchVo);
	}

	/** insomnia */
	public List<WaqDdlCol> getDdlColRqstList(WaqMstr search) {
		return waqcolmapper.selectDdlColRqstList(search);
	}

	/** meta */
	@Override
	public List<WaqDdlRel> getDdlRelRqstList(WaqMstr search) {
		return waqDdlRelMapper.selectDdlRelRqstList(search);
	}

	/** meta */
	@Override
	public int delDdlRelRqst(WaqMstr reqmst, ArrayList<WaqDdlRel> list) {
		int result = 0;

		for (WaqDdlRel delvo : list) {
//			if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//			} else {
//				delvo.setIbsStatus("U");
//				delvo.setRqstDcd("DD");
//			}
		}

		result += regDdlRelList(reqmst, list);


		return result;
	}

	/** meta */
	public int regDdlRelList(WaqMstr reqmst, List<WaqDdlRel> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqDdlRel savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveDdlRelRqst(savevo);
		}

		return result;
	}

	/** @param savevo
	/** @return meta */
	private int saveDdlRelRqst(WaqDdlRel savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();


		
		if("I".equals(tmpStatus)) {
			result = waqDdlRelMapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqDdlRelMapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqDdlRelMapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	@Override
	public int regTsfWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlTbl> list) throws Exception {
		int result = 0 ;

		List<WaqDdlTbl> wamlist = waqmapper.selectTsfDdlTblList(reqmst, list);

		result += registerTsf(reqmst, wamlist); 

		return result;
	}

	
	@Override
	public List<WaqDdlTbl> getDdlTsfTblRqstList(WaqMstr search) {  
		// TODO Auto-generated method stub
		//return waqmapper.selectDdlListbyMst(search) ;
		
		return waqmapper.selectDdlTsfTblRqstList(search) ; 
	}

	
	/** DDL 테이블 요청서 저장... insomnia */
	@Override
	public int registerTsf(WaqMstr mstVo, List<?> wamlist) throws Exception { 
		
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(wamlist != null) {
			for (WaqDdlTbl saveVo : (ArrayList<WaqDdlTbl>)wamlist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlTsfTblRqst(saveVo);
				
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}
	
	
	
	/** DDL 이관 테이블 단건 저장 insomnia */
	private int saveDdlTsfTblRqst(WaqDdlTbl saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 소스 해당하는 컬럼 추가...
			waqcolmapper.insertByRqstSnoTsf(saveVo);
						
			//초기 등록시 소스 해당하는 인덱스 추가...
			//waqidxmapper.insertByRqstSnoTsf(saveVo); 
			
			//초기 등록시 소스 해당하는 인덱스컬럼 추가...
			//waqidxcolmapper.insertByRqstSnoTsf(saveVo);   
			
		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			//테이블 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다....(요청구분 변경, 테이블명 변경에 따라...)
			waqcolmapper.deleteByrqstSno(saveVo);
			//waqidxmapper.deleteByRqstSno(saveVo); 			
			//waqidxcolmapper.deleteByRqstSno(saveVo); 
						
			waqcolmapper.insertByRqstSnoTsf(saveVo); 
			
			//초기 등록시 소스 해당하는 인덱스 추가...
			//waqidxmapper.insertByRqstSnoTsf(saveVo); 
			
			//초기 등록시 소스 해당하는 인덱스컬럼 추가...
			//waqidxcolmapper.insertByRqstSnoTsf(saveVo);   
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo); 	
			
			//인덱스 삭제 
			//waqidxmapper.deleteByRqstSno(saveVo);
			//인덱스 컬럼 삭제
			//waqidxcolmapper.deleteByRqstSno(saveVo); 
						
		}

		return result;
	}

	@Override
	public List<WaqDdlTbl> getDdlTblVrfCdList(WaqMstr reqmst) {
		// TODO Auto-generated method stub
		return waqmapper.selectDdlTblVrfCdList(reqmst); 
	}

 	/** WAQ에 있는 반려된 건을 재 등록한다.
	* @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
	
	int result = 0;
	
	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
	String userid = user.getUniqId();
	reqmst.setRqstUserId(userid);
	reqmst.getBizInfo().setBizDtlCd("DDLTBL");
	
	//waq의 반려내용을 waq로 다시 입력
	result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
	result = waqcolmapper.insertWaqColRejected(reqmst, oldRqstNo);
	
	//마스터 등록
	register(reqmst, null);
	//검증
	check(reqmst);
	
	return result;
	
	}
}
