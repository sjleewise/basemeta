package kr.wise.portal.policy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/policy")
public class Iso9001Ctrl {

	@RequestMapping("/Iso9001Ctrl.do")
	public String iso9001View(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/policy/iso9001");

		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000003";
	}
}
