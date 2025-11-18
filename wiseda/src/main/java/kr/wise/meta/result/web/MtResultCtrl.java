package kr.wise.meta.result.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.handler.PoiHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.meta.result.service.MtResultService;
import kr.wise.meta.result.service.MtResultVO;
import kr.wise.portal.dashboard.service.TotalDashService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ResultCtrl.java
 * 3. Package  : kr.wise.dq.result.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오전 9:48:36
 * </PRE>
 */ 
@Controller
public class MtResultCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private TotalDashService totalDashService;
	
	@Inject
	private MessageSource message;

	@Inject
	private MtResultService mtResultService;
	
	
	@RequestMapping("/meta/result/gapPoiDown.do")
    public void gapPoiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
	           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String poiFlag
	           , @RequestParam String gap, @RequestParam String tot
	           , @RequestParam String tblCnt, @RequestParam String tblNcnt
	           , @RequestParam String colCnt, @RequestParam String colNcnt
	           , @RequestParam String pdmColCnt, @RequestParam String pdmColNcnt
	           , @RequestParam String pdmTblCnt, @RequestParam String pdmTblNcnt
	           , @RequestParam(value="stndSort", required=false) String stndSort
	           , @RequestParam(value="asrt", required=false) String asrt) throws Exception{
		
		logger.debug("gapPoiDown:{}",dbmsId);
		logger.debug("gapPoiDown:{}",schId);
		logger.debug("tot:{}", tot);
		logger.debug("gap:{}", gap);
		
    	switch(poiFlag) {
	    	case "stnd":
	    		stndDown(response, dbmsId, schId, dbmsLnm, schLnm, gap, tot, stndSort, asrt);
	    		break;
	    	case "model":
	    		modelDown(response, dbmsId, schId, dbmsLnm, schLnm, poiFlag);
	    		break;
	    	case "govModel":
	    		govModelDown(response, dbmsId, schId, dbmsLnm, schLnm, poiFlag);
	    		break;		
    	}
	}
	
	private void stndDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String gap, String tot, String stndSort, String stndAsrt) throws Exception {
		//list를 추출하기 위한 vo
		
		logger.debug("stndSrot:{}", stndSort);
		logger.debug("stndAsrt:{}", stndAsrt);
		
		MtResultVO search = new MtResultVO();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);
	
		String excelType = ".xls"; //.xls
		PoiHandler poiHandler = new PoiHandler(excelType);
		
		MtResultVO term = null;
		MtResultVO cnt = null;
		List<MtResultVO> sditmTblList = null;
		List<MtResultVO> sditmList = null;
		List<MtResultVO> dmnList = null;
		
		search.setStndType(stndSort);
		search.setStndAsrt(stndAsrt);
		
		logger.debug("search:{}", search.toString());
		
		if(!search.getStndAsrt().equals("govStnd")){
			cnt = mtResultService.getCnt(search);
			term = mtResultService.getStndTerm(search);
			sditmTblList = mtResultService.getSditmTblList(search);
			sditmList = mtResultService.getSditmList(search);
			dmnList = mtResultService.getDmnList(search);
		} else {
			cnt = mtResultService.getGovCnt(search);
			term = mtResultService.getStndTerm(search);
			sditmTblList = mtResultService.getGovSditmTblList(search);
			sditmList = mtResultService.getGovSditmList(search);
			dmnList = mtResultService.getGovDmnList(search);
		}
		
		logger.debug("cnt:{}", cnt.getTotCnt());
		logger.debug("cnt:{}", cnt.getErrCnt());
		
		
//		term.setTotCnt(new BigDecimal(tot.trim()==null||tot.trim().equals("")?"0":tot.trim()));
//		term.setErrCnt(new BigDecimal(gap.trim()==null||gap.trim().equals("")?"0":gap.trim()));
		term.setTotCnt(cnt.getTotCnt());
		term.setErrCnt(cnt.getErrCnt());
		
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
	
