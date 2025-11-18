/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
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
package kr.wise.dq.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

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
import kr.wise.dq.stnd.service.WdqStndItemRqstService;
import kr.wise.dq.stnd.service.WdqpDvCanAsm;
import kr.wise.dq.stnd.service.WdqqSditm;

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

@Controller("wdqStdnItemRqstCtrl")
public class WdqStndItemRqstCtrl {

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
	private WdqStndItemRqstService WdqStndItemRqstService;

	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private CodeListService codelistService;

	static class WdqqSditms extends HashMap<String, ArrayList<WdqqSditm>> {}
	
	static class WdqpDvCanAsms extends HashMap<String, ArrayList<WdqpDvCanAsm>> {}

	
    /** 표준항목 요청 리스트 조회.... @return insomnia */
    @RequestMapping("/dq/stnd/getitemrqstlist.do")
    @ResponseBody
    public IBSheetListVO<WdqqSditm> getStndItemRqstList (WaqMstr search) {
    	
    	logger.debug("search:{}", search);
    	List<WdqqSditm> list = WdqStndItemRqstService.getStndItemRqstList(search);
    	
    	return new IBSheetListVO<WdqqSditm>(list, list.size());
    }
    
//    /** 표준항목 분리 기능 호출 @return insomnia */
//    @RequestMapping("/dq/stnd/getsditmdivjson.do")
//    @ResponseBody
//    public WdqqSditm getItemdivision(WdqqSditm search) {
//
//    	WdqqSditm result = WdqStndItemRqstService.getItemWordInfo(search);
//
//    	return result;
//    }
//
//    /** 표준항목 자동분할 기능 처리... @return insomnia */
//    @RequestMapping("/dq/stnd/getsditmdvcanlist.do")
//    @ResponseBody
//    public IBSheetListVO<WdqpDvCanAsm> getItemDivisionList(WapDvCanDic data, Locale locale) throws Exception {
//    	logger.debug("division:{}", data);
//
//    	List<WdqpDvCanAsm> list = WdqStndItemRqstService.getItemDivisionList(data);
//
//    	return new IBSheetListVO<WdqpDvCanAsm>(list, list.size());
//
//    }
//    
//    /** 표준항목 자동분할 조회...  */
//    @RequestMapping("/dq/stnd/getItemDivList.do")
//    @ResponseBody
//    public IBSheetListVO<WdqpDvCanAsm> getItemDivList(WapDvCanDic data, Locale locale) {
//    	logger.debug("division:{}", data);
//    	
//    	List<WdqpDvCanAsm> list = WdqStndItemRqstService.getItemDivList(data);
//    	
//    	return new IBSheetListVO<WdqpDvCanAsm>(list, list.size());
//    	
//    }

