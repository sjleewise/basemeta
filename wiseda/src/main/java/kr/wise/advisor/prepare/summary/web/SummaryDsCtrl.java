/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : SummaryDsCtrl.java
 * 2. Package : kr.wise.advisor.prepare.summary.web
 * 3. Comment : 테이블 summary 처리 컨트롤
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:21:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.summary.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.domain.service.DomainPredictService;
import kr.wise.advisor.prepare.domain.service.WadDmnPdt;
import kr.wise.advisor.prepare.summary.service.SummaryService;
import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgColVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.meta.model.service.WaqPdmTbl;

/**
 * <PRE>
 * 1. ClassName : SummaryDsCtrl
 * 2. FileName  : SummaryDsCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.summary.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class SummaryDsCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;

	/*@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;*/
	
	@Inject
	private SummaryService summaryService;
	
	//사용자정의 유효값
	static class AnaTrgTblVOs extends HashMap<String, ArrayList<AnaTrgTblVO>> { }
	static class WadAnaVars extends HashMap<String, ArrayList<WadAnaVar>> { }
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** 테이블 summary by DataSet...
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/summary/regSummaryDataset.do")
	@ResponseBody
	public IBSResultVO<CommonVo> regSummaryDs(WadDataSet search, Locale locale) throws Exception {
		logger.debug("테이블 summary by DataSet:{}", search);
		
		int result = 0;
		//데이터셋 id 체크 후 
		if (StringUtils.hasText(search.getDaseId())) {
			result = summaryService.regSummarybyDs(search);
		} else {
			result = -999; //데이터셋 id 미존재....
		}
		
		
		String resmsg;

		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "summary 저장을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터셋이 없습니다.<br>데이터셋을 등록 후 summary 조회가 가능합니다.";
		}
		else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "summary 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/summary/gethistodtl.do")
	@ResponseBody
	public IBSheetListVO<WadVarSum> getVarSummary(WadAnaVar search) {
		logger.debug("변수 히스토그램 조회:{}", search.getAnlVarId());
		
		List<WadVarSum> list =  summaryService.getHistoDtl(search);
		
		return new IBSheetListVO<WadVarSum>(list, list.size());
	}
	
	@RequestMapping("/advisor/prepare/summary/reghistogram.do")
	@ResponseBody
	public IBSResultVO<WadAnaVar> regHistogram(WadAnaVar search) {
		logger.debug("변수 히스토그램 저장 by 변수ID:{}", search);
		
		int result = 0;
		
		//변수 id 체크 후 
		if (StringUtils.hasText(search.getAnlVarId())) {
			result = summaryService.regHistobyVar(search);
		} else {
			result = -999; //데이터셋 id 미존재....
		}
		
		String resmsg;

		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "histogram 저장을 완료했습니다.";
		} else if (result == -999) {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "데이터셋이 없습니다.<br>데이터셋을 등록 후 histogram 조회가 가능합니다.";
		}
		else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "histogram 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		
		return new IBSResultVO<WadAnaVar>(search, result, resmsg, action);
	}
	

	

}
