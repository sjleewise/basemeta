/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorDb2Udb.java
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

import kr.wise.commons.DQConstant;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorDb2Udb.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : moonsungeun
 * 6. 작성일   : 2015. 11. 19. 
 * </PRE>
 */
public class CollectorDb2Udb{

	private static final Logger logger = Logger.getLogger(CollectorDb2Udb.class);

	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private TargetDbmsDM targetDbmsDM;
	private String dbConnTrgPnm = null;
	private String dbConnTrgId = null;
	private String dbSchPnm = null;
	private String dbType = null;
	
	private int execCnt = 10000;


	public CollectorDb2Udb() {

	}

	/** insomnia */
	public CollectorDb2Udb(Connection source, Connection target, TargetDbmsDM targetDbmsDM) {
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
		
		//TABLES --------------------------------------------------------
		cnt = insertDBA_TABLES();
		logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");

		//COLUMNS -------------------------------------------------------
		cnt = insertDBA_COLUMNS();
		logger.debug(sp + (++p) + ". insertDBA_COLUMNS " + cnt + " OK!!");

		//INDEXES ---------------------------------------------------
		cnt = insertDBA_INDEXES();
		logger.debug(sp + (++p) + ". insertDBA_INDEXES " + cnt + " OK!!");

		//IDX_COLUMNS ---------------------------------------------------
		cnt = insertDBA_IDX_COLUMNS();
		logger.debug(sp + (++p) + ". insertDBA_IDX_COLUMNS " + cnt + " OK!!");

		//CONSTRAINTS ---------------------------------------------------
		cnt = insertDBA_CONSTRAINTS();
		logger.debug(sp + (++p) + ". insertDBA_CONSTRAINTS " + cnt + " OK!!");

			//CONST_COLUMNS ---------------------------------------------------
//			cnt = insertDBA_CONST_COLUMNS();
//			logger.debug(sp + (++p) + ". insertDBA_CONST_COLUMNS " + cnt + " OK!!");

			//VIEWS ---------------------------------------------------
//			cnt = insertDBA_VIEWS();
//			logger.debug(sp + (++p) + ". insertDBA_VIEWS " + cnt + " OK!!");
			
			//VIEW_PARSE ---------------------------------------------------
//			cnt = insertDBA_VIEW_PARSE();
//			logger.debug(sp + (++p) + ". insertDBA_VIEW_PARSE " + cnt + " OK!!");

			//PROC ---------------------------------------------------
//			cnt = insertDBA_PROC();
//			logger.debug(sp + (++p) + ". insertDBA_PROC " + cnt + " OK!!");
			
			//PROC_PARA ---------------------------------------------------
//			cnt = insertDBA_PROC_PARA();
//			logger.debug(sp + (++p) + ". insertDBA_PROC_PARA " + cnt + " OK!!");
			
			//PROC_PARSE ---------------------------------------------------
//			cnt = insertDBA_PROC_PARSE();
//			logger.debug(sp + (++p) + ". insertDBA_PROC_PARSE " + cnt + " OK!!");

//		con_org.commit();
		result = true;
		return result;

	}

	/** @return insomnia
	 * @throws Exception */
	private int insertDBA_VIEW_PARSE() throws Exception {
		StringBuffer sb = new StringBuffer();

  		sb.append("\nSELECT A.SYSTEM_TABLE_SCHEMA AS USER_NAME             ");
  		sb.append("\n      ,A.TABLE_NAME AS VIEW_NAME                      ");
  		sb.append("\n      ,B.VIEW_DEFINITION AS PARSE                     ");
  		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM ");
  		sb.append("\n      ,'").append(dbSchPnm).append("'' AS SCH_NM   ");
  		sb.append("\nFROM   QSYS2.SYSTABLES A                              ");
  		sb.append("\n   INNER JOIN QSYS2.SYSVIEWS B                        ");
  		sb.append("\n      ON A.TABLE_NAME = B.TABLE_NAME                  ");
  		sb.append("\n     AND A.SYSTEM_TABLE_SCHEMA = B.SYSTEM_VIEW_SCHEMA ");
  		sb.append("\nWHERE A.SYSTEM_TABLE_SCHEMA IN ('").append(dbSchPnm).append("')");
  		sb.append("\n   AND A.TABLE_TYPE = 'V'                             ");
  		sb.append("\n   AND A.SYSTEM_TABLE = 'N'                           ");

		getResultSet(sb.toString());

		sb.setLength(0);

  		sb.append("\n INSERT INTO WAE_DB2_VIEW_PARSE ");
  		sb.append("\n ( USER_NAME   ");
  		sb.append("\n , VIEW_NAME   ");
  		sb.append("\n , PARSE       ");
  		sb.append("\n , DB_NM   ");
  		sb.append("\n , SCH_NM ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n RTRIM(?),RTRIM(?),?,RTRIM(?),RTRIM(?)  ");
  		sb.append("\n ) ");


		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_NAME"));
  				if(rs.getString("PARSE").getBytes().length > 3000) {
  					// 4000 ����Ʈ�� ����
  					byte [] parseArr = rs.getString("PARSE").getBytes();
  					byte[] parseStr = new byte[3000];
  					System.arraycopy( parseArr , 0 , parseStr , 0 , 2999 ) ; // ����Ʈ ī��
  					pst_org.setString(rsGetCnt++, new String(parseStr) );
  				} else {
  					pst_org.setString(rsGetCnt++, rs.getString("PARSE"));
  				}
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));

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
	 * DBC iPROC 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_PROC() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\nSELECT  DISTINCT " );
  		sb.append("\n        A.ROUTINESCHEMA 	AS USER_NAME          ");
  		sb.append("\n       ,A.SPECIFICNAME 	AS PROC_NAME          ");
  		sb.append("\n       ,A.ROUTINETYPE      AS ROUTINE_TYPE       ");
  		sb.append("\n       ,A.CREATE_TIME		AS ROUTINE_CREATED ");
  		sb.append("\n       ,B.TYPENAME AS DATA_TYPE                             ");
  		sb.append("\n       ,B.SCALE 	AS NUMERIC_SCALE                         ");
  		sb.append("\n       ,B.LENGTH 	AS NUMERIC_PRECISION                     ");
  		sb.append("\n       ,0 AS CCSID                                 ");
  		sb.append("\n       ,0 AS CHARACTER_MAXIMUM_LENGTH              ");
  		sb.append("\n       ,0 AS CHARACTER_OCTET_LENGTH                ");
  		sb.append("\n      , 0 AS IN_PARMS                              ");
  		sb.append("\n      , 0 AS OUT_PARMS                             ");
  		sb.append("\n      , 0 AS INOUT_PARMS                             ");
