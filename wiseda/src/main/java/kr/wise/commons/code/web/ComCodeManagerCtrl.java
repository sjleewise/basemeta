/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ComCodeManagerCtrl.java
 * 2. Package : kr.wise.commons.code.web
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2014. 3. 17. 오전 8:56:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 17. :            : 신규 개발.
 */
package kr.wise.commons.code.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeManagerService;
import kr.wise.commons.code.service.WaaCommDcd;
import kr.wise.commons.code.service.WaaCommDtlCd;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
 * 1. ClassName :
 * 2. FileName  : ComCodeManagerCtrl.java
 * 3. Package  : kr.wise.commons.code.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 17. 오전 8:56:13
 * </PRE>
 */
@Controller
@Aspect
public class ComCodeManagerCtrl {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeManagerService codeManagerService;

	@Inject
	private MessageSource message;


	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	/** 공통상세코드 JSON 리스트를 매핑할 리스트 */
	static class WaaCommDtlCds extends HashMap<String, ArrayList<WaaCommDtlCd>> { }

	/** 공통코드 JSON 리스트를 매핑할 리스트 */
	static class WaaCommDcds extends HashMap<String, ArrayList<WaaCommDcd>> { }

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON IBSheet용
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String commDcdIdibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(WiseMetaConfig.CodeListAction.COM_DCD));

		//코드리스트 List selectBox용
//		String usergrouplnm2 = UtilJson.convertJsonString(codelistService.getCodeList("userglnm"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("commDcdIdibs", commDcdIdibs);
		codeMap.put("regTypCdibs", regTypCd);


		//공통코드 - selectbox 용
