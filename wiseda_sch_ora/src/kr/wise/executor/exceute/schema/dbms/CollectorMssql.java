/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorMssql.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : moonsungeun
 * 5. 작성일  : 2015. 7. 17. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    moonsungeun : 2015. 7. 17. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema.dbms;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;


public class CollectorMssql{

	private static final Logger logger = Logger.getLogger(CollectorMssql.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;

	private String sSchemaCond = "";	//추출 대상이 되는 유저
	private String dbName = null;
	private String dbKName = null;
	private String dbId = null;
	private String schemaName = null;
	private String schemaId = null;
//	private String src_nm = "sys.";
	private String src_nm = ".dbo.";
//	private String src_nm = ".DBO.";

	private String dbType = null;
	private String dbmsVersion = "";

	private int execCnt = 10000;


	public CollectorMssql() {

	}

	/** insomnia */
	public CollectorMssql(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;
	}

	/**  insomnia
	 * @return */
	public boolean doProcess() throws Exception {
		boolean result = false;

		this.con_org.setAutoCommit(false);

		String sp = "   ";
		int dbCnt = 0;
		for (TargetDbmsDM targetDb : targetDblist) {
			this.schemaName = targetDb.getDbc_owner_nm();
			this.schemaId = targetDb.getDbSchId();
			this.dbName = targetDb.getDbc_owner_nm();
//			this.dbName = targetDb.getDbms_enm();
			this.dbKName = targetDb.getDbms_enm();
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
//				cnt = updateMetaDATABASES_MS_BLOCK_SIZE();
//				logger.debug(sp + (++p) + ". updateMetaDATABASES_MS_BLOCK_SIZE " + cnt + " OK!!");

				//DBA_TABLESPACES ---------------------------------------------------
//				cnt = insertDBA_TABLESPACES();
//				logger.debug(sp + (++p) + ". insertDBA_TABLESPACES " + cnt + " OK!!");

//				this.con_org.commit();
			}

			dbCnt++;

			logger.debug(" schemaName : "+schemaName);
			//SYSUSERS --------------------------------------------------------
//			cnt = insertSYSUSERS();
//			logger.debug(sp + (++p) + ". insertSYSUSERS " + cnt + " OK!!");

			//SYSOBJECTS -------------------------------------------------------
			cnt = insertSYSOBJECTS();
			logger.debug(sp + (++p) + ". insertSYSOBJECTS " + cnt + " OK!!");

			//SYSCOMMENTS ---------------------------------------------------
			cnt = insertSYSCOMMENTS();
			logger.debug(sp + (++p) + ". insertSYSCOMMENTS " + cnt + " OK!!");

			//SYSCOLUMNS ---------------------------------------------------
			cnt = insertSYSCOLUMNS();
			logger.debug(sp + (++p) + ". insertSYSCOLUMNS " + cnt + " OK!!");

			//SYSTYPES ---------------------------------------------------
			cnt = insertSYSTYPES();
			logger.debug(sp + (++p) + ". insertSYSTYPES " + cnt + " OK!!");

			//SYSFOREIGNKEYS ---------------------------------------------------
			cnt = insertSYSFOREIGNKEYS();
			logger.debug(sp + (++p) + ". insertSYSFOREIGNKEYS " + cnt + " OK!!");

			//SYSINDEXES ---------------------------------------------------
			cnt = insertSYSINDEXES();
			logger.debug(sp + (++p) + ". insertSYSINDEXES " + cnt + " OK!!");
			
			//SYSINDEXKEYS ---------------------------------------------------
			cnt = insertSYSINDEXKEYS();
			logger.debug(sp + (++p) + ". insertSYSINDEXKEYS " + cnt + " OK!!");

			//SYSCONSTRAINTS ---------------------------------------------------
			cnt = insertSYSCONSTRAINTS();
			logger.debug(sp + (++p) + ". insertSYSCONSTRAINTS " + cnt + " OK!!");

			//SYSREFERENCES ---------------------------------------------------
			cnt = insertSYSREFERENCES();
			logger.debug(sp + (++p) + ". insertSYSREFERENCES " + cnt + " OK!!");
			
			//extended_properties ---------------------------------------------------
			cnt = insertExtendedProperties();
			logger.debug(sp + (++p) + ". insertExtendedProperties " + cnt + " OK!!");

//			this.con_org.commit();

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
		sb.append("\n      ,'").append(dbKName).append("' AS DB_NM");
		sb.append("\n  FROM DBA_PROCEDURES");
		sb.append("\n WHERE UPPER(OWNER) = '").append(schemaName.toUpperCase()).append("'");

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
			sb.append("\n      ,'").append(dbKName).append("' AS DB_NM");
			sb.append("\n  FROM DBA_OBJECTS");
			sb.append("\n WHERE UPPER(OWNER) ='").append(schemaName.toUpperCase()).append("'");
			sb.append("\n   AND OBJECT_TYPE IN ('PACKAGE','FUNCTION','PROCEDURE')");

		}
		getResultSet(sb.toString());

		sb.setLength(0);
		sb.append("\nINSERT INTO WAE_ORA_OBJ (");
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
				pst_org.setString(rsGetCnt++, dbKName);
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

		return cnt;
	}

	/** @return insomnia
	 * @throws Exception */
	private int insertSYSINDEXKEYS() throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT A.ID, A.INDID, A.COLID, A.KEYNO          ");
		sb.append("\n      , D.NAME AS SCHEMA_NM                      ");
		sb.append("\n   FROM ").append(dbName).append(src_nm).append("sysindexkeys A ");
		sb.append("\n        INNER JOIN ").append(dbName).append(src_nm).append("sysindexes B                                 ");
		sb.append("\n           ON B.ID    = A.ID                                    ");
		sb.append("\n          AND B.INDID = A.INDID                                 ");
	    sb.append("\n        INNER JOIN ").append(dbName).append(src_nm).append("sysobjects C                             ");
	    sb.append("\n           ON C.xtype = 'U'                                     ");                              
	    sb.append("\n          AND C.ID = B.ID                                       ");
	    sb.append("\n        INNER JOIN sys.schemas D                                ");
		sb.append("\n           ON D.SCHEMA_ID =  C.UID                              ");
		sb.append("\n  WHERE D.NAME = '"+  schemaName +"'    ");
			

