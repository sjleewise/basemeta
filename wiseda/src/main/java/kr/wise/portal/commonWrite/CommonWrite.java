package kr.wise.portal.commonWrite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/commonWrite")
public class CommonWrite {
	
	@RequestMapping("/CommonWriteCtrl.do")
	public ModelAndView dqdashView(){
		
		ModelAndView mv = new ModelAndView(); 
        mv.setViewName("/portal/common/commonWrite");
        
		return mv;
	}

}
