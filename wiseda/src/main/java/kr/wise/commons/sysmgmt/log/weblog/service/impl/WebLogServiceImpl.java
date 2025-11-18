package kr.wise.commons.sysmgmt.log.weblog.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.sysmgmt.log.weblog.service.WebLog;
import kr.wise.commons.sysmgmt.log.weblog.service.WebLogMapper;
import kr.wise.commons.sysmgmt.log.weblog.service.WebLogService;

import org.springframework.stereotype.Service;


@Service("webLogService")
public class WebLogServiceImpl implements WebLogService {
	
	@Inject
	private WebLogMapper webLogMapper;
	
	@Inject
	private EgovIdGnrService webLogIdGnrService; 

	public void logInsertWebLog(WebLog webLog) throws Exception {
		String requstId = webLogIdGnrService.getNextStringId();
		webLog.setRequstId(requstId);
		webLogMapper.logInsertWebLog(webLog);
		
	}

	public void logInsertWebLogSummary() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public WebLog selectWebLog(WebLog webLog) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Map selectWebLogInf(WebLog webLog) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WebLog> getuseractionlist(WebLog searchVO) {
		
		return webLogMapper.selectweblogbyuser(searchVO);
	}

}
