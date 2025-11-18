package kr.wise.dq.result.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import kr.wise.dq.result.service.MultiDimVO;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.portal.dashboard.service.TotalDashService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


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
public class ResultCtrl {
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
	private ResultService resultService;
	
	/** 프로파일 품질추이 폼 */
	@RequestMapping("/dq/result/result_lst.do")
	public String formPage() throws Exception {
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {

    	}
		return "/dq/result/result_lst";
	}
	
	
	/** 프로파일 품질추이 조회 - IBSheet JSON */
	@RequestMapping("/dq/result/resultList.do")
	@ResponseBody
	public IBSheetListVO<ResultVO> selectList(@ModelAttribute ResultVO search) {
		logger.debug("{}", search);
		List<ResultVO> list = resultService.getResultList(search);
		
		return new IBSheetListVO<ResultVO>(list, list.size());
	}
	
	/** 다차원분석 폼 */
	@RequestMapping("/dq/result/multidim_lst.do")
	public String goMultiDim() throws Exception {
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		
    	}
    	
    	
		return "/dq/result/multidim_lst";
	}
	
	
	/** 다차원분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/result/getMultiDimLst.do")
	@ResponseBody
	public IBSheetListVO<MultiDimVO> getMultiDimLst(@ModelAttribute MultiDimVO search) {
		logger.debug("{}", search);
		List<MultiDimVO> list = resultService.getMultiDimLst(search);
		
		return new IBSheetListVO<MultiDimVO>(list, list.size());
	}
	
	/** 다차원분석 조회 - Top5 */
	@RequestMapping(value="/dq/result/getMultiDimLstTop5.do", method=RequestMethod.GET)
	@ResponseBody
	public List<MultiDimVO> getMultiDimLstTop5() {
		//logger.debug("{}", search);
		
		/* Top5 조회 */
		List<MultiDimVO> top5result = resultService.getMultiDimLstTop5();
		
		
		logger.debug("top5 : {}", top5result);
		
		return top5result;
	}
	
	
	@RequestMapping("/dq/result/gapPoiDown.do")
    public void gapPoiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
	           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String poiFlag
	           , @RequestParam String gap, @RequestParam String tot
	           , @RequestParam String tblCnt, @RequestParam String tblNcnt
	           , @RequestParam String colCnt, @RequestParam String colNcnt
	           , @RequestParam String pdmColCnt, @RequestParam String pdmColNcnt
	           , @RequestParam String pdmTblCnt, @RequestParam String pdmTblNcnt) throws Exception{
		
    	switch(poiFlag) {
	    	case "stnd":
	    		stndDown(response, dbmsId, schId, dbmsLnm, schLnm, gap, tot);
	    		break;
	    	case "model":
	    		modelDown(response, dbmsId, schId, dbmsLnm, schLnm, tblCnt, tblNcnt
	 		           , colCnt, colNcnt
	 		           , pdmColCnt, pdmColNcnt
	 		           , pdmTblCnt, pdmTblNcnt);
	    		break;		
    	}
	}
	
	
	@RequestMapping("/dq/result/poiDown.do")
    public void poiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
    		           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String searchBgnDe2,
    		           @RequestParam String searchEndDe2) throws Exception{
    	logger.debug("poiDown clicked");
//    	logger.debug(dbCodeValue+ "   " +schCodeValue + " "+ sheetNum);
    	
    	//list를 추출하기 위한 vo
    	ResultVO search = new ResultVO();
    	search.setDbConnTrgId(dbmsId); 
    	search.setDbSchId(schId);
    	
    	if(!searchBgnDe2.equals("") || searchBgnDe2 != null) {
    		search.setSearchBgnDe(searchBgnDe2);
    	}
    	if(!searchEndDe2.equals("") || searchEndDe2 != null) {
    		search.setSearchEndDe(searchEndDe2);
    	}
    	
//    	logger.debug("searchBgnDe:{}", search.getSearchBgnDe());
//    	logger.debug("searchEndDe:{}", search.getSearchEndDe());
    	
//    	String excelType = ".xlsx"; //.xlsx와 .xls 중 택 1
    	String excelType = ".xls"; //.xlsx와 .xls 중 택 1
    	PoiHandler poiHandler = new PoiHandler(excelType);
    	
    	ResultVO term = resultService.getResultTerm(search);
    	
    	if(term == null){
    		term = new ResultVO();
    	}
    	term.setTotCnt(resultService.getTotCnt(search));
    	term.setErrCnt(resultService.getRunCnt(search));    	
    	
//    	logger.debug("term:{}", term.getAnaStrDtm());
//    	logger.debug("term:{}", term.getAnaEndDtm());
//    	logger.debug("term:{}", term.getTotCnt());
//    	logger.debug("term:{}", term.getErrCnt());
    	
    	if(dbmsId == null || dbmsId.equals("")) {
    		term.setDbConnTrgLnm("전체");
    		term.setDbSchLnm("전체");
    	} else {
    		term.setDbConnTrgLnm(dbmsLnm);
    		term.setDbSchLnm(schLnm);
    	}
    	
    	List<ResultVO> dataList = null;	//조회 목록
    	List<ResultVO> tblList = null;	//조회 목록
    	List<ResultVO> dmnRule = null;	//조회 목록
    	List<ResultVO> execList = null;	//조회 목록
    	List<ResultVO> errList = null;	//조회 목록
    	
    	dataList=resultService.getResultList(search);
    	tblList=resultService.getTblList(search);
    	
//		logger.debug("tblList size:{}",tblList.size());
//		logger.debug("tblList:{}",tblList.toString());
    	
//    	dmnRule=resultService.getDmnRule(search);
    	execList=resultService.getExecList(search);
    	errList=resultService.getErrList(search);
    	
    	List<ResultVO> dqiErrList = null;

    	dqiErrList = new ArrayList<ResultVO>();
    	
    	ResultVO dqiErrVo = null;
    	
//    	String[] dqiList = {"관계키", "필수값", "금액", "날짜", "수량", "여부", "율", "코드"
//    						, "계산 및 집계", "시간순서", "컬럼 간 논리관계", "명칭", "번호"};
    	String[] dqiList = resultService.getDqiLnmLst();
    	
    	for(int i=0; i<dqiList.length; i++) {
    		search.setDqiLnm(dqiList[i]);
    		dqiErrVo = new ResultVO();
    		dqiErrVo.setErrList(resultService.getErrListByDqi(search));
    		List<ResultDataVO> colCnt = null;
    		colCnt = resultService.getErrDataMaxColCnt(search);
    		List<ResultDataVO> errData = new ArrayList<ResultDataVO>();
    		String notIn = "";
    		
    		for(int k=0; k<colCnt.size(); k++) {
    			String prfId = colCnt.get(k).getPrfId();
    			int colCnt1 = colCnt.get(k).getColCnt();
	    		int colCnt2 = colCnt1-1;
	    		String pcolnm = "";
	    		String bcolnm = "";
	    		
	    		if((prfId != null && !prfId.equals("")))
	    			notIn += (notIn==null || notIn.equals("")) ? "'"+prfId+"'" : ", '"+prfId+"'";
	    		
	    		//CONCAT(CONCAT(A, ','), B)
	    		for(int j=0; j<colCnt2*2-2; j++) {
	    			pcolnm += "CONCAT(";
	    		}
	    		for(int j=0; j<colCnt2; j++) {
	    			if(j==0) {
	    				pcolnm += "PERR.COL_NM" + (j+1);
	    			} else {
	    				pcolnm += ",', '),PERR.COL_NM" + (j+1) + ")";
	    			}
	    		}
	    		//CONCAT(CONCAT(CONCAT(CONCAT(BERR.COL_NM1,', '),BERR.COL_NM2,', '),BERR.COL_NM3)
	    		for(int j=1; j<colCnt2*2-1; j++) {
	    			bcolnm += "CONCAT(";
	    		}
	    		for(int j=0; j<colCnt2; j++) {
	    			if(j==0) {
	    				bcolnm += "BERR.COL_NM" + (j+2);
	    			} else {
	    				bcolnm += ",', '),BERR.COL_NM" + (j+2) + ")";
	    			}
	    		}
	    		
	    		if(colCnt1 == 0) {
	    			pcolnm += "'' AS COL_NM1, 0 AS ERR_CNT";
	        		bcolnm += "'' AS COL_NM1 ";
	    		} else if(colCnt1 == 2) {
	    			pcolnm = "PERR.COL_NM1 AS COL_NM1, PERR.COL_NM2 AS ERR_CNT";
	        		bcolnm = "BERR.COL_NM2 AS COL_NM1 ";
	    		} else if(colCnt1 == 1) {
	    			pcolnm += "PERR.COL_NM1 AS COL_NM1, PERR.COL_NM1 AS ERR_CNT ";
	        		bcolnm += "BERR.COL_NM1 AS COL_NM1 ";
	    		} else {
	    			pcolnm += " AS COL_NM1, PERR.COL_NM" + colCnt1 + " AS ERR_CNT";
	        		bcolnm += " AS COL_NM1 ";
	//        		bcolnm += " AS COL_NM1, TO_CHAR(BRC.ERR_CNT)  AS ERR_CNT";
	    		}
	  		
	    		search.setPrfId(prfId);
	    		search.setPcolnm(pcolnm);
	    		search.setBcolnm(bcolnm);
	    		
	  		
	    		errData.addAll(resultService.getErrDataByDqi(search));
	    		
    		}
    		
    		logger.debug("pcol >>> " + search.getPcolnm());
    		logger.debug("bcol >>> " + search.getBcolnm());
    		
    		if(search.getPcolnm() == null || search.getPcolnm().equals("")) 
    			search.setPcolnm(" PERR.COL_NM1 AS COL_NM1, PERR.COL_NM2 AS ERR_CNT ");
    		if(search.getBcolnm() == null || search.getBcolnm().equals("")) 
    			search.setBcolnm(" BERR.COL_NM1 AS COL_NM1 ");
    		
    		search.setPrfId("");
    		search.setNotIn(notIn);
    		errData.addAll(resultService.getErrDataByDqi(search));
    		
//    		HashSet<ResultDataVO> unqCheck = new HashSet<ResultDataVO>(errData);
//    		errData = new ArrayList<ResultDataVO>(unqCheck);
    		
    		dqiErrVo.setDqiLnm(dqiList[i]);
    		dqiErrVo.setErrData(errData);
    		
    		dqiErrList.add(dqiErrVo);
    	}
    	
    	
		poiHandler.createExcelResult(dataList, term, tblList, dmnRule, execList, errList, dqiErrList);
		poiHandler.downExcel("진단종합현황", response);
    }
	
	
	private void stndDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String gap, String tot) throws Exception {
		//list를 추출하기 위한 vo
	ResultVO search = new ResultVO();
	search.setDbConnTrgId(dbmsId); 
	search.setDbSchId(schId);

//	String excelType = "2"; //.xlsx
//	String excelType = "1"; //.xls
//	String excelType = "3"; //.xlsx
	String excelType = ".xls"; //.xls
	PoiHandler poiHandler = new PoiHandler(excelType);
	
	ResultVO term = resultService.getStndTerm(search);
	List<ResultVO> sditmTblList = resultService.getSditmTblList(search);
	List<ResultVO> sditmList = resultService.getSditmList(search);
	List<ResultVO> dmnList = resultService.getDmnList(search);
	
	term.setTotCnt(new BigDecimal(tot.trim()==null?"0":tot.trim()));
	term.setErrCnt(new BigDecimal(gap.trim()==null?"0":gap.trim()));
	
	if(dbmsId == null || dbmsId.equals("")) {
		term.setDbConnTrgLnm("전체");
		term.setDbSchLnm("전체");
	} else {
		term.setDbConnTrgLnm(dbmsLnm);
		term.setDbSchLnm(schLnm);
	}
	
//	dataList=resultService.getResultList(search);
	
	
		poiHandler.createExcelStnd(term, sditmTblList, sditmList, dmnList);
		poiHandler.downExcel("WISEDQ 표준진단종합현황", response);
	}
	
	private void modelDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception {
		//list를 추출하기 위한 vo
	ResultVO search = new ResultVO();
	search.setDbConnTrgId(dbmsId); 
	search.setDbSchId(schId);

//	String excelType = "2"; //.xlsx
	String excelType = ".xls"; //.xls
//	String excelType = "3"; //.xlsx
	PoiHandler poiHandler = new PoiHandler(excelType);
	
	ResultVO term = resultService.getModelTerm(search);
	ResultVO qualStatus = resultService.getModelStatus(search);
	
	if(dbmsId == null || dbmsId.equals("")) {
		term.setDbConnTrgLnm("전체");
		term.setDbSchLnm("전체");
	} else {
		term.setDbConnTrgLnm(dbmsLnm);
		term.setDbSchLnm(schLnm);
	}
	
		poiHandler.createExcelModel(term, tblCnt, tblNcnt, colCnt, colNcnt, pdmColCnt, pdmColNcnt, pdmTblCnt, pdmTblNcnt);
		poiHandler.downExcel("WISEDQ 구조진단종합현황", response);
	}	
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
//		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
//		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		
		//프로파일 종류코드
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		
		//진단대상
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsCd", connTrgDbms);		
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//스키마
		List<CodeListVo> schDbSchNm = codeListService.getCodeList(CodeListAction.connTrgSch);
		
		codeMap.put("schDbSchNm", schDbSchNm);
		codeMap.put("schDbSchNmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(schDbSchNm)));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		
		codeMap.put("connTrgSch", connTrgSch);
				
		return codeMap;
	}
	
}
