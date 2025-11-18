/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorInformix.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : moonsungeun
 * 5. 작성일  : 2015. 11. 19. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    moonsungeun : 2015. 11. 19. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema.dbms;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.wise.commons.DQConstant;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorInformix.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : moonsungeun
 * 6. 작성일   : 2015. 11. 19. 
 * </PRE>
 */
public class CollectorInformix{

	private static final Logger logger = Logger.getLogger(CollectorInformix.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private TargetDbmsDM targetDbmsDM;
	private String dbConnTrgPnm = null;
	private String dbConnTrgId = null;
	private String dbType = null;
	
	private int execCnt = 10000;


	public CollectorInformix() {

	}

	/** insomnia */
	public CollectorInformix(Connection source, Connection target,	TargetDbmsDM targetDbmsDM) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDbmsDM = targetDbmsDM;
	}

	/**  insomnia
	 * @return */
	public boolean doProcess() throws Exception {
		boolean result = false;

		con_org.setAutoCommit(false);
		
		this.dbConnTrgId = targetDbmsDM.getDbms_id();
		this.dbConnTrgPnm = targetDbmsDM.getDbms_enm();
		this.dbType = targetDbmsDM.getDbms_type_cd();

		String sp = "   ";
		int p = 0;
		int cnt = 0;
		
		logger.debug(" dbConnTrgId : "+dbConnTrgId + "  dbConnTrgPnm : "+dbConnTrgPnm);
		//스키마 정보를 모두 삭제한다.
		deleteSchema();

		//TABLES --------------------------------------------------------
		cnt = insertDBA_TABLES();
		logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");
		
		//SYSCONSTRAINTS --------------------------------------------------------
		cnt = insertDBA_CONSTRAINTS();
		logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

		//COLUMNS -------------------------------------------------------
		cnt = insertDBA_COLUMNS();
		logger.debug(sp + (++p) + ". insertDBA_COLUMNS " + cnt + " OK!!");

		//INDEXES ---------------------------------------------------
//		cnt = insertDBA_INDICES();
//		logger.debug(sp + (++p) + ". insertDBA_INDICES " + cnt + " OK!!");
		
		//INDEXES VIEW ---------------------------------------------------
		cnt = insertDBA_INDEXES();
		logger.debug(sp + (++p) + ". insertDBA_INDEXES " + cnt + " OK!!");


		result = true;
		return result;

	}

