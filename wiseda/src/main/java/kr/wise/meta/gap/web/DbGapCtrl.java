/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbGapCtrl.java
 * 2. Package : kr.wise.meta.gap.web
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 24. 오후 3:00:57
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.gap.service.DbGapService;
import kr.wise.meta.gap.service.WatDbcGapCol;
import kr.wise.meta.gap.service.WatDbcGapIdx;
import kr.wise.meta.gap.service.WatDbcGapIdxCol;
import kr.wise.meta.gap.service.WatDbcGapTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbGapCtrl.java
 * 3. Package  : kr.wise.meta.gap.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 24. 오후 3:00:57
 * </PRE>
 */
@Controller
public class DbGapCtrl {
Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	DbGapService dbGapService;

	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** DB GAP분석 조회 페이지 */
	/** @return yeonho */
	@RequestMapping("/meta/gap/dbgap_lst.do")
	public String goDbGapLst() {
		return "/meta/gap/dbgap_lst";
	}

	/** DB GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/meta/dbgap/getDbGapList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapTbl> getDbGapList(@ModelAttribute WatDbcGapTbl search) {
		logger.debug("{}", search);
		List<WatDbcGapTbl> list = dbGapService.getDbGapList(search);
		
		return new IBSheetListVO<WatDbcGapTbl>(list, list.size());
	}
	
	/** DB GAP 분석컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapColList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapCol> getGapColList(@ModelAttribute WatDbcGapCol search) {
		logger.debug("{}", search);
		List<WatDbcGapCol> list = dbGapService.getGapColList(search);
		
		return new IBSheetListVO<WatDbcGapCol>(list, list.size());
	}
	
	/** DB GAP 분석 SRC컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapSrcList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapCol> getGapSrcList(@ModelAttribute WatDbcGapCol search) {
		logger.debug("{}", search);
		List<WatDbcGapCol> list = dbGapService.getGapSrcList(search);
		
		return new IBSheetListVO<WatDbcGapCol>(list, list.size());
	}
	
	/** DB GAP 분석 TGT컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapTgtList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapCol> getGapTgtList(@ModelAttribute WatDbcGapCol search) {
		logger.debug("{}", search);
		List<WatDbcGapCol> list = dbGapService.getGapTgtList(search);
		
		return new IBSheetListVO<WatDbcGapCol>(list, list.size());
	}
	
	/** DB GAP 분석 인덱스 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapIdxList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapIdx> getGapIdxList(@ModelAttribute WatDbcGapIdx search) {
		logger.debug("{}", search);
		List<WatDbcGapIdx> list = dbGapService.getGapIdxList(search);   
		
		return new IBSheetListVO<WatDbcGapIdx>(list, list.size());
	}
	
	/** DB GAP 분석 인덱스컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapIdxColSrcList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapIdxCol> getGapIdxColSrcList(@ModelAttribute WatDbcGapIdx search) {
		logger.debug("{}", search);
		List<WatDbcGapIdxCol> list = dbGapService.getGapIdxColSrcList(search);   
		
		return new IBSheetListVO<WatDbcGapIdxCol>(list, list.size());
	}
	/** DB GAP 분석 인덱스컬럼 조회 - IBSheet JSON */
	@RequestMapping("/meta/gap/getGapIdxColTgtList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcGapIdxCol> getGapIdxColTgtList(@ModelAttribute WatDbcGapIdx search) {
		logger.debug("{}", search);
		List<WatDbcGapIdxCol> list = dbGapService.getGapIdxColTgtList(search);   
		
		return new IBSheetListVO<WatDbcGapIdxCol>(list, list.size());
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상/스키마 정보(double_select용 목록성코드)
		String dbGapSrcShdId   = UtilJson.convertJsonString(codeListService.getCodeList("dbGapSrcShdId"));
		codeMap.put("dbGapSrcShdId", dbGapSrcShdId);
		
		String dbGapTgtShdId   = UtilJson.convertJsonString(codeListService.getCodeList("dbGapTgtShdId"));
		codeMap.put("dbGapTgtShdId", dbGapTgtShdId);

		//DB명
		List<CodeListVo> connTrgDbms = codeListService.getCodeList("connTrgDbms");
		codeMap.put("connTrgDbms", connTrgDbms);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//DB스키마명
		List<CodeListVo> dbSchLnm = codeListService.getCodeList("dbSchLnm");
		codeMap.put("dbSchLnm", dbSchLnm);
		codeMap.put("dbSchLnmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(dbSchLnm)));

		//DBMS유형
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		
		
		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		//codeMap.put("sortTypeibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		
		
		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);

		return codeMap;
	}
}
