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
package kr.wise.executor.exceute.schema.dbms;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorOracle.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorAltibaseV6 {

	private static final Logger logger = Logger.getLogger(CollectorAltibaseV6.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;

	private String dbId = null;
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;

	private String dbType = null;

	private int execCnt = 10000;


	public CollectorAltibaseV6() {

	}

	/** insomnia */
	public CollectorAltibaseV6(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;
	}

	/**  insomnia
	 * @return */
	public boolean doProcess() throws Exception {
		boolean result = false;

		con_org.setAutoCommit(false);

		String sp = "   ";
		int dbCnt = 0;
		
		for (TargetDbmsDM targetDb : targetDblist) {
//			this.dbName = targetDb.getDbms_enm();
			this.dbId = targetDb.getDbms_id();
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
				
				//DBA_TABLESPACES --------------------------------------------------------
				cnt = insertDBA_TABLESPACES();
				logger.debug(sp + (++p) + ". insertDBA_TABLESPACES " + cnt + " OK!!");
				
				con_org.commit();
			}

			dbCnt++;

			//DBA_USERS --------------------------------------------------------
			cnt = insertDBA_USERS();
			logger.debug(sp + (++p) + ". insertDBA_USERS " + cnt + " OK!!");
			
			//DBA_TABLES -------------------------------------------------------
			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");

			//DBA_TAB_COLUMNS ---------------------------------------------------
			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");

			//DBA_CONSTRAINTS ---------------------------------------------------
			cnt = insertDBA_CONSTRAINTS();
			logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

			//DBA_CONS_COLUMNS ---------------------------------------------------
			cnt = insertDBA_CONS_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_CONS_COLUMNS " + cnt + " OK!!");

			//DBA_INDEXES ---------------------------------------------------
			cnt = insertDBA_INDEXES();
			logger.debug(sp + (++p) + ". insertDBA_INDEXES " + cnt + " OK!!");
			
			//DBA_IND_COLUMNS ---------------------------------------------------
			cnt = insertDBA_IND_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_IND_COLUMNS " + cnt + " OK!!");
			
			
			//DBA_VIEWS ---------------------------------------------------
			cnt = insertDBA_VIEWS();
			logger.debug(sp + (++p) + ". insertDBA_VIEWS " + cnt + " OK!!");
			
			//DBA_VIEW_PARSE 사용자 정의 VIEW TEXT ---------------------------------------------------
			if(cnt > 0){
				cnt = insertDBA_VIEW_PARSE();
				logger.debug(sp + (++p) + ". insertDBA_VIEW_PARSE " + cnt + " OK!!");
			}
			
			
			//DBA_SEQ ---------------------------------------------------
			cnt = insertDBA_SEQ();
			logger.debug(sp + (++p) + ". insertDBA_SEQ " + cnt + " OK!!");

			//DBA_PROC ---------------------------------------------------
			cnt = insertDBA_PROC();
			logger.debug(sp + (++p) + ". insertDBA_PROC " + cnt + " OK!!");

			if(cnt > 0){
				//DBA_PROC_PARA ---------------------------------------------------
				cnt = insertDBA_PROC_PARA();
				logger.debug(sp + (++p) + ". insertDBA_PROC_PARA " + cnt + " OK!!");
				
				//DBA_PROC_PARSE ---------------------------------------------------
				cnt = insertDBA_PROC_PARSE();
				logger.debug(sp + (++p) + ". insertDBA_PROC_PARSE " + cnt + " OK!!");
			}
			
			//DBA_USAGE ---------------------------------------------------
			//테이블, 인덱스 사용량 BITE
			cnt = insertDBA_USAGE();
			logger.debug(sp + (++p) + ". insertDBA_USAGE " + cnt + " OK!!");
			
			

			/* 파티션 테이블 
			//DBA_PART_TABLES ---------------------------------------------------
			cnt = insertDBA_PART_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_PART_TABLES " + cnt + " OK!!");

			//DBA_PART_TABLES ---------------------------------------------------
			cnt = insertDBA_TABLE_PARTS();
			logger.debug(sp + (++p) + ". insertDBA_TABLE_PARTS " + cnt + " OK!!");
			
			//DBA_PART_KEY_COLUMNS ---------------------------------------------------
			cnt = insertDBA_PART_KEY_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_PART_KEY_COLUMNS " + cnt + " OK!!");

			//DBA_PART_INDEXES ---------------------------------------------------
			cnt = insertDBA_PART_INDEXES();
			logger.debug(sp + (++p) + ". insertDBA_PART_INDEXES " + cnt + " OK!!");

			//DBA_INDEX_PARTS ---------------------------------------------------
			cnt = insertDBA_INDEX_PARTS();
			logger.debug(sp + (++p) + ". insertDBA_INDEX_PARTS " + cnt + " OK!!");
			 */
			
			con_org.commit();

		}

		result = true;

		return result;

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
		sb.append("\nINSERT INTO WAE_ALTI_DATABASES (");
		sb.append("\n   DB_NM                                            ");
		sb.append("\n   ,DB_CONN_TRG_ID                                   ");
		sb.append("\n   ,STR_DTM                                          ");
		sb.append("\n  )                              ");
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
	
	/**
	 * DBC DBA_USERS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_USERS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_ID            ");
  		sb.append("\n          , A.USER_NAME          ");
  		sb.append("\n          , A.PASSWORD           ");
  		sb.append("\n          , B.NAME AS DEFAULT_TBS_NM ");
  		sb.append("\n          , C.NAME AS TEMP_TBS_NM ");
  		sb.append("\n           , TO_CHAR(A.CREATED,'YYYY-MM-DD HH24:MI:SS') AS CREATED            ");
  		sb.append("\n           , TO_CHAR(A.LAST_DDL_TIME,'YYYY-MM-DD HH24:MI:SS') AS LAST_DDL_TIME      ");
  		sb.append("\n           ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A  ");
  		sb.append("\n INNER JOIN SYSTEM_.V$TABLESPACES B ");
  		sb.append("\n ON A.DEFAULT_TBS_ID = B.ID ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.V$TABLESPACES C ");
  		sb.append("\n ON A.TEMP_TBS_ID= C.ID ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_USERS ");
  		sb.append("\n ( USER_ID                  ");
  		sb.append("\n , USER_NAME                ");
  		sb.append("\n , PASSWORD                 ");
  		sb.append("\n , DEFAULT_TBS_NM           ");
  		sb.append("\n , TEMP_TBS_NM              ");
  		sb.append("\n , CREATED                  ");
  		sb.append("\n , LAST_DDL_TIME            ");
  		sb.append("\n , DB_NM              ");
  		sb.append("\n ) VALUES(                  ");
  		sb.append("\n ?, ?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'), TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'), ? ");
  		sb.append("\n )                          ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PASSWORD"));
  				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_TBS_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("TEMP_TBS_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("CREATED"));
  				pst_org.setString(rsGetCnt++, rs.getString("LAST_DDL_TIME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
	}
	
	
	/**
	 * DBC DBA_TABLESPACES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLESPACES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.ID AS TBS_ID           ");
  		sb.append("\n      , A.NAME AS TBS_NAME       ");
  		sb.append("\n      , A.DATAFILE_COUNT         ");
  		sb.append("\n      , A.TOTAL_PAGE_COUNT       ");
  		sb.append("\n      , A.EXTENT_PAGE_COUNT    ");  
  		sb.append("\n      , A.ALLOCATED_PAGE_COUNT   ");
  		sb.append("\n      , A.PAGE_SIZE              ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.V$TABLESPACES A    ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_TABLESPACES ");
  		sb.append("\n ( TBS_ID                 ");
  		sb.append("\n , TBS_NAME               ");
  		sb.append("\n , DATAFILE_COUNT         ");
  		sb.append("\n , TOTAL_PAGE_COUNT       ");
  		sb.append("\n , EXTENT_PAGE_COUNT      ");
  		sb.append("\n , ALLOCATED_PAGE_COUNT   ");
  		sb.append("\n , PAGE_SIZE              ");
  		sb.append("\n , DB_NM            ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?        ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("TBS_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("TBS_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATAFILE_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("TOTAL_PAGE_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("EXTENT_PAGE_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("ALLOCATED_PAGE_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("PAGE_SIZE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;

	}
	
	
	/**
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
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
  		//6.3.1 미존재 컬럼
//  		sb.append("\n      , A.INSERT_HIGH_LIMIT   ");
//  		sb.append("\n      , A.INSERT_LOW_LIMIT    ");
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
  		sb.append("\n             ON A.USER_ID = B.USER_ID                ");
  		sb.append("\n            LEFT OUTER JOIN SYSTEM_.V$TABLESPACES C ");
  		sb.append("\n             ON A.TBS_ID = C.ID                      ");
  		sb.append("\n            LEFT OUTER JOIN SYSTEM_.SYS_COMMENTS_ D ");
  		sb.append("\n             ON A.TABLE_NAME = D.TABLE_NAME                    ");
  		sb.append("\n            AND B.USER_NAME = D.USER_NAME                    ");
  		//컬럼명이 존재 하지 않는 것이 테이블 COMMENTS 이다.
  		sb.append("\n            AND D.COLUMN_NAME IS NULL                  ");
  		//ROW CNT 통계정보 테이블
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
  		sb.append("\n ) VALUES (                      ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,            ");
  		sb.append("\n ?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_OID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
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
  		
  		return cnt;
	}
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
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
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,            ");
  		sb.append("\n ?,?,?,?,?,?,? ");
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
  		return cnt;
	}

	
	/**
	 * DBC DBA_CONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONSTRAINTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
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
  		sb.append("\n AND C.CONSTRAINT_TYPE NOT IN ('1') "); //_SYS 로시작하는 제약조건 제외
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
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?, ");
  		sb.append("\n ?");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_TYPE_CODE"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_TYPE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_CNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("REF_TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("REF_INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
	}


	/**
	 * DBC DBA_CONS_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONS_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                               ");
  		sb.append("\n      , B.TABLE_NAME                              ");
  		sb.append("\n      , C.CONSTRAINT_NAME                         ");
  		sb.append("\n      , E.COLUMN_NAME AS CONST_COL_NAME           ");
  		//제약조건 컬럼 오더 0부터 시작
  		sb.append("\n      , (D.CONSTRAINT_COL_ORDER+1) AS CONST_COL_ORDER ");
//  		sb.append("\n      , D.CONSTRAINT_COL_ORDER AS CONST_COL_ORDER ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                        ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                 ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                         ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_CONSTRAINTS_ C            ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                       ");
  		sb.append("\n AND C.CONSTRAINT_TYPE NOT IN ('1') "); //_SYS 로시작하는 제약조건 제외
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
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
	}
	
	
	/**
	 * DBC DBA_INDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_INDEXES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT C.INDEX_ID                                                                 ");
  		sb.append("\n      , A.USER_NAME                                                                ");
  		sb.append("\n      , B.TABLE_NAME                                                               ");
  		sb.append("\n      , C.INDEX_NAME                                                               ");
  		sb.append("\n      , C.COLUMN_CNT                                                               ");
  		sb.append("\n      , CASE WHEN C.INDEX_TYPE = '1' THEN 'B-TREE' ELSE 'R-TREE' END AS INDEX_TYPE ");
  		sb.append("\n      , CASE WHEN C.IS_UNIQUE = 'T' THEN 'Y' ELSE 'N' END AS IS_UNIQUE             ");
  		sb.append("\n      , CASE WHEN C.IS_RANGE = 'T' THEN 'Y' ELSE 'N' END AS IS_RANGE               ");
  		sb.append("\n      , CASE WHEN C.IS_PARTITIONED = 'T' THEN 'Y' ELSE 'N' END AS IS_PARTITIONED   ");
  		sb.append("\n      , D.NAME AS TBS_NAME                                                         ");
  		sb.append("\n      , TO_CHAR(C.CREATED,'YYYY-MM-DD HH24:MI:SS') AS CREATED                      ");
  		sb.append("\n      , TO_CHAR(C.LAST_DDL_TIME,'YYYY-MM-DD HH24:MI:SS') AS LAST_DDL_TIME          ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                                                         ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                                                  ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                                                          ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDICES_ C                                                 ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                                                        ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.V$TABLESPACES D                                                   ");
  		sb.append("\n ON C.TBS_ID = D.ID                                                                ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_INDEXES     ");
  		sb.append("\n ( INDEX_ID           ");
  		sb.append("\n , USER_NAME          ");
  		sb.append("\n , TABLE_NAME         ");
  		sb.append("\n , INDEX_NAME         ");
  		sb.append("\n , COLUMN_CNT         ");
  		sb.append("\n , INDEX_TYPE         ");
  		sb.append("\n , IS_UNIQUE          ");
  		sb.append("\n , IS_RANGE           ");
  		sb.append("\n , IS_PARTITIONED     ");
  		sb.append("\n , TBS_NAME           ");
  		sb.append("\n , CREATED            ");
  		sb.append("\n , LAST_DDL_TIME      ");
  		sb.append("\n , DB_NM        ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,            ");
  		sb.append("\n TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_CNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_UNIQUE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_RANGE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_PARTITIONED"));
  				pst_org.setString(rsGetCnt++, rs.getString("TBS_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CREATED"));
  				pst_org.setString(rsGetCnt++, rs.getString("LAST_DDL_TIME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
	}

	/**
	 * DBC DBA_IND_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_IND_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                       ");
  		sb.append("\n      , B.TABLE_NAME                      ");
  		sb.append("\n      , C.INDEX_NAME                      ");
  		sb.append("\n      , E.COLUMN_NAME                     ");
  		sb.append("\n      , D.INDEX_COL_ORDER+1 AS INDEX_COL_ORDER ");
  		sb.append("\n      , CASE WHEN D.SORT_ORDER = 'A' THEN 'ASC' ELSE 'DESC' END AS SORT_ORDER ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B         ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                 ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDICES_ C        ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID               ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDEX_COLUMNS_ D  ");
  		sb.append("\n ON C.INDEX_ID = D.INDEX_ID               ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_COLUMNS_ E        ");
  		sb.append("\n ON B.TABLE_ID = E.TABLE_ID               ");
  		sb.append("\n AND D.COLUMN_ID = E.COLUMN_ID            ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_IDX_COLUMNS ");
  		sb.append("\n ( USER_NAME       ");
  		sb.append("\n , TABLE_NAME      ");
  		sb.append("\n , INDEX_NAME      ");
  		sb.append("\n , COLUMN_NAME     ");
  		sb.append("\n , INDEX_COL_ORDER ");
  		sb.append("\n , SORT_ORDER      ");
  		sb.append("\n , DB_NM     ");
  		sb.append("\n ) VALUES (        ");
  		sb.append("\n ?,?,?,?,?,?,?   ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_COL_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("SORT_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
	}
	
	
	/**
	 * DBC DBA_VIEWS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_VIEWS() throws SQLException, Exception
	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT C.VIEW_ID                                                   ");
  		sb.append("\n      , A.USER_NAME                                                 ");
  		sb.append("\n      , B.TABLE_NAME AS VIEW_NAME                                   ");
  		sb.append("\n      , CASE C.STATUS WHEN 0 THEN 'VALID' ELSE 'INVALID' END STATUS ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                                          ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                                   ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                                           ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_VIEWS_ C                                    ");
  		sb.append("\n ON B.TABLE_ID = C.VIEW_ID                                          ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_VIEWS ");
  		sb.append("\n ( VIEW_ID     ");
  		sb.append("\n , USER_NAME   ");
  		sb.append("\n , VIEW_NAME   ");
  		sb.append("\n , STATUS      ");
  		sb.append("\n , DB_NM ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?  ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		return cnt;
	}
	
	
	private int insertDBA_VIEW_PARSE() throws SQLException, IOException
  	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
//  		sb.append("\n SELECT A.USER_NAME                   ");
//  		sb.append("\n      , B.TABLE_NAME AS VIEW_NAME     ");
//  		sb.append("\n      , C.SEQ_NO                      ");
//  		sb.append("\n      , C.PARSE                       ");
//  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A            ");
//  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B     ");
//  		sb.append("\n ON A.USER_ID = B.USER_ID             ");
//  		sb.append("\n INNER JOIN SYSTEM_.SYS_VIEW_PARSE_ C ");
//  		sb.append("\n ON B.TABLE_ID = C.VIEW_ID            ");
//  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
//  		
//  		getResultSet(sb.toString());
//  		
//  		sb = new StringBuffer();
//  		
//  		sb.append("\n INSERT INTO WAE_ALTI_VIEW_PARSE ");
//  		sb.append("\n ( USER_NAME   ");
//  		sb.append("\n , VIEW_NAME   ");
//  		sb.append("\n , SEQ_NO      ");
//  		sb.append("\n , PARSE       ");
//  		sb.append("\n , DB_NM ");
//  		sb.append("\n ) VALUES ( ");
//  		sb.append("\n ?,?,?,?,?  ");
//  		sb.append("\n ) ");
//  		
//  		if( rs != null ) {
//  			pst_org = con_org.prepareStatement(sb.toString());
//  			pst_org.clearParameters();
//  			
//  			int rsCnt = 1;
//  			int rsGetCnt = 1;
//  			
//  			while(rs.next()) {
//  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
//  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_NAME"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SEQ_NO"));
//  				pst_org.setString(rsGetCnt++, rs.getString("PARSE"));
//  				pst_org.setString(rsGetCnt++, dbName);
//  				
//  				getSaveResult(rsCnt);
//  				
//  				if(rsCnt == execCnt)
//  				{
//  					executeBatch(true);
//  					rsCnt = 0;
//  				}
//  				
//  				rsCnt++;
//  				rsGetCnt = 1;
//  			}
//  			executeBatch();
//  		}
//  		
//  		sb = new StringBuffer();
  		
  		sb.append("\n SELECT A.USER_NAME                   ");
  		sb.append("\n      , B.TABLE_NAME AS VIEW_NAME     ");
  		sb.append("\n      , C.SEQ_NO                      ");
  		sb.append("\n      , C.PARSE                       ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A            ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B     ");
  		sb.append("\n ON A.USER_ID = B.USER_ID             ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_VIEW_PARSE_ C ");
  		sb.append("\n ON B.TABLE_ID = C.VIEW_ID            ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n ORDER BY USER_NAME, VIEW_NAME, SEQ_NO");
  		
  		getResultSet(sb.toString());
   		
  		sb = new StringBuffer();
  		
  		sb.append("\n BEGIN ");
  		sb.append("\n INSERT INTO WAE_ALTI_VIEW_PARSE_CLOB ");
  		sb.append("\n ( USER_NAME     ");
  		sb.append("\n , VIEW_NAME     ");
  		sb.append("\n , PARSE         ");
  		sb.append("\n , DB_NM   ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?     ");
  		sb.append("\n ); ");
  		sb.append("\n END; ");
  		
  		if( rs != null ) {
  			int rsGetCnt = 1;
  			String beforeSeqNo = "";
  			String afterSeqNo = "";
  			String varUSER_NAME = "";
  			String varVIEW_NAME = "";
  			String parseStr = "";
  			while(rs.next()) {
  				afterSeqNo = rs.getString("SEQ_NO");
  				if(!beforeSeqNo.equals("") && afterSeqNo.equals("1")) {

	  	  			pst_org = con_org.prepareStatement(sb.toString());
	  	  			pst_org.clearParameters();
	  				pst_org.setString(rsGetCnt++, varUSER_NAME);
	  				pst_org.setString(rsGetCnt++, varVIEW_NAME);
	  				pst_org.setString(rsGetCnt++, parseStr);
	  				pst_org.setString(rsGetCnt++, dbName);
	
	  				rsGetCnt = 1;
	  				
	  				pst_org.executeUpdate();
	  				
		  	        cnt++;

	  				varUSER_NAME = rs.getString("USER_NAME");
	  				varVIEW_NAME = rs.getString("VIEW_NAME");
	  	  			parseStr = rs.getString("PARSE");
	  	  			beforeSeqNo = afterSeqNo;
  				} else {
	  				varUSER_NAME = rs.getString("USER_NAME");
	  				varVIEW_NAME = rs.getString("VIEW_NAME");
	  	  			parseStr += rs.getString("PARSE");
	  	  			beforeSeqNo = afterSeqNo;  					
  				}
  			}
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
			pst_org.setString(rsGetCnt++, varUSER_NAME);
			pst_org.setString(rsGetCnt++, varVIEW_NAME);
			pst_org.setString(rsGetCnt++, parseStr);
			pst_org.setString(rsGetCnt++, dbName);
			
			pst_org.executeUpdate();
			
  	        cnt++;
  		}

  		return cnt;
  	}
	
	
	private int insertDBA_SEQ() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT B.TABLE_ID AS SEQ_ID                                         ");
  		sb.append("\n      , A.USER_NAME                                                  ");
  		sb.append("\n      , B.TABLE_NAME AS SEQ_NAME                                     ");
  		sb.append("\n      , C.CURRENT_SEQ                                                ");
  		sb.append("\n      , C.START_SEQ                                                  ");
  		sb.append("\n      , C.INCREMENT_SEQ                                              ");
  		sb.append("\n      , C.CACHE_SIZE                                                 ");
  		sb.append("\n      , C.MAX_SEQ                                                    ");
  		sb.append("\n      , C.MIN_SEQ                                                    ");
  		sb.append("\n      , CASE C.IS_CYCLE WHEN 'YES' THEN 'Y' ELSE 'N' END AS IS_CYCLE ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                                           ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                                    ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                                            ");
  		sb.append("\n INNER JOIN SYSTEM_.V$SEQ C                                                  ");
  		sb.append("\n ON B.TABLE_OID = C.SEQ_OID                                          ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_SEQ ");
  		sb.append("\n ( SEQ_ID      ");
  		sb.append("\n , USER_NAME   ");
  		sb.append("\n , SEQ_NAME    ");
  		sb.append("\n , CUR_SEQ     ");
  		sb.append("\n , STA_SEQ     ");
  		sb.append("\n , INC_SEQ     ");
  		sb.append("\n , CACHE_SIZE  ");
  		sb.append("\n , MAX_SEQ     ");
  		sb.append("\n , MIN_SEQ     ");
  		sb.append("\n , IS_CYCLE    ");
  		sb.append("\n , DB_NM   ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?,?            ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("SEQ_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("SEQ_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CURRENT_SEQ"));
  				pst_org.setString(rsGetCnt++, rs.getString("START_SEQ"));
  				pst_org.setString(rsGetCnt++, rs.getString("INCREMENT_SEQ"));
  				pst_org.setString(rsGetCnt++, rs.getString("CACHE_SIZE"));
  				pst_org.setString(rsGetCnt++, rs.getString("MAX_SEQ"));
  				pst_org.setString(rsGetCnt++, rs.getString("MIN_SEQ"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_CYCLE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
  	}
	
	private int insertDBA_PROC() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                    ");
  		sb.append("\n      , B.PROC_NAME                    ");
  		sb.append("\n      , CASE B.OBJECT_TYPE             ");
  		sb.append("\n             WHEN 0 THEN 'PROC'        ");
  		sb.append("\n             WHEN 1 THEN 'FUNC'        ");
  		sb.append("\n        ELSE 'TYPE' END AS OBJECT_TYPE ");
  		sb.append("\n      , CASE B.STATUS                  ");
  		sb.append("\n             WHEN 0 THEN 'VALID'       ");
  		sb.append("\n             ELSE 'INVALID'            ");
  		sb.append("\n        END AS STATUS                  ");
  		sb.append("\n      , NVL(D.TYPE_NAME,(CASE B.RETURN_DATA_TYPE WHEN 30001 THEN 'FILE_TYPE' WHEN 16 THEN 'BOOLEAN' ELSE '' END)) AS DATA_TYPE ");
  		sb.append("\n      , B.RETURN_PRECISION AS DATA_LENGTH ");
  		sb.append("\n      , B.RETURN_SCALE AS DATA_SCALE   ");
  		sb.append("\n      , B.PARA_NUM                     ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A             ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROCEDURES_ B  ");
  		sb.append("\n ON A.USER_ID = B.USER_ID              ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.V$DATATYPE D  ");
  		sb.append("\n ON B.RETURN_DATA_TYPE = D.DATA_TYPE   ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_PROC ");
  		sb.append("\n ( USER_NAME      ");
  		sb.append("\n , PROC_NAME      ");
  		sb.append("\n , OBJECT_TYPE    ");
  		sb.append("\n , STATUS         ");
  		sb.append("\n , PARA_NUM       ");
  		sb.append("\n , DATA_TYPE      ");
  		sb.append("\n , DATA_LENGTH    ");
  		sb.append("\n , DATA_SCALE     ");
  		sb.append("\n , DB_NM      ");
  		sb.append("\n ) VALUES (       ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?    ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARA_NUM"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_SCALE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
  	}
	
	
	private int insertDBA_PROC_PARA() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                       ");
  		sb.append("\n      , B.PROC_NAME                       ");
  		sb.append("\n      , C.PARA_NAME                       ");
  		sb.append("\n      , C.PARA_ORDER                      ");
  		sb.append("\n      , CASE C.INOUT_TYPE                 ");
  		sb.append("\n             WHEN 0 THEN 'IN'             ");
  		sb.append("\n             WHEN 1 THEN 'OUT'            ");
  		sb.append("\n             WHEN 2 THEN 'INOUT'          ");
  		sb.append("\n        ELSE '' END AS INOUT_TYPE         ");
  		sb.append("\n      , NVL(D.TYPE_NAME,                  ");
  		sb.append("\n            (CASE C.DATA_TYPE             ");
  		sb.append("\n             WHEN 30001 THEN 'FILE_TYPE'  ");
  		sb.append("\n             WHEN 16 THEN 'BOOLEAN'       ");
  		sb.append("\n             ELSE '' END)) AS DATA_TYPE   ");
  		sb.append("\n      , C.PRECISION AS DATA_LENGTH        ");
  		sb.append("\n      , C.SCALE AS DATA_SCALE             ");
  		sb.append("\n      , C.DEFAULT_VAL                     ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROCEDURES_ B     ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                 ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROC_PARAS_ C     ");
  		sb.append("\n ON B.PROC_OID = C.PROC_OID               ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.V$DATATYPE D     ");
  		sb.append("\n ON C.DATA_TYPE = D.DATA_TYPE             ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_PROC_PARA ");
  		sb.append("\n ( USER_NAME          ");
  		sb.append("\n , PROC_NAME          ");
  		sb.append("\n , PARA_NAME          ");
  		sb.append("\n , PARA_ORDER         ");
  		sb.append("\n , INOUT_TYPE         ");
  		sb.append("\n , DATA_TYPE          ");
  		sb.append("\n , DATA_LENGTH        ");
  		sb.append("\n , DATA_SCALE         ");
  		sb.append("\n , DEFAULT_VAL        ");
  		sb.append("\n , DB_NM        ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,?            ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARA_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARA_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("INOUT_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_SCALE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
  	}
	
	private int insertDBA_PROC_PARSE() throws SQLException, IOException
  	{
		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
//  		sb.append("\n SELECT A.USER_NAME                   ");
//  		sb.append("\n      , B.PROC_NAME                   ");
//  		sb.append("\n      , C.SEQ_NO                      ");
//  		sb.append("\n      , C.PARSE                       ");
////  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
//  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A            ");
//  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROCEDURES_ B ");
//  		sb.append("\n ON A.USER_ID = B.USER_ID             ");
//  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROC_PARSE_ C ");
//  		sb.append("\n ON B.PROC_OID = C.PROC_OID           ");
//  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
//  		
//  		getResultSet(sb.toString());
//  		
//  		sb = new StringBuffer();
//  		
//  		sb.append("\n INSERT INTO WAE_ALTI_PROC_PARSE ");
//  		sb.append("\n ( USER_NAME     ");
//  		sb.append("\n , PROC_NAME     ");
//  		sb.append("\n , SEQ_NO        ");
//  		sb.append("\n , PARSE         ");
//  		sb.append("\n , DB_NM   ");
//  		sb.append("\n ) VALUES ( ");
//  		sb.append("\n ?,?,?,?,?     ");
//  		sb.append("\n ) ");
//  		
//  		if( rs != null ) {
//  			pst_org = con_org.prepareStatement(sb.toString());
//  			pst_org.clearParameters();
//  			
//  			int rsCnt = 1;
//  			int rsGetCnt = 1;
//  			
//  			while(rs.next()) {
//  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
//  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SEQ_NO"));
//  				pst_org.setString(rsGetCnt++, rs.getString("PARSE"));
//  				pst_org.setString(rsGetCnt++, dbName);
//  				
//  				getSaveResult(rsCnt);
//  				
//  				if(rsCnt == execCnt)
//  				{
//  					executeBatch(true);
//  					rsCnt = 0;
//  				}
//  				
//  				rsCnt++;
//  				rsGetCnt = 1;
//  			}
//  			executeBatch();
//  		}
//
//  		sb = new StringBuffer();
  		
  		sb.append("\n SELECT A.USER_NAME                   ");
  		sb.append("\n      , B.PROC_NAME                   ");
  		sb.append("\n      , C.SEQ_NO                      ");
  		sb.append("\n      , C.PARSE                       ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A            ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROCEDURES_ B ");
  		sb.append("\n ON A.USER_ID = B.USER_ID             ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PROC_PARSE_ C ");
  		sb.append("\n ON B.PROC_OID = C.PROC_OID           ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n ORDER BY USER_NAME, PROC_NAME, SEQ_NO");
  		
  		getResultSet(sb.toString());
   		
  		sb = new StringBuffer();
  		
  		sb.append("\n BEGIN ");
  		sb.append("\n INSERT INTO WAE_ALTI_PROC_PARSE_CLOB ");
  		sb.append("\n ( USER_NAME     ");
  		sb.append("\n , PROC_NAME     ");
  		sb.append("\n , PARSE         ");
  		sb.append("\n , DB_NM   ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?     ");
  		sb.append("\n ); ");
  		sb.append("\n END; ");
  		
  		if( rs != null ) {
  			int rsGetCnt = 1;
  			String beforeSeqNo = "";
  			String afterSeqNo = "";
  			String varUSER_NAME = "";
  			String varPROC_NAME = "";
  			String parseStr = "";
  			while(rs.next()) {
  				afterSeqNo = rs.getString("SEQ_NO");
  				
  				if(!beforeSeqNo.equals("") && afterSeqNo.equals("1")) {

	  	  			pst_org = con_org.prepareStatement(sb.toString());
	  	  			pst_org.clearParameters();
	  				pst_org.setString(rsGetCnt++, varUSER_NAME);
	  				pst_org.setString(rsGetCnt++, varPROC_NAME);
	  				pst_org.setString(rsGetCnt++, parseStr);
	  				pst_org.setString(rsGetCnt++, dbName);
	
	  				rsGetCnt = 1;
	  				
	  				pst_org.executeUpdate();
	  				
	  	            cnt++;

	  				varUSER_NAME = rs.getString("USER_NAME");
	  				varPROC_NAME = rs.getString("PROC_NAME");
	  	  			parseStr = rs.getString("PARSE");
	  	  			beforeSeqNo = afterSeqNo;
  				} else {
	  				varUSER_NAME = rs.getString("USER_NAME");
	  				varPROC_NAME = rs.getString("PROC_NAME");
	  	  			parseStr += rs.getString("PARSE");
	  	  			beforeSeqNo = afterSeqNo;  					
  				}
  			}
  			
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
			pst_org.setString(rsGetCnt++, varUSER_NAME);
			pst_org.setString(rsGetCnt++, varPROC_NAME);
			pst_org.setString(rsGetCnt++, parseStr);
			pst_org.setString(rsGetCnt++, dbName);

			pst_org.executeUpdate();
			
            cnt++;
  		}
  		return cnt;
  	}
	
	
	/**
	 * DBC DBA_SEGMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_USAGE() throws SQLException
	{
		
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
		strSQL.append("\n           ON A.USER_ID = B.USER_ID ");
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

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(strSQL.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AGEABLE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("META_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TARGET_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USED_SPACE"));

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
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AGEABLE_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("META_SPACE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TARGET_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USED_SPACE"));
				
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

		return cnt;
	}

	
	private int insertDBA_PART_TABLES() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                            ");
  		sb.append("\n      , B.TABLE_NAME                           ");
  		sb.append("\n      , CASE C.PARTITION_METHOD                ");
  		sb.append("\n             WHEN 0 THEN 'RANGE'               ");
  		sb.append("\n             WHEN 1 THEN 'HASH'                ");
  		sb.append("\n             WHEN 2 THEN 'LIST'                ");
  		sb.append("\n        ELSE '' END AS PART_METHOD             ");
  		sb.append("\n      , C.PARTITION_KEY_COUNT AS PART_KEY_CNT  ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                     ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B              ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                      ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_TABLES_ C         ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                    ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_PART_TABLES ");
  		sb.append("\n ( USER_NAME    ");
  		sb.append("\n , TABLE_NAME   ");
  		sb.append("\n , PART_METHOD  ");
  		sb.append("\n , PART_KEY_CNT ");
  		sb.append("\n , DB_NM  ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_METHOD"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_KEY_CNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		return cnt;
  	}
	
	private int insertDBA_TABLE_PARTS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT C.PARTITION_ID AS PART_ID             ");
  		sb.append("\n      , A.USER_NAME                           ");
  		sb.append("\n      , B.TABLE_NAME                          ");
  		sb.append("\n      , C.PARTITION_NAME AS PART_NAME         ");
  		sb.append("\n      , C.PARTITION_MIN_VALUE AS PART_MIN_VAL ");
  		sb.append("\n      , C.PARTITION_MAX_VALUE AS PART_MAX_VAL ");
  		sb.append("\n      , C.PARTITION_ORDER AS PART_ORDER       ");
  		sb.append("\n      , D.NAME AS PART_TBS_NAME               ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM ");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B             ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                     ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLE_PARTITIONS_ C   ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                   ");
  		sb.append("\n INNER JOIN SYSTEM_.V$TABLESPACES D           ");
  		sb.append("\n ON C.TBS_ID = D.ID                           ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_TABLE_PARTS ");
  		sb.append("\n ( PART_ID        ");
  		sb.append("\n , USER_NAME      ");
  		sb.append("\n , TABLE_NAME     ");
  		sb.append("\n , PART_NAME      ");
  		sb.append("\n , PART_MIN_VAL   ");
  		sb.append("\n , PART_MAX_VAL   ");
  		sb.append("\n , PART_ORDER     ");
  		sb.append("\n , PART_TBS_NAME  ");
  		sb.append("\n , DB_NM    ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("PART_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_MIN_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_MAX_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_TBS_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
  	}
	
	private int insertDBA_PART_KEY_COLUMNS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                            ");
  		sb.append("\n      , B.TABLE_NAME                           ");
  		sb.append("\n      , 'TBL' AS OBJECT_TYPE                   ");
  		sb.append("\n      , B.TABLE_NAME AS OBJECT_NAME            ");
  		sb.append("\n      , E.COLUMN_NAME AS PART_COL_NAME         ");
  		sb.append("\n      , D.PART_COL_ORDER                       ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                     ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B              ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                      ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_TABLES_ C         ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_COLUMNS_ E             ");
  		sb.append("\n ON B.TABLE_ID = E.TABLE_ID                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_KEY_COLUMNS_ D    ");
  		sb.append("\n ON  B.TABLE_ID = D.PARTITION_OBJ_ID           ");
  		sb.append("\n AND E.COLUMN_ID = D.COLUMN_ID                 ");
  		sb.append("\n AND D.OBJECT_TYPE = 0                         ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		sb.append("\n UNION                                         ");
  		sb.append("\n SELECT A.USER_NAME                            ");
  		sb.append("\n      , B.TABLE_NAME                           ");
  		sb.append("\n      , 'IDX' AS OBJECT_TYPE                   ");
  		sb.append("\n      , C.INDEX_NAME AS OBJECT_NAME            ");
  		sb.append("\n      , E.COLUMN_NAME AS PART_COL_NAME         ");
  		sb.append("\n      , F.PART_COL_ORDER                       ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                     ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B              ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                      ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDICES_ C             ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_INDICES_ D        ");
  		sb.append("\n ON C.INDEX_ID = D.INDEX_ID                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_COLUMNS_ E             ");
  		sb.append("\n ON B.TABLE_ID = E.TABLE_ID                    ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_KEY_COLUMNS_ F    ");
  		sb.append("\n ON C.INDEX_ID = F.PARTITION_OBJ_ID            ");
  		sb.append("\n AND E.COLUMN_ID = F.COLUMN_ID                 ");
  		sb.append("\n AND F.OBJECT_TYPE = 1                         ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_PART_KEY_COLUMNS ");
  		sb.append("\n ( USER_NAME      ");
  		sb.append("\n , TABLE_NAME     ");
  		sb.append("\n , OBJECT_TYPE    ");
  		sb.append("\n , OBJECT_NAME    ");
  		sb.append("\n , PART_COL_NAME  ");
  		sb.append("\n , PART_COL_ORDER ");
  		sb.append("\n , DB_NM    ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?  ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_COL_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_COL_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		return cnt;
  	}
	
	private int insertDBA_PART_INDEXES() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT A.USER_NAME                     ");
  		sb.append("\n      , B.TABLE_NAME                    ");
  		sb.append("\n      , C.INDEX_NAME                    ");
  		sb.append("\n      , CASE D.PARTITION_TYPE           ");
  		sb.append("\n             WHEN 0 THEN 'LOCAL'        ");
  		sb.append("\n        ELSE 'GLOBAL' END AS PART_TYPE  ");
  		sb.append("\n      , D.IS_LOCAL_UNIQUE               ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A              ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B       ");
  		sb.append("\n ON A.USER_ID = B.USER_ID               ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDICES_ C      ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID             ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_PART_INDICES_ D ");
  		sb.append("\n ON C.INDEX_ID = D.INDEX_ID             ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_PART_INDEXES ");
  		sb.append("\n ( USER_NAME       ");
  		sb.append("\n , TABLE_NAME      ");
  		sb.append("\n , INDEX_NAME      ");
  		sb.append("\n , PART_TYPE       ");
  		sb.append("\n , IS_LOCAL_UNIQUE ");
  		sb.append("\n , DB_NM     ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_LOCAL_UNIQUE"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
  		
  		return cnt;
  	}
	
	private int insertDBA_INDEX_PARTS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT D.INDEX_PARTITION_ID AS IDX_PART_ID       ");
  		sb.append("\n      , A.USER_NAME                               ");
  		sb.append("\n      , B.TABLE_NAME                              ");
  		sb.append("\n      , C.INDEX_NAME                              ");
  		sb.append("\n      , NVL(E.PARTITION_NAME,'') AS PART_NAME     ");
  		sb.append("\n      , D.INDEX_PARTITION_NAME AS IDX_PART_NAME   ");
  		sb.append("\n      , D.PARTITION_MIN_VALUE AS PART_MIN_VAL     ");
  		sb.append("\n      , D.PARTITION_MAX_VALUE AS PART_MAX_VAL     ");
  		sb.append("\n      , F.NAME AS PART_TBS_NAME                   ");
  		sb.append("\n      ,'").append(dbName).append("' AS DB_NM");
  		sb.append("\n FROM SYSTEM_.SYS_USERS_ A                        ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_TABLES_ B                 ");
  		sb.append("\n ON A.USER_ID = B.USER_ID                         ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDICES_ C                ");
  		sb.append("\n ON B.TABLE_ID = C.TABLE_ID                       ");
  		sb.append("\n INNER JOIN SYSTEM_.SYS_INDEX_PARTITIONS_ D       ");
  		sb.append("\n ON C.INDEX_ID = D.INDEX_ID                       ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.SYS_TABLE_PARTITIONS_ E  ");
  		sb.append("\n ON D.TABLE_PARTITION_ID = E.PARTITION_ID         ");
  		sb.append("\n LEFT OUTER JOIN SYSTEM_.V$TABLESPACES F          ");
  		sb.append("\n ON D.TBS_ID = F.ID                               ");
  		sb.append("\n WHERE UPPER(A.USER_NAME) = UPPER('").append(schemaName).append("') ");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_ALTI_INDEX_PARTS ");
  		sb.append("\n ( IDX_PART_ID     ");
  		sb.append("\n , USER_NAME       ");
  		sb.append("\n , TABLE_NAME      ");
  		sb.append("\n , INDEX_NAME      ");
  		sb.append("\n , PART_NAME       ");
  		sb.append("\n , IDX_PART_NAME   ");
  		sb.append("\n , PART_MIN_VAL    ");
  		sb.append("\n , PART_MAX_VAL    ");
  		sb.append("\n , PART_TBS_NAME   ");
  		sb.append("\n , DB_NM     ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n ?,?,?,?,?,?,?,?,?,? ");
  		sb.append("\n ) ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("IDX_PART_ID"));
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("IDX_PART_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_MIN_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_MAX_VAL"));
  				pst_org.setString(rsGetCnt++, rs.getString("PART_TBS_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				
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
		//logger.debug("query_tgt:\n"+query_tgt);
		pst_tgt = null;
		rs = null;

		pst_tgt = con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		pst_tgt.setFetchSize(3000);
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

	

	/**  insomnia */
	private int insertSchema() {
		int result = 0;


		return result;

	}

	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		// DB 정보
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_ALTI_DATABASES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_DATABASES : " + result);

		//테이블스페이스
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_TABLESPACES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_TABLESPACES : " + result);
		
		//스키마
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_USERS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_USERS : " + result);
		
		//테이블
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_TABLES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_TABLES : " + result);
		
		//제약조건
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_CONSTRAINTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_CONSTRAINTS : " + result);
		
		//제약조건컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_CONST_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_CONST_COLUMNS : " + result);
		
		//테이블 컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_COLUMNS : " + result);
		
		//인덱스
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_INDEXES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_INDEXES : " + result);
		
		//인덱스 컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_IDX_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_IDX_COLUMNS : " + result);
		
		
		//VIEW
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_VIEWS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_VIEWS : " + result);
		
		//VIEW TEXT
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_VIEW_PARSE ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_VIEW_PARSE : " + result);
		
		
		//VIEW CLOB
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_VIEW_PARSE_CLOB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_VIEW_PARSE_CLOB : " + result);
		
		//SEQ 
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_SEQ ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_SEQ : " + result);
		
		//프로시져
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PROC ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_SEQ : " + result);
		
		//프로시져 변수
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PROC_PARA ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PROC_PARA : " + result);
		
		//프로시져 TEXT
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PROC_PARSE ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PROC_PARSE : " + result);
		
		//프로시져 TEXT CLOB
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PROC_PARSE_CLOB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PROC_PARSE_CLOB : " + result);
		
		//사용량
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_USAGE ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_USAGE : " + result);
		
		
		//파티션 테이블
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PART_TABLES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PART_TABLES : " + result);
		
		//파티션 테이블 컬럼
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PART_KEY_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PART_KEY_COLUMNS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_TABLE_PARTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_TABLE_PARTS : " + result);
		
		//인덱스 파티션
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_INDEX_PARTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_INDEX_PARTS : " + result);
		
		//인덱스 파티션
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ALTI_PART_INDEXES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ALTI_PART_INDEXES : " + result);

		
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

		//WAT_CONSTRAINT ---------------------------------------------------
		cnt = insertwatcond();
		logger.debug("insertwatcond : " + cnt + " OK!!");

		//WAT_CONSTRAINT_COLUMN ---------------------------------------------------
		cnt = insertwatcondcol();
		logger.debug("insertwatcondcol : " + cnt + " OK!!");

		//WAT_TABLESPACE ---------------------------------------------------
		cnt = insertwatspac();
		logger.debug("insertwatspac : " + cnt + " OK!!");

		result = true;

		return result;
	}

	/**  insomnia
	 * @throws Exception */
	private int insertwattbl() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL                      ");
        sb.append("\n   ( DB_SCH_ID             ");
        sb.append("\n   , DBC_TBL_NM            ");
        sb.append("\n   , DBC_TBL_KOR_NM        ");
        sb.append("\n   , VERS                  ");
        sb.append("\n   , REG_TYP               ");
        sb.append("\n   , REG_DTM               ");
        sb.append("\n   , UPD_DTM               ");
        sb.append("\n   , DESCN                 ");
        sb.append("\n   , DB_CONN_TRG_ID        ");
        sb.append("\n   , DBC_TBL_SPAC_NM       ");
        sb.append("\n   , DDL_TBL_ID            ");
        sb.append("\n   , PDM_TBL_ID            ");
        sb.append("\n   , DBMS_TYPE             ");
        sb.append("\n   , SUBJ_ID               ");
        sb.append("\n   , COL_EACNT             ");
        sb.append("\n   , ROW_EACNT             ");
        sb.append("\n   , TBL_SIZE              ");
        sb.append("\n   , DATA_SIZE             ");
        sb.append("\n   , IDX_SIZE		       )");		
		sb.append("\n SELECT S.DB_SCH_ID       AS DB_SCH_ID       ");
		sb.append("\n      , A.TABLE_NAME      AS DBC_TBL_NM     ");
		//TABLE COMMENT
		sb.append("\n      , A.COMMENTS         AS DBC_TBL_KOR_NM     ");
		sb.append("\n      , '1'                   AS VERS                  ");
		sb.append("\n      , NULL                   AS REG_TYP              ");
//		sb.append("\n      , 'C'                   AS REG_TYP              ");
		sb.append("\n      , SYSDATE               AS REG_DTM         ");
		sb.append("\n      , NULL               AS UPD_DTM         ");
		sb.append("\n      , ''                    AS DESCN                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID  ");
		sb.append("\n      , A.TBS_NAME         AS DBC_TBL_SPAC_NM      ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                   ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                  ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE              ");
		sb.append("\n      , ''                    AS SUBJ_ID                       ");
		sb.append("\n      , A.COLUMN_COUNT             AS COL_EACNT   ");
		//로우건수
		sb.append("\n      , A.NUM_ROW            AS ROW_EACNT           ");
		sb.append("\n      , C.USED_SPACE               AS TBL_SIZE           ");
		sb.append("\n      , ''                    AS DATA_SIZE                    ");
		sb.append("\n      , D.USED_SPACE               AS IDX_SIZE           ");
//		sb.append("\n      , ''                    AS NUSE_SIZE                    ");
//		sb.append("\n      , ''                    AS BF_COL_EACNT               ");
//		sb.append("\n      , ''                    AS BF_ROW_EACNT              ");
//		sb.append("\n      , ''                    AS BF_TBL_SIZE                   ");
//		sb.append("\n      , ''                    AS BF_DATA_SIZE                ");
//		sb.append("\n      , ''                    AS BF_IDX_SIZE                   ");
//		sb.append("\n      , ''                    AS BF_NUSE_SIZE                ");
//		sb.append("\n      , ''                    AS ANA_DTM                     ");
//		sb.append("\n      , ''                    AS CRT_DTM                      ");
//		sb.append("\n      , ''                    AS CHG_DTM                     ");
//		sb.append("\n      , ''                    AS PDM_DESCN                  ");
//		sb.append("\n      , ''                    AS TBL_DQ_EXP_YN              ");
//		sb.append("\n      , ''                    AS DDL_TBL_ERR_EXS            ");
//		sb.append("\n      , ''                    AS DDL_TBL_ERR_CD             ");
//		sb.append("\n      , ''                    AS DDL_TBL_ERR_DESCN        ");
//		sb.append("\n      , ''                    AS DDL_COL_ERR_EXS           ");
//		sb.append("\n      , ''                    AS DDL_COL_ERR_CD            ");
//		sb.append("\n      , ''                    AS DDL_COL_ERR_DESCN       ");
//		sb.append("\n      , ''                    AS PDM_TBL_ERR_EXS           ");
//		sb.append("\n      , ''                    AS PDM_TBL_ERR_CD            ");
//		sb.append("\n      , ''                    AS PDM_TBL_ERR_DESCN       ");
//		sb.append("\n      , ''                    AS PDM_COL_ERR_EXS          ");
//		sb.append("\n      , ''                    AS PDM_COL_ERR_CD           ");
//		sb.append("\n      , ''                    AS PDM_COL_ERR_DESCN       ");
//		sb.append("\n      , ''                    AS DDL_TBL_EXTNC_EXS        ");
//		sb.append("\n      , ''                    AS PDM_TBL_EXTNC_EXS        ");
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
//		sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_ALTI_TABLES A  ");
		sb.append("\n  INNER JOIN (                    ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.USER_NAME = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_ALTI_DATABASES B                                              ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
		
		//테이블 , 인덱스 SIZE
		sb.append("\n   LEFT OUTER JOIN WAE_ALTI_USAGE C                                       ");
		sb.append("\n     ON A.DB_NM = C.DB_NM                                              ");
		sb.append("\n    AND A.USER_NAME = C.USER_NAME                                            ");
		sb.append("\n    AND A.TABLE_NAME = C.TABLE_NAME                                           ");
		sb.append("\n    AND C.TYPE = 'T'                                          ");
		sb.append("\n   LEFT OUTER JOIN (SELECT DB_NM, USER_NAME, TABLE_NAME, SUM(USED_SPACE) AS USED_SPACE  ");
		sb.append("\n                            FROM WAE_ALTI_USAGE   ");
		sb.append("\n                           WHERE TYPE = 'I'  ");
		sb.append("\n                           GROUP BY DB_NM, USER_NAME, TABLE_NAME ) D");
		sb.append("\n     ON A.DB_NM = D.DB_NM                    ");
		sb.append("\n    AND A.USER_NAME = D.USER_NAME       ");
		sb.append("\n    AND A.TABLE_NAME = D.TABLE_NAME     ");
		
		
//		sb.append("\n   LEFT OUTER JOIN (                                                   ");
//		sb.append("\n                    SELECT DB_NM, USER_NAME, TABLE_NAME, COUNT(*) AS COL_CNT  ");
//		sb.append("\n                      FROM WAE_ALTI_COLUMNS                                 ");
//		sb.append("\n                     GROUP BY DB_NM, USER_NAME, TABLE_NAME                    ");
//		sb.append("\n                   ) E                               ");
//		sb.append("\n     ON A.DB_NM = E.DB_NM                 ");
//		sb.append("\n    AND A.USER_NAME = E.USER_NAME         ");
//		sb.append("\n    AND A.TABLE_NAME = E.TABLE_NAME        ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'       ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL   ");
		sb.append("\n  ( DB_SCH_ID             ");
		sb.append("\n  , DBC_TBL_NM            ");
		sb.append("\n  , DBC_COL_NM            ");
		sb.append("\n  , DBC_COL_KOR_NM        ");
		sb.append("\n  , VERS                  ");
		sb.append("\n  , REG_TYP               ");
		sb.append("\n  , REG_DTM               ");
		sb.append("\n  , UPD_DTM               ");
		sb.append("\n  , DESCN                 ");
		sb.append("\n  , DDL_COL_ID            ");
		sb.append("\n  , PDM_COL_ID            ");
		sb.append("\n  , ITM_ID                ");
		sb.append("\n  , DATA_TYPE             ");
		sb.append("\n  , DATA_LEN              ");
		sb.append("\n  , DATA_PNUM             ");
		sb.append("\n  , DATA_PNT              ");
		sb.append("\n  , NULL_YN               ");
		sb.append("\n  , DEFLT_LEN             ");
		sb.append("\n  , DEFLT_VAL             ");
		sb.append("\n  , PK_YN                 ");
		sb.append("\n  , ORD                   ");
		sb.append("\n  , PK_ORD                ");
		sb.append("\n  , COL_DESCN            ) ");
		sb.append("\n SELECT S.DB_SCH_ID    AS DB_SCH_ID         ");
		sb.append("\n      , A.TABLE_NAME   AS DBC_TBL_NM        ");
		sb.append("\n      , A.COLUMN_NAME  AS DBC_COL_NM        ");
		sb.append("\n      , A.COMMENTS     AS DBC_COL_KOR_NM    ");
		sb.append("\n      , '1'            AS VERS              ");
		sb.append("\n      , NULL           AS REG_TYP           ");
		sb.append("\n      , SYSDATE        AS REG_DTM           ");
		sb.append("\n      , ''             AS UPD_DTM           ");
		sb.append("\n      , ''             AS DESCN             ");
		sb.append("\n      , ''             AS DDL_COL_ID        ");
		sb.append("\n      , ''             AS PDM_COL_ID        ");
		sb.append("\n      , ''             AS ITM_ID            ");
		sb.append("\n      , DATA_TYPE      AS DATA_TYPE         ");
		sb.append("\n      , DATA_LENGTH    AS DATA_LEN          ");
		sb.append("\n      , ''             AS DATA_PNUM         ");
		sb.append("\n      , A.DATA_SCALE   AS DATA_PNT          ");
		sb.append("\n      , IS_NULL        AS NULL_YN           ");
		sb.append("\n      , ''             AS DEFLT_LEN         ");
		sb.append("\n      , TRIM(DEFAULT_VAL)  AS DEFLT_VAL ");
		sb.append("\n      , (CASE WHEN C.CONST_COL_ORDER IS NOT NULL THEN 'Y' ELSE 'N' END ) AS PK_YN   ");
		sb.append("\n      , A.COLUMN_ORDER    AS ORD      ");
		//PK_ORDER
		sb.append("\n      , C.CONST_COL_ORDER AS PK_ORD    ");
		sb.append("\n      , ''                AS COL_DESCN ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''        ");
//		sb.append("\n      , ''  -- MMART_COL_EXTNC_YN      ");  
//		sb.append("\n      , ''  -- MMART_ORD_ERR_EXS       ");
//		sb.append("\n      , ''  -- MMART_PK_YN_ERR_EXS     ");
//		sb.append("\n      , ''  -- MMART_PK_ORD_ERR_EXS    ");
//		sb.append("\n      , ''  -- MMART_NULL_YN_ERR_EXS   ");
//		sb.append("\n      , ''  -- MMART_DEFLT_ERR_EXS     ");
//		sb.append("\n      , ''  -- MMART_CMMT_ERR_EXS      ");
//		sb.append("\n      , ''  -- MMART_DATA_TYPE_ERR_EXS ");
//		sb.append("\n      , ''  -- MMART_DATA_LEN_ERR_EXS  ");
//		sb.append("\n      , ''  -- MMART_DATA_PNT_ERR_EXS  ");
//		sb.append("\n      , ''  -- MMARTL_COL_ERR_EXS      ");
		sb.append("\n   FROM WAE_ALTI_TABLES T                      ");
	    sb.append("\n            INNER JOIN WAE_ALTI_COLUMNS A  ");
	    sb.append("\n                ON T.DB_NM = A.DB_NM  ");
	    sb.append("\n               AND T.USER_NAME = A.USER_NAME  ");
	    sb.append("\n               AND T.TABLE_NAME = A.TABLE_NAME  ");
		sb.append("\n  INNER JOIN (                  ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM         ");
		sb.append("\n    AND A.USER_NAME  = S.DB_SCH_PNM          ");
		sb.append("\n  INNER JOIN WAE_ALTI_DATABASES B              ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                         ");
		sb.append("\n    LEFT OUTER JOIN WAE_ALTI_CONSTRAINTS D ");
		sb.append("\n      ON A.DB_NM = D.DB_NM ");
		sb.append("\n     AND A.USER_NAME = D.USER_NAME ");
		sb.append("\n     AND A.TABLE_NAME  = D.TABLE_NAME  ");
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
		sb.append("\n     AND D.CONST_TYPE_CODE  = '3' ");   

		sb.append("\n    LEFT OUTER JOIN WAE_ALTI_CONST_COLUMNS C ");
		sb.append("\n      ON A.DB_NM = C.DB_NM ");
		sb.append("\n     AND A.USER_NAME = C.USER_NAME ");
		sb.append("\n     AND A.TABLE_NAME = C.TABLE_NAME ");
		sb.append("\n     AND A.COLUMN_NAME  = C.CONST_COL_NAME  ");
		sb.append("\n     AND C.CONSTRAINT_NAME  = D.CONSTRAINT_NAME  ");
		sb.append("\n     AND C.CONST_COL_ORDER  IS NOT NULL ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");

		return setExecuteUpdate_Org(sb.toString());

	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, UPPER(S.DB_SCH_PNM) AS DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n                      INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                         ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n                        AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                        AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
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
		sb.append("\n INSERT INTO WAT_DBC_IDX   ");
		sb.append("\n SELECT S.DB_SCH_ID            ");
		sb.append("\n      , A.TABLE_NAME                  ");
		sb.append("\n      , A.INDEX_NAME                  ");
		sb.append("\n      , S.DB_CONN_TRG_ID      ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , '1'                            ");
		sb.append("\n      , NULL                            ");
//		sb.append("\n      , 'C'                            ");
		sb.append("\n      , SYSDATE                    ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , A.TBS_NAME AS TBL_SPAC_NM           ");
		sb.append("\n      , ''                             ");
		sb.append("\n      , ''                             ");
		/*
	    0: FOREIGN KEY
	 1: NOT NULL
	 2: UNIQUE
	 3: PRIMARY KEY
	 4: NULL
	 5: TIMESTAMP
	 6: LOCAL UNIQUE
	 7: CHECK
	    sb.append("\n             WHEN 0 THEN 'FOREIGN KEY'         ");
		sb.append("\n             WHEN 1 THEN 'NOT NULL'            ");
		sb.append("\n             WHEN 2 THEN 'UNIQUE'              ");
		sb.append("\n             WHEN 3 THEN 'PRIMARY KEY'         ");
		sb.append("\n             WHEN 4 THEN 'NULL'                ");
		sb.append("\n             WHEN 5 THEN 'TIMESTAMP'           ");
		sb.append("\n             WHEN 6 THEN 'LOCAL UNIQUE'        ");
	 */
		sb.append("\n      , (CASE WHEN D.CONST_TYPE_NAME = 'PRIMARY KEY'  THEN 'Y' ELSE 'N' END) AS PK_YN  ");
		sb.append("\n      , (CASE WHEN D.CONST_TYPE_NAME = 'UNIQUE'  THEN 'Y' ELSE 'N' END) AS UQ_YN  ");
//		sb.append("\n      , A.IS_UNIQUE AS UQ_YN                   ");
		sb.append("\n      , A.COLUMN_CNT                 ");
		sb.append("\n      , C.USED_SPACE  AS IDX_SIZE ");
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
		sb.append("\n   FROM WAE_ALTI_INDEXES A      ");
		sb.append("\n  INNER JOIN (                   ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM     ");
		sb.append("\n    AND A.USER_NAME = S.DB_SCH_PNM            ");
		sb.append("\n  INNER JOIN WAE_ALTI_DATABASES B                     ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                     ");
		sb.append("\n   LEFT OUTER JOIN WAE_ALTI_USAGE C            ");
		sb.append("\n     ON A.DB_NM = C.DB_NM                     ");
		sb.append("\n    AND A.USER_NAME = C.USER_NAME                  ");
		sb.append("\n    AND A.INDEX_NAME  = C.TABLE_NAME                ");
		sb.append("\n    AND C.TYPE = 'I'                  ");
		sb.append("\n   LEFT OUTER JOIN WAE_ALTI_CONSTRAINTS D           ");
		sb.append("\n     ON A.DB_NM = D.DB_NM                     ");
		sb.append("\n    AND A.USER_NAME = D.USER_NAME                 ");
		sb.append("\n    AND A.TABLE_NAME = D.TABLE_NAME          ");
		sb.append("\n    AND A.INDEX_NAME = D.INDEX_NAME          ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"' ");

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
//		sb.append("\n      , 'C'                         ");
		sb.append("\n      , SYSDATE                     ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , INDEX_COL_ORDER                 ");
		sb.append("\n      , SORT_ORDER                   ");
		sb.append("\n      , C.DATA_TYPE                 ");
		sb.append("\n      , NULL AS DATA_PNUM                 ");
		sb.append("\n      , C.DATA_LENGTH                  ");
		sb.append("\n      , C.DATA_SCALE                  ");
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
		sb.append("\n   FROM WAE_ALTI_IDX_COLUMNS A   ");
		sb.append("\n            INNER JOIN ( ");
		sb.append(getdbconnsql());
		sb.append("\n                            ) S ");
		sb.append("\n              ON A.DB_NM = S.DB_CONN_TRG_PNM  ");
		sb.append("\n             AND A.USER_NAME = S.DB_SCH_PNM      ");
		sb.append("\n           INNER JOIN WAE_ALTI_DATABASES B       ");
		sb.append("\n               ON A.DB_NM = B.DB_NM           ");
		sb.append("\n           INNER JOIN WAE_ALTI_COLUMNS C         ");
		sb.append("\n               ON A.DB_NM = C.DB_NM           ");
		sb.append("\n             AND A.USER_NAME = C.USER_NAME     ");
		sb.append("\n             AND A.TABLE_NAME = C.TABLE_NAME   ");
		sb.append("\n             AND A.COLUMN_NAME = C.COLUMN_NAME  ");
		sb.append("\n WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcond() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_CND   ");
		sb.append("\n SELECT S.DB_SCH_ID      ");
		sb.append("\n      , A.TABLE_NAME              ");
		sb.append("\n      , A.CONSTRAINT_NAME      ");
		sb.append("\n      , C.COL_CNT  AS COL_EACNT ");
		sb.append("\n      , '1'                     ");
//		sb.append("\n      , 'C'                     ");
		sb.append("\n      , NULL                     ");
		sb.append("\n      , SYSDATE             ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , CASE WHEN INDEX_NAME IS NULL THEN 'N' ELSE 'Y' END   ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n   FROM WAE_ALTI_CONSTRAINTS A   ");
		sb.append("\n           INNER JOIN ( ");
		sb.append(getdbconnsql());
		sb.append("\n                           ) S              ");
		sb.append("\n              ON A.DB_NM = S.DB_CONN_TRG_PNM   ");
		sb.append("\n            AND A.USER_NAME = S.DB_SCH_PNM      ");
		sb.append("\n          INNER JOIN WAE_ALTI_DATABASES B         ");
		sb.append("\n              ON A.DB_NM = B.DB_NM                   ");
		sb.append("\n          INNER JOIN (SELECT DB_NM,USER_NAME,TABLE_NAME,CONSTRAINT_NAME         ");
		sb.append("\n                                    ,COUNT(CONST_COL_NAME) AS COL_CNT         ");
		sb.append("\n                            FROM WAE_ALTI_CONST_COLUMNS         ");
		sb.append("\n                           GROUP BY DB_NM,USER_NAME,TABLE_NAME,CONSTRAINT_NAME ) C         ");
		sb.append("\n              ON A.DB_NM = C.DB_NM                   ");
		sb.append("\n             AND A.USER_NAME = C.USER_NAME       ");
		sb.append("\n             AND A.TABLE_NAME = C.TABLE_NAME     ");
		sb.append("\n             AND A.CONSTRAINT_NAME = C.CONSTRAINT_NAME  ");
		sb.append("\n   WHERE A.DB_NM = '"+dbName+"'     ");
		

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcondcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_CND_COL    ");
		sb.append("\n SELECT S.DB_SCH_ID                     ");
		sb.append("\n      , C.TABLE_NAME AS TABLE_NAME                      ");
		sb.append("\n      , C.CONSTRAINT_NAME      ");
		sb.append("\n      , C.CONST_COL_NAME        ");
		sb.append("\n      , COL.COLUMN_NAME        ");
		sb.append("\n      , '1'                              ");
		sb.append("\n      , NULL                             ");
//		sb.append("\n      , 'C'                              ");
		sb.append("\n      , SYSDATE                      ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , C.CONST_COL_ORDER       ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n      , ''                                ");
		sb.append("\n   FROM WAE_ALTI_DATABASES B       ");
		sb.append("\n           INNER JOIN WAE_ALTI_USERS U  ");
		sb.append("\n               ON B.DB_NM = U.DB_NM     ");
		sb.append("\n           INNER JOIN ( ");
		sb.append(getdbconnsql());
		sb.append("\n                            ) S  ");
		sb.append("\n               ON U.DB_NM = S.DB_CONN_TRG_PNM     ");
		sb.append("\n             AND U.USER_NAME  = S.DB_SCH_PNM       ");
		sb.append("\n           INNER JOIN WAE_ALTI_TABLES T  ");
		sb.append("\n               ON U.DB_NM = T.DB_NM     ");
		sb.append("\n              AND U.USER_NAME = T.USER_NAME     ");
		//제약조건
		sb.append("\n           INNER JOIN WAE_ALTI_CONSTRAINTS A           ");
		sb.append("\n               ON T.DB_NM = A.DB_NM                     ");
		sb.append("\n              AND T.USER_NAME = A.USER_NAME     ");
		sb.append("\n              AND T.TABLE_NAME = A.TABLE_NAME     ");
		//제약조건 컬럼
		sb.append("\n           INNER JOIN WAE_ALTI_CONST_COLUMNS C           ");
		sb.append("\n               ON A.DB_NM = C.DB_NM                     ");
		sb.append("\n              AND A.USER_NAME = C.USER_NAME     ");
		sb.append("\n              AND A.TABLE_NAME = C.TABLE_NAME     ");
		sb.append("\n              AND A.CONSTRAINT_NAME   = C.CONSTRAINT_NAME      ");
		//컬럼정보
		sb.append("\n           INNER JOIN WAE_ALTI_COLUMNS COL  ");
		sb.append("\n               ON C.DB_NM = COL.DB_NM     ");
		sb.append("\n              AND C.USER_NAME = COL.USER_NAME     ");
		sb.append("\n              AND C.TABLE_NAME = COL.TABLE_NAME     ");
		sb.append("\n              AND C.CONST_COL_NAME  = COL.COLUMN_NAME       ");
		
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

	/**  insomnia */
	private int insertwatspac() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL_SPAC  ");
		sb.append("\n SELECT B.DB_CONN_TRG_ID          ");
		sb.append("\n      , A.TBS_NAME ");
		sb.append("\n      , '1'           ");
		sb.append("\n      , NULL           ");
//		sb.append("\n      , 'C'           ");
		sb.append("\n      , SYSDATE   ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n      , ''            ");
		sb.append("\n   FROM WAE_ALTI_TABLESPACES A               ");
		sb.append("\n           INNER JOIN WAE_ALTI_DATABASES B  ");
		sb.append("\n               ON A.DB_NM = B.DB_NM            ");
		sb.append("\n WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

}
