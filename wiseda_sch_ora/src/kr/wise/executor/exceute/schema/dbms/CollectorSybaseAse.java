/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorSybaseAse.java
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

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorSybaseAse.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : moonsungeun
 * 6. 작성일   : 2015. 7. 17. 
 * </PRE>
 */
public class CollectorSybaseAse{

	private static final Logger logger = Logger.getLogger(CollectorSybaseAse.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private TargetDbmsDM targetDbmsDM;
	private String dbConnTrgPnm = null;
	private String dbConnTrgId = null;
	private String dbType = null;
	private String dbVer = null;
	
	private String[] sTable = null;
	private String[] sLoginame = null;
	private String[] sType = null;

	private int execCnt = 10000;


	public CollectorSybaseAse() {

	}

	/** insomnia */
	public CollectorSybaseAse(Connection source, Connection target,	TargetDbmsDM targetDbmsDM) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDbmsDM = targetDbmsDM;
	}

	/**  insomnia
	 * @return */
	public boolean doProcess() throws Exception {
		boolean result = false;
		this.con_org.setAutoCommit(false);
		this.dbConnTrgId = targetDbmsDM.getDbms_id();
		this.dbConnTrgPnm = targetDbmsDM.getDbms_enm();
		this.dbType = targetDbmsDM.getDbms_type_cd();

		String sp = "   ";
		int p = 0;
		int cnt = 0;
		
		logger.debug(" dbConnTrgId : "+dbConnTrgId + "  dbConnTrgPnm : "+dbConnTrgPnm);
		//스키마 정보를 모두 삭제한다.
		deleteSchema();
/*
			//첫번째 스키마 일때만 처리한다.
			if(dbCnt == 0) {
				//스키마 정보를 모두 삭제한다.
				deleteSchema();
				//스키마 정보를 추가한다. DB인스턴스 별로 한번만 실행한다.
				cnt = insertMetaDATABASES();
				logger.debug(sp + (++p) + ". insertMetaDATABASES " + cnt + " OK!!");
				
				//Meta_DATABASES block_size update----------------------------------
//				cnt = updateMetaDATABASES_ASE_BLOCK_SIZE();
//				logger.debug(sp + (++p) + ". updateMetaDATABASES_ASE_BLOCK_SIZE " + cnt + " OK!!");

				//DBA_TABLESPACES ---------------------------------------------------
//				cnt = insertDBA_TABLESPACES();
//				logger.debug(sp + (++p) + ". insertDBA_TABLESPACES " + cnt + " OK!!");

				con_org.commit();
			}
			*/


//			logger.debug(" null : "+null);
			//SYSUSERS --------------------------------------------------------
//			cnt = insertSYSUSERS();
//			logger.debug(sp + (++p) + ". insertSYSUSERS " + cnt + " OK!!");

			//SYSLOGINS -------------------------------------------------------
//			cnt = insertSYSLOGINS();
//			logger.debug(sp + (++p) + ". insertSYSLOGINS " + cnt + " OK!!");

			//SYSPROTECTS ---------------------------------------------------
//			cnt = insertSYSPROTECTS();
//			logger.debug(sp + (++p) + ". insertSYSPROTECTS " + cnt + " OK!!");

			//SYSOBJECTS ---------------------------------------------------
			cnt = insertSYSOBJECTS();
			logger.debug(sp + (++p) + ". insertSYSOBJECTS " + cnt + " OK!!");

			//SYSCOMMENTS ---------------------------------------------------
			cnt = insertSYSCOMMENTS();
			logger.debug(sp + (++p) + ". insertSYSCOMMENTS " + cnt + " OK!!");

			//SYSTABSTATS ---------------------------------------------------
//			cnt = insertSYSTABSTATS();
//			logger.debug(sp + (++p) + ". insertSYSTABSTATS " + cnt + " OK!!");

			//SYSCOLUMNS ---------------------------------------------------
			cnt = insertSYSCOLUMNS();
			logger.debug(sp + (++p) + ". insertSYSCOLUMNS " + cnt + " OK!!");
			
			//SYSKEYS ---------------------------------------------------
//			cnt = insertSYSKEYS();
//			logger.debug(sp + (++p) + ". insertSYSKEYS " + cnt + " OK!!");

			//SYSINDEXES ---------------------------------------------------
			cnt = insertSYSINDEXES();
			logger.debug(sp + (++p) + ". insertSYSINDEXES " + cnt + " OK!!");

			//SYSINDEXES_COLUMN ---------------------------------------------------
			cnt = insertSYSINDEXES_COLUMN();
			logger.debug(sp + (++p) + ". insertSYSINDEXES_COLUMN " + cnt + " OK!!");
			
			//FOREIGNKEY ---------------------------------------------------
//			cnt = insertFOREIGNKEY();
//			logger.debug(sp + (++p) + ". insertFOREIGNKEY " + cnt + " OK!!");
			
			//FOREIGNKEY_COLUMN ---------------------------------------------------
//			cnt = insertFOREIGNKEY_COLUMN();
//			logger.debug(sp + (++p) + ". insertFOREIGNKEY_COLUMN " + cnt + " OK!!");
			
			//SYSATTRIBUTES ---------------------------------------------------
//			cnt = insertSYSATTRIBUTES();
//			logger.debug(sp + (++p) + ". insertSYSATTRIBUTES " + cnt + " OK!!");
			
			//SYSSEGMENTS ---------------------------------------------------
//			cnt = insertSYSSEGMENTS();
//			logger.debug(sp + (++p) + ". insertSYSSEGMENTS " + cnt + " OK!!");
			
			//SYSTYPES ---------------------------------------------------
			cnt = insertSYSTYPES();
			logger.debug(sp + (++p) + ". insertSYSTYPES " + cnt + " OK!!");
			
			//SP_SPACEUSED ---------------------------------------------------
//			cnt = insertSP_SPACEUSED();
//			logger.debug(sp + (++p) + ". insertSP_SPACEUSED " + cnt + " OK!!");

			this.con_org.commit();

//		}

		result = true;

		return result;

	}