//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD"));


		return codeMap;
	}


	@RequestMapping("/commons/code/getComCodeList.do")
	@ResponseBody
	public Map<String, Object> getCodeList( String codenm, String type) {

		logger.debug("codenm:{}, type:{}", codenm, type);

		Map<String, Object> codelist = new HashMap<String, Object>();

		if ("LI".equals(type)) {
			codelist.put(codenm, codeListService.getCodeListIBS(codenm));
		} else if ("L".equals(type)) {
			codelist.put(codenm, codeListService.getCodeList(codenm));
		} else if ("CI".equals(type)) {
			codelist.put(codenm, cmcdCodeService.getCodeListIBS(codenm));
		} else if ("C".equals(type)) {
			codelist.put(codenm, cmcdCodeService.getCodeList(codenm));
		}

		return codelist;
	}


	/** 공통코드 관리 화면 @return insomnia */
	@RequestMapping("/commons/code/codemanager.do")
	public String codemanager(@ModelAttribute("searchVO") WaaCommDcd searchvo) {
		return "/commons/code/codemanage_lst";
	}

	/** 공통코드 관리 화면- 테스트용... @return insomnia */
	@RequestMapping("/commons/code/codedtlmanager.do")
	public String dtlcodemanager(@ModelAttribute("searchVO") WaaCommDcd searchvo) {
		return "/commons/code/codedtlmanage_lst";
	}

	/** 공통코드 리스트 조회 - IBSheet JSON  @param searchvo
	/** @return insomnia */
	@RequestMapping("/commons/code/getcodelist.do")
	@ResponseBody
	public IBSheetListVO<WaaCommDcd> getCodeList(@ModelAttribute WaaCommDcd searchvo) {

		logger.debug("{}", searchvo);

		List<WaaCommDcd> list = codeManagerService.getcodelist(searchvo);


		return new IBSheetListVO<WaaCommDcd>(list, list.size());
	}


	/** 상세코드 리스트 조회 - IBSheet JSON  @param searchvo
	/** @return insomnia */
	@RequestMapping("/commons/code/getcodeDtllist.do")
	@ResponseBody
	public IBSheetListVO<WaaCommDtlCd> getCodeDtlList( WaaCommDcd searchvo) {

		logger.debug("searchvo:{}", searchvo);

		List<WaaCommDtlCd> list = codeManagerService.getcodeDtllist(searchvo);


		return new IBSheetListVO<WaaCommDtlCd>(list, list.size());
	}

	/** 상세코드 리스트 저장 공통코드 동시저장- IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/commons/code/regDtlCodelist.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> regCodeDtlListWithCommDcd(@RequestBody WaaCommDtlCds data,  @ModelAttribute WaaCommDcd saveVO, Locale locale) throws Exception {

		logger.debug("data:{}, saveVO:{}", data, saveVO);
		ArrayList<WaaCommDtlCd> list = data.get("data");

		int result = codeManagerService.regCodeDtlList(list, saveVO);
		
		cmcdCodeService.init();
		
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

		return new IBSResultVO<WaaCommDcd>(saveVO, result, resmsg, action);
	}

	/** 상세코드 리스트 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/commons/code/regDtlCodelistWithout.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> regCodeDtlList(@RequestBody WaaCommDtlCds data, Locale locale) throws Exception {

		logger.debug("data:{}", data);
		ArrayList<WaaCommDtlCd> list = data.get("data");

		int result = codeManagerService.regCodeDtlListWithout(list);
		
		cmcdCodeService.init();
		
		String resmsg ;
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaCommDcd>(result, resmsg, action);
	}

	/** 상세코드 리스트 엑셀 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/commons/code/regDtlCodeListXls.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> regDtlCodeListXls(@RequestBody WaaCommDtlCds data, Locale locale) throws Exception {

		logger.debug("data:{}", data);
		ArrayList<WaaCommDtlCd> list = data.get("data");

		int result = codeManagerService.regDtlCodeList(list);
		
		cmcdCodeService.init();
		
		String resmsg ;
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaCommDcd>(result, resmsg, action);
	}

	/** 공통코드 상세조회 폼 @return insomnia */
	@RequestMapping("/commons/code/ajaxgrid/selectCodeDetail.do")
    public String getCodeDetail(WaaCommDcd searchVO, String saction,  ModelMap model) {

    	logger.debug("searvhVO : {}", searchVO);

    	model.addAttribute("saction", "I");

    	if( StringUtils.hasLength(searchVO.getCommDcdId()) ){

    		WaaCommDcd resultVO = codeManagerService.selectCodeDetail(searchVO);
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

//    	return "/commons/sys/program/programDetail";
    	return "/commons/code/codemanage_dtl";
    }

	/** 공통코드 단건 저장 - ajax 이용 @return insomnia
	 * @throws Exception */
	@RequestMapping("/commons/code/saveCodeRow.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> saveCodeRow(WaaCommDcd saveVO, String saction, Locale locale) throws Exception {
		logger.debug("saveVO:{}", saveVO);

		int result  = codeManagerService.saveCode(saveVO);
		
		cmcdCodeService.init();

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<WaaCommDcd>(saveVO, result, resmsg, action);
	}

	/** 공통코드 리스트 삭제 - 공통코드에 포함되는 상세코드도 같이 삭제 @throws Exception insomnia */
	@RequestMapping("/commons/code/delCodelist.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> delCodeList(@RequestBody WaaCommDcds data, Locale locale) throws Exception {

		List<WaaCommDcd> list = data.get("data");
		logger.debug("delVO:{}", list);

		int result  = codeManagerService.delCodeList(list);
		
		cmcdCodeService.init();
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaCommDcd>(result, resmsg, action);

	}


	/** 공통코드 리스트 엑셀등록 @throws Exception insomnia */
	@RequestMapping("/commons/code/regCodeList.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> regCodeList(@RequestBody WaaCommDcds data, Locale locale) throws Exception {

		List<WaaCommDcd> list = data.get("data");
		logger.debug("delVO:{}", list);

		int result  = codeManagerService.regCodeList(list);
		
		cmcdCodeService.init();
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaCommDcd>(result, resmsg, action);

	}


	/** 상세코드 리스트 삭제 @throws Exception insomnia */
	@RequestMapping("/commons/code/delDtlCodelist.do")
	@ResponseBody
	public IBSResultVO<WaaCommDtlCd> delDtlCodeList(@RequestBody WaaCommDtlCds data, Locale locale) throws Exception {

		List<WaaCommDtlCd> list = data.get("data");
		logger.debug("delVO:{}", list);

		int result  = codeManagerService.delDtlCodeList(list);
		
		cmcdCodeService.init();
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL_DTL.getAction();

		return new IBSResultVO<WaaCommDtlCd>(result, resmsg, action); 

	}

	/** 공통코드 엑셀업로드 팝업호출 @return insomnia */
	@RequestMapping("/commons/code/popup/commdcd_xls.do")
	public String commdcdform() {

		return "/commons/code/popup/commdcd_xls";
	}


	/** 공통상세코드 엑셀업로드 팝업호출 @return insomnia */
	@RequestMapping("/commons/code/popup/commdtlcd_xls.do")
	public String commdtlcdform() {

		return "/commons/code/popup/commdtlcd_xls";
	}
	
	/** 공통코드 리로드 @throws Exception */
	@RequestMapping("/commons/code/reloadCommonCode.do")
	@ResponseBody
	public IBSResultVO<WaaCommDcd> reloadCommonCode(Locale locale) throws Exception {

		String resmsg;
		int result = 0; 
		
		cmcdCodeService.init();
		
		resmsg = "reload 성공!";

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaCommDcd>(result, resmsg, action);

	}
}
