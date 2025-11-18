package kr.wise.portal.dqms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dqms")
public class ProcessCtrl {

	@RequestMapping("/ProcessCtrl.do")
	public ModelAndView processView(){
		
		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/dqms/process");
        
		return mv;
		
	} 
	
}
