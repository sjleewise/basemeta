package kr.wise.commons.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import kr.wise.commons.ConnectionHelper;

public class CommOrgMtaDAO {

	/**
	 * Logger for this class
	 */
	
	private static final Logger logger = Logger.getLogger(CommOrgDAO.class);
	
	private Connection con = null;
	private Connection con_tgt = null;
	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int pstmtmtIdx = 1;
	
	public void doCollectionCommOrgMta() throws Exception {
		logger.debug("들어감!!!");
		
		try {
			// DB Connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			// 대상 DB Connection
			con_tgt = ConnectionHelper.getConnection("cubrid.jdbc.driver.CUBRIDDriver","jdbc:CUBRID:10.175.146.100:30000:gdpm:::","dqm","dqm2022#");
			con_tgt.setAutoCommit(false);
			
			// 데이터 insert
			insertCommOrgMta(con,con_tgt);
			updateCommOrgMta(con);
			
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con_tgt.rollback();
		} finally {
			if(rs    != null) {
				try { rs.close(); } catch(Exception igonred) {}
			}
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
				try { con_tgt.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	public void insertCommOrgMta(Connection con, Connection con_tgt) throws SQLException {
		
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE FROM DATAMGMT.V_INF_DQM_MTA ");
		
		StringBuilder selectSQL = new StringBuilder();
		StringBuilder insertSQL = new StringBuilder();
		StringBuilder updateSQL = new StringBuilder();
		
		// 가져올 데이터 쿼리
		selectSQL.append("\n SELECT                    ");
		selectSQL.append("\n        ORG_NM 			   ");
		selectSQL.append("\n      , INFO_SYS_NM        ");
		selectSQL.append("\n      , DB_CONN_TRG_PNM    ");
		selectSQL.append("\n      , DB_CONN_TRG_LNM    ");
		selectSQL.append("\n      , DBMS_TYP           ");
		selectSQL.append("\n      , DBMS_VER           ");
		selectSQL.append("\n      , DB_SCH_NM          ");
		selectSQL.append("\n      , MTA_TBL_PNM        ");
		selectSQL.append("\n      , MTA_TBL_LNM        ");
		selectSQL.append("\n      , MTA_COL_PNM        ");
		selectSQL.append("\n      , MTA_COL_LNM        ");
		selectSQL.append("\n      , DATA_TYPE          ");
		selectSQL.append("\n      , DATA_LEN           ");
		selectSQL.append("\n      , NONUL_YN           ");
		selectSQL.append("\n      , PK_YN              ");
		selectSQL.append("\n   FROM V_INF_DQM_MTA 	   ");
		
		// 데이터 입력 쿼리
		insertSQL.append("\nINSERT INTO DATAMGMT.V_INF_DQM_MTA (");
	    insertSQL.append("\n  ORG_NM			");
	    insertSQL.append("\n ,INFO_SYS_NM		");                               
	    insertSQL.append("\n ,DB_CONN_TRG_PNM   ");                                 
	    insertSQL.append("\n ,DB_CONN_TRG_LNM   ");                             
	    insertSQL.append("\n ,DBMS_TYP          ");                  
	    insertSQL.append("\n ,DBMS_VER  	 	");                    
	    insertSQL.append("\n ,DB_SCH_NM      	");            
	    insertSQL.append("\n ,MTA_TBL_PNM    	");          
	    insertSQL.append("\n ,MTA_TBL_LNM       ");
	    insertSQL.append("\n ,MTA_COL_PNM       ");                            
	    insertSQL.append("\n ,MTA_COL_LNM 		");                                   
	    insertSQL.append("\n ,DATA_TYPE  		");                                  
	    insertSQL.append("\n ,DATA_LEN          ");                           
	    insertSQL.append("\n ,NONUL_YN      	");                              
	    insertSQL.append("\n ,PK_YN      		");                           
		insertSQL.append("\n) VALUES (          ");
		insertSQL.append("\n  ?,?,?,?,?,        ");
		insertSQL.append("\n  ?,?,?,?,?,        ");
		insertSQL.append("\n  ?,?,?,?,?         ");
		insertSQL.append("\n)                   ");
		
		try {
			
			//코드분류체계 삭제
			pstmt = null;
			pstmt = con.prepareStatement(deleteSQL.toString());
			pstmt.executeUpdate();
	//
	//		con.commit();
	//		
			// 대상DB 코드분류체계 조회
			pstmt = null;
			rs = null;
			pstmt = con_tgt.prepareStatement(selectSQL.toString());
			rs = pstmt.executeQuery();
	
			logger.debug("확인용");
			logger.debug(rs.toString());
			
			// meta rep db insert
			pstmt = null;
			pstmt = con.prepareStatement(insertSQL.toString());
			
			logger.debug("확인용2");
			
			while (rs.next()) {
				pstmtmtIdx = 1;
				pstmt.setString(pstmtmtIdx++, rs.getString("ORG_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("INFO_SYS_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DB_CONN_TRG_PNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DB_CONN_TRG_LNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DBMS_TYP"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DBMS_VER"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DB_SCH_NM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("MTA_TBL_PNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("MTA_TBL_LNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("MTA_COL_PNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("MTA_COL_LNM"));
				pstmt.setString(pstmtmtIdx++, rs.getString("DATA_TYPE"));
				pstmt.setInt(pstmtmtIdx++, Integer.parseInt(rs.getString("DATA_LEN")));
				pstmt.setString(pstmtmtIdx++, rs.getString("NONUL_YN"));
				pstmt.setString(pstmtmtIdx++, rs.getString("PK_YN"));
				pstmt.addBatch();
			}
			
			logger.debug("확인용3");
			
			pstmt.executeBatch();
			
			con.commit();
			
			//Batch 초기화
	        pstmt.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
            
            //실패시 rollback 해줘야함
            try {
                con_tgt.rollback();                
            }catch(Exception e1) {
                e1.printStackTrace();
            }
		} finally {
            if(pstmt != null){pstmt.close(); pstmt = null;}
            if(con_tgt != null){con_tgt.close(); con_tgt = null;}
		}
	}
	
	public void updateCommOrgMta(Connection con) throws SQLException {
		
		StringBuffer updateSQL = new StringBuffer();
		
		// 데이터 수정 쿼리
		updateSQL.append("\nUPDATE INF_DQM_MTA C");
		updateSQL.append("\n   SET (DB_CONN_TRG_ID, DB_SCH_ID) = (");
		updateSQL.append("\nSELECT B.DB_CONN_TRG_ID, B.DB_SCH_ID");
		updateSQL.append("\n  FROM WAA_DB_CONN_TRG A");
		updateSQL.append("\n INNER JOIN WAA_DB_SCH B ");
		updateSQL.append("\n    ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID");
		updateSQL.append("\n   AND B.REG_TYP_CD IN ('C','U')");
		updateSQL.append("\n   AND B.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD') ");
		updateSQL.append("\n WHERE A.REG_TYP_CD IN ('C','U')");
		updateSQL.append("\n   AND A.EXP_DTM = TO_DATE('9999-12-31','YYYY-MM-DD')");
		updateSQL.append("\n   AND A.DB_CONN_TRG_PNM = C.DB_CONN_TRG_PNM");
		updateSQL.append("\n   AND B.DB_SCH_PNM = C.DB_SCH_NM)");
		updateSQL.append("\n WHERE 1=1");
		
		try {
			
			//코드분류체계 삭제
			pstmt = null;
			pstmt = con.prepareStatement(updateSQL.toString());
			pstmt.executeUpdate();
			
//			pstmt.executeBatch();
			
			con.commit();
			
			//Batch 초기화
//	        pstmt.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
            
            //실패시 rollback 해줘야함
            try {
                con.rollback();                
            }catch(Exception e1) {
                e1.printStackTrace();
            }
		} finally {
            if(pstmt != null){pstmt.close(); pstmt = null;}
            if(con != null){con.close(); con = null;}
		}
	}
}
