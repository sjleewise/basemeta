package kr.wise.portal;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.AES256Cipher;
import kr.wise.commons.cmm.security.RSA;
import kr.wise.commons.cmm.security.UtilEncryption;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.handler.MailHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.user.service.UserLoginService;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dashboard.service.DqDashService;
import kr.wise.dq.dashboard.service.DqdashSystemVO;
import kr.wise.meta.sitemap.service.SiteMapService;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashService;
import kr.wise.portal.dashboard.service.UpdateCntVO;
import kr.wise.portal.myjob.service.ApprReqService;
import kr.wise.portal.myjob.service.RequestJobService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Controller
public class IndexController {

	private final Logger log = LoggerFactory.getLogger(getClass());


	@Inject
	private UserLoginService loginService;

	@Inject
	private RequestJobService reqJobService;

	@Inject
	private ApprReqService apprReqService;

	@Inject
	private BBSAttributeManageService bbsAttrbService ;

	@Inject
	private BBSManagerService bbsMngService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private TotalDashService totalDashService;


	@Inject
	private DqDashService dqdashService;
	
	
	@Inject
	private UserService userService;
	
	@Inject
	private SiteMapService siteMapService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MenuManageService menuManageService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private UsergMenuMapService usergMenuMapService;

	@Inject
	private JavaMailSender mailSender;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Value("#{configure['wiseda.langDcd']}")     
	private String langDcd;
	
	@Value("#{configure['wiseda.main.name']}")     
	private String mainSite;
	
//	@Value("#{configure['wiseda.stwd.dcd']}")     
//	private String stwdDcd;
	
	@Value("#{configure['wiseda.login.enc.yn']}")     
	private String encYn;
	
	//범정부 메타 연계 관련 사용 여부
	@Value("#{configure['wiseda.gov.yn']}")     
	private String govYn;

