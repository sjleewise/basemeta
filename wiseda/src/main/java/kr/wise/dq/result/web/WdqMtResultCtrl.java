package kr.wise.dq.result.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.handler.PoiHandler;
import kr.wise.meta.result.service.MtResultService;
import kr.wise.meta.result.service.MtResultVO;
import kr.wise.portal.dashboard.service.TotalDashService;

@Controller
public class WdqMtResultCtrl {
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
	
	
	@RequestMapping("/dq/result/gapPoiDown2.do")
    public void gapPoiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
	           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String poiFlag
	           , @RequestParam String gap, @RequestParam String tot
	           , @RequestParam String tblCnt, @RequestParam String tblNcnt
	           , @RequestParam String colCnt, @RequestParam String colNcnt
	           , @RequestParam String pdmColCnt, @RequestParam String pdmColNcnt
	           , @RequestParam String pdmTblCnt, @RequestParam String pdmTblNcnt
	           , @RequestParam(value="stndSort", required=false) String stndSort) throws Exception{
		
		logger.debug("gapPoiDown:{}",dbmsId);
		logger.debug("gapPoiDown:{}",schId);
		
    	switch(poiFlag) {
	    	case "stnd":
	    		stndDown(response, dbmsId, schId, dbmsLnm, schLnm, gap, tot, stndSort);
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
	           , String dbmsLnm, String schLnm, String gap, String tot, String stndSort) throws Exception {
		//list를 추출하기 위한 vo
		
		logger.debug("stndSrot:{}", stndSort);
		MtResultVO search = new MtResultVO();
		search.setDbConnTrgId(dbmsId); 
		search.setDbSchId(schId);
	
		String excelType = ".xls"; //.xls
		PoiHandler poiHandler = new PoiHandler(excelType);
		
		MtResultVO term = null;
		List<MtResultVO> sditmTblList = null;
		List<MtResultVO> sditmList = null;
		List<MtResultVO> dmnList = null;
		
		if(stndSort.equals("1")) {
			search.setStndType("ORG");
		} else if(stndSort.equals("2")) {
			search.setStndType("GOV");
		} else {
			search.setStndType("FID");
		}
		
		if(!search.getStndType().equals("FID")){
			term = mtResultService.getStndTerm(search);
			sditmTblList = mtResultService.getSditmTblList(search);
			sditmList = mtResultService.getSditmList(search);
			dmnList = mtResultService.getDmnList(search);
		} else {
			term = mtResultService.getStndTerm(search);
			sditmTblList = mtResultService.getGovSditmTblList(search);
			sditmList = mtResultService.getGovSditmList(search);
			dmnList = mtResultService.getGovDmnList(search);
		}
		
		term.setTotCnt(new BigDecimal(tot.trim()==null||tot.trim().equals("")?"0":tot.trim()));
		term.setErrCnt(new BigDecimal(gap.trim()==null||gap.trim().equals("")?"0":gap.trim()));
		
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
}
