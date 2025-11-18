package kr.wise.executor.exceute.etc;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class EtcExecuteCallableImpl extends EtcExecute implements Callable<Boolean> {

	public EtcExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