	@RequestMapping("/")
	public String goLogin(HttpSession session, Model model) throws Exception {
		if(UserDetailHelper.isAuthenticated()) {
		//	return "redirect:/dqmain.do";
			
			if(mainSite.equals("DQ")){
				return "forward:/dqmain.do";
			}else{
				return "forward:/main.do";
			}
						
		} else {
			//RSA ?�호??추�?
	        /*KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	        generator.initialize(512);
	        KeyPair keyPair = generator.genKeyPair();
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyPair.getPublic();
	        PrivateKey privateKey = keyPair.getPrivate();

	        session.setAttribute("_RSA_WEB_Key_", privateKey);   //?�션??RSA 개인?��? ?�션???�?�한??
	        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
	        String publicKeyModulus = publicSpec.getModulus().toString(16);
	        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

	        model.addAttribute("RSAModulus", publicKeyModulus);
	        model.addAttribute("RSAExponent", publicKeyExponent);

	        log.debug("RSAModulus[{}], RSAExponent[{}]", publicKeyModulus, publicKeyExponent);*/

	        /*request.setAttribute("RSAModulus", publicKeyModulus);  //로그???�에 Input Hidden??값을 ?�팅?�기?�해??
	        request.setAttribute("RSAExponent", publicKeyExponent);   //로그???�에 Input Hidden??값을 ?�팅?�기?�해??
	        rest = "login";*/

			//SSO 로그???�계
//			return "/ssologin";
			
			String sLangDcd = UtilString.null2Blank(langDcd); 
			
			if(sLangDcd.equals("en")) {
			
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
			}else if ("kr".equals(sLangDcd) || "ko".equals(sLangDcd)) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			} else {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			}

			//?�반 로그?�일 경우
			return "redirect:/loginform.do";
		}
	}

	/** @return insomnia */
	private String mainForward() {
		if ("META".equals(mainSite)) {
			return "forward:/main.do";
		} else if ("DQ".equals(mainSite)) {
			return "forward:/dqmain.do";
		} else if ("ADVISOR".equals(mainSite)) {
			return "forward:/admain.do";
		} else {
			return "forward:/main.do";
		}
	}
	@RequestMapping("/login.do")
	public String checkLogin(HttpSession session, HttpServletRequest request,  @ModelAttribute("loginVO") LoginVO loginVO, Model model) throws Exception {
		// 범정부 정보를 세션에 저장
    	session.setAttribute("govYn", govYn);
    	
		if(UserDetailHelper.isAuthenticated()) {
			
			return mainForward();
//			if(mainSite.equals("DQ")){
//				return "forward:/dqmain.do";
//			}else{
//				return "forward:/main.do";
//			}
		}
		// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
		if(loginVO != null) {
			
			String sLangDcd = UtilString.null2Blank(langDcd); 
			
			if(sLangDcd.equals("en")) {
			
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
			} else if ("kr".equals(sLangDcd) || "ko".equals(sLangDcd)) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			} else {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			}
			   
//			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
//	        session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	        
		    String privateKey =(String)  session.getAttribute("__rsaPrivateKey__");
	        session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
	        if (privateKey == null) {
	            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
	        }

//	        String rsausername = decryptRsa(privateKey, loginVO.getSecuredUsername());
//            String rsapassword = decryptRsa(privateKey, loginVO.getSecuredPassword());
	        
	        //aes로 교체
	        String rsausername = AES256Cipher.decrypt(loginVO.getSecuredUsername(), privateKey);
            String rsapassword = AES256Cipher.decrypt(loginVO.getSecuredPassword(), privateKey);

            loginVO.setId(rsausername);
            loginVO.setPassword(rsapassword);

//			loginVO.setId(decodeBase64(loginVO.getSecuredUsername()));
//			loginVO.setPassword(decodeBase64(loginVO.getSecuredPassword()));

			log.debug("userid[{}], pass[{}]", loginVO.getId(), loginVO.getPassword());
			
			//META,DQ,ALL
			session.setAttribute("mainSite", mainSite);
			
			//동음이의어 이음동의어 구분 (동음이의어:H,이음동의어:A,둘다허용:T)
//			session.setAttribute("stwdDcd", stwdDcd); 

			////로그인 화면에서 호출된 경우
			LoginVO resultVO = null;
			if(encYn.equals("N")) {
				resultVO = loginService.actionLogin(loginVO);
			} else if(encYn.equals("Y")) {
				int idCnt = loginService.idCnt(loginVO);
				
				if(idCnt < 1) {
					return "forward:/loginform.do?loginError=iderror";
				}
				
				log.debug("idCnt >>> " + idCnt);
				
				String salt = loginService.getSaltKey(loginVO);
				
				log.debug("salt >>> " + salt);
				
				resultVO = loginService.actionLogin(loginVO, salt);
			}
			
			//Email을 복호화 한다.
	    	UtilEncryption ue = null;
	    	String aes_key = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.AES_KEY, null, Locale.getDefault());
			ue = new UtilEncryption(aes_key);
			
			resultVO.setEmail(ue.decrypt(resultVO.getEmail()));
			
			session.setAttribute("isPwdExpYn", resultVO.getIsPwdExpYn());
			
	        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
	        	//FAIL COUNT <- 1
	        	if(encYn.equals("Y"))
	        		loginService.updateFailCnt(resultVO);
	        	
	        	resultVO.setIp(request.getRemoteAddr());
	        	// // 2-1. 로그인 정보를 세션에 저장
	        	session.setAttribute("loginVO", resultVO);
	        		        	

//	        	return "forward:/main.do";
	        } else {
	        	if(encYn.equals("Y")) {
		        	int fcnt = loginService.selectFailCnt(loginVO);
		    		int lcnt = loginService.selectLockCnt(loginVO);
		    		
		        	if(fcnt >= lcnt-1) {
		        		loginService.updateIsLock(loginVO);
		    			return "forward:/loginform.do?loginError=lockerror";
		    		} else {
		    			loginService.updateFailCnt1(loginVO);
		    			return "forward:/loginform.do?loginError=pwerror";
		    		}
	        	} else {
	        		//로그인 오류
		        	return "forward:/loginform.do?loginError=error";
	        	}
	        }
		} else {
//			return "/login";
		}

		usergMenuMapService.setLangDcdMenu(langDcd);
		
		return mainForward();

