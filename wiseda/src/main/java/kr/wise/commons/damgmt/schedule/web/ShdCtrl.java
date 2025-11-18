package kr.wise.commons.damgmt.schedule.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.schedule.service.ScheduleLogService;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ShdCtrl.java
 * 2. Package : kr.wise.dq.criinfo.schedule.web
 * 3. Comment : 스케줄 Controllor
 * 4. 작성자  : kwise(최결)
 * 5. 작성일  : 2014. 4. 23. 오후 5:22:31
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi(최결) : 2014. 4. 23. :            : 신규 개발.
 * </PRE>
 */
@Controller
public class ShdCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WamShdLogVOs extends HashMap<String, ArrayList<WamShdLogVO>> { }

	private Map<String, Object> codeMap;

	@Inject
	private ScheduleLogService scheduleLogService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON IBSheet용
		String schdTypeCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SCHD_TYPE_CD"));
		String schdKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SHD_KND_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));
//
//		//공통코드 - IBSheet Combo Code용
		codeMap.put("schdTypeCdibs", schdTypeCd);
		codeMap.put("schdKndCdibs", schdKndCd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("anaStsCdibs", anaStsCd);
//
		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));
//		String shdkndcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("SHD_KND_CD"));
		String shdtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("SCHD_TYPE_CD"));
//
		codeMap.put("regtypcd", regtypcd);
//		codeMap.put("shdkndcd", shdkndcd);
		codeMap.put("shdtypcd", shdtypcd);

		//공통코드 - selectbox 용
		codeMap.put("shdKndCd", cmcdCodeService.getCodeList("SHD_KND_CD"));
		codeMap.put("shdTypeCd", cmcdCodeService.getCodeList("SCHD_TYPE_CD"));
		codeMap.put("anaStsCd", cmcdCodeService.getCodeList("JOBSTSCD"));

		return codeMap;
	}

	//스케줄로그 조회 뷰 페이지 이동
		@RequestMapping("/commons/damgmt/schedule/schedulelog_lst.do")
		public String formpage() {

			return "/commons/damgmt/schedule/schedulelog_lst";
		}
	//스케줄로그 조회 팝업 페이지 이동 (스케줄 관리의 로그조회 버튼)	
		@RequestMapping("/commons/damgmt/schedule/schedule_log_pop.do")
		public String selectShdLogList(String shdLnm, ModelMap model) {

			model.addAttribute("shdLnm", shdLnm);

			return "/commons/damgmt/schedule/popup/schedule_log_pop";
		}

		//스케줄로그 조회
		@RequestMapping("/commons/damgmt/schedule/getScheduleLogList.do")
		@ResponseBody
		public IBSheetListVO<WamShdLogVO> selectList(@ModelAttribute WamShdLogVO search) {
			logger.debug("{}", search);
			List<WamShdLogVO> list = scheduleLogService.getScheduleLogList(search);
			//logger.debug("{}", list);
			//logger.debug("{}", search);

			return new IBSheetListVO<WamShdLogVO>(list, list.size());
		}
		
		/** 스케쥴로그 리스트 삭제 - IBSheet JSON 
		 * @throws Exception */
		@RequestMapping("/commons/damgmt/schedule/deleteScheduleLogList.do")
		@ResponseBody
		public IBSResultVO<WamShdLogVO> delList(@RequestBody WamShdLogVOs data, Locale locale) throws Exception {

			logger.debug("{}", data);
			ArrayList<WamShdLogVO> list = data.get("data");

			int result = scheduleLogService.deleteLogList(list);

			String resmsg ;
			if (result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.DEL", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.DEL", null, locale);
			}

			String action = WiseMetaConfig.IBSAction.DEL.getAction();


			return new IBSResultVO<WamShdLogVO>(result, resmsg, action);
		}

		//스케줄 작업목록 조회
		@RequestMapping("/commons/damgmt/schedule/getshdjoblst.do")
		@ResponseBody
		public IBSheetListVO<WamShdLogVO> selectJobList(@ModelAttribute WamShdLogVO search) {
			logger.debug("{} selectJobList ", search);
			List<WamShdLogVO> list = scheduleLogService.getScheduleJobList(search);
			
//			logger.debug("{} selectJobList ", list);
//			logger.debug("{} selectJobList ", search);

			return new IBSheetListVO<WamShdLogVO>(list, list.size());
		}

		// 스케줄 상세정보 조회
		@RequestMapping("/commons/damgmt/schedule/ajaxgrid/schedulelog_dtl.do")
		public String selectShdDetail(String shdId, ModelMap model) {
			logger.debug(" {}", shdId);
			//ID가 있을 경우 정보를 조회 하고 업데이트로 변경
			if(!UtilObject.isNull(shdId)) {
				WamShdLogVO result = scheduleLogService.selectShdDetail(shdId);
				model.addAttribute("result", result);
			}
			return "/commons/damgmt/schedule/schedulelog_dtl";
//			return "/commons/damgmt/schedule/shedulejoblst_dtl";
		}
}
