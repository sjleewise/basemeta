/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorSybaseIq.java
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

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.wise.executor.dm.TargetDbmsDM;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorSybaseIq.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : moonsungeun
 * 6. 작성일   : 2015. 7. 17. 
 * </PRE>
 */
public class CollectorSybaseIq{

	private static final Logger logger = Logger.getLogger(CollectorSybaseIq.class);

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


	public CollectorSybaseIq() {

	}

	/** insomnia */
	public CollectorSybaseIq(Connection source, Connection target,	TargetDbmsDM targetDbmsDM) {
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


/*
			//SYSDATABASES ---------------------------------------------------
			cnt = insertDBINFO();
			logger.debug(sp + (++p) + ". insertDBINFO " + cnt + " OK!!");
		
			//SYSGROUPS ---------------------------------------------------
			cnt = insertSYSGROUP ();
			logger.debug(sp + (++p) + ". insertSYSGROUPS " + cnt + " OK!!");

			//SYSUSERPERMS ---------------------------------------------------
			cnt = insertSYSUSERPERMS();
			logger.debug(sp + (++p) + ". insertSYSUSERPERMS " + cnt + " OK!!");

			//IQSTATUS ---------------------------------------------------
			cnt = insertIQSTATUS();
			logger.debug(sp + (++p) + ". insertIQSTATUS " + cnt + " OK!!");

			//DBPROPERTY ---------------------------------------------------
			cnt = insertDBPROPERTY();
			logger.debug(sp + (++p) + ". insertDBPROPERTY " + cnt + " OK!!");

			//DB SIZE ---------------------------------------------------
//			처리 시간이 너무 오래 걸려서 추출 대상에서 제외.
//			cnt = insertDBSIZE();
//			logger.debug(sp + (++p) + ". insertDBSIZE " + cnt + " OK!!");

			//SYSTABLEPERM ---------------------------------------------------
			cnt = insertSYSTABLEPERM();
			logger.debug(sp + (++p) + ". insertSYSTABLEPERM " + cnt + " OK!!");
*/
		//SYSTABLES ---------------------------------------------------
		cnt = insertSYSTABLES();
		logger.debug(sp + (++p) + ". insertSYSTABLES " + cnt + " OK!!");

		//SYSCOLUMNS ---------------------------------------------------
		cnt = insertSYSCOLUMNS();
		logger.debug(sp + (++p) + ". insertSYSCOLUMNS " + cnt + " OK!!");

		//SYSINDEXES ---------------------------------------------------
		cnt = insertSYSINDEXES();
		logger.debug(sp + (++p) + ". insertSYSINDEXES " + cnt + " OK!!");

		//SYSINDEXES COLUMN ---------------------------------------------------
		cnt = insertSYSINDEX_COLUMNS();
		logger.debug(sp + (++p) + ". insertSYSINDEX_COLUMNS " + cnt + " OK!!");

		//SYSFOREIGNKEY ---------------------------------------------------
//		cnt = insertSYSFOREIGNKEY();
//		logger.debug(sp + (++p) + ". insertSYSFOREIGNKEY " + cnt + " OK!!");

		//SYSFKCOL  ---------------------------------------------------
//		cnt = insertSYSFKCOL();
//		logger.debug(sp + (++p) + ". insertSYSFKCOL " + cnt + " OK!!");

		//VIEW  ---------------------------------------------------
//		cnt = insertSYSVIEW();
//		logger.debug(sp + (++p) + ". insertSYSVIEW " + cnt + " OK!!");

		//PROCEDURE  ---------------------------------------------------
//		cnt = insertSYSPROCEDURE();
//		logger.debug(sp + (++p) + ". insertSYSPROCEDURE " + cnt + " OK!!");

		con_org.commit();
		result = true;
		return result;

	}

