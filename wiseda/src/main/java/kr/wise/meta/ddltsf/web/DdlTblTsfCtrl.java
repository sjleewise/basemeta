package kr.wise.meta.ddltsf.web;

import java.io.IOException;
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
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddltsf.service.DdlTsfTblService;
import kr.wise.meta.ddltsf.service.WamDdlTsfColObj;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdx;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTbTsflCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 24. 오후 6:06:49
 * </PRE>
 */
@Controller("DdlTblTsfCtrl")
//@RequestMapping("/meta/ddl/*")
public class DdlTblTsfCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DdlTsfTblService ddlTsfTblService;

	@Inject
	private DdlScriptService ddlScriptService;

	@Inject
	private MessageSource message;


	private Map<String, Object> codeMap;

//	static class wamDdlTsfObj extends HashMap<String, ArrayList<WamDdlTsfObj>> {}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/meta/ddltsf/ddltsftbl_lst.do")
	public String gosubjFrom(@ModelAttribute("search")WamDdlTsfObj search,String linkFlag,Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/ddltsf/ddltsftbl_lst";
	}
	
	/** DDL 이관리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddltsf/getDdlTsflist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTsfObj> selectList(@ModelAttribute WamDdlTsfObj search) {
		logger.debug("{}", search);
		List<WamDdlTsfObj> list = ddlTsfTblService.getList(search);

		return new IBSheetListVO<WamDdlTsfObj>(list, list.size());
	}
	
	/** DDL 테이블 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsftblinfo_dtl.do")
	public String selectDdlTsfTblInfoDetail(String ddlTblId, ModelMap model) {
		logger.debug(" {}", ddlTblId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlTblId)) {

			WamDdlTsfObj result = ddlTsfTblService.selectDdlTsfTblInfo(ddlTblId);
			model.addAttribute("result", result);
		}
		return "/meta/ddltsf/ddltsftblinfo_dtl";
	}
	
	/** DDL 테이블 컬럼목록 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddlTsftblcollist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTsfColObj> selectDdlTsfTblColList(@ModelAttribute("searchVO") WamDdlTsfObj searchVO, String ddlTblId) throws Exception {

		logger.debug("{}", ddlTblId);
		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlTsfColObj> list = ddlTsfTblService.selectDdlTsfTblColList(ddlTblId);
			return new IBSheetListVO<WamDdlTsfColObj>(list, list.size());
		} else {
			return null;
		}
	}
	
	/** DDL 테이블 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsftblchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTsfObj> selectDdlTsfTblChangeList(@ModelAttribute("searchVO") WamDdlTsfObj searchVO, String ddlTblId) throws Exception {

		logger.debug("{}", ddlTblId);
		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlTsfObj> list = ddlTsfTblService.selectDdlTsfTblChangeList(ddlTblId);
			return new IBSheetListVO<WamDdlTsfObj>(list, list.size());
		} else {
			return null;
		}
	}
	
	/** DDL 테이블 컬럼 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsftblcolinfo_dtl.do")
	public String selectDdlTblColInfoDetail(String ddlColId, ModelMap model) {
		logger.debug(" {}", ddlColId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlColId)) {

			WamDdlTsfColObj result = ddlTsfTblService.selectDdlTsfTblColInfo(ddlColId);
			model.addAttribute("result", result);
		}
		return "/meta/ddltsf/ddltsftblcolinfo_dtl";
	}
	
	/** DDL 테이블 컬럼 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsftblcolchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTsfColObj> selectDdlTblColChangeList(@ModelAttribute("searchVO") WamDdlTsfColObj searchVO) throws Exception {

		if(!UtilObject.isNull(searchVO.getDdlTblId())) {
			List<WamDdlTsfColObj> list = ddlTsfTblService.selectDdlTsfTblColChange(searchVO.getDdlTblId());
			return new IBSheetListVO<WamDdlTsfColObj>(list, list.size());
		} else {
			return null;
		}
	}
		
	/** DDL 스크립트 상세정보 조회
	 * @throws IOException */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsfscript_dtl.do")
	public String getDdlScriptTable(String ddlTblId, String scrtInfo, ModelMap model) throws IOException {
		logger.debug(" {}", ddlTblId);

//		if(StringUtils.hasText(ddlTblId)) {
//			String ddlscirpt = ddlScriptService.getDdlScriptTable(ddlTblId);
//			model.addAttribute("ddlscript", ddlscirpt);
//		}
		
		if(StringUtils.hasText(scrtInfo)) {
			model.addAttribute("ddlscript", scrtInfo);
		}
		return "/meta/ddltsf/ddltsfscript_dtl";
	}
	
	@RequestMapping("/meta/ddltsf/popup/ddltsfscript_pop.do")
	public String goDdlScriptPop(@ModelAttribute("search")WamDdlTbl search, Model model) {
		
		logger.debug("search : {}",search);
		
		
		return "/meta/ddltsf/popup/ddltsfscript_pop";
	}

	/** DDL 스크립트 조회(다건 팝업)
	 * @throws IOException */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsfscript_pop_dtl.do")
	public String getDdlScriptPop(String objId, Model model) throws Exception {
		logger.debug("{}", objId);
		String ddlScript = "";
		if(objId != null) {
			String[] tmpDdlTblId = objId.split("\\|");
			for(String ddlTblId : tmpDdlTblId) {
				ddlScript += ddlScriptService.getDdlScriptTable(ddlTblId);
				ddlScript += "\n\r\n\r\n\r\n\r\n\r\n\r";
			}
		}
		
		model.addAttribute("ddlscript", ddlScript);
		return "/meta/ddl/popup/ddlscript_pop_dtl";
	}
	
	/** 이관요청대상 DDL 목록 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/getDdlTsfTblListForRqst.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlTsfTbl> selectDdlTsfTblListForRqst(WaqDdlTsfTbl search) throws Exception {

		logger.debug("{}", search);
		
		List<WaqDdlTsfTbl> list = ddlTsfTblService.selectDdlTsfTblListForRqst(search);
		return new IBSheetListVO<WaqDdlTsfTbl>(list, list.size());
		
	}
	
	
	/** DDL 이관 인덱스 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsfidxinfo_dtl.do")
	public String selectDdlIdxInfoDetail(String ddlIdxId, ModelMap model) {
		logger.debug(" {}", ddlIdxId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlIdxId)) {

			WaqDdlTsfIdx result = ddlTsfTblService.selectDdlTsfIdxInfo(ddlIdxId);
			model.addAttribute("result", result);
		}
		return "/meta/ddltsf/ddltsfidxinfo_dtl";
	}
	
	/** DDL 이관 인덱스컬럼 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddltsf/getDdlTsfIdxCollist.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlTsfIdxCol> selectDdlTsfIdxColList(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WaqDdlTsfIdxCol> list = ddlTsfTblService.getTsfIdxColList(search);

		return new IBSheetListVO<WaqDdlTsfIdxCol>(list, list.size());
	}
	/** DDL 이관 인덱스컬럼 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/meta/ddltsf/ajaxgrid/ddltsfidxcolinfo_dtl.do")
	public String selectDdlTsfIdxColInfoDetail(String ddlIdxColId, ModelMap model) {
		logger.debug(" {}", ddlIdxColId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlIdxColId)) {

			WaqDdlTsfIdxCol result = ddlTsfTblService.selectDdlTsfIdxColInfo(ddlIdxColId);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlidxcolinfo_dtl";
	}
	
	/** DDL 이관 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddltsf/getDdlTsfIdxChange.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlTsfIdx> selectDdlIdxChange(@ModelAttribute WamDdlIdx search) {
		logger.debug("{}", search);
		List<WaqDdlTsfIdx> list = ddlTsfTblService.getTsfIdxChangeList(search);

		return new IBSheetListVO<WaqDdlTsfIdx>(list, list.size());
	}
	
	/** DDL 이관 인덱스컬럼 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddltsf/getDdlTsfIdxColChange.do")
	@ResponseBody
	public IBSheetListVO<WaqDdlTsfIdxCol> selectDdlIdxColChange(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WaqDdlTsfIdxCol> list = ddlTsfTblService.getTsfIdxColChangeList(search);

		return new IBSheetListVO<WaqDdlTsfIdxCol>(list, list.size());
	}
	
//	@RequestMapping("/meta/ddl/ddltblrqst_lst.do")
//	public String goDdlTblRqstList(@ModelAttribute("search")WamDdlTbl search, Model model) {
//		
//		logger.debug("search : {}",search);
//		
//		
//		return "/meta/ddl/ddltblrqst_lst";
//	}
//	@RequestMapping("/meta/ddl/popup/ddlscript_pop.do")
//	public String goDdlScriptPop(@ModelAttribute("search")WamDdlTbl search, Model model) {
//		
//		logger.debug("search : {}",search);
//		
//		
//		return "/meta/ddl/popup/ddlscript_pop";
//	}
//	

//
//	/** DDL 테이블 요청리스트 조회 - IBSheet JSON */
//	@RequestMapping("/meta/ddl/getDdlRqstlist.do")
//	@ResponseBody
//	public IBSheetListVO<WamDdlTbl> selectDdlRqstList(@ModelAttribute WamDdlTbl search) {
//		logger.debug("{}", search);
//		List<WamDdlTbl> list = ddlTblService.getDdlTblRqstList(search);
//		
//		return new IBSheetListVO<WamDdlTbl>(list, list.size());
//	}
//


