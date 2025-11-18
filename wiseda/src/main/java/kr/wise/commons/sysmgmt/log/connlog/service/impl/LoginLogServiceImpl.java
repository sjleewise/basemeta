
package kr.wise.commons.sysmgmt.log.connlog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.stat.service.StatsVO;
import kr.wise.commons.sysmgmt.log.connlog.service.LoginLog;
import kr.wise.commons.sysmgmt.log.connlog.service.LoginLogMapper;
import kr.wise.commons.sysmgmt.log.connlog.service.LoginLogService;

import org.springframework.stereotype.Service;



/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginLogServiceImpl.java
 * 3. Package  : kr.wise.commons.sysmgmt.log.connlog.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 11. 27. 오후 1:43:30
 * </PRE>
 */ 
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
	
	
	@Inject
	private LoginLogMapper loginLogMapper;	

	

    /** ID Generation */    
	@Inject
	private EgovIdGnrService loginLogIdGnrService;



	/**
	 * 접속로그를 기록한다.
	 * 
	 * @param LoginLog
	 */
	public void logInsertLoginLog(LoginLog loinLog) throws Exception {


		String logId = loginLogIdGnrService.getNextStringId();
		loinLog.setLogId(logId);

		loginLogMapper.logInsertLoginLog(loinLog);    	
	}



	/**
	 * 접속로그를 조회한다.
	 * 
	 * @param loginLog
	 * @return loginLog
	 * @throws Exception 
	 */
	public LoginLog selectLoginLog(LoginLog loginLog) throws Exception{
		return loginLogMapper.selectLoginLog(loginLog);
	}	



	/**
	 * 접속로그 목록을 조회한다.
	 * 
	 * @param LoginLog
	 */
	public Map selectLoginLogInf(LoginLog loinLog) throws Exception {
		List _result = loginLogMapper.selectLoginLogInf(loinLog);
		int _cnt = loginLogMapper.selectLoginLogInfCnt(loinLog);
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));

		return _map;
	}


	/**
	 * 접속로그 목록을 조회한다.
	 * 
	 * @param LoginLog
	 */
	public List<LoginLog> selectLoginLogList(LoginLog searchVO) {
		
		return loginLogMapper.selectLoginLogList(searchVO);
	}



	/** 접속 통계 목록을 조회한다. */
	public List<StatsVO> selectConnStatList(LoginLog searchVO) {
		
		return loginLogMapper.selectConnStatList(searchVO);
	}



	public List<StatsVO> selectConnStatTot(LoginLog searchVO) {
		return loginLogMapper.selectConnStatTot(searchVO);
	}

}


