/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDomainCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 25. 오전 12:15:33
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 25. :            : 신규 개발.
 */
package kr.wise.dq.stnd.web;

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
import kr.wise.dq.stnd.service.WdqStndDomainService;
import kr.wise.dq.stnd.service.WdqmCdVal;
import kr.wise.dq.stnd.service.WdqmDmn;
import kr.wise.dq.stnd.service.WdqmStwdCnfg;
import kr.wise.dq.stnd.service.WdqmWhereUsed;

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
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 25. 오전 12:15:33
 * </PRE>
 */
@Controller
public class WdqStndDomainCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private WdqStndDomainService WdqStndDomainService;
	
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
	@RequestMapping("/dq/stnd/getDomainlist.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> getDomainList(@ModelAttribute WdqmDmn data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqmDmn> list = WdqStndDomainService.getDomainList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmDmn>(list, list.size());

	}
	
	/** 코드도메인 리스트 조회 */
	@RequestMapping("/dq/stnd/getDomainlistWithCdVal.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> getDomainListWithCdVal(@ModelAttribute WdqmDmn data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqmDmn> list = WdqStndDomainService.getDomainListWithCdVal(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmDmn>(list, list.size());

	}

	/**
	 * 표준사전 도메인조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dq/stnd/dmn_lst.do")
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
		return "/dq/stnd/dmn_lst";
	}
	
	/**
	 * 표준사전 코드도메인조회 페이지로 이동한다.
	 * 기술표준원에 맞게 설정되어있음.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dq/stnd/codedmn_lst.do")
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
		return "/dq/stnd/codedmn_lst";
	}

	/** 도메인 팝업 호출.... @return insomnia 
	 * @throws Exception */
	@RequestMapping(value="/dq/stnd/popup/stnddmn_pop.do")
	public String goStndDmnPop(@ModelAttribute("search") WdqmDmn searchvo,  Model model) throws Exception {
		logger.debug("search:{}", searchvo);

		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/dq/stnd/popup/stnddmn_pop";
	}



	/** 도메인 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmninfo_dtl.do")
	public String selectWordInfoDetail(String dmnId, ModelMap model) {
		logger.debug(" {}", dmnId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dmnId)) {

			WdqmDmn result = WdqStndDomainService.selectDomainDetail(dmnId);
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap", getcodeMap());
		return "/dq/stnd/dmninfo_dtl";
	}

	/** 도메인 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmnchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> selectDmnChangeList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmDmn> list = WdqStndDomainService.selectDmnChangeList(dmnId);
		return new IBSheetListVO<WdqmDmn>(list, list.size());
	}

	/** 도메인 구성항목 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmninit_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwdCnfg> selectDmnInitList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmStwdCnfg> list = WdqStndDomainService.selectDmnInitList(dmnId);
		return new IBSheetListVO<WdqmStwdCnfg>(list, list.size());
	}

	/** 도메인 코드값(유효값) 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmnvalue_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> selectDmnValueList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmDmn> list = WdqStndDomainService.selectDmnValueList(dmnId);
		return new IBSheetListVO<WdqmDmn>(list, list.size());
	}
	
	/** 도메인 코드값(유효값) 조회 -IBSheet json */
	@RequestMapping("/dq/stnd/ajaxgrid/dmncdval_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmCdVal> selectDmnCdValList(@ModelAttribute("searchVO") WdqmCdVal searchVO, String dmnId) throws Exception {
		
		logger.debug("{}", searchVO);
		List<WdqmCdVal> list = WdqStndDomainService.selectDmnValueListMsgPop(searchVO);
		return new IBSheetListVO<WdqmCdVal>(list, list.size());
	}

	/** 도메인 코드값(유효값) 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmnvaluechange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> selectDmnValueChangeList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmDmn> list = WdqStndDomainService.selectDmnValueChangeList(dmnId);
		return new IBSheetListVO<WdqmDmn>(list, list.size());
	}

	/** 도메인 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmnwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmWhereUsed> selectDmnWhereUsedList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmWhereUsed> list = WdqStndDomainService.selectDmnWhereUsedList(dmnId);
		return new IBSheetListVO<WdqmWhereUsed>(list, list.size());
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
//		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
//		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));
		
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
//		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
//		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("dataTypeCd", cmcdCodeService.getCodeList("DATA_TYPE"));

		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("cdValIvwCdibs", cdValIvwCd);
//		codeMap.put("cdValTypCdibs", cdValTypCd);
		
		//도메인출처구분코드
//		codeMap.put("dmnOrgDsibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DMN_ORG_DS_CD")));
//		codeMap.put("dmnOrgDs", cmcdCodeService.getCodeList("DMN_ORG_DS_CD"));
		
		//표준구분 이베이코리아
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));

				
		return codeMap;
	}
	
	/**
	 * 단순코드 도메인조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dq/stnd/simplecode_lst.do")
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
		return "/dq/stnd/simplecode_lst";
	}
	
	/** 단순코드 리스트 조회 */
	@RequestMapping("/dq/stnd/getSimpleCodeList.do")
	@ResponseBody
	public IBSheetListVO<WdqmCdVal> getSimpleCodeLst(@ModelAttribute WdqmCdVal data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqmCdVal> list = WdqStndDomainService.getSimpleCodeLst(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmCdVal>(list, list.size());

	}

	
		/**
	 * 복잡코드 조회 페이지로 이동한다.
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/dq/stnd/complexcode_lst.do")
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
		return "/dq/stnd/complexcode_lst";
	}
	
    /** 복잡코드 리스트 조회 */
	@RequestMapping("/dq/stnd/getComplexCodeList.do")
	@ResponseBody
	public IBSheetListVO<WdqmCdVal> getComplexCodeLst(@ModelAttribute WdqmCdVal data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqmCdVal> list = WdqStndDomainService.getComplexCodeLst(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WdqmCdVal>(list, list.size());

	}
	
	/** 도메인 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/dmncomparison_dtl.do")
	@ResponseBody
	public IBSheetListVO<WdqmDmn> selectDmnComparisonList(@ModelAttribute("searchVO") WdqmDmn searchVO, String dmnId) throws Exception {

		logger.debug("{}", dmnId);
		List<WdqmDmn> list = WdqStndDomainService.selectDmnComparisonList(dmnId);
		return new IBSheetListVO<WdqmDmn>(list, list.size());
	}

}