//	dataList=mtResultService.getResultList(search);
	
	
		poiHandler.createMetaExcelStnd(term, sditmTblList, sditmList, dmnList, stndSort);
		poiHandler.downExcel("WISEDQ 표준진단종합현황", response);
	}
	
	private void modelDown(HttpServletResponse response, String dbmsId, String schId, String dbmsLnm, String schLnm, String poiFlag) throws Exception {
		
		logger.debug("dbmsLnm:", dbmsLnm);
		logger.debug("dbmsId:", dbmsId);
		logger.debug("schId:", schId);
		logger.debug("schLnm:", schLnm);
		
		//list를 추출하기 위한 vo
		MtResultVO search = new MtResultVO();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);

		String excelType = ".xls"; //.xls
		
		PoiHandler poiHandler = new PoiHandler(excelType);
	
		MtResultVO term = mtResultService.getModelTerm(search);
		String url = mtResultService.getDbConnTrgURL(search);
		
		logger.debug("url:{}", url);
		
		List<MtResultVO> dataList = null; // 종합결과 조회 목록
		List<MtResultVO> dbcTblList = null; // 물리DB기준 테이블 현행화 조회 목록
		List<MtResultVO> dbcColList = null; // 물리DB기준 컬럼 현행화 조회 목록
		List<MtResultVO> pdmTblList = null; // 테이블정의서 기준 테이블 현행화 조회 목록
		List<MtResultVO> pdmColList = null; // 컬럼정의서 기준 컬럼 현행화 조회 목록
		List<MtResultVO> tblDdList = null; // 테이블정의서
		List<MtResultVO> colDdList = null; // 컬럼정의서
		
		dataList=mtResultService.getStructResultList(search); // 종합결과 조회 목록
		dbcTblList=mtResultService.getDbcTblList(search);
		dbcColList=mtResultService.getDbcColList(search);
		pdmTblList=mtResultService.getPdmTblList(search);
		pdmColList=mtResultService.getPdmColList(search);
		tblDdList=mtResultService.getTblDdList(search);
		colDdList=mtResultService.getColDdList(search);
		
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
		
		logger.debug("term:", term);
		
		List<MtResultVO> dqiErrList = null;
		
		dqiErrList = new ArrayList<MtResultVO>();
	
		poiHandler.createStructExcelResult(term, dataList, dbcTblList, dbcColList, pdmTblList, pdmColList, tblDdList, colDdList, dqiErrList, poiFlag, url);
		poiHandler.downExcel("WISEDQ 구조진단종합현황", response);
	}	
	
	private void govModelDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String poiFlag) throws Exception {
		//list를 추출하기 위한 vo
		MtResultVO search = new MtResultVO();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);

		String excelType = ".xls"; //.xls
		
		PoiHandler poiHandler = new PoiHandler(excelType);
	
		MtResultVO term = mtResultService.getModelTerm(search);
		String url = mtResultService.getDbConnTrgURL(search);
		
		logger.debug("url:{}", url);
		
		List<MtResultVO> dataList = null; // 종합결과 조회 목록
		List<MtResultVO> dbcTblList = null; // 물리DB기준 테이블 현행화 조회 목록
		List<MtResultVO> dbcColList = null; // 물리DB기준 컬럼 현행화 조회 목록
		List<MtResultVO> pdmTblList = null; // 테이블정의서 기준 테이블 현행화 조회 목록
		List<MtResultVO> pdmColList = null; // 컬럼정의서 기준 컬럼 현행화 조회 목록
		List<MtResultVO> tblDdList = null; // 테이블정의서
		List<MtResultVO> colDdList = null; // 컬럼정의서
		
		dataList=mtResultService.getGovStructResultList(search); // 종합결과 조회 목록
		dbcTblList=mtResultService.getGovDbcTblList(search);
		dbcColList=mtResultService.getGovDbcColList(search);
		pdmTblList=mtResultService.getGovTblList(search);
		pdmColList=mtResultService.getGovColList(search);
		tblDdList=mtResultService.getGovTblDdList(search);
		colDdList=mtResultService.getGovColDdList(search);
		
		if(dbmsId == null || dbmsId.equals("")) {
			term.setDbConnTrgLnm("전체");
			term.setDbSchLnm("전체");
		} else {
			term.setDbConnTrgLnm(dbmsLnm);
			term.setDbSchLnm(schLnm);
		}
		
		List<MtResultVO> dqiErrList = null;
		
		dqiErrList = new ArrayList<MtResultVO>();
	
		poiHandler.createStructExcelResult(term, dataList, dbcTblList, dbcColList, pdmTblList, pdmColList, tblDdList, colDdList, dqiErrList, poiFlag, url);
		poiHandler.downExcel("WISEDQ 구조진단종합현황", response);
	}	
	
//	@ModelAttribute("codeMap")
//	public Map<String, Object> getcodeMap() {
//
//		codeMap = new HashMap<String, Object>();
//
//		//시스템영역 코드리스트 JSON
//		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
//
//		//공통코드 - IBSheet Combo Code용
////		codeMap.put("regTypCdibs", regTypCd);
////		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
////		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
//		
//		//프로파일 종류코드
//		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
//		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
//		
//		//진단대상
//		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);
//
//		codeMap.put("connTrgDbmsCd", connTrgDbms);		
//		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
//		
//		//스키마
//		List<CodeListVo> schDbSchNm = codeListService.getCodeList(CodeListAction.connTrgSch);
//		
//		codeMap.put("schDbSchNm", schDbSchNm);
//		codeMap.put("schDbSchNmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(schDbSchNm)));
//		
//		//진단대상/스키마 정보(double_select용 목록성코드)
//		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
//		codeMap.put("connTrgSch", connTrgSch);
//				
//		return codeMap;
//	}
	
}