//		if(mainSite.equals("DQ")){
//			return "forward:/dqmain.do";
//		}else{
//			return "forward:/main.do";
//		}
	}

	@RequestMapping("ssologinexec.do")
	public String goSsoLoginExec() {
		log.debug("soologinexe start");
		return "/commons/sso/login_exec";
	}

	@RequestMapping("/loginform.do")
	public String goLoginForm(HttpSession session, ModelMap model, String loginError) throws Exception {
		
		String sLangDcd = UtilString.null2Blank(langDcd); 
		
		if(sLangDcd.equals("en")) {
		
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
		} else if ("kr".equals(sLangDcd) || "ko".equals(sLangDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
			
		} else {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
			
		}
		   
		//META,DQ,ALL
		session.setAttribute("mainSite", mainSite);

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(WiseConfig.KEY_SIZE);

        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        KeyGenerator generator2 = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        generator2.init(128, random);
        SecretKey secureKey = generator2.generateKey();
        String sKeyString = Hex.encodeHexString(secureKey.getEncoded());   
     // 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
        session.setAttribute("__rsaPrivateKey__", sKeyString);
        System.out.println(sKeyString);
        
     // 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
//        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
//
//        String publicKeyModulus = publicSpec.getModulus().toString(16);
//        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

//        model.addAttribute("publicKeyModulus", publicKeyModulus);
//        model.addAttribute("publicKeyExponent", publicKeyExponent);
        
      //로그인 에러시 에러메세지 호출
        log.debug("{}", loginError);
        if(loginError != null && loginError.equals("error")){
        	model.addAttribute("loginError", "error");
        } else if(loginError != null && loginError.equals("iderror")) {
        	model.addAttribute("loginError", "iderror");
        } else if(loginError != null && loginError.equals("pwerror")) {
        	model.addAttribute("loginError", "pwerror");
        } else if(loginError != null && loginError.equals("lockerror")) {
        	model.addAttribute("loginError", "lockerror");
        }

		return "/login";
	}

	
	   
	@RequestMapping("/ssologin.do")
	public String checkSsoLogin(HttpSession session, HttpServletRequest request,  @ModelAttribute("loginVO") LoginVO loginVO, Model model) throws Exception {
		String SSOID = (String) session.getAttribute("SSO_ID");
		log.debug("SSOID:{}", SSOID);
		
//		SSOID = "EX0465";
	
		// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
		if(SSOID != null && !"".equals(SSOID)) {

			//RSA 복호화 기능 추가
//			PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_Key_");
//			session.removeAttribute("_RSA_WEB_Key_"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
//			loginVO.setId(decryptRsa(privateKey, loginVO.getSecuredUsername()));
//			loginVO.setPassword(decryptRsa(privateKey, loginVO.getSecuredPassword()));
//			loginVO.setId(decodeBase64(loginVO.getSecuredUsername()));
//			loginVO.setPassword(decodeBase64(loginVO.getSecuredPassword()));

//			log.debug("userid[{}], pass[{}]", loginVO.getId(), loginVO.getPassword());
//			LoginVO loginVO = new LoginVO();
			loginVO.setId(SSOID);
			//로그인 화면에서 호출된 경우
			LoginVO resultVO = loginService.actionSsoLogin(loginVO);

			if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

				// 2-1. 로그인 정보를 세션에 저장
				resultVO.setIp(request.getRemoteAddr()); 
				
				session.setAttribute("loginVO", resultVO);

				return "forward:/main.do";
				
			} else {
				//로그인 오류 								
				String vMsg = "로그인ID 또는 비밀번호가 일치하지 않습니다."; 
				
				String vUrl = request.getContextPath() + "/loginform.do";
				
				model.addAttribute("msg",vMsg);
				model.addAttribute("vurl", vUrl);
								
				return "/errmessage";
			}
		} else {
//			return "/login";
		}

		return "redirect:/";
	}
	
	
	@RequestMapping("/errmessage.do")
	public String goErrMessage(HttpSession session, Model model) throws Exception {
		
		return "/errmessage";
	}
	


	public String decodeBase64(String input) throws Exception, IOException {
		 BASE64Decoder decoder = new BASE64Decoder();

//		 String tmpStr = new StringBuffer(input).toString();

		 return new String(decoder.decodeBuffer(input), "UTF-8");

		 /*String decUid, decPwd;

		 byte[] b1, CookieString1, CookieString2;
		 String struid = request.getParameter("userId");
		 String strpasswd = request.getParameter("passwd");
		 decUid =new StringBuffer(struid).toString();
		 decPwd = new StringBuffer(strpasswd).toString();
		 CookieString1 = decoder.decodeBuffer(decUid);
		 CookieString2 = decoder.decodeBuffer(decPwd);
		 uid = new String(CookieString1, "UTF-8");
		 passwd = new String(CookieString2, "UTF-8");
*/
	}


    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        log.debug("will decrypt : {}" , securedValue);
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
    /**
     * BigInteger를 사용해 hex를 byte[] 로 바꿀 경우 음수 영역의 값을 제대로 변환하지 못하는 문제가 있다.
     */
    @Deprecated
    public static byte[] hexToByteArrayBI(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

        public static String base64Encode(byte[] data) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(data);
        return encoded;
    }

    public static byte[] base64Decode(String encryptedData) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoded = decoder.decodeBuffer(encryptedData);
        return decoded;
    }

	@RequestMapping("/main.do")
	public ModelAndView goMain(HttpSession session, HttpServletRequest request, @ModelAttribute("loginVO") LoginVO loginVO) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("user = {}",user);
    	ModelAndView mv = new ModelAndView();

