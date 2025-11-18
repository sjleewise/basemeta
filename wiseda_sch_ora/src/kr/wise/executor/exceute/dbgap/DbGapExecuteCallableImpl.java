package kr.wise.executor.exceute.dbgap;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class DbGapExecuteCallableImpl extends DbGapExecute implements Callable<Boolean> {

	public DbGapExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
