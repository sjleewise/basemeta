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
package kr.wise.executor.exceute.schema.dbms.mssql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.mssql.CollectorMssqlService;
import org.apache.log4j.Logger;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorMssql.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : moonsungeun
 * 6. 작성일   : 2015. 7. 17. 
 * </PRE>
 */
public class CollectorMssql implements CollectorMssqlService{

	private static final Logger logger = Logger.getLogger(CollectorMssql.class);

	private Connection con_org = null;
	private Connection con_tgt = null;
	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;
	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;
	private String dbName = null;
	private String dbKName = null;
	private String dbId = null;
	private String schemaName = null;
	private String schemaId = null;
	private String src_nm = null;
	private String dbType = null;
	private String connTrgDbLnkChrw = null;
	private int execCnt = 10000;
	private String data_cpct = "";

	public CollectorMssql() {

	}

	public CollectorMssql(Connection source, Connection target,	List<TargetDbmsDM> lsitdm) {
		this.con_org = source;
		this.con_tgt = target;
		this.targetDblist = lsitdm;
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
			this.dbKName = targetDb.getDbms_enm();
			this.dbType = targetDb.getDbms_type_cd();
			this.connTrgDbLnkChrw = targetDb.getConn_trg_db_lnk_chrw();
			this.dbId = targetDb.getDbms_id();

logger.debug("schemaName : " + schemaName);			
logger.debug("schemaId : " + schemaId);
logger.debug("dbName : " + dbName);
logger.debug("dbKName : " + dbKName);
logger.debug("dbType : " + dbType);
logger.debug("connTrgDbLnkChrw : " + connTrgDbLnkChrw);
logger.debug("dbId : " + dbId);

			// DBLINK문자열
			if(!UtilString.null2Blank(connTrgDbLnkChrw).equals("")){
				this.src_nm = connTrgDbLnkChrw + "."+targetDb.getDbc_owner_nm()+".";
			}else{
				this.src_nm = "dbo.";
			}
logger.debug("src_nm : " + src_nm);			
			int p = 0;
			int cnt = 0;

			//첫번째 스키마 일때만 처리한다.
			if(dbCnt == 0) {
				//스키마 정보를 모두 삭제한다.
				deleteSchema();
				
				cnt = insertSYSCOMMENTS();
				logger.debug(sp + (++p) + ". insertSYSCOMMENTS " + cnt + " OK!!");
				
				cnt = insertSYSTYPES();
				logger.debug(sp + (++p) + ". insertSYSTYPES " + cnt + " OK!!");

				cnt = insertSYSFOREIGNKEYS();
				logger.debug(sp + (++p) + ". insertSYSFOREIGNKEYS " + cnt + " OK!!");
				
				cnt = insertSYSCONSTRAINTS();
				logger.debug(sp + (++p) + ". insertSYSCONSTRAINTS " + cnt + " OK!!");

				cnt = insertExtendedProperties();
				logger.debug(sp + (++p) + ". insertExtendedProperties " + cnt + " OK!!");
													  												
				con_org.commit();
			}
			dbCnt++;
			
			data_cpct = updateDataCpct();
			logger.debug(sp + (++p) + ". updateDataCpct " + data_cpct + " OK!!");
			
			cnt = insertSYSOBJECTS();
			logger.debug(sp + (++p) + ". insertSYSOBJECTS " + cnt + " OK!!");
			
			cnt = insertSYSCOLUMNS();
			logger.debug(sp + (++p) + ". insertSYSCOLUMNS " + cnt + " OK!!");
			
			cnt = insertSYSINDEXES();
			logger.debug(sp + (++p) + ". insertSYSINDEXES " + cnt + " OK!!");
			
			cnt = insertSYSINDEXKEYS();
			logger.debug(sp + (++p) + ". insertSYSINDEXKEYS " + cnt + " OK!!");
			
			cnt = insertDBA_COLUMNS_INFO();
			logger.debug(sp + (++p) + ". insertDBA_COLUMNS_INFO " + cnt + " OK!!");

			con_org.commit();
		}
		result = true;
		
