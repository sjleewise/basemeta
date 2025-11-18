package kr.wise.executor.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.BusinessRuleErrorDataDM;
import kr.wise.executor.dm.BusinessRuleResultDM;
import kr.wise.executor.dm.ExecutorDM;

import org.apache.log4j.Logger;

public class BusinessRuleDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BusinessRuleDAO.class);
	
	public Map<String, String> selectBusinessRuleInfo(String braId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT                                            ");
		query.append("\n        DB_CONN_TRG_ID ");
		query.append("\n      , DBC_COL_NM                                     ");
		query.append("\n      , CNT_SQL                                    ");
		query.append("\n      , ER_CNT_SQL                                ");
		query.append("\n      , ANA_SQL                                    ");
		query.append("\n   FROM WAM_BR_MSTR                                ");
		query.append("\n  WHERE BR_ID = ? ");
		query.append("\n     AND REG_TYP_CD IN ('C', 'U')                    ");
		
		
		logger.debug(query);

		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, braId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("DBMS_ID", rs.getString("DB_CONN_TRG_ID"));
				map.put("COL_NM", rs.getString("DBC_COL_NM"));
				map.put("CNT_SQL", rs.getString("CNT_SQL"));
				map.put("ERR_CNT_SQL", rs.getString("ER_CNT_SQL"));
				map.put("ANA_SQL", rs.getString("ANA_SQL"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	/** 검증대상 BR정보 조회 */
	public Map<String, String> selectBusinessRuleInfoTarget(String braId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT                                            ");
		query.append("\n        TGT_DB_CONN_TRG_ID                                    ");
		query.append("\n      , TGT_DBC_COL_NM                                     ");
		query.append("\n      , TGT_CNT_SQL                                    ");
		query.append("\n      , TGT_ER_CNT_SQL                                ");
		query.append("\n      , TGT_ANA_SQL                                    ");
		query.append("\n      , TGT_KEY_DBC_COL_NM                                    ");
		query.append("\n      , TGT_KEY_DBC_COL_VAL                                    ");
		query.append("\n      , TGT_VRF_JOIN_CD                                    ");
		query.append("\n   FROM WAM_BR_MSTR                                ");
		query.append("\n  WHERE BR_ID = ? ");
		query.append("\n     AND REG_TYP_CD IN ('C', 'U')                    ");
		
		      
		
		logger.debug(query);
		
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, braId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("TGT_DBMS_ID", rs.getString("TGT_DB_CONN_TRG_ID"));
				map.put("TGT_COL_NM", rs.getString("TGT_DBC_COL_NM"));
				map.put("TGT_CNT_SQL", rs.getString("TGT_CNT_SQL"));
				map.put("TGT_ERR_CNT_SQL", rs.getString("TGT_ER_CNT_SQL"));
				map.put("TGT_ANA_SQL", rs.getString("TGT_ANA_SQL"));
				map.put("TGT_KEY", rs.getString("TGT_KEY_DBC_COL_NM"));
				map.put("TGT_VAL", rs.getString("TGT_KEY_DBC_COL_VAL"));
				map.put("TGT_VRF_JOIN_CD", rs.getString("TGT_VRF_JOIN_CD"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}


	public void saveBusinessRuleResult(String logId, String staDttm, String endDttm, ExecutorDM executorDM, BusinessRuleResultDM brReulstDm, BusinessRuleErrorDataDM brErrorDataDM) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder query = null;
		try {
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);

			String brId = executorDM.getShdJobId();
			BigDecimal anaDgr = executorDM.getAnaDgr();
			
			if(anaDgr != null) {
				// 삭제할 차수의 ANA_STR_DTM 값을 구한다.
				pstmt = con.prepareStatement(" SELECT TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS  ANA_STR_DTM FROM WAM_BR_RESULT WHERE BR_ID = ? AND ANA_DGR = ? ");
				pstmt.setString(1, brId);
				pstmt.setBigDecimal(2, anaDgr);
				rs = pstmt.executeQuery();
				List<String> prevStaDttmList = new ArrayList<String>();
				while(rs.next()) {
					prevStaDttmList.add(rs.getString("ANA_STR_DTM"));
				}
				if(rs != null) try { rs.close(); } catch(Exception igonred) {}
				pstmt.close();
				pstmt = null;
				
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_BR_ERR_DATA WHERE BR_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS') ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, brId);
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;

				pstmt = con.prepareStatement(" DELETE FROM WAM_BR_RESULT WHERE BR_ID = ? AND ANA_DGR = ? ");
				pstmt.setString(1, brId);
				pstmt.setBigDecimal(2, anaDgr);
				pstmt.executeUpdate();
				pstmt.close();
				
				logger.debug("차수 데이터 삭제 완료!!");
			}
			
			else{
				// 삭제할 업무규칙의 ANA_STR_DTM 값을 구한다.
				pstmt = con.prepareStatement(" SELECT TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS  ANA_STR_DTM  FROM WAM_BR_RESULT WHERE BR_ID = ? AND ANA_DGR IS NULL  ");
				pstmt.setString(1, brId);
				rs = pstmt.executeQuery();
				List<String> prevStaDttmList = new ArrayList<String>();
				while(rs.next()) {
					prevStaDttmList.add(rs.getString("ANA_STR_DTM"));
				}
				if(rs != null) try { rs.close(); } catch(Exception igonred) {}
				pstmt.close();
				pstmt = null;
				
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_BR_ERR_DATA WHERE BR_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS') ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, brId);
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;

				pstmt = con.prepareStatement(" DELETE FROM WAM_BR_RESULT WHERE BR_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, brId);
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				
				logger.debug(" 차수 미존재 데이터 삭제 완료!!");
			}
			
			
			pstmt = null;
			query = new StringBuilder();
			
			// 업무규칙 결과 저장
			query.append("\n INSERT INTO WAM_BR_RESULT ( ");
			query.append("\n          BR_ID ");
			query.append("\n        , ANA_STR_DTM  ");
			query.append("\n        , ANA_END_DTM ");
			query.append("\n        , ANA_DGR ");
			query.append("\n        , ANA_CNT ");
			query.append("\n        , ER_CNT ");
			query.append("\n        , ANA_LOG_ID ");
			query.append("\n        , ANA_USER_ID ");
			query.append("\n )  VALUES ( ");
			query.append("\n          ? --BR_ID ");
			query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM  ");
			query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_END_DTM ");
			query.append("\n        , ? --ANA_DGR ");
			query.append("\n        , ? --ANA_CNT ");
			query.append("\n        , ? --ER_CNT ");
			query.append("\n        , ? --ANA_LOG_ID ");
			query.append("\n        , ? --ANA_USER_ID ");
			query.append("\n  ) ");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, brId);
			pstmt.setString(2, staDttm);
			pstmt.setString(3, endDttm);
			pstmt.setBigDecimal(4,  anaDgr);
			pstmt.setLong(5, brReulstDm.getTotCnt());
			pstmt.setLong(6, brReulstDm.getErrCnt());
			pstmt.setString(7, logId);
			pstmt.setString(8, executorDM.getRqstUserId());
			
			pstmt.executeUpdate();
			pstmt.close();
			
			if(brErrorDataDM != null || !Utils.null2Blank(executorDM.getErDataLdYn()).equals("N")) {
				// 오류패턴 저장
				logger.debug("=== start insert bizrule errdata ===");
				//컬럼명 저장
				//컬럼건수
				int colCnt =0;
				if(brErrorDataDM.getColumnNames() != null){
					colCnt = brErrorDataDM.getColumnNames().length;
				}
				StringBuilder insertColNm = new StringBuilder();
				StringBuilder insertColNmVal = new StringBuilder();
				StringBuilder insertColNmIdx = new StringBuilder();
				
				for(int i=0; i<colCnt; i++){
					insertColNm.append("\n, COL_NM").append( String.valueOf(i+1) );
					insertColNmVal.append("\n, '").append(brErrorDataDM.getColumnNames()[i]).append("'");
					insertColNmIdx.append("\n, ?");
				}
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_BR_ERR_DATA ( ");
				query.append("\n        BR_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --BR_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , 0 --ER_DATA_SNO ");
				query.append("\n        , ").append(colCnt).append(" --COL_CNT ");
				query.append(insertColNmVal.toString());
				query.append("\n  ) ");
				
				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, executorDM.getShdJobId());
				pstmt.setString(2, staDttm);
				
				pstmt.executeUpdate();
				
				//데이터 패턴 저장
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_BR_ERR_DATA ( ");
				query.append("\n        BR_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				//컬럼명 셋팅
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --BR_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , ? --ER_DATA_SNO ");
				query.append("\n        , ? --COL_CNT ");
				//INSERT SQL INDEX 셋팅
				query.append(insertColNmIdx.toString());
				query.append("\n  ) ");
				
				pstmt = con.prepareStatement(query.toString());
				
				
				for(int j=0; j<brErrorDataDM.getPatternList().size(); j++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, staDttm);
					pstmt.setInt(3, j+1);
					pstmt.setInt(4, colCnt);
					
					int pstmtIdx = 5;
					for(int i=0; i<brErrorDataDM.getColumnNames().length; i++){
						pstmt.setString(pstmtIdx, brErrorDataDM.getPatternList().get(j).get(brErrorDataDM.getColumnNames() [i]));
						pstmtIdx++;
					}
					pstmt.executeUpdate();
					
					//오류데이터 적재건수 와 현재적재건수가 동일하면 break 
					if(  Integer.toString((j+1)).equals(Utils.null2Blank(executorDM.getErDataLdCnt())) ){
						break;
					}
				}
			}
			
			con.commit();
			
		} catch(SQLException sqle) {
			logger.error(sqle);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw sqle;
		} catch(Exception e) {
			logger.error(e);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	public BusinessRuleResultDM executeCountQueryOfTargetDbms(Connection con, String query) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BusinessRuleResultDM brReulstDm = new BusinessRuleResultDM();
		int rsCnt = 0;
		try {
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rsCnt == 0){
					brReulstDm.setTotCnt(rs.getLong(1));
				}else{
					brReulstDm.setErrCnt(rs.getLong(1));
				}
				rsCnt++;
			}
			
//			while(rs.next()) {
//				brReulstDm.setTotCnt(rs.getLong(1));
//			}
			
			return brReulstDm;
			
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("CountQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("CountQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}
	
	public BusinessRuleErrorDataDM executeAnalysisQueryOfTargetDbms(Connection con, String query) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BusinessRuleErrorDataDM businessRuleErrorDataDM = null;
		try {
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			int colCnt = rs.getMetaData().getColumnCount();
			String [] columnNames = new String [colCnt];
			for(int i=0; i<colCnt; i++) {
				columnNames [i] = rs.getMetaData().getColumnLabel(i+1).trim();
			}
			List<Map<String, String>> patternList = new ArrayList<Map<String, String>>();
			int n = 0;
			while(rs.next()) {
				n = 0;
				Map<String, String> colMap = new HashMap<String, String>();
				for(int i=0; i<colCnt; i++) {
					colMap.put(columnNames [n++], rs.getString(columnNames [i]));
				}
				patternList.add(colMap);
			}

			businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
			businessRuleErrorDataDM.setColumnNames(columnNames);
			businessRuleErrorDataDM.setPatternList(patternList);
			
			return businessRuleErrorDataDM;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("AnalysisQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("AnalysisQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}
	
	public List<Map<String, String>> getSystemVarVal(String columnNm) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT A.VAR_NM, A.VAR_VAL ");
		query.append("\n   FROM WDQ_BRA_VARIABLE A  ");
		query.append("\n  WHERE A.VAR_TYPE = 'S'    ");
		query.append("\n    AND A.XPR_DTTM = TO_DATE('99991231', 'YYYYMMDD')   ");
		query.append("\n    AND A.ORGL_TYPE IN ('C', 'U')   ");
		query.append("\n  ORDER BY A.GLOV_VAR_ID   ");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			
			rs = pstmt.executeQuery();
			Map<String, String> tmp = null;
			while(rs.next()) {
				tmp = new HashMap<String, String>();
				if("SYS_COND_DMN_VV".equals(rs.getString("VAR_NM"))){
					tmp.put("SYS_COND_DMN_VV", getMetaDmnVv(columnNm));
				} else {
					String sysVarVal = rs.getString("VAR_VAL");
					if(sysVarVal != null && !"".equals(sysVarVal)) {
						sysVarVal = sysVarVal.replaceAll("\\$SYS_VAL_COL_NM", columnNm);
						tmp.put(rs.getString("VAR_NM"), sysVarVal);
					}
				}
				list.add(tmp);
			}
			return list;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	public List<Map<String, String>> getGlovalVarVal() throws SQLException, Exception {

		StringBuilder query = new StringBuilder();
		query.append("\n SELECT A.VAR_NM, A.VAR_VAL   ");
		query.append("\n   FROM WDQ_BRA_VARIABLE A    ");
		query.append("\n  WHERE A.VAR_TYPE = 'G'      ");
		query.append("\n    AND A.XPR_DTTM = TO_DATE('99991231', 'YYYYMMDD')   ");
		query.append("\n    AND A.ORGL_TYPE IN ('C', 'U') ");
		query.append("\n  ORDER BY A.GLOV_VAR_ID   ");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			
			rs = pstmt.executeQuery();
			Map<String, String> tmp = null;
			while(rs.next()) {
				tmp = new HashMap<String, String>();
				tmp.put(rs.getString("VAR_NM"), rs.getString("VAR_VAL"));

				list.add(tmp);
			}
			return list;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	private String getMetaDmnVv(String columnNm) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT A.ITEM_ENM, A.DMN_ID, B.DMN_ENM, C.VALID_VAL ");
		query.append("\n   FROM V_DQ_DATAELMT A, V_DQ_CODE_DOMAIN B, V_DQ_DOMAIN_VV C  ");
		query.append("\n  WHERE A.DMN_ID = B.DMN_ID   ");
		query.append("\n    AND B.DMN_ID = C.DMN_ID   ");
		query.append("\n    AND A.ITEM_ENM = ?   ");
		query.append("\n  ORDER BY C.DISP_ORD   ");
		
		logger.debug(query);

		StringBuilder strReturn = new StringBuilder();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getMetaConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, columnNm);
			
			rs = pstmt.executeQuery();
			int i = 0;
			while(rs.next()) {
				if(i == 0) strReturn.append(" ").append(columnNm).append(" <> ").append(" '").append(rs.getString("VALID_VAL")).append("'");
				else strReturn.append("\n AND ").append(columnNm).append(" <> ").append(" '").append(rs.getString("VALID_VAL")).append("'");
				i++;
			}
			return strReturn.toString();
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	
	
	/*public void saveBusinessRuleResult(String logId, String braId, BigDecimal braExeNum, String staDttm, String endDttm, String baseDttm, long anaCnt, long errCnt, BusinessRuleErrorDataDM brErrorDataDM) throws SQLException, Exception {
	brDAO.saveBusinessRuleResult(logId, executorDM.getShdJobId(), executorDM.getAnaDgr(), exeStaDttm, endDttm, executorDM.getShdStaDtm(), brReulstDm.getTotCnt(), brReulstDm.getErrCnt(), brErrorDataDM);
	brDAO.saveBusinessRuleResult(logId, exeStaDttm, endDttm, executorDM,  brReulstDm, brErrorDataDM);*/
	
	public void saveBizRullResultTgt(String logId, String braId, BigDecimal braExeNum, String staDttm, String endDttm, String baseDttm, 
			long anaCnt, long errCnt, BusinessRuleErrorDataDM brErrorDataDM) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder query = null;
		try {
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);

			if(braExeNum != null) {
				// 삭제할 차수의 ANA_STR_DTM 값을 구한다.
				pstmt = con.prepareStatement(" SELECT ANA_STR_DTM FROM WAM_BR_RESULT WHERE BR_ID = ? AND ANA_DGR = ? ");
				pstmt.setString(1, braId);
				pstmt.setBigDecimal(2, braExeNum);
				rs = pstmt.executeQuery();
				List<java.sql.Date> prevStaDttmList = new ArrayList<java.sql.Date>();
				while(rs.next()) {
					prevStaDttmList.add(rs.getDate(1));
				}
				if(rs != null) try { rs.close(); } catch(Exception igonred) {}
				pstmt.close();
				pstmt = null;
				
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_BR_ERR_DATA_TGT WHERE BR_ID = ? AND ANA_STR_DTM = ? ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, braId);
					pstmt.setDate(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;

				logger.debug("검증대상 차수 데이터 삭제 완료!!");
			}
			
			pstmt = null;
			query = new StringBuilder();
			
			
			query.append("\n UPDATE WAM_BR_RESULT SET ANA_END_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS') ");
			query.append("\n WHERE BR_ID = ? ");
			query.append("\n AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS') ");
			query.append("\n AND LOG_ID = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, endDttm);
			pstmt.setString(2, braId);
			pstmt.setString(3, staDttm);
			pstmt.setString(4, logId);
			pstmt.executeUpdate();
			pstmt.close();
			
			if(brErrorDataDM != null) {
				
				//컬럼명 저장
				//컬럼건수
				int colCnt = brErrorDataDM.getColumnNames().length;
				StringBuilder insertColNm = new StringBuilder();
				StringBuilder insertColNmVal = new StringBuilder();
				StringBuilder insertColNmIdx = new StringBuilder();
				
				for(int i=0; i<brErrorDataDM.getColumnNames().length; i++){
					insertColNm.append("\n, COL_NM").append( String.valueOf(i+1) );
					insertColNmVal.append("\n, '").append(brErrorDataDM.getColumnNames()[i]).append("'");
					insertColNmIdx.append("\n, ?");
				}
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_BR_ERR_DATA_TGT ( ");
				query.append("\n        BR_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --BR_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , 0 --ER_DATA_SNO ");
				query.append("\n        , ").append(colCnt).append(" --COL_CNT ");
				query.append(insertColNmVal.toString());
				query.append("\n  ) ");
//				logger.debug(query.toString());
				
				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, braId);
				pstmt.setString(2, staDttm);
				pstmt.executeUpdate();
				
				//데이터 패턴 저장
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_BR_ERR_DATA_TGT ( ");
				query.append("\n        BR_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				//컬럼명 셋팅
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --BR_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , ? --ER_DATA_SNO ");
				query.append("\n        , ? --COL_CNT ");
				//INSERT SQL INDEX 셋팅
				query.append(insertColNmIdx.toString());
				query.append("\n  ) ");
				
				pstmt = con.prepareStatement(query.toString());
				
				int pstmtIdx = 5;
				for(int j=0; j<brErrorDataDM.getPatternList().size(); j++) {
					pstmt.setString(1, braId);
					pstmt.setString(2, staDttm);
					pstmt.setInt(3, j+1);
					pstmt.setInt(4, colCnt);
					
					for(int i=0; i<brErrorDataDM.getColumnNames().length; i++){
						pstmt.setString(pstmtIdx+i, brErrorDataDM.getPatternList().get(j).get(brErrorDataDM.getColumnNames() [i]));
					}
//					logger.debug(query.toString());
					pstmt.executeUpdate();
				}
			}
			
			con.commit();
		} catch(SQLException sqle) {
			logger.error(sqle);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw sqle;
		} catch(Exception e) {
			logger.error(e);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}

	/** 업무규칙 쿼리를 실행한 결과를 List<Map> 형태로 리턴한다. */
	public List<Map<String, String>> executeBraQuery(Connection con,	String query) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		try {
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			result = getResultMapRows(rs);
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("AnalysisQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
		return result;
	}
	
	/**
     * ResultSet을 Row마다 Map에 저장후 List에 다시 저장.
     * @param rs DB에서 가져온 ResultSet
     * @return Listt<map> 형태로 리턴
     * @throws Exception Collection
     */
    private List<Map<String, String>> getResultMapRows(ResultSet rs) throws Exception
    {
        // ResultSet 의 MetaData를 가져온다.
        ResultSetMetaData metaData = rs.getMetaData();
        // ResultSet 의 Column의 갯수를 가져온다.
        int sizeOfColumn = metaData.getColumnCount();
 
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        String column;
        // rs의 내용을 돌려준다.
        while (rs.next())
        {
            // 내부에서 map을 초기화
            map = new HashMap<String, String>();
            // Column의 갯수만큼 회전
            for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++)
            {
                column = metaData.getColumnName(indexOfcolumn + 1);
                // map에 값을 입력 map.put(columnName, columnName으로 getString)
                map.put(column, rs.getString(column));
            }
            // list에 저장
            list.add(map);
        }
        return list;
    }

}
