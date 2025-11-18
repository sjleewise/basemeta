package kr.wise.executor.exceute.schema.dbms.cubrid;

import java.sql.Connection;
import java.util.List;

import kr.wise.executor.dm.TargetDbmsDM;

public interface CollectorCubridService {
	
	public boolean doProcess() throws Exception ;
	
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception; 
	
}
