/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorTibero.java
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
 * 2. FileName  : CollectorTibero.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorTibero {

	private static final Logger logger = Logger.getLogger(CollectorTibero.class);

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
	private String dbms_id = null;

	private int execCnt = 100; 


	public CollectorTibero() {

	}

	/** insomnia */
	public CollectorTibero(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;

//		this.schemaName = targetDb.getDbc_owner_nm();
//		this.dbName = targetDb.getDbms_enm();

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
			this.dbms_id = targetDb.getDbms_id();

			int p = 0;
			int cnt = 0;

			//첫번째 스키마 일때만 처리한다.
			if(dbCnt == 0) {
				//스키마 정보를 모두 삭제한다.
				deleteSchema();
				//스키마 정보를 추가한다. DB인스턴스 별로 한번만 실행한다.
				cnt = insertMetaDATABASES();
				logger.debug(sp + (++p) + ". insertMetaDATABASES " + cnt + " OK!!");
				//Meta_DATABASES block_size update----------------------------------
//				cnt = updateMetaDATABASES_ORA_BLOCK_SIZE();
//				logger.debug(sp + (++p) + ". updateMetaDATABASES_ORA_BLOCK_SIZE " + cnt + " OK!!");

				//DBA_TABLESPACES ---------------------------------------------------
//				cnt = insertDBA_TABLESPACES();
//				logger.debug(sp + (++p) + ". insertDBA_TABLESPACES " + cnt + " OK!!");

				con_org.commit();
			}

			dbCnt++;

			logger.debug(" schemaName : "+schemaName);
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
	//		cnt = insertDBA_CONSTRAINTS();
	//		logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

			//DBA_CONS_COLUMNS ---------------------------------------------------
	//		cnt = insertDBA_CONS_COLUMNS();
	//		logger.debug(sp + (++p) + ". insertDBA_CONS_COLUMNS " + cnt + " OK!!");

			//DBA_INDEXES ---------------------------------------------------
	//		cnt = insertDBA_INDEXES(); 
	//		logger.debug(sp + (++p) + ". insertDBA_INDEXES " + cnt + " OK!!");

			//DBA_IND_COLUMNS ---------------------------------------------------
	//		cnt = insertDBA_IND_COLUMNS();
	//		logger.debug(sp + (++p) + ". insertDBA_IND_COLUMNS " + cnt + " OK!!");
			
			//DBA_OBJECTS ---------------------------------------------------
//			cnt = insertDBA_OBJECTS();
//			logger.debug(sp + (++p) + ". insertDBA_OBJECTS " + cnt + " OK!!");


// *** PARTITION 관련 수집 내용 시작 ***********//

//			//DBA_IND_PARTITIONS ---------------------------------------------------
//			cnt = insertDBA_IND_PARTITIONS();
//			logger.debug(sp + (++p) + ". insertDBA_IND_PARTITIONS " + cnt + " OK!!");

//			//DBA_IND_SUBPARTITIONS ---------------------------------------------------
//			cnt = insertDBA_IND_SUBPARTITIONS();
//			logger.debug(sp + (++p) + ". insertDBA_IND_SUBPARTITIONS " + cnt + " OK!!");

//			//DBA_PART_TABLES ---------------------------------------------------
//			cnt = insertDBA_PART_TABLES();
//			logger.debug(sp + (++p) + ". insertDBA_PART_TABLES " + cnt + " OK!!");
//
//			//DBA_PART_INDEXES ---------------------------------------------------
//			cnt = insertDBA_PART_INDEXES();
//     		logger.debug(sp + (++p) + ". insertDBA_PART_INDEXES " + cnt + " OK!!");
//
//			//DBA_PART_KEY_COLUMNS ---------------------------------------------------
//			cnt = insertDBA_PART_KEY_COLUMNS();
//			logger.debug(sp + (++p) + ". insertDBA_PART_KEY_COLUMNS " + cnt + " OK!!");
//
//			//DBA_SUBPART_KEY_COLUMNS ---------------------------------------------------
//			cnt = insertDBA_SUBPART_KEY_COLUMNS();
//			logger.debug(sp + (++p) + ". insertDBA_SUBPART_KEY_COLUMNS " + cnt + " OK!!");

//			//DBA_PART_COL_STATISTICS ---------------------------------------------------
//			cnt = insertDBA_PART_COL_STATISTICS();
//			logger.debug(sp + (++p) + ". insertDBA_PART_COL_STATISTICS " + cnt + " OK!!");
//
//			//DBA_SUBPART_COL_STATISTICS ---------------------------------------------------
//			cnt = insertDBA_SUBPART_COL_STATISTICS();
//			logger.debug(sp + (++p) + ". insertDBA_SUBPART_COL_STATISTICS " + cnt + " OK!!");

//			//DBA_TAB_PARTITIONS ---------------------------------------------------
//			cnt = insertDBA_TAB_PARTITIONS();
//			logger.debug(sp + (++p) + ". insertDBA_TAB_PARTITIONS " + cnt + " OK!!");
//
//			//DBA_TAB_SUBPARTITIONS ---------------------------------------------------
//			cnt = insertDBA_TAB_SUBPARTITIONS();
//			logger.debug(sp + (++p) + ". insertDBA_TAB_SUBPARTITIONS " + cnt + " OK!!");

// *** PARTITION 관련 수집 내용 끝 ***********//

			//DBA_PROCEDURES ---------------------------------------------------
//			cnt = insertDBA_PROCEDURES();
//			logger.debug(sp + (++p) + ". insertDBA_PROCEDURES " + cnt + " OK!!");

			//DBA_DATA_FILES ---------------------------------------------------
//			cnt = insertDBA_DATA_FILES();
//			logger.debug(sp + (++p) + ". insertDBA_DATA_FILES " + cnt + " OK!!");

			//DBA_FREE_SPACE ---------------------------------------------------
//			cnt = insertDBA_FREE_SPACE();
//			logger.debug(sp + (++p) + ". insertDBA_FREE_SPACE " + cnt + " OK!!");


			//DBA_SEGMENTS ---------------------------------------------------
//			cnt = insertDBA_SEGMENTS();
//			logger.debug(sp + (++p) + ". insertDBA_SEGMENTS " + cnt + " OK!!");

			//DBA_VIEWS ---------------------------------------------------
//			cnt = insertDBA_VIEWS();
//			logger.debug(sp + (++p) + ". insertDBA_VIEWS " + cnt + " OK!!");

			//DBA_DEPENDENCIES ---------------------------------------------------
//			cnt = insertDBA_DEPENDENCIES();
//			logger.debug(sp + (++p) + ". insertDBA_DEPENDENCIES " + cnt + " OK!!");

			//DBA_SOURCE ---------------------------------------------------
			//cnt = insertDBA_SOURCE();
			//logger.debug(sp + (++p) + ". insertDBA_SOURCE " + cnt + " OK!!");

			con_org.commit();

		}

		result = true;

		return result;

	}

	/** @return insomnia
	 * @throws Exception */
	private int insertDBA_PROCEDURES() throws Exception {
		StringBuffer sb = new StringBuffer();

		if(dbmsVersion.equals("")) {
		// 9i이상에서만 존재함.
		sb.append("\nSELECT OWNER                    ");
		sb.append("\n      ,OBJECT_NAME              ");
		sb.append("\n      ,PROCEDURE_NAME           ");
		sb.append("\n      ,AGGREGATE                ");
		sb.append("\n      ,PIPELINED                ");
		sb.append("\n      ,IMPLTYPEOWNER            ");
		sb.append("\n      ,IMPLTYPENAME             ");
		sb.append("\n      ,PARALLEL                 ");
		sb.append("\n      ,INTERFACE                ");
		sb.append("\n      ,DETERMINISTIC            "); 
		sb.append("\n      ,AUTHID                   ");
		sb.append("\n      ,'").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n  FROM DBA_PROCEDURES");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");

		} else {

			sb.append("\nSELECT OWNER                    ");
			sb.append("\n      ,OBJECT_NAME              ");
			sb.append("\n      ,'' AS PROCEDURE_NAME     ");
			sb.append("\n      ,'' AS AGGREGATE          ");
			sb.append("\n      ,'' AS PIPELINED          ");
			sb.append("\n      ,'' AS IMPLTYPEOWNER      ");
			sb.append("\n      ,'' AS IMPLTYPENAME       ");
			sb.append("\n      ,'' AS PARALLEL           ");
			sb.append("\n      ,'' AS INTERFACE          ");
			sb.append("\n      ,'' AS DETERMINISTIC      ");
			sb.append("\n      ,'' AS AUTHID             ");
			sb.append("\n      ,'").append(dbName).append("' AS OF_DATABASE");
			sb.append("\n  FROM DBA_OBJECTS");
			sb.append("\n WHERE OWNER ='").append(schemaName).append("'");
			sb.append("\n   AND OBJECT_TYPE IN ('PACKAGE','FUNCTION','PROCEDURE')");

		}
		getResultSet(sb.toString());

		sb.setLength(0);
		sb.append("\nINSERT INTO WAE_TIBERO_OBJ (");
		sb.append("\n   DB_NM                         ");
		sb.append("\n  ,SCH_NM                    ");
		sb.append("\n  ,ETC_OBJ_NM                ");
		sb.append("\n  ,AGGREGATE                    ");
		sb.append("\n  ,PIPELINED                    ");
		sb.append("\n  ,IMPLTYPEOWNER                ");
		sb.append("\n  ,IMPLTYPENAME                 ");
		sb.append("\n  ,PARALLEL                     ");
		sb.append("\n  ,INTERFACE                    ");
		sb.append("\n  ,DETERMINISTIC                ");
		sb.append("\n  ,AUTHID                       ");
		sb.append("\n  ,PROC_NM                 	 ");
		sb.append("\n) VALUES (                      ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,          ");
		sb.append("\n  ?,?                         ");
		sb.append("\n)                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("OBJECT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("AGGREGATE"));
				pst_org.setString(rsGetCnt++, rs.getString("PIPELINED"));
				pst_org.setString(rsGetCnt++, rs.getString("IMPLTYPEOWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("IMPLTYPENAME"));
				pst_org.setString(rsGetCnt++, rs.getString("PARALLEL"));
				pst_org.setString(rsGetCnt++, rs.getString("INTERFACE"));
				pst_org.setString(rsGetCnt++, rs.getString("DETERMINISTIC"));
				pst_org.setString(rsGetCnt++, rs.getString("AUTHID"));
				pst_org.setString(rsGetCnt++, rs.getString("PROCEDURE_NAME"));

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

	/** @return insomnia
	 * @throws Exception */
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

		sb.append("\nINSERT INTO WAE_TIBERO_OBJECTS (");
		sb.append("\n   DB_NM                     ");
		sb.append("\n  ,SCH_NM                    ");
		sb.append("\n  ,OBJECT_NAME               ");
		sb.append("\n  ,SUBOBJECT_NAME            ");
		sb.append("\n  ,OBJECT_ID                 ");
		sb.append("\n  ,DATA_OBJECT_ID            ");
		sb.append("\n  ,OBJECT_TYPE               ");
		sb.append("\n  ,CREATED                   ");
		sb.append("\n  ,LAST_DDL_TIME             ");
		sb.append("\n  ,TIMESTAMP                 ");
		sb.append("\n  ,STATUS                    ");
		sb.append("\n  ,TEMPORARY                 ");
		sb.append("\n  ,GENERATED                 ");
		sb.append("\n  ,SECONDARY                 ");
		sb.append("\n) VALUES (                   ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,       ");
		sb.append("\n  ?,?,?,?                    ");
		sb.append("\n)                            ");


		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
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
	 * DBC DBA_TABLESPACES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLESPACES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                                  ");
		sb.append("\n --      ,데이터베이스명                 ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n       ,TABLESPACE_NAME                   ");
		sb.append("\n       ,INITIAL_EXTENT                   ");
		sb.append("\n       ,NEXT_EXTENT                      ");
		sb.append("\n       ,MIN_EXTENTS                      ");
		sb.append("\n       ,MAX_EXTENTS                      ");
		sb.append("\n       ,'' AS PCT_INCREASE                     ");
		sb.append("\n       ,'' AS MIN_EXTLEN                       ");
		sb.append("\n       ,'' AS STATUS                           ");
		sb.append("\n       ,CONTENTS                         ");
		sb.append("\n       ,'' AS LOGGING                          ");
		sb.append("\n       ,'' AS EXTENT_MANAGEMENT                ");
		sb.append("\n       ,'' AS ALLOCATION_TYPE                  ");
		sb.append("\n       ,'' AS PLUGGED_IN                       ");
		sb.append("\n   FROM DBA_TABLESPACES                  ");
//		sb.append("\n UNION ALL                               ");
//		sb.append("\n SELECT                                  ");
//		sb.append("\n --      ,데이터베이스명                 ");
//		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
//		sb.append("\n       ,TABLESPACE_NAME                   ");
//		sb.append("\n       ,INITIAL_EXTENT                   ");
//		sb.append("\n       ,NEXT_EXTENT                      ");
//		sb.append("\n       ,MIN_EXTENTS                      ");
//		sb.append("\n       ,MAX_EXTENTS                      ");
//		sb.append("\n       ,PCT_INCREASE                     ");
//		sb.append("\n       ,MIN_EXTLEN                       ");
//		sb.append("\n       ,STATUS                           ");
//		sb.append("\n       ,CONTENTS                         ");
//		sb.append("\n       ,LOGGING                          ");
//		sb.append("\n       ,EXTENT_MANAGEMENT                ");
//		sb.append("\n       ,ALLOCATION_TYPE                  ");
//		sb.append("\n       ,NULL AS PLUGGED_IN               ");
//		sb.append("\n   FROM USER_TABLESPACES                 ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_TBL_SPAC (");
		sb.append("\n   DB_NM               ");
		sb.append("\n       ,TBL_SPAC_NM                   ");
		sb.append("\n       ,INITIAL_EXTENT                   ");
		sb.append("\n       ,NEXT_EXTENT                      ");
		sb.append("\n       ,MIN_EXTENTS                      ");
		sb.append("\n       ,MAX_EXTENTS                      ");
		sb.append("\n       ,PCT_INCREASE                     ");
		sb.append("\n       ,MIN_EXTLEN                       ");
		sb.append("\n       ,STATUS                           ");
		sb.append("\n       ,CONTENTS                         ");
		sb.append("\n       ,LOGGING                          ");
		sb.append("\n       ,EXTENT_MANAGEMENT                ");
		sb.append("\n       ,ALLOCATION_TYPE                  ");
		sb.append("\n       ,PLUGGED_IN                       ");
		sb.append("\n) VALUES (                       ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,           ");
		sb.append("\n  ?,?,?,?                      ");
		sb.append("\n)                                ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLESPACE_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INITIAL_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NEXT_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_INCREASE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTLEN"));
				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("CONTENTS"));
				pst_org.setString(rsGetCnt++, rs.getString("LOGGING"));
				pst_org.setString(rsGetCnt++, rs.getString("EXTENT_MANAGEMENT"));
				pst_org.setString(rsGetCnt++, rs.getString("ALLOCATION_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("PLUGGED_IN"));

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
	 * DBC DBA_VIEWS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_VIEWS() throws SQLException, Exception
	{
		int cnt = 0;
		int resultCnt = 0;
		int iObjId = 0;
		int iTextLen = 0;
		String strText = null;
		String clobSql = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
	    Writer writer = null;
	    Reader src = null;

	    try{
		StringBuffer sb = new StringBuffer();

		if(dbmsVersion.equals("")) {
		    //9i 이상에서 사용
			sb.append("\nSELECT A.OWNER                          ");
			sb.append("\n      ,A.VIEW_NAME                      ");
			sb.append("\n      ,B.OBJECT_ID AS OBJECT_ID         ");
			sb.append("\n      ,A.TEXT_LENGTH                    ");
			sb.append("\n      ,A.TEXT                           ");
			sb.append("\n      ,A.TYPE_TEXT_LENGTH               ");
			sb.append("\n      ,A.TYPE_TEXT                      ");
			sb.append("\n      ,A.OID_TEXT_LENGTH                ");
			sb.append("\n      ,A.OID_TEXT                       ");
			sb.append("\n      ,A.VIEW_TYPE_OWNER                ");
			sb.append("\n      ,A.VIEW_TYPE                      ");
			sb.append("\n      ,A.SUPERVIEW_NAME                 ");
			sb.append("\n      ,'").append(dbName).append("' AS OF_SYSTEM");
			sb.append("\n      ,'").append(dbName).append("' AS OF_DATABASE");
			sb.append("\n  FROM DBA_VIEWS").append(" A");
			sb.append("\n     INNER JOIN DBA_OBJECTS").append(" B");
			sb.append("\n      ON A.OWNER = B.OWNER          ");
			sb.append("\n     AND A.VIEW_NAME = B.OBJECT_NAME");
			sb.append("\n     AND B.OBJECT_TYPE = 'VIEW'     ");
			sb.append("\n    WHERE A.OWNER = '").append(schemaName).append("'");
//System.out.println(sb.toString());
		} else {
			sb.append("\nSELECT A.OWNER                          ");
			sb.append("\n      ,A.VIEW_NAME                      ");
			sb.append("\n      ,B.OBJECT_ID AS OBJECT_ID         ");
			sb.append("\n      ,A.TEXT_LENGTH                    ");
			sb.append("\n      ,A.TEXT                           ");
			sb.append("\n      ,A.TYPE_TEXT_LENGTH               ");
			sb.append("\n      ,A.TYPE_TEXT                      ");
			sb.append("\n      ,A.OID_TEXT_LENGTH                ");
			sb.append("\n      ,A.OID_TEXT                       ");
			sb.append("\n      ,A.VIEW_TYPE_OWNER                ");
			sb.append("\n      ,A.VIEW_TYPE                      ");
			sb.append("\n      ,'' AS SUPERVIEW_NAME                 ");
			sb.append("\n      ,'").append(dbName).append("' AS OF_SYSTEM");
			sb.append("\n  FROM DBA_VIEWS").append(" A");
			sb.append("\n      , DBA_OBJECTS").append(" B");
			sb.append("\n   WHERE A.OWNER = B.OWNER          ");
			sb.append("\n     AND A.VIEW_NAME = B.OBJECT_NAME");
			sb.append("\n     AND B.OBJECT_TYPE = 'VIEW'     ");
			sb.append("\n     AND A.OWNER = '").append(schemaName).append("'");
		}
//System.out.println(sb.toString());
		getResultSet(sb.toString());

		sb = new StringBuffer();

		if( rs != null ) {
			sb = new StringBuffer();
			sb.append("\n INSERT INTO WAE_TIBERO_VIEW(    ");
			sb.append("\n  DB_NM                      ");
			sb.append("\n ,SCH_NM                      ");
			sb.append("\n ,VIEW_NM                     ");
			sb.append("\n ,OBJECT_ID                   ");
			sb.append("\n ,TEXT_LENGTH                 ");
			sb.append("\n ,TEXT                        ");
			sb.append("\n ,TYPE_TEXT_LENGTH            ");
			sb.append("\n ,TYPE_TEXT                   ");
			sb.append("\n ,OID_TEXT                    ");
			sb.append("\n ,VIEW_TYPE_OWNER             ");
			sb.append("\n ,SUPERVIEW_NAME              ");
			sb.append("\n )VALUES(                     ");
			sb.append("\n ?,?,?,?,?,empty_clob(),?,?,?,?,         ");
			sb.append("\n ?                            ");
			sb.append("\n )                            ");

			int rsCnt = 0;

			while( rs.next() ) {

				iObjId = rs.getInt("OBJECT_ID");
				strText = rs.getString("TEXT");
				iTextLen = rs.getInt("TEXT_LENGTH");

				try {

				con_org.setAutoCommit(false);


				pstmt = con_org.prepareStatement(sb.toString());

				rsCnt = 1;
				pstmt.setString(rsCnt++, rs.getString("OF_SYSTEM"));
				pstmt.setString(rsCnt++, rs.getString("OWNER"));
				pstmt.setString(rsCnt++, rs.getString("VIEW_NAME"));
				pstmt.setInt   (rsCnt++, iObjId);
				pstmt.setInt   (rsCnt++, rs.getInt("TEXT_LENGTH"));
				pstmt.setInt   (rsCnt++, rs.getInt("TYPE_TEXT_LENGTH"));
				pstmt.setString(rsCnt++, rs.getString("TYPE_TEXT"));
				//pstmt.setInt   (rsCnt++, rs.getInt("OID_TEXT_LENGTH"));
				pstmt.setString(rsCnt++, rs.getString("OID_TEXT"));
				pstmt.setString(rsCnt++, rs.getString("VIEW_TYPE_OWNER"));
//				pstmt.setString(rsCnt++, rs.getString("VIEW_TYPE"));
				pstmt.setString(rsCnt++, rs.getString("SUPERVIEW_NAME"));


				cnt = pstmt.executeUpdate();
				pstmt.close();

				//clob 처리
				if( cnt > 0 ) {
					resultCnt++;

					clobSql = " SELECT TEXT FROM WAE_TIBERO_VIEW WHERE OBJECT_ID = ? FOR UPDATE " ;

					pstmt = con_org.prepareStatement(clobSql);
					pstmt.clearParameters();
					pstmt.setInt(1, iObjId);

					resultSet = pstmt.executeQuery();

					try {
						if(resultSet.next()){
							//String viewDef = CLOB에 들어가는 String;
							CLOB clob = (CLOB)resultSet.getObject("TEXT");
							writer = clob.getCharacterOutputStream();
							src = new CharArrayReader(strText.toCharArray());

							char[] buffer = new char[iTextLen];
							int read = 0;

							while ( (read = src.read(buffer,0,iTextLen)) != -1) {
								writer.write(buffer, 0, read); // 여기가 CLOB으로 입력되는 곳입니다.
							}

							src.close();
							writer.close();
						}

					} catch(IOException e) {
						throw new Exception(e);
			      	}

					resultSet.close();
					pstmt.close();
				}
				con_org.commit();

				} catch (Exception e) {

					con_org.rollback();
			        e.printStackTrace();

			    } finally {

			    }

			}
		} // end of if

		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		    
		    if(pst_tgt != null) pst_tgt.close();
		    if(pst_org != null) pst_org.close();
		    if(rs != null) rs.close();
		}



		return resultCnt;
	}

	/**
	 * DBC DBA_SEGMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_SEGMENTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                   ");
		sb.append("\n --,데이터베이스명        ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n     , OWNER                ");
		sb.append("\n     ,SEGMENT_NAME        ");
		sb.append("\n     ,TABLESPACE_NAME     ");
		sb.append("\n     ,PARTITION_NAME      ");
		sb.append("\n     ,SEGMENT_TYPE        ");
		sb.append("\n     ,HEADER_FILE         ");
		sb.append("\n     ,HEADER_BLOCK        ");
		sb.append("\n     ,BYTES               ");
		sb.append("\n     ,BLOCKS              ");
		sb.append("\n     ,EXTENTS             ");
		sb.append("\n     ,INITIAL_EXTENT      ");
		sb.append("\n     ,NEXT_EXTENT         ");
		sb.append("\n     ,MIN_EXTENTS         ");
		sb.append("\n     ,MAX_EXTENTS         ");
		sb.append("\n     ,PCT_INCREASE        ");
		sb.append("\n     ,FREELISTS           ");
		sb.append("\n     ,FREELIST_GROUPS     ");
		sb.append("\n     ,RELATIVE_FNO        ");
		sb.append("\n     ,BUFFER_POOL         ");
		sb.append("\n   FROM DBA_SEGMENTS      ");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_SEG (");
		sb.append("\n   DB_NM                      ");
		sb.append("\n     ,SCH_NM                ");
		sb.append("\n     ,SGMT_NM        ");
		sb.append("\n     ,TBL_SPAC_NM     ");
		sb.append("\n     ,PART_NM      ");
		sb.append("\n     ,SGMT_TYPE        ");
		sb.append("\n     ,HEADER_FILE         ");
		sb.append("\n     ,HEADER_BLOCK        ");
		sb.append("\n     ,BYTES               ");
		sb.append("\n     ,BLOCKS              ");
		sb.append("\n     ,EXTENTS             ");
		sb.append("\n     ,INITIAL_EXTENT      ");
		sb.append("\n     ,NEXT_EXTENT         ");
		sb.append("\n     ,MIN_EXTENTS         ");
		sb.append("\n     ,MAX_EXTENTS         ");
		sb.append("\n     ,PCT_INCREASE        ");
		sb.append("\n     ,FREELISTS           ");
		sb.append("\n     ,FREELIST_GROUPS     ");
		sb.append("\n     ,RELATIVE_FNO        ");
		sb.append("\n     ,BUFFER_POOL         ");
		sb.append("\n) VALUES (                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?        ");
		sb.append("\n)                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("SEGMENT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLESPACE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("PARTITION_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("SEGMENT_TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("HEADER_FILE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("HEADER_BLOCK"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BYTES"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INITIAL_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NEXT_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_INCREASE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELISTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELIST_GROUPS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RELATIVE_FNO"));
				pst_org.setString(rsGetCnt++, rs.getString("BUFFER_POOL"));

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
	 * DBC DBA_IND_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_IND_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                             ");
		sb.append("\n --      데이터베이스명             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , TABLE_OWNER                   ");
		sb.append("\n      ,TABLE_NAME                   ");
		sb.append("\n      ,COLUMN_NAME AS COL_NM        ");
		sb.append("\n      ,INDEX_NAME                   ");
		sb.append("\n      ,COLUMN_NAME AS IDX_COL_NM    ");
		sb.append("\n      ,COLUMN_POSITION              ");
		sb.append("\n      ,COLUMN_LENGTH                ");
		sb.append("\n      ,DESCEND                      ");
		sb.append("\n   FROM DBA_IND_COLUMNS             ");
		sb.append("\n WHERE INDEX_OWNER = '").append(schemaName).append("'");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_IDX_COL(");
		sb.append("\n   DB_NM                  ");
		sb.append("\n  ,SCH_NM                   ");
		sb.append("\n  ,TBL_NM                  ");
		sb.append("\n  ,COL_NM                   ");
		sb.append("\n  ,IDX_NM                  ");
		sb.append("\n  ,IDX_COL_NM              ");
		sb.append("\n  ,IDX_COL_ORD                ");
		sb.append("\n  ,COL_LEN                      ");
		sb.append("\n  ,SORT_TYPE                    ");
		sb.append("\n) VALUES (                      ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?           ");
		sb.append("\n)                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COL_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("IDX_COL_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLUMN_POSITION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLUMN_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("DESCEND"));

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
	 * DBC DBA_INDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_INDEXES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                                    ");
		sb.append("\n     --,데이터베이스명                     ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      ,OWNER                               ");
		sb.append("\n      ,TABLE_NAME                          ");
		sb.append("\n      ,INDEX_NAME                          ");
		sb.append("\n      ,TABLESPACE_NAME                     ");
		sb.append("\n      ,INDEX_TYPE                          ");
		sb.append("\n      ,UNIQUENESS                          ");
		sb.append("\n      ,COMPRESSION                         ");
		sb.append("\n      ,PREFIX_LENGTH                       ");
		sb.append("\n      ,INI_TRANS                           ");
		sb.append("\n      ,MAX_TRANS                           ");
		sb.append("\n      ,INITIAL_EXTENT                      ");
		sb.append("\n      ,NEXT_EXTENT                         ");
		sb.append("\n      ,MIN_EXTENTS                         ");
		sb.append("\n      ,MAX_EXTENTS                         ");
		sb.append("\n      ,PCT_INCREASE                        ");
		sb.append("\n      ,PCT_THRESHOLD                       ");
		sb.append("\n      ,INCLUDE_COLUMN                      ");
		sb.append("\n      ,FREELISTS                           ");
		sb.append("\n      ,FREELIST_GROUPS                     ");
		sb.append("\n      ,PCT_FREE                            ");
		sb.append("\n      ,LOGGING                             ");
		sb.append("\n      ,BLEVEL                              ");
		sb.append("\n      ,LEAF_BLOCKS                         ");
		sb.append("\n      ,DISTINCT_KEYS                       ");
		sb.append("\n      ,AVG_LEAF_BLOCKS_PER_KEY             ");
		sb.append("\n      ,AVG_DATA_BLOCKS_PER_KEY             ");
		sb.append("\n      ,CLUSTERING_FACTOR                   ");
		sb.append("\n      ,STATUS                              ");
		sb.append("\n      ,NUM_ROWS                            ");
		sb.append("\n      ,SAMPLE_SIZE                         ");
		sb.append("\n      ,LAST_ANALYZED                       ");
		sb.append("\n      ,DEGREE                              ");
		sb.append("\n      ,INSTANCES                           ");
		sb.append("\n      ,PARTITIONED                         ");
		sb.append("\n      ,TEMPORARY                           ");
		sb.append("\n      ,GENERATED                           ");
		sb.append("\n      ,SECONDARY                           ");
		sb.append("\n      ,BUFFER_POOL                         ");
		sb.append("\n      ,USER_STATS                          ");
		sb.append("\n      ,DURATION                            ");
		sb.append("\n      ,PCT_DIRECT_ACCESS                   ");
		sb.append("\n      ,ITYP_OWNER                          ");
		sb.append("\n      ,ITYP_NAME                           ");
		sb.append("\n      ,PARAMETERS                          ");
		sb.append("\n      ,GLOBAL_STATS                        ");
		sb.append("\n      ,DOMIDX_STATUS                       ");
		sb.append("\n      ,DOMIDX_OPSTATUS                     ");
		sb.append("\n      ,FUNCIDX_STATUS                      ");
		sb.append("\n   FROM DBA_INDEXES                        ");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");
		sb.append("\n   AND INDEX_TYPE <> 'LOB'    ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_IDX ( ");
		sb.append("\n   DB_NM                      ");
		sb.append("\n      ,SCH_NM                               ");
		sb.append("\n      ,TBL_NM                          ");
		sb.append("\n      ,IDX_NM                          ");
		sb.append("\n      ,TBL_SPAC_NM                     ");
		sb.append("\n      ,IDX_TYPE                          ");
		sb.append("\n      ,UNIQUENESS                          ");
		sb.append("\n      ,COMPRESSION                         ");
		sb.append("\n      ,PREFIX_LENGTH                       ");
		sb.append("\n      ,INI_TRANS                           ");

		sb.append("\n      ,MAX_TRANS                           ");
		sb.append("\n      ,INITIAL_EXTENT                      ");
		sb.append("\n      ,NEXT_EXTENT                         ");
		sb.append("\n      ,MIN_EXTENTS                         ");
		sb.append("\n      ,MAX_EXTENTS                         ");
		sb.append("\n      ,PCT_INCREASE                        ");
		sb.append("\n      ,PCT_THRESHOLD                       ");
		sb.append("\n      ,INCLUDE_COLUMN                      ");
		sb.append("\n      ,FREELISTS                           ");
		sb.append("\n      ,FREELIST_GROUPS                     ");

		sb.append("\n      ,PCT_FREE                            ");
		sb.append("\n      ,LOGGING                             ");
		sb.append("\n      ,BLEVEL                              ");
		sb.append("\n      ,LEAF_BLOCKS                         ");
		sb.append("\n      ,DISTINCT_KEYS                       ");
		sb.append("\n      ,AVG_LEAF_BLOCKS_PER_KEY             ");
		sb.append("\n      ,AVG_DATA_BLOCKS_PER_KEY             ");
		sb.append("\n      ,CLUSTERING_FACTOR                   ");
		sb.append("\n      ,STATUS                              ");
		sb.append("\n      ,NUM_ROWS                            ");

		sb.append("\n      ,SAMPLE_SIZE                         ");
		sb.append("\n      ,LAST_ANALYZED                       ");
		sb.append("\n      ,DEGREE                              ");
		sb.append("\n      ,INSTANCES                           ");
		sb.append("\n      ,PARTITIONED                         ");
		sb.append("\n      ,TEMPORARY                           ");
		sb.append("\n      ,GENERATED                           ");
		sb.append("\n      ,SECONDARY                           ");
		sb.append("\n      ,BUFFER_POOL                         ");
		sb.append("\n      ,USER_STATS                          ");

		sb.append("\n      ,DURATION                            ");
		sb.append("\n      ,PCT_DIRECT_ACCESS                   ");
		sb.append("\n      ,ITYP_OWNER                          ");
		sb.append("\n      ,ITYP_NAME                           ");
		sb.append("\n      ,PARAMETERS                          ");
		sb.append("\n      ,GLOBAL_STATS                        ");
		sb.append("\n      ,DOMIDX_STATUS                       ");
		sb.append("\n      ,DOMIDX_OPSTATUS                     ");
		sb.append("\n      ,FUNCIDX_STATUS                      ");
		sb.append("\n) VALUES (                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?        ");
		sb.append("\n)                             ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLESPACE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("UNIQUENESS"));
				pst_org.setString(rsGetCnt++, rs.getString("COMPRESSION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PREFIX_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INI_TRANS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_TRANS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INITIAL_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NEXT_EXTENT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_EXTENTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_INCREASE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_THRESHOLD"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INCLUDE_COLUMN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELISTS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FREELIST_GROUPS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_FREE"));
				pst_org.setString(rsGetCnt++, rs.getString("LOGGING"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BLEVEL"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LEAF_BLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DISTINCT_KEYS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_LEAF_BLOCKS_PER_KEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_DATA_BLOCKS_PER_KEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CLUSTERING_FACTOR"));
				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUM_ROWS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SAMPLE_SIZE"));
				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_ANALYZED"));
				pst_org.setString(rsGetCnt++, rs.getString("DEGREE"));
				pst_org.setString(rsGetCnt++, rs.getString("INSTANCES"));
				pst_org.setString(rsGetCnt++, rs.getString("PARTITIONED"));
				pst_org.setString(rsGetCnt++, rs.getString("TEMPORARY"));
				pst_org.setString(rsGetCnt++, rs.getString("GENERATED"));
				pst_org.setString(rsGetCnt++, rs.getString("SECONDARY"));
				pst_org.setString(rsGetCnt++, rs.getString("BUFFER_POOL"));
				pst_org.setString(rsGetCnt++, rs.getString("USER_STATS"));
				pst_org.setString(rsGetCnt++, rs.getString("DURATION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_DIRECT_ACCESS"));
				pst_org.setString(rsGetCnt++, rs.getString("ITYP_OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("ITYP_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("PARAMETERS"));
				pst_org.setString(rsGetCnt++, rs.getString("GLOBAL_STATS"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMIDX_STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMIDX_OPSTATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("FUNCIDX_STATUS"));


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
	 * DBC DBA_CONS_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONS_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                                 ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n    , OWNER                              ");
		sb.append("\n    , TABLE_NAME                        ");
		sb.append("\n    , CONSTRAINT_NAME                   ");
		sb.append("\n    , COLUMN_NAME AS CNST_COND_COL_NM   ");
		sb.append("\n    , COLUMN_NAME AS COL_NM             ");
		sb.append("\n    , POSITION                          ");
		sb.append("\n  FROM DBA_CONS_COLUMNS");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_CND_COL (");
		sb.append("\n   DB_NM                          ");
		sb.append("\n  , SCH_NM                          ");
		sb.append("\n  ,TBL_NM                     ");
		sb.append("\n  ,CNST_CND_NM                ");
		sb.append("\n  ,CNST_CND_COL_NM                    ");
		sb.append("\n  ,COL_NM                    ");
		sb.append("\n  ,SEQ                       ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?,?,?,?                   ");
		sb.append("\n)                                 ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CNST_COND_COL_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("COL_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("POSITION"));


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
	 * DBC DBA_CONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONSTRAINTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      ,  OWNER                       ");
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
		sb.append("\n      , INDEX_OWNER                 ");
		sb.append("\n      , INDEX_NAME                  ");
		sb.append("\n      , INVALID                     ");
		sb.append("\n      , VIEW_RELATED                ");
		sb.append("\n   FROM DBA_CONSTRAINTS             ");
		sb.append("\n WHERE OWNER = '").append(schemaName).append("'");

		//System.out.println(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		//System.out.println("insertDBA_CONSTRAINTS : \n"+sb);

		sb.append("\nINSERT INTO WAE_TIBERO_CND (");
		sb.append("\n       DB_NM                        ");
		sb.append("\n     ,  SCH_NM                        ");
		sb.append("\n      , TBL_NM                  ");
		sb.append("\n      , CNST_CND_NM             ");
		sb.append("\n      , CNST_TYPE             ");
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
		sb.append("\n      , INDEX_OWNER                 ");
		sb.append("\n      , INDEX_NAME                  ");
		sb.append("\n      , INVALID                     ");
		sb.append("\n      , VIEW_RELATED                ");
		sb.append("\n) VALUES (                       ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,           ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,           ");
		sb.append("\n  ?                             ");
		sb.append("\n)                                ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
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
	private int insertDBA_TAB_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		if(dbmsVersion.equals("")) {

			sb.append("\n SELECT --데이터베이스명                                             ");
			sb.append("\n      '").append(dbName).append("' AS OF_DATABASE ");
			sb.append("\n         ,A.OWNER                                                    ");
			sb.append("\n         ,A.TABLE_NAME                                               ");
			sb.append("\n         ,A.COLUMN_NAME                                              ");
			sb.append("\n         ,C.COMMENTS                                                 ");
			sb.append("\n         ,A.DATA_TYPE                                                ");
			sb.append("\n         ,'' as DATA_TYPE_MOD                                              ");
			sb.append("\n         ,'' as DATA_TYPE_OWNER                                            ");
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
//			sb.append("\n          ELSE A.DATA_SCALE END DATA_SCALE                           ");
			sb.append("\n         ,A.NULLABLE                                                 ");
			sb.append("\n         ,A.COLUMN_ID                                                ");
			sb.append("\n         ,0 as DEFAULT_LENGTH                                           ");
			sb.append("\n         ,A.DATA_DEFAULT                                             ");
			sb.append("\n         ,'' as NUM_DISTINCT                                               ");
			sb.append("\n         ,'' as LOW_VALUE                                                  ");
			sb.append("\n         ,'' as HIGH_VALUE                                                 ");
			sb.append("\n         ,'' as DENSITY                                                    ");
			sb.append("\n         ,'' as NUM_NULLS                                                  ");
			sb.append("\n         ,'' as NUM_BUCKETS                                                ");
			sb.append("\n         ,'' as LAST_ANALYZED                                              ");
			sb.append("\n         ,'' as SAMPLE_SIZE                                                ");
			sb.append("\n         ,'' as CHARACTER_SET_NAME                                         ");
			sb.append("\n         ,'' as CHAR_COL_DECL_LENGTH                                       ");
			sb.append("\n         ,'' as GLOBAL_STATS                                               ");
			sb.append("\n         ,'' as USER_STATS                                                 ");
			sb.append("\n         ,'' as AVG_COL_LEN                                                ");
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
			// 8i 이하에서는 아래 쿼리 사용
			sb.append("\nSELECT A.OWNER                            ");
			sb.append("\n      ,A.TABLE_NAME                       ");
			sb.append("\n      ,A.COLUMN_NAME                      ");
			sb.append("\n      ,A.DATA_TYPE                        ");
			sb.append("\n      ,A.DATA_TYPE_MOD                    ");
			sb.append("\n      ,A.DATA_TYPE_OWNER                  ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_LENGTH)  AS DATA_LENGTH ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_PRECISION)  AS DATA_PRECISION ");
			sb.append("\n      ,DECODE(A.DATA_TYPE,'CLOB',NULL, 'BLOB', NULL, 'TEXT', NULL, 'DATE', NULL, 'DATETIME', NULL, 'TIMESTAMP', NULL,A.DATA_SCALE)  AS DATA_SCALE ");
			sb.append("\n      ,A.NULLABLE                         ");
			sb.append("\n      ,A.COLUMN_ID                        ");
			sb.append("\n      ,A.DEFAULT_LENGTH                   ");
			sb.append("\n      ,A.DATA_DEFAULT                     ");
			sb.append("\n      , '' as NUM_DISTINCT                     ");
			sb.append("\n      , '' as LOW_VALUE                        ");
			sb.append("\n      , '' as HIGH_VALUE                       ");
			sb.append("\n      , '' as DENSITY                          ");
			sb.append("\n      , '' as NUM_NULLS                          ");
			sb.append("\n      , '' as NUM_BUCKETS                      ");
			sb.append("\n      , '' as LAST_ANALYZED                    ");
			sb.append("\n      , '' as SAMPLE_SIZE                      ");
			sb.append("\n      , '' as CHARACTER_SET_NAME               ");
			sb.append("\n      , '' as CHAR_COL_DECL_LENGTH             ");
			sb.append("\n      , '' as GLOBAL_STATS                     ");
			sb.append("\n      , '' as USER_STATS                         ");
			sb.append("\n      , '' as AVG_COL_LEN                      ");
			sb.append("\n      ,'").append(dbName).append("' AS OF_SYSTEM ");
			sb.append("\n      ,'").append(dbName).append("' AS OF_DATABASE ");
			sb.append("\n   FROM DBA_TAB_COLUMNS").append(" A ");
			sb.append("\n      , DBA_OBJECTS").append(" B ");
			sb.append("\n   WHERE A.OWNER = B.OWNER           ");
			sb.append("\n     AND A.TABLE_NAME = B.OBJECT_NAME");
			sb.append("\n     AND B.OBJECT_TYPE = 'TABLE'     ");
			sb.append("\n     AND B.OWNER ='").append(schemaName).append("'");

		}
		
		logger.debug("test sql:"+sb.toString());

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_COL (         ");
		sb.append("\n        DB_NM                        ");
		sb.append("\n        ,SCH_NM                     ");
		sb.append("\n        ,TBL_NM                ");
		sb.append("\n        ,COL_NM               ");
		sb.append("\n        ,COL_CMMT                  ");
		sb.append("\n        ,DATA_TYPE                 ");
		sb.append("\n        ,DATA_TYPE_MOD               ");
		sb.append("\n        ,DATA_TYPE_OWNER             ");
		sb.append("\n        ,DATA_LEN                 ");
		sb.append("\n        ,DATA_PNUM              ");

		sb.append("\n        ,DATA_PNT                  ");
		sb.append("\n        ,NULL_YN                  ");
		sb.append("\n        ,COL_ID                 ");
		sb.append("\n        ,DEFLT_LEN            ");
		sb.append("\n        ,DEFLT_VAL              ");
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
		sb.append("\n) VALUES (                                ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?                         ");
		sb.append("\n)                                         ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
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
	private int insertDBA_TABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT --데이터베이스명                "); // 데이터베이스명
		sb.append("\n      '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      , A.OWNER                           "); // 스키마명
		sb.append("\n      , A.TABLE_NAME                      "); // 테이블명
		sb.append("\n      , B.COMMENTS                        "); // 테이블한글명
		sb.append("\n      , '' as TABLESPACE_NAME                 "); // 테이블스페이스
		sb.append("\n      , '' as CLUSTER_NAME                    ");
		sb.append("\n      , '' as IOT_NAME                        ");
		sb.append("\n      , '' as PCT_FREE                        ");
		sb.append("\n      , '' as PCT_USED                        ");
		sb.append("\n      , '' as INI_TRANS                       ");
		sb.append("\n      , '' as MAX_TRANS                       ");
		sb.append("\n      , '' as INITIAL_EXTENT                  ");
		sb.append("\n      , '' as NEXT_EXTENT                     ");
		sb.append("\n      , '' as MIN_EXTENTS                     ");
		sb.append("\n      , '' as MAX_EXTENTS                     ");
		sb.append("\n      , '' as PCT_INCREASE                    ");
		sb.append("\n      , '' as FREELISTS                       ");
		sb.append("\n      , '' as FREELIST_GROUPS                 ");
		sb.append("\n      , '' as LOGGING                         ");
		sb.append("\n      , '' as BACKED_UP                       ");
		sb.append("\n      , '' as NUM_ROWS                        ");
		sb.append("\n      , '' as BLOCKS                          ");
		sb.append("\n      , '' as EMPTY_BLOCKS                    ");
		sb.append("\n      , '' as AVG_SPACE                       ");
		sb.append("\n      , '' as CHAIN_CNT                       ");
		sb.append("\n      , '' as AVG_ROW_LEN                     ");
		sb.append("\n      , '' as AVG_SPACE_FREELIST_BLOCKS       ");
		sb.append("\n      , '' as NUM_FREELIST_BLOCKS             ");
		sb.append("\n      , '' as DEGREE                          ");
		sb.append("\n      , '' as INSTANCES                       ");
		sb.append("\n      , '' as CACHE                           ");
		sb.append("\n      , '' as TABLE_LOCK                      ");
		sb.append("\n      , '' as SAMPLE_SIZE                     ");
		sb.append("\n      , TO_DATE(TO_CHAR(A.LAST_ANALYZED, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS LAST_ANALYZED ");
		sb.append("\n      , '' as PARTITIONED                     ");
		sb.append("\n      , '' as IOT_TYPE                        ");
		sb.append("\n      , '' as TEMPORARY                       ");
		sb.append("\n      , '' as SECONDARY                       ");
		sb.append("\n      , '' as NESTED                          ");
		sb.append("\n      , '' as BUFFER_POOL                     ");
		sb.append("\n      , '' as ROW_MOVEMENT                    ");
		sb.append("\n      , '' as GLOBAL_STATS                    ");
		sb.append("\n      , '' as USER_STATS                      ");
		sb.append("\n      , '' as DURATION                        ");
		sb.append("\n      , '' as SKIP_CORRUPT                    ");
		sb.append("\n      , '' as MONITORING                      ");
		sb.append("\n      , '' as CLUSTER_OWNER                   ");
		sb.append("\n      , TO_DATE(TO_CHAR(C.CREATED, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS CREATED "); //테이블생성 시간
		sb.append("\n      , TO_DATE(TO_CHAR(C.LAST_DDL_TIME, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS LAST_DDL_TIME "); //테이블변경 시간
		sb.append("\n   FROM DBA_TABLES A                      ");
		sb.append("\n  INNER JOIN DBA_TAB_COMMENTS B           ");
		sb.append("\n     ON A.TABLE_NAME = B.TABLE_NAME       ");
		sb.append("\n    AND A.OWNER = B.OWNER                 ");
		sb.append("\n    AND B.TABLE_TYPE = 'TABLE'            ");
		sb.append("\n   LEFT OUTER JOIN DBA_OBJECTS C  ");
		sb.append("\n     ON A.OWNER = C.OWNER   ");
		sb.append("\n    AND A.TABLE_NAME = C.OBJECT_NAME   ");
		sb.append("\n    AND C.OBJECT_TYPE = 'TABLE'   ");
		sb.append("\n WHERE A.OWNER = '").append(schemaName).append("'");

		System.out.println("insertDBA_TABLES : \n"+sb);
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_TIBERO_TBL (    ");
//		sb.append("\n        DB_NM                           "); // 데이터베이스명
//
//		sb.append("\n     ,   SCH_NM                          "); // 스키마명
//		sb.append("\n      , TBL_NM                          "); // 테이블명
//		sb.append("\n      , TBL_CMMT                        "); // 테이블한글명
//		sb.append("\n      , TBL_SPAC_NM                     "); // 테이블스페이스
		sb.append("\n      DB_NM                     "); // 테이블스페이스
		sb.append("\n      , SCH_NM                     "); // 테이블스페이스
		sb.append("\n      , TBL_NM                     "); // 테이블스페이스
		sb.append("\n      , TBL_CMMT                     "); // 테이블스페이스
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
		sb.append("\n      , CREATED           "); //테이블생성 시간
		sb.append("\n      , LAST_DDL_TIME     "); //테이블변경 시간
		sb.append("\n) VALUES (                              ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,                        ");
		sb.append("\n  ?,? "); // CREATED LAST_DDL_TIME
		sb.append("\n)                                       ");
//System.out.println(sb.toString());
		//System.out.println("단계:[1]\n" + sb.toString());

		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
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
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("LAST_ANALYZED"));
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
				
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("CREATED")); //테이블생성 시간
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("LAST_DDL_TIME")); //테이블변경 시간

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
	private int insertDBA_USERS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                               ");
		sb.append("\n --,데이터베이스명                    ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n     ,USERNAME                         ");
		sb.append("\n     ,USER_ID                         ");
		sb.append("\n     ,PASSWORD                        ");
		sb.append("\n     ,'' as ACCOUNT_STATUS                  ");
		sb.append("\n     ,'' as LOCK_DATE                       ");
		sb.append("\n     ,'' as EXPIRY_DATE                     ");
		sb.append("\n     ,'' as DEFAULT_TABLESPACE              ");
		sb.append("\n     ,'' as TEMPORARY_TABLESPACE            ");
		sb.append("\n     ,'' as CREATED                         ");
		sb.append("\n     ,'' as PROFILE                         ");
		sb.append("\n     ,'' as INITIAL_RSRC_CONSUMER_GROUP     ");
		sb.append("\n     ,'' as EXTERNAL_NAME                   ");
		sb.append("\n  FROM DBA_USERS                      ");
		sb.append("\n WHERE USERNAME = '").append(schemaName).append("'");
//logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\nINSERT INTO WAE_TIBERO_SCH (       ");
		sb.append("\n   DB_NM                       ");
		sb.append("\n     ,SCH_NM                         ");
		sb.append("\n     ,DB_SCH_ID                         ");
		sb.append("\n     ,PWD                        ");
		sb.append("\n     ,AC_STS                  ");
		sb.append("\n     ,LOCK_DTM                       ");
		sb.append("\n     ,EXP_DTM                     ");
		sb.append("\n     ,DEFLT_TBL_SPAC_NM              ");
		sb.append("\n     ,TEMP_TBL_SPAC_NM            ");
		sb.append("\n     ,CRT_DTM                         ");
		sb.append("\n     ,PROFILE                         ");
		sb.append("\n     ,INITIAL_RSRC_CONSUMER_GROUP     ");
		sb.append("\n     ,EXTERNAL_NAME                   ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,            ");
		sb.append("\n  ?,?,?                         ");
		sb.append("\n)                                 ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("USERNAME"));
				pst_org.setString(rsGetCnt++, schemaId);
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USER_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("PASSWORD"));
				pst_org.setString(rsGetCnt++, rs.getString("ACCOUNT_STATUS"));
				pst_org.setDate(rsGetCnt++, rs.getDate("LOCK_DATE"));
				pst_org.setDate(rsGetCnt++, rs.getDate("EXPIRY_DATE"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_TABLESPACE"));
				pst_org.setString(rsGetCnt++, rs.getString("TEMPORARY_TABLESPACE"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CREATED"));
				pst_org.setString(rsGetCnt++, rs.getString("PROFILE"));
				pst_org.setString(rsGetCnt++, rs.getString("INITIAL_RSRC_CONSUMER_GROUP"));
				pst_org.setString(rsGetCnt++, rs.getString("EXTERNAL_NAME"));

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
	 * DBC DBA_DATABASES 저장 : ORACLE TABLE SIZING을 위한 BLOCK SIZE 입력
	 * powered by javala 2008.11.20
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int updateMetaDATABASES_ORA_BLOCK_SIZE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n   SELECT TO_NUMBER(value) AS BLOCK_BYTE  FROM v$parameter  ");
		sb.append("\n   WHERE name = 'db_block_size'                            ");

		getResultSet(sb.toString());


		sb.setLength(0);


		int block_byte = 0;

		if( rs != null ) {
//			pst_org = con_org.prepareStatement(sb.toString());
//			pst_org.clearParameters();

			while(rs.next())
			{
				block_byte = rs.getInt("BLOCK_BYTE");
			}
		}

		sb.append("\n UPDATE WAE_TIBERO_DB ");
		sb.append("\n   SET  BLOCK_BYTE =  ").append(block_byte).append(" ");
		sb.append("\n  WHERE  TRIM(DB_NM)    = TRIM('").append(dbName).append("')");

		return setExecuteUpdate_Org(sb.toString());
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
		sb.append("\nINSERT INTO WAE_TIBERO_DB (");
		sb.append("\n   DB_NM                                            ");
		sb.append("\n  ,DB_CONN_TRG_ID                                   ");
		sb.append("\n  ,BLOCK_BYTE                                       ");
		sb.append("\n  ,STR_DTM                                          ");
		sb.append("\n  ,RESETLOGS_CHANGE                                 ");
		sb.append("\n  ,RESETLOGS_TIME                                   ");
		sb.append("\n  ,PRIOR_RESETLOGS_CHANGE                           ");
		sb.append("\n  ,PRIOR_RESETLOGS_TIME                             ");
		sb.append("\n  ,LOG_MODE                                         ");
		sb.append("\n  ,CHECKPOINT_CHANGE                                ");
		sb.append("\n  ,ARCHIVE_CHANGE                                   ");
		sb.append("\n  ,CONTROLFILE_TYPE                                 ");
		sb.append("\n  ,CONTROLFILE_CREATED                              ");
		sb.append("\n  ,CONTROLFILE_SEQUENCE                             ");
		sb.append("\n  ,CONTROLFILE_CHANGE                               ");
		sb.append("\n  ,CONTROLFILE_TIME                                 ");
		sb.append("\n  ,OPEN_RESETLOGS                                   ");
		sb.append("\n  ,VERSION_TIME                                     ");
		sb.append("\n  ,OPEN_MODE                                        ");
		sb.append("\n  ,PROTECTION_MODE                                  ");
		sb.append("\n  ,PROTECTION_LEVEL                                 ");
		sb.append("\n  ,REMOTE_ARCHIVE                                   ");
		sb.append("\n  ,ACTIVATION                                       ");
		sb.append("\n  ,DATABASE_ROLE                                    ");
		sb.append("\n  ,ARCHIVELOG_CHANGE                                ");
		sb.append("\n  ,SWITCHOVER_STATUS                                ");
		sb.append("\n  ,DATAGUARD_BROKER                                 ");
		sb.append("\n  ,GUARD_STATUS                                     ");
		sb.append("\n  ,SUPPLEMENTAL_LOG_DATA_MIN                        ");
		sb.append("\n  ,SUPPLEMENTAL_LOG_DATA_PK                         ");
		sb.append("\n  ,SUPPLEMENTAL_LOG_DATA_UI                         ");
		sb.append("\n  ,FORCE_LOGGING                                    ");
		sb.append("\n)                              ");
		sb.append("\n SELECT DISTINCT DB_CONN_TRG_PNM                                        ");
		sb.append("\n        ,A.DB_CONN_TRG_ID                                      ");
		sb.append("\n         ,'' AS BLOCK_BYTE                                     ");
		sb.append("\n         ,A.STR_DTM                                            ");
		sb.append("\n         ,'' AS RESETLOGS_CHANGE                               ");
		sb.append("\n         ,'' AS RESETLOGS_TIME                                 ");
		sb.append("\n         ,'' AS PRIOR_RESETLOGS_CHANGE                         ");
		sb.append("\n         ,'' AS PRIOR_RESETLOGS_TIME                           ");
		sb.append("\n         ,'' AS LOG_MODE                                       ");
		sb.append("\n         ,'' AS CHECKPOINT_CHANGE                              ");
		sb.append("\n         ,'' AS ARCHIVE_CHANGE                                 ");
		sb.append("\n         ,'' AS CONTROLFILE_TYPE                               ");
		sb.append("\n         ,'' AS CONTROLFILE_CREATED                            ");
		sb.append("\n         ,'' AS CONTROLFILE_SEQUENCE                           ");
		sb.append("\n         ,'' AS CONTROLFILE_CHANGE                             ");
		sb.append("\n         ,'' AS CONTROLFILE_TIME                               ");
		sb.append("\n         ,'' AS OPEN_RESETLOGS                                 ");
		sb.append("\n         ,'' AS VERSION_TIME                                   ");
		sb.append("\n         ,'' AS OPEN_MODE                                      ");
		sb.append("\n         ,'' AS PROTECTION_MODE                                ");
		sb.append("\n         ,'' AS PROTECTION_LEVEL                               ");
		sb.append("\n         ,'' AS REMOTE_ARCHIVE                                 ");
		sb.append("\n         ,'' AS ACTIVATION                                     ");
		sb.append("\n         ,'' AS DATABASE_ROLE                                  ");
		sb.append("\n         ,'' AS ARCHIVELOG_CHANGE                              ");
		sb.append("\n         ,'' AS SWITCHOVER_STATUS                              ");
		sb.append("\n         ,'' AS DATAGUARD_BROKER                               ");
		sb.append("\n         ,'' AS GUARD_STATUS                                   ");
		sb.append("\n         ,'' AS SUPPLEMENTAL_LOG_DATA_MIN                      ");
		sb.append("\n         ,'' AS SUPPLEMENTAL_LOG_DATA_PK                       ");
		sb.append("\n         ,'' AS SUPPLEMENTAL_LOG_DATA_UI                       ");
		sb.append("\n         ,'' AS FORCE_LOGGING                                  ");
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
		strSQL.append("\n  DELETE FROM WAE_TIBERO_DB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_DB : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_OBJ ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_OBJ : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_OBJECTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_OBJECTS : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_OBJ_SRC ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_OBJ_SRC : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_VIEW ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_VIEW : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_SEG ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_SEG : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_SCH ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_SCH : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_IDX ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_IDX : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_IDX_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
////		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
//
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_IDX_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_CND ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_CND : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_CND_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
////		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
//
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_CND_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_TBL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_TBL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TIBERO_TBL_SPAC ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TIBERO_TBL_SPAC : " + result);

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
//		cnt = insertwatidx();
//		logger.debug("insertwatidx : " + cnt + " OK!!");

		//WAT_INDEX_COLUMN ---------------------------------------------------
//		cnt = insertwatidxcol();
//		logger.debug("insertwatidxcol : " + cnt + " OK!!");

		//WAT_CONSTRAINT ---------------------------------------------------
//		cnt = insertwatcond();
//		logger.debug("insertwatcond : " + cnt + " OK!!");

		//WAT_CONSTRAINT_COLUMN ---------------------------------------------------
//		cnt = insertwatcondcol();
//		logger.debug("insertwatcondcol : " + cnt + " OK!!");

		//WAT_TABLESPACE ---------------------------------------------------
//		cnt = insertwatspac();
//		logger.debug("insertwatspac : " + cnt + " OK!!");

		result = true;

		return result;
	}

	/**  insomnia
	 * @throws Exception */
	private int insertwattbl() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL                                            ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                           ");
		sb.append("\n      , A.TBL_NM              AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TBL_CMMT            AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                   AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL               AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , A.TBL_SPAC_NM         AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                            ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                            ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS SUBJ_ID                               ");
		sb.append("\n      , E.COL_CNT             AS COL_EACNT                             ");
		sb.append("\n      , A.NUM_ROWS            AS ROW_EACNT                             ");
		sb.append("\n      , ''               AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''               AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , ''                    AS BF_COL_EACNT                          ");
		sb.append("\n      , ''                    AS BF_ROW_EACNT                          ");
		sb.append("\n      , ''                    AS BF_TBL_SIZE                           ");
		sb.append("\n      , ''                    AS BF_DATA_SIZE                          ");
		sb.append("\n      , ''                    AS BF_IDX_SIZE                           ");
		sb.append("\n      , ''                    AS BF_NUSE_SIZE                          ");
		sb.append("\n      , TO_DATE(TO_CHAR(LAST_ANALYZED, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS ANA_DTM -- 분석일자 ");    
		sb.append("\n      , TO_DATE(TO_CHAR(CREATED, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS CRT_DTM  --생성일자 ");
		sb.append("\n      , TO_DATE(TO_CHAR(LAST_DDL_TIME, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') AS CHG_DTM --변경일자 ");
		sb.append("\n      , ''                    AS PDM_DESCN                             ");
		sb.append("\n      , ''                    AS TBL_DQ_EXP_YN                         ");
		sb.append("\n      , ''                    AS DDL_TBL_ERR_EXS                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ERR_CD                        ");
		sb.append("\n      , ''                    AS DDL_TBL_ERR_DESCN                     ");
		sb.append("\n      , ''                    AS DDL_COL_ERR_EXS                       ");
		sb.append("\n      , ''                    AS DDL_COL_ERR_CD                        ");
		sb.append("\n      , ''                    AS DDL_COL_ERR_DESCN                     ");
		sb.append("\n      , ''                    AS PDM_TBL_ERR_EXS                       ");
		sb.append("\n      , ''                    AS PDM_TBL_ERR_CD                        ");
		sb.append("\n      , ''                    AS PDM_TBL_ERR_DESCN                     ");
		sb.append("\n      , ''                    AS PDM_COL_ERR_EXS                       ");
		sb.append("\n      , ''                    AS PDM_COL_ERR_CD                        ");
		sb.append("\n      , ''                    AS PDM_COL_ERR_DESCN                     ");
		sb.append("\n      , ''                    AS DDL_TBL_EXTNC_EXS                     ");
		sb.append("\n      , ''                    AS PDM_TBL_EXTNC_EXS                     ");
		sb.append("\n      , 'A'                    AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_TIBERO_TBL A                                                  ");
//		sb.append("\n  INNER JOIN WAE_TIBERO_OBJECTS O                                         ");
//		sb.append("\n     ON A.DB_NM = O.DB_NM                                              ");
//		sb.append("\n    AND A.SCH_NM = O.SCH_NM                                            ");
//		sb.append("\n    AND A.TBL_NM = O.OBJECT_NAME                                       ");
//		sb.append("\n    AND O.OBJECT_TYPE = 'TABLE'                                        ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                                              ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
//		sb.append("\n   LEFT OUTER JOIN WAE_TIBERO_SEG C                                       ");
//		sb.append("\n     ON A.DB_NM = C.DB_NM                                              ");
//		sb.append("\n    AND A.SCH_NM = C.SCH_NM                                            ");
//		sb.append("\n    AND A.TBL_NM = C.SGMT_NM                                           ");
//		sb.append("\n    AND C.SGMT_TYPE = 'TABLE'                                          ");
//		sb.append("\n   LEFT OUTER JOIN WAE_TIBERO_SEG D                                       ");
//		sb.append("\n     ON A.DB_NM = D.DB_NM                                              ");
//		sb.append("\n    AND A.SCH_NM = D.SCH_NM                                            ");
//		sb.append("\n    AND A.TBL_NM = D.SGMT_NM                                           ");
//		sb.append("\n    AND D.SGMT_TYPE IN ('INDEX', 'PARTTIONINDEX')                      ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TBL_NM, COUNT(*) AS COL_CNT  ");
		sb.append("\n                      FROM WAE_TIBERO_COL                                 ");
		sb.append("\n                     GROUP BY DB_NM, SCH_NM, TBL_NM                    ");
		sb.append("\n                   ) E                                                 ");
		sb.append("\n     ON A.DB_NM = E.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                            ");
		sb.append("\n    AND A.TBL_NM = E.TBL_NM                                            ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                        ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                                   ");
		sb.append("\n SELECT S.DB_SCH_ID                                ");
		sb.append("\n      , A.TBL_NM                                              ");
		sb.append("\n      , A.COL_NM                                              ");
		sb.append("\n      , COL_CMMT                                              ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                   ");
		sb.append("\n      , SYSDATE                                               ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , DATA_TYPE                                             ");
		sb.append("\n      , DATA_LEN                                              ");
		sb.append("\n      , DATA_PNUM                                             ");
		sb.append("\n      , DATA_PNT                                              ");
		sb.append("\n      , NULL_YN                                               ");
		sb.append("\n      , DEFLT_LEN                                             ");
		sb.append("\n      , TRIM(DEFLT_VAL)                                        ");
		sb.append("\n      , '' AS PK_YN              ");
		sb.append("\n      , A.COL_ID                                              ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n   FROM WAE_TIBERO_COL A                                         ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                                     ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                     ");
//		sb.append("\n    LEFT OUTER JOIN WAE_TIBERO_CND D ");
//		sb.append("\n      ON A.DB_NM = D.DB_NM ");
//		sb.append("\n     AND A.SCH_NM = D.SCH_NM ");
//		sb.append("\n     AND A.TBL_NM = D.TBL_NM ");
//		sb.append("\n     --AND C.CNST_CND_NM = D.CNST_CND_NM ");
//		sb.append("\n     AND D.CNST_TYPE = 'P' ");
//		sb.append("\n    LEFT OUTER JOIN WAE_TIBERO_CND_COL C ");
//		sb.append("\n      ON A.DB_NM = C.DB_NM ");
//		sb.append("\n     AND A.SCH_NM = C.SCH_NM ");
//		sb.append("\n     AND A.TBL_NM = C.TBL_NM ");
//		sb.append("\n     AND A.COL_NM = C.CNST_CND_COL_NM ");
//		sb.append("\n     AND C.CNST_CND_NM = D.CNST_CND_NM ");
//		sb.append("\n     AND C.SEQ IS NOT NULL ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");

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

	/**  insomnia
	 * @throws Exception */
	private int insertwatidx() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX                                     ");
		sb.append("\n SELECT S.DB_SCH_ID                                                    ");
		sb.append("\n      , A.TBL_NM                                                                  ");
		sb.append("\n      , A.IDX_NM                                                                  ");
		sb.append("\n      , S.DB_CONN_TRG_ID                                                          ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , '1'                                                                       ");
		sb.append("\n      , NULL                                                                       ");
		sb.append("\n      , SYSDATE                                                                   ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , A.TBL_SPAC_NM                                                             ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'P' THEN 'Y' ELSE 'N' END PK_YN                   ");
		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'U' THEN 'Y' ELSE 'N' END PK_YN                   ");
		sb.append("\n      , E.COL_CNT                                                                 ");
		sb.append("\n      , C.BYTES AS IDX_SIZE                                                       ");
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
		sb.append("\n   FROM WAE_TIBERO_IDX A                                                             ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                                                         ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                                         ");
		sb.append("\n   LEFT OUTER JOIN WAE_TIBERO_SEG C                                                  ");
		sb.append("\n     ON A.DB_NM = C.DB_NM                                                         ");
		sb.append("\n    AND A.SCH_NM = C.SCH_NM                                                       ");
		sb.append("\n    AND A.IDX_NM = C.SGMT_NM                                                      ");
		sb.append("\n    AND C.SGMT_TYPE = 'INDEX'                                                     ");
		sb.append("\n   LEFT OUTER JOIN WAE_TIBERO_CND D                                                  ");
		sb.append("\n     ON A.DB_NM = D.DB_NM                                                         ");
		sb.append("\n    AND A.SCH_NM = D.SCH_NM                                                       ");
		sb.append("\n    AND A.IDX_NM = D.CNST_CND_NM                                                  ");
		sb.append("\n   LEFT OUTER JOIN (                                                              ");
		sb.append("\n                     SELECT DB_NM, SCH_NM, TBL_NM, IDX_NM, COUNT(*) AS COL_CNT    ");
		sb.append("\n                       FROM WAE_TIBERO_IDX_COL                                       ");
		sb.append("\n                      GROUP BY DB_NM, SCH_NM, TBL_NM, IDX_NM                      ");
		sb.append("\n                   ) E                                                            ");
		sb.append("\n     ON A.DB_NM =  E.DB_NM                                                        ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                                       ");
		sb.append("\n    AND A.TBL_NM = E.TBL_NM                                                       ");
		sb.append("\n    AND A.IDX_NM = E.IDX_NM                                                       ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT S.DB_SCH_ID      ");
		sb.append("\n      , A.TBL_NM                    ");
		sb.append("\n      , A.COL_NM                    ");
		sb.append("\n      , A.IDX_NM                    ");
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
		sb.append("\n      , IDX_COL_ORD                 ");
		sb.append("\n      , SORT_TYPE                   ");
		sb.append("\n      , C.DATA_TYPE                 ");
		sb.append("\n      , C.DATA_PNUM                 ");
		sb.append("\n      , C.DATA_LEN                  ");
		sb.append("\n      , C.DATA_PNT                  ");
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
		sb.append("\n   FROM WAE_TIBERO_IDX_COL A           ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B           ");
		sb.append("\n     ON A.DB_NM = B.DB_NM           ");
		sb.append("\n  INNER JOIN WAE_TIBERO_COL C          ");
		sb.append("\n     ON A.DB_NM = C.DB_NM           ");
		sb.append("\n    AND A.SCH_NM = C.SCH_NM         ");
		sb.append("\n    AND A.TBL_NM = C.TBL_NM         ");
		sb.append("\n    AND A.COL_NM = C.COL_NM         ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcond() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_CND ");
		sb.append("\n SELECT S.DB_SCH_ID    ");
		sb.append("\n      , A.TBL_NM            ");
		sb.append("\n      , A.CNST_CND_NM   ");
		sb.append("\n      , C.COL_CNT  ");
		sb.append("\n      , '1'             ");
		sb.append("\n      , NULL             ");
		sb.append("\n      , SYSDATE     ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , CASE WHEN INDEX_NAME IS NULL THEN 'N' ELSE 'Y' END   ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n      , ''              ");
		sb.append("\n   FROM WAE_TIBERO_CND A   ");
		sb.append("\n  INNER JOIN (                 ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM          ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                  ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                   ");
		sb.append("\n  INNER JOIN (SELECT DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ");
		sb.append("\n                           , COUNT(CNST_CND_COL_NM) AS COL_CNT  ");
		sb.append("\n                    FROM WAE_TIBERO_CND_COL  ");
		sb.append("\n                   GROUP BY DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ) C ");
		sb.append("\n      ON A.DB_NM = C.DB_NM ");
		sb.append("\n     AND A.SCH_NM = C.SCH_NM ");
		sb.append("\n     AND A.TBL_NM = C.TBL_NM ");
		sb.append("\n     AND A.CNST_CND_NM = C.CNST_CND_NM ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		

		
		
		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcondcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_CND_COL                                 ");
		sb.append("\n SELECT S.DB_SCH_ID                              ");
		sb.append("\n      , TBL_NM                                               ");
		sb.append("\n      , CNST_CND_NM                                          ");
		sb.append("\n      , CNST_CND_COL_NM                                      ");
		sb.append("\n      , COL_NM                                               ");
		sb.append("\n      , '1'                                                  ");
		sb.append("\n      , NULL                                                  ");
		sb.append("\n      , SYSDATE                                              ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , SEQ                                                  ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n   FROM WAE_TIBERO_CND_COL A                                    ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                                    ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

	/**  insomnia */
	private int insertwatspac() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL_SPAC                                ");
		sb.append("\n SELECT B.DB_CONN_TRG_ID                                     ");
		sb.append("\n      , TBL_SPAC_NM                                          ");
		sb.append("\n      , '1'                                                  ");
		sb.append("\n      , NULL                                                  ");
		sb.append("\n      , SYSDATE                                              ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n      , ''                                                   ");
		sb.append("\n   FROM WAE_TIBERO_TBL_SPAC A                                   ");
		sb.append("\n  INNER JOIN WAE_TIBERO_DB B                                    ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

}
