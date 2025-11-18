package kr.wise.meta.aocd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.aocd.service.PusAocdService;
import kr.wise.meta.aocd.service.PusAocdVO;

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
 * 2. FileName  : PusAocdCtrl.java
 * 3. Package  : kr.wise.meta.aocd.web
 * 4. Comment  : 
 * 5. 작성자   : jyson(손준영)
 * 6. 작성일   : 2014. 7. 23. 오후 3:35:14
 * </PRE>
 */


@Controller
public class PusAocdCtrl {

Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private PusAocdService pusAocdService;
	
	/**표준단어 활용도 분석 페이지*/
	/**  @return jyson */
	@RequestMapping("/meta/aocd/stndWordPusAocd_lst.do")
	public String goStndWordPusAocd(){
		return "/meta/aocd/stndWordPusAocd_lst";
	}
	
	/**표준단어 활용도 분석 조회 */
	@RequestMapping("/meta/aocd/getStndWordPusAocd.do")
	@ResponseBody
	public IBSheetListVO<PusAocdVO> getStndWordPusAocd(@ModelAttribute PusAocdVO search){
		logger.debug("{}", search);
		List<PusAocdVO> list = pusAocdService.getStndWordPusAocd(search);
		return new IBSheetListVO<PusAocdVO>(list,list.size());
	}
		
	/**도메인 활용도 분석 페이지*/
	@RequestMapping("/meta/aocd/dmnPusAocd_lst.do")
	public String goDmnPusAocd(){
		return "/meta/aocd/dmnpusaocd_lst";
	}
	
	/**도메인 활용도 분석 조회*/
	@RequestMapping("/meta/aocd/getDmnPusAocd.do")
	@ResponseBody
	public IBSheetListVO<PusAocdVO> getDmnPusAocd(@ModelAttribute PusAocdVO search){
		logger.debug("{}",search);
		List<PusAocdVO> list = pusAocdService.getDmnPusAocd(search);
		return new IBSheetListVO<PusAocdVO>(list,list.size());
	}
	
	/**항목 활용도 분석 페이지*/
	@RequestMapping("/meta/aocd/itmPusAocd_lst.do")
	public String goItmPusAocd(){
		return "/meta/aocd/itmpusaocd_lst";
	}
	
	/**항목 활용도 분석 조회*/
	@RequestMapping("/meta/aocd/getItmPusAocd.do")
	@ResponseBody
	public IBSheetListVO<PusAocdVO> getItmPusAocd(@ModelAttribute PusAocdVO search){
		logger.debug("{}",search);
		List<PusAocdVO> list = pusAocdService.getItmPusAocd(search);
		return new IBSheetListVO<PusAocdVO>(list,list.size());
	}
	
	/**테이블 활용도 분석 페이지*/
	@RequestMapping("/meta/aocd/tblPusAocd_lst.do")
	public String goTblPusAocd(){
		return "/meta/aocd/tblpusaocd_lst";
	}
	
	/**테이블 활용도 분석 조회*/
	@RequestMapping("/meta/aocd/getTblPusAocd.do")
	@ResponseBody
	public IBSheetListVO<PusAocdVO> getTblPusAocd(@ModelAttribute PusAocdVO search){
		logger.debug("{}",search);
		List<PusAocdVO> list = pusAocdService.getTblPusAocd(search);
		return new IBSheetListVO<PusAocdVO>(list,list.size());
	}
	
	/**테이블 활용도 whereused 조회*/
	@RequestMapping("/meta/aocd/ajaxgrid/tblwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<PusAocdVO> getTblPusAocdWhereused(@ModelAttribute PusAocdVO search){
		logger.debug("{}",search);
		List<PusAocdVO> list = pusAocdService.getTblPusAocdWhereused(search);
		return new IBSheetListVO<PusAocdVO>(list,list.size());
	}
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		//업무구분코드
	//	codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
	//	codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		
		return codeMap;
	}
}
