package kr.wise.portal.myjob.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.dq.measure.bizrule.service.DqJobService;
import kr.wise.dq.measure.bizrule.service.DqJobVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/myjob")
public class DqjobCtrl {
	
    private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqJobService dqjobService;

	@RequestMapping("/DqJobCtrl.do")
	public ModelAndView dqjobView(@RequestParam Map<String, Object> param) throws Exception{
		
        LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		
		param.put("userId", user.getId());
		
		log.debug("param : [{}]  ", param);
		ModelAndView mv = new ModelAndView(); 
		
		List<DqJobVO> dbmsEnmCode = dqjobService.selectDbmsEnmCode();
		List<DqJobVO> errStatusImpStatusCode = dqjobService.selectErrStatusImpStatusCode();
//		List<DqJobVO> result = dqjobService.selectDqJobList(param);
		
//		int totCnt = 0;
//		if(result != null){ 
//			totCnt = result.size();
//		}
		
		mv.addObject("param", param);
		mv.addObject("dbmsEnmCode", dbmsEnmCode);
		mv.addObject("errStatusImpStatusCode", errStatusImpStatusCode);
//		mv.addObject("result", result);
//		mv.addObject("totCnt", totCnt);
        mv.setViewName("/portal/myjob/dqjob");
        
		return mv;
	}
	
	
	/** 프로그램 본수-ajaxgrid */
	@RequestMapping("/ajaxgrid/dqJobList.do")
	@ResponseBody
	public IBSheetListVO<DqJobVO> selectProgramBonList(@ModelAttribute DqJobVO search) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		
		search.setUserId(user.getUniqId());
		
		
		List<DqJobVO> result = dqjobService.selectDqJobList(search);

		return new IBSheetListVO<DqJobVO>(result, result.size());
	}
}
