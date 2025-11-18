package kr.wise.dq.codemng.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.dataset.service.WadDataSetMapper;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.codemng.service.CodeMngService;
import kr.wise.dq.codemng.service.WaaCdRule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeMngCtrl.java
 * 3. Package  : kr.wise.dq.codemng.web
 * 4. Comment  : DQLITE 코드관리
 * 5. 작성자   : meta
 * 6. 작성일   : 2019. 6. 18. 오후 4:28:30
 * </PRE>
 */
@Controller
public class CodeMngCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaCdRules extends HashMap<String, ArrayList<WaaCdRule>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private CodeMngService codeMngService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private WadDataSetMapper targetDbms;
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);
		List<CodeListVo> dbSch = codelistService.getCodeList(CodeListAction.dbSch);

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		codeMap.put("dbSch", dbSch);

		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//						codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		//검증join코드
		codeMap.put("tgtVrfJoinCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TGT_VRF_JOIN_CD")));
		codeMap.put("tgtVrfJoinCd", cmcdCodeService.getCodeList("TGT_VRF_JOIN_CD"));
		
		//코드유형코드 
		codeMap.put("cdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_TYP_CD")));
		codeMap.put("cdTypCd", cmcdCodeService.getCodeList("CD_TYP_CD"));
		
		//스키마
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("devConnTrgSch", connTrgSch);
		
		return codeMap;
	}
	
	
	
	
	
	@RequestMapping("/dq/codemng/code_mng_lst.do")
	public String codeformpage() throws Exception{
		return "/dq/codemng/code_mng_lst";
	}
	
	
  /** 코드 관리  
    * 
    * 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
    * 
    * @throws Exception 
    **/
    @RequestMapping("/dq/codemng/regCode.do")
    @ResponseBody
    public IBSResultVO<WaaCdRule> regCode(WaaCdRule saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = 0;
    	
    	result = sqlVrfc(saveVO.getDbConnTrgId(), saveVO.getCdSql());
		String resultMsg = "";
		if(result <= 0) {
			result = -1;
			resultMsg = "SQL구문을 확인해주세요.";
			
			String action = "";

	    	return new IBSResultVO<WaaCdRule>(saveVO, result, resultMsg, action);
		}
    	
		result = codeMngService.regCode(saveVO, saction);

		if(result > 0) {
			result = 0;
			resultMsg = "저장 되었습니다.";
		} else {
			result = -1;
			resultMsg = "저장을 실패했습니다.";
		}
    	
    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaCdRule>(saveVO, result, resultMsg, action);
    }
	
    
    /** 코드 요청 리스트 조회 - IBSheet JSON @return meta */
    @RequestMapping("/dq/codemng/getcodelist.do")
	@ResponseBody
	public IBSheetListVO<WaaCdRule> getCodelist(@ModelAttribute WaaCdRule search) {
		logger.debug("searchVO:{}", search);

		List<WaaCdRule> list = codeMngService.getCodeMngList(search);

		return new IBSheetListVO<WaaCdRule>(list, list.size());
	}
    
    
    /** 코드 삭제 
	 * @throws Exception */
	@RequestMapping("/dq/codemng/delcodeList.do")
	@ResponseBody
	public IBSResultVO<WaaCdRules> delCodeList(@RequestBody WaaCdRules delVOs, @ModelAttribute WaaCdRule mstr, Locale locale) throws Exception {
		
		logger.debug("data : {}\nsearch : {}", delVOs);
		Map<String, String> resultMap = codeMngService.delCodeList(delVOs.get("data"), mstr);
		int result = Integer.parseInt(resultMap.get("result"));
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		String resultMsg = null;
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
    	
		
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WaaCdRules>(resultMap, result, resultMsg, action);
    }
	
	
	/** 코드관리 엑셀업로드 팝업 호출 */
    /** meta */
	@RequestMapping("/dq/codemng/popup/code_xls.do")
    public String goBizruleXls(@ModelAttribute("search") WamBrMstr search) {
		logger.debug("{}", search);
    	return "/dq/codemng/popup/code_xls";
    }
	
	/** 코드 관리  
    * 
    * 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
    * 
    * @throws Exception 
    **/
    @RequestMapping("/dq/codemng/checkSQL.do")
    @ResponseBody
    public IBSResultVO<WaaCdRule> checkSQL(WaaCdRule saveVO, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}", saveVO);
    	int result = 0;
    	
		result = sqlVrfc(saveVO.getDbConnTrgId(), saveVO.getCdSql());
		String resultMsg = "";
		if(result > 0) {
			result = 0;
			resultMsg = "SQL 검증이 완료되었습니다.";
		} else {
			result = -1;
			resultMsg = "SQL구문을 확인해주세요.";
		}
    	
    	String action = ""; //WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WaaCdRule>(saveVO, result, resultMsg, action);
    }
	
	//쿼리 검증 메소드
	private int sqlVrfc(String trgId, String sql) {
		int result = 0;
		
		Connection con = null;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadDataSet dataSet = new WadDataSet();
		dataSet.setDbConnTrgId(trgId);
		WaaDbConnTrgVO targetDbmsDM = targetDbms.selectTgtDbmsbyTrgId(dataSet);
		
		if(sql.toUpperCase().contains("WHERE")) {
			sql = sql.concat(" AND 1=2");
		} else {
			sql = sql.concat(" WHERE 1=2");
		}
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getConnTrgDrvrNm(), targetDbmsDM.getConnTrgLnkUrl(), targetDbmsDM.getDbConnAcId(), targetDbmsDM.getDbConnAcPwd());
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs != null) {
				result = 1;
			} else {
				result = 0;
			}
		} catch(Exception e) {
			logger.debug(e.toString());
			return 0;
		}
		
		return result;
	}
	
}
