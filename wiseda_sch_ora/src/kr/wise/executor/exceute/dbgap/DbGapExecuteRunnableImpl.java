package kr.wise.executor.exceute.dbgap;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class DbGapExecuteRunnableImpl extends DbGapExecute implements Runnable {

	public DbGapExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public void run() {
		try {
            doExecute();
        } catch (SQLException e) {
            
            e.printStackTrace();
        } catch (Exception e) {
           
            e.printStackTrace();
        }
	}

}
