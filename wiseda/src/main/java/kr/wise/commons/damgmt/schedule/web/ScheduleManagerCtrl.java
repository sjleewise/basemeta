/**
 * 0. Project  : WISE DA �봽濡쒖젥�듃
 *
 * 1. FileName : ScheduleManagerCtrl.java
 * 2. Package : kr.wise.commons.damgmt.schedule.web
 * 3. Comment :
 * 4. �옉�꽦�옄  : hwang
 * 5. �옉�꽦�씪  : 2014. 4. 11. �삤�썑 3:14:50
 * 6. 蹂�寃쎌씠�젰 :
 *                    �씠由�     : �씪�옄          : 洹쇨굅�옄猷�   : 蹂�寃쎈궡�슜
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 11. :            : �떊洹� 媛쒕컻.
 */
package kr.wise.commons.damgmt.schedule.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.domain.service.DomainPredictService;
import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
 * 2. FileName  : ScheduleManagerCtrl.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.web
 * 4. Comment  :
 * 5. �옉�꽦�옄   : hwang
 * 6. �옉�꽦�씪   : 2014. 4. 11. �삤�썑 3:14:50
 * </PRE>
 */
@Controller
public class ScheduleManagerCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WamShdJobVO extends HashMap<String, ArrayList<WamShdJob>> { }
	
	static class WamShds extends HashMap<String, ArrayList<WamShd>> {}

	private Map<String, Object> codeMap;

	@Inject
	ScheduleManagerService scheduleManagerService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;
	
	@Inject
	private DomainPredictService dmnPredictService;

	@Inject
	private MessageSource message;
	
	