    /** 표준항목 리스트 등록 @throws Exception insomnia */
    @RequestMapping("/dq/stnd/regitemrqstlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regStndItemRqstList(@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WdqqSditm> list = data.get("data");

		int result = WdqStndItemRqstService.register(reqmst, list);

		result += WdqStndItemRqstService.check(reqmst);
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
    
    

    @RequestMapping("/dq/stnd/regWam2WaqStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regWam2WaqList(@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
    	List<WdqqSditm> list = data.get("data");

    	int result = WdqStndItemRqstService.regWam2Waq(reqmst, list);
    	logger.debug("체크1");
    	result += WdqStndItemRqstService.check(reqmst);
        logger.debug("체크2");
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

    @RequestMapping("/dq/stnd/delSditmrqstlist.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> delStndItemRqstList(@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);

    	ArrayList<WdqqSditm> list = data.get("data");

    	int result = WdqStndItemRqstService.delStndItemRqstList(reqmst, list);
    	result += WdqStndItemRqstService.check(reqmst);

    	String resmsg;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("MSG.DEL", null, locale);
    	}
    	else {
    		result = -1;
    		resmsg = message.getMessage("ERR.DEL", null, locale);
    	}

    	String action = WiseMetaConfig.IBSAction.DEL.getAction();

    	//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
    	reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

    }

    @RequestMapping("/dq/stnd/approveStndItem.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> approveStndItem (@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {

    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
    	String resmsg;

    	LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//결재자인지 확인한다.
//		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
//		if(!checkapprove) {
//			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
//			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
//		}
		
    	List<WdqqSditm> list = data.get("data");

    	int result = WdqStndItemRqstService.approve(reqmst, list);


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


//    @RequestMapping("/dq/stnd/regitemdivision.do")
//    @ResponseBody
//    public IBSResultVO<WaqMstr> regItemDivision(@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {
//    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);
//
//    	List<WdqqSditm> list = data.get("data");
//
//    	int result = WdqStndItemRqstService.regitemdivision(reqmst, list);
//
//    	result += WdqStndItemRqstService.check(reqmst);
//
//		String resmsg;
//
//		if(result > 0 ){
//			result = 0;
//			resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
//		}
//
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//    }
//    
//    
//    
//    /** 표준항목 자동분할 조회...  */
//    @RequestMapping("/dq/stnd/getItemDvRqstList.do")
//    @ResponseBody
//    public IBSheetListVO<WdqpDvCanAsm> getItemDvRqstList(WapDvCanDic data, Locale locale) {
//    	logger.debug("division:{}", data);
//    	
//    	List<WdqpDvCanAsm> list = WdqStndItemRqstService.getItemDvRqstList(data);
//    	
//    	return new IBSheetListVO<WdqpDvCanAsm>(list, list.size());
//    	
//    }
//    
//    @RequestMapping("/dq/stnd/regItemAutoDiv.do")
//    @ResponseBody
//    public IBSResultVO<WdqpDvCanAsm> regItemAutoDiv(@RequestBody WdqpDvCanAsms data, Locale locale,WapDvCanDic data2) throws Exception {
//    	logger.debug("division:{}", data);
//    	List<WdqpDvCanAsm> list = data.get("data");
//   	
//    	//항목분할요청 ID
//    	Map<String, String> resultMap = WdqStndItemRqstService.regItemAutoDiv(list,data2);
//    	int result = Integer.parseInt(resultMap.get("result"));
//    	String resmsg;
//    	
//    	if(result > 0 ){
//    		result = 0;
//    		resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
//    	} else {
//    		result = -1;
//    		resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
//    	}
//    	String action = WiseMetaConfig.IBSAction.REG.getAction();
//    	
//    	return new IBSResultVO<WdqpDvCanAsm>(resultMap, result, resmsg, action);
//    }
//    
//    @RequestMapping("/dq/stnd/delItemAutoDiv.do")
//    @ResponseBody
//    public IBSResultVO<WdqpDvCanAsm> delItemAutoDiv(@RequestBody WdqpDvCanAsms data, Locale locale) throws Exception {
//    	logger.debug("division:{}", data);
//    	List<WdqpDvCanAsm> list = data.get("data");
//    	
//    	//항목분할요청 ID
//    	Map<String, String> resultMap = WdqStndItemRqstService.delItemAutoDiv(list);
//    	int result = Integer.parseInt(resultMap.get("result"));
//    	String resmsg;
//    	
//    	if(result > 0 ){
//    		result = 0;
//    		resmsg = message.getMessage("MSG.DEL", null, locale);
//    	} else {
//    		result = -1;
//    		resmsg = message.getMessage("ERR.DEL", null, locale);
//    	}
//    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
//    	
//    	return new IBSResultVO<WdqpDvCanAsm>(resultMap, result, resmsg, action);
//    }
//    
//    /** 표준단어 요청 저장 - IBSheet JSON @return insomnia
//	 * @throws Exception */
//	@RequestMapping("/dq/stnd/regStndItemByDiv.do")
//	@ResponseBody
//	public IBSResultVO<WaqMstr> regStndItemByDiv(@RequestBody WdqpDvCanAsms data, WaqMstr reqmst, Locale locale) throws Exception {
//
//		ArrayList<WdqpDvCanAsm> list = data.get("data");
//		logger.debug("data:{}",  data);
//
//		reqmst.setBizDcd("DIC");
//		reqmst.setBizDtlCd("SDITM");
//		reqmst.setRqstStepCd("N");
//		//요청번호가 없을 경우 요청번호를 먼저 채번한다.
//		reqmst = requestMstService.getBizInfoInit(reqmst);
//		
//		//REQ 저장
//		int result = WdqStndItemRqstService.regStndItemByDiv(reqmst, list);
//		WdqStndItemRqstService.check(reqmst);
//
//		String resmsg ;
//
//		if(result > 0) {
//    		result = 0;
//    		resmsg = message.getMessage("MSG.SAVE", null, locale);
//    	}
//    	else {
//    		result = -1;
//    		resmsg = message.getMessage("ERR.SAVE", null, locale);
//    	}
//		logger.debug("reqmst:{}",  reqmst);
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
//
//	}
//    @RequestMapping("/dq/stnd/regWam2WaqStndItem2.do")
//    @ResponseBody
//    public String regWam2WaqList2(@RequestBody WdqqSditms data, WaqMstr reqmst, Locale locale) throws Exception {
//    	logger.debug("reqmst:{}\ndata:{}", reqmst, data);
//    	List<WdqqSditm> list = data.get("data");
//
//    	int result = WdqStndItemRqstService.regWam2Waq(reqmst, list);
//    	logger.debug("체크1");
//    	result += WdqStndItemRqstService.check(reqmst);
//        logger.debug("체크2");
//		String resmsg;
//
//		if(result > 0 ){
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
//
//		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);
//
//		return "/dq/stnd/stndtot_rqst";
//
//    }
//    
//	
//    /** 미사용 표준항목 요청 리스트 조회.... @return AJH */
//    @RequestMapping("/dq/stnd/getUnuseSditm.do")
//    @ResponseBody
//    public IBSheetListVO<WdqqSditm> getUnuseSditmList (WdqqSditm data) {
//    	logger.debug("data:{}", data);
//
//    	List<WdqqSditm> list = WdqStndItemRqstService.getUnuseStndItemRqstList(data);
//
//    	return new IBSheetListVO<WdqqSditm>(list, list.size());
//    }
}
