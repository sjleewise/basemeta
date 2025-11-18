package kr.wise.commons.api.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.api.service.ApiService;
import kr.wise.commons.api.service.WamDdlColApi;
import kr.wise.commons.api.service.WamDdlTblApi;
import kr.wise.commons.api.service.WamDmnApi;
import kr.wise.commons.api.service.WamPdmColApi;
import kr.wise.commons.api.service.WamPdmTblApi;
import kr.wise.commons.api.service.WamSditmApi;
import kr.wise.commons.api.service.WamStwdApi;
import kr.wise.commons.api.service.WatDbcColApi;
import kr.wise.commons.api.service.WatDbcTblApi;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListApiVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilApi;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.commons.api.service.vStdTermApi;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.schedule.service.ScheduleLogService;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;

@Controller("apiCtrl")
class apiCtrl{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;	
	
	@Inject
	private ApiService apiService;
	
	@Inject
	private ScheduleLogService scheduleLogService;
	
	@Inject 
	private CmvwDbService cmvwDbService;
	
	static class WaaDbConnTrgVOs extends HashMap<String, ArrayList<WaaDbConnTrgVO>> { }

	static class WaaDbSchs extends HashMap<String, ArrayList<WaaDbSch>> { }
	
	@Inject 
	UtilApi UtilApi;
	
