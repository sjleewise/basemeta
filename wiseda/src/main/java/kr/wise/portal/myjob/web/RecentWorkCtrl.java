package kr.wise.portal.myjob.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.util.UtilString;
import kr.wise.portal.myjob.service.RecentWorkService;
import kr.wise.portal.myjob.service.RecentWorkVO;

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
public class RecentWorkCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private RecentWorkService recentWorkService;
	
	
	@RequestMapping("/recentWork.do")
	public String svnWorkView(@ModelAttribute("searchVO") SearchVO searchVo) {
		
		
		
		return "/portal/myjob/RecentWork";
	}
	
	//최근작업내역 리스트 추출
	@RequestMapping("/ajax/recentWork.do")
	public ModelAndView recntWorkView(@RequestParam Map<String, Object> param, @ModelAttribute("searchVO") SearchVO searchVO) throws Exception {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		/*List<RecentWorkVO> resultTotCnt;*/
		List<RecentWorkVO> result;
		RecentWorkVO vo;
		ModelAndView mv = new ModelAndView();
		/*Map<String,String> param = new HashMap<String,String>();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(WiseConfig.PAGEUNIT);
		paginationInfo.setPageSize(WiseConfig.PAGESIZE);		
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		*/
		
		//param.put("usId", user.getId());
/*    	if(request.getParameter("ntceBgnde").equals("")) {
			param.put("searchDateStart","NULL");
		}	
    	if(request.getParameter("ntceEndde").equals("")) {
			param.put("searchDateEnd","NULL");
		}*/
		param.put("usId", user.getId());
		
		String svnUserid =  recentWorkService.selectsvnUserId(param);
		
		log.debug("svnUserID:{}", svnUserid);
		
		//param.put("usId", user.getId());
		//param.put("usId", "2007121411305166"); 
		

/*			param.put("searchDateStart",request.getParameter("ntceBgnde")); //요청일자 Start
			param.put("searchDateEnd",request.getParameter("ntceEndde"));	//요청일자 END
*/	/*	param.put("prjId",request.getParameter("workGB"));	// 업무구분
*/		/*param.put("formId",request.getParameter("reqGB"));	//요청서구분
*/				
		/*resultTotCnt = recentWorkService.selectRecentWorkTotal(param);*/
		
//    	Map<String,Object> param1 = new HashMap<String,Object>();
    	
    	param.put("perPage", 100);
    	param.put("pageNum", 1);
/*		param1.put("firstIndex", boardVO.getFirstIndex());
		param1.put("recordCountPerPage", boardVO.getRecordCountPerPage());*/
		
    	
/*    	if(request.getParameter("ntceBgnde") != null) {		//요청일자 Start
    		if(!request.getParameter("ntceBgnde").equals("")) {
    			param1.put("searchDateStart",request.getParameter("ntceBgnde"));
    		}
    	}
    	if(request.getParameter("ntceEndde") != null) {		////요청일자 END
    		if(!request.getParameter("ntceBgnde").equals("")) {
    			param1.put("searchDateEnd",request.getParameter("ntceEndde"));
    		}
    	}
    	if(request.getParameter("workGB") != null) {			//업무구분
    		if(!request.getParameter("workGB").equals("")) {
    			param1.put("prjId",request.getParameter("workGB"));
    		}
    	}
    	if(request.getParameter("reqGB") != null) {				//요청서구분
    		if(!request.getParameter("reqGB").equals("")) {
    			param1.put("formId",request.getParameter("reqGB"));
    		}
    	}*/
    	/*param1.put("searchDateStart",request.getParameter("ntceBgnde")); 
		param1.put("searchDateEnd",request.getParameter("ntceEndde"));	
*/		
//    	param.put("svnUserId", "2007121411305166");	
    	param.put("svnUserId", UtilString.null2Blank(svnUserid));	
    	log.debug("para:{}", param);
//		param1.put("usId", vo.getUsId());	
		/*param1.put("prjId",request.getParameter("workGB"));	// 업무구분
		 param1.put("formId",request.getParameter("reqGB"));	//요청서구분
*/		
    	
		result =  recentWorkService.selectRecentWork(param);
		int totalcnt = 0;
		if(result.size() > 0)
			totalcnt = Integer.parseInt(result.get(0).getTotalCnt());
			
		/*paginationInfo.setTotalRecordCount(totalcnt);
		DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
		String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);*/

/*		List<RecentWorkVO> listRecentWork = recentWorkService.selectRecentWork(map);
		mv.addObject("listRecentWork", listRecentWork);*/
		
		
		mv.addObject("resultList", result);		
//		mv.addObject("result", param1);		
		mv.setViewName("/portal/myjob/RecentWork");
//		mv.addObject("pageui", pageUi);
		mv.addObject("resultCnt", totalcnt);
		return mv;
	}
	
	@RequestMapping("/ajaxgrid/recentWorkList.do")
	@ResponseBody
	public JqGridListVO<RecentWorkVO> selectApprReqList(@RequestParam Map<String, Object> param) throws Exception {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
		param.put("userId",user.getId());
		
		String svnUserid =  recentWorkService.selectsvnUserId(param);
		param.put("svnUserId", UtilString.null2Blank(svnUserid));
		
//		param.put("svnUserId", "2007121411305166"); 
		
		param.put("perPage", 100);
    	param.put("pageNum", 1);
    	log.debug("para:{}", param);
		
		
		List<RecentWorkVO> result = recentWorkService.selectRecentWork(param);
		
		return new JqGridListVO<RecentWorkVO>(result);
	}
	
/*	@RequestMapping("/recentWorkUser.do")
	public ModelAndView searchUser(HttpServletRequest request) throws Exception {
	
		ModelAndView mv = new ModelAndView();
		Map<String, String> map = new HashedMap();
		
		map.put("usId", request.getParameter("usId"));
		
		List<RecentWorkVO> userSearch = recentWorkServiceSub.selectUserId(map);
		mv.addObject("userSearch", userSearch);
		
		mv.setViewName("/portal/myjob/RecentWork");
		return mv;
		
	}*/
}
