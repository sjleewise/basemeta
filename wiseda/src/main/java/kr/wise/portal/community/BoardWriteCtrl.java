package kr.wise.portal.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/community")
public class BoardWriteCtrl {

	@RequestMapping("/BoardWriteCtrl.do")
	public ModelAndView writeboardView(){
		
		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/community/writeboard");
        
		return mv;
	}
}
