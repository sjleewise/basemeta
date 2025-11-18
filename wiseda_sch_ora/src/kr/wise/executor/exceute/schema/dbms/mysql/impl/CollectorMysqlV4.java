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
package kr.wise.executor.exceute.schema.dbms.mysql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.mysql.CollectorMysqlService;
import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorMysqlV4.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms.mysql.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorMysqlV4 implements CollectorMysqlService {

	private static final Logger logger = Logger.getLogger(CollectorMysqlV4.class);

	private Connection con_org = null;
	private Connection con_tgt = null;
	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;
	private PreparedStatement pst_tgt2 = null;		
	private ResultSet rs = null;
	private ResultSet rs2 = null;

	private List<TargetDbmsDM> targetDblist;
	private String dbName = null;
	private String schemaName = null;
	private String schemaId = null;
	private String dbms_id = null;
	private String dbType = null;
	private int execCnt = 100; 
	
	public CollectorMysqlV4() {

	}

	public CollectorMysqlV4(Connection source, Connection target, List<TargetDbmsDM> lsitdm) {
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
			
			cnt = changeUseDatabase();
			logger.debug(sp + (++p) + ". changeUseDatabase ["+schemaName+"] OK!!");
		
			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");
			
			cnt = updateDataCpct();
			logger.debug(sp + (++p) + ". updateDataCpct " + dbName + " OK!!");
			
			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");
					
			con_org.commit();
		}
		result = true;

		return result;
	}
	
	private int changeUseDatabase() throws SQLException {
		String query_tgt = "";
		query_tgt = "\n use "+schemaName;
		pst_tgt = con_tgt.prepareStatement(query_tgt);

		return pst_tgt.executeUpdate();
	}
	
	/**
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException {
		StringBuffer query_tgt = new StringBuffer();
		query_tgt = query_tgt.append("show table status from "+schemaName);
		getResultSet(query_tgt.toString());
		
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MYSQL_TBL ( ");
		sb.append("\n       DB_NM                "); 
		sb.append("\n      ,SCH_NM               "); 
		sb.append("\n      ,TABLE_CATALOG        ");
		sb.append("\n      ,TABLE_SCHEMA         ");
		sb.append("\n      ,TABLE_NAME           ");
		sb.append("\n      ,TABLE_TYPE           ");
		sb.append("\n      ,ENGINE               ");
		sb.append("\n      ,VERSION              ");
		sb.append("\n      ,ROW_FORMAT           ");
		sb.append("\n      ,TABLE_ROWS           ");
		sb.append("\n      ,AVG_ROW_LENGTH       ");
		sb.append("\n      ,DATA_LENGTH          ");
		sb.append("\n      ,MAX_DATA_LENGTH      ");
		sb.append("\n      ,INDEX_LENGTH         ");
		sb.append("\n      ,DATA_FREE            ");
		sb.append("\n      ,AUTO_INCREMENT       ");
		sb.append("\n      ,CREATE_TIME          ");
		sb.append("\n      ,UPDATE_TIME          ");
		sb.append("\n      ,CHECK_TIME           ");
		sb.append("\n      ,TABLE_COLLATION      ");
		sb.append("\n      ,CHECKSUM             ");
		sb.append("\n      ,CREATE_OPTIONS       ");
		sb.append("\n      ,TABLE_COMMENT        ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n) VALUES (                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,      ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,      ");
		sb.append("\n  ?,?,?,?                     ");
		sb.append("\n)                           ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, "def");
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, "NULL");
				pst_org.setString(rsGetCnt++, rs.getString("ENGINE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("VERSION"));
				pst_org.setString(rsGetCnt++, rs.getString("ROW_FORMAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROWS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_ROW_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEX_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_FREE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AUTO_INCREMENT"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CREATE_TIME"));
				pst_org.setDate(rsGetCnt++, rs.getDate("UPDATE_TIME"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CHECK_TIME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHECKSUM"));
				pst_org.setString(rsGetCnt++, rs.getString("CREATE_OPTIONS"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));
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
	
	private ResultSet getResultSet2(String query_tgt) throws SQLException {
		pst_tgt2 = null;
		rs2 = null;
		pst_tgt2 = con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		pst_tgt2.setFetchSize(1000);
		rs2 = pst_tgt2.executeQuery();

		return rs2;
	}
	
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MYSQL_COL   ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MYSQL_TBL   ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_TBL : " + result);
		
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
		sb.append("\n      , DBMS_TYP_CD                           ");
		sb.append("\n      , COL_EACNT                             ");
		sb.append("\n      , ROW_EACNT                             ");
		sb.append("\n      , TBL_SIZE                              ");
		sb.append("\n      , DATA_SIZE                             ");
		sb.append("\n      , IDX_SIZE                              ");
		sb.append("\n      , NUSE_SIZE                             ");
		sb.append("\n      , TBL_ANA_DTM                           ");
		sb.append("\n      , TBL_CRT_DTM                           ");
		sb.append("\n      , TBL_CHG_DTM                           ");
		sb.append("\n      , TBL_CLLT_DCD                          ");
		sb.append("\n )                                            ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.TABLE_NAME          AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TABLE_COMMENT       AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS OBJ_VERS                              ");
		sb.append("\n      , NULL                  AS REG_TYP                               ");
		sb.append("\n      , SYSDATE           AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYP_CD                           ");
		sb.append("\n      , E.COL_CNT             AS COL_EACNT                             ");
		sb.append("\n      , A.TABLE_ROWS          AS ROW_EACNT                             ");
		sb.append("\n      , ''                    AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''                    AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , NULL                  AS ANA_DTM                               ");
		sb.append("\n      , A.CREATE_TIME         AS CRT_DTM                               ");
		sb.append("\n      , NULL                  AS CHG_DTM                               ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_MYSQL_TBL A                                                ");
		sb.append("\n  INNER JOIN (                                                         ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                                                                  ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                                    ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                                        ");
		sb.append("\n    AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                            ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TABLE_NAME, COUNT(*) AS COL_CNT, DB_CONN_TRG_ID  ");
		sb.append("\n                      FROM WAE_MYSQL_COL                               ");
		sb.append("\n                     GROUP BY DB_NM, SCH_NM, TABLE_NAME                ");
		sb.append("\n                   ) E                                                 ");
		sb.append("\n     ON A.DB_NM = E.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                            ");
		sb.append("\n    AND A.TABLE_NAME = E.TABLE_NAME                                    ");
		sb.append("\n    AND A.DB_CONN_TRG_ID = E.DB_CONN_TRG_ID               ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                       ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.TABLE_NAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)													");

		return setExecuteUpdate_Org(sb.toString());
	}

	private int insertwatcol() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL (                                    	");
		sb.append("\n        DB_SCH_ID                                             	");
		sb.append("\n      , DBC_TBL_NM                                            	");
		sb.append("\n      , DBC_COL_NM                                            	");
		sb.append("\n      , DBC_COL_KOR_NM                                        	");
		sb.append("\n      , OBJ_VERS                                              	");
		sb.append("\n      , REG_TYP_CD                                            	");
		sb.append("\n      , REG_DTM                                               	");
		sb.append("\n      , UPD_DTM                                               	");
		sb.append("\n      , DATA_TYPE                                             	");
		sb.append("\n      , DATA_LEN                                              	");
		sb.append("\n      , DATA_PNUM                                             	");
		sb.append("\n      , DATA_PNT                                              	");
		sb.append("\n      , NULL_YN                                               	");
		sb.append("\n      , DEFLT_LEN                                             	");
		sb.append("\n      , DEFLT_VAL                                             	");
		sb.append("\n      , PK_YN                                                 	");
		sb.append("\n      , FK_YN                                                 	");
		sb.append("\n      , ORD                                                   	");
		sb.append("\n      , PK_ORD                                                	");
		sb.append("\n      , COL_DESCN                                             	");
		sb.append("\n )                                                            	");
		sb.append("\n SELECT S.DB_SCH_ID   								AS DB_SCH_ID		");
		sb.append("\n      , A.TABLE_NAME  								AS DBC_TBL_NM		");
		sb.append("\n      , A.COLUMN_NAME  							AS DBC_COL_NM 		");
		sb.append("\n      , A.COLUMN_COMMENT   						AS DBC_COL_KOR_NM   ");
		sb.append("\n      , '1'                						AS OBJ_VERS         ");
		sb.append("\n      , NULL               						AS REG_TYP_CD      	");
		sb.append("\n      , SYSDATE        						AS REG_DTM       	");
		sb.append("\n      , NULL               						AS UPD_DTM          ");
		sb.append("\n      , DATA_TYPE          						AS DATA_TYPE        ");
		sb.append("\n      , CHARACTER_MAXIMUM_LENGTH 					AS DATA_LEN         ");
		sb.append("\n      , NUMERIC_PRECISION        					AS DATA_PNUM        ");
		sb.append("\n      , NUMERIC_SCALE            					AS DATA_PNT         ");
		sb.append("\n      , DECODE(A.IS_NULLABLE, 'YES', 'Y', 'N') 	AS NULL_YN          ");
		sb.append("\n      , ''                                     	AS DEFLT_LEN        ");
		sb.append("\n      , TRIM(COLUMN_DEFAULT)                   	AS DEFLT_VAL        ");
		sb.append("\n      , DECODE(A.COLUMN_KEY, 'PRI', 'Y', 'N') 		AS PK_YN        	");
		sb.append("\n      , ''											AS FK_YN        	");
		sb.append("\n      , A.ORDINAL_POSITION                    		AS ORD              ");
		sb.append("\n      , NULL                                  		AS PK_ORD           ");
		sb.append("\n      , ''                                    		AS COL_DESCN        ");
		sb.append("\n   FROM WAE_MYSQL_COL A                                       			");
		sb.append("\n  INNER JOIN (                                                			");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                                                         			");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                          			");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                               			");
		sb.append("\n    AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                   			");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                              			");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.TABLE_NAME NOT IN ( 	");
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
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		StringBuffer query_tgt = new StringBuffer();
		query_tgt = query_tgt.append("show tables");
		getResultSet2(query_tgt.toString());
		
		int cnt = 0;
		if(rs2 != null){
			while(rs2.next()){
				sb = new StringBuffer();
				sb.append("\nINSERT INTO WAE_MYSQL_COL (       ");
				sb.append("\n        DB_NM                     ");
				sb.append("\n      ,SCH_NM                     ");
				sb.append("\n      ,TABLE_CATALOG              ");
				sb.append("\n      ,TABLE_SCHEMA               ");
				sb.append("\n      ,TABLE_NAME                 ");
				sb.append("\n      ,COLUMN_NAME                "); //1
				sb.append("\n      ,COLUMN_DEFAULT             "); //6
				sb.append("\n      ,IS_NULLABLE                "); //4
				sb.append("\n      ,DATA_TYPE                  "); //2
				sb.append("\n      ,COLLATION_NAME             "); //3
				sb.append("\n      ,COLUMN_KEY                 "); //5
				sb.append("\n      ,EXTRA                      "); //7
				sb.append("\n      ,PRIVILEGES_TEXT            "); //8
				sb.append("\n      ,COLUMN_COMMENT             "); //9
				sb.append("\n      , DB_CONN_TRG_ID                  ");
				sb.append("\n) VALUES (                        ");
				sb.append("\n  ?,?,?,?,?,?,?,?,?,?,            ");
				sb.append("\n  ?,?,?,?,?                       ");
				sb.append("\n)                                 ");
		
				String tableNm = rs2.getString(1);
				StringBuffer query_tgt_col = new StringBuffer();
				query_tgt_col.append("show full columns from "+tableNm);
				getResultSet(query_tgt_col.toString());
				
				if( rs != null ) {
					pst_org = con_org.prepareStatement(sb.toString());
					pst_org.clearParameters();
		
					int rsCnt = 1;
					int rsGetCnt = 1;
																		
					while(rs.next()) {
						pst_org.setString(rsGetCnt++, dbName);
						pst_org.setString(rsGetCnt++, schemaName);
						pst_org.setString(rsGetCnt++, "def");
						pst_org.setString(rsGetCnt++, schemaName);
						pst_org.setString(rsGetCnt++, tableNm);
						pst_org.setString(rsGetCnt++, rs.getString(1));
						pst_org.setString(rsGetCnt++, rs.getString(6));
						pst_org.setString(rsGetCnt++, rs.getString(4));
						pst_org.setString(rsGetCnt++, rs.getString(2));
						pst_org.setString(rsGetCnt++, rs.getString(3));
						pst_org.setString(rsGetCnt++, rs.getString(5));
						pst_org.setString(rsGetCnt++, rs.getString(7));
						pst_org.setString(rsGetCnt++, rs.getString(8));
						pst_org.setString(rsGetCnt++, rs.getString(9));
						pst_org.setString(rsGetCnt++, dbms_id);
						pst_org.executeUpdate();
						
						if(rsCnt == execCnt) {
							executeBatch(true);
							rsCnt = 0;
						}
						rsCnt++;
						rsGetCnt = 1;
						cnt++;
					}
				}
			}
			executeBatch();
			
			if(pst_tgt != null) pst_tgt.close();
			if(pst_tgt2 != null) pst_tgt2.close();
			if(pst_org != null) pst_org.close();
			if(rs != null) rs.close();
			if(rs2 != null) rs2.close();
		}
		
		return cnt;
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
	
	private int updateDataCpct() throws SQLException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET data_cpct = (        ");
		strSQL.append("\n		SELECT FORMAT(SUM((data_length+index_length)/1024/1024),2) AS MB FROM wae_mysql_tbl WHERE sch_nm = '"+schemaName+"' ");
		strSQL.append("\n     )   						    ");
		strSQL.append("\n      	WHERE 1=1       ");
		strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
		strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
		strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbms_id+"'     ");
				
		return setExecuteUpdate_Org(strSQL.toString());
	}
}
