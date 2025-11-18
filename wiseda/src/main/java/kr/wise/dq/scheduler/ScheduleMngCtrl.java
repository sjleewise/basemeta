package kr.wise.dq.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleMngCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WamShdVO extends HashMap<String, ArrayList<WamShd>> { }

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
	private MessageSource message;

	//스케줄 관리 뷰 페이지 이동
	@RequestMapping("/dq/scheduler/schedule_lst.do")
	public String formpage() {
		return "/dq/schedule/schedule_lst";
	}

	//스케줄로그 조회 뷰 페이지 이동
	@RequestMapping("/dq/scheduler/schedulelog_lst.do")
	public String shdlogformpage() {
		return "/dq/schedule/schedulelog_lst";
	}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON IBSheet용
		String schdTypeCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SCHD_TYPE_CD"));
		String schdKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SHD_KND_CD"));
		String etcJobKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ETC_JOB_KND_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("schdTypeCdibs", schdTypeCd);
		codeMap.put("schdKndCdibs", schdKndCd);
		codeMap.put("etcJobKndCdibs", etcJobKndCd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("anaStsCdibs", anaStsCd);

		//스케줄로그 조회
		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));
		String shdtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("SCHD_TYPE_CD"));

		codeMap.put("regtypcd", regtypcd);
		codeMap.put("shdtypcd", shdtypcd);

		//공통코드 - selectbox 용
		codeMap.put("shdKndCd", cmcdCodeService.getCodeList("SHD_KND_CD"));
		codeMap.put("schdHourCd", cmcdCodeService.getCodeList("SCHD_HOUR_CD"));
		codeMap.put("shdTypeCd", cmcdCodeService.getCodeList("SCHD_TYPE_CD"));
		codeMap.put("shdWeekCd", cmcdCodeService.getCodeList("SCHD_WEEK_CD"));
		codeMap.put("shdMonthCd", cmcdCodeService.getCodeList("SCHD_MONTH_CD"));
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("etcJobKndCd", cmcdCodeService.getCodeList("ETC_JOB_KND_CD"));
		codeMap.put("anaStsCd", cmcdCodeService.getCodeList("JOBSTSCD"));

		return codeMap;
	}
}
