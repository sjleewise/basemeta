/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DataSetCtrl.java
 * 2. Package : kr.wise.advisor.prepare.dataset.web
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 20. 오전 9:31:32
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 20. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.dataset.web;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.DataSetNstndService;
import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstnd;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.dataset.service.WadDataSetNstnd;
import kr.wise.commons.helper.grid.IBSheetListVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DataSetCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.dataset.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 20. 오전 9:31:32
 * </PRE>
 */
@Controller
public class DataSetCtrl {

	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private DataSetService dataSetService;
	
	@Inject
	private DataSetNstndService dataSetNstndService;

	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/advisor/prepare/dataset/getDataSetList.do")
	@ResponseBody
	public IBSheetListVO<WadDataSet> getDataSetList(WadDataSet search, Locale locale) {
		logger.debug("데이터셋조회:{}", search);
		List<WadDataSet> list = dataSetService.getDataSetList(search);
		return new IBSheetListVO<WadDataSet>(list, list.size());
	} 
	
	@RequestMapping("/advisor/prepare/dataset/getAanVarList.do")
	@ResponseBody
	public IBSheetListVO<WadAnaVar> getAnaVarbyDs(WadDataSet search, Locale locale) {
		logger.debug("도메인판별조회 by 데이터셋:{}", search);
		List<WadAnaVar> list = dataSetService.getAnaVarbyDs(search);
		return new IBSheetListVO<WadAnaVar>(list, list.size());
	}
	
	@RequestMapping("/advisor/prepare/dataset/getDaseColList.do")
	@ResponseBody
	public IBSheetListVO<WadAnaVar> getDaseColList(WadDataSet search, Locale locale) {
		logger.debug("도메인판별조회 by 데이터셋:{}", search);
		List<WadAnaVar> list = dataSetService.getDaseColList(search);
		return new IBSheetListVO<WadAnaVar>(list, list.size());
	}
	
	@RequestMapping("/advisor/prepare/dataset/getDataSetNstndList.do")
	@ResponseBody
	public IBSheetListVO<WadDataSetNstnd> getDataSetNstndList(WadDataSetNstnd search, Locale locale) {
		logger.debug("데이터셋조회:{}", search);
		List<WadDataSetNstnd> list = dataSetNstndService.getDataSetNstndList(search);
		return new IBSheetListVO<WadDataSetNstnd>(list, list.size());
	} 
	@RequestMapping("/advisor/prepare/dataset/getAanVarNstndList.do")
	@ResponseBody
	public IBSheetListVO<WadAnaVarNstnd> getAnaVarNstndbyDs(WadDataSetNstnd search, Locale locale) {
		logger.debug("도메인판별조회 by 데이터셋:{}", search);
		List<WadAnaVarNstnd> list = dataSetNstndService.getAnaVarNstndbyDs(search);
		return new IBSheetListVO<WadAnaVarNstnd>(list, list.size());
	}
}
