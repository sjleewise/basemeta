package kr.wise.executor.exceute.schema.dbms.altibase;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorAltibaseService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception;
}
