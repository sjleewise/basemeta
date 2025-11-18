/************************************************************************************
 * wiseitech.wisecomp.helper.batch.CBatchHelper 클래스 수정
 ************************************************************************************/

package kr.wise.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import kr.wise.commons.Utils;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.exceute.DAExecutor;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

/**
 * @version  1.0
 */
public class QuartzSchedulerHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(QuartzSchedulerHelper.class);
	
	/** Quartz Scheduler <code>sched</code> */
	private Scheduler scheduler = null;

	/** Instance of QuartzSchedulerHelper <code>instance</code> */
	private static QuartzSchedulerHelper	instance = null;
	
	/**
	 * 생성자 
	 * @throws SchedulerException
	 */
	private QuartzSchedulerHelper() throws SchedulerException, IOException {
	    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ExecutorConf.SCHEDULER_PROPERTIES_FILENAME);
		Properties properties = new Properties();
		properties.load(inputStream);
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(properties);
		scheduler = schedFact.getScheduler();
	}
	
	/**
	 * SingleTon 방식으로 QuartzSchedulerHelper 획득한다.
	 * @return  QuartzSchedulerHelper SingleTon 방식의 QuartzSchedulerHelper instance
	 * @return
	 * @throws Exception
	 */
	private synchronized static QuartzSchedulerHelper getInstance() throws SchedulerException, Exception {
	    try {
			if (instance == null) {
				instance = new QuartzSchedulerHelper();
			}
	
			return instance;
	    } catch(SchedulerException e) {
	    	logger.error("Scheduler 서버에 접속할 수 없습니다.\n" + e);
	        throw e;
	    } catch(Exception e) {
	    	logger.error("QuartzSchedulerHelper 프로퍼티 로드시 오류가 발생하였습니다.\n" + e);
	        throw e;
	    }
	}

	/**
	 * 배치 서버의 기동을 시작한다.
	 * @throws SchedulerException
	 */
//	public static void start() throws SchedulerException, Exception {
//		logger.info("배치 서비스 기동을 시작한다.");
//		getInstance().sched.start();
//	}
	
	/**
	 * 배치 서버의 기동을 중단한다.
	 * @throws SchedulerException
	 */
	public static void shutdown() throws SchedulerException, Exception {
		getInstance().scheduler.shutdown(true);
		getInstance().scheduler = null;
		logger.info("Scheduler Server is stopped.");
	}
	
	/**
	 * Scheduler에 Job과 Trigger를 등록한다.
	 * @param jobDataMap work들의 instance ListMap
	 * @param trigger Job을 실행시킬 Trigger
	 * @throws SchedulerException
	 */
	public static void registerBatch(JobDataMap jobDataMap, Trigger trigger) throws SchedulerException, Exception {
		logger.info("DA Schedule Register start!!");
		boolean ISJOBDUPLICATE = false;
		
		if( getInstance().scheduler != null ) {
			String[] jobNames = getJobNames(trigger.getGroup());

			if( !Utils.isNull(jobNames) ) {
				for(int i=0; i<jobNames.length; i++) {
					if( jobNames[i].equals(trigger.getJobName()) ) {
						ISJOBDUPLICATE = true;
						break;
					}
				}

				if( !ISJOBDUPLICATE ) {
					// Job Detail 생성
					JobDetail jobDetail = new JobDetail(trigger.getJobName(), trigger.getGroup(), DAExecutor.class);
		
					// Job 관련 Meta Data 세팅
					jobDetail.setJobDataMap(jobDataMap);

					// Job 관련 Listener 세팅
                    logger.debug("registerBatch start1 jobDetail : " + jobDetail);
                    logger.debug("registerBatch start1 trigger : " + trigger);

					// Job과 Trigger를 Scheduler에 적재
                    getInstance().scheduler.scheduleJob(jobDetail, trigger);
                    
                    logger.debug("registerBatch start2 jobDetail : " + jobDetail);
                    logger.debug("registerBatch start2 trigger : " + trigger);
				} else {
				    // Trigger를 update한다.
					getInstance().scheduler.scheduleJob(trigger);
				}
				
				logger.debug("registerBatch "+ getInstance().scheduler.getTrigger(trigger.getName(), trigger.getGroup()).getNextFireTime() );
			}
		}
		logger.info("DA Schedule Register finished!!");
	}
	
	/**
     * Scheduler에서 Trigger를 제외 시킴으로 Job의 실행을 멈춘다.
	 * @param groupName Scheduler에 등록한 Trigger의 GroupName
	 * @return boolean 성공여부
	 * @throws SchedulerException
	 */
	public static boolean removeBatch(String groupName) throws SchedulerException, Exception {
		logger.info("DQ Schedule Remove start!!");
		boolean returnResult = false;
		
		if(getInstance().scheduler != null )
		{
			String[] jobNames = getJobNames(groupName);
			String[] triggerNames = getTriggerNames(groupName);

			// Trigger의 有, 無의 확인 선행
			if( !Utils.isArrayNull(triggerNames) )
			{
				for(int i=0; i<triggerNames.length; i++)
				{
					returnResult = getInstance().scheduler.unscheduleJob(triggerNames[i], groupName);
				}
			}
			
			if( !Utils.isArrayNull(jobNames) )
			{
				for(int i=0; i<jobNames.length; i++)
				{
					returnResult = getInstance().scheduler.deleteJob(jobNames[i], groupName);
				}
			}
		}
		logger.info("DA Schedule Remove finished!!");

		return returnResult;
	}
	
	/**
	 * Scheduler에서 Trigger를 재설정함으로써 새로운 일정으로 재배치시킨다.
	 * @param groupName job과 trigger를 찾고자 하는 Group Name 
	 * @param trigger	재적용할 trigger
	 * @return 성공여부
	 * @throws SchedulerException
	 */
	public static boolean updateTrigger(String groupName, Trigger trigger) throws SchedulerException, Exception {
		boolean returnResult = false;

		if( getInstance().scheduler != null )
		{
//			String[] jobNames = getJobNames(groupName);
			String[] triggerNames = getTriggerNames(groupName);

			// Trigger의 有, 無의 확인 선행
			if( !Utils.isArrayNull(triggerNames) )
			{
				Date tmpDate = null;
				for(int i=0; i<triggerNames.length; i++)
				{
					tmpDate = getInstance().scheduler.rescheduleJob(triggerNames[i], groupName, trigger);
				}

				if( !Utils.isNull(tmpDate) )
					returnResult = true;
			}
		}

		return returnResult;
	}
	
	/**
	 * groupName으로 구성되어 있는 JobName Array를 반환한다.
	 * @param groupName JobName Array를 찾고자 하는 groupName
	 * @throws SchedulerException
	 */
	private static String[] getJobNames(String groupName) throws SchedulerException, Exception {
		String[] jobNames = getInstance().scheduler.getJobNames(groupName);
		logger.debug("groupName("+groupName+")으로 등록되어 있는 jobName의 갯수 ["+jobNames.length+"]");
		return jobNames;
	}
	
	/**
     * Scheduler에 등록되어 있는 Trigger의 목록을 GroupName으로 조회한다.
	 * @param groupName 조회코저하는 groupName
	 * @return String[] Trigger Name Array
	 * @throws SchedulerException
	 */
	private static String[] getTriggerNames(String groupName) throws SchedulerException, Exception {
		String[] triggerNames = getInstance().scheduler.getTriggerNames(groupName);
		logger.debug("groupName("+groupName+")으로 등록되어 있는 triggerName의 갯수 ["+triggerNames.length+"]");
		return triggerNames;
	}
	
}
