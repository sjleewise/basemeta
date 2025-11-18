package kr.wise.executor.exceute.vrfcrule;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class VrfcRuleExecuteCallableImpl extends VrfcRuleExecute implements Callable<Boolean> {

	public VrfcRuleExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
