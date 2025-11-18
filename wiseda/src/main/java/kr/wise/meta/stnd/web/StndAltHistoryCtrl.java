

package kr.wise.meta.stnd.web;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.meta.stnd.service.StndAltHistoryService;
import kr.wise.meta.stnd.service.WamStwd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDicCtrl.java
 * 3. Package  : kr.wise.meta.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 13. 오후 8:46:36
 * </PRE>
 */
@Controller
@RequestMapping("/meta/stnd/*")
public class StndAltHistoryCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private StndAltHistoryService stndAltHistoryService;

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	/** 사전변경이력 조회 @return insomnia */
	@RequestMapping("getaltlist.do")
	@ResponseBody
	public IBSheetListVO<WamStwd> getAltHistoryList(@ModelAttribute WamStwd data, Locale locale) {
		List<WamStwd> list = stndAltHistoryService.getAltHistoryList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamStwd>(list, list.size());

	}

}
