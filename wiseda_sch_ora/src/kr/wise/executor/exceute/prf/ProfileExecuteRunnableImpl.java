package kr.wise.executor.exceute.prf;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class ProfileExecuteRunnableImpl extends ProfileExecute implements Runnable {

	public ProfileExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
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