	@RequestMapping("/response/tableApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamPdmTblApi> tableApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ltable",  required=false) String ltable
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="subjectArea",  required=false) String subjectArea 
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn
			,@RequestParam(value="pers_info_grd",  required=false) String pers_info_grd) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("pdmTblLnm", ltable);
		param.put("pdmTblPnm", ptable);
		param.put("subjLnm", subjectArea);
		param.put("persInfoGrd", pers_info_grd);
		param.put("persInfoCnvYn", pers_info_cnv_yn);
		
		int count = apiService.getPdmTblCount(param);
		
		List<WamPdmTblApi> list = apiService.getPdmTblList(param);
		
		int code = 0;
		String message = "테이블";
		return new IBSheetListApiVO<WamPdmTblApi>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/columnApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamPdmColApi> columnApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="subjectArea",  required=false) String subjectArea
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn
			,@RequestParam(value="pers_info_grd",  required=false) String pers_info_grd) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("pdmTblPnm", ptable);
		param.put("subjLnm", subjectArea);
		param.put("persInfoGrd", pers_info_grd);
		param.put("persInfoCnvYn", pers_info_cnv_yn); 
		
		int count = apiService.getPdmColCount(param);
		
		List<WamPdmColApi> list = apiService.getPdmColList(param);
		
		int code = 0;
		String message = "컬럼";
		
		return new IBSheetListApiVO<WamPdmColApi>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/wordApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamStwdApi> wordApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="pword",  required=false) String pword
			,@RequestParam(value="lword",  required=false) String lword
			,@RequestParam(value="dictype",  required=false) String dictype) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("stwdLnm", lword);
		param.put("stwdPnm", pword);
		param.put("subjLnm", dictype);
		
		int count = apiService.getStndWordCount(param);
		
		List<WamStwdApi> list = apiService.getStndWordList(param);
		
		int code = 0;
		String message = "표준단어";
		
		return new IBSheetListApiVO<WamStwdApi>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/domainApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamDmnApi> domainApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ldomain",  required=false) String ldomain
			,@RequestParam(value="dictype",  required=false) String dictype) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("dmnLnm", ldomain);
		param.put("subjLnm", dictype);
		
		int count = apiService.getDomainCount(param);
		
		List<WamDmnApi> list = apiService.getDomainList(param);
		
		int code = 0;
		String message = "도메인";
		
		return new IBSheetListApiVO<WamDmnApi>(list, code, message, count);
		
	}
	
	@RequestMapping("/response/commCodeApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamCdVal> CommonCodeApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ldomain",  required=false) String ldomain
			,@RequestParam(value="dictype",  required=false) String dictype
			,@RequestParam(value="cdVal",  required=false) String cdVal
			,@RequestParam(value="cdValNm",  required=false) String cdValNm
			) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("dmnLnm", ldomain);
		param.put("cdVal", cdVal);
		param.put("subjLnm", dictype);
		
		int count = apiService.getCdValCount(param);
		
		List<WamCdVal> list = apiService.getCdValList(param);
		
		int code = 0;
		String message = "코드";
		
		return new IBSheetListApiVO<WamCdVal>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/termApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamSditmApi> termApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="lterm",  required=false) String lterm
			,@RequestParam(value="pterm",  required=false) String pterm
			,@RequestParam(value="dictype",  required=false) String dictype
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn
			,@RequestParam(value="pers_info_grd",  required=false) String pers_info_grd) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("sditmLnm", lterm);
		param.put("sditmPnm", pterm);
		param.put("subjLnm", dictype);
		param.put("persInfoGrd", pers_info_grd);
		param.put("persInfoCnvYn", pers_info_cnv_yn); 
		
		int count = apiService.getStndItemCount(param);
		
		List<WamSditmApi> list = apiService.getStndItemList(param);
		
		int code = 0;
		String message = "표준용어";
		
		return new IBSheetListApiVO<WamSditmApi>(list, code, message, count);
		
	}
	
	@RequestMapping("/response/ddlTableApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamDdlTblApi> ddlTableApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ltable",  required=false) String ltable
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="subjectArea",  required=false) String subjectArea
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("ddlTblLnm", ltable);
		param.put("ddlTblPnm", ptable);
		param.put("subjLnm", subjectArea);
		param.put("persInfoCnvYn", pers_info_cnv_yn);
		
		int count = apiService.getDdlTblCount(param);
		
		List<WamDdlTblApi> list = apiService.getDdlTblList(param);
		
		int code = 0;
		String message = "ddl테이블";
		return new IBSheetListApiVO<WamDdlTblApi>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/ddlColumnApi.do")
	@ResponseBody
	public IBSheetListApiVO<WamDdlColApi> ddlColumnApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="subjectArea",  required=false) String subjectArea
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn
			,@RequestParam(value="pers_info_grd",  required=false) String pers_info_grd) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("ddlTblPnm", ptable);
		param.put("subjLnm", subjectArea);
		param.put("persInfoGrd", pers_info_grd);
		param.put("persInfoCnvYn", pers_info_cnv_yn);
		
		int count = apiService.getDdlColCount(param);
		
		List<WamDdlColApi> list = apiService.getDdlColList(param);
		
		int code = 0;
		String message = "ddl컬럼";
		return new IBSheetListApiVO<WamDdlColApi>(list, code, message, count);
		
	}
	
	
	@RequestMapping("/response/vStdTermApi.do")
	@ResponseBody
	public IBSheetListApiVO<vStdTermApi> vStdTermApi(
			@RequestParam(value="termname",  required=false) String termname
			,@RequestParam(value="termengname",  required=false) String termengname
			,@RequestParam(value="domainname",  required=false) String domainname
			,@RequestParam(value="ldatatype",  required=false) String ldatatype
			,@RequestParam(value="pdatatype",  required=false) String pdatatype
			,@RequestParam(value="comments",  required=false) String comments
			,@RequestParam(value="sysname",  required=false) String sysname
			,@RequestParam(value="pers_info_cnv_yn",  required=false) String pers_info_cnv_yn
			,@RequestParam(value="pers_info_grd",  required=false) String pers_info_grd
			,HttpSession session) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		Long beforeCallTime = (Long)session.getAttribute("CallTime");
		Long CallTime =System.currentTimeMillis(); 
		Long coolTime=(long) (60*1000);
		int code = 0;
		String message = "V_STD_TERM";
		List<vStdTermApi> list = new ArrayList<>();
		
		/*if(beforeCallTime!=null){
			if(CallTime-beforeCallTime<coolTime){
				message = "일분 후에 조회가 가능합니다.";
				return new IBSheetListApiVO<vStdTermApi>(list, code, message);
			}
		}*/
		session.setAttribute("CallTime", System.currentTimeMillis());
		param.put("termname", termname);
		param.put("termengname", termengname);
		param.put("domainname", domainname);
		param.put("ldatatype", ldatatype);
		param.put("pdatatype", pdatatype);
		param.put("comments", comments);
		param.put("sysname", sysname);
		param.put("pers_info_cnv_yn", pers_info_cnv_yn);
		param.put("pers_info_grd", pers_info_grd);
		
		list = apiService.getVStdTermList(param);
		
		
		return new IBSheetListApiVO<vStdTermApi>(list, code, message);
		
	}
	
	
	
	@RequestMapping("/commons/damgmt/apitestview.do")
	String apitestview(Model model){
	    String remoteServer = UtilString.replaceNull(message.getMessage("remoteServer", null, null),"");
		
		model.addAttribute("remoteServer", remoteServer);
		return "/commons/damgmt/apiTestHttpConnect";
	}
	
	
	//스케줄로그 조회 테스트용
	@RequestMapping("/request/getScheduleLogList.do")
	@ResponseBody
	public Object requestgetScheduleLogList(@ModelAttribute WamShdLogVO search) throws JsonParseException, JsonMappingException, IOException {
		  String remoteServer = UtilString.replaceNull(message.getMessage("remoteServer", null, null),"");
//		  String url =remoteServer +"/response/getScheduleLogList.do";
		  String url ="http://localhost:8080/wiseda/remote/getScheduleLogList.do";
		  RestTemplate restTemplate = new RestTemplate();
		  
		    ObjectMapper objectMapper = new ObjectMapper();
		    ObjectNode node = objectMapper.createObjectNode();
			node = UtilApi.ObjectNodeFromObject(search);
			ObjectNode res = restTemplate.postForObject(url,node, ObjectNode.class);
			
			System.out.println(res.toString());
		    
			return res;
//			list = objectMapper.readValue(res.toString(), new TypeReference<ArrayList<WamShdLogVO>>(){});
//		return new IBSheetListVO<WamShdLogVO>(list, list.size());
	}
	//스케줄로그 조회 테스트용
	@RequestMapping("/remote/getScheduleLogList.do")
	@ResponseBody
	public IBSheetListVO<WamShdLogVO> getScheduleLogList(@RequestBody WamShdLogVO search) {
		logger.debug("{}", search);
		List<WamShdLogVO> list = scheduleLogService.getScheduleLogList(search);
		//logger.debug("{}", list);
		//logger.debug("{}", search);

		return new IBSheetListVO<WamShdLogVO>(list, list.size());
	}
	
