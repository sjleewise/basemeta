package kr.wise.executor.exceute.schema.dbms.postgresql;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorPostgresqlService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception; 
	
}