/*    	// 1. 로그인 세션 체크
		if(user == null) {
			//세션 미존재시
			log.debug("loginVO = {}",user);
			// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
			if(loginVO != null) {
				//로그인 화면에서 호출된 경우
				LoginVO resultVO = loginService.actionLogin(loginVO);
		        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

		        	// 2-1. 로그인 정보를 세션에 저장
		        	request.getSession().setAttribute("loginVO", resultVO);

					BoardVO vo = new BoardVO();
					vo.setBbsId("BBSMSTR_000000000002");

					vo.setFirstIndex(1);
					vo.setRecordCountPerPage(5);

					//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
					Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

					Map<String,Object> param = new HashMap<String,Object>();

		    		param.put("firstIndex", "1");
		    		param.put("recordCountPerPage", "5");
		    		param.put("userId", resultVO.getId());

		    		List<WaqReqMst> reqResult = reqJobService.searchMyRegReq(param);
		    		List<VWaaAprvPrcs> aprvResult = apprReqService.apprReqList(param);
		    		//건수가 모자른 경우 처리
		    		for(int i=reqResult.size(); i<5;i++) {
		      			WaqReqMst waqReqMst = new WaqReqMst();
		      			waqReqMst.setBizNm("&nbsp");
		    			reqResult.add(waqReqMst);
		    		}
		    		for(int i=aprvResult.size(); i<5;i++) {
		    			VWaaAprvPrcs vWaaAprvPrcs = new VWaaAprvPrcs();
		    			vWaaAprvPrcs.setBizNm("&nbsp");
		    			aprvResult.add(vWaaAprvPrcs);
		    		}
		        	mv.addObject("bbsList", map.get("resultList"));
		        	mv.addObject("reqList", reqResult);
		        	mv.addObject("aprvList", aprvResult);
		    		mv.setViewName("/main");

		    		return mv;
		        } else {
		        	//로그인 정보가 틀린 경우
		        	//메세지 추가해도 괜찮을듯..
		    		mv.setViewName("/login");
		        }
			} else {
				//로그인 화면에서 호출되지 않은 경우(세션이 끊긴 경우)
	    		mv.setViewName("/login");
	        	return mv;
			}
		} else {*/
    	//세션 존재시
		//게시글 리스트
			BoardVO vo = new BoardVO();
			vo.setBbsId("BBSMSTR_000000000007");

			vo.setFirstIndex(0);
			vo.setRecordCountPerPage(6);

			//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
			Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

			Map<String,Object> param = new HashMap<String,Object>();

    		param.put("firstIndex", 0);
    		param.put("recordCountPerPage", 6);
    		param.put("userId", user.getId());


    		WaqMstr record = new WaqMstr() ;
    		record.setRqstStepCd("Q");
    		
    		List<WaqMstr> rqstMyList = requestMstService.getRqstMyListForMain(record);
    		
    		for(int i=rqstMyList.size();i<5;i++) {
    			WaqMstr waqMstr = new WaqMstr();
    			waqMstr.setRqstNm("&nbsp");
    			rqstMyList.add(waqMstr);
    		}

    		List<WaqMstr> rqstToDoList = requestMstService.getRqstToDoListForMain(record);
    		for(int i=rqstToDoList.size();i<5;i++) {
    			WaqMstr waqMstr = new WaqMstr();
    			waqMstr.setRqstNm("&nbsp");
    			rqstToDoList.add(waqMstr);
    		}
        	mv.addObject("bbsList", map.get("resultList"));
        	mv.addObject("reqList", rqstMyList);
        	mv.addObject("aprvList", rqstToDoList);

        	/* 표준데이터 조회 */
    		List<TotalCountVO> stndresult =  totalDashService.selectTotCntWAMs(userid);
    		
    		int ntnum = 0;
    		int nntnum = 0;
    		for(int i=0; i<stndresult.size(); i++) {
    			if(stndresult.get(i).getCntName().equals("유사어")) ntnum = i;
    			if(stndresult.get(i).getCntName().equals("금지어")) nntnum = i;
    		}
    		
    		if(ntnum != 0 && nntnum != 0) {
	    		stndresult.get(ntnum).setCntName("유사어/금지어");
	    		stndresult.get(ntnum).setTotCnt(stndresult.get(ntnum).getTotCnt() + " /" + stndresult.get(nntnum).getTotCnt());
	    		stndresult.remove(nntnum);
    		}
    		
    		/* 데이터모델 조회 */
    		List<UpdateCntVO> modelresult = totalDashService.selectUpdateCntStat(userid);

    		mv.addObject("stndresult", stndresult);
    		mv.addObject("modelresult", modelresult);


    		//모델 vs DB 일치율
