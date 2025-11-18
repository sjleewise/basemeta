/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorMaria.java
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
 * 2. FileName  : CollectorMaria.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorMaria {

	private static final Logger logger = Logger.getLogger(CollectorMaria.class);

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
	private String dbms_id = null;	
	private int execCnt = 100; 
	private String data_capa = "";

	public CollectorMaria() {

	}

	public CollectorMaria(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
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
				//스키마 정보를 추가한다. DB인스턴스 별로 한번만 실행한다.
				cnt = insertMetaDATABASES();
				logger.debug(sp + (++p) + ". insertMetaDATABASES " + cnt + " OK!!");
				
				con_org.commit();
			}
			dbCnt++;
			
			cnt = changeUseDatabase();
			logger.debug(sp + (++p) + ". changeUseDatabase [infomation_schema] OK!!");
			
			data_capa = updateDataCpct();
			logger.debug(sp + (++p) + ". UPDATE_data_cpct " + data_capa + " OK!!");
			
			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");
			
			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");

			con_org.commit();
		}
		result = true;

		return result;
	}
	
	private int changeUseDatabase() throws SQLException {
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
	private int insertDBA_TABLES() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                   "); // 데이터베이스명
		sb.append("\n      '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      ,TABLE_CATALOG      ");
		sb.append("\n      ,TABLE_SCHEMA       ");
		sb.append("\n      ,TABLE_NAME         ");
		sb.append("\n      ,TABLE_TYPE         ");
		sb.append("\n      ,ENGINE             ");
		sb.append("\n      ,VERSION            ");
		sb.append("\n      ,ROW_FORMAT         ");
		sb.append("\n      ,TABLE_ROWS         ");
		sb.append("\n      ,ROUND(AVG_ROW_LENGTH, 2) AS AVG_ROW_LENGTH    ");	//20190516 값이 22자리 이상으로 인해 오류 발생.
		sb.append("\n      ,DATA_LENGTH        ");
		sb.append("\n      ,MAX_DATA_LENGTH    ");
		sb.append("\n      ,INDEX_LENGTH       ");
		sb.append("\n      ,DATA_FREE          ");
		sb.append("\n      ,AUTO_INCREMENT     ");
		sb.append("\n      ,CREATE_TIME        ");
		sb.append("\n      ,UPDATE_TIME        ");
		sb.append("\n      ,CHECK_TIME         ");
		sb.append("\n      ,TABLE_COLLATION    ");
		sb.append("\n      ,CHECKSUM           ");
		sb.append("\n      ,CREATE_OPTIONS     ");
		sb.append("\n      ,TABLE_COMMENT      ");
		sb.append("\n   FROM TABLES A          ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MARIA_TBL ( ");
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
		sb.append("\n  ?,?,?,?                   ");
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
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("ENGINE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("VERSION"));
				pst_org.setString(rsGetCnt++, rs.getString("ROW_FORMAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ROWS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AVG_ROW_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEX_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_FREE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("AUTO_INCREMENT"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CREATE_TIME"));
				pst_org.setDate(rsGetCnt++, rs.getDate("UPDATE_TIME"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CHECK_TIME"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_COLLATION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHECKSUM"));
				pst_org.setString(rsGetCnt++, rs.getString("CREATE_OPTIONS"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_COMMENT"));
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
	
	/**
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MARIA_DB (");
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
	
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MARIA_DB    ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MARIA_DB : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MARIA_COL   ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MARIA_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MARIA_TBL   ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MARIA_TBL : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MARIA_INDEX ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MARIA_INDEX : " + result);
		
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
		
//		cnt = updateTblCnt();
//		logger.debug("updateTblCnt " + dbName + cnt + " OK!!");
		result = true;

		return result;
	}

	private int insertwattbl() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL                                            ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                           ");
		sb.append("\n      , A.TABLE_NAME          AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TABLE_COMMENT       AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                  AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                            ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                            ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS SUBJ_ID                               ");
		sb.append("\n      , E.COL_CNT             AS COL_EACNT                             ");
		sb.append("\n      , A.TABLE_ROWS          AS ROW_EACNT                             ");
		sb.append("\n      , ''                    AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''                    AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , ''                    AS BF_COL_EACNT                          ");
		sb.append("\n      , ''                    AS BF_ROW_EACNT                          ");
		sb.append("\n      , ''                    AS BF_TBL_SIZE                           ");
		sb.append("\n      , ''                    AS BF_DATA_SIZE                          ");
		sb.append("\n      , ''                    AS BF_IDX_SIZE                           ");
		sb.append("\n      , ''                    AS BF_NUSE_SIZE                          ");
		sb.append("\n      , ''                    AS ANA_DTM                               ");
		sb.append("\n      , ''                    AS CRT_DTM                               ");
		sb.append("\n      , ''                    AS CHG_DTM                               ");
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
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_MARIA_TBL A                                                ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MARIA_DB B                                              ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TABLE_NAME, COUNT(*) AS COL_CNT  ");
		sb.append("\n                      FROM WAE_MARIA_COL                                 ");
		sb.append("\n                     GROUP BY DB_NM, SCH_NM, TABLE_NAME                    ");
		sb.append("\n                   ) E                                                 ");
		sb.append("\n     ON A.DB_NM = E.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                            ");
		sb.append("\n    AND A.TABLE_NAME = E.TABLE_NAME                                            ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                        ");

		return setExecuteUpdate_Org(sb.toString());

	}
	
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                                      ");
		sb.append("\n SELECT S.DB_SCH_ID                                           ");
		sb.append("\n      , A.TABLE_NAME                                          ");
		sb.append("\n      , A.COLUMN_NAME                                         ");
		sb.append("\n      , A.COLUMN_COMMENT                                      ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                  ");
		sb.append("\n      , SYSDATE                                               ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    "); //10
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , DATA_TYPE                                             ");
		sb.append("\n      , CHARACTER_MAXIMUM_LENGTH                              ");
		sb.append("\n      , NUMERIC_PRECISION                                     ");
		sb.append("\n      , NUMERIC_SCALE                                         ");
		sb.append("\n      , DECODE(A.IS_NULLABLE, 'YES', 'Y', 'N')                ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , TRIM(COLUMN_DEFAULT)                                  ");
		sb.append("\n      , DECODE(A.COLUMN_KEY, 'PRI', 'Y', 'N') AS PK_YN        "); //20
		sb.append("\n      , A.ORDINAL_POSITION                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    "); //30
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    "); //40
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    "); //48
		sb.append("\n   FROM WAE_MARIA_COL A                                       ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MARIA_DB B                                     ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                     ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");

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
		sb.append("\n SELECT                                           ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      ,A.TABLE_SCHEMA                             ");
		sb.append("\n	   ,A.TABLE_CATALOG                            ");
		sb.append("\n      ,A.TABLE_SCHEMA                             ");
		sb.append("\n      ,A.TABLE_NAME                               ");
		sb.append("\n      ,A.COLUMN_NAME                              ");
		sb.append("\n      ,A.ORDINAL_POSITION                         ");
		sb.append("\n      ,A.COLUMN_DEFAULT                           ");
		sb.append("\n      ,A.IS_NULLABLE                              ");
		sb.append("\n      ,A.DATA_TYPE                                ");
		sb.append("\n      ,A.CHARACTER_MAXIMUM_LENGTH                 ");
		sb.append("\n      ,A.CHARACTER_OCTET_LENGTH                   ");
		sb.append("\n      ,A.NUMERIC_PRECISION                        ");
		sb.append("\n      ,A.NUMERIC_SCALE                            ");
		sb.append("\n      ,A.CHARACTER_SET_NAME                       ");
		sb.append("\n      ,A.COLLATION_NAME                           ");
		sb.append("\n      ,A.COLUMN_TYPE                              ");
		sb.append("\n      ,A.COLUMN_KEY                               ");
		sb.append("\n      ,A.EXTRA                                    ");
		sb.append("\n      ,A.PRIVILEGES                               ");
		sb.append("\n      ,A.COLUMN_COMMENT                           ");
		sb.append("\n   FROM COLUMNS A                                 ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
logger.debug(sb.toString());		
		getResultSet(sb.toString());

		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MARIA_COL (       ");
		sb.append("\n        DB_NM                     ");
		sb.append("\n      ,SCH_NM                     ");
		sb.append("\n      ,TABLE_CATALOG              ");
		sb.append("\n      ,TABLE_SCHEMA               ");
		sb.append("\n      ,TABLE_NAME                 ");
		sb.append("\n      ,COLUMN_NAME                ");
		sb.append("\n      ,ORDINAL_POSITION           ");
		sb.append("\n      ,COLUMN_DEFAULT             ");
		sb.append("\n      ,IS_NULLABLE                ");
		sb.append("\n      ,DATA_TYPE                  ");
		sb.append("\n      ,CHARACTER_MAXIMUM_LENGTH   ");
		sb.append("\n      ,CHARACTER_OCTET_LENGTH     ");
		sb.append("\n      ,NUMERIC_PRECISION          ");
		sb.append("\n      ,NUMERIC_SCALE              ");
		sb.append("\n      ,CHARACTER_SET_NAME         ");
		sb.append("\n      ,COLLATION_NAME             ");
		sb.append("\n      ,COLUMN_TYPE                ");
		sb.append("\n      ,COLUMN_KEY                 ");
		sb.append("\n      ,EXTRA                      ");
		sb.append("\n      ,PRIVILEGES_TEXT            ");
		sb.append("\n      ,COLUMN_COMMENT             ");
		sb.append("\n      , DB_CONN_TRG_ID            ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,            ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,            ");
		sb.append("\n  ?,?			                   ");
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
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ORDINAL_POSITION"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_DEFAULT"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHARACTER_MAXIMUM_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CHARACTER_OCTET_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUMERIC_PRECISION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUMERIC_SCALE"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_KEY"));
				pst_org.setString(rsGetCnt++, rs.getString("EXTRA"));
				pst_org.setString(rsGetCnt++, rs.getString("PRIVILEGES"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_COMMENT"));
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
		tgtSQL = tgtSQL.append("select ROUND(sum((data_length+index_length)/1024/1204),2) MB from information_schema.tables where table_schema = '"+schemaName+"'");
		rs = getResultSet(tgtSQL.toString());
		
		if(rs != null){
			rs.next();
			data_capa = rs.getString(1);
			
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET DATA_CAPA = (        ");
			strSQL.append(rs.getString(1));
			strSQL.append("\n     )   						    ");
			strSQL.append("\n      	WHERE 1=1       ");
			strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
			strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
			strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbms_id+"'     ");
			setExecuteUpdate_Org(strSQL.toString());
		}
						
		return data_capa;
	}
}
