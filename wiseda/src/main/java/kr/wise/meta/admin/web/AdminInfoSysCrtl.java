/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : AdminInfoSysCrtl.java
 * 2. Package : kr.wise.meta.admin.web
 * 3. Comment : 기관메타 > 관리자 - 정보시스템관리
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.12. :            : 신규 개발.
 */
package kr.wise.meta.admin.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.net.CommNetApiUtil;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.admin.service.AdminInfoSysService;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.admin.service.WaaOrg;

@Controller("AdminInfoSysCrtl")
public class AdminInfoSysCrtl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private AdminInfoSysService adminInfoSysService;
	
	static class WaaInfoSyss extends HashMap<String, ArrayList<WaaInfoSys>> {};
	
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCdValue", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String ssOrgCd = null; 
		
		WaaOrg orgVo = null;
		WaaInfoSys infoSysVo = null;
		
		if(user!=null) {
			ssOrgCd = UtilString.null2Blank(user.getOrgCd());
			
			//사용자 또는 관리자
			if(!UtilString.isBlank(user.getOrgLvlUseYn())) {
				
				orgVo = new WaaOrg();
				orgVo.setOrgCd(ssOrgCd);
				orgVo.setAdminYn(user.getOrgLvlUseYn());
				
				infoSysVo = new WaaInfoSys();
				infoSysVo.setOrgCd(ssOrgCd);
				infoSysVo.setMappingUserId(user.getId());
			}
		}
		
		if(orgVo != null) {
			
			//(null : system관리자[전체코드조회], N:업무담당자[단일기관코드조회], Y:실무담당자[본인기관 상하기관조회]) 
			//List<CodeListVo> orgCdList = codeListService.getOrgCdList(orgVo);
			//codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(orgCdList)));
			//codeMap.put("orgCd", orgCdList);
			
			//List<CodeListVo> infoSysCdUserMapList = codeListService.getInfoSysCdEachListUserMap(infoSysVo);
			//codeMap.put("infoSysCd", infoSysCdUserMapList);
			
		}else {
			codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("orgCd")));
			codeMap.put("orgCd", codeListService.getCodeList("orgCd"));
			
			codeMap.put("infoSysCd", codeListService.getCodeList("infoSysCd"));
		}
		
		/*if(user!=null) {
			ssOrgCd = UtilString.null2Blank(user.getOrgCd());
		}
		
		//기관코드
		if("0000000".equals(ssOrgCd) || "".equals(ssOrgCd)){
			codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("orgCd")));
			codeMap.put("orgCd", codeListService.getCodeList("orgCd"));
			
			codeMap.put("infoSysCd", codeListService.getCodeList("infoSysCd"));
		}else{
			List<CodeListVo> orgCdList = codeListService.getTopOrgCdLowerList(ssOrgCd);
			codeMap.put("orgCdibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(orgCdList)));
			codeMap.put("orgCd", orgCdList);
			
			WaaInfoSys infoSysVo = new WaaInfoSys();
			infoSysVo.setOrgCd(ssOrgCd);
			infoSysVo.setMappingUserId(user.getId());
			List<CodeListVo> infoSysCdUserMapList = codeListService.getInfoSysCdEachListUserMap(infoSysVo);
			codeMap.put("infoSysCd", infoSysCdUserMapList);
		}*/
		
		return codeMap;
	}
	
	/** 정보시스템관리 페이지 호출 */
	@RequestMapping("/meta/admin/infoSysMng_lst.do")
	public String goInfoSysMngList() {
		return "/meta/admin/infoSysMng_lst";
	}
	
	/** 정보시스템 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/admin/getInfoSysList.do")
	@ResponseBody
	public IBSheetListVO<WaaInfoSys> getInfoSysList(@ModelAttribute WaaInfoSys search) throws Exception {
		
		logger.info("/meta/admin/getInfoSysList.do");
		logger.debug("search: {}", search);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String sOrgCd = UtilString.null2Blank(user.getOrgCd());

		/*if(!"0000000".equals(sOrgCd) && !"".equals(sOrgCd)){
			search.setOrgCd(sOrgCd);
		}
		//사용자id셋팅(사용자-정보시스템매핑 검색조건 추가 181218)
		if(user!=null){
			search.setMappingUserId(user.getId());
		}*/
		
		//사용자 또는 관리자
		if(user!=null && !UtilString.isBlank(user.getOrgLvlUseYn())) {
			
			search.setMappingUserId(user.getUniqId());
			
			//선택된 기관코드가 없을 경우 기관코드범위 제한
			if(UtilString.isBlank(search.getOrgCd())) {
				//search.getAdminYn (null : system관리자[전체코드조회], N:업무담당자[단일기관코드조회], Y:실무담당자[본인기관 상하기관조회]) 
				search.setAdminYn(user.getOrgLvlUseYn());
				search.setOrgCd(sOrgCd);
			}
		}
		
		
		List<WaaInfoSys> list = adminInfoSysService.getInfoSysList(search);
		
		return new IBSheetListVO<WaaInfoSys>(list, list.size());
	}
	
	/** 정보시스템 등록  - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/admin/regInfoSysList.do")
	@ResponseBody
	public IBSResultVO<WaaInfoSys> regInfoSysList(@RequestBody WaaInfoSyss data, Locale locale) throws Exception {
		logger.info("/meta/admin/regInfoSysList.do started...");
		logger.debug("#regInfoSysList data:{}", data);
		
		String resultMsg = "";
		ArrayList<WaaInfoSys> list = data.get("data");
		
		int result = adminInfoSysService.regInfoSysList(list);
				
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		}else if(result == -999) {
			//중복된 정보시스템명이 존재합니다.
			result = -1;
			resultMsg = message.getMessage("ERR.DUP.SYS", null, locale);
		}else if(result == -998) {
			//기등록된 정보시스템명이 존재합니다. 정보시스템에 대한 담당자 부여는 본부 실무담당자에게 문의하세요.
			result = -1;
			resultMsg = message.getMessage("ERR.DUP.SYS.NOT.EXIS", null, locale);
			
		} else { 
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<WaaInfoSys>(result, resultMsg, action);
	}
	
	
	@RequestMapping("/meta/admin/popup/adminsysinfo_pop.do")
	public String adminInfoDetail(@ModelAttribute WaaInfoSys data, ModelMap model) {
		if(!"".equals(UtilString.null2Blank(data.getInfoSysCd()))){
			WaaInfoSys result = adminInfoSysService.getInfoSysDetail(data.getOrgCd(), data.getInfoSysCd());
			result.setConstYy(data.getConstYy());
			model.addAttribute("data", result);
		}else{
			model.addAttribute("data", data);
		}
		
		Calendar cal = Calendar.getInstance();
		model.addAttribute("toYear", cal.get(Calendar.YEAR));
		
		return "/meta/admin/popup/adminsysinfo_pop";
	}
	
	
 	
	/** 정보시스템관리 목록 삭제  - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/admin/delInfoSysList.do")
	@ResponseBody
	public IBSResultVO<WaaInfoSys> delInfoSysList(@RequestBody WaaInfoSyss data, Locale locale) throws Exception {
		logger.info("/meta/admin/delInfoSysList.do started...");
		logger.debug("data:{}", data);
		
		String resultMsg = "";
		
		ArrayList<WaaInfoSys> list = data.get("data");
		
		int result = adminInfoSysService.delInfoSysList(list);
				
		if(result > 0) {
			
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
			
		}else {
			
			result = -1;
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		
		return new IBSResultVO<WaaInfoSys>(result, resultMsg, action);
	}

	/**
	 * 정보시스템 API연계 모듈  181019
	 * 
	 * */
	@RequestMapping("/response/infosysdata.do")
	@ResponseBody
	public HashMap<String, Object> responseInfoSysData(@RequestBody HashMap<String, Object> paramMap) throws JsonProcessingException{
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		HashMap<String,String> echomap = new HashMap<String,String>();
		HashMap<String,String> resultmap = new HashMap<String,String>();
		
		WaaInfoSys search = new WaaInfoSys();

		List<WaaInfoSys> list = adminInfoSysService.getInfoSysListApi(paramMap);
		
		if(list!=null&&list.size()>0){
			resultmap.put("CODE", "200");
			resultmap.put("MESSAGE", "정상처리");
		}else{
			resultmap.put("CODE", "210");
			resultmap.put("MESSAGE", "결과값 미존재");
		}
		
		returnMap.put("LIST", list);		
		returnMap.put("RESULT", resultmap);
		
//		   “ECHO”:{
//			"INF_DCD" : "SMS",
//			"INF_FROM" : "E",
//			"TGT_SYS_CD" : "A18052900012",
//			"MESSAGE" : "장애고장"
//		},
//		   “RESULT”:{
//		     
//		        “CODE”:”200”,
//		        “MESSAGE”:”승인 요청 정상적으로 등록되었습니다”
//		         }
//
		
		
		return returnMap;
		
	}
}