//        	DbcAllErrChartVO chartVO = totalDashService.selectErrChart();
//
//        	StringBuffer errChartSb = new StringBuffer();
//        	errChartSb.append("<chart caption='모델 vs DB 일치율'>");
//        	errChartSb.append("<set name='오류건수' value='" + (chartVO.getTotal() - chartVO.getNoErr()) + "'/>");
//        	errChartSb.append("<set name='정상건수' value='" + chartVO.getNoErr() + "'/>");
//        	errChartSb.append("</chart>");
//
//
//        	mv.addObject("errChartSb", errChartSb);
    		mv.setViewName("/damain");

    		return mv;
//		}

//    	return mv;

/*		//TODO 로그인아이디, 비밀번호 체크
		if(!"".equals(loginId) && !"".equals(loginPwd)) {
			String userId = session.getId(); // userId 는 추후 loginId 를 이용하여 DB에서 사용자 정보 읽어옴.
			SessionListener sessionListener = new SessionListener();
			sessionListener.setUserId(userId);
			session.setAttribute("SESSION_LISTENER", sessionListener);

			//TODO 로그인유저정보 세션 등록

			// 사용자의 메뉴정보 세션 등록
			// session.setAttribute("MENUS", menuService.getMenusByGroupId(""));

			session.setAttribute("ssUsrId", loginId);
			session.setAttribute("ssLoginId", "test");

			return "redirect:/main";
		} else {
			return "redirect:/";
		}*/

	}
	@RequestMapping("/dqmain.do")
	public ModelAndView goDqMain() throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("user = {}",user);

		ModelAndView mv = new ModelAndView();


		BoardVO vo = new BoardVO();
		vo.setBbsId("BBSMSTR_000000000007");

		vo.setFirstIndex(0);
		vo.setRecordCountPerPage(6);

		//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
		Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

		Map<String,Object> param = new HashMap<String,Object>();

		param.put("firstIndex", 0);
		param.put("recordCountPerPage", 6);
		param.put("userId", user.getId());
		param.put("comCd", user.getComCd());


		//내 요청정보 조회
		WaqMstr record = new WaqMstr() ;
		record.setRqstStepCd("Q");
		List<WaqMstr> rqstMyList = requestMstService.getRqstMyListForMain(record);
		for(int i=rqstMyList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstMyList.add(waqMstr);
		}

		//내 결재정보 조회
		List<WaqMstr> rqstToDoList = requestMstService.getRqstToDoListForMain(record);
		for(int i=rqstToDoList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstToDoList.add(waqMstr);
		}
    	mv.addObject("bbsList", map.get("resultList"));
    	mv.addObject("reqList", rqstMyList);
    	mv.addObject("aprvList", rqstToDoList);

    	//기준정보 현황 조회
		List<TotalCountVO> criresult =  dqdashService.getTotCntDqCri();

		/* 업무영역별 데이터품질 개선활동 진행현황  */
		List<DqdashSystemVO> bizareaImpvResult = dqdashService.getBizAareaImpvList();


		/* 데이터모델 조회 */
		List<UpdateCntVO> modelresult = totalDashService.selectUpdateCntStat(userid);
		mv.addObject("modelresult", modelresult);

		mv.addObject("criresult", criresult);
		mv.addObject("bizareaImpvResult", bizareaImpvResult);

		mv.setViewName("/dqmain");

		return mv;

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/notAuth.do")
	public String notAuth(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
		return "/notAuth";
	}

	@RequestMapping(value={"/leftMenu.do", "*/leftMenu.do", "*/*/leftMenu.do"})
	public ModelAndView leftMenu() {
		ModelAndView mv = new ModelAndView("/leftMenu");

		return mv;
	}

	@RequestMapping(value={"/gen_cont.do"})
	public String goGenCont() {

		return "/gen_cont";
	}

	@RequestMapping("/rqstCount.do")
	@ResponseBody
	public List getRqstCountJSON(@ModelAttribute("loginVO") LoginVO loginVO) throws Exception{
		//등록요청건수, 결재대상건수 조회...
//    	int rqstMyCount = requestMstService.getRqstMyListCount(new WaqMstr());
    	WaqMstr waqMstr = requestMstService.getRqstCount(new WaqMstr());
    	int rqstToDoCount = requestMstService.getRqstToDoListCount(new WaqMstr());
		ArrayList list = new ArrayList();
		list.add(0, waqMstr.getRqstTmpCount() );
		list.add(1, waqMstr.getRqstMyCount());
		list.add(2, rqstToDoCount);
//		System.out.println(list);
		return list;
	}


	/** 요청목록, 결재대상 더블클릭시 해당 링크페이지 이동 메서드...*/
	/** @param waqmst
	/** @return yeonho */
	@RequestMapping("/goRqstPage.do")
	public String goRqstPage(WaqMstr reqmst) {
		log.debug("마스터정보 : {}", reqmst);

		WaaBizInfo bizInfo = requestMstService.getBizInfo(reqmst);

		log.debug("bizInfo : {}",bizInfo);
		String url = bizInfo.getUrl() + "?rqstNo=" + reqmst.getRqstNo() + "&bizDcd=" + bizInfo.getBizDcd() + "&bizDtlCd=" + bizInfo.getBizDtlCd();
		return "redirect:" + url;
	}
	
	@RequestMapping("/changePassword.do")
	public String goChangePassword(WaaUser user) {
		log.debug("유저정보 : {}", user);
		
		return "/changePassword";
	}

	@RequestMapping("/setlang.do")
	public String setLang(HttpSession session, Model model, String pLangDcd) throws Exception {
			
		log.debug("pLangDcd :: ", pLangDcd);
		log.debug("langDcd :: ", langDcd);
		
		if("en".equals(pLangDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
		} else if ("kr".equals(pLangDcd) || "ko".equals(langDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
			
		} else {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.getDefault());
			
		}
		
		usergMenuMapService.setLangDcdMenu(pLangDcd);
		//일반로그인일 경우
		return mainForward();
	}
	//회원가입
	@RequestMapping("/userRegPop.do")		
	public String userRegPop(HttpSession session){
		log.debug("Clicked Reg");
		
//		List<CodeListVo> comCd = cmcdCodeService.getCodeList("COM_CD");
//		log.debug("comCd >>> " + comCd);
		session.setAttribute("comCd", cmcdCodeService.getCodeList("COM_CD"));
		
		return "/commons/user/popup/userRegPop";		
	}

	//팝업창에서 가입버튼 클릭시
	@RequestMapping("/join.do")
	public void registered(WaaUser user) throws Exception{
		log.debug("join");

		String url ="http://dq.wise.co.kr/verify.do?user_id="+RSA.base64Encode((user.getUserId().getBytes()))+
				"&user_email="+RSA.base64Encode((user.getEmailAddr().getBytes()));

		//메일 인증
		MailHandler sendMail=new MailHandler(mailSender);
		sendMail.setSubject("[WiseDQ 회원가입 이메일 인증]");
		sendMail.setText(new StringBuffer().append("<h3>메일 인증</h3>")
				.append("<a href='"+url)
				.append("'target='_blenk'>이메일 인증 확인</a>").toString());
		sendMail.setFrom("dq@wise.co.kr", "wise");
		sendMail.setTo(user.getEmailAddr());
		sendMail.send();

		//DB에 user등록
		userService.register(user);
	}
	
	
	//인증메일에서 인증확인 버튼 클릭시
	@RequestMapping(value="/verify.do")
	public String verifyEmail(@RequestParam("user_id") String user_id,@RequestParam("user_email") String user_email) throws Exception{

		String userId = new String(RSA.base64Decode(user_id));
		String userEmail = new String(RSA.base64Decode(user_email));

		log.debug(userId+", "+userEmail);

		userService.updateVerify(userId);
		
		return "redirect:/loginform.do";
	}

	//id중복체크
		//기존방식과 다름
	@ResponseBody
	@RequestMapping(value = "/idCheck.do")
	public int postIdCheck(HttpServletRequest req) throws Exception{

		log.info("post idCheck");

		String userId =req.getParameter("userId");		
		int idCheck =userService.idCheck(userId);

		log.info("idCheck >>> " + idCheck);
		int result=0;		

		if(idCheck>0) {
			result = 1;
		}

		log.debug("" + result);
		return result; 
	}

	
	@RequestMapping("/error.do")
	public String goErrorPaged(WaaUser user, @RequestParam("errCd") String errCd, Model model) { 
		//log.debug("유저정보 : {}", user);
		
//		log.debug("에러코드:{}",errCd);
		
		model.addAttribute("errCd",errCd);
		
		return "/commons/error/errorPage";
	}
	
	@RequestMapping("/loginback.do")
	public String goLoginBack(HttpSession session, Model model) throws Exception {
		
		session.invalidate();
		
		//일반 로그인일 경우
		return "/loginback";
	}
	
	@RequestMapping("/loginpwdno.do")
	public String checkLoginPwdNo(HttpSession session, HttpServletRequest request,  @ModelAttribute("loginVO") LoginVO loginVO, Model model) throws Exception {
		
		
		
		if(UserDetailHelper.isAuthenticated()) {
			return "redirect:/";
		}
		// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
		if(loginVO != null) {

			
			loginVO.setId(loginVO.getSecuredUsername());
			
			log.debug("userid[{}], pass[{}]", loginVO.getId(), loginVO.getSecuredPassword());

			String loginPwd = UtilString.null2Blank(loginVO.getSecuredPassword());
			
			if(!loginPwd.equals("meta")) {
			
				return "/loginback";
			}
			
			//로그인 화면에서 호출된 경우
			LoginVO resultVO = loginService.actionSsoLogin(loginVO);
			
	        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

	        	resultVO.setIp(request.getRemoteAddr()); 
	        	
	        	// 2-1. 로그인 정보를 세션에 저장
	        	session.setAttribute("loginVO", resultVO);

//	        	return "forward:/main.do";
	        } else {
	        	//로그인 오류
	        	return "forward:/loginform.do?loginError=error";
	        }
		} else {
//			return "/login";
		}

		return "redirect:/";
	}

}
