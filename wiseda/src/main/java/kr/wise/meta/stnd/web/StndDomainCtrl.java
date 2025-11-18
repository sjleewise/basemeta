/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDomainCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 25. 오전 12:15:33
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.dmnginfo.service.DmngService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.StndDomainService;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamDmn;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdCnfg;
import kr.wise.meta.stnd.service.WamWhereUsed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDomainCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 25. 오전 12:15:33
 * </PRE>
 */
@Controller
public class StndDomainCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private StndDomainService stndDomainService;
	
	@Inject
	private DmngService dmngService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** 도메인 리스트 조회 */
	@RequestMapping("/meta/stnd/getDomainlist.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> getDomainList(@ModelAttribute WamDmn data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WamDmn> list = stndDomainService.getDomainList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDmn>(list, list.size());

	}
	
	/** 코드도메인 리스트 조회 */
	@RequestMapping("/meta/stnd/getDomainlistWithCdVal.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> getDomainListWithCdVal(@ModelAttribute WamDmn data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WamDmn> list = stndDomainService.getDomainListWithCdVal(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDmn>(list, list.size());

	}

	/**
	 * 표준사전 도메인조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/meta/stnd/dmn_lst.do")
	public String goStndDmnList(HttpSession session, @RequestParam(value="objId", required=false) String dmnId,  String linkFlag,Model model) throws Exception {
		logger.debug("objId 조회 {}", dmnId);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("dmnId", dmnId);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/dmn_lst";
	}
	
	/**
	 * 표준사전 코드도메인조회 페이지로 이동한다.
	 * 기술표준원에 맞게 설정되어있음.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/meta/stnd/codedmn_lst.do")
	public String goStndCodeDmnList(HttpSession session, Model model) throws Exception {

		//도메인그룹이 코드인 도메인 ID를 조회하여 모델에 추가한다.
		WaaDmng dmngVO = dmngService.getDmngId("코드");
		if (dmngVO != null) {
			model.addAttribute("codeDmngId", dmngVO.getDmngId());
		}
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/codedmn_lst";
	}

	/** 도메인 팝업 호출.... @return insomnia 
	 * @throws Exception */
	@RequestMapping(value="/meta/stnd/popup/stnddmn_pop.do")
	public String goStndDmnPop(@ModelAttribute("search") WamDmn searchvo,  Model model) throws Exception {
		logger.debug("search:{}", searchvo);

		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/popup/stnddmn_pop";
	}



	/** 도메인 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmninfo_dtl.do")
	public String selectWordInfoDetail(String dmnId, ModelMap model) {
		logger.debug(" {}", dmnId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dmnId)) {
			String govYn = "0";
			
			govYn = stndDomainService.selectGovYn(dmnId);

			WamDmn result = null;
			
			if(govYn.equals("1")) {
				result = stndDomainService.selectGovDomainDetail(dmnId);
			} else {
				result = stndDomainService.selectDomainDetail(dmnId);
			}
			
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/dmninfo_dtl";
	}

	/** 도메인 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmnchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> selectDmnChangeList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamDmn> list = stndDomainService.selectDmnChangeList(dmnId);
		return new IBSheetListVO<WamDmn>(list, list.size());
	}

	/** 도메인 구성항목 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmninit_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamStwdCnfg> selectDmnInitList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamStwdCnfg> list = stndDomainService.selectDmnInitList(dmnId);
		return new IBSheetListVO<WamStwdCnfg>(list, list.size());
	}

	/** 도메인 코드값(유효값) 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmnvalue_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> selectDmnValueList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		
		String govYn = "0";
		
		govYn = stndDomainService.selectGovYn(dmnId);

		List<WamDmn> list = null;
		
		if(govYn.equals("1")) {
			list = stndDomainService.selectGovDmnValueList(dmnId);
		} else {
			list = stndDomainService.selectDmnValueList(dmnId);
		}

		return new IBSheetListVO<WamDmn>(list, list.size());
	}
	
	/** 도메인 코드값(유효값) 조회 -IBSheet json */
	@RequestMapping("/meta/stnd/ajaxgrid/dmncdval_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamCdVal> selectDmnCdValList(@ModelAttribute("searchVO") WamCdVal searchVO, String dmnId) throws Exception {
		
		logger.debug("{}", searchVO);
		List<WamCdVal> list = stndDomainService.selectDmnValueListMsgPop(searchVO);
		return new IBSheetListVO<WamCdVal>(list, list.size());
	}

	/** 도메인 코드값(유효값) 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmnvaluechange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> selectDmnValueChangeList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamDmn> list = stndDomainService.selectDmnValueChangeList(dmnId);
		return new IBSheetListVO<WamDmn>(list, list.size());
	}

	/** 도메인 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmnwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamWhereUsed> selectDmnWhereUsedList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamWhereUsed> list = stndDomainService.selectDmnWhereUsedList(dmnId);
		return new IBSheetListVO<WamWhereUsed>(list, list.size());
	}


//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> infotp = codeListService.getCodeList("infotp");

		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng")));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(infotp)));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));
		codeMap.put("infotpId", infotp);

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));

		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("dataTypeCd", cmcdCodeService.getCodeList("DATA_TYPE"));

		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("cdValIvwCdibs", cdValIvwCd);
		codeMap.put("cdValTypCdibs", cdValTypCd);
		
		//도메인출처구분코드
		codeMap.put("dmnOrgDsibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DMN_ORG_DS_CD")));
		codeMap.put("dmnOrgDs", cmcdCodeService.getCodeList("DMN_ORG_DS_CD"));
		
		//표준구분 이베이코리아
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

				
		return codeMap;
	}
	
	/**
	 * 단순코드 도메인조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/meta/stnd/simplecode_lst.do")
	public String goSimpleCodeList(HttpSession session, @RequestParam(value="objId", required=false) String dmnId,  String linkFlag,Model model) throws Exception {
		logger.debug("objId 조회 {}", dmnId);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("dmnId", dmnId);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/simplecode_lst";
	}
	
	/** 단순코드 리스트 조회 */
	@RequestMapping("/meta/stnd/getSimpleCodeList.do")
	@ResponseBody
	public IBSheetListVO<WamCdVal> getSimpleCodeLst(@ModelAttribute WamCdVal data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WamCdVal> list = stndDomainService.getSimpleCodeLst(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamCdVal>(list, list.size());

	}

	
		/**
	 * 복잡코드 조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/meta/stnd/complexcode_lst.do")
	public String goComplexCodeList(HttpSession session, @RequestParam(value="objId", required=false) String dmnId,  String linkFlag,Model model) throws Exception {
		logger.debug("objId 조회 {}", dmnId);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("dmnId", dmnId);
		
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		
		model.addAttribute("codeMap", getcodeMap());
		return "/meta/stnd/complexcode_lst";
	}
	
    /** 복잡코드 리스트 조회 */
	@RequestMapping("/meta/stnd/getComplexCodeList.do")
	@ResponseBody
	public IBSheetListVO<WamCdVal> getComplexCodeLst(@ModelAttribute WamCdVal data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WamCdVal> list = stndDomainService.getComplexCodeLst(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamCdVal>(list, list.size());

	}
	
	/** 도메인 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/stnd/ajaxgrid/dmncomparison_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDmn> selectDmnComparisonList(@ModelAttribute("searchVO") WamDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WamDmn> list = stndDomainService.selectDmnComparisonList(dmnId);
		return new IBSheetListVO<WamDmn>(list, list.size());
	}

}