	/**
	 * DBC TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
  		sb.append("\nSELECT  TABNAME	");
  		sb.append("\n		,OWNER      ");
  		sb.append("\n		,PARTNUM    ");
  		sb.append("\n		,TABID      ");
  		sb.append("\n		,ROWSIZE    ");
  		sb.append("\n		,NCOLS      ");
  		sb.append("\n		,NROWS      ");
  		sb.append("\n		,CREATED    ");
  		sb.append("\n		,VERSION    ");
  		sb.append("\n		,TABTYPE    "); 
  		sb.append("\n  FROM SYSTABLES ");
//  		sb.append("\n  FROM 'informix'.SYSTABLES ");
  		sb.append("\n WHERE 1 = 1 ");
//  		sb.append("\n   AND TABTYPE = 'T' ");
  		sb.append("\n   AND TABID >= 100 "); //시스템 테이블 제외
  		
		
		getResultSet(sb.toString());
		sb.setLength(0);

  		sb.append("\n INSERT INTO WAE_IFX_SYSTABLES ( ");
  		sb.append("\n        TABNAME	");
  		sb.append("\n		,OWNER      ");
  		sb.append("\n		,PARTNUM    ");
  		sb.append("\n		,TABID      ");
  		sb.append("\n		,ROWSIZE    ");
  		
  		sb.append("\n		,NCOLS      ");
  		sb.append("\n		,NROWS      ");
  		sb.append("\n		,CREATED    ");
  		sb.append("\n		,VERSION    ");
  		sb.append("\n		,TABTYPE    "); 
  		
  		sb.append("\n		,DB_CONN_TRG_ID    "); 
  		sb.append("\n		,DB_SCH_ID    "); 
  		sb.append("\n ) VALUES (  ");
  		sb.append("\n  RTRIM(?),RTRIM(?),?,?,?");
  		sb.append("\n  ,?,?,?,?,? ");
  		sb.append("\n  ,RTRIM(?),RTRIM(?) ");
  		
  		sb.append("\n ) ");

		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				
  				pst_org.setString(rsGetCnt++, rs.getString("TABNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("PARTNUM"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("TABID"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ROWSIZE"));
  				
  				pst_org.setInt(rsGetCnt++, rs.getInt("NCOLS"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("NROWS"));
  				pst_org.setDate(rsGetCnt++, rs.getDate("CREATED"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("VERSION"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABTYPE"));
  				
  				pst_org.setString(rsGetCnt++, dbConnTrgId);
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
	 * DBC CONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONSTRAINTS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\n SELECT  CONSTRID  ");
  		sb.append("\n        ,CONSTRNAME ");
  		sb.append("\n        ,OWNER     ");
  		sb.append("\n        ,TABID     ");
  		sb.append("\n        ,CONSTRTYPE "); //C : 검사제한조건 N : NOT NULL P : 기본키 R : 참조 T:테이블 U:고유
  		sb.append("\n        ,IDXNAME    ");
  		sb.append("\n        ,COLLATION  ");
//  		sb.append("\n   FROM 'informix'.SYSCONSTRAINTS  ");
  		sb.append("\n   FROM SYSCONSTRAINTS A  ");

		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_IFX_SYSCONSTRAINTS ( ");
  		sb.append("\n         CONSTRID  ");
  		sb.append("\n        ,CONSTRNAME ");
  		sb.append("\n        ,OWNER     ");
  		sb.append("\n        ,TABID     ");
  		sb.append("\n        ,CONSTRTYPE "); //C : 검사제한조건 N : NOT NULL P : 기본키 R : 참조 T:테이블 U:고유
  		sb.append("\n        ,IDXNAME    ");
  		sb.append("\n        ,COLLATION  ");
  		sb.append("\n        ,DB_CONN_TRG_ID  ");
  		sb.append("\n        ,DB_SCH_ID       ");
  		sb.append("\n ) VALUES (            ");
  		sb.append("\n     ?, RTRIM(?),RTRIM(?), ? ,RTRIM(?)  ");
  		sb.append("\n     , RTRIM(?),RTRIM(?), RTRIM(?) ,RTRIM(?)  ");
  		sb.append("\n ) ");
	

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setInt(rsGetCnt++, rs.getInt("CONSTRID"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("TABID"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRTYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IDXNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, dbConnTrgId);
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
	 * DBC COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT  A.COLNAME   ");
		sb.append("\n        ,A.TABID     ");
		sb.append("\n        ,A.COLNO     ");
//		sb.append("\n        ,B.NAME AS COLTYPE   ");
		sb.append("\n        ,A.COLTYPE   ");
		sb.append("\n        ,A.COLLENGTH "); 
		sb.append("\n        ,A.COLMIN    ");
		sb.append("\n        ,A.COLMAX    ");
		sb.append("\n        ,A.EXTENDED_ID ");
		sb.append("\n        ,A.SECLABELID  ");
		sb.append("\n        ,A.COLATTR   ");
		sb.append("\n        ,C.DEFAULT AS DEFAULTVALUE  ");
		sb.append("\n   FROM SYSCOLUMNS A  ");
		sb.append("\n        LEFT OUTER JOIN SYSDEFAULTS C  ");
		sb.append("\n          ON A.TABID = C.TABID  ");
		sb.append("\n         AND A.COLNO = C.COLNO  ");
//		sb.append("\n        LEFT OUTER JOIN 'informix'.SYSXTDTYPES B  ");
//		sb.append("\n          ON A.EXTENDED_ID = B.EXTENDED_ID  ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_IFX_SYSCOLUMNS ( ");
		sb.append("\n         COLNAME   ");
		sb.append("\n        ,TABID     ");
		sb.append("\n        ,COLNO     ");
		sb.append("\n        ,COLTYPE   ");
		sb.append("\n        ,COLLENGTH "); 
		sb.append("\n        ,DEFAULTVALUE "); 
		
		sb.append("\n        ,COLMIN    ");
		sb.append("\n        ,COLMAX    ");
		sb.append("\n        ,EXTENDED_ID ");
		sb.append("\n        ,SECLABELID  ");
		sb.append("\n        ,COLATTR   ");
		
		sb.append("\n        ,DB_CONN_TRG_ID  ");
		sb.append("\n        ,DB_SCH_ID       ");
		sb.append("\n ) VALUES (            ");
		sb.append("\n     ?, RTRIM(?),RTRIM(?), ? ,RTRIM(?), ?  ");
		sb.append("\n     ,? ,? ,? ,? ,?  ");
		sb.append("\n     , RTRIM(?),RTRIM(?)  ");
		sb.append("\n ) ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("COLNAME"));
				pst_org.setInt(rsGetCnt++, rs.getInt("TABID"));
				pst_org.setInt(rsGetCnt++, rs.getInt("COLNO"));
				pst_org.setString(rsGetCnt++, rs.getString("COLTYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULTVALUE"));
				
				pst_org.setInt(rsGetCnt++, rs.getInt("COLMIN"));
				pst_org.setInt(rsGetCnt++, rs.getInt("COLMAX"));
				pst_org.setInt(rsGetCnt++, rs.getInt("EXTENDED_ID"));
				pst_org.setInt(rsGetCnt++, rs.getInt("SECLABELID"));
				pst_org.setInt(rsGetCnt++, rs.getInt("COLATTR"));
				
				pst_org.setString(rsGetCnt++, dbConnTrgId);
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
	 * DBC INDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_INDICES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT  IDXNAME   ");
		sb.append("\n        ,OWNER   ");
		sb.append("\n        ,TABID     ");
		sb.append("\n        ,IDXTYPE     ");
		sb.append("\n        ,CLUSTERED   ");
		sb.append("\n        ,LEVELS "); 
		sb.append("\n        ,LEAVES    ");
		sb.append("\n        ,NUNIQUE    ");
		sb.append("\n        ,CLUST ");
		sb.append("\n        ,NROWS  ");
		sb.append("\n        ,INDEXKEYS   ");
		sb.append("\n        ,AMID   ");
		sb.append("\n        ,AMPARAM   ");
		sb.append("\n        ,COLLATION   ");
		sb.append("\n   FROM 'informix'.SYSINDICES  ");
		sb.append("\n  WHERE IDXTYPE IN ('U','D') ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_IFX_SYSINDICES  ( ");
		sb.append("\n         IDXNAME   ");
		sb.append("\n        ,OWNER   ");
		sb.append("\n        ,TABID     ");
		sb.append("\n        ,IDXTYPE     ");
		sb.append("\n        ,CLUSTERED   ");
		
		sb.append("\n        ,LEVELS "); 
		sb.append("\n        ,LEAVES    ");
		sb.append("\n        ,NUNIQUE    ");
		sb.append("\n        ,CLUST ");
		sb.append("\n        ,NROWS  ");
		
		sb.append("\n        ,INDEXKEYS   ");
		sb.append("\n        ,AMID   ");
		sb.append("\n        ,AMPARAM   ");
		sb.append("\n        ,COLLATION   ");
		
		sb.append("\n        ,DB_CONN_TRG_ID  ");
		sb.append("\n        ,DB_SCH_ID       ");
		sb.append("\n ) VALUES (            ");
		sb.append("\n     RTRIM(?),RTRIM(?), ? ,RTRIM(?) ,RTRIM(?) ");
		sb.append("\n     ,? ,? ,? ,? ,?  ");
		sb.append("\n     , RTRIM(?) ,? ,RTRIM(?) ,RTRIM(?) ");
		sb.append("\n     , RTRIM(?) ,RTRIM(?) ");
		sb.append("\n ) ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("IDXNAME"));
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setInt(rsGetCnt++, rs.getInt("TABID"));
				pst_org.setString(rsGetCnt++, rs.getString("IDXTYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("CLUSTERED"));
				
				pst_org.setInt(rsGetCnt++, rs.getInt("LEVELS"));
				pst_org.setInt(rsGetCnt++, rs.getInt("LEAVES"));
				pst_org.setInt(rsGetCnt++, rs.getInt("NUNIQUE"));
				pst_org.setInt(rsGetCnt++, rs.getInt("CLUST"));
				pst_org.setInt(rsGetCnt++, rs.getInt("NROWS"));
				
				pst_org.setString(rsGetCnt++, rs.getString("INDEXKEYS"));
				pst_org.setInt(rsGetCnt++, rs.getInt("AMID"));
				pst_org.setString(rsGetCnt++, rs.getString("AMPARAM"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				
				pst_org.setString(rsGetCnt++, dbConnTrgId);
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
	 * DBC INDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_INDEXES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT IDXNAME   ");
		sb.append("\n       ,OWNER     ");
		sb.append("\n       ,TABID     ");
		sb.append("\n       ,IDXTYPE   ");
		sb.append("\n       ,CLUSTERED ");
		
		sb.append("\n       ,PART1     ");
		sb.append("\n       ,PART2     ");
		sb.append("\n       ,PART3     ");
		sb.append("\n       ,PART4     ");
		sb.append("\n       ,PART5     ");
		sb.append("\n       ,PART6     ");
		sb.append("\n       ,PART7     ");
		sb.append("\n       ,PART8     ");
		sb.append("\n       ,PART9     ");
		sb.append("\n       ,PART10    ");
		sb.append("\n       ,PART11    ");
		sb.append("\n       ,PART12    ");
		sb.append("\n       ,PART13    ");
		sb.append("\n       ,PART14    ");
		sb.append("\n       ,PART15    ");
		sb.append("\n       ,PART16    ");
		
		sb.append("\n       ,LEVELS    ");
		sb.append("\n       ,LEAVES    ");
		sb.append("\n       ,NUNIQUE   ");
		sb.append("\n       ,CLUST     ");
		sb.append("\n   FROM SYSINDEXES  ");
//		sb.append("\n  WHERE IDXTYPE IN ('U','D') ");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAE_IFX_SYSINDEXES  ( ");
		sb.append("\n         IDXNAME   ");
		sb.append("\n        ,OWNER   ");
		sb.append("\n        ,TABID     ");
		sb.append("\n        ,IDXTYPE     ");
		sb.append("\n        ,CLUSTERED   ");

		sb.append("\n       ,PART1     ");
		sb.append("\n       ,PART2     ");
		sb.append("\n       ,PART3     ");
		sb.append("\n       ,PART4     ");
		sb.append("\n       ,PART5     ");
		sb.append("\n       ,PART6     ");
		sb.append("\n       ,PART7     ");
		sb.append("\n       ,PART8     ");
		sb.append("\n       ,PART9     ");
		sb.append("\n       ,PART10    ");
		sb.append("\n       ,PART11    ");
		sb.append("\n       ,PART12    ");
		sb.append("\n       ,PART13    ");
		sb.append("\n       ,PART14    ");
		sb.append("\n       ,PART15    ");
		sb.append("\n       ,PART16    ");
		
		sb.append("\n        ,LEVELS "); 
		sb.append("\n        ,LEAVES    ");
		sb.append("\n        ,NUNIQUE    ");
		sb.append("\n        ,CLUST ");
		
		sb.append("\n        ,DB_CONN_TRG_ID  ");
		sb.append("\n        ,DB_SCH_ID       ");
		sb.append("\n ) VALUES (            ");
		sb.append("\n     RTRIM(?),RTRIM(?), ? ,RTRIM(?) ,RTRIM(?) ");
		sb.append("\n     ,? ,? ,? ,? ,?  ");
		sb.append("\n     ,? ,? ,? ,? ,?  ");
		sb.append("\n     ,? ,? ,? ,? ,?,?  ");
		sb.append("\n     , ? ,? ,? ,? ");
		sb.append("\n     , RTRIM(?) ,RTRIM(?) ");
		sb.append("\n ) ");
		
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("IDXNAME"));
				pst_org.setString(rsGetCnt++, rs.getString("OWNER"));
				pst_org.setInt(rsGetCnt++, rs.getInt("TABID"));
				pst_org.setString(rsGetCnt++, rs.getString("IDXTYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("CLUSTERED"));
				
				pst_org.setInt(rsGetCnt++, rs.getInt("PART1"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART2"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART3"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART4"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART5"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART6"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART7"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART8"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART9"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART10"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART11"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART12"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART13"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART14"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART15"));
				pst_org.setInt(rsGetCnt++, rs.getInt("PART16"));
				
				pst_org.setInt(rsGetCnt++, rs.getInt("LEVELS"));
				pst_org.setInt(rsGetCnt++, rs.getInt("LEAVES"));
				pst_org.setInt(rsGetCnt++, rs.getInt("NUNIQUE"));
				pst_org.setInt(rsGetCnt++, rs.getInt("CLUST"));
				
				pst_org.setString(rsGetCnt++, dbConnTrgId);
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


	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_IFX_SYSTABLES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IFX_SYSTABLES : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IFX_SYSCONSTRAINTS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IFX_SYSCONSTRAINTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IFX_SYSCOLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IFX_SYSCOLUMNS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IFX_SYSINDICES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IFX_SYSINDICES : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IFX_SYSINDEXES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IFX_SYSINDEXES : " + result);

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

		result = true;

		return result;
	}

	/**  insomnia
	 * @throws Exception */
	private int insertwattbl() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_TBL          ");
		sb.append("\n SELECT S.DB_SCH_ID  AS DB_SCH_ID       ");
		sb.append("\n      , A.TABNAME    AS DBC_TBL_NM      ");
		sb.append("\n      , ''           AS DBC_TBL_KOR_NM  ");
		sb.append("\n      , 1            AS VERS            ");
		sb.append("\n      , NULL         AS REG_TYP         ");
		sb.append("\n      , SYSDATE      AS REG_DTM         ");
		sb.append("\n      , NULL AS UPD_DTM  ");
		sb.append("\n      , NULL AS DESCN    ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID  ");
		sb.append("\n      , NULL         AS DBC_TBL_SPAC_NM ");
		sb.append("\n      , NULL         AS DDL_TBL_ID      ");
		sb.append("\n      , NULL         AS PDM_TBL_ID      ");
		sb.append("\n      , '"+dbType+"' AS DBMS_TYPE       ");
		sb.append("\n      , NULL         AS SUBJ_ID         ");
		sb.append("\n      , A.NCOLS      AS COL_EACNT       ");
		sb.append("\n      , ''    AS ROW_EACNT     ");
		sb.append("\n      , ''    AS TBL_SIZE      ");
		sb.append("\n      , ''    AS DATA_SIZE     ");
		sb.append("\n      , ''    AS IDX_SIZE      ");
		sb.append("\n      , ''    AS NUSE_SIZE     ");
		sb.append("\n      , ''    AS BF_COL_EACNT  ");
		sb.append("\n      , ''    AS BF_ROW_EACNT  ");
		sb.append("\n      , ''    AS BF_TBL_SIZE     ");
		sb.append("\n      , ''    AS BF_DATA_SIZE    ");
		sb.append("\n      , ''    AS BF_IDX_SIZE     ");
		sb.append("\n      , ''    AS BF_NUSE_SIZE    ");
		sb.append("\n      , ''    AS ANA_DTM         ");
		sb.append("\n      , ''    AS CRT_DTM         ");
		sb.append("\n      , ''    AS CHG_DTM         ");
		sb.append("\n      , ''    AS PDM_DESCN       ");
		sb.append("\n      , ''    AS TBL_DQ_EXP_YN   ");
		sb.append("\n      , ''    AS DDL_TBL_ERR_EXS ");
		sb.append("\n      , ''    AS DDL_TBL_ERR_CD  ");
		sb.append("\n      , ''    AS DDL_TBL_ERR_DESCN  ");
		sb.append("\n      , ''    AS DDL_COL_ERR_EXS    ");
		sb.append("\n      , ''    AS DDL_COL_ERR_CD     ");
		sb.append("\n      , ''    AS DDL_COL_ERR_DESCN  ");
		sb.append("\n      , ''    AS PDM_TBL_ERR_EXS    ");
		sb.append("\n      , ''    AS PDM_TBL_ERR_CD     ");
		sb.append("\n      , ''    AS PDM_TBL_ERR_DESCN  ");
		sb.append("\n      , ''    AS PDM_COL_ERR_EXS    ");
		sb.append("\n      , ''    AS PDM_COL_ERR_CD     ");
		sb.append("\n      , ''    AS PDM_COL_ERR_DESCN  ");
		sb.append("\n      , ''    AS DDL_TBL_EXTNC_EXS  ");
		sb.append("\n      , ''    AS PDM_TBL_EXTNC_EXS  ");
		sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
		sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_IFX_SYSTABLES A    ");
		sb.append("\n        INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                   ) S ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.OWNER) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL     ");
		sb.append("\n SELECT S.DB_SCH_ID          ");
		sb.append("\n      , A.TABNAME AS DBC_TBL_NM   ");
		sb.append("\n      , B.COLNAME AS DBC_COL_NM   ");
		sb.append("\n      , NULL AS DBC_COL_KOR_NM  ");
		sb.append("\n      , 1   AS VERS ");
		sb.append("\n      , to_char(NULL) AS REG_TYP ");
		sb.append("\n      , SYSDATE AS REG_DTM  ");
		sb.append("\n      , to_date(null) as UPD_DTM ");
		sb.append("\n      , NULL AS DESCN ");
		sb.append("\n      , NULL AS DDL_COL_ID ");
		sb.append("\n      , NULL AS PDM_COL_ID ");
		sb.append("\n      , NULL AS ITM_ID ");
		sb.append("\n        ,CASE WHEN B.COLTYPE = '0' THEN 'CHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '256' THEN 'CHAR'  "); //char not null
		sb.append("\n              WHEN B.COLTYPE = '1' THEN 'SMALLINT'  ");
		sb.append("\n              WHEN B.COLTYPE = '257' THEN 'SMALLINT'  "); //SMALLINT not null
		sb.append("\n              WHEN B.COLTYPE = '2' THEN 'INTEGER'  ");
		sb.append("\n              WHEN B.COLTYPE = '258' THEN 'INTEGER'  "); //INTEGER not null
		sb.append("\n              WHEN B.COLTYPE = '3' THEN 'FLOAT'  ");
		sb.append("\n              WHEN B.COLTYPE = '259' THEN 'FLOAT'  "); //FLOAT not null
		sb.append("\n              WHEN B.COLTYPE = '4' THEN 'SMALLFLOAT'  ");
		sb.append("\n              WHEN B.COLTYPE = '260' THEN 'SMALLFLOAT'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '5' THEN 'DECIMAL'  ");
		sb.append("\n              WHEN B.COLTYPE = '261' THEN 'DECIMAL'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '6' THEN 'SERIAL'  ");
		sb.append("\n              WHEN B.COLTYPE = '262' THEN 'SERIAL'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '7' THEN 'DATE'  ");
		sb.append("\n              WHEN B.COLTYPE = '263' THEN 'DATE'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '8' THEN 'MONEY'  ");
		sb.append("\n              WHEN B.COLTYPE = '264' THEN 'MONEY'  "); //not null
//		sb.append("\n              WHEN B.COLTYPE = '9' THEN 'NULL'  ");
		sb.append("\n              WHEN B.COLTYPE = '10' THEN 'DATETIME'  ");
		sb.append("\n              WHEN B.COLTYPE = '266' THEN 'DATETIME'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '11' THEN 'BYTE'  ");
		sb.append("\n              WHEN B.COLTYPE = '267' THEN 'BYTE'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '12' THEN 'TEXT'  ");
		sb.append("\n              WHEN B.COLTYPE = '268' THEN 'TEXT'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '13' THEN 'VARCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '269' THEN 'VARCHAR'  "); //not null
//		sb.append("\n              WHEN B.COLTYPE = '14' THEN 'INTERVAL'  ");
		sb.append("\n              WHEN B.COLTYPE = '14' THEN 'VARCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '270' THEN 'VARCHAR'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '15' THEN 'NCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '271' THEN 'NCHAR'  ");  //not null
		sb.append("\n              WHEN B.COLTYPE = '16' THEN 'NVARCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '272' THEN 'NVARCHAR'  ");  //not null
		sb.append("\n              WHEN B.COLTYPE = '17' OR  B.COLTYPE = '273' THEN 'INT8'  ");
		sb.append("\n              WHEN B.COLTYPE = '18' OR  B.COLTYPE = '274' THEN 'SERIAL8'  ");
		sb.append("\n              WHEN B.COLTYPE = '19' OR  B.COLTYPE = '275' THEN 'SET'  ");
		sb.append("\n              WHEN B.COLTYPE = '20' OR  B.COLTYPE = '276' THEN 'MULTISET'  ");
		sb.append("\n              WHEN B.COLTYPE = '21' OR  B.COLTYPE = '277' THEN 'LIST'  ");
		sb.append("\n              WHEN B.COLTYPE = '22' OR  B.COLTYPE = '278' THEN 'ROW'  ");
		sb.append("\n              WHEN B.COLTYPE = '23' OR  B.COLTYPE = '279' THEN 'COLLETION'  ");
		sb.append("\n              WHEN B.COLTYPE = '24' OR  B.COLTYPE = '280' THEN 'ROWREF'  ");
		sb.append("\n              WHEN B.COLTYPE = '40' THEN 'LVARCHAR'  ");
//		sb.append("\n              WHEN B.COLTYPE = '40' AND  B.COLLENGTH = '2048' THEN 'LVARCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '41' THEN 'BLOB, CLOB, BOOLEAN'  ");
		sb.append("\n              WHEN B.COLTYPE = '43' THEN 'LVARCHAR'  ");
		sb.append("\n              WHEN B.COLTYPE = '45' THEN 'BOOLEAN'  ");
		sb.append("\n              WHEN B.COLTYPE = '52' THEN 'BIGINT'  ");
		sb.append("\n              WHEN B.COLTYPE = '53' THEN 'BIGSERIAL'  ");
		sb.append("\n              ELSE B.COLTYPE END AS DATA_TYPE ");
		sb.append("\n      , B.COLLENGTH  AS DATA_LEN   ");
		sb.append("\n      , NULL AS DATA_PNUM          ");
		sb.append("\n      , NULL AS DATA_PNT           ");
		sb.append("\n        ,CASE WHEN B.COLTYPE = '256' THEN 'Y'  "); //char not null
		sb.append("\n              WHEN B.COLTYPE = '257' THEN 'Y'  "); //SMALLINT not null
		sb.append("\n              WHEN B.COLTYPE = '258' THEN 'Y'  "); //INTEGER not null
		sb.append("\n              WHEN B.COLTYPE = '259' THEN 'Y'  "); //FLOAT not null
		sb.append("\n              WHEN B.COLTYPE = '260' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '261' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '262' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '263' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '264' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '9' THEN 'N'  "); //null
		sb.append("\n              WHEN B.COLTYPE = '266' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '267' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '268' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '269' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '270' THEN 'Y'  "); //not null
		sb.append("\n              WHEN B.COLTYPE = '271' THEN 'Y'  ");  //not null
		sb.append("\n              WHEN B.COLTYPE = '272' THEN 'Y'  ");  //not null
		sb.append("\n              ELSE '' END AS NULL_YN ");
		sb.append("\n      , NULL AS DEFLT_LEN          ");
		sb.append("\n      , B.DEFAULTVALUE AS DATA_DEFAULT  ");
		sb.append("\n      , PK.PK_YN AS PK_YN  ");
		sb.append("\n      , B.COLNO AS ORD ");
		sb.append("\n      , PK.COLNO AS PK_ORD ");
		sb.append("\n      , '' AS COL_DESCN              ");   
	    sb.append("\n      , '' AS COL_DQ_EXP_YN          ");
	    sb.append("\n      , '' AS DDL_COL_EXTNC_YN       ");
	    sb.append("\n      , '' AS DDL_ORD_ERR_EXS        ");
	    sb.append("\n      , '' AS DDL_PK_YN_ERR_EXS      ");
	    sb.append("\n      , '' AS DDL_PK_ORD_ERR_EXS     ");
	    sb.append("\n      , '' AS DDL_NULL_YN_ERR_EXS    ");
	    sb.append("\n      , '' AS DDL_DEFLT_ERR_EXS      ");
	    sb.append("\n      , '' AS DDL_CMMT_ERR_EXS       ");
	    sb.append("\n      , '' AS DDL_DATA_TYPE_ERR_EXS  ");
	    sb.append("\n      , '' AS DDL_DATA_LEN_ERR_EXS   ");
	    sb.append("\n      , '' AS DDL_DATA_PNT_ERR_EXS   ");
	    sb.append("\n      , '' AS DDL_COL_ERR_EXS        ");
	    sb.append("\n      , '' AS PDM_COL_EXTNC_EXS      ");
	    sb.append("\n      , '' AS PDM_ORD_ERR_EXS        ");
	    sb.append("\n      , '' AS PDM_PK_YN_ERR_EXS      ");
	    sb.append("\n      , '' AS PDM_PK_ORD_ERR_EXS     ");
	    sb.append("\n      , '' AS PDM_NULL_YN_ERR_EXS    ");
	    sb.append("\n      , '' AS PDM_DEFLT_ERR_EXS      ");
	    sb.append("\n      , '' AS PDM_CMMT_ERR_EXS       ");
	    sb.append("\n      , '' AS PDM_DATA_TYPE_ERR_EXS  ");
	    sb.append("\n      , '' AS PDM_DATA_LEN_ERR_EXS   ");
	    sb.append("\n      , '' AS PDM_DATA_PNT_ERR_EXS   ");
	    sb.append("\n      , '' AS PDM_COL_ERR_EXS        ");
	    sb.append("\n      , '' AS COL_ERR_CONTS          ");
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
		sb.append("\n FROM WAE_IFX_SYSTABLES A    ");
		sb.append("\n      INNER JOIN  WAE_IFX_SYSCOLUMNS B    ");
		sb.append("\n         ON A.TABID = B.TABID ");
		sb.append("\n      INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                     ) S  ");
		sb.append("\n         ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n        AND UPPER(A.OWNER) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n       LEFT OUTER JOIN (    ");
		sb.append("\n       SELECT A.DB_CONN_TRG_ID, A.OWNER, A.TABID, D.COLNO, 'Y' AS PK_YN ");
		sb.append("\n         FROM WAE_IFX_SYSTABLES A                 ");
		sb.append("\n              INNER JOIN WAE_IFX_SYSCONSTRAINTS B ");
		sb.append("\n                 ON A.TABID = B.TABID             ");
		sb.append("\n              INNER JOIN WAE_IFX_SYSINDEXES C     ");
		sb.append("\n                 ON B.IDXNAME = C.IDXNAME         ");
		sb.append("\n                AND A.TABID = C.TABID             ");
		sb.append("\n              INNER JOIN WAE_IFX_SYSCOLUMNS D     ");
		sb.append("\n                 ON A.TABID = D.TABID             ");
		sb.append("\n                AND (D.COLNO = C.PART1 OR         ");
		sb.append("\n                     D.COLNO = C.PART2 OR         ");
		sb.append("\n                     D.COLNO = C.PART3 OR         ");
		sb.append("\n                     D.COLNO = C.PART4 OR         ");
		sb.append("\n                     D.COLNO = C.PART5 OR         ");
		sb.append("\n                     D.COLNO = C.PART6 OR         ");
		sb.append("\n                     D.COLNO = C.PART7 OR         ");
		sb.append("\n                     D.COLNO = C.PART8 OR         ");
		sb.append("\n                     D.COLNO = C.PART9 OR         ");
		sb.append("\n                     D.COLNO = C.PART10 OR        ");
		sb.append("\n                     D.COLNO = C.PART11 OR        ");
		sb.append("\n                     D.COLNO = C.PART12 OR        ");
		sb.append("\n                     D.COLNO = C.PART13 OR        ");
		sb.append("\n                     D.COLNO = C.PART14 OR        ");
		sb.append("\n                     D.COLNO = C.PART16 OR        ");
		sb.append("\n                     D.COLNO = C.PART16           ");
		sb.append("\n                    )                             ");
		sb.append("\n             ) PK  ");
		sb.append("\n         ON A.DB_CONN_TRG_ID = PK.DB_CONN_TRG_ID ");
		sb.append("\n        AND UPPER(A.OWNER) = UPPER(PK.OWNER) ");
		sb.append("\n        AND A.TABID = PK.TABID ");
		sb.append("\n        AND B.COLNO = PK.COLNO ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

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
		sb.append("\n INSERT INTO WAT_DBC_IDX     ");
		sb.append("\n SELECT S.DB_SCH_ID AS DB_SCH_ID        ");
		sb.append("\n      , A.TABNAME AS DBC_TBL_NM  ");
		sb.append("\n      , B.IDXNAME AS DBC_IDX_NM  ");
		sb.append("\n      , S.DB_CONN_TRG_ID     ");
		sb.append("\n      , '' AS DBC_IDX_KOR_NM  ");
		sb.append("\n      , 1  AS VERS         ");
		sb.append("\n      , NULL AS REG_TYP    ");
		sb.append("\n      , SYSDATE AS REG_DTM ");
		sb.append("\n      , NULL AS REG_USER   ");
		sb.append("\n      , to_date(null) AS UPD_DTM ");
		sb.append("\n      , NULL AS UPD_USER   ");
		sb.append("\n      , NULL AS DESCN      ");
		sb.append("\n      , NULL AS DBC_TBL_SPAC_NM  ");
		sb.append("\n      , NULL AS DDL_IDX_ID ");
		sb.append("\n      , NULL AS PDM_IDX_ID ");
		sb.append("\n      , NULL AS PK_YN  ");
		sb.append("\n      , NULL AS UQ_YN   ");
		sb.append("\n      , NULL AS COL_EACNT    ");
		sb.append("\n      , NULL AS IDX_SIZE     ");
		sb.append("\n      , NULL AS BF_IDX_EACNT     ");
		sb.append("\n      , NULL AS BF_IDX_SIZE      ");
		sb.append("\n      , to_date(null) AS ANA_DTM ");
		sb.append("\n      , to_date(null) AS CRT_DTM ");
		sb.append("\n      , to_date(null) AS CHG_DTM ");
		sb.append("\n      , NULL  AS IDX_DQ_EXP_YN        ");
		sb.append("\n      , 0  AS SGMT_BYTE_SIZE       ");
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
		sb.append("\n   FROM WAE_IFX_SYSTABLES A  ");
		sb.append("\n        INNER JOIN WAE_IFX_SYSINDEXES B ");
		sb.append("\n           ON A.TABID = B.TABID ");
		sb.append("\n        INNER JOIN (       ");
		sb.append(getdbconnsql());
		sb.append("\n                    ) S    ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.OWNER) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL    ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID    ");
		sb.append("\n      , T.TABNAME AS DBC_TBL_NM  ");
		sb.append("\n      , C.COLNAME AS DBC_IDX_COL_NM      ");
		sb.append("\n      , A.IDXNAME AS DBC_IDX_NM     ");
		sb.append("\n      , NULL AS DBC_IDX_COL_KOR_NM  ");
		sb.append("\n      , 1 AS VERS ");
		sb.append("\n      , TO_CHAR(NULL) AS REG_TYP ");
		sb.append("\n      , SYSDATE REG_DTM ");
		sb.append("\n      , NULL AS REG_USER          ");
		sb.append("\n      , to_date(null) AS UPD_DTM  ");
		sb.append("\n      , NULL AS UPD_USER          ");
		sb.append("\n      , NULL AS DESCN             ");
		sb.append("\n      , NULL AS DDL_IDX_COL_ID    ");
		sb.append("\n      , NULL AS PDM_IDX_COL_ID    ");
		sb.append("\n      , A.ORD AS ORD        ");
		sb.append("\n      , NULL AS SORT_TYPE  ");
		sb.append("\n        ,CASE WHEN C.COLTYPE = '0' THEN 'CHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '256' THEN 'CHAR'  "); //char not null
		sb.append("\n              WHEN C.COLTYPE = '1' THEN 'SMALLINT'  ");
		sb.append("\n              WHEN C.COLTYPE = '257' THEN 'SMALLINT'  "); //SMALLINT not null
		sb.append("\n              WHEN C.COLTYPE = '2' THEN 'INTEGER'  ");
		sb.append("\n              WHEN C.COLTYPE = '258' THEN 'INTEGER'  "); //INTEGER not null
		sb.append("\n              WHEN C.COLTYPE = '3' THEN 'FLOAT'  ");
		sb.append("\n              WHEN C.COLTYPE = '259' THEN 'FLOAT'  "); //FLOAT not null
		sb.append("\n              WHEN C.COLTYPE = '4' THEN 'SMALLFLOAT'  ");
		sb.append("\n              WHEN C.COLTYPE = '260' THEN 'SMALLFLOAT'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '5' THEN 'DECIMAL'  ");
		sb.append("\n              WHEN C.COLTYPE = '261' THEN 'DECIMAL'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '6' THEN 'SERIAL'  ");
		sb.append("\n              WHEN C.COLTYPE = '262' THEN 'SERIAL'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '7' THEN 'DATE'  ");
		sb.append("\n              WHEN C.COLTYPE = '263' THEN 'DATE'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '8' THEN 'MONEY'  ");
		sb.append("\n              WHEN C.COLTYPE = '264' THEN 'MONEY'  "); //not null
//		sb.append("\n              WHEN C.COLTYPE = '9' THEN 'NULL'  ");
		sb.append("\n              WHEN C.COLTYPE = '10' THEN 'DATETIME'  ");
		sb.append("\n              WHEN C.COLTYPE = '266' THEN 'DATETIME'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '11' THEN 'BYTE'  ");
		sb.append("\n              WHEN C.COLTYPE = '267' THEN 'BYTE'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '12' THEN 'TEXT'  ");
		sb.append("\n              WHEN C.COLTYPE = '268' THEN 'TEXT'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '13' THEN 'VARCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '269' THEN 'VARCHAR'  "); //not null
//		sb.append("\n              WHEN C.COLTYPE = '14' THEN 'INTERVAL'  ");
		sb.append("\n              WHEN C.COLTYPE = '14' THEN 'VARCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '270' THEN 'VARCHAR'  "); //not null
		sb.append("\n              WHEN C.COLTYPE = '15' THEN 'NCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '271' THEN 'NCHAR'  ");  //not null
		sb.append("\n              WHEN C.COLTYPE = '16' THEN 'NVARCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '272' THEN 'NVARCHAR'  ");  //not null
		sb.append("\n              WHEN C.COLTYPE = '17' OR  C.COLTYPE = '273' THEN 'INT8'  ");
		sb.append("\n              WHEN C.COLTYPE = '18' OR  C.COLTYPE = '274' THEN 'SERIAL8'  ");
		sb.append("\n              WHEN C.COLTYPE = '19' OR  C.COLTYPE = '275' THEN 'SET'  ");
		sb.append("\n              WHEN C.COLTYPE = '20' OR  C.COLTYPE = '276' THEN 'MULTISET'  ");
		sb.append("\n              WHEN C.COLTYPE = '21' OR  C.COLTYPE = '277' THEN 'LIST'  ");
		sb.append("\n              WHEN C.COLTYPE = '22' OR  C.COLTYPE = '278' THEN 'ROW'  ");
		sb.append("\n              WHEN C.COLTYPE = '23' OR  C.COLTYPE = '279' THEN 'COLLETION'  ");
		sb.append("\n              WHEN C.COLTYPE = '24' OR  C.COLTYPE = '280' THEN 'ROWREF'  ");
		sb.append("\n              WHEN C.COLTYPE = '40' THEN 'LVARCHAR'  ");
//		sb.append("\n              WHEN C.COLTYPE = '40' AND  B.COLLENGTH = '2048' THEN 'LVARCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '41' THEN 'BLOB, CLOB, BOOLEAN'  ");
		sb.append("\n              WHEN C.COLTYPE = '43' THEN 'LVARCHAR'  ");
		sb.append("\n              WHEN C.COLTYPE = '45' THEN 'BOOLEAN'  ");
		sb.append("\n              WHEN C.COLTYPE = '52' THEN 'BIGINT'  ");
		sb.append("\n              WHEN C.COLTYPE = '53' THEN 'BIGSERIAL'  ");
		sb.append("\n              ELSE C.COLTYPE END AS DATA_TYPE ");
		sb.append("\n      , NULL AS DATA_PNUM  ");
		sb.append("\n      , C.COLLENGTH  AS DATA_LEN   ");
		sb.append("\n      , NULL AS DATA_PNT   ");
		sb.append("\n      , NULL AS IDXCOL_DQ_EXP_YN      ");
		sb.append("\n      , '' AS DDL_IDX_COL_LNM_ERR_EXS ");
		sb.append("\n      , '' AS DDL_IDX_COL_ORD_ERR_EXS ");
		sb.append("\n      , '' AS DDL_IDX_COL_SORT_TYPE_ERR_EXS  ");
		sb.append("\n      , '' AS DDL_IDX_COL_EXTNC_EXS   ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_EXS     ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_CONTS   ");
		sb.append("\n      , '' AS PDM_IDX_COL_LNM_ERR_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_ORD_ERR_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_SORT_TYPE_ERR_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_EXTNC_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_EXS   ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_CONTS ");
		sb.append("\n   FROM (SELECT IDXNAME        ");
		sb.append("\n                ,OWNER         ");
		sb.append("\n                ,TABID         ");
		sb.append("\n                ,IDXTYPE       ");
		sb.append("\n                ,CLUSTERED     ");
		sb.append("\n                ,LEVELS        ");
		sb.append("\n                ,LEAVES        ");
		sb.append("\n                ,NUNIQUE       ");
		sb.append("\n                ,CLUST         ");
		sb.append("\n                ,B.LVL AS ORD  ");
		sb.append("\n                ,CASE WHEN B.LVL = 1 THEN A.PART1  ");
		sb.append("\n                     WHEN B.LVL = 2 THEN A.PART2   ");
		sb.append("\n                     WHEN B.LVL = 3 THEN A.PART3   ");
		sb.append("\n                     WHEN B.LVL = 4 THEN A.PART4   ");
		sb.append("\n                     WHEN B.LVL = 5 THEN A.PART5   ");
		sb.append("\n                     WHEN B.LVL = 6 THEN A.PART6   ");
		sb.append("\n                     WHEN B.LVL = 7 THEN A.PART7   ");
		sb.append("\n                     WHEN B.LVL = 8 THEN A.PART8   ");
		sb.append("\n                     WHEN B.LVL = 9 THEN A.PART9   ");
		sb.append("\n                     WHEN B.LVL = 10 THEN A.PART10 ");
		sb.append("\n                     WHEN B.LVL = 11 THEN A.PART11 ");
		sb.append("\n                     WHEN B.LVL = 12 THEN A.PART12 ");
		sb.append("\n                     WHEN B.LVL = 13 THEN A.PART13 ");
		sb.append("\n                     WHEN B.LVL = 14 THEN A.PART14 ");
		sb.append("\n                     WHEN B.LVL = 15 THEN A.PART15 ");
		sb.append("\n                     WHEN B.LVL = 16 THEN A.PART16 ");
		sb.append("\n                 END AS IDX_COLNO       ");
		sb.append("\n           FROM WAE_IFX_SYSINDEXES A    ");
		sb.append("\n                , (SELECT LEVEL LVL FROM DUAL CONNECT BY LEVEL <= 16 ) B ");
		sb.append("\n         ) A                              ");
		sb.append("\n         INNER JOIN WAE_IFX_SYSTABLES T   ");
		sb.append("\n            ON A.TABID = T.TABID          ");
		sb.append("\n         INNER JOIN WAE_IFX_SYSCOLUMNS C  ");
		sb.append("\n            ON A.TABID = C.TABID          ");
		sb.append("\n           AND A.IDX_COLNO = C.COLNO      ");
		sb.append("\n         INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                     ) S  ");
		sb.append("\n           ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(T.OWNER) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n  WHERE A.IDX_COLNO > 0                   ");
		sb.append("\n    AND T.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
//	private int insertwatcond() throws Exception {
////		String tdbnm = targetDbmsDM.getDbms_enm();
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n INSERT INTO WAT_DBC_CND ");
//		sb.append("\n SELECT S.DB_SCH_ID    ");
//		sb.append("\n      , A.TBL_NM            ");
//		sb.append("\n      , A.CNST_CND_NM   ");
//		sb.append("\n      , C.COL_CNT  ");
//		sb.append("\n      , '1'             ");
//		sb.append("\n      , NULL             ");
//		sb.append("\n      , SYSDATE     ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , CASE WHEN INDEX_NAME IS NULL THEN 'N' ELSE 'Y' END   ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n      , ''              ");
//		sb.append("\n   FROM WAE_ORA_CND A   ");
//		sb.append("\n  INNER JOIN (                 ");
//		sb.append(getdbconnsql());
//		sb.append("\n  ) S                               ");
//		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM   ");
//		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM          ");
//		sb.append("\n  INNER JOIN WAE_ORA_DB B                  ");
//		sb.append("\n     ON A.DB_NM = B.DB_NM                   ");
//		sb.append("\n  INNER JOIN (SELECT DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ");
//		sb.append("\n                           , COUNT(CNST_CND_COL_NM) AS COL_CNT  ");
//		sb.append("\n                    FROM WAE_ORA_CND_COL  ");
//		sb.append("\n                   GROUP BY DB_NM, SCH_NM, TBL_NM, CNST_CND_NM ) C ");
//		sb.append("\n      ON A.DB_NM = C.DB_NM ");
//		sb.append("\n     AND A.SCH_NM = C.SCH_NM ");
//		sb.append("\n     AND A.TBL_NM = C.TBL_NM ");
//		sb.append("\n     AND A.CNST_CND_NM = C.CNST_CND_NM ");
//		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgId+"'     ");
//
//		
//
//		
//		
//		return setExecuteUpdate_Org(sb.toString());
//
//	}
//
//	/**  insomnia
//	 * @throws Exception */
//	private int insertwatcondcol() throws Exception {
////		String tdbnm = targetDbmsDM.getDbms_enm();
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n INSERT INTO WAT_DBC_CND_COL                                 ");
//		sb.append("\n SELECT S.DB_SCH_ID                              ");
//		sb.append("\n      , TBL_NM                                               ");
//		sb.append("\n      , CNST_CND_NM                                          ");
//		sb.append("\n      , CNST_CND_COL_NM                                      ");
//		sb.append("\n      , COL_NM                                               ");
//		sb.append("\n      , '1'                                                  ");
//		sb.append("\n      , NULL                                                  ");
//		sb.append("\n      , SYSDATE                                              ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , SEQ                                                  ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n   FROM WAE_ORA_CND_COL A                                    ");
//		sb.append("\n  INNER JOIN (                                ");
//		sb.append(getdbconnsql());
//		sb.append("\n  ) S                               ");
//		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
//		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
//		sb.append("\n  INNER JOIN WAE_ORA_DB B                                    ");
//		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
//		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgId+"'     ");
//
//		return setExecuteUpdate_Org(sb.toString());
//	}
//
//	/**  insomnia */
//	private int insertwatspac() throws Exception {
////		String tdbnm = targetDbmsDM.getDbms_enm();
//		StringBuffer sb = new StringBuffer();
//		sb.append("\n INSERT INTO WAT_DBC_TBL_SPAC                                ");
//		sb.append("\n SELECT B.DB_CONN_TRG_ID                                     ");
//		sb.append("\n      , TBL_SPAC_NM                                          ");
//		sb.append("\n      , '1'                                                  ");
//		sb.append("\n      , NULL                                                  ");
//		sb.append("\n      , SYSDATE                                              ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n      , ''                                                   ");
//		sb.append("\n   FROM WAE_ORA_TBL_SPAC A                                   ");
//		sb.append("\n  INNER JOIN WAE_ORA_DB B                                    ");
//		sb.append("\n     ON A.DB_NM = B.DB_NM                                   ");
//		sb.append("\n    WHERE A.DB_NM = '"+dbConnTrgId+"'     ");
//
//		return setExecuteUpdate_Org(sb.toString());
//	}

}
