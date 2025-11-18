package kr.wise.portal.dqms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dqms")
public class IntroCtrl {

	@RequestMapping("/IntroCtrl.do")
	public ModelAndView introView() {
		
		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/dqms/intro");
        
		return mv;
	}
}
