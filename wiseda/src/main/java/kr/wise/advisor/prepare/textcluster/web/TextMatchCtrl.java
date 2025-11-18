/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : TextMatchCtrl.java
 * 2. Package : kr.wise.advisor.prepare.textcluster.web
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 8. 오후 4:58:26
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 8. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.textcluster.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.textcluster.service.TextMatchService;
import kr.wise.advisor.prepare.textcluster.service.WadClstData;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.advisor.prepare.textcluster.service.WadMtcData;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : TextMatchCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 4:58:26
 * </PRE>
 */
@Controller
public class TextMatchCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;

	/*@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;*/
	
	@Inject
	private TextMatchService textMatchService;
	
	//사용자정의 유효값
	static class WadAnaVars extends HashMap<String, ArrayList<WadAnaVar>> { }
	static class WadDataMtcColVars extends HashMap<String, ArrayList<WadDataMtcCol>> { }
	static class WadClstDatas extends HashMap<String, ArrayList<WadClstData>> { }
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/advisor/prepare/textcluster/getMatchTblList.do")
	@ResponseBody
	public IBSheetListVO<WadDataMtcTbl> getMatchTblList(WadDataMtcTbl search) {
		logger.debug("데이터매칭테이블리스트 조회:{}", search);
		
		List<WadDataMtcTbl> list =  textMatchService.getMatchTbl(search);
		
		return new IBSheetListVO<WadDataMtcTbl>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/textcluster/getMatchColList.do")
	@ResponseBody
	public IBSheetListVO<WadDataMtcCol> getMatchColList(WadDataMtcTbl search) {
		logger.debug("데이터매칭 컬럼리스트 조회 by 매칭ID:{}", search.getMtcId());
		
		List<WadDataMtcCol> list =  textMatchService.getMatchCol(search);
		
		return new IBSheetListVO<WadDataMtcCol>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/textcluster/getmatchdata.do")
	@ResponseBody
	public IBSheetListVO<WadMtcData> getMatchData(WadDataMtcTbl search) {
		logger.debug("데이터매칭 결과데이터 조회 by 매칭ID:{}", search.getMtcId());
		logger.debug("데이터매칭 결과데이터 조회 by getiPageSize:{}", search.getiPageSize());
		
		List<WadMtcData> list =  textMatchService.getMatchData(search);
		
		return new IBSheetListVO<WadMtcData>(list, list.size());
	}
	@RequestMapping("/advisor/prepare/textcluster/getclustdata.do")
	@ResponseBody
	public IBSheetListVO<WadClstData> getClusterData(WadAnaVar search) {
		logger.debug("텍스트 클러스터링 결과데이터 조회 by 컬럼ID:{}", search.getAnlVarId());
		
		List<WadClstData> list =  textMatchService.getClstData(search);
		
		return new IBSheetListVO<WadClstData>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/textcluster/getmatchtgtdata.do")
	@ResponseBody
	public IBSheetListVO<WadMtcData> getMatchTgtData(WadMtcData search) {
		logger.debug("데이터매칭 내용별 순위 조회 :{}", search);
		
		List<WadMtcData> list =  textMatchService.getMatchtgtData(search);
		
		return new IBSheetListVO<WadMtcData>(list, list.size());
	}
	
	
	@RequestMapping("/advisor/prepare/textcluster/execdatamatch.do")
	@ResponseBody
	public IBSResultVO<WadDataMtcTbl> requestTextMatch(WadDataMtcTbl search, Locale locale) throws Exception {
		logger.debug("데이터매칭 실행 by 매칭ID :{}", search.getMtcId());
		
		int result = 0;
		
		//데이터셋 id 체크 후 
		if (StringUtils.hasText(search.getMtcId())) {
			result = textMatchService.requestTextMatch(search);
		} else {
			result = -999; //데이터셋 id 미존재....
		}
		
		
		String resmsg;

		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "데이터매칭 실행을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 정보가 없습니다..<br>데이터매칭 목록에서 선택후 실행해 주십시요.";
		} else if (result == -10) {
			result = -1;
	//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭할 컬럼정보가 없습니다.<br>컬럼을 추가 후 실행해 주십시요.";
		} else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.SUBMIT.getAction();

		return new IBSResultVO<WadDataMtcTbl>(search, result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/textcluster/requestTextClust.do")
	@ResponseBody
	public IBSResultVO<WadDataSet> requestTextCluster(@RequestBody WadAnaVars data, WadDataSet dataset, Locale locale) throws Exception {
		logger.debug("텍스트클러스터링 실행 by 컬럼리스트:{}", dataset.getDbSchId());
		
		List<WadAnaVar> list = data.get("data");
		
		int result = 0;
		
		result = textMatchService.requestTextCluster(dataset, list);
		
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "데이터매칭 실행을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 정보가 없습니다..<br>데이터매칭 목록에서 선택후 실행해 주십시요.";
		} else if (result == -10) {
			result = -1;
			//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭할 컬럼정보가 없습니다.<br>컬럼을 추가 후 실행해 주십시요.";
		} else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.SUBMIT.getAction();
		
		return new IBSResultVO<WadDataSet>(dataset, result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/textcluster/regSchTextCluster.do")
	@ResponseBody
	public IBSResultVO<WadDataSet> regSchTextCluster(@RequestBody WadAnaVars data, WadDataSet dataset, Locale locale) throws Exception {
		logger.debug("텍스트클러스터링 실행 by 컬럼리스트:{}", dataset.getDbSchId());
		
		List<WadAnaVar> list = data.get("data");
		
		int result = 0;
		
		result = textMatchService.requestTextCluster(dataset, list);
		
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "데이터매칭 실행을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 정보가 없습니다..<br>데이터매칭 목록에서 선택후 실행해 주십시요.";
		} else if (result == -10) {
			result = -1;
			//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭할 컬럼정보가 없습니다.<br>컬럼을 추가 후 실행해 주십시요.";
		} else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.SUBMIT.getAction();
		
		return new IBSResultVO<WadDataSet>(dataset, result, resmsg, action);
	}
	
	
	@RequestMapping("/advisor/prepare/textcluster/insertWadMtcInf.do")
	@ResponseBody
	public IBSResultVO<WadDataMtcTbl> insertWadMtcInf(@RequestBody WadDataMtcColVars data, WadDataMtcTbl dataset, Locale locale) throws Exception {
		logger.debug("텍스트클러스터링 실행 by 컬럼리스트:{}", dataset.getDbSchId());
		
		List<WadDataMtcCol> list = data.get("data"); 
		
		int result = 0;
		
		result = textMatchService.regTextMatchInf(dataset, list); 
				
		String resmsg; 
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction(); 
		
		return new IBSResultVO<WadDataMtcTbl>(dataset, result, resmsg, action); 
	}
	
	@RequestMapping("/advisor/prepare/textcluster/getTblList.do")
	@ResponseBody
	public List<WadDataMtcTbl> getTblList(WadDataMtcTbl search) {
		logger.debug("테이블리스트 조회:{}", search);
		
		List<WadDataMtcTbl> list =  textMatchService.getTbl(search);
		
		return list;
	}
	
	@RequestMapping("/advisor/prepare/textcluster/getHiveTblList.do")
	@ResponseBody
	public List<WadDataMtcTbl> getHiveTblList(WadDataMtcTbl search) {
		logger.debug("테이블리스트 조회:{}", search);
		
		List<WadDataMtcTbl> list =  textMatchService.getHiveTbl(search);
		
		return list;
	}
	
	@RequestMapping("/advisor/prepare/textcluster/delmatchdata.do")
	@ResponseBody
	public IBSResultVO<WadDataSet> delMatchData(@RequestBody WadDataMtcColVars data, WadDataSet dataset, Locale locale) throws Exception {
		logger.debug("텍스트클러스터링 실행 by 컬럼리스트:{}", dataset.getDbSchId());
		
		List<WadDataMtcCol> list = data.get("data");
		
		int result = 0;
		
		result = textMatchService.delMatchData(dataset, list);
		
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "데이터매칭 삭제를 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 정보가 없습니다..<br>데이터매칭 목록에서 선택후 실행해 주십시요.";
		} else if (result == -10) {
			result = -1;
			//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "삭제할 컬럼정보가 없습니다.";
		} else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터매칭 삭제중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.SUBMIT.getAction();
		
		return new IBSResultVO<WadDataSet>(dataset, result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/textcluster/regTextClust.do")
	@ResponseBody
	public IBSResultVO<WadClstData> regTextClust(@RequestBody WadClstDatas data, Locale locale) throws Exception {
		logger.debug("텍스트클러스터링 실행 by 컬럼리스트:{}", data);
		
		List<WadClstData> list = data.get("data");
		
		int result = 0;
		
		result = textMatchService.regTextClust(list);
		
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "추천용어 저장을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "추천용어 정보가 없습니다..<br>추천용어 목록에서 선택 후 실행해 주십시요.";
		} else if (result == -10) {
			result = -1;
			//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "저장할 컬럼정보가 없습니다.<br>컬럼을 추가 후 실행해 주십시요.";
		} else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "추천용어 저장 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.SUBMIT.getAction();
		
		return new IBSResultVO<WadClstData>(list.get(0), result, resmsg, action);
	}

}
