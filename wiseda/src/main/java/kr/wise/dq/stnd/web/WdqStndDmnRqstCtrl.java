/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDmnRqstCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
 * 3. Comment : 표준도메인 요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 15. 오후 2:18:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 15. :            : 신규 개발.
 */
package kr.wise.dq.stnd.web;
 
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
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
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
import kr.wise.dq.stnd.service.WdqStndDmnRqstService;
import kr.wise.dq.stnd.service.WdqpDvCanAsm;
import kr.wise.dq.stnd.service.WdqpDvCanDic;
import kr.wise.dq.stnd.service.WdqqCdVal;
import kr.wise.dq.stnd.service.WdqqDmn;
import kr.wise.dq.stnd.web.WdqStndItemRqstCtrl.WdqpDvCanAsms;

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

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDmnRqstCtrl.java
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 15. 오후 2:18:13
 * 
 * 190117 url요청 시 마다 불필요하게 코드맵이 호출되어 속도저하를 일으킴.(modelattribute가 붙은 메소드는 모든 요청시에 실행되기 때문에 쿼리가 불필요하게 많이 조회됨)
 *       view만 관리하는 컨트롤러와 나머지 request&response를 관리하는 컨트롤러로 분리
 *       StndDmnRqstViewCtrl.java , StndDmnRqstCtrl.java
 *       
 * </PRE>
 */
@Controller
public class WdqStndDmnRqstCtrl {

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

	private Map<String, Object> codeMap;
	
	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WdqStndDmnRqstService WdqStndDmnRqstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private InfotpService infotpService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;


	static class WdqqDmns extends HashMap<String, ArrayList<WdqqDmn>> {}

	static class WdqqCdVals extends HashMap<String, ArrayList<WdqqCdVal>> {}


	/** 도메인 분리버튼 클릭시 단어구성의 정보를 가져온다... @return insomnia */
	@RequestMapping("/dq/stnd/getdmndivjson.do")
	@ResponseBody
	public Map<String, String> getDmnStwdInfo(@ModelAttribute WdqqDmn data, Locale locale) {


		return WdqStndDmnRqstService.getDmnStwdInfo(data);
	}

	/** 도메인 자동분할 기능 @return insomnia */
	@RequestMapping("/dq/stnd/getdmndivisionlist.do")
	@ResponseBody
	public IBSheetListVO<WdqpDvCanAsm> getDomainDivisionList(WdqpDvCanDic data, Locale locale)  throws Exception {
		logger.debug("search:{}", data);

		List<WdqpDvCanAsm> list = WdqStndDmnRqstService.getDmnDivisionlist(data);

		return new IBSheetListVO<WdqpDvCanAsm>(list, list.size());

	}
	
	/** 도메인 자동분할 조회...  */
    @RequestMapping("/dq/stnd/getDmnDvRqstList.do")
    @ResponseBody
    public IBSheetListVO<WdqpDvCanAsm> getDmnDvRqstList(WdqpDvCanDic data, Locale locale) {
    	logger.debug("division:{}", data);
    	
    	List<WdqpDvCanAsm> list = WdqStndDmnRqstService.getDmnDvRqstList(data);
    	
    	return new IBSheetListVO<WdqpDvCanAsm>(list, list.size());
    	
    }
    
	@RequestMapping("/dq/stnd/regDmnAutoDiv.do")
    @ResponseBody
    public IBSResultVO<WdqpDvCanAsm> regDmnAutoDiv(@RequestBody WdqpDvCanAsms data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);
    	List<WdqpDvCanAsm> list = data.get("data");
    	
    	//항목분할요청 ID
    	Map<String, String> resultMap = WdqStndDmnRqstService.regDmnAutoDiv(list);
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
    	
