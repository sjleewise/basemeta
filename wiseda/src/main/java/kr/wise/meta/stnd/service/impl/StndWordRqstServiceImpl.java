/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRqstServieImpl.java
 * 2. Package : kr.wise.meta.stnd.web.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 2:54:03
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibm.as400.access.User;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.app.service.WaqAppStwdMapper;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.StndWordRqstService;
import kr.wise.meta.stnd.service.WapWordDv;
import kr.wise.meta.stnd.service.WapWordDvMapper;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.stnd.service.WaqStwdMapper;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordRqstServieImpl.java
 * 3. Package  : kr.wise.meta.stnd.web.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 4. 오후 2:54:03
 * </PRE>
 */
@Service("stndWordRqstService")
public class StndWordRqstServiceImpl implements StndWordRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private RequestMstService requestMstService;

    @Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private WaqStwdMapper waqmapper;
	
	@Inject
	private WaqAppStwdMapper waqappmapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private StndDmnRqstService stndDmnRqstService;

	@Inject
	private StndItemRqstService stndItemRqstService;

	@Inject
	private WapWordDvMapper wapWordDvMapper;

	/** insomnia 여기
	 * @throws Exception */ 
	public int check(WaqMstr mstVo) throws Exception {

		int result = 0;

		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");
		
		String ConfigUseYn = cmcdCodeService.getDetailCodeNm("CONFIG_SET", "동음이의어허용여부"); // 동읨이의어 허용여부
		

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String adminYn = UtilString.null2Blank(user.getIsAdminYn());
		
		//String stwdKeyDcd = UtilString.null2Blank(mstVo.getStwdKeyDcd());
		
		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("STWD");
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		

		//검증 시작
		//검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, "WAQ_STWD");
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, "STWD");
		//checkmap.put("stwdKeyDcd", stwdKeyDcd);
		
		//등록유형코드(C/U/D), 검증결과코드 초기화 ('4') 업데이트
		waqmapper.updateCheckInit(checkmap);
		
		//삭제일경우 모든 컬럼 업데이트
		waqmapper.updateStwdDelInfo(rqstNo);

		//등록요청중인 표준단어 검증(WD011)
		checkmap.put("vrfDtlCd", "WD011");
		waqmapper.checkRequestStwd(checkmap);

		//요청서내 중복자료 검증(WD001)
		checkmap.put("vrfDtlCd", "WD001");
		waqmapper.checkDupStwd(checkmap);
		//미존재 표준단어(삭제시)(WD002)
		checkmap.put("vrfDtlCd", "WD002");
		waqmapper.checkNotExistStwd(checkmap);
		
		//논리명,물리명 Unique 
		// if(stwdKeyDcd.equals("U")){
			
        if (!ConfigUseYn.equals("Y")) {	// 동음이의어 설정코드 체크		
			//표준단어 한글명 중복(WD003)   
			checkmap.put("vrfDtlCd", "WD003");
			waqmapper.checkDupStwdLnm(checkmap);
        }
			
			//표준단어 영문명 중복(WD004)
			checkmap.put("vrfDtlCd", "WD004");
			waqmapper.checkDupStwdPnm(checkmap);
	
				
			
//       if (!ConfigUseYn.equals("Y")) { // 동음이의어 설정코드 체크			
//		//표준단어 동음이의어 존재
//		checkmap.put("vrfDtlCd", "WD012");
//		waqmapper.checkHomonym(checkmap);
//       }
//			
		
		//표준단어 금칙어 존재
		checkmap.put("vrfDtlCd", "WD015");
		waqmapper.checkNtChek(checkmap);
		
		//TODO : 유사어 존재(WD006)
		checkmap.put("vrfDtlCd", "WD006");
		waqmapper.checkExistSymn(checkmap);

		
