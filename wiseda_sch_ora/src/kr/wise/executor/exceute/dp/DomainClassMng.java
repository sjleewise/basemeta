package kr.wise.executor.exceute.dp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.UniqueKeyGenerator;
import kr.wise.commons.Utils;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.ExecuteLogDAO;
import kr.wise.executor.dao.ScheduleDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.SchJobDM;
import kr.wise.executor.dm.SchMstDM;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.br.BusinessRuleExecuteCallableImpl;
import kr.wise.executor.exceute.br.BusinessRuleExecuteRunnableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteCallableImpl;
import kr.wise.executor.exceute.dbgap.DbGapExecuteRunnableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteCallableImpl;
import kr.wise.executor.exceute.etc.EtcExecuteRunnableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteCallableImpl;
import kr.wise.executor.exceute.prf.ProfileExecuteRunnableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteCallableImpl;
import kr.wise.executor.exceute.schema.DbCatalogExecuteRunnableImpl;
import kr.wise.meta.mmart.dao.Mmart9WaeErwinDAO;
import kr.wise.scheduler.ScheduleRegistry;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class DomainClassMng  {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DomainClassMng.class);
	
	
	private static ExecutorService executor; 
	
	private static String shdId    = "";
	private static String shdJobId    = "";
	private static String shdTypCd = "QP"; //GN, QB: 업무규칙, QP :프로파일
                
	private static String rqstUserId = "meta";
	private static String aprvUserId = "meta";
	
	private static String shdStaDtm = "";
	
	/**
     * @param args
	 * @throws Exception 
	 * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException, Exception {   
        
        Connection con = ConnectionHelper.getDAConnection();
        
        try{
            
        	ExecutorDM dm = new ExecutorDM();
        	
        	dm.setShdJobId("REQ_00000001192");
        	
        	DomainClass dc = new DomainClass("xxxxxxxx", dm); 
        	
        	dc.doExecute();
            
        } catch(Exception e) {
            con.rollback();
            
            e.printStackTrace();
            System.exit(1);
        }finally{
            
            if(con   != null) try { con.close(); } catch(Exception igonred) {}
        }
    }
    
}
