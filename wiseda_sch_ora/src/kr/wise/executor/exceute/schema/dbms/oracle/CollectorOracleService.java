package kr.wise.executor.exceute.schema.dbms.oracle;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorOracleService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception;
}
