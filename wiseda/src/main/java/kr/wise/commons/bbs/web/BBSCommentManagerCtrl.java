package kr.wise.commons.bbs.web;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BBSCommentManagerService;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.Comment;
import kr.wise.commons.bbs.service.CommentVO;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.pagenation.DefaultPaginationRenderer;
import kr.wise.commons.cmm.pagenation.PaginationInfo;
import kr.wise.commons.cmm.security.HTMLInputFilter;
import kr.wise.commons.cmm.service.FileManagerService;
import kr.wise.commons.cmm.service.FileManagerUtil;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 댓글 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 신용호
 * @since 2016.07.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2016.07.22   신용호              최초 생성
 *   2018.06.27     신용호		    댓글 등록후 처리 예외 수정
 * </pre>
 */

@Controller
@RequestMapping("/commons")
public class BBSCommentManagerCtrl {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private BBSCommentManagerService bbsCommentMngService;
	
	@Inject
	private UsergMenuMapService usergMenuMapService;

	@Inject
	private CodeListService codeListService;
    
	private final Map<String, Object> codeMap = new HashMap<String, Object>();
	
	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 * 
	 * @return Map<String,String>
	 * @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		// 시스템영역 코드리스트 JSON
		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, "BBS006"));
		codeMap.put("prjCode", codeListService.getCodeList(WiseConfig.PRJLIST));
		codeMap.put("reportType", codeListService.getComCodeList(WiseConfig.PORTAL, "BBS011"));

		return codeMap;
	}

	private void setusermenumap(String bbsid, ModelMap model) {
		String servletPath = "/commons/bbs/selectBoardList.do";
		if (StringUtils.hasText(bbsid)) {
			servletPath += "?bbsId=" + bbsid;
		}
		Map<String, Object> menumap = usergMenuMapService.getMenuMap2(servletPath);

		if (menumap.containsKey("REQ_MENU")) {
			model.addAttribute("REQ_MENU", menumap.get("REQ_MENU"));
		}
		if (menumap.containsKey("TOP_MENU")) {
			model.addAttribute("TOP_MENU", menumap.get("TOP_MENU"));
		}
		if (menumap.containsKey("SUB_MENU")) {
			model.addAttribute("SUB_MENU", UtilJson.convertJsonString(menumap.get("SUB_MENU")));
		}
	}
	
	private void setusermenumapDq(String bbsid, ModelMap model) {
		String servletPath = "/commons/bbs_dq/selectBoardList.do";
		if (StringUtils.hasText(bbsid)) {
			servletPath += "?bbsId=" + bbsid;
		}
		Map<String, Object> menumap = usergMenuMapService.getMenuMap2(servletPath);

		if (menumap.containsKey("REQ_MENU")) {
			model.addAttribute("REQ_MENU", menumap.get("REQ_MENU"));
		}
		if (menumap.containsKey("TOP_MENU")) {
			model.addAttribute("TOP_MENU", menumap.get("TOP_MENU"));
		}
		if (menumap.containsKey("SUB_MENU")) {
			model.addAttribute("SUB_MENU", UtilJson.convertJsonString(menumap.get("SUB_MENU")));
		}
	}
	
    /**
     * 댓글관리 목록 조회를 제공한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs/selectArticleCommentList.do")
    public String selectArticleCommentList(@ModelAttribute("searchVO") CommentVO commentVO, 
    		RedirectAttributes redirectAttributes, ModelMap model) throws Exception {

    	CommentVO articleCommentVO = new CommentVO();
    	
		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
		    commentVO.setCommentNo("");
		    commentVO.setCommentCn("");
		}
		
		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
			return "forward:/commons/bbs/updateArticleCommentView.do";
		}
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		model.addAttribute("sessionUniqId", user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		
		commentVO.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
		
		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, commentVO.getCateCode()));
		
//		commentVO.setSubPageUnit(propertyService.getInt("pageUnit"));
//		commentVO.setSubPageSize(propertyService.getInt("pageSize"));
		
		commentVO.setSubPageUnit(WiseConfig.SUBPAGEUNIT);
		commentVO.setSubPageSize(WiseConfig.SUBPAGESIZE);

    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Map<String, Object> map = bbsCommentMngService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		commentVO.setSubTotalRecordCount(totCnt);
		
		DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
		String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_SUB_PAGE);
		
		commentVO.setSubTotalPageCount(paginationInfo.getTotalPageCount());
		
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("pageui", pageUi);
		
		model.addAttribute("articleCommentVO", articleCommentVO);	// validator 용도 
		
		commentVO.setCommentCn("");	// 등록 후 댓글 내용 처리
		
		setusermenumap(commentVO.getBbsId(), model);
	
		return "/commons/bbs/bbsCommentList_dtl";
    }
    
    
    /**
     * 댓글을 등록한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs/insertArticleComment.do")
    public String insertArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, 
    		@ModelAttribute("comment") Comment comment, //BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes, ModelMap model, HashMap<String, String> map) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    comment.setFrstRegisterId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    comment.setWrterId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    comment.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
		    
		    bbsCommentMngService.insertArticleComment(comment);
		    
		    commentVO.setCommentCn("");
		    commentVO.setCommentNo("");
		    
		    int totCnt = commentVO.getSubTotalRecordCount();
		    
		    //log.debug("totCnt = " + totCnt);
		    
		    if(totCnt != 0 && totCnt % 5 == 0)
		    	commentVO.setSubPageIndex((commentVO.getSubTotalPageCount() + 1));
		    else
		    	commentVO.setSubPageIndex(commentVO.getSubTotalPageCount());
		    
		    //log.debug("total page (insert) = " + commentVO.getSubTotalPageCount());
		    //log.debug("page index (insert) = " + commentVO.getSubPageIndex());
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", user == null ? "" : UtilString.null2Blank(commentVO.getCateCode()));
		    
		}
		
		return "redirect:/commons/bbs/selectBoardArticle.do";
		
    }
    
    
    /**
     * 댓글을 삭제한다.
     * 
     * @param commentVO
     * @param comment
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs/deleteArticleComment.do")
    public String deleteArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, 
    		RedirectAttributes redirectAttributes, ModelMap model, HashMap<String, String> map) throws Exception {
    	
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    bbsCommentMngService.deleteArticleComment(commentVO);
		    
		    int totCnt = commentVO.getSubTotalRecordCount();
		    
		    //log.debug("totCnt = " + totCnt);
		    
		    if((commentVO.getSubPageIndex() == commentVO.getSubTotalPageCount()) && (totCnt % 5 == 1))
		    	commentVO.setSubPageIndex((commentVO.getSubTotalPageCount() - 1));
		    
		    //log.debug("total page (delete) = " + commentVO.getSubTotalPageCount());
		    //log.debug("page index (delete) = " + commentVO.getSubPageIndex());
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", commentVO.getCateCode());
		    
		}
		
		commentVO.setCommentCn("");
		commentVO.setCommentNo("");
		
		//return "forward:/commons/bbs/selectBoardArticle.do";
		
		return "redirect:/commons/bbs/selectBoardArticle.do";
    }
    
    
    /**
     * 댓글 수정 페이지로 이동한다.
     * 
     * @param commentVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs/updateArticleCommentView.do")
    public String updateArticleCommentView(@ModelAttribute("searchVO") CommentVO commentVO, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
	
	log.debug("USER : {}", user);
	 //KISA 보안취약점 조치 (2018-12-10, 신용호)
    Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

	CommentVO articleCommentVO = new CommentVO();
	
	commentVO.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
	
	codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, commentVO.getCateCode()));

	//commentVO.setSubPageUnit(propertyService.getInt("pageUnit"));
	//commentVO.setSubPageSize(propertyService.getInt("pageSize"));

	commentVO.setSubPageUnit(WiseConfig.SUBPAGEUNIT);
	commentVO.setSubPageSize(WiseConfig.SUBPAGESIZE);
	
	PaginationInfo paginationInfo = new PaginationInfo();
	paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
	paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
	paginationInfo.setPageSize(commentVO.getSubPageSize());

	commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
	commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
	commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = bbsCommentMngService.selectArticleCommentList(commentVO);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
	paginationInfo.setTotalRecordCount(totCnt);
	
	commentVO.setSubTotalPageCount(paginationInfo.getTotalPageCount());
	
	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
	String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_SUB_PAGE);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("paginationInfo", paginationInfo);
	model.addAttribute("pageui", pageUi);
	
	articleCommentVO = bbsCommentMngService.selectArticleCommentDetail(commentVO);
	
	model.addAttribute("articleCommentVO", articleCommentVO);
	
	setusermenumap(commentVO.getBbsId(), model);
	
	return "/commons/bbs/bbsCommentList_dtl";
    }
    
    
    /**
     * 댓글을 수정한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs/updateArticleComment.do")
    public String updateArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, //BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
	
		if (isAuthenticated) {
		    comment.setLastUpdusrId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    
		    bbsCommentMngService.updateArticleComment(comment);
		    
		    commentVO.setCommentCn("");
		    commentVO.setCommentNo("");
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", commentVO.getCateCode());
		    
		}
	
		//return "forward:/commons/bbs/selectBoardArticle.do";
		
		return "redirect:/commons/bbs/selectBoardArticle.do";
    }
    
    /**
     * 댓글관리 목록 조회를 제공한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs_dq/selectArticleCommentList.do")
    public String selectArticleCommentListDq(@ModelAttribute("searchVO") CommentVO commentVO, 
    		RedirectAttributes redirectAttributes, ModelMap model) throws Exception {

    	CommentVO articleCommentVO = new CommentVO();
    	
		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
		    commentVO.setCommentNo("");
		    commentVO.setCommentCn("");
		}
		
		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
			return "forward:/commons/bbs_dq/updateArticleCommentView.do";
		}
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		model.addAttribute("sessionUniqId", user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		
		commentVO.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
		
		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, commentVO.getCateCode()));
		
//		commentVO.setSubPageUnit(propertyService.getInt("pageUnit"));
//		commentVO.setSubPageSize(propertyService.getInt("pageSize"));
		
		commentVO.setSubPageUnit(WiseConfig.SUBPAGEUNIT);
		commentVO.setSubPageSize(WiseConfig.SUBPAGESIZE);

    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Map<String, Object> map = bbsCommentMngService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		commentVO.setSubTotalRecordCount(totCnt);
		
		DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
		String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_SUB_PAGE);
		
		commentVO.setSubTotalPageCount(paginationInfo.getTotalPageCount());
		
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("pageui", pageUi);
		
		model.addAttribute("articleCommentVO", articleCommentVO);	// validator 용도 
		
		commentVO.setCommentCn("");	// 등록 후 댓글 내용 처리
		
		setusermenumapDq(commentVO.getBbsId(), model);
	
		return "/commons/bbs_dq/bbsCommentList_dtl";
    }
    
    
    /**
     * 댓글을 등록한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs_dq/insertArticleComment.do")
    public String insertArticleCommentDq(@ModelAttribute("searchVO") CommentVO commentVO, 
    		@ModelAttribute("comment") Comment comment, //BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes, ModelMap model, HashMap<String, String> map) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    comment.setFrstRegisterId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    comment.setWrterId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    comment.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
		    
		    bbsCommentMngService.insertArticleComment(comment);
		    
		    commentVO.setCommentCn("");
		    commentVO.setCommentNo("");
		    
		    int totCnt = commentVO.getSubTotalRecordCount();
		    
		    //log.debug("totCnt = " + totCnt);
		    
		    if(totCnt != 0 && totCnt % 5 == 0)
		    	commentVO.setSubPageIndex((commentVO.getSubTotalPageCount() + 1));
		    else
		    	commentVO.setSubPageIndex(commentVO.getSubTotalPageCount());
		    
		    //log.debug("total page (insert) = " + commentVO.getSubTotalPageCount());
		    //log.debug("page index (insert) = " + commentVO.getSubPageIndex());
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", user == null ? "" : UtilString.null2Blank(commentVO.getCateCode()));
		    
		}
		
		return "redirect:/commons/bbs_dq/selectBoardArticle.do";
		
    }
    
    
    /**
     * 댓글을 삭제한다.
     * 
     * @param commentVO
     * @param comment
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs_dq/deleteArticleComment.do")
    public String deleteArticleCommentDq(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, 
    		RedirectAttributes redirectAttributes, ModelMap model, HashMap<String, String> map) throws Exception {
    	
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    bbsCommentMngService.deleteArticleComment(commentVO);
		    
		    int totCnt = commentVO.getSubTotalRecordCount();
		    
		    //log.debug("totCnt = " + totCnt);
		    
		    if((commentVO.getSubPageIndex() == commentVO.getSubTotalPageCount()) && (totCnt % 5 == 1))
		    	commentVO.setSubPageIndex((commentVO.getSubTotalPageCount() - 1));
		    
		    //log.debug("total page (delete) = " + commentVO.getSubTotalPageCount());
		    //log.debug("page index (delete) = " + commentVO.getSubPageIndex());
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", commentVO.getCateCode());
		    
		}
		
		commentVO.setCommentCn("");
		commentVO.setCommentNo("");
		
		//return "forward:/commons/bbs/selectBoardArticle.do";
		
		return "redirect:/commons/bbs_dq/selectBoardArticle.do";
    }
    
    
    /**
     * 댓글 수정 페이지로 이동한다.
     * 
     * @param commentVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs_dq/updateArticleCommentView.do")
    public String updateArticleCommentViewDq(@ModelAttribute("searchVO") CommentVO commentVO, ModelMap model) throws Exception {

	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
	
	log.debug("USER : {}", user);
	 //KISA 보안취약점 조치 (2018-12-10, 신용호)
    Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

	CommentVO articleCommentVO = new CommentVO();
	
	commentVO.setWrterNm(user == null ? "" : UtilString.null2Blank(user.getName()));
	
	codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, commentVO.getCateCode()));

	//commentVO.setSubPageUnit(propertyService.getInt("pageUnit"));
	//commentVO.setSubPageSize(propertyService.getInt("pageSize"));

	commentVO.setSubPageUnit(WiseConfig.SUBPAGEUNIT);
	commentVO.setSubPageSize(WiseConfig.SUBPAGESIZE);
	
	PaginationInfo paginationInfo = new PaginationInfo();
	paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
	paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
	paginationInfo.setPageSize(commentVO.getSubPageSize());

	commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
	commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
	commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = bbsCommentMngService.selectArticleCommentList(commentVO);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
	paginationInfo.setTotalRecordCount(totCnt);
	
	commentVO.setSubTotalPageCount(paginationInfo.getTotalPageCount());
	
	DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
	String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_SUB_PAGE);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("paginationInfo", paginationInfo);
	model.addAttribute("pageui", pageUi);
	
	articleCommentVO = bbsCommentMngService.selectArticleCommentDetail(commentVO);
	
	model.addAttribute("articleCommentVO", articleCommentVO);
	
	setusermenumapDq(commentVO.getBbsId(), model);
	
	return "/commons/bbs_dq/bbsCommentList_dtl";
    }
    
    
    /**
     * 댓글을 수정한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/bbs_dq/updateArticleComment.do")
    public String updateArticleCommentDq(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, //BindingResult bindingResult, 
    		RedirectAttributes redirectAttributes, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
	
		if (isAuthenticated) {
		    comment.setLastUpdusrId(user == null ? "" : UtilString.null2Blank(user.getUniqId()));
		    
		    bbsCommentMngService.updateArticleComment(comment);
		    
		    commentVO.setCommentCn("");
		    commentVO.setCommentNo("");
		    
		    redirectAttributes.addAttribute("bbsId", commentVO.getBbsId());
		    redirectAttributes.addAttribute("nttId", commentVO.getNttId());
		    redirectAttributes.addAttribute("subPageIndex", commentVO.getSubPageIndex());
		    redirectAttributes.addAttribute("cateCode", commentVO.getCateCode());
		    
		}
	
		//return "forward:/commons/bbs/selectBoardArticle.do";
		
		return "redirect:/commons/bbs_dq/selectBoardArticle.do";
    }
	
}