//표준단어 유사어 존재  //190111 코드 미존재(사용할수 없음)
//		checkmap.put("vrfDtlCd", "WD016");
//		waqmapper.checkSwChek(checkmap);
			
		
		
		//사용중 표준단어(삭제시)(WD005)
		checkmap.put("vrfDtlCd", "WD005");
		waqmapper.checkUseStwd(checkmap);

		
		//표준단어 한글명 공백 존재(WD007)
		checkmap.put("vrfDtlCd", "WD007");
		waqmapper.checkLnmExistSpac(checkmap);
		//표준단어 영문명 공백 존재(WD008)
		checkmap.put("vrfDtlCd", "WD008");
		waqmapper.checkPnmExistSpac(checkmap);
		//표준단어 한글명 길이 초과(WD009)
		checkmap.put("vrfDtlCd", "WD009");
		waqmapper.checkLnmMaxLen(checkmap);
		//표준단어 영문명 길이 초과(WD010)
		checkmap.put("vrfDtlCd", "WD010");
		waqmapper.checkPnmMaxLen(checkmap);
				
		//표준단어 변경된 데이터 없음(WD000)
		checkmap.put("vrfDtlCd", "WD000");
		waqmapper.checkNoChg(checkmap);

		//표준단어 물리명 존재하지 않음
		checkmap.put("vrfDtlCd", "WD013");
		waqmapper.checkPnmExists(checkmap);
		//등록가능여부(검증코드) 업데이트
		
		//표준단어 설명 등록 여부 체크
		checkmap.put("vrfDtlCd", "WD014");
		waqmapper.checkObjDesn(checkmap);
		
		//공통표준 존재 - 확인(3)
		//WD016	공통표준 존재		예	공통표준이 존재합니다. 확인 후 등록하세요.
		checkmap.put("vrfDtlCd", "WD016");
		waqmapper.checkExistUpStndDtl(checkmap);
		
		result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_STWD", rqstNo, "STWD");
