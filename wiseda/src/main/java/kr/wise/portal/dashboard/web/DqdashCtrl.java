package kr.wise.portal.dashboard.web;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.dashboard.service.DqDashService;
import kr.wise.dq.dashboard.service.DqdashSystemVO;
import kr.wise.dq.dashboard.service.PieChartVO;
import kr.wise.dq.dashboard.service.WidgetsChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dashboard")
public class DqdashCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqDashService dqdashService;
	
	
	@RequestMapping("/DqDashCtrl.do")
	public ModelAndView dqdashView() throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		/* 시스템별 데이터품질 측정결과 */
		List<DqdashSystemVO> result = dqdashService.getDataDQList();
		/* 업무팀별 데이터품질 개선활동 진행현황  */
		List<DqdashSystemVO> resultTeam = dqdashService.getJobTeamDQList();
				
		mv.addObject("result", result);
		mv.addObject("resultTeam", resultTeam);
		mv.setViewName("/portal/dashboard/dqdash");
        return mv;
	}
	
	
	//파워차트호출 메서드
	@RequestMapping("/widgetsChartList.do")
	public ModelAndView widgetsChartList()throws Exception{
		
		List<WidgetsChartVO> list = dqdashService.selectWidgetsChart() ;
		
		ModelAndView mv = new ModelAndView(); 
		mv.addObject("list", list);
		
        mv.setViewName("/portal/dashboard/widgetsChart");
        
		return mv;
	}
	
	//메인화면 업무영역별 품질현황 (Json)
	@RequestMapping("/widgetsChartListJson.do")
	@ResponseBody
	public List<WidgetsChartVO> widgetsChartListJson() throws Exception{

		List<WidgetsChartVO> list = dqdashService.selectWidgetsChart() ;

		return list;
	}
	
	//파이차트호출 메서드
	@RequestMapping("/pieChart.do")
	public ModelAndView pieChart()throws Exception{
		
		ModelAndView mv = new ModelAndView();
		List<PieChartVO> listPie = dqdashService.selectPieChart();
		
		 
		mv.addObject("listPie", listPie);
		mv.setViewName("/portal/dashboard/pieChart");
        
		return mv;
	}
	
}
