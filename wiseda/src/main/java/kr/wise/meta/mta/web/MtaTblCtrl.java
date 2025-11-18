/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblCtrl.java
 * 2. Package : kr.wise.meta.mta.web
 * 3. Comment : 메타테이블정의서 조회
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.02.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.02. :            : 신규 개발.
 */
package kr.wise.meta.mta.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.esb.send.service.EsbFilesendVO;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.MtaTblRqstService;
import kr.wise.meta.mta.service.MtaTblService;
import kr.wise.meta.mta.service.WaaBrmDetailOrg;
import kr.wise.meta.mta.service.WamMtaCol;
import kr.wise.meta.mta.service.WamMtaExl;
import kr.wise.meta.mta.service.WamMtaTbl;
import kr.wise.meta.mta.service.WaqMtaTbl;
/*import kr.wise.meta.srcdata.service.SrcDataRqstService;
import kr.wise.meta.srcdata.service.WaqSrcDataRqst;*/

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("MtaTblCtrl")
public class MtaTblCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static class WamMtaTbls extends HashMap<String, ArrayList<WamMtaTbl>> {}
	public static class WamMtaCols extends HashMap<String, ArrayList<WamMtaCol>> {}
	public static class WamMtaExls extends HashMap<String, ArrayList<WamMtaExl>> { }
	public static class WaqMtaTbls extends HashMap<String, ArrayList<WaqMtaTbl>>{}
	
	@Inject
	private MtaTblService mtaTblService;
	
	@Inject
	private MtaTblRqstService mtaTblRqstService;
	
