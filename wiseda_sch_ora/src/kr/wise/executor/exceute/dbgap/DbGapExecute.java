package kr.wise.executor.exceute.dbgap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.executor.Executor;
import kr.wise.executor.dm.ExecutorDM;

import org.apache.log4j.Logger;

public class DbGapExecute extends Executor {
	private static final Logger logger = Logger.getLogger(DbGapExecute.class);
	
	public DbGapExecute(String logId, ExecutorDM executorDM){
		super(logId, executorDM);
	}
	
	protected Boolean execute() throws SQLException, Exception {
		try {
			//DB간 MAP 정보 조회
			Map<String, String> dbgapInfo = selectDbGapInfo(executorDM.getShdJobId());

			// DBC 정보 저장 및 GAP 분석
			DbGapManager dbgapRun = new DbGapManager(dbgapInfo);
			dbgapRun.launch();

		} catch(SQLException e) {
			logger.error(e);
			throw e;
		} catch(Exception e) {
			logger.error(e);
			throw e;
		}
		return new Boolean(true);
	}
	
	
	//DB간 MAP 정보 조회
	public Map<String, String> selectDbGapInfo(String shdJobId) throws SQLException, Exception {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT SRC_DB_SCH_ID, TGT_DB_SCH_ID ");
		strSQL.append("\n   FROM WAA_DB_MAP ");
		strSQL.append("\n  WHERE DB_MAP_ID = ? ");
		strSQL.append("\n    AND EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    AND REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n    AND NVL(DDL_TSF_YN, 'N') = 'Y' ");

		
		logger.debug(strSQL+"\n" + "shdJobId ["+shdJobId+"]");

		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, shdJobId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("SRC_DB_SCH_ID", rs.getString("SRC_DB_SCH_ID"));
				map.put("TGT_DB_SCH_ID", rs.getString("TGT_DB_SCH_ID"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	
	
}
