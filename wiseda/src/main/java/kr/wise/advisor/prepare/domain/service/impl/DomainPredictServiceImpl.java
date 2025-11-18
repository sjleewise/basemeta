/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DomainPredictServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.domain.service.impl
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:36:31
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarMapper;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;

import kr.wise.advisor.prepare.domain.service.DomainPredictService;
import kr.wise.advisor.prepare.domain.service.WadDmnPdt;
import kr.wise.advisor.prepare.domain.service.WadDmnPdtMapper;
import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.advisor.prepare.summary.service.WadVarSumMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import scala.collection.Iterator;
import scala.collection.Seq;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DomainPredictServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.domain.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:36:31
 * </PRE>
 */
@Service("dmnPredictService")
public class DomainPredictServiceImpl implements DomainPredictService {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private DataSetService dataSetService;
	
	@Inject
	private WadAnaVarMapper varmapper;
	
	@Inject
	private WadDmnPdtMapper mapper;
	
	@Inject
	private WadVarSumMapper wadVarSumMapper;

	/** insomnia 
	 * @throws Exception */
	public int requestDmnPredict(List<AnaTrgTblVO> list) throws Exception {
		int result = -1;
		String rqstNo = null;
		//데이터셋을 등록한다. 없으면 신규, 있으면 업데이트...
		List<WadDataSet> dslist  = dataSetService.regDataSet(list);
		if (dslist != null && !dslist.isEmpty()) rqstNo = dslist.get(0).getRqstNo();
		
		List<WadAnaVar> varlist = dataSetService.getAnaVar(dslist);
		
		
		//rqst_no 채번
//		String reqid = requestIdGnrService.getNextStringId();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//컬럼한글명을 형태소분석해서 마지막을 가져온다...
		for (WadAnaVar varvo : varlist) {
			 varvo.setWritUserId(userid);
			
			//컬럼한글명 형태소 분석후 마지막 단어 셋팅
			String varlnm = varvo.getColMrpLnm();
			if (StringUtils.hasText(varlnm)) {
				varvo.setColMrpLnm(processMrp(varlnm));
			}
			//컬럼영문명 '_' 기준으로 분리후 마지막 단어 셋팅
			String varpnm = varvo.getColMrpPnm();
			if (StringUtils.hasText(varpnm)) {
				String[] splstr = StringUtils.tokenizeToStringArray(varpnm, "_");
				if (splstr.length > 0) varvo.setColMrpPnm(splstr[splstr.length-1]);
				else varvo.setColMrpPnm(null);
				logger.debug("컬럼영문명 분리 _ :{}->{}", varpnm, varvo.getColMrpPnm());
			}
			
			//result =+ regAnaVarRow(varvo);
		}
		
		result = dataSetService.regAnaVar(varlist);
		
		//도메인판별 파생변수 업데이트
		result = regAnaVarFeature(rqstNo);
		
		//도메인판별 요청
//		callDmnPredictGrpc(rqstNo);
		
		
		list.get(0).setRqstNo(rqstNo);
		
		return result;
	}
	
	/** @param rqstNo
	/** @return insomnia */
	private int regAnaVarFeature(String rqstNo) {
		int result = 0;
		
		//데이터타입 매핑 업데이트....
//		result =+ varmapper.updateDataTypeMap(rqstNo);
		//소수점여부, 숫자여부, 일자여부, 길이변경여부, 데이터길이초과여부(200) 
		result =+ varmapper.updatefloatYn(rqstNo);
//		result =+ varmapper.updatenumYn(rqstNo);
//		result =+ varmapper.updatedateYn(rqstNo);
		result =+ varmapper.updatelenchgYn(rqstNo);
//		result =+ varmapper.updatelenexcYn(rqstNo);
		
		
		return result;
	}

	/** 한글 형태소 분석 후 마지막 단어 리턴
	/** @return insomnia */
	private String processMrp(String varlnm) {
		String resstr = null;
		
		// Normalize
	    CharSequence normalized = TwitterKoreanProcessorJava.normalize(varlnm);
	    // 한국어를 처리하는 예시입니다ㅋㅋ #한국어

	    // Tokenize
	    Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
	    
	    List<KoreanTokenJava> tokenlist = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(tokens);
	    for (KoreanTokenJava koreanTokenJava : tokenlist) {
			//System.out.println(koreanTokenJava.getText()+":"+koreanTokenJava.getPos());
	    	String pos =  koreanTokenJava.getPos().toString();
		      if(!"Space".equals(pos) && !"KoreanParticle".equals(pos) && !"Punctuation".equals(pos))
		    	  resstr = koreanTokenJava.getText();
		}
	    
	    /*Iterator<KoreanToken> tokenized = tokens.iterator();
	    while (tokenized.hasNext()) {
	      KoreanToken token = tokenized.next();
	     String pos =  token.pos().toString();
	      if(!"Space".equals(pos) && !"KoreanParticle".equals(pos) && !"Punctuation".equals(pos))
	    	  resstr = token.text();
	    }*/
	    
//	    List<KoreanPhraseExtractor.KoreanPhrase> phrases = TwitterKoreanProcessorJava.extractPhrases(tokens, false, false);
//	    
//	    if (phrases.size() > 0 ) resstr = phrases.get(phrases.size()-1).text();
		
	    logger.debug("한글형태소분석:{}->{}", varlnm, resstr);
	    
		return resstr;
	}

