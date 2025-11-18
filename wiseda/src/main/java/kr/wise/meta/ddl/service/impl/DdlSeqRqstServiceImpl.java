package kr.wise.meta.ddl.service.impl;

import java.sql.SQLException;
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
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlSeqRqstService;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlSeqMapper;

/**
 * <PRE>
 * 1. ClassName : DdlSeqRqstServiceImpl
 * 2. FileName  : DdlSeqRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 02.
 * </PRE>
 */
@Service("ddlSeqRqstService")
public class DdlSeqRqstServiceImpl implements DdlSeqRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaqDdlSeqMapper waqmapper;

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
    private DdlScriptMapper ddlmapper;
    
    
    /** DDL 시퀀스 요청서 조회 **/
	public List<WaqDdlSeq> getDdlSeqRqstList(WaqMstr search) {
//			//logger.debug("testcol:{}", testcol.toVerboseString());
		return waqmapper.selectDdlSeqListbyMst(search) ;
	}
	
	/** DDL 시퀀스 요청서 상세 조회 **/
	public WaqDdlSeq getDdlSeqRqstDetail(WaqDdlSeq searchVo) {
		return waqmapper.selectDdlSeqDetail(searchVo);
	}


	/** DDL 시퀀스 요청서 저장  
	 * @throws Exception */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		//logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		int result = 0;

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();
		String ddlTrgDcd = UtilString.null2Blank(mstVo.getDdlTrgDcd());
		

	

		if(reglist != null) {
			for (WaqDdlSeq saveVo : (ArrayList<WaqDdlSeq>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
				//개발인 경우에만 일괄 세팅, DDL이관(테스트,운영)인 경우에는 이미 값이 지정되어 있음
				if(ddlTrgDcd.equals("D")){ 
					saveVo.setDdlTrgDcd(ddlTrgDcd); 
				}

				//단건 저장...
				result += saveDdlSeqRqst(saveVo);
				logger.debug("시퀀스 저장건수 ::: "  + result);
			}
		}

		//이관일경우 마지막에 한번에 RqstStepCd UPDATE
		if (!"DTT".equals(mstVo.getBizDcd())){
			if (!"DTR".equals(mstVo.getBizDcd())){
				mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
				requestMstService.updateRqstPrcStep(mstVo);
			}
		}
			

			
		return result;

	}

	/** DDL 시퀀스 단건 저장 **/
	private int saveDdlSeqRqst(WaqDdlSeq saveVo) throws SQLException {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		
		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			
		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}



	/** DDL 시퀀스 요청서 체크
	 * @throws Exception */
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();
		
		String ddlTrgDcd = UtilString.null2Blank(mstVo.getDdlTrgDcd());
		//logger.debug("check ddlTrgDcd : " + ddlTrgDcd);
		
		if(ddlTrgDcd.equals("D")){
			bizdtlcd = mstVo.getBizInfo().getBizDtlCd();
			tblnm    = mstVo.getBizInfo().getTblNm();
		}else{ //TR : DDL이관시
			bizdtlcd = "TSFSEQ";
			tblnm    = "WAQ_DDL_SEQ";
		}	

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//DBMS 스키마 업데이트
		waqmapper.updateDbmsId(rqstNo);

		//스키마ID로 명 업데이트
		waqmapper.updateDbmsPnm(rqstNo);
				
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
		
		//이관정보 업데이트(변경 삭제대상일 경우)
		waqmapper.updateTsfInfo(rqstNo);


		//검증 시작
		//DDL 시퀀스 검증
		//DDL 시퀀스 검증 파라메터 초기화....(시퀀스명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DS001)
		checkmap.put("vrfDtlCd", "DS001");
		waqmapper.checkDupSeq(checkmap);

		//삭제일때 미존재 시퀀스 체크(Ds002)
		checkmap.put("vrfDtlCd", "DS002");
		waqmapper.checkNotExistSeq(checkmap);

		//DB스키마 ID 미존재 체크(DS003)
		checkmap.put("vrfDtlCd", "DS003");
		waqmapper.checkNonDbmsID(checkmap);

		//요청중인 시퀀스 (DS004)
		checkmap.put("vrfDtlCd", "DS004");
		waqmapper.checkRequestSeq(checkmap);
	/*	2018.11.02 - 우리은행아님
		//시퀀스 명명규칙(엔티티기반) 위반 (DS005)
		//테이블명+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))+##(일련번호2자리)
		checkmap.put("vrfDtlCd", "DS005");
		waqmapper.checkSeqPnmEnt(checkmap);
		
		//시퀀스 명명규칙(컬럼기반) 위반 (DS006)
		//컬럼명+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))+#(일련번호1자리)
		checkmap.put("vrfDtlCd", "DS006");
		waqmapper.checkSeqPnmCol(checkmap);
		
		//시퀀스 명명규칙(주제영역기반) 위반 (DS007)
		//L1코드+L3코드+###(일련번호3자리)+N+_+##(자릿수2자리)+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))+_(옵션)+YYYYMMDD(일자,옵션)
		checkmap.put("vrfDtlCd", "DS007");
		waqmapper.checkSeqPnmSubj(checkmap);
		
		//미존재테이블 (DS008) - 엔티티기반에만 해당
		checkmap.put("vrfDtlCd", "DS008");
		waqmapper.checkExistsTbl(checkmap);
		
		//미존재컬럼 (DS009) - 컬럼기반에만 해당
		checkmap.put("vrfDtlCd", "DS009");
		waqmapper.checkExistsCol(checkmap);
		
		//미존재 L1코드 또는 L3코드 (DS010) - 주제영역기반에만 해당
		checkmap.put("vrfDtlCd", "DS010");
		waqmapper.checkExistsL1L3Cd(checkmap);
		
		//미존재 권한 포함 (DS011)
		if(ddlTrgDcd.equals("D")){
			checkmap.put("vrfDtlCd", "DS011");
			waqmapper.checkExistsGrtLst(checkmap);
		}
		
		//적용예정일자 오늘날자보다 작은지 체크
		checkmap.put("vrfDtlCd", "DS012");
		waqmapper.checkAplRegDd(checkmap);

		//DDL등록요청시에 엑셀로 테스트나 운영정보가 들어올경우 검증
		if(!UtilString.null2Blank(bizdtlcd).equals("TSFSEQ")){
			checkmap.put("vrfDtlCd", "DS013");
			waqmapper.checkExistsTsfSeq(checkmap);
		}
		
		//운영반영시 SR번호 필수 체크
		checkmap.put("vrfDtlCd", "DT014"); 
		waqmapper.checkRealSrMngNo(checkmap);	
		
		//운영이관 시 체크카드여부 테이블의 적용요청일자 는 매월둘째주금요일 임
		// DDL_TRG_DCD = 'R' APL_REQ_DT
		checkmap.put("vrfDtlCd", "DT018"); 
		waqmapper.checkAplReqDtByDdlTrgDcd(checkmap);
		
		//운영이관시 적용요청구분,적용요청일자 필수
		// DDL_TRG_DCD = 'R' APL_REQ_TYP_CD ,APL_REQ_DT
		//201803015 우동원차장 요청으로 개발,테스트, 운영 구분 없이 적용요청일자는 필수로 변경
		checkmap.put("vrfDtlCd", "DT019"); 
		waqmapper.checkAplInfoByDdlTrgDcd(checkmap);
		
		//계정계이고 운영이관일 경우 적용요청일자는 매주 금요일
		// 만약에 주제영역에서 적용요청일 과 적용요청일검증 기준일을 관리 한다면 매주 금요일 관련 검증은 없어도 될 것 같습니다. 우동원차장(20180529)
		// DT022, DT023 으로 데체
		//엔티티기반 제외 엔티티기반일 경우  DT022, DT023 사용
		checkmap.put("vrfDtlCd", "DT020"); 
		waqmapper.checkAplReqDtByDdlTrgDcdReal(checkmap);
		
		
		//운영이관시 적용요청일자는 항상 금요일 이다. 화 목도 추가 될수 있다?
		//엔티티기반일경우
		checkmap.put("vrfDtlCd", "DT022"); 
		waqmapper.checkAplReqDt(checkmap);
		//엔티티기반 아닐경우 
//		checkmap.put("vrfDtlCd", "DT024"); 
//		waqmapper.checkAplReqDt3(checkmap);
		
		//운영이관시 적용요청일자가 수, 목, 금 일경우 차주 금요일이다...
		//엔티티기반일경우
		checkmap.put("vrfDtlCd", "DT023"); 
		waqmapper.checkAplReqDt2(checkmap);
		//엔티티기반 아닐경우
//		checkmap.put("vrfDtlCd", "DT025"); 
//		waqmapper.checkAplReqDt4(checkmap);
		
		//INIT유형 사용용도 일별(D) 필수 체크 DS014
		checkmap.put("vrfDtlCd", "DS014"); 
		waqmapper.checkSeqInitCdExists(checkmap);
		//INIT유형 8, 9 일경우 업무영향도 필수 체크 DS015
		checkmap.put("vrfDtlCd", "DS015"); 
		waqmapper.checkSeqBizIfncByInitCd(checkmap);
		*/
		//요청사유 10글자 이상, 필수항목 검증
