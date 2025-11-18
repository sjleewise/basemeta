package kr.wise.commons.cmm.web;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.ErrLogService;
import kr.wise.commons.cmm.service.MessengerService;
import kr.wise.commons.cmm.service.WaaErrLog;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrLogCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	ErrLogService errLogService;
	
	@RequestMapping("/commons/sys/err/errlog_lst.do")
	public String goErrlogLst(WaaErrLog search)
	{
		return "/commons/sys/err/errlog_lst";
	}
	
	
	@RequestMapping("/commons/sys/err/geterrloglst.do")
	@ResponseBody
	public IBSheetListVO<WaaErrLog> getErrLogLst(WaaErrLog search)
	{
		
		List<WaaErrLog> list = errLogService.selectErrLogList(search);
		
		return new IBSheetListVO<>(list, list.size());
	}
}
