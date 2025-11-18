package kr.wise.portal.myjob;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/myjob")
public class ScmjobCtrl {
	
	@RequestMapping("/ScmJobCtrl.do")
	public ModelAndView scmjobView(){
		
		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/myjob/scmjob");
        
		return mv;
	}

}