//		checkmap.put("vrfDtlCd", "COM01");
//		waqRqstVrfDtlsMapper.checkRqstResn(checkmap);
		
		//오라클만 사용 가능
//		checkmap.put("vrfDtlCd", "COM02");
//		waqRqstVrfDtlsMapper.checkDbmsType(checkmap);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DS000)
		checkmap.put("vrfDtlCd", "DS000");
		waqmapper.checkNotChgData(checkmap);
		logger.info("DdlSeqRqstServiceImpl.java - check 성공1");
		
//		if(ddlTrgDcd.equals("D")){
//			
//		}else
//		//DDL이관일경우 변경데이터 없음일경우 삭제 해버려
//		{	// 이관.. //DS000
//			Map<String, Object> delmap = new HashMap<String, Object>();
//			delmap.put("tblnm"	, "WAQ_DDL_SEQ");
//			delmap.put("rqstNo"	, rqstNo);
//			delmap.put("vrfDtlCd"	, "DS000");
//			waqRqstVrfDtlsMapper.delByBrfCd(delmap);
//		}
		
		logger.info("DdlSeqRqstServiceImpl.java - check 성공2");
		
		//시퀀스 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		waqRqstVrfDtlsMapper.updateNotChgData(checkmap);
		
		logger.info("DdlSeqRqstServiceImpl.java - check 성공3");
		
		//마스터 정보 업데이트...
		//이관일경우 마지막에 한번에 RqstStepCd UPDATE
		if (!"DTT".equals(mstVo.getBizDcd())){
			if (!"DTR".equals(mstVo.getBizDcd())){
				//마스터 정보 업데이트...
				requestMstService.updateRqstPrcStep(mstVo);
			}
		}
		
		logger.info("DdlSeqRqstServiceImpl.java - check 성공4");

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);

		logger.info("DdlSeqRqstServiceImpl.java - check 성공5");
		
		//DDL 스크립트 업데이트...
		updateDdlSeqScriptWaq(mstVo);
		
		logger.info("DdlSeqRqstServiceImpl.java - check 성공6");

		logger.info("DdlSeqRqstServiceImpl.java - check 성공7");
		return result;
	}
	
	
	public void updateDdlSeqScriptWaq(WaqMstr mstVo) throws Exception {

		List<WaqDdlSeq> waqlist = ddlScriptService.updateDdlSeqScirptWaq(mstVo);

		for (WaqDdlSeq savevo : waqlist) {
			waqmapper.updateDdlScriptWaq(savevo);
		}

	}
	
	/** DDL 시퀀스 요청서 등록요청 */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/** DDL 시퀀스 요청서 승인 */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//logger.debug("DDL시퀀스 결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);
        if(reglist != null){//자동발행을 위해 list가 null이어도 결재가 넘어가도록 처리
		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		  for (WaqDdlSeq savevo : (ArrayList<WaqDdlSeq>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		  }
        }else{
          result = 1;
        }
		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			//logger.debug("DDL시퀀스 결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "DDL시퀀 결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 시퀀스를 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3. 최종 승인인지 아닌지 확인한다. 
        boolean waq2wam = true;
		//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DDL_TBL");
        if(reglist != null){ 
           waq2wam = requestApproveService.setApproveProcess(mstVo);
        }
		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			//logger.debug("DDL 시퀀스 waq to wam and wah");
			
			//자동등록일 경우 c,u,d 업데이트
			//시퀀스에서는 아직 구현하지 않음
            if(reglist == null){
//     
//            	result += waqmapper.updateWaqCUD(rqstNo);
//          
//     			//시퀀스가 변경일 때 컬럼 위치가 바뀌었을 경우 테이블 생성유형 drop&create로 변경
//			    waqmapper.updateCheckColPositonChg(rqstNo);  
//     			//  테이블이 변경일 때 PK정보가 바뀌었을 경우 테이블 생성유형 drop&create로 변경
//			    waqmapper.updateCheckPKChg(rqstNo);  
//            			//DDL 스크립트 업데이트...
//		        updateDdlScriptWaq(mstVo);
            }
			result = 0;
			result += regWaq2Wam(mstVo);


            if(reglist !=null){  //자동발행용 조건 추가 
			//업데이트 내용이 없으면 오류 리턴한다.
			   if (result <= 0 ) {
				   //logger.debug("DDL시퀀스 결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				   throw new WiseBizException("ERR.APPROVE", "DDL시퀀스 결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			   }
            }

		}

		return result;
	}
	

	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlSeq> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlSeq savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlSeqId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		updateDdlSeqScriptWaq(mstVo);
		
		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}
	

	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception {
		int result = 0 ;
       
		List<WaqDdlSeq> wamlist = waqmapper.selectwamseqlist(reqmst, list);

		result += register(reqmst, wamlist);

		return result;
	}

	public int delDdlSeqRqst(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception {
		int result = 0;

		for (WaqDdlSeq savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}	
	
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("DDLSEQ");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);
		
		return result;
	}
	
	@Override 
	public int updateSrMngNoPrjMngNo(WaqMstr mstVo) throws Exception {
		int result = 0;
		List<WaqDdlSeq> srlist = waqmapper.selectSrMngNoPrjMngNo(mstVo);
		
		for (WaqDdlSeq savevo : srlist) {
			result += waqmapper.updateWamSrMngNoPrjMngNoByKey(savevo);
			result += waqmapper.updateWahSrMngNoPrjMngNoByKey(savevo);
		}
		return result;
	}
	@Override
	public List<WamDdlSeq> selectDdlTsfSeqListForRqst(WamDdlSeq search) {
		 
		List<WamDdlSeq> list = null;
		
		String rqstDcd = UtilString.null2Blank(search.getRqstDcd());
		
		if(rqstDcd.equals("DD")){
			
			list = waqmapper.selectDelDdlTsfSeqListForRqst(search); 
		}else{
			list = waqmapper.selectDdlTsfSeqListForRqst(search);  
		}
		
		return list; 
	}
	

	@Override
	public int regTsfWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception {
		int result = 0 ;

		List<WaqDdlSeq> wamlist = waqmapper.selectTsfDdlSeqList(reqmst, list);

		result += registerTsf(reqmst, wamlist); 

		return result;
	}
	
	/** DDL 시퀀스 요청서 저장... insomnia */
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
			for (WaqDdlSeq saveVo : (ArrayList<WaqDdlSeq>)wamlist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlTsfSeqRqst(saveVo);
				
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}
	
	/** DDL 이관 테이블 단건 저장 insomnia */
	private int saveDdlTsfSeqRqst(WaqDdlSeq saveVo) {
		int result  = 0;
		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			
		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}
}
