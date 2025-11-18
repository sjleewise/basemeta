package kr.wise.portal.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/community")
public class QnaCtrl {

	@RequestMapping("/QnaCtrl.do")
	public String qnaView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/community/qna");

		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000010";
	}

}
