package kr.wise.meta.stnd.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * <PRE>
 * 1. ClassName : StndMainCtrl
 * 2. FileName  : StndMainCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : hokim(김연호)
 * 6. 작성일   : 2014. 3. 25. 오전 10:01:51
 * </PRE>
 */
@Controller("StndMainCtrl")
@RequestMapping("/meta/stnd/*")
public class StndMainCtrl{

	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 4. 17.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng")));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp")));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));

		//공통 코드(요청구분 코드리스트)
		codeMap.put("bizdtlcd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD")));
		codeMap.put("bizdtlcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD")));
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regtypcd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD")));
		codeMap.put("regTypCd", UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD")));
		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));
		codeMap.put("wdDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD")));
		codeMap.put("dataTypeCd", cmcdCodeService.getCodeList("DATA_TYPE"));
		
		//표준구분 멀티사전용
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));
		
		//범정부연계항목
		//비공개사유
		codeMap.put("nopenRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NOPEN_RSN_CD")));
		codeMap.put("nopenRsnCd", cmcdCodeService.getCodeList("NOPEN_RSN_CD"));
				
		return codeMap;
	}

	/**
	 * 표준사전 표준전체조회 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping(value="stnd_lst.do")
	public String goStndList(HttpSession session, @RequestParam(value="stndNm", required=false) String stndNm, Model model) throws Exception  {
		logger.debug("{}", stndNm);
		
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}
				
		model.addAttribute("stndNm", stndNm);
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/stnd_lst";
	}

	/**
	 * 표준사전 표준단어조회 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping(value="stwd_lst.do")
	public String goStndStwdList(HttpSession session, @RequestParam(value="action",  required=false) String action, @RequestParam(value="objId", required=false) String stwdId, String linkFlag, Model model) {
		logger.debug("{}", action);
		logger.debug("linkFlag : {}",linkFlag);
		
		model.addAttribute("action", action);
		model.addAttribute("stwdId", stwdId);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/stwd_lst";
	}

	@RequestMapping(value="stwd_ifm.do")
	public String goStndStwdTop30(HttpSession session,Model model) {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/stwd_ifm";
	}

	/**
	 * 표준사전 변경이력 페이지로 이동한다.
	 * @return
	 */
	@RequestMapping(value="althistory_lst.do")
	public String goStndAltHistoryList(HttpSession session, @RequestParam(value="action",  required=false) String action, Model model) {
		logger.debug("{}", action);
		model.addAttribute("action", action);
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/althistory_lst";
	}

	@RequestMapping(value="althistory_ifm.do")
	public String goAltHistoryTop30(HttpSession session, Model model) {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/althistory_ifm";
	}


	
	@RequestMapping(value="dmn_ifm.do")
	public String goStndDmnTop30(HttpSession session,  Model model) {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/dmn_ifm";
	}

	/**
	 * 표준사전 표준항목조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="sditm_lst.do")
	public String goStndSditmList(HttpSession session, @RequestParam(value="objId", required=false) String sditmId, String linkFlag, Model model) throws Exception {
		logger.debug("objId 조회 {}", sditmId);
		logger.debug("linkFlag : {}", linkFlag);
		model.addAttribute("sditmId", sditmId);
		model.addAttribute("linkFlag", linkFlag);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/sditm_lst";
	}
	
	/**
	 * 표준사전 비표준항목조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="notsditm_lst.do")
	public String goNotStndSditmList(HttpSession session, @RequestParam(value="objId", required=false) String sditmId, String linkFlag, Model model) throws Exception {
		logger.debug("objId 조회 {}", sditmId);
		logger.debug("linkFlag : {}", linkFlag);
		model.addAttribute("sditmId", sditmId);
		model.addAttribute("linkFlag", linkFlag);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/notsditm_lst";
	}

	@RequestMapping(value="sditm_ifm.do")
	public String goStndSditmTop30(HttpSession session, Model model) {
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/sditm_ifm";
	}
}