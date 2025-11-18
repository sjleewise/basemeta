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
package kr.wise.executor.exceute.schema.dbms.cubrid.impl;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.cubrid.CollectorCubridService;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorMysql.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorCubrid9Impl extends CollectorCubridImpl implements CollectorCubridService   { 

	private static final Logger logger = Logger.getLogger(CollectorCubrid9Impl.class);

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


	public CollectorCubrid9Impl() {

	}

	/** insomnia */
	public CollectorCubrid9Impl(Connection source, Connection target, List<TargetDbmsDM> targetDbmsDM) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = targetDbmsDM; 

//		this.schemaName = targetDb.getDbc_owner_nm();
//		this.dbName = targetDb.getDbms_enm();

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

			//논리명컬럼(코멘트) 테이블 여부 확인 (2021.12) - 추가 ------------------------
			boolean commYn = check_cubSchemaComment();
			logger.debug("코멘트여부 : "+commYn);
			if(commYn){//코멘트테이블 있음
				//DB_CLASS -  comment Y -------------------------------------------------------
				cnt = insertDB_CLASS_CMMT_Y();
				logger.debug(sp + (++p) + ". insertDB_CLASS_CMMT_Y " + cnt + " OK!!");
				
				//DB_ATTRIBUTE -  comment Y  ------------------------------------------------
				cnt = insertDB_ATTRIBUTE_CMMT_Y();
				logger.debug(sp + (++p) + ". insertDB_ATTRIBUTE_CMMT_Y " + cnt + " OK!!");
				
			}
			else{//코멘트테이블 없음
				//DB_CLASS -  comment N -------------------------------------------------------
				cnt = insertDB_CLASS();
				logger.debug(sp + (++p) + ". insertDB_CLASS " + cnt + " OK!!");
				
				//DB_ATTRIBUTE -  comment N  ------------------------------------------------
				cnt = insertDB_ATTRIBUTE();
				logger.debug(sp + (++p) + ". insertDB_ATTRIBUTE " + cnt + " OK!!");
			}
			
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
	
	//코멘트 테이블 존재여부 확인
	private boolean check_cubSchemaComment() throws SQLException {
		boolean result;
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  SELECT 1 ");
		strSQL.append("\n  FROM DB_CLASS ");
		strSQL.append("\n  WHERE class_name = '_cub_schema_comments' ");
		
		
		logger.debug(strSQL);
		getResultSet(strSQL.toString());
		
		if(rs.next()) {
			result = true;
		}
		else{
			result = false;
		}
		
/*		pst_tgt = null;

		pst_tgt = con_tgt.prepareStatement(strSQL.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		result = pst_tgt.execute();
		
		if(pst_tgt != null) pst_tgt.close();*/
		
		if(pst_tgt != null) pst_tgt.close();
		if(rs != null) rs.close();
		
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
	    //sb.append("\n      , COMMENT                      ");
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
        sb.append("\n  , \"DB_TRIGGER\"                 "); //10   TRIGGERS -> DB_TRIGGER         
        //sb.append("\n  , \"COMMENT\"              ");         
		sb.append("\n ) VALUES (                  ");
		sb.append("\n    ?, ?, ?, ?, ?            ");
		sb.append("\n  , ?, ?, ?                  ");
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
				pst_org.setString(rsGetCnt++, rs.getString("TRIGGERS"));
				//pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));

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
	
////////////////////////////////////////////////////////
	
	/**
	 * DBC DB_CLASS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDB_CLASS_CMMT_Y() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      , CLASS_NAME                      ");
        sb.append("\n      , OWNER_NAME                      ");
        sb.append("\n      , CLASS_TYPE                      ");
        sb.append("\n      , IS_SYSTEM_CLASS                 ");
        sb.append("\n      , PARTITIONED                     ");
        sb.append("\n      , IS_REUSE_OID_CLASS              ");
        //sb.append("\n      , COLLATION                       ");
        sb.append("\n      , B.DESCRIPTION AS COMMENT                         ");
		sb.append("\n   FROM DB_CLASS A                      ");
		sb.append("\n   			LEFT JOIN _cub_schema_comments B               ");
		sb.append("\n   			ON B.TABLE_NAME = A.CLASS_NAME               ");
		sb.append("\n   		 AND B.COLUMN_NAME = '*'               ");
		sb.append("\n  WHERE A.OWNER_NAME = '").append(schemaName).append("'");

		System.out.println("insertDB_CLASS : \n"+sb);
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
        //sb.append("\n      , COLLATION                  ");
        sb.append("\n      , \"COMMENT\"                "); //10
		sb.append("\n ) VALUES (                        ");
		sb.append("\n     ?, ?, ?, ?, ?                 ");
		sb.append("\n   , ?, ?, ?, ?                       ");		
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
				//pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
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
	private int insertDB_ATTRIBUTE_CMMT_Y() throws SQLException
	{
		StringBuffer sb = new StringBuffer();


		sb.append("\n SELECT '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      , ATTR_NAME             ");
        sb.append("\n      , CLASS_NAME            ");
        sb.append("\n      , ATTR_TYPE             ");
        sb.append("\n      , DEF_ORDER+1  AS DEF_ORDER           ");		//(2021.12) 추가 - 큐브리드는 순서가 0부터 시작하므로 +1을 하여 수집
        sb.append("\n      , FROM_CLASS_NAME       ");
        sb.append("\n      , FROM_ATTR_NAME        ");
        sb.append("\n      , DECODE(DATA_TYPE,'STRING','VARCHAR',DATA_TYPE) AS DATA_TYPE             ");
        sb.append("\n      , CASE WHEN DATA_TYPE = 'DATETIME' THEN NULL                  ");
        sb.append("\n       		    WHEN DATA_TYPE = 'INTEGER' THEN NULL");
        sb.append("\n       		    WHEN DATA_TYPE = 'BIGINT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'DATE' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'TIMESTAMP' THEN NULL");
        sb.append("\n       		    WHEN DATA_TYPE = 'SHORT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'FLOAT' THEN NULL");
        sb.append("\n       		    ELSE PREC");
        sb.append("\n        END AS PREC");
        sb.append("\n      , CASE WHEN DATA_TYPE = 'DATETIME' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'INTEGER' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'BIGINT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'DATE' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'TIMESTAMP' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'SHORT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'FLOAT' THEN NULL");
        sb.append("\n       		    ELSE DECODE(SCALE,0,NULL,SCALE)");
        sb.append("\n        END AS SCALE");
        //sb.append("\n      , CHARSET               ");
        //sb.append("\n      , COLLATION             ");
        sb.append("\n      , DOMAIN_CLASS_NAME     ");
        sb.append("\n      , DEFAULT_VALUE         ");
        sb.append("\n      , IS_NULLABLE           ");
        sb.append("\n      , B.DESCRIPTION AS COMMENT                         ");
		sb.append("\n   FROM DB_ATTRIBUTE A        ");
		sb.append("\n   			LEFT JOIN _cub_schema_comments B               ");
		sb.append("\n   			ON B.TABLE_NAME = A.CLASS_NAME               ");
		sb.append("\n   		 AND B.COLUMN_NAME = A.ATTR_NAME               ");

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
        //sb.append("\n      , CHARSET                   ");
        //sb.append("\n      , COLLATION                 ");
        sb.append("\n      , DOMAIN_CLASS_NAME         ");
        sb.append("\n      , DEFAULT_VALUE             "); //15
        sb.append("\n      , IS_NULLABLE               ");
        sb.append("\n      , \"COMMENT\"               ");
		sb.append("\n  ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?, ?                   ");  		
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
				//pst_org.setString(rsGetCnt++, rs.getString("CHARSET"));
				//pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
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
	
	
///////////////////////////////////////////////////////
	
	
	
	
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
        //sb.append("\n      , COLLATION                       ");
        //sb.append("\n      , COMMENT                         ");
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
        //sb.append("\n      , COLLATION                  ");
        //sb.append("\n      , \"COMMENT\"                "); //10
		sb.append("\n ) VALUES (                        ");
		sb.append("\n     ?, ?, ?, ?, ?                 ");
		sb.append("\n   , ?, ?, ?                       ");		
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
				//pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				//pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));
				
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
        sb.append("\n      , DEF_ORDER+1  AS DEF_ORDER           ");		//(2021.12) 추가 - 큐브리드는 순서가 0부터 시작하므로 +1을 하여 수집
        sb.append("\n      , FROM_CLASS_NAME       ");
        sb.append("\n      , FROM_ATTR_NAME        ");
        sb.append("\n      , DECODE(DATA_TYPE,'STRING','VARCHAR',DATA_TYPE) AS DATA_TYPE             ");
        sb.append("\n      , CASE WHEN DATA_TYPE = 'DATETIME' THEN NULL                  ");
        sb.append("\n       		    WHEN DATA_TYPE = 'INTEGER' THEN NULL");
        sb.append("\n       		    WHEN DATA_TYPE = 'BIGINT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'DATE' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'TIMESTAMP' THEN NULL");
        sb.append("\n       		    WHEN DATA_TYPE = 'SHORT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'FLOAT' THEN NULL");
        sb.append("\n       		    ELSE PREC");
        sb.append("\n        END AS PREC");
        sb.append("\n      , CASE WHEN DATA_TYPE = 'DATETIME' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'INTEGER' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'BIGINT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'DATE' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'TIMESTAMP' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'SHORT' THEN NULL ");
        sb.append("\n       		    WHEN DATA_TYPE = 'FLOAT' THEN NULL");
        sb.append("\n       		    ELSE DECODE(SCALE,0,NULL,SCALE)");
        sb.append("\n        END AS SCALE");
        //sb.append("\n      , CHARSET               ");
        //sb.append("\n      , COLLATION             ");
        sb.append("\n      , DOMAIN_CLASS_NAME     ");
        sb.append("\n      , DEFAULT_VALUE         ");
        sb.append("\n      , IS_NULLABLE           ");
        //sb.append("\n      , COMMENT               ");
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
        //sb.append("\n      , CHARSET                   ");
        //sb.append("\n      , COLLATION                 ");
        sb.append("\n      , DOMAIN_CLASS_NAME         ");
        sb.append("\n      , DEFAULT_VALUE             "); //15
        sb.append("\n      , IS_NULLABLE               ");
        //sb.append("\n      , \"COMMENT\"               ");
		sb.append("\n  ) VALUES (                      ");
		sb.append("\n    ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?, ?                 ");
		sb.append("\n  , ?, ?, ?, ?                    ");  		
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
				//pst_org.setString(rsGetCnt++, rs.getString("CHARSET"));
				//pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setString(rsGetCnt++, rs.getString("DOMAIN_CLASS_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_VALUE"));
				pst_org.setString(rsGetCnt++, rs.getString("IS_NULLABLE"));
				//pst_org.setString(rsGetCnt++, rs.getString("COMMENT")); 

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
        //sb.append("\n      , FILTER_EXPRESSION  ");		(2021.12) 추가 - 9버전엔 없는 컬럼 주석처리
       // sb.append("\n      , HAVE_FUNCTION      ");
        //sb.append("\n      , COMMENT            ");
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
        //sb.append("\n      , FILTER_EXPRESSION  "); //10 
        //sb.append("\n      , HAVE_FUNCTION      ");
        //sb.append("\n      , \"COMMENT\"        ");
		sb.append("\n ) VALUES (                ");
		sb.append("\n    ?, ?, ?, ?, ?          ");
		sb.append("\n  , ?, ?, ?          ");
		sb.append("\n  , ?                      ");		
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
				//pst_org.setString(rsGetCnt++, rs.getString("FILTER_EXPRESSION"));
				//pst_org.setString(rsGetCnt++, rs.getString("HAVE_FUNCTION"));
				//pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));

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
        //sb.append("\n      , FUNC               ");			(2021.12) 추가 - 9버전엔 없는 컬럼 주석처리
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
       // sb.append("\n      , FUNC                          ");        (2021.12) 추가 - 9버전엔 없는 컬럼 주석처리
		sb.append("\n ) VALUES (                           ");
		sb.append("\n    ?, ?, ?, ?, ?                     ");
		sb.append("\n  , ?, ?, ?                        ");		
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
				//pst_org.setString(rsGetCnt++, rs.getString("FUNC")); (2021.12) 추가 - 9버전엔 없는 컬럼 주석처리

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
	 * 타겟데이터의 SQL을 받아 저장한다.
	 * @param String 저장 SQL
	 * @return boolean 저장결과값
	 * @throws SQLException
	 * (2021.10) pst_org 과 con_org에서 NPE가 떠서 가져옴
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
	 * DBC DBA_DATABASES 저장 : WAA_MEAT_DBMS 정보 이용
	 * powered by javala 2008.1.8
	 * @return int 수행결과 건수
	 * @throws SQLException
	 * (2021.10)변수문제때문에 가져옴
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

		return setExecuteUpdate_Org(sb.toString());
	}
	
	/**
	 * 타겟데이터의 SQL을 받아 CResultSet을 넘겨준다.
	 * @param String 조회 SQL
	 * @return CResultSet Query 결과값
	 * @throws SQLException
	 * (2021.10) 가져옴
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
	 * 저장데이터의 SQL을 받아 저장여부를 넘겨준다.
	 * @param String 저장 SQL
	 * @param String 저장 내용 목록
	 * @return int 수행결과 건수
	 * @throws SQLException
	 *  (2021.10) 가져옴
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
	
	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 * (2021.10) 가져옴
	 */
	protected int[] executeBatch() throws SQLException 
	{
		return executeBatch(false);
	}

	/**
	 * PreparedStatement를 executeBatch하고 닫는다.
	 * @return int[] executeBatch 결과값
	 * @throws SQLException
	 * (2021.10) 가져옴
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

	
	
	

}
