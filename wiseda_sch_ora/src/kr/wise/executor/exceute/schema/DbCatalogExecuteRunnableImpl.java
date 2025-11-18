package kr.wise.executor.exceute.schema;

import java.sql.SQLException;

import kr.wise.executor.dm.ExecutorDM;


public class DbCatalogExecuteRunnableImpl extends DbCatalogExecute implements Runnable {

	public DbCatalogExecuteRunnableImpl(String logId, ExecutorDM executorDM) {
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
