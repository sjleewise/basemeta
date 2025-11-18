/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ApproveLineCtrl.java
 * 2. Package : kr.wise.commons.damgmt.approve.web
 * 3. Comment : 요청업무별 결재라인 관리
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 27. 오전 10:54:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 27. :            : 신규 개발.
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
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.damgmt.approve.service.WaaAprPrc;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
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
 * 2. FileName  : ApproveLineCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.approve.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 27. 오전 10:54:57
 * </PRE>
 */
@Controller
public class ApproveLineCtrl {

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
	private ApproveLineServie approveLineServie;

	@Inject
	private MessageSource message;

	static class WaaRqstBizAprs extends HashMap<String, ArrayList<WaaRqstBizApr>> { }


	/** 요청서별 결재라인 관리 페이지 @return insomnia */
    @RequestMapping("/commons/damgmt/approve/approveline_lst.do")
	public String approvelineform(@ModelAttribute("searchVO") WaaRqstBizApr searchvo) {
		return "/commons/damgmt/approve/approveline_lst";
	}

    @RequestMapping("/commons/damgmt/approve/popup/approveProcess_pop.do")
    public String goapproveprocess(@ModelAttribute("reqmst") WaqMstr reqmst) {

    	return "/commons/damgmt/approve/popup/approveProcess_pop";
    }


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);
		List<CodeListVo> approvegroup = codeListService.getCodeList(CodeListAction.approvegroup);
