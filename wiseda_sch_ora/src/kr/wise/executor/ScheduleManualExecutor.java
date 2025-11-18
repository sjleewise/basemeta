package kr.wise.executor;

import kr.wise.scheduler.ScheduleRegistry;

import org.quartz.SchedulerException;


/**
 * 스케줄 수동 실행
 * @author 김용훈
 */
public class ScheduleManualExecutor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length != 1) {
			System.err.println("It's not SCH_ID arguments.");
		} else {
			try {
				ScheduleManualExecutor scheduleManualExecutor = new ScheduleManualExecutor();
				scheduleManualExecutor.execute(args [0]);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void execute(String schId) throws Exception {
		try {
			ScheduleRegistry registry = new ScheduleRegistry();
			registry.register("U", schId);
		} catch(SchedulerException e) {
			System.err.println("스케줄러 서버에 접속할 수 없습니다.");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
