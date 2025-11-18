package kr.wise.advisor.prepare.outlier.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

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

import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.outlier.service.OutlierService;
import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.advisor.prepare.summary.service.SummaryService;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.report.service.DataPatternVO;



/**
 * <PRE>
 * 1. ClassName : OutlierDetectView
 * 2. FileName  : OutlierDetectView.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.web
 * 4. Comment  : 이상값 탐지에 이용되는 뷰 컨트롤러....
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 15. 오후 1:27:34
 * </PRE>
 */ 
@Controller("OutlierDetectView") 
public class OutlierDetectView { 
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private SummaryService summaryService;

	@Inject
	private OutlierService outlierService;
	
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);
		codeMap.put("connTrgDbmsCd", connTrgDbms);
				
		//스키마
//		List<CodeListVo> schDbSchNm = codelistService.getCodeList(CodeListAction.connTrgSch);
//		codeMap.put("schDbSchNm", UtilJson.convertJsonString(schDbSchNm));
		
		//진단대상, 스키마 Double Select
		String connTrgSch = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("schDbSchId", connTrgSch);
		
		//OD 알고리즘 코드
		List<CodeListVo> otlAlgCd = codelistService.getCodeList("OTL_ALG_ID");
		codeMap.put("otlAlgCd", otlAlgCd);
		
		//품질지표
		//List<CodeListVo> dqiCd = codelistService.getCodeList("OTL_DQI_MAP");
		//codeMap.put("dqiPop", UtilJson.convertJsonString(getComboText(dqiCd)));
				
		//알고리즘 유형
		codeMap.put("algTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ALG_TYP_CD")));
		
		return codeMap;
	}
	
	/*private ComboIBSVo getComboText(List<CodeListVo> dqiCd) {
		ComboIBSVo comboIbs = new ComboIBSVo();
		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

		List<CodeListVo> codeList = dqiCd;

		int i = 0;
		for (CodeListVo vo : codeList) {
			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getCodeCd());
			text.append(vo.getCodeLnm());
		}

		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();
		
		return comboIbs;
	}*/
	
	
	/** 이상값 탐지 요청화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/outlier/outlier_rqst.do")
	public String goOutlierDetection(@ModelAttribute("search") CommonVo search) {
		logger.debug("이상값 탐지 요청화면:{}", search);
		
		return "/advisor/prepare/outlier/outlier_rqst";
	}

	@RequestMapping("/advisor/prepare/outlier/getoutlierparamlist.do")
	public String getOutlierParam(@ModelAttribute("search") WaaAlg search, Model model) {
		logger.debug("이상값탐지 변수목록 by 알고리즘:{}", search);
		
		List<WaaAlgArg> list = outlierService.getAlgParam(search);
		
		model.addAttribute("alGarglist", list);
		
		return "/advisor/prepare/outlier/outlierarg_dtl";
	}

	@RequestMapping("/advisor/prepare/outlier/getoutlierparambyid.do")
	public String getOutlierParambyId(@ModelAttribute("search") WadOtlResult search, Model model) {
		logger.debug("이상값탐지 변수목록 by ID:{}", search);
		
		List<WaaAlgArg> list = outlierService.getAlgParamById(search);
		
		model.addAttribute("alGarglist", list);
		
		return "/advisor/prepare/outlier/outlierarg_dtl";
	}

	/** 이상값 탐지 결과화면
	/** @return insomnia */
	@RequestMapping("/advisor/prepare/outlier/outlier_lst.do")
	public String goOutlierResult(@ModelAttribute("search") CommonVo search) {
		logger.debug("이상값 탐지 결과화면:{}", search);
		
		return "/advisor/prepare/outlier/outlier_lst";
	}

	/** @return insomnia */
	@RequestMapping("/advisor/prepare/outlier/outlierprf_lst.do")
	public String goOutlierProfile(@ModelAttribute("search") CommonVo search) {
		logger.debug("이상값 탐지 프로파일 화면:{}", search);
		
		return "/advisor/prepare/outlier/outlierprf_lst";
	}
	
	
	//데이터 패턴 팝업이동
	@RequestMapping("/advisor/prepare/outlier/popup/outlierdata_pop.do")
	public String goOutlierDataPop(@ModelAttribute("search") DataPatternVO search, @ModelAttribute("otlVO") WadOtlResult resvo, Model model, Locale locale) throws Exception {
		logger.debug("DataPatternVO:{}", search);
		logger.debug("WadOtlResult:{}", resvo);

		int headerCnt = 0;
		String headerText = new String();
		
		List<WadOtlData> otldatalist = outlierService.getOutDataColNm(search);
//		Class<WadOtlData> cls = WadOtlData.class;

		if( null != otldatalist && !otldatalist.isEmpty()){
			WadOtlData otldatavo = otldatalist.get(0);
			
			headerCnt = otldatavo.getColCnt()  ;

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;

			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					//tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
					Method colGetter = otldatavo.getClass().getMethod("getColNm"+i);
//					String colnm = colGetter.invoke(otldatavo).toString();
					tmpColNm.append(colGetter.invoke(otldatavo).toString()+"|");
				}
//				headerColNm = tmpColNm.toString();
//				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));

				//IBSHEET 헤더텍스트 조회
				headerText = tmpColNm.substring(0, tmpColNm.length()-1);
			}

			search.setColNm(colNm.toString());
			
//			model.addAttribute("otlVO", resvo);
		} else {
			otldatalist = outlierService.getOutDataAnlVarId(search);
		}
		
		
		search.setColCnt(headerCnt);
		search.setHeaderText(headerText);

		
		model.addAttribute("headerVO", search);
		model.addAttribute("colist", otldatalist);
		
		
		return "/advisor/prepare/outlier/popup/outlierdata_pop"; 
	}
	
}
