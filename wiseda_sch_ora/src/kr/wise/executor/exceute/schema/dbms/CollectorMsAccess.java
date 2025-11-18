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
 * 2. FileName  : CollectorMsAccess.java
 * 3. Package  : kr.wise.executor.exceute.schema.dbms
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorMsAccess {

	private static final Logger logger = Logger.getLogger(CollectorMsAccess.class);

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

	public CollectorMsAccess() {

	}

	public CollectorMsAccess(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
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

			cnt = insertDBA_TABLES();
			logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");

			cnt = insertDBA_TAB_COLUMNS();
			logger.debug(sp + (++p) + ". insertDBA_TAB_COLUMNS " + cnt + " OK!!");

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
		String[] arrTypes = {"TABLE"};
		rs = con_tgt.getMetaData().getTables(null, null, "%", arrTypes); 
			
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MSA_TBL (        ");
		sb.append("\n        DB_NM                     "); 
		sb.append("\n      , SCH_NM                    ");
		sb.append("\n      , TABLE_SCHEM               ");
		sb.append("\n      , TABLE_NAME                ");
		sb.append("\n      , TABLE_TYPE                "); //5
		sb.append("\n      , REMARKS                   ");
		sb.append("\n      , TYPE_CAT                  ");
		sb.append("\n      , TYPE_SCHEM                ");
		sb.append("\n      , TYPE_NAME                 ");
		sb.append("\n      , SELF_REFERENCING_COL_NAME "); //10
		sb.append("\n      , REF_GENERATION            ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                       ");
		sb.append("\n     ?, ?, ?, ?, ?                ");
		sb.append("\n   , ?, ?, ?, ?, ?                ");
		sb.append("\n   , ?, ?                         ");
		sb.append("\n )                                ");
		pst_org = con_org.prepareStatement(sb.toString());
		pst_org.clearParameters();

		int rsCnt = 1;
		int rsGetCnt = 1;
		
		while (rs.next()) {
			pst_org.setString(rsGetCnt++, dbName);
			pst_org.setString(rsGetCnt++, schemaName);
			pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEM"));
			pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
			pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
			pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));
			pst_org.setString(rsGetCnt++, rs.getString("TYPE_CAT"));
			pst_org.setString(rsGetCnt++, rs.getString("TYPE_SCHEM"));
			pst_org.setString(rsGetCnt++, rs.getString("TYPE_NAME"));
			pst_org.setString(rsGetCnt++, rs.getString("SELF_REFERENCING_COL_NAME"));
			pst_org.setString(rsGetCnt++, rs.getString("REF_GENERATION"));
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

		return cnt;
	}	
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException	{
		rs = con_tgt.getMetaData().getColumns(null, null, "%", null);
			
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MSA_COL ( ");
		sb.append("\n    DB_NM                  ");
		sb.append("\n  , SCH_NM                 "); 
		sb.append("\n  , TABLE_CAT              ");
		sb.append("\n  , TABLE_SCHEM            ");
		sb.append("\n  , TABLE_NAME             "); //5
		sb.append("\n  , COLUMN_NAME            ");
		sb.append("\n  , DATA_TYPE              ");
		sb.append("\n  , TYPE_NAME              ");
		sb.append("\n  , COLUMN_SIZE            ");
		sb.append("\n  , BUFFER_LENGTH          "); //10
		sb.append("\n  , DECIMAL_DIGITS         ");
		sb.append("\n  , NUM_PREC_RADIX         ");
		sb.append("\n  , NULLABLE               ");
		sb.append("\n  , REMARKS                ");
		sb.append("\n  , COLUMN_DEF             "); //15
		sb.append("\n  , SQL_DATA_TYPE          ");
		sb.append("\n  , SQL_DATETIME_SUB       ");
		sb.append("\n  , CHAR_OCTET_LENGTH      ");
		sb.append("\n  , ORDINAL_POSITION       ");
		sb.append("\n  , IS_NULLABLE            "); //20
		sb.append("\n  , SCOPE_CATALOG          ");
		sb.append("\n  , SCOPE_SCHEMA           ");
		sb.append("\n  , SCOPE_TABLE            ");
		sb.append("\n  , SOURCE_DATA_TYPE       ");
		sb.append("\n  , IS_AUTOINCREMENT       "); //25
		sb.append("\n  , IS_GENERATEDCOLUMN     ");
		sb.append("\n      , DB_CONN_TRG_ID                  ");
		sb.append("\n ) VALUES (                ");
		sb.append("\n    ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?                   ");
		sb.append("\n )                         ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CAT"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEM"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));  //5
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_SIZE"));
				pst_org.setString(rsGetCnt++, rs.getString("BUFFER_LENGTH")); //10
				pst_org.setString(rsGetCnt++, rs.getString("DECIMAL_DIGITS"));
				pst_org.setString(rsGetCnt++, rs.getString("NUM_PREC_RADIX"));
				pst_org.setString(rsGetCnt++, rs.getString("NULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_DEF")); //15
				pst_org.setString(rsGetCnt++, rs.getString("SQL_DATA_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("SQL_DATETIME_SUB"));
				pst_org.setString(rsGetCnt++, rs.getString("CHAR_OCTET_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("ORDINAL_POSITION"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));  //20
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("SCOPE_TABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("SOURCE_DATA_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_AUTOINCREMENT")); //25
				pst_org.setString(rsGetCnt++, rs.getString("IS_GENERATEDCOLUMN"));
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

	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MSA_TBL     ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MSA_COL     ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbms_id+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		
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
		sb.append("\n )                                                                     ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.TABLE_NAME          AS DBC_TBL_NM                            ");
		sb.append("\n      , A.REMARKS             AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS OBJ_VERS                              ");
		sb.append("\n      , NULL                  AS REG_TYP_CD                            ");
		sb.append("\n      , SYSDATE           AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , '" + dbType + "'      AS DBMS_TYP_CD                           ");
		sb.append("\n      , 0                     AS COL_EACNT                             ");
		sb.append("\n      , 0                     AS ROW_EACNT                             ");
		sb.append("\n      , 0                     AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , 0                     AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , NULL                  AS TBL_ANA_DTM                           ");
		sb.append("\n      , NULL                  AS TBL_CRT_DTM                           ");
		sb.append("\n      , NULL                  AS TBL_CHG_DTM                           ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_MSA_TBL A                                                  ");
		sb.append("\n        INNER JOIN (                                       ");
		sb.append(             getdbconnsql());
		sb.append("\n        ) S                                                ");
		sb.append("\n         ON A.DB_NM  = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n        AND A.SCH_NM = S.DB_SCH_PNM                        ");
		sb.append("\n        AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID            ");
		sb.append("\n  WHERE A.DB_CONN_TRG_ID = '"+ dbms_id +"'                           ");
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
		sb.append("\n INSERT INTO WAT_DBC_COL ( ");
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
		sb.append("\n      , ORD                                                   ");
		sb.append("\n      , PK_ORD                                                ");
		sb.append("\n      , COL_DESCN                                             ");
		sb.append("\n )                         "); 
		sb.append("\n SELECT S.DB_SCH_ID                     AS DB_SCH_ID                                  ");
		sb.append("\n      , A.TABLE_NAME                    AS DBC_TBL_NM                                 ");
		sb.append("\n      , A.COLUMN_NAME                   AS DBC_COL_NM                                 ");
		sb.append("\n      , A.REMARKS                       AS DBC_COL_KOR_NM                             ");
		sb.append("\n      , '1'                             AS OBJ_VERS                                   ");
		sb.append("\n      , NULL                            AS REG_TYP_CD                                 ");
		sb.append("\n      , SYSDATE                     AS REG_DTM                                    ");
		sb.append("\n      , NULL                            AS UPD_DTM                                    ");
		sb.append("\n      , A.TYPE_NAME                     AS DATA_TYPE                                  ");
		sb.append("\n      , A.COLUMN_SIZE                   AS DATA_LEN                                   ");
		sb.append("\n      , 0                               AS DATA_PNUM                                  ");
		sb.append("\n      , A.DECIMAL_DIGITS                AS DATA_PNT                                   ");
		sb.append("\n      , CASE WHEN SUBSTR(A.IS_NULLABLE,1,1) = 'F' THEN 'N' ELSE 'Y' END AS NULL_YN    ");
		sb.append("\n      , 0                               AS DEFLT_LEN                                  ");
		sb.append("\n      , A.COLUMN_DEF                    AS DEFLT_VAL                                  ");
		sb.append("\n      , (CASE WHEN A.NULLABLE = 0 THEN 'Y' ELSE 'N' END)  AS PK_YN                    ");
		sb.append("\n      , A.ORDINAL_POSITION              AS ORD                                        ");
		sb.append("\n      , (CASE WHEN A.NULLABLE = 0 THEN A.ORDINAL_POSITION END) AS PK_ORD              ");
		sb.append("\n      , ''                              AS COL_DESCN                                  ");
		sb.append("\n   FROM WAE_MSA_COL A                                        ");
		sb.append("\n  INNER JOIN (                                         ");
		sb.append(           getdbconnsql()                                       );
		sb.append("\n             ) S                                                  ");
		sb.append("\n          ON A.DB_NM  = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n         AND A.SCH_NM = S.DB_SCH_PNM                        ");
		sb.append("\n         AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID            ");
		sb.append("\n  WHERE A.DB_CONN_TRG_ID = '"+dbms_id+"'                             ");
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
}