//		waqmapper.updateVrfCd(rqstNo);
		
		//공통표준 존재 - 확인(3)
		result += waqRqstVrfDtlsMapper.updateVrfCdCheck("WAQ_STWD", rqstNo, "STWD");

		//도메인 검증 체크
		stndDmnRqstService.check(mstVo);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}

	/** 삭제예정 insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}


	/** 표준단어 요청 리스트 조회 insomnia */
	public List<WaqStwd> getStndWordRqstist(WaqMstr search) {

		return waqmapper.selectrqstStndWordListbyMst(search);
	}

	/** insomnia */
	public int regStndWordRqstlist(ArrayList<WaqStwd> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaqStwd saveVo : list) {
			saveVo.setWritUserId(userid);

//			result += saveStndWordRqst(saveVo);
		}

		return result;
	}

	/**표준단어 요청 상세조회 insomnia */
	public WaqStwd getStndWordRqstDetail(WaqStwd searchVO) {
		return waqmapper.selectRqstStndWord(searchVO);
	}

	/** 표준단어 리스트 등록 insomnia */
	/** 요청서 내용을 저장한다.(완료 후 임시저장 상태로 변경) insomnia
	 * @throws Exception */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqStwd saveVo : (ArrayList<WaqStwd>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndWord(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		//requestMstService.updateRequestMsterNm(mstVo);
//		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");

		//요청명 업데이트 (표준단어 aaaa 외 3건)
//		requestMstService.updateWaqMstrqstNm(mstVo.getRqstNo(), "WAQ_STWD", "STWD_LNM",  bizdtlnm);

		return result;
	}

	/** @param saveVo
	/** @return insomnia
	 * @throws Exception */
	private int saveRqstStndWord(WaqStwd saveVo) throws Exception {

		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			result = waqmapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}



	/** 표준단어 요청 리스트 삭제 insomnia */
	public int delStndWordRqst(WaqMstr reqmst, String delkey) {
		String[] delkeys = delkey.split("[|]");

		int cnt = delkeys.length;
		Integer [] ids = new Integer[cnt];


		for (int i=0; i<cnt; i++) {
			String delk = delkeys[i];
			ids[i] = new Integer(delk);
			logger.debug("delkey:{}", delk);
		}

		logger.debug("ids:{}", ids);

		return waqmapper.deletebyRqstSno(ids, reqmst.getRqstNo());
	}

	/** 결재 승인 처리 업데이트.... insomnia
	 * @throws Exception */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqStwd savevo : (ArrayList<WaqStwd>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqmapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_STWD");

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("waq to wam and wah");

			result = 0;
			result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @param mstVo insomnia
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID 채번한다.
		List<WaqStwd> waqclist =  waqmapper.selectWaqC(rqstno);

		for (WaqStwd savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setStwdId(id);
//			savevo.setIbsStatus("U");

			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);
		
//		waqappmapper.updateWAM(mstVo);

		return result;

	}

	/** WAM에 있는 표준단어를 변경 요청 등록한다. insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqStwd> list) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		
		result = waqmapper.insertwam2waq(reqmst, list);

		register(reqmst, null);

		/*for (WaqStwd saveVo : list) {
			saveVo.setIbsStatus("I");
			saveVo.setRqstDcd("CU");
		}
		return register(reqmst, list);*/

		return result;

	}

	/** insomnia
	 * @throws Exception */
	public int delStndWordRqstList(WaqMstr reqmst, ArrayList<WaqStwd> list) throws Exception {
		int result = 0;

		for (WaqStwd savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** insomnia */
	public int regapprove(WaqMstr mstVo, List<WaqStwd> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqStwd savevo : (ArrayList<WaqStwd>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqmapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result < 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		return result;
	}
	
	//신청목록에 표준단어가 존재할 경우 리턴(표준신청 탭체크용)
	public int checkExistsWaqWord(WaqMstr mstr){
	
		List<WaqStwd> list = waqmapper.selectExistsWordCheck(mstr);

		if(list.isEmpty()){
			return 0;
		}else{
			
		    return 3;
		}
	   
	}
	
		/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);

		//마스터 등록
		register(reqmst, null);
        
		//검증
		check(reqmst);
		
		//임시저장으로 변경
		reqmst.setRqstStepCd("S");
		

		return result;

	}

		@Override
		public List<WapWordDv> getWordDivList(WapWordDv search) throws Exception {
			return wapWordDvMapper.selectWordDivListList(search);
		}

		@Override
		public Map<String, String> delWordAutoDiv(List<WapWordDv> list) throws Exception {
			Map<String, String> resultMap =  new HashMap<String, String>();
			int rtnCnt = 0;
			String dvrqstno = "";
			for (WapWordDv savevo : list) {
				dvrqstno = savevo.getDvRqstNo();
				rtnCnt += wapWordDvMapper.delWordAutoDiv(savevo);
			}
			
			
			resultMap.put("result", Integer.toString(rtnCnt) );
			resultMap.put("dvrqstno", dvrqstno);
			
			return resultMap;
		}

		@Override
		public Map<String, String> regWordAutoDiv(List<WapWordDv> list) throws Exception {
			Map<String, String> resultMap =  new HashMap<String, String>();
			
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			
			String dvrqstno = objectIdGnrService.getNextStringId();
			
			int rtnCnt = 0;
			List<WapWordDv> result = komoran(list);

			logger.debug(""+result.size());
			for(WapWordDv vo : result) {
				vo.setDvRqstNo(dvrqstno);
				vo.setDvRqstUserId(userid);
				rtnCnt += saveWordDvRqst(vo);
				logger.debug(vo.toString());
			}
			
			resultMap.put("result", Integer.toString(rtnCnt) );
	    	resultMap.put("dvrqstno", dvrqstno);
			return resultMap;
		}
		
		public int saveWordDvRqst(WapWordDv saveVo) {
			return wapWordDvMapper.insertSelective(saveVo);
		}
		
		public List<WapWordDv> komoran(List<WapWordDv> contents) throws Exception {
			Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
	        KomoranResult analyzeResult = null;
	        String method = "setDivWord";
	        
	        for(WapWordDv word : contents) {
        		logger.debug("regWord:{}",word.getRegWord());
        		analyzeResult = komoran.analyze(word.getRegWord());
	        	int idx = 0;
	        	for(Token t : analyzeResult.getTokenList()) {
	        		String morph = t.getMorph();
	        		logger.debug("morph:{}",morph);
	        		String methodName = method + ++idx;
	        		Method setIdMethod = WapWordDv.class.getMethod(methodName, String.class);
	                setIdMethod.invoke(word,  morph);
	        	}
	        	
	        }
			return contents;
		}
		public void saveCsv(List<List<Token>> results, List<List<String>> contents, String filePath) throws Exception {
			//출력 스트림 생성
	        BufferedWriter bufWriter = null;
	        try{
	            bufWriter = Files.newBufferedWriter(Paths.get(filePath),Charset.forName("UTF-8"));
	            
	            for(List<Token> newLine : results){
	            	HashSet<Token> hashSet = new HashSet<Token>(newLine);
	            	List<Token> distinctLst = new ArrayList<Token>(hashSet);
	            	
//	            	for(int i=0; i<distinctLst.size(); i++) {
//	            		bufWriter.write(distinctLst.get(i).toString());
//	            		bufWriter.write(",");
//	            	}
	            	for(Token data : distinctLst){
	            		List<String> saveLst = new ArrayList<String>();
	                    saveLst.add(data.getMorph());
	                    bufWriter.write(saveLst.toString());
	                    bufWriter.write(",");
	                }
	                /*for(Token data : distinctLst){
	                    bufWriter.write(data.getMorph());
	                    bufWriter.write(",");
	                }*/
	                //추가하기
//	                bufWriter.write("주소");
	                //개행코드추가
	                bufWriter.newLine();
	            }
	        }catch(FileNotFoundException e){
	            e.printStackTrace();
	        }catch(IOException e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(bufWriter != null){
	                    bufWriter.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	        
	        System.out.println("save end");
		}
}
