/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlHistCtrl.java
 * 2. Package : kr.wise.meta.ddl.web
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 9. 15. 오전 10:49:15
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 9. 15. :            : 신규 개발.
 */
package kr.wise.meta.ddl.web;

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
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlHistService;
import kr.wise.meta.ddl.service.WamDdlCol;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlRel;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;

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
 * 2. FileName  : DdlHistCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 9. 15. 오전 10:49:15
 * </PRE>
 */
 @Controller
public class DdlHistCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DdlHistService ddlHistService;

	@Inject
	private DdlScriptService ddlScriptService;

	@Inject
	private MessageSource message;

//	private IBSResult ibsRes = new IBSResult();
//
//	private IBSJsonSearch ibsJson = new IBSJsonSearch();

	private Map<String, Object> codeMap;

	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** DDL테이블 변경이력페이지 호출 */
	@RequestMapping("/meta/ddl/ddlhist_lst.do")
	public String gosubjFrom(@ModelAttribute("search")WamDdlTbl search, Model model) {
		
		//loadDetail()을 사용하는 페이지에서 WAM, WAH 테이블 구분을 위한 변수 설정
		model.addAttribute("wamwahFlag", "WAH");
		
		return "/meta/ddl/ddlhist_lst";
	}
	
	/** DDL인덱스 변경이력페이지 호출 */
	@RequestMapping("/meta/ddl/ddlidxhist_lst.do")
	public String goDdlIdxFrom(@ModelAttribute("search")WamDdlIdx search, Model model) {
		
		//loadDetail()을 사용하는 페이지에서 WAM, WAH 테이블 구분을 위한 변수 설정
		model.addAttribute("wamwahFlag", "WAH");
		
		return "/meta/ddl/ddlidxhist_lst";
	}

	/** DDL 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxHistlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdx> selectDdlIdxHistList(@ModelAttribute WamDdlIdx search) {
		logger.debug("{}", search);
		List<WamDdlIdx> list = ddlHistService.getIdxHistList(search);

		return new IBSheetListVO<WamDdlIdx>(list, list.size());
	}
	
	/** DDL 인덱스이력 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlidxinfohist_dtl.do")
	public String selectDdlIdxInfoHistDetail(WamDdlIdx search, ModelMap model) {
		logger.debug(" {}", search);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(search.getDdlIdxId())) {

			WamDdlIdx result = ddlHistService.selectDdlIdxHistInfo(search);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlidxinfo_dtl";
	}
	
	/** DDL 인덱스컬럼 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxCollistHist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlIdxColHistList(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WamDdlIdxCol> list = ddlHistService.getIdxColHistList(search);

		return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
	}
	/** DDL 인덱스컬럼 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlidxcolinfohist_dtl.do")
	public String selectDdlIdxColInfoDetail(WamDdlIdxCol search, ModelMap model) {
		logger.debug(" {}", search);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(search.getDdlIdxColId())) {

			WamDdlIdxCol result = ddlHistService.selectDdlIdxColInfo(search);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlidxcolinfo_dtl";
	}
	
	/** DDL 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxChangeHist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdx> selectDdlIdxChangeHist(@ModelAttribute WamDdlIdx search) {
		logger.debug("{}", search);
		List<WamDdlIdx> list = ddlHistService.getIdxChangeHistList(search);

		return new IBSheetListVO<WamDdlIdx>(list, list.size());
	}
	
	/** DDL 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxColChangeHist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlIdxColChangeHist(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WamDdlIdxCol> list = ddlHistService.getIdxColChangeHistList(search);

		return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
	}
	
	/** DDL 테이블 이력리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlHistlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectHistList(@ModelAttribute WamDdlTbl search) {
		logger.debug("{}", search);
		List<WamDdlTbl> list = ddlHistService.getDdlHistList(search);

		return new IBSheetListVO<WamDdlTbl>(list, list.size());
	}
	
	/** DDL 테이블이력 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblinfohist_dtl.do")
	public String selectDdlTblInfoDetail(WamDdlTbl search, ModelMap model) {
		logger.debug(" {}", search);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(search.getDdlTblId())) {

			WamDdlTbl result = ddlHistService.selectDdlTblInfoHist(search);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddltblinfo_dtl";
	}
	
	/** DDL 테이블이력 컬럼목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcollisthist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlCol> selectDdlTblColHistList(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		logger.debug("{}", searchVO);
//		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlCol> list = ddlHistService.selectDdlTblColHistList(searchVO);
			return new IBSheetListVO<WamDdlCol>(list, list.size());
//		} else {
//			return null;
//		}


	}
	
	/** DDL 테이블이력 컬럼 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcolinfohist_dtl.do")
	public String selectDdlTblColInfoDetail(WamDdlCol search, ModelMap model) {
		logger.debug(" {}", search);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(search.getDdlColId())) {

			WamDdlCol result = ddlHistService.selectDdlTblColHistInfo(search);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddltblcolinfo_dtl";
	}
	
	/** DDL 테이블이력 관계목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblrellisthist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlRel> selectDdlTblRelList(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		logger.debug("{}", searchVO);
//		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlRel> list = ddlHistService.selectDdlTblRelHistList(searchVO);
			return new IBSheetListVO<WamDdlRel>(list, list.size());
//		} else {
//			return null;
//		}


	}
	
	/** DDL 테이블이력 인덱스컬럼 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblidxcolhist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlTblIdxColHistList(@ModelAttribute("searchVO") WamDdlIdxCol searchVO) throws Exception {
		logger.debug("{}", searchVO);
		//DDL테이블 조회에서는 tblId를, DDL요청에서는 Pnm을 들고 검색
		if(!UtilObject.isNull(searchVO.getDdlTblId()) || !UtilObject.isNull(searchVO.getDdlTblPnm())) {
			List<WamDdlIdxCol> list = ddlHistService.selectDdlTblIdxColHistList(searchVO);

			return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
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

		//주제영역 정보(목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		
		//공통 코드(요청구분 코드리스트)

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
		
		//오브젝트구분코드(OBJ_DCD)
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
