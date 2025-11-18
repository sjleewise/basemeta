package kr.wise.commons.sysmgmt.log.connlog.web;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.handler.customeditor.DateFormatPropertyEditor;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.commons.stat.service.StatsVO;
import kr.wise.commons.sysmgmt.log.connlog.service.LoginLog;
import kr.wise.commons.sysmgmt.log.connlog.service.LoginLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginLogCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private LoginLogService loginLogService;
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.setDisallowedFields("writDtm", "writUserId");
	    binder.registerCustomEditor(String.class, "searchBgnDe", new DateFormatPropertyEditor());
	    binder.registerCustomEditor(String.class, "searchEndDe", new DateFormatPropertyEditor());
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectLoginLog
	 * 3. Comment   : 사용자별 접속이력 리스트 
	 * 4. 작성자    : insomnia(장명수)
	 * 5. 작성일    : 2013. 12. 23. 오후 1:37:44
	 * </PRE>
	 *   @return String
	 *   @param loginLog
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping("/commons/sys/connlog/selectLoginLog.do")
	public String selectLoginLog(@ModelAttribute("searchVO") LoginLog loginLog, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
//		return "/commons/sys/connlog/loginLogList";
		return "/commons/sys/connlog/loginlog_lst";
	}
	
	/** 사용자별 접속이력 조회 - IBSheet JSON 으로 리턴... */
	@RequestMapping("/commons/sys/connlog/selectLoginLogList.do")
	@ResponseBody
	public IBSheetListVO<LoginLog> selectList(@ModelAttribute("searchVO") LoginLog searchVO) {
		logger.debug("{}", searchVO);
		List<LoginLog> resultlist = loginLogService.selectLoginLogList(searchVO);
		
		
		return new IBSheetListVO<LoginLog>(resultlist, resultlist.size());
//		ibsresult.TOTAL = list.size();
//		ibsresult.DATA = list;
		
//		return ibsresult;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectLoginLogList
	 * 2. ClassName  : LoginLogCtrl
	 * 3. Comment   : 사용자별 접속이력 조회 (jqgrid)
	 * 4. 작성자    : insomnia(장명수)
	 * 5. 작성일    : 2013. 12. 23. 오후 1:38:15
	 * </PRE>
	 *   @return JqGridListVO<LoginLog>
	 *   @param searchVO
	 *   @return
	 */
	@RequestMapping("/commons/sys/connlog/ajaxgrid/selectLoginLogList.do")
	@ResponseBody
	public JqGridListVO<LoginLog> selectLoginLogList(@ModelAttribute("searchVO") LoginLog searchVO) {
		
		List<LoginLog> resultlist = loginLogService.selectLoginLogList(searchVO);
		
		return new JqGridListVO<LoginLog>(resultlist);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName : selectConnStat
	 * 2. ClassName  : LoginLogCtrl
	 * 3. Comment   : 사용자별 접속통계 폼
	 * 4. 작성자    : insomnia(장명수)
	 * 5. 작성일    : 2013. 12. 23. 오후 1:38:32
	 * </PRE>
	 *   @return String
	 *   @param loginLog
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping("/commons/sys/connlog/selectConnStat.do")
	public String selectConnStat(@ModelAttribute("searchVO") LoginLog loginLog, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
//		return "/commons/sys/connlog/connStatList";
		return "/commons/sys/connlog/connstat_lst";
	}
	
	/** 기간별 사용자 접속통계 */
	@RequestMapping("/commons/sys/connlog/ajaxgrid/selectConnStatList.do")
	@ResponseBody
	public JqGridListVO<StatsVO> selectConnStatList(@ModelAttribute("searchVO") LoginLog searchVO) {
		logger.debug("loginLog : {}", searchVO);
		
		List<StatsVO> resultlist = loginLogService.selectConnStatList(searchVO);
		
		return new JqGridListVO<StatsVO>(resultlist);
	}

	/** 기간별 사용자 접속통계-IBSheet Json */
	@RequestMapping("/commons/sys/connlog/selectConnStatList.do")
	@ResponseBody
	public IBSheetListVO<StatsVO> selectConnStatListIBS(@ModelAttribute("searchVO") LoginLog searchVO) {
		logger.debug("loginLog : {}", searchVO);
		
		List<StatsVO> resultlist = loginLogService.selectConnStatList(searchVO);
		
		return new IBSheetListVO<StatsVO>(resultlist, resultlist.size());
	}

	/** 기간별 접속통계 */
	@RequestMapping("/commons/sys/connlog/ajaxgrid/selectConnStatTot.do")
	@ResponseBody
	public JqGridListVO<StatsVO> selectConnStatTot(@ModelAttribute("searchVO") LoginLog searchVO) {
		logger.debug("loginLog : {}", searchVO);
		
		List<StatsVO> resultlist = loginLogService.selectConnStatTot(searchVO);
		
		return new JqGridListVO<StatsVO>(resultlist);
	}

	/** 기간별 접속통계 - IBSheet Json */
	@RequestMapping("/commons/sys/connlog/selectConnStatTot.do")
	@ResponseBody
	public IBSheetListVO<StatsVO> selectConnStatTotIBS(@ModelAttribute("searchVO") LoginLog searchVO) {
		logger.debug("loginLog : {}", searchVO);
		
		List<StatsVO> resultlist = loginLogService.selectConnStatTot(searchVO);
		
		return new IBSheetListVO<StatsVO>(resultlist, resultlist.size());
	}

}
