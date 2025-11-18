package kr.wise.portal.totalsearch.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.HTMLInputFilter;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.portal.totalsearch.service.TotalSearch;
import kr.wise.portal.totalsearch.service.TotalSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TotalSearchCtrl {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private TotalSearchService totalSearchService;
	
	@RequestMapping("/portal/totalsearch/TotalSearchCtrl.do")
	public ModelAndView ownershipView(HttpServletRequest request, @RequestParam Map<String, String> param)throws Exception{
//		Map<String,String> param = new HashMap<String,String>();
//		String searchNm = request.getParameter("bfSearchNm");
		String searchNm = UtilString.null2Blank(param.get("bfSearchNm"));
		searchNm = new HTMLInputFilter().filter(searchNm);
		
		String categ1Cd = param.get("categ1Cd");
		if(searchNm == null || "".equals(searchNm)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			searchNm = param.get("searchNm");
			searchNm = new HTMLInputFilter().filter(searchNm);
			
			param.put("searchWord",searchNm);
			param.put("userId",user.getId());
			totalSearchService.insertTotalSearchWord(param);
		}
		if(searchNm == null || searchNm == "")
			searchNm = "";
		if(categ1Cd == null || categ1Cd == "")
			categ1Cd = "";
		//Upper처리
		param.put("searchNm",searchNm.toUpperCase());
		param.put("categ1Cd",categ1Cd);
		List<TotalSearch> cntList = null;
		List<TotalSearch> schList = null;
		cntList =  totalSearchService.selectTotalSearchCnt(param);
		if(categ1Cd.equals("")) {
			schList =  totalSearchService.selectTotalSearchTot(param);
		} else if(categ1Cd.equals("1")) {
			schList =  totalSearchService.selectTotalSearchStnd(param);
		} else if(categ1Cd.equals("2")) {
			schList =  totalSearchService.selectTotalSearchTbl(param);
		} else if(categ1Cd.equals("3")) {
			schList =  totalSearchService.selectTotalSearchDq(param);
		} else if(categ1Cd.equals("4")) {
			schList =  totalSearchService.selectTotalSearchSubj(param);
		} else if(categ1Cd.equals("5")) {
			schList =  totalSearchService.selectTotalSearchBbs(param);
		}
		
		//건수 MAP 초기화
		param.put("stndCnt","0");
		param.put("tblCnt","0");
		param.put("dqCnt","0");
		param.put("subjCnt", "0");
		param.put("bbsCnt","0");
		int totCnt = 0;
		for(TotalSearch vo : cntList) {
			totCnt += vo.getTotCnt();
			//건수 MAP에 저장
			if(vo.getCateg1Cd().equals("1")) 
				param.put("stndCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("2")) 
				param.put("tblCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("3")) 
				param.put("dqCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("4")) 
				param.put("subjCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("5")) 
				param.put("bbsCnt",vo.getTotCnt()+"");
		}
		param.put("totCnt", totCnt + "");
		
		//소문자 검색단어 반환
		param.put("searchNm",searchNm);
		ModelAndView mv = new ModelAndView(); 
		mv.addObject("result", param);
		mv.addObject("cntList", cntList);
		mv.addObject("schList", schList);
		mv.setViewName("/portal/totalsearch/totalsearch");
        
		return mv;
	}
	
	@RequestMapping("/portal/totalsearch/TotalSearchWord.json")
	@ResponseBody
	public List<TotalSearch> getTotalSearchWordJSON() throws Exception{
		return totalSearchService.selectTotalSearchWord();
	}
	
	@RequestMapping("/portal/totalsearch_dq/TotalSearchCtrl.do")
	public ModelAndView ownershipView_dq(HttpServletRequest request, @RequestParam Map<String, String> param)throws Exception{
//		Map<String,String> param = new HashMap<String,String>();
//		String searchNm = request.getParameter("bfSearchNm");
		String searchNm = UtilString.null2Blank(param.get("bfSearchNm"));
		searchNm = new HTMLInputFilter().filter(searchNm);
		
		String categ1Cd = param.get("categ1Cd");
		if(searchNm == null || "".equals(searchNm)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			searchNm = param.get("searchNm");
			searchNm = new HTMLInputFilter().filter(searchNm);
			
			param.put("searchWord",searchNm);
			param.put("userId",user.getId());
			totalSearchService.insertTotalSearchWord(param);
		}
		if(searchNm == null || searchNm == "")
			searchNm = "";
		if(categ1Cd == null || categ1Cd == "")
			categ1Cd = "";
		//Upper처리
		param.put("searchNm",searchNm.toUpperCase());
		param.put("categ1Cd",categ1Cd);
		List<TotalSearch> cntList = null;
		List<TotalSearch> schList = null;
		cntList =  totalSearchService.selectTotalSearchCnt(param);
		if(categ1Cd.equals("")) {
			schList =  totalSearchService.selectTotalSearchTot(param);
		} else if(categ1Cd.equals("1")) {
			schList =  totalSearchService.selectTotalSearchStnd(param);
		} else if(categ1Cd.equals("2")) {
			schList =  totalSearchService.selectTotalSearchTbl(param);
		} else if(categ1Cd.equals("3")) {
			schList =  totalSearchService.selectTotalSearchDq(param);
		} else if(categ1Cd.equals("4")) {
			schList =  totalSearchService.selectTotalSearchSubj(param);
		} else if(categ1Cd.equals("5")) {
			schList =  totalSearchService.selectTotalSearchBbs(param);
		}
		
		//건수 MAP 초기화
		param.put("stndCnt","0");
		param.put("tblCnt","0");
		param.put("dqCnt","0");
		param.put("subjCnt", "0");
		param.put("bbsCnt","0");
		int totCnt = 0;
		for(TotalSearch vo : cntList) {
			totCnt += vo.getTotCnt();
			//건수 MAP에 저장
			if(vo.getCateg1Cd().equals("1")) 
				param.put("stndCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("2")) 
				param.put("tblCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("3")) 
				param.put("dqCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("4")) 
				param.put("subjCnt",vo.getTotCnt()+"");
			if(vo.getCateg1Cd().equals("5")) 
				param.put("bbsCnt",vo.getTotCnt()+"");
		}
		param.put("totCnt", totCnt + "");
		
		//소문자 검색단어 반환
		param.put("searchNm",searchNm);
		ModelAndView mv = new ModelAndView(); 
		mv.addObject("result", param);
		mv.addObject("cntList", cntList);
		mv.addObject("schList", schList);
		mv.setViewName("/portal/totalsearch_dq/totalsearch_dq");
        
		return mv;
	}
	
	@RequestMapping("/portal/totalsearch_dq/TotalSearchWord.json")
	@ResponseBody
	public List<TotalSearch> getTotalSearchWordJSON_dq() throws Exception{
		return totalSearchService.selectTotalSearchWord();
	}

}