/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRequestCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준단어 등록요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 12:54:05
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.meta.symn.web;

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
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.app.service.WamAppStwd;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.stnd.service.StndDmnRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.StndTotRqstService;
import kr.wise.meta.stnd.service.StndWordRqstService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqStndTot;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.symn.service.SymnItemRqstService;
import kr.wise.meta.symn.service.SymnItemService;
import kr.wise.meta.symn.service.WamSymnItem;
import kr.wise.meta.symn.service.WaqSymnItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : SymnItemRqstCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  : 유사항목 등록요청 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 11. 26. 
 * </PRE>
 */
@Controller
public class SymnItemCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private SymnItemRqstService symnItemRqstService;

	
	@Inject
	private SymnItemService symnItemService;

	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;


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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}
	
	
	/** DDL시퀀스 조회페이지 호출 */
	@RequestMapping("/meta/symn/symnItem_lst.do")
	public String goDdlSeqForm(@ModelAttribute("search")WamSymnItem search, String linkFlag, Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/symn/symnItem_lst";
	}
	
    
	 /** 유사항목 변경신청 팝업  @return insomnia */
    @RequestMapping("/meta/symn/popup/symnItem_pop.do")
    public String goSymnItemPop(@ModelAttribute("search") WamSymnItem search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/symn/popup/symnItem_pop";
	}
   
    
    /** 유사항목 WAM 리스트 조회.... @return  */
    @RequestMapping("/meta/symn/getSymnItemList.do")
	@ResponseBody
	public IBSheetListVO<WamSymnItem> getAppStndWordList(@ModelAttribute WamSymnItem data, Locale locale) {
		
		logger.debug("reqvo:{}", data);
		
		List<WamSymnItem> list = symnItemService.getSymnItemList(data);
		
		
//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		
		return new IBSheetListVO<WamSymnItem>(list, list.size());
		
	}

	
	/** 단어 상세정보 조회 */
	@RequestMapping("/meta/symn/ajaxgrid/symnItemInfo_dtl.do")
	public String selectSymnItemInfoDetail(String symnItemId, ModelMap model) {
		logger.debug(" {}", symnItemId);

		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(symnItemId)) {

			WamSymnItem result = symnItemService.selectSymnItemInfoDetail(symnItemId);
			model.addAttribute("result", result);
		}
		return "/meta/symn/symnItemInfo_dtl";
	}    	
    
    	
	/** 단어 변경이력 조회 -IBSheet json */
	@RequestMapping("/meta/symn/ajaxgrid/symnItemChange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamSymnItem> selectMenuList(@ModelAttribute("searchVO") WamSymnItem searchVO, String symnItemId) throws Exception {

		logger.debug("{}", symnItemId);
		List<WamSymnItem> list = symnItemService.selectSymnItemChangeList(symnItemId);
		return new IBSheetListVO<WamSymnItem>(list, list.size());
	}

    	

}