//	/** @param binder request 蹂��닔瑜� 留ㅽ븨�떆 鍮덇컪�쓣 NULL濡� 泥섎━ insomnia */
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//	}

	//�뒪耳�以� 愿�由� 酉� �럹�씠吏� �씠�룞
	@RequestMapping("/commons/damgmt/schedule/schedule_lst.do")
	public String formpage() {

		return "/commons/damgmt/schedule/schedule_lst";
	}

	//�뒪耳�以� 愿�由� 議고쉶
	@RequestMapping("/commons/damgmt/schedule/getScheduleList.do")
	@ResponseBody
	public IBSheetListVO<WamShd> selectList(@ModelAttribute WamShd search) {
		logger.debug("{}", search);
		List<WamShd> list = scheduleManagerService.getScheduleList(search);

		return new IBSheetListVO<WamShd>(list, list.size());
	}

	//�뒪耳�以� 愿�由� �긽�꽭議고쉶
	@RequestMapping("/commons/damgmt/schedule/ajaxgrid/schedule_dtl.do")
	public String detailList(WamShd search, ModelMap model, String saction) {

		model.addAttribute("saction", "I");

		if(StringUtils.hasLength(search.getShdId())) {
			WamShd result = scheduleManagerService.getScheduleDtlList(search);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/schedule/schedule_dtl";
	}

	//�뒪耳�以� 愿�由� 諛곗튂�젙蹂� �긽�꽭議고쉶
	@RequestMapping("/commons/damgmt/schedule/ajaxgrid/schedule_batch_dtl.do")
	public String selecDtltList(WamShd search, ModelMap model, String saction) {

		model.addAttribute("saction", "I");

		if(StringUtils.hasLength(search.getShdId())) {

			WamShd result = scheduleManagerService.getScheduleDtlList(search);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/commons/damgmt/schedule/schedule_batch_dtl";
	}



	//�뒪耳�以� 愿�由� �옉�뾽�젙蹂� �솕硫�
	@RequestMapping("/commons/damgmt/schedule/schedule_job_lst.do")
	public String formDtlpage(String shdId) {
		return "/commons/damgmt/schedule/schedule_job_lst";
	}

	//�옉�뾽�젙蹂� �긽�꽭議고쉶
	@RequestMapping("/commons/damgmt/schedule/getScheduleJobList.do")
	@ResponseBody
	public IBSheetListVO<WamShd> selectjobList(@ModelAttribute WamShd search) {
		logger.debug("{}", search);

		List<WamShd> list = null;
		list = scheduleManagerService.getScheduleJobList(search);

		return new IBSheetListVO<WamShd>(list, list.size());
	}

	//�옉�뾽�젙蹂� 寃��깋 �뙘�뾽
	@RequestMapping("/commons/damgmt/schedule/selectJobPopList.do")
	public String selectJobPopList(String shdKndCd, String shdKndNm, String shdId, ModelMap model) {

		model.addAttribute("shdKndCd", shdKndCd);
		model.addAttribute("shdKndNm", shdKndNm);
		model.addAttribute("shdId", shdId);

		return "/commons/damgmt/schedule/popup/schedule_job_pop";
	}

	//�옉�뾽�젙蹂� �뙘�뾽 議고쉶
	@RequestMapping("/commons/damgmt/schedule/getJobPopList.do")
	@ResponseBody
	public IBSheetListVO<WamShdJob> selectJobPopList(@ModelAttribute WamShdJob search) {
		List<WamShdJob> list = scheduleManagerService.getJobPopList(search);

		return new IBSheetListVO<WamShdJob>(list, list.size());
	}

	//�옉�뾽�젙蹂�(�씪諛섎같移�) 寃��깋 �뙘�뾽
	@RequestMapping("/commons/damgmt/schedule/selectJobGnPop.do")
	public String selectJobGnPop(String shdKndCd, String shdKndNm, String shdId, ModelMap model) {

		try {
			shdKndNm = new String(shdKndNm.getBytes("8859_1"), "UTF-8");	//�븳湲�源⑥쭚 諛⑹�
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("shdKndCd", shdKndCd);
		model.addAttribute("shdKndNm", shdKndNm);
		model.addAttribute("shdId", shdId);

		return "/commons/damgmt/schedule/popup/schedule_jobGn_pop";
	}


	// 도메인 판별 스케줄 저장
	@RequestMapping("/commons/damgmt/schedule/ajaxgrid/insertSchedulebydc.do")
	@ResponseBody
	public IBSResultVO<WamShd> saveDomainSchedule(@RequestBody WamShdJobVO data, WamShd saveVO, Locale locale) throws Exception {
//    	logger.debug("data:{}", data);
		ArrayList<WamShdJob> list = data.get("data");
		int result = 0;
		String resmsg ;
		
		logger.debug("saveVO:{}", saveVO);
		
		List<AnaTrgTblVO> tbllist = new ArrayList<AnaTrgTblVO>();
		for (WamShdJob jobvo : list) {
			String jobid = jobvo.getShdJobId();
			String[] tblarr = jobid.split("[.]");
			
			AnaTrgTblVO tblvo = new AnaTrgTblVO();
			tblvo.setDbSchId(tblarr[0]);
			tblvo.setDbcTblNm(tblarr[1]);
			
			tbllist.add(tblvo);
		}
		
		result = dmnPredictService.requestDmnPredict(tbllist);
		String rqstNo = tbllist.get(0).getRqstNo();
		
		
		ArrayList<WamShdJob> joblist = new ArrayList<WamShdJob>();
		WamShdJob job = new WamShdJob();
		job.setShdJobId(rqstNo);
		job.setShdJobNm("[도메인판별] ("+rqstNo+")");
		joblist.add(job);
		
		
		//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
		
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		//Quartz server 구동 확인
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//스케줄 마스터, 스케줄작업 저장
			result = scheduleManagerService.saveSchedule(joblist, saveVO);
			
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.QUARTZ", null, locale);
			}
			else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
//			Quartz 등록
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		
		return new IBSResultVO<WamShd>(saveVO, result, resmsg, action);
	}
	
    // 스케줄 전체 저장
    @RequestMapping("/commons/damgmt/schedule/ajaxgrid/insertSchedule.do")
    @ResponseBody
    public IBSResultVO<WamShd> saveSchedule(@RequestBody WamShdJobVO data, WamShd saveVO, Locale locale) throws Exception {
    	logger.debug("data:{}", data);
    	ArrayList<WamShdJob> list = data.get("data");
    	int result = 0;
    	String resmsg ;
    	
    	if(UtilString.null2Blank(saveVO.getShdLnm()).equals("") ){
    		String shdLnm = "";
    		//프로파일
    		if(saveVO.getShdKndCd().equals("QP")){
    			shdLnm = "[프로파일] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
    		}
    		//검증룰
    		else if(saveVO.getShdKndCd().equals("CR")){
    			shdLnm = "[검증룰] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
    		}
    		//업무규칙
    		else if(saveVO.getShdKndCd().equals("QB")){
    			shdLnm = "[업무규칙] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
    		}
    		//파이썬 호출
    		else if("SM".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[Summary] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
    			
    			AnaTrgTblVO vo = new AnaTrgTblVO();
    			
    			for(int i=0; i<list.size(); i++) {
	    			vo.setDbSchId(list.get(i).getDbSchId());
	    			vo.setDbcTblNm(list.get(i).getDbcTblNm());
	    			
	    			logger.debug("\n DbSchId: " + 	vo.getDbSchId());
	    			logger.debug("\n DbcTblNm: " + 	vo.getDbcTblNm());
	    			
	    			ArrayList<AnaTrgTblVO> volist = new ArrayList<AnaTrgTblVO>();
	    			volist.add(vo);
	    			
	    			String sRtn = dmnPredictService.requestSumDaseVar(volist);   
	    			
	    			//daseId를 shdJobId 로 세팅한다. 
	    			list.get(i).setShdJobId(sRtn);
    			}
    			
    		}else if("HS".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[히스토그램] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
    		}else if("PY".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[이상값프로파일] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")"; 
    		}else if("TM".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[텍스트매칭] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")"; 
    		}else if("TC".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[텍스트클러스트링] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")"; 
    			
    		}else if("UO".equals(saveVO.getShdKndCd())) {
    			shdLnm = "[사용자정의이상값] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")"; 	
    		}
    		
    		saveVO.setShdLnm(shdLnm);
    		saveVO.setShdTypCd("O"); 
    		
    		String dbmsTypCd = message.getMessage("wiseda."+WiseConfig.DB_KIND, null, Locale.getDefault()); 
    		
    		if(!UtilString.isBlank(dbmsTypCd)){
    			if(dbmsTypCd.equals("POS")){
        			saveVO.setShdStrDtm(UtilDate.getCurrentDateHms());
        		} else {
        			saveVO.setShdStrDtm(UtilDate.getCurrentDate());
        		}
    		}
    		
    		saveVO.setShdStrHr("00");  //�떆
    		saveVO.setShdStrMnt("00");  //遺�
    		saveVO.setShdUseYn("Y"); //�뒪耳�以꾩궗�슜�뿬遺�
    		saveVO.setShdBprYn("N"); //�씪愿꾩쿂由ъ뿬遺�
    		saveVO.setRegTypCd("C");
    		
    		//�뒪耳�以꾧�由� �븘�꽣留� �쐞�빐
    		saveVO.setObjDescn("ONLINE");
    	}
    	
		saveVO.setShdStrDtm(saveVO.getShdStrDtm().replaceAll("-", ""));
		
    	//�뒪耳�以� _ HOME full  寃쎈줈
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz �벑濡� shell 寃쎈줈
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		//Quartz server 援щ룞 �솗�씤
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			
			result = scheduleManagerService.saveSchedule(list, saveVO);
			
			if(result > 0) {
	    		result = 0;
	    		resmsg = message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}

			//Quartz �벑濡�
			SchedulerUtils.registrySchedule(saveVO, schedulercmd); 
			
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamShd>(saveVO, result, resmsg, action);
    }

    // �뒪耳�以� �젙蹂� �궘�젣
	@RequestMapping("/commons/damgmt/schedule/delSchedule.do")
	@ResponseBody
	public IBSResultVO<WamShd> delScheduleList(@RequestBody WamShdJobVO data, Locale locale) throws Exception {

		List<WamShdJob> list = data.get("data");
		logger.debug("delVO:{}", list);
		
		//�뒪耳�以� _ HOME full  寃쎈줈
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz �벑濡� shell 寃쎈줈
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
		
		
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		String resmsg;
		int result= 0;
		//Quartz server 援щ룞 �솗�씤
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//rep db �벑濡�
			result  = scheduleManagerService.delSchedule(list);
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			
			//Quartz �궘�젣
			for(WamShd saveVO :  list){
				SchedulerUtils.registrySchedule(saveVO, schedulercmd);
			}
			
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}


		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WamShd>(result, resmsg, action);
		
	}
	
	//�뒪耳�以� �옉�뾽 �궘�젣
	@RequestMapping("/commons/damgmt/schedule/delScheduleJob.do")
	@ResponseBody
	public IBSResultVO<WamShd> delScheduleJobList(@RequestBody WamShdJobVO data, Locale locale, String shdId) throws Exception {

		List<WamShdJob> list = data.get("data");
		logger.debug("delVO:{}", list);

		int result  = scheduleManagerService.delScheduleJob(list, shdId);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL_DTL.getAction();
		return new IBSResultVO<WamShd>(result, resmsg, action);

	}
	
	@RequestMapping("/commons/damgmt/schedule/schStart.do")
	@ResponseBody
	public IBSResultVO<WamShd> schStart() throws Exception {
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()) + "/scheduler/bin";
		
		int result  = scheduleManagerService.schStart(schedulerpath);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = "스케줄러가 성공적으로 기동하였습니다."; //message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = "스케줄러 기동이 실패하였습니다."; //message.getMessage("ERR.DEL", null, locale);
		}

		String action = "스케줄러 기동";
		return new IBSResultVO<WamShd>(result, resmsg, action);

	}
	
	@RequestMapping("/commons/damgmt/schedule/schStop.do")
	@ResponseBody
	public IBSResultVO<WamShd> schStop() throws Exception {
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()) + "/scheduler/bin";
		
		int result  = scheduleManagerService.schStop(schedulerpath);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = "스케줄러가 성공적으로 중지되었습니다."; //message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = "스케줄러 중지가 실패하였습니다."; //message.getMessage("ERR.DEL", null, locale);
		}

		String action = "스케줄러 중지";
		return new IBSResultVO<WamShd>(result, resmsg, action);

	}
	
	
    // 스케줄 즉시 실행 or 재실행
	// 스케줄관리의 즉시 실행은 shdId를 새로 채번하고 스케줄로그의 재실행은 shdId 그대로..
	// 스케줄 즉시 실행은 건마다 새로운 shdId가 채번되기 때문에 즉시실행된 스케줄 간에는 실행 체크 불가능. 
    @RequestMapping("/commons/damgmt/schedule/ajaxgrid/insertScheduleImdExec.do")
    @ResponseBody
    public IBSResultVO<WamShd> saveScheduleImdExec(@RequestBody WamShds data, String scrnDcd, Locale locale) throws Exception {
    	logger.debug("즉시실행 data:{}, 화면구분 scrnDcd:{}", data, scrnDcd);
		ArrayList<WamShd> dataList = data.get("data");
		int result = 0;
		String resmsg = null ;
		
		//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
		
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		// 즉시 실행 or 재실행 하려는 스케줄 중 실행 중인 스케줄러가 있다면 취소됨.	
		String runSchIds = "";
		runSchIds = SchedulerUtils.getRunningSchedule(schedulerpath, dataList);
		
		if(runSchIds.length() > 0) {
			result = -1;
	    	resmsg = "실행 중인 스케줄러가 존재하여 분석실행에 실패했습니다.\n"
	    		   + runSchIds;
	    		  
		}else{//선택된 스케줄러 중 실행 중인 스케줄러가 없으면 스케줄 즉시 실행 or 재실행
			if(dataList != null) {
				for (WamShd saveVO : dataList) {
					ArrayList<WamShdJob> list = (ArrayList<WamShdJob>)scheduleManagerService.getImdScheduleJobList(saveVO);	
					
					//스케줄관리의 즉시 실행은 shdId를 새로 채번하고 스케줄로그의 재실행은 shdId그대로
					if (scrnDcd.equals("SHDMNG")){
						saveVO.setShdId(null);
					}else {
						saveVO.setShdId(saveVO.getShdId());
					}
//    			String shdLnm = saveVO.getShdLnm();
//    			saveVO.setShdLnm(shdLnm);
					saveVO.setShdTypCd("O"); // 스케줄종류코드(한번만)
					saveVO.setShdStrDtm(UtilDate.getCurrentDate()); // 스케줄 시작일시
					saveVO.setShdStrHr("00");   // 스케줄시작시간
					saveVO.setShdStrMnt("00");  // 스케줄시작분
					saveVO.setShdUseYn("Y"); // 스케줄 사용여부
					saveVO.setShdBprYn("N"); // 스케줄일괄처리여부
					saveVO.setRegTypCd("C");
					saveVO.setObjDescn("ONLINE");
					
					logger.debug("saveVO:{}", saveVO);
					
					//Quartz server 구동확인
					if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
						
						//스케줄 마스터, 스케줄작업 저장
						result = scheduleManagerService.saveSchedule(list, saveVO);
						
						//Quartz 등록
						SchedulerUtils.registrySchedule(saveVO, schedulercmd); 
						
					}else{
						result = -1;
						resmsg = message.getMessage("ERR.QUARTZ", null, locale);
					}
				}
			}
		}
    	
    	if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.QUARTZ", null, locale);
		}
