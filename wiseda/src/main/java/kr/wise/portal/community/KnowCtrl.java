package kr.wise.portal.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/community")
public class KnowCtrl {

	@RequestMapping("/KnowCtrl.do")
	public String knowView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/community/know");

		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000008";
	}
}
