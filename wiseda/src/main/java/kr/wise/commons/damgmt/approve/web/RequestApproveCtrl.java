/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestApproveCtrl.java
 * 2. Package : kr.wise.commons.damgmt.approve.web
 * 3. Comment : 요청별 결재자 관리 (관리자 및 결재자만 사용하는 메뉴)
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 28. 오전 11:01:53
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 28. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.WiseMetaConfig.RqstAction;
import kr.wise.commons.cmm.service.MessengerService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.approve.service.WaaAprPrc;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestApproveCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.approve.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 28. 오전 11:01:53
 * </PRE>
 */
@Controller
public class RequestApproveCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private MessageSource message;
	
	@Inject
	private MessengerService mService;

	static class WaaRqstAprps extends HashMap<String, ArrayList<WaaRqstAprp>> { }

	static class WaaAprPrcs extends HashMap<String, ArrayList<WaaAprPrc>> { }


	/** 요청서별 결재자 관리 페이지 @return insomnia */
    @RequestMapping("/commons/damgmt/approve/requestapprove_lst.do")
	public String requestapprove(@ModelAttribute("searchVO") WaaRqstAprp searchvo) {
		return "/commons/damgmt/approve/requestapprove_lst";
	}

    /** 등록요청 팝업 호출 - 요청마스터 정보를 넘긴다. @return insomnia */
    @RequestMapping("/commons/damgmt/approve/popup/approveSubmit_pop.do")
    public String requestapprovesubmit(@ModelAttribute("reqmst") WaqMstr reqmst, ModelMap model) {

    	//bizInfo 정보를 가져와서 요청 마스터에 셋팅한다...AOP로 처리 할까????
    	WaaBizInfo bizinfo = requestMstService.getBizInfo(reqmst);
//    	reqmst.setBizInfo(bizinfo);

    	//결재라인별 결재자 리스트를 가져온다....
    	ArrayList<WaaRqstAprp> list = requestApproveService.getapproveuserbyline(reqmst);


    	List<ComboIBSVo> approvecodelist =  codeListService.getApproveListIBS(list);

    	model.addAttribute("aprvcodelist", UtilJson.convertJsonString(approvecodelist));
    	model.addAttribute("bizinfo", bizinfo);

    	return "/commons/damgmt/approve/popup/approveSubmit_pop";
    }


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);
		List<CodeListVo> approvegroup = codeListService.getCodeList(CodeListAction.approvegroup);
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
		//시스템 구분
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea)));
		codeMap.put("sysDcd", sysarea);
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd",  cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("aprFrmlCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("APR_FRML_CD")));
		codeMap.put("aprFrmlCd",  cmcdCodeService.getCodeList("APR_FRML_CD"));
		//결재그룹 코드 리스트
		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(approvegroup)));
		codeMap.put("aprgId", approvegroup);
		//전결대결구분코드
		codeMap.put("abdDaprDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ABD_DAPR_DCD")));
		//등록유형코드
		codeMap.put("regTypCdibs", regTypCd);

		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		return codeMap;
	}

    /** 요청서별 결재자 리스트 조회 @return insomnia */
    @RequestMapping("/commons/damgmt/approve/getrequestapprovelist.do")
	@ResponseBody
	public IBSheetListVO<WaaRqstAprp> getapprovelinelist(@ModelAttribute WaaRqstAprp search) {
		logger.debug("searchVO:{}", search);

		List<WaaRqstAprp> list = requestApproveService.getRequestApproveList(search);

		return new IBSheetListVO<WaaRqstAprp>(list, list.size());
	}

	/** 요청서별 결재자 리스트 등록 - IBSheet JSON @return insomnia */
	@RequestMapping("/commons/damgmt/approve/regrequestapprovelist.do")
	@ResponseBody
	public IBSResultVO<WaaRqstAprp> regaprvlinelise(@RequestBody WaaRqstAprps data, Locale locale) {
	logger.debug("dkdkdkdkdk{}", data);
		logger.debug("{}", data);
		ArrayList<WaaRqstAprp> list = data.get("data");

		int result = requestApproveService.regRequestApproveList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaRqstAprp>(result, resmsg, action);
	}

    /** 등록요청을 위한 업무구분별 결재라인을 가져온다. @return insomnia */
    @RequestMapping("/commons/damgmt/approve/getapproveline.do")
	@ResponseBody
	public IBSheetListVO<WaaAprPrc> getapproveline(@ModelAttribute WaqMstr search) {
		logger.debug("reqmst:{}", search);

		List<WaaAprPrc> list = requestApproveService.getapproveline(search);

		return new IBSheetListVO<WaaAprPrc>(list, list.size());
	}

    /** 결재진행에 결재자를 저장한다. @return insomnia */
    @RequestMapping("/commons/damgmt/approve/regapproveprocess.do")
    @ResponseBody
    public IBSResultVO<WaqMstr> regapproveprocess(@RequestBody WaaAprPrcs data,  WaqMstr reqmst, Locale locale) throws Exception {
    	logger.debug("reqmst:{} \ndata:{}", reqmst, data);

    	//requestApproveService.setWaqObjectID(reqmst);

    	ArrayList<WaaAprPrc> list = data.get("data");

    	int result = requestApproveService.submit(reqmst, list);
    	//logger.debug("리퀘스트상태" + reqmst.getRqstStepCd());
    	//result = requestMstService.updateRequestMsterNm(reqmst);
    	String resmsg;

    	if(result > 0) {
    		result = 0;
    		resmsg = message.getMessage("REQ.SUBMIT", null, locale);
    	} else {
    		result = -1;
    		resmsg = message.getMessage("REQ.SUBMIT.ERR", null, locale);
    	}

    	String action = RqstAction.SUBMIT.getAction();

    	//TODO 결재 진행 등록 후 알림연동(메일 및 메신저) 필요...
    	//TODO 해당 요청서에서 등록가능이 아닌 경우 삭제 처리?? 아니면 요청서에서 보여주지 않기??? 일단 보여주지 말자.....
    	
//    	logger.debug("요청명 >>> " + reqmst.getRqstNm());
//    	logger.debug("업무구분코드 >>> " + reqmst.getBizDcd());
//    	logger.debug("업무상세구분코드 >>> " + reqmst.getBizDtlCd());
//    	logger.debug("reqmst toString >>> " + reqmst.toString());
    	if(result == 0) {
    		for(WaaAprPrc wap : list) {
    			mService.sendMsg(reqmst.getRqstUserId(), wap.getUserId(), reqmst.getRqstNm() + " 등록 요청이 있습니다.", "", "kindName");
    		}
    	}

    	return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
    }

	/** @param reqmst insomnia */
	public void setWaqObjectID(WaqMstr reqmst) {


	}


}
