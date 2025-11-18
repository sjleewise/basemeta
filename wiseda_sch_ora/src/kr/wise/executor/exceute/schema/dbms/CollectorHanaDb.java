/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorDB2Udb.java
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import kr.wise.executor.dm.TargetDbmsDM;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorHanaDb.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorHanaDb {

	private static final Logger logger = Logger.getLogger(CollectorHanaDb.class);

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

	public CollectorHanaDb() {

	}

	public CollectorHanaDb(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
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
			this.schemaName = targetDb.getDbc_owner_nm();
			this.schemaId   = targetDb.getDbSchId();
			this.dbName     = targetDb.getDbms_enm();
			this.dbType     = targetDb.getDbms_type_cd();
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
			
			cnt = insertTABLES();
			logger.debug(sp + (++p) + ". insertTABLES " + cnt + " OK!!");

			cnt = insertTABLE_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");
			
//			cnt = insertREFERENCED_CONSTRAINT();
			logger.debug(sp + (++p) + ". insertREFERENCED_CONSTRAINT " + cnt + " OK!!");
			
			con_org.commit();
		}
		result = true;

		return result;
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
	
	private int insertTABLES() throws SQLException {
		int cnt = 0;
		
		StringBuffer sb = new StringBuffer();
//  	sb.append("\n SELECT A.SCHEMA_NAME                          ");
//		sb.append("\n      , A.TABLE_NAME                           ");
//		sb.append("\n      , A.TABLE_OID                            ");
//		sb.append("\n      , A.COMMENTS                             ");
//		sb.append("\n      , A.FIXED_PART_SIZE                      ");
//		sb.append("\n      , A.IS_LOGGED                            ");
//		sb.append("\n      , A.IS_SYSTEM_TABLE                      ");
//		sb.append("\n      , A.IS_COLUMN_TABLE                      ");
//		sb.append("\n      , A.TABLE_TYPE                           ");
//		sb.append("\n      , A.IS_INSERT_ONLY                       ");
//		sb.append("\n      , A.IS_TENANT_SHARED_DATA                ");
//		sb.append("\n      , A.IS_TENANT_SHARED_METADATA            ");
//		sb.append("\n      , A.SESSION_TYPE                         ");
//		sb.append("\n      , A.IS_TEMPORARY                         ");
//		sb.append("\n      , A.TEMPORARY_TABLE_TYPE                 ");
//		sb.append("\n      , A.COMMIT_ACTION                        ");
//		sb.append("\n      , A.IS_USER_DEFINED_TYPE                 ");
//		sb.append("\n      , A.HAS_PRIMARY_KEY                      ");
//		sb.append("\n      , A.PARTITION_SPEC                       ");
//		sb.append("\n      , A.USES_EXTKEY                          ");
//		sb.append("\n      , A.AUTO_MERGE_ON                        ");
//		sb.append("\n      , A.USES_DIMFN_CACHE                     ");
//		sb.append("\n      , A.IS_PUBLIC                            ");
//		sb.append("\n      , A.AUTO_OPTIMIZE_COMPRESSION_ON         ");
//		sb.append("\n      , A.COMPRESSED_EXTKEY                    ");
//		sb.append("\n      , A.HAS_TEXT_FIELDS                      ");
//		sb.append("\n      , A.USES_QUEUE_TABLE                     ");
//		sb.append("\n      , A.IS_PRELOAD                           ");
//		sb.append("\n      , A.IS_PARTIAL_PRELOAD                   ");
//		sb.append("\n      , A.UNLOAD_PRIORITY                      ");
//		sb.append("\n      , A.HAS_SCHEMA_FLEXIBILITY               ");
//		sb.append("\n      , A.IS_REPLICA                           ");
//		sb.append("\n      , A.HAS_STRUCTURED_PRIVILEGE_CHECK       ");
//		sb.append("\n      , A.IS_SERIES_TABLE                      ");
//		sb.append("\n      , A.ROW_ORDER_TYPE                       ");
//		sb.append("\n      , A.CREATE_TIME                          ");
//		sb.append("\n      , A.TEMPORAL_TYPE                        ");
//		sb.append("\n      , A.HAS_MASKED_COLUMNS                   ");
//		sb.append("\n      , A.PERSISTENT_MEMORY                    ");
//		sb.append("\n      , A.HAS_RECORD_COMMIT_TIMESTAMP          ");
//		sb.append("\n      , A.IS_REPLICATION_LOG_ENABLED           ");
////		sb.append("\n      , B.RECORD_COUNT			              ");
//		sb.append("\n   FROM TABLES A                         ");
////		sb.append("\n   INNER JOIN M_TABLES B                 ");
////		sb.append("\n   ON A.TABLE_NAME = B.TABLE_NAME            ");
//  		sb.append("\n  WHERE UPPER(A.SCHEMA_NAME) = UPPER('").append(schemaName).append("')  ");
		
		//연세의료원
		sb.append("\n SELECT A.SCHEMA_NAME                          ");
		sb.append("\n      , A.TABLE_NAME                           ");
		sb.append("\n      , B.TABLE_OID                            ");
		sb.append("\n      , B.COMMENTS                             ");
		sb.append("\n      , '' AS FIXED_PART_SIZE                      ");
		sb.append("\n      , '' AS IS_LOGGED                            ");
		sb.append("\n      , '' AS IS_SYSTEM_TABLE                      ");
		sb.append("\n      , '' AS IS_COLUMN_TABLE                      ");
		sb.append("\n      , '' AS TABLE_TYPE                           ");
		sb.append("\n      , '' AS IS_INSERT_ONLY                       ");
		sb.append("\n      , '' AS IS_TENANT_SHARED_DATA                ");
		sb.append("\n      , '' AS IS_TENANT_SHARED_METADATA            ");
		sb.append("\n      , '' AS SESSION_TYPE                         ");
		sb.append("\n      , '' AS IS_TEMPORARY                         ");
		sb.append("\n      , '' AS TEMPORARY_TABLE_TYPE                 ");
		sb.append("\n      , '' AS COMMIT_ACTION                        ");
		sb.append("\n      , '' AS IS_USER_DEFINED_TYPE                 ");
		sb.append("\n      , '' AS HAS_PRIMARY_KEY                      ");
		sb.append("\n      , '' AS PARTITION_SPEC                       ");
		sb.append("\n      , '' AS USES_EXTKEY                          ");
		sb.append("\n      , '' AS AUTO_MERGE_ON                        ");
		sb.append("\n      , '' AS USES_DIMFN_CACHE                     ");
		sb.append("\n      , '' AS IS_PUBLIC                            ");
		sb.append("\n      , '' AS AUTO_OPTIMIZE_COMPRESSION_ON         ");
		sb.append("\n      , '' AS COMPRESSED_EXTKEY                    ");
		sb.append("\n      , '' AS HAS_TEXT_FIELDS                      ");
		sb.append("\n      , '' AS USES_QUEUE_TABLE                     ");
		sb.append("\n      , '' AS IS_PRELOAD                           ");
		sb.append("\n      , '' AS IS_PARTIAL_PRELOAD                   ");
		sb.append("\n      , '' AS UNLOAD_PRIORITY                      ");
		sb.append("\n      , '' AS HAS_SCHEMA_FLEXIBILITY               ");
		sb.append("\n      , '' AS IS_REPLICA                           ");
		sb.append("\n      , '' AS HAS_STRUCTURED_PRIVILEGE_CHECK       ");
		sb.append("\n      , '' AS IS_SERIES_TABLE                      ");
		sb.append("\n      , '' AS ROW_ORDER_TYPE                       ");
		sb.append("\n      , A.CREATE_TIME                          	");
		sb.append("\n      , '' AS TEMPORAL_TYPE                        ");
		sb.append("\n      , '' AS HAS_MASKED_COLUMNS                   ");
		sb.append("\n      , '' AS PERSISTENT_MEMORY                    ");
		sb.append("\n      , '' AS HAS_RECORD_COMMIT_TIMESTAMP          ");
		sb.append("\n      , '' AS IS_REPLICATION_LOG_ENABLED           ");
		sb.append("\n      , A.RECORD_COUNT			              ");
		sb.append("\n   FROM M_CS_TABLES A                         ");
		sb.append("\n  INNER JOIN TABLES B                         ");
		sb.append("\n     ON B.SCHEMA_NAME = A.SCHEMA_NAME         ");
		sb.append("\n    AND B.TABLE_NAME = A.TABLE_NAME           ");
//		sb.append("\n    AND A.MODIFY_TIME = (SELECT MAX(C.MODIFY_TIME) FROM M_CS_TABLES C WHERE C.SCHEMA_NAME = A.SCHEMA_NAME AND C.TABLE_NAME = A.TABLE_NAME)           ");
//		sb.append("\n    AND A.CREATE_TIME = (SELECT MAX(D.CREATE_TIME) FROM M_CS_TABLES D WHERE D.SCHEMA_NAME = A.SCHEMA_NAME AND D.TABLE_NAME = A.TABLE_NAME)           ");
		
//		sb.append("\n   INNER JOIN M_TABLES B                 ");
//		sb.append("\n   ON A.TABLE_NAME = B.TABLE_NAME            ");
  		sb.append("\n  WHERE UPPER(A.SCHEMA_NAME) = UPPER('").append(schemaName).append("')  ");
  		sb.append("\n    AND A.LAST_MERGE_TIME = (SELECT MAX(D.LAST_MERGE_TIME) FROM M_CS_TABLES D WHERE D.SCHEMA_NAME = A.SCHEMA_NAME AND D.TABLE_NAME = A.TABLE_NAME)           ");
  		
  		logger.debug("\n " + sb.toString());
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_HAN_TABLES                  ");
		sb.append("\n (                                           ");
		sb.append("\n        DB_NM                                ");
		sb.append("\n      , SCH_NM                               ");
		sb.append("\n      , TABLE_NAME                           ");
		sb.append("\n      , TABLE_OID                            ");
		sb.append("\n      , COMMENTS                             "); //5
		sb.append("\n      , FIXED_PART_SIZE                      "); 
		sb.append("\n      , IS_LOGGED                            ");
		sb.append("\n      , IS_SYSTEM_TABLE                      ");
		sb.append("\n      , IS_COLUMN_TABLE                      ");
		sb.append("\n      , TABLE_TYPE                           "); //10
		sb.append("\n      , IS_INSERT_ONLY                       "); 
		sb.append("\n      , IS_TENANT_SHARED_DATA                ");
		sb.append("\n      , IS_TENANT_SHARED_METADATA            ");
		sb.append("\n      , SESSION_TYPE                         ");
		sb.append("\n      , IS_TEMPORARY                         "); //15
		sb.append("\n      , TEMPORARY_TABLE_TYPE                 "); 
		sb.append("\n      , COMMIT_ACTION                        ");
		sb.append("\n      , IS_USER_DEFINED_TYPE                 ");
		sb.append("\n      , HAS_PRIMARY_KEY                      ");
		sb.append("\n      , PARTITION_SPEC                       "); //20
		sb.append("\n      , USES_EXTKEY                          "); 
		sb.append("\n      , AUTO_MERGE_ON                        ");
		sb.append("\n      , USES_DIMFN_CACHE                     ");
		sb.append("\n      , IS_PUBLIC                            ");
		sb.append("\n      , AUTO_OPTIMIZE_COMPRESSION_ON         "); //25
		sb.append("\n      , COMPRESSED_EXTKEY                    "); 
		sb.append("\n      , HAS_TEXT_FIELDS                      ");
		sb.append("\n      , USES_QUEUE_TABLE                     ");
		sb.append("\n      , IS_PRELOAD                           ");
		sb.append("\n      , IS_PARTIAL_PRELOAD                   "); //30
		sb.append("\n      , UNLOAD_PRIORITY                      "); 
		sb.append("\n      , HAS_SCHEMA_FLEXIBILITY               ");
		sb.append("\n      , IS_REPLICA                           ");
		sb.append("\n      , HAS_STRUCTURED_PRIVILEGE_CHECK       ");
		sb.append("\n      , IS_SERIES_TABLE                      "); //35
		sb.append("\n      , ROW_ORDER_TYPE                       "); 
		sb.append("\n      , CREATE_TIME                          ");
		sb.append("\n      , TEMPORAL_TYPE                        ");
		sb.append("\n      , HAS_MASKED_COLUMNS                   ");
		sb.append("\n      , PERSISTENT_MEMORY                    "); //40
		sb.append("\n      , HAS_RECORD_COMMIT_TIMESTAMP          "); 
		sb.append("\n      , IS_REPLICATION_LOG_ENABLED           ");
		sb.append("\n      , ROW_EACNT					          ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n  )                              ");
		sb.append("\n  VALUES                         ");
		sb.append("\n  (                              ");
		sb.append("\n       ?, ?, ?, ?, ?             ");
		sb.append("\n     , ?, ?, ?, ?, ?             ");  //10
		sb.append("\n     , ?, ?, ?, ?, ?             ");
		sb.append("\n     , ?, ?, ?, ?, ?             ");  //20
		sb.append("\n     , ?, ?, ?, ?, ?             ");
		sb.append("\n     , ?, ?, ?, ?, ?             ");  //30
		sb.append("\n     , ?, ?, ?, ?, ?             ");
		sb.append("\n     , ?, ?, ?, ?, ?             ");  //40
		sb.append("\n     , ?, ?, ?, ?                ");
		sb.append("\n  )                              ");
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
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_OID"));  				
  				pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));   //5
  				pst_org.setString(rsGetCnt++, rs.getString("FIXED_PART_SIZE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_LOGGED"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_SYSTEM_TABLE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_COLUMN_TABLE"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));  	 //10			  				
  				pst_org.setString(rsGetCnt++, rs.getString("IS_INSERT_ONLY"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_TENANT_SHARED_DATA"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_TENANT_SHARED_METADATA"));
  				pst_org.setString(rsGetCnt++, rs.getString("SESSION_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_TEMPORARY")); //15
  				pst_org.setString(rsGetCnt++, rs.getString("TEMPORARY_TABLE_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("COMMIT_ACTION"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_USER_DEFINED_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_PRIMARY_KEY"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARTITION_SPEC")); //20
  				pst_org.setString(rsGetCnt++, rs.getString("USES_EXTKEY"));
  				pst_org.setString(rsGetCnt++, rs.getString("AUTO_MERGE_ON"));
  				pst_org.setString(rsGetCnt++, rs.getString("USES_DIMFN_CACHE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PUBLIC"));
  				pst_org.setString(rsGetCnt++, rs.getString("AUTO_OPTIMIZE_COMPRESSION_ON"));  //25
  				pst_org.setString(rsGetCnt++, rs.getString("COMPRESSED_EXTKEY"));
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_TEXT_FIELDS"));
  				pst_org.setString(rsGetCnt++, rs.getString("USES_QUEUE_TABLE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PRELOAD"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PARTIAL_PRELOAD"));  //30
  				pst_org.setString(rsGetCnt++, rs.getString("UNLOAD_PRIORITY"));  				
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_SCHEMA_FLEXIBILITY"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_REPLICA"));
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_STRUCTURED_PRIVILEGE_CHECK"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_SERIES_TABLE"));  //35  				  				
  				pst_org.setString(rsGetCnt++, rs.getString("ROW_ORDER_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("CREATE_TIME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TEMPORAL_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_MASKED_COLUMNS"));
  				pst_org.setString(rsGetCnt++, rs.getString("PERSISTENT_MEMORY")); //40
  				pst_org.setString(rsGetCnt++, rs.getString("HAS_RECORD_COMMIT_TIMESTAMP"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_REPLICATION_LOG_ENABLED"));
  				pst_org.setString(rsGetCnt++, rs.getString("RECORD_COUNT"));
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

	private int insertTABLE_COLUMNS() throws SQLException {
		int cnt = 0;
		
		StringBuffer sb = new StringBuffer();
  		sb.append("\nSELECT DISTINCT A.SCHEMA_NAME                        ");
        sb.append("\n     , A.TABLE_NAME                         ");
        sb.append("\n     , B.TABLE_OID                          ");
        sb.append("\n     , A.COLUMN_NAME                        ");
        sb.append("\n     , B.POSITION                           ");
        sb.append("\n     , B.DATA_TYPE_ID                       ");
        sb.append("\n     , B.DATA_TYPE_NAME                     ");
        sb.append("\n     , B.OFFSET                             ");
        sb.append("\n     , B.LENGTH                             ");
        sb.append("\n     , B.SCALE                              ");
        sb.append("\n     , B.IS_NULLABLE                        ");
        sb.append("\n     , B.DEFAULT_VALUE                      ");
        sb.append("\n     , B.COLLATION                          ");
        sb.append("\n     , B.COMMENTS                           ");
        sb.append("\n     , B.MAX_VALUE                          ");
        sb.append("\n     , B.MIN_VALUE                          ");
        sb.append("\n     , B.CS_DATA_TYPE_ID                    ");
        sb.append("\n     , B.CS_DATA_TYPE_NAME                  ");
        sb.append("\n     , B.DDIC_DATA_TYPE_ID                  ");
        sb.append("\n     , B.DDIC_DATA_TYPE_NAME                ");
        sb.append("\n     , B.COMPRESSION_TYPE                   ");
        sb.append("\n     , B.INDEX_TYPE                         ");
        sb.append("\n     , B.COLUMN_ID                          ");
        sb.append("\n     , B.PRELOAD                            ");
        sb.append("\n     , B.GENERATED_ALWAYS_AS                ");
        sb.append("\n     , B.HAS_SCHEMA_FLEXIBILITY             ");
        sb.append("\n     , B.FUZZY_SEARCH_INDEX                 ");
        sb.append("\n     , B.FUZZY_SEARCH_MODE                  ");
        sb.append("\n     , B.MEMORY_THRESHOLD                   ");
        sb.append("\n     , B.LOAD_UNIT                          ");
        sb.append("\n     , B.GENERATION_TYPE                    ");
        sb.append("\n     , B.IS_CACHABLE                        ");
        sb.append("\n     , B.IS_CACHE_KEY                       ");
        sb.append("\n     , B.ROW_ORDER_POSITION                 ");
        sb.append("\n     , B.IS_HIDDEN                          ");
        sb.append("\n     , B.IS_MASKED                          ");
        sb.append("\n     , B.MASK_EXPRESSION                    ");   
        sb.append("\n     , B.PERSISTENT_MEMORY                  ");
        sb.append("\n  FROM M_CS_ALL_COLUMNS A                  ");
        sb.append("\n INNER JOIN TABLE_COLUMNS B                ");
        sb.append("\n    ON B.SCHEMA_NAME = A.SCHEMA_NAME       ");
        sb.append("\n   AND B.TABLE_NAME = A.TABLE_NAME         ");
        sb.append("\n   AND B.COLUMN_NAME = A.COLUMN_NAME       ");
  		sb.append("\n WHERE UPPER(A.SCHEMA_NAME) = UPPER('").append(schemaName).append("') ");
  		logger.debug("\n " + sb.toString());
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_HAN_TABLE_COLUMNS ");
		sb.append("\n (                                 ");
		sb.append("\n     DB_NM                         ");
		sb.append("\n   , SCH_NM                        ");
		sb.append("\n   , TABLE_NAME                    ");
		sb.append("\n   , TABLE_OID                     ");
        sb.append("\n   , COLUMN_NAME                   "); //5
        sb.append("\n   , POSITION                      "); 
        sb.append("\n   , DATA_TYPE_ID                ");
        sb.append("\n   , DATA_TYPE_NAME                ");
        sb.append("\n   , OFFSET                        ");
        sb.append("\n   , LENGTH                        "); //10
        sb.append("\n   , SCALE                         "); 
        sb.append("\n   , IS_NULLABLE                   ");
        sb.append("\n   , DEFAULT_VALUE                 ");
        sb.append("\n   , COLLATION                     ");
        sb.append("\n   , COMMENTS                      "); //15
        sb.append("\n   , MAX_VALUE                     "); 
        sb.append("\n   , MIN_VALUE                     ");
        sb.append("\n   , CS_DATA_TYPE_ID               ");
        sb.append("\n   , CS_DATA_TYPE_NAME             ");
        sb.append("\n   , DDIC_DATA_TYPE_ID             "); //20
        sb.append("\n   , DDIC_DATA_TYPE_NAME           "); 
        sb.append("\n   , COMPRESSION_TYPE              ");
        sb.append("\n   , INDEX_TYPE                    ");
        sb.append("\n   , COLUMN_ID                     ");
        sb.append("\n   , PRELOAD                       "); //25
        sb.append("\n   , GENERATED_ALWAYS_AS           "); 
        sb.append("\n   , HAS_SCHEMA_FLEXIBILITY        ");
        sb.append("\n   , FUZZY_SEARCH_INDEX            ");
        sb.append("\n   , FUZZY_SEARCH_MODE             ");
        sb.append("\n   , MEMORY_THRESHOLD              "); //30
        sb.append("\n   , LOAD_UNIT                     "); 
        sb.append("\n   , GENERATION_TYPE               ");
        sb.append("\n   , IS_CACHABLE                   ");
        sb.append("\n   , IS_CACHE_KEY                  ");
        sb.append("\n   , ROW_ORDER_POSITION            "); //35
        sb.append("\n   , IS_HIDDEN                     "); 
        sb.append("\n   , IS_MASKED                     ");
        sb.append("\n   , MASK_EXPRESSION               ");   
        sb.append("\n   , PERSISTENT_MEMORY             ");
        sb.append("\n   , DB_CONN_TRG_ID                  ");
		sb.append("\n  )                                ");
		sb.append("\n  VALUES                           ");  
		sb.append("\n  (                                ");
		sb.append("\n       ?, ?, ?, ?, ?               ");
		sb.append("\n     , ?, ?, ?, ?, ?               ");  //10
		sb.append("\n     , ?, ?, ?, ?, ?               ");
		sb.append("\n     , ?, ?, ?, ?, ?               ");  //20
		sb.append("\n     , ?, ?, ?, ?, ?               ");
		sb.append("\n     , ?, ?, ?, ?, ?               ");  //30
		sb.append("\n     , ?, ?, ?, ?, ?               "); 
		sb.append("\n     , ?, ?, ?, ?, ?               ");  
		sb.append("\n  )                                ");
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
  		  		pst_org.setString(rsGetCnt++, rs.getString("TABLE_OID"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));  //5
  		  		pst_org.setString(rsGetCnt++, rs.getString("POSITION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE_ID"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("OFFSET"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("LENGTH")); //10
  		  		pst_org.setString(rsGetCnt++, rs.getString("SCALE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_VALUE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COMMENTS"));  //15
  		  		pst_org.setString(rsGetCnt++, rs.getString("MAX_VALUE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("MIN_VALUE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CS_DATA_TYPE_ID"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CS_DATA_TYPE_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DDIC_DATA_TYPE_ID")); //20
  		  		pst_org.setString(rsGetCnt++, rs.getString("DDIC_DATA_TYPE_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COMPRESSION_TYPE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLUMN_ID"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("PRELOAD"));  //25
  		  		pst_org.setString(rsGetCnt++, rs.getString("GENERATED_ALWAYS_AS"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("HAS_SCHEMA_FLEXIBILITY"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("FUZZY_SEARCH_INDEX"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("FUZZY_SEARCH_MODE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("MEMORY_THRESHOLD"));  //30
  		  		pst_org.setString(rsGetCnt++, rs.getString("LOAD_UNIT"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("GENERATION_TYPE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_CACHABLE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_CACHE_KEY"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("ROW_ORDER_POSITION"));  //35
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_HIDDEN"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_MASKED"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("MASK_EXPRESSION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("PERSISTENT_MEMORY"));
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
	
	private int insertREFERENCED_CONSTRAINT() throws SQLException {
		int cnt = 0;
		StringBuffer sb = new StringBuffer();
  		sb.append("\nSELECT SCHEMA_NAME                        ");
        sb.append("\n     , TABLE_NAME                         ");
        sb.append("\n     , COLUMN_NAME                        ");
        sb.append("\n     , POSITION                        	");
        sb.append("\n     , CONSTRAINT_NAME                     ");
        sb.append("\n     , REFERENCED_SCHEMA_NAME              ");
        sb.append("\n     , REFERENCED_TABLE_NAME             	");
        sb.append("\n     , REFERENCED_COLUMN_NAME              ");
        sb.append("\n     , REFERENCED_CONSTRAINT_NAME          ");
        sb.append("\n     , UPDATE_RULE                   		");
        sb.append("\n     , DELETE_RULE                         ");
        sb.append("\n     , IS_ENFORCED                    		");
        sb.append("\n     , IS_VALIDATED                        ");
        sb.append("\n     , CHECK_TIME                       	");
  		sb.append("\n  FROM REFERENTIAL_CONSTRAINTS         ");  		
  		sb.append("\n WHERE UPPER(SCHEMA_NAME) = UPPER('").append(schemaName).append("') ");
  		//logger.debug("\n " + sb.toString());
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		sb.append("\n INSERT INTO WAE_HAN_REFERENCED_CONSTRAINT ");
		sb.append("\n (                                 ");
		sb.append("\n     DB_NM                         ");
		sb.append("\n   , SCH_NM                        ");
		sb.append("\n   , TABLE_NAME                    ");
        sb.append("\n   , COLUMN_NAME                   "); 
        sb.append("\n   , POSITION                     "); 
        sb.append("\n   , CONSTRAINT_NAME                ");
        sb.append("\n   , REFERENCED_SCHEMA_NAME         ");
        sb.append("\n   , REFERENCED_TABLE_NAME          ");
        sb.append("\n   , REFERENCED_COLUMN_NAME         ");
        sb.append("\n   , REFERENCED_CONSTRAINT_NAME     "); 
        sb.append("\n   , UPDATE_RULE                    ");
        sb.append("\n   , DELETE_RULE                 	 ");
        sb.append("\n   , IS_ENFORCED                    ");
        sb.append("\n   , IS_VALIDATED                   ");
        sb.append("\n   , CHECK_TIME                     ");
        sb.append("\n   , DB_CONN_TRG_ID                 ");
		sb.append("\n  )                                ");
		sb.append("\n  VALUES                           ");  
		sb.append("\n  (                                ");
		sb.append("\n       ?, ?, ?, ?, ?               ");
		sb.append("\n     , ?, ?, ?, ?, ?               "); 
		sb.append("\n     , ?, ?, ?, ?, ?               ");
		sb.append("\n     , ?               ");
		sb.append("\n  )                                ");
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
  		  		pst_org.setString(rsGetCnt++, rs.getString("POSITION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("REFERENCED_SCHEMA_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("REFERENCED_TABLE_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("REFERENCED_COLUMN_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("REFERENCED_CONSTRAINT_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("UPDATE_RULE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DELETE_RULE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_ENFORCED"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_VALIDATED"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CHECK_TIME"));
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

	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_HAN_TABLES           ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_HAN_TABLES : " + result);
		logger.debug(">>> " + strSQL.toString());

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_HAN_TABLE_COLUMNS  ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");	
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_HAN_TABLE_COLUMNS : " + result);
		
//		strSQL.setLength(0);
//		strSQL.append("\n  DELETE FROM WAE_HAN_REFERENCED_CONSTRAINT  ");
//		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");	
//		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_HAN_REFERENCED_CONSTRAINT : " + result);
		
		return result;
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
		
//		cnt = updateTblCnt();
		logger.debug("updateTblCnt " + dbName + cnt + " OK!!");
		result = true;

		return result;
	}
	
	private int insertwattbl() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL                       ");
		sb.append("\n (                                             ");
		sb.append("\n        DB_SCH_ID                             ");
		sb.append("\n      , DBC_TBL_NM                            ");
		sb.append("\n      , DBC_TBL_KOR_NM                        ");
		sb.append("\n      , VERS                              ");
		sb.append("\n      , REG_TYP                            ");
		sb.append("\n      , REG_DTM                               ");
		sb.append("\n      , UPD_DTM                               ");
		sb.append("\n      , DESCN                                 ");
		sb.append("\n      , DB_CONN_TRG_ID                        ");
		sb.append("\n      , DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ROW_EACNT                             ");
		sb.append("\n      , TBL_SIZE                              ");
		sb.append("\n      , DATA_SIZE                             ");
		sb.append("\n      , IDX_SIZE                              ");
		sb.append("\n      , NUSE_SIZE                             ");
//		sb.append("\n      , TBL_ANA_DTM                           ");
//		sb.append("\n      , CRT_DTM                           ");
//		sb.append("\n      , TBL_CHG_DTM                           ");
//		sb.append("\n      , TBL_CLLT_DCD                          ");
		sb.append("\n )                                             ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.TABLE_NAME          AS DBC_TBL_NM                            ");
		sb.append("\n      , A.COMMENTS            AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS OBJ_VERS                              ");
		sb.append("\n      , NULL                  AS REG_TYP_CD                            ");
		sb.append("\n      , SYSDATE           AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , A.ROW_EACNT           AS ROW_EACNT                             ");
		sb.append("\n      , ''                    AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''                    AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
//		sb.append("\n      , NULL                  AS ANA_DTM                               ");
//		sb.append("\n      , A.CREATE_TIME         AS CRT_DTM                               ");
//		sb.append("\n      , NULL                  AS CHG_DTM                               ");
//		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_HAN_TABLES A                                               ");
		sb.append("\n        INNER JOIN (                                                   ");
		sb.append(                      getdbconnsql()                   );
		sb.append("\n        ) S                                         ");
		sb.append("\n           ON A.DB_NM  = S.DB_CONN_TRG_PNM                                ");
		sb.append("\n          AND A.SCH_NM = S.DB_SCH_PNM                                     ");	
		sb.append("\n          AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                         ");	
		sb.append("\n  WHERE A.DB_CONN_TRG_ID = '"+ dbms_id +"'                                          ");
		//수집데이터 제외 테이블
//		sb.append("\n   AND A.SCH_NM || '.' || A.TABLE_NAME NOT IN ( 	");
//		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
//		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
//		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
//		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
//		sb.append("\n   	)													");
		
		logger.debug("insert wat tbl >>> ");
		logger.debug("" + sb.toString());

		return setExecuteUpdate_Org(sb.toString());
	}

	private int insertwatcol() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL   ");
		sb.append("\n (                         ");
		sb.append("\n        DB_SCH_ID                                             ");
		sb.append("\n      , DBC_TBL_NM                                            ");
		sb.append("\n      , DBC_COL_NM                                            ");
		sb.append("\n      , DBC_COL_KOR_NM                                        ");
		sb.append("\n      , VERS                                              ");
		sb.append("\n      , REG_TYP                                            ");
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
		sb.append("\n )                         "); 
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID                     AS DB_SCH_ID                                  ");
		sb.append("\n      , A.TABLE_NAME                    AS DBC_TBL_NM                                 ");
		sb.append("\n      , A.COLUMN_NAME                   AS DBC_COL_NM                                 ");
		sb.append("\n      , A.COMMENTS                      AS DBC_COL_KOR_NM                             ");
		sb.append("\n      , '1'                             AS OBJ_VERS                                   ");
		sb.append("\n      , NULL                            AS REG_TYP_CD                                 ");
		sb.append("\n      , SYSDATE                     AS REG_DTM                                    ");
		sb.append("\n      , NULL                            AS UPD_DTM                                    ");
		sb.append("\n      , A.DATA_TYPE_NAME                AS DATA_TYPE                                  ");
		sb.append("\n      , A.LENGTH                        AS DATA_LEN                                   ");
		sb.append("\n      , 0                               AS DATA_PNUM                                  ");
		sb.append("\n      , A.SCALE                         AS DATA_PNT                                   ");
		sb.append("\n      , CASE WHEN SUBSTR(A.IS_NULLABLE,1,1) = 'F' THEN 'N' ELSE 'Y' END AS NULL_YN    ");
		sb.append("\n      , 0                               AS DEFLT_LEN                                  ");
		sb.append("\n      , A.DEFAULT_VALUE                 AS DEFLT_VAL                                  ");
		sb.append("\n      , (CASE WHEN SUBSTR(A.INDEX_TYPE,1,1) = 'F' THEN 'Y' ELSE 'N' END)  AS PK_YN    ");
		sb.append("\n      , ''																  AS FK_YN    ");
		sb.append("\n      , A.POSITION                      AS ORD                                        ");
		sb.append("\n      , (CASE WHEN SUBSTR(A.INDEX_TYPE,1,1) = 'F' THEN A.POSITION END)    AS PK_ORD   ");
		sb.append("\n      , ''                              AS COL_DESCN                                  ");
		sb.append("\n   FROM WAE_HAN_TABLE_COLUMNS A                ");
		sb.append("\n        INNER JOIN (                           ");
		sb.append(                    getdbconnsql()                 );
		sb.append("\n        ) S                                    ");
		sb.append("\n          ON A.DB_NM  = S.DB_CONN_TRG_PNM      ");
		sb.append("\n         AND A.SCH_NM = S.DB_SCH_PNM           ");	
		sb.append("\n         AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID     ");
		sb.append("\n  WHERE A.DB_CONN_TRG_ID = '" + dbms_id + "'             ");
		//수집데이터 제외 테이블
//		sb.append("\n   AND A.SCH_NM || '.' || A.TABLE_NAME NOT IN ( 	");
//		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
//		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
//		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
//		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
//		sb.append("\n   	)													");

		return setExecuteUpdate_Org(sb.toString());
	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n             INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                ON S.DB_CONN_TRG_ID = T.DB_CONN_TRG_ID ");
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
				
		logger.debug("tblCnt >>> " + strSQL.toString());
		
		return setExecuteUpdate_Org(strSQL.toString());
	}
}
