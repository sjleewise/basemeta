/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbmsMappingCtrl.java
 * 2. Package : kr.wise.commons.damgmt.db.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 8. 오전 11:11:21
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.DbmsMappingService;
import kr.wise.commons.damgmt.db.service.WaaDbMap;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DbmsMappingCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.db.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 8. 오전 11:11:21
 * </PRE>
 */
@Controller
public class DbmsMappingCtrl {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	static class WaaDbMaps extends HashMap<String, ArrayList<WaaDbMap>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private DbmsMappingService dbmsMappingService;

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("ddlTrgDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DDL_TRG_DCD")));

		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//스키마 정보(1 select box 용)
		List<CodeListVo> dbSch = codeListService.getCodeList("dbSch"); 
//		String dbSchibs = UtilJson.convertJsonString(codeListService.getCodeListIBS("dbSch"));
		
		codeMap.put("dbSch", UtilJson.convertJsonString(dbSch));
		codeMap.put("dbSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSch)));
		
//		logger.debug("dbSch{}", dbSch);
		
		return codeMap;
	}

	/** DB 매핑 뷰 @return insomnia */
	@RequestMapping("/commons/damgmt/db/dbmap.do")
	public String godbmap() {


		return "/commons/damgmt/db/dbmap_lst";
	}

	/** DB매핑 팝업 */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/popup/dbmap_pop.do")
	public String goDbMapPop(@ModelAttribute("search") WaaDbMap search, String sFlag, Model model, Locale locale) {
		logger.debug("search:{}", search);
		logger.debug("sFlag : {}", sFlag);
		model.addAttribute("sFlag", sFlag);

		return "/commons/damgmt/db/popup/dbmap_pop";
	}
	
	
	@RequestMapping("/commons/damgmt/db/getdbmaplist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbMap> getDbMapList(WaaDbMap search) {
		logger.debug("search:{}", search);

		List<WaaDbMap> list = dbmsMappingService.getDbMapList(search);
		
		logger.debug("list:{}", list);

		return new IBSheetListVO<WaaDbMap>(list, list.size());
	}
	
	/** DB 매핑 저장 @return 유성열 */
	@RequestMapping("/commons/damgmt/db/dbMapSaveList.do")
	@ResponseBody
	public IBSResultVO<WaaDbMap> regDbMapList(@RequestBody WaaDbMaps data, Locale locale)  throws Exception {
		
		logger.debug("{}", data);
		ArrayList<WaaDbMap> list = data.get("data");

		int result = dbmsMappingService.regDbMapList(list);
		String resmsg;
		
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			if(result == -9){
				result = -1;
				resmsg = message.getMessage("ERR.DUP", null, locale);
			}else{
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			
		}		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbMap>(result, resmsg, action);
	}
	
	/** DB 매핑 삭제 */
	@RequestMapping("/commons/damgmt/db/dbMapDelList.do")
	@ResponseBody
	public IBSResultVO<WaaDbMap> delDbMapList(@RequestBody WaaDbMaps data, Locale locale) {
		
		logger.debug("{}", data);
		ArrayList<WaaDbMap> list = data.get("data");

		int result = dbmsMappingService.delDbMapList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<WaaDbMap>(result, resmsg, action);
	}

}
