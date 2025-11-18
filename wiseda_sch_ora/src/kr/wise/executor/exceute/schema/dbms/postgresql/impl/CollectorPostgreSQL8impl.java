package kr.wise.executor.exceute.schema.dbms.postgresql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.postgresql.CollectorPostgresqlService;
import org.apache.log4j.Logger;

public class CollectorPostgreSQL8impl implements CollectorPostgresqlService {

	private static final Logger logger = Logger.getLogger(CollectorPostgreSQL8impl.class);

	private Connection con_org = null;
	private Connection con_tgt = null;
	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;
	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;
	private String dbms_id = null;
	private String dbType = null;	
	private int execCnt = 100; 
	private String data_cpct = "";

	public CollectorPostgreSQL8impl() {

	}

	public CollectorPostgreSQL8impl(Connection source, Connection target, List<TargetDbmsDM> lsitdm) {
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
		con_org.setAutoCommit(false);

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
			logger.debug(sp + (++p) + ". updateDataCpct " + data_cpct + " OK!!");
			
			cnt = insertPG_STAT_USER_TABLES();
			logger.debug(sp + (++p) + ". insertTABLES " + cnt + " OK!!");	
			
			cnt = insertPG_DESCRIPTTION();
			logger.debug(sp + (++p) + ". insertPG_DESCRIPTTION " + cnt + " OK!!");
			
			cnt = insertCOLUMNS();
			logger.debug(sp + (++p) + ". insertCOLUMNS " + cnt + " OK!!");
			
			cnt = insertPG_ATTRIBUTE();
			logger.debug(sp + (++p) + ". insertPG_ATTRIBUTE " + cnt + " OK!!");
			
			cnt = insertTABLE_CONSTRAINTS();
			logger.debug(sp + (++p) + ". insertTABLE_CONSTRAINTS " + cnt + " OK!!");

			cnt = insertCONSTRAINT_COLUMN_USAGE();
			logger.debug(sp + (++p) + ". insertCONSTRAINT_COLUMN_USAGE " + cnt + " OK!!");
			
			cnt = insertKEY_COLUMN_USAGE();
			logger.debug(sp + (++p) + ". insertKEY_COLUMN_USAGE " + cnt + " OK!!");
			
			cnt = insertDBA_COLUMNS_INFO();
			logger.debug(sp + (++p) + ". insertDBA_COLUMNS_INFO " + cnt + " OK!!");
			
			con_org.commit();
		}
		result = true;

