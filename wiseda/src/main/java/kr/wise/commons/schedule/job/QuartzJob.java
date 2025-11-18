/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : QuartzJob.java
 * 2. Package : kr.wise.commons.schedule.job
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 16. 오후 4:55:30
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 16. :            : 신규 개발.
 */
package kr.wise.commons.schedule.job;

import kr.wise.commons.schedule.task.TotalSearchTask;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : QuartzJob.java
 * 3. Package  : kr.wise.commons.schedule.job
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 16. 오후 4:55:30
 * </PRE>
 */
public class QuartzJob extends QuartzJobBean {

	private TotalSearchTask totSearchTask;

	/**
	 * @param totSearchTask the totSearchTask to set
	 */
	public void setTotSearchTask(TotalSearchTask totSearchTask) {
		this.totSearchTask = totSearchTask;
	}


	/** insomnia */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		totSearchTask.sayHello();

	}




}
