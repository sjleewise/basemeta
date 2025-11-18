/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcTblCtrl.java
 * 2. Package : kr.wise.meta.dbc.web
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 22. 오후 4:20:12
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 22. :            : 신규 개발.
 */
package kr.wise.meta.dbc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.dbc.service.DbcTblService;
import kr.wise.meta.dbc.service.WatDbcCol;
import kr.wise.meta.dbc.service.WatDbcIdxCol;
import kr.wise.meta.dbc.service.WatDbcTbl;

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
 * 2. FileName  : DbcTblCtrl.java
 * 3. Package  : kr.wise.meta.dbc.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 22. 오후 4:20:12
 * </PRE>
 */
@Controller
@RequestMapping
public class DbcTblCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DbcTblService dbcTblService;
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	static class WatDbcTbls extends HashMap<String, ArrayList<WatDbcTbl>> {}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@RequestMapping("/meta/dbc/dbctbl_lst.do")
	public String goFormpage(@ModelAttribute("search") WatDbcTbl search ,String linkFlag ,Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		return "/meta/dbc/dbctbl_lst";
	}
	
	@RequestMapping("/meta/dbc/dbchist_lst.do")
	public String goDbcHistPage(@ModelAttribute("search") WatDbcTbl search, Model model) {
		return "/meta/dbc/dbchist_lst";
	}
	
	/** DBC 테이블 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/dbc/getDbclist.do")
	@ResponseBody
	public IBSheetListVO<WatDbcTbl> selectList(@ModelAttribute WatDbcTbl search) {
		logger.debug("{}", search);
		List<WatDbcTbl> list = dbcTblService.getList(search);

		return new IBSheetListVO<WatDbcTbl>(list, list.size());
	}
	
	/** DBC 테이블 변경이력 조회 - IBSheet JSON */
	@RequestMapping("/meta/dbc/getDbcHistlist.do")
	@ResponseBody
	public IBSheetListVO<WatDbcTbl> selectHistList(@ModelAttribute WatDbcTbl search) {
		logger.debug("{}", search);
		List<WatDbcTbl> list = dbcTblService.getHistList(search);

		return new IBSheetListVO<WatDbcTbl>(list, list.size());
	}

	/** DBC 테이블 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblinfo_dtl.do")
	public String selectDbcTblInfoDetail(String dbSchId, String dbcTblNm, ModelMap model) {
		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm)) {

			WatDbcTbl result = dbcTblService.selectDbcTblInfo(dbSchId, dbcTblNm);
			model.addAttribute("result", result);
		}
		return "/meta/dbc/dbctblinfo_dtl";
	}
	
	/** DBC 테이블 컬럼목록 조회-모델 GAP분석에서 사용(GAP 붉은색표시) -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblcollist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcCol> selectDdlTblColList(@ModelAttribute("searchVO") WatDbcCol search) throws Exception {
		
		logger.debug("{}", search);
		
		logger.debug("param : {}, {}", search.getDbSchId(), search.getDbcTblNm());
		if(!UtilObject.isNull(search.getDbSchId()) && !UtilObject.isNull(search.getDbcTblNm())) {
			List<WatDbcCol> list = dbcTblService.selectDbcTblColList(search.getDbSchId(), search.getDbcTblNm());
			return new IBSheetListVO<WatDbcCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DBC 테이블 컬럼목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblcollist_noncolor_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcCol> selectDdlTblColListNonColor(@ModelAttribute("searchVO") WatDbcCol search) throws Exception {
		
		logger.debug("{}", search);
		
		logger.debug("param : {}, {}", search.getDbSchId(), search.getDbcTblNm());
		if(!UtilObject.isNull(search.getDbSchId()) && !UtilObject.isNull(search.getDbcTblNm())) {
			List<WatDbcCol> list = dbcTblService.selectDbcTblColListNonColor(search.getDbSchId(), search.getDbcTblNm());
			return new IBSheetListVO<WatDbcCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DBC 테이블 컬럼 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblcolinfo_dtl.do")
	public String selectDbcTblColInfoDetail(String dbSchId, String dbcTblNm, String dbcColNm, ModelMap model) {
		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		logger.debug("param2 : {}", dbcColNm);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm) && !UtilObject.isNull(dbcColNm)) {

			WatDbcCol result = dbcTblService.selectDbcTblColInfo(dbSchId, dbcTblNm, dbcColNm);
			model.addAttribute("result", result);
		}
		return "/meta/dbc/dbctblcolinfo_dtl";
	}
	
	/** DBC 테이블 인덱스컬럼 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblidxcol_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcIdxCol> selectDdlTblIdxColList(String dbSchId, String dbcTblNm) throws Exception {

		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm) && !UtilObject.isNull(dbcTblNm)) {
			List<WatDbcIdxCol> list = dbcTblService.selectDbcTblIdxColList(dbSchId, dbcTblNm);

			return new IBSheetListVO<WatDbcIdxCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DBC 테이블 테이블이력 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbctblchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcTbl> selectDdlTblChangeList(String dbSchId, String dbcTblNm) throws Exception {

		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm)) {
			List<WatDbcTbl> list = dbcTblService.selectDbcTblChangeList(dbSchId, dbcTblNm);

			return new IBSheetListVO<WatDbcTbl>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DBC 테이블  컬럼이력 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbccolchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcCol> selectDdlColChangeList(String dbSchId, String dbcTblNm) throws Exception {

		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm)) {
			List<WatDbcCol> list = dbcTblService.selectDbcColChangeList(dbSchId, dbcTblNm);

			return new IBSheetListVO<WatDbcCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DBC 테이블 인덱스컬럼이력 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbcidxcolchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcIdxCol> selectDdlIdxColChangeList(String dbSchId, String dbcTblNm) throws Exception {

		logger.debug("param : {}, {}", dbSchId, dbcTblNm);
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcTblNm)) {
			List<WatDbcIdxCol> list = dbcTblService.selectDbcIdxColChangeList(dbSchId, dbcTblNm);

			return new IBSheetListVO<WatDbcIdxCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		//주제영역 정보(double_select용 목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		
		//공통 코드(요청구분 코드리스트)

		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);

		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		
		return codeMap;
	}
	
	/** 의존객체 리스트 조회 -IBSheet json */
	@RequestMapping("/meta/dbc/ajaxgrid/dbcdpnd_dtl.do")
	@ResponseBody
	public IBSheetListVO<WatDbcTbl> getDbcDpndList(String dbSchId, String dbcTblNm, String dbConnTrgLnm, String dbSchLnm) throws Exception {
		//logger.debug("param : {}, {}", dbSchLnm, dbcTblNm);
		List<WatDbcTbl> list = dbcTblService.getDbcDpndList(dbSchId, dbcTblNm, dbConnTrgLnm, dbSchLnm);
		return new IBSheetListVO<WatDbcTbl>(list, list.size());
	}
}
