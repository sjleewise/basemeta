/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName :MessageSyncTask
 * 2. Package : kr.wise.sysinf.messageSync.task;
 * 3. Comment : 메시지 동기화 태스트
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 10. 30.
 * 6. 변경이력 :
 */
package kr.wise.sysinf.messageSync.task;

import javax.inject.Inject;

import kr.wise.sysinf.messageSync.service.MessageSyncService;

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
public class MessageSyncTask {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private MessageSyncService  messageSyncService;

	public void sayHello() {
		System.out.println("Task Hello !!!");
	}

	public void MessageSyncExecute() throws Exception {
		logger.debug("start Sync Code()");
//		totalSearchService.selectTotalSearchWord();
		messageSyncService.syncMessage();
		logger.debug("end Sync Code()");
	}

}
