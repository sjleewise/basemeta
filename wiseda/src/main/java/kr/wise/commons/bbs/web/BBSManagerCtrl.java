package kr.wise.commons.bbs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BBSCommentManagerService;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.Board;
import kr.wise.commons.bbs.service.BoardMaster;
import kr.wise.commons.bbs.service.BoardMasterVO;
import kr.wise.commons.bbs.service.BoardVO;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   -------    ---------------------------
 *  2009.03.19   이삼섭            최초 생성
 *  2009.06.29   한성곤            2단계 기능 추가 (댓글관리, 만족도조사)
 *  2011.07.01   안민정            댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *  2011.08.26   정진오            IncludedInfo annotation 추가
 *  2011.09.07   서준식            유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *  2016.06.13   김연호            표준프레임워크 3.6 개선
 *  2019.05.17   신용호            KISA 취약점 조치 및 보완
 *  2020.10.27   신용호            파일 업로드 수정 (multiRequest.getFiles)
 * 
 * </pre>
 */

@Controller
@RequestMapping("/commons")
public class BBSManagerCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * @Resource(name = "EgovArticleService") private EgovArticleService
	 *                egovArticleService;
	 * @Resource(name = "EgovBBSMasterService") private EgovBBSMasterService
	 *                egovBBSMasterService;
	 * @Resource(name = "EgovFileMngService") private EgovFileMngService
	 *                fileMngService;
	 * @Resource(name = "EgovFileMngUtil") private EgovFileMngUtil fileUtil;
	 * @Resource(name = "propertiesService") protected EgovPropertyService
	 *                propertyService;
	 * @Resource(name="egovMessageSource") EgovMessageSource egovMessageSource;
	 * @Resource(name = "EgovArticleCommentService") protected
	 *                EgovArticleCommentService egovArticleCommentService;
	 * @Resource(name = "EgovBBSSatisfactionService") private
	 *                EgovBBSSatisfactionService bbsSatisfactionService;
	 * @Resource(name = "EgovTemplateManageService") private
	 *                EgovTemplateManageService egovTemplateManageService;
	 * @Autowired private DefaultBeanValidator beanValidator;
	 * 
	 *            //protected Logger log = Logger.getLogger(this.getClass());
	 */

	@Inject
	private BBSAttributeManageService bbsAttrbService;

	@Inject
	private BBSManagerService bbsMngService;

	@Inject
	private FileManagerUtil fileManagerUtil;

	@Inject
	private FileManagerService fileMngService;
	
	@Inject
	private BBSCommentManagerService bbsCommentMngService;

	@Inject
	private UsergMenuMapService usergMenuMapService;

	@Inject
	private CodeListService codeListService;

	private final Map<String, Object> codeMap = new HashMap<String, Object>();

	/**
	 * XSS 방지 처리.
	 * 
	 * @param data
	 * @return
	 */
	protected String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}

		String ret = data;

		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

		ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

		ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

		ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

		ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

		return ret;
	}


	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 16.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		//시스템영역 코드리스트 JSON
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
	 * 게시물에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/selectBoardList.do")
	public String selectBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());

		boardVO.setSearchWrd(new HTMLInputFilter().filter(boardVO.getSearchWrd()));
		
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, master.getCateCode()));

		boardVO.setPageUnit(WiseConfig.PAGEUNIT);
		boardVO.setPageSize(WiseConfig.PAGESIZE);

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);
		DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
		String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("bdMstr", master);
		model.addAttribute("brdMstrVO", master);
		// model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("pageui", pageUi);

		setusermenumap(master.getBbsId(), model);

		return "/commons/bbs/bbsNoticeList";
	}

	/**
	 * 게시물에 대한 상세 정보를 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/selectBoardArticle.do")
	public String selectBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		// 조회수 증가 여부 지정
		boardVO.setPlusCount(true);

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "": user.getUniqId());
		BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ----------------------------
		// template 처리 (기본 BBS template 지정 포함)
		// ----------------------------
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVO = bbsAttrbService.selectBBSMasterInf(master);
		
		//----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		//----------------------------
		if (bbsCommentMngService != null){
			if (bbsCommentMngService.canUseComment(boardVO.getBbsId())) {
					  model.addAttribute("useComment", "true");
			}
		}

		model.addAttribute("bdMstr", masterVO);

		setusermenumap(masterVO.getBbsId(), model);

		return "/commons/bbs/bbsNoticeDetail";
	}

	/**
	 * 게시물 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/addBoardArticle.do")
	public String addBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		BoardMasterVO bdMstr = new BoardMasterVO();
		
		if (isAuthenticated) {
			BoardMasterVO vo = new BoardMasterVO();
			vo.setBbsId(boardVO.getBbsId());
			vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
			model.addAttribute("bdMstr", bdMstr);
			codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bdMstr.getCateCode()));
		}


		model.addAttribute("articleVO", boardVO);
		model.addAttribute("bdMstr", bdMstr);

		setusermenumap(bdMstr.getBbsId(), model);

		return "/commons/bbs/bbsNoticeRegist";
	}

	/**
	 * 게시물을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/insertBoardArticle.do")
	public String insertBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, // BindingResult bindingResult, SessionStatus status, 
			RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		// if(!isAuthenticated) { //KISA 보안취약점 조치 (2018-12-10, 이정은)
		// return "egovframework/com/uat/uia/EgovLoginUsr";
		// }
		/**
		 * beanValidator.validate(board, bindingResult); if
		 * (bindingResult.hasErrors()) {
		 * 
		 * BoardMasterVO master = new BoardMasterVO();
		 * 
		 * master.setBbsId(boardVO.getBbsId()); master.setUniqId((user == null
		 * || user.getUniqId() == null) ? "" : user.getUniqId());
		 * 
		 * master = egovBBSMasterService.selectBBSMasterInf(master);
		 * 
		 * 
		 * //---------------------------- // 기본 BBS template 지정
		 * //---------------------------- if (master.getTmplatCours() == null ||
		 * master.getTmplatCours().equals("")) { master.setTmplatCours(
		 * "css/egovframework/com/cop/tpl/egovBaseTemplate.css"); }
		 * 
		 * model.addAttribute("boardMasterVO", master);
		 * ////-----------------------------
		 * 
		 * return "egovframework/com/cop/bbs/EgovArticleRegist"; }
		 */
		
			if (isAuthenticated) {
			List<FileVO> result = null;
			String atchFileId = "";

			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			// final List<MultipartFile> files = multiRequest.getFiles("file_1");
			if (!files.isEmpty()) {
				result = fileManagerUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
				log.debug("File ID [{}]", atchFileId);
			}
			
			board.setAtchFileId(UtilString.null2Blank(atchFileId));
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(boardVO.getBbsId());

			board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			board.setPassword(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			
			bbsMngService.insertBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		// status.setComplete();
		return "redirect:/commons/bbs/selectBoardList.do";
	}

	/**
	 * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/addReplyBoardArticle.do")
	public String addReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		// if(!isAuthenticated) {
		// return "egovframework/com/uat/uia/EgovLoginUsr";
		// }

		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		BoardVO articleVO = new BoardVO();
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		master = bbsAttrbService.selectBBSMasterInf(vo);
		boardVO = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("bdMstr", master);
		model.addAttribute("result", boardVO);
		
		model.addAttribute("articleVO", articleVO);

		setusermenumap(master.getBbsId(), model);

		return "/commons/bbs/bbsNoticeReply";
	}

	/**
	 * 게시물에 대한 답변을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/replyBoardArticle.do")
	public String replyBoardArticle(final MultipartHttpServletRequest multiRequest,@ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("bdMstr") BoardMaster bdMstr,@ModelAttribute("board") BoardVO board, // BindingResult bindingResult,
			RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		/**
		 * if(!isAuthenticated) { //KISA 보안취약점 조치 (2018-12-10, 이정은) return
		 * "egovframework/com/uat/uia/EgovLoginUsr"; }
		 * 
		 * beanValidator.validate(board, bindingResult); if
		 * (bindingResult.hasErrors()) { BoardMasterVO master = new
		 * BoardMasterVO();
		 * 
		 * master.setBbsId(boardVO.getBbsId()); master.setUniqId((user == null
		 * || user.getUniqId() == null) ? "" : user.getUniqId());
		 * 
		 * master = egovBBSMasterService.selectBBSMasterInf(master);
		 * 
		 * 
		 * //---------------------------- // 기본 BBS template 지정
		 * //---------------------------- if (master.getTmplatCours() == null ||
		 * master.getTmplatCours().equals("")) { master.setTmplatCours(
		 * "/css/egovframework/com/cop/tpl/egovBaseTemplate.css"); }
		 * 
		 * model.addAttribute("articleVO", boardVO);
		 * model.addAttribute("boardMasterVO", master);
		 * ////-----------------------------
		 * 
		 * return "egovframework/com/cop/bbs/EgovArticleReply"; }
		 */
		
		if (isAuthenticated) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			// final List<MultipartFile> files = multiRequest.getFiles("file_1");
			String atchFileId = "";

			if (!files.isEmpty()) {
				List<FileVO> result = fileManagerUtil.parseFileInf(files,"BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}
			
			board.setAtchFileId(UtilString.null2Blank(atchFileId));
			board.setReplyAt("Y");
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(board.getBbsId());
			board.setParnts(Long.toString(boardVO.getNttId()));
			board.setSortOrdr(boardVO.getSortOrdr());
			board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));
			/**
			 * //익명등록 처리 if(board.getAnonymousAt() != null &&
			 * board.getAnonymousAt().equals("Y")){
			 * board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
			 * board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
			 * board.setFrstRegisterId("anonymous");
			 * 
			 * } else { board.setNtcrId((user == null || user.getId() == null) ?
			 * "" : user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
			 * board.setNtcrNm((user == null || user.getName() == null) ? "" :
			 * user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
			 * 
			 * }
			 */
			board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지

			bbsMngService.insertBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		return "redirect:/commons/bbs/selectBoardList.do";
	}

	/**
	 * 게시물 수정을 위한 수정페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/forUpdateBoardArticle.do")
	public String selectBoardArticleForUpdt(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model) 
			throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String errCode = "";

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());
		
		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
			bmvo = bbsAttrbService.selectBBSMasterInf(master);
			bdvo = bbsMngService.selectBoardArticle(boardVO);
			codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bmvo.getCateCode()));
		} else {
			errCode = "BBS.ERRAUTH";
		}
		
		model.addAttribute("articleVO", bdvo);
		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);
		model.addAttribute("errCode", errCode);
		//model.addAttribute("brdMstrVO", bmvo);

		setusermenumap(bmvo.getBbsId(), model);

		return "/commons/bbs/bbsNoticeUpdt";
	}

	/**
	 * 게시물에 대한 내용을 수정한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/bbs/updateBoardArticle.do")
    public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, //BindingResult bindingResult, SessionStatus status,
	    RedirectAttributes re, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String atchFileId = boardVO.getAtchFileId();

/*		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

		    boardVO.setFrstRegisterId(user.getUniqId());

		    BoardMaster master = new BoardMaster();
		    BoardMasterVO bmvo = new BoardMasterVO();
		    BoardVO bdvo = new BoardVO();

		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId(user.getUniqId());

		    bmvo = bbsAttrbService.selectBBSMasterInf(master);
		    bdvo = bbsMngService.selectBoardArticle(boardVO);

		    model.addAttribute("result", bdvo);
		    model.addAttribute("bdMstr", bmvo);

		    return "egovframework/com/cop/bbs/EgovNoticeUpdt";
		}*/

		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
				    List<FileVO> result = fileManagerUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    board.setAtchFileId(atchFileId);
				} else {
				    FileVO fvo = new FileVO();
				    fvo.setAtchFileId(atchFileId);
				    int cnt = fileMngService.getMaxFileSN(fvo);
				    List<FileVO> _result = fileManagerUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				    fileMngService.updateFileInfs(_result);
				}
		    }

		    board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    board.setFrstRegisterId(user.getUniqId());

		    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

		    board.setNttCn(unscript(board.getNttCn())); // XSS 방지

		    bbsMngService.updateBoardArticle(board);
		    
		    re.addAttribute("bbsId", bdMstr.getBbsId());
		}
	
		return "redirect:/commons/bbs/selectBoardList.do";
    }

	/**
	 * 게시물에 대한 내용을 삭제한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param bdMstr
	 * @param re
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs/deleteBoardArticle.do")
	public String deleteBoardArticle(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
			@ModelAttribute("bdMstr") BoardMaster bdMstr, RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
			board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setFrstRegisterId(user.getUniqId());

			bbsMngService.deleteBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		return "redirect:/commons/bbs/selectBoardList.do";

	}
	
	/**
	 * 게시물에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/selectBoardList.do")
	public String selectBoardArticlesDq(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());

		boardVO.setSearchWrd(new HTMLInputFilter().filter(boardVO.getSearchWrd()));
		
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

		codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, master.getCateCode()));

		boardVO.setPageUnit(WiseConfig.PAGEUNIT);
		boardVO.setPageSize(WiseConfig.PAGESIZE);

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);
		DefaultPaginationRenderer pageui = new DefaultPaginationRenderer();
		String pageUi = pageui.renderPagination(paginationInfo, WiseConfig.FN_PAGE);

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("bdMstr", master);
		model.addAttribute("brdMstrVO", master);
		// model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("pageui", pageUi);

		setusermenumapDq(master.getBbsId(), model);

		return "/commons/bbs_dq/bbsNoticeList";
	}

	/**
	 * 게시물에 대한 상세 정보를 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/selectBoardArticle.do")
	public String selectBoardArticleDq(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		// 조회수 증가 여부 지정
		boardVO.setPlusCount(true);

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "": user.getUniqId());
		BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ----------------------------
		// template 처리 (기본 BBS template 지정 포함)
		// ----------------------------
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVO = bbsAttrbService.selectBBSMasterInf(master);
		
		//----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		//----------------------------
		if (bbsCommentMngService != null){
			if (bbsCommentMngService.canUseComment(boardVO.getBbsId())) {
					  model.addAttribute("useComment", "true");
			}
		}

		model.addAttribute("bdMstr", masterVO);

		setusermenumapDq(masterVO.getBbsId(), model);

		return "/commons/bbs_dq/bbsNoticeDetail";
	}

	/**
	 * 게시물 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/addBoardArticle.do")
	public String addBoardArticleDq(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		BoardMasterVO bdMstr = new BoardMasterVO();
		
		if (isAuthenticated) {
			BoardMasterVO vo = new BoardMasterVO();
			vo.setBbsId(boardVO.getBbsId());
			vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
			model.addAttribute("bdMstr", bdMstr);
			codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bdMstr.getCateCode()));
		}


		model.addAttribute("articleVO", boardVO);
		model.addAttribute("bdMstr", bdMstr);

		setusermenumapDq(bdMstr.getBbsId(), model);

		return "/commons/bbs_dq/bbsNoticeRegist";
	}

	/**
	 * 게시물을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/insertBoardArticle.do")
	public String insertBoardArticleDq(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, // BindingResult bindingResult, SessionStatus status, 
			RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		// if(!isAuthenticated) { //KISA 보안취약점 조치 (2018-12-10, 이정은)
		// return "egovframework/com/uat/uia/EgovLoginUsr";
		// }
		/**
		 * beanValidator.validate(board, bindingResult); if
		 * (bindingResult.hasErrors()) {
		 * 
		 * BoardMasterVO master = new BoardMasterVO();
		 * 
		 * master.setBbsId(boardVO.getBbsId()); master.setUniqId((user == null
		 * || user.getUniqId() == null) ? "" : user.getUniqId());
		 * 
		 * master = egovBBSMasterService.selectBBSMasterInf(master);
		 * 
		 * 
		 * //---------------------------- // 기본 BBS template 지정
		 * //---------------------------- if (master.getTmplatCours() == null ||
		 * master.getTmplatCours().equals("")) { master.setTmplatCours(
		 * "css/egovframework/com/cop/tpl/egovBaseTemplate.css"); }
		 * 
		 * model.addAttribute("boardMasterVO", master);
		 * ////-----------------------------
		 * 
		 * return "egovframework/com/cop/bbs/EgovArticleRegist"; }
		 */
		
			if (isAuthenticated) {
			List<FileVO> result = null;
			String atchFileId = "";

			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			// final List<MultipartFile> files = multiRequest.getFiles("file_1");
			if (!files.isEmpty()) {
				result = fileManagerUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
				log.debug("File ID [{}]", atchFileId);
			}
			
			board.setAtchFileId(UtilString.null2Blank(atchFileId));
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(boardVO.getBbsId());

			board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			board.setPassword(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			
			bbsMngService.insertBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		// status.setComplete();
		return "redirect:/commons/bbs_dq/selectBoardList.do";
	}

	/**
	 * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/addReplyBoardArticle.do")
	public String addReplyBoardArticleDq(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		
		// KISA 보안취약점 조치 (2018-12-10, 이정은)
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		
		// if(!isAuthenticated) {
		// return "egovframework/com/uat/uia/EgovLoginUsr";
		// }

		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		BoardVO articleVO = new BoardVO();
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		master = bbsAttrbService.selectBBSMasterInf(vo);
		boardVO = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("bdMstr", master);
		model.addAttribute("result", boardVO);
		
		model.addAttribute("articleVO", articleVO);

		setusermenumapDq(master.getBbsId(), model);

		return "/commons/bbs_dq/bbsNoticeReply";
	}

	/**
	 * 게시물에 대한 답변을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/replyBoardArticle.do")
	public String replyBoardArticleDq(final MultipartHttpServletRequest multiRequest,@ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("bdMstr") BoardMaster bdMstr,@ModelAttribute("board") BoardVO board, // BindingResult bindingResult,
			RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		/**
		 * if(!isAuthenticated) { //KISA 보안취약점 조치 (2018-12-10, 이정은) return
		 * "egovframework/com/uat/uia/EgovLoginUsr"; }
		 * 
		 * beanValidator.validate(board, bindingResult); if
		 * (bindingResult.hasErrors()) { BoardMasterVO master = new
		 * BoardMasterVO();
		 * 
		 * master.setBbsId(boardVO.getBbsId()); master.setUniqId((user == null
		 * || user.getUniqId() == null) ? "" : user.getUniqId());
		 * 
		 * master = egovBBSMasterService.selectBBSMasterInf(master);
		 * 
		 * 
		 * //---------------------------- // 기본 BBS template 지정
		 * //---------------------------- if (master.getTmplatCours() == null ||
		 * master.getTmplatCours().equals("")) { master.setTmplatCours(
		 * "/css/egovframework/com/cop/tpl/egovBaseTemplate.css"); }
		 * 
		 * model.addAttribute("articleVO", boardVO);
		 * model.addAttribute("boardMasterVO", master);
		 * ////-----------------------------
		 * 
		 * return "egovframework/com/cop/bbs/EgovArticleReply"; }
		 */
		
		if (isAuthenticated) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			// final List<MultipartFile> files = multiRequest.getFiles("file_1");
			String atchFileId = "";

			if (!files.isEmpty()) {
				List<FileVO> result = fileManagerUtil.parseFileInf(files,"BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}
			
			board.setAtchFileId(UtilString.null2Blank(atchFileId));
			board.setReplyAt("Y");
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(board.getBbsId());
			board.setParnts(Long.toString(boardVO.getNttId()));
			board.setSortOrdr(boardVO.getSortOrdr());
			board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));
			/**
			 * //익명등록 처리 if(board.getAnonymousAt() != null &&
			 * board.getAnonymousAt().equals("Y")){
			 * board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
			 * board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
			 * board.setFrstRegisterId("anonymous");
			 * 
			 * } else { board.setNtcrId((user == null || user.getId() == null) ?
			 * "" : user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
			 * board.setNtcrNm((user == null || user.getName() == null) ? "" :
			 * user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
			 * 
			 * }
			 */
			board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지

			bbsMngService.insertBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		return "redirect:/commons/bbs_dq/selectBoardList.do";
	}

	/**
	 * 게시물 수정을 위한 수정페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/forUpdateBoardArticle.do")
	public String selectBoardArticleForUpdtDq(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model) 
			throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String errCode = "";

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());
		
		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
			bmvo = bbsAttrbService.selectBBSMasterInf(master);
			bdvo = bbsMngService.selectBoardArticle(boardVO);
			codeMap.put("cateCode", codeListService.getComCodeList(WiseConfig.PORTAL, bmvo.getCateCode()));
		} else {
			errCode = "BBS.ERRAUTH";
		}
		
		model.addAttribute("articleVO", bdvo);
		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);
		model.addAttribute("errCode", errCode);
		//model.addAttribute("brdMstrVO", bmvo);

		setusermenumapDq(bmvo.getBbsId(), model);

		return "/commons/bbs_dq/bbsNoticeUpdt";
	}

	/**
	 * 게시물에 대한 내용을 수정한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/bbs_dq/updateBoardArticle.do")
    public String updateBoardArticleDq(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, //BindingResult bindingResult, SessionStatus status,
	    RedirectAttributes re, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		String atchFileId = boardVO.getAtchFileId();

/*		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

		    boardVO.setFrstRegisterId(user.getUniqId());

		    BoardMaster master = new BoardMaster();
		    BoardMasterVO bmvo = new BoardMasterVO();
		    BoardVO bdvo = new BoardVO();

		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId(user.getUniqId());

		    bmvo = bbsAttrbService.selectBBSMasterInf(master);
		    bdvo = bbsMngService.selectBoardArticle(boardVO);

		    model.addAttribute("result", bdvo);
		    model.addAttribute("bdMstr", bmvo);

		    return "egovframework/com/cop/bbs/EgovNoticeUpdt";
		}*/

		//인증 취약점 보완...
		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
				    List<FileVO> result = fileManagerUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    board.setAtchFileId(atchFileId);
				} else {
				    FileVO fvo = new FileVO();
				    fvo.setAtchFileId(atchFileId);
				    int cnt = fileMngService.getMaxFileSN(fvo);
				    List<FileVO> _result = fileManagerUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				    fileMngService.updateFileInfs(_result);
				}
		    }

		    board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    board.setFrstRegisterId(user.getUniqId());

		    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

		    board.setNttCn(unscript(board.getNttCn())); // XSS 방지

		    bbsMngService.updateBoardArticle(board);
		    
		    re.addAttribute("bbsId", bdMstr.getBbsId());
		}
	
		return "redirect:/commons/bbs_dq/selectBoardList.do";
    }

	/**
	 * 게시물에 대한 내용을 삭제한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param bdMstr
	 * @param re
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bbs_dq/deleteBoardArticle.do")
	public String deleteBoardArticleDq(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
			@ModelAttribute("bdMstr") BoardMaster bdMstr, RedirectAttributes re, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		log.debug("USER : {}", user);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();

		if (isAuthenticated) {
			String bdRegID = bbsMngService.selectBoardRegID(boardVO);
			isAuthenticated = user.getUniqId().equals(bdRegID);
		}

		if (isAuthenticated) {
			board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setFrstRegisterId(user.getUniqId());

			bbsMngService.deleteBoardArticle(board);
			
			re.addAttribute("bbsId", bdMstr.getBbsId());
		}

		return "redirect:/commons/bbs_dq/selectBoardList.do";

	}
}
