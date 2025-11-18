/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ScheduleManagerService.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 4. 11. 오후 3:13:51
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 11. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.schedule.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ScheduleManagerService.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 4. 11. 오후 3:13:51
 * </PRE>
 */
public interface ScheduleManagerService {

	List<WamShd> getScheduleList(WamShd search);

	WamShd getScheduleDtlList(WamShd search);

	public List<WamShd> getScheduleJobList(WamShd search);

	public List<WamShdJob> getJobPopList(WamShdJob search);

	public int saveSchedule(ArrayList<WamShdJob> list, WamShd saveVO) throws Exception;

	int delSchedule(List<WamShdJob> list) throws Exception;
	
	int delScheduleJob(List<WamShdJob> list, String shdId) throws Exception;

	int schStart(String schedulerpath) throws Exception;

	int schStop(String schedulerpath) throws Exception;
	
	public List<WamShdJob> getImdScheduleJobList(WamShd search);

	String getDbmsTypCd(String dbConnTrgId) throws Exception;
}
