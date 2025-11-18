/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorMysql.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 17. 오후 6:07:16
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 17. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema.dbms;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.wise.executor.dm.TargetDbmsDM;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorMysql.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorPostgreSQL {

	private static final Logger logger = Logger.getLogger(CollectorMysql.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;

	private String sSchemaCond = "";	//추출 대상이 되는 유저
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;

	private String dbType = null;
	private String dbmsVersion = "";

	private int execCnt = 100; 


	public CollectorPostgreSQL() {

	}

	/** insomnia */
	public CollectorPostgreSQL(Connection source, Connection target, List<TargetDbmsDM>  lsitdm) {
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

	/**  insomnia
	 * @return */
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

			int p = 0;
			int cnt = 0;

			//첫번째 스키마 일때만 처리한다.
			if(dbCnt == 0) {
				//스키마 정보를 모두 삭제한다.
				deleteSchema();
				//스키마 정보를 추가한다. DB인스턴스 별로 한번만 실행한다.
				cnt = insertMetaDATABASES();
				logger.debug(sp + (++p) + ". insertMetaDATABASES " + cnt + " OK!!");

				con_org.commit();
			}

			dbCnt++;
			
			logger.debug(" schemaName : "+schemaName);
			//DBA_USERS --------------------------------------------------------
			cnt = insertSCHEMATA();
			logger.debug(sp + (++p) + ". insertDBA_USERS " + cnt + " OK!!");

			//INFORMATION_SCHEMA.TABLES -------------------------------------------------------
			//cnt = insertTABLES();
			
			cnt = insertPG_STAT_USER_TABLES();
			logger.debug(sp + (++p) + ". insertTABLES " + cnt + " OK!!");
			
			cnt = insertPG_DESCRIPTTION();
			logger.debug(sp + (++p) + ". insertPG_DESCRIPTTION " + cnt + " OK!!");
			
			//INFORMATION_SCHEMA.COLUMNS ---------------------------------------------------
			cnt = insertCOLUMNS();
			logger.debug(sp + (++p) + ". insertCOLUMNS " + cnt + " OK!!");
			
			//PG_ATTRIBUTE---------------------------------------------------
			cnt = insertPG_ATTRIBUTE();
			logger.debug(sp + (++p) + ". insertPG_ATTRIBUTE " + cnt + " OK!!");
			
			// INFORMATION_SCHEMA.TABLE_CONSTRAINTS ---------------------------------------------------
			cnt = insertTABLE_CONSTRAINTS();
			logger.debug(sp + (++p) + ". insertCOLUMNS " + cnt + " OK!!");

			//INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ---------------------------------------------------
			cnt = insertCONSTRAINT_COLUMN_USAGE();
			logger.debug(sp + (++p) + ". insertCONSTRAINT_COLUMN_USAGE " + cnt + " OK!!");
			
			con_org.commit();

		}

		result = true;

		return result;

	}

	private int insertPG_ATTRIBUTE() throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , ATTRELID	      ");
		sb.append("\n      , ATTNAME	      ");
		sb.append("\n      , ATTTYPID	      ");
		sb.append("\n      , ATTSTATTARGET	  ");
		sb.append("\n      , ATTLEN	          ");
		sb.append("\n      , ATTNUM	          ");
		sb.append("\n      , ATTNDIMS	      ");
		sb.append("\n      , ATTCACHEOFF	  ");
		sb.append("\n      , ATTTYPMOD	      ");
		sb.append("\n      , ATTBYVAL	      ");
		sb.append("\n      , ATTSTORAGE	      ");
		sb.append("\n      , ATTALIGN	      ");
		sb.append("\n      , ATTNOTNULL	      ");
		sb.append("\n      , ATTHASDEF	      ");
		sb.append("\n      , ATTIDENTITY	  ");
		sb.append("\n      , ATTISDROPPED	  ");
		sb.append("\n      , ATTISLOCAL	      ");
		sb.append("\n      , ATTINHCOUNT	  ");
		sb.append("\n      , ATTCOLLATION	  ");
		sb.append("\n      , ATTACL	          ");
		sb.append("\n      , ATTOPTIONS	      ");
		sb.append("\n      , ATTFDWOPTIONS    "); 
		sb.append("\n   FROM PG_ATTRIBUTE     ");
		
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
		sb.append("\n      , ATTIDENTITY	        ");
		sb.append("\n      , ATTISDROPPED	        ");
		sb.append("\n      , ATTISLOCAL	            ");
		sb.append("\n      , ATTINHCOUNT	        "); //20
		sb.append("\n      , ATTCOLLATION	        ");
		sb.append("\n      , ATTACL	                ");
		sb.append("\n      , ATTOPTIONS	            ");
		sb.append("\n      , ATTFDWOPTIONS          "); 
		sb.append("\n ) VALUES (                    "); 
		sb.append("\n     ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?, ?             ");
		sb.append("\n   , ?, ?, ?, ?                ");
		sb.append("\n )                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
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
				pst_org.setString(rsGetCnt++, rs.getString("ATTIDENTITY"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTISDROPPED"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTISLOCAL"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTINHCOUNT")); //20
				pst_org.setString(rsGetCnt++, rs.getString("ATTCOLLATION"));				
				pst_org.setString(rsGetCnt++, rs.getString("ATTACL"));       
				pst_org.setString(rsGetCnt++, rs.getString("ATTOPTIONS")); 
				pst_org.setString(rsGetCnt++, rs.getString("ATTFDWOPTIONS"));

				getSaveResult(rsCnt);

				if(rsCnt == execCnt)
				{
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
		sb.append("\n     , OBJOID	                ");
		sb.append("\n     , CLASSOID	            ");
		sb.append("\n     , OBJSUBID	            ");
		sb.append("\n     , DESCRIPTION             ");
		sb.append("\n  FROM PG_DESCRIPTION          ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_POST_PG_DESCRIPTION(");
		sb.append("\n    	 DB_NM                   ");
		sb.append("\n      , SCH_NM                  ");
		sb.append("\n      , OBJOID	                ");
		sb.append("\n      , CLASSOID	            ");
		sb.append("\n      , OBJSUBID	            "); //5
		sb.append("\n      , DESCRIPTION            ");
		sb.append("\n ) VALUES (                    ");
		sb.append("\n    ?, ?, ?, ?, ?              ");
		sb.append("\n   ,?               	        ");
		sb.append("\n )                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("OBJOID"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASSOID"));
				pst_org.setString(rsGetCnt++, rs.getString("OBJSUBID"));
				pst_org.setString(rsGetCnt++, rs.getString("DESCRIPTION"));
				

				getSaveResult(rsCnt);

				if(rsCnt == execCnt)
				{
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

	private int changeUseDatabase() throws SQLException
	{
		String query_tgt = "";

		query_tgt = "\n use information_schema ";

		pst_tgt = con_tgt.prepareStatement(query_tgt);

		return pst_tgt.executeUpdate();
	}

	

	
	
	/**
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertPG_STAT_USER_TABLES() throws SQLException
	{
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
		sb.append("\n      , N_MOD_SINCE_ANALYZE	 ");
		sb.append("\n      , LAST_VACUUM	         ");
		sb.append("\n      , LAST_AUTOVACUUM	     ");
		sb.append("\n      , LAST_ANALYZE	         ");
		sb.append("\n      , LAST_AUTOANALYZE	     ");
		sb.append("\n      , VACUUM_COUNT	         ");
		sb.append("\n      , AUTOVACUUM_COUNT	     ");
		sb.append("\n      , ANALYZE_COUNT	         ");
		sb.append("\n      , AUTOANALYZE_COUNT       ");
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
		sb.append("\n      , N_MOD_SINCE_ANALYZE	 "); 
		sb.append("\n      , LAST_VACUUM	         ");
		sb.append("\n      , LAST_AUTOVACUUM	     ");
		sb.append("\n      , LAST_ANALYZE	         ");
		sb.append("\n      , LAST_AUTOANALYZE	     "); //20
		sb.append("\n      , VACUUM_COUNT	         "); 
		sb.append("\n      , AUTOVACUUM_COUNT	     ");
		sb.append("\n      , ANALYZE_COUNT	         ");
		sb.append("\n      , AUTOANALYZE_COUNT       ");
		sb.append("\n ) VALUES (                     ");
		sb.append("\n    ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?                  ");
		sb.append("\n )                              ");


		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
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
				pst_org.setString(rsGetCnt++, rs.getString("N_MOD_SINCE_ANALYZE"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_VACUUM"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_AUTOVACUUM"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_ANALYZE"));
				pst_org.setString(rsGetCnt++, rs.getString("LAST_AUTOANALYZE")); //20 				
				pst_org.setString(rsGetCnt++, rs.getString("VACUUM_COUNT"));
				pst_org.setString(rsGetCnt++, rs.getString("AUTOVACUUM_COUNT"));
				pst_org.setString(rsGetCnt++, rs.getString("ANALYZE_COUNT"));
				pst_org.setString(rsGetCnt++, rs.getString("AUTOANALYZE_COUNT"));  

				getSaveResult(rsCnt);

				if(rsCnt == execCnt) 
				{
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
	private int insertTABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      , TABLE_CATALOG	               "); 
        sb.append("\n      , TABLE_SCHEMA	               ");
        sb.append("\n      , TABLE_NAME	                   ");  
        sb.append("\n      , TABLE_TYPE	                   "); 
        sb.append("\n      , SELF_REFERENCING_COLUMN_NAME  ");
        sb.append("\n      , REFERENCE_GENERATION	       "); 
        sb.append("\n      , USER_DEFINED_TYPE_CATALOG	   "); 
        sb.append("\n      , USER_DEFINED_TYPE_SCHEMA	   "); 
        sb.append("\n      , USER_DEFINED_TYPE_NAME	       "); 
        sb.append("\n      , IS_INSERTABLE_INTO	           "); 
        sb.append("\n      , IS_TYPED	                   ");
        sb.append("\n      , COMMIT_ACTION                 ");
		sb.append("\n   FROM INFORMATION_SCHEMA.TABLES A   ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'"); 

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_POST_TABLES (         ");
		sb.append("\n        DB_NM                         "); 
		sb.append("\n      , SCH_NM                        "); 
		sb.append("\n      , TABLE_CATALOG	               "); 
        sb.append("\n      , TABLE_SCHEMA	               ");
        sb.append("\n      , TABLE_NAME	                   "); //5 
        sb.append("\n      , TABLE_TYPE	                   "); 
        sb.append("\n      , SELF_REFERENCING_COLUMN_NAME  ");
        sb.append("\n      , REFERENCE_GENERATION	       "); 
        sb.append("\n      , USER_DEFINED_TYPE_CATALOG	   "); 
        sb.append("\n      , USER_DEFINED_TYPE_SCHEMA	   "); //10
        sb.append("\n      , USER_DEFINED_TYPE_NAME	       "); 
        sb.append("\n      , IS_INSERTABLE_INTO	           "); 
        sb.append("\n      , IS_TYPED	                   ");
        sb.append("\n      , COMMIT_ACTION                 ");
		sb.append("\n ) VALUES (                           ");
		sb.append("\n    ?, ?, ?, ?, ?                     ");
		sb.append("\n  , ?, ?, ?, ?, ?                     ");
		sb.append("\n  , ?, ?, ?, ?                        ");
		sb.append("\n )                                    ");


		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("SELF_REFERENCING_COLUMN_NAME"));				
				pst_org.setString(rsGetCnt++, rs.getString("REFERENCE_GENERATION"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_DEFINED_TYPE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_DEFINED_TYPE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_DEFINED_TYPE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_INSERTABLE_INTO"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_TYPED"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMIT_ACTION"));

				getSaveResult(rsCnt);

				if(rsCnt == execCnt) 
				{
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
	private int insertCOLUMNS() throws SQLException 
	{
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
		
		logger.debug("test sql:" + sb.toString());

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
		sb.append("\n ) VALUES (                          ");
		sb.append("\n     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?                "); 		
		sb.append("\n )                                   ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
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

				getSaveResult(rsCnt);

				if(rsCnt == execCnt)
				{
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
	private int insertTABLE_CONSTRAINTS() throws SQLException
	{
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
		sb.append("\n ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?                            ");
		sb.append("\n )                               ");


		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
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
				
				getSaveResult(rsCnt);

				if(rsCnt == execCnt) 
				{
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
	private int insertCONSTRAINT_COLUMN_USAGE() throws SQLException
	{
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
		sb.append("\n ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?                   ");		
		sb.append("\n )                               ");


		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));   //5
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_CATALOG"));				
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				
				getSaveResult(rsCnt);

				if(rsCnt == execCnt) 
				{
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
	 * DBC DBA_USERS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSCHEMATA() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                               ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , CATALOG_NAME	                ");
		sb.append("\n      , SCHEMA_NAME	                ");
		sb.append("\n      , SCHEMA_OWNER	                ");
		sb.append("\n      , DEFAULT_CHARACTER_SET_CATALOG	");
		sb.append("\n      , DEFAULT_CHARACTER_SET_SCHEMA	");
		sb.append("\n      , DEFAULT_CHARACTER_SET_NAME	    ");
		sb.append("\n      , SQL_PATH                       ");
		sb.append("\n   FROM INFORMATION_SCHEMA.SCHEMATA                      ");
		sb.append("\n  WHERE SCHEMA_NAME = '").append(schemaName).append("'");
		
		logger.debug(sb.toString());
		
		getResultSet(sb.toString());
		
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_POST_SCHEMATA (       ");
		sb.append("\n     DB_NM                         ");
		sb.append("\n   , SCH_NM                        ");
		sb.append("\n   , DB_SCH_ID						");
		sb.append("\n   , CATALOG_NAME                  ");
		sb.append("\n   , DEFAULT_CHARACTER_SET_CATALOG "); //5
		sb.append("\n   , DEFAULT_CHARACTER_SET_SCHEMA  ");
		sb.append("\n   , DEFAULT_CHARACTER_SET_NAME    ");
		sb.append("\n   , SQL_PATH                      ");
		sb.append("\n ) VALUES (                        ");
		sb.append("\n    ?, ?, ?, ?, ?                  ");
		sb.append("\n  , ?, ?, ?                        ");
		sb.append("\n )                                 ");

		int cnt = 0;
		
		if( rs != null ) {
			
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NAME"));
				pst_org.setString(rsGetCnt++, schemaId);
				pst_org.setString(rsGetCnt++, rs.getString("CATALOG_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_CHARACTER_SET_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_CHARACTER_SET_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_CHARACTER_SET_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("SQL_PATH"));

				getSaveResult(rsCnt);

				if(rsCnt == execCnt)
				{
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
	private ResultSet getResultSet(String query_tgt) throws SQLException
	{
//		logger.debug("query_tgt:\n"+query_tgt);
		pst_tgt = null;
		rs = null;

		pst_tgt = con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		pst_tgt.setFetchSize(1000);
		rs = pst_tgt.executeQuery();

		return rs;
	}

	/**
	 * 타겟데이터의 SQL을 받아 CResultSet을 넘겨준다.
	 * @param String 조회 SQL
	 * @return CResultSet Query 결과값
	 * @throws SQLException
	 */
	private ResultSet getResultSet_Org(String query_org) throws SQLException
	{
		pst_org = con_org.prepareStatement(query_org);
		//logger.debug("query Org: " + query_org);
		rs = pst_org.executeQuery();

		return rs;
	}

	

	/**
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_POST_DB ("); 
		sb.append("\n   DB_NM                                            ");
		sb.append("\n  ,DB_CONN_TRG_ID                                   ");
		sb.append("\n  ,STR_DTM                                          ");
		sb.append("\n)                              ");
		sb.append("\n SELECT DISTINCT DB_CONN_TRG_PNM                                        ");
		sb.append("\n        ,A.DB_CONN_TRG_ID                                      ");
		sb.append("\n         ,A.STR_DTM                                            ");
		sb.append("\n   FROM WAA_DB_CONN_TRG A                                      ");
		sb.append("\n  INNER JOIN WAA_DB_SCH B                                      ");
		sb.append("\n     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID                    ");
		sb.append("\n    AND B.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND B.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND A.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  AND	A.DBMS_TYP_CD    = '").append(dbType).append("'") ;
		sb.append("\n  AND  UPPER(A.DB_CONN_TRG_PNM)   =    UPPER('").append(dbName).append("')") ;
//		sb.append("\n  AND  UPPER(B.OBJ_KNM)   =    UPPER('").append(sDBName).append("')") ;

//		logger.debug("insertMetaDATABASES : \n"+sb);

		return setExecuteUpdate_Org(sb.toString());
	}

	/**  insomnia */
	private int insertSchema() {
		int result = 0;


		return result;

	}

	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_POST_DB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_DB : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_SCHEMATA ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_SCH : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_TABLES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_TABLES : " + result);
		

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_STAT_USER_TABLES ");
		strSQL.append("\n   WHERE DB_NM = '"+ dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_DESCRIPTION ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_TBL : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COLUMNS : " + result);
		
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_PG_ATTRIBUTE ");
		strSQL.append("\n   WHERE DB_NM  = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' "); 

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_TABLE_CONSTRAINTS ");
		strSQL.append("\n   WHERE DB_NM  = '"+dbName+"'     ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' "); 

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_TABLE_CONSTRAINTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_POST_CONSTRAINT_COLUMN ");
		strSQL.append("\n   WHERE DB_NM  = '"+dbName+"'     ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' "); 

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_POST_CONSTRAINT_COLUMN : " + result);

		return result;
	}


	/**
	 * 타겟데이터의 SQL을 받아 저장한다.
	 * @param String 저장 SQL
	 * @return boolean 저장결과값
	 * @throws SQLException
	 */
	private int setExecuteUpdate(String query_tgt) throws SQLException
	{
		pst_tgt = con_tgt.prepareStatement(query_tgt);

		int cnt = pst_tgt.executeUpdate();

		pst_tgt.close();

		return cnt;
	}

	/**
	 * 타겟데이터의 SQL을 받아 저장한다.
	 * @param String 저장 SQL
	 * @return boolean 저장결과값
	 * @throws SQLException
	 */
	private int setExecuteUpdate_Org(String query_tgt) throws SQLException
	{
		logger.debug(query_tgt);
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
	private boolean getSaveResult(int cnt) throws SQLException
	{
		boolean result = false;

		//BigInteger z = new BigInteger(long(100));


		//if (remainder(cnt,) = 0 );
		//logger.debug(cnt);

		pst_org.addBatch();

		result = true;

		return result;
	}

	private BigInteger remainder(BigInteger val1, BigInteger val2)
    {
            return val1.remainder(val2);
    }
	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 */
	private int[] executeBatch() throws SQLException
	{
		return executeBatch(false);
	}

	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 */
	private int[] executeBatch(boolean execYN) throws SQLException
	{
		//logger.debug("단계:executeBatch [실행]");

		int[] i = pst_org.executeBatch();

		if(execYN)
		{
			//logger.debug("단계:executeBatch [execYN]");
			pst_org.clearBatch();
		}
		else
		{
			//logger.debug("단계:executeBatch [close]");
			rs.close();
			pst_org.close();
		}

		return i;
	}

	/**  insomnia */
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception {
		this.schemaName = "";
		this.schemaId = "";
		this.dbName = targetDb.getDbms_enm();
		this.dbType = targetDb.getDbms_type_cd();

		boolean result = false;

		int cnt = 0;

		cnt = insertwattbl();
		logger.debug("insertwattbl : " + cnt + " OK!!");

		//WAT_COL ---------------------------------------------------
		cnt = insertwatcol();
		logger.debug("insertwatcol : " + cnt + " OK!!");

		//WAT_INDEX ---------------------------------------------------
		cnt = insertwatidx();
		logger.debug("insertwatidx : " + cnt + " OK!!");

		//WAT_INDEX_COLUMN ---------------------------------------------------
		cnt = insertwatidxcol();
		logger.debug("insertwatidxcol : " + cnt + " OK!!");

		result = true;

		return result;
	}

	/**  insomnia
	 * @throws Exception */
	private int insertwattbl() throws Exception {

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAT_DBC_TBL                       ");
		sb.append("\n (                                             ");
		sb.append("\n         DB_SCH_ID                             ");
		sb.append("\n      ,  DBC_TBL_NM                            ");
		sb.append("\n      ,  DBC_TBL_KOR_NM                        ");
		sb.append("\n      ,  VERS                                  ");
		sb.append("\n      ,  REG_TYP                               ");
		sb.append("\n      ,  REG_DTM                               ");
		sb.append("\n      ,  UPD_DTM                               ");
		sb.append("\n      ,  DESCN                                 ");
		sb.append("\n      ,  DB_CONN_TRG_ID                        ");
		sb.append("\n      ,  DBC_TBL_SPAC_NM                       ");
		sb.append("\n      ,  DDL_TBL_ID                            ");
		sb.append("\n      ,  PDM_TBL_ID                            ");
		sb.append("\n      ,  DBMS_TYPE                             ");
		sb.append("\n      ,  SUBJ_ID                               ");
		sb.append("\n      ,  COL_EACNT                             ");
		sb.append("\n      ,  ROW_EACNT                             ");
		sb.append("\n      ,  TBL_SIZE                              ");
		sb.append("\n      ,  DATA_SIZE                             ");
		sb.append("\n      ,  IDX_SIZE                              ");
		sb.append("\n      ,  NUSE_SIZE                             ");
		sb.append("\n      ,  BF_COL_EACNT                          ");
		sb.append("\n      ,  BF_ROW_EACNT                          ");
		sb.append("\n      ,  BF_TBL_SIZE                           ");
		sb.append("\n      ,  BF_DATA_SIZE                          ");
		sb.append("\n      ,  BF_IDX_SIZE                           ");
		sb.append("\n      ,  BF_NUSE_SIZE                          ");
		sb.append("\n      ,  ANA_DTM                               ");
		sb.append("\n      ,  CRT_DTM                               ");
		sb.append("\n      ,  CHG_DTM                               ");
		sb.append("\n      ,  PDM_DESCN                             ");
		sb.append("\n      ,  TBL_DQ_EXP_YN                         "); 
		sb.append("\n )                                             "); 	 		
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID            ");
		sb.append("\n      , A.RELNAME             AS DBC_TBL_NM           ");
		sb.append("\n      , F.DESCRIPTION         AS DBC_TBL_KOR_NM       ");
		sb.append("\n      , '1'                   AS VERS                 ");
		sb.append("\n      , NULL                  AS REG_TYP              ");
		sb.append("\n      , SYSDATE               AS REG_DTM              ");
		sb.append("\n      , NULL                  AS UPD_DTM              ");
		sb.append("\n      , ''                    AS DESCN                ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID       ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM      ");
		sb.append("\n      , ''                    AS DDL_TBL_ID           ");
		sb.append("\n      , ''                    AS PDM_TBL_ID           ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE            ");
		sb.append("\n      , ''                    AS SUBJ_ID              ");
		sb.append("\n      , E.COL_CNT             AS COL_EACNT            ");
		sb.append("\n      , 0                     AS ROW_EACNT            ");
		sb.append("\n      , ''                    AS TBL_SIZE             ");
		sb.append("\n      , ''                    AS DATA_SIZE            ");
		sb.append("\n      , ''                    AS IDX_SIZE             ");
		sb.append("\n      , ''                    AS NUSE_SIZE            ");
		sb.append("\n      , ''                    AS BF_COL_EACNT         ");
		sb.append("\n      , ''                    AS BF_ROW_EACNT         ");
		sb.append("\n      , ''                    AS BF_TBL_SIZE          ");
		sb.append("\n      , ''                    AS BF_DATA_SIZE         ");
		sb.append("\n      , ''                    AS BF_IDX_SIZE          ");
		sb.append("\n      , ''                    AS BF_NUSE_SIZE         ");
		sb.append("\n      , ''                    AS ANA_DTM              ");
		sb.append("\n      , ''                    AS CRT_DTM              ");
		sb.append("\n      , ''                    AS CHG_DTM              ");
		sb.append("\n      , ''                    AS PDM_DESCN            ");
		sb.append("\n      , ''                    AS TBL_DQ_EXP_YN        "); 		
		sb.append("\n   FROM WAE_POST_PG_STAT_USER_TABLES A                            ");
		sb.append("\n        INNER JOIN (                                              ");
		sb.append("\n      " + getdbconnsql() + "                                      ");
		sb.append("\n        ) S                                                       ");
		sb.append("\n           ON S.DB_CONN_TRG_PNM = A.DB_NM                         ");
		sb.append("\n          AND S.DB_SCH_PNM      = A.SCH_NM                        ");
		sb.append("\n        INNER JOIN WAE_POST_DB B                                  ");
		sb.append("\n           ON A.DB_NM = B.DB_NM                                   ");
		sb.append("\n        LEFT JOIN                                                 ");
		sb.append("\n        (                                                         ");
		sb.append("\n          SELECT DB_NM, SCH_NM, TABLE_NAME, COUNT(*) AS COL_CNT   ");
		sb.append("\n            FROM WAE_POST_COLUMNS                                 ");
		sb.append("\n           GROUP BY DB_NM, SCH_NM, TABLE_NAME                     ");
		sb.append("\n         ) E                                                      ");
		sb.append("\n          ON E.DB_NM      = A.DB_NM                               ");
		sb.append("\n         AND E.SCH_NM     = A.SCH_NM                              ");
		sb.append("\n         AND E.TABLE_NAME = A.RELNAME                             ");
		sb.append("\n        LEFT JOIN WAE_POST_PG_DESCRIPTION F                       ");
		sb.append("\n          ON F.DB_NM      = A.DB_NM                               "); 
		sb.append("\n         AND F.SCH_NM     = A.SCH_NM                              ");
		sb.append("\n         AND F.SCH_NM     = A.SCH_NM                              ");
		sb.append("\n         AND F.OBJOID     = A.RELID                               "); 
		sb.append("\n         AND F.OBJSUBID  = 0                                      "); 
		sb.append("\n  WHERE A.DB_NM = '" + dbName + "'                                ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                                   ");
		sb.append("\n (                         ");
		sb.append("\n        DB_SCH_ID          ");
		sb.append("\n      , DBC_TBL_NM         ");      
		sb.append("\n      , DBC_COL_NM         ");      
		sb.append("\n      , DBC_COL_KOR_NM     ");      
		sb.append("\n      , VERS               ");      
		sb.append("\n      , REG_TYP            ");      
		sb.append("\n      , REG_DTM            ");      
		sb.append("\n      , UPD_DTM            ");      
		sb.append("\n      , DESCN              ");      
		sb.append("\n      , DDL_COL_ID         ");      
		sb.append("\n      , PDM_COL_ID         ");      
		sb.append("\n      , ITM_ID             ");      
		sb.append("\n      , DATA_TYPE          ");      
		sb.append("\n      , DATA_LEN           ");      
		sb.append("\n      , DATA_PNUM          ");      
		sb.append("\n      , DATA_PNT           ");      
		sb.append("\n      , NULL_YN            ");      
		sb.append("\n      , DEFLT_LEN          ");      
		sb.append("\n      , DEFLT_VAL          "); 
		sb.append("\n      , PK_YN              "); 
		sb.append("\n      , ORD                "); 
		sb.append("\n      , PK_ORD             "); 
		sb.append("\n      , COL_DESCN          "); 
		sb.append("\n      , COL_DQ_EXP_YN      ");
		sb.append("\n )                         "); 
		sb.append("\n SELECT S.DB_SCH_ID                     AS DB_SCH_ID                                  ");
		sb.append("\n      , A.RELNAME                       AS DBC_TBL_NM                                 ");
		sb.append("\n      , E.COLUMN_NAME                   AS DBC_COL_NM                                 ");
		sb.append("\n      , G.DESCRIPTION                   AS DBC_COL_KOR_NM                             ");
		sb.append("\n      , '1'                             AS VERS                                       ");
		sb.append("\n      , NULL                            AS REG_TYP                                    ");
		sb.append("\n      , SYSDATE                         AS REG_DTM                                    ");
		sb.append("\n      , ''                              AS UPD_DTM                                    ");
		sb.append("\n      , ''                              AS DESCN                                      ");
		sb.append("\n      , ''                              AS DDL_COL_ID                                 ");
		sb.append("\n      , ''                              AS PDM_COL_ID                                 ");
		sb.append("\n      , ''                              AS ITM_ID                                     ");
		sb.append("\n      , E.DATA_TYPE                     AS DATA_TYPE                                  ");
		sb.append("\n      , E.CHARACTER_MAXIMUM_LENGTH      AS DATA_LEN                                   ");
		sb.append("\n      , 0                               AS DATA_PNUM                                  ");
		sb.append("\n      , E.NUMERIC_SCALE                 AS DATA_PNT                                   ");
		sb.append("\n      , SUBSTR(E.IS_NULLABLE,1,1)       AS NULL_YN                                    ");
		sb.append("\n      , 0                               AS DEFLT_LEN                                  ");
		sb.append("\n      , E.COLUMN_DEFAULT                AS DEFLT_VAL                                  ");
		sb.append("\n      , (CASE WHEN F.DB_NM IS NOT NULL THEN 'Y' ELSE 'N' END) AS PK_YN                ");
		sb.append("\n      , E.ORDINAL_POSITION              AS ORD                                        ");
		sb.append("\n      , (CASE WHEN F.DB_NM IS NOT NULL THEN E.ORDINAL_POSITION END) AS PK_ORD         ");
		sb.append("\n      , ''                              AS COL_DESCN                                  ");
		sb.append("\n      , ''                              AS COL_DQ_EXP_YN                              ");
		sb.append("\n   FROM WAE_POST_PG_STAT_USER_TABLES A                                                ");
		sb.append("\n        INNER JOIN (                                                                  ");
		sb.append("\n                                                                                      ");
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
		sb.append("\n        INNER JOIN WAE_POST_DB B                                                      ");
		sb.append("\n           ON B.DB_NM = A.DB_NM                                                       ");                               
		sb.append("\n        INNER JOIN WAE_POST_COLUMNS  E                                                ");
		sb.append("\n           ON E.DB_NM       = A.DB_NM                                                 ");
		sb.append("\n          AND E.SCH_NM      = A.SCH_NM                                                ");
		sb.append("\n          AND E.TABLE_NAME  = A.RELNAME                                               ");
		sb.append("\n        LEFT JOIN                                                                     ");
		sb.append("\n        (                                                                             ");
		sb.append("\n         SELECT A.DB_NM, A.SCH_NM, A.TABLE_NAME                                       ");
		sb.append("\n              , B.COLUMN_NAME                                                         ");
		sb.append("\n           FROM WAE_POST_TABLE_CONSTRAINTS A                                          ");
		sb.append("\n                INNER JOIN WAE_POST_CONSTRAINT_COLUMN B                               ");
		sb.append("\n                   ON B.DB_NM           = A.DB_NM                                     ");
		sb.append("\n                  AND B.SCH_NM          = A.SCH_NM                                    ");
		sb.append("\n                  AND B.TABLE_NAME      = A.TABLE_NAME                                ");
		sb.append("\n                  AND B.CONSTRAINT_NAME = A.CONSTRAINT_NAME                           ");
		sb.append("\n          WHERE A.CONSTRAINT_TYPE = 'PRIMARY KEY'                                     ");
		sb.append("\n        ) F                                                                           ");
		sb.append("\n         ON F.DB_NM       = E.DB_NM                                                   ");
		sb.append("\n        AND F.SCH_NM      = E.SCH_NM                                                  ");
		sb.append("\n        AND F.TABLE_NAME  = E.TABLE_NAME                                              ");
		sb.append("\n        AND F.COLUMN_NAME = E.COLUMN_NAME                                             ");
		sb.append("\n        LEFT JOIN                                                                     ");
		sb.append("\n        (                                                                             ");
		sb.append("\n         SELECT C.DB_NM, C.SCH_NM, D.ATTRELID, D.ATTNAME, C.DESCRIPTION               ");
		sb.append("\n           FROM WAE_POST_PG_DESCRIPTION C                                             ");
		sb.append("\n                INNER JOIN WAE_POST_PG_ATTRIBUTE D                                    ");
		sb.append("\n                   ON D.DB_NM    = C.DB_NM                                            ");
		sb.append("\n                  AND D.SCH_NM   = C.SCH_NM                                           ");
		sb.append("\n                  AND D.ATTRELID = C.OBJOID                                           ");
		sb.append("\n                  AND D.ATTNUM   = C.OBJSUBID                                         ");
		sb.append("\n          WHERE C.OBJSUBID != 0                                                       ");
		sb.append("\n        ) G                                                                           ");
		sb.append("\n        ON G.DB_NM    = A.DB_NM                                                       ");
		sb.append("\n       AND G.SCH_NM   = A.SCH_NM                                                      ");
		sb.append("\n       AND G.ATTRELID = A.RELID                                                       ");
		sb.append("\n       AND G.ATTNAME  = E.COLUMN_NAME                                                 ");                                         	    
		sb.append("\n   WHERE A.DB_NM = '" + dbName + "'                          ");

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

		return strSQL.toString();

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidx() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX                                     ");
		sb.append("\n SELECT S.DB_SCH_ID                                                    ");
		sb.append("\n      , A.TABLE_NAME                                                                  ");
		sb.append("\n      , A.INDEX_NAME                                                                  ");
		sb.append("\n      , S.DB_CONN_TRG_ID                                                          ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , '1'                                                                       ");
		sb.append("\n      , NULL                                                                       ");
		sb.append("\n      , SYSDATE                                                                   ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                             ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , CASE WHEN A.INDEX_NAME = 'PRIMARY' THEN 'Y' ELSE 'N' END PK_YN                   ");
		sb.append("\n      , CASE WHEN A.NON_UNIQUE = 0 THEN 'Y' ELSE 'N' END UQ_YN                    ");
		sb.append("\n      , COUNT(*) AS COL_CNT                                                                 ");
		sb.append("\n      , '' AS IDX_SIZE                                                       ");
		sb.append("\n      , ''  AS BF_IDX_EACNT          ");
		sb.append("\n      , ''  AS BF_IDX_SIZE             ");
		sb.append("\n      , ''  AS ANA_DTM               ");
		sb.append("\n      , ''  AS CRT_DTM                ");
		sb.append("\n      , ''  AS CHG_DTM                ");
		sb.append("\n      , ''  AS IDX_DQ_EXP_YN        ");
		sb.append("\n      , ''  AS SGMT_BYTE_SIZE       ");
		sb.append("\n      , '' AS DDL_IDX_CMPS_CONTS ");
		sb.append("\n      , '' AS PDM_IDX_CMPS_CONTS ");
		sb.append("\n      , '' AS DDL_IDX_ERR_EXS       ");
		sb.append("\n      , '' AS DDL_IDX_ERR_CD        ");
		sb.append("\n      , '' AS DDL_IDX_ERR_DESCN   ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_EXS ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_CD  ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_DESCN ");
		sb.append("\n      , '' AS PDM_IDX_ERR_EXS     ");
		sb.append("\n      , '' AS PDM_IDX_ERR_CD      ");
		sb.append("\n      , '' AS PDM_IDX_ERR_DESCN  ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_CD  ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_DESCN ");
		sb.append("\n      , '' AS DDL_IDX_EXTNC_EXS    ");
		sb.append("\n      , '' AS PDM_IDX_EXTNC_EXS   ");
		sb.append("\n   FROM WAE_POST_IDX A                                                             ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_POST_DB B                                                         ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                                         ");
		
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");
		sb.append("\n   GROUP BY S.DB_SCH_ID, A.TABLE_NAME, A.INDEX_NAME, S.DB_CONN_TRG_ID,A.NON_UNIQUE    ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT S.DB_SCH_ID      ");
		sb.append("\n      , A.TABLE_NAME                    ");
		sb.append("\n      , A.COLUMN_NAME                    ");
		sb.append("\n      , A.INDEX_NAME                    ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , '1'                         ");
		sb.append("\n      , NULL                         ");
		sb.append("\n      , SYSDATE                     ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , SEQ_IN_INDEX                 ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , C.DATA_TYPE                 ");
		sb.append("\n      , C.NUMERIC_PRECISION                 ");
		sb.append("\n      , C.CHARACTER_MAXIMUM_LENGTH                  ");
		sb.append("\n      , C.NUMERIC_SCALE                  ");
		sb.append("\n      , '' AS IDXCOL_DQ_EXP_YN                         ");
		sb.append("\n      , '' AS DDL_IDX_COL_LNM_ERR_EXS                       ");
		sb.append("\n      , '' AS DDL_IDX_COL_ORD_ERR_EXS                        ");
		sb.append("\n      , '' AS DDL_IDX_COL_SORT_TYPE_ERR_EXS                        ");
		sb.append("\n      , '' AS DDL_IDX_COL_EXTNC_EXS                       ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_EXS                         ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_CONTS                        ");
		sb.append("\n      , '' AS PDM_IDX_COL_LNM_ERR_EXS                         ");
		sb.append("\n      , '' AS PDM_IDX_COL_ORD_ERR_EXS                         ");
		sb.append("\n      , '' AS PDM_IDX_COL_SORT_TYPE_ERR_EXS                         ");
		sb.append("\n      , '' AS PDM_IDX_COL_EXTNC_EXS                         ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_EXS                         ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_CONTS                         ");
		sb.append("\n   FROM WAE_POST_IDX A           ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_POST_DB B           ");
		sb.append("\n     ON A.DB_NM = B.DB_NM           ");
		sb.append("\n  INNER JOIN WAE_POST_COLUMNS C          ");
		sb.append("\n     ON A.DB_NM = C.DB_NM           ");
		sb.append("\n    AND A.SCH_NM = C.SCH_NM         ");
		sb.append("\n    AND A.TABLE_NAME = C.TABLE_NAME         ");
		sb.append("\n    AND A.COLUMN_NAME = C.COLUMN_NAME         ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}
	
	
	


}
