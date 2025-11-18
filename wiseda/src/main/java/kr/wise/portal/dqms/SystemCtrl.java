package kr.wise.portal.dqms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dqms")
public class SystemCtrl {
 
	@RequestMapping("/SystemCtrl.do")
	public ModelAndView systemView(){

		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/dqms/system");
        
		return mv;
	}
	
}
