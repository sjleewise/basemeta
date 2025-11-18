/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstCtrl.java
 * 2. Package : kr.wise.meta.stnd.web
 * 3. Comment : 표준항목 요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 27.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 27. :            : 신규 개발.
 *                    
 *                    
 *  *190117 url요청 시 마다 불필요하게 코드맵이 호출되어 속도저하를 일으킴.(modelattribute가 붙은 메소드는 모든 요청시에 실행되기 때문에 쿼리가 불필요하게 많이 조회됨)
 *       view만 관리하는 컨트롤러와 나머지 request&response를 관리하는 컨트롤러로 분리
 *       StndItemRqstViewCtrl.java , StndItemRqstCtrl.java                 
 */
package kr.wise.meta.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.dmnginfo.service.InfotpService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.web.StndWordCtrl.WamStwdAbrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("StdnItemRqstCtrl")
public class StndItemRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("rqstDtm");
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;
	
	@Inject
	private RequestApproveService requestApproveService;

//	private Map<String, Object> codeMap;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private InfotpService infotpService;

	@Inject
	private StndItemRqstService stndItemRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CodeListService codelistService;

	static class WaqSditms extends HashMap<String, ArrayList<WaqSditm>> {}
	
	static class WapDvCanAsms extends HashMap<String, ArrayList<WapDvCanAsm>> {}

	
    /** 표준항목 요청 리스트 조회.... @return insomnia */
    @RequestMapping("/meta/stnd/getitemrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WaqSditm> getStndItemRqstList (WaqMstr search) {
    	logger.debug("search:{}", search);

    	List<WaqSditm> list = stndItemRqstService.getStndItemRqstList(search);

    	return new IBSheetListVO<WaqSditm>(list, list.size());
    }

    /** 표준항목 분리 기능 호출 @return insomnia */
    @RequestMapping("/meta/stnd/getsditmdivjson.do")
    @ResponseBody
    public WaqSditm getItemdivision(WaqSditm search) {

    	WaqSditm result = stndItemRqstService.getItemWordInfo(search);

    	return result;
    }

    /** 표준항목 자동분할 기능 처리... @return insomnia */
    @RequestMapping("/meta/stnd/getsditmdvcanlist.do")
    @ResponseBody
    public IBSheetListVO<WapDvCanAsm> getItemDivisionList(WapDvCanDic data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);

    	List<WapDvCanAsm> list = stndItemRqstService.getItemDivisionList(data);

    	return new IBSheetListVO<WapDvCanAsm>(list, list.size());

    }
    
    /** 표준항목 자동분할 조회...  */
    @RequestMapping("/meta/stnd/getItemDivList.do")
    @ResponseBody
    public IBSheetListVO<WapDvCanAsm> getItemDivList(WapDvCanDic data, Locale locale) {
    	logger.debug("division:{}", data);
    	
    	List<WapDvCanAsm> list = stndItemRqstService.getItemDivList(data);
    	
    	return new IBSheetListVO<WapDvCanAsm>(list, list.size());
    	
    }

    /** 표준항목 리스트 등록 @throws Exception insomnia */
    @RequestMapping("/meta/stnd/regitemrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regStndItemRqstList(@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WaqSditm> list = data.get("data");
		
//		//범정부연계여부별 추가 검증시 필요한 값 세팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);
		
		int result = stndItemRqstService.register(reqmst, list);

		result += stndItemRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

    @RequestMapping("/meta/stnd/regWam2WaqStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqList(@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
    	List<WaqSditm> list = data.get("data");
    	
//    	//범정부연계여부에 따른 검증 추가를 위해 govYn 값을 셋팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);

    	int result = stndItemRqstService.regWam2Waq(reqmst, list);
    	result += stndItemRqstService.check(reqmst);
    	
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

    @RequestMapping("/meta/stnd/delSditmrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delStndItemRqstList(@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WaqSditm> list = data.get("data");
    	
//    	//범정부연계여부에 따른 검증 추가를 위해 govYn 값을 셋팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);

    	int result = stndItemRqstService.delStndItemRqstList(reqmst, list);
    	result += stndItemRqstService.check(reqmst);

    	String resmsg;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

    	String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

    	//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
    	reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

    @RequestMapping("/meta/stnd/approveStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveStndItem (@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
		if(!checkapprove) {
			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
		}

    	List<WaqSditm> list = data.get("data");

    	int result = stndItemRqstService.approve(reqmst, list);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		//승인요청 메일전송, 2019.02.11 
		//String reqURL = request.getRequestURI();
		//int tempResult = requestApproveService.sendMail(reqmst); 
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }


    @RequestMapping("/meta/stnd/regitemdivision.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regItemDivision(@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);

    	List<WaqSditm> list = data.get("data");
    	
//    	//범정부연계여부에 따른 검증 추가를 위해 govYn 값을 셋팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);

    	int result = stndItemRqstService.regitemdivision(reqmst, list);

    	result += stndItemRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }
    
    
    
    /** 표준항목 자동분할 조회...  */
    @RequestMapping("/meta/stnd/getItemDvRqstList.do")
    @ResponseBody
    public IBSheetListVO<WapDvCanAsm> getItemDvRqstList(WapDvCanDic data, Locale locale) {
    	logger.debug("division:{}", data);
    	
    	List<WapDvCanAsm> list = stndItemRqstService.getItemDvRqstList(data);
    	
    	return new IBSheetListVO<WapDvCanAsm>(list, list.size());
    	
    }
    
    @RequestMapping("/meta/stnd/regItemAutoDiv.do")
    @ResponseBody
    public IBSResultVO<WapDvCanAsm> regItemAutoDiv(@RequestBody WapDvCanAsms data, Locale locale,WapDvCanDic data2) throws Exception {
    	logger.debug("division:{}", data);
    	List<WapDvCanAsm> list = data.get("data");
   	
    	//항목분할요청 ID
    	Map<String, String> resultMap = stndItemRqstService.regItemAutoDiv(list,data2);
    	int result = Integer.parseInt(resultMap.get("result"));
    	String resmsg;
    	
    	if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
    	}
    	String action = WiseMetaConfig.IBSAction.REG.getAction();
    	
    	return new IBSResultVO<WapDvCanAsm>(resultMap, result, resmsg, action);
    }
    
    @RequestMapping("/meta/stnd/delItemAutoDiv.do")
    @ResponseBody
    public IBSResultVO<WapDvCanAsm> delItemAutoDiv(@RequestBody WapDvCanAsms data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);
    	List<WapDvCanAsm> list = data.get("data");
    	
    	//항목분할요청 ID
    	Map<String, String> resultMap = stndItemRqstService.delItemAutoDiv(list);
    	int result = Integer.parseInt(resultMap.get("result"));
    	String resmsg;
    	
    	if(result > 0 ){
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	
    	return new IBSResultVO<WapDvCanAsm>(resultMap, result, resmsg, action);
    }
    
    /** 표준단어 요청 저장 - IBSheet JSON @return insomnia
	 * @throws Exception */
	@RequestMapping("/meta/stnd/regStndItemByDiv.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStndItemByDiv(@RequestBody WapDvCanAsms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {

		ArrayList<WapDvCanAsm> list = data.get("data");
		logger.debug("data:{}",  data);

		reqmst.setBizDcd("DIC");
		reqmst.setBizDtlCd("SDITM");
		reqmst.setRqstStepCd("N");
		//요청번호가 없을 경우 요청번호를 먼저 채번한다.
		reqmst = requestMstService.getBizInfoInit(reqmst);
		
//		//범정부연계여부에 따른 검증 추가를 위해 govYn 값을 셋팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);
		
		//REQ 저장
		int result = stndItemRqstService.regStndItemByDiv(reqmst, list);
		stndItemRqstService.check(reqmst);

		String resmsg ;

		if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.SAVE", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.SAVE", null, locale);
    	}
		logger.debug("reqmst:{}",  reqmst);
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
    @RequestMapping("/meta/stnd/regWam2WaqStndItem2.do")
    @ResponseBody
    public String regWam2WaqList2(@RequestBody WaqSditms data, WaqMstr reqmst, Locale locale, HttpSession session) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
    	List<WaqSditm> list = data.get("data");
    	
//    	//범정부연계여부에 따른 검증 추가를 위해 govYn 값을 셋팅
//		String govYn = (String)session.getAttribute("govYn");
//		reqmst.setGovYn(govYn);

    	int result = stndItemRqstService.regWam2Waq(reqmst, list);
    	result += stndItemRqstService.check(reqmst);
    	
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return "/meta/stnd/stndtot_rqst";

    }
    
	
    /** 미사용 표준항목 요청 리스트 조회.... @return AJH */
    @RequestMapping("/meta/stnd/getUnuseSditm.do")
    @ResponseBody
    public IBSheetListVO<WaqSditm> getUnuseSditmList (WaqSditm data) {
    	logger.debug("data:{}", data);

    	List<WaqSditm> list = stndItemRqstService.getUnuseStndItemRqstList(data);

    	return new IBSheetListVO<WaqSditm>(list, list.size());
    }
}
