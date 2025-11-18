package kr.wise.executor.exceute.schema.dbms.tibero;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorTiberoService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception; 
	
}
