package kr.wise.meta.ddl.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.ddl.service.DdlSeqRqstService;
import kr.wise.meta.ddl.service.DdlSeqService;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlSeq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : DdlSeqCtrl
 * 2. FileName  : DdlSeqCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
@Controller("DdlSeqCtrl")
public class DdlSeqCtrl {

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

	//private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private DdlSeqRqstService ddlSeqRqstService;
	
	@Inject
	private DdlSeqService ddlSeqService;

	@Inject
	private ApproveLineServie approveLineServie;

	static class WaqDdlSeqs extends HashMap<String, ArrayList<WaqDdlSeq>> {}

	/** DDL 시퀀스 변경대상 추가 팝업  */
    @RequestMapping("/meta/ddl/popup/ddlseq_pop.do")
    public String goDdlSeqPop(@ModelAttribute("search") WamDdlSeq search, Model model, Locale locale) {
    	//logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlseq_pop";
    }
    
	/** DDL 시퀀스 WAM 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlSeqlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlSeq> selectDdlSeqList(@ModelAttribute WamDdlSeq search) {
		//logger.debug("DDL 시퀀스 WAM 리스트 조회:{}", search);
		List<WamDdlSeq> list = ddlSeqService.getWamSeqList(search);

		return new IBSheetListVO<WamDdlSeq>(list, list.size());
	}
	
	/** DDL 시퀀스 WAH 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlSeqChange.do")
	@ResponseBody
	public IBSheetListVO<WamDdlSeq> getSeqChangeList(@ModelAttribute WamDdlSeq search) {
		//logger.debug("DDL 시퀀스 WAH 리스트 조회:{}", search);
		List<WamDdlSeq> list = ddlSeqService.getSeqChangeList(search);

		return new IBSheetListVO<WamDdlSeq>(list, list.size());
	}
	
	/** DDL 시퀀스 WAM 상세정보 조회 */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlseqinfo_dtl.do")
	public String selectDdlSeqInfoDetail(String ddlSeqId, String rqstNo, ModelMap model) {
		//logger.debug("DDL 시퀀스 상세정보 조회 / ddlSeqId :{}", ddlSeqId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlSeqId) && !UtilObject.isNull(rqstNo)) {
			WamDdlSeq result = ddlSeqService.getWamSeqDetail(ddlSeqId, rqstNo);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlseqinfo_dtl";
	}
	
	
	/** DDL시퀀스 조회페이지 호출 */
	@RequestMapping("/meta/ddl/ddlseq_lst.do")
	public String goDdlSeqForm(@ModelAttribute("search")WamDdlSeq search, String linkFlag, Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/ddl/ddlseq_lst";
	}

  
  /** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

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
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//시퀀스명명규칙유형
		codeMap.put("nmRlTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NM_RL_TYP_CD")));
		codeMap.put("nmRlTypCd", cmcdCodeService.getCodeList("NM_RL_TYP_CD"));
		//시퀀스사용용도유형
		codeMap.put("usTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("US_TYP_CD")));
		codeMap.put("usTypCd", cmcdCodeService.getCodeList("US_TYP_CD"));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		codeMap.put("connTrgSch", UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId")));
		
		String prcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRC_TYP_CD"));
		codeMap.put("prcTypCd", cmcdCodeService.getCodeList("PRC_TYP_CD"));
		codeMap.put("prcTypCdibs", prcTypCd);
		
		//적용요청구분코드
		codeMap.put("aplReqTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APL_REQ_TYP_CD")));
		codeMap.put("aplReqTypCd", cmcdCodeService.getCodeList("APL_REQ_TYP_CD"));
		
		//클래스 코드
		codeMap.put("seqClasCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SEQ_CLAS_NM")));
		codeMap.put("seqClasCd", cmcdCodeService.getCodeList("SEQ_CLAS_NM"));
		//init_type 코드
		codeMap.put("seqInitCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SEQ_INLT_DSCD")));
		codeMap.put("seqInitCd", cmcdCodeService.getCodeList("SEQ_INLT_DSCD"));

		return codeMap;
	}
	
	/** 2018 04 25 최형윤 주임 
	 *  DDL시퀀스 상세정보 팝업 페이지 호출 */
	@RequestMapping("/meta/ddl/popup/ddlseqlst_pop.do")
	public String goDdlSeqPopForm(@ModelAttribute("search")WamDdlSeq search, String linkFlag, Model model) {
		//logger.debug("DDL인덱스 조회페이지 linkFlag: {}",linkFlag);
		//logger.debug("DDL인덱스 조회페이지 search: {}",search);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("ddlSeqId", search.getDdlSeqId());
		return "/meta/ddl/popup/ddlseqlst_pop";
	}

}