	/**
	 * DBC DBINFO  저장
	 * @return int 등록된 DB정보 갯수
	 * @throws SQLException
	 */
	private int insertDBINFO() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n exec sa_db_info ");
//		logger.debug(sb.toString());
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_IQ_DBINFO ( ");
		sb.append("\n   SEQ_NUM ,ALIAS ,FILE_NM ,CONNCOUNT ,PAGESIZE ");
		sb.append("\n   ,LOGNAME ");
		sb.append("\n   ,DB_CONN_TRG_ID "); //, DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n   TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?)");
		sb.append("\n  ,TRIM(?) ");
		sb.append("\n  ,TRIM(?) "); //, TRIM(?)
		sb.append("\n ) ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal(1));
				pst_org.setString(rsGetCnt++, rs.getString(2));
				pst_org.setString(rsGetCnt++, rs.getString(3));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal(4));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal(5));
				pst_org.setString(rsGetCnt++, rs.getString(6));
				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC SYSGROUP  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSGROUP() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT GROUP_ID ,GROUP_MEMBER ");
		sb.append("\n   FROM SYS.SYSGROUP ");
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_SYSGROUP ( ");
		sb.append("\n    GROUP_ID, GROUP_MEMBER ");
		sb.append("\n   ,DB_CONN_TRG_ID "); //, DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n    TRIM(?), TRIM(?) ");
		sb.append("\n   ,TRIM(?) ");  //, TRIM(?)
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GROUP_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GROUP_MEMBER"));
				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC SYSUSERPERMS  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSUSERPERMS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT user_id, user_name as user_nm, resourceauth, dbaauth, scheduleauth, user_group,publishauth, remotedbaauth, remarks ");
		sb.append("\n  FROM SYS.SYSUSERPERMS ");
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_SYSUSERPERMS ( ");
		sb.append("\n user_id, user_nm, resourceauth, dbaauth, scheduleauth, user_group,publishauth, remotedbaauth, remarks, DB_CONN_TRG_ID "); //, DB_SCH_ID 
		sb.append("\n ) VALUES ( ");
		sb.append("\n TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");  //, TRIM(?)
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("user_id"));
				pst_org.setString(rsGetCnt++, rs.getString("user_nm"));
				pst_org.setString(rsGetCnt++, rs.getString("resourceauth"));

				pst_org.setString(rsGetCnt++, rs.getString("dbaauth"));
				pst_org.setString(rsGetCnt++, rs.getString("scheduleauth"));
				pst_org.setString(rsGetCnt++, rs.getString("user_group"));

				pst_org.setString(rsGetCnt++, rs.getString("publishauth"));
				pst_org.setString(rsGetCnt++, rs.getString("remotedbaauth"));
				pst_org.setString(rsGetCnt++, rs.getString("remarks"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC IQSTATUS  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertIQSTATUS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n EXEC SP_IQSTATUS ");
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_IQSTATUS ( ");
		sb.append("\n NAME ,VALUE, DB_CONN_TRG_ID "); //, DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n TRIM(?), TRIM(?), TRIM(?) "); //, TRIM(?)
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("VALUE"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC DBPROPERTY  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertDBPROPERTY() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n exec sa_db_properties  ");
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_DBPROPERTIES ( ");
		sb.append("\n SEQ_NUM ,PROPNUM ,PROPNAME ,PROPDESCRIPTION ,VALUE, DB_CONN_TRG_ID "); //, DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) "); //, TRIM(?)
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NUMBER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PROPNUM"));
				pst_org.setString(rsGetCnt++, rs.getString("PROPNAME"));
				pst_org.setString(rsGetCnt++, rs.getString("PROPDESCRIPTION"));
				pst_org.setString(rsGetCnt++, rs.getString("VALUE"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC DB SIZE  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertDBSIZE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n sp_iqdbsize  "); //-->sp_iqdbspace
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_DBSIZE ( ");
		sb.append("\n DATABASE_FILE, PHYSICALBLOCKS, KBYTES, PAGES,COMPRESSEDPAGES, NBLOCKS, CATALOGBLOCKS,DB_CONN_TRG_ID,DB_SCH_ID ");
		sb.append("\n ) VALUES ( ");
		sb.append("\n TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("DATABASE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PHYSICALBLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KBYTES"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PAGES"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COMPRESSEDPAGES"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NBLOCKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CATALOGBLOCKS"));

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
	 * DBC SYSTABLEPERM  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSTABLEPERM() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT STABLE_ID,GRANTEE,GRANTOR,null as TTABLE_ID,SELECTAUTH "); 
		sb.append("\n        ,INSERTAUTH,DELETEAUTH,UPDATEAUTH ,UPDATECOLS ,ALTERAUTH  "); 
		sb.append("\n        ,REFERENCEAUTH "); 
		sb.append("\n   FROM SYS.SYSTABLEPERM  "); 
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_SYSTABLEPERM ( ");
		sb.append("\n      STABLE_ID,GRANTEE,GRANTOR,TTABLE_ID,SELECTAUTH ");
		sb.append("\n      ,INSERTAUTH,DELETEAUTH,UPDATEAUTH ,UPDATECOLS ,ALTERAUTH  ");
		sb.append("\n      ,REFERENCEAUTH  ");
		sb.append("\n      ,DB_CONN_TRG_ID   "); //,DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n     TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n    ,TRIM(?)");
		sb.append("\n    ,TRIM(?) "); //, TRIM(?)
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STABLE_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GRANTEE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("GRANTOR"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TTABLE_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("SELECTAUTH"));
				
				pst_org.setString(rsGetCnt++, rs.getString("INSERTAUTH"));
				pst_org.setString(rsGetCnt++, rs.getString("DELETEAUTH"));
				pst_org.setString(rsGetCnt++, rs.getString("UPDATEAUTH"));
				pst_org.setString(rsGetCnt++, rs.getString("UPDATECOLS"));
				pst_org.setString(rsGetCnt++, rs.getString("ALTERAUTH"));
				
				pst_org.setString(rsGetCnt++, rs.getString("REFERENCEAUTH"));
				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC SYSTABLES  저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSTABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n	select	 ");              
		sb.append("\n		 A.table_id     ");             
		sb.append("\n		,A.table_name      as table_nm  ");        
		sb.append("\n		,a.table_type  ");              
		sb.append("\n		,a.remarks     ");            
		sb.append("\n		,c.file_id     ");              
		sb.append("\n		,c.dbspace_name    as dbspace_nm ");       
		sb.append("\n		,c.file_name       as file_nm    ");   
		sb.append("\n		,a.first_page  ");
		sb.append("\n		,a.last_page   ");           
		sb.append("\n		,if a.primary_root = 0 then 'N' else 'Y' endif	is_primary_root  ");
		sb.append("\n		,a.creator creator_id     ");   
		sb.append("\n		,d.user_name creator_nm   ");   
		sb.append("\n		,a.first_ext_page         ");   
		sb.append("\n		,a.last_ext_page          ");   
		sb.append("\n		,a.table_page_count       ");   
		sb.append("\n		,a.ext_page_count         ");   
		sb.append("\n		,b.create_time            ");   
		sb.append("\n		,b.update_time            ");   
		sb.append("\n		,if a.table_type <> 'VIEW' then a.view_def endif   view_def ");     
		sb.append("\n	from  SYS.SYSTABLE A     ");            
		sb.append("\n		, SYS.SYSIQTABLE B   ");              
		sb.append("\n		, SYS.SYSFILE C      ");             
		sb.append("\n		, SYS.SYSUSERPERMS D ");
		sb.append("\n	where	1	=	1        ");
		sb.append("\n	and	A.table_id	=	B.table_id ");
		sb.append("\n	and	A.creator	=	D.user_id  ");
		sb.append("\n	and	A.file_id	=	C.file_id  ");     
		sb.append("\n	and	(D.user_name	not in ('SYS') and A.creator not in (0) and c.file_id not in (0))  ");
//		logger.debug(sb.toString());

		getResultSet(sb.toString());
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_IQ_SYSTABLES ( ");
		sb.append("\n     TABLE_ID,TABLE_NM,TABLE_TYPE,REMARKS,FILE_ID  ");
		sb.append("\n    ,DBSPACE_NM,FILE_NM,FIRST_PAGE,LAST_PAGE,IS_PRIMARY_ROOT  ");
		sb.append("\n    ,CREATOR_ID,CREATOR_NM,FIRST_EXT_PAGE,LAST_EXT_PAGE,TABLE_PAGE_COUNT  ");
		sb.append("\n    ,EXT_PAGE_COUNT,CREATE_TIME,UPDATE_TIME,VIEW_DEF  ");
		sb.append("\n    ,DB_CONN_TRG_ID "); //, DB_SCH_ID 
		sb.append("\n ) VALUES ( ");
		sb.append("\n      TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n     ,TRIM(?), ?, ?, TRIM(?) ");
		sb.append("\n     ,TRIM(?) ");  //, TRIM(?) 
		sb.append("\n )");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FILE_ID"));
				
				pst_org.setString(rsGetCnt++, rs.getString("DBSPACE_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("FILE_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FIRST_PAGE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LAST_PAGE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_PRIMARY_ROOT"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("CREATOR_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FIRST_EXT_PAGE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("LAST_EXT_PAGE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_PAGE_COUNT"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("EXT_PAGE_COUNT"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("CREATE_TIME"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("UPDATE_TIME"));
				pst_org.setString(rsGetCnt++, rs.getString("VIEW_DEF"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSCOLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n 	select D.user_name as creator_nm      ");           
		sb.append("\n					,B.creator creator_id           ");                        
		sb.append("\n					,B.table_name      as table_nm  ");                                   
		sb.append("\n					,A.table_id                     ");              
		sb.append("\n					,A.column_name     as column_nm ");                                    
		sb.append("\n					,A.column_id                    ");                        
		sb.append("\n					,(select domain_name from SYS.SYSDOMAIN where  ");        
		sb.append("\n				      domain_id = A.domain_id) domain_nm         ");
		sb.append("\n					,A.domain_id   ");                                          
		sb.append("\n					,A.nulls       ");                                          
		sb.append("\n					,A.width       ");                                          
		sb.append("\n					,A.scale       ");                                          
		sb.append("\n					,A.pkey        ");      
		sb.append("\n					,A.").append("\"default\"").append(" as default_txt  ");                                       
		sb.append("\n					,A.column_type ");                                          
		sb.append("\n					,A.remarks     ");                                
		sb.append("\n				from   SYS.SYSCOLUMN A   ");                
		sb.append("\n				       , SYS.SYSTABLE B ");                  
		sb.append("\n                      , SYS.SYSFILE C ");                  
		sb.append("\n 		               , SYS.SYSUSERPERMS D ");
		sb.append("\n 	where	1	=	1 ");  
		sb.append("\n 	and	A.table_id	=	B.table_id ");
		sb.append("\n 	and	B.creator	=	D.user_id    ");
		sb.append("\n 	and	B.file_id	=	C.file_id	   ");  
		sb.append("\n 	and	(D.user_name	not in ('SYS') and B.creator not in (0) and C.file_id not in (0)) ");

		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_IQ_SYSCOLUMNS (    ");           
		sb.append("\n      CREATOR_NM,CREATOR_ID,TABLE_NM,TABLE_ID,COLUMN_NM   ");           
		sb.append("\n     ,COLUMN_ID,DOMAIN_NM,DOMAIN_ID,NULLS,WIDTH  ");           
		sb.append("\n     ,SCALE,PKEY,DEFAULT_TXT,COLUMN_TYPE,REMARKS  ");           
		sb.append("\n     ,DB_CONN_TRG_ID ");   //,DB_SCH_ID
		sb.append("\n ) VALUES ( ");
		sb.append("\n       TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");        
		sb.append("\n      ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");        
		sb.append("\n      ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");        
		sb.append("\n      ,TRIM(?) ");     //, TRIM(?)    
		sb.append("\n )  ");           
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("CREATOR_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NM"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLUMN_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DOMAIN_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("NULLS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("WIDTH"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SCALE"));
				pst_org.setString(rsGetCnt++, rs.getString("PKEY"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_TXT"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSINDEXES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n 	select  ");           
		sb.append("\n 	(select user_name from SYS.SYSUSERPERMS where user_id = SYSINDEX.creator) creator_nm ");           
		sb.append("\n 	,SYSINDEX.creator creator_id  ");           
		sb.append("\n 	,index_id  ");           
		sb.append("\n 	,index_name as index_nm  ");           
		sb.append("\n 	,index_type ");           
		sb.append("\n 	,(select file_name from SYS.SYSFILE where file_id = SYSINDEX.file_id) file_nm ");           
		sb.append("\n 	,SYSINDEX.file_id ");           
		sb.append("\n 	,table_name as table_nm   ");           
		sb.append("\n 	,SYSINDEX.table_id     ");           
		sb.append("\n 	,if \"unique\" = 'N' then 'Non-unique' ");           
		sb.append("\n 	 else ");           
		sb.append("\n 	 if \"unique\" = 'U' then 'UNIQUE constraint' else 'Unique' endif ");           
		sb.append("\n 	 endif unique_txt ");           
		sb.append("\n 	 ,(select list(string(column_name, ");           
		sb.append("\n 	 if \"order\" = 'A' then ' ASC' else ' DESC' endif))from ");           
		sb.append("\n 	 SYS.SYSIXCOL join SYS.SYSCOLUMN where ");           
		sb.append("\n 	 index_id = SYSINDEX.index_id and  ");           
		sb.append("\n 	 SYSIXCOL.table_id = SYSINDEX.table_id) order_txt  ");           
		sb.append("\n 	 ,SYSINDEX.remarks  ");           
		sb.append("\n from  SYS.SYSTABLE join SYS.SYSINDEX   ");           

		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_IQ_SYSINDEXES (    ");           
		sb.append("\n     CREATOR_NM,CREATOR_ID,INDEX_ID,INDEX_NM,INDEX_TYPE  ");           
		sb.append("\n     ,FILE_NM,FILE_ID,TABLE_NM,TABLE_ID,UNIQUE_TXT  ");           
		sb.append("\n     ,ORDER_TXT,REMARKS ");           
		sb.append("\n     ,DB_CONN_TRG_ID ");          //,DB_SCH_ID 
		sb.append("\n ) VALUES (    ");           
		sb.append("\n        TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");        
		sb.append("\n       ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");        
		sb.append("\n       ,TRIM(?), TRIM(?) ");        
		sb.append("\n       ,TRIM(?) ");      //, TRIM(?)  
		sb.append("\n )    ");      
		

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("CREATOR_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEX_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
				
				pst_org.setString(rsGetCnt++, rs.getString("FILE_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FILE_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("UNIQUE_TXT"));
				
				pst_org.setString(rsGetCnt++, rs.getString("ORDER_TXT"));
				pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));
				
				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC SYSINDEX COLUMNS 저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSINDEX_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n 	select									          ");
		sb.append("\n 	 B.index_id                       ");            
		sb.append("\n 	,B.index_name       as index_nm   ");            
		sb.append("\n 	,B.creator creator_id             ");            
		sb.append("\n 	,B.index_type                     ");            
		sb.append("\n 	,B.index_owner                    ");            
		sb.append("\n 	,C.table_id                       ");            
		sb.append("\n 	,C.column_id                      ");            
		sb.append("\n 	,C.column_name       as column_nm ");            
		sb.append("\n 	,C.pkey                           ");            
		sb.append("\n 	,A.SEQUENCE                       ");            
		sb.append("\n from  SYS.SYSIXCOL A, SYS.SYSINDEX B, SYS.SYSCOLUMN C  ");       
		sb.append("\n where	1	=	1                        ");                
		sb.append("\n and	A.index_id	  = 	B.index_id   ");               
		sb.append("\n and	A.table_id	  =	  B.table_id   ");               
		sb.append("\n and	A.column_id	  = 	c.column_id  ");               
		sb.append("\n and	A.table_id	  =	  c.table_id   ");                         

		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_IQ_SYSINDEX_COLUMNS (    ");           
		sb.append("\n     INDEX_ID,INDEX_NM,CREATOR_ID,INDEX_TYPE,INDEX_OWNER  ");           
		sb.append("\n    ,TABLE_ID ,COLUMN_ID,COLUMN_NM,PKEY,SEQUENCE  ");           
		sb.append("\n    ,DB_CONN_TRG_ID ");    //,DB_SCH_ID
		sb.append("\n ) VALUES (    ");           
		sb.append("\n         TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");                   
		sb.append("\n        ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");                   
		sb.append("\n        ,TRIM(?) ");    //, TRIM(?)              
		sb.append("\n )    "); 

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEX_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_OWNER"));
				
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLUMN_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NM"));
				pst_org.setString(rsGetCnt++, rs.getString("PKEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SEQUENCE"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSFOREIGNKEY() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n select	");
		sb.append("\n 	 FOREIGN_TABLE_ID,FOREIGN_KEY_ID,PRIMARY_TABLE_ID,ROOT,CHECK_ON_COMMIT ");            
		sb.append("\n 	 ,NULLS,ROLE,REMARKS,PRIMARY_INDEX_ID,FK_NOT_ENFORCED    ");            
		sb.append("\n 	 ,HASH_LIMIT             ");            
		sb.append("\n  FROM SYS.SYSFOREIGNKEY  ");       

		getResultSet(sb.toString());
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_IQ_SYSFOREIGNKEY (    ");           
		sb.append("\n      FOREIGN_TABLE_ID,FOREIGN_KEY_ID,PRIMARY_TABLE_ID,ROOT,CHECK_ON_COMMIT ");           
		sb.append("\n     ,NULLS,ROLE,REMARKS,PRIMARY_INDEX_ID,FK_NOT_ENFORCED ");           
		sb.append("\n     ,HASH_LIMIT ");           
		sb.append("\n     ,DB_CONN_TRG_ID ");    //,DB_SCH_ID       
		sb.append("\n ) VALUES (    ");           
		sb.append("\n          TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n         ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
		sb.append("\n         ,TRIM(?) ");
		sb.append("\n         ,TRIM(?) "); //, TRIM(?)
		sb.append("\n )    "); 

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FOREIGN_TABLE_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FOREIGN_KEY_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PRIMARY_TABLE_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROOT"));
				pst_org.setString(rsGetCnt++, rs.getString("CHECK_ON_COMMIT"));
				pst_org.setString(rsGetCnt++, rs.getString("NULLS"));
				pst_org.setString(rsGetCnt++, rs.getString("ROLE"));
				pst_org.setString(rsGetCnt++, rs.getString("REMARKS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PRIMARY_INDEX_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("FK_NOT_ENFORCED"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("HASH_LIMIT"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC SYS FK COLUMN 저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSFKCOL() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n select	");
		sb.append("\n 	 FOREIGN_TABLE_ID,FOREIGN_KEY_ID,FOREIGN_COLUMN_ID,PRIMARY_COLUMN_ID ");            
		sb.append("\n  FROM SYS.SYSFKCOL  ");       

		getResultSet(sb.toString());
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_IQ_SYSFKCOL (    ");           
		sb.append("\n      FOREIGN_TABLE_ID,FOREIGN_KEY_ID,FOREIGN_COLUMN_ID,PRIMARY_COLUMN_ID ");           
		sb.append("\n     ,DB_CONN_TRG_ID ");        //,DB_SCH_ID   
		sb.append("\n ) VALUES (    ");           
		sb.append("\n           TRIM(?), TRIM(?), TRIM(?), TRIM(?)");
		sb.append("\n          ,TRIM(?)"); //, TRIM(?)
		sb.append("\n )    "); 

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FOREIGN_TABLE_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FOREIGN_KEY_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FOREIGN_COLUMN_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PRIMARY_COLUMN_ID"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

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
	 * DBC VIEW 저장
	 * @return cnt
	 * @throws SQLException
	 */
	private int insertSYSVIEW() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n select distinct	");
		sb.append("\n 	 u.user_name as creator_nm ");            
		sb.append("\n 	 ,t.creator as creator_id ");            
		sb.append("\n 	 ,t.table_name as table_nm ");            
		sb.append("\n 	 ,t.table_id ");            
		sb.append("\n 	 ,t.view_def ");            
		sb.append("\n  from sys.sysuserperms u  ");       
		sb.append("\n       ,sys.systable t  ");       
		sb.append("\n where t.creator=u.user_id   "); 
		sb.append("\n   and t.table_type='VIEW'  "); 

		getResultSet(sb.toString());
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_IQ_SYSVIEW (    ");           
		sb.append("\n      CREATOR_NM, CREATOR_ID, TABLE_NM , TABLE_ID, VIEW_DEF ");           
		sb.append("\n     ,DB_CONN_TRG_ID ");   //,DB_SCH_ID     
		sb.append("\n ) VALUES (    ");           
		sb.append("\n       TRIM(?), TRIM(?), TRIM(?), TRIM(?), empty_clob()  ");           
		sb.append("\n      ,TRIM(?)   ");     //, TRIM(?)       
		sb.append("\n )    "); 

		String clobSql ;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;

	    Writer writer = null;
	    Reader src = null;

		String viewDef = "";
		int tableId ;


		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;
			int insResult = 0;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("CREATOR_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("TABLE_ID"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);

				tableId	= rs.getInt("TABLE_ID");
				viewDef = rs.getString("VIEW_DEF");

				insResult = pst_org.executeUpdate();

				// clob 처리 시작
				if (insResult > 0) { // if start
					
					clobSql = " SELECT  VIEW_DEF FROM WAE_IQ_SYSVIEW WHERE TABLE_ID = TRIM(?) FOR UPDATE " ;

					pstmt = con_org.prepareStatement(clobSql);
					pstmt.clearParameters();
					pstmt.setInt(1, tableId);

					resultSet = pstmt.executeQuery();

					try {

						if(resultSet.next()){
							//String viewDef = CLOB에 들어가는 String;
							CLOB clob = (CLOB) resultSet.getObject("VIEW_DEF");
							writer = clob.getCharacterOutputStream();
							src = new CharArrayReader(viewDef.toCharArray());

							char[] buffer = new char[1024];
							int read = 0;

							while ( (read = src.read(buffer,0,1024)) != -1) {
								writer.write(buffer, 0, read); // 여기가 CLOB으로 입력되는 곳입니다.
							}

							src.close();
							writer.close();
						}

					} catch(IOException e) {
						e.printStackTrace();
			      	}

					resultSet.close();
					pstmt.close();
				} 

				cnt++;
				rsGetCnt = 1;
			}
		}

		return cnt;
	}
	
	
	
	/**
	 * DBC PROCEDURE/FUNCTION 저장
	 * @return int
	 * @throws SQLException
	 */
	private int insertSYSPROCEDURE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n select distinct	");
		sb.append("\n 	 u.user_name as creator_nm ");            
		sb.append("\n 	 ,p.creator   as creator_id ");            
		sb.append("\n 	 ,case when (patindex('%procedure%', lcase(p.proc_defn)) < patindex('%'+lcase(p.proc_name)+'%', lcase(p.proc_defn)) and  patindex('%procedure%', lcase(p.proc_defn)) > patindex('%create%', lcase(p.proc_defn)) ) then 'P' else 'F' end proc_type ");            
		sb.append("\n 	 ,p.proc_id ");            
		sb.append("\n 	 ,p.proc_name as proc_nm ");            
		sb.append("\n 	 ,p.proc_defn ");            
		sb.append("\n  from sys.sysuserperms u    ");       
		sb.append("\n       join sys.sysprocedure p  ");       
		sb.append("\n         on (p.creator=u.user_id)   ");       
		sb.append("\n --where u.user_id != 0   "); 

		getResultSet(sb.toString());
		sb.setLength(0);
		
		sb.append("\n INSERT INTO WAE_IQ_SYSPROCEDURE (    ");           
		sb.append("\n     CREATOR_NM, CREATOR_ID, PROC_TYPE, PROC_ID, PROC_NM , PROC_DEFN ");           
		sb.append("\n    ,DB_CONN_TRG_ID ");     //,DB_SCH_ID      
		sb.append("\n ) VALUES (    ");           
		sb.append("\n     TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?), empty_clob()  ");           
		sb.append("\n    ,TRIM(?)    "); //, TRIM(?)          
		sb.append("\n )    "); 
		
		String clobSql ;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;

	    Writer writer = null;
	    Reader src = null;

		String procDefn = "";
		int procId ;

		int j = 0;


		int cnt = 0;
		if (rs != null)
		{
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;
			int insResult = 0;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("CREATOR_NM"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CREATOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("PROC_TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PROC_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("PROC_NM"));
				//pst.setString(rsGetCnt++, rs.getString("REMARKS"));

				pst_org.setString(rsGetCnt++, dbConnTrgId);
//				pst_org.setString(rsGetCnt++, dbSchId);
				
				procId	= rs.getInt("PROC_ID");
				procDefn = rs.getString("PROC_DEFN");

				insResult = pst_org.executeUpdate();
				insResult =

				j--;
				// clob 처리 시작

				if (insResult > 0) { 

					clobSql = " SELECT  PROC_DEFN FROM WAE_IQ_SYSPROCEDURE WHERE PROC_ID = TRIM(?) FOR UPDATE " ;

					pstmt = con_org.prepareStatement(clobSql);
					pstmt.clearParameters();
					pstmt.setInt(1, procId);

					resultSet = pstmt.executeQuery();

					try {

						if(resultSet.next()){
							//String viewDef = CLOB에 들어가는 String;
							CLOB clob = (CLOB) resultSet.getObject("PROC_DEFN");
							writer = clob.getCharacterOutputStream();
							src = new CharArrayReader(procDefn.toCharArray());

							char[] buffer = new char[1024];
							int read = 0;

							while ( (read = src.read(buffer,0,1024)) != -1) {
								writer.write(buffer, 0, read); // 여기가 CLOB으로 입력되는 곳입니다.
							}

							src.close();
							writer.close();
						}

					} catch(IOException e) {
						logger.debug("catch_1" );
			      	}

					resultSet.close();
					pstmt.close();

				}

				cnt++;
				rsCnt++;
				rsGetCnt = 1;
			}
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
		strSQL.append("\n  DELETE FROM WAE_IQ_DBINFO ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_DBINFO : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_DBPROPERTIES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_DBPROPERTIES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_DBSIZE ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_DBSIZE : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_IQSTATUS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_IQSTATUS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSCATALOG ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSCATALOG : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSCOLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSCOLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSFKCOL ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSFKCOL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSFOREIGNKEY ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSFOREIGNKEY : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSGROUP ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSGROUP : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSINDEX_COLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSINDEX_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSINDEXES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSINDEXES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSPROCEDURE ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSPROCEDURE : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSTABLEPERM ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSTABLEPERM : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSTABLES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSTABLES : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSTABLESIZE ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSTABLESIZE : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSUSERPERMS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSUSERPERMS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_IQ_SYSVIEW ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");
//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_IQ_SYSVIEW : " + result);
		

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
		sb.append("\n INSERT INTO WAT_DBC_TBL    ");
		sb.append("\n SELECT S.DB_SCH_ID AS DB_SCH_ID      ");
		sb.append("\n      , A.TABLE_NM  AS DBC_TBL_NM     ");
		sb.append("\n      , A.REMARKS   AS DBC_TBL_KOR_NM ");
		sb.append("\n      , '1'         AS VERS           ");
		sb.append("\n      , NULL        AS REG_TYP        ");
		sb.append("\n      , SYSDATE     AS REG_DTM        ");
		sb.append("\n      , NULL        AS UPD_DTM        ");
		sb.append("\n      , ''          AS DESCN          ");
		sb.append("\n      , S.DB_CONN_TRG_ID  AS DB_CONN_TRG_ID  ");
		sb.append("\n      , A.DBSPACE_NM AS DBC_TBL_SPAC_NM     ");
		sb.append("\n      , '' AS DDL_TBL_ID          ");
		sb.append("\n      , '' AS PDM_TBL_ID          ");
		sb.append("\n      , '"+dbType+"' AS DBMS_TYPE ");
		sb.append("\n      , '' AS SUBJ_ID      ");
		sb.append("\n      , '' AS COL_EACNT    ");
		sb.append("\n      , '' AS ROW_EACNT    ");
		sb.append("\n      , '' AS TBL_SIZE     ");
		sb.append("\n      , '' AS DATA_SIZE    ");
		sb.append("\n      , '' AS IDX_SIZE     ");
		sb.append("\n      , '' AS NUSE_SIZE    ");
		sb.append("\n      , '' AS BF_COL_EACNT ");
		sb.append("\n      , '' AS BF_ROW_EACNT ");
		sb.append("\n      , '' AS BF_TBL_SIZE  ");
		sb.append("\n      , '' AS BF_DATA_SIZE ");
		sb.append("\n      , '' AS BF_IDX_SIZE  ");
		sb.append("\n      , '' AS BF_NUSE_SIZE ");
		sb.append("\n      , '' AS ANA_DTM      ");
		sb.append("\n      , '' AS CRT_DTM      ");
		sb.append("\n      , '' AS CHG_DTM      ");
		sb.append("\n      , '' AS PDM_DESCN    ");
		sb.append("\n      , '' AS TBL_DQ_EXP_YN     ");
		sb.append("\n      , '' AS DDL_TBL_ERR_EXS   ");
		sb.append("\n      , '' AS DDL_TBL_ERR_CD    ");
		sb.append("\n      , '' AS DDL_TBL_ERR_DESCN ");
		sb.append("\n      , '' AS DDL_COL_ERR_EXS   ");
		sb.append("\n      , '' AS DDL_COL_ERR_CD    ");
		sb.append("\n      , '' AS DDL_COL_ERR_DESCN ");
		sb.append("\n      , '' AS PDM_TBL_ERR_EXS   ");
		sb.append("\n      , '' AS PDM_TBL_ERR_CD    ");
		sb.append("\n      , '' AS PDM_TBL_ERR_DESCN ");
		sb.append("\n      , '' AS PDM_COL_ERR_EXS   ");
		sb.append("\n      , '' AS PDM_COL_ERR_CD    ");
		sb.append("\n      , '' AS PDM_COL_ERR_DESCN ");
		sb.append("\n      , '' AS DDL_TBL_EXTNC_EXS ");
		sb.append("\n      , '' AS PDM_TBL_EXTNC_EXS ");
		sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
		sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
		sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
		sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_IQ_SYSTABLES A  ");
		sb.append("\n        INNER JOIN (  ");
		sb.append(getdbconnsql());
		sb.append("\n                   ) S ");
		sb.append("\n     ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
//		sb.append("\n    AND A.DB_SCH_ID = S.DB_SCH_ID      ");
		sb.append("\n    AND UPPER(A.CREATOR_NM) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n  WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID AS DB_SCH_ID    ");
		sb.append("\n      , T.TABLE_NM  AS DBC_TBL_NM   ");
		sb.append("\n      , C.COLUMN_NM AS DBC_COL_NM   ");
		sb.append("\n      , C.REMARKS   AS DBC_COL_KOR_NM  ");
		sb.append("\n      , '1'        AS VERS      ");
		sb.append("\n      , NULL       AS REG_TYP      ");
		sb.append("\n      , SYSDATE    AS REG_DTM      ");
		sb.append("\n      , ''         AS UPD_DTM      ");
		sb.append("\n      , ''         AS DESCN    ");
		sb.append("\n      , ''         AS DDL_COL_ID      ");
		sb.append("\n      , ''         AS PDM_COL_ID      ");
		sb.append("\n      , ''         AS ITM_ID     ");
		sb.append("\n      , C.DOMAIN_NM AS DATA_TYPE       ");
		
		sb.append("\n      , C.WIDTH AS LENGTH  ");
		sb.append("\n      , NULL AS  PREC      ");
		sb.append("\n      , C.SCALE AS SCALE   ");
		sb.append("\n      , C.NULLS AS IS_NULLABLE   ");
		sb.append("\n      , NULL AS DEFLT_LEN        ");
		sb.append("\n      , C.DEFAULT_TXT AS DATA_DEFAULT ");
		sb.append("\n      , C.PKEY AS PK_YN");
		sb.append("\n      , C.COLUMN_ID AS ORD ");
		sb.append("\n      , NULL AS PK_ORD ");
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
		sb.append("\n FROM WAE_IQ_SYSTABLES T  ");
		sb.append("\n      INNER JOIN WAE_IQ_SYSCOLUMNS C  ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID  ");
//		sb.append("\n        AND T.DB_SCH_ID = C.DB_SCH_ID  ");
		sb.append("\n        AND T.TABLE_ID = C.TABLE_ID   ");
		sb.append("\n      INNER JOIN (   ");
		sb.append(getdbconnsql());
		sb.append("\n                  ) S ");
		sb.append("\n     ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
//		sb.append("\n    AND T.DB_SCH_ID = S.DB_SCH_ID      ");
		sb.append("\n    AND UPPER(T.CREATOR_NM) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n  WHERE T.DB_CONN_TRG_ID = '"+dbConnTrgId+"'     ");

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
		sb.append("\n INSERT INTO WAT_DBC_IDX         ");
		sb.append("\n SELECT S.DB_SCH_ID AS DB_SCH_ID  ");
		sb.append("\n      ,T.TABLE_NM  AS DBC_TBL_NM ");
		sb.append("\n      ,I.INDEX_NM AS DBC_IDX_NM  ");
		sb.append("\n      ,S.DB_CONN_TRG_ID AS DB_CONN_TRG_ID  ");
		sb.append("\n      ,NULL AS DBC_IDX_KOR_NM  ");
		sb.append("\n      ,NULL AS VERS            ");
		sb.append("\n      ,NULL AS REG_TYP         ");
		sb.append("\n      ,SYSDATE AS REG_DTM      ");
		sb.append("\n      ,NULL AS REG_USER        ");
		sb.append("\n      ,NULL AS UPD_DTM         ");
		sb.append("\n      ,NULL AS UPD_USER        ");
		sb.append("\n      ,NULL AS DESCN           ");
		sb.append("\n      ,'' AS DBC_TBL_SPAC_NM   ");
		sb.append("\n      ,'' AS DDL_IDX_ID        ");
		sb.append("\n      ,'' AS PDM_IDX_ID        ");
		sb.append("\n      ,'' AS PK_YN             ");
		sb.append("\n      ,'' AS UQ_YN             ");
		
		sb.append("\n      ,NULL AS COL_EACNT       ");
		sb.append("\n      ,NULL AS IDX_SIZE        ");
		sb.append("\n      ,NULL AS BF_IDX_EACNT    ");
		sb.append("\n      ,NULL AS BF_IDX_SIZE     ");
		sb.append("\n      ,NULL AS ANA_DTM         ");
		sb.append("\n      ,NULL AS CRT_DTM         ");
		sb.append("\n      ,NULL AS CHG_DTM         ");
		sb.append("\n      ,NULL AS IDX_DQ_EXP_YN   ");
		sb.append("\n      ,NULL AS SGMT_BYTE_SIZE  ");
		sb.append("\n      ,NULL AS DDL_IDX_CMPS_CONTS     ");
		sb.append("\n      ,NULL AS PDM_IDX_CMPS_CONTS     ");
		sb.append("\n      ,NULL AS DDL_IDX_ERR_EXS        ");
		sb.append("\n      ,NULL AS DDL_IDX_ERR_CD         ");
		sb.append("\n      ,NULL AS DDL_IDX_ERR_DESCN      ");
		sb.append("\n      ,NULL AS DDL_IDX_COL_ERR_EXS    ");
		sb.append("\n      ,NULL AS DDL_IDX_COL_ERR_CD     ");
		sb.append("\n      ,NULL AS DDL_IDX_COL_ERR_DESCN  ");
		sb.append("\n      ,NULL AS PDM_IDX_ERR_EXS        ");
		sb.append("\n      ,NULL AS PDM_IDX_ERR_CD         ");
		sb.append("\n      ,NULL AS PDM_IDX_ERR_DESCN      ");
		sb.append("\n      ,NULL AS PDM_IDX_COL_ERR_EXS    ");
		sb.append("\n      ,NULL AS PDM_IDX_COL_ERR_CD     ");
		sb.append("\n      ,NULL AS PDM_IDX_COL_ERR_DESCN  ");
		sb.append("\n      ,NULL AS DDL_IDX_EXTNC_EXS      ");
		sb.append("\n      ,NULL AS PDM_IDX_EXTNC_EXS      ");
		sb.append("\n  FROM WAE_IQ_SYSTABLES T    ");
		sb.append("\n       INNER JOIN WAE_IQ_SYSINDEXES I   ");
		sb.append("\n          ON T.DB_CONN_TRG_ID = I.DB_CONN_TRG_ID  ");
//		sb.append("\n         AND T.DB_SCH_ID = I.DB_SCH_ID   ");
		sb.append("\n         AND T.TABLE_ID = I.TABLE_ID   ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
//		sb.append("\n    AND T.DB_SCH_ID = S.DB_SCH_ID      ");
		sb.append("\n    AND UPPER(T.CREATOR_NM) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n  WHERE T.DB_CONN_TRG_ID = '"+dbConnTrgId+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL       ");
	    sb.append("\n SELECT DISTINCT S.DB_SCH_ID  AS DB_SCH_ID   ");            
	    sb.append("\n       ,I.TABLE_NM  AS DBC_TBL_NM  ");            
	    sb.append("\n       ,IC.COLUMN_NM  AS DBC_IDX_COL_NM     ");            
	    sb.append("\n       ,I.INDEX_NM  AS DBC_IDX_NM  ");            
	    sb.append("\n       ,NULL AS DBC_IDX_COL_KOR_NM ");            
	    sb.append("\n       ,NULL AS VERS               ");            
	    sb.append("\n       ,NULL AS REG_TYP            ");            
	    sb.append("\n       ,SYSDATE AS REG_DTM         ");            
	    sb.append("\n       ,NULL AS REG_USER           ");            
	    sb.append("\n       ,NULL AS UPD_DTM            ");            
	    sb.append("\n       ,NULL AS UPD_USER           ");            
	    sb.append("\n       ,NULL AS DESCN              ");            
	    sb.append("\n       ,NULL AS DDL_IDX_COL_ID     ");            
	    sb.append("\n       ,NULL AS PDM_IDX_COL_ID     ");            
	    sb.append("\n       ,(IC.SEQUENCE + 1) AS ORD         ");            
	    sb.append("\n       ,NULL AS SORT_TYPE          ");            
	    sb.append("\n       ,NULL AS DATA_TYPE          ");            
	    sb.append("\n       ,NULL AS DATA_PNUM          ");            
	    sb.append("\n       ,NULL AS DATA_LEN           ");            
	    sb.append("\n       ,NULL AS DATA_PNT           ");            
	    sb.append("\n       ,NULL AS IDXCOL_DQ_EXP_YN   ");            
	    sb.append("\n       ,NULL AS DDL_IDX_COL_LNM_ERR_EXS       ");
	    sb.append("\n       ,NULL AS DDL_IDX_COL_ORD_ERR_EXS       ");
	    sb.append("\n       ,NULL AS DDL_IDX_COL_SORT_TYPE_ERR_EXS ");
	    sb.append("\n       ,NULL AS DDL_IDX_COL_EXTNC_EXS         ");
	    sb.append("\n       ,NULL AS DDL_IDX_COL_ERR_EXS           ");
	    sb.append("\n       ,NULL AS DDL_IDX_COL_ERR_CONTS         ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_LNM_ERR_EXS       ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_ORD_ERR_EXS       ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_SORT_TYPE_ERR_EXS ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_EXTNC_EXS         ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_ERR_EXS           ");
	    sb.append("\n       ,NULL AS PDM_IDX_COL_ERR_CONTS         ");
		sb.append("\n   FROM WAE_IQ_SYSINDEXES I          ");
		sb.append("\n        INNER JOIN WAE_IQ_SYSINDEX_COLUMNS IC ");
		sb.append("\n           ON I.INDEX_ID = IC.INDEX_ID ");
		sb.append("\n          AND I.TABLE_ID = IC.TABLE_ID ");
//		sb.append("\n          AND I.DB_CONN_TRG_ID = IC.DB_CONN_TRG_ID ");
//		sb.append("\n          AND I.DB_SCH_ID = IC.DB_SCH_ID ");
		sb.append("\n        INNER JOIN (  ");
		sb.append(getdbconnsql());
		sb.append("\n                    ) S  ");
		sb.append("\n     ON I.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
//		sb.append("\n    AND I.DB_SCH_ID = S.DB_SCH_ID      ");
		sb.append("\n    AND UPPER(I.CREATOR_NM) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n  WHERE I.DB_CONN_TRG_ID = '"+dbConnTrgId+"'     ");

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
