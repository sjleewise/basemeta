/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ScheduleLogService.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service
 * 3. Comment :
 * 4. 작성자  : kchoi(최결)
 * 5. 작성일  : 2014. 4. 11. 오후 1:10:31
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi(최결) : 2014. 4. 24. :            : 신규 개발.
 *
 */
package kr.wise.commons.damgmt.schedule.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ScheduleLogService.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service
 * 4. Comment  :
 * 5. 작성자   : kchoi
 * 6. 작성일   : 2014. 4. 24. 오후 1:10:31
 * </PRE>
 */
public interface ScheduleLogService {

	List<WamShdLogVO> getScheduleLogList(WamShdLogVO search);

	List<WamShdLogVO> getScheduleJobList(WamShdLogVO search);

	WamShdLogVO selectShdDetail(String shdId);

	List<WamShdLogVO> getLogLst(WamShdLogVO search);

	WamShdLogVO selectAnaKndCd(WamShdLogVO search);

	/** @param list
	/** @return yeonho */
	int deleteLogList(ArrayList<WamShdLogVO> list);

}
