package kr.wise.executor.exceute.etc;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class EtcExecuteRunnableImpl extends EtcExecute implements Runnable {

	public EtcExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public void run() {
		try {
            doExecute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
