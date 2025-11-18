package kr.wise.dq.gap.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.gap.service.GapMainService;
import kr.wise.meta.gap.service.ModelGapVO;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : GapMainCtrl.java
 * 3. Package  : kr.wise.meta.gap.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 6. 24. 오후 5:14:29
 * </PRE>
 */ 
@Controller
public class WdqGapMainCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private GapMainService gapMainService;

	private Map<String, Object> codeMap;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	//시스템 영역별 dbms정보를 불러오기위해 추가 
	// 사용하는 곳 존재하지 않으므로 return null처리
//	@RequestMapping("/dq/gap/getDbmsSchList.do")
	@ResponseBody
	public List<CodeListVo> getDbmsSchList(@RequestParam(value = "sysAreaId") String sysAreaId){
		
//		return codeListService.getDbmsSchList(sysAreaId);
		return null;
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상/스키마 정보(double_select용 목록성코드)
		//String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		//codeMap.put("connTrgSch", connTrgSch);
		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgSch", connTrgSch);
		
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId2"));
		codeMap.put("devConnTrgSch", devConnTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		//표준분류
		String stndAsrt = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT"));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		codeMap.put("stndAsrtibs", stndAsrt);

		// DBMS + 스키마 (double_select)
		String connTrgDbmsSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgDbmsSch", connTrgDbmsSch);
		
		//시스템영역 코드리스트 JSON
		List<CodeListVo> sysareatmp = codeListService.getCodeList("sysarea");
		String sysarea = UtilJson.convertJsonString(sysareatmp);
		String sysareaibs = UtilJson.convertJsonString(codeListService.getCodeListIBS(sysareatmp));
				
		codeMap.put("sysarea", sysarea);
		codeMap.put("sysareaibs", sysareaibs); 
		
		return codeMap;
	}
	
	/** 모델GAP분석 조회 페이지 */
	/** @return yeonho */
	@RequestMapping("/dq/gap/modelgap_lst.do")
	public String goPdmTblGapLst() {
		return "/dq/gap/modelgap_lst";
	}
	
	/** 모델GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getModelGapAnalyze.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getModelGapAnalyze(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getModelGapAnalyze(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** 물리모델 VS DDL GAP 리스트 - IBSheet JSON */
	@RequestMapping("/dq/gap/getPdmDdlGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getPdmDdlGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getPdmDdlGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
		
	/** ===========메인화면 모델 VS DB GAP분석   ================*/
	/** @return
	/** @throws Exception yeonho */
	/** 메인화면 GAP분석 차트 */
	@RequestMapping("/dq/gap/getGapChart.do")
	@ResponseBody
	public List<ModelGapVO> getGapChart() throws Exception{

		List<ModelGapVO> list = gapMainService.getGapChart();
		logger.debug("{}", list);
		return list;
	}
	
	@RequestMapping("/dq/gap/getGapChartDate.json")
	@ResponseBody
	public List<ModelGapVO> getGapChartDateJSON() throws Exception{
		return gapMainService.getGapChartDateJSON();
	}

    /** ===========메인화면 모델 VS DB GAP분석 END   ================*/
	
	/** 모델GAP분석 pdm vs ddl 팝업 페이지 */

	@RequestMapping("/dq/gap/pdmddlgap_pop.do")
	public String goPdmDdlGapPop(ModelGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/pdmddlgap_pop";
		
	}
	
	/** PDM VS DDL 컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getPdmDdlColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getPdmDdlColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("물리모델테이블ID "+search.getPdmTblId());
		logger.debug("DDL테이블ID "+search.getDdlTblId());
		List<ModelGapVO> list = gapMainService.getModelDdlColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	
	/** 모델GAP분석 pdm vs ddl 팝업 페이지 */

	@RequestMapping("/dq/gap/ddldbcgap_pop.do")
	public String goDdlDbcGapPop(ModelGapVO search, Model model) {
	
		
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/ddldbcgap_pop";
		
	}
	
	/** DDL VS DBC 분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlDbcGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlDbcGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getDdlDbcGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DDL VS DBC 컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlDbcColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlDbcColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("DDL테이블 "+search.getDdlTblPnm());
		logger.debug("dbconntrgLnm"+ search.getDbConnTrgLnm());
		logger.debug("dbSchId"+ search.getDbSchId());
		
		List<ModelGapVO> list = gapMainService.getDdlDbcColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	
	/** 마트 vs 물리모델 리스트 - IBSheet JSON */
	@RequestMapping("/dq/gap/getMartPdmGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getMartPdmGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getMartPdmGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
		
	
	/** 모델GAP분석 pdm vs ddl 팝업 페이지 */

	@RequestMapping("/dq/gap/martpdmgap_pop.do")
	public String goMartPdmGapPop(ModelGapVO search, Model model) {
	
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/martpdmgap_pop";
		
	}
	
	
	
	/** 마트 VS 물리모델 gap컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getMartPdmColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getMartPdmColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("주제영역명 "+search.getSubjLnm());
		logger.debug("마트테이블명"+ search.getMartTblPnm());
		logger.debug("물리모델컬럼명"+ search.getPdmTblPnm());
		
		List<ModelGapVO> list = gapMainService.getMartPdmColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DDL VS DDL운영 팝업 페이지 */
	@RequestMapping("/dq/gap/ddltsfgap_pop.do")
	public String goDdlTsfGapPop(ModelGapVO search, Model model) {
	
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/ddltsfgap_pop";
		
	}
	/** DDL VS DDL운영 분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlTsfGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlTsfGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getDdlTsfGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/**  DDL VS DDL운영  gap컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlTsfColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlTsfColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("테이블id"+ search.getDdlTblId());
		logger.debug("테이블TSFid"+ search.getDdlTsfTblId());
		logger.debug("소스스키마"+ search.getDbSchId());
		logger.debug("타겟스키마"+ search.getDbTsfSchId());
		
		List<ModelGapVO> list = gapMainService.getDdlTsfColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DDL운영 VS DBC운영 분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlTsfDbcGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlTsfDbcGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getDdlTsfDbcGapList(search);
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DDL VS DDL운영 팝업 페이지 */
	@RequestMapping("/dq/gap/ddltsfdbcgap_pop.do")
	public String goDdlTsfDbcGapPop(ModelGapVO search, Model model) {
	
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/ddltsfdbcgap_pop";
		
	}
	
		/** DDL VS DBC 컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDdlTsfDbcColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDdlTsfDbcColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("DDL테이블 "+search.getDdlTsfTblPnm());
		logger.debug("dbconntrgLnm"+ search.getDbTsfConnTrgLnm());
		logger.debug("dbSchId"+ search.getDbTsfSchId());
		
		List<ModelGapVO> list = gapMainService.getDdlTsfDbcColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DBC개발 VS DBC운영 분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDbcGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDbcGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getDbcGapList(search);
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** DDL VS DDL운영 팝업 페이지 */
	@RequestMapping("/dq/gap/dbcgap_pop.do")
	public String goDbcGapPop(ModelGapVO search, Model model) {
	
		model.addAttribute("search", search);
		
		return "/dq/gap/popup/dbcgap_pop";
		
	}
	
		/** DBC개발 VS DBC운영 컬럼목록 - IBSheet JSON */
	@RequestMapping("/dq/gap/getDbcColGap.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getDbcColGap(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		logger.debug("개발dbSchId"+ search.getDbSchId());
		logger.debug("개발테이블명"+ search.getDbcTblNm());
		logger.debug("운영dbSchId"+ search.getDbRealSchId());
		logger.debug("운영테이블명"+ search.getDbcRealTblNm());
		
		List<ModelGapVO> list = gapMainService.getDbcColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	
	
	
	/** ========ER-WIN모델GAP분석 페이지 - IBSheet JSON ===========*/	
	/** @return yeonho */
	@RequestMapping("/dq/gap/erwinpdmtblgap_lst.do")  
	public String goErwinPdmTblGapLst() {
		return "/dq/gap/erwinpdmtblgap_lst";  
	}
	
	/** ER-WIN모델GAP분석 테이블 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getErwinPdmTblGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getErwinPdmTblGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getErwinPdmTblGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** ER-WIN모델GAP분석 컬럼 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getErwinPdmColGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getErwinPdmColGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getErwinPdmColGapList(search); 
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** ===============ER-WIN모델GAP분석 페이지 END ================*/ 
	
	
	/** ==============비표준컬럼GAP분석 조회 페이지 =============*/
	/** @return yeonho */
	@RequestMapping("/dq/gap/nonstndpdmcolgap_lst.do")    
	public String goNonStndPdmColGapLst() {
		return "/dq/gap/nonstndpdmcolgap_lst";  
	}
	
	/** 비표준컬럼GAP분석 조회 - IBSheet JSON */
	@RequestMapping("/dq/gap/getNonStndPdmColGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getNonStndPdmColGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getNonStndPdmColGapList(search);
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	/** ==============비표준컬럼GAP분석 조회 페이지 END =============*/
	
	
	/** ============모델개발DB GAP분석 조회 페이지 ==============*/
    /** @return yeonho */
    @RequestMapping("/dq/gap/mdldevdbgap_lst.do")
    public String goMdlDevDbGapLst() {
        return "/dq/gap/mdldevdbgap_lst";
    }
    
    
    /** 모델개발DB GAP분석 테이블 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getMdlDevDbTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDevDbTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\nsubjLnm:" + search.getSubjLnm());
        
        List<ModelGapVO> list = gapMainService.getMdlDevDbTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    
    /** 모델개발DB GAP분석 컬럼 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getMdlDevDbColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDevDbColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getMdlDevDbColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** ============모델개발DB GAP분석 조회 페이지 END ==============*/
	
    
    /** ============모델개발DDL GAP분석 조회 페이지 ==============*/
    /** @return yeonho */
    @RequestMapping("/dq/gap/mdldevddlgap_lst.do")
    public String goMdlDevDdlGapLst() {
        return "/dq/gap/mdldevddlgap_lst";
    }
        
    /** 모델개발DDL GAP분석 테이블 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getMdlDevDdlTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDevDdlTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\nsubjLnm:" + search.getSubjLnm());
        
        List<ModelGapVO> list = gapMainService.getMdlDevDdlTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    
    /** 모델개발DDL GAP분석 컬럼 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getMdlDevDdlColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDevDdlColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getMdlDevDdlColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** ============모델개발DDL GAP분석 조회 페이지 END ==============*/
    
	
	/**=============== DB간 GAP분석 페이지 - IBSheet JSON ==========*/    
    /** @return  */
    @RequestMapping("/dq/gap/dbsrctgtgap_lst.do")  
    public String goDbSrcTgtTblGapLst() {
        
        return "/dq/gap/dbsrctgtgap_lst";  
    }
    
    /** 소스 DB스키마 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getSrcDbSchList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getSrcDbSchList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getSrcDbSchList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
	
    /** 타겟 DB스키마 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getTgtDbSchList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getTgtDbSchList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getTgtDbSchList(search); 
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    
    /** DB간 GAP분석 테이블 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDbSrcTgtTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDbSrcTgtTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDbSrcTgtTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** DB간 GAP분석 컬럼 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDbSrcTgtColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDbSrcTgtColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDbSrcTgtColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /**=============== DB간 GAP분석 페이지 END ================*/
    
    
    /** ===========DDL간 GAP분석 페이지 - IBSheet JSON ========*/    
    /** @return  */
    @RequestMapping("/dq/gap/ddlsrctgtgap_lst.do")  
    public String goDdlSrcTgtTblGapLst() {
        
        return "/dq/gap/ddlsrctgtgap_lst";  
    }
    
    /** DDL간 GAP분석 테이블 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDdlSrcTgtTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDdlSrcTgtTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDdlSrcTgtTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** DDL간 GAP분석 컬럼 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDdlSrcTgtColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDdlSrcTgtColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDdlSrcTgtColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** ===========DDL간 GAP분석 페이지 END ================*/
    
    /** ===========DDL인덱스간 GAP분석 페이지 - IBSheet JSON ========*/    
    /** @return  */
    @RequestMapping("/dq/gap/ddlidxsrctgtgap_lst.do")  
    public String goDdlIdxSrcTgtTblGapLst() {
        
        return "/dq/gap/ddlidxsrctgtgap_lst";  
    }
    
    /** DDL인덱스간 GAP분석 테이블 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDdlIdxSrcTgtTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDdlIdxSrcTgtTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDdlIdxSrcTgtTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** DDL인덱스간 GAP분석 컬럼 조회 - IBSheet JSON */
    @RequestMapping("/dq/gap/getDdlIdxSrcTgtColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getDdlIdxSrcTgtColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        logger.debug("\n srcDbSchId:" + search.getSrcDbSchId());
        logger.debug("\n tgtDbSchId:" + search.getTgtDbSchId());
        
        List<ModelGapVO> list = gapMainService.getDdlIdxSrcTgtColGapList(search);
      
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    /** ===========DDL인덱스간 GAP분석 페이지 END ================*/    
 
    
	/** 모델GAP통합 조회 페이지 */
	/** @return hjan93 */
	@RequestMapping("/dq/gap/modelgaptot_lst.do")
	public String goModelGapTotLst(ModelGapVO search, Model model) {
		return "/dq/gap/modelgaptot_lst";
	}
	
	@RequestMapping("/dq/gap/mtnonstndpdmcolgap_lst.do")    
	public String goMtNonStndPdmColGapLst() {
		return "/dq/gap/mtnonstndpdmcolgap_lst";  
	}
	
	@RequestMapping("/dq/gap/getMtNonStndGovColGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getMtNonStndGovColGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("{}", search);
		List<ModelGapVO> list = gapMainService.getMtNonStndGovColGapList(search); 
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	@RequestMapping("/dq/gap/getMtNonStndPdmColGapList.do")
	@ResponseBody
	public IBSheetListVO<ModelGapVO> getMtNonStndPdmColGapList(@ModelAttribute ModelGapVO search) {
		logger.debug("search:{}", search);
		List<ModelGapVO> list = gapMainService.getMtNonStndPdmColGapList(search); 
		
		return new IBSheetListVO<ModelGapVO>(list, list.size());
	}
	
	@RequestMapping("/dq/gap/mtmdldevdbgap_lst.do")    
	public String goMtMdlDevDbGapLst() {
		return "/dq/gap/mtmdldevdbgap_lst";  
	}
	
    @RequestMapping("/dq/gap/getMdlDbTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDbTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        
        List<ModelGapVO> list = gapMainService.getMdlDevDbTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
	
    @RequestMapping("/dq/gap/getGovDbTblGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getGovDbTblGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("GovTbTblGapList{}", search);
        
        List<ModelGapVO> list = gapMainService.getGovDevDbTblGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
	
    @RequestMapping("/dq/gap/getMdlDbColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getMdlDbColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getMdlDevDbColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    @RequestMapping("/dq/gap/getGovDbColGapList.do")
    @ResponseBody
    public IBSheetListVO<ModelGapVO> getGovDbColGapList(@ModelAttribute ModelGapVO search) {
        logger.debug("{}", search);
        List<ModelGapVO> list = gapMainService.getGovDevDbColGapList(search);
        
        return new IBSheetListVO<ModelGapVO>(list, list.size());
    }
    
    @RequestMapping("/dq/gap/wdqdbcmtagap_lst.do")  
    public String goMnDbSrcTgtTblGapLst() {
        
        return "/dq/gap/wdqdbcmtagap_lst";  
    }
}
