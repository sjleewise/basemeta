package kr.wise.executor;

import java.sql.Connection;
import java.sql.SQLException;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dao.ExecuteLogDAO;
import kr.wise.executor.dm.ExecutorDM;

public abstract class Executor {
	
	/** 성공 */
	public static final String SUCCESS = "01";
	/** 진행중(시작) */
	public static final String PROCEED = "02";
	/** 실행전 */
	public static final String NOT_PROCEED = "05";
	/** 에러(JOB 실행시 에러)-작업내부에러 */
	public static final String JOB_ERROR = "03";
	/** 에러(스케줄에서 JOB 생성시 에러)-작업호출 */
	public static final String SCH_ERROR = "04";
	
	protected String logId = null;
	protected String exeStaDttm = null;	// 시작일시
	protected ExecutorDM executorDM = null;
	
	public Executor(String logId, ExecutorDM executorDM) {
		this.logId = logId;
		this.executorDM = executorDM;
	}

	/**
	 * 실행로직을 구현한다.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	protected abstract Boolean execute() throws SQLException, Exception;
	
	/**
	 * 구현된 실행로직의 로그 저장을 담당한다.<br>
	 * execute() 메소드의 시작, 종료 로그 및 Exception을 catch하여 저장한다.<br>
	 * Thread 에서는 이 메소드(doExecute())를 호출하여 실행해야만 한다.
	 * @return
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Boolean doExecute() throws SQLException, Exception  {
		Boolean b = false;
		
		Connection con = ConnectionHelper.getDAConnection();   
		
		ExecuteLogDAO executeLogDAO = new ExecuteLogDAO(con);
		try {
			exeStaDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 시작일시
			executeLogDAO.updateLogStart(logId, exeStaDttm);
//			System.out.println("=== start execute ===");
			b = execute();
//			System.out.println("=== end execute ===");
			
			executeLogDAO.updateLog(logId, Executor.SUCCESS);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			executeLogDAO.updateLog(logId, Executor.JOB_ERROR, e.getLocalizedMessage(), null);
			b = new Boolean(false);
		} finally {
		    
		    if(con != null) con.close();
		    
			// executeLogDAO.updateEndLog(logId);
		}
		
		return b;
	}
}