//		else {
//			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
//		}
    	
    	String action = "스케줄러 즉시 실행";
    	
    	return new IBSResultVO<WamShd>(result, resmsg, action);
    }
	


	/** 怨듯넻肄붾뱶 諛� 紐⑸줉�꽦 肄붾뱶由ъ뒪�듃瑜� 媛��졇�삩�떎. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//肄붾뱶由ъ뒪�듃 JSON IBSheet�슜
		String schdTypeCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SCHD_TYPE_CD"));
		String schdKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SHD_KND_CD"));
		String etcJobKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ETC_JOB_KND_CD"));
//		String prfKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD"));

		//怨듯넻肄붾뱶 - IBSheet Combo Code�슜
		codeMap.put("schdTypeCdibs", schdTypeCd);
		codeMap.put("schdKndCdibs", schdKndCd);
		codeMap.put("etcJobKndCdibs", etcJobKndCd);
//		codeMap.put("prfKndCdibs", prfKndCd);

		//怨듯넻肄붾뱶 - selectbox �슜
		codeMap.put("shdKndCd", cmcdCodeService.getCodeList("SHD_KND_CD"));
		codeMap.put("schdMinCd", cmcdCodeService.getCodeList("SCHD_MIN_CD"));
		codeMap.put("schdHourCd", cmcdCodeService.getCodeList("SCHD_HOUR_CD"));
		codeMap.put("shdTypeCd", cmcdCodeService.getCodeList("SCHD_TYPE_CD"));
		codeMap.put("shdWeekCd", cmcdCodeService.getCodeList("SCHD_WEEK_CD"));
		codeMap.put("shdMonthCd", cmcdCodeService.getCodeList("SCHD_MONTH_CD"));
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("etcJobKndCd", cmcdCodeService.getCodeList("ETC_JOB_KND_CD"));


		return codeMap;
	}
	
	
	
	  // 스케줄 스탑 
    @RequestMapping("/commons/damgmt/schedule/ajaxgrid/stopSchedule.do")
    @ResponseBody
    public IBSResultVO<WamShd> stopSchedule(@RequestBody WamShds saveVO, Locale locale) throws Exception {
     	int result = 0;
    	String resmsg ;
    	List<WamShd> list = saveVO.get("data");
    	
    	logger.debug("saveVO:{}", saveVO);
    	
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		
		if(SchedulerUtils.stopSchedule(schedulerpath, list)) {
	    	result = 0;
	    	resmsg = message.getMessage("MSG.QUARTZ.JOB.STOP", null, locale);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ.JOB.STOP", null, locale);
		}
    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamShd>(result, resmsg, action);
    }
}
