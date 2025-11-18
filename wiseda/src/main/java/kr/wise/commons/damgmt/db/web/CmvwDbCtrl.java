package kr.wise.commons.damgmt.db.web;

/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwDbController.java
 * 2. Package : kr.wise.cmvw.db.controller
 * 3. Comment :
 * 4. 작성자  : jwoolee(이정우)
 * 5. 작성일  : 2013. 5. 28.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    jwoolee 	: 2013. 5. 28.	: 신규 개발.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.db.service.WaaDbRole;
import kr.wise.commons.damgmt.db.service.WaaTblSpac;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : CmvwDbController
 * 2. Package  : kr.wise.commons.damgmt.db.controller
 * 3. Comment  :
 * 4. 작성자   : jwoolee(이정우)
 * 5. 작성일   : 2013. 5. 28.
 * </PRE>
 */
@Controller
public class CmvwDbCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaDbConnTrgVOs extends HashMap<String, ArrayList<WaaDbConnTrgVO>> { }

	static class WaaDbSchs extends HashMap<String, ArrayList<WaaDbSch>> { }

	private Map<String, Object> codeMap;

	@Inject
	private CmvwDbService cmvwDbService;

	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 4. 17.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dbmstypvers 		= UtilJson.convertJsonString(codeListService.getCodeList("dbmstypvers"));
       // tblSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("tblSpacPnm"));
        //String idxSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("idxSpacPnm"));
		
		codeMap.put("dbmstypvers", dbmstypvers);
		//codeMap.put("tblSpacPnmibs", tblSpacPnm);
		//codeMap.put("idxSpacPnmibs", idxSpacPnm);

		//공통 코드(요청구분 코드리스트)
		String dbmsverscdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		String dbmstypcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String ddlTrgDcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DDL_TRG_DCD"));
		

		codeMap.put("dbmsverscdibs", dbmsverscdibs);
		codeMap.put("dbmstypcdibs", dbmstypcdibs);
		codeMap.put("regTypCdibs", regTypCdibs);
		codeMap.put("ddlTrgDcdibs", ddlTrgDcdibs);

		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));

		String tblSpacTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_SPAC_TYP_CD"));
		codeMap.put("tblSpacTypCd", cmcdCodeService.getCodeList("TBL_SPAC_TYP_CD"));
		codeMap.put("tblSpacTypCdibs", tblSpacTypCd);
		
		//진단대상(DB_CONN_TRG_ID) 정보
		List<CodeListVo> connTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbms", connTrgDbms);
				
		//2019.5.28
		codeMap.put("infoSysCd", UtilJson.convertJsonString(codeListService.getCodeListIBS("infoSysCd")));
		

		codeMap.put("osversibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OS_VERS_CD")));
		codeMap.put("oskndibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OS_KND_CD")));
		codeMap.put("gthexcptrsnibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("GTH_EXCPT_RSN_CD")));

		
		return codeMap;
	}

	@RequestMapping("/commons/damgmt/db/dbconntrg_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbconntrg_lst";
	}

	@RequestMapping("/commons/damgmt/db/popup/dbschema_pop.do")
	public String godbschemapop(@ModelAttribute("search") WaaDbSch search ) {
        
		return "/commons/damgmt/db/popup/dbschema_pop";
	}

	@RequestMapping("/commons/damgmt/db/getdbschemalist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> getDbSchemaList(WaaDbSch search) {
		List<WaaDbSch> list = cmvwDbService.getDbSchemaList(search);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}
	
	@RequestMapping("/commons/damgmt/db/popup/dbspac_pop.do")
	public String godbspacpop(@ModelAttribute("search") WaaTblSpac search,Model model, @RequestParam(value="row") String row ) {
         	model.addAttribute("row", row);
		return "/commons/damgmt/db/popup/dbspac_pop";
	}
	
	@RequestMapping("/commons/damgmt/db/getdevsubjdbschemalist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> getDevDbSchemaList(WaaDbSch search) {
		List<WaaDbSch> list = cmvwDbService.getDevSubjDbSchemaList(search);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}


	@RequestMapping("/commons/damgmt/db/selectconntrglist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbConnTrgVO> selectConnTrgList(@ModelAttribute WaaDbConnTrgVO search) {
		//LoginVO temp =(LoginVO) session.getAttribute("loginVO");
		
		//search.setComCd(temp.getComCd());
		
		List<WaaDbConnTrgVO> list = cmvwDbService.getDbConnTrgList(search);
		//List<WaaDbConnTrgVO> list2 = (List<WaaDbConnTrgVO>) new WaaDbConnTrgVO();
//        for(int i=0; i< list.size() ; i++){
//           if(WiseConfig.SECURITY_APPLY.equals("Y")){
//              //list.get(i).setDbConnAcPwd(seed.EncryptUtils.getDecData(list.get(i).getDbConnAcPwd(), list.get(i).getDbConnAcId()));
//           }
//          // list2.add(vo);
//        }
        //logger.debug("ddd"+list2.toString());
		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());
	}
	
	@RequestMapping("/commons/damgmt/db/popup/devdbschema_pop.do")
	public String goDevDbSchemaPop(@ModelAttribute("search") WaaDbSch search ) { 

		return "/commons/damgmt/db/popup/devdbschema_pop"; 
	}
	

	@RequestMapping("/commons/damgmt/db/regconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> regConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		int result = cmvwDbService.regDbConnTrgList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -2) { //물리명 중복 에러
			result = -1;
			resmsg = message.getMessage("ERR.DBMS.PNM", null, locale);
		
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);



	}

	@RequestMapping("/commons/damgmt/db/delconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> delConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");

		int result = cmvwDbService.delDbConnTrgList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}else if (result == -2) {
			result = -1;
			//스키마 삭제후 DBMS삭제 가능합니다.
			resmsg = message.getMessage("MSG.DELDBMS", null, locale);		
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	}

	@RequestMapping("/commons/damgmt/db/chkconntrglist.do")
	@ResponseBody
	public IBSResultVO chkConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");

		cmvwDbService.chkDbConnTrgList(list);

		IBSResultVO ibsres = new IBSResultVO();
		ibsres.RESULT.CODE = 0;
		ibsres.RESULT.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return ibsres;
	}

	/** DBMS에 대한 스키마 grid_sub에서 조회 - IBSheet JSON */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/dbconntrg_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> selectTrgSchemaList(@Param("dbConnTrgId") String dbConnTrgId) {
		List<WaaDbSch> list = cmvwDbService.getDbSchList(dbConnTrgId);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}

	/** 스키마 등록 - IBSheet JSON
	 * @throws Exception */
	/** yeonho */
	@RequestMapping("/commons/damgmt/db/regSchList.do")
	@ResponseBody
	public IBSResultVO<WaaDbSch> regSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbSch> list = data.get("data");
		int result = cmvwDbService.regDbSchList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else if (result == -3){
			result = -1;
			resmsg = "존재하지 않는 DBMS접속정보입니다. <br>값을 확인해주세요.";
			
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = "SCH";
		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
	}

	/** 스키마 리스트 삭제 - IBSheet JSON
	 * @throws Exception */
	@RequestMapping("/commons/damgmt/db/delSchList.do")
	@ResponseBody
	public IBSResultVO<WaaDbSch> delSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbSch> list = data.get("data");

		int result = cmvwDbService.delDbSchList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}else if (result == -2) {
			result = -1;
			//삭제할 스키마에 DDL테이블이 존재합니다.
			resmsg = message.getMessage("MSG.DELDBSCH", null, locale);		
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = "SCH";


		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
	}
	
	/** DBMS 접속 테스트 
	 * @param data
	/** @param locale
	/** @return
	/** @throws Exception yeonho
	 */
	@RequestMapping("/commons/damgmt/db/dbConnTrgConnTest.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> DbConnTrgConnTest(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		int result = cmvwDbService.dbConnTrgConnTest(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.CONNTEST", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("MSG.CONNTEST", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.CONNTEST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);



	}
	
	@RequestMapping("/meta/ddl/popup/dbrole_pop.do")
	public String godbrolepop(@ModelAttribute("search") WaaDbRole search ) {
        
		return "/meta/ddl/popup/dbrole_pop";
	}	
	
	@RequestMapping("/commons/damgmt/db/getdbrolelist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbRole> getDbRoleList(WaaDbRole search) {
		List<WaaDbRole> list = cmvwDbService.getDbRoleList(search);

		return new IBSheetListVO<WaaDbRole>(list, list.size());

	}


}
