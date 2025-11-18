package kr.wise.portal.devguide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/devguide")
public class ReportregCtrl {

	@RequestMapping("/reportreg.do")
	public String reportregView(){
//
//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/devguide/reportreg");
//
		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000005";
	}
}
