/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : IdxGapCtrl.java
 * 2. Package : kr.wise.meta.gap.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 24. 오후 4:34:12
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.gap.service.CodeGapVO;
import kr.wise.meta.gap.service.IdxGapService;
import kr.wise.meta.gap.service.IdxGapVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : IdxGapCtrl.java
 * 3. Package  : kr.wise.meta.gap.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 24. 오후 4:34:12
 * </PRE>
 */
@Controller
public class IdxGapCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private IdxGapService idxGapService;

	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 인덱스 GAP분석 조회 페이지 */
	/** @return yeonho */
	@RequestMapping("/meta/gap/idxgap_lst.do")
	public String goIdxGapLst() {
		return "/meta/gap/idxgap_lst";
	}
	
	/** 인덱스 GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getIdxGapAnalyze.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getIdxGapAnalyze(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getIdxGapAnalyze(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/** 인덱스GAP분석 DBC 인덱스컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/selectDbcIdxColList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDbcIdxColList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDbcIdxColList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	

	
	/** 인덱스GAP분석 DDL 인덱스컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/selectDdlIdxColList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlIdxColList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlIdxColList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	
	/** 인덱스 ddl vs dbc (개발) GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlDbcIdxGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlDbcIdxGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlDbcIdxGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/**인덱스GAP분석 ddl vs dbc개발 팝업 페이지 */

	@RequestMapping("/meta/gap/idxddldbcgap_pop.do")
	public String goDdlDbcGapPop(IdxGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/meta/gap/popup/idxddldbcgap_pop";
		
	}

	/** 인덱스 ddl vs dbc (개발) GAP 컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlDbcIdxColGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlDbcIdxColGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlDbcIdxColGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/** 인덱스 ddl vs ddl (운영) GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlTsfIdxGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlTsfGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlTsfIdxGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/** 인덱스GAP분석 ddl ddl운영 팝업 페이지 */

	@RequestMapping("/meta/gap/idxddltsfgap_pop.do")
	public String goDdlTsfGapPop(IdxGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/meta/gap/popup/idxddltsfgap_pop";
		
	}

	/** 인덱스 ddl vs ddl (운영) GAP 컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlTsfIdxColGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlTsfIdxColGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlTsfIdxColGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	
	
	/** 인덱스 ddl vs DBC (운영) GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlTsfDbcIdxGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlTsfDbcGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlTsfDbcIdxGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/** 인덱스GAP분석 ddl DBC운영 팝업 페이지 */

	@RequestMapping("/meta/gap/idxddltsfdbcgap_pop.do")
	public String goDdlTsfDbcGapPop(IdxGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/meta/gap/popup/idxddltsfdbcgap_pop";
		
	}

	/** 인덱스 ddl vs dbc (운영) GAP 컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDdlTsfDbcIdxColGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDdlTsfDbcIdxColGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDdlTsfDbcIdxColGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
		/** 인덱스 dbc(개발) vs DBC (운영) GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDbcIdxGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDbcGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDbcIdxGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
	}
	
	/** 인덱스GAP분석 ddl DBC운영 팝업 페이지 */

	@RequestMapping("/meta/gap/idxdbcgap_pop.do")
	public String goDbcGapPop(IdxGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/meta/gap/popup/idxdbcgap_pop";
		
	}

	/** 인덱스 ddl vs dbc (운영) GAP 컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getDbcIdxColGapList.do")
	@ResponseBody
	public IBSheetListVO<IdxGapVO> getDbcIdxColGapList(@ModelAttribute IdxGapVO search) {
		logger.debug("{}", search);
		List<IdxGapVO> list = idxGapService.getDbcIdxColGapList(search);
		
		return new IBSheetListVO<IdxGapVO>(list, list.size());
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

		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		//codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		//codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));
		
		return codeMap;
	}
}
