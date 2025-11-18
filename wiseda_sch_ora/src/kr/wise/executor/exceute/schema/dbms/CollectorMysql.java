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
import java.util.List;

import kr.wise.executor.dm.TargetDbmsDM;
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
public class CollectorMysql {

	private static final Logger logger = Logger.getLogger(CollectorMysql.class);

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


	public CollectorMysql() {

	}

	/** insomnia */
	public CollectorMysql(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
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

				con_org.commit();
			}

			dbCnt++;
			
			//Change DB ---------------------------------------------------
			cnt = changeUseDatabase();
			logger.debug(sp + (++p) + ". changeUseDatabase [infomation_schema] OK!!");

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

			//DBA_INDEX ---------------------------------------------------
			cnt = insertDBA_INDEX();
			logger.debug(sp + (++p) + ". insertDBA_INDEX " + cnt + " OK!!");
			
			//DBA_VIEWS ---------------------------------------------------
//			cnt = insertDBA_VIEWS();
//			logger.debug(sp + (++p) + ". insertDBA_VIEWS " + cnt + " OK!!");

			con_org.commit();

		}

		result = true;

		return result;

	}

	private int changeUseDatabase() throws SQLException
	{
		String query_tgt = "";

		query_tgt = "\n use information_schema ";

		pst_tgt = con_tgt.prepareStatement(query_tgt);

		return pst_tgt.executeUpdate();
	}

	/**
	 * DBC DBA_VIEWS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_VIEWS() throws SQLException, Exception
	{
		int cnt = 0;
		int resultCnt = 0;
		int iObjId = 0;
		int iTextLen = 0;
		String strText = null;
		String clobSql = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
	    Writer writer = null;
	    Reader src = null;

	    try{
			StringBuffer sb = new StringBuffer();
			sb.append("\n SELECT                "); // 데이터베이스명
			sb.append("\n      '").append(dbName).append("' AS OF_SYSTEM");
			sb.append("\n      ,A.TABLE_CATALOG               ");
			sb.append("\n      ,A.TABLE_SCHEMA               ");
			sb.append("\n      ,A.TABLE_NAME          ");
			sb.append("\n      ,A.TABLE_TYPE             ");
			sb.append("\n      ,A.ENGINE                 ");
			sb.append("\n      ,A.VERSION                 ");
			sb.append("\n      ,A.ROW_FORMAT                 ");
			sb.append("\n      ,A.TABLE_ROWS                ");
			sb.append("\n      ,A.AVG_ROW_LENGTH                ");
			sb.append("\n      ,A.DATA_LENGTH           ");
			sb.append("\n      ,A.MAX_DATA_LENGTH              ");
			sb.append("\n      ,A.INDEX_LENGTH              ");
			sb.append("\n      ,A.DATA_FREE              ");
			sb.append("\n      ,A.AUTO_INCREMENT             ");
			sb.append("\n      ,A.CREATE_TIME                ");
			sb.append("\n      ,A.UPDATE_TIME          ");
			sb.append("\n      ,A.CHECK_TIME                  ");
			sb.append("\n      ,A.TABLE_COLLATION                ");
			sb.append("\n      ,A.CHECKSUM                 ");
			sb.append("\n      ,A.CREATE_OPTIONS                   ");
			sb.append("\n      ,A.TABLE_COMMENT             ");
			sb.append("\n   FROM TABLES A                      ");
			sb.append("\n WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
			sb.append("\n   AND A.TABLE_TYPE = 'VIEW'");
			
			getResultSet(sb.toString());
	
			sb = new StringBuffer();
	
			if( rs != null ) {
				sb = new StringBuffer();
				sb.append("\n INSERT INTO WAE_MYSQL_VIEW(    ");
				sb.append("\n  DB_NM                      ");
				sb.append("\n ,SCH_NM                      ");
				sb.append("\n ,VIEW_NM                     ");
				sb.append("\n ,OBJECT_ID                   ");
				sb.append("\n ,TEXT_LENGTH                 ");
				sb.append("\n ,TEXT                        ");
				sb.append("\n ,TYPE_TEXT_LENGTH            ");
				sb.append("\n ,TYPE_TEXT                   ");
				sb.append("\n ,OID_TEXT                    ");
				sb.append("\n ,VIEW_TYPE_OWNER             ");
				sb.append("\n ,SUPERVIEW_NAME              ");
				sb.append("\n )VALUES(                     ");
				sb.append("\n ?,?,?,?,?,empty_clob(),?,?,?,?,         ");
				sb.append("\n ?                            ");
				sb.append("\n )                            ");
	
				int rsCnt = 0;
	
				while( rs.next() ) {
	
					iObjId = rs.getInt("OBJECT_ID");
					strText = rs.getString("TEXT");
					iTextLen = rs.getInt("TEXT_LENGTH");
	
					try {
	
					con_org.setAutoCommit(false);
	
	
					pstmt = con_org.prepareStatement(sb.toString());
	
					rsCnt = 1;
					pstmt.setString(rsCnt++, rs.getString("OF_SYSTEM"));
					pstmt.setString(rsCnt++, rs.getString("OWNER"));
					pstmt.setString(rsCnt++, rs.getString("VIEW_NAME"));
					pstmt.setInt   (rsCnt++, iObjId);
					pstmt.setInt   (rsCnt++, rs.getInt("TEXT_LENGTH"));
					pstmt.setInt   (rsCnt++, rs.getInt("TYPE_TEXT_LENGTH"));
					pstmt.setString(rsCnt++, rs.getString("TYPE_TEXT"));
					//pstmt.setInt   (rsCnt++, rs.getInt("OID_TEXT_LENGTH"));
					pstmt.setString(rsCnt++, rs.getString("OID_TEXT"));
					pstmt.setString(rsCnt++, rs.getString("VIEW_TYPE_OWNER"));
	//				pstmt.setString(rsCnt++, rs.getString("VIEW_TYPE"));
					pstmt.setString(rsCnt++, rs.getString("SUPERVIEW_NAME"));
	
	
					cnt = pstmt.executeUpdate();
					pstmt.close();
	
					//clob 처리
					if( cnt > 0 ) {
						resultCnt++;
	
						clobSql = " SELECT TEXT FROM WAE_ORA_VIEW WHERE OBJECT_ID = ? FOR UPDATE " ;
	
						pstmt = con_org.prepareStatement(clobSql);
						pstmt.clearParameters();
						pstmt.setInt(1, iObjId);
	
						resultSet = pstmt.executeQuery();
	
						try {
							if(resultSet.next()){
								//String viewDef = CLOB에 들어가는 String;
								CLOB clob = (CLOB)resultSet.getObject("TEXT");
								writer = clob.getCharacterOutputStream();
								src = new CharArrayReader(strText.toCharArray());
	
								char[] buffer = new char[iTextLen];
								int read = 0;
	
								while ( (read = src.read(buffer,0,iTextLen)) != -1) {
									writer.write(buffer, 0, read); // 여기가 CLOB으로 입력되는 곳입니다.
								}
	
								src.close();
								writer.close();
							}
	
						} catch(IOException e) {
							throw new Exception(e);
				      	}
	
						resultSet.close();
						pstmt.close();
					}
					con_org.commit();
	
					} catch (Exception e) {
	
						con_org.rollback();
				        e.printStackTrace();
	
				    } finally {
	
				    }
	
				}
			} // end of if

		} catch(Exception e) {
			e.printStackTrace();
		} finally{
		    
		    if(pst_tgt != null) pst_tgt.close();
		    if(pst_org != null) pst_org.close();
		    if(rs != null) rs.close();
		}



		return resultCnt;
	}
	


	/**
	 * DBC DBA_IND_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_INDEX() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      ,INDEX_SCHEMA                 ");
		sb.append("\n      ,TABLE_CATALOG               ");
		sb.append("\n      ,TABLE_SCHEMA          ");
		sb.append("\n      ,TABLE_NAME          ");
		sb.append("\n      ,NON_UNIQUE             ");
		sb.append("\n      ,INDEX_SCHEMA                 ");
		sb.append("\n      ,INDEX_NAME                 ");
		sb.append("\n      ,SEQ_IN_INDEX                 ");
		sb.append("\n      ,COLUMN_NAME                ");
		sb.append("\n      ,COLLATION                ");
		sb.append("\n      ,CARDINALITY           ");
		sb.append("\n      ,SUB_PART              ");
		sb.append("\n      ,PACKED              ");
		sb.append("\n      ,NULLABLE              ");
		sb.append("\n      ,INDEX_TYPE             ");
		sb.append("\n      ,COMMENT                ");
		sb.append("\n      ,INDEX_COMMENT          ");
		sb.append("\n   FROM STATISTICS             ");
		sb.append("\n WHERE INDEX_SCHEMA = '").append(schemaName).append("'");

		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_MYSQL_INDEX(");
		sb.append("\n   	DB_NM                  ");
		sb.append("\n  	   ,SCH_NM                   ");
		sb.append("\n      ,TABLE_CATALOG               ");
		sb.append("\n      ,TABLE_SCHEMA          ");
		sb.append("\n      ,TABLE_NAME          ");
		sb.append("\n      ,NON_UNIQUE             ");
		sb.append("\n      ,INDEX_SCHEMA                 ");
		sb.append("\n      ,INDEX_NAME                 ");
		sb.append("\n      ,SEQ_IN_INDEX                 ");
		sb.append("\n      ,COLUMN_NAME                ");
		sb.append("\n      ,COLLATION                ");
		sb.append("\n      ,CARDINALITY           ");
		sb.append("\n      ,SUB_PART              ");
		sb.append("\n      ,PACKED              ");
		sb.append("\n      ,NULLABLE              ");
		sb.append("\n      ,INDEX_TYPE             ");
		sb.append("\n      ,CMMT                ");
		sb.append("\n      ,INDEX_COMMENT          ");
		sb.append("\n) VALUES (                      ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?           ");
		sb.append("\n  ,?,?,?,?,?,?,?,?		           ");
		sb.append("\n)                               ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_CATALOG"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("NON_UNIQUE"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_SCHEMA"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_NAME"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SEQ_IN_INDEX"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CARDINALITY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SUB_PART"));
				pst_org.setString(rsGetCnt++, rs.getString("PACKED"));
				pst_org.setString(rsGetCnt++, rs.getString("NULLABLE"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("COMMENT"));
				pst_org.setString(rsGetCnt++, rs.getString("INDEX_COMMENT"));

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
	 * DBC DBA_TABLES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TABLES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT                "); // 데이터베이스명
		sb.append("\n      '").append(dbName).append("' AS OF_SYSTEM");
		sb.append("\n      ,A.TABLE_CATALOG               ");
		sb.append("\n      ,A.TABLE_SCHEMA               ");
		sb.append("\n      ,A.TABLE_NAME          ");
		sb.append("\n      ,A.TABLE_TYPE             ");
		sb.append("\n      ,A.ENGINE                 ");
		sb.append("\n      ,A.VERSION                 ");
		sb.append("\n      ,A.ROW_FORMAT                 ");
		sb.append("\n      ,A.TABLE_ROWS                ");
		sb.append("\n      ,A.AVG_ROW_LENGTH                ");
		sb.append("\n      ,A.DATA_LENGTH           ");
		sb.append("\n      ,A.MAX_DATA_LENGTH              ");
		sb.append("\n      ,A.INDEX_LENGTH              ");
		sb.append("\n      ,A.DATA_FREE              ");
		sb.append("\n      ,A.AUTO_INCREMENT             ");
		sb.append("\n      ,A.CREATE_TIME                ");
		sb.append("\n      ,A.UPDATE_TIME          ");
		sb.append("\n      ,A.CHECK_TIME                  ");
		sb.append("\n      ,A.TABLE_COLLATION                ");
		sb.append("\n      ,A.CHECKSUM                 ");
		sb.append("\n      ,A.CREATE_OPTIONS                   ");
		sb.append("\n      ,A.TABLE_COMMENT             ");
		sb.append("\n   FROM TABLES A                      ");
		sb.append("\n WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
		sb.append("\n   AND A.TABLE_TYPE = 'BASE TABLE'");

		logger.debug("insertDBA_TABLES : \n"+sb);
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_MYSQL_TBL (    ");
		sb.append("\n      DB_NM                     "); // 테이블스페이스
		sb.append("\n      , SCH_NM                     "); // 테이블스페이스
		sb.append("\n      ,TABLE_CATALOG               ");
		sb.append("\n      ,TABLE_SCHEMA               ");
		sb.append("\n      ,TABLE_NAME          ");
		sb.append("\n      ,TABLE_TYPE             ");
		sb.append("\n      ,ENGINE                 ");
		sb.append("\n      ,VERSION                 ");
		sb.append("\n      ,ROW_FORMAT                 ");
		sb.append("\n      ,TABLE_ROWS                ");
		sb.append("\n      ,AVG_ROW_LENGTH                ");
		sb.append("\n      ,DATA_LENGTH           ");
		sb.append("\n      ,MAX_DATA_LENGTH              ");
		sb.append("\n      ,INDEX_LENGTH              ");
		sb.append("\n      ,DATA_FREE              ");
		sb.append("\n      ,AUTO_INCREMENT             ");
		sb.append("\n      ,CREATE_TIME                ");
		sb.append("\n      ,UPDATE_TIME          ");
		sb.append("\n      ,CHECK_TIME                  ");
		sb.append("\n      ,TABLE_COLLATION                ");
		sb.append("\n      ,CHECKSUM                 ");
		sb.append("\n      ,CREATE_OPTIONS                   ");
		sb.append("\n      ,TABLE_COMMENT             ");
		sb.append("\n) VALUES (                              ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                  ");
		sb.append("\n  ?,?,?                         		");
		sb.append("\n)                                       ");
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
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
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
		sb.append("\n SELECT                               ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE");
		sb.append("\n      ,SCHEMA_NAME AS SCHEMA_NAME                ");
		sb.append("\n      ,CATALOG_NAME                         ");
		sb.append("\n      ,DEFAULT_CHARACTER_SET_NAME ");
		sb.append("\n      ,DEFAULT_COLLATION_NAME     ");
		sb.append("\n      ,SQL_PATH                   ");
		sb.append("\n  FROM SCHEMATA                      ");
		sb.append("\n WHERE SCHEMA_NAME = '").append(schemaName).append("'");
//logger.debug(sb.toString());
		
		logger.debug("insertDBA_USERS : \n"+sb);
		getResultSet(sb.toString());
		sb.setLength(0);

		sb.append("\nINSERT INTO WAE_MYSQL_SCH (       ");
		sb.append("\n   DB_NM                       ");
		sb.append("\n  ,SCH_NM                         ");
		sb.append("\n  ,DB_SCH_ID						");
		sb.append("\n  ,CATALOG_NAME                   ");
		sb.append("\n  ,DEFAULT_CHARACTER_SET_NAME     ");
		sb.append("\n  ,DEFAULT_COLLATION_NAME         ");
		sb.append("\n  ,SQL_PATH                       ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?,?,?,?            ");
		sb.append("\n)                                 ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("SCHEMA_NAME"));
				pst_org.setString(rsGetCnt++, schemaId);
				pst_org.setString(rsGetCnt++, rs.getString("CATALOG_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_CHARACTER_SET_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("DEFAULT_COLLATION_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("SQL_PATH"));

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
	private int insertMetaDATABASES() throws SQLException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MYSQL_DB (");
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

	/**  insomnia */
	private int insertSchema() {
		int result = 0;


		return result;

	}

	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MYSQL_DB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_DB : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MYSQL_SCH ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_SCH : " + result);


		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MYSQL_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MYSQL_TBL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_TBL : " + result);
		
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MYSQL_INDEX ");
		strSQL.append("\n   WHERE DB_NM = '"+dbName+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MYSQL_INDEX : " + result);
		
		



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

		return result;
	}

	/**  insomnia
	 * @throws Exception */
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
		sb.append("\n   FROM WAE_MYSQL_TBL A                                                ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MYSQL_DB B                                              ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TABLE_NAME, COUNT(*) AS COL_CNT  ");
		sb.append("\n                      FROM WAE_MYSQL_COL                                 ");
		sb.append("\n                     GROUP BY DB_NM, SCH_NM, TABLE_NAME                    ");
		sb.append("\n                   ) E                                                 ");
		sb.append("\n     ON A.DB_NM = E.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                            ");
		sb.append("\n    AND A.TABLE_NAME = E.TABLE_NAME                                            ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                        ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
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
		sb.append("\n   FROM WAE_MYSQL_COL A                                       ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MYSQL_DB B                                     ");
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

	/**  insomnia
	 * @throws Exception */
	private int insertwatidx() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX                                     ");
		sb.append("\n SELECT S.DB_SCH_ID                                                    ");
		sb.append("\n      , A.TABLE_NAME                                                                  ");
		sb.append("\n      , A.INDEX_NAME                                                                  ");
		sb.append("\n      , S.DB_CONN_TRG_ID                                                          ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , '1'                                                                       ");
		sb.append("\n      , NULL                                                                       ");
		sb.append("\n      , SYSDATE                                                                   ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                             ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , ''                                                                        ");
		sb.append("\n      , CASE WHEN A.INDEX_NAME = 'PRIMARY' THEN 'Y' ELSE 'N' END PK_YN                   ");
		sb.append("\n      , CASE WHEN A.NON_UNIQUE = 0 THEN 'Y' ELSE 'N' END UQ_YN                    ");
		sb.append("\n      , COUNT(*) AS COL_CNT                                                                 ");
		sb.append("\n      , '' AS IDX_SIZE                                                       ");
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
		sb.append("\n   FROM WAE_MYSQL_INDEX A                                                             ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MYSQL_DB B                                                         ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                                         ");
		
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                               ");
		sb.append("\n   GROUP BY S.DB_SCH_ID, A.TABLE_NAME, A.INDEX_NAME, S.DB_CONN_TRG_ID,A.NON_UNIQUE    ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT S.DB_SCH_ID      ");
		sb.append("\n      , A.TABLE_NAME                    ");
		sb.append("\n      , A.COLUMN_NAME                    ");
		sb.append("\n      , A.INDEX_NAME                    ");
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
		sb.append("\n      , SEQ_IN_INDEX                 ");
		sb.append("\n      , ''                   ");
		sb.append("\n      , C.DATA_TYPE                 ");
		sb.append("\n      , C.NUMERIC_PRECISION                 ");
		sb.append("\n      , C.CHARACTER_MAXIMUM_LENGTH                  ");
		sb.append("\n      , C.NUMERIC_SCALE                  ");
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
		sb.append("\n   FROM WAE_MYSQL_INDEX A           ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MYSQL_DB B           ");
		sb.append("\n     ON A.DB_NM = B.DB_NM           ");
		sb.append("\n  INNER JOIN WAE_MYSQL_COL C          ");
		sb.append("\n     ON A.DB_NM = C.DB_NM           ");
		sb.append("\n    AND A.SCH_NM = C.SCH_NM         ");
		sb.append("\n    AND A.TABLE_NAME = C.TABLE_NAME         ");
		sb.append("\n    AND A.COLUMN_NAME = C.COLUMN_NAME         ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}
	
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();


		sb.append("\n SELECT                                             ");
		sb.append("\n      '").append(dbName).append("' AS OF_DATABASE ");
		sb.append("\n      ,A.TABLE_SCHEMA                       ");
		sb.append("\n	   ,A.TABLE_CATALOG                            ");
		sb.append("\n      ,A.TABLE_SCHEMA                       ");
		sb.append("\n      ,A.TABLE_NAME                      ");
		sb.append("\n      ,A.COLUMN_NAME    ");
		sb.append("\n      ,A.ORDINAL_POSITION                    ");
		sb.append("\n      ,A.COLUMN_DEFAULT                  ");
		sb.append("\n      ,A.IS_NULLABLE                         ");
		sb.append("\n      ,A.DATA_TYPE                        ");
		sb.append("\n      ,A.CHARACTER_MAXIMUM_LENGTH                   ");
		sb.append("\n      ,A.CHARACTER_OCTET_LENGTH                     ");
		sb.append("\n      ,A.NUMERIC_PRECISION                     ");
		sb.append("\n      ,A.NUMERIC_SCALE                        ");
//		sb.append("\n      ,A.DATETIME_PRECISION                       ");
		sb.append("\n      ,A.CHARACTER_SET_NAME                          ");
		sb.append("\n      ,A.COLLATION_NAME                          ");
		sb.append("\n      ,A.COLUMN_TYPE                      ");
		sb.append("\n      ,A.COLUMN_KEY                    ");
		sb.append("\n      ,A.EXTRA                      ");
		sb.append("\n      ,A.PRIVILEGES               ");
		sb.append("\n      ,A.COLUMN_COMMENT             ");
		sb.append("\n   FROM COLUMNS A                                            ");
		sb.append("\n  WHERE A.TABLE_SCHEMA = '").append(schemaName).append("'");
		
//		logger.debug("test sql:"+sb.toString());
		logger.debug("insertDBA_TAB_COLUMNS : \n"+sb);
		getResultSet(sb.toString());
		sb = new StringBuffer();

		sb.append("\nINSERT INTO WAE_MYSQL_COL (         ");
		sb.append("\n        DB_NM                        ");
		sb.append("\n        ,SCH_NM                     ");
		sb.append("\n      ,TABLE_CATALOG                                  ");
		sb.append("\n      ,TABLE_SCHEMA                       ");
		sb.append("\n      ,TABLE_NAME                      ");
		sb.append("\n      ,COLUMN_NAME    ");
		sb.append("\n      ,ORDINAL_POSITION                    ");
		sb.append("\n      ,COLUMN_DEFAULT                  ");
		sb.append("\n      ,IS_NULLABLE                         ");
		sb.append("\n      ,DATA_TYPE                        ");
		sb.append("\n      ,CHARACTER_MAXIMUM_LENGTH                   ");
		sb.append("\n      ,CHARACTER_OCTET_LENGTH                     ");
		sb.append("\n      ,NUMERIC_PRECISION                     ");
		sb.append("\n      ,NUMERIC_SCALE                        ");
//		sb.append("\n      ,DATETIME_PRECISION                       ");
		sb.append("\n      ,CHARACTER_SET_NAME                          ");
		sb.append("\n      ,COLLATION_NAME                          ");
		sb.append("\n      ,COLUMN_TYPE                      ");
		sb.append("\n      ,COLUMN_KEY                    ");
		sb.append("\n      ,EXTRA                      ");
		sb.append("\n      ,PRIVILEGES_TEXT               ");
		sb.append("\n      ,COLUMN_COMMENT             ");
		sb.append("\n) VALUES (                                ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                    ");
		sb.append("\n  ?,?,?,?,?,?,?,?,?,?,                    ");
		sb.append("\n  ?			                           ");
		sb.append("\n)                                         ");

		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next())
			{
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, rs.getString("TABLE_SCHEMA"));
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
//				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DATETIME_PRECISION"));
				pst_org.setString(rsGetCnt++, rs.getString("CHARACTER_SET_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLLATION_NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_TYPE"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_KEY"));
				pst_org.setString(rsGetCnt++, rs.getString("EXTRA"));
				pst_org.setString(rsGetCnt++, rs.getString("PRIVILEGES"));
				pst_org.setString(rsGetCnt++, rs.getString("COLUMN_COMMENT"));

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


}
