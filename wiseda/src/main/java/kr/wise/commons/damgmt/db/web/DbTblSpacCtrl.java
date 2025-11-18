/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbTblSpacCtrl.java
 * 2. Package : kr.wise.commons.damgmt.db.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 12. 오후 3:13:59
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 12. :            : 신규 개발.
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
import kr.wise.commons.damgmt.db.service.DbTblSpacService;
import kr.wise.commons.damgmt.db.service.WaaTblSpac;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbTblSpacCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.db.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 12. 오후 3:13:59
 * </PRE>
 */
@Controller
public class DbTblSpacCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DbTblSpacService dbTblSpacService;
	
	@Inject
	private MessageSource message;

	private Map<String, Object> codeMap;

	static class WaaTblSpacs extends HashMap<String, ArrayList<WaaTblSpac>> {}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 테이블스페이스 관리 화면 */
	@RequestMapping("/commons/damgmt/db/dbtblspac_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbtblspac_lst";
	}
	
	/** 테이블스페이스 상세 정보 조회 */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/ajaxgrid/dbtblspac_dtl.do")
	public String selectDbTblSpacDetail(String dbTblSpacId, String saction, ModelMap model) {
		logger.debug(" {}", dbTblSpacId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbTblSpacId)) {

			WaaTblSpac result = dbTblSpacService.selectDbTblSpacDetail(dbTblSpacId);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/db/dbtblspac_dtl";
	}
	
	/** 테이블스페이스 리스트 조회 - IBSheet JSON */
	@RequestMapping("/commons/damgmt/db/getDbTblSpacList.do")
	@ResponseBody
	public IBSheetListVO<WaaTblSpac> getDbTblSpacList(@ModelAttribute WaaTblSpac search) {
		logger.debug("searchVO : {}", search);
		List<WaaTblSpac> list = dbTblSpacService.getDbTblSpacList(search);
		
		return new IBSheetListVO<WaaTblSpac>(list, list.size());
		
	}
	
	/** 테이블스페이스 단건 저장 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/commons/damgmt/db/regDbTblSpac.do")
    @ResponseBody
    public IBSResultVO<WaaTblSpac> regDbTblSpac(WaaTblSpac saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = dbTblSpacService.regDbTblSpac(saveVO);

    	String resmsg ;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaTblSpac>(saveVO, result, resmsg, action);
    }
	
    /** 테이블스페이스 리스트 등록(엑셀업로드용) - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/regDbTblSpacList.do")
	@ResponseBody
	public IBSResultVO<WaaTblSpac> SaveSymns(@RequestBody WaaTblSpacs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaTblSpac> list = data.get("data");

		int result = dbTblSpacService.regDbTblSpacList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return new IBSResultVO<WaaTblSpac>(result, resmsg, action);
	}
    
    /** 테이블스페이스 리스트 삭제 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/delDbTblSpacList.do")
	@ResponseBody
	public IBSResultVO<WaaTblSpac> delDbTblSpacList(@RequestBody WaaTblSpacs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaTblSpac> list = data.get("data");

		int result = dbTblSpacService.delDbTblSpacList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaTblSpac>(result, resmsg, action);
	}
    
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상(DB_CONN_TRG_ID) 정보
		List<CodeListVo> connTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbms", connTrgDbms);

		List<CodeListVo> dbSch   = codeListService.getCodeList("dbSchLnm");
		codeMap.put("dbSchibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSch)));
		

	    //진단대상, 스키마 Double Select
		String connTrgSch 		= UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		
		
		
		//공통 코드(요청구분 코드리스트)

		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		String tblSpacTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_SPAC_TYP_CD"));
		codeMap.put("tblSpacTypCd", cmcdCodeService.getCodeList("TBL_SPAC_TYP_CD"));
		codeMap.put("tblSpacTypCdibs", tblSpacTypCd);
		
		
		

		return codeMap;
	}
}
