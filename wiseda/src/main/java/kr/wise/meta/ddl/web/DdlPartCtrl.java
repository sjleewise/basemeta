package kr.wise.meta.ddl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.ddl.service.DdlPartService;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlPartMain;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

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
 * 1. ClassName :
 * 2. FileName  : DdlPartCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 24. 오후 6:06:49
 * </PRE>
 */
@Controller("DdlPartCtrl")
//@RequestMapping("/meta/ddl/*")
public class DdlPartCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private MessageSource message;
	
	@Inject
	private DdlPartService ddlPartService;


	//private Map<String, Object> codeMap;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** DDL파티션 조회페이지 호출 */
	@RequestMapping("/meta/ddl/ddlpart_lst.do")
	public String goDdlPartPage(@ModelAttribute("search")WamDdlIdx search, String linkFlag, Model model) {
		//logger.debug("DDL파티션 조회페이지 linkFlag: {}",linkFlag);
		//logger.debug("DDL파티션 조회페이지 search: {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/ddl/ddlpart_lst";
	}

	@RequestMapping("/meta/ddl/ajaxgrid/ddlpartinfo_dtl.do")
	public String getDdlPartDtlInfo(String ddlPartId, String rqstNo, ModelMap model) {
		//logger.debug("DDL 테이블 상세정보 조회:{}", ddlPartId);
		if(!UtilObject.isNull(ddlPartId) && !UtilObject.isNull(rqstNo)) {
			WaqDdlPart result = ddlPartService.getDdlPartDtlInfo(ddlPartId, rqstNo);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlpartinfo_dtl";
	}
	
	
	@RequestMapping("/meta/ddl/getDdlPartlist.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlPart> getDdlPartlist(@ModelAttribute WaqDdlPart search) {
		//logger.debug("DDL파티션 리스트 조회:{}", search);
		List<WaqDdlPart> list = ddlPartService.getDdlPartLst(search);

		return new IBSheetListVO<WaqDdlPart>(list, list.size());
	}
	
	
    @RequestMapping("/meta/ddl/getddlmainpartlst.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlPartMain> getDdlPartMainList(WaqDdlPart search) {
		//logger.debug("DDL 주파티션 조회:{}",search);

		List<WaqDdlPartMain> list = ddlPartService.getDdlPartMainList(search);

		return new IBSheetListVO<WaqDdlPartMain>(list, list.size());
	}

    @RequestMapping("/meta/ddl/getddlsubpartlst.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlPartSub> getDdlPartSubList(WaqDdlPart search) {
    	//logger.debug("DDL 서브파티션  조회:{}",search);
    	
    	List<WaqDdlPartSub> list = ddlPartService.getDdlPartSubList(search);
    	
    	return new IBSheetListVO<WaqDdlPartSub>(list, list.size());
    }
    
    @RequestMapping("/meta/ddl/getddlparthistlst.do")
    @ResponseBody
    public IBSheetListVO<WaqDdlPart> getDdlPartHistLst(WaqDdlPart search) {
    	//logger.debug("DDL 서브파티션  조회:{}",search);
    	
    	List<WaqDdlPart> list = ddlPartService.getDdlPartHistLst(search);
    	
    	return new IBSheetListVO<WaqDdlPart>(list, list.size());
    }
    
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		Map<String, Object> codeMap = new HashMap<String, Object>(); 

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상/스키마 정보(double_select_upcode 용 목록성코드)
		String connTrgSchJsonBySubjSchMap   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchJsonBySubjSchMap"));
		codeMap.put("connTrgSchJsonBySubjSchMap", connTrgSchJsonBySubjSchMap);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//주제영역 정보(목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		
		//공통 코드(요청구분 코드리스트)

		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		codeMap.put("partTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PART_TYP_CD")));
		codeMap.put("partTypCd", cmcdCodeService.getCodeList("PART_TYP_CD"));
		
		//목록성 코드(시스템영역 코드리스트)
		//시스템영역
		List<CodeListVo> sysarea = codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		
		//적용요청구분코드
		codeMap.put("aplReqTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APL_REQ_TYP_CD")));
		codeMap.put("aplReqTypCd", cmcdCodeService.getCodeList("APL_REQ_TYP_CD"));
		
		return codeMap;
	}
	
	/** 2018 04 25 최형윤 주임 
	 *  DDL시퀀스 상세정보 팝업 페이지 호출 */
	@RequestMapping("/meta/ddl/popup/ddlpartlst_pop.do")
	public String goDdlPartPopForm(@ModelAttribute("search")WaqDdlPart search, String linkFlag, Model model) {
		//logger.debug("DDL인덱스 조회페이지 linkFlag: {}",linkFlag);
		//logger.debug("DDL인덱스 조회페이지 search: {}",search);
		model.addAttribute("linkFlag",linkFlag);
		model.addAttribute("ddlPartId", search.getDdlPartId());
		return "/meta/ddl/popup/ddlpartlst_pop";
	}
	
	
}
