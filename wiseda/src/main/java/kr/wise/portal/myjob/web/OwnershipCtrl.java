package kr.wise.portal.myjob.web;

import java.util.HashMap;
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
import kr.wise.commons.helper.grid.JqGridVO;
import kr.wise.portal.myjob.service.OwnShipService;
import kr.wise.portal.myjob.service.TblChangeVO;

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
public class OwnershipCtrl {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private OwnShipService ownshipService;
	
	@RequestMapping("/OwnShipCtrl.do")
	public ModelAndView ownershipView(@RequestParam Map<String, Object> param,  @ModelAttribute("searchVO") BoardVO boardVO)throws Exception{
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
		TblChangeVO resultTotCnt;
		List<TblChangeVO> result;
		ModelAndView mv = new ModelAndView();
		/*Map<String,Object> param = new HashMap<String,Object>();
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(WiseConfig.PAGEUNIT);
		paginationInfo.setPageSize(WiseConfig.PAGESIZE);
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		param.put("mngUserIdAf",request.getParameter("mngUserIdAf"));
		param.put("mngUserAf",request.getParameter("mngUserAf"));

		param.put("firstIndex", boardVO.getFirstIndex());
		param.put("recordCountPerPage", boardVO.getRecordCountPerPage());
		param.put("userid", user.getId());*/
		
		param.put("userid", user.getId());
		log.debug("param:{}", param);
		
		//데이터 리스트
//		result =  ownshipService.selectTblChangeParam(param);
		//데이터 갯수
//		resultTotCnt = ownshipService.selectTblChangeSub(param);
			
		//log.debug("tottal count [{}]", resultTotCnt.getTotCnt());  	
    	
		//데이터 리스트
		result =  ownshipService.selectTblChangeParam(param);
		int totCnt = 0;
		if(result != null) 
			totCnt = result.size();
		
		/*paginationInfo.setTotalRecordCount(resultTotCnt.getTotCnt());
    	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
    	String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);
*/
		mv.addObject("result", result);
//		mv.addObject("pageui", pageUi);
		mv.addObject("resultCnt", totCnt);
		mv.setViewName("/portal/myjob/ownership");
	    
		return mv;
	}
	
	/** 테이블 조회- jqgrid */
	@RequestMapping("/ajaxgrid/OwnShipList.do")
	@ResponseBody
	public JqGridListVO<TblChangeVO> selectApprReqList(@RequestParam Map<String, Object> param) throws Exception {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
		param.put("userid",user.getId());
		log.debug("param:{}", param);
		
		List<TblChangeVO> result = ownshipService.selectTblChangeParam(param);
		
		return new JqGridListVO<TblChangeVO>(result);
	}
	
	/** 테이블담당자 변경 - ajax 처리 */
	@RequestMapping("/ajaxgrid/OwnShipUpdate.do")
	@ResponseBody
	public JqGridVO<TblChangeVO> updateOwnShip(@RequestParam Map<String, Object> param) {
		
		log.debug("param:{}", param);
		
		int result = ownshipService.ownerShipUpdate(param);
		
		return new JqGridVO<TblChangeVO>(null, result, "", "U");
	}
	
	//담당자ID 및 담당자 변경
	@RequestMapping("/OwnShipUpdateCtrl.do")
	public String ownershipUpdate(HttpServletRequest request)throws Exception{
		
		String[] arrObjId = request.getParameterValues("objId");
		String sObjId = "";
		
		for(int i=0;i<arrObjId.length;i++){
			sObjId += ",'"+arrObjId[i]+"'";
		}
		if(!sObjId.equals("")) {
			sObjId = sObjId.substring(1,sObjId.length());
		}
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("mngUserIdAf",request.getParameter("mngUserIdAf"));
		param.put("mngUserAf",request.getParameter("mngUserAf"));
		param.put("objId", sObjId);
		ownshipService.ownershipUpdate(param);
        
//		return "forward:/portal/myjob/OwnShipSubCtrl.do";
		return "redirect:/portal/myjob/OwnShipCtrl.do";
	}
	
	//update 후 조회
	@RequestMapping("/OwnShipSubCtrl.do")
	public ModelAndView OwnShipSubCtrl(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO)throws Exception{
		
		TblChangeVO resultTotCnt;
		List<TblChangeVO> result;
		List<TblChangeVO> resultUser;
		ModelAndView mv = new ModelAndView();
		Map<String,Object> param = new HashMap<String,Object>();
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(WiseConfig.PAGEUNIT);
		paginationInfo.setPageSize(WiseConfig.PAGESIZE);
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		param.put("mngUserIdAf",request.getParameter("mngUserIdAf"));
		param.put("mngUserAf",request.getParameter("mngUserAf"));
		param.put("objId", request.getParameter("objId"));

		param.put("firstIndex", boardVO.getFirstIndex());
		param.put("recordCountPerPage", boardVO.getRecordCountPerPage());
		
		//변경 후 데이터 리스트
		result =  ownshipService.selectTblUpdateParam(param);
		//변경 후 데이터 갯수
		resultTotCnt = ownshipService.selectTblUpdateSub(param);
		
		paginationInfo.setTotalRecordCount(resultTotCnt.getTotCnt());
    	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
    	String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);

		mv.addObject("result", result);
		mv.addObject("pageui", pageUi);
		mv.setViewName("/portal/myjob/ownership");
	    
		return mv;
		
	}
	
	
}
