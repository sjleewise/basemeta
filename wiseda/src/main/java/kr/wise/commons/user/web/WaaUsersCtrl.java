package kr.wise.commons.user.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.pagenation.DefaultPaginationRenderer;
import kr.wise.commons.cmm.pagenation.PaginationInfo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.user.service.WaaUsers;
import kr.wise.commons.user.service.WaaUsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/pop")
public class WaaUsersCtrl {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaUsersService waaUsersService;
	
	@RequestMapping("/popUserSearch.do")
	public ModelAndView ownershipView(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, @RequestParam Map<String,Object> param) throws Exception{
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
		ModelAndView mv = new ModelAndView();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(WiseConfig.PAGEUNIT);
		paginationInfo.setPageSize(WiseConfig.PAGESIZE);
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		param.put("firstIndex", boardVO.getFirstIndex());
		param.put("recordCountPerPage", boardVO.getRecordCountPerPage());
		
		List<WaaUsers> result = waaUsersService.selectUserList(param);
		int resultTotCnt = waaUsersService.selectTotCountList(param);
		
		paginationInfo.setTotalRecordCount(resultTotCnt);
    	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
    	String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);
				 
		mv.addObject("result", result);
		mv.addObject("pageui", pageUi);
		mv.setViewName("/portal/pop/popusersearch");
        
		return mv;
	}
	
	/** 사용자 조회팝업- jqgrid */
	@RequestMapping("/ajaxgrid/popUserSearch.do")
	@ResponseBody
	public JqGridListVO<WaaUsers> selectUserList(@RequestParam Map<String, Object> param) throws Exception {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
		param.put("userid",user.getId());
		log.debug("param:{}", param);
		
		List<WaaUsers> result = waaUsersService.selectUserList2(param);
		
		return new JqGridListVO<WaaUsers>(result);
	}

	
}