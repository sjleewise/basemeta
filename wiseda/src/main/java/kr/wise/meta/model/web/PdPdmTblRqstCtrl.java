/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdPdmTblRqstCtrl.java
 * 2. Package : kr.wise.meta.model.web
 * 3. Comment : 물리모델 등록 요청 컨트롤러...
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 10. 05. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 10. 05. :            : 신규 개발.
 */
package kr.wise.meta.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.model.service.PdPdmTblRqstService;
import kr.wise.meta.model.service.PdmTblRqstService;
//import kr.wise.meta.model.service.R7PdmTblRqstService;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmTbl;

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

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdPdmTblRqstCtrl.java
 * 3. Package  : kr.wise.meta.model.web
 * 4. Comment  : 파워디자니어 연계 물리모델 등록 요청 컨트롤러...
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 10. 05.
 * </PRE>
 */
@Controller("PdPdmTblRqstCtrl")
public class PdPdmTblRqstCtrl {

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

	//@Inject
	//private R7PdmTblRqstService r7pdmTblRqstService;
	
	@Inject
	private PdPdmTblRqstService pdpdmTblRqstService;

	@Inject
	private PdmTblRqstService pdmTblRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqPdmTbls extends HashMap<String, ArrayList<WaqPdmTbl>> {}

	static class WaqPdmCols extends HashMap<String, ArrayList<WaqPdmCol>> {}

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

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea 		= codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));

		
			//주제영역 정보(목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		return codeMap;
	}

    /**연계 물리모델 요청서 입력 폼.... @throws Exception insomnia */
    @RequestMapping("/meta/model/pd_pdmtbl_rqst.do")
    public String goModelPdmTblRqst(WaqMstr reqmst, ModelMap model, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}", reqmst);

       	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		String reqid = requestIdGnrService.getNextStringId();
    		reqmst.setRqstNo(reqid);
    		reqmst.setBizDcd("PDP");
//    		reqmst.setBizDtlCd("TBL");
    		reqmst.setRqstStepCd("N");
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	logger.debug("reqmst:{}", reqmst);

    	model.addAttribute("waqMstr", reqmst);
    	
      	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//        System.out.println("요청번호 : " + reqmst.getRqstNo());
//        System.out.println("요청구분 : " + reqmst.getBizDcd());
//        System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        int mapcount = approveLineServie.checkRequst(mapvo); 
//        System.out.println("카운트 : " + mapcount);
        
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());

    	return "/meta/model/pd_pdmtbl_rqst";

    }


    /** R7 모델마트 테이블 검색 팝업... @return insomnia */
    @RequestMapping("/meta/model/popup/pd_pdmtblSearchPop.do")
    public String goR7MartTblPop(@ModelAttribute("search") WamPdmTbl search, Model model, Locale locale) {
    	logger.debug("{}", search);

    	return "/meta/model/popup/pd_pdmTblSearch_pop";
    }

    /** 연계물리모델 테이블 요청 상세 정보 조회 @return insomnia */
    @RequestMapping("/meta/model/ajaxgrid/pd_pdmtbl_rqst_dtl.do")
    public String getpdmtblDetail(WaqPdmTbl searchVo, ModelMap model) {

    	logger.debug("searchVO:{}", searchVo);

    	if(searchVo.getRqstSno() == null) {
    		model.addAttribute("saction", "I");

    	} else {
    		WaqPdmTbl resultvo =  pdpdmTblRqstService.getPdmTblRqstDetail(searchVo);
    		model.addAttribute("result", resultvo);
    		model.addAttribute("saction", "U");
    	}

    	return "/meta/model/pd_pdmtbl_rqst_dtl";

    }


    /** pd 모델마트 테이블 조회 - IBSHEET JSON @return insomnia */
    @RequestMapping("/meta/model/getpdmarttbllist.do")
    @ResponseBody
    public IBSheetListVO<WaqPdmTbl> getPdMartTblList(WaqPdmTbl search, Locale loclae) {
    	logger.debug("{}", search);
    	List<WaqPdmTbl> list = pdpdmTblRqstService.getMartTblList(search);

    	return new IBSheetListVO<WaqPdmTbl>(list, list.size());

    }

    /** 물리모델 테이블 변경대상 요청 리스트 추가... @throws Exception insomnia */
    @RequestMapping("/meta/model/regpdMart2WaqPdmTbl.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regMart2Waqlist(@RequestBody WaqPdmTbls data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
//		System.out.println("요청상태" + reqmst.getRqstDcd());
		ArrayList<WaqPdmTbl> list = data.get("data");
        
		int result = pdpdmTblRqstService.regMart2Waq(reqmst, list);

		//result += pdpdmTblRqstService.check(reqmst);

		result += pdmTblRqstService.check(reqmst);  
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		}else if(result < -5){  	
			result = -1;
			resmsg = message.getMessage("ERR.PDGAP", null, locale);
		} 
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}


}
