package kr.wise.meta.kats.CodeCfcSys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class CodeDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CodeDAO.class);

	private Connection con = null;
	private Connection tgtCon = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int pstmtIdx = 1;
	
	public void doProcess(TargetDbmsDM trgDM) throws SQLException, Exception {

		try {
			//meta rep db connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			//대상 DB connection
			tgtCon = ConnectionHelper.getConnection(trgDM.getJdbc_driver(), trgDM.getConnect_string(), trgDM.getDb_user(), trgDM.getDb_pwd());
			tgtCon.setAutoCommit(false);
			
			//코드도메인 meta rep insert
			insertWatCode(con, tgtCon, trgDM);
			
			//코드도메인 유효값 meta rep insert
			insertWatCodeVal(con, tgtCon, trgDM);
			
			con.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
			tgtCon.rollback();
		}finally {
			if(rs    != null) {
				try { rs.close(); } catch(Exception igonred) {}
			}
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
				try { tgtCon.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	
	public void insertWatCode(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE WAT_CODE ");
		deleteSQL.append("\n WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		deleteSQL.append("\n    AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		
		//접속대상 공통코드 테이블 조회
		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("\n SELECT ");
		selectSQL.append("\n		 RTRIM(LTRIM(A.CODE_ENG_GROUP_NM)) AS CODE_PNM        --    VARCHAR2 (200) NOT NULL, ") ;                                              
		selectSQL.append("\n		,RTRIM(LTRIM(A.CODE_GROUP_NM)) AS CODE_LNM        --    VARCHAR2 (200), ") ;                                                       
		selectSQL.append("\n		,RTRIM(LTRIM(A.CODE_GROUP_ID)) AS CODE_ID         --    VARCHAR2 (200), ") ;       
		selectSQL.append("\n		,RTRIM(LTRIM(A.CODE_GROUP_SE_CODE)) AS CODE_TYP_CD         --    VARCHAR2 (200), ") ;       
		selectSQL.append("\n		,RTRIM(LTRIM(A.TABLE_NM)) AS CODE_TBL        --    VARCHAR2 (200), ") ;                                                       
		selectSQL.append("\n		,NULL AS CODE_COL        --    VARCHAR2 (200), ") ;                                                       
		selectSQL.append("\n		,A.CODE_GROUP_DC AS CODE_DESCN      --    VARCHAR2 (4000), ") ;                                                      
		selectSQL.append("\n		,B.CODE_VAL_CNT AS CODE_EACNT      --    NUMBER (22), ") ;                                                          
		selectSQL.append("\n	    ,A.REGIST_DT AS REG_DTM            -- DATE, ") ;
		selectSQL.append("\n	    ,A.UPDT_DT AS UPD_DTM            -- DATE, ") ;                                                         
		selectSQL.append("\n  FROM TCM_CODE_GROUP_C A ");
		selectSQL.append("\n          LEFT OUTER JOIN (SELECT CODE_GROUP_ID   , COUNT(DETAIL_CODE_ID) AS CODE_VAL_CNT  ") ;
		selectSQL.append("\n                                  FROM TCM_CODE_DETAIL_C  ") ;
		selectSQL.append("\n                                 GROUP BY CODE_GROUP_ID ) B ") ;
		selectSQL.append("\n          ON A.CODE_GROUP_ID = B.CODE_GROUP_ID ") ;

		
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("\n INSERT INTO WAT_CODE ( ") ;
		insertSQL.append("\n		DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSQL.append("\n		,DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ;                                               
		insertSQL.append("\n		,CODE_PNM        --    VARCHAR2 (200) NOT NULL, ") ;                                              
		insertSQL.append("\n		,CODE_LNM        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,CODE_ID         --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,CODE_TYP_CD         --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,CODE_TBL        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,CODE_COL        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,CODE_DESCN      --    VARCHAR2 (4000), ") ;                                                      
		insertSQL.append("\n		,CODE_EACNT      --    NUMBER (22), ") ;                                                          
		insertSQL.append("\n		,REG_DTM         --    DATE, ") ;                                                          
		insertSQL.append("\n		,UPD_DTM         --    DATE, ") ;                                                          
//		insertSQL.append("\n		,CODE_EXTNC_EXS      -- VARCHAR2 (1), ") ;                                                          
//		insertSQL.append("\n		,DMN_EXTNC_EXS      -- VARCHAR2 (1) ") ;                                                          
		insertSQL.append("\n )VALUES( ") ;
		insertSQL.append("\n		 ? -- DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSQL.append("\n		,? -- DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ;                                               
		insertSQL.append("\n		,? -- CODE_PNM        --    VARCHAR2 (200) NOT NULL, ") ;                                              
		insertSQL.append("\n		,? -- CODE_LNM        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,? -- CODE_ID         --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,? -- CODE_TYP_CD         --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,? -- CODE_TBL        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,? -- CODE_COL        --    VARCHAR2 (200), ") ;                                                       
		insertSQL.append("\n		,? -- CODE_DESCN      --    VARCHAR2 (4000), ") ;                                                      
		insertSQL.append("\n		,? -- CODE_EACNT      --    NUMBER (22), ") ;                                                          
		insertSQL.append("\n		,? -- REG_DTM         --    DATE, ") ;                                                          
		insertSQL.append("\n		,? -- UPD_DTM         --    DATE, ") ;                                                          
//		insertSQL.append("\n		,'Y' ") ;                                                           
//		insertSQL.append("\n		,'N' ") ;                                                           
		insertSQL.append("\n  ) ") ;
		
		//코드분류체계 삭제
		pstmt = null;
		pstmt = con.prepareStatement(deleteSQL.toString());
		pstmt.executeUpdate();
		
		//대상DB 코드분류체계 조회
		pstmt = null;
		rs = null;
		pstmt = tgtCon.prepareStatement(selectSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		pstmt = null;
		pstmt = con.prepareStatement(insertSQL.toString());
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt.setString(pstmtIdx++, trgDM.getDbms_id());
			pstmt.setString(pstmtIdx++, trgDM.getDbSchId());
			pstmt.setString(pstmtIdx++, rs.getString("CODE_PNM"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_LNM"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_TYP_CD"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_TBL"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_COL"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_DESCN"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CODE_EACNT"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REG_DTM"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPD_DTM"));
			pstmt.addBatch();
		}
		pstmt.executeBatch();
	}
	
	public void insertWatCodeVal(Connection con, Connection tgtCon, TargetDbmsDM trgDM) throws SQLException{
		StringBuffer deleteSQL = new StringBuffer();
		deleteSQL.append("\n DELETE WAT_CODE_VAL ") ;
		deleteSQL.append("\n WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		deleteSQL.append("\n    AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		
		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("\n SELECT ");
		selectSQL.append("\n	         RTRIM(LTRIM(B.CODE_ENG_GROUP_NM)) AS CODE_PNM            --VARCHAR2 (200) NOT NULL, ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.DETAIL_ENG_CODE_NM)) AS CODE_VAL_PNM       -- VARCHAR2 (200) NOT NULL, ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.CODE_GROUP_ID)) AS CODE_ID            -- VARCHAR2 (200), ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.DETAIL_CODE_NM)) AS CODE_VAL_LNM       -- VARCHAR2 (200), ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.DETAIL_CODE_ID)) AS CODE_VAL        -- VARCHAR2 (200), ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.DETAIL_CODE_ID)) AS CODE_VAL_ID        -- VARCHAR2 (200), ") ;
		selectSQL.append("\n	        ,A.SORT_ORDR AS CODE_VAL_ORD       -- NUMBER, ") ;
		selectSQL.append("\n	        ,A.USE_AT AS CODE_VAL_USE_YN    -- VARCHAR2 (1), ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.DETAIL_CODE_DC)) AS CODE_VAL_DESCN     -- VARCHAR2 (4000), ") ;
		selectSQL.append("\n	        ,RTRIM(LTRIM(A.ESTN_COLUMN_NM)) AS ESTN_COL           -- VARCHAR2 (200), ") ;
		selectSQL.append("\n	        ,NULL AS VERS               -- NUMBER (5), ") ;
		selectSQL.append("\n	        ,NULL AS REG_TYP            -- VARCHAR2 (5), ") ;
		selectSQL.append("\n	        ,A.REGIST_DT AS REG_DTM            -- DATE, ") ;
		selectSQL.append("\n	        ,A.UPDT_DT AS UPD_DTM            -- DATE, ") ;
		selectSQL.append("\n	        ,NULL AS DMN_ID          -- VARCHAR2 (15), ") ; 
		selectSQL.append("\n	        ,NULL AS CD_VAL_ID          --VARCHAR2 (15), ") ;
		selectSQL.append("\n	        ,NULL AS CODE_VAL_ERR_EXS   --  VARCHAR2 (1), ") ;
		selectSQL.append("\n	        ,NULL AS CODE_VAL_ERR_CD    --  VARCHAR2 (1), ") ;
		selectSQL.append("\n	        ,NULL AS CODE_VAL_ERR_DESCN --  VARCHAR2 (4000), ") ;
		selectSQL.append("\n	        ,NULL AS DMN_VAL_ERR_EXS    -- VARCHAR2 (1), ") ;
		selectSQL.append("\n	        ,NULL AS DMN_VAL_ERR_CD     -- VARCHAR2 (1), ") ;
		selectSQL.append("\n	        ,NULL AS DMN_VAL_ERR_DESCN  -- VARCHAR2 (4000) ") ;
		selectSQL.append("\n  FROM TCM_CODE_DETAIL_C A ");
		selectSQL.append("\n          INNER JOIN  TCM_CODE_GROUP_C B ");
		selectSQL.append("\n              ON A.CODE_GROUP_ID = B.CODE_GROUP_ID  ");
		
		
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("\n INSERT INTO WAT_CODE_VAL ( ") ;
		insertSql.append("\n		DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSql.append("\n		,DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ; 
		insertSql.append("\n	    ,CODE_PNM            --VARCHAR2 (200) NOT NULL, ") ;
		insertSql.append("\n	    ,CODE_ID            -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,CODE_VAL_PNM       -- VARCHAR2 (200) NOT NULL, ") ;
		insertSql.append("\n	    ,CODE_VAL_LNM       -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,CODE_VAL            -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,CODE_VAL_ID        -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,CODE_VAL_ORD       -- NUMBER, ") ;
		insertSql.append("\n	    ,CODE_VAL_USE_YN    -- VARCHAR2 (1), ") ;
		insertSql.append("\n	    ,CODE_VAL_DESCN     -- VARCHAR2 (4000), ") ;
		insertSql.append("\n	    ,ESTN_COL           -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,REG_DTM            -- DATE, ") ;
		insertSql.append("\n	    ,UPD_DTM            -- DATE, ") ;
//		insertSql.append("\n		,CODE_VAL_EXTNC_EXS      -- VARCHAR2 (1), ") ;                                                          
//		insertSql.append("\n		,DMN_VAL_EXTNC_EXS      -- VARCHAR2 (1) ") ;   
		insertSql.append("\n )VALUES( ") ;
		insertSql.append("\n		 ? -- DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSql.append("\n		,? -- DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ; 
		insertSql.append("\n	    ,? -- CODE_PNM            --VARCHAR2 (200) NOT NULL, ") ;
		insertSql.append("\n	    ,? -- CODE_ID            -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_PNM       -- VARCHAR2 (200) NOT NULL, ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_LNM       -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,? -- CODE_VAL        -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_ID        -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_ORD       -- NUMBER, ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_USE_YN    -- VARCHAR2 (1), ") ;
		insertSql.append("\n	    ,? -- CODE_VAL_DESCN     -- VARCHAR2 (4000), ") ;
		insertSql.append("\n	    ,? -- ESTN_COL           -- VARCHAR2 (200), ") ;
		insertSql.append("\n	    ,? -- REG_DTM            -- DATE, ") ;
		insertSql.append("\n	    ,? -- UPD_DTM            -- DATE, ") ;
//		insertSql.append("\n		,'Y' ") ;                                                           
//		insertSql.append("\n		,'N' ") ;  
		insertSql.append("\n  ) ") ;
		
		//코드분류체계항목 삭제
		pstmt = null;
		pstmt = con.prepareStatement(deleteSQL.toString());
		pstmt.executeUpdate();
		
		
		//대상DB 코드분류체계항목 조회
		pstmt = null;
		rs = null;
		pstmt = tgtCon.prepareStatement(selectSQL.toString());
		rs = pstmt.executeQuery();
		
		pstmt = null;
		pstmt = con.prepareStatement(insertSql.toString());
		
		//meta rep db insert
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt.setString(pstmtIdx++, trgDM.getDbms_id());
			pstmt.setString(pstmtIdx++, trgDM.getDbSchId());
			
			pstmt.setString(pstmtIdx++, rs.getString("CODE_PNM"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_ID"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL_PNM"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL_LNM"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL_ID"));
			pstmt.setInt(pstmtIdx++, rs.getInt("CODE_VAL_ORD"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL_USE_YN"));
			pstmt.setString(pstmtIdx++, rs.getString("CODE_VAL_DESCN"));
			pstmt.setString(pstmtIdx++, rs.getString("ESTN_COL"));
			pstmt.setDate(pstmtIdx++, rs.getDate("REG_DTM"));
			pstmt.setDate(pstmtIdx++, rs.getDate("UPD_DTM"));
			pstmt.addBatch();
		}
		pstmt.executeBatch();
	}
	
	
}
