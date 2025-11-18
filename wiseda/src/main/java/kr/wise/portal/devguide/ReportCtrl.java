package kr.wise.portal.devguide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/devguide")
public class ReportCtrl {

	@RequestMapping("/reportlist.do")
	public String reportlistView(){

//    	ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/devguide/reportlist");

//		return mv;
		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000005";
	}
}