//	//오브젝트노드로 변환
//    private ObjectNode ObjectNodeFromObject(Object object) throws IOException {
//	        ObjectMapper mapper = new ObjectMapper();
//	        return (ObjectNode) mapper.readTree(mapper.writeValueAsString(object));
//   } 
//    
    
	//dbms접속정보 입력 (전체 리스트를 입력)
    @RequestMapping("/request/regconnTrgSchAllList.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> requestRegConnTrgList( Locale locale) throws Exception {
		int result =0;
		String resmsg = "";
		WaaDbConnTrgVO search = new WaaDbConnTrgVO();
		List<WaaDbConnTrgVO> list = cmvwDbService.getDbConnTrgList(search);
		HashMap<String, List<WaaDbConnTrgVO>> data = new HashMap<>();
		data.put("data",list);
		//저장완료 시 운영tomcat으로 정보 전송			
		try{
			String remoteServer = UtilString.replaceNull(message.getMessage("remoteServer", null, null),"");
			String url =remoteServer +"/remote/regconntrgAlllist.do";			
//			RestTemplate restTemplate = new RestTemplate();
		    ObjectMapper objectMapper = new ObjectMapper();
//		    ObjectNode node = objectMapper.createObjectNode();
//			node = UtilApi.ObjectNodeFromObject(data);
//			ObjectNode res = restTemplate.postForObject(url,node, ObjectNode.class);
			HashMap<String, Object> res = (HashMap<String, Object>) UtilApi.requestUrlHelperPostJson(url,data,message);
//			HashMap<String , Object> apiresult = objectMapper.readValue(res.toString(), new TypeReference<HashMap<String, Object>>(){});
			if(res.get("RESULT")!=null){
				HashMap<String,Object> rm = (HashMap<String, Object>) res.get("RESULT");
				String r = Integer.toString((int) rm.get("CODE"));
				if(!r.equals("0")){
					result = -1;
					resmsg = message.getMessage("ERR.REMOTE.SAVE", null, locale);
				}
			}
			//성공 후 스키마 리스트 입력
			WaaDbSch search2 = new WaaDbSch();
			List<WaaDbSch> list2 = cmvwDbService.getDbSchemaList(search2);
			HashMap<String, List<WaaDbSch>> data2 = new HashMap<>();
			data2.put("data",list2);
			url =remoteServer +"/remote/regSchAllList.do";		
//			node = UtilApi.ObjectNodeFromObject(data2);
//			ObjectNode res2 = restTemplate.postForObject(url,node, ObjectNode.class);
			HashMap<String, Object> res2 = (HashMap<String, Object>) UtilApi.requestUrlHelperPostJson(url,data2,message);
//			HashMap<String , Object> apiresult2 = objectMapper.readValue(res2.toString(), new TypeReference<HashMap<String, Object>>(){});
			if(res2.get("RESULT")!=null){
				HashMap<String,Object> rm2 = (HashMap<String, Object>)res2.get("RESULT");
				String r2 = Integer.toString((int) rm2.get("CODE"));
				if(!r2.equals("0")){
					result = -1;
					resmsg = message.getMessage("ERR.REMOTE.SAVE", null, locale);
				}
			}
			//성공 후 스키마 리스트 입력
		}catch (Exception e) {
			logger.debug(e.getMessage());
	 		result = -1;
     		resmsg = message.getMessage("ERR.REMOTE.SAVE", null, locale);
     		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
    		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
		}
//			//저장완료 시 운영tomcat으로 정보 전송

		if(result == 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	} 
    
  //dbms접속정보 입력 (전체 리스트를 입력)
//    @RequestMapping("/remote/regconntrgAlllist.do")
//	@ResponseBody
//	public IBSResultVO<WaaDbConnTrgVO> responseRegConnTrgAllList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
//		logger.debug("{}", data);
//		ArrayList<WaaDbConnTrgVO> list = data.get("data");
//		int result = cmvwDbService.regDbConnTrgAllList(list);
//		String resmsg;
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
//		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
//	} 
    
	//dbms접속정보 입력 
    @RequestMapping("/remote/regconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> responseRegConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		int result = cmvwDbService.regDbConnTrgList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	} 
    
    @RequestMapping("/remote/delconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> responsedelConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");

		int result = cmvwDbService.delDbConnTrgList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
//		else if (result == -2) {
//			result = -1;
//			//스키마 삭제후 DBMS삭제 가능합니다.
//			resmsg = message.getMessage("MSG.DELDBMS", null, locale);		
//		} 
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	}
    
    
    /** 스키마 등록 - IBSheet JSON
	 * @throws Exception */
	/** yeonho */
//	@RequestMapping("/remote/regSchList.do")
//	@ResponseBody
//	public IBSResultVO<WaaDbSch> responseRegSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {
//		logger.debug("{}", data);
//		ArrayList<WaaDbSch> list = data.get("data");
//		int result = cmvwDbService.regDbSchListRomote(list);
//		String resmsg;
//
//		if(result > 0) {
//			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
//		} else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
//
//		String action = "SCH";
//		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
//	}
	
	  /** 스키마 전체 등록 - IBSheet JSON api용
		 * @throws Exception */
		/** yeonho */
//		@RequestMapping("/remote/regSchAllList.do")
//		@ResponseBody
//		public IBSResultVO<WaaDbSch> responseRegSchAllList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {
//			logger.debug("{}", data);
//			ArrayList<WaaDbSch> list = data.get("data");
//			int result = cmvwDbService.regDbSchAllList(list);
//			String resmsg;
//
//			if(result > 0) {
//				result = 0;
//				resmsg = message.getMessage("MSG.SAVE", null, locale);
//			} else {
//				result = -1;
//				resmsg = message.getMessage("ERR.SAVE", null, locale);
//			}
//
//			String action = "SCH";
//			return new IBSResultVO<WaaDbSch>(result, resmsg, action);
//		}

	/** 스키마 리스트 삭제 - IBSheet JSON
	 * @throws Exception */
	@RequestMapping("/remote/delSchList.do")
	@ResponseBody
	public IBSResultVO<WaaDbSch> responseDelSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbSch> list = data.get("data");

		int result = cmvwDbService.delDbSchList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = "SCH";


		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
	}
	
	@RequestMapping("/response/dbcTableApi.do")
	@ResponseBody
	public IBSheetListApiVO<WatDbcTblApi> dbcTableApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="schNm",  required=false) String schNm
			,@RequestParam(value="dbPnm",  required=false) String dbPnm
			,@RequestParam(value="subjectArea",  required=false) String subjectArea) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("dbcTblNm", ptable);
		param.put("subjLnm", subjectArea);
		param.put("dbSchPnm", schNm);
		param.put("dbConnTrgPnm", dbPnm);
		
		int count = apiService.getDbcTblCount(param);
		
		List<WatDbcTblApi> list = apiService.getDbcTblList(param);

		int code = 0;
		String message = "DBC테이블";
		
		return new IBSheetListApiVO<WatDbcTblApi>(list, code, message, count);
		
	}
	
	@RequestMapping("/response/dbcColumnApi.do")
	@ResponseBody
	public IBSheetListApiVO<WatDbcColApi> dbcColumnApi(
			@RequestParam(value="pageIndex", defaultValue="1") int pageIndex
			,@RequestParam(value="sizeIndex",defaultValue="50") int sizeIndex
			,@RequestParam(value="schNm",  required=false) String schNm
			,@RequestParam(value="dbPnm",  required=false) String dbPnm
			,@RequestParam(value="subjectArea",  required=false) String subjectArea
			,@RequestParam(value="ptable",  required=false) String ptable
			,@RequestParam(value="colNm",  required=false) String colNm) {
		
		if(sizeIndex>1000){
			sizeIndex=1000;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("pageIndex", pageIndex);
		param.put("sizeIndex", sizeIndex);
		param.put("dbcTblNm", ptable);
		param.put("dbcColNm", colNm);
		param.put("subjLnm", subjectArea);
		param.put("dbSchPnm", schNm);
		param.put("dbConnTrgPnm", dbPnm);
		
		int count = apiService.getDbcColCount(param);
		
		List<WatDbcColApi> list = apiService.getDbcColList(param);

		int code = 0;
		String message = "DBC컬럼";
		
		return new IBSheetListApiVO<WatDbcColApi>(list, code, message, count);
		
	}
}
