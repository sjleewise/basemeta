/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeSyncTask
 * 2. Package : kr.wise.commons.code.task
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 10. 30.
 * 6. 변경이력 :
 */
package kr.wise.commons.code.task;

import javax.inject.Inject;

import kr.wise.commons.code.service.CodeSyncService;
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
public class CodeSyncTask {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeSyncService  codeSyncService;

	public void sayHello() {
		System.out.println("Task Hello !!!");
	}

	public void CodeSyncExecute() throws Exception {
		logger.debug("start Sync Code()");
//		totalSearchService.selectTotalSearchWord();
		codeSyncService.syncCode();
		logger.debug("end Sync Code()");
	}

}