		return result;
	}

	private int insertPG_ATTRIBUTE() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , A.ATTRELID	      ");
		sb.append("\n      , A.ATTNAME	      ");
		sb.append("\n      , A.ATTTYPID	      ");
		sb.append("\n      , A.ATTSTATTARGET	  ");
		sb.append("\n      , A.ATTLEN	          ");
		sb.append("\n      , A.ATTNUM	          ");
		sb.append("\n      , A.ATTNDIMS	      ");
		sb.append("\n      , A.ATTCACHEOFF	  ");
		sb.append("\n      , A.ATTTYPMOD	      ");
		sb.append("\n      , A.ATTBYVAL	      ");
		sb.append("\n      , A.ATTSTORAGE	      ");
		sb.append("\n      , A.ATTALIGN	      ");
		sb.append("\n      , A.ATTNOTNULL	      ");
		sb.append("\n      , A.ATTHASDEF	      ");
		sb.append("\n      , A.ATTISDROPPED	  ");
		sb.append("\n      , A.ATTISLOCAL	      ");
		sb.append("\n      , A.ATTINHCOUNT	  ");
		sb.append("\n      , A.ATTACL	          ");
		sb.append("\n   FROM PG_ATTRIBUTE A    ");
		sb.append("\n INNER JOIN PG_STAT_USER_TABLES B ");
		sb.append("\n         ON A.ATTRELID = B.RELID  ");    
		sb.append("\n WHERE A.ATTSTATTARGET != 0       "); 
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_PG_ATTRIBUTE (");
		sb.append("\n    	 DB_NM                  ");
		sb.append("\n      , SCH_NM                 ");
		sb.append("\n      , ATTRELID	            ");
		sb.append("\n      , ATTNAME	            ");
		sb.append("\n      , ATTTYPID	            "); //5
		sb.append("\n      , ATTSTATTARGET	        ");
		sb.append("\n      , ATTLEN	                ");
		sb.append("\n      , ATTNUM	                ");
		sb.append("\n      , ATTNDIMS	            ");
		sb.append("\n      , ATTCACHEOFF	        "); //10
		sb.append("\n      , ATTTYPMOD	            ");
		sb.append("\n      , ATTBYVAL	            ");
		sb.append("\n      , ATTSTORAGE	            ");
		sb.append("\n      , ATTALIGN	            ");
		sb.append("\n      , ATTNOTNULL	            "); //15
		sb.append("\n      , ATTHASDEF	            ");
		sb.append("\n      , ATTISDROPPED	        ");
		sb.append("\n      , ATTISLOCAL	            ");
		sb.append("\n      , ATTINHCOUNT	        "); //20
		sb.append("\n      , ATTACL	                ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                    "); 
		sb.append("\n     ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?                   		");
		sb.append("\n )                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("ATTRELID"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTNAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTTYPID"));   //5
				pst_org.setString(rsGetCnt++, rs.getString("ATTSTATTARGET"));				
				pst_org.setString(rsGetCnt++, rs.getString("ATTLEN"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTNUM"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTNDIMS"));				
				pst_org.setString(rsGetCnt++, rs.getString("ATTCACHEOFF"));  //10
				pst_org.setString(rsGetCnt++, rs.getString("ATTTYPMOD")); 
				pst_org.setString(rsGetCnt++, rs.getString("ATTBYVAL"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTSTORAGE"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTALIGN"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTNOTNULL"));   //15
				pst_org.setString(rsGetCnt++, rs.getString("ATTHASDEF"));  
				pst_org.setString(rsGetCnt++, rs.getString("ATTISDROPPED"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTISLOCAL"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTINHCOUNT")); //20
				pst_org.setString(rsGetCnt++, rs.getString("ATTACL"));       
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

	private int insertPG_DESCRIPTTION() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n     , A.OBJOID	                ");
		sb.append("\n     , A.CLASSOID	            ");
		sb.append("\n     , A.OBJSUBID	            ");
		sb.append("\n     , A.DESCRIPTION             ");
		sb.append("\n  FROM PG_DESCRIPTION A          ");
		sb.append("\n  INNER JOIN PG_ATTRIBUTE B     ");
		sb.append("\n          ON B.ATTRELID = A.OBJOID      ");
		sb.append("\n         AND B.ATTNUM   = A.OBJSUBID   ");
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_PG_DESCRIPTION(");
		sb.append("\n    	 DB_NM                   ");
		sb.append("\n      , SCH_NM                  ");
		sb.append("\n      , OBJOID	                ");
		sb.append("\n      , CLASSOID	            ");
		sb.append("\n      , OBJSUBID	            "); //5
		sb.append("\n      , DESCRIPTION            ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                    ");
		sb.append("\n    ?, ?, ?, ?, ?              ");
		sb.append("\n   ,?, ?             	        ");
		sb.append("\n )                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("OBJOID"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASSOID"));
				pst_org.setString(rsGetCnt++, rs.getString("OBJSUBID"));
				pst_org.setString(rsGetCnt++, rs.getString("DESCRIPTION"));
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
	private int insertPG_STAT_USER_TABLES() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      , RELID	                 ");
		sb.append("\n      , SCHEMANAME	             ");
		sb.append("\n      , RELNAME	             ");
		sb.append("\n      , SEQ_SCAN	             ");
		sb.append("\n      , SEQ_TUP_READ	         ");
		sb.append("\n      , IDX_SCAN	             ");
		sb.append("\n      , IDX_TUP_FETCH	         ");
		sb.append("\n      , N_TUP_INS	             ");
		sb.append("\n      , N_TUP_UPD	             ");
		sb.append("\n      , N_TUP_DEL	             ");
		sb.append("\n      , N_TUP_HOT_UPD	         ");
		sb.append("\n      , N_LIVE_TUP	             ");
		sb.append("\n      , N_DEAD_TUP	             ");
		sb.append("\n      , LAST_VACUUM	         ");
		sb.append("\n      , LAST_AUTOVACUUM	     ");
		sb.append("\n      , LAST_ANALYZE	         ");
		sb.append("\n      , LAST_AUTOANALYZE	     ");
		sb.append("\n   FROM PG_STAT_USER_TABLES A   ");
		sb.append("\n  WHERE A.SCHEMANAME = '").append(schemaName).append("'"); 
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_PG_STAT_USER_TABLES (         ");
		sb.append("\n        DB_NM                   "); 
		sb.append("\n      , SCH_NM	                 ");
		sb.append("\n      , RELID	                 ");
		sb.append("\n      , SCHEMANAME	             ");
		sb.append("\n      , RELNAME	             "); //5
		sb.append("\n      , SEQ_SCAN	             "); 
		sb.append("\n      , SEQ_TUP_READ	         ");
		sb.append("\n      , IDX_SCAN	             ");
		sb.append("\n      , IDX_TUP_FETCH	         ");
		sb.append("\n      , N_TUP_INS	             "); //10
		sb.append("\n      , N_TUP_UPD	             "); 
		sb.append("\n      , N_TUP_DEL	             ");
		sb.append("\n      , N_TUP_HOT_UPD	         ");
		sb.append("\n      , N_LIVE_TUP	             ");
		sb.append("\n      , N_DEAD_TUP	             "); //15
		sb.append("\n      , LAST_VACUUM	         ");
		sb.append("\n      , LAST_AUTOVACUUM	     ");
		sb.append("\n      , LAST_ANALYZE	         ");
		sb.append("\n      , LAST_AUTOANALYZE	     "); //20
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                     ");
		sb.append("\n    ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n )                              ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("RELID"));      
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMANAME"));				
				pst_org.setString(rsGetCnt++, rs.getString("RELNAME"));//5
				pst_org.setString(rsGetCnt++, rs.getString("SEQ_SCAN"));
				pst_org.setString(rsGetCnt++, rs.getString("SEQ_TUP_READ"));				
				pst_org.setString(rsGetCnt++, rs.getString("IDX_SCAN"));
				pst_org.setString(rsGetCnt++, rs.getString("IDX_TUP_FETCH"));
				pst_org.setString(rsGetCnt++, rs.getString("N_TUP_INS"));  //10
				pst_org.setString(rsGetCnt++, rs.getString("N_TUP_UPD"));
				pst_org.setString(rsGetCnt++, rs.getString("N_TUP_DEL"));
				pst_org.setString(rsGetCnt++, rs.getString("N_TUP_HOT_UPD"));
				pst_org.setString(rsGetCnt++, rs.getString("N_LIVE_TUP"));
				pst_org.setString(rsGetCnt++, rs.getString("N_DEAD_TUP")); //15
				pst_org.setString(rsGetCnt++, rs.getString("LAST_VACUUM"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_AUTOVACUUM"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_ANALYZE"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_AUTOANALYZE")); //20 				
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
	private int insertCOLUMNS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      , TABLE_CATALOG	             ");
		sb.append("\n      , TABLE_SCHEMA	             ");
		sb.append("\n      , TABLE_NAME	                 ");
		sb.append("\n      , COLUMN_NAME	             ");
		sb.append("\n      , ORDINAL_POSITION	         ");
		sb.append("\n      , COLUMN_DEFAULT	             ");
		sb.append("\n      , IS_NULLABLE	             ");
		sb.append("\n      , DATA_TYPE	                 ");
		sb.append("\n      , CHARACTER_MAXIMUM_LENGTH	 ");
		sb.append("\n      , CHARACTER_OCTET_LENGTH	     ");
		sb.append("\n      , NUMERIC_PRECISION	         ");
		sb.append("\n      , NUMERIC_PRECISION_RADIX	 ");
		sb.append("\n      , NUMERIC_SCALE	             ");
		sb.append("\n      , DATETIME_PRECISION	         ");
		sb.append("\n      , INTERVAL_TYPE	             ");
		sb.append("\n      , INTERVAL_PRECISION	         ");
		sb.append("\n      , CHARACTER_SET_CATALOG	     ");
		sb.append("\n      , CHARACTER_SET_SCHEMA	     ");
		sb.append("\n      , CHARACTER_SET_NAME	         ");
		sb.append("\n      , COLLATION_CATALOG	         ");
		sb.append("\n      , COLLATION_SCHEMA	         ");
		sb.append("\n      , COLLATION_NAME	             ");
		sb.append("\n      , DOMAIN_CATALOG	             ");
		sb.append("\n      , DOMAIN_SCHEMA	             ");
		sb.append("\n      , DOMAIN_NAME	             ");
		sb.append("\n      , UDT_CATALOG	             ");
		sb.append("\n      , UDT_SCHEMA	                 ");
		sb.append("\n      , UDT_NAME	                 ");
		sb.append("\n      , SCOPE_CATALOG	             ");
		sb.append("\n      , SCOPE_SCHEMA	             ");
		sb.append("\n      , SCOPE_NAME	                 ");
		sb.append("\n      , MAXIMUM_CARDINALITY	     ");
		sb.append("\n      , DTD_IDENTIFIER	             ");
		sb.append("\n      , IS_SELF_REFERENCING	     ");
		sb.append("\n      , IS_IDENTITY	             ");
		sb.append("\n      , IDENTITY_GENERATION	     ");
		sb.append("\n      , IDENTITY_START	             ");
		sb.append("\n      , IDENTITY_INCREMENT	         ");
		sb.append("\n      , IDENTITY_MAXIMUM	         ");
		sb.append("\n      , IDENTITY_MINIMUM	         ");
		sb.append("\n      , IDENTITY_CYCLE	             ");
		sb.append("\n      , IS_GENERATED	             ");
		sb.append("\n      , GENERATION_EXPRESSION	     ");
		sb.append("\n      , IS_UPDATABLE                ");
		sb.append("\n   FROM INFORMATION_SCHEMA.COLUMNS A   ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
		//logger.debug("test sql:" + sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_COLUMNS (      ");
		sb.append("\n         DB_NM                       ");
		sb.append("\n       , SCH_NM                      ");
		sb.append("\n       , TABLE_CATALOG	             ");
		sb.append("\n       , TABLE_SCHEMA	             ");
		sb.append("\n       , TABLE_NAME	             "); //5
		sb.append("\n       , COLUMN_NAME	             ");
		sb.append("\n       , ORDINAL_POSITION	         ");
		sb.append("\n       , COLUMN_DEFAULT	         ");
		sb.append("\n       , IS_NULLABLE	             ");
		sb.append("\n       , DATA_TYPE	                 "); //10
		sb.append("\n       , CHARACTER_MAXIMUM_LENGTH	 ");
		sb.append("\n       , CHARACTER_OCTET_LENGTH	 ");
		sb.append("\n       , NUMERIC_PRECISION	         ");
		sb.append("\n       , NUMERIC_PRECISION_RADIX	 ");
		sb.append("\n       , NUMERIC_SCALE	             "); //15
		sb.append("\n       , DATETIME_PRECISION	     ");
		sb.append("\n       , INTERVAL_TYPE	             ");
		sb.append("\n       , INTERVAL_PRECISION	     ");
		sb.append("\n       , CHARACTER_SET_CATALOG	     ");
		sb.append("\n       , CHARACTER_SET_SCHEMA	     "); //20
		sb.append("\n       , CHARACTER_SET_NAME	     ");
		sb.append("\n       , COLLATION_CATALOG	         ");
		sb.append("\n       , COLLATION_SCHEMA	         ");
		sb.append("\n       , COLLATION_NAME	         ");
		sb.append("\n       , DOMAIN_CATALOG	         "); //25
		sb.append("\n       , DOMAIN_SCHEMA	             "); 
		sb.append("\n       , DOMAIN_NAME	             ");
		sb.append("\n       , UDT_CATALOG	             ");
		sb.append("\n       , UDT_SCHEMA	             ");
		sb.append("\n       , UDT_NAME	                 "); //30
		sb.append("\n       , SCOPE_CATALOG	             ");
		sb.append("\n       , SCOPE_SCHEMA	             ");
		sb.append("\n       , SCOPE_NAME	             ");
		sb.append("\n       , MAXIMUM_CARDINALITY	     ");
		sb.append("\n       , DTD_IDENTIFIER	         "); //35
		sb.append("\n       , IS_SELF_REFERENCING	     ");
		sb.append("\n       , IS_IDENTITY	             ");
		sb.append("\n       , IDENTITY_GENERATION	     ");
		sb.append("\n       , IDENTITY_START	         ");
		sb.append("\n       , IDENTITY_INCREMENT	     "); //40
		sb.append("\n       , IDENTITY_MAXIMUM	         ");
		sb.append("\n       , IDENTITY_MINIMUM	         ");
		sb.append("\n       , IDENTITY_CYCLE	         ");
		sb.append("\n       , IS_GENERATED	             ");
		sb.append("\n       , GENERATION_EXPRESSION	     "); //45
		sb.append("\n       , IS_UPDATABLE                ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                          ");
		sb.append("\n     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?             "); 		
		sb.append("\n )                                   ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));  //5
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ORDINAL_POSITION"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_DEFAULT"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));  //10
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_MAXIMUM_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_OCTET_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION"));
				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION_RADIX"));
				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_SCALE")); //15
				pst_org.setString(rsGetCnt++, rs.getString("DATETIME_PRECISION"));
				pst_org.setString(rsGetCnt++, rs.getString("INTERVAL_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("INTERVAL_PRECISION"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_SCHEMA")); //20
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_CATALOG")); //25
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("UDT_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("UDT_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("UDT_NAME"));  //30
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("MAXIMUM_CARDINALITY"));
				pst_org.setString(rsGetCnt++, rs.getString("DTD_IDENTIFIER")); //35
				pst_org.setString(rsGetCnt++, rs.getString("IS_SELF_REFERENCING"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_IDENTITY"));
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_GENERATION"));
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_START"));
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_INCREMENT")); //40
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_MAXIMUM"));
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_MINIMUM"));
				pst_org.setString(rsGetCnt++, rs.getString("IDENTITY_CYCLE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_GENERATED"));
				pst_org.setString(rsGetCnt++, rs.getString("GENERATION_EXPRESSION")); //45
				pst_org.setString(rsGetCnt++, rs.getString("IS_UPDATABLE"));
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
	private int insertTABLE_CONSTRAINTS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM  ");
		sb.append("\n      , CONSTRAINT_CATALOG	                        ");
		sb.append("\n      , CONSTRAINT_SCHEMA	                        ");
		sb.append("\n      , CONSTRAINT_NAME	                        ");
		sb.append("\n      , TABLE_CATALOG	                            ");
		sb.append("\n      , TABLE_SCHEMA	                            ");
		sb.append("\n      , TABLE_NAME	                                ");
		sb.append("\n      , CONSTRAINT_TYPE	                        ");
		sb.append("\n      , IS_DEFERRABLE	                            ");
		sb.append("\n      , INITIALLY_DEFERRED                         ");
		sb.append("\n   FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS A     ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'"); 
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_TABLE_CONSTRAINTS (   ");
		sb.append("\n        DB_NM                    "); 
		sb.append("\n      , SCH_NM                   "); 
		sb.append("\n      , CONSTRAINT_CATALOG	      ");
		sb.append("\n      , CONSTRAINT_SCHEMA	      ");
		sb.append("\n      , CONSTRAINT_NAME	      "); //5
		sb.append("\n      , TABLE_CATALOG	          ");
		sb.append("\n      , TABLE_SCHEMA	          ");
		sb.append("\n      , TABLE_NAME	              ");
		sb.append("\n      , CONSTRAINT_TYPE	      ");
		sb.append("\n      , IS_DEFERRABLE	          "); //10
		sb.append("\n      , INITIALLY_DEFERRED       ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?                            ");
		sb.append("\n )                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));				
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_DEFERRABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("INITIALLY_DEFERRED"));
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
	private int insertCONSTRAINT_COLUMN_USAGE() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM  ");		 		     
		sb.append("\n      , TABLE_CATALOG	     ");
		sb.append("\n      , TABLE_SCHEMA	     ");
		sb.append("\n      , TABLE_NAME	         ");
		sb.append("\n      , COLUMN_NAME	     ");
		sb.append("\n      , CONSTRAINT_CATALOG  ");
		sb.append("\n      , CONSTRAINT_SCHEMA	 ");
		sb.append("\n      , CONSTRAINT_NAME     ");
		sb.append("\n   FROM INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE A     ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");  
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_CONSTRAINT_COLUMN (   ");
		sb.append("\n        DB_NM                    "); 
		sb.append("\n      , SCH_NM                   "); 
		sb.append("\n      , TABLE_CATALOG	          ");
		sb.append("\n      , TABLE_SCHEMA	          ");
		sb.append("\n      , TABLE_NAME	              "); //5
		sb.append("\n      , COLUMN_NAME	          ");
		sb.append("\n      , CONSTRAINT_CATALOG       ");
		sb.append("\n      , CONSTRAINT_SCHEMA	      ");
		sb.append("\n      , CONSTRAINT_NAME          ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?, ?                ");		
		sb.append("\n )                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));   //5
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_CATALOG"));				
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
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
	
	private int insertKEY_COLUMN_USAGE() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM  ");		 		     
		sb.append("\n      , TABLE_CATALOG	     ");
		sb.append("\n      , TABLE_SCHEMA	     ");
		sb.append("\n      , TABLE_NAME	         ");
		sb.append("\n      , COLUMN_NAME	     ");
		sb.append("\n      , CONSTRAINT_CATALOG  ");
		sb.append("\n      , CONSTRAINT_SCHEMA	 ");
		sb.append("\n      , CONSTRAINT_NAME     ");
		sb.append("\n      , ORDINAL_POSITION	     ");
		sb.append("\n      , POSITION_IN_UNIQUE_CONSTRAINT	     ");
		sb.append("\n   FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE A     ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_KEY_COLUMN (   ");
		sb.append("\n        DB_NM                    "); 
		sb.append("\n      , SCH_NM                   "); 
		sb.append("\n      , TABLE_CATALOG	          ");
		sb.append("\n      , TABLE_SCHEMA	          ");
		sb.append("\n      , TABLE_NAME	              "); //5
		sb.append("\n      , COLUMN_NAME	          ");
		sb.append("\n      , CONSTRAINT_CATALOG       ");
		sb.append("\n      , CONSTRAINT_SCHEMA	      ");
		sb.append("\n      , CONSTRAINT_NAME          ");
		sb.append("\n      , ORDINAL_POSITION          ");
		sb.append("\n      , POSITION_IN_UNIQUE_CONSTRAINT ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?, ?                ");	
		sb.append("\n  , ?, ?                		  ");
		sb.append("\n )                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));   //5
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_CATALOG"));				
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ORDINAL_POSITION")); // 10
				pst_org.setString(rsGetCnt++, rs.getString("POSITION_IN_UNIQUE_CONSTRAINT"));
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
		sb.append("\n SELECT TC.TABLE_NAME AS CHLID_TABLE,						");
		sb.append("\n	     KCU.COLUMN_NAME AS CHLID_COLUMN,    	        	");
		sb.append("\n		 CCU.TABLE_NAME AS FOREIGN_TABLE,					");
		sb.append("\n		 CCU.COLUMN_NAME AS FOREIGN_COLUMN					");
		sb.append("\n FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC				");
		sb.append("\n JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU				");
		sb.append("\n ON TC.CONSTRAINT_NAME::TEXT=KCU.CONSTRAINT_NAME::TEXT		");
		sb.append("\n JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE CCU		");
		sb.append("\n ON CCU.CONSTRAINT_NAME::TEXT = TC.CONSTRAINT_NAME::TEXT	");
		sb.append("\n WHERE TC.CONSTRAINT_TYPE = 'FOREIGN KEY';					");
  		//logger.debug("\n " + sb.toString());
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_POST_COLUMNS_INFO	");
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
  				pst_org.setString(rsGetCnt++, rs.getString("CHLID_TABLE"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHLID_COLUMN"));
  				pst_org.setString(rsGetCnt++, rs.getString(3)+"."+rs.getString(4));
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
		strSQL.append("\n  DELETE FROM WAE_POST_DB ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_DB : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_STAT_USER_TABLES ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_DESCRIPTION ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_TBL : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_COLUMNS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COLUMNS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_ATTRIBUTE ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_PG_ATTRIBUTE : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_TABLE_CONSTRAINTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_TABLE_CONSTRAINTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_CONSTRAINT_COLUMN ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_CONSTRAINT_COLUMN : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_KEY_COLUMN ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_KEY_COLUMN : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_COLUMNS_INFO ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COLUMNS_INFO : " + result);

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
		sb.append("\n INSERT INTO WAT_DBC_TBL                       ");
		sb.append("\n (                                             ");
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
		sb.append("\n      ,  ROW_EACNT                             ");
		sb.append("\n      ,  TBL_SIZE                              ");
		sb.append("\n      ,  DATA_SIZE                             ");
		sb.append("\n      ,  IDX_SIZE                              ");
		sb.append("\n      ,  NUSE_SIZE                             ");
		sb.append("\n      ,  TBL_ANA_DTM                           ");
		sb.append("\n      ,  TBL_CRT_DTM                           ");
		sb.append("\n      ,  TBL_CHG_DTM                           ");
		sb.append("\n      , TBL_CLLT_DCD                          ");
		sb.append("\n )                                             "); 	 		
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID            ");
		sb.append("\n      , A.RELNAME             AS DBC_TBL_NM           ");
		sb.append("\n      , F.DESCRIPTION         AS DBC_TBL_KOR_NM       ");
		sb.append("\n      , '1'                   AS OBJ_VERS             ");
		sb.append("\n      , NULL                  AS REG_TYP_CD           ");
		sb.append("\n      , SYSDATE           AS REG_DTM              ");
		sb.append("\n      , NULL                  AS UPD_DTM              ");
		sb.append("\n      , ''                    AS DESCN                ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID       ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM      ");
		sb.append("\n      , A.N_LIVE_TUP          AS ROW_EACNT            ");
		sb.append("\n      , ''                    AS TBL_SIZE             ");
		sb.append("\n      , ''                    AS DATA_SIZE            ");
		sb.append("\n      , ''                    AS IDX_SIZE             ");
		sb.append("\n      , ''                    AS NUSE_SIZE            ");
		sb.append("\n      , NULL                  AS TBL_ANA_DTM          ");
		sb.append("\n      , NULL                  AS TBL_CRT_DTM          ");
		sb.append("\n      , NULL                  AS TBL_CHG_DTM          ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                     ");
		sb.append("\n   FROM WAE_POST_PG_STAT_USER_TABLES A                            ");
		sb.append("\n        INNER JOIN (                                              ");
		sb.append("\n      " + getdbconnsql() + "                                      ");
		sb.append("\n        ) S                                                       ");
		sb.append("\n           ON S.DB_CONN_TRG_PNM = A.DB_NM                         ");
		sb.append("\n          AND S.DB_SCH_PNM      = A.SCH_NM                        ");
		sb.append("\n          AND S.DB_CONN_TRG_ID  = A.DB_CONN_TRG_ID                ");
		sb.append("\n        LEFT JOIN WAE_POST_PG_DESCRIPTION F                       ");
		sb.append("\n          ON F.DB_NM      = A.DB_NM                               "); 
		sb.append("\n         AND F.SCH_NM     = A.SCH_NM                              ");
		sb.append("\n         AND F.SCH_NM     = A.SCH_NM                              ");
		sb.append("\n         AND F.OBJOID     = A.RELID                               "); 
		sb.append("\n         AND F.OBJSUBID  = 0                                      "); 
		sb.append("\n         AND F.DB_CONN_TRG_ID = A.DB_CONN_TRG_ID                  ");
		sb.append("\n  WHERE A.DB_NM = '" + dbName + "'                                ");
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+ dbms_id +"'                        ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.RELNAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT				");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)										");

		return setExecuteUpdate_Org(sb.toString());
	}

	private int insertwatcol() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL   ");
		sb.append("\n (                         ");
		sb.append("\n        DB_SCH_ID          ");
		sb.append("\n      , DBC_TBL_NM         ");      
		sb.append("\n      , DBC_COL_NM         ");      
		sb.append("\n      , DBC_COL_KOR_NM     ");      
		sb.append("\n      , OBJ_VERS           ");      
		sb.append("\n      , REG_TYP_CD         ");      
		sb.append("\n      , REG_DTM            ");      
		sb.append("\n      , UPD_DTM            ");      
		sb.append("\n      , DATA_TYPE          ");      
		sb.append("\n      , DATA_LEN           ");      
		sb.append("\n      , DATA_PNUM          ");      
		sb.append("\n      , DATA_PNT           ");      
		sb.append("\n      , NULL_YN            ");      
		sb.append("\n      , DEFLT_LEN          ");      
		sb.append("\n      , DEFLT_VAL          "); 
		sb.append("\n      , PK_YN              "); 
		sb.append("\n      , FK_YN              ");
		sb.append("\n      , ORD                "); 
		sb.append("\n      , PK_ORD             "); 
		sb.append("\n      , COL_DESCN          "); 
		sb.append("\n )                         "); 
		sb.append("\n SELECT S.DB_SCH_ID                     AS DB_SCH_ID                                  ");
		sb.append("\n      , A.RELNAME                       AS DBC_TBL_NM                                 ");
		sb.append("\n      , E.COLUMN_NAME                   AS DBC_COL_NM                                 ");
		sb.append("\n      , G.DESCRIPTION                   AS DBC_COL_KOR_NM                             ");
		sb.append("\n      , '1'                             AS VERS                                       ");
		sb.append("\n      , NULL                            AS REG_TYP                                    ");
		sb.append("\n      , SYSDATE                         AS REG_DTM                                    ");
		sb.append("\n      , NULL                            AS UPD_DTM                                    ");
		sb.append("\n      , E.DATA_TYPE                     AS DATA_TYPE                                  ");
		sb.append("\n      , E.CHARACTER_MAXIMUM_LENGTH      AS DATA_LEN                                   ");
		sb.append("\n      , 0                               AS DATA_PNUM                                  ");
		sb.append("\n      , E.NUMERIC_SCALE                 AS DATA_PNT                                   ");
		sb.append("\n      , SUBSTR(E.IS_NULLABLE,1,1)       AS NULL_YN                                    ");
		sb.append("\n      , 0                               AS DEFLT_LEN                                  ");
		sb.append("\n      , E.COLUMN_DEFAULT                AS DEFLT_VAL                                  ");
		sb.append("\n      , (CASE WHEN F.DB_NM IS NOT NULL THEN 'Y' ELSE 'N' END) AS PK_YN                ");
		sb.append("\n      , K.FK_INFO 													AS FK_YN           ");
		sb.append("\n      , E.ORDINAL_POSITION              AS ORD                                        ");
		sb.append("\n      , (CASE WHEN F.DB_NM IS NOT NULL THEN E.ORDINAL_POSITION END) AS PK_ORD         ");
		sb.append("\n      , ''                              AS COL_DESCN                                  ");
		sb.append("\n   FROM WAE_POST_PG_STAT_USER_TABLES A                                                ");
		sb.append("\n        INNER JOIN (                                                                  ");
		sb.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM     ");
		sb.append("\n              FROM WAA_DB_CONN_TRG T                                                  ");
		sb.append("\n             INNER JOIN WAA_DB_SCH S                                                  ");
		sb.append("\n                ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                                ");
		sb.append("\n               AND S.REG_TYP_CD IN ('C','U')                                          ");
		sb.append("\n               AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD')                         ");
		sb.append("\n             WHERE 1=1                                                                ");
		sb.append("\n               AND T.REG_TYP_CD IN ('C','U')                                          ");
		sb.append("\n               AND T.EXP_DTM = TO_DATE('99991231','YYYYMMDD')                         ");  
		sb.append("\n        ) S                                                                           ");
		sb.append("\n          ON S.DB_CONN_TRG_PNM = A.DB_NM                                              ");
		sb.append("\n         AND S.DB_SCH_PNM      = A.SCH_NM                                             ");
		sb.append("\n         AND S.DB_CONN_TRG_ID  = A.DB_CONN_TRG_ID                                     ");
		sb.append("\n        INNER JOIN WAE_POST_COLUMNS  E                                                ");
		sb.append("\n           ON E.DB_NM       = A.DB_NM                                                 ");
		sb.append("\n          AND E.SCH_NM      = A.SCH_NM                                                ");
		sb.append("\n          AND E.TABLE_NAME  = A.RELNAME                                               ");
		sb.append("\n          AND E.DB_CONN_TRG_ID = A.DB_CONN_TRG_ID                                     ");
		sb.append("\n        LEFT JOIN                                                                     ");
		sb.append("\n        (                                                                             ");
		sb.append("\n         SELECT A.DB_NM, A.SCH_NM, A.TABLE_NAME                                       ");
		sb.append("\n              , B.COLUMN_NAME, B.DB_CONN_TRG_ID                                       ");
		sb.append("\n           FROM WAE_POST_TABLE_CONSTRAINTS A                                          ");
		sb.append("\n                INNER JOIN WAE_POST_CONSTRAINT_COLUMN B                               ");
		sb.append("\n                   ON B.DB_NM           = A.DB_NM                                     ");
		sb.append("\n                  AND B.SCH_NM          = A.SCH_NM                                    ");
		sb.append("\n                  AND B.TABLE_NAME      = A.TABLE_NAME                                ");
		sb.append("\n                  AND B.CONSTRAINT_NAME = A.CONSTRAINT_NAME                           ");
		sb.append("\n                  AND B.DB_CONN_TRG_ID  = A.DB_CONN_TRG_ID                            ");
		sb.append("\n          WHERE A.CONSTRAINT_TYPE = 'PRIMARY KEY'                                     ");
		sb.append("\n        ) F                                                                           ");
		sb.append("\n         ON F.DB_NM       = E.DB_NM                                                   ");
		sb.append("\n        AND F.SCH_NM      = E.SCH_NM                                                  ");
		sb.append("\n        AND F.TABLE_NAME  = E.TABLE_NAME                                              ");
		sb.append("\n        AND F.COLUMN_NAME = E.COLUMN_NAME                                             ");
		sb.append("\n        AND F.DB_CONN_TRG_ID = E.DB_CONN_TRG_ID                                       ");
		sb.append("\n        LEFT JOIN                                                                     ");
		sb.append("\n        (                                                                             ");
		sb.append("\n         SELECT C.DB_NM, C.SCH_NM, D.ATTRELID, D.ATTNAME, C.DESCRIPTION, C.DB_CONN_TRG_ID  ");
		sb.append("\n           FROM WAE_POST_PG_DESCRIPTION C                                             ");
		sb.append("\n                INNER JOIN WAE_POST_PG_ATTRIBUTE D                                    ");
		sb.append("\n                   ON D.DB_NM    = C.DB_NM                                            ");
		sb.append("\n                  AND D.SCH_NM   = C.SCH_NM                                           ");
		sb.append("\n                  AND D.ATTRELID = C.OBJOID                                           ");
		sb.append("\n                  AND D.ATTNUM   = C.OBJSUBID                                         ");
		sb.append("\n                  AND D.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID                             ");
		sb.append("\n          WHERE C.OBJSUBID != 0                                                       ");
		sb.append("\n        ) G                                                                           ");
		sb.append("\n        ON G.DB_NM    = A.DB_NM                                                       ");
		sb.append("\n       AND G.SCH_NM   = A.SCH_NM                                                      ");
		sb.append("\n       AND G.ATTRELID = A.RELID                                                       ");
		sb.append("\n       AND G.DB_CONN_TRG_ID = A.DB_CONN_TRG_ID       								   ");
		sb.append("\n       AND G.ATTNAME  = E.COLUMN_NAME                                                 ");
		sb.append("\n	LEFT JOIN (										                                   ");
		sb.append("\n		SELECT DB_CONN_TRG_ID, DB_NM, SCH_NM, TBL_NM, COL_NM, WM_CONCAT(FK_INFO) FK_INFO ");
		sb.append("\n		FROM WAE_POST_COLUMNS_INFO 													   ");
		sb.append("\n		GROUP BY DB_NM, SCH_NM, TBL_NM, COL_NM, DB_CONN_TRG_ID						   ");
		sb.append("\n				) K												");
		sb.append("\n	ON	E.DB_NM = K.DB_NM										");
		sb.append("\n	AND E.SCH_NM = K.SCH_NM									    ");
		sb.append("\n	AND E.TABLE_NAME  = K.TBL_NM								");
		sb.append("\n	AND E.COLUMN_NAME = K.COL_NM								");
		sb.append("\n	AND E.DB_CONN_TRG_ID = K.DB_CONN_TRG_ID						");
		sb.append("\n WHERE A.DB_NM = '" + dbName + "'                              ");
		sb.append("\n   AND S.DB_CONN_TRG_ID = '"+ dbms_id +"'                      ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.RELNAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT				");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)										");

		return setExecuteUpdate_Org(sb.toString());
	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n             INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n               AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n               AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
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
		tgtSQL = tgtSQL.append("select round(PG_DATABASE_SIZE(DATNAME)/1024/1024.00,2) from PG_DATABASE where DATNAME = 'postgres'");
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
