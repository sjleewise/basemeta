package kr.wise.executor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import kr.wise.executor.Executor;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.ExecutorDM;

import org.apache.log4j.Logger;


public class ExecuteLogDAO {
	
       
    /**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExecuteLogDAO.class);
	
	private Connection conn = null;
	
	private PreparedStatement pstmtInsertLog = null;
	private PreparedStatement pstmtUpdateLogStart = null;
	private PreparedStatement pstmtUpdateLog = null;
	
	public ExecuteLogDAO(Connection con) throws SQLException{
	        
        conn = con;
        
        insertLogPstmt(conn);
        
        updateLogStartPstmt(conn);  
        
        updateLogPstmt(conn);
    }
	
	protected void finalize() throws Throwable {
	    
	    super.finalize();
	   
	    if(pstmtInsertLog != null) pstmtInsertLog.close();
	    
	    if(pstmtUpdateLogStart != null) pstmtUpdateLogStart.close();
	    
	    if(pstmtUpdateLog != null) pstmtUpdateLog.close();
	}
	 
	
	private void insertLogPstmt(Connection con) throws SQLException{ 
	    
	    StringBuilder query = new StringBuilder();
	    
        query.append("\n INSERT INTO WAA_SHD_LOG (    ");
        query.append("\n             ANA_LOG_ID       ");
        query.append("\n             ,SHD_ID          ");
        query.append("\n             ,SHD_JOB_ID      ");
        query.append("\n             ,ANA_STS_CD      ");
        query.append("\n             ,ANA_USER_ID     ");
        query.append("\n             ,SHD_STR_DTM     ");
        query.append("\n             ,SHD_JOB_NM      ");
        query.append("\n ) VALUES (                   ");
        query.append("\n             ?                ");
        query.append("\n           , ?                ");
        query.append("\n           , ?                ");
        query.append("\n           , ?                ");
        query.append("\n           , ?                ");
        query.append("\n           , to_date(?, 'YYYYMMDDHH24MISS')  ");
        query.append("\n           , ?                ");
        query.append("\n )                            ");
	            	    
	    pstmtInsertLog = con.prepareStatement(query.toString());
	    
	    logger.info("ExecuteLogDAO에서 insertLogPstmt()가 실행되었습니다.");
		logger.debug(String.format("query [%s]",  query));
	}
	
	private void updateLogStartPstmt(Connection con) throws SQLException{ 
        
        StringBuilder query = new StringBuilder();
                
        query.append("\n UPDATE WAA_SHD_LOG                                   ");
        query.append("\n    SET ANA_STS_CD   = ?                              ");
        query.append("\n      , ANA_STR_DTM  = to_date(?, 'YYYYMMDDHH24MISS') ");
        query.append("\n  WHERE ANA_LOG_ID = ?                                ");
                        
        pstmtUpdateLogStart = con.prepareStatement(query.toString());                
    }
	
	private void updateLogPstmt(Connection con) throws SQLException {
	    
	    StringBuilder query = new StringBuilder();
	    
        query.append("\n UPDATE WAA_SHD_LOG                                                                       ");
        query.append("\n    SET ANA_STS_CD  = ?                                                                   ");
        query.append("\n      , ANA_ER_MSG  = SUBSTRB(NVL2(ANA_ER_MSG, ANA_ER_MSG || CHR(10) || ? , ? ), 1, 4000) ");                        
        query.append("\n      , ANA_END_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')                                      ");
        query.append("\n      , ANA_SEC = (TO_DATE(?, 'YYYYMMDDHH24MISS') - ANA_STR_DTM  ) *24*60*60              ");
        query.append("\n  WHERE ANA_LOG_ID = ?                                                                    "); 
        
        pstmtUpdateLog = con.prepareStatement(query.toString());       
	}
	   

	/**
	 * 진행전 로그 insert
	 * @param logId
	 * @param executorDM
	 */
	public void insertLog(String logId, ExecutorDM executorDM) {
		if(logId == null) {
			logger.info("LOG ID 가 null입니다.");
			return;
		}
		
		/*
		StringBuilder query = new StringBuilder();
		query.append("\n INSERT INTO WAA_SHD_LOG (    ");
		query.append("\n             ANA_LOG_ID          ");
		query.append("\n             ,SHD_ID          ");
		query.append("\n             ,SHD_JOB_ID          ");
		query.append("\n             ,ANA_STS_CD     ");
		query.append("\n             ,ANA_USER_ID     ");
		query.append("\n             ,SHD_STR_DTM    ");
		query.append("\n             ,SHD_JOB_NM    ");
		query.append("\n ) VALUES (                   ");
		query.append("\n             ?                ");
		query.append("\n           , ?                ");
		query.append("\n           , ?                ");
		query.append("\n           , ?                ");
		query.append("\n           , ?                ");
		query.append("\n           , to_date(?, 'YYYYMMDDHH24MISS')  ");
		query.append("\n           , ?                ");
		query.append("\n )                            ");

		logger.debug("sql:"+query);
		logger.debug("logid:"+logId);
		logger.debug("exeDM:"+executorDM);
		*/
		
		//Connection con = null;
		//PreparedStatement pstmt = null;
		
		try{
			//con = ConnectionHelper.getDAConnection();
			//pstmt = conn.prepareStatement(query.toString());
			
		    pstmtInsertLog.clearParameters();
		    
			int colIdx = 1;
			String shdId = executorDM.getShdId();
			String shdJobId = executorDM.getShdJobId();
			
			pstmtInsertLog.setString(colIdx++, logId);
			pstmtInsertLog.setString(colIdx++, executorDM.getShdId());
			pstmtInsertLog.setString(colIdx++, executorDM.getShdJobId());
			pstmtInsertLog.setString(colIdx++, Executor.NOT_PROCEED);
			pstmtInsertLog.setString(colIdx++, executorDM.getRqstUserId());
			pstmtInsertLog.setString(colIdx++, executorDM.getShdStaDtm());
			pstmtInsertLog.setString(colIdx++, executorDM.getShdJobNm());
			
			pstmtInsertLog.executeUpdate();
			logger.info("ExecuteLogDAO에서 insertLog()가 실행되었습니다.");
			logger.debug(String.format("ANA_LOG_ID[%s]\nSHD_ID[%s]\nSHD_JOB_ID[%s]", logId, shdId, shdJobId));
			
			conn.commit();
		}catch(SQLException se){
			logger.error(se);
		}catch(Exception e){
			logger.error(e);
		} finally {
			//if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			//if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	/**
	 * 시작시간 update 및 상태를 진행중으로 update
	 * @param logId
	 * @param exeStaDttm
	 */
	public void updateLogStart(String logId, String exeStaDttm) {
		if(logId == null) {
			logger.info("LOG ID 가 null입니다.");
			return;
		}
		
		/*
		StringBuilder query = new StringBuilder();
		query.append("\n UPDATE WAA_SHD_LOG                                   ");
		query.append("\n    SET ANA_STS_CD   = ?                              ");
		query.append("\n      , ANA_STR_DTM  = to_date(?, 'YYYYMMDDHH24MISS') ");
		query.append("\n  WHERE ANA_LOG_ID = ?                                ");

		logger.debug(String.format("query [%s]\nANA_LOG_ID[%s]",  query, logId));
		*/
		
		//Connection con = null;
		//PreparedStatement pstmt = null;
		
		try{
			//con = ConnectionHelper.getDAConnection();
			//pstmt = conn.prepareStatement(query.toString());

		    pstmtUpdateLogStart.clearParameters();
		    
			int colIdx = 1;
			
			pstmtUpdateLogStart.setString(colIdx++, Executor.PROCEED);
			pstmtUpdateLogStart.setString(colIdx++, exeStaDttm);
			pstmtUpdateLogStart.setString(colIdx++, logId);
			
			pstmtUpdateLogStart.executeUpdate();
			logger.info("ExecuteLogDAO에서 updateLogStart()가 실행되었습니다.");
			conn.commit();
		}catch(SQLException se){
			logger.error(se);
		}catch(Exception e){
			logger.error(e);
		} finally {
			//if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			//if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	/**
	 * 상태 변경 로그
	 * @param logId
	 * @param statCd
	 */
	public void updateLog(String logId, String statCd) {
		updateLog(logId, statCd, null, null);
	}
	
	/**
	 * 에러정보 수정 오류
	 * @param logId
	 * @param statCd
	 * @param errMsg
	 * @param errCd
	 */
	public void updateLog(String logId, String statCd, String errMsg, String errCd) {
		if(logId == null) {
			logger.info("LOG ID 가 null입니다.");
			return;
		}
		
		StringBuilder query = new StringBuilder();
		
		query.append("\n UPDATE WAA_SHD_LOG             ");
		query.append("\n    SET                         ");
		query.append("\n        ANA_STS_CD  = ?        ");
		if(errMsg != null) {
			query.append("\n      , ANA_ER_MSG  = SUBSTRB(NVL2(ANA_ER_MSG, ANA_ER_MSG || CHR(10) || ? , ? ), 1, 3500) ");
		}
		if(errCd != null) {
			query.append("\n      , EXE_ERR_CD   = ?        ");
		}
		
		query.append("\n     , ANA_END_DTM = to_date(?, 'YYYYMMDDHH24MISS')                           ");
        query.append("\n     , ANA_SEC = (to_date(?, 'YYYYMMDDHH24MISS') - ANA_STR_DTM  ) *24*60*60   ");
		query.append("\n  WHERE ANA_LOG_ID = ?              ");

		logger.info("ExecuteLogDAO에서 updateLog()가 실행되었습니다.");
		logger.debug(String.format("query [%s]\nANA_LOG_ID[%s]\nSTAT_CD[%s]\nERR_MSG[%s]\nERR_CD[%s]",  query, logId, statCd, errMsg, errCd));

		//Connection con = null;
		//PreparedStatement pstmt = null;
		try{
			//con = ConnectionHelper.getDAConnection();
			//pstmt = conn.prepareStatement(query.toString());

		    pstmtUpdateLog.clearParameters();
		    
			int colIdx = 1;
			
			errMsg = UtilString.null2Blank(errMsg); 
			errCd  = UtilString.null2Blank(errCd);
			
			pstmtUpdateLog.setString(colIdx++, statCd);
			pstmtUpdateLog.setString(colIdx++, errMsg);  
			pstmtUpdateLog.setString(colIdx++, errMsg);			
			pstmtUpdateLog.setString(colIdx++, Utils.getCurrDttm("yyyyMMddHHmmss"));
			pstmtUpdateLog.setString(colIdx++, Utils.getCurrDttm("yyyyMMddHHmmss"));
            
			pstmtUpdateLog.setString(colIdx++, logId);
			
			pstmtUpdateLog.executeUpdate();
			
			conn.commit();
		}catch(SQLException se){
			logger.error(se);
		}catch(Exception e){
			logger.error(e);
		} finally {
			//if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			//if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	public void updateEndLog(String logId) {
		if(logId == null) {
			logger.info("LOG ID 가 null입니다.");
			return;
		}
		StringBuilder query = new StringBuilder();
		query.append("\n UPDATE WAA_SHD_LOG             ");
		query.append("\n    SET ANA_END_DTM = to_date(?, 'YYYYMMDDHH24MISS')  ");
		query.append("\n         ,ANA_SEC = (to_date(?, 'YYYYMMDDHH24MISS') - ANA_STR_DTM  ) *24*60*60   ");
		query.append("\n  WHERE ANA_LOG_ID = ?              ");

		logger.debug(String.format("query [%s]\nANA_LOG_ID[%s]",  query, logId));
		
		//Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			//con = ConnectionHelper.getDAConnection();
			pstmt = conn.prepareStatement(query.toString());

			int colIdx = 1;
			pstmt.setString(colIdx++, Utils.getCurrDttm("yyyyMMddHHmmss"));
			pstmt.setString(colIdx++, Utils.getCurrDttm("yyyyMMddHHmmss"));
			pstmt.setString(colIdx++, logId);
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		}catch(SQLException se){
			logger.error(se);
		}catch(Exception e){
			logger.error(e);
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			//if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	public void updateInterruptLog(String schId, String schStaDttm) {
		StringBuilder query = new StringBuilder();
		query.append("\n UPDATE WAA_SHD_LOG    ");
		query.append("\n    SET                         ");
		query.append("\n        ANA_STS_CD  = '06'     ");
		query.append("\n      , ANA_END_DTM = to_date(?, 'YYYYMMDDHH24MISS')  ");
		query.append("\n  WHERE SHD_ID       = ?        ");
		query.append("\n    AND ANA_STR_DTM = to_date(?, 'YYYYMMDDHH24MISS')   ");
		query.append("\n    AND ANA_END_DTM IS NULL    ");

		logger.debug(query);
		
		//Connection con = null;
		PreparedStatement pstmt = null;
		try{
			//con = ConnectionHelper.getDAConnection();
			pstmt = conn.prepareStatement(query.toString());

			int colIdx = 1;
			pstmt.setString(colIdx++, Utils.getCurrDttm("yyyyMMddHHmmss"));
			pstmt.setString(colIdx++, schId);
			pstmt.setString(colIdx++, schStaDttm);
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		}catch(SQLException se){
			logger.error(se);
		}catch(Exception e){
			logger.error(e);
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			//if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
}
