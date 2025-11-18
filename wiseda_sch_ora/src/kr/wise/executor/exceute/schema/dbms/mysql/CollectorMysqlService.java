package kr.wise.executor.exceute.schema.dbms.mysql;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorMysqlService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception;
}
