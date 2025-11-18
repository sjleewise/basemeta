package kr.wise.portal.devguide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/devguide")
public class DevguideCtrl {

	@RequestMapping("/devguide.do")
	public String devguideView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/devguide/devguide");
//
		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000004";
	}
}