		return result;
	}
	
	private int insertSYSINDEXKEYS() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT A.ID, A.INDID, A.COLID, A.KEYNO          ");
		sb.append("\n      , D.NAME AS SCHEMA_NM                      ");
		sb.append("\n   FROM ").append(src_nm).append("SYSINDEXKEYS A ");
		sb.append("\n  INNER JOIN ").append(src_nm).append("SYSINDEXES B            ");
		sb.append("\n          ON B.ID    = A.ID                                    ");
		sb.append("\n         AND B.INDID = A.INDID                                 ");
	    sb.append("\n  INNER JOIN ").append(src_nm).append("SYSOBJECTS C            ");
	    sb.append("\n          ON C.xtype = 'U'                                     ");                              
	    sb.append("\n         AND C.ID = B.ID                                       ");
	    sb.append("\n  INNER JOIN SYS.SCHEMAS D                                     ");
		sb.append("\n          ON D.SCHEMA_ID =  C.UID                              ");
		sb.append("\n  WHERE D.NAME = '"+  schemaName +"'                           ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb.setLength(0);
		sb.append("\n INSERT INTO WAE_MS_INDEXKEYS ( ");
		sb.append("\n        ID, INDID, COLID, KEYNO, DB_NM, SCH_NM, DB_CONN_TRG_ID) ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?)   ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYNO"));
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}

	/**
	 * DBC SYSCONSTRAINTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCONSTRAINTS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT CONSTID, ID, COLID, SPARE1, STATUS ");
		sb.append("\n      , ACTIONS, ERROR ");
		sb.append("\n   FROM ").append(src_nm).append("SYSCONSTRAINTS ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_CONSTRAINTS (");
		sb.append("\n        CONSTID, ID, COLID, SPARE1, STATUS    ");
		sb.append("\n      , ACTIONS, ERROR, DB_NM, SCH_NM, DB_CONN_TRG_ID)    ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next()) {
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CONSTID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("COLID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SPARE1"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ACTIONS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ERROR"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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
		
		return cnt;
	}
	
	/**
	 * DBC extended_properties 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertExtendedProperties() throws SQLException {
		StringBuffer sb = new StringBuffer(); 
		sb.append("\n SELECT CLASS, CLASS_DESC, MAJOR_ID, MINOR_ID, NAME, CONVERT(nvarchar(3000),VALUE) AS VALUE  ");
		sb.append("\n FROM SYS.EXTENDED_PROPERTIES ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_EXTENDED_PROPERTIES (");
		sb.append("\n        CLASS, CLASS_DESC,  MAJOR_ID,  MINOR_ID,  NAME,  VALUE, DB_NM, SCH_NM, DB_CONN_TRG_ID  ) ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();
			
			int rsCnt = 1;
			int rsGetCnt = 1;
			
			while(rs.next()) {	
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CLASS"));
				pst_org.setString(rsGetCnt++, rs.getString("CLASS_DESC"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MAJOR_ID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("MINOR_ID"));
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("VALUE"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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
		
		return cnt;
	}

	/**
	 * DBC SYSINDEXES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSINDEXES() throws SQLException {
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
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 0X800) / 0X800) = '1' THEN 'Y' ELSE 'N' END	AS IS_PK    ");
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 2) / 2) = '1' THEN 'Y' ELSE 'N' END	AS IS_UNIQUE        ");
		sb.append("\n      , CASE WHEN CONVERT(BIT, (A.STATUS & 16) / 16) = '1' THEN 'Y' ELSE 'N' END	AS IS_CLUSTERED ");
		sb.append("\n      , (A.STATUS & 32) AS STATUS32                            ");
		sb.append("\n      , C.NAME        AS SCHEMA_NM                             ");
		sb.append("\n   FROM ").append(src_nm).append("SYSINDEXES  A                ");
		sb.append("\n  INNER JOIN ").append(src_nm).append("SYSOBJECTS B            ");       
	    sb.append("\n          ON B.xtype = 'U'                                     ");           
	    sb.append("\n         AND B.ID = A.ID                                       ");           
	    sb.append("\n  INNER JOIN sys.schemas C                                     ");          
	    sb.append("\n          ON C.schema_id = B.uid                               ");
	    sb.append("\n  WHERE C.NAME = '"+  schemaName +"'                           ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_INDEXES(");
		sb.append("\n        ID, STATUS, FIRST, INDID, ROOT   ");
		sb.append("\n      , MINLEN, KEYCNT, GROUPID, DPAGES, RESERVED     ");
		sb.append("\n      , USED, ROWCNT, ROWMODCTR, RESERVED3, RESERVED4  ");
		sb.append("\n      , XMAXLEN, MAXIROW, ORIGFILLFACTOR, STATVERSION, RESERVED2    ");
		sb.append("\n      , FIRSTIAM, IMPID, LOCKFLAGS, PGMODCTR, KEYS, NAME   ");
		sb.append("\n      , MAXLEN, C_ROWS, IS_PK, IS_UNIQUE, IS_CLUSTERED, STATUS32, DB_NM, SCH_NM, DB_CONN_TRG_ID)             ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?)   ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
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
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}
	
	/**
	 * DBC SYSFOREIGNKEYS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSFOREIGNKEYS() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT CONSTID, FKEYID, RKEYID, FKEY, RKEY, KEYNO   ");
		sb.append("\n   FROM ").append(src_nm).append("SYSFOREIGNKEYS     ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_FOREIGNKEYS ( ");
		sb.append("\n        CONSTID, FKEYID, RKEYID, FKEY, RKEY   ");
		sb.append("\n      , KEYNO, DB_NM, SCH_NM, DB_CONN_TRG_ID)        ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)    ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CONSTID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEYID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FKEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("RKEY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("KEYNO"));
				pst_org.setString(rsGetCnt++, dbKName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}

	/**
	 * DBC SYSTYPES 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSTYPES() throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT REPLACE(NAME, 'sysname', 'nvarchar') AS NAME, XTYPE, STATUS, XUSERTYPE, LENGTH  "); //20190920 sysname이 불가
		sb.append("\n      , XPREC, XSCALE, TDEFAULT, DOMAIN, UID ");
		sb.append("\n      , RESERVED, COLLATIONID, USERTYPE, VARIABLE, ALLOWNULLS   ");
		sb.append("\n      , TYPE, PRINTFMT, PREC, SCALE, COLLATION ");
		sb.append("\n   FROM ").append(src_nm).append("SYSTYPES  ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_TYPES (");
		sb.append("\n        NAME, XTYPE, STATUS, XUSERTYPE, LENGTH      ");
		sb.append("\n      , XPREC, XSCALE, TDEFAULT, DOMAIN, C_UID       ");
		sb.append("\n      , RESERVED, COLLATIONID, USERTYPE, VARIABLE, ALLOWNULLS     ");
		sb.append("\n      , TYPE, PRINTFMT, PREC, SCALE, COLLATION    ");
		sb.append("\n      , DB_NM, SCH_NM, DB_CONN_TRG_ID)                   ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n      , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?    ");
		sb.append("\n      , ?, ?, ?)                        ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
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
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}
	
	/**
	 * DBC SYSCOLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCOLUMNS() throws SQLException {
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
		sb.append("\n      , CONVERT(INT,                                              ");
		sb.append("\n         CASE                                                     ");
		sb.append("\n           WHEN TYPE_NAME(A.TYPE) IN ('NUMERIC', 'DECIMAL')       ");
		sb.append("\n             THEN ODBCPREC(A.TYPE, A.LENGTH, A.PREC) + 2          ");
		sb.append("\n           ELSE LENGTH                                            ");
		sb.append("\n         END ) AS DATA_LENGTH                                     ");
		sb.append("\n      , CONVERT(SMALLINT, ODBCSCALE(A.TYPE, A.SCALE)) AS DATA_SCALE ");
		sb.append("\n      , C.NAME AS   SCHEMA_NM                                       ");
		sb.append("\n  FROM ").append(src_nm).append("SYSCOLUMNS A                       ");
		sb.append("\n INNER JOIN ").append(src_nm).append("SYSOBJECTS B                  ");
		sb.append("\n         ON B.xtype = 'U'                                           ");
	    sb.append("\n        AND B.ID = A.ID                                             ");
	    sb.append("\n INNER JOIN sys.schemas C                                           ");
		sb.append("\n         ON C.schema_id = B.uid                                     ");
		sb.append("\n WHERE C.NAME = '"+  schemaName +"'                                 ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\nINSERT INTO WAE_MS_COLUMNS (                             ");
		sb.append("\n         NAME, ID, XTYPE, TYPESTAT, XUSERTYPE            ");
		sb.append("\n       , LENGTH, XPREC, XSCALE, COLID, XOFFSET           ");
		sb.append("\n       , BITPOS, RESERVED, COLSTAT, CDEFAULT, DOMAIN     ");
		sb.append("\n       , C_NUMBER, COLORDER                              ");
		sb.append("\n       , OFFSET, COLLATIONID                             ");
		sb.append("\n       , LANGUAGE, STATUS, TYPE, USERTYPE, PRINTFMT      ");
		sb.append("\n       , PREC, SCALE, ISCOMPUTED, ISOUTPARAM, ISNULLABLE ");
		sb.append("\n       , COLLATION, TDSCOLLATION, DB_NM, SCH_NM, DB_CONN_TRG_ID)         ");
		sb.append("\n  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?                   ");
		sb.append("\n       , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                    ");
		sb.append("\n       , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                    ");
		sb.append("\n       , ?, ?, ?, ?)                                        ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
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
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}

	/**
	 * DBC SYSCOMMENTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSCOMMENTS() throws SQLException {
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
		sb.append("\n  FROM ").append(src_nm).append("SYSCOMMENTS");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_COMMENTS (         ");
		sb.append("\n        ID, C_NUMBER, COLID, STATUS    ");
		sb.append("\n      , TEXTTYPE, LANGUAGE, ENCRYPTED, COMPRESSED, TEXT ");
		sb.append("\n      , DB_NM, SCH_NM, DB_CONN_TRG_ID  )               ");
		sb.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		sb.append("\n      , ?, ?)                             ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {	
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
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}

	/**
	 * DBC SYSOBJECTS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertSYSOBJECTS() throws SQLException {
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
		//sb.append("\n      , A.CRDATE          ");
		sb.append("\n      , CONVERT(CHAR(19), A.CRDATE, 120) AS CRDATE ");
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
		sb.append("\n      , B.NAME AS SCHEMA_NM  ");
		sb.append("\n      , C.ROW_COUNT      ");
		sb.append("\n  FROM ").append(src_nm).append("SYSOBJECTS A");
		sb.append("\n INNER JOIN SYS.SCHEMAS B               ");
		sb.append("\n         ON B.SCHEMA_ID = A.UID         ");
		sb.append("\n INNER JOIN (SELECT OBJECT_ID, MAX(ROW_COUNT) AS ROW_COUNT          ");
		sb.append("\n             FROM SYS.DM_DB_PARTITION_STATS						 ");
		sb.append("\n             GROUP BY OBJECT_ID 									 ");
		sb.append("\n             ) C													 ");
		sb.append("\n         ON C.OBJECT_ID = A.ID          ");
		sb.append("\n  WHERE A.XTYPE = 'U'                   ");
		sb.append("\n    AND B.NAME = '"+  schemaName +"'    ");
logger.debug(sb.toString());
		getResultSet(sb.toString());
		
		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_OBJECTS (                                 ");
		sb.append("\n        NAME, ID, XTYPE, C_UID, INFO                          "); 
		sb.append("\n      , STATUS, BASE_SCHEMA_VER, REPLINFO, PARENT_OBJ, CRDATE "); 
		sb.append("\n      , FTCATID, SCHEMA_VER, STATS_SCHEMA_VER, TYPE, USERSTAT "); 
		sb.append("\n      , SYSSTAT, INDEXDEL, REFDATE, VERSION, DELTRIG          "); 
		sb.append("\n      , INSTRIG, UPDTRIG, SELTRIG, CATEGORY, CACHE            "); 
		sb.append("\n      , DB_NM, SCH_NM, ROW_EACNT, DB_CONN_TRG_ID )                              ");
		sb.append("\n VALUES (								                        ");
		sb.append("\n        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,                         ");
		sb.append("\n        ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,                          ");
		sb.append("\n        ?, ?, ?, ?, ?, ?, ?, ?, ?                               ");
		sb.append("\n  )                                                         ");
logger.debug(sb.toString());
		
		int cnt = 0;
		if( rs != null ) {
			pst_org = con_org.prepareStatement(sb.toString());
			pst_org.clearParameters();

			int rsCnt = 1;
			int rsGetCnt = 1;

			while(rs.next()) {
				pst_org.setString(rsGetCnt++, rs.getString("NAME"));
				pst_org.setString(rsGetCnt++, rs.getString("ID"));
				pst_org.setString(rsGetCnt++, rs.getString("XTYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INFO"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATUS"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("BASE_SCHEMA_VER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("REPLINFO"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("PARENT_OBJ"));
				pst_org.setDate(rsGetCnt++, rs.getDate("CRDATE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("FTCATID"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SCHEMA_VER"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("STATS_SCHEMA_VER"));
				pst_org.setString(rsGetCnt++, rs.getString("TYPE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("USERSTAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SYSSTAT"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INDEXDEL"));
				pst_org.setTimestamp(rsGetCnt++, rs.getTimestamp("REFDATE"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("VERSION"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("DELTRIG"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("INSTRIG"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("UPDTRIG"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("SELTRIG"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CATEGORY"));
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("CACHE"));
				pst_org.setString(rsGetCnt++, dbName);
				pst_org.setString(rsGetCnt++, schemaName);
				pst_org.setBigDecimal(rsGetCnt++, rs.getBigDecimal("ROW_COUNT"));
				pst_org.setString(rsGetCnt++, dbId);
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

		return cnt;
	}

	private int insertDBA_COLUMNS_INFO() throws SQLException {
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		sb.append("\n SELECT F.NAME AS TABSCHEMA ,								");
  		sb.append("\n	     OBJECT_NAME(F.PARENT_OBJECT_ID) AS TABLENAME,      ");
  		sb.append("\n		 COL_NAME(FC.PARENT_OBJECT_ID, FC.PARENT_COLUMN_ID) AS COLNAME,	");
  		sb.append("\n		 OBJECT_NAME(F.REFERENCED_OBJECT_ID) AS REFETABLENAME,	");
  		sb.append("\n		 COL_NAME(FC.REFERENCED_OBJECT_ID, FC.REFERENCED_COLUMN_ID) AS REFECOLNAME	");
  		sb.append("\n FROM														");
  		sb.append("\n SYS.FOREIGN_KEYS AS F										");
  		sb.append("\n INNER JOIN SYS.FOREIGN_KEY_COLUMNS AS FC					");
  		sb.append("\n ON F.OBJECT_ID = FC.CONSTRAINT_OBJECT_ID					");
  		sb.append("\n INNER JOIN SYS.TABLES T									");
  		sb.append("\n ON T.OBJECT_ID = FC.REFERENCED_OBJECT_ID					");
logger.debug(sb.toString());
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
		sb.append("\n INSERT INTO WAE_MS_COLUMNS_INFO	");
		sb.append("\n (   DB_NM 					");
		sb.append("\n  , SCH_NM						");
		sb.append("\n  , TBL_NM						");
		sb.append("\n  , COL_NM                   	");
		sb.append("\n  , FK_INFO 					");
		sb.append("\n  , CHECK_INFO					");
		sb.append("\n  , DB_CONN_TRG_ID 			");	
		sb.append("\n  )                              ");
		sb.append("\n  VALUES                         ");
		sb.append("\n  (  TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?)   ");
		sb.append("\n   , TRIM(?), TRIM(?)								");
		sb.append("\n   )                                       		");
logger.debug(sb.toString());
		  		                      		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;

  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, dbName);
  				pst_org.setString(rsGetCnt++, schemaName);
  				pst_org.setString(rsGetCnt++, rs.getString("TABLENAME"));
  				pst_org.setString(rsGetCnt++, rs.getString("COLNAME"));
  				pst_org.setString(rsGetCnt++, rs.getString(4)+"."+rs.getString(5));
  				pst_org.setString(rsGetCnt++, "");
  				pst_org.setString(rsGetCnt++, dbId);
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
  		
  		return cnt;
  	}
	
	/**
	 * 타겟데이터의 SQL을 받아 CResultSet을 넘겨준다.
	 * @param String 조회 SQL
	 * @return CResultSet Query 결과값
	 * @throws SQLException
	 */
	private ResultSet getResultSet(String query_tgt) throws SQLException {
		pst_tgt = null;
		rs = null;
		pst_tgt = con_tgt.prepareStatement(query_tgt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		pst_tgt.setFetchSize(1000);
		rs = pst_tgt.executeQuery();

		return rs;
	}
	
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MS_COLUMNS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_COLUMNS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_COMMENTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_COMMENTS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_CONSTRAINTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_CONSTRAINTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_FOREIGNKEYS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_FOREIGNKEYS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_INDEXES ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_INDEXES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_INDEXKEYS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_INDEXKEYS : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_OBJECTS ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_OBJECTS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_TYPES ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_TYPES : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_EXTENDED_PROPERTIES ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_EXTENDED_PROPERTIES : " + result);
	
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MS_COLUMNS_INFO ");
		strSQL.append("\n     WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MS_COLUMNS_INFO : " + result);

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
		sb.append("\n )                                            ");
		sb.append("\n SELECT S.DB_SCH_ID           AS DB_SCH_ID                             ");
		sb.append("\n      , A.NAME                AS DBC_TBL_NM                            ");
		sb.append("\n      , SUBSTR(P.VALUE, 0, 200) AS DBC_TBL_KOR_NM                      ");
		sb.append("\n      , '1'                   AS VERS                                  ");
		sb.append("\n      , NULL                  AS REG_TYP                               ");
		sb.append("\n      , SYSDATE               AS REG_DTM                               ");
		sb.append("\n      , NULL                  AS UPD_DTM                               ");
		sb.append("\n      , ''                    AS DESCN                                 ");
		sb.append("\n      , S.DB_CONN_TRG_ID      AS DB_CONN_TRG_ID                        ");
		sb.append("\n      , ''                    AS DBC_TBL_SPAC_NM                       ");
		sb.append("\n      , '"+dbType+"'          AS DBMS_TYPE                             ");
		sb.append("\n      , ''                    AS COL_EACNT                             ");
		sb.append("\n      , A.ROW_EACNT           AS ROW_EACNT                             ");
		sb.append("\n      , ''                    AS TBL_SIZE                              ");
		sb.append("\n      , ''                    AS DATA_SIZE                             ");
		sb.append("\n      , ''                    AS IDX_SIZE                              ");
		sb.append("\n      , ''                    AS NUSE_SIZE                             ");
		sb.append("\n      , NULL                  AS ANA_DTM                               ");
		sb.append("\n      , A.CRDATE              AS CRT_DTM                               ");
		sb.append("\n      , NULL                  AS CHG_DTM                               ");
		sb.append("\n      , 'A'                   AS TBL_CLLT_DCD                          ");
		sb.append("\n   FROM WAE_MS_OBJECTS A                                               ");
		sb.append("\n  INNER JOIN (                                                         ");
		sb.append(getdbconnsql());
		sb.append("\n             ) S                                                       ");
		sb.append("\n          ON A.DB_NM  = S.DB_CONN_TRG_PNM                              ");
		sb.append("\n         AND A.SCH_NM = S.DB_SCH_PNM                                   ");
		sb.append("\n         AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                       ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                     SELECT CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,MAX(VALUE) AS VALUE,DB_NM,SCH_NM,DB_CONN_TRG_ID    ");
		sb.append("\n                       FROM WAE_MS_EXTENDED_PROPERTIES                                        ");
		sb.append("\n                      GROUP BY CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,DB_NM,SCH_NM,DB_CONN_TRG_ID ");
		sb.append("\n                   ) P                                                 ");	
		sb.append("\n                ON A.ID    = P.MAJOR_ID                                ");	
		sb.append("\n               AND A.DB_NM = P.DB_NM                                   ");
		sb.append("\n               AND A.DB_CONN_TRG_ID = P.DB_CONN_TRG_ID                 ");
		sb.append("\n               AND P.MINOR_ID = 0                                      ");	
		sb.append("\n  WHERE A.DB_NM   = '"+ dbName  +"'                                    "); 		
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"'                                  ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.NAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)													");
logger.debug(sb.toString());
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int insertwatcol() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL (                                    ");
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
		sb.append("\n      , FK_YN                                                 ");
		sb.append("\n      , ORD                                                   ");
		sb.append("\n      , PK_ORD                                                ");
		sb.append("\n      , COL_DESCN                                             ");
		sb.append("\n )                                                            ");
		sb.append("\n SELECT S.DB_SCH_ID AS DB_SCH_ID                              ");
		sb.append("\n      , A.O_NAME    AS DBC_TBL_NM                             ");
		sb.append("\n      , A.NAME      AS DBC_COL_NM                             ");
		sb.append("\n      , P_VALUE     AS DBC_COL_KOR_NM                         ");
		sb.append("\n      , '1'         AS OBJ_VERS                               ");
		sb.append("\n      , NULL        AS REG_TYP_CD                             ");
		sb.append("\n      , SYSDATE AS REG_DTM                                ");
		sb.append("\n      , NULL        AS UPD_DTM                                ");
		sb.append("\n      , A.T_NAME    AS DATA_TYPE                              ");
		sb.append("\n      , A.PREC      AS DATA_LEN                               ");
		sb.append("\n      , A.PREC      AS DATA_PNUM                              ");
		sb.append("\n      , A.SCALE     AS DATA_PNT                               ");
		sb.append("\n      , CASE WHEN A.ISNULLABLE = 1 THEN 'Y' ELSE 'N' END AS NULL_YN  ");
		sb.append("\n      , LENGTH(A.M_TEXT) AS DEFLT_LEN                                ");
		sb.append("\n      , A.M_TEXT    AS DEFLT_VAL                                     ");
		sb.append("\n      , CASE WHEN A.COLID = A.F_COLID THEN 'Y' ELSE 'N' END AS PK_YN ");
		sb.append("\n      , F.FK_INFO  AS FK_YN                		                   ");
		sb.append("\n      , A.COLORDER  AS ORD                                     ");
		sb.append("\n      , NULL          AS PK_ORD                                 	 ");
		sb.append("\n      , ''            AS COL_DESCN                                  ");
		sb.append("\n   FROM (SELECT C.DB_NM, C.SCH_NM, C.ID, C.XTYPE, C.CDEFAULT, C.COLID, C.DB_CONN_TRG_ID ");
		sb.append("\n              , C.SCALE, C.NAME, C.PREC, C.ISNULLABLE, C.COLORDER    ");
		sb.append("\n              , O.NAME AS O_NAME, T.NAME AS T_NAME, F.COLID AS F_COLID, F.KEYNO AS F_KEYNO, M.TEXT AS M_TEXT,substr(P.VALUE,1,200) AS P_VALUE ");
		sb.append("\n    	    FROM WAE_MS_OBJECTS O                                     ");
		sb.append("\n          INNER JOIN WAE_MS_COLUMNS C                                ");
		sb.append("\n                  ON O.DB_NM = C.DB_NM                               ");
		sb.append("\n                 AND O.SCH_NM = C.SCH_NM                             ");
		sb.append("\n                 AND O.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID             ");
		sb.append("\n                 AND O.ID = C.ID                                     ");
		sb.append("\n                 AND UPPER(O.NAME) NOT IN ('DTPROPERTIES')           ");
		sb.append("\n           LEFT OUTER JOIN WAE_MS_TYPES T                            ");
		sb.append("\n                        ON C.DB_NM = T.DB_NM                         ");
		sb.append("\n                       AND C.XUSERTYPE = T.XUSERTYPE                 ");
		sb.append("\n                       AND C.XTYPE = T.XTYPE                         ");
		sb.append("\n                       AND C.DB_CONN_TRG_ID = T.DB_CONN_TRG_ID       ");
		sb.append("\n                       AND T.NAME != 'sysname'                       ");
		sb.append("\n           LEFT OUTER JOIN WAE_MS_COMMENTS M                         ");
		sb.append("\n                        ON C.DB_NM = M.DB_NM                         ");
		sb.append("\n                       AND C.SCH_NM = M.SCH_NM                       ");
		sb.append("\n                       AND C.CDEFAULT = M.ID                         ");
		sb.append("\n                       AND C.DB_CONN_TRG_ID = M.DB_CONN_TRG_ID       ");
		sb.append("\n                       AND M.COLID = 1                               ");
		sb.append("\n           LEFT OUTER JOIN (SELECT E.DB_NM, E.SCH_NM, E.ID, E.INDID, G.COLID, G.KEYNO, G.DB_CONN_TRG_ID ");
		sb.append("\n                              FROM WAE_MS_INDEXES E                  ");
		sb.append("\n                                 , WAE_MS_INDEXKEYS G                ");
		sb.append("\n                             WHERE G.DB_NM = E.DB_NM                 ");
		sb.append("\n                               AND G.SCH_NM = E.SCH_NM               ");
		sb.append("\n                               AND G.ID = E.ID                       ");
		sb.append("\n                               AND G.DB_CONN_TRG_ID = E.DB_CONN_TRG_ID ");
		sb.append("\n                               AND G.INDID = E.INDID) F              ");
		sb.append("\n                        ON F.DB_NM = C.DB_NM                         ");
		sb.append("\n                       AND F.SCH_NM = C.SCH_NM                       ");
		sb.append("\n                       AND F.ID = C.ID                               ");
		sb.append("\n                       AND F.COLID = C.COLID                         ");
		sb.append("\n                       AND F.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID       ");
		sb.append("\n                       AND F.INDID = 1                               ");
		sb.append("\n           LEFT OUTER JOIN (                                         ");
		sb.append("\n                   	      SELECT CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,MAX(VALUE) AS VALUE,DB_NM,SCH_NM,DB_CONN_TRG_ID    ");
		sb.append("\n                   	        FROM WAE_MS_EXTENDED_PROPERTIES       ");
		sb.append("\n                   	       GROUP BY CLASS,CLASS_DESC,MAJOR_ID,MINOR_ID,DB_NM,SCH_NM,DB_CONN_TRG_ID ");
		sb.append("\n                           ) P                                       ");
		sb.append("\n                        ON P.MAJOR_ID = C.ID                         ");
		sb.append("\n                       AND P.MINOR_ID = C.COLID                      ");
		sb.append("\n                       AND P.DB_NM = C.DB_NM                         ");
		sb.append("\n                       AND P.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID       ");
		sb.append("\n          WHERE ((RTRIM(O.TYPE) IN ('U', 'D', 'F') AND C.C_NUMBER = 0)) ");
		sb.append("\n        ) A                                                          ");
		sb.append("\n	LEFT JOIN (										");
		sb.append("\n		SELECT DB_CONN_TRG_ID, DB_NM, SCH_NM, TBL_NM, COL_NM, WM_CONCAT(FK_INFO) FK_INFO		");
		sb.append("\n		FROM WAE_MS_COLUMNS_INFO 														");
		sb.append("\n		GROUP BY DB_NM, SCH_NM, TBL_NM, COL_NM, DB_CONN_TRG_ID							");
		sb.append("\n				) F												");
		sb.append("\n	ON	A.DB_NM = F.DB_NM										");
		sb.append("\n	AND A.SCH_NM = F.SCH_NM									");
		sb.append("\n	AND A.O_NAME  = F.TBL_NM								");
		sb.append("\n	AND A.NAME = F.COL_NM								");
		sb.append("\n	AND A.DB_CONN_TRG_ID = F.DB_CONN_TRG_ID						");
		sb.append("\n  INNER JOIN (                                                       ");
		sb.append(getdbconnsql());
		sb.append("\n             ) S                                                     ");
		sb.append("\n          ON A.DB_NM = S.DB_CONN_TRG_PNM                             ");
		sb.append("\n         AND A.SCH_NM = S.DB_SCH_PNM                                 ");
		sb.append("\n         AND A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID                     ");
		sb.append("\n  WHERE A.DB_NM   = '"+ dbName  +"'                                  "); 		
		sb.append("\n    AND S.DB_CONN_TRG_ID = '"+dbId+"'                                ");
		//수집데이터 제외 테이블
		sb.append("\n   AND A.SCH_NM || '.' || A.NAME NOT IN ( 	");
		sb.append("\n   		SELECT DB_SCH_PNM || '.' || TBL_PNM	");
		sb.append("\n   		FROM WAA_COLLECT_EXCEPT							");
		sb.append("\n   		WHERE DB_NM = A.DB_NM				");
		sb.append("\n   		AND DB_SCH_PNM = A.SCH_NM			");
		sb.append("\n   	)													");
logger.debug(sb.toString());
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, T.DB_CONN_TRG_LNM,S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n             INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                     ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n                    AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                    AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n             WHERE 1=1 ");
		strSQL.append("\n               AND T.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n               AND T.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n               AND T.DB_CONN_TRG_ID = '"+dbId+"'");

		return strSQL.toString();
	}
	
	private int updateTblCnt() throws SQLException {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET tbl_cnt = (        ");
		strSQL.append("\n		SELECT COUNT(DBC_TBL_NM) FROM WAT_DBC_TBL WHERE DB_CONN_TRG_ID = '"+dbId+"' ");
		strSQL.append("\n     )   						    ");
		strSQL.append("\n      	WHERE 1=1       ");
		strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
		strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
		strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbId+"'     ");
				
		return setExecuteUpdate_Org(strSQL.toString());
	}
	
	private String updateDataCpct() throws SQLException {
		StringBuffer tgtSQL = new StringBuffer();
		tgtSQL = tgtSQL.append("select sum(total_pages)/128.00 from sys.allocation_units ");
		rs = getResultSet(tgtSQL.toString());
		rs.next();
		
		tgtSQL = new StringBuffer();
		tgtSQL = tgtSQL.append("\n declare @RESULT NUMERIC(38,2)");
				 tgtSQL.append("\n SET @RESULT = " + Math.round(Float.parseFloat(rs.getString(1))*100)/100.0	 + "");
				 tgtSQL.append("\n select @RESULT");
		rs = getResultSet(tgtSQL.toString());
		
		if(rs != null){
			rs.next();
			data_cpct = rs.getString(1);
			
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n     UPDATE WAA_DB_CONN_TRG SET DATA_CPCT = (        ");
			strSQL.append(rs.getString(1));
			strSQL.append("\n     )   						    ");
			strSQL.append("\n      	WHERE 1=1       ");
			strSQL.append("\n       	AND REG_TYP_CD IN ('C','U')      ");
			strSQL.append("\n       	AND EXP_DTM = TO_DATE('99991231','YYYYMMDD')      ");
			strSQL.append("\n       	AND DB_CONN_TRG_ID = '"+dbId+"'     ");
			setExecuteUpdate_Org(strSQL.toString());
		}
						
		return data_cpct;
	}	
}
