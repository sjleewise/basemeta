package kr.wise.portal.pop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/pop")
public class MainPopCtrl {

	//1번 팝업호출
	@RequestMapping("/mainPop1.do")
	public ModelAndView mainPop1View() throws Exception{
        ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/portal/pop/mainPop1");
        return mv;
	}
	//2번 팝업호출
	@RequestMapping("/mainPop2.do")
	public ModelAndView mainPop2View() throws Exception{
        ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/portal/pop/mainPop2");
        return mv;
	}
	//3번 팝업호출
	@RequestMapping("/mainPop3.do")
	public ModelAndView mainPop3View() throws Exception{
        ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/portal/pop/mainPop3");
        return mv;
	}
	//4번 팝업호출
	@RequestMapping("/mainPop4.do")
	public ModelAndView mainPop4View() throws Exception{
        ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/portal/pop/mainPop4");
        return mv;
	}
	//5번 팝업호출
	@RequestMapping("/mainPop5.do")
	public ModelAndView mainPop5View() throws Exception{
        ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/portal/pop/mainPop5");
        return mv;
	}
}
