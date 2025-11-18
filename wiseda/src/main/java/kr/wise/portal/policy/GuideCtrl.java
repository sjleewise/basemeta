package kr.wise.portal.policy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/policy")
public class GuideCtrl {

	@RequestMapping("/GuideCtrl.do")
	public String guideView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/policy/guide");

        return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000002";
	}

}
