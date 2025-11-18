package kr.wise.portal.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/community")
public class NoticeCtrl {

	@RequestMapping("/NoticeCtrl.do")
	public String noticeView(){

//		ModelAndView mv = new ModelAndView();
//        mv.setViewName("/portal/community/notice");

		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000007";
	}
}