	/** insomnia */
	public int regDmnResult(List<WadAnaVar> list) {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for (WadAnaVar savevo : list) {
			savevo.setWritUserId(userid);
			result =+ varmapper.updateDmnResult(savevo);
			
			/*WadVarSum wadVarSum = new WadVarSum();
			wadVarSum.setAnlVarId(savevo.getAnlVarId());
			
			String dmngNm = savevo.getDmngNm();
			
			if(dmngNm.equals("코드") || dmngNm.equals("플래그") || dmngNm.equals("날짜")) {
				wadVarSum.setVarType("categorical");
			}
			result += wadVarSumMapper.updateByPrimaryKeySelective(wadVarSum);*/
		}
		
		return result;
	}

	/** insomnia */
	public List<WadDmnPdt> getDmnPredictResultbyId(WadDmnPdt search) {
		logger.debug("도메인판별결과 조회 by id:{}", search.getAnlVarId());
		
		return mapper.selectListbyId(search);
	}

	/** insomnia */
	/*
	public int callDmnPredictGrpc(String rqstNo) {
		logger.debug("도메인판별 grpc 호출  by rqstNo:{}", rqstNo);
		int result = 0;
		
		String host = message.getMessage("wiseda.grpc.host.python", null, Locale.getDefault()).trim();
		String port = message.getMessage("wiseda.grpc.port.python", null, Locale.getDefault()).trim();
		
		
		//rqstNo에 해당하는 타겟DB정보 및 스키마 정보 조회....
		List<WaaDbConnTrgVO> volist = dataSetService.getDataSetListbyrqstNo(rqstNo);
		for (WaaDbConnTrgVO dbvo : volist) {
			DmnPredictClient client = new DmnPredictClient(host, Integer.parseInt(port));
//			DmnPredictClient client = new DmnPredictClient("192.168.0.18", 50051);
			//결과 리스트를 가져온다.
			List<DmnPredictResult> reslist = client.CallDmnPredictbydb(dbvo);
			
			for (DmnPredictResult resvo : reslist) {
				
				String anlVarId = resvo.getAnlVarId();
				List<Float> dmnPrbList = resvo.getDmnPrbList();
				
				result =+ regDmnPdt(anlVarId, dmnPrbList);
				
			}
		}
		
		
		return result;
	}
	*/

	/** @param anlVarId
	/** @param dmnPrbList
	/** @return insomnia */
	private int regDmnPdt(String anlVarId, List<Float> dmnPrbList) {
		logger.debug("도메인판별결과저장:{}", anlVarId);
		int result = 0;
		String dmns = "ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그";
		
		dmns = message.getMessage("wiseda.dmnpdt.list", null, Locale.getDefault());
		
		String[] arrdmn = StringUtils.tokenizeToStringArray(dmns, "|");
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
			//변수ID에 해당하는 결과 삭제
			mapper.deleteByanlVarId(anlVarId);
			int cnt = 0;
			for (Float dmnPrb : dmnPrbList) {
				WadDmnPdt savevo = new WadDmnPdt();
				savevo.setAnlVarId(anlVarId);
				savevo.setDmnPrb(dmnPrb);
				savevo.setDmngLnm(arrdmn[cnt]);
				savevo.setWritUserId(userid);
				
				result =+ mapper.insertSelective(savevo);
				cnt++;
			}
		
		return result;
	}
	
	
	/** insomnia 
	 * @throws Exception */
	public String requestSumDaseVar(List<AnaTrgTblVO> list) throws Exception {
		int result = -1;
		String rqstNo = null;
		//데이터셋을 등록한다. 없으면 신규, 있으면 업데이트...
		List<WadDataSet> dslist  = dataSetService.regDataSet(list);
		if (dslist != null && !dslist.isEmpty()) rqstNo = dslist.get(0).getRqstNo();
		
		List<WadAnaVar> varlist = dataSetService.getAnaVar(dslist);
		
		
		//rqst_no 채번
//		String reqid = requestIdGnrService.getNextStringId();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//컬럼한글명을 형태소분석해서 마지막을 가져온다...
		for (WadAnaVar varvo : varlist) {
			 varvo.setWritUserId(userid);
			
			//컬럼한글명 형태소 분석후 마지막 단어 셋팅
			String varlnm = varvo.getColMrpLnm();
			if (StringUtils.hasText(varlnm)) {
				varvo.setColMrpLnm(processMrp(varlnm));
			}
			//컬럼영문명 '_' 기준으로 분리후 마지막 단어 셋팅
			String varpnm = varvo.getColMrpPnm();
			if (StringUtils.hasText(varpnm)) {
				String[] splstr = StringUtils.tokenizeToStringArray(varpnm, "_");
				if (splstr.length > 0) varvo.setColMrpPnm(splstr[splstr.length-1]);
				else varvo.setColMrpPnm(null);
				logger.debug("컬럼영문명 분리 _ :{}->{}", varpnm, varvo.getColMrpPnm());
			}
			
			//result =+ regAnaVarRow(varvo);
		}
		
		result = dataSetService.regAnaVar(varlist);
		
		//도메인판별 파생변수 업데이트
		result = regAnaVarFeature(rqstNo);
		
		//도메인판별 요청
//		callDmnPredictGrpc(rqstNo);
		
		
		String daseId = dslist.get(0).getDaseId();
		
		return daseId;
	}

	@Override
	public int callDmnPredictGrpc(String rqstNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
