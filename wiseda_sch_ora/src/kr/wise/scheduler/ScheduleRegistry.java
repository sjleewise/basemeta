package kr.wise.scheduler;

import java.util.Calendar;

import kr.wise.commons.Utils;
import kr.wise.executor.dao.ScheduleDAO;
import kr.wise.executor.dm.SchMstDM;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * 스케줄러 등록을 실행하는 클래스
 */
public class ScheduleRegistry {

	/**
	 * <p>스케줄 등록/수정/삭제면 {C,U,D 택일} {스케줄ID}</p>
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Parameter is not vaild.");
			System.exit(1);
		} else {
			//스케줄 종류 또는 regTypCd 사용
			String shdObjCD   = args [0];
			
			// 스케줄 관리 등록
			if("C".equals(shdObjCD) || "U".equals(shdObjCD) || "D".equals(shdObjCD)) {	
				if(args.length < 2) {
					System.err.println("Parameter is not vaild.");
					System.exit(1);
				} else {
					
					String schId = args [1];
					try {
						ScheduleRegistry registry = new ScheduleRegistry();
						
						System.err.println("스케줄 아이디"+schId);
						registry.register(shdObjCD, schId);
						
					} catch(SchedulerException e) {
						System.err.println("스케줄러 서버에 접속할 수 없습니다.");
						e.printStackTrace();
						System.exit(2);
					} catch(Exception e) {
						e.printStackTrace();
						System.exit(3);
					}
				}
			} else {
				System.err.println("Parameter is not vaild.");
				System.exit(1);
			}
		}
	}
	
	
	/**
	 * 온라인 즉시 실행 스키마수집이면 SC {진단대상 ID} {실행명} {실행자ID}  <br>
	 *               SC 0 "스키마수집 수동실행" system  <br>
	 * 온라인 즉시 실행 프로파일이면 QP {프로파일 ID} {실행명} {실행자ID} <br>
	 *               QP P1292397905656 "프로파일 수동실행" system <br>
	 * 온라인 즉시 실행 업무규칙이면 QB {업무규칙 ID} {실행명} {실행자ID} <br>
	 *               QB B1292397948234 "업무규칙 수동실행" system <br>
	 * <p>스케줄 등록/수정/삭제면 {I,U,D 택일} {스케줄ID}</p>
	 * @param args
	 */
/*	
   public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Parameter is not vaild.");
			System.exit(1);
		} else {
			//스케줄 종류 또는 regTypCd 사용
			String shdObjCD   = args [0];
			
			// 온라인 실행 등록
			if(DQConstant.EXE_TYPE_01_CD.equals(shdObjCD) || DQConstant.EXE_TYPE_02_CD.equals(shdObjCD) || DQConstant.EXE_TYPE_03_CD.equals(shdObjCD) || "JC".equals(shdObjCD)) {	
				if(args.length < 6) {
					System.err.println("Parameter is not vaild.");
					System.exit(1);
				} else {
					
					String shdId = args [1];
					String shdLnm = args [2];
					String rqstUserId = args [3];
					
					try {
						ScheduleRegistry registry = new ScheduleRegistry();
						registry.register(shdObjCD, shdId, shdLnm, rqstUserId);
					} catch(SchedulerException e) {
						System.err.println("스케줄러 서버에 접속할 수 없습니다.");
						e.printStackTrace();
						System.exit(2);
					} catch(Exception e) {
						e.printStackTrace();
						System.exit(3);
					}
				}
			} 
			
			// 스케줄 관리 등록
			else if("C".equals(shdObjCD) || "U".equals(shdObjCD) || "D".equals(shdObjCD)) {	
				if(args.length < 2) {
					System.err.println("Parameter is not vaild.");
					System.exit(1);
				} else {
					
					String schId = args [1];
					try {
						ScheduleRegistry registry = new ScheduleRegistry();
						registry.register(shdObjCD, schId);
						
					} catch(SchedulerException e) {
						System.err.println("스케줄러 서버에 접속할 수 없습니다.");
						e.printStackTrace();
						System.exit(2);
					} catch(Exception e) {
						e.printStackTrace();
						System.exit(3);
					}
				}
			} else {
				System.err.println("Parameter is not vaild.");
				System.exit(1);
			}
		}
	}
*/
	/**
	 * 스케줄 등록, 수정, 삭제
	 * @param cmd
	 * @param schId
	 * @throws Exception
	 */
	public void register(String shdObjCD, String schId) throws SchedulerException, Exception {
		if("C".equals(shdObjCD)) {
	        JobDataMap jobDataMap = new JobDataMap();
	        jobDataMap.put("REG_TYP_CD", shdObjCD);
	        jobDataMap.put("SHD_ID", schId);
	        
			QuartzSchedulerHelper.registerBatch(jobDataMap, getTrigger(schId));
		} else if("U".equals(shdObjCD)) {
//			QuartzSchedulerHelper.updateTrigger(schId, getTrigger(schId));
			try {
				QuartzSchedulerHelper.removeBatch(schId);
			} catch(Exception ignored) {
			}
	        JobDataMap jobDataMap = new JobDataMap();
	        jobDataMap.put("REG_TYP_CD", shdObjCD);
	        jobDataMap.put("SHD_ID", schId);
			QuartzSchedulerHelper.registerBatch(jobDataMap, getTrigger(schId));
		} else if("D".equals(shdObjCD)) {
			try {
				QuartzSchedulerHelper.removeBatch(schId);
			} catch(Exception ignored) {
			}
		} else {
			throw new Exception("스케줄 등록 type 오류입니다.");
		}
	}
	