		logger.debug(sb.toString());
		getResultSet(sb.toString());

		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_MS_INDEXKEYS (");
		sb.append("\n   ID, INDID, COLID, KEYNO, DB_NM, SCH_NM)   ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?)   ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYNO"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NM"));

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
		sb.append("\n SELECT                                  ");
		sb.append("\n --      ,데이터베이스명                 ");
		sb.append("\n      '").append(dbKName).append("' AS DB_NM");
		sb.append("\n       ,TABLESPACE_NAME                   ");
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
		sb.append("\n   FROM DBA_TABLESPACES                  ");
//		sb.append("\n UNION ALL                               ");
//		sb.append("\n SELECT                                  ");
//		sb.append("\n --      ,데이터베이스명                 ");
//		sb.append("\n      '").append(dbKName).append("' AS DB_NM");
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

		sb.append("\nINSERT INTO WAE_ORA_TBL_SPAC (");
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
				pst_org.setString(rsGetCnt++, dbKName);
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

		return cnt;

	}


	/**
	 * DBC SYSCONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCONSTRAINTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT CONSTID, ID, COLID, SPARE1, STATUS ");
		sb.append("\n    , ACTIONS, ERROR ");
//		sb.append("\n  FROM ").append(src_nm).append("SYSCONSTRAINTS ");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("sysconstraints ");
		
		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_MS_CONSTRAINTS (");
		sb.append("\n   CONSTID, ID, COLID, SPARE1, STATUS    ");
		sb.append("\n   , ACTIONS, ERROR, DB_NM, SCH_NM)    ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)  ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CONSTID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SPARE1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ACTIONS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ERROR"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				
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
	 * DBC SYSREFERENCES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSREFERENCES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT CONSTID, FKEYID, RKEYID, RKEYINDID, KEYCNT ");
		sb.append("\n    , FORKEYS, REFKEYS, FKEYDBID, RKEYDBID, FKEY1 ");
		sb.append("\n    , FKEY2, FKEY3, FKEY4, FKEY5, FKEY6 ");
		sb.append("\n    , FKEY7, FKEY8, FKEY9, FKEY10, FKEY11 ");
		sb.append("\n    , FKEY12, FKEY13, FKEY14, FKEY15, FKEY16 ");
		sb.append("\n    , RKEY1, RKEY2, RKEY3, RKEY4, RKEY5 ");
		sb.append("\n    , RKEY6, RKEY7, RKEY8, RKEY9, RKEY10 ");
		sb.append("\n    , RKEY11, RKEY12, RKEY13, RKEY14, RKEY15 ");
		sb.append("\n    , RKEY16 ");
//		sb.append("\n  FROM ").append(src_nm).append("SYSREFERENCES ");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("sysreferences ");
		
		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_MS_REFERENCES (");
		sb.append("\n   CONSTID, FKEYID, RKEYID, RKEYINDID, KEYCNT    ");
		sb.append("\n   , FKEYDBID, RKEYDBID, FKEY1    ");
		sb.append("\n   , FKEY2, FKEY3, FKEY4, FKEY5, FKEY6    ");
		sb.append("\n   , FKEY7, FKEY8, FKEY9, FKEY10, FKEY11    ");
		sb.append("\n   , FKEY12, FKEY13, FKEY14, FKEY15, FKEY16    ");
		sb.append("\n   , RKEY1, RKEY2, RKEY3, RKEY4, RKEY5    ");
		sb.append("\n   , RKEY6, RKEY7, RKEY8, RKEY9, RKEY10    ");
		sb.append("\n   , RKEY11, RKEY12, RKEY13, RKEY14, RKEY15    ");
		sb.append("\n   , RKEY16, DB_NM, SCH_NM)    ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n    , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n    , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n    , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n    , ? ) ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{	
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CONSTID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEYINDID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYCNT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEYDBID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEYDBID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY5"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY6"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY7"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY8"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY9"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY10"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY11"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY12"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY13"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY14"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY15"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY16"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY5"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY6"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY7"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY8"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY9"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY10"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY11"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY12"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY13"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY14"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY15"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY16"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				
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
	 * DBC extended_properties 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertExtendedProperties() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT CLASS, CLASS_DESC,  MAJOR_ID,  MINOR_ID,  NAME,  CONVERT(char(3000),VALUE) AS VALUE  ");
		sb.append("\n FROM sys.extended_properties ");
		
		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_MS_EXTENDED_PROPERTIES (");
		sb.append("\n   CLASS, CLASS_DESC,  MAJOR_ID,  MINOR_ID,  NAME,  VALUE, DB_NM, SCH_NM  ) ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ? ) ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{	
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CLASS"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_DESC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAJOR_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MINOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("VALUE"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				
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
	 * DBC SYSINDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSINDEXES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT A.ID                   ");
		sb.append("\n      , A.STATUS               ");
		sb.append("\n      , A.FIRST                ");
		sb.append("\n      , A.INDID                ");
		sb.append("\n      , A.ROOT                 ");
		sb.append("\n      , A.MINLEN               ");
		sb.append("\n      , A.KEYCNT               ");
		sb.append("\n      , A.GROUPID              ");
		sb.append("\n      , A.DPAGES               ");
		sb.append("\n      , A.RESERVED             ");
		sb.append("\n      , A.USED, 0 as ROWCNT    ");
		sb.append("\n      , A.ROWMODCTR            ");
		sb.append("\n      , A.RESERVED3            ");
		sb.append("\n      , A.RESERVED4            ");
		sb.append("\n      , A.XMAXLEN              ");
		sb.append("\n      , A.MAXIROW              ");
		sb.append("\n      , A.ORIGFILLFACTOR       ");
		sb.append("\n      , A.STATVERSION          ");
		sb.append("\n      , A.RESERVED2            ");
		sb.append("\n      , A.FIRSTIAM             ");
		sb.append("\n      , A.IMPID                ");
		sb.append("\n      , A.LOCKFLAGS            ");
		sb.append("\n      , A.PGMODCTR             ");
		sb.append("\n      , A.KEYS                 ");
		sb.append("\n      , A.NAME                 ");
		sb.append("\n      , A.STATBLOB             ");
		sb.append("\n      , A.MAXLEN               ");
		sb.append("\n      , A.ROWS                 ");
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 0X800) / 0X800) = '1' THEN 'Y' ELSE 'N' END	AS IS_PK     ");
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 2) / 2) = '1' THEN 'Y' ELSE 'N' END	AS IS_UNIQUE    ");
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 16) / 16) = '1' THEN 'Y' ELSE 'N' END	AS IS_CLUSTERED              ");
		sb.append("\n      , (A.STATUS & 32) AS STATUS32                                         ");
		sb.append("\n      , C.NAME        AS SCHEMA_NM                                          ");
		sb.append("\n   FROM ").append(dbName).append(src_nm).append("sysindexes  A              ");
		sb.append("\n        INNER JOIN ").append(dbName).append(src_nm).append("sysobjects B    ");       
	    sb.append("\n           ON B.xtype = 'U'                                     ");           
	    sb.append("\n          AND B.ID = A.ID                                       ");           
	    sb.append("\n        INNER JOIN sys.schemas C                                ");          
	    sb.append("\n           on C.schema_id = B.uid                               ");
	    sb.append("\n WHERE C.NAME = '"+  schemaName +"'    ");

		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_MS_INDEXES(");
		sb.append("\n   ID, STATUS, FIRST, INDID, ROOT   ");
		sb.append("\n  , MINLEN, KEYCNT, GROUPID, DPAGES, RESERVED     ");
		sb.append("\n  , USED, ROWCNT, ROWMODCTR, RESERVED3, RESERVED4  ");
		sb.append("\n  , XMAXLEN, MAXIROW, ORIGFILLFACTOR, STATVERSION, RESERVED2    ");
		sb.append("\n  , FIRSTIAM, IMPID, LOCKFLAGS, PGMODCTR, KEYS, NAME   ");
		sb.append("\n  , MAXLEN, C_ROWS, IS_PK, IS_UNIQUE, IS_CLUSTERED, STATUS32, DB_NM, SCH_NM)             ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?)   ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("FIRST"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDID"));
				pst_org.setString(rsGetCnt++, rs.getString("ROOT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MINLEN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYCNT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GROUPID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DPAGES"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROWCNT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROWMODCTR"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XMAXLEN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAXIROW"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ORIGFILLFACTOR"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATVERSION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED2"));
				pst_org.setString(rsGetCnt++, rs.getString("FIRSTIAM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("IMPID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LOCKFLAGS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PGMODCTR"));
				pst_org.setString(rsGetCnt++, rs.getString("KEYS"));
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAXLEN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROWS"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_PK"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_UNIQUE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_CLUSTERED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS32"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NM"));

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
	 * DBC SYSFOREIGNKEYS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSFOREIGNKEYS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT                                    ");
		sb.append("\n     CONSTID, FKEYID, RKEYID, FKEY, RKEY, KEYNO              ");
//		sb.append("\n    FROM ").append(src_nm).append("SYSFOREIGNKEYS");
		sb.append("\n    FROM ").append(dbName).append(src_nm).append("sysforeignkeys                        ");

		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_MS_FOREIGNKEYS ( ");
		sb.append("\n   CONSTID, FKEYID, RKEYID, FKEY, RKEY   ");
		sb.append("\n   , KEYNO, DB_NM, SCH_NM)        ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?)    ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CONSTID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYNO"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);

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
	 * DBC SYSTYPES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSTYPES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT NAME, XTYPE, STATUS, XUSERTYPE, LENGTH  ");
		sb.append("\n  , XPREC, XSCALE, TDEFAULT, DOMAIN, UID ");
		sb.append("\n  , RESERVED, COLLATIONID, USERTYPE, VARIABLE, ALLOWNULLS   ");
		sb.append("\n  , TYPE, PRINTFMT, PREC, SCALE, COLLATION ");
//		sb.append("\n  FROM ").append(src_nm).append("SYSTYPES  ");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("systypes  ");

		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_MS_TYPES (");
		sb.append("\n   NAME, XTYPE, STATUS, XUSERTYPE, LENGTH      ");
		sb.append("\n  , XPREC, XSCALE, TDEFAULT, DOMAIN, C_UID       ");
		sb.append("\n  , RESERVED, COLLATIONID, USERTYPE, VARIABLE, ALLOWNULLS     ");
		sb.append("\n  , TYPE, PRINTFMT, PREC, SCALE, COLLATION    ");
		sb.append("\n  , DB_NM, SCH_NM)                   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n  , ?, ?)                        ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XUSERTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XPREC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XSCALE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TDEFAULT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DOMAIN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLLATIONID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USERTYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("VARIABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("ALLOWNULLS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PRINTFMT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PREC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SCALE"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);

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
	 * DBC SYSCOLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCOLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nSELECT  A.NAME                                                    ");
		sb.append("\n      , A.ID                                                      ");
		sb.append("\n      , A.XTYPE                                                   ");
		sb.append("\n      , A.TYPESTAT                                                ");
		sb.append("\n      , A.XUSERTYPE                                               ");
		sb.append("\n      , A.LENGTH                                                  ");
		sb.append("\n      , A.XPREC                                                   ");
		sb.append("\n      , A.XSCALE                                                  ");
		sb.append("\n      , A.COLID                                                   ");
		sb.append("\n      , A.XOFFSET                                                 ");
		sb.append("\n      , A.BITPOS                                                  ");
		sb.append("\n      , A.RESERVED                                                ");
		sb.append("\n      , A.COLSTAT                                                 ");
		sb.append("\n      , A.CDEFAULT                                                ");
		sb.append("\n      , A.DOMAIN                                                  ");
		sb.append("\n      , A.NUMBER                                                  ");
		sb.append("\n      , A.COLORDER                                                ");
		sb.append("\n      , A.AUTOVAL                                                 ");
		sb.append("\n      , A.OFFSET                                                  ");
		sb.append("\n      , A.COLLATIONID                                             ");
		sb.append("\n      , A.LANGUAGE                                                ");
		sb.append("\n      , A.STATUS                                                  ");
		sb.append("\n      , A.TYPE                                                    ");
		sb.append("\n      , A.USERTYPE                                                ");
		sb.append("\n      , A.PRINTFMT                                                ");
		sb.append("\n      , A.PREC                                                    ");
		sb.append("\n      , A.SCALE                                                   ");
		sb.append("\n      , A.ISCOMPUTED                                              ");
		sb.append("\n      , A.ISOUTPARAM                                              ");
		sb.append("\n      , A.ISNULLABLE                                              ");
		sb.append("\n      , A.COLLATION                                               ");
		sb.append("\n      , A.TDSCOLLATION                                            ");
		sb.append("\n      ,CONVERT(INT,                                               ");
		sb.append("\n         CASE                                                     ");
		sb.append("\n           WHEN TYPE_NAME(A.TYPE) IN ('NUMERIC', 'DECIMAL')       ");
		sb.append("\n             THEN ODBCPREC(A.TYPE, A.LENGTH, A.PREC) + 2          ");
		sb.append("\n           ELSE LENGTH                                            ");
		sb.append("\n         END ) AS DATA_LENGTH                                     ");
		sb.append("\n      , CONVERT(SMALLINT, ODBCSCALE(A.TYPE, A.SCALE)) AS DATA_SCALE   ");
		sb.append("\n      , '"+schemaName+"' AS   SCHEMA_NM                                 ");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("syscolumns A            ");
		sb.append("\n       INNER JOIN ").append(dbName).append(src_nm).append("sysobjects B ");
		sb.append("\n          ON B.xtype = 'U'                                              ");
	    sb.append("\n         AND B.ID = A.ID                                                ");

		logger.debug(" insertSYSCOLUMNS : "+sb.toString());
//System.out.println(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		//System.out.println("insertDBA_CONSTRAINTS : \n"+sb);

		sb.append("\nINSERT INTO WAE_MS_COLUMNS (");
		sb.append("\n     NAME, ID, XTYPE, TYPESTAT, XUSERTYPE                      ");
		sb.append("\n       , LENGTH, XPREC, XSCALE, COLID, XOFFSET               ");
		sb.append("\n       , BITPOS, RESERVED, COLSTAT, CDEFAULT, DOMAIN          ");
		sb.append("\n       , C_NUMBER, COLORDER             ");
		sb.append("\n       , OFFSET, COLLATIONID       ");
		sb.append("\n       , LANGUAGE, STATUS, TYPE, USERTYPE, PRINTFMT        ");
		sb.append("\n       , PREC, SCALE, ISCOMPUTED, ISOUTPARAM, ISNULLABLE       ");
		sb.append("\n       , COLLATION, TDSCOLLATION, DB_NM, SCH_NM)          ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?       ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?         ");
		sb.append("\n      , ?, ?, ?)                 ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TYPESTAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XUSERTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_LENGTH"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XPREC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATA_SCALE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("XOFFSET"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BITPOS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RESERVED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLSTAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CDEFAULT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DOMAIN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUMBER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLORDER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("OFFSET"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLLATIONID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LANGUAGE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USERTYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("PRINTFMT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PREC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SCALE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISCOMPUTED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISOUTPARAM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISNULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, rs.getString("TDSCOLLATION"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NM"));

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
	 * DBC SYSCOMMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCOMMENTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\nSELECT ID        ");
		sb.append("\n      ,NUMBER    ");
		sb.append("\n      ,COLID     ");
		sb.append("\n      ,STATUS    ");
		sb.append("\n      ,CTEXT     ");
		sb.append("\n      ,TEXTTYPE  ");
		sb.append("\n      ,LANGUAGE  ");
		sb.append("\n      ,ENCRYPTED ");
		sb.append("\n      ,COMPRESSED");
		sb.append("\n      ,CONVERT(nvarchar(1000), text) AS TEXT ");
//		sb.append("\n  FROM ").append(src_nm).append("SYSCOMMENTS");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("syscomments ");

		logger.debug(" insertSYSCOMMENTS : "+sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_MS_COMMENTS (         ");
		sb.append("\n      ID, C_NUMBER, COLID, STATUS           ");
		sb.append("\n      , TEXTTYPE, LANGUAGE, ENCRYPTED, COMPRESSED, TEXT       ");
		sb.append("\n      , DB_NM, SCH_NM  )    ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?           ");
		sb.append("\n      , ?)                 ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{	
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUMBER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TEXTTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LANGUAGE"));
				pst_org.setString(rsGetCnt++, rs.getString("ENCRYPTED"));
				pst_org.setString(rsGetCnt++, rs.getString("COMPRESSED"));
				pst_org.setString(rsGetCnt++, rs.getString("TEXT"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);

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
	 * DBC SYSOBJECTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSOBJECTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nSELECT  A.NAME            ");
		sb.append("\n      , A.ID              ");
		sb.append("\n      , A.XTYPE           ");
		sb.append("\n      , A.UID             ");
		sb.append("\n      , A.INFO            ");
		sb.append("\n      , A.STATUS          ");
		sb.append("\n      , A.BASE_SCHEMA_VER ");
		sb.append("\n      , A.REPLINFO        ");
		sb.append("\n      , A.PARENT_OBJ      ");
		sb.append("\n      , A.CRDATE          ");
		sb.append("\n      , A.FTCATID         ");
		sb.append("\n      , A.SCHEMA_VER      ");
		sb.append("\n      , A.STATS_SCHEMA_VER");
		sb.append("\n      , A.TYPE            ");
		sb.append("\n      , A.USERSTAT        ");
		sb.append("\n      , A.SYSSTAT         ");
		sb.append("\n      , A.INDEXDEL        ");
		sb.append("\n      , A.REFDATE         ");
		sb.append("\n      , A.VERSION         ");
		sb.append("\n      , A.DELTRIG         ");
		sb.append("\n      , A.INSTRIG         ");
		sb.append("\n      , A.UPDTRIG         ");
		sb.append("\n      , A.SELTRIG         ");
		sb.append("\n      , A.CATEGORY        ");
		sb.append("\n      , A.CACHE           ");
		sb.append("\n      , '"+schemaName+"' AS SCHEMA_NM  ");
		sb.append("\n  FROM ").append(dbName).append(src_nm).append("sysobjects A");
		sb.append("\n  WHERE A.XTYPE = 'U'                   ");

		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_MS_OBJECTS (    ");
		sb.append("\n      NAME, ID, XTYPE, C_UID, INFO                     "); 
		sb.append("\n      , STATUS, BASE_SCHEMA_VER, REPLINFO, PARENT_OBJ, CRDATE    "); 
		sb.append("\n      , FTCATID, SCHEMA_VER, STATS_SCHEMA_VER, TYPE, USERSTAT    "); 
		sb.append("\n      , SYSSTAT, INDEXDEL, REFDATE, VERSION, DELTRIG             "); 
		sb.append("\n      , INSTRIG, UPDTRIG, SELTRIG, CATEGORY, CACHE    "); 
		sb.append("\n      , DB_NM, SCH_NM)                    ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?              ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                    ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?)              ");
		
		logger.debug(sb.toString());

		int cnt = 0;
		if( this.rs != null ) {

			this.pst_org = con_org.prepareStatement(sb.toString());
			this.pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(this.rs.next())
			{
				this.pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				this.pst_org.setString(rsGetCnt++, rs.getString("ID"));
				this.pst_org.setString(rsGetCnt++, rs.getString("XTYPE"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UID"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INFO"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BASE_SCHEMA_VER"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("REPLINFO"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PARENT_OBJ"));
				this.pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("CRDATE"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FTCATID"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SCHEMA_VER"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATS_SCHEMA_VER"));
				this.pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USERSTAT"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SYSSTAT"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEXDEL"));
				this.pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("REFDATE"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("VERSION"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DELTRIG"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INSTRIG"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UPDTRIG"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SELTRIG"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CATEGORY"));
				this.pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CACHE"));
				this.pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NM"));

				getSaveResult(rsCnt);

				if(rsCnt == this.execCnt)
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
	 * DBC SYSUSERS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSUSERS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nSELECT UID        ");
		sb.append("\n      ,STATUS     ");
		sb.append("\n      ,NAME       ");
		sb.append("\n      ,SID        ");
		sb.append("\n      ,ROLES      ");
		sb.append("\n      ,CREATEDATE ");
		sb.append("\n      ,UPDATEDATE ");
		sb.append("\n      ,ALTUID     ");
		sb.append("\n      ,PASSWORD   ");
		sb.append("\n      ,GID        ");
		sb.append("\n      ,ENVIRON    ");
		sb.append("\n      ,HASDBACCESS");
		sb.append("\n      ,ISLOGIN    ");
		sb.append("\n      ,ISNTNAME   ");
		sb.append("\n      ,ISNTGROUP  ");
		sb.append("\n      ,ISNTUSER   ");
		sb.append("\n      ,ISSQLUSER  ");
		sb.append("\n      ,ISALIASED  ");
		sb.append("\n      ,ISSQLROLE  ");
		sb.append("\n      ,ISAPPROLE  ");
//		sb.append("\n  FROM ").append(src_nm).append("SYSUSERS");
		sb.append("\n  FROM ").append(this.dbName).append(this.src_nm).append("sysusers");
//		sb.append("\n WHERE UPPER(NAME) IN ('").append(schemaName.toUpperCase()).append("')");

//		logger.debug(" insertSYSUSERS : "+sb.toString());
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_MS_USERS (       ");
		sb.append("\n   C_UID, STATUS, NAME, SID, ROLES       ");
		sb.append("\n   , CREATEDATE, UPDATEDATE, ALTUID, PASSWORD, GID    ");
		sb.append("\n   , ENVIRON, HASDBACCESS, ISLOGIN, ISNTNAME, ISNTGROUP    ");
		sb.append("\n   , ISNTUSER, ISSQLUSER, ISALIASED, ISSQLROLE, ISAPPROLE  ");
		sb.append("\n   , DB_NM, SCH_NM)   ");
		sb.append("\n  VALUES (?, ?, UPPER(?), ?, ?, ?, ?, ?, ?, ?      ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n  , ?, ?)              ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("SID"));
				pst_org.setString(rsGetCnt++, rs.getString("ROLES"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("CREATEDATE"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("UPDATEDATE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ALTUID"));
				pst_org.setString(rsGetCnt++, rs.getString("PASSWORD"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ENVIRON"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("HASDBACCESS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISLOGIN"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISNTNAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISNTGROUP"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISNTUSER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISSQLUSER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISALIASED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISSQLROLE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ISAPPROLE"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);

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
		logger.debug("query_tgt:\n"+query_tgt);
		
		this.pst_tgt = null;
		this.rs = null;

		this.pst_tgt = this.con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		this.pst_tgt.setFetchSize(1000);
		this.rs = pst_tgt.executeQuery();

		return this.rs;
	}

	/**
	 * 타겟데이터의 SQL을 받아 CResultSet을 넘겨준다.
	 * @param String 조회 SQL
	 * @return CResultSet Query 결과값
	 * @throws SQLException
	 */
	private ResultSet getResultSet_Org(String query_org) throws SQLException
	{
		this.pst_org = con_org.prepareStatement(query_org);
		//logger.debug("query Org: " + query_org);
		this.rs = pst_org.executeQuery();

		return this.rs;
	}

	/**
	 * DBC DBA_DATABASES 저장 : ORACLE TABLE SIZING을 위한 BLOCK SIZE 입력
	 * powered by javala 2008.11.20
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
//	private int updateMetaDATABASES_MS_BLOCK_SIZE() throws SQLException
//	{
//		StringBuffer sb = new StringBuffer();
//
//		sb.append("\n	select db_name(dbid) db_nm, ");
//		sb.append("\n\n				dbid db_id,	                            ");
//		sb.append("\n\n				segmap seg_type,	                            ");
//		sb.append("\n\n				(sum(size)*(@@maxpagesize/1024)/1024) seg_size,	                            ");
//		sb.append("\n\n				(sum(size)*(@@maxpagesize/1024)/1024 - sum(curunreservedpgs(dbid,lstart,unreservedpgs))*(@@maxpagesize/1024)/1024) used_size,	                            ");
//		sb.append("\n\n				(sum(curunreservedpgs(dbid, lstart,unreservedpgs))*(@@maxpagesize/1024)/1024) free_size,	                            ");
//		sb.append("\n\n				(100 - 100*sum(curunreservedpgs(dbid, lstart,unreservedpgs))/sum(size)) pctused	                            ");
//		sb.append("\n\n				from master..sysusages	                            ");
//		sb.append("\n\n				where size > 1024	                            ");
//		sb.append("\n\n				and (segmap in (3, 4) or dbid = 2)	                            ");
//		sb.append("\n\n				and (db_name(dbid) <> 'sybsecurity')	                            ");
//		sb.append("\n\n				group by db_name(dbid), dbid, segmap	                            ");
//		
//		getResultSet(sb.toString());
//
//
//		sb.setLength(0);
//
//
//		int block_byte = 0;
//
//		if( rs != null ) {
////			pst_org = con_org.prepareStatement(sb.toString());
////			pst_org.clearParameters();
//
//			while(rs.next())
//			{
//				block_byte = rs.getInt("BLOCK_BYTE");
//			}
//		}
//
//		sb.append("\n INSERT INTO WAE_ASE_DBSIZE ");
//		sb.append("\n  (db_nm, db_id, seg_type, seg_size, used_size, free_size, pctused , DB_NM ) ");
//		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?)");		
//
//		return setExecuteUpdate_Org(sb.toString());
//	}

	/**
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MS_DATABASES (");
		sb.append("\n   NAME                                            ");
		sb.append("\n  ,DBID                                  ");
		sb.append("\n  ,DB_NM                                   ");
//		sb.append("\n  ,STR_DTM                                          ");
		sb.append("\n)                              ");
		sb.append("\n SELECT DISTINCT DB_CONN_TRG_PNM                                        ");
		sb.append("\n        ,A.DB_CONN_TRG_ID                                      ");
		sb.append("\n         ,'").append(dbKName).append("' AS DB_NM");
//		sb.append("\n         ,A.STR_DTM                                            ");
		sb.append("\n   FROM WAA_DB_CONN_TRG A                                      ");
		sb.append("\n  INNER JOIN WAA_DB_SCH B                                      ");
		sb.append("\n     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID                    ");
		sb.append("\n    AND B.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND B.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND A.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  AND	A.DBMS_TYP_CD    = '").append(dbType).append("'") ;
		sb.append("\n  AND  UPPER(A.DB_CONN_TRG_PNM)   =    UPPER('").append(dbKName).append("')") ;
//		sb.append("\n  AND  UPPER(B.OBJ_KNM)   =    UPPER('").append(sdbName).append("')") ;

		logger.debug("insertMetaDATABASES : \n"+sb.toString());

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
		strSQL.append("\n  DELETE FROM WAE_MS_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_COMMENTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_COMMENTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_CONSTRAINTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_CONSTRAINTS : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_DATABASES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_DATABASES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_FOREIGNKEYS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_FOREIGNKEYS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_INDEXES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_INDEXES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_INDEXKEYS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_INDEXKEYS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_OBJECTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_OBJECTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_REFERENCES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_REFERENCES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_SERVERS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		//result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_SERVERS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_SP_SPACEUSED ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		//result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_SP_SPACEUSED : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_TYPES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_TYPES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_USERS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_USERS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_EXTENDED_PROPERTIES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbKName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_EXTENDED_PROPERTIES : " + result);

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
		this.pst_org.addBatch();
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

		int[] i = this.pst_org.executeBatch();

		if(execYN)
		{
			//logger.debug("단계:executeBatch [execYN]");
			this.pst_org.clearBatch();
		}
		else
		{
			//logger.debug("단계:executeBatch [close]");
			this.rs.close();
			this.pst_org.close();
		}

		return i;
	}

	/**  insomnia */
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception {
		this.schemaName = targetDb.getDbc_owner_nm();
		this.schemaId = targetDb.getDbSchId();
		this.dbName = targetDb.getDbms_enm();
		this.dbKName = targetDb.getDbms_enm();
		this.dbId = targetDb.getDbms_id();
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
		sb.append("\n (      DB_SCH_ID                           ");
		sb.append("\n      , DBC_TBL_NM                            ");
		sb.append("\n      , DBC_TBL_KOR_NM                        ");
		sb.append("\n      , VERS                                  ");
		sb.append("\n      , REG_TYP                               ");
		sb.append("\n      , REG_DTM                               ");
		sb.append("\n      , UPD_DTM                               ");
		sb.append("\n      , DESCN                                 ");
		sb.append("\n      , DB_CONN_TRG_ID                        ");
//		sb.append("\n      , DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , DDL_TBL_ID                            ");
		sb.append("\n      , PDM_TBL_ID                            ");
		sb.append("\n      , DBMS_TYPE                             ");
		sb.append("\n      , SUBJ_ID                               ");
//		sb.append("\n      , COL_EACNT                             ");
//		sb.append("\n      , ROW_EACNT                             ");
//		sb.append("\n      , TBL_SIZE                              ");
//		sb.append("\n      , DATA_SIZE                             ");
//		sb.append("\n      , IDX_SIZE                              ");
		sb.append("\n      , COL_EACNT                             ");
		sb.append("\n      , ROW_EACNT                             ");
		sb.append("\n      , TBL_SIZE                              ");
		sb.append("\n      , DATA_SIZE                             ");
		sb.append("\n      , IDX_SIZE                              ");
		sb.append("\n      , NUSE_SIZE                             ");
		sb.append("\n      , BF_COL_EACNT                          ");
		sb.append("\n      , BF_ROW_EACNT                          ");
		sb.append("\n      , BF_TBL_SIZE                           ");
		sb.append("\n      , BF_DATA_SIZE                          ");
		sb.append("\n      , BF_IDX_SIZE                           ");
		sb.append("\n      , BF_NUSE_SIZE                          ");
		sb.append("\n      , ANA_DTM                               ");
		sb.append("\n      , CRT_DTM                               ");
		sb.append("\n      , CHG_DTM                               ");
		sb.append("\n      , PDM_DESCN                             ");
		sb.append("\n      , TBL_DQ_EXP_YN                         ");
		sb.append("\n      , DDL_TBL_ERR_EXS                       ");
		sb.append("\n      , DDL_TBL_ERR_CD                        ");
		sb.append("\n      , DDL_TBL_ERR_DESCN                     ");
		sb.append("\n      , DDL_COL_ERR_EXS                       ");
		sb.append("\n      , DDL_COL_ERR_CD                        ");
		sb.append("\n      , DDL_COL_ERR_DESCN                     ");
		sb.append("\n      , PDM_TBL_ERR_EXS                       ");
		sb.append("\n      , PDM_TBL_ERR_CD                        ");
		sb.append("\n      , PDM_TBL_ERR_DESCN                     ");
		sb.append("\n      , PDM_COL_ERR_EXS                       ");
		sb.append("\n      , PDM_COL_ERR_CD                        ");
		sb.append("\n      , PDM_COL_ERR_DESCN                     ");
		sb.append("\n      , DDL_TBL_EXTNC_EXS                     ");
		sb.append("\n      , PDM_TBL_EXTNC_EXS )                     ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                           ");
		sb.append("\n      , A.NAME              AS DBC_TBL_NM                            ");
		sb.append("\n      , SUBSTR(P.VALUE,0,200)  AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                   AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL               AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
//		sb.append("\n      , A.TBL_SPAC_NM         AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''         AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                            ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                            ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS SUBJ_ID                               ");
//		sb.append("\n      , E.COL_CNT             AS COL_EACNT                             ");
//		sb.append("\n      , A.NUM_ROWS            AS ROW_EACNT                             ");
//		sb.append("\n      , C.BYTES               AS TBL_SIZE                              ");
//		sb.append("\n      , ''                    AS DATA_SIZE                             ");
//		sb.append("\n      , D.BYTES               AS IDX_SIZE                              ");
		sb.append("\n      , ''             AS COL_EACNT                             ");
		sb.append("\n      , ''            AS ROW_EACNT                             ");
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
		//sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
		//sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
		//sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
		//sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
		//sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
		//sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
		//sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_MS_OBJECTS A                                                  ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n    LEFT OUTER JOIN (                                       ");
		sb.append("\n     SELECT CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,NAME,VALUE,DB_NM,SCH_NM                                       ");
		sb.append("\n     FROM WAE_MS_EXTENDED_PROPERTIES                                       ");
		sb.append("\n     GROUP BY CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,NAME,VALUE,DB_NM,SCH_NM                                       ");
		sb.append("\n    ) P                                       ");	
		sb.append("\n     ON A.ID = P.MAJOR_ID                                       ");	
		sb.append("\n    AND A.DB_NM = P.DB_NM                                     ");	
		sb.append("\n    AND P.MINOR_ID = 0                                      ");	
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                        ");
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"'                                        ");
//		sb.append("\n    AND RTRIM(C.TYPE) = 'U'                                        ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                         ");
		sb.append("\n (      DB_SCH_ID                                ");
		sb.append("\n      , DBC_TBL_NM                               ");
		sb.append("\n      , DBC_COL_NM                               ");
		sb.append("\n      , DBC_COL_KOR_NM                           ");
		sb.append("\n      , VERS                                     ");
		sb.append("\n      , REG_TYP                                  ");
		sb.append("\n      , REG_DTM                                  ");
		sb.append("\n      , DATA_TYPE                                ");
		sb.append("\n      , DATA_LEN                                 ");
		sb.append("\n      , DATA_PNUM                                ");
		sb.append("\n      , DATA_PNT                                 ");
		sb.append("\n      , NULL_YN                                  ");
		sb.append("\n      , DEFLT_LEN                                ");
		sb.append("\n      , DEFLT_VAL                                ");
		sb.append("\n      , PK_YN                                    ");
		sb.append("\n      , ORD                                      ");
		sb.append("\n      , PK_ORD )                                 ");
		sb.append("\n SELECT S.DB_SCH_ID  AS DB_SCH_ID                                                  ");
		sb.append("\n      , A.O_NAME     AS DBC_TBL_NM                                                 ");
		sb.append("\n      , A.NAME       AS DBC_COL_NM                                                 ");
		sb.append("\n      , A.P_VALUE    AS DBC_COL_KOR_NM                                             ");
		sb.append("\n      , '1'          AS VERS                                                       ");
		sb.append("\n      , NULL         AS REG_TYP                                                    ");
		sb.append("\n      , SYSDATE      AS REG_DTM                                                    ");
//		sb.append("\n      , ''           AS UPD_DTM                                                    ");
//		sb.append("\n      , ''           AS DESCN                                                      ");
//		sb.append("\n      , ''           AS DDL_COL_ID                                                 ");
//		sb.append("\n      , ''           AS PDM_COL_ID                                                 ");
//		sb.append("\n      , ''           AS ITM_ID                                                     ");
		sb.append("\n      , A.T_NAME     AS DATA_TYPE                                                  ");
		sb.append("\n      , A.PREC       AS DATA_LEN                                                   ");
		sb.append("\n      , A.PREC       AS DATA_PNUM                                                  ");
		sb.append("\n      , A.SCALE      AS DATA_PNT                                                   ");
		sb.append("\n      , CASE WHEN A.ISNULLABLE = 1 THEN 'Y' ELSE 'N' END AS NULL_YN                ");
		sb.append("\n      , LENGTH(A.M_TEXT) AS DEFLT_LEN                                              ");
		sb.append("\n      , A.M_TEXT     AS DEFLT_VAL                                                  ");
		sb.append("\n      , CASE WHEN A.COLID = A.F_COLID THEN 'Y' ELSE 'N' END AS PK_YN               ");
		sb.append("\n      , A.COLORDER   AS ORD                                                        ");
		sb.append("\n      , CASE WHEN A.COLID = A.F_COLID THEN A.F_KEYNO ELSE null END    AS PK_ORD    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
//		sb.append("\n      , ''                                                    ");
		//sb.append("\n      , ''  -- MMART_COL_EXTNC_YN      ");  
		//sb.append("\n      , ''  -- MMART_ORD_ERR_EXS       ");
		//sb.append("\n      , ''  -- MMART_PK_YN_ERR_EXS     ");
		//sb.append("\n      , ''  -- MMART_PK_ORD_ERR_EXS    ");
		//sb.append("\n      , ''  -- MMART_NULL_YN_ERR_EXS   ");
		//sb.append("\n      , ''  -- MMART_DEFLT_ERR_EXS     ");
		//sb.append("\n      , ''  -- MMART_CMMT_ERR_EXS      ");
		//sb.append("\n      , ''  -- MMART_DATA_TYPE_ERR_EXS ");
		//sb.append("\n      , ''  -- MMART_DATA_LEN_ERR_EXS  ");
		//sb.append("\n      , ''  -- MMART_DATA_PNT_ERR_EXS  ");
		//sb.append("\n      , ''  -- MMARTL_COL_ERR_EXS      ");
		sb.append("\n   FROM (SELECT C.*, O.NAME AS O_NAME, T.NAME AS T_NAME, F.COLID AS F_COLID, F.KEYNO AS F_KEYNO, M.TEXT AS M_TEXT,substr(P.VALUE,1,200) AS P_VALUE    ");
		sb.append("\n    	  FROM		WAE_MS_OBJECTS O                                       ");
		sb.append("\n                  INNER JOIN WAE_MS_COLUMNS C                                       ");
		sb.append("\n                     ON O.DB_NM = C.DB_NM                                    ");
		sb.append("\n                    AND O.SCH_NM = C.SCH_NM                                      ");
		sb.append("\n                    AND O.ID = C.ID                                      ");
		sb.append("\n                    AND UPPER(O.NAME) NOT IN ('DTPROPERTIES')                                      ");
		sb.append("\n                  LEFT OUTER JOIN WAE_MS_TYPES T                                       ");
		sb.append("\n                     ON C.DB_NM = T.DB_NM                                  ");
		//sb.append("\n                    AND C.SCH_NM = T.SCH_NM                                      ");
//		sb.append("\n                    AND C.XUSERTYPE = T.XUSERTYPE                              ");
		sb.append("\n                    AND C.XTYPE = T.XTYPE                              ");
		sb.append("\n                    AND T.NAME != 'sysname'                              ");
		sb.append("\n                    LEFT OUTER JOIN WAE_MS_COMMENTS M                                        ");
		sb.append("\n                      ON C.DB_NM = M.DB_NM                                       ");
		sb.append("\n                     AND C.SCH_NM = M.SCH_NM                                       ");
		sb.append("\n                     AND C.CDEFAULT = M.ID                                       ");
		sb.append("\n                     AND M.COLID = 1                                       ");
		sb.append("\n                    LEFT OUTER JOIN (SELECT E.DB_NM, E.SCH_NM, E.ID, E.INDID, G.COLID, G.KEYNO                                         ");
		sb.append("\n                                       FROM WAE_MS_INDEXES E                                       ");
		sb.append("\n                                          , WAE_MS_INDEXKEYS G                                       ");
		sb.append("\n                                      WHERE G.DB_NM = E.DB_NM                                       ");
		sb.append("\n                                        AND G.SCH_NM = E.SCH_NM                                       ");
		sb.append("\n                                        AND G.ID = E.ID                                        ");
		sb.append("\n                                        AND G.INDID = E.INDID) F                                        ");
		sb.append("\n                      ON F.DB_NM = C.DB_NM                                       ");
		sb.append("\n                     AND F.SCH_NM = C.SCH_NM                                       ");
		sb.append("\n                     AND F.ID = C.ID                                       ");
		sb.append("\n                     AND F.COLID = C.COLID                                       ");
		sb.append("\n                     AND F.INDID = 1                                       ");
		sb.append("\n                   LEFT OUTER JOIN (                                       ");
		sb.append("\n                   	SELECT CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,NAME,VALUE,DB_NM,SCH_NM                                       ");
		sb.append("\n                   	FROM WAE_MS_EXTENDED_PROPERTIES                                       ");
		sb.append("\n                   	GROUP BY CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,NAME,VALUE,DB_NM,SCH_NM                                       ");
		sb.append("\n                   ) P                                       ");
		sb.append("\n                     ON P.MAJOR_ID = C.ID                                       ");
		sb.append("\n                    AND P.MINOR_ID = C.COLID                                       ");
		sb.append("\n                    AND P.DB_NM = C.DB_NM                                       ");
		sb.append("\n         WHERE (RTRIM(O.TYPE) NOT IN ('P', 'FN', 'TF', 'IF') OR (RTRIM(O.TYPE) IN ('TF', 'IF') AND C.C_NUMBER = 0))                                       ");
		sb.append("\n   ) A                                         ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                                 ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM       ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'     ");
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"' ");
		sb.append("\n    AND A.NAME IS NOT NULL            ");

		return setExecuteUpdate_Org(sb.toString());

	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, T.DB_CONN_TRG_LNM,S.DB_SCH_ID, S.DB_SCH_PNM ");
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
		sb.append("\n INSERT INTO WAT_DBC_IDX                                      ");
		sb.append("\n  (     DB_SCH_ID                                             ");
		sb.append("\n      , DBC_TBL_NM                                            ");
		sb.append("\n      , DBC_IDX_NM                                            ");
		sb.append("\n      , DB_CONN_TRG_ID                                        ");
		sb.append("\n      , DBC_IDX_KOR_NM                                        ");
		sb.append("\n      , VERS                                                  ");
		sb.append("\n      , REG_TYP                                               ");
		sb.append("\n      , REG_DTM                                               ");
		sb.append("\n      , REG_USER                                              ");
		sb.append("\n      , UPD_DTM                                               ");
		sb.append("\n      , UPD_USER                                              ");
		sb.append("\n      , DESCN                                                 ");
//		sb.append("\n      , DBC_TBL_SPAC_NM                                       ");
		sb.append("\n      , DBC_TBL_SPAC_NM                                       ");
		sb.append("\n      , DDL_IDX_ID                                            ");
		sb.append("\n      , PDM_IDX_ID                                            ");
		sb.append("\n      , PK_YN                                                 ");
		sb.append("\n      , UQ_YN                                                 ");
//		sb.append("\n      , COL_EACNT                                             ");
//		sb.append("\n      , IDX_SIZE                                              ");
		sb.append("\n      , COL_EACNT                                             ");
		sb.append("\n      , IDX_SIZE                                              ");
		sb.append("\n      , BF_IDX_EACNT                                          ");
		sb.append("\n      , BF_IDX_SIZE                                           ");
		sb.append("\n      , ANA_DTM                                               ");
		sb.append("\n      , CRT_DTM                                               ");
		sb.append("\n      , CHG_DTM                                               ");
		sb.append("\n      , IDX_DQ_EXP_YN                                         ");
		sb.append("\n      , SGMT_BYTE_SIZE                                        ");
		sb.append("\n      , DDL_IDX_CMPS_CONTS                                    ");
		sb.append("\n      , PDM_IDX_CMPS_CONTS                                    ");
		sb.append("\n      , DDL_IDX_ERR_EXS                                       ");
		sb.append("\n      , DDL_IDX_ERR_CD                                        ");
		sb.append("\n      , DDL_IDX_ERR_DESCN                                     ");
		sb.append("\n      , DDL_IDX_COL_ERR_EXS                                   ");
		sb.append("\n      , DDL_IDX_COL_ERR_CD                                    ");
		sb.append("\n      , DDL_IDX_COL_ERR_DESCN                                 ");
		sb.append("\n      , PDM_IDX_ERR_EXS                                       ");
		sb.append("\n      , PDM_IDX_ERR_CD                                        ");
		sb.append("\n      , PDM_IDX_ERR_DESCN                                     ");
		sb.append("\n      , PDM_IDX_COL_ERR_EXS                                   ");
		sb.append("\n      , PDM_IDX_COL_ERR_CD                                    ");
		sb.append("\n      , PDM_IDX_COL_ERR_DESCN                                 ");
		sb.append("\n      , DDL_IDX_EXTNC_EXS                                     ");
		sb.append("\n      , PDM_IDX_EXTNC_EXS  )                                  ");
		sb.append("\n SELECT S.DB_SCH_ID             AS DB_SCH_ID                  ");
		sb.append("\n      , A.O_NAME                AS DBC_TBL_NM                 ");
		sb.append("\n      , A.NAME                  AS DBC_IDX_NM                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID        AS DB_CONN_TRG_ID             ");
		sb.append("\n      , ''                      AS DBC_IDX_KOR_NM             ");
		sb.append("\n      , '1'                     AS VERS                       ");
		sb.append("\n      , NULL                    AS REG_TYP                    ");
		sb.append("\n      , SYSDATE                 AS REG_DTM                    ");
		sb.append("\n      , ''                      AS REG_USER                   ");
		sb.append("\n      , ''                      AS UPD_DTM                    ");
		sb.append("\n      , ''                      AS UPD_USER                   ");
		sb.append("\n      , ''                      AS DESCN                      ");
//		sb.append("\n      , A.TBL_SPAC_NM           AS DBC_TBL_SPAC_NM            ");
		sb.append("\n      , ''                      AS DBC_TBL_SPAC_NM            ");
		sb.append("\n      , ''                      AS DDL_IDX_ID                 ");
		sb.append("\n      , ''                      AS PDM_IDX_ID                 ");
		sb.append("\n      , A.IS_PK                 AS PK_YN                      ");
		sb.append("\n      , A.IS_UNIQUE             AS UQ_YN                      ");
//		sb.append("\n      , E.COL_CNT               AS COL_EACNT                  ");
//		sb.append("\n      , C.BYTES                 AS IDX_SIZE                   ");
		sb.append("\n      , ''                      AS COL_EACNT                  ");
		sb.append("\n      , ''                      AS IDX_SIZE                   ");
		sb.append("\n      , ''                      AS BF_IDX_EACNT               ");
		sb.append("\n      , ''                      AS BF_IDX_SIZE                ");
		sb.append("\n      , ''                      AS ANA_DTM                    ");
		sb.append("\n      , ''                      AS CRT_DTM                    ");
		sb.append("\n      , ''                      AS CHG_DTM                    ");
		sb.append("\n      , ''                      AS IDX_DQ_EXP_YN              ");
		sb.append("\n      , ''                      AS SGMT_BYTE_SIZE             ");
		sb.append("\n      , ''                      AS DDL_IDX_CMPS_CONTS         ");
		sb.append("\n      , ''                      AS PDM_IDX_CMPS_CONTS         ");
		sb.append("\n      , ''                      AS DDL_IDX_ERR_EXS            ");
		sb.append("\n      , ''                      AS DDL_IDX_ERR_CD             ");
		sb.append("\n      , ''                      AS DDL_IDX_ERR_DESCN          ");
		sb.append("\n      , ''                      AS DDL_IDX_COL_ERR_EXS        ");
		sb.append("\n      , ''                      AS DDL_IDX_COL_ERR_CD         ");
		sb.append("\n      , ''                      AS DDL_IDX_COL_ERR_DESCN      ");
		sb.append("\n      , ''                      AS PDM_IDX_ERR_EXS            ");
		sb.append("\n      , ''                      AS PDM_IDX_ERR_CD             ");
		sb.append("\n      , ''                      AS PDM_IDX_ERR_DESCN          ");
		sb.append("\n      , ''                      AS PDM_IDX_COL_ERR_EXS        ");
		sb.append("\n      , ''                      AS PDM_IDX_COL_ERR_CD         ");
		sb.append("\n      , ''                      AS PDM_IDX_COL_ERR_DESCN      ");
		sb.append("\n      , ''                      AS DDL_IDX_EXTNC_EXS          ");
		sb.append("\n      , ''                      AS PDM_IDX_EXTNC_EXS          ");
		sb.append("\n   FROM ( SELECT X.*, O.NAME AS O_NAME                        ");
		sb.append("\n            FROM WAE_MS_OBJECTS O                             ");
		sb.append("\n                 , WAE_MS_INDEXES X                           ");
		sb.append("\n           WHERE RTRIM(O.TYPE) = 'U'                          ");
		sb.append("\n               AND O.NAME NOT IN ('DTPROPERTIES')             ");
		sb.append("\n               AND X.DB_NM = O.DB_NM                          ");
		sb.append("\n                AND X.SCH_NM = O.SCH_NM                       ");
		sb.append("\n                AND X.ID = O.ID                               ");
		sb.append("\n               AND X.INDID > 0                                ");
		sb.append("\n               AND X.INDID < 255                              ");
		sb.append("\n               AND X.STATUS32 = 0                             ");
		sb.append("\n   ) A                                                        ");
		sb.append("\n  INNER JOIN (                                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                                                         ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                           ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                               ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'                             ");
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"'                         ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL                                      ");
		sb.append("\n (      DB_SCH_ID                        ");
		sb.append("\n      , DBC_TBL_NM                       ");
		sb.append("\n      , DBC_IDX_COL_NM                   ");
		sb.append("\n      , DBC_IDX_NM                       ");
		sb.append("\n      , DBC_IDX_COL_KOR_NM               ");
		sb.append("\n      , VERS                             ");
		sb.append("\n      , REG_TYP                          ");
		sb.append("\n      , REG_DTM                          ");
		sb.append("\n      , REG_USER                         ");
		sb.append("\n      , UPD_DTM                          ");
		sb.append("\n      , UPD_USER                         ");
		sb.append("\n      , DESCN                            ");
		sb.append("\n      , DDL_IDX_COL_ID                   ");
		sb.append("\n      , PDM_IDX_COL_ID                   ");
		sb.append("\n      , ORD                              ");
		sb.append("\n      , SORT_TYPE                        ");
		sb.append("\n      , DATA_TYPE                        ");
		sb.append("\n      , DATA_PNUM                        ");
		sb.append("\n      , DATA_LEN                         ");
		sb.append("\n      , DATA_PNT                         ");
		sb.append("\n      , IDXCOL_DQ_EXP_YN                 ");
		sb.append("\n      , DDL_IDX_COL_LNM_ERR_EXS          ");
		sb.append("\n      , DDL_IDX_COL_ORD_ERR_EXS          ");
		sb.append("\n      , DDL_IDX_COL_SORT_TYPE_ERR_EXS    ");
		sb.append("\n      , DDL_IDX_COL_EXTNC_EXS            ");
		sb.append("\n      , DDL_IDX_COL_ERR_EXS              ");
		sb.append("\n      , DDL_IDX_COL_ERR_CONTS            ");
		sb.append("\n      , PDM_IDX_COL_LNM_ERR_EXS          ");
		sb.append("\n      , PDM_IDX_COL_ORD_ERR_EXS          ");
		sb.append("\n      , PDM_IDX_COL_SORT_TYPE_ERR_EXS    ");
		sb.append("\n      , PDM_IDX_COL_EXTNC_EXS            ");
		sb.append("\n      , PDM_IDX_COL_ERR_EXS              ");
		sb.append("\n      , PDM_IDX_COL_ERR_CONTS     )      ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                        ");
		sb.append("\n      , A.O_NAME              AS DBC_TBL_NM                       ");
		sb.append("\n      , A.NAME                AS DBC_IDX_COL_NM                   ");
		sb.append("\n      , A.I_NAME              AS DBC_IDX_NM                       ");
		sb.append("\n      , ''                    AS DBC_IDX_COL_KOR_NM               ");
		sb.append("\n      , '1'                   AS VERS                             ");
		sb.append("\n      , NULL                  AS REG_TYP                          ");
		sb.append("\n      , SYSDATE               AS REG_DTM                          ");
		sb.append("\n      , ''                    AS REG_USER                         ");
		sb.append("\n      , ''                    AS UPD_DTM                          ");
		sb.append("\n      , ''                    AS UPD_USER                         ");
		sb.append("\n      , ''                    AS DESCN                            ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_ID                   ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_ID                   ");
		sb.append("\n      , A.I_KEYNO             AS ORD                              ");
//		sb.append("\n      , SORT_TYPE             AS SORT_TYPE                        ");
//		sb.append("\n      , C.DATA_TYPE           AS DATA_TYPE                        ");
//		sb.append("\n      , C.DATA_PNUM           AS DATA_PNUM                        ");
//		sb.append("\n      , C.DATA_LEN            AS DATA_LEN                         ");
//		sb.append("\n      , C.DATA_PNT            AS DATA_PNT                         ");
		sb.append("\n      , ''                    AS SORT_TYPE                        ");
		sb.append("\n      , ''                    AS DATA_TYPE                        ");
		sb.append("\n      , ''                    AS DATA_PNUM                        ");
		sb.append("\n      , ''                    AS DATA_LEN                         ");
		sb.append("\n      , ''                    AS DATA_PNT                         ");
		sb.append("\n      , ''                    AS IDXCOL_DQ_EXP_YN                 ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_LNM_ERR_EXS          ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_ORD_ERR_EXS          ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_SORT_TYPE_ERR_EXS    ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_EXTNC_EXS            ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_ERR_EXS              ");
		sb.append("\n      , ''                    AS DDL_IDX_COL_ERR_CONTS            ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_LNM_ERR_EXS          ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_ORD_ERR_EXS          ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_SORT_TYPE_ERR_EXS    ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_EXTNC_EXS            ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_ERR_EXS              ");
		sb.append("\n      , ''                    AS PDM_IDX_COL_ERR_CONTS            ");
		sb.append("\n   FROM (   SELECT TC.*, O.NAME AS O_NAME, IDX.NAME AS I_NAME, IC.KEYNO AS I_KEYNO        ");
		sb.append("\n            FROM WAE_MS_INDEXKEYS IC         ");
		sb.append("\n               , WAE_MS_INDEXES IDX         ");
		sb.append("\n               , WAE_MS_OBJECTS O         ");
		sb.append("\n               , WAE_MS_COLUMNS TC         ");
		sb.append("\n                 LEFT OUTER JOIN WAE_MS_COMMENTS CC         ");
		sb.append("\n                   ON TC.DB_NM = CC.DB_NM         ");
		sb.append("\n                   AND TC.SCH_NM = CC.SCH_NM         ");
		sb.append("\n                   AND TC.ID = CC.ID         ");
		sb.append("\n                  AND TC.C_NUMBER = CC.C_NUMBER         ");
		sb.append("\n                  AND TC.COLID = CC.COLID         ");
		sb.append("\n           WHERE IC.DB_NM = IDX.DB_NM         ");
		sb.append("\n              AND IC.SCH_NM = IDX.SCH_NM         ");
		sb.append("\n              AND IC.ID = IDX.ID         ");
		sb.append("\n             AND IC.INDID = IDX.INDID         ");
		sb.append("\n             AND IDX.DB_NM = TC.DB_NM         ");
		sb.append("\n              AND IDX.SCH_NM = TC.SCH_NM         ");
		sb.append("\n              AND IDX.ID = TC.ID         ");
		sb.append("\n             AND IDX.INDID > 0         ");
		sb.append("\n             AND IDX.INDID < 255         ");
		sb.append("\n             AND IDX.STATUS32 = 0         ");
		sb.append("\n             AND IC.DB_NM = TC.DB_NM         ");
		sb.append("\n              AND IC.SCH_NM = TC.SCH_NM         ");
		sb.append("\n              AND IC.COLID = TC.COLID         ");
		sb.append("\n             AND RTRIM(O.TYPE) = 'U'         ");
		sb.append("\n             AND IDX.DB_NM = O.DB_NM         ");
		sb.append("\n              AND IDX.SCH_NM = O.SCH_NM         ");
		sb.append("\n              AND IDX.ID = O.ID         ");
		sb.append("\n   ) A           ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'     ");
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"'                                        ");

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
		sb.append("\n   FROM WAE_ORA_CND A   ");
		sb.append("\n  INNER JOIN (                 ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM          ");
		sb.append("\n  INNER JOIN WAE_ORA_DB B                  ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                   ");
		sb.append("\n  INNER JOIN (SELECT DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ");
		sb.append("\n                           , COUNT(CNST_CND_COL_NM) AS COL_CNT  ");
		sb.append("\n                    FROM WAE_ORA_CND_COL  ");
		sb.append("\n                   GROUP BY DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ) C ");
		sb.append("\n      ON A.DB_NM = C.DB_NM ");
		sb.append("\n     AND A.SCH_NM = C.SCH_NM ");
		sb.append("\n     AND A.TBL_NM = C.TBL_NM ");
		sb.append("\n     AND A.CNST_CND_NM = C.CNST_CND_NM ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'     ");

		

		
		
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
		sb.append("\n   FROM WAE_ORA_CND_COL A                                    ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_ORA_DB B                                    ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'     ");

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
		sb.append("\n   FROM WAE_ORA_TBL_SPAC A                                   ");
		sb.append("\n  INNER JOIN WAE_ORA_DB B                                    ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
		sb.append("\n    WHERE A.DB_NM = '"+dbKName+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

}