    	return new IBSResultVO<WdqpDvCanAsm>(resultMap, result, resmsg, action);
    }


	@RequestMapping("/dq/stnd/delDmnAutoDiv.do")
    @ResponseBody
    public IBSResultVO<WdqpDvCanAsm> delDmnAutoDiv(@RequestBody WdqpDvCanAsms data, Locale locale) throws Exception {
    	logger.debug("division:{}", data);
    	List<WdqpDvCanAsm> list = data.get("data");
    	
    	//항목분할요청 ID
    	Map<String, String> resultMap = WdqStndDmnRqstService.delDmnAutoDiv(list);
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
    	
    	return new IBSResultVO<WdqpDvCanAsm>(resultMap, result, resmsg, action);
    }
	
	
	
	 /** 표준단어 요청 저장 - IBSheet JSON @return insomnia
		 * @throws Exception */
		@RequestMapping("/dq/stnd/regStndDmnByDiv.do")
		@ResponseBody
		public IBSResultVO<WaqMstr> regStndItemByDiv(@RequestBody WdqpDvCanAsms data, WaqMstr reqmst, Locale locale) throws Exception {

			ArrayList<WdqpDvCanAsm> list = data.get("data");
			logger.debug("data:{}",  data);

			reqmst.setBizDcd("DIC");
			reqmst.setBizDtlCd("DMN");
			reqmst.setRqstStepCd("N");
			//요청번호가 없을 경우 요청번호를 먼저 채번한다.
			reqmst = requestMstService.getBizInfoInit(reqmst);
			
			//REQ 저장
			int result = WdqStndDmnRqstService.regStndDmnByDiv(reqmst, list);
			WdqStndDmnRqstService.check(reqmst);

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
	
	@RequestMapping("/dq/stnd/getdmninfojson.do")
	@ResponseBody
	public WdqqDmn getDmnInfo(WdqqDmn data, Locale locale) {
		WdqqDmn rtnData = WdqStndDmnRqstService.getDmnInfoByDmnLnm(data);
		if(rtnData == null){
			return data;
		}else{
			return rtnData;
		}
	}


	/** 도메인 요청 리스트 등록.... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/regdmnrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStnddmnRqstList(@RequestBody WdqqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WdqqDmn> list = data.get("data");

		int result = WdqStndDmnRqstService.register(reqmst, list);

		result += WdqStndDmnRqstService.check(reqmst);

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


	/** 도메인 요청리스트 조회.... @return insomnia */
	@RequestMapping("/dq/stnd/getdmnrqstlist.do")
	@ResponseBody
	public IBSheetListVO<WdqqDmn> getStndDmnRqstList(WaqMstr search) {

		logger.debug("search:{}", search);

		List<WdqqDmn> list = WdqStndDmnRqstService.getDmnRqstList(search);


		return new IBSheetListVO<WdqqDmn>(list, list.size());
	}

	/** 도메인 요청리스트 삭제.... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/deldmnrqstlistold.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delStndDmnRqstListOld(@RequestBody WdqqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("delete domain list-reqmst:{}\ndata:{}", reqmst, data);

		ArrayList<WdqqDmn> list = data.get("data");

		int result = WdqStndDmnRqstService.delList(reqmst, list);

		result += WdqStndDmnRqstService.check(reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}

	/** 표준도메인 승인..... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/approveStndDmn.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> approveStndDmn(@RequestBody WdqqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

//		//결재자인지 확인한다.
//		Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
//		if(!checkapprove) {
//			resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
//			return new IBSResultVO<WaqMstr>(-1, resmsg, null);
//		}

		ArrayList<WdqqDmn> list = data.get("data");

		int result  = WdqStndDmnRqstService.approve(reqmst, list);


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

	/** WAM에 있는 내용을 여러개 선택해서 waq에 처리하는 방법 @throws Exception insomnia */
	@RequestMapping("/dq/stnd/regWam2WaqStndDmn.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2Waqlist(@RequestBody WdqqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WdqqDmn> list = data.get("data");

		int result = WdqStndDmnRqstService.regWam2Waq(reqmst, list);

		result += WdqStndDmnRqstService.check(reqmst);

//		int result = stndWordRqstServie.regStndWordRqstlist(list);
		String resmsg;

		if(result > 0) {
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

	/** 도메인 요청내용 삭제... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/deldmnrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delStndDmnRqstList(@RequestBody WdqqDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);

		ArrayList<WdqqDmn> list = data.get("data");

		int result = WdqStndDmnRqstService.delStndDmnRqst(reqmst, list	);
		result += WdqStndDmnRqstService.check(reqmst);

		String resmsg ;

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
	
	/** 코드도메인 리스트 조회 */
	@RequestMapping("/dq/stnd/getDomainCdVal.do")
	@ResponseBody
	public IBSheetListVO<WdqqDmn> getDomainCdVal(@ModelAttribute WdqqDmn data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<WdqqDmn> list = WdqStndDmnRqstService.getDomainCdVal(data);

		return new IBSheetListVO<WdqqDmn>(list, list.size());

	}
	
	/** 코드 요청리스트 조회... @return insomnia */
	@RequestMapping("/dq/stnd/getcdvalrqstlist.do")
	@ResponseBody
	public IBSheetListVO<WdqqCdVal> getCdvalRqstList(WaqMstr search) {
		logger.debug("search:{}",search);

		List<WdqqCdVal> list = WdqStndDmnRqstService.getCdvalRqstList(search);

		return new IBSheetListVO<WdqqCdVal>(list, list.size());
	}

	/** 코드 리스트 등록.... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/regcdvalrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regCdvalRqstList(@RequestBody WdqqCdVals data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WdqqCdVal> list = data.get("data");

		int result = WdqStndDmnRqstService.regCdvalList(reqmst, list); 
		//TODO 체크로직 처리해야한다....
		result += WdqStndDmnRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
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

	/** 코드 리스트 요청서 삭제... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/delcdvalrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delCdvalRqstList(@RequestBody WdqqCdVals data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata{}", reqmst, data);

		List<WdqqCdVal> list = data.get("data");

		int result = WdqStndDmnRqstService.delCdvalList(reqmst, list);
		result += WdqStndDmnRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

   }
	/** 모든코드 리스트 요청서 삭제... @throws Exception insomnia */
	@RequestMapping("/dq/stnd/delallcdvalrqstlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delAllCdvalRqstList(@RequestBody WdqqCdVals data, WaqMstr reqmst, Locale locale) throws Exception {
		
		logger.debug("reqmst:{}\ndata{}", reqmst, data);
		
//		List<WdqqCdVal> list = data.get("data");
		
		int result = WdqStndDmnRqstService.delAllCdvalList(reqmst);
		result += WdqStndDmnRqstService.check(reqmst);
		
		String resmsg;
		
		if(result > 0) {
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
	
}





