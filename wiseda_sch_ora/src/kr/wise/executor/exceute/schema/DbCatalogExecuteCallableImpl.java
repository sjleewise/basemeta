package kr.wise.executor.exceute.schema;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

public class DbCatalogExecuteCallableImpl extends DbCatalogExecute implements Callable<Boolean> {

	public DbCatalogExecuteCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	public Boolean call() throws Exception {
		return doExecute();
	}

}
