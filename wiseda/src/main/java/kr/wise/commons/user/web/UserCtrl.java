/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserCtrl.java
 * 2. Package : kr.wise.cmvw.user.controller
 * 3. Comment :
 * 4. 작성자  : (ycyoo)유연철
 * 5. 작성일  : 2013. 4. 24
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons.user.web;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.user.service.UserLoginService;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : UserCtrl.java
 * 3. Package  : kr.wise.commons.user.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 28. 오후 7:50:12
 * </PRE>
 */ 
@Controller("UserCtrl")
@RequestMapping("/commons/user/*")
public class UserCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

//	IBSJsonSearch ibsresult = new IBSJsonSearch();
//	IBSResult ibsres = new IBSResult();

	static class WaaUsers extends HashMap<String, ArrayList<WaaUser>> { }

	private Map<String, Object> codeMap;

	@Inject
	private UserService userService;

	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;
	
	@Inject
	private UserLoginService loginService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/** 사용자 관리 화면 */
	@RequestMapping("user_lst.do")
	public String formpage() {
		return "/commons/user/user_lst";
	}
	
	/** 평가시스템 업무대행용 로그인 by 관리자 
	 * @throws Exception */
	@RequestMapping("userloginbyadmin.do")
	public String gologinbyadmin(WaaUser uservo, HttpSession session, HttpServletRequest request) throws Exception {
		logger.debug("업무대행로그인정보:{}",uservo);
		LoginVO resultVO = loginService.actionLoginbyAdmin(uservo);
        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
        	//현재 사용자 정보를 가져온다.
        	LoginVO loginUserVO = (LoginVO) UserDetailHelper.getAuthenticatedUser();
        	
        	//이미 관리자 id가 세션에 있는 경우 업무대행 중 이므로 adminid로 셋팅한다.
        	//아닌 경우 세션의 uniqid를 셋팅한다.
        	String adminid = loginUserVO.getAdminId();
        	if (StringUtils.hasText(adminid)) {
        		resultVO.setAdminId(adminid);
        	} else {
        		resultVO.setAdminId(loginUserVO.getUniqId());
        	}
        	
        	resultVO.setIp(request.getRemoteAddr());
        	
        	// 2-1. 로그인 정보를 세션에 저장
        	session.setAttribute("loginVO", resultVO);

//        	return "forward:/main.do";
        } else {
        	//로그인 오류
        	return "forward:/loginform.do?loginError=error";
        }
		
		
		return "redirect:/main.do";
	}



	/** 사용자 팝업 화면 */
	@RequestMapping("pop/userSearchPop.do")
	public String gosubjSearchPop(@ModelAttribute("search") WaaUser search, Model model, Locale locale) {
		logger.debug("{}", search);

		ModelAndView mv = new ModelAndView();
		if(!UtilString.isBlank(search.getUserId())) {
			model.addAttribute("userId" , search.getUserId());
		}
		return "/commons/user/popup/userSearchPop";
	}

	/** 사용자 팝업 화면 */
	@RequestMapping("popup/userSearchPop.do")
	public String gouserSearchPop(@ModelAttribute("search") WaaUser search, Model model, Locale locale) {
		logger.debug("{}", search);

		
		//if(!UtilString.isNull(search.getUsergTypCd())){
		//	model.addAttribute("usergTypCd", search.getUsergTypCd());
		//}
		return "/commons/user/popup/userSearchPop";
	}

	/** dba검색 팝업 화면 */
	@RequestMapping("popup/dbaSearchPop.do")
	public String goudbaSearchPop(@ModelAttribute("search") WaaUser search, Model model, Locale locale) {
		logger.debug("{}", search);

		
		//if(!UtilString.isNull(search.getUsergTypCd())){
		//	model.addAttribute("usergTypCd", search.getUsergTypCd());
		//}
		return "/commons/user/popup/dbaSearchPop";
	}
	
	/** 사용자 리스트 조회 - IBSheet JSON */
	@RequestMapping("userSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUser> selectList(@ModelAttribute WaaUser search) {
		logger.debug("{}", search);
		String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
		List<WaaUser> list = userService.getList(search, aes_key);
		return new IBSheetListVO<WaaUser>(list, list.size());
	}


	/** 사용자 리스트 조회 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("getuserlistbydept.do")
	@ResponseBody
	public IBSheetListVO<WaaUser> getUserListbyDept(@ModelAttribute WaaUser search) throws Exception {
		logger.debug("{}", search);
		String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
		List<WaaUser> list = userService.getUserListbyDept(search, aes_key);
		return new IBSheetListVO<WaaUser>(list, list.size());
	}
	
	/** DBA 리스트 조회 - IBSheet JSON 이상익
	 * @throws Exception */
	@RequestMapping("getDbalistbydept.do")
	@ResponseBody
	public IBSheetListVO<WaaUser> getDbaListbyDept(@ModelAttribute WaaUser search) throws Exception {
		logger.debug("{}", search);
		search.setUsergTypCd("DB");
		String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
		List<WaaUser> list = userService.getUserListbyDept(search, aes_key);
		return new IBSheetListVO<WaaUser>(list, list.size());
	}

	/** 사용자 리스트 조회(로그인유저 부서기준 정렬) - IBSheet JSON */
	/** @param search
	/** @return yeonho */
	@RequestMapping("userSelectlistOrderByDeptNm.do")
	@ResponseBody
	public IBSheetListVO<WaaUser> selectListOrderByDeptNm(@ModelAttribute WaaUser search) {
		logger.debug("{}", search);
		String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
		List<WaaUser> list = userService.getListOrderByDeptNm(search, aes_key);
		return new IBSheetListVO<WaaUser>(list, list.size());
	}


	/** 사용자 리스트 등록 - IBSheet JSON */
	@RequestMapping("userReglist.do")
	@ResponseBody
	public IBSResultVO<WaaUser> regList(@RequestBody WaaUsers data, Locale locale) {

		logger.debug("{}", data);
		ArrayList<WaaUser> list = data.get("data");

		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getIbsStatus().equals("I") || list.get(i).getIbsStatus().equals("U")) {
				int usrCnt = userService.getUserCnt(list.get(i));
				
				if(usrCnt > 0) {
					int result = -1;
					String resmsg = "중복된 아이디가 있습니다. <br> 입력한 값을 확인해주세요";
	
					String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
					return new IBSResultVO<WaaUser>(result, resmsg, action);
				}
			}
		}

		String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
		
	    Set<String> set = new HashSet<String>();
	      
	    for(WaaUser user : list) {
	       WaaUser temp = user;
	       set.add(temp.getLoginAcId());
	    }
	      
	    if(list.size() != set.size()) {
	        int result = -1;
	        String resmsg = "중복된 아이디가 있습니다. <br> 입력한 값을 확인해주세요";
	        
	        String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

	        return new IBSResultVO<WaaUser>(result, resmsg, action);         
	    }

		int result = userService.regList(list, aes_key);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaUser>(result, resmsg, action);
	}

	/** 사용자 리스트 삭제 - IBSheet JSON */
	@RequestMapping("userDellist.do")
	@ResponseBody
	public IBSResultVO<WaaUser> delList(@RequestBody WaaUsers data, Locale locale) {

		logger.debug("{}", data);
		ArrayList<WaaUser> list = data.get("data");

		int result = userService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaUser>(result, resmsg, action);
	}

	/** 결재자 등록을 위한 팝업 화면 */
	@RequestMapping("userPop.do")
	public String approveuserPop(@RequestParam("aprgId") String aprgId) {
		return "/commons/damgmt/approve/popup/approveuser_pop";
	}

	/** 부서 팝업 화면 */
	@RequestMapping("popup/deptlst_pop.do")
	public String goDeptSearchPop(@ModelAttribute("search") WaaDept search, Model model, @RequestParam(value="row") String row, Locale locale) {
		logger.debug("파람값은 : {}", row);		
		model.addAttribute("row", row);
		return "/commons/user/popup/deptlst_pop";
	}
	
		
	/** 내 정보 변경 팝업 ...*/
	/** pOOh 15.10.29 
	 * @throws Exception */
	@RequestMapping("/commons/user/gousrInfoChngPop.do")
	public String gousrInfoChngPop(@ModelAttribute("search") WaaUser uservo, HttpSession session, Model model) throws Exception {
		LoginVO resultVO;
		
		String res = "/commons/user/popup/userInfoChngPop";
		
		if(uservo != null && uservo.getLoginAcId() != null && !uservo.getLoginAcId().equals("")) {
			logger.debug("uservo >>> " + uservo);
			resultVO = loginService.actionLoginbyAdmin(uservo);
		} else {
			resultVO = (LoginVO) session.getAttribute("loginVO");
			if(resultVO.getIsPwdExpYn().equals("Y")){
				//res = "/commons/user/popup/newUserPwdChngPop";
				res = "/commons/user/popup/userInfoChngPop";
			}
		}
		
		model.addAttribute("loginVO", resultVO);

		return res;
		
	}
	
	/** 내 정보 변경 */
	/** pOOh 15.10.29 */
	@RequestMapping("usrInfoChng.do")
	@ResponseBody
	public String usrInfoChng(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletResponse response) {

		//logger.debug("{}", loginVO);
		//logger.debug("==3:::userid[{}], pass[{}]", loginVO.getSecuredUsername(), loginVO.getSecuredPassword());
		LoginVO loginUserVO = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String salt = userService.selectSaltKey(loginVO.getSecuredUsername());
		int pwCnt = userService.checkBfPw(loginVO, salt);
		if(pwCnt < 1) {
			return "현재 비밀번호가 틀렸습니다. \n현재 비밀번호를 확인해주세요";
		}
		
		int result;
		String resmsg ="";
		if(loginUserVO.getIsAdminYn().equals("Y") || loginVO.getSecuredUsername().equals(loginUserVO.getId()) ) {
			WaaUser data = new WaaUser();
			data.setUserId(loginVO.getSecuredUsername());
			data.setLoginAcPwd(loginVO.getSecuredPassword());
			data.setLoginAcPwdEnc(loginVO.getSecuredPasswordEnc());
			
			//logger.debug("{}", data);
			
			result = userService.chngUserInfo(data);
			
			if(result > 0) {
				result = 0;
				resmsg = "저장이 완료되었습니다.";
				return resmsg;
			}
		}
		//관리자가 아니거나 요청한 사용자 정보가 존재하지 않을때
		response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
		
		//return "/commons/user/popup/userInfoChngPop";
		return resmsg;
	}
	
	/** 사용자 비밀번호변경 팝업 화면 
	 * @throws Exception */
	@RequestMapping("popup/userPWChangePop.do")
	public String goPasswordChange(HttpSession session, @ModelAttribute("search") WaaUser search, Model model, Locale locale) throws Exception {
				
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(WiseConfig.KEY_SIZE);

        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
        session.setAttribute("__rsaPrivateKey__", privateKey);

        // 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

        String publicKeyModulus = publicSpec.getModulus().toString(16);
        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

        model.addAttribute("publicKeyModulus", publicKeyModulus);
        model.addAttribute("publicKeyExponent", publicKeyExponent);
		
		return "/commons/user/popup/userPWChangePop";
	}
	
	@RequestMapping("userPwInit.do")
	@ResponseBody
	public String userPwInit(WaaUser data, Locale locale) {

		logger.debug("userPwInit >>> {}", data);
		WaaUser userVo = data;

		int result = userService.userPwInit(userVo);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();


		return resmsg;
	}
	

	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");

		List<CodeListVo> userglist = codelistService.getCodeList(WiseMetaConfig.CodeListAction.usergroup);
//		String usergroup2 = UtilJson.convertJsonString(codelistService.getCodeList("usergroup"));
		String usergroup1 = UtilJson.convertJsonString(codelistService.getCodeListIBS(userglist));
//		codeMap.put("usergroup", usergroup2);
		codeMap.put("userglist", userglist);
		codeMap.put("usergp", usergroup1);

		//공통코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		

		return codeMap;
	}



}