	/**
	 * 온라인실행인 경우에 사용되며, 즉시 실행되도록 현재 시간의 10초 후로 셋팅되며, 스케줄러 체크주기가 30초이므로 30초이내에 실행된다.
	 * @param cmd
	 * @parma cmdName
	 * @param exeId
	 * @param exeUserId
	 * @param exeUserNm
	 * @param exeDeptNm
	 * @throws Exception
	 */
	public void register(String shdKndCD, String shdId, String shdLnm, String rqstUserId) throws SchedulerException, Exception {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("SHD_ID", shdId);
        jobDataMap.put("SHD_KND_CD", shdKndCD);
        jobDataMap.put("SHD_LNM", shdLnm);
        jobDataMap.put("RQST_USER_ID", rqstUserId);
        
        // 현재시간의 10초 후 트리거 생성
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);		
        
        Trigger trigger = QuartzTriggerHelper.getOnceTrigger(calendar.getTime());
        trigger.setGroup(shdId);
		trigger.setName(shdId);
		trigger.setJobGroup(shdId);
		trigger.setJobName(shdId);
        
		QuartzSchedulerHelper.registerBatch(jobDataMap, trigger);
	}
	
    private Trigger getTrigger(String shdId) throws Exception {
    	//스케줄 정보 조회
		ScheduleDAO dao = new ScheduleDAO();
		SchMstDM schMstDM = dao.selectSchMst(shdId);
		
		int startHour = Utils.nvlInt( schMstDM.getShdStrHr(), 0);
		int startMin  = Utils.nvlInt(  schMstDM.getShdStrMnt(), 0);
		
		Trigger trigger = null;
        if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.ONCE))         // 일시
            trigger = QuartzTriggerHelper.getOnceTrigger(schMstDM.getShdStrDtm(), startHour, startMin);
        else if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.DAY))     // 일
            trigger = QuartzTriggerHelper.getDayTrigger(schMstDM.getShdDly(), schMstDM.getShdDlyVal(), schMstDM.getShdStrDtm(), startHour, startMin);
        else if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.WEEK))    // 주
            trigger = QuartzTriggerHelper.getWeekTrigger(schMstDM.getShdWkl(), schMstDM.getShdWklVal(), schMstDM.getShdStrDtm(), startHour, startMin);
        else if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.MONTH))   // 월
            trigger = QuartzTriggerHelper.getMonthTrigger(schMstDM.getShdMny(), schMstDM.getShdMnyVal(), schMstDM.getShdStrDtm(), startHour, startMin);
        else if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.HOUR))   // 매시
            trigger = QuartzTriggerHelper.getHourTrigger(schMstDM.getShdStrDtm(), startHour);
        else if(schMstDM.getShdTypCd().equals(QuartzTriggerHelper.MINUTE))   // 매분
            trigger = QuartzTriggerHelper.getMinuteTrigger(schMstDM.getShdStrDtm(), startMin);
        
        trigger.setGroup(shdId);            // Trigger의 Group세팅 (EXEJSCID)    
        trigger.setName(shdId);             // Trigger의 Name세팅 (EXESCHID)
        trigger.setJobGroup(shdId);         // Trigger의 JobName세팅 (EXEJSCID)
        trigger.setJobName(shdId);          // Trigger의 GroupName세팅 (EXEJSCID)
		
    	return trigger;
    }
    
}
