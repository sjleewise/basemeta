package kr.wise.commons.sysmgmt.log.weblog.service;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WebLogService.java
 * 3. Package  : kr.wise.commons.sysmgmt.log.weblog.service
 * 4. Comment  : 웹로그 정보를 처리하는 서비스
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 11. 27. 오후 6:13:57
 * </PRE>
 */ 
public interface WebLogService {
	
	/**
	 * 웹 로그를 기록한다.
	 * 
	 * @param WebLog
	 */
	public void logInsertWebLog(WebLog webLog) throws Exception;

	/**
	 * 웹 로그정보를 요약한다.
	 * 
	 * @param 
	 */
	public void logInsertWebLogSummary() throws Exception;

	/**
	 * 웹로그를 조회한다.
	 * 
	 * @param webLog
	 * @return webLog
	 * @throws Exception 
	 */
	public WebLog selectWebLog(WebLog webLog) throws Exception;

	/**
	 * 웹 로그정보 목록을 조회한다.
	 * 
	 * @param WebLog
	 */
	public Map selectWebLogInf(WebLog webLog) throws Exception;

	public List<WebLog> getuseractionlist(WebLog searchVO);	

}