/*	@Inject
	private SrcDataRqstService srcDataRqstService;*/
	
	@Inject
	private ScheduleManagerService scheduleManagerService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	

	private Map<String, Object> codeMap;
	
	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드리스트를 가져온다 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getCodeMap() {
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String ssOrgCd = "";
		
		if(user!=null) {
			ssOrgCd = UtilString.null2Blank(user.getOrgCd());
		}
		
		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		//공통코드 - IBSheet Combo Code용
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCdValue", cmcdCodeService.getCodeList("REG_TYP_CD"));


		List<CodeListVo> abrNm = codelistService.getCodeList("abrTempList");
		codeMap.put("abrNm", abrNm);
		
		
//		String wdDcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD"));
//		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));
//		codeMap.put("wdDcdibs", wdDcdibs);
		
		//기관코드
		codeMap.put("orgCdibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("orgCd")));
		codeMap.put("infoSysCdibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("infoSysCd")));
		
		if("Y".equals(user.getIsAdminYn())){
			codeMap.put("orgCd", codelistService.getCodeList("orgCd"));
			codeMap.put("infoSysCd", codelistService.getCodeList("infoSysCd"));
		}else{
			List<CodeListVo> orgList = codelistService.getAuthOrgCdList(user);  
			codeMap.put("orgCd", orgList);
			
			List<CodeListVo> infoSysList = codelistService.getInfoSysCdEachList(ssOrgCd); 
			codeMap.put("infoSysCd", infoSysList);
		}
		codeMap.put("connTrgSchibs", UtilJson.convertJsonString(codelistService.getCodeListIBS("connTrgSchId")));
		
		//dbms유형,dbms버전
		codeMap.put("dbmsverscdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD")));
		codeMap.put("dbmstypcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD")));
		
		//비공개사유 
		codeMap.put("nopenRsnibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NOPEN_RSN_CD")));
		
		//=============================================
		
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId")); 
		codeMap.put("connTrgSch", connTrgSch);
		
		//GAP상태코드
		codeMap.put("gapStsCd", cmcdCodeService.getCodeList("GAP_STS_CD"));
		codeMap.put("gapStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("GAP_STS_CD")));
		
		//테이블수집구분코드   
		codeMap.put("tblClltDcd", cmcdCodeService.getCodeList("TBL_CLLT_DCD"));
		codeMap.put("tblClltDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CLLT_DCD")));

		//공개사유 
		codeMap.put("openRsnCd", cmcdCodeService.getCodeList("OPEN_RSN_CD"));
		codeMap.put("openRsnCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OPEN_RSN_CD")));

		//비공개사유
		codeMap.put("nopenRsnCd", cmcdCodeService.getCodeList("NOPEN_RSN_CD"));
		
		return codeMap;
	}
	
	/** 테이블정의서 조회 페이지 호출 */
	@RequestMapping("/meta/mta/mtatbl_lst.do")
	public String goMtaTblList(HttpSession session) {
		session.setAttribute("codenm", "test");
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>"+session.getAttribute("codenm"));
		return "/meta/mta/mtatbl_lst";
		
	}
	
	/** 테이블정의서 목록 조회 */
	@RequestMapping("/meta/mta/getMtaTbllist.do")
	@ResponseBody
	public IBSheetListVO<WamMtaTbl> getMtaTblList(@ModelAttribute WamMtaTbl data, Locale locale ) {
		
		logger.debug("reqvo:{}", data);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String orgcd = UtilString.null2Blank(user.getOrgCd());
		if(!"".equals(orgcd) && !"0000000".equals(orgcd)){
			data.setOrgCd(orgcd);
		}
		
		List<WamMtaTbl> list = mtaTblService.getMtaTblList(data);
		
		return new IBSheetListVO<WamMtaTbl>(list, list.size());
	}
	
	
	/** 테이블정의서에 대한 컬럼 목록 조회  */
	@RequestMapping("/meta/mta/ajaxgrid/mtatbl_dtl.do")
	public String selectMtaTblInfoDetail(String mtaTblId, ModelMap model) {
		
		logger.debug("mtaTblId:",mtaTblId);

		if(!UtilObject.isNull(mtaTblId)) {

			WamMtaTbl result = mtaTblService.selectMtaTblInfoDetail(mtaTblId);
			model.addAttribute("result", result);
			
			logger.debug("result: {}", result);
		}
		
		return "/meta/mta/mtatblinfo_dtl";
	}
	
	/** 메타데이터 컬럼 조회 */
	@RequestMapping("/meta/mta/ajaxgrid/mtacol_lst.do")
	@ResponseBody
	public IBSheetListVO<WamMtaCol> getMtaColList(@ModelAttribute("searchVO") WamMtaCol searchVO, String mtaTblId) throws Exception {

		logger.debug("searchVO: {}", searchVO);
		logger.debug("{}", mtaTblId);
		List<WamMtaCol> list = mtaTblService.getMtaColList(searchVO);
		
		return new IBSheetListVO<WamMtaCol>(list, list.size());
	}
	
	
	/*public IBSheetListVO<WamMtaCol> getMtaColList(WamMtaCol wamMtaCol) {
		
		logger.debug("wamMtaCol:{}", wamMtaCol);
		
		List<WamMtaCol> list = null;
		
		return new IBSheetListVO<WamMtaCol>(list, list.size());
	}*/
	
	/** 정보시스템 상세정보 조회  */
	@RequestMapping("/meta/mta/ajaxgrid/infosysinfo_dtl.do")
	public String getInfoSysInfoDetail(String orgCd, String infoSysCd, ModelMap model) {
		
		logger.debug("infoSysCd:{}",infoSysCd);

		if(!UtilObject.isNull(infoSysCd)) {

			WaaInfoSys result = mtaTblService.getInfoSysInfoDetail(orgCd, infoSysCd);
			model.addAttribute("result", result);
			
			logger.debug("result: {}", result);
		}
		
		return "/meta/mta/infosysinfo_dtl";
	}
	
	/** 메타데이터조회 - DB정의서 목록 조회 - IBSheet JSON */
	@RequestMapping("/meta/mta/getDbdefnList.do")
	@ResponseBody
	public IBSheetListVO<WaaDbConnTrgVO> getDbConnTrgList(@ModelAttribute WaaDbConnTrgVO search) {
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		/*String orgcd = UtilString.null2Blank(user.getOrgCd());
		
		if(!"".equals(orgcd) && !"0000000".equals(orgcd)){
			search.setOrgCd(orgcd);
		}*/
		
		String ssOrgcd = UtilString.null2Blank(user.getOrgCd());
		
		//사용자 또는 관리자
		if(!UtilString.isBlank(user.getOrgLvlUseYn())) {
			
			search.setMappingUserId(user.getUniqId());
			
			//선택된 기관코드가 없을 경우 기관코드범위 제한
			if(UtilString.isBlank(search.getOrgCd())) {
				//search.getAdminYn (null : system관리자[전체코드조회], N:업무담당자[단일기관코드조회], Y:실무담당자[본인기관 상하기관조회]) 
				search.setAdminYn(user.getOrgLvlUseYn());
				search.setOrgCd(ssOrgcd);
			}
		}
		
		List<WaaDbConnTrgVO> list = mtaTblService.getDbConnTrgList(search);

		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());
	}
	
	/** 메타데이터조회 - DBMS에 대한 스키마 grid_sub에서 조회 - IBSheet JSON */
	@RequestMapping("/meta/mta/dbconntrg_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> getTrgSchemaList(@Param("dbConnTrgId") String dbConnTrgId) {
		List<WaaDbSch> list = mtaTblService.getDbSchList(dbConnTrgId);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}
	
	/** 메타데이터조회 - DB정의서 상세정보 조회  */
	@RequestMapping("/meta/mta/ajaxgrid/dbdefninfo_dtl.do")
	public String getDbDefnInfoDetail(String dbConnTrgId, ModelMap model) {
		
		logger.debug("dbConnTrgId:{}",dbConnTrgId);

		if(!UtilObject.isNull(dbConnTrgId)) {

			WaaDbConnTrgVO result = mtaTblService.getDbDefnInfoDetail(dbConnTrgId);
			model.addAttribute("result", result);
			
			logger.debug("result: {}", result);
		}
		
		return "/meta/mta/dbdefninfo_dtl";
	}
	
	
	@RequestMapping("/meta/mta/popup/mtacolPop.do")
	public String goMtaColPop(@ModelAttribute WamMtaTbl search, Model model, Locale locale) throws Exception {
		logger.debug("{}", search);
		
		List<WamMtaTbl> list = mtaTblService.getMtaTblList(search);
		if(!list.isEmpty() && list.size() > 0) {
			model.addAttribute("result", list.get(0));
		}
		
		return "/meta/mta/popup/mtacollst_pop";
	}
	
	/** 보유DB변경(DDL) 컬럼정보 조회 */
	@RequestMapping("/meta/mta/popup/mtacolDdlPop.do")
	public String goMtaColDdlPop(@ModelAttribute("search") WamMtaCol search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mta/popup/mtacolddlst_pop";
	}
	
	/** 보유DB변경(DDL) 조회 */
	@RequestMapping("/meta/mta/dbchg_lst.do")
	public String goDbChgPage(@ModelAttribute("search") WamMtaCol search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mta/dbchg_lst";
	}
	
	/** 보유DB변경(DDL) - 변경된 컬럼정보 DDL 저장 */
	/*@RequestMapping("/meta/mta/regDbChgList.do")
	@ResponseBody
	public IBSResultVO<WamMtaTbl> regDbChgList(@RequestBody WamMtaTbls data, @ModelAttribute("param") WamMtaTbl param, Locale locale ) {
		
		logger.debug("regDbChg========="+data);
		ArrayList<WamMtaTbl> list = data.get("data");
		
		int result = mtaTblService.regDbChgList(list, param);
		
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			if(result == -9){
				resmsg = message.getMessage("ERR.QUARTZ", null, locale);
			}else{
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			result = -1;
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WamMtaTbl>(result, resmsg, action);
	}*/
	
	/*@RequestMapping("/meta/mta/regDbChg.do")
	@ResponseBody
	public String regDbChgInfo(@ModelAttribute WamMtaTbl data, Locale locale ) throws Exception {
		
		logger.debug("regDbChg=========data  : "+data);
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		String resmsg = "";
		
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			WaqSrcDataRqst saveVo = new WaqSrcDataRqst();
			String[] conts = data.getRvwConts().split("\\n");
			logger.debug("saveVO 세팅 시작 --------------------------------------------------");
			for(int i=0; i<conts.length; i++){
				saveVo.setRgstType("D");	//요청유형 : DDL
				saveVo.setRgstNo(System.currentTimeMillis()+String.format("%02d",(int)(Math.random()*100)));
				saveVo.setSendOrgCd(data.getOrgCd());
				saveVo.setSendOrgNm(data.getOrgNm());
				saveVo.setRecvOrgCd(data.getOrgCd());
				saveVo.setRecvOrgNm(data.getOrgNm());
				saveVo.setInfoSysCd(data.getInfoSysCd());
				saveVo.setInfoSysNm(data.getInfoSysNm());
				saveVo.setDbConnTrgPnm(data.getDbConnTrgPnm());
				saveVo.setDbSchNm(data.getDbSchPnm());
				saveVo.setTblNm(data.getMtaTblPnm());
				saveVo.setCrgUserId(user.getUniqId());	//요청자 = 담당자
				saveVo.setReqUserNm(user.getName());
				saveVo.setClctSql(conts[i]);
				saveVo.setProcStatus("02");	//배부완료
				saveVo.setShdStrDtm(sdFormat.format(new Date()));
				saveVo.setShdStrHr("00");
				saveVo.setShdStrMnt("00");
				saveVo.setFrsRqstUserId(user.getUniqId());
				saveVo.setFrsRqstDtm(transFormat.parse(FileManagerUtil.getTimeStamp24()));
				saveVo.setRqstUserId(user.getUniqId());
				saveVo.setRqstDtm(transFormat.parse(FileManagerUtil.getTimeStamp24()));
				saveVo.setAprvUserId(user.getUniqId());
				
				result = srcDataRqstService.register(saveVo);	//요청정보 저장
				logger.debug("saveVO 세팅 종료 --------------------------------------------------");
				//스케쥴 등록
				int schedulresult = 0;
				try{
					WamShd value = new WamShd(); //스케줄러 등록
					
					//스케줄 _ HOME full  경로
					String schedulerpath = message.getMessage(message.getMessage("mode", null, locale)+"."+WiseConfig.SCHDULER_PATH, null, locale); 
					//Quartz 등록 shell 경로
					String schedulercmd = message.getMessage(message.getMessage("mode", null, locale)+"."+WiseConfig.SCHDULER_CMD, null, locale);
					String schedulerDataPath = message.getMessage(message.getMessage("mode", null, locale)+"."+WiseConfig.SCHDULER_GETDATA, null, locale);
					logger.debug("Quartz 등록 시작 --------------------------------------------------");
					//Quartz server 구동 확인
					if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)||SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
						value = scheduleManagerService.saveDt(schedulerDataPath, saveVo.getRgstNo(),saveVo.getShdStrDtm(),saveVo.getShdStrHr(),saveVo.getShdStrMnt(), saveVo.getRecvOrgCd(), saveVo.getRgstType());//스케줄러 관련 table에 데이터 insert (rgstNo,날짜(20181010),시간(00),분(00),기관코드,요청유형)
						logger.debug("saveDt 실행 끝 --------------------------------------------------");
						logger.debug("saveVo.getRgstNo() ========================== "+saveVo.getRgstNo());
						logger.debug("saveVo.getShdStrDtm() ========================== "+saveVo.getShdStrDtm());
						logger.debug("saveVo.getShdStrHr() ========================== "+saveVo.getShdStrHr());
						logger.debug("saveVo.getShdStrMnt() ========================== "+saveVo.getShdStrMnt());
						logger.debug("saveVo.getRgstType() ========================== "+saveVo.getRgstNo());   
						SchedulerUtils.registrySchedule(value, schedulercmd);//스케줄러 등록
					}else{
						schedulresult = -1;
						logger.debug("스케줄 등록 오류", saveVo.getRgstNo());
						throw new WiseBizException("ERR.QUARTZ", "스케쥴 등록 오류");
					}
				}catch(Exception e){
					schedulresult = -1;
					e.printStackTrace();
				}
					
				if(schedulresult >= 0){
					saveVo.setProcStatus("03");	//승인
					result = srcDataRqstService.updateApprSrcDataRqst(saveVo);//승인요청 관련 테이블 update
				
					if(result > 0) {
						resmsg = message.getMessage("MSG.SAVE", null, locale);
					} else {
						resmsg = message.getMessage("ERR.SAVE", null, locale);
						result = 0;
					}
				}else{
					resmsg = message.getMessage("ERR.QUARTZ", null, locale);
					result = 0;
				}
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			resmsg = message.getMessage("ERR.SAVE", null, locale);
			e.printStackTrace();
		}
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		return resmsg;
	}*/
	
	/** 테이블정의서 목록 조회 */
	@RequestMapping("/meta/mta/popup/getWatTblList.do")
	@ResponseBody
	public IBSheetListVO<WatDbcTbl> getWatTbllist(@ModelAttribute WatDbcTbl data, Locale locale ) {
		
		logger.debug("reqvo:{}", data); 
		
		int result = 0;
		
		//result = mtaTblRqstService.updateWamMtaGapStsCd(data);
		
		logger.debug("\n result:" + result);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		/*
		if(!"Y".equals(user.getIsAdminYn())){	//시스템관리자가 아닐 경우
			data.setFrsRqstUserId(user.getUniqId()); 
		}
		*/
		
		List<WatDbcTbl> list = mtaTblService.getWatTblList(data);   
		 		
		return new IBSheetListVO<WatDbcTbl>(list, list.size());   
	}
	
	/** 테이블정의서 목록 조회 */
	@RequestMapping("/meta/mta/popup/getMtaExlLst.do")
	@ResponseBody
	public IBSheetListVO<WamMtaExl> getMtaExlLst(@ModelAttribute WamMtaExl data, Locale locale ) {
		
		logger.debug("reqvo:{}", data); 
		
		int result = 0;
		
		logger.debug("\n result:" + result);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		List<WamMtaExl> list = mtaTblService.getMtaExlLst(data);   
		 		
		return new IBSheetListVO<WamMtaExl>(list, list.size());   
	}
	
	/** 테이블정의서 목록 조회 */
	@RequestMapping("/meta/mta/getMtaExlRqst.do")
	@ResponseBody
	public IBSheetListVO<WamMtaExl> getMtaExlRqst(@ModelAttribute WamMtaExl data, Locale locale ) {
		
		logger.debug("reqvo:{}", data); 
		
		int result = 0;
		
		logger.debug("\n result:" + result);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		List<WamMtaExl> list = mtaTblService.getMtaExlRqst(data);   
		 		
		return new IBSheetListVO<WamMtaExl>(list, list.size());   
	}
	
	/**  BRM 조회 */
	@RequestMapping("/meta/mta/popup/brmOrgSearchPop.do")
	public String goBrmSearchPage(@ModelAttribute("search") WaaBrmDetailOrg search, Model model, Locale locale) {
		logger.debug("{}", search);

		return "/meta/mta/popup/brmOrgSearchPop";  
	}
	
	/** 테이블정의서 조회 페이지 호출 */
	@RequestMapping("/meta/mta/wat_mta_lst.do")
	public String goWatMtaTblList(@ModelAttribute WaqMtaTbl data, Model model, Locale locale) {
		
		
		model.addAttribute("search", data);
		return "/meta/mta/wat_mta_lst";
		
	}
	
	@RequestMapping("/meta/mta/ajax/updGapStsCd.do")
	@ResponseBody 
	public int updGapStsCd(@ModelAttribute WatDbcTbl data, Model model, Locale locale) {
		
		
		int result = mtaTblRqstService.updateWamMtaGapStsCd(data);
		
		return result;		
	}
	
	@RequestMapping("/meta/mta/mta_exl_lst.do")
	public String goMtaExlLst(@ModelAttribute WaqMtaTbl data, Model model, Locale locale) {
		
		
		model.addAttribute("search", data);
		return "/meta/mta/mta_exl_lst";
		
	}
	
	@RequestMapping("/meta/mta/mta_exl_rqst.do")
	public String goMtaExlRqst(@ModelAttribute WaqMtaTbl data, Model model, Locale locale) {
		
		
		model.addAttribute("search", data);
		return "/meta/mta/mta_exl_rqst";
		
	}
	
	@RequestMapping("/meta/mta/regMtaExlRqst.do")
	@ResponseBody
	public IBSResultVO<WamMtaExl> regList(@RequestBody WamMtaExls data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WamMtaExl> list = data.get("data");

		int result = mtaTblService.regList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WamMtaExl>(result, resmsg, action);
	}
	
	  @RequestMapping({"/meta/mta/autoMtaTbl.do"})
	  @ResponseBody
	  public Map<String, String> autoMtaTbl(@RequestBody WaqMtaTbls data, WaqMstr reqmst, Locale locale) throws Exception {
	    logger.debug("== autoMtaTbl ==");
	    LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
	    String userid = user.getUniqId();
	    try
	    {
	      ArrayList<WaqMtaTbl> mList = data.get("data");
	      for (WaqMtaTbl tempList : mList)
	      {
	        EsbFilesendVO fileVO = new EsbFilesendVO();

	        fileVO.setFileGb("M");
//뭔지 모르겠음
//	        String sysCd = message.getMessage("sysCd", null, Locale.getDefault());
String sysCd = "";
	        fileVO.setSrcSysCd(sysCd);
	        fileVO.setSrcOrgCd(UtilString.null2Blank(user.getTopOrgCd()));
	        fileVO.setMtaTblId(tempList.getMtaTblId());
	        fileVO.setDbSchId(tempList.getDbSchId());
	        fileVO.setInfoSysCd(tempList.getInfoSysCd());
	        fileVO.setMtaRqstNo(UtilString.null2Blank(tempList.getRqstNo()));

	        boolean sendYn = mtaTblRqstService.sendEsbAuto(fileVO);

	        if (sendYn) {
	          fileVO.setSendStsCd(reqmst.getSendStsCd());
	          mtaTblRqstService.preAutoEnd(fileVO);
	          logger.debug("== ESB FILE SEND End... ==");
	        } else {
	          mtaTblRqstService.rollBackRegTbl(fileVO);
	          logger.debug("== ESB FILE SEND Error... ==");

	          Map result = new HashMap();
	          result.put("chk", "fail");
	        }
	      }

	    }
	    catch (Exception e){
	      Map result = new HashMap();
	      result.put("chk", "fail");
	    }

	    Map result = new HashMap();
	    result.put("chk", "success");

	    return result;
	  }
	
	
	
	
	
	
	
	
}
