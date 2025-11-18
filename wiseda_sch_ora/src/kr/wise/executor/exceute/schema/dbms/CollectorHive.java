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
import java.util.ArrayList;
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
public class CollectorHive {

	private static final Logger logger = Logger.getLogger(CollectorHive.class);

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


	public CollectorHive() {

	}

	/** insomnia */
	public CollectorHive(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
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
				//cnt = updateMetaDATABASES_ORA_BLOCK_SIZE();
				logger.debug(sp + (++p) + ". updateMetaDATABASES_ORA_BLOCK_SIZE " + cnt + " OK!!");

				//DBA_TABLESPACES ---------------------------------------------------
				//cnt = insertDBA_TABLESPACES();
				logger.debug(sp + (++p) + ". insertDBA_TABLESPACES " + cnt + " OK!!");

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

			con_org.commit();

		}

		result = true;

		return result;

	}
	
	private ArrayList<String> selectTAB_NAME() throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT TBL_NM          ");
		sb.append("\n   FROM WAE_HIVE_TBL  ");
		sb.append("\n  WHERE DB_NM = '").append(dbName).append("'");
		
		PreparedStatement pstmt = null;
		ResultSet res = null;

		pstmt = con_org.prepareStatement(sb.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		res = pstmt.executeQuery();
		
		ArrayList<String> tblNmList = new ArrayList<String>();
		
		while(res.next()) {
			tblNmList.add(res.getString(1));
		}
		
		pstmt.close();
		res.close();
		
		return tblNmList;
	}

	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		//sb.append("\n SHOW TABLES");
		int cnt = 0;
		
		ArrayList<String> tblNmList = selectTAB_NAME();
		
		for(int i=0; i<tblNmList.size(); i++) {
			sb = new StringBuffer();
			
			String tblNm = tblNmList.get(i);

			sb.append("\n DESCRIBE " + tblNm);
			
			getResultSet(sb.toString());
			
			sb = new StringBuffer();
			
			sb.append("\nINSERT INTO WAE_HIVE_COL (         ");
			sb.append("\n        DB_NM                        ");
			sb.append("\n        ,SCH_NM                     ");
			sb.append("\n        ,TBL_NM                ");
			sb.append("\n        ,COL_NM               ");
			sb.append("\n        ,DATA_TYPE                 ");
			sb.append("\n        ,COL_CMNT                 ");
			sb.append("\n) VALUES (                                ");
			sb.append("\n  ?,?,?,?,?,?                               ");
			sb.append("\n)                                         ");
	
			cnt = 0;
			if( rs != null ) {
				pst_org = con_org.prepareStatement(sb.toString());
				pst_org.clearParameters();
	
				int rsCnt = 1;
				int rsGetCnt = 1;
				
				String colNm = "";
				String colTyp = "";
				String colCmnt = "";
				
				while(rs.next())
				{
					colNm = rs.getString(1);
					colTyp = rs.getString(2);
					colCmnt = rs.getString(3);
					
					pst_org.setString(rsGetCnt++, dbName);
					pst_org.setString(rsGetCnt++, schemaName);
					pst_org.setString(rsGetCnt++, tblNm);
					pst_org.setString(rsGetCnt++, colNm);
					pst_org.setString(rsGetCnt++, colTyp);
					pst_org.setString(rsGetCnt++, colCmnt==null?colNm:colCmnt);
	
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
		sb.append("\n SHOW TABLES");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_HIVE_TBL (    ");
		sb.append("\n      DB_NM                     ");
		sb.append("\n      , SCH_NM                     ");
		sb.append("\n      , TBL_NM                     ");
		sb.append("\n) VALUES (                              ");
		sb.append("\n  ?,?,?                                 ");
		sb.append("\n)                                       ");

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
				pst_org.setString(rsGetCnt++, rs.getString(1));

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

		sb.setLength(0);

		sb.append("\nINSERT INTO WAE_HIVE_SCH (       ");
		sb.append("\n   DB_NM                       ");
		sb.append("\n     ,SCH_NM                         ");
		sb.append("\n     ,DB_SCH_ID                         ");
		sb.append("\n     ,PWD                        ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?            ");
		sb.append("\n)                                 ");

		pst_org = con_org.prepareStatement(sb.toString());
		pst_org.clearParameters();
		
		int rsGetCnt = 1;
		
		pst_org.setString(rsGetCnt++, dbName);
		pst_org.setString(rsGetCnt++, schemaName);
		pst_org.setString(rsGetCnt++, schemaId);
		pst_org.setString(rsGetCnt++, "");
		
		getSaveResult(1);
		
		executeBatch(true);
		
		
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return 1;
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
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_HIVE_DB (");
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
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_HIVE_DB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_DB : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_HIVE_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_HIVE_TBL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_TBL : " + result);

		return result;
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

		try {
			pst_org.addBatch();
		} catch(Exception e) {
			System.out.println(e);
		}

		result = true;

		return result;
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
		System.out.println("안되는겨?");
		int[] i = pst_org.executeBatch();
		System.out.println("되는겨?");

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
		sb.append("\n      , A.TBL_NM              AS DBC_TBL_KOR_NM                        ");
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
		sb.append("\n      , ''                    AS ROW_EACNT                             ");
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
		sb.append("\n   FROM WAE_HIVE_TBL A                                                 ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_HIVE_DB B                                             ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TBL_NM, COUNT(*) AS COL_CNT  ");
		sb.append("\n                      FROM WAE_HIVE_COL                                ");
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
		sb.append("\n      , A.COL_CMNT                                              ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                   ");
		sb.append("\n      , SYSDATE                                               ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , DATA_TYPE                                             ");
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
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n   FROM WAE_HIVE_COL A                                        ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_HIVE_DB B                                     ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                     ");
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

		return strSQL.toString();

	}


}
