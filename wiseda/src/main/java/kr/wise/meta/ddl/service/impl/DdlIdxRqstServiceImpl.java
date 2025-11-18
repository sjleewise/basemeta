/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlIdxRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 8. 6.  16:17:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열   : 2014. 8. 6. :            : 신규 개발.
 *                    김연호   : 2014. 8.27. : 			  : 추가
 */
package kr.wise.meta.ddl.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdxCol;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlTbl;

import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmTbl; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlIdxRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 8. 6.  16:17:00
 * </PRE>
 */
@Service("DdlIdxRqstService")
public class DdlIdxRqstServiceImpl implements DdlIdxRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDdlIdxMapper waqmapper;

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
	private WaqDdlIdxColMapper waqcolmapper;

    @Inject
    private DdlScriptMapper ddlmapper;


	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** DDL 인덱스 요청서 조회... 유성열 */
	public List<WaqDdlIdx> getDdlIdxRqstList(WaqMstr search) {
		return waqmapper.selectDdlIdxListbyMst(search) ;
	}
	
	/** DDL 인덱스 요청서 상세 조회... 유성열 */
	public WaqDdlIdx getDdlIdxRqstDetail(WaqDdlIdx searchVo) {
		return waqmapper.selectDdlIdxDetail(searchVo);
	}
	

	/** DDL 인덱스 컬럼 요청서 조회... meta */
	public List<WaqDdlIdxCol> getDdlIdxColRqstList(WaqMstr search) {
		return waqcolmapper.selectDdlIdxColRqstList(search); 
	}
	
	/** DDL 인덱스 컬럼 상세정보 조회... 유성열 */
	public WaqDdlIdxCol getDdlIdxColRqstDetail(WaqDdlIdxCol search) {

		return waqcolmapper.selectDdlIdxColDetail(search);
	}


	/** DDL 인덱스 요청서 저장... meta */
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
			for (WaqDdlIdx saveVo : (ArrayList<WaqDdlIdx>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlIdxRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** DDL 인덱스 단건 저장 insomnia */
	private int saveDdlIdxRqst(WaqDdlIdx saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		// 엑셀로 업로드하였는지 확인
		String[] arrDdlIdxColAsm = UtilString.null2Blank(saveVo.getDdlIdxColAsm()).split("\\;");
		String[] arrTemp = UtilString.null2Blank(arrDdlIdxColAsm[0]).split("\\,");
		String saveDdlIdxColAsm = saveVo.getDdlIdxColAsm();//초기화(엑셀업로드가 아닌 경우 그대로 사용)
		
		// 인덱스컬럼순서 분리
		if(arrTemp.length == 3){			
			String tmpDdlIdxColAsm = "";
			for (int i = 0; i < arrDdlIdxColAsm.length; i++) {					
				String[] colArr = UtilString.null2Blank(arrDdlIdxColAsm[i]).split("\\,");
				if(!tmpDdlIdxColAsm.equals("")){
					tmpDdlIdxColAsm += ";";
				}
				tmpDdlIdxColAsm += colArr[0]+","+colArr[1];
			}
			saveVo.setDdlIdxColAsm(tmpDdlIdxColAsm);
		}
		
		if ("I".equals(tmpstatus)) {
						
			WaqDdlIdx tmpidxvo = waqmapper.selectExistsRqstSno(saveVo);   
			
			if(tmpidxvo == null){
				
				 tmpidxvo = waqmapper.selectMaxRqstSno(saveVo);   
			}
			
			logger.debug("\n rqstSno:" + tmpidxvo.getRqstSno());
			
			saveVo.setRqstSno(tmpidxvo.getRqstSno());
			
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			saveVo.setDdlIdxColAsm(saveDdlIdxColAsm);
			
			//인덱스 컬럼삭제
			waqcolmapper.deleteByRqstDtlSno(saveVo);
			
			//인덱스 컬럼 INSERT
			result += saveDdlIdxColAsm(saveVo);
			
			//초기 등록시 WAM_PDM_TBL에 해당하는 컬럼 추가...
			//waqcolmapper.insertByRqstSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			saveVo.setDdlIdxColAsm(saveDdlIdxColAsm);
			
			//인덱스 컬럼삭제
			waqcolmapper.deleteByRqstDtlSno(saveVo);
			
			//인덱스 컬럼 INSERT
			result += saveDdlIdxColAsm(saveVo);
			
//			waqcolmapper.updateTblNmbyRqstsno(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}
	
	private int saveDdlIdxColAsm(WaqDdlIdx saveVo){
		
		int result = 0;
		
		String[] arrDdlIdxColAsm = UtilString.null2Blank(saveVo.getDdlIdxColAsm()).split("\\;");
		
		for(int i = 0; i < arrDdlIdxColAsm.length;i++){
			
			String[] arrTemp = UtilString.null2Blank(arrDdlIdxColAsm[i]).split("\\,");
			
			String ddlIdxColPnm  = arrTemp[0];
			String sortTyp       = arrTemp[1];
			
			WaqDdlIdxCol colVo = new WaqDdlIdxCol(); 
			
			colVo.setRqstNo(saveVo.getRqstNo());
			colVo.setRqstSno(saveVo.getRqstSno());
			colVo.setRqstDtlSno(saveVo.getRqstDtlSno());
			colVo.setRqstDcd("CU");			
			colVo.setDdlIdxPnm(saveVo.getDdlIdxPnm());
			colVo.setRqstUserId(saveVo.getRqstUserId());
			
			colVo.setDdlIdxColPnm(ddlIdxColPnm);
			colVo.setDdlIdxColLnm(ddlIdxColPnm);
			colVo.setSortTyp(sortTyp);
			
			if(arrTemp.length == 2){
				colVo.setDdlIdxColOrd(i + 1);
			} else{//인덱스컬럼순서를 그대로 사용
				colVo.setDdlIdxColOrd(Integer.parseInt(arrTemp[2]));
			}
			colVo.setObjDescn(saveVo.getObjDescn());
			
			result += waqcolmapper.insertSelective(colVo);
		}
		
		return result;
	}

	/** DDL 인덱스 요청서 체크... insomnia
	 * @throws Exception */
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
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("DDLIDXCOL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//순선바꾸면 안됨(엑셀업로드)
		// 접속대상ID, 스키마ID, DDL테이블ID, 인덱스스페이스ID 업데이트
        waqmapper.updateKeyId(rqstNo);                        
		//===============================
        
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
				
		//인덱스테이블 스페이스 업데이트 
		waqmapper.updateIdxSpacPnm(rqstNo); 
						
		//등록유형코드(C/U/D), 검증코드 업데이트(인덱스컬럼)
		waqcolmapper.updateCheckInit(rqstNo);
		
		//인덱스 삭제시 컬럼 삭제 대상으로 업데이트한다.
		waqcolmapper.updateDelColIdxColDel(rqstNo); 
		
		//DDL에 있고 없는 컬럼 삭제 대상으로 추가한다.
		waqcolmapper.insertDelInit(rqstNo);
		
		//인덱스컬럼 논리명 업데이트
		waqcolmapper.updateDdlIdxColLnm(rqstNo);
		
		//인덱스컬럼테이블 인덱스물리명 업데이트(ID는 등록요청할때 업데이트)
		waqcolmapper.updateIdxPnm(rqstNo);

		//인덱스컬럼테이블 컬럼ID 업데이트...
		waqcolmapper.updateColId(rqstNo);
		
		
		//인덱스컬럼순서 업데이트...
		//컬럼순서대로 정렬한 다음 ROWNUM으로 업데이트...
		//오라클 전용으로 짜여진 쿼리니, 필요시 rqstSno만큼 For문 돌려서 처리해야할듯..
		waqcolmapper.updateColOrd(rqstNo);
		
		// Unique인덱스여부가 빈값일 경우 N으로 업데이트
		waqmapper.updateUkIdxYn(rqstNo);

		//검증 시작
		//DDL 테이블 검증
		//DDL 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DDX01)
		checkmap.put("vrfDtlCd", "DDX01");
		waqmapper.checkDupIdx(checkmap);

		//삭제일때 미존재 인덱스 체크(DDX02)
		checkmap.put("vrfDtlCd", "DDX02");
		waqmapper.checkNotExistIdx(checkmap);

		//요청중인 인덱스 (DDX03)
		checkmap.put("vrfDtlCd", "DDX03");
		waqmapper.checkRequestIdx(checkmap);
		
		//요청중인 DDL테이블 (DDX13)
		checkmap.put("vrfDtlCd", "DDX13");
		waqmapper.checkRequestDdlTbl(checkmap); 

		//DB접속정보 미존재 (DDX04)
		checkmap.put("vrfDtlCd", "DDX04");
		waqmapper.checkNonDbConnTrg(checkmap);

		//DB스키마정보 미존재 (DDX05)
		checkmap.put("vrfDtlCd", "DDX05");
		waqmapper.checkNonDbSch(checkmap);
		
		//DDL테이블정보 미존재 (DDX06)
		checkmap.put("vrfDtlCd", "DDX06");
		waqmapper.checkNonDdlTbl(checkmap);
		
		//인덱스스페이스정보 미존재 (DDX07)
		//checkmap.put("vrfDtlCd", "DDX07");
		//waqmapper.checkNonIdxSpac(checkmap);
		
		//인덱스컬럼 존재유무 (DDX08)
		checkmap.put("vrfDtlCd", "DDX08");
		waqmapper.checkNonExistCol(checkmap);
		
		//인덱스 명명규칙 위반(DDX09)
		checkmap.put("vrfDtlCd", "DDX09");
		waqmapper.checkIdxPnmConst(checkmap);  
		
		//인덱스 컬럼 검증
		//인덱스 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_DDL_IDX_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "DDLIDXCOL");

		//요청서내 중복(DDC01)
		checkmap2.put("vrfDtlCd", "DDC01");
		waqcolmapper.checkDupCol(checkmap2);

		//삭제일때 미존재 컬럼 체크(DDC02)
		checkmap2.put("vrfDtlCd", "DDC02");
		waqcolmapper.checkNotExistCol(checkmap2);

		//요청중인 컬럼 (DDC03) 요청중인 테이블에 의해 결정됨...
		checkmap2.put("vrfDtlCd", "DDC03");
		waqcolmapper.checkRequestCol(checkmap2);

		//컬럼명 기준으로 중복 체크 (DDC04)
		checkmap2.put("vrfDtlCd", "DDC04");
		waqcolmapper.checkDupColPnm(checkmap2);

		//동일한 컬럼순서가 있을경우 오류 (DDC05)
		checkmap2.put("vrfDtlCd", "DDC05");
		waqcolmapper.checkColOrdDup(checkmap2);
		
		//컬럼순서 공백 여부 (DDC06)
		checkmap2.put("vrfDtlCd", "DDC06");
		waqcolmapper.checkColOrdNonul(checkmap2);
		
		//존재하지 않는 DDL컬럼 여부 (DDC07)
		checkmap2.put("vrfDtlCd", "DDC07");
		waqcolmapper.checkNotExistDdlCol(checkmap2);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDC00)
		checkmap2.put("vrfDtlCd", "DDC00");
		waqcolmapper.checkNotChgData(checkmap2);

		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdDtlsNo(checkmap2);
		//result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDX00)
		checkmap.put("vrfDtlCd", "DDX00");
		waqmapper.checkInsNotChgData(checkmap);

		//컬럼에 오류가 있는 경우 체크(DDX99)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DDX99");
		waqmapper.checkColErr(checkmap); 

		//인덱스 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap);
		
		//result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);
		
		//요청서명 업데이트		
		requestMstService.updateRequestMsterNm(mstVo);  

//		//DDL 스크립트 업데이트...
		updateDdlIdxScriptWaq(mstVo);

		return result;
	}

	/** @param mstVo insomnia
	 * @throws IOException */
	private void updateDdlIdxScriptWaq(WaqMstr mstVo) throws Exception {

		List<WaqDdlIdx> waqlist = ddlScriptService.updateDdlIdxScirptWaq(mstVo);

		for (WaqDdlIdx savevo : waqlist) {
			waqmapper.updateDdlIdxScriptWaq(savevo);
		}

	}

//	/** DDL 테이블 요청서 등록요청... insomnia */
//	public int submit(WaqMstr mstVo) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
	/** DDL 인덱스 요청서 승인... insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDdlIdx savevo : (ArrayList<WaqDdlIdx>)reglist) {
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
			logger.debug("DDL인덱스 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamCol(mstVo);



			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}
//
	/** @param mstVo
	/** @return meta
	 * @throws Exception */
	public int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlIdxCol> waqclist = waqcolmapper.selectWaqC(rqstno);

		for (WaqDdlIdxCol savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlIdxColId(id);

			waqcolmapper.updateidByKey(savevo);
		}

		result += waqcolmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqcolmapper.updateWaqId(rqstno);
		
		//DDL_COL_ID 업데이트 한다
		result += waqcolmapper.updateDdlColId(rqstno);

		result += waqcolmapper.deleteWAM(rqstno);

		result += waqcolmapper.insertWAM(rqstno);

		result += waqcolmapper.updateWAH(rqstno);

		result += waqcolmapper.insertWAH(rqstno);


		return result ;
	}

	/** @return meta
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {   
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlIdx> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlIdx savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlIdxId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//DDL 이관시 DDL TBL ID 업데이트 하도록 한다.
		result += waqmapper.updateWaqDdlTblId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	
	/** 인덱스컬럼 등록 */
	/** meta */
	@Override
	public int regDdlIdxColList(WaqMstr reqmst, List<WaqDdlIdxCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqDdlIdxCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveDdlIdxColRqst(savevo);
			
		}

		return result;
	}

	/** @param savevo
	/** @return meta */
	private int saveDdlIdxColRqst(WaqDdlIdxCol savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();

		if("I".equals(tmpStatus)) {

			result = waqcolmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqcolmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqcolmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}


	/** meta
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception {
		int result = 0 ;

		List<WaqDdlIdx> wamlist = waqmapper.selectddlidxlist(reqmst, list);

		result += register(reqmst, wamlist);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delDdlIdxRqst(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDdlIdx savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** meta */
	@Override
	public int delDdlIdxColRqst(WaqMstr reqmst, ArrayList<WaqDdlIdxCol> list) {
		int result = 0;

		for (WaqDdlIdxCol delvo : list) {
//				if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//				} else {
//					delvo.setIbsStatus("U");
//					delvo.setRqstDcd("DD");
//				}
		}

		result += regDdlIdxColList(reqmst, list);


		return result;
	}

	/** meta 
	 * @throws Exception */
	@Override
	public HashMap<String, String> regDdlIdxxlsColList(WaqMstr reqmst, List<WaqDdlIdxCol> list) throws Exception {
		HashMap<String, String> rtnMap = new HashMap<String, String>();
		int result = 0;

		//요청 인덱스 리스트를 가져온다.
		//List<WaqDdlIdx> idxlist = waqmapper.selectDdlIdxListbyMst(reqmst);
		Map<String, WaqDdlIdx> idxmap = new HashMap<String, WaqDdlIdx>();

		for (WaqDdlIdxCol colvo : list) {
			String comkey = colvo.getDdlIdxPnm() + "|" + colvo.getDbConnTrgPnm() + "|" + colvo.getDbSchPnm() + "|" + "|" + colvo.getDdlTblPnm();//+ colvo.getIdxSpacPnm()
						
			if (idxmap.containsKey(comkey)) {
				WaqDdlIdx tmpidx = idxmap.get(comkey);
				String colAsm="";
				if(!tmpidx.getDdlIdxColAsm().equals(colAsm) || tmpidx.getDdlIdxColAsm() != null){
					colAsm = tmpidx.getDdlIdxColAsm()+";";
				}
				// 엑셀업로드 경우 인덱스컬럼 순서를 붙여준다
				colAsm += colvo.getDdlIdxColPnm()+","+colvo.getSortTyp()+","+colvo.getDdlIdxColOrd();		
				tmpidx.setDdlIdxColAsm(colAsm);
			} else {
				WaqDdlIdx savevo = new WaqDdlIdx();
				/*LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
				String userid = user.getUniqId();
				
				// 마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
				if ("N".equals(reqmst.getRqstStepCd())) {
					requestMstService.insertWaqMst(reqmst);
				}
				
				String rqstNo = reqmst.getRqstNo();

				
				
				// 요청번호 셋팅
				savevo.setFrsRqstUserId(userid);
				savevo.setRqstUserId(userid);
				savevo.setRqstNo(rqstNo);*/
				savevo.setIbsStatus(colvo.getIbsStatus());
				
				savevo.setRqstDcd(colvo.getRqstDcd());				
				savevo.setDdlIdxPnm(colvo.getDdlIdxPnm());
				savevo.setDbConnTrgPnm(colvo.getDbConnTrgPnm());
				savevo.setDbSchPnm(colvo.getDbSchPnm());
				savevo.setDdlTblPnm(colvo.getDdlTblPnm());
										
				savevo.setDdlIdxColAsm(colvo.getDdlIdxColPnm()+","+colvo.getSortTyp()+","+colvo.getDdlIdxColOrd());				
				savevo.setDdlIdxId(colvo.getDdlIdxId());
				savevo.setObjDescn(colvo.getObjDescn());
				
				//result += saveDdlIdxxlsColRqst(reqmst, savevo, colvo);
				idxmap.put(comkey, savevo);				
			}
		}
		
		//테이블이 비어있을 경우 ...
		if(idxmap.isEmpty()) {
			rtnMap.put("result", "-999");
			rtnMap.put("colKey", "");
			return rtnMap;
		}

		ArrayList<WaqDdlIdx> reglist = new ArrayList<WaqDdlIdx>();
		
		for(Map.Entry<String, WaqDdlIdx> entry : idxmap.entrySet()) {
			reglist.add(entry.getValue());
		}

		//result += regDdlIdxColList(reqmst, list);
		result = register(reqmst,reglist);
		rtnMap.put("result",  String.valueOf(result));
		rtnMap.put("colKey", "");

		return rtnMap;
	}

	

	@Override
	public List<WaqDdlIdx> selectDdlTsfIdxListForRqst(WaqDdlIdx search) {
		List<WaqDdlIdx> list = null;
		String rqstDcd = UtilString.null2Blank(search.getRqstDcd());
		
		if(rqstDcd.equals("DD")){
			
			list = waqmapper.selectDelDdlTsfIdxListForRqst(search); 
		}else{
			list = waqmapper.selectDdlTsfIdxListForRqst(search); 
		}
		
		return list;
	}

	@Override
	public int regTsfWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlIdx> list) throws Exception {
		int result = 0 ;

		List<WaqDdlIdx> wamlist = waqmapper.selectTsfDdlIdxList(reqmst, list);   

		result += registerTsf(reqmst, wamlist); 

		return result;
	}

	
	/** DDL인덱스이관 요청서 저장... insomnia */
	public int registerTsf(WaqMstr mstVo, List<?> reglist) throws Exception {
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
			for (WaqDdlIdx saveVo : (ArrayList<WaqDdlIdx>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlTsfIdxRqst(saveVo);
				
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** DDL 인덱스 단건 저장 */
	private int saveDdlTsfIdxRqst(WaqDdlIdx saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpstatus)) {
			//요청서 추가...						
			WaqDdlIdx tmpidxvo = waqmapper.selectExistsRqstSno(saveVo);   
			
			if(tmpidxvo == null){
				
				 tmpidxvo = waqmapper.selectMaxRqstSno(saveVo);   
			}
			
			logger.debug("\n rqstSno:" + tmpidxvo.getRqstSno());
			
			saveVo.setRqstSno(tmpidxvo.getRqstSno());
			
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 소스 해당하는 인덱스컬럼 추가...
			waqcolmapper.insertByRqstSnoTsf(saveVo); 
		} else if ("U".equals(tmpstatus)){
			//
			////요청서 내용 변경시...
			//waqmapper.deleteByPrimaryKey(saveVo.getRqstNo(), saveVo.getRqstSno());  
			//result = waqmapper.insertByRqstSnoTsf(saveVo);
			////테이블 정보가 변경된 경우 해당 컬럼을 삭제 후 재적재한다....(요청구분 변경, 테이블명 변경에 따라...)
			//
			//WaqDdlIdx idxVo = new WaqDdlIdx();
			//
			//idxVo.setRqstNo(saveVo.getRqstNo());
			//idxVo.setRqstSno(saveVo.getRqstSno());
			//
			////초기 등록시 소스 해당하는 인덱스컬럼 추가...
			//waqcolmapper.deleteByrqstSno(idxVo);
			//waqcolmapper.insertByRqstSnoTsf(saveVo); 
			
			waqmapper.deleteByRqstSno(saveVo); 			
			waqcolmapper.deleteByRqstSno(saveVo); 
			//초기 등록시 소스 해당하는 인덱스 추가...
			result = waqmapper.insertByRqstSnoTsf(saveVo); 
			
			//초기 등록시 소스 해당하는 인덱스컬럼 추가...
			waqcolmapper.insertByRqstSnoTsf(saveVo);  
			
		} else if ("D".equals(tmpstatus)) {
			////요청내용 삭제...
			//result = waqmapper.deleteByrqstSno(saveVo);
			////컬럼 요청 리스트 삭제.....
			//waqcolmapper.deleteByrqstSno(saveVo); 	

			//인덱스 삭제 
			waqmapper.deleteByRqstSno(saveVo);
			//인덱스 컬럼 삭제
			waqcolmapper.deleteByRqstSno(saveVo); 
		}

		return result;
	}

	@Override
	public List<WaqDdlIdx> getDdlTsfIdxRqstList(WaqMstr search) {
		// TODO Auto-generated method stub
		return waqmapper.selecttDdlTsfIdxRqstList(search); 
	}

 	/** WAQ에 있는 반려된 건을 재 등록한다.
	* @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
	
	int result = 0;
	
	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
	String userid = user.getUniqId();
	reqmst.setRqstUserId(userid);
	reqmst.getBizInfo().setBizDtlCd("DDLIDX");
	
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
