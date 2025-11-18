/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbRoleAuthCtrl.java
 * 2. Package : kr.wise.commons.damgmt.db.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 13. 오후 1:06:34
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 13. :            : 신규 개발.
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
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.db.service.DbSynonymAuthService;
import kr.wise.commons.damgmt.db.service.WaaDbSynonym;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbRoleAuthCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.db.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 13. 오후 1:06:34
 * </PRE>
 */
@Controller
public class DbSynonymAuthCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DbSynonymAuthService dbSynonymAuthService;
	
	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaDbSynonyms extends HashMap<String, ArrayList<WaaDbSynonym>> {}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** Synonym권한 관리 화면 */
	@RequestMapping("/commons/damgmt/db/dbsynonymauth_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbsynonymauth_lst";
	}
		
	/** Synonym권한 리스트 조회 - IBSheet JSON */
	@RequestMapping("/commons/damgmt/db/getDbSynonymAuthLst.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSynonym> getDbSynonymList(@ModelAttribute WaaDbSynonym search) {
		logger.debug("searchVO : {}", search);
		List<WaaDbSynonym> list = dbSynonymAuthService.getDbSynonymAuthList(search);
		
		return new IBSheetListVO<WaaDbSynonym>(list, list.size());
		
	}
		
    /** Role권한 리스트 등록(엑셀업로드 포함) - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/regDbSynonymAuthLst.do")
	@ResponseBody
	public IBSResultVO<WaaDbSynonym> SaveSymns(@RequestBody WaaDbSynonyms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbSynonym> list = data.get("data");

		int result = dbSynonymAuthService.regDbSynonymAuthList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaDbSynonym>(result, resmsg, action);
	}
    
    /** Role권한 리스트 삭제 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/delDbSynonymAuthLst.do")
	@ResponseBody
	public IBSResultVO<WaaDbSynonym> delDbSynonymList(@RequestBody WaaDbSynonyms data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbSynonym> list = data.get("data");

		int result = dbSynonymAuthService.delDbSynonymAuthList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaDbSynonym>(result, resmsg, action);
	}
    
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//소스대상DDB(DB_CONN_TRG_ID) 정보 (Source)
		List<CodeListVo> srcConnTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("srcDbConnTrgIdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(srcConnTrgDbms)));
		codeMap.put("srcDbConnTrgId", srcConnTrgDbms);

		//스키마 Double Select(IBSheet) (Source)
		Map<String, ComboIBSVo> srcDbSchId = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("srcDbSchIdibs",  UtilJson.convertJsonString(srcDbSchId));
		
		//타겟대상(DB_CONN_TRG_ID) 정보 (Target)
		List<CodeListVo> trgConnTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("trgDbConnTrgIdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(trgConnTrgDbms)));
		codeMap.put("trgDbConnTrgId", trgConnTrgDbms);
		
		//스키마 Double Select(IBSheet) (Target)
		Map<String, ComboIBSVo> trgDbSchId = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("trgDbSchIdibs",  UtilJson.convertJsonString(trgDbSchId));
		
		
		//진단대상, 스키마 Double Select
		String srcConnTrgSch 		= UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("srcDbConnTrgSchId", srcConnTrgSch);
		
		//진단대상, Role명 Double Select
		String trgConnTrgSch 		= UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("trgDbConnTrgSchId", trgConnTrgSch);
		
		
		//공통 코드(요청구분 코드리스트)

		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		
		

		return codeMap;
	}
}
