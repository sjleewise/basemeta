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
import kr.wise.commons.damgmt.db.service.DbRoleAuthService;
import kr.wise.commons.damgmt.db.service.WaaDbPrivilege;
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
public class DbRoleAuthCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DbRoleAuthService dbRoleAuthService;
	
	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaDbPrivileges extends HashMap<String, ArrayList<WaaDbPrivilege>> {}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** Role권한 관리 화면 */
	@RequestMapping("/commons/damgmt/db/dbroleauth_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbroleauth_lst";
	}
		
	/** Role권한 리스트 조회 - IBSheet JSON */
	@RequestMapping("/commons/damgmt/db/getDbRoleAuthList.do")
	@ResponseBody
	public IBSheetListVO<WaaDbPrivilege> getDbRoleList(@ModelAttribute WaaDbPrivilege search) {
		logger.debug("searchVO : {}", search);
		List<WaaDbPrivilege> list = dbRoleAuthService.getDbRoleAuthList(search);
		
		return new IBSheetListVO<WaaDbPrivilege>(list, list.size());
		
	}
		
    /** Role권한 리스트 등록(엑셀업로드 포함) - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/regDbRoleAuthList.do")
	@ResponseBody
	public IBSResultVO<WaaDbPrivilege> SaveSymns(@RequestBody WaaDbPrivileges data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbPrivilege> list = data.get("data");

		int result = dbRoleAuthService.regDbRoleAuthList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaDbPrivilege>(result, resmsg, action);
	}
    
    /** Role권한 리스트 삭제 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/delDbRoleAuthList.do")
	@ResponseBody
	public IBSResultVO<WaaDbPrivilege> delDbRoleList(@RequestBody WaaDbPrivileges data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbPrivilege> list = data.get("data");

		int result = dbRoleAuthService.delDbRoleAuthList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaDbPrivilege>(result, resmsg, action);
	}
    
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상(DB_CONN_TRG_ID) 정보 (Grantor)
		List<CodeListVo> connTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("grantorConnTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("grantorConnTrgDbms", connTrgDbms);

		//스키마 Double Select(IBSheet) (Grantor)
		Map<String, ComboIBSVo> connTrgSchIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("dbSchForDoubleSelectIBS"));
		codeMap.put("grantorConnTrgSchibs",  UtilJson.convertJsonString(connTrgSchIbs));
		
		//진단대상(DB_CONN_TRG_ID) 정보 (Granted to)
		List<CodeListVo> connTrgDbmsGranted   = codeListService.getCodeList("dbConnTrgIdInDbRole");
		codeMap.put("grantedConnTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbmsGranted)));
		codeMap.put("grantedConnTrgDbms", connTrgDbmsGranted);
		
		//ROLE명 Double Select(IBSheet) (Granted To)
		Map<String, ComboIBSVo> RoleNmIbs = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("roleNmForDoubleSelectIBS"));
		codeMap.put("grantedRoleNmibs",  UtilJson.convertJsonString(RoleNmIbs));
		
		
		//진단대상, 스키마 Double Select
		String connTrgSch 		= UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		//진단대상, Role명 Double Select
		String dbRoleNm 		= UtilJson.convertJsonString(codeListService.getCodeList("dbRoleNm"));
		codeMap.put("dbRoleNm", dbRoleNm);
		
		
		//공통 코드(요청구분 코드리스트)

		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		
		

		return codeMap;
	}
}
