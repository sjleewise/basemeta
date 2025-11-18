/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeGapCtrl.java
 * 2. Package : kr.wise.meta.gap.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 23. 오후 4:18:39
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 23. :            : 신규 개발.
 */
package kr.wise.meta.gap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.gap.service.CodeGapService;
import kr.wise.meta.gap.service.CodeGapVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeGapCtrl.java
 * 3. Package  : kr.wise.meta.gap.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 23. 오후 4:18:39
 * </PRE>
 */
@Controller
public class CodeGapCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CodeGapService codeGapService;
	
	@Inject
	private InfotpService infotpService;

	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 코드값 GAP분석 조회 페이지 */
	/** @return yeonho */
	@RequestMapping("/meta/gap/codegap_lst.do")
	public String goCodeGapLst() {
		return "/meta/gap/codegap_lst";
	}
	
	/** 코드GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCodeGapAnalyze.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeGapAnalyze(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeGapAnalyze(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	
	/** 코드GAP분석 접속정보DB 코드값 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/selectCodeValList.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeValList(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeValList(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	
	/** 코드GAP분석 메타도메인 코드값 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/selectDmnCdValList.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getDmnCdValList(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getDmnValList(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);

		//코드값부여방식코드
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		
		
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		
		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		List<CodeListVo> dmng 	= codeListService.getCodeList("dmng");

		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		codeMap.put("dmng", UtilJson.convertJsonString(dmng));
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dmng)));
		codeMap.put("infotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp")));
		codeMap.put("dmnginfotpibs", UtilJson.convertJsonString(codeListService.getCodeListIBS("dmnginfotp")));
		codeMap.put("dmnginfotp", UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp")));
		codeMap.put("infotpinfolst", UtilJson.convertJsonString(infotpService.getInfoTypeList()));

		Map<String, ComboIBSVo> infoTpCodeListIBS = codeListService.getDbmsDataTypIBS(codeListService.getCodeList("infoTpCodeListIBS"));
		codeMap.put("infoTpCodeListIBS",  UtilJson.convertJsonString(infoTpCodeListIBS));
		return codeMap;
	}
	
	
	/** 단순코드(메타vs개발)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCodeGapMetaDev.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeGapMetaDev(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeGapMetaDev(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 단순코드(메타vs운영)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCodeGapMetaReal.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeGapMetaReal(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeGapMetaReal(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 단순코드(메타vs이관)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCodeGapMetaTsf.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeGapMetaTsf(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeGapMetaTsf(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 단순코드(개발 vs 운영)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCodeGapDevReal.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCodeGapDevReal(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCodeGapDevReal(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 복잡코드(메타vs개발)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCmpCodeGapMetaDev.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCmpCodeGapMetaDev(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCmpCodeGapMetaDev(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 복잡코드(메타vs운영)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCmpCodeGapMetaReal.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCmpCodeGapMetaReal(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCmpCodeGapMetaReal(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 복잡코드(메타vs메타이관)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCmpCodeGapMetaTsf.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCmpCodeGapMetaTsf(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCmpCodeGapMetaTsf(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
	/** 복잡코드(개발 vs 운영)GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getCmpCodeGapDevReal.do")
	@ResponseBody
	public IBSheetListVO<CodeGapVO> getCmpCodeGapDevReal(@ModelAttribute CodeGapVO search) {
		logger.debug("{}", search);
		List<CodeGapVO> list = codeGapService.getCmpCodeGapDevReal(search);
		
		return new IBSheetListVO<CodeGapVO>(list, list.size());
	}
}
