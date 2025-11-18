package kr.wise.executor.exceute.schema.dbms.mssql;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorMssqlService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception;
}
