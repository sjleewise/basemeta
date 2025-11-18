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
package kr.wise.advisor.prepare.udefoutlier.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.service.UdefOutlierService;
import kr.wise.advisor.prepare.udefoutlier.service.WadErrData;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UdefOutlierCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService; 
	
	@Inject
    private EgovIdGnrService uodIdGnrService;  
	
	@Inject
	private UdefOutlierService udefOutlierService;
	
	@Inject
	private PythonScriptService pythonScriptService; 
	
	static class WadErrDatas extends HashMap<String, ArrayList<WadErrData>> { }
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		String python_script_path  =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+ "python.script.dir", null, Locale.getDefault());
		codeMap.put("python_script_path", python_script_path);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSchHive   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchIdHive"));
		codeMap.put("connTrgSchHive", connTrgSchHive);
				
		return codeMap;
	}
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 사용자정의 이상값 탐지 요청화면 
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/udefoutlier_lst.do")
	public String goUdefOutlier(@ModelAttribute("search") CommonVo search) {
		logger.debug("이상값 탐지 요청화면:{}", search);
		
		return "/advisor/prepare/udefoutlier/udefoutlier_lst";
	}

	/** 사용자정의 이상값 탐지 요청화면 
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/udefoutlier/udefoutlier_rqst.do")
	public String goUdefOutlierRqst(@ModelAttribute("search") CommonVo search, WadUod uodVo, ModelMap model) throws Exception {
		logger.debug("이상값 탐지 요청화면:{}", search);
		
		logger.debug("\n udfOtlDtcId:" + uodVo.getUdfOtlDtcId());		
		
		if (!StringUtils.hasText(uodVo.getUdfOtlDtcId())){ 
			
			String udfOtlDtcId = uodIdGnrService.getNextStringId(); 
						
			uodVo.setUdfOtlDtcId(udfOtlDtcId);			
			
			uodVo.setIbsStatus("C");
		}else{
			
			uodVo = udefOutlierService.getWadUodSelectDetail(uodVo);
			
			uodVo.setIbsStatus("U");						
		}
		
		model.addAttribute("wadUod", uodVo); 
		
		return "/advisor/prepare/udefoutlier/udefoutlier_rqst";
	}

	@RequestMapping("/advisor/prepare/udefoutlier/getUdefOutlierList.do")
	@ResponseBody
	public IBSheetListVO<WadUod> getUdefOutlierList(WadUod search) {
		
		List<WadUod> list =  udefOutlierService.getUdefOutlierList(search);  
		
		return new IBSheetListVO<WadUod>(list, list.size()); 
	}
	
	static class WadUods extends HashMap<String, ArrayList<WadUod>>{};   
	
	@RequestMapping("/advisor/prepare/udefoutlier/delUdfOtlDtcList.do")
	@ResponseBody
	public IBSResultVO<WadUodcDaseImp> delUdfOtlDtcList(@RequestBody WadUods data, Locale locale) throws Exception { 
		
		//logger.debug("division:{}", data);
		int result = 0;
		
    	List<WadUod> list = data.get("data"); 
    	
    	//데이터임포트 등록 
    	result = udefOutlierService.delUdfOtlDtcList(list);  
    
    	String resmsg = "";
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

    	return new IBSResultVO<WadUodcDaseImp>(result, resmsg, action);
	}
	

	@RequestMapping("/advisor/prepare/udefoutlier/getAnaDaseId.do")
	@ResponseBody
	public IBSheetListVO<WadUodcAnaDase> getAnaDaseId(WadUodcAnaDase search) {  
		
		List<WadUodcAnaDase> list =  udefOutlierService.getAnaDaseId(search);  
		
		return new IBSheetListVO<WadUodcAnaDase>(list, list.size()); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/getAnaDaseIdSaveres.do")
	@ResponseBody
	public IBSheetListVO<WadUodcAnaDase> getAnaDaseIdSaveres(WadUodcAnaDase search) {  
		
		List<WadUodcAnaDase> list =  udefOutlierService.getAnaDaseIdSaveres(search);  
		
		return new IBSheetListVO<WadUodcAnaDase>(list, list.size()); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/script/scriptFileCre.do")
	@ResponseBody
	public IBSResultVO<WadUod> getUdefOutlierScript(@RequestBody WadUods data, Locale locale) throws Exception {  
		
		int result = 0 ;
		
    	List<WadUod> list = data.get("data"); 
    	
    	for(WadUod scrtVo : list) {
    		
    		String udfOtlDtcId = UtilString.null2Blank(scrtVo.getUdfOtlDtcId()) ; 
    	
    		result = pythonScriptService.scriptFileCre(udfOtlDtcId);
    	} 	
    	
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUod>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/delComp.do")
	@ResponseBody
	public IBSResultVO<WadUod> delComp(WadUodCreComp param, Locale locale) {
		
		int result = udefOutlierService.delComp(param);
		
		String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUod>(result, resmsg, action); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/updUod.do")
	@ResponseBody
	public IBSResultVO<WadUod> updUod(WadUod data, Locale locale) {
		int result = 0 ;
		
		result = udefOutlierService.updUod(data);
		
		String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUod>(result, resmsg, action); 
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/insertLink.do")
	@ResponseBody
	public int insertLink(String udfOtlDtcId, Locale locale) throws Exception {
		WadUod result = pythonScriptService.insertLinkInfo(udfOtlDtcId);
		
		if(result != null)	return 1;
		else				return 0;
	}
	
	/** 파이선 스크립트 팜업  
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/popup/python_scrt.do") 
	public String goPythonScrt(@ModelAttribute("search") WadUod search) {
		logger.debug("파이선 스크립트 팝업  :{}", search);
		
		return "/advisor/prepare/udefoutlier/popup/python_scrt";
	}
	
	/** 결과보기 팝업  
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/udefoutlier/popup/result_view.do") 
	public String goResultView(@ModelAttribute("search") WadErrData search, Model model, Locale locale) throws Exception {
		logger.debug("DataPatternVO:{}", search);
		//logger.debug("WadOtlResult:{}", resvo);

		int headerCnt = 0;
		String headerText = new String();
		
		List<WadErrData> otldatalist = udefOutlierService.getResultViewColNm(search);

		if( null != otldatalist && !otldatalist.isEmpty()){
			WadErrData otldatavo = otldatalist.get(0);
			
			headerCnt = otldatavo.getColCnt()  ;

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;

			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);

					Method colGetter = otldatavo.getClass().getMethod("getColNm"+i);

					tmpColNm.append(colGetter.invoke(otldatavo).toString()+"|");
				}
				//IBSHEET 헤더텍스트 조회
				headerText = tmpColNm.substring(0, tmpColNm.length()-1);
			}

			search.setColNm(colNm.toString());
			
		}
		
		
		search.setColCnt(headerCnt);
		search.setHeaderText(headerText);

		
		model.addAttribute("headerVO", search);
		model.addAttribute("colist", otldatalist);
		
		return "/advisor/prepare/udefoutlier/popup/result_view";
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/getResultData.do")
	@ResponseBody
	public IBSheetListVO<WadErrData> getResultData(WadErrData search) throws Exception {
		logger.debug("사용자 정의 이상값탐지 결과 보기[{}]", search);
		
		List<WadErrData> list =  udefOutlierService.getResultData(search);
		
		return new IBSheetListVO<WadErrData>(list, list.size());
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/script/scriptFileCreOne.do")
	@ResponseBody
	public IBSResultVO<WadUod> getUdefOutlierScriptOne(WadUod data, Locale locale) throws Exception {  
		logger.debug("getUdefOutlierScriptOne data >>> " + data);
		
		int result = 0 ;
		
		String udfOtlDtcId = UtilString.null2Blank(data.getUdfOtlDtcId()) ; 
	
		result = pythonScriptService.scriptFileCre(udfOtlDtcId);
    	
    	String resmsg;
    	
    	if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	return new IBSResultVO<WadUod>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/udefoutlier/updateOtlYn.do")
	@ResponseBody
	public IBSResultVO<CommonVo> updateOtlYn(@RequestBody WadErrDatas data, Locale locale) throws Exception {
		logger.debug("이상값 여부 수정사항 저장 by 컬럼리스트");
		
		List<WadErrData> list = data.get("data");
		
		int result = udefOutlierService.updateOtlYn(list);
		
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
	
	@RequestMapping("/advisor/prepare/udefoutlier/updateOtlRpl.do")
	@ResponseBody
	public IBSResultVO<CommonVo> updateOtlRpl(WadErrData data, Locale locale) throws Exception {
		logger.debug("이상값 여부 수정사항 저장 by 컬럼리스트");
		
		int result = udefOutlierService.updateOtlRpl(data);
		
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
