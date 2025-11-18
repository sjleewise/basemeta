package kr.wise.commons.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.helper.CStreamGobbler;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component("SchedulerUtils")
public class SchedulerUtils {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);
	
	private SchedulerUtils(){}

    @Inject
    private static MessageSource message;
	
//  @Value("#{configure['CSchedulerUtils']}")
//  private static String schdulerpath;
	 
    private boolean connectSchedulerServer(String schdulerpath) {
//    	String schdulerpath = message.getMessage(WiseConfig.UPLOAD_PATH, null, Locale.getDefault()); 
//		schdulerpath = "C:/IDE/workspace-nhic/wiseda_scheduler";
		
		try {
			Properties properties = new Properties();
			FileInputStream fi = new FileInputStream(schdulerpath+"/scheduler/classes/schedulerRMI.properties");
			properties.load(new BufferedInputStream(fi));
			new StdSchedulerFactory(properties).getScheduler();
			
			return true;
		} catch(SchedulerException e) {
			logger.error(e.toString());
			return false;
		} catch(Exception e) {
			logger.error(e.toString());
			return false;
		}
	}
    
	public static boolean testConnectSchedulerServer(String schdulerpath) {
		return new SchedulerUtils().connectSchedulerServer(schdulerpath);
	}

	//Quartz 등록
    public static  void registrySchedule(WamShd saveVO, String schdulerCmd) throws Exception {
    	
		if(null == saveVO.getShdId()) {
			throw new Exception("스케줄 등록 type 오류입니다.");
		}
		Process process = null;
		try {
			logger.debug(" {} \n" , saveVO);
			// {I,U,D 택일} {스케줄ID}
			String cmd = schdulerCmd + " " + saveVO.getRegTypCd() + " " + saveVO.getShdId();
			logger.debug(" registrySchedule cmd ::  "+cmd);
//			String cmd = schdulerpath+"/scheduler/bin/ScheduleRegistry.cmd" + " " + saveVO.getRegTypCd() + " " + saveVO.getShdId();
			process = Runtime.getRuntime().exec(cmd);

			// 서버가 윈도우인 경우 버퍼 해소
			CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
			CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");

			out.start();
			error.start();

			int exitValue = process.waitFor();
			logger.debug("ExitValue : " + exitValue);
			
			if(exitValue != 0) {
				throw new Exception("Runtime error.("+exitValue+")");
			}
		} catch (InterruptedException e) {
			logger.error(e.toString());
			throw new Exception(e);
		} catch(IOException e) {
			logger.error(e.toString());
			throw new Exception(e);
		} catch(Exception e) {
			logger.error(e.toString());
			throw e;
		} finally {
			if(process != null) try { process.destroy(); } catch(Exception ignored) {}
		}
	}
    
    //20201027 스케줄 실행되는 도중 작업정지
    public static boolean stopSchedule(String schdulerpath, List<WamShd> list ) {
//    	String schdulerpath = message.getMessage(WiseConfig.UPLOAD_PATH, null, Locale.getDefault()); 
//		schdulerpath = "C:/IDE/workspace-nhic/wiseda_scheduler";
    	boolean result = false;
		HashMap<String, String> shdMap = new HashMap<String,String>();
		//스케줄id를 맵으로 변환
    	for(WamShd vo : list) {
    		shdMap.put(vo.getShdId(),vo.getShdId());
        }
    	
		try {
			Properties properties = new Properties();
			FileInputStream fi = new FileInputStream(schdulerpath+"/scheduler/classes/schedulerRMI.properties");
			properties.load(new BufferedInputStream(fi));
	
			Scheduler scheduler  = new StdSchedulerFactory(properties).getScheduler();
			List<JobExecutionContext> joblist =  scheduler.getCurrentlyExecutingJobs();
			if(joblist.size()<0) {
				return false;
			}
			//실행중인 job목록을 읽어서 중지한 스케줄 id와 일치하면 인터럽트
			 for (JobExecutionContext jobExecutionContext : joblist) {
				 if(shdMap.get(jobExecutionContext.getJobDetail().getKey().getName()) !=null) {
			         scheduler.interrupt(jobExecutionContext.getJobDetail().getKey());
			         result = true;
				 }
			 }
			return result;
		} catch(SchedulerException e) {
			logger.error(e.toString());
			return false;
		} catch(Exception e) {
			logger.error(e.toString());
			return false;
		}
	}
    
    //20201028 파라매터로 넘겨준 스케줄 중 실행 중인 shdId 반환
    public static String getRunningSchedule(String schedulerpath, List<WamShd> list ) {
    	String result = "";
    	HashMap<String, String> shdMap = new HashMap<String,String>();
    	//스케줄id를 맵으로 변환
    	for(WamShd vo : list) {
    		shdMap.put(vo.getShdId(),vo.getShdId());
    	}
    	
    	try {
    		Properties properties = new Properties();
    		FileInputStream fi = new FileInputStream(schedulerpath+"/scheduler/classes/schedulerRMI.properties");
    		properties.load(new BufferedInputStream(fi));
    		
    		Scheduler scheduler  = new StdSchedulerFactory(properties).getScheduler();
    		List<JobExecutionContext> joblist =  scheduler.getCurrentlyExecutingJobs();
    		if(joblist.size()<0) {
    			return "";
    		}
    		//실행중인 job목록을 읽어서 넘겨받은 스케줄 id와 일치하면 result에 스케줄 id 추가
    		for (JobExecutionContext jobExecutionContext : joblist) {
    			if(shdMap.get(jobExecutionContext.getJobDetail().getKey().getName()) !=null) {
    				result += jobExecutionContext.getJobDetail().getKey().getName() + ",";
    			}
    		}
    		return result.substring(0, result.length()-1);
    		
    	} catch(SchedulerException e) {
    		logger.error(e.toString());
    		return "";
    	} catch(Exception e) {
    		logger.error(e.toString());
    		return "";
    	}
    }
}
