package kr.wise.executor.exceute.br;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class BusinessRuleExecuteRunnableImpl extends BusinessRuleExecute implements Runnable {

	public BusinessRuleExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
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