	/** @return insomnia
	 * @throws Exception */
	private int insertDBA_PROCEDURES() throws Exception {
		StringBuffer sb = new StringBuffer();

		if(dbVer.equals("")) {
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
		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM");
		sb.append("\n  FROM DBA_PROCEDURES");
//		sb.append("\n WHERE OWNER = '").append(null).append("'");

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
			sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM");
			sb.append("\n  FROM DBA_OBJECTS");
			sb.append("\n WHERE 1 = 1 ");
//			sb.append("\n WHERE OWNER ='").append(null).append("'");
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
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
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
	private int insertSYSKEYS() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT id, depid, keycnt, size, type  ");
		sb.append("\n      , key1, key2, key3, key4, key5           ");
		sb.append("\n      , key6, key7, key8, depkey1, depkey2        ");
		sb.append("\n      , depkey3, depkey4, depkey5, depkey6, depkey7            ");
		sb.append("\n      , depkey8, spare1        ");
		sb.append("\n   FROM ").append("master..syskeys           ");
//		sb.append("\n   FROM ").append(dbConnTrgPnm).append("..syskeys           ");

		getResultSet(sb.toString());

		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_ASE_KEYS ");
		sb.append("\n  (ID, DEPID, KEYCNT, C_SIZE, TYPE   ");
		sb.append("\n  , KEY1, KEY2, KEY3, KEY4, KEY5 ");
		sb.append("\n  , KEY6, KEY7, KEY8, DEPKEY1, DEPKEY2  ");
		sb.append("\n  , DEPKEY3, DEPKEY4, DEPKEY5, DEPKEY6, DEPKEY7  ");
		sb.append("\n  , DEPKEY8, SPARE1, DB_NM, SCH_NM)   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?            ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?               ");
		sb.append("\n  , ?, ?, ?, ?)                   ");


		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("keycnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("size"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("type"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key5"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key6"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key7"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("key8"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey5"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey6"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey7"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("depkey8"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("spare1"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
//	private int insertDBA_TABLESPACES() throws SQLException
//	{
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n SELECT                                  ");
//		sb.append("\n --      ,데이터베이스명                 ");
//		sb.append("\n      '").append(dbConnTrgPnm).append("' AS SCH_NM");
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
//		sb.append("\n       ,PLUGGED_IN                       ");
//		sb.append("\n   FROM DBA_TABLESPACES                  ");
////		sb.append("\n UNION ALL                               ");
////		sb.append("\n SELECT                                  ");
////		sb.append("\n --      ,데이터베이스명                 ");
////		sb.append("\n      '").append(dbConnTrgPnm).append("' AS DB_NM");
////		sb.append("\n       ,TABLESPACE_NAME                   ");
////		sb.append("\n       ,INITIAL_EXTENT                   ");
////		sb.append("\n       ,NEXT_EXTENT                      ");
////		sb.append("\n       ,MIN_EXTENTS                      ");
////		sb.append("\n       ,MAX_EXTENTS                      ");
////		sb.append("\n       ,PCT_INCREASE                     ");
////		sb.append("\n       ,MIN_EXTLEN                       ");
////		sb.append("\n       ,STATUS                           ");
////		sb.append("\n       ,CONTENTS                         ");
////		sb.append("\n       ,LOGGING                          ");
////		sb.append("\n       ,EXTENT_MANAGEMENT                ");
////		sb.append("\n       ,ALLOCATION_TYPE                  ");
////		sb.append("\n       ,NULL AS PLUGGED_IN               ");
////		sb.append("\n   FROM USER_TABLESPACES                 ");
//
//		getResultSet(sb.toString());
//		sb = new StringBuffer();
//
//		sb.append("\nINSERT INTO WAE_ORA_TBL_SPAC (");
//		sb.append("\n   DB_NM               ");
//		sb.append("\n       ,TBL_SPAC_NM                   ");
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
//		sb.append("\n       ,PLUGGED_IN                       ");
//		sb.append("\n) VALUES (                       ");
//		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,           ");
//		sb.append("\n  ?,?,?,?                      ");
//		sb.append("\n)                                ");
//
//		int cnt = 0;
//		if( rs != null ) {
//			pst_org = con_org.prepareStatement(sb.toString());
//			pst_org.clearParameters();
//
//			int rsCnt = 1;
//			int rsGetCnt = 1;
//
//			while(rs.next())
//			{
//				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
//				pst_org.setString(rsGetCnt++, rs.getString("TABLESPACE_NAME"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INITIAL_EXTENT"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NEXT_EXTENT"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTENTS"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAX_EXTENTS"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PCT_INCREASE"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MIN_EXTLEN"));
//				pst_org.setString(rsGetCnt++, rs.getString("STATUS"));
//				pst_org.setString(rsGetCnt++, rs.getString("CONTENTS"));
//				pst_org.setString(rsGetCnt++, rs.getString("LOGGING"));
//				pst_org.setString(rsGetCnt++, rs.getString("EXTENT_MANAGEMENT"));
//				pst_org.setString(rsGetCnt++, rs.getString("ALLOCATION_TYPE"));
//				pst_org.setString(rsGetCnt++, rs.getString("PLUGGED_IN"));
//
//				getSaveResult(rsCnt);
//
//				if(rsCnt == execCnt)
//				{
//					executeBatch(true);
//					rsCnt = 0;
//				}
//
//				rsCnt++;
//				rsGetCnt = 1;
//				cnt++;
//			}
//			executeBatch();
//		}
//
//		return cnt;
//
//	}

	/**
	 * DBC SYSINDEXES_COLUMN 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSINDEXES_COLUMN() throws SQLException, Exception
	{
		int cnt = 0;
		String union_string = "\n UNION ALL";
		String query_tgt = "";

		StringBuffer sb = new StringBuffer();

		for(int i = 1; i < 17; i++)
		{
			query_tgt += "\n select 	"
				+ "\n          u.name of_schema	"
				+ "\n        , o.name of_table	"
				+ "\n        , i.name of_index "
				+ "\n        , " + i + " as idx_col_seq "
				+ "\n        , index_col(o.name,indid, " + i + ")  idx_col	"
				+ "\n     FROM master..sysindexes i, master..sysobjects o , master..sysusers u "
//				+ "\n     FROM " + dbConnTrgPnm + "..sysindexes i," + dbConnTrgPnm + "..sysobjects o , " + dbConnTrgPnm + "..sysusers u "
				+ "\n     where i.id = o.id "
				+ "\n     and o.uid = u.uid   "
				+ "\n     and i.indid > 0   "
				+ "\n     and i.indid < 255   "
				+ "\n	  and index_col(o.name,indid, " + i + ") is not null"
				+ "\n     and o.type = 'U'   " ;

			if( i < 16) query_tgt += union_string ;

		}
		
//System.out.println(sb.toString());
//		getResultSet(sb.toString());
		getResultSet(query_tgt);

		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_INDEXES_COLUMN   ");
		sb.append("\n  ( IDX_COL_SEQ,IDX_COL, DB_NM, SCH_NM, OF_SCHEMA , OF_TABLE , OF_INDEX)   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?)                      ");
		
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("idx_col_seq"));
				pst_org.setString(rsGetCnt++, rs.getString("idx_col"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, rs.getString("of_schema"));
				pst_org.setString(rsGetCnt++, rs.getString("of_table"));
				pst_org.setString(rsGetCnt++, rs.getString("of_index"));

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
	 * DBC FOREIGNKEY_COLUMN 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertFOREIGNKEY_COLUMN() throws SQLException, Exception
	{
		int cnt = 0;
		String union_string = "\n UNION ALL";
		String query_tgt = "";
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 1; i < 17; i++)
		{
			query_tgt += "\n select	"
					+ "\n        u.name fk_creator,	"
					+ "\n        o.name fk_table_nm,	"
					+ "\n        c.name fk_nm,		"
					+ "\n        v.name ref_creator,	"
					+ "\n        p.name ref_table_nm,	"
					+ "\n        " + i + " fk_col_seq, 		"
					+ "\n        col_name(o.id, r.fokey" + i + ") fokey, 		"
					+ "\n        col_name(p.id, r.refkey" + i + ") refkey		"
					+ "\n     FROM "
					+ "\n                 master..sysobjects c	"
					+ "\n           join  master..sysconstraints s  on (s.constrid = c.id)	"
					+ "\n           join  master..sysobjects o on (o.id = s.tableid )	"
					+ "\n           join  master..sysusers u on (u.uid = o.uid )	"
					+ "\n           join  master..sysreferences r on (r.tableid = o.id and r.constrid = s.constrid)	"
					+ "\n           join  master..sysobjects p on (p.id = r.reftabid)	"
					+ "\n           join  master..sysusers v on (v.uid = p.uid)	"
//					+ "\n           " + dbConnTrgPnm + "..sysobjects c	"
//					+ "\n           join " + dbConnTrgPnm + "..sysconstraints s  on (s.constrid = c.id)	"
//					+ "\n           join " + dbConnTrgPnm + "..sysobjects o on (o.id = s.tableid )	"
//					+ "\n           join " + dbConnTrgPnm + "..sysusers u on (u.uid = o.uid )	"
//					+ "\n           join " + dbConnTrgPnm + "..sysreferences r on (r.tableid = o.id and r.constrid = s.constrid)	"
//					+ "\n           join " + dbConnTrgPnm + "..sysobjects p on (p.id = r.reftabid)	"
//					+ "\n           join " + dbConnTrgPnm + "..sysusers v on (v.uid = p.uid)	"
					+ "\n        where c.type = 'RI'	"
					+ "\n          and  col_name(o.id, r.fokey" + i + ") is not null	";
			
			if( i < 16) query_tgt += union_string ;
			
		}
		
//System.out.println(sb.toString());
//		getResultSet(sb.toString());
		getResultSet(query_tgt);
		
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_ASE_FOREIGNKEY_COLUMN   ");
		sb.append("\n  ( OF_FK_SCHEMA, OF_FK_TABLE, OF_FK, OF_REF_SCHEMA   ");
		sb.append("\n  , OF_REF_TABLE, FK_COL_SEQ, FOKEY, REFKEY, DB_NM, SCH_NM)    ");
		sb.append("\n  VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )    ");
		
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("fk_creator"));
				pst_org.setString(rsGetCnt++, rs.getString("fk_table_nm"));
				pst_org.setString(rsGetCnt++, rs.getString("fk_nm"));
				pst_org.setString(rsGetCnt++, rs.getString("ref_creator"));
				pst_org.setString(rsGetCnt++, rs.getString("ref_table_nm"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("fk_col_seq"));
				pst_org.setString(rsGetCnt++, rs.getString("fokey"));
				pst_org.setString(rsGetCnt++, rs.getString("refkey"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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

		sb.append("\n SELECT i.crdate, i.id, i.doampg, i.ioampg, i.oampgtrips  ");
		sb.append("\n    , i.ipgtrips, i.first, i.root, i.distribution, i.base_partition       ");
		sb.append("\n    , i.identitygap, i.indid, i.status2, i.usagecnt, i.segment ");
		sb.append("\n    , i.status, i.maxrowsperpage, i.minlen, i.maxlen, i.maxirow   ");
		sb.append("\n    , i.keycnt, i.fill_factor, i.res_page_gap, i.exp_rowsize, i.name   ");
		sb.append("\n    , i.soid, i.csid, i.keys1, i.keys2, i.keys3  ");
		sb.append("\n    , case (i.status&2048) when 2048 then 'YES' else 'NO' end  IS_PK_INDEX   ");
		sb.append("\n    , case when (i.status & 2)<>0 then 'YES' else 'NO' end IS_UNIQUE  ");
		sb.append("\n    , 			case (o.sysstat2&131072) when 131072 then case (i.status2&512) when 512 then 'YES' else 'NO' end else  ");
		sb.append("\n      					case (i.status&16) when 16 then 'YES' else 'NO' end end IS_CLUSTERED        ");
		sb.append("\n    ,	o.name OF_TABLE     ");
		sb.append("\n    FROM ").append("master..sysindexes i,").append("master..sysobjects o ");
//		sb.append("\n    FROM ").append(dbConnTrgPnm).append("..sysindexes i,").append(dbConnTrgPnm).append("..sysobjects o ");
		sb.append("\n    where i.id = o.id  ");
		sb.append("\n    and i.indid > 0     ");
		sb.append("\n    and i.indid < 255       ");
		sb.append("\n    and o.type = 'U'       ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_INDEXES ");
		sb.append("\n  (CRDATE, ID, DOAMPG, IOAMPG, OAMPGTRIPS    ");
		sb.append("\n     , IPGTRIPS, FIRST, ROOT, DISTRIBUTION, BASE_PARTITION   ");
		sb.append("\n     , IDENTITYGAP, INDID, STATUS2, USAGECNT, SEGMENT  ");
		sb.append("\n     , STATUS, MAXROWSPERPAGE, MINLEN, MAXLEN, MAXIROW ");
		sb.append("\n     , KEYCNT, FILL_FACTOR, RES_PAGE_GAP, EXP_ROWSIZE, NAME  ");
		sb.append("\n     , SOID, CSID, KEYS1, KEYS2, KEYS3  ");
		sb.append("\n     , IS_PK_INDEX, IS_UNIQUE, IS_CLUSTERED, DB_NM, SCH_NM, OF_TABLE)   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?   ");
		sb.append("\n     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?   ");
		sb.append("\n     , ?, ?, ?, ?, ?, ?)      ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("crdate"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("doampg"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ioampg"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("oampgtrips"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ipgtrips"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("first"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("root"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("distribution"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("base_partition"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("identitygap"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("indid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("usagecnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("segment"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("maxrowsperpage"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("minlen"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("maxlen"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("maxirow"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("keycnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("fill_factor"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("res_page_gap"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("exp_rowsize"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("soid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("csid"));
				pst_org.setString(rsGetCnt++, rs.getString("keys1"));
				pst_org.setString(rsGetCnt++, rs.getString("keys2"));
				pst_org.setString(rsGetCnt++, rs.getString("keys3"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_PK_INDEX"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_UNIQUE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_CLUSTERED"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, rs.getString("OF_TABLE"));

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
	 * DBC FOREIGNKEY 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertFOREIGNKEY() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n select u.name fk_creator, o.name fk_table_nm, c.name fk_nm, v.name ref_creator, p.name ref_table_nm, c.crdate crdate ");
		sb.append("\n FROM ").append("master..sysobjects c ");
		sb.append("\n    join ").append("master..sysconstraints s  on (s.constrid = c.id) ");
		sb.append("\n    join ").append("master..sysobjects o on (o.id = s.tableid )  ");
		sb.append("\n    join ").append("master..sysusers u on (u.uid = o.uid )   ");
		sb.append("\n    join ").append("master..sysreferences r on (r.tableid = o.id and r.constrid = s.constrid)  ");
		sb.append("\n    join ").append("master..sysobjects p on (p.id = r.reftabid)  ");
		sb.append("\n    join ").append("master..sysusers v on (v.uid = p.uid)  ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysconstraints s  on (s.constrid = c.id) ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysobjects o on (o.id = s.tableid )  ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysusers u on (u.uid = o.uid )   ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysreferences r on (r.tableid = o.id and r.constrid = s.constrid)  ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysobjects p on (p.id = r.reftabid)  ");
//		sb.append("\n    join ").append(dbConnTrgPnm).append("..sysusers v on (v.uid = p.uid)  ");
		sb.append("\n  where c.type = 'RI'  ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_FOREIGNKEY ");
		sb.append("\n  (CRDATE, OF_FK_SCHEMA, OF_FK_TABLE, FK_NM, OF_REF_SCHEMA    ");
		sb.append("\n     , OF_REF_TABLE, DB_NM, SCH_NM)   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ? )  ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("crdate"));
				pst_org.setString(rsGetCnt++, rs.getString("fk_creator"));
				pst_org.setString(rsGetCnt++, rs.getString("fk_table_nm"));
				pst_org.setString(rsGetCnt++, rs.getString("fk_nm"));
				pst_org.setString(rsGetCnt++, rs.getString("ref_creator"));
				pst_org.setString(rsGetCnt++, rs.getString("ref_table_nm"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC SYSATTRIBUTES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSATTRIBUTES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT object_type, image_value, object, object_info1, object_info2 ");
		sb.append("\n    , object_info3, class, attribute, text_value, object_cinfo ");
		sb.append("\n    , char_value, comments ");
		sb.append("\n  FROM ").append("master..sysattributes  ");
//		sb.append("\n  FROM ").append(dbConnTrgPnm).append("..sysattributes  ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_ATTRIBUTES ");
		sb.append("\n  (OBJECT_TYPE, IMAGE_VALUE, OBJECT, OBJECT_INFO1, OBJECT_INFO2   ");
		sb.append("\n     , OBJECT_INFO3, CLASS, ATTRIBUTE, TEXT_VALUE, OBJECT_CINFO   ");
		sb.append("\n     , CHAR_VALUE, COMMENTS, DB_NM, SCH_NM)   ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n     , ?, ?, ?, ?)  ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("object_type"));
				pst_org.setString(rsGetCnt++, rs.getString("image_value"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("object"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("object_info1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("object_info2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("object_info3"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("class"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("attribute"));
				pst_org.setString(rsGetCnt++, rs.getString("text_value"));
				pst_org.setString(rsGetCnt++, rs.getString("object_cinfo"));
				pst_org.setString(rsGetCnt++, rs.getString("char_value"));
				pst_org.setString(rsGetCnt++, rs.getString("comments"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC SYSSEGMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSSEGMENTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT segment, status, name ");
		sb.append("\n FROM ").append("master..syssegments ");
//		sb.append("\n FROM ").append(dbConnTrgPnm).append("..syssegments ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_SEGMENTS ");
		sb.append("\n  (SEGMENT, STATUS, NAME, DB_NM, SCH_NM)   ");
		sb.append("\n VALUES (?, ?, ?, ?, ?)   ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("segment"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
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

		sb.append("\n SELECT variable, allownulls, uid, length, tdefault ");
		sb.append("\n    , domain, xtypeid, xdbid, accessrule, usertype ");
		sb.append("\n    , name, type, prec, scale, ident ");
		sb.append("\n    , hierarchy ");
		sb.append("\n FROM ").append("master..systypes ");
//		sb.append("\n FROM ").append(dbConnTrgPnm).append("..systypes ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_TYPES ");
		sb.append("\n  (VARIABLE, ALLOWNULLS, C_UID, LENGTH, TDEFAULT   ");
		sb.append("\n      , DOMAIN, XTYPEID, XDBID, ACCESSRULE, USERTYPE   ");
		sb.append("\n      , NAME, TYPE, PREC, SCALE, IDENT   ");
		sb.append("\n      , HIERARCHY, DB_NM, SCH_NM)   ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?   ");
		sb.append("\n   , ?, ?, ?, ?, ?, ?, ?, ?)   ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("variable"));
				pst_org.setString(rsGetCnt++, rs.getString("allownulls"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("uid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("length"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("tdefault"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("domain"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("xtypeid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("xdbid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("accessrule"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("usertype"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("type"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("prec"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("scale"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ident"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("hierarchy"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC SP_SPACEUSED 저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSP_SPACEUSED()
	{
		String query_org = "";
		String query_tgt = "";
		
		int cnt = 0;

		for(int i = 0; i < sTable.length; i++)
		{
			if(sType[i].toUpperCase().trim().equals("U"))
			{
				try
				{
					if(sLoginame[i].equals("") || sLoginame[i].equals("sa")) {
						query_tgt = "  master..sp_spaceused " + "'" +sTable[i] + "'";
//						query_tgt = "  " + dbConnTrgPnm + "..sp_spaceused " + "'" +sTable[i] + "'";
					} else {
						query_tgt = "  master." + sLoginame[i] + "." + "sp_spaceused " + "'" + sLoginame[i] + "."+ sTable[i] +"'";
//						query_tgt = "  " + dbConnTrgPnm + "." + sLoginame[i] + "." + "sp_spaceused " + "'" + sLoginame[i] + "."+ sTable[i] +"'";
					}

					//System.out.println("insertSP_SPACEUSED["+i+"]:" + query_tgt);
					
					getResultSet(query_tgt);

					query_org = "INSERT INTO WAE_ASE_SP_SPACEUSED "
							+ " (NAME, ROWTOTAL, RESERVED, DATA, INDEX_SIZE "
							+ "\n        , UNUSED, DB_NM, SCH_NM, OF_SCHEMA) "
							+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

					if (rs != null)
					{
						pst_org = con_org.prepareStatement(query_org);
						pst_org.clearParameters();

						int rsCnt = 1;
						int rsGetCnt = 1;

						while(rs.next())
						{
							pst_org.setString(rsGetCnt++, rs.getString("NAME"));
							pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROWTOTAL"));
							pst_org.setString(rsGetCnt++, rs.getString("RESERVED"));
							pst_org.setString(rsGetCnt++, rs.getString("DATA"));
							pst_org.setString(rsGetCnt++, rs.getString("INDEX_SIZE"));
							pst_org.setString(rsGetCnt++, rs.getString("UNUSED"));

							pst_org.setString(rsGetCnt++, dbConnTrgPnm);
							pst_org.setString(rsGetCnt++, null);
							pst_org.setString(rsGetCnt++, sLoginame[i]);

							getSaveResult(rsCnt);
							
							if(rsCnt == execCnt)
							{
								executeBatch(true);
								rsCnt = 0;
							}

							cnt++;
							rsCnt++;
							rsGetCnt = 1;
						}
						executeBatch();
					}
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			
			}
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
		sb.append("\n SELECT id, length, cdefault, domain, remote_type   ");
		sb.append("\n 	   , xstatus, xtype, xdbid, accessrule, status2             ");
		sb.append("\n      , number, colid, offset, usertype, name ");
//		sb.append("\n      , status, type, prec, scale, remote_name  ");
		sb.append("\n      , type, prec, scale, remote_name  ");
		sb.append("\n   FROM ").append("master..syscolumns     ");
//		sb.append("\n   FROM ").append(dbConnTrgPnm).append("..syscolumns     ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_COLUMNS");
		sb.append("\n  (ID, LENGTH, CDEFAULT, DOMAIN, REMOTE_TYPE     ");
		sb.append("\n  , XSTATUS, XTYPE, XDBID, ACCESSRULE, STATUS2   ");
		sb.append("\n  , C_NUMBER, COLID, OFFSET, USERTYPE, NAME      ");
//		sb.append("\n  , STATUS, TYPE, PREC, SCALE, REMOTE_NAME       ");
		sb.append("\n  , TYPE, PREC, SCALE, REMOTE_NAME       ");
		sb.append("\n  , DB_NM, SCH_NM)                  ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?          ");
//		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?                ");
		sb.append("\n  , ?, ?)                      ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("length"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("cdefault"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("domain"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("remote_type"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("xstatus"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("xtype"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("xdbid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("accessrule"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("number"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("colid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("offset"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("usertype"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("type"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("prec"));
				pst_org.setString(rsGetCnt++, rs.getString("scale"));
				pst_org.setString(rsGetCnt++, rs.getString("remote_name"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
	 * DBC SYSTABSTATS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSTABSTATS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT rowcnt, forwrowcnt, delrowcnt, dpagecrcnt, ipagecrcnt ");
		sb.append("\n    , drowcrcnt, datarowsize, leafrowsize                      ");
//		sb.append("\n    , drowcrcnt, datarowsize, leafrowsize, spare2, spare4                     ");
		sb.append("\n    , id, leafcnt, pagecnt, oamapgcnt, extent0pgcnt ");
//		sb.append("\n    , status, rslastoam, rslastpage, frlastoam  ");
		sb.append("\n    , rslastoam, rslastpage, frlastoam  ");
//		sb.append("\n    , status, spare1, rslastoam, rslastpage, frlastoam  ");
		sb.append("\n    , frlastpage, emptypgcnt, indid, activestatid, indexheight  ");
		sb.append("\n    , conopt_thld  ");
//		sb.append("\n    , conopt_thld, spare3  ");
		sb.append("\n  FROM ").append("master..systabstats    ");
//		sb.append("\n  FROM ").append(dbConnTrgPnm).append("..systabstats    ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_TABSTATS ");
		sb.append("\n  (ROWCNT, FORWROWCNT, DELROWCNT, DPAGECRCNT, IPAGECRCNT                     ");
		sb.append("\n   , DROWCRCNT, DATAROWSIZE, LEAFROWSIZE       ");
//		sb.append("\n   , DROWCRCNT, DATAROWSIZE, LEAFROWSIZE, SPARE2, SPARE4      ");
		sb.append("\n   , ID, LEAFCNT, PAGECNT, OAMAPGCNT, EXTENT0PGCNT   ");
//		sb.append("\n   , STATUS, RSLASTOAM, RSLASTPAGE, FRLASTOAM    ");
		sb.append("\n   , RSLASTOAM, RSLASTPAGE, FRLASTOAM    ");
//		sb.append("\n   , STATUS, SPARE1, RSLASTOAM, RSLASTPAGE, FRLASTOAM    ");
		sb.append("\n   , FRLASTPAGE, EMPTYPGCNT, INDID, ACTIVESTATID, INDEXHEIGHT  ");
		sb.append("\n   , CONOPT_THLD, DB_NM, SCH_NM)                          ");
//		sb.append("\n   , CONOPT_THLD, SPARE3, DB_NM, SCH_NM)                          ");
		sb.append("\n   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?                        ");
//		sb.append("\n     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                        ");
		sb.append("\n     , ?, ?, ?, ?, ?, ?, ?, ?, ?                        ");
		sb.append("\n     , ?, ?, ?, ?, ? )                       ");
//		sb.append("\n     , ?, ?, ?, ?, ?, ?, ?, ?, ?)                       ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("rowcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("forwrowcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("delrowcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("dpagecrcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ipagecrcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("drowcrcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("datarowsize"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("leafrowsize"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("spare2"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("spare4"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("leafcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("pagecnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("oamapgcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("extent0pgcnt"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("spare1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("rslastoam"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("rslastpage"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("frlastoam"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("frlastpage"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("emptypgcnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("indid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("activestatid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("indexheight"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("conopt_thld"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("spare3"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
		sb.append("\n SELECT id, number, colid, texttype, language  ");
//		sb.append("\n    , colid2, status, text ");
		sb.append("\n    , colid2, text ");
		sb.append("\n    FROM ").append("master..syscomments  ");
//		sb.append("\n    FROM ").append(dbConnTrgPnm).append("..syscomments  ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_COMMENTS ");
		sb.append("\n (ID, C_NUMBER, COLID, TEXTTYPE, LANGUAGE ");
//		sb.append("\n   , COLID2, STATUS, TEXT, DB_NM, SCH_NM)                         ");
		sb.append("\n   , COLID2, TEXT, DB_NM, SCH_NM)                         ");
//		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)                     ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)                     ");
	

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("number"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("colid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("texttype"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("language"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("colid2"));
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("status"));
				pst_org.setString(rsGetCnt++, rs.getString("text"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
//		sb.append("\n SELECT a.versionts, a.type, a.crdate, a.expdate, a.id ");
		sb.append("\n SELECT a.type, a.crdate, a.expdate, a.id ");
		sb.append("\n   , a.uid, a.sysstat2, a.deltrig, a.instrig, a.updtrig ");
		sb.append("\n   , a.seltrig, a.ckfirst, a.objspare, a.userstat, a.sysstat  ");
		sb.append("\n   , a.indexdel, a.schemacnt, a.cache, a.name, b.name as loginame  ");
		sb.append("\n    FROM ").append("master..sysobjects a ,             ");
		sb.append("\n         ").append("master..sysusers b            ");
//		sb.append("\n    FROM ").append(dbConnTrgPnm).append("..sysobjects a ,             ");
//		sb.append("\n         ").append(dbConnTrgPnm).append("..sysusers b            ");
		sb.append("\n   WHERE	a.uid	=	b.uid            ");
//		sb.append("\n     and b.name = '").append(null).append("'");

//System.out.println(sb.toString());
		getResultSetLast(sb.toString());
		sb = new StringBuffer();

		//System.out.println("insertDBA_CONSTRAINTS : \n"+sb);
		
		sb.append("\n INSERT INTO WAE_ASE_OBJECTS ");
//		sb.append("\n  (VERSIONTS, C_TYPE, CRDATE, EXPDATE, ID                        ");
		sb.append("\n  (C_TYPE, CRDATE, EXPDATE, ID                        ");
		sb.append("\n     , C_UID, SYSSTAT2, DELTRIG, INSTRIG, UPDTRIG                        ");
		sb.append("\n     , SELTRIG, CKFIRST, OBJSPARE, USERSTAT, SYSSTAT                  ");
		sb.append("\n     , INDEXDEL, SCHEMACNT, CACHE, NAME, LOGINAME             ");
		sb.append("\n  , DB_NM, SCH_NM)             ");
//		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?           ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?           ");
		sb.append("\n  , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                    ");
		sb.append("\n  , ?, ?)           ");

		int cnt = 0;
		int j = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;
			int k = 0;
			
			rs.last();
			j = rs.getRow();
			rs.beforeFirst();

			sTable = new String[j];
			sLoginame = new String[j];
			sType = new String[j];
			
			while(rs.next())
			{
//				pst_org.setString(rsGetCnt++, rs.getString("versionts"));
				pst_org.setString(rsGetCnt++, rs.getString("type"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("crdate"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("expdate"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("uid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("sysstat2"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("deltrig"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("instrig"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("updtrig"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("seltrig"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ckfirst"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("objspare"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("userstat"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("sysstat"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("indexdel"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("schemacnt"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("cache"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setString(rsGetCnt++, rs.getString("loginame"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

				getSaveResult(rsCnt);

				if(rsCnt == execCnt)
				{
					executeBatch(true);
					rsCnt = 0;
				}

				rsCnt++;
				rsGetCnt = 1;
				cnt++;
				
				sTable[k] = rs.getString("name");

				if(  rs.getString("loginame") == null ) {
					sLoginame[k] = "";
				} else {
					sLoginame[k] = rs.getString("loginame");
				}

				sType[k++] = rs.getString("type");
			}
			executeBatch();
		}

		return cnt;
	}


	/**
	 * DBC SYSPROTECTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSPROTECTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n select o.id,o.uid,o.action,o.protecttype,o.columns,o.grantor ");
//		sb.append("\n FROM ").append(dbConnTrgPnm).append("..sysprotects ");
		sb.append("\n FROM ").append("master..sysprotects o ");
//		sb.append("\n      ,").append("master..sysusers u");
//		sb.append("\n WHERE u.name = '").append(null).append("'");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_ASE_PROTECTS         ");
		sb.append("\n (ID,C_UID,ACTION,PROTECTTYPE,COLUMNS,GRANTOR       ");
		sb.append("\n        , DB_NM, SCH_NM)                     ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?)               ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("uid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("action"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("protecttype"));
				pst_org.setString(rsGetCnt++, rs.getString("columns"));
				pst_org.setString(rsGetCnt++, rs.getString("grantor"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null );

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
	 * DBC SYSLOGINS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSLOGINS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n select suid, name, fullname,                "); 
		sb.append("\n case status when 0 then 'unlock' ");
		sb.append("\n     when 224 then 'unlock'                           "); 
		sb.append("\n     when 2 then 'lock'                "); 
		sb.append("\n     when 226 then 'lock'                    "); 
		sb.append("\n  else '-' end lock_chk                 "); 
		sb.append("\n  from master..syslogins                    ");


//		System.out.println("insertDBA_TABLES : \n"+sb);
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_ASE_LOGINS   ");
		sb.append("\n  (SUID, NAME, FULLNAME, LOCK_CHK            "); 
		sb.append("\n  , DB_NM, SCH_NM)                     ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?)                     "); 

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
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("suid"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setString(rsGetCnt++, rs.getString("fullname"));
				pst_org.setString(rsGetCnt++, rs.getString("lock_chk"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
	 * DBC SYSUSERS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSUSERS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT                               ");
		sb.append("\n      suid, uid, gid, name, environ");
		
//		sb.append("\n  FROM ").append(dbConnTrgPnm).append("..sysusers ");
		sb.append("\n  FROM ").append("master..sysusers ");
//		sb.append("\n WHERE name = '").append(null).append("'");
//logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_ASE_USERS  ");
		sb.append("\n (SUID, C_UID, GID, NAME, ENVIRON  ");
		sb.append("\n  , DB_NM, SCH_NM)    ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?)       ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("suid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("uid"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("gid"));
				pst_org.setString(rsGetCnt++, rs.getString("name"));
				pst_org.setString(rsGetCnt++, rs.getString("environ"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);
				pst_org.setString(rsGetCnt++, null);

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
	private ResultSet getResultSetLast(String query_tgt) throws SQLException
	{
		//logger.debug("query_tgt:\n"+query_tgt);
		pst_tgt = null;
		rs = null;
		
		pst_tgt = con_tgt.prepareStatement(query_tgt,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
	private int updateMetaDATABASES_ASE_BLOCK_SIZE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n	select db_name(dbid) db_nm, ");
		sb.append("\n\n				dbid db_id,	                            ");
		sb.append("\n\n				segmap seg_type,	                            ");
		sb.append("\n\n				(sum(size)*(@@maxpagesize/1024)/1024) seg_size,	                            ");
		sb.append("\n\n				(sum(size)*(@@maxpagesize/1024)/1024 - sum(curunreservedpgs(dbid,lstart,unreservedpgs))*(@@maxpagesize/1024)/1024) used_size,	                            ");
		sb.append("\n\n				(sum(curunreservedpgs(dbid, lstart,unreservedpgs))*(@@maxpagesize/1024)/1024) free_size,	                            ");
		sb.append("\n\n				(100 - 100*sum(curunreservedpgs(dbid, lstart,unreservedpgs))/sum(size)) pctused	                            ");
		sb.append("\n\n				from master..sysusages	                            ");
		sb.append("\n\n				where size > 1024	                            ");
		sb.append("\n\n				and (segmap in (3, 4) or dbid = 2)	                            ");
		sb.append("\n\n				and (db_name(dbid) <> 'sybsecurity')	                            ");
		sb.append("\n\n				group by db_name(dbid), dbid, segmap	                            ");

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

		sb.append("\n UPDATE WAE_ORA_DB ");
		sb.append("\n   SET  BLOCK_BYTE =  ").append(block_byte).append(" ");
		sb.append("\n  WHERE  TRIM(DB_NM)    = TRIM('").append(dbConnTrgPnm).append("')");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("db_nm"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("db_id"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("seg_type"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("seg_size"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("used_size"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("free_size"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("pctused"));
				pst_org.setString(rsGetCnt++, dbConnTrgPnm);

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
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_ASE_DATABASES (");
		sb.append("\n   NAME                                            ");
		sb.append("\n  ,DBID                                   ");
		sb.append("\n  ,DB_NM                                       ");
		sb.append("\n)                              ");
		sb.append("\n SELECT DISTINCT DB_CONN_TRG_PNM                                        ");
		sb.append("\n        ,A.DB_CONN_TRG_ID                                      ");
		sb.append("\n         ,'").append(dbConnTrgPnm).append("' AS DB_NM");
		sb.append("\n   FROM WAA_DB_CONN_TRG A                                      ");
		sb.append("\n  INNER JOIN WAA_DB_SCH B                                      ");
		sb.append("\n     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID                    ");
		sb.append("\n    AND B.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND B.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND A.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  AND	A.DBMS_TYP_CD    = '").append(dbType).append("'") ;
		sb.append("\n  AND  UPPER(A.DB_CONN_TRG_PNM)   =    UPPER('").append(dbConnTrgPnm).append("')") ;
//		sb.append("\n  AND  UPPER(B.OBJ_KNM)   =    UPPER('").append(sdbConnTrgPnm).append("')") ;

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
		strSQL.append("\n  DELETE FROM WAE_ASE_USERS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_USERS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_TYPES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_TYPES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_TABSTATS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_TABSTATS : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_SERVERS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_SERVERS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_SEGMENTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_SEGMENTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_PROTECTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_PROTECTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_OBJECTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_OBJECTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_LOGINS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_LOGINS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_KEYS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_KEYS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_INDEXES_COLUMN ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_INDEXES_COLUMN : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_INDEXES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_INDEXES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_DATABASES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_DATABASES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_COMMENTS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_COMMENTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_COLUMNS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_COLUMNS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_ATTRIBUTES ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_ATTRIBUTES : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_SP_SPACEUSED ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_SP_SPACEUSED : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_FOREIGNKEY_COLUMN ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_FOREIGNKEY_COLUMN : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_FOREIGNKEY ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_FOREIGNKEY : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_ASE_DBSIZE ");
		strSQL.append("\n   WHERE DB_NM = '"+dbConnTrgPnm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+null+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ASE_DBSIZE : " + result);

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
		this.dbConnTrgPnm = targetDb.getDbms_enm();
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
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                           ");
		sb.append("\n      , A.NAME                AS DBC_TBL_NM                            ");
		sb.append("\n      , ''            AS DBC_TBL_KOR_NM                        ");
//		sb.append("\n      , A.TBL_CMMT            AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                   AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL               AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''         AS DBC_TBL_SPAC_NM                       ");
//		sb.append("\n      , A.TBL_SPAC_NM         AS DBC_TBL_SPAC_NM                       ");
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
		sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
		sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_ASE_OBJECTS A                                                  ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON UPPER(A.DB_NM) = UPPER(S.DB_CONN_TRG_PNM)      ");
//		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM          ");
		sb.append("\n    AND A.C_TYPE = 'U'                   ");
		sb.append("\n    AND UPPER(A.LOGINAME) = UPPER(S.DB_SCH_PNM) ");
		sb.append("\n  WHERE A.DB_NM = '"+dbConnTrgPnm+"'         ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                                   ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID                                ");
		sb.append("\n      , A.TBL_NM                                              ");
		sb.append("\n      , RTRIM(A.NAME)                                              ");
//		sb.append("\n      , COL_CMMT                                              ");
		sb.append("\n      , ''                                              ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                   ");
		sb.append("\n      , SYSDATE                                               ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , A.B_NAME                                              ");
		sb.append("\n      , CASE WHEN UPPER(A.B_NAME) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME','NUMERIC', 'DECIMAL')                                                ");
		sb.append("\n           THEN A.LENGTH                                                ");
		sb.append("\n         END AS LENGTH                                              ");
		sb.append("\n      , CASE WHEN UPPER(A.B_NAME) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME','NUMERIC', 'DECIMAL')                                                ");
		sb.append("\n           THEN A.PREC                                                ");
		sb.append("\n         END AS PREC                                              ");
		sb.append("\n      , CASE WHEN UPPER(A.B_NAME) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME','NUMERIC', 'DECIMAL')                                            ");
		sb.append("\n           THEN A.SCALE                                                ");
		sb.append("\n         END AS SCALE                                              ");
		sb.append("\n      , CASE WHEN A.STATUS = 8         THEN 'Y' ELSE '' END AS IS_NULLABLE                                               ");
		sb.append("\n      , CASE LOWER(SUBSTR(LTRIM(M_TEXT), 1, 7)) WHEN 'default' THEN LENGTH(M_TEXT) - 8 ELSE null END AS DEFLT_LEN                                             ");
		sb.append("\n      , CASE LOWER(SUBSTR(LTRIM(M_TEXT), 1, 7)) WHEN 'default' THEN SUBSTR(M_TEXT,8,LENGTH(M_TEXT) ) ELSE '' END AS DATA_DEFAULT                                        ");
		sb.append("\n      , CASE WHEN TRIM(A.NAME) = TRIM(A.IDX_COL) THEN CASE WHEN A.IS_PK_INDEX = 'YES' THEN 'Y' ELSE '' END ELSE '' END AS PK_YN");
		sb.append("\n      , A.COLID                                              ");
		sb.append("\n      , CASE WHEN TRIM(A.NAME) = TRIM(A.IDX_COL) THEN CASE WHEN A.IS_PK_INDEX = 'YES' THEN A.IDX_COL_SEQ END END AS PK_ORD ");
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
		sb.append("\n      , ''  -- MMART_COL_EXTNC_YN      ");  
		sb.append("\n      , ''  -- MMART_ORD_ERR_EXS       ");
		sb.append("\n      , ''  -- MMART_PK_YN_ERR_EXS     ");
		sb.append("\n      , ''  -- MMART_PK_ORD_ERR_EXS    ");
		sb.append("\n      , ''  -- MMART_NULL_YN_ERR_EXS   ");
		sb.append("\n      , ''  -- MMART_DEFLT_ERR_EXS     ");
		sb.append("\n      , ''  -- MMART_CMMT_ERR_EXS      ");
		sb.append("\n      , ''  -- MMART_DATA_TYPE_ERR_EXS ");
		sb.append("\n      , ''  -- MMART_DATA_LEN_ERR_EXS  ");
		sb.append("\n      , ''  -- MMART_DATA_PNT_ERR_EXS  ");
		sb.append("\n      , ''  -- MMARTL_COL_ERR_EXS      ");
		sb.append("\n      FROM (SELECT DISTINCT A.*, D.NAME AS TBL_NM, B.NAME AS B_NAME, M.TEXT AS M_TEXT, I.IS_PK_INDEX, IC.IDX_COL, IC.IDX_COL_SEQ , D.LOGINAME               ");
		sb.append("\n      	 	 FROM WAE_ASE_COLUMNS A                                            ");
		sb.append("\n            LEFT OUTER JOIN WAE_ASE_COMMENTS M                                         ");
		sb.append("\n               ON A.DB_NM = M.DB_NM                                        ");
//		sb.append("\n              AND A.SCH_NM = M.SCH_NM                                        ");
		sb.append("\n              AND A.CDEFAULT = M.ID                                        ");
		sb.append("\n              AND M.COLID = 1                                        ");
		sb.append("\n            LEFT OUTER JOIN WAE_ASE_TYPES B                                        ");
		sb.append("\n               ON A.USERTYPE = B.USERTYPE                                        ");
		sb.append("\n              AND (B.USERTYPE BETWEEN 0 AND 100 AND B.USERTYPE NOT IN (18, 24, 25, 42, 80))                                           ");
		sb.append("\n            LEFT OUTER JOIN WAE_ASE_OBJECTS D                                        ");
		sb.append("\n              ON A.ID = D.ID                                        ");
		sb.append("\n            LEFT OUTER JOIN WAE_ASE_INDEXES I                                        ");
		sb.append("\n              ON A.ID = I.ID                                        ");
		sb.append("\n             AND I.INDID = 2                                        ");
		sb.append("\n            LEFT OUTER JOIN WAE_ASE_INDEXES_COLUMN IC                                        ");
		sb.append("\n              ON A.NAME = IC.IDX_COL                                        ");
		sb.append("\n             AND D.NAME = IC.OF_TABLE                                        ");
		sb.append("\n             AND I.NAME = IC.OF_INDEX                                        ");
		sb.append("\n            WHERE 1 = 1                                        ");
		sb.append("\n              AND A.DB_NM = B.DB_NM                                        ");
//		sb.append("\n              AND A.SCH_NM = B.SCH_NM                                        ");
		sb.append("\n              AND A.DB_NM = D.DB_NM                                         ");
//		sb.append("\n              AND A.SCH_NM = D.SCH_NM                                        ");
		sb.append("\n    ) A                                         ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON UPPER(A.DB_NM) = UPPER(S.DB_CONN_TRG_PNM)                   ");
//		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n    AND UPPER(A.LOGINAME) = UPPER(S.DB_SCH_PNM) ");
		sb.append("\n  WHERE A.DB_NM = '"+dbConnTrgPnm+"'                               ");

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
		sb.append("\n      , A.B_NAME                                                                  ");
		sb.append("\n      , RTRIM(A.NAME)                                                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID                                                          ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , '1'                                                                       ");
		sb.append("\n      , NULL                                                                       ");
		sb.append("\n      , SYSDATE                                                                   ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
//		sb.append("\n      , A.TBL_SPAC_NM                                                             ");
		sb.append("\n      , ''                                                             ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
//		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'P' THEN 'Y' ELSE 'N' END PK_YN                   ");
//		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'U' THEN 'Y' ELSE 'N' END PK_YN                   ");
//		sb.append("\n      , E.COL_CNT                                                                 ");
//		sb.append("\n      , C.BYTES AS IDX_SIZE                                                       ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , ''                                                                ");
		sb.append("\n      , ''                                                       ");
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
		sb.append("\n   FROM ( SELECT C.*, B.NAME AS B_NAME , B.LOGINAME ");
		sb.append("\n          FROM WAE_ASE_OBJECTS B, WAE_ASE_INDEXES C                         ");
		sb.append("\n          WHERE B.DB_NM = C.DB_NM                         ");
//		sb.append("\n           AND B.SCH_NM = C.SCH_NM                                                        ");
		sb.append("\n           AND B.ID = C.ID                                                        ");
		sb.append("\n           AND B.C_TYPE = 'U'                                                      ");
		sb.append("\n   ) A                                                             ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON UPPER(A.DB_NM) = UPPER(S.DB_CONN_TRG_PNM)                  ");
//		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n    AND UPPER(A.LOGINAME) = UPPER(S.DB_SCH_PNM) ");
		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgPnm+"'                               ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID      ");
		sb.append("\n      , A.OF_TABLE                    ");
		sb.append("\n      , A.IDX_COL                    ");
		sb.append("\n      , A.OF_INDEX                    ");
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
//		sb.append("\n      , IDX_COL_ORD                 ");
//		sb.append("\n      , SORT_TYPE                   ");
		sb.append("\n      , TO_NUMBER(A.IDX_COL_SEQ) +1                 ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , B.B_NAME                 ");
//		sb.append("\n      , C.DATA_PNUM                 ");
//		sb.append("\n      , C.DATA_LEN                  ");
//		sb.append("\n      , C.DATA_PNT                  ");
		sb.append("\n      , CASE WHEN UPPER(B.B_NAME) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME')                   ");
		sb.append("\n             THEN B.PREC                   ");
		sb.append("\n        END AS PREC                  ");
		sb.append("\n      , CASE WHEN UPPER(B.B_NAME) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME')                   ");
		sb.append("\n             THEN B.LENGTH                   ");
		sb.append("\n        END AS LENGTH                  ");
		sb.append("\n      , B.SCALE                  ");
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
		sb.append("\n   FROM WAE_ASE_INDEXES_COLUMN A           ");
		sb.append("\n        INNER JOIN (SELECT C.*, T.NAME AS T_NAME, B.NAME AS B_NAME , T.LOGINAME ");
		sb.append("\n                      FROM WAE_ASE_COLUMNS C                             ");
		sb.append("\n                           INNER JOIN WAE_ASE_OBJECTS T                             ");
		sb.append("\n                              ON C.DB_NM = T.DB_NM                               ");
//		sb.append("\n                             AND C.SCH_NM = T.SCH_NM                               ");
		sb.append("\n                             AND C.ID = T.ID                               ");
		sb.append("\n                             AND T.C_TYPE = 'U'                              ");
		sb.append("\n                            LEFT OUTER JOIN WAE_ASE_TYPES B                               ");
		sb.append("\n              	               ON C.USERTYPE = B.USERTYPE                               ");
		sb.append("\n                             AND (B.USERTYPE BETWEEN 0 AND 100 AND B.USERTYPE NOT IN (18, 24, 25, 42, 80))                              ");
		sb.append("\n                     ) B                             ");
		sb.append("\n          ON A.IDX_COL = B.NAME                             ");
		sb.append("\n         AND A.OF_TABLE = B.T_NAME                             ");
		sb.append("\n       INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n                  ) S                               ");
		sb.append("\n     ON UPPER(A.DB_NM) = UPPER(S.DB_CONN_TRG_PNM)                   ");
		sb.append("\n    AND UPPER(B.LOGINAME) = UPPER(S.DB_SCH_PNM) ");
		sb.append("\n  WHERE A.DB_NM = '"+dbConnTrgPnm+"'     ");

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
		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgPnm+"'     ");

		

		
		
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
		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgPnm+"'     ");

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
		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgPnm+"'     ");

		return setExecuteUpdate_Org(sb.toString());
	}

}
