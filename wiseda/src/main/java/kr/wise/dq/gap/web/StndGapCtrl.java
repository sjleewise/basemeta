package kr.wise.dq.gap.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

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

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.gap.service.StndGapService;
import kr.wise.dq.gap.service.StndGapVO;
import kr.wise.meta.stnd.service.WamSditm;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : GapMainCtrl.java
 * 3. Package  : kr.wise.meta.gap.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 6. 24. 오후 5:14:29
 * </PRE>
 */ 
@Controller
public class StndGapCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	StndGapService stndGapService;

	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상/스키마 정보(double_select용 목록성코드)
		//String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		//codeMap.put("connTrgSch", connTrgSch);
		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgSch", connTrgSch);
		
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId2"));
		codeMap.put("devConnTrgSch", devConnTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		
		String niaStndAnaCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NIA_STND_ANA_CD"));
		codeMap.put("niaStndAnaCd", cmcdCodeService.getCodeList("NIA_STND_ANA_CD"));
		codeMap.put("niaStndAnaCdibs", niaStndAnaCd);


		return codeMap;
	}
	
	/** 표준용어 진단 페이지 */
	@RequestMapping("/dq/gap/stnd_gap.do")
	public String goStndItemGapLst() {
		return "/dq/gap/stnd_gap";
	}
	
	/** 표준용어 진단 페이지 */
	@RequestMapping("/dq/gap/stnd_gap_res_lst.do")
	public String goStndItemGapResultLst() {
		return "/dq/gap/stnd_gap_res_lst";
	}
	
	
	
	/** 표준용어 불일치 진단 페이지 */
	@RequestMapping("/dq/gap/stnd_gap_un.do")
	public String goStndItemGapLstUneq() {
		return "/dq/gap/stnd_gap_un";
	}
	
	/** db표준용어 진단 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDBStndItemGapList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDBStndItemGapList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDBStndItemGap(search);
		Map<String,String> etc = stndGapService.getDBStndItemGapResult();
		
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	/** db표준용어 진단 불일치 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDBStndItemGapUneqList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDBStndItemGapUneqList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDBStndItemGap(search);
		Map<String,String> etc = stndGapService.getDBStndItemGapUneqResult();
		
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	
	
	/** db도메인 진단 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDBDmnGapList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDBDmnGapList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDBDmnGap(search);
		Map<String,String> etc = stndGapService.getDBDmnGapResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	/** db도메인 진단 불일치 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDBDmnGapUneqList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDBDmnGapUneqList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDBDmnGap(search);
		Map<String,String> etc = stndGapService.getDBDmnGapUneqResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	
	/** 기관표준용어 진단 - IBSheet JSON */
	@RequestMapping("/dq/gap/getStndItemGapList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getStndItemGapList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getStndItemGap(search);
		Map<String,String> etc = stndGapService.getStndItemGapResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	/** 기관표준용어 진단 불일치 - IBSheet JSON */
	@RequestMapping("/dq/gap/getStndItemGapUneqList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getStndItemGapUneqList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getStndItemGap(search);
		Map<String,String> etc = stndGapService.getStndItemGapUneqResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	
	/** 기관도메인 진단 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDmnGapList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDmnItemGapList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDmnGap(search);
		Map<String,String> etc = stndGapService.getDmnGapResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
	
	/** 기관도메인 진단 불일치 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDmnGapUneqList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getDmnItemGapUneqList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getDmnGap(search);
		Map<String,String> etc = stndGapService.getDmnGapUneqResult();
		return new IBSheetListVO<StndGapVO>(list, list.size(),etc);
	}
	
      /** db표준항목 표준준수율 @throws Exception insomnia */
    @RequestMapping("dq/gap/saveGapResult.do")
    @ResponseBody
    public IBSResultVO<StndGapVO> saveGapResult(StndGapVO saveVo, Locale locale) throws Exception {

//    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//    	ArrayList<WamSditm> list = data.get("data");

    	int result = stndGapService.saveGapResult(saveVo);

    	String resmsg;

    	if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}
    	
    	String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
    	
    	
    	return new IBSResultVO<StndGapVO>(saveVo, result, resmsg, action);
    }	    	
    
	

	@RequestMapping("/dq/gap/coldefgap_lst.do")
	public String goColDefGapLst() {
		return "/dq/gap/coldefgap_lst";
	}
	
    @RequestMapping("/dq/gap/getColDefGapList.do")
    @ResponseBody
    public IBSheetListVO<StndGapVO> getColDefGapList(@ModelAttribute StndGapVO search) {
        
        List<StndGapVO> list = stndGapService.getColDefGapList(search);
        return new IBSheetListVO<StndGapVO>(list, list.size());
    }
    
    	
	@RequestMapping("/dq/gap/regColDefGapList.do")
	@ResponseBody
    public IBSResultVO<StndGapVO> regColDefGapList(StndGapVO saveVO, String saction, Locale locale) throws Exception {
    	
		logger.debug(saveVO.toString());
    	int result = stndGapService.regColDefGapList(saveVO);

    	String resmsg ;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<StndGapVO>(saveVO, result, resmsg, action);
    }
	
	
	/** 기관도메인 진단 - IBSheet JSON */
	@RequestMapping("/dq/gap/getGapResultList.do")
	@ResponseBody
	public IBSheetListVO<StndGapVO> getGapResultList(@ModelAttribute StndGapVO search) {
		logger.debug("{}", search);
		List<StndGapVO> list = stndGapService.getGapResultList(search);
		
		return new IBSheetListVO<StndGapVO>(list, list.size());
	}
	
	
	
	@RequestMapping("/dq/gap/mtacolgap_lst.do")
	public String goMtaDefGapLst() {
		return "/dq/gap/mtacolgap_lst";
	}
	
    @RequestMapping("/dq/gap/getMtaColGapList.do")
    @ResponseBody
    public IBSheetListVO<StndGapVO> getMtaColGapList(@ModelAttribute StndGapVO search) {
        
        List<StndGapVO> list = stndGapService.getMtaColGapList(search);
        Map<String, String> etc = new HashMap<String, String>();
        //1.8 에서 가능
//        String colAplyCnt = String.valueOf(list.stream().filter(vo -> vo.getGapStatus().equals("APLY")).count());
        String colAplyCnt = "0";
        etc.put("colAplyCnt", colAplyCnt);
        return new IBSheetListVO<StndGapVO>(list, list.size(), etc);
    }
    	
	@RequestMapping("/dq/gap/regMtaColGapList.do")
	@ResponseBody
    public IBSResultVO<StndGapVO> regMtaColGapList(StndGapVO saveVO, String saction, Locale locale) throws Exception {
    	
		logger.debug(saveVO.toString());
    	int result = stndGapService.regMtaColGapList(saveVO);

    	String resmsg ;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<StndGapVO>(saveVO, result, resmsg, action);
    }
	@RequestMapping("/dq/gap/stnd_gap_result.do")
	public String goStndGapResultLst() {
		return "/dq/gap/stnd_gap_result";
	}
	
	   	
	@RequestMapping("/dq/gap/getStndGapResultLst.do")
    @ResponseBody
    public IBSheetListVO<StndGapVO> getStndGapResultLst(@ModelAttribute StndGapVO search) {
        List<StndGapVO> list = stndGapService.getGapResultList(search);
        
        return new IBSheetListVO<StndGapVO>(list, list.size()); 
    }
}