//		List<CodeListVo> approvegroup = codeListService.getCodeList(CodeListAction.approvegroup);
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
		//등록유형코드
		codeMap.put("regTypCdibs", regTypCd);
		//전결대결구분코드
		codeMap.put("abdDaprDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ABD_DAPR_DCD")));
		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드


		return codeMap;
	}

    /** 요청서별 결재라인 리스트 조회 - IBShj @return insomnia */
    @RequestMapping("/commons/damgmt/approve/getapprlinelist.do")
	@ResponseBody
	public IBSheetListVO<WaaRqstBizApr> getapprovelinelist(@ModelAttribute WaaRqstBizApr search) {
		logger.debug("searchVO:{}", search);

		List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(search);

		return new IBSheetListVO<WaaRqstBizApr>(list, list.size());
	}

    /** 결재진행 상태 리스트 조회... @return insomnia */
    @RequestMapping("/commons/damgmt/approve/getapproveprocess.do")
    @ResponseBody
    public IBSheetListVO<WaaAprPrc> getapproveprocess(WaqMstr search) {
    	logger.debug("search:{}", search);

    	List<WaaAprPrc> list = approveLineServie.getapproveprocess(search);

    	return new IBSheetListVO<WaaAprPrc>(list, list.size());
    }

    /** 현재 결재라인 결재자인지 확인... @return insomnia */
    @RequestMapping("/commons/damgmt/approve/getapproveYnbyUser.do")
    @ResponseBody
    public String getcheckApprove(WaqMstr reqmst) {

    	Boolean result = approveLineServie.checkapproveuser(reqmst);
    	if(result) {
    		return "Y";
    	} else {
    		return "N";
    	}

    }

	/** 요청서별 결재라인 리스트 저장 - IBSheet JSON @throws Exception insomnia */
	@RequestMapping("/commons/damgmt/approve/regaprvlinelist.do")
	@ResponseBody
	public IBSResultVO<WaaRqstBizApr> regaprvlinelise(@RequestBody WaaRqstBizAprs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaRqstBizApr> list = data.get("data");

		int result = approveLineServie.regaprvlinelise(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaRqstBizApr>(result, resmsg, action);
	}

	/** 요청서별 결재라인 리스트 삭제 - IBSheet JSON @throws Exception insomnia */
	@RequestMapping("/commons/damgmt/approve/delapproveline.do")
	@ResponseBody
	public IBSResultVO<WaaRqstBizApr> delapproveline(@RequestBody WaaRqstBizAprs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaRqstBizApr> list = data.get("data");

		int result = approveLineServie.delaprvlinelist(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaRqstBizApr>(result, resmsg, action);
	}
	
	/** 등록요청취소(팝업) */
	@RequestMapping("/commons/damgmt/approve/requestCancelPop.do")
	@ResponseBody
	public IBSResultVO<WaaRqstBizApr> requestCancelPop(WaqMstr mstVo, ModelMap model, Locale locale) throws Exception {
   	 
       logger.debug("\n rqstNo: " + mstVo.getRqstNo());
       logger.debug("\n bizDcd : " + mstVo.getBizDcd());
       
       String rqstNo = mstVo.getRqstNo();
       int result = -1;
       
       result = approveLineServie.requestCancel(rqstNo);    
       
       String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = "등록요청 취소 되었습니다.";
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.REG", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG.getAction();

		return new IBSResultVO<WaaRqstBizApr>(result, resmsg, action);
	}
	
    
	/** 등록요청취소 */
	 @RequestMapping("/commons/damgmt/approve/requestCancel.do")
		public String requestCancel(MstrAprPrcVO mapvo, ModelMap model) throws Exception {
	    	 
//	        System.out.println("리퀘스트넘버 : " + mapvo.getRqst_no());
//	        System.out.println("요첩구분 : " + mapvo.getBiz_dcd());
	        String bizdcd= mapvo.getBiz_dcd();
	        
	        String sParam = "";
	        
	        sParam = "?rqstNo=" + mapvo.getRqst_no();
	        
	        approveLineServie.requestCancel(mapvo.getRqst_no());   
	        
	        if(bizdcd.equals("CTQ")){
	        	//중요정보항목
	        	return "redirect:";
	        }
	        if(bizdcd.equals("DQI")){
	        	//데이터품질지표
	        	return "redirect:";
	        }
	        if(bizdcd.equals("BZA")){
	        	//업무영역
	        	return "redirect:";
	        }
	        if(bizdcd.equals("DIC")){
	        	//표준사전
	        	return "redirect:/meta/stnd/stndtot_rqst.do" + sParam;
	        }
	        if(bizdcd.equals("PDM")){
	        	//물리모델
		    	return "redirect:/meta/model/pdmtbl_rqst.do" + sParam;
	        }
	        if(bizdcd.equals("R7P")){
	        	//물리모델(R7)
		    	return "redirect:/meta/model/r7_pdmtbl_rqst.do" + sParam;
	        }
	        if(bizdcd.equals("EXP")){
	        	//물리모델(exerd)
		    	return "redirect:/meta/model/exerd_pdmtbl_rqst.do" + sParam;
	        }
	        if(bizdcd.equals("R9P")){  
	        	//물리모델(R9)
		    	return "redirect:/meta/model/r9_pdmtbl_rqst.do" + sParam; 
	        }
	        if(bizdcd.equals("DDL")){
	        	//DDL생성
	        	return "redirect:/meta/ddl/ddltbl_rqst.do"+ sParam;
	        }
	        if(bizdcd.equals("DTT")){
	        	//DDL이관
	        	return "redirect:/meta/ddltsf/ddltsftbl_rqst.do" + sParam;  
	        }
	        if(bizdcd.equals("IMR")){
	        	//개선결과
	        	return "redirect:";
	        }
	        if(bizdcd.equals("PRF")){
	        	//프로 파일
	        	return "redirect:";
	        }
	        if(bizdcd.equals("DRR")){
	        	//프로 파일
	        	return "redirect:";
	        }
	        if(bizdcd.equals("CCS")){
	        	//코드분류체계
	        	return "redirect:";
	        }
	        if(bizdcd.equals("CDT")){
	        	//코드이관
	        	return "redirect:";
	        }
	        if(bizdcd.equals("MST")){
	        	//메시지이관
	        	return "redirect:";
	        }        
	        if(bizdcd.equals("TMP")){
	        	//테이블매핑정의서
	        	return "redirect:/meta/mapping/tblmap_rqst.do";
	        }
	        if(bizdcd.equals("DMP")){
	        	//코드매핑정의서
	        	return "redirect:/meta/mapping/codmap_rqst.do";
	        }
	        if(bizdcd.equals("CMP")){
	        	//컬럼매핑정의서
	        	return "redirect:";
	        }
	        if(bizdcd.equals("IMP")){
	        	//개선계획
	        	return "redirect:";
	        }
	        if(bizdcd.equals("BRA")){
	        	//업무규칙
	        	return "redirect:";
	        }
	        if(bizdcd.equals("DDX")){
	        	//DDL인덱스
	        	return "redirect:/meta/ddl/ddlidx_rqst.do";
	        }
	        if(bizdcd.equals("MSG")){
	        	//메시지
	        	return "redirect:";
	        }
	        if(bizdcd.equals("PDP")){
	        	//PD연계물리모델
	        	return "redirect:/meta/model/pd_pdmtbl_rqst.do";
	        }       
	        if(bizdcd.equals("CDM")){
	        	//코드도메인
	        	return "redirect:/meta/stnd/codedmn_rqst.do";
	        }
	        if(bizdcd.equals("ADC")){
	        	//APP사전
	        	return "redirect:/meta/app/appstndtot_rqst.do";
	        }
	        if(bizdcd.equals("API")){
	        	//전문용어
	        	return "redirect:/meta/app/appstnditem_rqst.do";
	        }
	        if(bizdcd.equals("APD")){
	        	//APP단어
	        	return "redirect:/meta/app/appstndword_rqst.do";
	        }
	        if(bizdcd.equals("SEM")){
	        	//표준오류코드
	        	return "redirect:/meta/stnd/messagestd_rqst.do";
	        }
	        if(bizdcd.equals("CEM")){
	        	//채널오류코드
	        	return "redirect:/meta/stnd/messagechnl_rqst.do";
	        }
	        if(bizdcd.equals("OEM")){
	        	//대외기관오류코드매핑
	        	return "redirect:/meta/stnd/messagemap_rqst.do";
	        }
	        if(bizdcd.equals("MAC")){
	        	//조치코드
	        	return "redirect:/meta/stnd/messagecode_rqst.do";
	        }
	        if(bizdcd.equals("ASP")){
	        	//ASIS물리모델
	        	return "redirect:/meta/model/asispdmtbl_rqst.do";
	        }
	        if(bizdcd.equals("DCV")){
	        	//ASIS유효값
	        	return "redirect:/meta/model/asisdmncdval_rqst.do";
	        }
	        if(bizdcd.equals("SOW")){
	        	//주제영역권한
	        	return "redirect:/meta/subjarea/subjowner_rqst.do";
	        }
	        if(bizdcd.equals("DMG")){
	        	//주제영역권한
	        	return "redirect:/meta/dmngrqst/dmng_rqst.do";
	        }
/*	        
	        CTQ	중요정보항목
	        DQI	데이터품질지표
	        BZA	업무영역
	        DIC	표준사전
	        PDM	물리모델
	        DDL	DDL생성
	        IMR	개선결과
	        PRF	프로파일
	        CCS	코드분류체계
	        CDT	코드이관
	        MST	메시지이관
	        TMP	테이블매핑정의서
	        DMP	코드매핑정의서
	        DTT	DDL이관
	        R7P	연계물리모델
	        EXP	exerd연계물리모델
	        CMP	컬럼매핑정의서
	        IMP	개선계획
	        BRA	업무규칙
	        DDX	DDL인덱스
	        MSG	메시지
	        PDP	PD연계물리모델
	        CDM	코드도메인
	        ADC	APP사전
	        API	전문용어
	        APD	APP단어
	        SEM 표준오류코드
	        CEM 채널오류코드
	        OEM 대외기관오류코드매핑
	        MAC 조치코드
	        ASP ASIS물리모델
	        DCV ASIS유효값
*/	     
	        
	        return "/error.do";
		}
}
