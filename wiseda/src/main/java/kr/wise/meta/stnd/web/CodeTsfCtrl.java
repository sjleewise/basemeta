/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : codeTsfCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 코드이관 요청 컨트롤러
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 27. 
 * 6. 변경이력 :
 */
package kr.wise.meta.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.CodeTsfRqstService;
import kr.wise.meta.stnd.service.CodeTsfService;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamCdValTsf;
import kr.wise.meta.stnd.service.WamCodeTsfMapper;
import kr.wise.meta.stnd.service.WaqCdVal;
import kr.wise.meta.stnd.service.WaqCdValTsf;
import kr.wise.meta.stnd.service.WaqDmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("CodeTsfCtrl")
public class CodeTsfCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private CodeTsfService codeTsfService;

	@Inject
	private ApproveLineServie approveLineServie;


	@Inject
	private BasicInfoLvlService basicInfoLvlService;


	static class WaqDmns extends HashMap<String, ArrayList<WaqDmn>> {}

	static class WaqCdValTsfs extends HashMap<String, ArrayList<WaqCdValTsf>> {}


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//코드값유형코드
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		//코드값부여방식코드
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));


//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
/*		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		List<CodeListVo> dmng 	= codeListService.getCodeList("dmng");

		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		codeMap.put("dmng", UtilJson.convertJsonString(dmng));
		codeMap.put("dmngibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dmng)));*/

       List<CodeListVo> connTrgSchIdCodeTsf = codeListService.getCodeList("connTrgSchIdCodeTsf");
       codeMap.put("connTrgSchIdCodeTsf", UtilJson.convertJsonString(connTrgSchIdCodeTsf));

		return codeMap;
	}


    /** 코드이관 요청서 .... @throws Exception */
    @RequestMapping("meta/stnd/codetsf_lst.do")
    public String goCodeTsfForm( ModelMap model) throws Exception {
//    	logger.debug("reqmst:{}", reqmst);

    	return "/meta/stnd/codetsf_lst";

    }
    
    /** 코드 이관 조회 */
    @RequestMapping("meta/stnd/getCodeTsfWamList.do")
    @ResponseBody
    public IBSheetListVO<WamCdValTsf> getCodeTsfWamList(WamCdValTsf searchVo, ModelMap model, Locale locale){
    	
        List<WamCdValTsf> list = codeTsfService.selectCodeTsfWamList(searchVo);
        return new IBSheetListVO<WamCdValTsf>(list, list.size());
    }
    
        /** 코드이관 요청서 상세 .... @throws Exception */
    @RequestMapping("meta/stnd/ajaxgrid/codetsf_dtl.do")
    public String geCodeTsfWamDetail(WamCdValTsf searchVo, ModelMap model) throws Exception {

    	WamCdValTsf result = codeTsfService.selectCodeTsfWamDetail(searchVo);

    	model.addAttribute("result", result);
    	
    	return "/meta/stnd/codetsf_dtl";

    }
    
   /** 코드 이관 변경이력 조회 */
    @RequestMapping("/meta/stnd/ajaxgrid/codetsfchange_dtl.do")
    @ResponseBody
    public IBSheetListVO<WamCdValTsf> getCodeTsfWamChg(WamCdValTsf searchVo, ModelMap model, Locale locale){
    	logger.debug("타겟" + searchVo.getTgtDbConnTrgId());
    	
        List<WamCdValTsf> list = codeTsfService.selectCodeTsfWamChg(searchVo);
        return new IBSheetListVO<WamCdValTsf>(list, list.size());
    }
 
}





