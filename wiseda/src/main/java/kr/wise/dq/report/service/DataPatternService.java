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
package kr.wise.dq.report.service;

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
public interface DataPatternService {

	List<DataPatternVO> getDataPattern(DataPatternVO search);

	DataPatternVO DptrnHeaderInit(DataPatternVO search);

	DataPatternVO DptrnHeaderText(DataPatternVO search);

	DataPatternVO getPrfAnaResDtl(DataPatternVO search);

	DataPatternVO getBrAnaResDtl(DataPatternVO search);

	/**
	 * @param search
	 * @return
	 */
	List<DataPatternVO> getPkDataPattern(DataPatternVO search);

	/**
	 * @param search
	 * @return
	 */
	DataPatternVO PkDptrnHeaderText(DataPatternVO search);

	String getPrfcScript(DataPatternVO search);

	int prfcScriptSave(DataPatternVO search);

}
