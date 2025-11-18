/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierDetectCtrl.java
 * 2. Package : kr.wise.advisor.prepare.outlier.web
 * 3. Comment : 테이상값 탐지 컨트롤러...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:21:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.outlier.service.OutScatterData;
import kr.wise.advisor.prepare.outlier.service.OutlierService;
import kr.wise.advisor.prepare.outlier.service.WadOtlChartData;
import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : OutlierDetectCtrl
 * 2. FileName  : OutlierDetectCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.web
 * 4. Comment  : 이상값 탐지 컨트롤러...
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class OutlierDetectCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;

	/*@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;*/
	
	@Inject
	private OutlierService outlierService;
	
	@Inject
	private DataSetService dataSetService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	//사용자정의 유효값
	static class WadOtlDtcs extends HashMap<String, ArrayList<WadOtlDtc>> { }
	static class WadAnaVars extends HashMap<String, ArrayList<WadAnaVar>> { }
	static class WadOtlDatas extends HashMap<String, ArrayList<WadOtlData>> { }
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/advisor/prepare/outlier/deloutlier.do")
	@ResponseBody
	public IBSResultVO<WadOtlDtc> delOutlierDetection(WadOtlDtc search, Locale locale) throws Exception {
		logger.debug("이상값탐지 삭제 by otlid:{}", search.getOtlDtcId());
		
		int result = 0;
		
		result = outlierService.delOutlierDetection(search);
		
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		return new IBSResultVO<WadOtlDtc>(result, resmsg, action);
	}
	
	/** 테이블 summary by DataSet...
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/outlier/regoutlier.do")
	@ResponseBody
	public IBSResultVO<WadOtlDtc> regOutlierDetection(@RequestBody WadAnaVars data, WadOtlDtc search, Locale locale) throws Exception {
		logger.debug("이상값탐지 등록 by DataSet:{}", search);
		
		int result = 0;
		
		List<WadAnaVar> list = data.get("data");
		
		//데이터셋 id 체크 후 
		if (StringUtils.hasText(search.getDaseId())) {
			result = outlierService.regOutlierDetection(search, list);
//			outlierService.requestOutlier(search);
		} else {
			result = -999; //데이터셋 id 미존재....
		}
		
		
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "저장을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터셋이 없습니다.<br>데이터셋을 등록 후 이상값 탐지가 가능합니다.";
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
//			resmsg = "이상값 탐지 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		return new IBSResultVO<WadOtlDtc>(search, result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/outlier/getoutlierbyds.do")
	@ResponseBody
	public IBSheetListVO<WadOtlResult> getOutlierResult(WadOtlDtc search) {
		logger.debug("이상값결과 조회[탐지ID:{}]", search.getOtlDtcId());
		
		List<WadOtlResult> list =  outlierService.getOutlierResult(search);
		
		return new IBSheetListVO<WadOtlResult>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/outlier/getAlgVarList.do")
	@ResponseBody
	public IBSheetListVO<WadAnaVar> getAlgVal(WadOtlDtc search) {
		logger.debug("이상값탐지대상 컬럼 조회[탐지ID:{}]", search.getOtlDtcId());
		
		List<WadAnaVar> list =  outlierService.getAlgVarList(search);
		
		return new IBSheetListVO<WadAnaVar>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/outlier/getOtlVarList.do")
	@ResponseBody
	public IBSheetListVO<WadAnaVar> getOtlVal(WadOtlDtc search) {
		logger.debug("이상값탐지대상 컬럼 조회[탐지ID:{}]", search.getOtlDtcId());
		
		List<WadAnaVar> list =  outlierService.getOtlVarList(search);
		
		return new IBSheetListVO<WadAnaVar>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/outlier/getOutData.do")
	@ResponseBody
	public IBSheetListVO<WadOtlData> getOutData(WadOtlDtc search) throws Exception {
		logger.debug("이상값탐지 결과 데이터 리스트[탐지ID:{}]", search.getOtlDtcId());
		
//		List<WadOtlData> collist = outlierService.getOutDataColList(search);
		
		List<WadOtlData> list =  outlierService.getOutDataList(search);
		
		return new IBSheetListVO<WadOtlData>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/outlier/getOutDataOne.do")
	@ResponseBody
	public IBSheetListVO<WadOtlData> getOutDataOne(WadOtlDtc search, DataPatternVO pattvo) throws Exception {
		logger.debug("이상값탐지 결과 데이터 단변량[{}]", search);
		
		List<WadOtlData> list =  outlierService.getOutDataListOne(search, pattvo);
		
		return new IBSheetListVO<WadOtlData>(list, list.size());
	}

	@RequestMapping("/advisor/prepare/outlier/getOutDataMulti.do")
	@ResponseBody
	public IBSheetListVO<WadOtlData> getOutDataMulti(DataPatternVO pattvo) throws Exception {
		logger.debug("이상값탐지 결과 데이터 리스트[탐지ID:{}]", pattvo.getObjId());
		
		List<WadOtlData> list =  outlierService.getOutDataMulti(pattvo);
		Map<String, String> etcmap = new HashMap<String, String>();
		String colnms = "";
		//첫번째 row는 컬럼명 -> 컬럼명|컬럼명 형태로 메시지에 포함해서 보낸다.....
		if (!CollectionUtils.isEmpty(list)) {
			WadOtlData outdata = list.get(0);
			int cntcol = outdata.getColCnt();
			
			//컬럼카운터 수만큼 컬럼목록을 만든다.
			for(int i=0;i<cntcol;i++) {
				Method colGetter = outdata.getClass().getMethod("getColNm"+(i+1));
				String colnm = colGetter.invoke(outdata).toString();
				if (i == 0) 
					colnms += colnm;
				else 
					colnms += "|" + colnm;
			}
			etcmap.put("colnms", colnms);
			list.remove(0);
			
		}
		
		return new IBSheetListVO<WadOtlData>(list, list.size(), etcmap);
	}
	
	@RequestMapping("/advisor/prepare/outlier/getOutData2.do")
	@ResponseBody
	public IBSheetListVO<WadOtlData> getOutData2(WadOtlDtc search) throws Exception {
		logger.debug("이상값탐지 결과 데이터 리스트[탐지ID:{}]", search.getOtlDtcId());
		
//		List<WadOtlData> collist = outlierService.getOutDataColList(search);
		
		List<WadOtlData> list =  outlierService.getOutDataList2(search);
		Map<String, String> etcmap = new HashMap<String, String>();
		String colnms = "";
		//첫번째 row는 컬럼명 -> 컬럼명|컬럼명 형태로 메시지에 포함해서 보낸다.....
		if (!CollectionUtils.isEmpty(list)) {
			WadOtlData outdata = list.get(0);
			int cntcol = outdata.getColCnt();
			
			//컬럼카운터 수만큼 컬럼목록을 만든다.
			for(int i=0;i<cntcol;i++) {
				Method colGetter = outdata.getClass().getMethod("getColNm"+(i+1));
				String colnm = colGetter.invoke(outdata).toString();
				if (i == 0) 
					colnms += colnm;
				else 
					colnms += "|" + colnm;
			}
			etcmap.put("colnms", colnms);
			list.remove(0);
			
		}
		
		return new IBSheetListVO<WadOtlData>(list, list.size(), etcmap);
	}

	@RequestMapping("/advisor/prepare/outlier/getchartdata.do")
	@ResponseBody
	public List<WadOtlChartData> getOutChartData(WadOtlDtc search) {
		logger.debug("이상값탐지 차트데이트 리스트[탐지ID:{}]", search.getOtlDtcId());
		
		List<WadOtlChartData> list =  outlierService.getOutChartData(search);
		
		return list;
	}

	@RequestMapping("/advisor/prepare/outlier/getscatterdata.do")
	@ResponseBody
	public OutScatterData getOutScatterData(WadOtlDtc search) {
		logger.debug("이상값탐지 scatter 차트데이트 리스트[탐지ID:{}]", search.getOtlDtcId());
		
		OutScatterData result =  outlierService.getOutScatterData(search);
		
		return result;
	}
	
/*	@RequestMapping("/advisor/prepare/summary/reghistogram.do")
	@ResponseBody
	public IBSResultVO<WadAnaVar> regHistogram(WadAnaVar search) {
		logger.debug("변수 히스토그램 저장 by 변수ID:{}", search);
		
		int result = 0;
		
		//변수 id 체크 후 
		if (StringUtils.hasText(search.getAnlVarId())) {
			result = summaryService.regHistobyVar(search);
		} else {
			result = -999; //데이터셋 id 미존재....
		}
		
		String resmsg;

		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "histogram 저장을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터셋이 없습니다.<br>데이터셋을 등록 후 histogram 조회가 가능합니다.";
		}
		else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "histogram 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		
		return new IBSResultVO<WadAnaVar>(search, result, resmsg, action);
	}*/
	
	@RequestMapping("/advisor/prepare/outlier/updateOtlYn.do")
	@ResponseBody
	public IBSResultVO<CommonVo> updateOtlYn(@RequestBody WadOtlDatas data, Locale locale) throws Exception {
		logger.debug("이상값 여부 수정사항 저장 by 컬럼리스트");
		
		List<WadOtlData> list = data.get("data");
		
		int result = outlierService.updateOtlYn(list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
//			resmsg = "도메인판별을 완료했습니다.";
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
//			resmsg = "도메인판별 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/outlier/updateOtlRpl.do")
	@ResponseBody
	public IBSResultVO<CommonVo> updateOtlRpl(WadOtlData data, Locale locale) throws Exception {
		logger.debug("이상값 여부 수정사항 저장 by 컬럼리스트");
		
		//List<WadOtlData> list = data.get("data");
		
		int result = outlierService.updateOtlRpl(data);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
//			resmsg = "도메인판별을 완료했습니다.";
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
//			resmsg = "도메인판별 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}
}
