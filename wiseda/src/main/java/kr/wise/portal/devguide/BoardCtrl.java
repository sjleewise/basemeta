package kr.wise.portal.devguide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/devguide")
public class BoardCtrl {

	@RequestMapping("/board.do")
	public String boardView(){
		
//		ModelAndView mv = new ModelAndView(); 
//        mv.setViewName("/portal/devguide/board");
        
		return "forward:/commons/bbs/selectBoardList.do?bbsId=BBSMSTR_000000000006";
	}
}
