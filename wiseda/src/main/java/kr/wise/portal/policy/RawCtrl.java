package kr.wise.portal.policy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/policy")
public class RawCtrl {

	@RequestMapping("/RawCtrl.do")
	public String rawView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/policy/raw");

		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000001";
	}
}
