package kr.wise.dq.stnd.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.stnd.service.WdqStndDicService;
import kr.wise.dq.stnd.service.WdqmStwd;

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
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 13. 오후 8:46:36
 * </PRE>
 */
@Controller
@RequestMapping("/dq/stnd/*")
public class WdqStndDicCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqStndDicService WdqStndDicService;

	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	/** 사전통합 조회 @return insomnia */
	@RequestMapping("getstndlist.do")
	@ResponseBody
	public IBSheetListVO<WdqmStwd> getStndList(@ModelAttribute WdqmStwd data, Locale locale) {
		
		log.debug(" {} "+data);
		//전체조회
		List<WdqmStwd> list = WdqStndDicService.getStndList(data);
		// 페이징 처리
		//return DATA SET
//		List<WdqmStwd> rtnList = new ArrayList<WdqmStwd>();
		
//		int startrow,endrow;
//		startrow = ((data.getPageNum()-1) * data.getOnePageRow()) + 1;
//		endrow = startrow + (data.getOnePageRow()-1);
//
//		for(int i = (startrow-1); i <= endrow; i++){
//			if(i == list.size()){  break; }
//			rtnList.add(list.get(i));
//		}

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		// 페이징 처리
//		return new IBSheetListVO<WdqmStwd>(rtnList, list.size());
		return new IBSheetListVO<WdqmStwd>(list, list.size());

	}

}
