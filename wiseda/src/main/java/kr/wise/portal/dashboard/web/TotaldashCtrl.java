package kr.wise.portal.dashboard.web;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.dashboard.service.DbcAllErrChartVO;
import kr.wise.portal.dashboard.service.DqMainChartVO;
import kr.wise.portal.dashboard.service.StandardChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashService;
import kr.wise.portal.dashboard.service.UpdateCntVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dashboard")
public class TotaldashCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private TotalDashService totdashService;

	@RequestMapping("/TotDashCtrl.do")
	public ModelAndView totaldashView() throws Exception{
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		/* 표준데이터 조회 */
		List<TotalCountVO> result =  totdashService.selectTotCntWAMs(userid);
		 /* 데이터모델 조회 */
		List<UpdateCntVO> upresult = totdashService.selectUpdateCntStat(userid);

		ModelAndView mv = new ModelAndView();
		mv.addObject("result", result);
		mv.addObject("upresult", upresult);
        mv.setViewName("/portal/dashboard/totaldash");

		return mv;
	}

	/* 표준차트 조회(모델 vs DB 일치율) */
	@RequestMapping("/ErrChartCtrl.do")
	public ModelAndView selectErrChart()throws Exception{

		DbcAllErrChartVO errChartList = totdashService.selectErrChart();

		ModelAndView mv = new ModelAndView();
		mv.addObject("errChartList", errChartList);

		 mv.setViewName("/portal/dashboard/errChart");

		return mv;
	}

	/* 표준차트 조회(데이터 에러율) */
	@RequestMapping("/ErrChartSubCtrl.do")
	public ModelAndView selectDqErrChartList()throws Exception{

		List<DbcAllErrChartVO> errChartSubList = totdashService.selectDqErrChartList();

		ModelAndView mv = new ModelAndView();
		mv.addObject("errChartSubList", errChartSubList);

		mv.setViewName("/portal/dashboard/errChartSub");

		return mv;
	}

	//부서별 표준준수율 (기존)
	@RequestMapping("/standardChartList.do")
	public ModelAndView standardChartList()throws Exception{

		List<StandardChartVO> list = totdashService.selectDeptStandardChart() ;

		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);

        mv.setViewName("/portal/dashboard/standardChart");

		return mv;
	}




	//부서별 표준준수율 (Json)
	@RequestMapping("/standardChartListjson.do")
	@ResponseBody
	public List<StandardChartVO> standardChartListJson()throws Exception{

		List<StandardChartVO> list = totdashService.selectDeptStandardChart() ;

		return list;
	}

	/**
	 * @return meta
	 * @throws Exception 
	 * DQ메인화면 테이블 (Json) */
	@RequestMapping("/dqPrfTable.do")
	@ResponseBody
	public List<DqMainChartVO> prfCntListJson(@ModelAttribute WaaDbConnTrgVO vo){ 

		List<DqMainChartVO> list = totdashService.selectPrfCntByDbms(vo) ;
		
		log.debug("list:{}", list.toString());
		return list;
	}



}
