package kr.wise.portal.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/community")
public class ToctocCtrl {

	@RequestMapping("/ToctocCtrl.do")
	public String toctocView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/community/toctoc");
//
		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000009";
	}
}
