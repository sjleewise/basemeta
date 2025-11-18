/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorOracle.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 17. 오후 6:07:16
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 17. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema.dbms.oracle.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.oracle.CollectorOracleService;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorOracle8i.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms.oracle.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorOracle8i implements CollectorOracleService {

	private static final Logger logger = Logger.getLogger(CollectorOracle8i.class);

	private Connection con_org = null;
	private Connection con_tgt = null;
	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;
	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;
	private String dbType = null;
	private String dbmsVersion = "8i";
	private String dbms_id = null;
	private String data_cpct = "";		
	private int execCnt = 100; 

	public CollectorOracle8i() {

	}

	public CollectorOracle8i(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;
	}
	
	protected void finalize() throws Throwable {
        super.finalize();
        if(pst_org != null) pst_org.close();
        if(con_org != null) con_org.close();
        if(pst_tgt != null) pst_tgt.close();
        if(con_tgt != null) con_tgt.close();                 
    }

	public boolean doProcess() throws Exception {
		boolean result = false;
		//con_org.setAutoCommit(false);

		String sp = "   ";
		int dbCnt = 0;
		for (TargetDbmsDM targetDb : targetDblist) {
			this.schemaName = targetDb.getDbc_owner_nm();
			this.schemaId = targetDb.getDbSchId();
			this.dbName = targetDb.getDbms_enm();
			this.dbType = targetDb.getDbms_type_cd();
			this.dbms_id = targetDb.getDbms_id();
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
			logger.debug(sp + (++p) + ". UPDATE_data_cpct " + data_cpct + " OK!!");

			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");

			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");

			cnt = insertDBA_CONSTRAINTS();
			logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

			cnt = insertDBA_CONS_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_CONS_COLUMNS " + cnt + " OK!!");
						
			cnt = insertDBA_OBJECTS();
			logger.debug(sp + (++p) + ". insertDBA_OBJECTS " + cnt + " OK!!");
			
			cnt = insertDBA_COLUMNS_INFO();
			logger.debug(sp + (++p) + ". insertDBA_COLUMNS_INFO " + cnt + " OK!!");
			
			data_cpct = updateDataCpct();
			logger.debug(sp + (++p) + ". updateDataCpct " + data_cpct + " OK!!");
			
			con_org.commit();
		}
		result = true;

		return result;
	}

	private int insertDBA_OBJECTS() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\nSELECT OWNER                 ");
		sb.append("\n      ,OBJECT_NAME           ");
		sb.append("\n      ,SUBOBJECT_NAME        ");
		sb.append("\n      ,OBJECT_ID             ");
		sb.append("\n      ,DATA_OBJECT_ID        ");
		sb.append("\n      ,OBJECT_TYPE           ");
		sb.append("\n      ,CREATED               ");
		sb.append("\n      ,LAST_DDL_TIME         ");
		sb.append("\n      ,TIMESTAMP             ");
		sb.append("\n      ,STATUS                ");
		sb.append("\n      ,TEMPORARY             ");
		sb.append("\n      ,GENERATED             ");
		sb.append("\n      ,SECONDARY             ");
		sb.append("\n      ,'").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n  FROM DBA_OBJECTS");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");
		getResultSet(sb.toString());

		sb.setLength(0);
		sb.append("\nINSERT INTO WAE_ORA_OBJECTS ( ");
		sb.append("\n   DB_NM                      ");
		sb.append("\n  ,SCH_NM                     ");
		sb.append("\n  ,OBJECT_NAME                ");
		sb.append("\n  ,SUBOBJECT_NAME             ");
		sb.append("\n  ,OBJECT_ID                  ");
		sb.append("\n  ,DATA_OBJECT_ID             ");
		sb.append("\n  ,OBJECT_TYPE                ");
		sb.append("\n  ,CREATED                    ");
		sb.append("\n  ,LAST_DDL_TIME              ");
		sb.append("\n  ,TIMESTAMP                 ");
		sb.append("\n  ,STATUS                     ");
		sb.append("\n  ,TEMPORARY                 ");
		sb.append("\n  ,GENERATED                  ");
		sb.append("\n  ,SECONDARY                  ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?                   ");
		sb.append("\n)                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("SUBOBJECT_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("OBJECT_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_OBJECT_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_TYPE"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CREATED"));
				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_DDL_TIME"));
				pst_org.setString(rsGetCnt++, rs.getString("TIMESTAMP"));
				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("TEMPORARY"));
				pst_org.setString(rsGetCnt++, rs.getString("GENERATED"));
				pst_org.setString(rsGetCnt++, rs.getString("SECONDARY"));
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
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return cnt;
	}
	
	private int insertDBA_COLUMNS_INFO() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\n SELECT A.CONSTRAINT_NAME AS CONSTRAINT_NAME,					");
		sb.append("\n	     A.TABLE_NAME AS TABLE_NAME,    	        			");
		sb.append("\n		 A.COLUMN_NAME AS COLUMN_NAME,							");
		sb.append("\n		 C.OWNER AS OWNER,										");
		sb.append("\n 		 C_PK.TABLE_NAME AS R_TABLE_NAME,						");
		sb.append("\n 		 B.COLUMN_NAME AS R_COLUMN_NAME							");
		sb.append("\n FROM ALL_CONS_COLUMNS A, ALL_CONSTRAINTS C,					");
		sb.append("\n 		ALL_CONSTRAINTS C_PK, ALL_CONS_COLUMNS B				");
		sb.append("\n WHERE A.OWNER = C.OWNER										");
		sb.append("\n AND A.CONSTRAINT_NAME = C.CONSTRAINT_NAME						");
		sb.append("\n AND C.R_OWNER = C_PK.OWNER									");
		sb.append("\n AND C.R_CONSTRAINT_NAME = C_PK.CONSTRAINT_NAME				");
		sb.append("\n AND C_PK.OWNER = B.OWNER										");
		sb.append("\n AND C_PK.CONSTRAINT_NAME = B.CONSTRAINT_NAME					");
		sb.append("\n AND B.POSITION = A.POSITION									");
		sb.append("\n AND C.CONSTRAINT_TYPE = 'R'									");
		sb.append("\n AND C.OWNER = '"+schemaName+"' 								");
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_ORA_COL_INFO	");
		sb.append("\n (   DB_NM 					");
		sb.append("\n  , SCH_NM						");
		sb.append("\n  , TBL_NM						");
		sb.append("\n  , COL_NM                   	");
		sb.append("\n  , FK_INFO 					");
		sb.append("\n  , CHECK_INFO					");
		sb.append("\n  , DB_CONN_TRG_ID 			");	
		sb.append("\n  )                              ");
		sb.append("\n  VALUES                         ");
		sb.append("\n  (  TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?)   ");
		sb.append("\n   , TRIM(?), TRIM(?)								");
		sb.append("\n   )                                       		");
		//logger.debug("\n " + sb.toString());
		  		                      		
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
  				pst_org.setString(rsGetCnt++, rs.getString(5)+"."+rs.getString(6));
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
	 * DBC DBA_CONS_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONS_COLUMNS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                                 ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n    , OWNER                             ");
		sb.append("\n    , TABLE_NAME                        ");
		sb.append("\n    , CONSTRAINT_NAME                   ");
		sb.append("\n    , COLUMN_NAME AS CNST_COND_COL_NM   ");
		sb.append("\n    , COLUMN_NAME AS COL_NM             ");
		sb.append("\n    , POSITION                          ");
		sb.append("\n  FROM DBA_CONS_COLUMNS                 ");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_ORA_CND_COL (     ");
		sb.append("\n   DB_NM                          ");
		sb.append("\n  , SCH_NM                        ");
		sb.append("\n  ,TBL_NM                         ");
		sb.append("\n  ,CNST_CND_NM                    ");
		sb.append("\n  ,CNST_CND_COL_NM                ");
		sb.append("\n  ,COL_NM                         ");
		sb.append("\n  ,SEQ                            ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?,?,?,?,?                 ");
		sb.append("\n)                                 ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CNST_COND_COL_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("COL_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("POSITION"));
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
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return cnt;
	}
	
	/**
	 * DBC DBA_CONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONSTRAINTS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      , OWNER                       ");
		sb.append("\n      , TABLE_NAME                  ");
		sb.append("\n      , CONSTRAINT_NAME             ");
		sb.append("\n      , CONSTRAINT_TYPE             ");
		sb.append("\n      , SEARCH_CONDITION            ");
		sb.append("\n      , R_OWNER                     ");
		sb.append("\n      , R_CONSTRAINT_NAME           ");
		sb.append("\n      , DELETE_RULE                 ");
		sb.append("\n      , STATUS                      ");
		sb.append("\n      , DEFERRABLE                  ");
		sb.append("\n      , DEFERRED                    ");
		sb.append("\n      , VALIDATED                   ");
		sb.append("\n      , GENERATED                   ");
		sb.append("\n      , BAD                         ");
		sb.append("\n      , RELY                        ");
		sb.append("\n      , LAST_CHANGE                 ");
		sb.append("\n      , NULL   AS INDEX_OWNER              ");
		sb.append("\n      , NULL   AS INDEX_NAME               ");
		sb.append("\n      , NULL   AS INVALID                ");
		sb.append("\n      , NULL   AS VIEW_RELATED           ");
		sb.append("\n   FROM DBA_CONSTRAINTS             ");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_ORA_CND (");
		sb.append("\n        DB_NM                       ");
		sb.append("\n      , SCH_NM                      ");
		sb.append("\n      , TBL_NM                      ");
		sb.append("\n      , CNST_CND_NM                 ");
		sb.append("\n      , CNST_TYPE                   ");
		sb.append("\n      , SEARCH_CONDITION            ");
		sb.append("\n      , R_OWNER                     ");
		sb.append("\n      , R_CONSTRAINT_NAME           ");
		sb.append("\n      , DELETE_RULE                 ");
		sb.append("\n      , STATUS                      ");
		sb.append("\n      , DEFERRABLE                 ");
		sb.append("\n      , DEFERRED                   ");
		sb.append("\n      , VALIDATED                   ");
		sb.append("\n      , GENERATED                 ");
		sb.append("\n      , BAD                         ");
		sb.append("\n      , RELY                        ");
		sb.append("\n      , LAST_CHANGE                 ");
		sb.append("\n      , INDEX_OWNER                 ");
		sb.append("\n      , INDEX_NAME                  ");
		sb.append("\n      , INVALID                     ");
		sb.append("\n      , VIEW_RELATED                ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                          ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,              ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,              ");
		sb.append("\n  ?,?                               ");
		sb.append("\n)                                   ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("SEARCH_CONDITION"));
				pst_org.setString(rsGetCnt++, rs.getString("R_OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("R_CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DELETE_RULE"));
				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFERRABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFERRED"));
				pst_org.setString(rsGetCnt++, rs.getString("VALIDATED"));
				pst_org.setString(rsGetCnt++, rs.getString("GENERATED"));
				pst_org.setString(rsGetCnt++, rs.getString("BAD"));
				pst_org.setString(rsGetCnt++, rs.getString("RELY"));
				pst_org.setDate(rsGetCnt++,   rs.getDate("LAST_CHANGE"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("INVALID"));
				pst_org.setString(rsGetCnt++, rs.getString("VIEW_RELATED"));
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
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return cnt;
	}

	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		if(dbmsVersion.equals("")) {
			sb.append("\n SELECT                                                              ");
			sb.append("\n      '").append(dbName).append("' AS OF_DATABASE                    ");
			sb.append("\n         ,A.OWNER                                                    ");
			sb.append("\n         ,A.TABLE_NAME                                               ");
			sb.append("\n         ,A.COLUMN_NAME                                              ");
			sb.append("\n         ,C.COMMENTS                                                 ");
			sb.append("\n         ,A.DATA_TYPE                                                ");
			sb.append("\n         ,DATA_TYPE_MOD                                              ");
			sb.append("\n         ,DATA_TYPE_OWNER                                            ");
			sb.append("\n         ,CASE A.DATA_TYPE WHEN 'CLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'BLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'TEXT' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATE' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATETIME' THEN NULL                 ");
			sb.append("\n                           WHEN 'TIMESTAMP' THEN NULL                ");
			sb.append("\n                           WHEN 'NUMBER' THEN A.DATA_PRECISION       ");
			sb.append("\n          ELSE A.DATA_LENGTH END DATA_LENGTH                         ");
			sb.append("\n         ,CASE A.DATA_TYPE WHEN 'CLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'BLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'TEXT' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATE' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATETIME' THEN NULL                 ");
			sb.append("\n                           WHEN 'TIMESTAMP' THEN NULL                ");
			sb.append("\n          ELSE A.DATA_PRECISION END DATA_PRECISION                   ");
			sb.append("\n         ,CASE A.DATA_TYPE WHEN 'CLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'BLOB' THEN NULL                     ");
			sb.append("\n                           WHEN 'TEXT' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATE' THEN NULL                     ");
			sb.append("\n                           WHEN 'DATETIME' THEN NULL                 ");
			sb.append("\n                           WHEN 'TIMESTAMP' THEN NULL                ");
			sb.append("\n          ELSE CASE WHEN A.DATA_TYPE = 'NUMBER' AND A.DATA_PRECISION IS NULL THEN NULL ELSE A.DATA_SCALE END  END DATA_SCALE   ");
			sb.append("\n         ,A.NULLABLE                                                 ");
			sb.append("\n         ,A.COLUMN_ID                                                ");
			sb.append("\n         ,A.DEFAULT_LENGTH                                           ");
			sb.append("\n         ,A.DATA_DEFAULT                                             ");
			sb.append("\n         ,NUM_DISTINCT                                               ");
			sb.append("\n         ,LOW_VALUE                                                  ");
			sb.append("\n         ,HIGH_VALUE                                                 ");
			sb.append("\n         ,DENSITY                                                    ");
			sb.append("\n         ,NUM_NULLS                                                  ");
			sb.append("\n         ,NUM_BUCKETS                                                ");
			sb.append("\n         ,LAST_ANALYZED                                              ");
			sb.append("\n         ,SAMPLE_SIZE                                                ");
			sb.append("\n         ,CHARACTER_SET_NAME                                         ");
			sb.append("\n         ,CHAR_COL_DECL_LENGTH                                       ");
			sb.append("\n         ,GLOBAL_STATS                                               ");
			sb.append("\n         ,USER_STATS                                                 ");
			sb.append("\n         ,AVG_COL_LEN                                                ");
			sb.append("\n   FROM DBA_TAB_COLUMNS A                                            ");
			sb.append("\n  INNER JOIN DBA_OBJECTS B                                           ");
			sb.append("\n     ON A.OWNER = B.OWNER                                            ");
			sb.append("\n    AND A.TABLE_NAME = B.OBJECT_NAME                                 ");
			sb.append("\n    AND B.OBJECT_TYPE = 'TABLE'                                      ");
			sb.append("\n    AND B.OWNER = '").append(schemaName).append("'");
			sb.append("\n  INNER JOIN DBA_COL_COMMENTS C                                      ");
			sb.append("\n     ON A.OWNER = C.OWNER                                            ");
			sb.append("\n    AND A.TABLE_NAME = C.TABLE_NAME                                  ");
			sb.append("\n    AND A.COLUMN_NAME = C.COLUMN_NAME                                ");
		} else {
			sb.append("\n SELECT                                                              ");
			sb.append("\n      '").append(dbName).append("' AS OF_DATABASE                    ");
			sb.append("\n         ,A.OWNER                                                    ");
			sb.append("\n         ,A.TABLE_NAME                                               ");
			sb.append("\n         ,A.COLUMN_NAME                                              ");
			sb.append("\n         ,C.COMMENTS                                                 ");
			sb.append("\n         ,A.DATA_TYPE                                                ");
			sb.append("\n         ,DATA_TYPE_MOD                                              ");
			sb.append("\n         ,DATA_TYPE_OWNER                                            ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_LENGTH)  AS DATA_LENGTH ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_PRECISION)  AS DATA_PRECISION ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_SCALE)  AS DATA_SCALE ");
			sb.append("\n         ,A.NULLABLE                                                 ");
			sb.append("\n         ,A.COLUMN_ID                                                ");
			sb.append("\n         ,A.DEFAULT_LENGTH                                           ");
			sb.append("\n         ,A.DATA_DEFAULT                                             ");
			sb.append("\n         ,NUM_DISTINCT                                               ");
			sb.append("\n         ,LOW_VALUE                                                  ");
			sb.append("\n         ,HIGH_VALUE                                                 ");
			sb.append("\n         ,DENSITY                                                    ");
			sb.append("\n         ,NUM_NULLS                                                  ");
			sb.append("\n         ,NUM_BUCKETS                                                ");
			sb.append("\n         ,LAST_ANALYZED                                              ");
			sb.append("\n         ,SAMPLE_SIZE                                                ");
			sb.append("\n         ,CHARACTER_SET_NAME                                         ");
			sb.append("\n         ,CHAR_COL_DECL_LENGTH                                       ");
			sb.append("\n         ,GLOBAL_STATS                                               ");
			sb.append("\n         ,USER_STATS                                                 ");
			sb.append("\n         ,AVG_COL_LEN                                                ");
			sb.append("\n   FROM DBA_TAB_COLUMNS A                                            ");
			sb.append("\n      , DBA_OBJECTS B                                           ");
			sb.append("\n      , DBA_COL_COMMENTS C                                      ");
			sb.append("\n   WHERE A.OWNER = B.OWNER                                            ");
			sb.append("\n    AND A.TABLE_NAME = B.OBJECT_NAME                                 ");
			sb.append("\n    AND B.OBJECT_TYPE = 'TABLE'                                      ");
			sb.append("\n    AND B.OWNER = '").append(schemaName).append("'");
			sb.append("\n    AND A.OWNER = C.OWNER                                            ");
			sb.append("\n    AND A.TABLE_NAME = C.TABLE_NAME                                  ");
			sb.append("\n    AND A.COLUMN_NAME = C.COLUMN_NAME                                ");
		}
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_ORA_COL (            ");
		sb.append("\n        DB_NM                        ");
		sb.append("\n        ,SCH_NM                      ");
		sb.append("\n        ,TBL_NM                      ");
		sb.append("\n        ,COL_NM                      ");
		sb.append("\n        ,COL_CMMT                    ");
		sb.append("\n        ,DATA_TYPE                   ");
		sb.append("\n        ,DATA_TYPE_MOD               ");
		sb.append("\n        ,DATA_TYPE_OWNER             ");
		sb.append("\n        ,DATA_LEN                    ");
		sb.append("\n        ,DATA_PNUM                   ");
		sb.append("\n        ,DATA_PNT                    ");
		sb.append("\n        ,NULL_YN                     ");
		sb.append("\n        ,COL_ID                      ");
		sb.append("\n        ,DEFLT_LEN                   ");
		sb.append("\n        ,DEFLT_VAL                   ");
		sb.append("\n        ,NUM_DISTINCT                ");
		sb.append("\n        ,LOW_VALUE                   ");
		sb.append("\n        ,HIGH_VALUE                  ");
		sb.append("\n        ,DENSITY                     ");
		sb.append("\n        ,NUM_NULLS                   ");
		sb.append("\n        ,NUM_BUCKETS                 ");
		sb.append("\n        ,LAST_ANALYZED               ");
		sb.append("\n        ,SAMPLE_SIZE                 ");
		sb.append("\n        ,CHARACTER_SET_NAME          ");
		sb.append("\n        ,CHAR_COL_DECL_LENGTH        ");
		sb.append("\n        ,GLOBAL_STATS                ");
		sb.append("\n        ,USER_STATS                  ");
		sb.append("\n        ,AVG_COL_LEN                 ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                           ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,               ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,               ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?                  ");
		sb.append("\n)                                    ");

		int cnt = 0;
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
				pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE_MOD"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE_OWNER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_PRECISION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_SCALE"));
				pst_org.setString(rsGetCnt++, rs.getString("NULLABLE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLUMN_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DEFAULT_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_DEFAULT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_DISTINCT"));
				pst_org.setString(rsGetCnt++, rs.getString("LOW_VALUE"));
				pst_org.setString(rsGetCnt++, rs.getString("HIGH_VALUE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DENSITY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_NULLS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_BUCKETS"));
				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_ANALYZED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SAMPLE_SIZE"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHAR_COL_DECL_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("GLOBAL_STATS"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_STATS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_COL_LEN"));
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
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return cnt;
	}

	/**
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                                   "); 
		sb.append("\n      '").append(dbName).append("' AS OF_SYSTEM"); // 데이터베이스명
		sb.append("\n      , A.OWNER                           "); // 스키마명
		sb.append("\n      , A.TABLE_NAME                      "); // 테이블명
		sb.append("\n      , B.COMMENTS                        "); // 테이블한글명
		sb.append("\n      , A.TABLESPACE_NAME                 "); // 테이블스페이스
		sb.append("\n      , A.CLUSTER_NAME                    ");
		sb.append("\n      , A.IOT_NAME                        ");
		sb.append("\n      , A.PCT_FREE                        ");
		sb.append("\n      , A.PCT_USED                        ");
		sb.append("\n      , A.INI_TRANS                       ");
		
		sb.append("\n      , A.MAX_TRANS                       ");
		sb.append("\n      , A.INITIAL_EXTENT                  ");
		sb.append("\n      , A.NEXT_EXTENT                     ");
		sb.append("\n      , A.MIN_EXTENTS                     ");
		sb.append("\n      , A.MAX_EXTENTS                     ");
		sb.append("\n      , A.PCT_INCREASE                    ");
		sb.append("\n      , A.FREELISTS                       ");
		sb.append("\n      , A.FREELIST_GROUPS                 ");
		sb.append("\n      , A.LOGGING                         ");
		sb.append("\n      , A.BACKED_UP                       ");
		
		sb.append("\n      , A.NUM_ROWS                        ");
		sb.append("\n      , A.BLOCKS                          ");
		sb.append("\n      , A.EMPTY_BLOCKS                    ");
		sb.append("\n      , A.AVG_SPACE                       ");
		sb.append("\n      , A.CHAIN_CNT                       ");
		sb.append("\n      , ROUND(A.AVG_ROW_LEN, 2) AS AVG_ROW_LEN    ");	//20190516 값이 22자리 이상으로 인해 오류 발생.
		sb.append("\n      , A.AVG_SPACE_FREELIST_BLOCKS       ");
		sb.append("\n      , A.NUM_FREELIST_BLOCKS             ");
		sb.append("\n      , A.DEGREE                          ");
		sb.append("\n      , A.INSTANCES                       ");
		
		sb.append("\n      , A.CACHE                           ");
		sb.append("\n      , A.TABLE_LOCK                      ");
		sb.append("\n      , A.SAMPLE_SIZE                     ");
		sb.append("\n      , A.LAST_ANALYZED                   ");
		sb.append("\n      , A.PARTITIONED                     ");
		sb.append("\n      , A.IOT_TYPE                        ");
		sb.append("\n      , A.TEMPORARY                       ");
		sb.append("\n      , A.SECONDARY                       ");
		sb.append("\n      , A.NESTED                          ");
		sb.append("\n      , A.BUFFER_POOL                     ");
		
		sb.append("\n      , A.ROW_MOVEMENT                    ");
		sb.append("\n      , A.GLOBAL_STATS                    ");
		sb.append("\n      , A.USER_STATS                      ");
		sb.append("\n      , A.DURATION                        ");
		sb.append("\n      , A.SKIP_CORRUPT                    ");
		sb.append("\n      , A.MONITORING                      ");
		sb.append("\n      , A.CLUSTER_OWNER                   ");
		sb.append("\n   FROM DBA_TABLES A                      ");
		sb.append("\n  , DBA_TAB_COMMENTS B           ");
		sb.append("\n     WHERE A.TABLE_NAME = B.TABLE_NAME       ");
		sb.append("\n    AND A.OWNER = B.OWNER                 ");
		sb.append("\n    AND B.TABLE_TYPE = 'TABLE'            ");
		sb.append("\n AND A.OWNER = '").append(schemaName).append("'");
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_ORA_TBL (    ");
		sb.append("\n        DB_NM                           "); // 데이터베이스명
		sb.append("\n      , SCH_NM                          "); // 스키마명
		sb.append("\n      , TBL_NM                          "); // 테이블명
		sb.append("\n      , TBL_CMMT                        "); // 테이블한글명
		sb.append("\n      , TBL_SPAC_NM                     "); // 테이블스페이스
		sb.append("\n      , CLUSTER_NAME                    ");
		sb.append("\n      , IOT_NAME                        ");
		sb.append("\n      , PCT_FREE                        ");
		sb.append("\n      , PCT_USED                        ");
		sb.append("\n      , INI_TRANS                       ");
		
		sb.append("\n      , MAX_TRANS                       ");
		sb.append("\n      , INITIAL_EXTENT                  ");
		sb.append("\n      , NEXT_EXTENT                     ");
		sb.append("\n      , MIN_EXTENTS                     ");
		sb.append("\n      , MAX_EXTENTS                     ");
		sb.append("\n      , PCT_INCREASE                    ");
		sb.append("\n      , FREELISTS                       ");
		sb.append("\n      , FREELIST_GROUPS                 ");
		sb.append("\n      , LOGGING                         ");
		sb.append("\n      , BACKED_UP                       ");

		sb.append("\n      , NUM_ROWS                        ");
		sb.append("\n      , BLOCKS                          ");
		sb.append("\n      , EMPTY_BLOCKS                    ");
		sb.append("\n      , AVG_SPACE                       ");
		sb.append("\n      , CHAIN_CNT                       ");
		sb.append("\n      , AVG_ROW_LEN                     ");
		sb.append("\n      , AVG_SPACE_FREELIST_BLOCKS       ");
		sb.append("\n      , NUM_FREELIST_BLOCKS             ");
		sb.append("\n      , DEGREE                          ");
		sb.append("\n      , INSTANCES                       ");
		
		sb.append("\n      , CACHE                           ");
		sb.append("\n      , TABLE_LOCK                      ");
		sb.append("\n      , SAMPLE_SIZE                     ");
		sb.append("\n      , LAST_ANALYZED                   ");
		sb.append("\n      , PARTITIONED                     ");
		sb.append("\n      , IOT_TYPE                        ");
		sb.append("\n      , TEMPORARY                       ");
		sb.append("\n      , SECONDARY                       ");
		sb.append("\n      , NESTED                          ");
		sb.append("\n      , BUFFER_POOL                     ");

		sb.append("\n      , ROW_MOVEMENT                    ");
		sb.append("\n      , GLOBAL_STATS                    ");
		sb.append("\n      , USER_STATS                      ");
		sb.append("\n      , DURATION                        ");
		sb.append("\n      , SKIP_CORRUPT                    ");
		sb.append("\n      , MONITORING                      ");
		sb.append("\n      , CLUSTER_OWNER                   ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                              ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?                       ");
		sb.append("\n)                                       ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLESPACE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CLUSTER_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("IOT_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_FREE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_USED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INI_TRANS"));

				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_TRANS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INITIAL_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NEXT_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_INCREASE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELISTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELIST_GROUPS"));
				pst_org.setString(rsGetCnt++, rs.getString("LOGGING"));
				pst_org.setString(rsGetCnt++, rs.getString("BACKED_UP"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_ROWS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("EMPTY_BLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHAIN_CNT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_ROW_LEN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_SPACE_FREELIST_BLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_FREELIST_BLOCKS"));
				pst_org.setString(rsGetCnt++, rs.getString("DEGREE"));
				pst_org.setString(rsGetCnt++, rs.getString("INSTANCES"));
				
				pst_org.setString(rsGetCnt++, rs.getString("CACHE"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_LOCK"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SAMPLE_SIZE"));
				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_ANALYZED"));
				pst_org.setString(rsGetCnt++, rs.getString("PARTITIONED"));
				pst_org.setString(rsGetCnt++, rs.getString("IOT_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("TEMPORARY"));
				pst_org.setString(rsGetCnt++, rs.getString("SECONDARY"));
				pst_org.setString(rsGetCnt++, rs.getString("NESTED"));
				pst_org.setString(rsGetCnt++, rs.getString("BUFFER_POOL"));
				
				pst_org.setString(rsGetCnt++, rs.getString("ROW_MOVEMENT"));
				pst_org.setString(rsGetCnt++, rs.getString("GLOBAL_STATS"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_STATS"));
				pst_org.setString(rsGetCnt++, rs.getString("DURATION"));
				pst_org.setString(rsGetCnt++, rs.getString("SKIP_CORRUPT"));
				pst_org.setString(rsGetCnt++, rs.getString("MONITORING"));
				pst_org.setString(rsGetCnt++, rs.getString("CLUSTER_OWNER"));
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
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

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
		pst_tgt.setFetchSize(1000);
		rs = pst_tgt.executeQuery();

		return rs;
	}
		
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_ORA_OBJECTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_OBJECTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ORA_CND ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_CND : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ORA_CND_COL ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_CND_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ORA_COL ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ORA_TBL ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_TBL : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ORA_COL_INFO ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_COL_INFO : " + result);

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
		con_org.commit();
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
		sb.append("\n INSERT INTO WAT_DBC_TBL (                    ");
		sb.append("\n        DB_SCH_ID                             ");
		sb.append("\n      , DBC_TBL_NM                            ");
		sb.append("\n      , DBC_TBL_KOR_NM                        ");
		sb.append("\n      , OBJ_VERS                              ");
		sb.append("\n      , REG_TYP_CD                            ");
		sb.append("\n      , REG_DTM                               ");
		sb.append("\n      , UPD_DTM                               ");
		sb.append("\n      , DESCN                                 ");
		sb.append("\n      , DB_CONN_TRG_ID                        ");
		sb.append("\n      , DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ROW_EACNT                             ");
		sb.append("\n      , DATA_SIZE                             ");
		sb.append("\n      , NUSE_SIZE                             ");
		sb.append("\n      , TBL_ANA_DTM                           ");
		sb.append("\n      , TBL_CRT_DTM                           ");
		sb.append("\n      , TBL_CHG_DTM                           ");
		sb.append("\n      , TBL_CLLT_DCD                          ");
		sb.append("\n )                                            ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.TBL_NM              AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TBL_CMMT            AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                  AS REG_TYP                               ");
		sb.append("\n      , SYSDATE           AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , A.TBL_SPAC_NM         AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , A.NUM_ROWS            AS ROW_EACNT                             ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , NULL                  AS ANA_DTM                               ");
		sb.append("\n      , O.CREATED             AS CRT_DTM                               ");
		sb.append("\n      , NULL                  AS CHG_DTM                               ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_ORA_TBL A                                                  ");
		sb.append("\n  INNER JOIN WAE_ORA_OBJECTS O                                         ");
		sb.append("\n     ON A.DB_NM = O.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = O.SCH_NM                                            ");
		sb.append("\n    AND A.TBL_NM = O.OBJECT_NAME                                       ");
		sb.append("\n    AND A.DB_CONN_TRG_ID = O.DB_CONN_TRG_ID            ");
		sb.append("\n    AND O.OBJECT_TYPE = 'TABLE'                                        ");
		sb.append("\n  INNER JOIN (                                                         ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                                                                  ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                                    ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                                        ");
		sb.append("\n    AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID            ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                       ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.TBL_NM NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
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
		sb.append("\n )                                                            ");
		sb.append("\n SELECT S.DB_SCH_ID                                           ");
		sb.append("\n      , A.TBL_NM                                              ");
		sb.append("\n      , A.COL_NM                                              ");
		sb.append("\n      , COL_CMMT                                              ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                  ");
		sb.append("\n      , SYSDATE                                           ");
		sb.append("\n      , NULL                                                  ");
		sb.append("\n      , DATA_TYPE                                             ");
		sb.append("\n      , DATA_LEN                                              ");
		sb.append("\n      , DATA_PNUM                                             ");
		sb.append("\n      , DATA_PNT                                              ");
		sb.append("\n      , NULL_YN                                               ");
		sb.append("\n      , DEFLT_LEN                                             ");
		sb.append("\n      , TRIM(DEFLT_VAL)                                       ");
		sb.append("\n      , DECODE(C.DB_NM, NULL, 'N', 'Y') AS PK_YN              ");
		sb.append("\n      , F.FK_INFO						  AS FK_YN             ");
		sb.append("\n      , A.COL_ID                                              ");
		sb.append("\n      , C.SEQ                                                 ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n   FROM WAE_ORA_COL A                                         ");
		sb.append("\n  INNER JOIN (                                                ");
		sb.append(getdbconnsql());
		sb.append("\n             ) S                                              ");
		sb.append("\n          ON A.DB_NM = S.DB_CONN_TRG_PNM                      ");
		sb.append("\n         AND A.SCH_NM = S.DB_SCH_PNM                          ");
		sb.append("\n         AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID              ");
		sb.append("\n  LEFT OUTER JOIN WAE_ORA_CND D                               ");
		sb.append("\n               ON A.DB_NM = D.DB_NM                           ");
		sb.append("\n              AND A.SCH_NM = D.SCH_NM                         ");
		sb.append("\n              AND A.TBL_NM = D.TBL_NM                         ");
		sb.append("\n              AND A.DB_CONN_TRG_ID = D.DB_CONN_TRG_ID         ");
		sb.append("\n              AND D.CNST_TYPE = 'P'                           ");
		sb.append("\n  LEFT OUTER JOIN WAE_ORA_CND_COL C                           ");
		sb.append("\n               ON A.DB_NM = C.DB_NM                           ");
		sb.append("\n              AND A.SCH_NM = C.SCH_NM                         ");
		sb.append("\n              AND A.TBL_NM = C.TBL_NM                         ");
		sb.append("\n              AND A.COL_NM = C.CNST_CND_COL_NM                ");
		sb.append("\n              AND A.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID         ");
		sb.append("\n              AND C.CNST_CND_NM = D.CNST_CND_NM               ");
		sb.append("\n              AND C.SEQ IS NOT NULL                           ");
		sb.append("\n	LEFT JOIN (										");
		sb.append("\n		SELECT DB_CONN_TRG_ID, DB_NM, SCH_NM, TBL_NM, COL_NM, WM_CONCAT(FK_INFO) FK_INFO		");
		sb.append("\n		FROM WAE_ORA_COL_INFO 														");
		sb.append("\n		GROUP BY DB_NM, SCH_NM, TBL_NM, COL_NM, DB_CONN_TRG_ID							");
		sb.append("\n				) F												");
		sb.append("\n	ON	A.DB_NM = F.DB_NM										");
		sb.append("\n	AND A.SCH_NM = F.SCH_NM									");
		sb.append("\n	AND A.TBL_NM  = F.TBL_NM								");
		sb.append("\n	AND A.COL_NM = F.COL_NM								");
		sb.append("\n	AND A.DB_CONN_TRG_ID = F.DB_CONN_TRG_ID						");
		sb.append("\n WHERE A.DB_NM = '"+dbName+"'                                 ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.TBL_NM NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)													");

		return setExecuteUpdate_Org(sb.toString());
	}
	
	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n             INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                     ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n                    AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                    AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n             WHERE 1=1 ");
		strSQL.append("\n               AND T.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n               AND T.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n               AND T.DB_CONN_TRG_ID = '"+dbms_id+"'");

		return strSQL.toString();
	}
	
	private int updateTblCnt() throws SQLException {
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
		tgtSQL = tgtSQL.append("SELECT TO_CHAR(sum(bytes/1024/1024)) AS MB FROM DBA_SEGMENTS WHERE OWNER = '"+schemaName+"' ");
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
