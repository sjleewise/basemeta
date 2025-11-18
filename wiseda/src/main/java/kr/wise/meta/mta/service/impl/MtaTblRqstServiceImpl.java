
/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.mta.service.impl
 * 3. Comment : 메타데이터 등록
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12. 오후 4:32:45
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service.impl;


import java.io.IOException;
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
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.esb.EsbConfig;
import kr.wise.esb.send.service.EsbFilesendService;
import kr.wise.esb.send.service.EsbFilesendVO;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.BrmInfoVo;
import kr.wise.meta.mta.service.MtaTblRqstService;
import kr.wise.meta.mta.service.WamMtaColMapper;
import kr.wise.meta.mta.service.WamMtaTblMapper;
import kr.wise.meta.mta.service.WaqMtaCol;
import kr.wise.meta.mta.service.WaqMtaColMapper;
import kr.wise.meta.mta.service.WaqMtaTbl;
import kr.wise.meta.mta.service.WaqMtaTblMapper;

@Service("mtaTblRqstService")
public class MtaTblRqstServiceImpl implements MtaTblRqstService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaqMtaTblMapper waqMtaTblMapper; 
	
	@Inject
	private WaqMtaColMapper waqMtaColMapper;
	
	@Inject 
	private WamMtaTblMapper wamMtaTblMapper;
	
	@Inject
	private WamMtaColMapper wamMtaColMapper;
	
	@Inject
	private WaaDbConnTrgMapper waaDbConnTrgMapper;	 
	
	/** 요청 마스터 */
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;
	
	@Inject
	private EgovIdGnrService mtaObjectIdGnrService;
	
	@Inject
	private EsbFilesendService esbFilesendService;

	@Override
	public WaqMtaTbl getMtaTblRqstDetail(WaqMtaTbl searchVo) {
		return waqMtaTblMapper.selectMtaTblDetail(searchVo);
	}

	/** 메타데이터 테이블 요청서 목록 조회 eychoi */
	@Override
	public List<WaqMtaTbl> getMtaTblrqstList(WaqMstr search) {

		return waqMtaTblMapper.selectMtaTblListbyMst(search);
	}

	/** 메타데이터 테이블 요청서 삭제 eychoi */
	@Override
	public int delMtaTblRqst(WaqMstr reqmstVo, ArrayList<WaqMtaTbl> list) throws Exception {

		int result = 0;
		
		for(WaqMtaTbl saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = register(reqmstVo, list); 			
		
		return result;
	}
	
	/** 메타데이터 요청서 저장 */
	public int register(WaqMstr reqmstVo, List<?> reglist) throws Exception {
		
		logger.debug("reqmstvo:{}\nbizInfo:{}", reqmstVo, reqmstVo.getBizInfo());
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String orgCd  = user.getOrgCd();
		
		String rqstNo = reqmstVo.getRqstNo();
		int result = 0;
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmstVo.getRqstStepCd())) {
			
			reqmstVo.setOrgCd(orgCd);
			
			requestMstService.insertWaqMst(reqmstVo);
		}
		
		if(reglist != null) {
			for(WaqMtaTbl savevo : (ArrayList<WaqMtaTbl>)reglist) {
				//요청번호 셋팅
				savevo.setFrsRqstUserId(userid);
				savevo.setRqstUserId(userid);
				savevo.setRqstNo(rqstNo);
				savevo.setSubjClDcd(reqmstVo.getSubjClDcd()); 
				
				savevo.setTblCreDt( UtilString.replace(savevo.getTblCreDt(), "-", ""));
				
				//단건 저장...
				result += saveMtaTblRqst(savevo);
			}
		}
		
		reqmstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmstVo);
		
		return result;
	}

	/** 메타데이터 단건 저장 */
	private int saveMtaTblRqst(WaqMtaTbl saveVo) {
		
		int result = 0;
		
		String tmpStatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpStatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = mtaObjectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqMtaTblMapper.insertSelective(saveVo);
			
			//초기 등록시 WAM에 해당하는 컬럼 존재시 추가...
			waqMtaColMapper.insertByRqstSno(saveVo);
						
		}else if ("U".equals(tmpStatus)) {
			//업데이트
			result = waqMtaTblMapper.updateByPrimaryKeySelective(saveVo);
		}else if ("D".equals(tmpStatus)) {
			result = waqMtaTblMapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqMtaColMapper.deleteByrqstSno(saveVo);
			//관계 요청 리스트 삭제.....
		}
		
		return result;
	}
	
	/** 컬럼 리스트 저장... */
	public int regMtaColList(WaqMstr reqmst, List<WaqMtaCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqMtaCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			
			if(UtilString.isBlank(savevo.getPkYn()) ) savevo.setPkYn("N");
//			if(UtilString.isBlank(savevo.getFkYn()) ) savevo.setFkYn("N");
			if(UtilString.isBlank(savevo.getNonulYn()) ) savevo.setNonulYn("N");
			

			result += saveMtaColRqst(savevo);
		}

		return result;
	}
	
	/** 컬럼 단건 저장... */
	private int saveMtaColRqst(WaqMtaCol savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waqMtaColMapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqMtaColMapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqMtaColMapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/** 메타데이터 요청서 검증 */
	@Override
	public int check(WaqMstr mstVo) {
		
		logger.info("== MtaTblRqstServiceImpl.check started...");
	 
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = "TBL";

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//		waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("COL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//메타데이터 테이블 요청 > 자동항목 수집테이블에서 업데이트'
		waqMtaTblMapper.updateTblAutoItem(rqstNo);
	
		//메타데이터 테이블 요청 > 등록유형코드(C/U/D), 검증코드 업데이트
		waqMtaTblMapper.updateCheckInit(rqstNo);
	
		//테이블이 삭제요청일 경우 해당 컬럼도 삭제요청으로 변경한다.
		waqMtaColMapper.updateRqstDcdbyTable(rqstNo);

		//메타데이터 컬럼 요청 >등록유형코드(C/U/D), 검증코드 업데이트
		waqMtaColMapper.updateCheckInit(rqstNo);
		
		
		//테이블이 변경/삭제일 경우 컬럼이 없는 경우 삭제대상으로 추가한다.
		//logger.debug("삭제대상추가 시작");
		waqMtaColMapper.insertnoWaqdelCol(rqstNo);
		//logger.debug("삭제대상추가 종료");

		//컬럼에 테이블 물리명 업데이트...
		waqMtaColMapper.updateTblPnmbyRqstSno(rqstNo);
		
		
		//컬럼순서 정비
		waqMtaColMapper.updateColOrd(rqstNo); 
				
		
		//검증 시작
		//물리모델 테이블 검증
		//물리모델 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		
		tblnm = "WAQ_MTA_TBL";
	
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(PT001)
		checkmap.put("vrfDtlCd", "PT001");
		waqMtaTblMapper.checkDupTbl(checkmap);
 
		//삭제일때 미존재 테이블 체크(PT002)
		checkmap.put("vrfDtlCd", "PT002");
		waqMtaTblMapper.checkNotExistTbl(checkmap);

		//주제영역 미존재 체크(PT003)
		//주석checkmap.put("vrfDtlCd", "PT003");
		//주석waqmapper.checkNonSubjID(checkmap);

		//요청중인 테이블 (PT004)
		checkmap.put("vrfDtlCd", "PT004");
		waqMtaTblMapper.checkRequestTbl(checkmap);

		//설명존재유무
		//checkmap.put("vrfDtlCd", "PT009");
		//waqmapper.checkObjdesc(checkmap); 
		
		//컬럼 존재여부 체크
		checkmap.put("vrfDtlCd", "PT006");
		waqMtaTblMapper.checkColCnt(checkmap);
		
		//필수항목 미입력 시 오류 
		checkmap.put("vrfDtlCd", "MTA01");
		waqMtaTblMapper.checkMtaTblItem(checkmap);
		
		
		//물리모델 컬럼 검증
		//물리모델 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_MTA_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "COL");

		//요청서내 컬럼 중복(PC001) 
		checkmap2.put("vrfDtlCd", "PC001");
		waqMtaColMapper.checkDupCol(checkmap2);

		//삭제일때 미존재 컬럼 체크(PC002)
		//checkmap2.put("vrfDtlCd", "PC002");
		//waqMtaColMapper.checkNotExistCol(checkmap2);

		//컬럼 길이를 체크(PC004)
		//checkmap2.put("vrfDtlCd", "PC004");
		//주석waqcolmapper.checkColNameLength(checkmap2);

		//데이터 타입이 없는 경우 오류(PC005)
		//checkmap2.put("vrfDtlCd", "PC005");
		//주석waqcolmapper.checkNoDataType(checkmap2);

		//컬럼 물리명 미존재(PC014)
		//checkmap2.put("vrfDtlCd", "PC014");
		//주석waqcolmapper.checkNoColPnm(checkmap2);
		
		//컬럼 물리명 기준으로 중복 체크
		//checkmap2.put("vrfDtlCd", "PC006");
		//주석waqMtaColMapper.checkDupColPnm(checkmap2);

		//컬럼 논리명 기준으로 중복 체크
		checkmap2.put("vrfDtlCd", "PC007");
		waqMtaColMapper.checkDupColLnm(checkmap2);
										
		//표준 대상인데 미존재 용어가 존재하는 경우
		//주석checkmap2.put("vrfDtlCd", "PC008");
		//주석waqcolmapper.checkNoSditm(checkmap2);
		
		//표준일 경우 데이터타입 표준으로 업데이트
		//주석checkmap2.put("dbmsTypCd", dbmsTypCd);
		//주석waqcolmapper.updateStndDateType(checkmap2);		
		
		//타입,길이 표준미준수
		//checkmap2.put("vrfDtlCd", "PC015");  
		//주석checkmap2.put("dbmsTypCd", dbmsTypCd);
		
		//주석waqcolmapper.checkDataLenScal(checkmap2);

		//PK여부가 Y일때 NOT NULL여부가 N이면 오류
		//checkmap2.put("vrfDtlCd", "PC009");
		//주석waqcolmapper.checkPkNotNull(checkmap2);
		
		//동일한 컬럼순서가 있을경우 오류
		//checkmap2.put("vrfDtlCd", "PC010");
		//주석waqcolmapper.checkColOrdDup(checkmap2);
		
		//동일한 PK컬럼순서가 있을경우 오류
		//checkmap2.put("vrfDtlCd", "PC011");
		//주석waqcolmapper.checkPKColOrdDup(checkmap2);

		//PK여부가 Y일경우 PK순서, NOTNULL여부 검증
		//checkmap2.put("vrfDtlCd", "PC012");
		//주석waqcolmapper.checkPKOrdNonul(checkmap2);
		

		//필수항목 미입력 시 오류 
		checkmap2.put("vrfDtlCd", "MTA01");
		waqMtaColMapper.checkMtaColItem(checkmap2);
		
		//비공개사유 체크 
		checkmap2.put("vrfDtlCd", "MTA01");
		waqMtaColMapper.checkNopenRsn(checkmap2);  
		
		//waqcolmapper.updateRqstDcdNoChgCol(rqstNo);
		//컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2); 

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PC000)
		checkmap2.put("vrfDtlCd", "PC000");
		waqMtaColMapper.checkNotChgData(checkmap2);

		
		//컬럼 오류가 있는 경우 체크(PT999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "PT999"); 
		waqMtaTblMapper.checkColErr(checkmap); 
				
		//테이블 등록가능여부(검증코드) 업데이트
		//컬럼정보 오류시 테이블에 검증오류 업뎃처리 
		//checkmap.put("bizDtlCd", null);	 	
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap); 
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PT000)  
		checkmap.put("vrfDtlCd", "PT000");
		waqMtaTblMapper.checkNotChgData(checkmap); 
		
		
		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo); 

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		logger.info("== MtaTblRqstServiceImpl.check end...");
		
		return result;
	}

	/**  요청서 등록요청 */
	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 요청서 승인 처리 eychoi */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
        logger.debug("여기여기");
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqMtaTbl savevo : (ArrayList<WaqMtaTbl>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqMtaTblMapper.updatervwStsCd(savevo); 
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)

		boolean waq2wam = true;
		
		mstVo.setRqstStepCd("A"); 
		
		result = requestMstService.updateRqstPrcStep(mstVo);
		
		//waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("물리모델 waq to wam and wah");

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
	
	/** 메타데이터 테이블 정보 요청에서 승인으로 저장 */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		String rqstno = mstVo.getRqstNo();

		List<WaqMtaTbl> waqclist = waqMtaTblMapper.selectWaqC(rqstno);

		for (WaqMtaTbl savevo : waqclist) {
			
			if(UtilString.isBlank(savevo.getMtaTblId())) {
				String id =  mtaObjectIdGnrService.getNextStringId();
				
				String orgCd = UtilString.null2Blank(user.getOrgCd());
				
				id = orgCd + "_" + id;
				
				savevo.setMtaTblId(id);
				waqMtaTblMapper.updateidByKey(savevo);
			}
		}

		result += waqMtaTblMapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//주석result += waqMtaTblMapper.updateWaqId(rqstno); 

		//승인자료 삭제처리 ??
		result += waqMtaTblMapper.deleteWAM(rqstno);

		result += waqMtaTblMapper.insertWAM(rqstno);

		//주석result += waqMtaTblMapper.updateWAH(rqstno);
		//주석result += waqMtaTblMapper.insertWAH(rqstno);

		return result;
	}
	
	/** 메타데이터 컬럼 정보 요청에서 승인으로 저장 */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		String rqstno = mstVo.getRqstNo();

		List<WaqMtaCol> waqclist = waqMtaColMapper.selectWaqC(rqstno);

		for (WaqMtaCol savevo : waqclist) {
			if(UtilString.isBlank(savevo.getMtaTblId() )) {
				String id =  mtaObjectIdGnrService.getNextStringId();
				
				String orgCd = UtilString.null2Blank(user.getOrgCd());
				
				id = orgCd + "_" + id;
				
				savevo.setMtaColId(id);

				waqMtaColMapper.updateidByKey(savevo); 
			}
		}

		result += waqMtaColMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqMtaColMapper.updateWaqId(rqstno);
 
		result += waqMtaColMapper.deleteWAM(rqstno);

		result += waqMtaColMapper.insertWAM(rqstno);

		//주석result += waqMtaColMapper.updateWAH(rqstno);

		//주석result += waqMtaColMapper.insertWAH(rqstno);


		return result ;
	}
	
	/** 메타데이터 테이블 테이블 변경대상 요청 리스트 추가  */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqMtaTbl> list) throws Exception {
		int result = 0;

		List<WaqMtaTbl> wamlist = waqMtaTblMapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

	/** 메타데이터 컬럼 요청 리스트 조회  eychoi */
	@Override
	public List<WaqMtaCol> getMtaColRqstList(WaqMstr search) {
		return waqMtaColMapper.selectMtaColRqstList(search);
	}
	
	/**  eychoi */
	@Override
	public WaqMtaCol getMtaColRqstDetail(WaqMtaCol search) {
		return waqMtaColMapper.selectMtaColDetail(search);
	}

	/**  eychoi */
	@Override
	public int delMtaColRqst(WaqMstr reqmst, ArrayList<WaqMtaCol> list) {
		int result = 0;

		for (WaqMtaCol delvo : list) {
				delvo.setIbsStatus("D");
		}

		result += regMtaColList(reqmst, list);
		
		return result;
	}

	/** 메타데이터 변경요청을 위한 목록 조회(DBC) */
	@Override
	public List<WaqMtaTbl> getDbcTblList(WaqMtaTbl search) {
		logger.debug("searchvo:{}", search);

		return waqMtaTblMapper.selectDbcTblList(search);
	}
	
	/** 메타데이터 변경요청을 위한 테이블 목록 조회(MTA) 
	 * 등록화면에서 기 등록된 메타데이터 테이블 리스트 조회 */
	@Override
	public List<WaqMtaTbl> getMtaTblList(WaqMtaTbl search) {
		logger.debug("searchvo:{}", search);

		return wamMtaTblMapper.selectMtaTblList(search);
	}
	
	
	private boolean sendDbConnTrg(EsbFilesendVO fileVO) throws IOException { 
		
		boolean checkCreateFile = true;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_DBCON); 
		
		List<HashMap<String, Object>> list = waaDbConnTrgMapper.selectEsbDbConnTrg();  
		
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}

	/** esb 전송 파일 생성 및 esb 연계 db 입력 
	 * @throws IOException */
	@Override
	public boolean sendMtaTbl(EsbFilesendVO fileVO) throws IOException {
		
		boolean checkCreateFile = true;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_TBL);
		
		List<HashMap<String, Object>> list = wamMtaTblMapper.selectListByRqstNo(fileVO.getMtaRqstNo());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}
	
	@Override
	public boolean sendMtaCol(EsbFilesendVO fileVO) throws IOException {
		boolean checkCreateFile = true;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_COL);
		
		List<HashMap<String, Object>> list = wamMtaColMapper.selectListByRqstNo(fileVO.getMtaRqstNo());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}
	
	@Override
	public boolean sendMtaTotal(EsbFilesendVO fileVO) throws IOException {
		boolean checkCreateFile = true;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_TOTAL);

		List<HashMap<String, Object>> list = wamMtaTblMapper.selectEsbTotal(fileVO.getMtaRqstNo());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}	

	@Override
	public boolean sendMtaInfoSys(EsbFilesendVO fileVO) throws IOException {
		boolean checkCreateFile = true;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_INFOSYS);
		List<HashMap<String, Object>> list = wamMtaTblMapper.selectEsbInfoSys(fileVO.getInfoSysCd());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}
	/** esb 전송 파일 생성 및 esb 연계 db 입력 자동 
	 * @throws IOException */
	/*@Override
	public boolean sendMtaTblId(EsbFilesendVO fileVO) throws IOException {
		
		boolean checkCreateFile = false;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_TBL);
		
		List<HashMap<String, Object>> list = wamMtaTblMapper.selectListByTblId(fileVO.getMtaTblId());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}*/
	
	/*@Override
	public boolean sendMtaColId(EsbFilesendVO fileVO) throws IOException {
		boolean checkCreateFile = false;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_COL);
		
		List<HashMap<String, Object>> list = wamMtaColMapper.selectListByTblId(fileVO.getMtaTblId());
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}*/
	/** esb 전송 파일 생성 및 esb 연계 db 입력 
	 * @throws IOException */
	/*@Override
	public boolean sendWamMtaTbl(EsbFilesendVO fileVO) throws IOException {
		
		boolean checkCreateFile = false;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_TBL);
		
		List<HashMap<String, Object>> list = wamMtaTblMapper.selectWamMtaTblByRqstNo(fileVO.getMtaRqstNo());
		
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}*/
	
	/*@Override
	public boolean sendWamMtaCol(EsbFilesendVO fileVO) throws IOException {
		boolean checkCreateFile = false;
		
		fileVO.setFileDtlCd(EsbConfig.MTA_COL); 
				
		List<HashMap<String, Object>> list = wamMtaColMapper.selectWamMtaColByRqstNo(fileVO.getMtaRqstNo());
		
		checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);
		
		return checkCreateFile;
	}*/

	@Override
	public boolean sendEsb(EsbFilesendVO fileVO) throws IOException {
		
		boolean checkSendDbConTrg = false;
		boolean checkSendMtaTbl = false;
		boolean checkSendMtaCol = false;
		boolean checkSendMtaTotal = false;
		boolean checkSendMtaInfoSys = false;
		
		checkSendDbConTrg = sendDbConnTrg(fileVO);
		
		checkSendMtaTbl = sendMtaTbl(fileVO); 

		checkSendMtaCol = sendMtaCol(fileVO);

		checkSendMtaTotal = sendMtaTotal(fileVO);
		
		checkSendMtaInfoSys = sendMtaInfoSys(fileVO);
			
		if(checkSendMtaTbl && checkSendMtaCol && checkSendDbConTrg && checkSendMtaTotal && checkSendMtaInfoSys) {
			
			return true;
		}else {
			return false;
		}
	}
	
	/*@Override
	public boolean sendEsbAuto(EsbFilesendVO fileVO) throws IOException {
		
		boolean checkSendDbConTrg = false;
		boolean checkSendMtaTbl = false;
		boolean checkSendMtaCol = false;
		
		checkSendDbConTrg = sendDbConnTrg(fileVO);
		
		checkSendMtaTbl = sendMtaTblId(fileVO); 
		
		checkSendMtaCol = sendMtaColId(fileVO);
			
		if(checkSendMtaTbl && checkSendMtaCol && checkSendDbConTrg) {
			
			return true;
		}else {
			return false;
		}
	}*/

	

	/*@Override
	public boolean resendEsb(List<EsbFilesendVO> list) throws IOException {
		
		boolean checkSendDbConTrg = false;
		boolean checkSendMtaTbl = false;
		boolean checkSendMtaCol = false;
		
		boolean bRtn = false;
		
		for(EsbFilesendVO fileVO : list) {
			
			checkSendDbConTrg = sendDbConnTrg(fileVO);
			
			//=====최종 승인 데이터만 재전송=============
			checkSendMtaTbl = sendWamMtaTbl(fileVO);
			
			checkSendMtaCol = sendWamMtaCol(fileVO);
			
			if(checkSendMtaTbl && checkSendMtaCol && checkSendDbConTrg) {
				
				bRtn  = true;
			}else {
				bRtn = false;
			}
						
		}
		
		return bRtn;
	}*/

	@Override
	public int regWat2Waq(WaqMstr reqmst, ArrayList<WaqMtaTbl> list) throws Exception { 
		int result = 0;
		
		List<WaqMtaTbl> wamlist = null;  
		
		if(UtilString.null2Blank(reqmst.getGapStsCd()).equals("D")){
			
			reqmst.setRqstDcd("DD");
			
			wamlist = waqMtaTblMapper.selectwamlist(reqmst, list);  
		}else{
			wamlist = waqMtaTblMapper.selectwatlist(reqmst, list);   			
		}

		result = registerWat(reqmst, wamlist);  
		
		return result;
	}
 
	public int registerWat(WaqMstr reqmstVo, List<WaqMtaTbl> reglist) {
		
		logger.debug("reqmstvo:{}\nbizInfo:{}", reqmstVo, reqmstVo.getBizInfo());
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String orgCd  = user.getOrgCd();
		
		String rqstNo = reqmstVo.getRqstNo();
		int result = 0;
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmstVo.getRqstStepCd()) || "S".equals(reqmstVo.getRqstStepCd())) {
			
			reqmstVo.setOrgCd(orgCd);
			reqmstVo.setBizDcd("MTA"); 
			
			requestMstService.insertWaqMst(reqmstVo);
		}
		
		if(reglist != null) {
			for(WaqMtaTbl savevo : (ArrayList<WaqMtaTbl>)reglist) {
				//요청번호 셋팅
				savevo.setFrsRqstUserId(userid);
				savevo.setRqstUserId(userid);
				savevo.setRqstNo(rqstNo);
				
				savevo.setTblCreDt( UtilString.replace(savevo.getTblCreDt(), "-", ""));
				
				if(UtilString.null2Blank(reqmstVo.getGapStsCd()).equals("D")){
					//단건 저장...
					result += saveMtaTblRqst(savevo);
				}else{
					//단건 저장...
					result += saveWatTblRqst(savevo);
				}								
			}
		}
		
		reqmstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmstVo);
		
		return result;
	}

	private int saveWatTblRqst(WaqMtaTbl saveVo) {
		
		int result = 0;
		
		String tmpStatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpStatus)) {

			result = waqMtaTblMapper.insertSelective(saveVo);
			
			//초기 등록시 WAM에 해당하는 컬럼 존재시 추가...
			waqMtaColMapper.insertByWatRqstSno(saveVo);
						
		}else if ("U".equals(tmpStatus)) {
			//업데이트
			result = waqMtaTblMapper.updateByPrimaryKeySelective(saveVo);
		}else if ("D".equals(tmpStatus)) {
			result = waqMtaTblMapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqMtaColMapper.deleteByrqstSno(saveVo);
			//관계 요청 리스트 삭제.....
		}
		
		return result;
	}

	@Override
	public int updateWamMtaGapStsCd(WatDbcTbl data) {
		
		int result = 0;
		
		result = waqMtaTblMapper.updateInitGapStsCd(data); 
		
		result = waqMtaTblMapper.updateWamMtaGapStsCd(data);
		
		return result; 
	}

	@Override
	public List<HashMap> getOrgInfoSys(HashMap data) {   

		return waqMtaTblMapper.selectOrgInfoSys(data);
	}

	@Override
	public List<HashMap> getInfoSysDbConnTrg(HashMap<String, String> data) {
		
		return waqMtaTblMapper.selectInfoSysDbConnTrg(data); 
	}

	@Override
	public List<HashMap> getInfoSysDbSch(HashMap<String, String> data) {
		
		return waqMtaTblMapper.selectInfoSysDbSch(data);  
	}

	@Override
	public HashMap<String, String>  getTempRqstCnt(WaqMstr reqmst) {
		
		return waqMtaTblMapper.selectTempRqstCnt(reqmst);    
	}

	@Override
	public WaqMtaCol getPopMtaColRqstDetail(WaqMtaCol search) {

		return waqMtaColMapper.selectPopMtaColRqstDetail(search); 
	}

	@Override
	public ArrayList<WaqMtaTbl> getMtaTblByDbSchId(WaqMtaTbl data) {
		
		//임시저장 중 오류 데이터 삭제 처리
		int delErrWaqMstr = waqMtaTblMapper.deleteErrWaqMstr(data);
		logger.debug("\n delErrWaqMstr:" + delErrWaqMstr);
		
		ArrayList<WaqMtaTbl> result = waqMtaTblMapper.selectMtaTblByDbSchId(data);
		
		String rqstNo = "";
		
		if(result != null){
			
			if(result.size() > 0) {
			
				WaqMtaTbl rtnTbl = (WaqMtaTbl)result.get(0);
				
				int iRtn = waqMtaTblMapper.deleteNotMyTempSave(rtnTbl);
				
				logger.debug("\n iRtn:" + iRtn);
			}			 
		}

		return result;
	}

	@Override
	public int updatePrsnColList(WaqMstr reqmst, List<WaqMtaCol> reglist) {

		int result = 0;
		
		if(reglist != null) {
			
			for(WaqMtaCol savevo : (ArrayList<WaqMtaCol>)reglist) {
				
				result += waqMtaColMapper.updatePrsnInfo(savevo);
			}
		}
		
		return result; 
	}
	
	@Override
	public String checkMtaColNm(WaqMtaCol search) {
		return waqMtaColMapper.checkMtaColNm(search) == 0?"Y":"N";
	}

	@Override
	public ArrayList<WaqMtaTbl> getUpdWatMtaCheck(WaqMtaTbl search) { 

		return waqMtaTblMapper.selectUpdWatMtaCheck(search);
	}
	
	@Override
	public List<WaqMtaTbl> getMatTblAuto(WaqMtaTbl search) { 
		
		return wamMtaTblMapper.selectMtaTblListAuto(search);
	}
	@Override
	public List<BrmInfoVo> getBrmInfoVoList(){
		return wamMtaTblMapper.selectBrmList();
	};
	
	@Override
	public boolean sendEsbAuto(EsbFilesendVO fileVO){
	    boolean checkSendDbConTrg = false;
	    boolean checkSendMtaTbl = false;
	    boolean checkSendMtaCol = false;
	
	    try {
			checkSendDbConTrg = sendDbConnTrg(fileVO);

			checkSendMtaTbl = sendMtaTblId(fileVO);

			checkSendMtaCol = sendMtaColId(fileVO);
			
		} catch (Exception e) {
			return false;
		}
	
	      return true;
	}
	
	public boolean sendMtaTblId(EsbFilesendVO fileVO) throws IOException {
	  boolean checkCreateFile = false;

	  fileVO.setFileDtlCd("tbl");

	  List list = wamMtaTblMapper.selectListByTblId(fileVO.getMtaTblId());
	  checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);

	  return checkCreateFile;
	}

	public boolean sendMtaColId(EsbFilesendVO fileVO) throws IOException {
	  boolean checkCreateFile = false;

	  fileVO.setFileDtlCd("col");

	  List list = wamMtaColMapper.selectListByTblId(fileVO.getMtaTblId());
	  checkCreateFile = esbFilesendService.createEsbFilesend(list, fileVO);

	  return checkCreateFile;
	}
	
	public int preAutoEnd(EsbFilesendVO fileVO) throws Exception {
	  int result = 0;
	
	  result = waqMtaTblMapper.updateMtaGapStsCd(fileVO);
	
	  return result;
	}
	
	public int rollBackRegTbl(EsbFilesendVO fileVO) throws Exception {
	  int result = 0;

	  result = waqMtaTblMapper.updateMtaRegTypCd(fileVO);
	  result = waqMtaTblMapper.updateMtaColRegTypCd(fileVO);

	  return result;
	}
	
}
