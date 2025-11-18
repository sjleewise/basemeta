package kr.wise.commons.util;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubridSchemaCollect {

	Logger logger = LoggerFactory.getLogger(getClass());

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
	
	public CubridSchemaCollect() {

	}

	/** insomnia */
	public CubridSchemaCollect(Connection source, Connection target, List<TargetDbmsDM> lsitdm) {
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
				logger.debug(sp + (++p) + ". insertMetaDATABASES WAE_CBRD_DB: " + cnt + " OK!!");

				con_org.commit();
			}

			dbCnt++;
						
			logger.debug(" schemaName : "+schemaName);
			//DBA_USERS --------------------------------------------------------
			cnt = insertDB_USER();
			logger.debug(sp + (++p) + ". insertDB_USER " + cnt + " OK!!");

			//DB_CLASS -------------------------------------------------------
			cnt = insertDB_CLASS();
			logger.debug(sp + (++p) + ". insertDB_CLASS " + cnt + " OK!!");
			
			//DB_ATTRIBUTE ---------------------------------------------------
			cnt = insertDB_ATTRIBUTE();
			logger.debug(sp + (++p) + ". insertDB_ATTRIBUTE " + cnt + " OK!!");

			//DB_INDEX ---------------------------------------------------
			cnt = insertDB_INDEX();
			logger.debug(sp + (++p) + ". insertDB_INDEX " + cnt + " OK!!");
			
			//DB_INDEX_KEY ---------------------------------------------------
			cnt = insertDB_INDEX_KEY();
			logger.debug(sp + (++p) + ". insertDB_INDEX_KEY " + cnt + " OK!!");
			
			
			con_org.commit();

		}

		result = true;

		return result;

	}
	
	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB_CLASS ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB_CLASS : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB_ATTRIBUTE ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB_ATTRIBUTE : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB_INDEX ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB_INDEX : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB_INDEX_KEY ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB_INDEX_KEY : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB_USER "); 
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"'  ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB_USER : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_CBRD_DB     ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_CBRD_DB : " + result);
		
		return result;
	}

	/**
	 * DBC DBA_USERS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDB_USER() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , NAME                         ");
	    sb.append("\n      , ID                           ");
	    sb.append("\n      , PASSWORD                     ");
	    sb.append("\n      , DIRECT_GROUPS                ");
	    sb.append("\n      , GROUPS                       ");
	    sb.append("\n      , AUTHORIZATION                ");
	    sb.append("\n      , TRIGGERS                     ");
	    sb.append("\n      , COMMENT                      ");
		sb.append("\n   FROM DB_USER                      ");
		sb.append("\n  WHERE NAME = '").append(schemaName).append("'");
		
		logger.debug(sb.toString());
		
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\n INSERT INTO WAE_CBRD_DB_USER (   ");
		sb.append("\n    DB_NM                    ");
		sb.append("\n  , SCH_NM                   ");
		sb.append("\n  , DB_SCH_ID				  ");
		sb.append("\n  , NAME                     ");                    
        sb.append("\n  , ID                       "); //5               
        sb.append("\n  , PASSWORD                 ");                    
        //sb.append("\n  , DIRECT_GROUPS            ");                    
        //sb.append("\n  , GROUPS                   ");                    
        sb.append("\n  , AUTHORIZATION            ");                    
        sb.append("\n  , DB_TRIGGER                 "); //10  TRIGGERS -> DB_TRIGGER    
        sb.append("\n  , \"COMMENT\"              ");         
		sb.append("\n ) VALUES (                  ");
		sb.append("\n    ?, ?, ?, ?, ?            ");
		sb.append("\n  , ?, ?, ?, ?               ");
		sb.append("\n )                           ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, schemaId);
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ID"));
				pst_org.setString(rsGetCnt++, UtilString.null2Blank(rs.getString("PASSWORD")));
				//pst_org.setString(rsGetCnt++, rs.getString("DIRECT_GROUPS"));
				//pst_org.setString(rsGetCnt++, rs.getString("GROUPS"));
				pst_org.setString(rsGetCnt++, UtilString.null2Blank(rs.getObject("AUTHORIZATION")));
				pst_org.setString(rsGetCnt++, rs.getString("DB_TRIGGER")); 	//TRIGGERS -> DB_TRIGGER
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));

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
	 * DBC DB_CLASS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDB_CLASS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      , CLASS_NAME                      ");
        sb.append("\n      , OWNER_NAME                      ");
        sb.append("\n      , CLASS_TYPE                      ");
        sb.append("\n      , IS_SYSTEM_CLASS                 ");
        sb.append("\n      , PARTITIONED                     ");
        sb.append("\n      , IS_REUSE_OID_CLASS              ");
        sb.append("\n      , COLLATION                       ");
        sb.append("\n      , COMMENT                         ");
		sb.append("\n   FROM DB_CLASS A                      ");
		sb.append("\n  WHERE A.OWNER_NAME = '").append(schemaName).append("'");

		//System.out.println("insertDB_CLASS : \n"+sb);
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_CBRD_DB_CLASS (   ");
		sb.append("\n        DB_NM                      ");
		sb.append("\n      , SCH_NM                     "); 
		sb.append("\n      , CLASS_NAME                 ");
        sb.append("\n      , OWNER_NAME                 ");
        sb.append("\n      , CLASS_TYPE                 "); //5
        sb.append("\n      , IS_SYSTEM_CLASS            ");
        sb.append("\n      , PARTITIONED                ");
        sb.append("\n      , IS_REUSE_OID_CLASS         ");
        sb.append("\n      , COLLATION                  ");
        sb.append("\n      , \"COMMENT\"                "); //10
		sb.append("\n ) VALUES (                        ");
		sb.append("\n     ?, ?, ?, ?, ?                 ");
		sb.append("\n   , ?, ?, ?, ?, ?                 ");		
		sb.append("\n )                                 ");
		
		logger.debug("\n" + sb.toString());
		
		int cnt = 0;
		
		if( rs != null ) {

			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("OWNER_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("OWNER_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_SYSTEM_CLASS"));
				pst_org.setString(rsGetCnt++, rs.getString("PARTITIONED"));			
				pst_org.setString(rsGetCnt++, rs.getString("IS_REUSE_OID_CLASS"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));
				
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
	 * DBC DB_ATTRIBUTE 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDB_ATTRIBUTE() throws SQLException
	{
		StringBuffer sb = new StringBuffer();


		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      , ATTR_NAME             ");
        sb.append("\n      , CLASS_NAME            ");
        sb.append("\n      , ATTR_TYPE             ");
        sb.append("\n      , DEF_ORDER             ");
        sb.append("\n      , FROM_CLASS_NAME       ");
        sb.append("\n      , FROM_ATTR_NAME        ");
        sb.append("\n      , DATA_TYPE             ");
        sb.append("\n      , PREC                  ");
        sb.append("\n      , SCALE                 ");
        sb.append("\n      , CHARSET               ");
        sb.append("\n      , COLLATION             ");
        sb.append("\n      , DOMAIN_CLASS_NAME     ");
        sb.append("\n      , DEFAULT_VALUE         ");
        sb.append("\n      , IS_NULLABLE           ");
        sb.append("\n      , COMMENT               ");
		sb.append("\n   FROM DB_ATTRIBUTE A        ");
		
		logger.debug("test sql:"+sb.toString());

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_CBRD_DB_ATTRIBUTE (         ");
		sb.append("\n        DB_NM                     ");
		sb.append("\n      , SCH_NM                    ");
		sb.append("\n      , ATTR_NAME                 ");
        sb.append("\n      , CLASS_NAME                ");
        sb.append("\n      , ATTR_TYPE                 "); //5
        sb.append("\n      , DEF_ORDER                 ");
        sb.append("\n      , FROM_CLASS_NAME           ");
        sb.append("\n      , FROM_ATTR_NAME            ");
        sb.append("\n      , DATA_TYPE                 ");
        sb.append("\n      , PREC                      "); //10
        sb.append("\n      , SCALE                     "); 
        sb.append("\n      , CHARSET                   ");
        sb.append("\n      , COLLATION                 ");
        sb.append("\n      , DOMAIN_CLASS_NAME         ");
        sb.append("\n      , DEFAULT_VALUE             "); //15
        sb.append("\n      , IS_NULLABLE               ");
        sb.append("\n      , \"COMMENT\"               ");
		sb.append("\n  ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?, ?                 "); 
		sb.append("\n  , ?, ?                          "); 		
		sb.append("\n  )                               ");

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
				pst_org.setString(rsGetCnt++, rs.getString("ATTR_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ATTR_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("DEF_ORDER"));
				pst_org.setString(rsGetCnt++, rs.getString("FROM_CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("FROM_ATTR_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DATA_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("PREC"));
				pst_org.setString(rsGetCnt++, rs.getString("SCALE"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARSET"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_VALUE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT")); 

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
	 * DBC DB_INDEX 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDB_INDEX() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , INDEX_NAME         ");
        sb.append("\n      , IS_UNIQUE          ");
        sb.append("\n      , IS_REVERSE         ");
        sb.append("\n      , CLASS_NAME         ");
        sb.append("\n      , KEY_COUNT          ");
        sb.append("\n      , IS_PRIMARY_KEY     ");
        sb.append("\n      , IS_FOREIGN_KEY     ");
        sb.append("\n      , FILTER_EXPRESSION  ");
        sb.append("\n      , HAVE_FUNCTION      ");
        sb.append("\n      , COMMENT            ");
		sb.append("\n   FROM DB_INDEX           ");		

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_CBRD_DB_INDEX  ( "); 		
		sb.append("\n   	 DB_NM              ");
		sb.append("\n  	   , SCH_NM             ");
		sb.append("\n      , INDEX_NAME         ");
        sb.append("\n      , IS_UNIQUE          ");
        sb.append("\n      , IS_REVERSE         "); //5
        sb.append("\n      , CLASS_NAME         ");
        sb.append("\n      , KEY_COUNT          ");
        sb.append("\n      , IS_PRIMARY_KEY     ");
        sb.append("\n      , IS_FOREIGN_KEY     ");
        sb.append("\n      , FILTER_EXPRESSION  "); //10 
        sb.append("\n      , HAVE_FUNCTION      ");
        sb.append("\n      , \"COMMENT\"        ");
		sb.append("\n ) VALUES (                ");
		sb.append("\n    ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?                   ");		
		sb.append("\n )                         ");

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
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_UNIQUE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_REVERSE"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("KEY_COUNT"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_PRIMARY_KEY"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_FOREIGN_KEY"));
				pst_org.setString(rsGetCnt++, rs.getString("FILTER_EXPRESSION"));
				pst_org.setString(rsGetCnt++, rs.getString("HAVE_FUNCTION"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));

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
	
	private int insertDB_INDEX_KEY() throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      , INDEX_NAME         ");
        sb.append("\n      , CLASS_NAME         ");
        sb.append("\n      , KEY_ATTR_NAME      ");
        sb.append("\n      , KEY_ORDER          ");
        sb.append("\n      , ASC_DESC           ");
        sb.append("\n      , KEY_PREFIX_LENGTH  ");
        sb.append("\n      , FUNC               ");
		sb.append("\n   FROM DB_INDEX_KEY       ");		

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\n INSERT INTO WAE_CBRD_DB_INDEX_KEY  ( "); 		
		sb.append("\n   	 DB_NM                         ");
		sb.append("\n  	   , SCH_NM                        ");
		sb.append("\n      , INDEX_NAME                    ");
        sb.append("\n      , CLASS_NAME                    ");
        sb.append("\n      , KEY_ATTR_NAME                 "); //5
        sb.append("\n      , KEY_ORDER                     ");
        sb.append("\n      , ASC_DESC                      ");
        sb.append("\n      , KEY_PREFIX_LENGTH             ");
        sb.append("\n      , FUNC                          ");        
		sb.append("\n ) VALUES (                           ");
		sb.append("\n    ?, ?, ?, ?, ?                     ");
		sb.append("\n  , ?, ?, ?, ?                        ");		
		sb.append("\n )                                    ");

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
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("KEY_ATTR_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("KEY_ORDER"));
				pst_org.setString(rsGetCnt++, rs.getString("ASC_DESC"));
				pst_org.setString(rsGetCnt++, rs.getString("KEY_PREFIX_LENGTH"));
				pst_org.setString(rsGetCnt++, rs.getString("FUNC"));

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
	protected ResultSet getResultSet(String query_tgt) throws SQLException 
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
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	protected int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_CBRD_DB (");
		sb.append("\n   DB_NM                                                       ");
		sb.append("\n  ,DB_CONN_TRG_ID                                              ");
		sb.append("\n  ,STR_DTM                                                     ");
		sb.append("\n )                                                             ");
		sb.append("\n SELECT DISTINCT DB_CONN_TRG_PNM                               ");
		sb.append("\n      , A.DB_CONN_TRG_ID                                       ");
		sb.append("\n      , A.STR_DTM                                              ");
		sb.append("\n   FROM WAA_DB_CONN_TRG A                                      ");
		sb.append("\n        INNER JOIN WAA_DB_SCH B                                ");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID              ");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')  ");
		sb.append("\n          AND B.REG_TYP_CD IN ('C', 'U')                       ");
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('9999-12-31', 'YYYY-MM-DD')        ");
		sb.append("\n    AND A.REG_TYP_CD IN ('C', 'U')                             ");
		sb.append("\n  AND	A.DBMS_TYP_CD    = '").append(dbType).append("'") ;
		sb.append("\n  AND  UPPER(A.DB_CONN_TRG_PNM) = UPPER('").append(dbName).append("')") ;
//		sb.append("\n  AND  UPPER(B.OBJ_KNM)   =    UPPER('").append(sDBName).append("')") ;

//		logger.debug("insertMetaDATABASES : \n"+sb);

		return setExecuteUpdate_Org(sb.toString());
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
	protected int setExecuteUpdate_Org(String query_tgt) throws SQLException
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
	protected boolean getSaveResult(int cnt) throws SQLException
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
	protected int[] executeBatch() throws SQLException 
	{
		return executeBatch(false);
	}

	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 */
	protected int[] executeBatch(boolean execYN) throws SQLException 
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
		cnt = insertwatidx();
		logger.debug("insertwatidx : " + cnt + " OK!!");

		//WAT_INDEX_COLUMN ---------------------------------------------------
		cnt = insertwatidxcol();
		logger.debug("insertwatidxcol : " + cnt + " OK!!");

		result = true;
		
		con_org.commit();

		return result;
	}

	/**  insomnia
	 * @throws Exception */
	private int insertwattbl() throws Exception {

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAT_DBC_TBL                     ");		
		sb.append("\n (                                           ");
		sb.append("\n       DB_SCH_ID                             ");
		sb.append("\n    ,  DBC_TBL_NM                            ");
		sb.append("\n    ,  DBC_TBL_KOR_NM                        ");
		sb.append("\n    ,  VERS                                  ");
		sb.append("\n    ,  REG_TYP                               ");
		sb.append("\n    ,  REG_DTM                               ");
		sb.append("\n    ,  UPD_DTM                               ");
		sb.append("\n    ,  DESCN                                 ");
		sb.append("\n    ,  DB_CONN_TRG_ID                        ");
		sb.append("\n    ,  DBC_TBL_SPAC_NM                       ");
		sb.append("\n    ,  DDL_TBL_ID                            ");
		sb.append("\n    ,  PDM_TBL_ID                            ");
		sb.append("\n    ,  DBMS_TYPE                             ");
		sb.append("\n    ,  SUBJ_ID                               ");
		sb.append("\n    ,  COL_EACNT                             ");
		sb.append("\n    ,  ROW_EACNT                             ");
		sb.append("\n    ,  TBL_SIZE                              ");
		sb.append("\n    ,  DATA_SIZE                             ");
		sb.append("\n    ,  IDX_SIZE                              ");
		sb.append("\n    ,  NUSE_SIZE                             ");
		sb.append("\n    ,  BF_COL_EACNT                          ");
		sb.append("\n    ,  BF_ROW_EACNT                          ");
		sb.append("\n    ,  BF_TBL_SIZE                           ");
		sb.append("\n    ,  BF_DATA_SIZE                          ");
		sb.append("\n    ,  BF_IDX_SIZE                           ");
		sb.append("\n    ,  BF_NUSE_SIZE                          ");
		sb.append("\n    ,  ANA_DTM                               ");
		sb.append("\n    ,  CRT_DTM                               ");
		sb.append("\n    ,  CHG_DTM                               ");
		sb.append("\n    ,  PDM_DESCN                             ");
		sb.append("\n    ,  TBL_DQ_EXP_YN                         ");	
		sb.append("\n )                                                                     ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.CLASS_NAME          AS DBC_TBL_NM                            ");
		sb.append("\n      , A.\"COMMENT\"         AS DBC_TBL_KOR_NM                        ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                  AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , ''                    AS DDL_TBL_ID                            ");
		sb.append("\n      , ''                    AS PDM_TBL_ID                            ");
		sb.append("\n      , '" + dbType + "'      AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS SUBJ_ID                               ");
		sb.append("\n      , E.COL_CNT             AS COL_EACNT                             ");
		sb.append("\n      , 0                     AS ROW_EACNT                             ");
		sb.append("\n      , 0                     AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , 0                     AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , ''                    AS BF_COL_EACNT                          ");
		sb.append("\n      , ''                    AS BF_ROW_EACNT                          ");
		sb.append("\n      , ''                    AS BF_TBL_SIZE                           ");
		sb.append("\n      , ''                    AS BF_DATA_SIZE                          ");
		sb.append("\n      , ''                    AS BF_IDX_SIZE                           ");
		sb.append("\n      , ''                    AS BF_NUSE_SIZE                          ");
		sb.append("\n      , NULL                    AS ANA_DTM                               ");
		sb.append("\n      , NULL                    AS CRT_DTM                               ");
		sb.append("\n      , NULL                    AS CHG_DTM                               ");
		sb.append("\n      , ''                    AS PDM_DESCN                             ");
		sb.append("\n      , ''                    AS TBL_DQ_EXP_YN                         ");		
		sb.append("\n   FROM WAE_CBRD_DB_CLASS A                                      ");
		sb.append("\n        INNER JOIN (                                             ");
		sb.append("\n            " + getdbconnsql() + "                               ");
		sb.append("\n        ) S                                                      ");
		sb.append("\n          ON S.DB_CONN_TRG_PNM = A.DB_NM                         ");
		sb.append("\n         AND S.DB_SCH_PNM      = A.SCH_NM                        ");
		sb.append("\n       INNER JOIN WAE_CBRD_DB_USER B                             ");
		sb.append("\n          ON B.DB_NM =  A.DB_NM                                  ");		
		sb.append("\n         AND B.SCH_NM = A.SCH_NM                                 ");
		sb.append("\n        LEFT OUTER JOIN                                          ");
		sb.append("\n        (                                                        ");
		sb.append("\n         SELECT DB_NM, SCH_NM, CLASS_NAME, COUNT(*) AS COL_CNT   ");
		sb.append("\n           FROM WAE_CBRD_DB_ATTRIBUTE                            ");
		sb.append("\n          GROUP BY DB_NM, SCH_NM, CLASS_NAME                     ");
		sb.append("\n         ) E                                                     ");
		sb.append("\n          ON E.DB_NM      = A.DB_NM                              ");
		sb.append("\n         AND E.SCH_NM     = A.SCH_NM                             ");
		sb.append("\n         AND E.CLASS_NAME = A.CLASS_NAME                         ");
		sb.append("\n  WHERE A.DB_NM = '"+dbName+"'                                   ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {  

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAT_DBC_COL   ");
		sb.append("\n (                         ");
		sb.append("\n        DB_SCH_ID          ");
		sb.append("\n      , DBC_TBL_NM         ");      
		sb.append("\n      , DBC_COL_NM         ");      
		sb.append("\n      , DBC_COL_KOR_NM     ");      
		sb.append("\n      , VERS               ");      
		sb.append("\n      , REG_TYP            ");      
		sb.append("\n      , REG_DTM            ");      
		sb.append("\n      , UPD_DTM            ");      
		sb.append("\n      , DESCN              ");      
		sb.append("\n      , DDL_COL_ID         ");      
		sb.append("\n      , PDM_COL_ID         ");      
		sb.append("\n      , ITM_ID             ");      
		sb.append("\n      , DATA_TYPE          ");      
		sb.append("\n      , DATA_LEN           ");      
		sb.append("\n      , DATA_PNUM          ");      
		sb.append("\n      , DATA_PNT           ");      
		sb.append("\n      , NULL_YN            ");      
		sb.append("\n      , DEFLT_LEN          ");      
		sb.append("\n      , DEFLT_VAL          "); 
		sb.append("\n      , PK_YN              "); 
		sb.append("\n      , ORD                "); 
		sb.append("\n      , PK_ORD             "); 
		sb.append("\n      , COL_DESCN          "); 
		sb.append("\n      , COL_DQ_EXP_YN      "); 	
		sb.append("\n )                                                                      ");
		sb.append("\n SELECT S.DB_SCH_ID                                                     ");
		sb.append("\n      , A.CLASS_NAME                    AS DBC_TBL_NM                   ");      
		sb.append("\n      , A.ATTR_NAME                     AS DBC_COL_NM                   ");      
		sb.append("\n      , A.\"COMMENT\"                   AS DBC_COL_KOR_NM               ");      
		sb.append("\n      , '1'                             AS VERS                         ");      
		sb.append("\n      , NULL                            AS REG_TYP                      ");      
		sb.append("\n      , SYSDATE                         AS REG_DTM                      ");      
		sb.append("\n      , NULL                              AS UPD_DTM                      ");      
		sb.append("\n      , ''                              AS DESCN                        ");      
		sb.append("\n      , ''                              AS DDL_COL_ID                   ");      
		sb.append("\n      , ''                              AS PDM_COL_ID                   ");      
		sb.append("\n      , ''                              AS ITM_ID                       ");      
		sb.append("\n      , A.DATA_TYPE                     AS DATA_TYPE                    ");      
		sb.append("\n      , A.PREC                          AS DATA_LEN                     ");      
		sb.append("\n      , 0                               AS DATA_PNUM                    ");      
		sb.append("\n      , A.SCALE                         AS DATA_PNT                     ");      
		sb.append("\n      , SUBSTR(A.IS_NULLABLE,1,1)       AS NULL_YN                      ");      
		sb.append("\n      , 0                               AS DEFLT_LEN                    ");      
		sb.append("\n      , A.DEFAULT_VALUE                 AS DEFLT_VAL                    "); 
		sb.append("\n      , (CASE WHEN C.DB_NM IS NOT NULL THEN 'Y' ELSE 'N' END) AS PK_YN  "); 
		sb.append("\n      , A.DEF_ORDER                     AS ORD                          "); 
		sb.append("\n      , (CASE WHEN C.DB_NM IS NOT NULL THEN A.DEF_ORDER END) AS PK_ORD  "); 
		sb.append("\n      , ''                              AS COL_DESCN                    "); 
		sb.append("\n      , ''                              AS COL_DQ_EXP_YN                "); 	
		sb.append("\n   FROM WAE_CBRD_DB_ATTRIBUTE A                               ");
		sb.append("\n       INNER JOIN (                                           ");
		sb.append("\n       " + getdbconnsql() + "                                 ");
		sb.append("\n       ) S                                                    ");
		sb.append("\n        ON S.DB_CONN_TRG_PNM  = A.DB_NM                       ");
		sb.append("\n       AND S.DB_SCH_PNM       = A.SCH_NM                      ");
		sb.append("\n      INNER JOIN WAE_CBRD_DB_USER B                           ");
		sb.append("\n         ON B.DB_NM = A.DB_NM                                 ");
		sb.append("\n        AND B.SCH_NM = A.SCH_NM                               ");
		sb.append("\n      LEFT JOIN                                               ");
	    sb.append("\n      (SELECT C.DB_NM, C.SCH_NM                               ");
	    sb.append("\n            , C.CLASS_NAME                                    ");
	    sb.append("\n            , C.INDEX_NAME                                    ");
	    sb.append("\n            , D.KEY_ATTR_NAME                                 ");
	    sb.append("\n         FROM WAE_CBRD_DB_INDEX C                             ");
	    sb.append("\n              INNER JOIN WAE_CBRD_DB_INDEX_KEY D              ");
	    sb.append("\n                 ON C.DB_NM      = D.DB_NM                    ");
	    sb.append("\n                AND C.SCH_NM     = D.SCH_NM                   ");
	    sb.append("\n                AND C.CLASS_NAME = D.CLASS_NAME               ");
	    sb.append("\n                AND C.INDEX_NAME = D.INDEX_NAME               ");
	    sb.append("\n        WHERE C.IS_PRIMARY_KEY = 'YES'                        ");
	    sb.append("\n       ) C                                                    ");
	    sb.append("\n         ON C.DB_NM      = A.DB_NM                            ");
	    sb.append("\n        AND C.SCH_NM     = A.SCH_NM                           ");   
	    sb.append("\n        AND C.CLASS_NAME = A.CLASS_NAME                       ");  
	    sb.append("\n        AND C.KEY_ATTR_NAME = A.ATTR_NAME                     ");
		sb.append("\n WHERE A.DB_NM = '" + dbName + "'                             ");

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

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAT_DBC_IDX   ");
		sb.append("\n (                         ");
		sb.append("\n     DB_SCH_ID             "); 
		sb.append("\n   , DBC_TBL_NM            ");
		sb.append("\n   , DBC_IDX_NM            ");
		sb.append("\n   , DB_CONN_TRG_ID        ");
		sb.append("\n   , DBC_IDX_KOR_NM        ");
		sb.append("\n   , VERS                  ");
		sb.append("\n   , REG_TYP               ");
		sb.append("\n   , REG_DTM               ");
		sb.append("\n   , REG_USER              ");
		sb.append("\n   , UPD_DTM               ");
		sb.append("\n   , UPD_USER              ");
		sb.append("\n   , DESCN                 ");
		sb.append("\n   , DBC_TBL_SPAC_NM       ");
		sb.append("\n   , DDL_IDX_ID            ");
		sb.append("\n   , PDM_IDX_ID            ");
		sb.append("\n   , PK_YN                 ");
		sb.append("\n   , UQ_YN                 ");
		sb.append("\n   , COL_EACNT             ");
		sb.append("\n   , IDX_SIZE              "); 
		sb.append("\n )                                                                           ");
		sb.append("\n SELECT S.DB_SCH_ID                                       AS DB_SCH_ID       "); 
		sb.append("\n      , A.CLASS_NAME                                      AS DBC_TBL_NM      ");
		sb.append("\n      , A.INDEX_NAME                                      AS DBC_IDX_NM      ");
		sb.append("\n      , S.DB_CONN_TRG_ID                                  AS DB_CONN_TRG_ID  ");
		sb.append("\n      , ''                                                AS DBC_IDX_KOR_NM  ");
		sb.append("\n      , '1'                                               AS VERS            ");
		sb.append("\n      , NULL                                              AS REG_TYP         ");
		sb.append("\n      , SYSDATE                                           AS REG_DTM         ");
		sb.append("\n      , ''                                                AS REG_USER        ");
		sb.append("\n      , NULL                                              AS UPD_DTM         ");
		sb.append("\n      , ''                                                AS UPD_USER        ");
		sb.append("\n      , ''                                                AS DESCN           ");
		sb.append("\n      , ''                                                AS DBC_TBL_SPAC_NM ");
		sb.append("\n      , ''                                                AS DDL_IDX_ID      ");
		sb.append("\n      , ''                                                AS PDM_IDX_ID      ");
		sb.append("\n      , SUBSTR(A.IS_PRIMARY_KEY,1,1)                      AS PK_YN           ");
		sb.append("\n      , SUBSTR(A.IS_UNIQUE,1,1)                           AS UQ_YN           ");
		sb.append("\n      , A.KEY_COUNT                                       AS COL_EACNT       ");
		sb.append("\n      , 0                                                 AS IDX_SIZE        ");                             	
		sb.append("\n   FROM WAE_CBRD_DB_INDEX A                         ");
		sb.append("\n        INNER JOIN (                                ");
		sb.append("\n        " + getdbconnsql() + "                      ");
		sb.append("\n        ) S                                         ");
		sb.append("\n           ON S.DB_CONN_TRG_PNM = A.DB_NM           ");
		sb.append("\n          AND S.DB_SCH_PNM      = A.SCH_NM          ");
		sb.append("\n        INNER JOIN WAE_CBRD_DB_USER B               ");
		sb.append("\n           ON B.DB_NM  = A.DB_NM                    ");
		sb.append("\n          AND B.SCH_NM = A.SCH_NM                   ");
		sb.append("\n    WHERE A.DB_NM = '" + dbName + "'                ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {

		StringBuffer sb = new StringBuffer();
		
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL     ");
		sb.append("\n (                               ");
		sb.append("\n         DB_SCH_ID               ");
		sb.append("\n      ,  DBC_TBL_NM              ");
		sb.append("\n      ,  DBC_IDX_COL_NM          ");
		sb.append("\n      ,  DBC_IDX_NM              ");
		sb.append("\n      ,  DBC_IDX_COL_KOR_NM      ");
		sb.append("\n      ,  VERS                    ");
		sb.append("\n      ,  REG_TYP                 ");
		sb.append("\n      ,  REG_DTM                 ");
		sb.append("\n      ,  REG_USER                ");
		sb.append("\n      ,  UPD_DTM                 ");
		sb.append("\n      ,  UPD_USER                ");
		sb.append("\n      ,  DESCN                   ");
		sb.append("\n      ,  DDL_IDX_COL_ID          ");
		sb.append("\n      ,  PDM_IDX_COL_ID          ");
		sb.append("\n      ,  ORD                     ");
		sb.append("\n      ,  SORT_TYPE               ");
		sb.append("\n      ,  DATA_TYPE               ");
		sb.append("\n      ,  DATA_PNUM               ");
		sb.append("\n      ,  DATA_LEN                ");
		sb.append("\n      ,  DATA_PNT                ");
		sb.append("\n      ,  IDXCOL_DQ_EXP_YN                       ");
		sb.append("\n )                                              ");
		sb.append("\n SELECT S.DB_SCH_ID     AS DB_SCH_ID            ");
		sb.append("\n      , A.CLASS_NAME    AS DBC_TBL_NM           ");
		sb.append("\n      , A.KEY_ATTR_NAME AS DBC_IDX_COL_NM       ");
		sb.append("\n      , A.INDEX_NAME    AS DBC_IDX_NM           ");
		sb.append("\n      , ''              AS DBC_IDX_COL_KOR_NM   ");
		sb.append("\n      , '1'             AS VERS                 ");
		sb.append("\n      , NULL            AS REG_TYP              ");
		sb.append("\n      , SYSDATE         AS REG_DTM              ");
		sb.append("\n      , ''              AS REG_USER             ");
		sb.append("\n      , NULL              AS UPD_DTM              ");
		sb.append("\n      , ''              AS UPD_USER             ");
		sb.append("\n      , ''              AS DESCN                ");
		sb.append("\n      , ''              AS DDL_IDX_COL_ID       "); 
		sb.append("\n      , ''              AS PDM_IDX_COL_ID       "); 		
		sb.append("\n      , A.KEY_ORDER     AS ORD                  "); 
	    sb.append("\n      , A.ASC_DESC      AS SORT_TYPE            ");
	    sb.append("\n      , C.DATA_TYPE     AS DATA_TYPE            ");
	    sb.append("\n      , C.PREC          AS DATA_PNUM            ");
	    sb.append("\n      , C.PREC          AS DATA_LEN             ");
	    sb.append("\n      , C.SCALE         AS DATA_PNT             ");
		sb.append("\n      , ''              AS IDXCOL_DQ_EXP_YN     ");		
		sb.append("\n   FROM WAE_CBRD_DB_INDEX_KEY A                 ");
		sb.append("\n        INNER JOIN (                            ");
		sb.append("\n        " + getdbconnsql() + "                  ");
		sb.append("\n        ) S                                     ");
		sb.append("\n           ON S.DB_CONN_TRG_PNM = A.DB_NM       ");
		sb.append("\n          AND S.DB_SCH_PNM      = A.SCH_NM      ");
		sb.append("\n        INNER JOIN WAE_CBRD_DB_USER B           ");
		sb.append("\n           ON B.DB_NM  = A.DB_NM                ");
		sb.append("\n          AND B.SCH_NM = A.SCH_NM               ");
		sb.append("\n        INNER JOIN WAE_CBRD_DB_ATTRIBUTE C      ");
		sb.append("\n           ON C.DB_NM      = A.DB_NM            ");
		sb.append("\n          AND C.SCH_NM     = A.SCH_NM           ");
		sb.append("\n          AND C.CLASS_NAME = A.CLASS_NAME       ");
		sb.append("\n          AND C.ATTR_NAME  = A.KEY_ATTR_NAME    ");
		sb.append("\n  WHERE A.DB_NM = '" + dbName + "'              ");

		return setExecuteUpdate_Org(sb.toString());

	}
	
	public void deleteWat(String tdbid) throws Exception {
		int result = 0;		

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAT_DBC_TBL ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+tdbid+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_TBL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_COL ");
		strSQL.append("\n   WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n         JOIN WAA_DB_SCH B ");
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n          AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n   ) ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_IDX ");
		strSQL.append("\n   WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n         JOIN WAA_DB_SCH B ");
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n          AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n   ) ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_IDX : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_IDX_COL ");
		strSQL.append("\n   WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n         JOIN WAA_DB_SCH B ");
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n          AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n   ) ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_IDX_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_CND ");
		strSQL.append("\n   WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n         JOIN WAA_DB_SCH B ");
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n          AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n   ) ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_CND : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_CND_COL ");
		strSQL.append("\n   WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n         JOIN WAA_DB_SCH B ");
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n          AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n   ) ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_CND_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAT_DBC_TBL_SPAC ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+tdbid+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAT_DBC_TBL_SPAC : " + result);
		
		 //DEPENDENCIES
	    strSQL.setLength(0);
	    strSQL.append("\n  DELETE FROM WAT_DBC_DEPENDENCIES ");
	    strSQL.append("\n   WHERE DB_CONN_TRG_ID = '" + tdbid + "' ");
	    
	    result = setExecuteUpdate_Org(strSQL.toString());
	    logger.debug("delete WAT_DBC_DEPENDENCIES : " + result);
		
		 //시퀀스
	    strSQL.setLength(0);
	    strSQL.append("\n  DELETE FROM WAT_DBC_SEQ ");
	    strSQL.append("\n   WHERE DB_CONN_TRG_ID = '" + tdbid + "' ");
	    
	    result = setExecuteUpdate_Org(strSQL.toString());
	    logger.debug("delete WAT_DBC_SEQ : " + result);


	}

}
