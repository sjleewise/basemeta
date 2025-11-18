/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : TotalSearchTask.java
 * 2. Package : kr.wise.commons.schedule.task
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 16. 오후 4:52:22
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 16. :            : 신규 개발.
 */
package kr.wise.commons.schedule.task;

import javax.inject.Inject;

import kr.wise.portal.totalsearch.service.TotalSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : TotalSearchTask.java
 * 3. Package  : kr.wise.commons.schedule.task
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 16. 오후 4:52:22
 * </PRE>
 */
public class TotalSearchTask {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private TotalSearchService  totalSearchService;

	public void sayHello() {
		System.out.println("Task Hello !!!");
	}

	public void totSearchexecute() throws Exception {
		logger.debug("start totSearchexecute()");
//		totalSearchService.selectTotalSearchWord();
		totalSearchService.regTotalSearchIndex();
		logger.debug("end totSearchexecute()");
	}

}
