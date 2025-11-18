package kr.wise.executor.exceute.prf;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class ProfileExecuteCallableImpl extends ProfileExecute implements Callable<Boolean> {

	public ProfileExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