//  		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM");
//  		sb.append("\n      ,'").append(dbSchPnm).append("' AS SCH_NM");
  		sb.append("\nFROM SYSCAT.ROUTINES A                        ");
  		sb.append("\n   LEFT OUTER JOIN SYSCAT.ROUTINEPARMS B             ");
  		sb.append("\n      ON A.ROUTINESCHEMA  = B.ROUTINESCHEMA ");
  		sb.append("\n     AND A.ROUTINENAME 	= B.ROUTINENAME      ");
  		sb.append("\n     AND A.SPECIFICNAME = B.SPECIFICNAME      ");
  		//sb.append("\n     AND B.ROWTYPE 	 = 'O'               ");
  		//sb.append("\n     AND B.ORDINAL_POSITION =  '1'              ");
//  		sb.append("\nWHERE A.ROUTINESCHEMA IN ('").append(dbSchPnm).append("')");

		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_PROC ");
  		sb.append("\n ( USER_NAME      ");
  		sb.append("\n , PROC_NAME      ");
  		sb.append("\n , ROUTINE_TYPE    ");
  		sb.append("\n , ORGL_DTTM      ");
  		sb.append("\n , DATA_TYPE       ");
  		sb.append("\n , NUMERIC_SCALE       ");
  		sb.append("\n , NUMERIC_PRECISION     ");
  		sb.append("\n , CCSID     ");
  		sb.append("\n , CHARACTER_MAXIMUM_LENGTH     ");
  		sb.append("\n , CHARACTER_OCTET_LENGTH    ");
  		sb.append("\n , IN_PARMS    ");
  		sb.append("\n , OUT_PARMS   ");
  		sb.append("\n , INOUT_PARMS   ");
  		sb.append("\n , DB_NM    ");
  		sb.append("\n , SCH_NM    ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");  
  		sb.append("\n ) VALUES (       ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),? ,RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),    ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?)                 ");
  		sb.append("\n ) ");
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("ROUTINE_TYPE"));
  				pst_org.setDate(rsGetCnt++, rs.getDate("ROUTINE_CREATED"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_SCALE"));
  				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION"));
  				pst_org.setString(rsGetCnt++, rs.getString("CCSID"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_MAXIMUM_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_OCTET_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("IN_PARMS"));
  				pst_org.setString(rsGetCnt++, rs.getString("OUT_PARMS"));
  				pst_org.setString(rsGetCnt++, rs.getString("INOUT_PARMS"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC PROC_PARA 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_PROC_PARA() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\nSELECT  SPECIFIC_SCHEMA AS USER_NAME   ");
  		sb.append("\n       ,SPECIFIC_NAME AS PROC_NAME     ");
  		sb.append("\n       ,ORDINAL_POSITION AS PARA_ORDER ");
  		sb.append("\n       ,PARAMETER_MODE                 ");
  		sb.append("\n       ,PARAMETER_NAME                 ");
  		sb.append("\n       ,DATA_TYPE                      ");
  		sb.append("\n       ,NUMERIC_SCALE                  ");
  		sb.append("\n       ,NUMERIC_PRECISION              ");
  		sb.append("\n       ,CCSID                          ");
  		sb.append("\n       ,CHARACTER_MAXIMUM_LENGTH       ");
  		sb.append("\n       ,CHARACTER_OCTET_LENGTH         ");
  		sb.append("\n       ,'").append(dbConnTrgPnm).append("' AS DB_NM");
  		sb.append("\n       ,'").append(dbSchPnm).append("' AS SCH_NM");
  		sb.append("\nFROM QSYS2.SYSPARMS                    ");
  		sb.append("\nWHERE SPECIFIC_SCHEMA IN ('").append(dbSchPnm).append("')");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

 		sb.append("\n INSERT INTO WAE_DB2_PROC_PARA ");
  		sb.append("\n ( USER_NAME          ");
  		sb.append("\n , PROC_NAME          ");
  		sb.append("\n , PARA_ORDER         ");
  		sb.append("\n , PARAMETER_MODE         ");
  		sb.append("\n , PARAMETER_NAME         ");
  		sb.append("\n , DATA_TYPE         ");
  		sb.append("\n , NUMERIC_SCALE        ");
  		sb.append("\n , NUMERIC_PRECISION        ");
  		sb.append("\n , CCSID        ");
  		sb.append("\n , CHARACTER_MAXIMUM_LENGTH        ");
  		sb.append("\n , CHARACTER_OCTET_LENGTH        ");
  		sb.append("\n , DB_NM          ");
  		sb.append("\n , SCH_NM        ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),            ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARA_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARAMETER_MODE"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARAMETER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_SCALE"));
  				pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION"));
  				pst_org.setString(rsGetCnt++, rs.getString("CCSID"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_MAXIMUM_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_OCTET_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				
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
	 * DBC PROC_PARSE 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_PROC_PARSE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\n SELECT  SPECIFIC_SCHEMA AS USER_NAME                   ");
  		sb.append("\n      ,SPECIFIC_NAME AS PROC_NAME                   ");
  		sb.append("\n      ,ROUTINE_DEFINITION AS PARSE                     ");
  		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM");
  		sb.append("\n      ,'").append(dbSchPnm).append("' AS SCH_NM");
  		sb.append("\n FROM QSYS2.SYSROUTINES A            ");
  		sb.append("\n WHERE SPECIFIC_SCHEMA IN ('").append(dbSchPnm).append("')");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_PROC_PARSE ");
  		sb.append("\n ( USER_NAME     ");
  		sb.append("\n , PROC_NAME     ");
  		sb.append("\n , PARSE         ");
  		sb.append("\n , DB_NM     ");
  		sb.append("\n , SCH_NM   ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n RTRIM(?),RTRIM(?),?,RTRIM(?),RTRIM(?)     ");
  		sb.append("\n ) ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("PROC_NAME"));
  				if(rs.getString("PARSE").getBytes().length > 3000) {
  					// 4000 ����Ʈ�� ����
  					byte [] parseArr = rs.getString("PARSE").getBytes();
  					byte[] parseStr = new byte[3000];
  					System.arraycopy( parseArr , 0 , parseStr , 0 , 2999 ) ; // ����Ʈ ī��
  					pst_org.setString(rsGetCnt++, new String(parseStr) );
  				} else {
  					pst_org.setString(rsGetCnt++, rs.getString("PARSE"));
  				}
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
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
	 * DBC VIEWS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_VIEWS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\n SELECT VIEWSCHEMA AS USER_NAME              ");
  		sb.append("\n      , VIEWNAME 	AS VIEW_NAME                       ");
  		sb.append("\n      , '' 		AS VIEW_TEXT                       ");
//  		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM ");
//  		sb.append("\n      ,'").append(dbSchPnm).append("' AS SCH_NM   ");
  		sb.append("\n FROM  SYSCAT.VIEWS                                ");
  		sb.append("\n WHERE 1 = 1                               ");
//  		sb.append("\n   AND VIEWSCHEMA IN ('").append(dbSchPnm).append("')");

		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_VIEWS ");
  		sb.append("\n ( USER_NAME   ");
  		sb.append("\n , VIEW_NAME   ");
  		sb.append("\n , VIEW_TEXT   ");
  		sb.append("\n , DB_NM   ");
  		sb.append("\n , SCH_NM ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?)  ");
  		sb.append("\n ) ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("VIEW_TEXT"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC CONST_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_CONST_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\nSELECT  A.SYSTEM_TABLE_SCHEMA AS USER_NAME            ");
  		sb.append("\n       ,A.SYSTEM_TABLE_NAME                           ");
  		sb.append("\n       ,A.CONSTRAINT_NAME                             ");
  		sb.append("\n       ,B.COLUMN_NAME  AS CONST_COL_NAME              ");
  		sb.append("\n       ,B.ORDINAL_POSITION AS CONST_COL_ORDER         ");
  		sb.append("\n       ,'").append(dbConnTrgPnm).append("' AS DB_NM");
  		sb.append("\n       ,'").append(dbSchPnm).append("' AS SCH_NM  ");
  		sb.append("\nFROM    QSYS2.SYSCST A                                ");
  		sb.append("\n   INNER JOIN QSYS2.SYSKEYCST B                       ");
  		sb.append("\n      ON A.CONSTRAINT_NAME = B.CONSTRAINT_NAME        ");
  		sb.append("\n     AND A.CONSTRAINT_SCHEMA = B.CONSTRAINT_SCHEMA    ");
  		sb.append("\n   INNER JOIN QSYS2.SYSTABLES C                       ");
  		sb.append("\n      ON A.SYSTEM_TABLE_SCHEMA = C.SYSTEM_TABLE_SCHEMA");
  		sb.append("\n     AND A.SYSTEM_TABLE_NAME = C.SYSTEM_TABLE_NAME    ");
  		sb.append("\n     AND C.TABLE_TYPE = 'T'                           ");
  		sb.append("\n     AND C.SYSTEM_TABLE = 'N'                         ");
  		sb.append("\nWHERE A.CONSTRAINT_STATE = 'ESTABLISHED'              ");
  		sb.append("\n  AND A.SYSTEM_TABLE_SCHEMA IN ('").append(dbSchPnm).append("')");
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_CONST_COLUMNS ");
  		sb.append("\n ( USER_NAME       ");
  		sb.append("\n , SYSTEM_TABLE_NAME");
  		sb.append("\n , CONSTRAINT_NAME ");
  		sb.append("\n , CONST_COL_NAME  ");
  		sb.append("\n , CONST_COL_ORDER ");
  		sb.append("\n , DB_NM       ");
  		sb.append("\n , SCH_NM     ");
  		sb.append("\n ) VALUES ( ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("SYSTEM_TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONST_COL_ORDER"));
  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));

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

  		sb.append("\nSELECT  A.TABSCHEMA AS USER_NAME             ");
  		sb.append("\n       ,A.TABNAME                            ");
  		sb.append("\n       ,A.CONSTNAME AS CONSTRAINT_NAME                              ");
  		sb.append("\n       ,A.TYPE 	 AS CONSTRAINT_TYPE                              ");
  		sb.append("\n       ,0 AS CONSTRAINT_KEYS                              ");
  		sb.append("\n       ,0 AS IASP_NUMBER                                  ");
  		sb.append("\n       ,'' AS CHECK_CLAUSE                                 ");
  		sb.append("\n       ,'' AS REF_CONSTRAINT_NAME");
//  		sb.append("\n       ,'").append(dbConnTrgPnm).append("' AS DB_NM ");
//  		sb.append("\n       ,'").append(dbSchPnm).append("' AS SCH_NM   ");
  		sb.append("\nFROM    SYSCAT.TABCONST A                                 ");
//  		sb.append("\n  WHERE A.TABSCHEMA IN ('").append(dbSchPnm).append("')");

		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_CONSTRAINTS ");
  		sb.append("\n ( USER_NAME           ");
  		sb.append("\n , SYSTEM_TABLE_NAME   ");
  		sb.append("\n , CONSTRAINT_NAME     ");
  		sb.append("\n , CONSTRAINT_TYPE     ");
  		sb.append("\n , CONSTRAINT_KEYS     ");
  		sb.append("\n , IASP_NUMBER         ");
  		sb.append("\n , CHECK_CLAUSE        ");
  		sb.append("\n , REF_CONSTRAINT_NAME ");
  		sb.append("\n , DB_NM           ");
  		sb.append("\n , SCH_NM         ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");
  		sb.append("\n ) VALUES (            ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");
	

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("CONSTRAINT_KEYS"));
  				pst_org.setString(rsGetCnt++, rs.getString("IASP_NUMBER"));
  				pst_org.setString(rsGetCnt++, rs.getString("CHECK_CLAUSE"));
  				pst_org.setString(rsGetCnt++, rs.getString("REF_CONSTRAINT_NAME"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC IDX_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_IDX_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

  		sb.append("\nSELECT  A.INDSCHEMA AS USER_NAME                                 ");
  		sb.append("\n       ,B.TABNAME   AS TABLE_NAME                                             ");
  		sb.append("\n       ,A.INDNAME   AS INDEX_NAME                                      ");
  		sb.append("\n       ,A.COLNAME   AS COLUMN_NAME                                     ");
  		sb.append("\n       ,'' AS COLUMN_TEXT                                                  ");
  		sb.append("\n       ,A.COLSEQ AS ORDINAL_POSITION                                       ");
  		sb.append("\n       ,CASE WHEN A.COLORDER = 'A' THEN  'ASC' ELSE 'DESC' END AS ORDERING ");
//  		sb.append("\n       ,'").append(dbConnTrgPnm).append("' AS DB_NM                     ");
//  		sb.append("\n       ,'").append(dbSchPnm).append("' AS SCH_NM                       ");
  		sb.append("\nFROM    SYSCAT.INDEXCOLUSE A                                               ");
  		sb.append("\n  INNER JOIN    SYSCAT.INDEXES B                                           ");
  		sb.append("\n  ON 	A.INDSCHEMA = B.INDSCHEMA                                           ");
  		sb.append("\n  AND  A.INDNAME 	= B.INDNAME                                             ");
//  		sb.append("\nWHERE  A.INDSCHEMA IN ('").append(dbSchPnm).append("')");

		getResultSetLast(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_IDX_COLUMNS ");
  		sb.append("\n ( USER_NAME                     ");
  		sb.append("\n  ,SYSTEM_TABLE_NAME             ");
  		sb.append("\n  ,INDEX_NAME                    ");
  		sb.append("\n  ,COLUMN_NAME                   ");
  		sb.append("\n  ,COLUMN_TEXT                   ");
  		sb.append("\n  ,ORDINAL_POSITION              ");
  		sb.append("\n  ,ORDERING                      ");
  		sb.append("\n  ,DB_NM         ");
  		sb.append("\n  ,SCH_NM        ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");  
  		sb.append("\n ) VALUES (        ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");

		int cnt = 0;
		int j = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_TEXT"));
  				pst_org.setString(rsGetCnt++, rs.getString("ORDINAL_POSITION"));
  				pst_org.setString(rsGetCnt++, rs.getString("ORDERING"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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

  		sb.append("\nSELECT A.INDSCHEMA AS USER_NAME                             ");
  		sb.append("\n      ,A.TABNAME                                            ");
  		sb.append("\n      ,A.INDNAME 	AS INDEX_NAME                                                    ");
  		sb.append("\n      ,A.COLCOUNT  AS COLUMN_COUNT                                                 ");
  		sb.append("\n      ,CASE WHEN A.UNIQUERULE != 'D' THEN 'Y' ELSE 'N' END AS IS_UNIQUE ");
  		sb.append("\n      ,'' AS LONG_COMMENT                                                 ");
  		sb.append("\n      ,0 AS IASP_NUMBER                                                  ");
  		sb.append("\n      ,'' AS INDEX_TEXT                                                   ");
//  		sb.append("\n      ,'").append(dbConnTrgPnm).append("' AS DB_NM                 ");
//  		sb.append("\n      ,'").append(dbSchPnm).append("' AS SCH_NM                   ");
  		sb.append("\nFROM   SYSCAT.INDEXES A                                             ");
  		sb.append("\n   INNER JOIN SYSCAT.TABLES B                                       ");
  		sb.append("\n      ON A.INDSCHEMA = B.TABSCHEMA                ");
  		sb.append("\n     AND A.TABNAME = B.TABNAME                    ");
  		sb.append("\n     AND B.TYPE = 'T'                                           ");
  		//sb.append("\n     AND B.SYSTEM_TABLE = 'N'                                         ");
//  		sb.append("\n WHERE A.TABSCHEMA IN ('").append(dbSchPnm).append("')     ");
  		//sb.append("\nORDER BY USER_NAME, A.TABNAME, A.INDEX_NAME                 ");
  		
		
		getResultSet(sb.toString());
		sb = new StringBuffer();

  		sb.append("\n INSERT INTO WAE_DB2_INDEXES ");
  		sb.append("\n ( USER_NAME                 ");
  		sb.append("\n  ,SYSTEM_TABLE_NAME         ");
  		sb.append("\n  ,INDEX_NAME                ");
  		sb.append("\n  ,COLUMN_COUNT              ");
  		sb.append("\n  ,IS_UNIQUE                 ");
  		sb.append("\n  ,LONG_COMMENT              ");
  		sb.append("\n  ,IASP_NUMBER               ");
  		sb.append("\n  ,INDEX_TEXT                ");
  		sb.append("\n  ,DB_NM              ");
  		sb.append("\n  ,SCH_NM            ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");  
  		sb.append("\n ) VALUES (    ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?)            ");
  		sb.append("\n ) ");
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_COUNT"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_UNIQUE"));
  				pst_org.setString(rsGetCnt++, rs.getString("LONG_COMMENT"));
  				pst_org.setString(rsGetCnt++, rs.getString("IASP_NUMBER"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TEXT"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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
		
  		sb.append("\nSELECT  A.TABSCHEMA AS USER_NAME                                   ");
  		sb.append("\n       ,A.TABNAME                                                  ");
  		sb.append("\n       ,A.COLNAME                                                          ");
  		sb.append("\n       ,( A.COLNO + 1 ) 	AS COL_POSITION                                     ");
  		sb.append("\n       ,A.TYPENAME AS DATA_TYPE                                            ");
  		sb.append("\n       ,A.LENGTH   AS DATA_LENGTH                                          ");
  		sb.append("\n       ,A.SCALE	AS DATA_SCALE                                           ");
  		sb.append("\n       ,A.NULLS    AS IS_NULLABLE                                          ");
  		sb.append("\n       ,CASE WHEN A.KEYSEQ IS NOT NULL THEN 'Y' ELSE 'N' END AS IS_PK       ");
  		sb.append("\n       ,'N' AS IS_FK                                                       ");
  		sb.append("\n       ,''  AS IS_UPDATABLE                                                ");
  		sb.append("\n       ,A.REMARKS AS LONG_COMMENT                                          ");
  		sb.append("\n       ,'' AS HAS_DEFAULT                                                  ");
  		sb.append("\n       ,A.DEFAULT AS COLUMN_DEFAULT                                        ");
  		sb.append("\n       ,0 AS STORAGE                                                            ");
  		sb.append("\n       ,0 AS NUMERIC_PRECISION                                                  ");
  		sb.append("\n       ,0 AS CCSID                                                              ");
  		sb.append("\n       ,0 AS CHARACTER_MAXIMUM_LENGTH                                           ");
  		sb.append("\n       ,0 AS CHARACTER_OCTET_LENGTH                                             ");
  		sb.append("\n       ,0 AS NUMERIC_PRECISION_RADIX                                            ");
  		sb.append("\n       ,0 AS DATETIME_PRECISION                                                 ");
  		sb.append("\n       ,A.IDENTITY AS IS_IDENTITY ");
  		sb.append("\n       ,A.REMARKS AS COLUMN_TEXT                                                      ");
  		sb.append("\nFROM    SYSCAT.COLUMNS A                                                   ");
  		sb.append("\n   INNER JOIN SYSCAT.TABLES B                                              ");
  		sb.append("\n      ON A.TABSCHEMA = B.TABSCHEMA                     ");
  		sb.append("\n     AND A.TABNAME = B.TABNAME                         ");
  		sb.append("\n     AND B.TYPE = 'T'                                                ");
  		//sb.append("\n     AND B.SYSTEM_TABLE = 'N'                                        ");
//  		sb.append("\nWHERE A.TABSCHEMA IN ('").append(dbSchPnm).append("')               ");
  		//sb.append("\nORDER BY USER_NAME, A.TABNAME, A.ORDINAL_POSITION                    ");


		getResultSet(sb.toString());
		sb = new StringBuffer();
		
  		sb.append("\n INSERT INTO WAE_DB2_COLUMNS ");
  		sb.append("\n ( USER_NAME                 ");
  		sb.append("\n  ,SYSTEM_TABLE_NAME         ");
  		sb.append("\n  ,COLUMN_NAME               ");
  		sb.append("\n  ,ORDINAL_POSITION          ");
  		sb.append("\n  ,DATA_TYPE                 ");
  		sb.append("\n  ,LENGTH                    ");
  		sb.append("\n  ,NUMERIC_SCALE             ");
  		sb.append("\n  ,IS_NULLABLE               ");
  		sb.append("\n  ,IS_PK                     ");
  		sb.append("\n  ,IS_FK                     ");
  		
  		sb.append("\n  ,IS_UPDATABLE              ");
  		sb.append("\n  ,LONG_COMMENT              ");
  		sb.append("\n  ,HAS_DEFAULT               ");
  		sb.append("\n  ,COLUMN_DEFAULT            ");
  		sb.append("\n  ,STORAGE                   ");
  		sb.append("\n  ,NUMERIC_PRECISION         ");
  		sb.append("\n  ,CCSID                     ");
  		sb.append("\n  ,CHARACTER_MAXIMUM_LENGTH  ");
  		sb.append("\n  ,CHARACTER_OCTET_LENGTH    ");
  		sb.append("\n  ,NUMERIC_PRECISION_RADIX   ");
  		
  		sb.append("\n  ,DATETIME_PRECISION        ");
  		sb.append("\n  ,IS_IDENTITY               ");
  		sb.append("\n  ,COLUMN_TEXT               ");
  		sb.append("\n  ,DB_NM                 ");
  		sb.append("\n  ,SCH_NM               ");
  		sb.append("\n , DB_CONN_TRG_ID  ");
  		sb.append("\n , DB_SCH_ID       ");  
  		sb.append("\n ) VALUES (                  ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),        ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),        ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");
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
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("TABNAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLNAME"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COL_POSITION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATA_LENGTH"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATA_SCALE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_PK"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_FK"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_UPDATABLE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("LONG_COMMENT"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("HAS_DEFAULT"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLUMN_DEFAULT"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("STORAGE"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CCSID"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_MAXIMUM_LENGTH"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_OCTET_LENGTH"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("NUMERIC_PRECISION_RADIX"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("DATETIME_PRECISION"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("IS_IDENTITY"));
  		  		pst_org.setString(rsGetCnt++, rs.getString("COLUMN_TEXT"));
//  		  		pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  		  		pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
				pst_org.setString(rsGetCnt++, null);
				pst_org.setString(rsGetCnt++, null);
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
	 * DBC TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
  		sb.append("\nSELECT  TABSCHEMA AS USER_NAME 			");
  		sb.append("\n		,TABNAME           					");
  		sb.append("\n		,REMARKS 		AS TABLE_TEXT                  	");
  		sb.append("\n		,TYPE       AS TABLE_TYPE      				");
  		sb.append("\n		,COLCOUNT 	AS COLUMN_COUNT                ");
  		sb.append("\n		,AVGROWSIZE AS ROW_LENGTH                  ");
  		sb.append("\n		,REMARKS 	AS LONG_COMMENT                ");
  		sb.append("\n		,'' 		AS FILE_TYPE                   ");
  		sb.append("\n		,'' AS IS_INSERTABLE_INTO             	   ");
  		sb.append("\n		,0 AS IASP_NUMBER                 		   ");
  		sb.append("\n		,PARTITION_MODE 			AS PARTITION_TABLE             "); //H, R OR NULL
  		sb.append("\n		,INVALIDATE_TIME AS LAST_ALTERED_TIME ");
  		sb.append("\n		,KEYUNIQUE 			             	");
  		sb.append("\n		,CREATE_TIME 			             	");
  		sb.append("\n		,LAST_REGEN_TIME 			             ");
  		sb.append("\n		,TBSPACE 			             ");
  		sb.append("\n		,INDEX_TBSPACE 			             ");
  		sb.append("\n		,LONG_TBSPACE 			             ");
//  		sb.append("\n       ,'").append(dbConnTrgPnm).append("' AS DB_NM");
//  		sb.append("\n       ,'").append(dbSchPnm).append("' AS SCH_NM");
  		sb.append("\n  FROM SYSCAT.TABLES          ");
//  		sb.append("\n WHERE TABSCHEMA IN ('").append(dbSchPnm).append("')");
  		sb.append("\n WHERE TYPE = 'T'         ");
  		sb.append("\n   AND STATUS = 'N'       ");
  		
		
		getResultSet(sb.toString());
		sb.setLength(0);

  		sb.append("\n INSERT INTO WAE_DB2_TABLES ");
  		sb.append("\n ( USER_NAME       ");
  		sb.append("\n , SYSTEM_TABLE_NAME         ");
  		sb.append("\n , TABLE_TEXT                ");
  		sb.append("\n , TABLE_TYPE                ");
  		sb.append("\n , COLUMN_COUNT              ");
  		sb.append("\n , ROW_LENGTH                ");
  		sb.append("\n , LONG_COMMENT              ");
  		sb.append("\n , FILE_TYPE                 ");
  		sb.append("\n , IS_INSERTABLE_INTO        ");
  		sb.append("\n , IASP_NUMBER               ");
  		
  		sb.append("\n , PARTITION_TABLE           ");
  		sb.append("\n , LAST_ALTERED_TIME         ");
  		sb.append("\n , KEYUNIQUE         ");
  		sb.append("\n , CREATE_TIME         ");
  		sb.append("\n , LAST_REGEN_TIME         ");
  		
  		sb.append("\n , TBSPACE         ");
  		sb.append("\n , INDEX_TBSPACE         ");
  		sb.append("\n , LONG_TBSPACE         ");
  		sb.append("\n , DB_NM                 ");
  		sb.append("\n , SCH_NM               ");
  		sb.append("\n , DB_CONN_TRG_ID               ");
  		sb.append("\n , DB_SCH_ID               ");
  		
  		sb.append("\n ) VALUES (                  ");
  		sb.append("\n RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ,RTRIM(?),?,RTRIM(?),?,? ");
  		sb.append("\n ,RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?),RTRIM(?) ");
  		sb.append("\n ) ");

		int cnt = 0;
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next())
			{
				
  				pst_org.setString(rsGetCnt++, rs.getString("USER_NAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TEXT"));
  				pst_org.setString(rsGetCnt++, rs.getString("TABLE_TYPE"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("COLUMN_COUNT"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ROW_LENGTH"));
  				pst_org.setString(rsGetCnt++, rs.getString("LONG_COMMENT"));
  				pst_org.setString(rsGetCnt++, rs.getString("FILE_TYPE"));
  				pst_org.setString(rsGetCnt++, rs.getString("IS_INSERTABLE_INTO"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("IASP_NUMBER"));
  				pst_org.setString(rsGetCnt++, rs.getString("PARTITION_TABLE"));
  				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_ALTERED_TIME"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("KEYUNIQUE"));
  				pst_org.setDate(rsGetCnt++, rs.getDate("CREATE_TIME"));
  				pst_org.setDate(rsGetCnt++, rs.getDate("LAST_REGEN_TIME"));
  				pst_org.setString(rsGetCnt++, rs.getString("TBSPACE"));
  				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TBSPACE"));
  				pst_org.setString(rsGetCnt++, rs.getString("LONG_TBSPACE"));
//  				pst_org.setString(rsGetCnt++, rs.getString("DB_NM"));
//  				pst_org.setString(rsGetCnt++, rs.getString("SCH_NM"));
  				pst_org.setString(rsGetCnt++, null);
  				pst_org.setString(rsGetCnt++, null);
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
		sb.append("\nINSERT INTO WAE_DB2_DATABASES (");
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
		sb.append("\n    AND A.DBMS_TYP_CD    = '").append(dbType).append("'") ;
		sb.append("\n    AND UPPER(A.DB_CONN_TRG_PNM)   =    UPPER('").append(dbConnTrgPnm).append("')") ;

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
		strSQL.append("\n  DELETE FROM WAE_DB2_TABLES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_TABLES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_COLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_INDEXES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_INDEXES : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_IDX_COLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_IDX_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_CONSTRAINTS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_CONSTRAINTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_CONST_COLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_CONST_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_VIEWS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_VIEWS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_VIEW_PARSE ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_VIEW_PARSE : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_PROC ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_PROC : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_PROC_PARA ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_PROC_PARA : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_DB2_PROC_PARSE ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTrgId+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_DB2_PROC_PARSE : " + result);

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
	    this.pst_org = this.con_org.prepareStatement(query_tgt);
	    int cnt = this.pst_org.executeUpdate();
	    this.pst_org.close();
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

	    if (execYN)
	    {
	      this.pst_org.clearBatch();
	    }
	    else
	    {
	      this.rs.close();
	      this.pst_org.close();
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
		sb.append("\n INSERT INTO WAT_DBC_TBL   ("
				+ "DB_SCH_ID                \n" + 
				", DBC_TBL_NM               \n" + 
				", DBC_TBL_KOR_NM           \n" + 
				", VERS                     \n" + 
				", REG_TYP                  \n" + 
				", REG_DTM                  \n" + 
				", UPD_DTM                  \n" + 
				", DESCN                    \n" + 
				", DB_CONN_TRG_ID           \n" + 
				", DBC_TBL_SPAC_NM          \n" + 
				", DDL_TBL_ID               \n" + 
				", PDM_TBL_ID               \n" + 
				", DBMS_TYPE                \n" + 
				", SUBJ_ID                  \n" + 
				", COL_EACNT                \n" + 
				", ROW_EACNT                \n" + 
				", TBL_SIZE                 \n" + 
				", DATA_SIZE                \n" + 
				", IDX_SIZE                 \n" + 
				", NUSE_SIZE                \n" + 
				", BF_COL_EACNT             \n" + 
				", BF_ROW_EACNT             \n" + 
				", BF_TBL_SIZE              \n" + 
				", BF_DATA_SIZE             \n" + 
				", BF_IDX_SIZE              \n" + 
				", BF_NUSE_SIZE             \n" + 
				", ANA_DTM                  \n" + 
				", CRT_DTM                  \n" + 
				", CHG_DTM                  \n" + 
				", PDM_DESCN                \n" + 
				", TBL_DQ_EXP_YN            \n" + 
				", DDL_TBL_ERR_EXS          \n" + 
				", DDL_TBL_ERR_CD           \n" + 
				", DDL_TBL_ERR_DESCN        \n" + 
				", DDL_COL_ERR_EXS          \n" + 
				", DDL_COL_ERR_CD           \n" + 
				", DDL_COL_ERR_DESCN        \n" + 
				", PDM_TBL_ERR_EXS          \n" + 
				", PDM_TBL_ERR_CD           \n" + 
				", PDM_TBL_ERR_DESCN        \n" + 
				", PDM_COL_ERR_EXS          \n" + 
				", PDM_COL_ERR_CD           \n" + 
				", PDM_COL_ERR_DESCN        \n" + 
				", DDL_TBL_EXTNC_EXS        \n" + 
				", PDM_TBL_EXTNC_EXS        \n" + 
				 ")                                         ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                           ");
		sb.append("\n      , A.SYSTEM_TABLE_NAME   AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TABLE_TEXT          AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , 1                     AS VERS                                  ");
		sb.append("\n      , to_char(null)         AS REG_TYP                               ");
		if(DQConstant.REP_DB_TYPE.equals("DB2")){
		sb.append("\n      , CURRENT_TIMESTAMP     AS REG_DTM                               ");
		}else{
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		}
		sb.append("\n      , to_date(null)         AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''         AS DBC_TBL_SPAC_NM                       ");
//		sb.append("\n      , A.TBL_SPAC_NM         AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                            ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                            ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS SUBJ_ID                               ");
//		sb.append("\n      , C.BYTES               AS TBL_SIZE                              ");
//		sb.append("\n      , ''                    AS DATA_SIZE                             ");
//		sb.append("\n      , D.BYTES               AS IDX_SIZE                              ");
		sb.append("\n      , A.COLUMN_COUNT        AS COL_EACNT                             ");
		sb.append("\n      , A.ROW_LENGTH          AS ROW_EACNT                             ");
		sb.append("\n      , 0                     AS TBL_SIZE                              ");
		sb.append("\n      , 0                     AS DATA_SIZE                             ");
		sb.append("\n      , 0                     AS IDX_SIZE                              ");
		sb.append("\n      , 0                     AS NUSE_SIZE                             ");
		sb.append("\n      , 0                     AS BF_COL_EACNT                          ");
		sb.append("\n      , 0                     AS BF_ROW_EACNT                          ");
		sb.append("\n      , 0                     AS BF_TBL_SIZE                           ");
		sb.append("\n      , 0                     AS BF_DATA_SIZE                          ");
		sb.append("\n      , 0                     AS BF_IDX_SIZE                           ");
		sb.append("\n      , 0                     AS BF_NUSE_SIZE                          ");
		sb.append("\n      , to_date(null)         AS ANA_DTM                               ");
		sb.append("\n      , to_date(null)         AS CRT_DTM                               ");
		sb.append("\n      , to_date(null)         AS CHG_DTM                               ");
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
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_EXS   "); 
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_CD    ");
//		sb.append("\n      , ''                    AS MMART_TBL_ERR_DESCN ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_EXS   ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_CD    ");
//		sb.append("\n      , ''                    AS MMART_COL_ERR_DESCN ");
//		sb.append("\n      , ''                    AS MMART_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAE_DB2_TABLES A                                                  ");
		sb.append("\n        INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                   ) S ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.USER_NAME) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL  ("
				+ " DB_SCH_ID\n" + 
				",DBC_TBL_NM\n" + 
				",DBC_COL_NM\n" + 
				",DBC_COL_KOR_NM\n" + 
				",VERS\n" + 
				",REG_TYP\n" + 
				",REG_DTM\n" + 
				",UPD_DTM\n" + 
				",DESCN\n" + 
				",DDL_COL_ID\n" + 
				",PDM_COL_ID\n" + 
				",ITM_ID\n" + 
				",DATA_TYPE\n" + 
				",DATA_LEN\n" + 
				",DATA_PNUM\n" + 
				",DATA_PNT\n" + 
				",NULL_YN\n" + 
				",DEFLT_LEN\n" + 
				",DEFLT_VAL\n" + 
				",PK_YN\n" + 
				",ORD\n" + 
				",PK_ORD\n" + 
				",COL_DESCN\n" + 
				",COL_DQ_EXP_YN\n" + 
				",DDL_COL_EXTNC_YN\n" + 
				",DDL_ORD_ERR_EXS\n" + 
				",DDL_PK_YN_ERR_EXS\n" + 
				",DDL_PK_ORD_ERR_EXS\n" + 
				",DDL_NULL_YN_ERR_EXS\n" + 
				",DDL_DEFLT_ERR_EXS\n" + 
				",DDL_CMMT_ERR_EXS\n" + 
				",DDL_DATA_TYPE_ERR_EXS\n" + 
				",DDL_DATA_LEN_ERR_EXS\n" + 
				",DDL_DATA_PNT_ERR_EXS\n" + 
				",DDL_COL_ERR_EXS\n" + 
				",PDM_COL_EXTNC_EXS\n" + 
				",PDM_ORD_ERR_EXS\n" + 
				",PDM_PK_YN_ERR_EXS\n" + 
				",PDM_PK_ORD_ERR_EXS\n" + 
				",PDM_NULL_YN_ERR_EXS\n" + 
				",PDM_DEFLT_ERR_EXS\n" + 
				",PDM_CMMT_ERR_EXS\n" + 
				",PDM_DATA_TYPE_ERR_EXS\n" + 
				",PDM_DATA_LEN_ERR_EXS\n" + 
				",PDM_DATA_PNT_ERR_EXS\n" + 
				",PDM_COL_ERR_EXS\n" + 
				",COL_ERR_CONTS\n" + 
				",FK_YN"
				+ ")   ");
		sb.append("\n SELECT S.DB_SCH_ID          ");
		sb.append("\n      , A.SYSTEM_TABLE_NAME  ");
		sb.append("\n      , RTRIM(A.COLUMN_NAME) ");
		sb.append("\n      , A.COLUMN_TEXT        ");
		sb.append("\n      , 1                    ");
		sb.append("\n      , to_char(NULL)        ");
		if(DQConstant.REP_DB_TYPE.equals("DB2")){
		sb.append("\n      , CURRENT_TIMESTAMP    ");
			}else{
		sb.append("\n      , SYSDATE              ");
			}
		sb.append("\n      , to_date(null) as UPD_DTM ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , ''                       ");
		sb.append("\n      , A.DATA_TYPE              ");
		sb.append("\n      , CASE WHEN UPPER(A.DATA_TYPE) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME','NUMERIC', 'DECIMAL')                                                ");
		sb.append("\n             THEN A.LENGTH  ");
		sb.append("\n         END AS LENGTH      ");
		sb.append("\n      , CASE WHEN NVL(A.NUMERIC_SCALE, 0)  > 0 ");
		sb.append("\n             THEN A.NUMERIC_SCALE   ");
		sb.append("\n             ELSE NULL   ");
		sb.append("\n         END AS SCALE             ");
		sb.append("\n      , NULL                ");
		sb.append("\n      , A.IS_NULLABLE AS IS_NULLABLE                                               ");
		sb.append("\n      , LENGTH(A.COLUMN_DEFAULT) AS DEFLT_LEN                                             ");
		sb.append("\n      , A.COLUMN_DEFAULT AS DATA_DEFAULT                                        ");
		sb.append("\n      , A.IS_PK AS PK_YN");
		sb.append("\n      , A.ORDINAL_POSITION                                              ");
		sb.append("\n      , CASE WHEN A.IS_PK = 'Y' THEN A.IC_POSITION END AS PK_ORD ");
		sb.append("\n      , A.COLUMN_TEXT                                                    ");
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
		sb.append("\n      FROM ( SELECT DISTINCT C.*, (SELECT MIN(IC.ORDINAL_POSITION)        ");
		sb.append("\n                                     FROM WAE_DB2_IDX_COLUMNS IC          ");
		sb.append("\n                                          INNER JOIN WAE_DB2_INDEXES I    ");
		sb.append("\n                                             ON IC.INDEX_NAME = I.INDEX_NAME  ");
		sb.append("\n                                            AND IC.SYSTEM_TABLE_NAME = I.SYSTEM_TABLE_NAME      ");
		sb.append("\n                                            AND IC.USER_NAME = I.USER_NAME    ");
		sb.append("\n                                     WHERE C.SYSTEM_TABLE_NAME = IC.SYSTEM_TABLE_NAME    ");
		sb.append("\n                                       AND C.COLUMN_NAME = IC.COLUMN_NAME     ");
		sb.append("\n                                       AND C.IS_PK = 'Y'     ");
		sb.append("\n                                       AND I.IS_UNIQUE ='Y'  ");
		sb.append("\n                                   ) AS IC_POSITION          ");
		sb.append("\n            FROM WAE_DB2_COLUMNS C       ");		
		sb.append("\n           ) A    ");
		sb.append("\n          INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                     ) S  ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.USER_NAME) = UPPER(S.DB_SCH_PNM)     ");
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
		sb.append("\n SELECT S.DB_SCH_ID          ");
		sb.append("\n      , A.SYSTEM_TABLE_NAME  ");
		sb.append("\n      , RTRIM(A.INDEX_NAME)  ");
		sb.append("\n      , S.DB_CONN_TRG_ID     ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , 1                    ");
		sb.append("\n      , NULL                 ");
		if(DQConstant.REP_DB_TYPE.equals("DB2")){
	    sb.append("\n      , CURRENT_TIMESTAMP    ");
		}else{
		sb.append("\n      , SYSDATE              ");
		}
		sb.append("\n      , ''                   ");
		sb.append("\n      , to_date(null)        ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , A.INDEX_TEXT         ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , ''                   ");
//		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'P' THEN 'Y' ELSE 'N' END PK_YN                   ");
//		sb.append("\n      , CASE WHEN D.CNST_TYPE = 'U' THEN 'Y' ELSE 'N' END PK_YN                   ");
//		sb.append("\n      , E.COL_CNT            ");
//		sb.append("\n      , C.BYTES AS IDX_SIZE  ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , A.IS_UNIQUE          ");
		sb.append("\n      , A.COLUMN_COUNT       ");
		sb.append("\n      , 0                    ");
		sb.append("\n      , 0  AS BF_IDX_EACNT   ");
		sb.append("\n      , 0  AS BF_IDX_SIZE    ");
		sb.append("\n      , to_date(null)  AS ANA_DTM   ");
		sb.append("\n      , to_date(null)  AS CRT_DTM   ");
		sb.append("\n      , to_date(null)  AS CHG_DTM   ");
		sb.append("\n      , ''  AS IDX_DQ_EXP_YN        ");
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
		sb.append("\n   FROM WAE_DB2_INDEXES A  ");
		sb.append("\n        INNER JOIN (       ");
		sb.append(getdbconnsql());
		sb.append("\n                    ) S    ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.USER_NAME) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID      ");
		sb.append("\n      , A.SYSTEM_TABLE_NAME                    ");
		sb.append("\n      , A.COLUMN_NAME                    ");
		sb.append("\n      , A.INDEX_NAME                    ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , 1                         ");
		sb.append("\n      , TO_CHAR(NULL)                         ");
		if(DQConstant.REP_DB_TYPE.equals("DB2")){
		sb.append("\n      , CURRENT_TIMESTAMP                                      ");
		}else{
		sb.append("\n      , SYSDATE                             ");
		}
		sb.append("\n      , ''                          ");
		sb.append("\n      , to_date(null)                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , A.COLUMN_TEXT                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , ''                          ");
		sb.append("\n      , A.ORDINAL_POSITION                 ");
		sb.append("\n      , A.ORDERING                  ");
		sb.append("\n      , A.DATA_TYPE                 ");
		sb.append("\n      , 0                 ");
		sb.append("\n      , CASE WHEN UPPER(A.DATA_TYPE) IN ('VARCHAR', 'CHAR', 'NVARCHAR', 'NUIVARCHAR', 'SYSNAME')                   ");
		sb.append("\n             THEN A.LENGTH                   ");
		sb.append("\n        END AS LENGTH                  ");
		sb.append("\n      , A.NUMERIC_SCALE                  ");
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
		sb.append("\n   FROM (SELECT IC.*, C.DATA_TYPE, C.LENGTH, C.NUMERIC_SCALE              ");
		sb.append("\n           FROM WAE_DB2_IDX_COLUMNS IC          ");
		sb.append("\n                INNER JOIN WAE_DB2_COLUMNS C    ");
		sb.append("\n                   ON C.SYSTEM_TABLE_NAME = IC.SYSTEM_TABLE_NAME                ");
		sb.append("\n                  AND C.COLUMN_NAME = IC.COLUMN_NAME  ) A           ");
		sb.append("\n         INNER JOIN (    ");
		sb.append(getdbconnsql());
		sb.append("\n                     ) S  ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n          AND UPPER(A.USER_NAME) = UPPER(S.DB_SCH_PNM)     ");
		sb.append("\n   WHERE A.DB_CONN_TRG_ID = '"+dbConnTrgId+"'      ");

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
