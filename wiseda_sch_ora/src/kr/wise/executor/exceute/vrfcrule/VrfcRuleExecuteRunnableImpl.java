package kr.wise.executor.exceute.vrfcrule;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class VrfcRuleExecuteRunnableImpl extends VrfcRuleExecute implements Runnable {

	public VrfcRuleExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
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