//
//	/** DDL 스크립트 조회(다건 팝업)
//	 * @throws IOException */
//	@RequestMapping("/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do")
//	public String getDdlScriptPop(String objId, Model model) throws Exception {
//		logger.debug("{}", objId);
//		String ddlScript = "";
//		if(objId != null) {
//			String[] tmpDdlTblId = objId.split("\\|");
//			for(String ddlTblId : tmpDdlTblId) {
//				ddlScript += ddlScriptService.getDdlScriptTable(ddlTblId);
//				ddlScript += "\n\r\n\r\n\r\n\r\n\r\n\r";
//			}
//		}
//		
//		model.addAttribute("ddlscript", ddlScript);
//		return "/meta/ddl/popup/ddlscript_pop_dtl";
//	}

//	/** DDL 테이블 인덱스컬럼 리스트 조회 -IBSheet json */
//	/** yeonho */
//	@RequestMapping("/meta/ddl/ajaxgrid/ddltblidxcol_dtl.do")
//	@ResponseBody
//	public IBSheetListVO<WamDdlIdxCol> selectDdlTblIdxColList(@ModelAttribute("searchVO") WamDdlIdxCol searchVO, String ddlTblId) throws Exception {
//
//		if(!UtilObject.isNull(ddlTblId)) {
//			List<WamDdlIdxCol> list = ddlTblService.selectDdlTblIdxColList(ddlTblId);
//
//			return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
//		} else {
//			return null;
//		}
//
//
//	}
//
//
//	/** DDL테이블 요청처리 저장 - IBSheet JSON 
//	 * @throws Exception */
//	@RequestMapping("/meta/ddl/saveDdlTblRqstPrc.do")
//	@ResponseBody
//	public IBSResultVO<WamDdlTbl> regList(@RequestBody WamDdlTsfObj data, Locale locale) throws Exception {
//		logger.debug("{}", data);
//		ArrayList<WamDdlTbl> list = data.get("data");
//		int result = ddlTblService.saveDdlTblRqstPrc(list);
//
//		String resmsg;
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
//		return new IBSResultVO<WamDdlTbl>(result, resmsg, action);
//	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//DB스키마명
		List<CodeListVo> dbSchLnm = codeListService.getCodeList("dbSchLnm");
		codeMap.put("dbSchLnm", dbSchLnm);
		codeMap.put("dbSchLnmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSchLnm)));

		//DB스키마명
		List<CodeListVo> connTrgDbms = codeListService.getCodeList("connTrgDbms");
		codeMap.put("connTrgDbms", connTrgDbms);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//DBMS유형
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
				
		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		//테이블종류코드(TBL_TYP_CD)
		String tblTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_TYP_CD"));
		codeMap.put("tblTypCd", cmcdCodeService.getCodeList("TBL_TYP_CD"));
		codeMap.put("tblTypCdibs", tblTypCd);
		
		//오브젝트구분코드(OBJ_DCD)
		String objDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OBJ_DCD"));
		codeMap.put("objDcd", cmcdCodeService.getCodeList("OBJ_DCD"));
		codeMap.put("objDcdibs", objDcd);
		
		//처리유형코드(PRC_TYP_CD)
		String prcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRC_TYP_CD"));
		codeMap.put("prcTypCd", cmcdCodeService.getCodeList("PRC_TYP_CD"));
		codeMap.put("prcTypCdibs", prcTypCd);

		//테이블변경유형코드
		codeMap.put("tblChgTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CHG_TYP_CD")));
		codeMap.put("tblChgTypCd", cmcdCodeService.getCodeList("TBL_CHG_TYP_CD"));
		
		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		//codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		//codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));
		
		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));

		return codeMap;
	}

}
