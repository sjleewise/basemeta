package kr.wise.executor.exceute.br;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class BusinessRuleExecuteCallableImpl extends BusinessRuleExecute implements Callable<Boolean> {

	public BusinessRuleExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
