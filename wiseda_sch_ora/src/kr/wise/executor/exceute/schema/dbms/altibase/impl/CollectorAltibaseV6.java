/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorAltibaseV6.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 17. 오후 6:07:16
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 17. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema.dbms.altibase.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.altibase.CollectorAltibaseService;
import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorAltibaseV6.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms.altibase.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorAltibaseV6 implements CollectorAltibaseService{

	private static final Logger logger = Logger.getLogger(CollectorAltibaseV6.class);

	private Connection con_org = null;
	private Connection con_tgt = null;
	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;
	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;
	private String dbms_id = null;
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;
	private String dbType = null;
	private int execCnt = 10000;
	private String data_cpct = "";

	public CollectorAltibaseV6() {

	}

	public CollectorAltibaseV6(Connection source, Connection target, List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;
	}

	public boolean doProcess() throws Exception {
		boolean result = false;
		//con_org.setAutoCommit(false);

		String sp = "   ";
		int dbCnt = 0;
		
		for (TargetDbmsDM targetDb : targetDblist) {
			this.dbms_id = targetDb.getDbms_id();
			this.schemaName = targetDb.getDbc_owner_nm();
			this.schemaId   = targetDb.getDbSchId();
			this.dbName     = targetDb.getDbms_enm();
			this.dbType     = targetDb.getDbms_type_cd();
			
			int p = 0;
			int cnt = 0;

			//첫번째 스키마 일때만 처리한다.
			if(dbCnt == 0) {
				//스키마 정보를 모두 삭제한다.
				deleteSchema();
				con_org.commit();
			}
			dbCnt++;
			
			data_cpct = updateDataCpct();
			logger.debug(sp + (++p) + ". updateDataCpct " + data_cpct + " OK!!");

			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");
			
			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");

			cnt = insertDBA_CONSTRAINTS();
			logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

			cnt = insertDBA_CONS_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_CONS_COLUMNS " + cnt + " OK!!");
			
			cnt = insertDBA_TAB_COLUMNS_INFO();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS_INFO " + cnt + " OK!!");

			cnt = insertDBA_USAGE();
			logger.debug(sp + (++p) + ". insertDBA_USAGE " + cnt + " OK!!");
			
			con_org.commit();
		}
		result = true;

		return result;
	}
	
	/**
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT A.TABLE_ID                       ");
  		sb.append("\n      , A.TABLE_OID                      ");
  		sb.append("\n      , B.USER_NAME                      ");
  		sb.append("\n      , A.TABLE_NAME                     ");
  		sb.append("\n      , D.COMMENTS                     ");
  		sb.append("\n      , A.TABLE_TYPE                     ");
  		sb.append("\n      , A.COLUMN_COUNT                   ");
  		sb.append("\n      , A.REPLICATION_COUNT              ");
  		sb.append("\n      , A.REPLICATION_RECOVERY_COUNT     ");
  		sb.append("\n      , A.MAXROW                         ");
  		sb.append("\n      , C.NAME AS TBS_NAME               ");
  		sb.append("\n      , '' AS INSERT_HIGH_LIMIT   ");
  		sb.append("\n      , '' AS INSERT_LOW_LIMIT    ");
  		sb.append("\n      , A.INITEXTENTS AS INIT_EXTENTS   ");
  		sb.append("\n      , A.NEXTEXTENTS AS NEXT_EXTENTS    ");
  		sb.append("\n      , A.MINEXTENTS AS MIN_EXTENTS    ");
  		sb.append("\n      , A.MAXEXTENTS AS MAX_EXTENTS    ");
  		sb.append("\n      , CASE WHEN A.IS_PARTITIONED  = 'F' THEN 'N' ELSE 'Y' END AS IS_PARTITIONED ");
  		sb.append("\n      , TO_CHAR(A.CREATED,'YYYY-MM-DD HH24:MI:SS') AS CREATED ");
  		sb.append("\n      , TO_CHAR(A.LAST_DDL_TIME,'YYYY-MM-DD HH24:MI:SS') AS LAST_DDL_TIME ");
  		sb.append("\n      , E.NUM_ROW ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_TABLES_ A              ");
  		sb.append("\n         INNER JOIN SYSTEM_.SYS_USERS_ B         ");
  		sb.append("\n             ON A.USER_ID = B.USER_ID              ");
  		sb.append("\n            LEFT OUTER JOIN SYSTEM_.V$TABLESPACES C ");
  		sb.append("\n             ON A.TBS_ID = C.ID                      ");
  		sb.append("\n            LEFT OUTER JOIN SYSTEM_.SYS_COMMENTS_ D ");
  		sb.append("\n             ON A.TABLE_NAME = D.TABLE_NAME                    ");
  		sb.append("\n            AND B.USER_NAME = D.USER_NAME                    ");
  		sb.append("\n            AND D.COLUMN_NAME IS NULL                  ");
  		sb.append("\n            LEFT OUTER JOIN SYSTEM_.V$DBMS_STATS E ");
  		sb.append("\n              ON A.TABLE_OID = E.TARGET_ID ");
  		sb.append("\n             AND E.TYPE = 'T' ");
  		sb.append("\n WHERE UPPER(B.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n    AND   A.TABLE_TYPE = 'T'                ");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_ALTI_TABLES     ");
  		sb.append("\n ( TABLE_ID                      ");
  		sb.append("\n , TABLE_OID                     ");
  		sb.append("\n , USER_NAME                     ");
  		sb.append("\n , TABLE_NAME                    ");
  		sb.append("\n , COMMENTS                    ");
  		sb.append("\n , TABLE_TYPE                    ");
  		sb.append("\n , COLUMN_COUNT                  ");
  		sb.append("\n , REPLICATION_COUNT             ");
  		sb.append("\n , REPLICATION_RECOVERY_COUNT    ");
  		sb.append("\n , MAXROW                        ");
  		sb.append("\n , TBS_NAME                      ");
  		sb.append("\n , INSERT_HIGH_LIMIT             ");
  		sb.append("\n , INSERT_LOW_LIMIT              ");
  		sb.append("\n , INIT_EXTENTS              ");
  		sb.append("\n , NEXT_EXTENTS              ");
  		sb.append("\n , MIN_EXTENTS              ");
  		sb.append("\n , MAX_EXTENTS              ");
  		sb.append("\n , IS_PARTITIONED                ");
  		sb.append("\n , CREATED                       ");
  		sb.append("\n , LAST_DDL_TIME                 ");
  		sb.append("\n , NUM_ROW                 ");
  		sb.append("\n , DB_NM                   ");
  		sb.append("\n      , DB_CONN_TRG_ID                  ");
  		sb.append("\n ) VALUES (                      ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,            ");
  		sb.append("\n ?,?,TO_DATETIME(?,'YYYY-MM-DD HH24:MI:SS'),TO_DATETIME(?,'YYYY-MM-DD HH24:MI:SS'),?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_OID"));
  				pst_org.setString(rsGetCnt++, schemaName);
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("REPLICATION_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("REPLICATION_RECOVERY_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("MAXROW"));
  				pst_org.setString(rsGetCnt++, rs.getString("TBS_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INSERT_HIGH_LIMIT"));
  				pst_org.setString(rsGetCnt++, rs.getString("INSERT_LOW_LIMIT"));
  				pst_org.setString(rsGetCnt++, rs.getString("INIT_EXTENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("NEXT_EXTENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("MIN_EXTENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("MAX_EXTENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PARTITIONED"));
  				pst_org.setString(rsGetCnt++, rs.getString("CREATED"));
  				pst_org.setString(rsGetCnt++, rs.getString("LAST_DDL_TIME"));
  				pst_org.setString(rsGetCnt++, rs.getString("NUM_ROW"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, dbms_id);
  				getSaveResult(rsCnt);
  				
  				if(rsCnt == execCnt) {
  					executeBatch(true);
  					rsCnt = 0;
  				}
  				rsCnt++;
  				rsGetCnt = 1;
  				cnt++;
  			}
  			executeBatch();
  		}
  		
  		return cnt;
	}
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT C.COLUMN_ID                  ");
  		sb.append("\n      , A.USER_NAME                  ");
  		sb.append("\n      , B.TABLE_NAME                 ");
  		sb.append("\n      , C.COLUMN_NAME                ");
  		sb.append("\n      , C.COLUMN_ORDER + 1 AS COLUMN_ORDER ");
  		sb.append("\n      , D.TYPE_NAME AS DATA_TYPE     ");
  		sb.append("\n      , C.PRECISION AS DATA_LENGTH   ");
  		sb.append("\n      , CASE WHEN C.SCALE = 0 THEN NULL ELSE C.SCALE END AS DATA_SCALE  ");
  		sb.append("\n      , CASE WHEN C.IS_NULLABLE = 'T' THEN 'Y' ELSE 'N' END AS IS_NULL ");
  		sb.append("\n      , 'N' AS IS_PK                 ");
  		sb.append("\n      , 'N' AS IS_FK                 ");
  		sb.append("\n      , C.DEFAULT_VAL                ");
  		sb.append("\n      , C.STORE_TYPE                 ");
  		sb.append("\n      , C.IN_ROW_SIZE                ");
  		sb.append("\n      , C.REPL_CONDITION             ");
  		sb.append("\n      , E.COMMENTS             ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A           ");
  		sb.append("\n         INNER JOIN SYSTEM_.SYS_TABLES_ B    ");
  		sb.append("\n             ON A.USER_ID = B.USER_ID            ");
  		sb.append("\n         INNER JOIN SYSTEM_.SYS_COLUMNS_ C   ");
  		sb.append("\n             ON B.TABLE_ID = C.TABLE_ID          ");
  		sb.append("\n         INNER JOIN SYSTEM_.V$DATATYPE D     ");
  		sb.append("\n             ON C.DATA_TYPE = D.DATA_TYPE        ");
  		sb.append("\n          LEFT OUTER JOIN SYSTEM_.SYS_COMMENTS_ E     ");
  		sb.append("\n             ON A.USER_NAME = E.USER_NAME        ");
  		sb.append("\n            AND B.TABLE_NAME = E.TABLE_NAME        ");
  		sb.append("\n            AND C.COLUMN_NAME = E.COLUMN_NAME        ");
  		sb.append("\n            AND E.COMMENTS IS NOT NULL        ");
  		sb.append("\n  WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n     AND   B.TABLE_TYPE = 'T'                ");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_ALTI_COLUMNS     ");
  		sb.append("\n ( COLUMN_ID      ");
  		sb.append("\n , USER_NAME      ");
  		sb.append("\n , TABLE_NAME     ");
  		sb.append("\n , COLUMN_NAME    ");
  		sb.append("\n , COMMENTS    ");
  		sb.append("\n , COLUMN_ORDER   ");
  		sb.append("\n , DATA_TYPE      ");
  		sb.append("\n , DATA_LENGTH    ");
  		sb.append("\n , DATA_SCALE     ");
  		sb.append("\n , IS_NULL        ");
  		sb.append("\n , IS_PK          ");
  		sb.append("\n , IS_FK          ");
  		sb.append("\n , DEFAULT_VAL    ");
  		sb.append("\n , STORE_TYPE     ");
  		sb.append("\n , IN_ROW_SIZE    ");
  		sb.append("\n , REPL_CONDITION ");
  		sb.append("\n , DB_NM    ");
  		sb.append("\n      , DB_CONN_TRG_ID                  ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,            ");
  		sb.append("\n ?,?,?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_LENGTH"));
  				pst_org.setString(rsGetCnt++,  rs.getString("DATA_SCALE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_NULL"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PK"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_FK"));
  				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("STORE_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IN_ROW_SIZE"));
  				pst_org.setString(rsGetCnt++, rs.getString("REPL_CONDITION"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, dbms_id);
  				getSaveResult(rsCnt);
  				
  				if(rsCnt == execCnt) {
  					executeBatch(true);
  					rsCnt = 0;
  				}
  				rsCnt++;
  				rsGetCnt = 1;
  				cnt++;
  			}
  			executeBatch();
  		}
  		
  		return cnt;
	}
	
	/**
	 * DBC DBA_CONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONSTRAINTS() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT C.CONSTRAINT_ID                        ");
  		sb.append("\n      , A.USER_NAME                            ");
  		sb.append("\n      , B.TABLE_NAME                           ");
  		sb.append("\n      , C.CONSTRAINT_NAME                      ");
  		sb.append("\n      , C.CONSTRAINT_TYPE AS CONST_TYPE_CODE   ");
  		sb.append("\n      , CASE C.CONSTRAINT_TYPE                 ");
  		sb.append("\n             WHEN 0 THEN 'FOREIGN KEY'         ");
  		sb.append("\n             WHEN 1 THEN 'NOT NULL'            ");
  		sb.append("\n             WHEN 2 THEN 'UNIQUE'              ");
  		sb.append("\n             WHEN 3 THEN 'PRIMARY KEY'         ");
  		sb.append("\n             WHEN 4 THEN 'NULL'                ");
  		sb.append("\n             WHEN 5 THEN 'TIMESTAMP'           ");
  		sb.append("\n             WHEN 6 THEN 'LOCAL UNIQUE'        ");
  		sb.append("\n             ELSE '' END AS CONST_TYPE_NAME    ");
  		sb.append("\n      , D.INDEX_NAME                           ");
  		sb.append("\n      , C.COLUMN_CNT                           ");
  		sb.append("\n      , E.TABLE_NAME AS REF_TABLE_NAME         ");
  		sb.append("\n      , F.INDEX_NAME AS REF_INDEX_NAME         ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                     ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B              ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                      ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_CONSTRAINTS_ C         ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                    ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.SYS_INDICES_ D        ");
  		sb.append("\n ON C.INDEX_ID = D.INDEX_ID                    ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.SYS_TABLES_ E         ");
  		sb.append("\n ON C.REFERENCED_TABLE_ID = E.TABLE_ID         ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.SYS_INDICES_ F        ");
  		sb.append("\n ON C.REFERENCED_INDEX_ID = F.INDEX_ID         ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_ALTI_CONSTRAINTS ");
  		sb.append("\n ( CONSTRAINT_ID    ");
  		sb.append("\n , USER_NAME        ");
  		sb.append("\n , TABLE_NAME       ");
  		sb.append("\n , CONSTRAINT_NAME  ");
  		sb.append("\n , CONST_TYPE_CODE  ");
  		sb.append("\n , CONST_TYPE_NAME  ");
  		sb.append("\n , INDEX_NAME       ");
  		sb.append("\n , COLUMN_CNT       ");
  		sb.append("\n , REF_TABLE_NAME   ");
  		sb.append("\n , REF_INDEX_NAME   ");
  		sb.append("\n , DB_NM      ");
  		sb.append("\n      , DB_CONN_TRG_ID                  ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?, ");
  		sb.append("\n ?,? 					");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_ID"));
  				pst_org.setString(rsGetCnt++, schemaName);
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_TYPE_CODE"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_TYPE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_CNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("REF_TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("REF_INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, dbms_id);
  				getSaveResult(rsCnt);
  				
  				if(rsCnt == execCnt) {
  					executeBatch(true);
  					rsCnt = 0;
  				}
  				rsCnt++;
  				rsGetCnt = 1;
  				cnt++;
  			}
  			executeBatch();
  		}
  		
  		return cnt;
	}

	/**
	 * DBC DBA_CONS_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONS_COLUMNS() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT A.USER_NAME                               ");
  		sb.append("\n      , B.TABLE_NAME                              ");
  		sb.append("\n      , C.CONSTRAINT_NAME                         ");
  		sb.append("\n      , E.COLUMN_NAME AS CONST_COL_NAME           ");
  		sb.append("\n      , (D.CONSTRAINT_COL_ORDER+1) AS CONST_COL_ORDER ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                        ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                 ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                         ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_CONSTRAINTS_ C            ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                       ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_CONSTRAINT_COLUMNS_ D     ");
  		sb.append("\n ON C.CONSTRAINT_ID = D.CONSTRAINT_ID             ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_COLUMNS_ E                ");
  		sb.append("\n ON B.TABLE_ID = E.TABLE_ID                       ");
  		sb.append("\n AND D.COLUMN_ID = E.COLUMN_ID                    ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_ALTI_CONST_COLUMNS ");
  		sb.append("\n ( USER_NAME       ");
  		sb.append("\n , TABLE_NAME      ");
  		sb.append("\n , CONSTRAINT_NAME ");
  		sb.append("\n , CONST_COL_NAME  ");
  		sb.append("\n , CONST_COL_ORDER ");
  		sb.append("\n , DB_NM     ");
  		sb.append("\n      , DB_CONN_TRG_ID                  ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, schemaName);
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, dbms_id);
  				getSaveResult(rsCnt);
  				
  				if(rsCnt == execCnt) {
  					executeBatch(true);
  					rsCnt = 0;
  				}
  				rsCnt++;
  				rsGetCnt = 1;
  				cnt++;
  			}
  			executeBatch();
  		}
  		
  		return cnt;
	}
	
	private int insertDBA_TAB_COLUMNS_INFO() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT A.USER_NAME										");                  
  		sb.append("\n      , B.TABLE_NAME										");               
  		sb.append("\n      , C.COLUMN_NAME										");                                                                       
  		sb.append("\n      , CASE WHEN F.COLUMN_NAME IS NOT NULL THEN F.TABLE_NAME || '.' || F.COLUMN_NAME ELSE NULL END AS FK_INFO ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A 								");
  		sb.append("\n		INNER JOIN SYSTEM_.SYS_TABLES_ B					");    
  		sb.append("\n		ON A.USER_ID = B.USER_ID							");            
  		sb.append("\n 		INNER JOIN SYSTEM_.SYS_COLUMNS_ C					");   
  		sb.append("\n       ON B.TABLE_ID = C.TABLE_ID 							");              
  		sb.append("\n   	LEFT OUTER JOIN (									");
  		sb.append("\n			SELECT A.TABLE_ID AS TABLE_ID					");
  		sb.append("\n          			, B.COLUMN_ID AS COLUMN_ID				");
  		sb.append("\n        			, E.TABLE_NAME AS TABLE_NAME			");
  		sb.append("\n					, D.COLUMN_NAME AS COLUMN_NAME			");
  		sb.append("\n			FROM SYSTEM_.SYS_USERS_ U						");
  		sb.append("\n				INNER JOIN SYSTEM_.SYS_CONSTRAINTS_ A 		");
  		sb.append("\n				ON U.USER_ID = A.USER_ID					");
  		sb.append("\n				INNER JOIN SYSTEM_.SYS_CONSTRAINT_COLUMNS_ B");
  		sb.append("\n				ON A.CONSTRAINT_ID = B.CONSTRAINT_ID		");
  		sb.append("\n				AND A.USER_ID = B.USER_ID					");
  		sb.append("\n				AND A.TABLE_ID = B.TABLE_ID					");
  		sb.append("\n				INNER JOIN SYSTEM_.SYS_INDEX_COLUMNS_ C		");
  		sb.append("\n				ON A.REFERENCED_INDEX_ID = C.INDEX_ID		");
  		sb.append("\n				INNER JOIN SYSTEM_.SYS_COLUMNS_ D			");
  		sb.append("\n				ON C.COLUMN_ID = D.COLUMN_ID				");
  		sb.append("\n				INNER JOIN SYSTEM_.SYS_TABLES_ E			");
  		sb.append("\n				ON A.REFERENCED_TABLE_ID = E.TABLE_ID		");
  		sb.append("\n			WHERE A.CONSTRAINT_TYPE = 0						");
  		sb.append("\n  						)F									");
  		sb.append("\n   		ON C.TABLE_ID = F.TABLE_ID						");
  		sb.append("\n  			AND C.COLUMN_ID = F.COLUMN_ID                  	");
  		sb.append("\n  WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n     AND   B.TABLE_TYPE = 'T'                				");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_ALTI_COLUMNS_INFO     		");
  		sb.append("\n ( DB_NM, SCH_NM, TBL_NM     					");
  		sb.append("\n , COL_NM, FK_INFO, CHECK_INFO, DB_CONN_TRG_ID ");
  		sb.append("\n ) VALUES ( 									");
  		sb.append("\n ?,?,?            								");
  		sb.append("\n ,?,?,?,? 										");
  		sb.append("\n ) 											");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, dbName);
  				pst_org.setString(rsGetCnt++, schemaName);
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("FK_INFO"));
  				pst_org.setString(rsGetCnt++, "");
  				pst_org.setString(rsGetCnt++, dbms_id);
  				getSaveResult(rsCnt);
  				
  				if(rsCnt == execCnt) {
  					executeBatch(true);
  					rsCnt = 0;
  				}
  				rsCnt++;
  				rsGetCnt = 1;
  				cnt++;
  			}
  			executeBatch();
  		}
  		
  		return cnt;
	}
		
	/**
	 * DBC DBA_SEGMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_USAGE() throws SQLException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.USER_NAME ");
		strSQL.append("\n          ,B.TABLE_NAME ");
		strSQL.append("\n          ,U.TYPE ");
		strSQL.append("\n          ,U.FREE_SPACE ");
		strSQL.append("\n          ,U.AGEABLE_SPACE ");
		strSQL.append("\n          ,U.META_SPACE ");
		strSQL.append("\n          ,U.TARGET_ID ");
		strSQL.append("\n          ,U.USED_SPACE ");
		strSQL.append("\n        ,'").append(dbName).append("' AS DB_NM");
		strSQL.append("\n   FROM SYSTEM_.SYS_USERS_ A ");
		strSQL.append("\n        INNER JOIN SYSTEM_.SYS_TABLES_ B ");
		strSQL.append("\n           ON A.DEFAULT_TBS_ID = B.USER_ID ");
		strSQL.append("\n        INNER JOIN SYSTEM_.V$USAGE U ");
		strSQL.append("\n           ON U.TARGET_ID = B.TABLE_OID ");
		strSQL.append("\n  WHERE U.TYPE='T' ");
		strSQL.append("\n     AND UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
		getResultSet(strSQL.toString());
		
		strSQL = new StringBuffer();
		strSQL.append("\nINSERT INTO WAE_ALTI_USAGE (");
		strSQL.append("\n      DB_NM                      ");
		strSQL.append("\n     ,USER_NAME                ");
		strSQL.append("\n     ,TABLE_NAME                ");
		strSQL.append("\n     ,TYPE        ");
		strSQL.append("\n     ,FREE_SPACE     ");
		strSQL.append("\n     ,AGEABLE_SPACE      ");
		strSQL.append("\n     ,META_SPACE        ");
		strSQL.append("\n     ,TARGET_ID         ");
		strSQL.append("\n     ,USED_SPACE        ");
		strSQL.append("\n      , DB_CONN_TRG_ID                  ");
		strSQL.append("\n) VALUES (                    ");
		strSQL.append("\n      ? --DB_NM                      ");
		strSQL.append("\n     ,? --USER_NAME                ");
		strSQL.append("\n     ,? --TABLE_NAME                ");
		strSQL.append("\n     ,? --TYPE        ");
		strSQL.append("\n     ,? --FREE_SPACE     ");
		strSQL.append("\n     ,? --AGEABLE_SPACE      ");
		strSQL.append("\n     ,? --META_SPACE        ");
		strSQL.append("\n     ,? --TARGET_ID         ");
		strSQL.append("\n     ,? --USED_SPACE        ");
		strSQL.append("\n     ,? --DB_CONN_TRG_ID        ");
		strSQL.append("\n)                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(strSQL.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AGEABLE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("META_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TARGET_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USED_SPACE"));
				pst_org.setString(rsGetCnt++, dbms_id);
				getSaveResult(rsCnt);

				if(rsCnt == execCnt) {
					executeBatch(true);
					rsCnt = 0;
				}
				rsCnt++;
				rsGetCnt = 1;
				cnt++;
			}
			executeBatch();
		}
		
		strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.USER_NAME ");
		strSQL.append("\n        ,B.TABLE_NAME ");
		strSQL.append("\n        ,U.TYPE ");
		strSQL.append("\n        ,U.FREE_SPACE ");
		strSQL.append("\n        ,U.AGEABLE_SPACE ");
		strSQL.append("\n        ,U.META_SPACE ");
		strSQL.append("\n        ,U.TARGET_ID ");
		strSQL.append("\n        ,U.USED_SPACE ");
		strSQL.append("\n        ,'").append(dbName).append("' AS DB_NM");
		strSQL.append("\n   FROM SYSTEM_.SYS_USERS_ A ");
		strSQL.append("\n        INNER JOIN SYSTEM_.SYS_TABLES_ B ");
		strSQL.append("\n           ON A.USER_ID = B.USER_ID ");
		strSQL.append("\n        INNER JOIN SYSTEM_.SYS_INDICES_ C ");
		strSQL.append("\n           ON B.TABLE_ID = C.TABLE_ID ");
		strSQL.append("\n        INNER JOIN SYSTEM_.V$USAGE U ");
		strSQL.append("\n           ON U.TARGET_ID = C.INDEX_ID ");
		strSQL.append("\n  WHERE U.TYPE='I' ");
		strSQL.append("\n     AND UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
		getResultSet(strSQL.toString());
		
		strSQL = new StringBuffer();
		strSQL.append("\nINSERT INTO WAE_ALTI_USAGE (");
		strSQL.append("\n      DB_NM                      ");
		strSQL.append("\n     ,USER_NAME                ");
		strSQL.append("\n     ,TABLE_NAME                ");
		strSQL.append("\n     ,TYPE        ");
		strSQL.append("\n     ,FREE_SPACE     ");
		strSQL.append("\n     ,AGEABLE_SPACE      ");
		strSQL.append("\n     ,META_SPACE        ");
		strSQL.append("\n     ,TARGET_ID         ");
		strSQL.append("\n     ,USED_SPACE        ");
		strSQL.append("\n) VALUES (                    ");
		strSQL.append("\n      ? --DB_NM                      ");
		strSQL.append("\n     ,? --USER_NAME                ");
		strSQL.append("\n     ,? --TABLE_NAME                ");
		strSQL.append("\n     ,? --TYPE        ");
		strSQL.append("\n     ,? --FREE_SPACE     ");
		strSQL.append("\n     ,? --AGEABLE_SPACE      ");
		strSQL.append("\n     ,? --META_SPACE        ");
		strSQL.append("\n     ,? --TARGET_ID         ");
		strSQL.append("\n     ,? --USED_SPACE        ");
		strSQL.append("\n)                             ");
		
		cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(strSQL.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next()) {
				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AGEABLE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("META_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TARGET_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USED_SPACE"));
				getSaveResult(rsCnt);
				
				if(rsCnt == execCnt) {
					executeBatch(true);
					rsCnt = 0;
				}
				rsCnt++;
				rsGetCnt = 1;
				cnt++;
			}
			executeBatch();
		}

		return cnt;
	}
	
	/**
	 * 타겟데이터의 SQL을 받아 CResultSet을 넘겨준다.
	 * @param String 조회 SQL
	 * @return CResultSet Query 결과값
	 * @throws SQLException
	 */
	private ResultSet getResultSet(String query_tgt) throws SQLException {
		pst_tgt = null;
		rs = null;
		pst_tgt = con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		pst_tgt.setFetchSize(3000);
		rs = pst_tgt.executeQuery();

		return rs;
	}
	
	private int deleteSchema() throws Exception {
		int result = 0 ;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_ALTI_TABLES ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_TABLES : " + result);
		
		//제약조건
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_CONSTRAINTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_CONSTRAINTS : " + result);
		
		//제약조건컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_CONST_COLUMNS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_CONST_COLUMNS : " + result);
		
		//테이블 컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_COLUMNS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_COLUMNS : " + result);
		
		//테이블 컬럼정보
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_COLUMNS_INFO ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_COLUMNS_INFO : " + result);
		
		//사용량
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_USAGE ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_USAGE : " + result);
		
		return result;
	}
	
	/**
	 * 타겟데이터의 SQL을 받아 저장한다.
	 * @param String 저장 SQL
	 * @return boolean 저장결과값
	 * @throws SQLException
	 */
	private int setExecuteUpdate_Org(String query_tgt) throws SQLException {
		pst_org = con_org.prepareStatement(query_tgt);
		int cnt = pst_org.executeUpdate();
		pst_org.close();

		return cnt;
	}

	/**
	 * 저장데이터의 SQL을 받아 저장여부를 넘겨준다.
	 * @param String 저장 SQL
	 * @param String 저장 내용 목록
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private boolean getSaveResult(int cnt) throws SQLException {
		boolean result = false;
		pst_org.addBatch();
		result = true;

		return result;
	}

	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 */
	private int[] executeBatch() throws SQLException {
		return executeBatch(false);
	}

	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 */
	private int[] executeBatch(boolean execYN) throws SQLException {
		//logger.debug("단계:executeBatch [실행]");
		int[] i = pst_org.executeBatch();

		if(execYN) {
			//logger.debug("단계:executeBatch [execYN]");
			pst_org.clearBatch();
		} else {
			//logger.debug("단계:executeBatch [close]");
			rs.close();
			pst_org.close();
		}
		con_org.commit();	//2019.05.27 LOCK 문제로 인하여 중간에 commit 처리하도록 추가

		return i;
	}

	public boolean saveWat(TargetDbmsDM targetDb) throws Exception {
		this.schemaName = "";
		this.schemaId = "";
		this.dbName = targetDb.getDbms_enm();
		this.dbType = targetDb.getDbms_type_cd();
		boolean result = false;
		int cnt = 0;

		cnt = insertwattbl();
		logger.debug("insertwattbl : " + cnt + " OK!!");

		cnt = insertwatcol();
		logger.debug("insertwatcol : " + cnt + " OK!!");
		
		cnt = updateTblCnt();
		logger.debug("updateTblCnt " + dbName + cnt + " OK!!");
		result = true;
		
		return result;
	}

	private int insertwattbl() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL   ");
		sb.append("\n (                         ");
		sb.append("\n         DB_SCH_ID                             ");
		sb.append("\n      ,  DBC_TBL_NM                            ");
		sb.append("\n      ,  DBC_TBL_KOR_NM                        ");
		sb.append("\n      ,  OBJ_VERS                              ");
		sb.append("\n      ,  REG_TYP_CD                            ");
		sb.append("\n      ,  REG_DTM                               ");
		sb.append("\n      ,  UPD_DTM                               ");
		sb.append("\n      ,  DESCN                                 ");
		sb.append("\n      ,  DB_CONN_TRG_ID                        ");
		sb.append("\n      ,  DBC_TBL_SPAC_NM                       ");
		sb.append("\n      ,  DBMS_TYP_CD                           ");
		sb.append("\n      ,  COL_EACNT                             ");
		sb.append("\n      ,  ROW_EACNT                             ");
		sb.append("\n      ,  DATA_SIZE                             ");
		sb.append("\n      ,  NUSE_SIZE                             ");
		sb.append("\n      ,  TBL_ANA_DTM                           ");
		sb.append("\n      ,  TBL_CRT_DTM                           ");
		sb.append("\n      ,  TBL_CHG_DTM                           ");
		sb.append("\n      , TBL_CLLT_DCD                           ");
		sb.append("\n )                         "); 
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID       ");
		sb.append("\n      , A.TABLE_NAME          AS DBC_TBL_NM      ");		
		sb.append("\n      , A.COMMENTS            AS DBC_TBL_KOR_NM  ");
		sb.append("\n      , '1'                   AS OBJ_VERS        ");
		sb.append("\n      , NULL                  AS REG_TYP_CD      ");
		sb.append("\n      , SYSDATE           AS REG_DTM         ");
		sb.append("\n      , NULL                  AS UPD_DTM         ");
		sb.append("\n      , ''                    AS DESCN           ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID  ");
		sb.append("\n      , A.TBS_NAME            AS DBC_TBL_SPAC_NM ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYP_CD     ");
		sb.append("\n      , A.COLUMN_COUNT        AS COL_EACNT       ");		
		sb.append("\n      , A.NUM_ROW             AS ROW_EACNT       ");
		sb.append("\n      , ''                    AS DATA_SIZE       ");
		sb.append("\n      , ''                    AS NUSE_SIZE       ");
		sb.append("\n      , NULL                  AS TBL_ANA_DTM     ");
		sb.append("\n      , A.CREATED             AS TBL_CRT_DTM     ");
		sb.append("\n      , NULL                  AS TBL_CHG_DTM     ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_ALTI_TABLES A  ");
		sb.append("\n  INNER JOIN (                    ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.USER_NAME = S.DB_SCH_PNM                             ");
		sb.append("\n    AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID              ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'       ");
		sb.append("\n   AND A.USER_NAME || '.' || A.TABLE_NAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.USER_NAME		");
		sb.append("\n   	)													");

		return setExecuteUpdate_Org(sb.toString());
	}

	private int insertwatcol() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL (                                    ");
		sb.append("\n        DB_SCH_ID                                             ");
		sb.append("\n      , DBC_TBL_NM                                            ");
		sb.append("\n      , DBC_COL_NM                                            ");
		sb.append("\n      , DBC_COL_KOR_NM                                        ");
		sb.append("\n      , OBJ_VERS                                              ");
		sb.append("\n      , REG_TYP_CD                                            ");
		sb.append("\n      , REG_DTM                                               ");
		sb.append("\n      , UPD_DTM                                               ");
		sb.append("\n      , DATA_TYPE                                             ");
		sb.append("\n      , DATA_LEN                                              ");
		sb.append("\n      , DATA_PNUM                                             ");
		sb.append("\n      , DATA_PNT                                              ");
		sb.append("\n      , NULL_YN                                               ");
		sb.append("\n      , DEFLT_LEN                                             ");
		sb.append("\n      , DEFLT_VAL                                             ");
		sb.append("\n      , PK_YN                                                 ");
		sb.append("\n      , FK_YN                                                 ");
		sb.append("\n      , ORD                                                   ");
		sb.append("\n      , PK_ORD                                                ");
		sb.append("\n      , COL_DESCN                                             ");
		sb.append("\n )                         									"); 
		sb.append("\n SELECT S.DB_SCH_ID 			AS DB_SCH_ID       				");
		sb.append("\n      , A.TABLE_NAME 			AS DBC_TBL_NM      				");
		sb.append("\n      , A.COLUMN_NAME 			AS DBC_COL_NM      				");
		sb.append("\n      , A.COMMENTS 			AS DBC_COL_KOR_NM        		");
		sb.append("\n      , '1'  					AS OBJ_VERS             		");
		sb.append("\n      , NULL 					AS REG_TYP_CD             		");
		sb.append("\n      , SYSDATE 			AS REG_DTM       				");
		sb.append("\n      , NULL  					AS UPD_DTM             			");
		sb.append("\n      , DATA_TYPE 				AS DATA_TYPE         			");
		sb.append("\n      , DATA_LENGTH 			AS DATA_LEN       				");
		sb.append("\n      , '' 					AS DATA_PNUM 					");
		sb.append("\n      , A.DATA_SCALE 			AS DATA_PNT          			");
		sb.append("\n      , IS_NULL 				AS NULL_YN                      ");
		sb.append("\n      , '' 					AS DEFLT_LEN                   	");
		sb.append("\n      , TRIM(DEFAULT_VAL)  	AS DEFLT_VAL 					");
		sb.append("\n      , (CASE WHEN C.CONST_COL_ORDER IS NOT NULL THEN 'Y' ELSE 'N' END ) AS IS_PK   ");
		sb.append("\n	   , F.FK_INFO 			AS FK_YN		");	
		sb.append("\n      , A.COLUMN_ORDER  		AS ORD        					");
		sb.append("\n      , NULL   				AS PK_ORD     					");
		sb.append("\n      , C.CONST_COL_ORDER  	AS COL_DESCN  					");
		sb.append("\n   FROM WAE_ALTI_TABLES T                      				");
	    sb.append("\n            INNER JOIN WAE_ALTI_COLUMNS A  					");
	    sb.append("\n                ON T.DB_NM = A.DB_NM  							");
	    sb.append("\n               AND T.USER_NAME = A.USER_NAME  					");
	    sb.append("\n               AND T.TABLE_NAME = A.TABLE_NAME  				");
	    sb.append("\n               AND T.DB_CONN_TRG_ID = A.DB_CONN_TRG_ID         ");
		sb.append("\n  INNER JOIN (                  								");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               							");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM         					");
		sb.append("\n    AND A.USER_NAME  = S.DB_SCH_PNM         			");
		sb.append("\n    AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID             		 ");
		sb.append("\n    LEFT OUTER JOIN WAE_ALTI_CONSTRAINTS D 					");
		sb.append("\n      ON A.DB_NM = D.DB_NM 									");
		sb.append("\n     AND A.USER_NAME = D.USER_NAME 							");
		sb.append("\n     AND A.TABLE_NAME  = D.TABLE_NAME  						");
		sb.append("\n     AND A.DB_CONN_TRG_ID = D.DB_CONN_TRG_ID              		");
		/*
    	   0: FOREIGN KEY
		 1: NOT NULL
		 2: UNIQUE
		 3: PRIMARY KEY
		 4: NULL
		 5: TIMESTAMP
		 6: LOCAL UNIQUE
		 7: CHECK
		 */
		sb.append("\n     AND D.CONST_TYPE_CODE  = '3' 								");   
		sb.append("\n    LEFT OUTER JOIN WAE_ALTI_CONST_COLUMNS C 					");
		sb.append("\n      ON A.DB_NM = C.DB_NM 									");
		sb.append("\n     AND A.USER_NAME = C.USER_NAME 							");
		sb.append("\n     AND A.TABLE_NAME = C.TABLE_NAME 							");
		sb.append("\n     AND A.COLUMN_NAME  = C.CONST_COL_NAME  					");
		sb.append("\n     AND A.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID              		");
		sb.append("\n     AND C.CONSTRAINT_NAME  = D.CONSTRAINT_NAME  				");
		sb.append("\n     AND C.CONST_COL_ORDER  IS NOT NULL 						");
		sb.append("\n	LEFT JOIN (										");
		sb.append("\n		SELECT DB_CONN_TRG_ID, DB_NM, SCH_NM, TBL_NM, COL_NM, WM_CONCAT(FK_INFO) FK_INFO		");
		sb.append("\n		FROM WAE_ALTI_COLUMNS_INFO 														");
		sb.append("\n		GROUP BY DB_NM, SCH_NM, TBL_NM, COL_NM, DB_CONN_TRG_ID							");
		sb.append("\n				) F												");
		sb.append("\n	ON	A.DB_NM = F.DB_NM										");
		sb.append("\n	AND A.USER_NAME = F.SCH_NM									");
		sb.append("\n	AND A.TABLE_NAME  = F.TBL_NM								");
		sb.append("\n	AND A.COLUMN_NAME = F.COL_NM								");
		sb.append("\n	AND A.DB_CONN_TRG_ID = F.DB_CONN_TRG_ID						");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");
		sb.append("\n   AND A.USER_NAME || '.' || A.TABLE_NAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.USER_NAME		");
		sb.append("\n   	)													");

		return setExecuteUpdate_Org(sb.toString());
	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n                      INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                         ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n                        AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                        AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n             WHERE 1=1 ");
		strSQL.append("\n               AND T.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n               AND T.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n               AND T.DB_CONN_TRG_ID = '"+dbms_id+"'");

		return strSQL.toString();
	}
	
	private int updateTblCnt() throws SQLException{
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET tbl_cnt = (        ");
		strSQL.append("\n		SELECT COUNT(DBC_TBL_NM) FROM WAT_DBC_TBL WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		strSQL.append("\n     )   						    ");
		strSQL.append("\n      	WHERE 1=1       ");
		strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
		strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
		strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbms_id+"'     ");
				
		return setExecuteUpdate_Org(strSQL.toString());
	}
	
	private String updateDataCpct() throws SQLException {
		StringBuffer tgtSQL = new StringBuffer();
		tgtSQL.append("\n SELECT    ");
		tgtSQL.append("\n SUM(ROUND((FIXED_USED_MEM+VAR_USED_MEM)/1024/1204,2)) USED    ");
		tgtSQL.append("\n FROM SYSTEM_.SYS_TABLES_ T, V$MEMTBL_INFO M, SYSTEM_.SYS_USERS_ U    ");
		tgtSQL.append("\n WHERE T.TABLE_OID = M.TABLE_OID     ");
		tgtSQL.append("\n AND T.USER_ID = U.USER_ID     ");				
		rs = getResultSet(tgtSQL.toString());
		
		if(rs != null){
			rs.next();
			data_cpct = rs.getString(1);
			
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET DATA_CPCT = (        ");
			strSQL.append(rs.getString(1));
			strSQL.append("\n     )   						    ");
			strSQL.append("\n      	WHERE 1=1       ");
			strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
			strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
			strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbms_id+"'     ");
			setExecuteUpdate_Org(strSQL.toString());
		}
						
		return data_cpct;
	}
}
