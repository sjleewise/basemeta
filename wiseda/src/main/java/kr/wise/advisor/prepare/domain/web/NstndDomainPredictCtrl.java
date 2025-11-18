/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DomainPredictCtrl.java
 * 2. Package : kr.wise.advisor.prepare.domain.web
 * 3. Comment : 도메인판별 처리 컨트롤
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:21:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.domain.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstnd;
import kr.wise.advisor.prepare.domain.service.NstndDomainPredictService;
import kr.wise.advisor.prepare.domain.service.WadNstndDmnPdt;
import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : DomainPredictCtrl
 * 2. FileName  : DomainPredictCtrl.java
 * 3. Package  : kr.wise.advisor.prepare.domain.web
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:21:16
 * </PRE>
 */
@Controller
public class NstndDomainPredictCtrl {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;

	/*@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;*/
	
	@Inject
	private NstndDomainPredictService dmnPredictService;
	
	@Inject
	ScheduleManagerService scheduleManagerService;
	
	//사용자정의 유효값
	static class AnaTrgTblVOs extends HashMap<String, ArrayList<AnaTrgTblVO>> { }
	static class WadAnaVarNstnds extends HashMap<String, ArrayList<WadAnaVarNstnd>> { }
	
	/** request로 넘어오는 변수값을 바인딩시 처리로직을 공통으로 적용... */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/** 도메인판별 요청 by 테이블리스트...
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/domain/regnstnddmnpredict.do")
	@ResponseBody
	public IBSResultVO<CommonVo> getPrfColLst(@RequestBody AnaTrgTblVOs data, Locale locale) throws Exception {
		logger.debug("비표준도메인판별요청 by 테이블리스트");
		
		List<AnaTrgTblVO> list = data.get("data");
		
		int result = dmnPredictService.requestNstndDmnPredict(list);
		String rqstNo = list.get(0).getRqstNo();
		result = dmnPredictService.callNstndDmnPredictGrpc(rqstNo);

		String resmsg;

		if(result > 0 ){
			result = 0;
//			resmsg = message.getMessage("MSG.SAVE", null, locale);
			resmsg = "도메인판별을 완료했습니다.";
		}
		else {
			result = -1;
//			resmsg = message.getMessage("ERR.SAVE", null, locale);
			resmsg = "도메인판별 처리중 오류가 발생했습니다.";
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		CommonVo comvo = new CommonVo();
		comvo.setRqstNo(rqstNo);
		logger.debug("비표준도메인판별결과 rqstNo:{}", comvo.getRqstNo());
		
		return new IBSResultVO<CommonVo>(comvo, result, resmsg, action);
	}

	/** 도메인판별 요청 by 테이블리스트...
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/domain/requestNstndDmnClass.do")
	@ResponseBody
	public IBSResultVO<CommonVo> requestNstndDmnClass(@RequestBody AnaTrgTblVOs data, Locale locale) throws Exception {
		logger.debug("비표준도메인판별요청 by 테이블리스트");
		
		List<AnaTrgTblVO> list = data.get("data");
		
		int result = dmnPredictService.requestNstndDmnPredict(list);
		String rqstNo = list.get(0).getRqstNo();
		//result = dmnPredictService.callNstndDmnPredictGrpc(rqstNo);
		

		WamShd saveVO = new WamShd();
		saveVO.setShdKndCd("ND");
		String shdLnm = "[비표준도메인판별] 실시간 분석실행("+UtilDate.getCurrentDateHms()+")";
		saveVO.setShdLnm(shdLnm);
		saveVO.setShdTypCd("O"); //배치형태  한번만
		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //배치시작일자  //현재날짜
		saveVO.setShdStrHr("00");  //시
		saveVO.setShdStrMnt("00");  //분
		saveVO.setShdUseYn("Y"); //스케줄사용여부
		saveVO.setShdBprYn("N"); //일괄처리여부
		saveVO.setRegTypCd("C");
		
		//스케줄관리 필터링 위해
		saveVO.setObjDescn("ONLINE");
		
		ArrayList<WamShdJob> joblist = new ArrayList<WamShdJob>();
		WamShdJob job = new WamShdJob();
		job.setShdJobId(rqstNo);
		job.setShdJobNm("[비표준도메인판별] ("+rqstNo+")");
		joblist.add(job);
		
		//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault());

		String resmsg;
		
		//Quartz server 구동 확인
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
		//if(true) {
			//스케줄 마스터, 스케줄작업 저장
			result = scheduleManagerService.saveSchedule(joblist, saveVO);
			result = 1;
			
			if(result > 0) {
	    		result = 0;
	    		resmsg = message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}
//					Quartz 등록
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}
		
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		CommonVo comvo = new CommonVo();
		comvo.setRqstNo(rqstNo);
		logger.debug("비표준도메인판별결과 rqstNo:{}", comvo.getRqstNo());
		
		return new IBSResultVO<CommonVo>(comvo, result, resmsg, action);
	}

	/** 도메인판별 결과 저장....
	/** @return insomnia 
	 * @throws Exception */
	@RequestMapping("/advisor/prepare/domain/regnstnddmnresult.do")
	@ResponseBody
	public IBSResultVO<CommonVo> regNstndDmnResult(@RequestBody WadAnaVarNstnds data, Locale locale) throws Exception {
		logger.debug("비표준도메인판별결과 저장 by 컬럼리스트");
		
		List<WadAnaVarNstnd> list = data.get("data");
		
		int result = dmnPredictService.regNstndDmnResult(list);
		
		String resmsg;
		
		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
//			resmsg = "도메인판별을 완료했습니다.";
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
//			resmsg = "도메인판별 처리중 오류가 발생했습니다.";
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		return new IBSResultVO<CommonVo>(result, resmsg, action);
	}
	
	@RequestMapping("/advisor/prepare/domain/getnstnddmnpdtresultlist.do")
	@ResponseBody
	public IBSheetListVO<WadNstndDmnPdt> getAnaVarNstndbyDs(WadNstndDmnPdt search, Locale locale) {
		logger.debug("도메인판별 결과조회 by id:{}", search);
		List<WadNstndDmnPdt> list = dmnPredictService.getNstndDmnPredictResultbyId(search);
		return new IBSheetListVO<WadNstndDmnPdt>(list, list.size());
	} 

}
