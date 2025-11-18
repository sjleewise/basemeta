package kr.wise.meta.stts.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.mapping.service.WamTblMap;
import kr.wise.meta.stts.service.StndSttsService;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.portal.dashboard.service.StandardChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("StndSttsCtrl")
public class StndSttsCtrl {

	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Inject
	private StndSttsService stndSttsServcie;
	
	@Inject
	private MessageSource message;
	
	private Map<String, String> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService; 
	
	@Inject
	private CodeListService codelistService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("codeMap")
	public Map<String, String> getcodeMap() {

		codeMap = new HashMap<String, String>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");
		List<CodeListVo> sysarea = codelistService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(sysarea)));
				
		//공통코드
		String lecyDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("LECY_DCD"));
		codeMap.put("lecyDcdibs", lecyDcd);

		String dbmsTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCdibs);

		//등록유형코드
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCdibs);
				
		return codeMap;
	}
	
	
    /** 표준현황조회 페이지 이동 */
	@RequestMapping("/meta/stts/stndstts_lst.do")
	public String goTgtTblMapPop(@ModelAttribute("search") WamTblMap search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/stts/stndstts_lst";
	}
	
	/**주제영역 표준비표준 그래프*/
	@RequestMapping("/meta/stts/stndsttsgrph.do")
	@ResponseBody
	public List<StandardChartVO> standardChartListJson()throws Exception{

		List<StandardChartVO> list = stndSttsServcie.selectSttsStandardChart() ;

		return list;
	}
	
	/** 메타용 주제영역 리스트 조회 */
	/** @param search
	/** @param locale
	/** @return meta 
	 * @throws Exception */
	@RequestMapping("/meta/stts/getMetaSubjList.do")
	@ResponseBody
	public IBSheetListVO<WaaSubj> getMetaSubjList(@ModelAttribute WaaSubj search, Locale locale) throws Exception {

		logger.debug("{}", search);
		
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = stndSttsServcie.selectStsInfoList("SUBJ");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
		
		search.setLvlList(lvlList);
		
		List<WaaSubj> list = stndSttsServcie.getMetaStsSubjList(search);

		return new IBSheetListVO<WaaSubj>(list, list.size());

	}
	
	
	
	
}
