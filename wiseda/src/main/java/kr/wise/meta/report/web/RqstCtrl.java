package kr.wise.meta.report.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.report.service.RqstReportService;
import kr.wise.meta.report.service.RqstReportVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : RqstCtrl.java
 * 3. Package  : kr.wise.meta.report.web
 * 4. Comment  : 
 * 5. 작성자   : jyson
 * 6. 작성일   : 2014. 7. 15. 오후 2:05:57
 * </PRE>
 */
@Controller
public class RqstCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private RqstReportService rqstReportService;
	
	
	/** 기간별 요청서 처리현황 페이지 */
	/** @return jyson */
	@RequestMapping("/meta/report/periodRqst_lst.do")
	public String goPeriodRqst(){
		return "/meta/report/periodRqst_lst";
	}
	
	/**기간별 요청서 처리현황 조회 */
	@RequestMapping("/meta/report/getPeriodRqst.do")
	@ResponseBody
	public IBSheetListVO<RqstReportVO> getPeriodRqst(@ModelAttribute RqstReportVO search) throws Exception{
		logger.debug("{}", search);
	
		List<RqstReportVO> list = rqstReportService.getPeriodRqst(search);
		
		return new IBSheetListVO<RqstReportVO>(list, list.size());
	}
	
	/** DBA DDL요청서 처리현황 */
	@RequestMapping("/meta/report/ddlRqst_lst.do")
	public String goDdlRqst(){
		return "/meta/report/ddlRqst_lst";
	}
	
	/**DBA DDL요청서 처리현황 조회 - 월별 DDL요청서 처리현황 */
	@RequestMapping("/meta/report/getDdlRqstPeriod.do")
	@ResponseBody
	public IBSheetListVO<RqstReportVO> getDdlRqstPeriod(@ModelAttribute RqstReportVO search) throws Exception{
		logger.debug("{}", search);
	
		List<RqstReportVO> list = rqstReportService.getDdlRqstPeriod(search);
		
		return new IBSheetListVO<RqstReportVO>(list, list.size());
	}
	
	/**DBA DDL요청서 처리현황 조회 - 업무별 DDL요청서 처리현황 */
	@RequestMapping("/meta/report/getDdlRqstBiz.do")
	@ResponseBody
	public IBSheetListVO<RqstReportVO> getDdlRqstBiz(@ModelAttribute RqstReportVO search) throws Exception{
		logger.debug("{}", search);
	
		List<RqstReportVO> list = rqstReportService.getDdlRqstBiz(search);
		
		return new IBSheetListVO<RqstReportVO>(list, list.size());
	}
	
	/**DBA DDL요청서 처리현황 조회 - 부서별 DDL요청서 처리현황 */
	@RequestMapping("/meta/report/getDdlRqstDept.do")
	@ResponseBody
	public IBSheetListVO<RqstReportVO> getDdlRqstDept(@ModelAttribute RqstReportVO search) throws Exception{
		logger.debug("{}", search);
	
		List<RqstReportVO> list = rqstReportService.getDdlRqstDept(search);
		
		return new IBSheetListVO<RqstReportVO>(list, list.size());
	}
	
	/**DBA DDL요청서 처리현황 조회 - DB별 DDL요청서 처리현황 */
	@RequestMapping("/meta/report/getDdlRqstDb.do")
	@ResponseBody
	public IBSheetListVO<RqstReportVO> getDdlRqstDb(@ModelAttribute RqstReportVO search) throws Exception{
		logger.debug("{}", search);
	
		List<RqstReportVO> list = rqstReportService.getDdlRqstDb(search);
		
		return new IBSheetListVO<RqstReportVO>(list, list.size());
	}
	
	//메인화면 요청현황 차트 (Json)
	@RequestMapping("/meta/report/getRqstStatusList.do")
	@ResponseBody
	public List<RqstReportVO> RqstChartJson() throws Exception{

		List<RqstReportVO> list = rqstReportService.getRqstStatusList() ;

		return list;
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		
		return codeMap;
	}
}
