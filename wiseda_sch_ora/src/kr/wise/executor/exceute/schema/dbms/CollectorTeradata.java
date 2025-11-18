package kr.wise.executor.exceute.schema.dbms;

/*
 *  TeraData v14 jdbc연결
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class CollectorTeradata {

	
	private static final Logger logger = Logger.getLogger(CollectorTeradata.class);
	
	private Connection con_org = null;
	private Connection con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private TargetDbmsDM tgtDbmsDM;
	private String dbConnTgtId = null;
	private String dbConnTgtPnm = null;
	private String dbType = null;
	
	private int execCnt = 10000;
	
	public CollectorTeradata() {

	}

	/** insomnia */
	public CollectorTeradata(Connection source, Connection target,	TargetDbmsDM  tgtDbmsDM) {
		this.con_org = source;
		this.con_tgt = target;
		this.tgtDbmsDM = tgtDbmsDM;
	}
	
	

  	public boolean doProcess() throws Exception
  	{
  		boolean result = false;
		con_org.setAutoCommit(false);

		this.dbConnTgtId = tgtDbmsDM.getDbms_id();
		this.dbConnTgtPnm = tgtDbmsDM.getDbms_enm();
		this.dbType = tgtDbmsDM.getDbms_type_cd();
		
		//스키마 정보를 모두 삭제한다.
		deleteSchema();

		String sp = "   ";
		int p = 0;
		int cnt = 0;

		cnt = insertDBA_USERS();
		logger.debug(sp + (++p) + ". insertDBA_USERS " + cnt + " OK!!");
		
		cnt = insertDBA_TABLES();
		logger.debug(sp + (++p) + ". insertDBA_TABLES " + cnt + " OK!!");
		
		cnt = insertDBA_COLUMNS();
		logger.debug(sp + (++p) + ". insertDBA_COLUMNS " + cnt + " OK!!");
		
		cnt = insertDBA_IDX_COLUMNS();
		logger.debug(sp + (++p) + ". insertDBA_IDX_COLUMNS " + cnt + " OK!!");

		result = true;
		return result;
	}


  	private int deleteSchema() throws Exception {
		int result = 0 ;
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_TERA_USERS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTgtId+"' ");
	//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TERA_USERS : " + result);
	
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TERA_TABLES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTgtId+"' ");
	//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TERA_TABLES : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TERA_COLUMNS ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTgtId+"' ");
	//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TERA_COLUMNS : " + result);
		
		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_TERA_INDICES ");
		strSQL.append("\n   WHERE DB_CONN_TRG_ID = '"+dbConnTgtId+"' ");
	//		strSQL.append("\n     AND DB_SCH_ID = '"+dbSchId+"' ");
		
		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_TERA_INDICES : " + result);

		return result;
	}
  	
  	
  	
  	private int insertDBA_USERS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT	DatabaseName, CreatorName, OwnerName, AccountName, ProtectionType, ");
  		sb.append("\n 		JournalFlag, PermSpace, SpoolSpace, TempSpace, CommentString, ");
  		sb.append("\n 		CreateTimeStamp, LastAlterName, LastAlterTimeStamp, DBKind, AccessCount, ");
  		sb.append("\n 		LastAccessTimeStamp ");
  		sb.append("\n FROM	DBC.Databases ");
  		sb.append("\n WHERE UPPER(TRIM(DatabaseName)) = UPPER(TRIM('").append(dbConnTgtPnm).append("'))");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT	INTO WAE_TERA_USERS ( ");
  		sb.append("\n    DatabaseName, CreatorName, OwnerName, AccountName, ProtectionType ");
  		sb.append("\n   ,JournalFlag, PermSpace, SpoolSpace, TempSpace, CommentString ");
  		sb.append("\n   ,CreateTimeStamp, LastAlterName, LastAlterTimeStamp, DBKind, AccessCount ");
  		sb.append("\n 	,LastAccessTimeStamp,DB_CONN_TRG_ID ");  //DB_SCH_ID
  		sb.append("\n )  VALUES  (");
  		sb.append("\n    TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?) ");
  		sb.append("\n )  ");
 		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("DatabaseName"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreatorName"));
  				pst_org.setString(rsGetCnt++, rs.getString("OwnerName"));
  				pst_org.setString(rsGetCnt++, rs.getString("AccountName"));
  				pst_org.setString(rsGetCnt++, rs.getString("ProtectionType"));
  				pst_org.setString(rsGetCnt++, rs.getString("JournalFlag"));
  				pst_org.setDouble(rsGetCnt++, rs.getDouble("PermSpace"));
  				pst_org.setDouble(rsGetCnt++, rs.getDouble("SpoolSpace"));
  				pst_org.setDouble(rsGetCnt++, rs.getDouble("TempSpace"));
  				pst_org.setString(rsGetCnt++, rs.getString("CommentString"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreateTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterName"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("DBKind"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("AccessCount"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAccessTimeStamp"));
  				pst_org.setString(rsGetCnt++, dbConnTgtId);
//  				pst_org.setString(rsGetCnt++, dbSchId);
  				
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
  	
  	
  	private int insertDBA_TABLES() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\nSELECT	DatabaseName, TableName, Version, TableKind, ProtectionType ");
  		sb.append("\n		,JournalFlag, CreatorName, RequestText, CommentString, ParentCount ");
  		sb.append("\n		,ChildCount, NamedTblCheckCount, UnnamedTblCheckExist, PrimaryKeyIndexId, RepStatus ");
  		sb.append("\n		,CreateTimeStamp, LastAlterName, LastAlterTimeStamp,RequestTxtOverflow, AccessCount ");
  		sb.append("\n		,LastAccessTimeStamp, UtilVersion,QueueFlag, CommitOpt, TransLog ");
  		sb.append("\n		,CheckOpt, TemporalProperty, ResolvedCurrent_Date,ResolvedCurrent_Timestamp, SystemDefinedJI ");
  		sb.append("\n		,VTQualifier, TTQualifier ");
  		sb.append("\n  FROM DBC.Tables ");
  		sb.append("\n WHERE UPPER(TRIM(DatabaseName)) = UPPER(TRIM('").append(dbConnTgtPnm).append("'))");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\n INSERT INTO WAE_TERA_TABLES (    ");
  		sb.append("\n         DatabaseName, TableName, Version, TableKind, ProtectionType ");
  		sb.append("\n		, JournalFlag,CreatorName, RequestText, CommentString, ParentCount ");
  		sb.append("\n		, ChildCount,NamedTblCheckCount, UnnamedTblCheckExist, PrimaryKeyIndexId,RepStatus ");
  		sb.append("\n		, CreateTimeStamp, LastAlterName, LastAlterTimeStamp,RequestTxtOverflow, AccessCount ");
  		sb.append("\n		, LastAccessTimeStamp, UtilVersion,QueueFlag, CommitOpt, TransLog ");
  		sb.append("\n		, CheckOpt, TemporalProperty, ResolvedCurrent_Date,ResolvedCurrent_Timestamp, SystemDefinedJI ");
  		sb.append("\n		, VTQualifier, TTQualifier, DB_CONN_TRG_ID ");
		sb.append("\n )  VALUES  (");
  		sb.append("\n     TRIM(?), TRIM(?), ?, TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), ? ");
  		sb.append("\n    ,?, ?, TRIM(?), ?, TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), ? ");
  		sb.append("\n    ,TRIM(?), ?, TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n    ,TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n )  ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("DatabaseName"));
  				pst_org.setString(rsGetCnt++, rs.getString("TableName"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("Version"));
  				pst_org.setString(rsGetCnt++, rs.getString("TableKind"));
  				pst_org.setString(rsGetCnt++, rs.getString("ProtectionType"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("JournalFlag"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreatorName"));
  				pst_org.setString(rsGetCnt++, "");
  				pst_org.setString(rsGetCnt++, rs.getString("CommentString"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ParentCount"));
  				
  				pst_org.setInt(rsGetCnt++, rs.getInt("ChildCount"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("NamedTblCheckCount"));
  				pst_org.setString(rsGetCnt++, rs.getString("UnnamedTblCheckExist"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("PrimaryKeyIndexId"));
  				pst_org.setString(rsGetCnt++, rs.getString("RepStatus"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("CreateTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterName"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("RequestTxtOverflow"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("AccessCount"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("LastAccessTimeStamp"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("UtilVersion"));
  				pst_org.setString(rsGetCnt++, rs.getString("QueueFlag"));
  				pst_org.setString(rsGetCnt++, rs.getString("CommitOpt"));
  				pst_org.setString(rsGetCnt++, rs.getString("TransLog"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("CheckOpt"));
  				pst_org.setString(rsGetCnt++, rs.getString("TemporalProperty"));
  				pst_org.setString(rsGetCnt++, rs.getString("ResolvedCurrent_Date"));
  				pst_org.setString(rsGetCnt++, rs.getString("ResolvedCurrent_Timestamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("SystemDefinedJI"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("VTQualifier"));
  				pst_org.setString(rsGetCnt++, rs.getString("TTQualifier"));
  				pst_org.setString(rsGetCnt++, dbConnTgtId);
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
  
  	
  	private int insertDBA_IDX_COLUMNS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
  		sb.append("\n SELECT DatabaseName, TableName, IndexNumber, IndexType, UniqueFlag, IndexName, ColumnName, ColumnPosition, CreatorName, CreateTimeStamp, LastAlterName, LastAlterTimeStamp, IndexMode, AccessCount, LastAccessTimeStamp, UniqueOrPK, VTConstraintType, TTConstraintType, SystemDefinedJI ");
  		sb.append("\n   FROM DBC.Indices ");
  		sb.append("\n WHERE UPPER(TRIM(DatabaseName)) = UPPER(TRIM('").append(dbConnTgtPnm).append("'))");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\nINSERT	INTO WAE_TERA_INDICES ( ");
  		sb.append("\n       DatabaseName, TableName, IndexNumber, IndexType, UniqueFlag ");
  		sb.append("\n		, IndexName,ColumnName, ColumnPosition, CreatorName, CreateTimeStamp ");
  		sb.append("\n		, LastAlterName,LastAlterTimeStamp, IndexMode, AccessCount, LastAccessTimeStamp ");
  		sb.append("\n		, UniqueOrPK, VTConstraintType, TTConstraintType, SystemDefinedJI,DB_CONN_TRG_ID ");  //DB_SCH_ID
		sb.append("\n )  VALUES  (");
  		sb.append("\n      TRIM(?), TRIM(?), ?, TRIM(?), TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?), ?, TRIM(?), TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), ?, TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n )  ");
  		
  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("DatabaseName"));
  				pst_org.setString(rsGetCnt++, rs.getString("TableName"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("IndexNumber"));
  				pst_org.setString(rsGetCnt++, rs.getString("IndexType"));
  				pst_org.setString(rsGetCnt++, rs.getString("UniqueFlag"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("IndexName"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnName"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ColumnPosition"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreatorName"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreateTimeStamp"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterName"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("IndexMode"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("AccessCount"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAccessTimeStamp"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("UniqueOrPK"));
  				pst_org.setString(rsGetCnt++, rs.getString("VTConstraintType"));
  				pst_org.setString(rsGetCnt++, rs.getString("TTConstraintType"));
  				pst_org.setString(rsGetCnt++, rs.getString("SystemDefinedJI"));
  				pst_org.setString(rsGetCnt++, dbConnTgtId);
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
  	
  	
  	private int insertDBA_COLUMNS() throws SQLException
  	{
  		StringBuffer sb = new StringBuffer();
  		int cnt = 0;
  		
//  		sb.append("\nSELECT	* ");
  		sb.append("\nSELECT	DatabaseName, TableName, ColumnName, ColumnFormat, ColumnTitle, ");
  		sb.append("\n		SPParameterType, ColumnType, ColumnUDTName, ColumnLength, DefaultValue, ");
  		sb.append("\n		Nullable, CommentString, DecimalTotalDigits, DecimalFractionalDigits, ");
  		sb.append("\n		ColumnId, UpperCaseFlag, Compressible, CompressValue, ColumnConstraint, ");
  		sb.append("\n		ConstraintCount, CreatorName, CreateTimeStamp, LastAlterName, ");
  		sb.append("\n		LastAlterTimeStamp, CharType, IdColType, AccessCount, LastAccessTimeStamp, ");
  		sb.append("\n		CompressValueList, TimeDimension, VTCheckType, TTCheckType  ");
//  		sb.append("\n		, ConstraintId, ArrayColNumberOfDimensions, ArrayColScope, ArrayColElementType, ArrayColElementUdtName ");
  		sb.append("\nFROM	DBC.Columns ");
  		sb.append("\n WHERE UPPER(TRIM(DatabaseName)) = UPPER(TRIM('").append(dbConnTgtPnm).append("'))");
  		
  		getResultSet(sb.toString());
  		
  		sb = new StringBuffer();
  		
  		sb.append("\nINSERT INTO WAE_TERA_COLUMNS ( ");
  		sb.append("\n        DatabaseName, TableName, ColumnName, ColumnFormat, ColumnTitle ");
  		sb.append("\n		,SPParameterType, ColumnType, ColumnUDTName, ColumnLength, DefaultValue ");
  		sb.append("\n		,Nullable, CommentString, DecimalTotalDigits, DecimalFractionalDigits,ColumnId ");
  		sb.append("\n		,UpperCaseFlag, Compressible, CompressValue, ColumnConstraint,ConstraintCount ");
  		sb.append("\n		,CreatorName, CreateTimeStamp, LastAlterName,LastAlterTimeStamp, CharType ");
  		sb.append("\n		,IdColType, AccessCount, LastAccessTimeStamp,CompressValueList, TimeDimension ");
  		sb.append("\n		,VTCheckType, TTCheckType  ");
//  		sb.append("\n		ConstraintId, ArrayColNumberOfDimensions, ArrayColScope, ArrayColElementType, ArrayColElementUdtName, ");
  		sb.append("\n		,DB_CONN_TRG_ID ");  //DB_SCH_ID
		sb.append("\n )  VALUES  (");
  		sb.append("\n      TRIM(?), TRIM(?), TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), ?, TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?), ?, ?, ? ");
  		sb.append("\n     ,TRIM(?), TRIM(?), ?, TRIM(?), ? ");
  		sb.append("\n     ,TRIM(?), TRIM(?), TRIM(?), TRIM(?), ? ");
  		sb.append("\n     ,TRIM(?), ?, TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n     ,TRIM(?), TRIM(?) ");
//  		sb.append("\n     ,TRIM(?), ?, TRIM(?), TRIM(?), TRIM(?) ");
  		sb.append("\n     ,TRIM(?)");
  		sb.append("\n )  ");

  		if( rs != null ) {
  			pst_org = con_org.prepareStatement(sb.toString());
  			pst_org.clearParameters();
  			
  			int rsCnt = 1;
  			int rsGetCnt = 1;
  			
  			while(rs.next()) {
  				pst_org.setString(rsGetCnt++, rs.getString("DatabaseName"));
  				pst_org.setString(rsGetCnt++, rs.getString("TableName"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnName"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnFormat"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnTitle"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("SPParameterType"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnType"));
  				pst_org.setString(rsGetCnt++, rs.getString("ColumnUDTName"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ColumnLength"));
  				pst_org.setString(rsGetCnt++, rs.getString("DefaultValue"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("Nullable"));
  				pst_org.setString(rsGetCnt++, rs.getString("CommentString"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("DecimalTotalDigits"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("DecimalFractionalDigits"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("ColumnId"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("UpperCaseFlag"));
  				pst_org.setString(rsGetCnt++, rs.getString("Compressible"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("CompressValue"));
  				pst_org.setString(rsGetCnt++, "");
  				pst_org.setInt(rsGetCnt++, rs.getInt("ConstraintCount"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("CreatorName"));
  				pst_org.setString(rsGetCnt++, rs.getString("CreateTimeStamp"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterName"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAlterTimeStamp"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("CharType"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("IdColType"));
  				pst_org.setInt(rsGetCnt++, rs.getInt("AccessCount"));
  				pst_org.setString(rsGetCnt++, rs.getString("LastAccessTimeStamp"));
  				pst_org.setString(rsGetCnt++, "");
  				pst_org.setString(rsGetCnt++, rs.getString("TimeDimension"));
  				
  				pst_org.setString(rsGetCnt++, rs.getString("VTCheckType"));
  				pst_org.setString(rsGetCnt++, rs.getString("TTCheckType"));
//  				pst_org.setString(rsGetCnt++, rs.getString("ConstraintId"));
//  				pst_org.setInt(rsGetCnt++, rs.getInt("ArrayColNumberOfDimensions"));
//  				pst_org.setString(rsGetCnt++, rs.getString("ArrayColScope"));
//  				pst_org.setString(rsGetCnt++, rs.getString("ArrayColElementType"));
//  				pst_org.setString(rsGetCnt++, rs.getString("ArrayColElementUdtName"));
  				pst_org.setString(rsGetCnt++, dbConnTgtId);
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
  	
  	
	/**  insomnia */
	public boolean saveWat(TargetDbmsDM targetDb) throws Exception {
		this.dbConnTgtPnm = targetDb.getDbms_enm();
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
		sb.append("\n INSERT INTO WAT_DBC_TBL ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID    AS DB_SCH_ID  ");
		sb.append("\n      , A.TABLENAME     AS DBC_TBL_NM ");
		sb.append("\n      , A.COMMENTSTRING     AS DBC_TBL_KOR_NM    ");
		sb.append("\n      , 1        AS VERS     ");
		sb.append("\n      , NULL     AS REG_TYP  ");
		sb.append("\n      , SYSDATE  AS REG_DTM  ");
		sb.append("\n      , NULL     AS UPD_DTM  ");
		sb.append("\n      , NULL     AS DESCN ");
		sb.append("\n      , S.DB_CONN_TRG_ID  AS DB_CONN_TRG_ID   ");
		sb.append("\n      , NULL  AS DBC_TBL_SPAC_NM  ");
		sb.append("\n      , NULL  AS DDL_TBL_ID       ");
		sb.append("\n      , NULL  AS PDM_TBL_ID       ");
		sb.append("\n      , '"+dbType+"'      AS DBMS_TYPE        ");
		sb.append("\n      , ''    AS SUBJ_ID       ");
		sb.append("\n      , ''    AS COL_EACNT     ");
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
		sb.append("\n   FROM WAE_TERA_TABLES A           ");
		sb.append("\n        INNER JOIN ( ");
		sb.append(getdbconnsql());
		sb.append("\n                         ) S  ");
		sb.append("\n          ON A.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID   ");
		//동일 CreatorName 으로 동일 TABLE 이 상이한 DATABASENAME 별로 생성 될 수 있음
		//DB_CONN_TRG_PNM 이 DATABASENAME 과 동일 해야 함
		sb.append("\n         AND UPPER(A.DATABASENAME ) = UPPER(S.DB_CONN_TRG_PNM)  ");
		//CreatorName 오너가 아닐수도 있습니다...
		sb.append("\n         AND UPPER(A.CREATORNAME) = UPPER(S.DB_SCH_PNM)  ");
		sb.append("\n WHERE A.DB_CONN_TRG_ID = '"+dbConnTgtId+"' ");
		sb.append("\n   AND A.TABLEKIND = 'T' ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL ");
		sb.append("\n SELECT DISTINCT S.DB_SCH_ID AS DB_SCH_ID    ");
		sb.append("\n      , T.TABLENAME   AS DBC_TBL_NM   ");
		sb.append("\n      , C.COLUMNNAME  AS DBC_COL_NM   ");
		sb.append("\n      , C.COLUMNTITLE AS DBC_COL_KOR_NM  ");
		sb.append("\n      , 1      AS VERS     ");
		sb.append("\n      , NULL   AS REG_TYP  ");
		sb.append("\n      , SYSDATE AS REG_DTM ");
		sb.append("\n      , NULL   AS UPD_DTM  ");
		sb.append("\n      , NULL   AS DESCN    ");
		sb.append("\n      , NULL   AS DDL_COL_ID ");
		sb.append("\n      , NULL   AS PDM_COL_ID ");
		sb.append("\n      , NULL   AS ITM_ID     ");
		sb.append("\n      , CASE C.COLUMNTYPE WHEN 'BF' THEN 'BYTE'         ");
		sb.append("\n                          WHEN 'BV' THEN 'VARBYTE'         ");
		sb.append("\n                          WHEN 'CF' THEN 'CHAR'         ");
		sb.append("\n                          WHEN 'CV' THEN 'VARCHAR'         ");
		sb.append("\n                          WHEN 'D' THEN 'DECIMAL'         ");
		sb.append("\n                          WHEN 'DA' THEN 'DATE'         ");
		sb.append("\n                          WHEN 'F' THEN 'FLOAT'         ");
		sb.append("\n                          WHEN 'I1' THEN 'BYTEINT'         ");
		sb.append("\n                          WHEN 'I2' THEN 'SMALLINT'         ");
		sb.append("\n                          WHEN 'I8' THEN 'BIGINT'         ");
		sb.append("\n                          WHEN 'I' THEN 'INTEGER'         ");
		sb.append("\n                          WHEN 'AT' THEN 'TIME'         ");
		sb.append("\n                          WHEN 'TS' THEN 'TIMESTAMP'         ");
		sb.append("\n                          WHEN 'TZ' THEN 'TIME WITH TIME ZONE'         ");
		sb.append("\n                          WHEN 'SZ' THEN 'TIMESTAMP WITH TIME ZONE'         ");
		sb.append("\n                          WHEN 'YR' THEN 'INTERVAL YEAR'         ");
		sb.append("\n                          WHEN 'YM' THEN 'INTERVAL YEAR TO MONTH'         ");
		sb.append("\n                          WHEN 'MO' THEN 'INTERVAL MONTH'         ");
		sb.append("\n                          WHEN 'DY' THEN 'INTERVAL DAY'         ");
		sb.append("\n                          WHEN 'DH' THEN 'INTERVAL DAY TO HOUR'         ");
		sb.append("\n                          WHEN 'DM' THEN 'INTERVAL DAY TO MINUTE'         ");
		sb.append("\n                          WHEN 'DS' THEN 'INTERVAL DAY TO SECOND'         ");
		sb.append("\n                          WHEN 'HR' THEN 'INTERVAL HOUR'         ");
		sb.append("\n                          WHEN 'HM' THEN 'INTERVAL HOUR TO MINUTE'         ");
		sb.append("\n                          WHEN 'HS' THEN 'INTERVAL HOUR TO SECOND'         ");
		sb.append("\n                          WHEN 'MI' THEN 'INTERVAL MINUTE'         ");
		sb.append("\n                          WHEN 'MS' THEN 'INTERVAL MINUTE TO SECOND'         ");
		sb.append("\n                          WHEN 'SC' THEN 'INTERVAL SECOND'         ");
		sb.append("\n                          WHEN 'BO' THEN 'BLOB'         ");
		sb.append("\n                          WHEN 'CO' THEN 'CLOB'         ");
		sb.append("\n                          WHEN 'PD' THEN 'PERIOD(DATE)'         ");
		sb.append("\n                          WHEN 'PM' THEN 'PERIOD(TIMESTAMP WITH TIME ZONE)'         ");
		sb.append("\n                          WHEN 'PS' THEN 'PERIOD(TIMESTAMP)'         ");
		sb.append("\n                          WHEN 'PT' THEN 'PERIOD(TIME)'         ");
		sb.append("\n                          WHEN 'PZ' THEN 'PERIOD(TIME WITH TIME ZONE)'         ");
		sb.append("\n                          WHEN 'UT' THEN 'User Defined DataType'         ");
		sb.append("\n                          WHEN '++' THEN 'TD_ANYTYPE'         ");
		sb.append("\n                          WHEN 'N' THEN 'NUMBER'         ");
		sb.append("\n                          WHEN 'A1' THEN 'ARRAY'         ");
		sb.append("\n                          WHEN 'AN' THEN 'ARRAY (multidimensional)'         ");
		sb.append("\n                          WHEN 'JN' THEN 'JSON'         ");
		sb.append("\n                          WHEN 'VA' THEN 'TD_VALIST'         ");
		sb.append("\n                          WHEN 'XM' THEN 'XML'         ");
		sb.append("\n                          ELSE '<Unknown>'   END     ");
		sb.append("\n        AS DATA_TYPE         ");
		sb.append("\n      , C.COLUMNLENGTH  AS LENGTH  ");
		sb.append("\n      , NULL AS  PREC  ");
		sb.append("\n      , NULL AS SCALE  ");
		sb.append("\n      , CASE WHEN I.COLUMNNAME IS NOT NULL THEN 'Y' ELSE C.NULLABLE END  AS IS_NULLABLE   ");
		sb.append("\n      , NULL AS DEFLT_LEN        ");
		sb.append("\n      , replace(trim(regexp_replace(C.DEFAULTVALUE, '\\s', '')),'''','') AS DEFLT_VAL  ");
		sb.append("\n      , CASE WHEN I.COLUMNNAME IS NOT NULL THEN 'Y' END  AS PK_YN");
		sb.append("\n      , ROW_NUMBER() OVER(PARTITION BY C.DATABASENAME ,C.TABLENAME ORDER BY C.COLUMNID )  AS ORD ");
		sb.append("\n      , CASE WHEN I.COLUMNNAME IS NOT NULL THEN I.COLUMNPOSITION END  AS PK_ORD");
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
		sb.append("\n FROM WAE_TERA_TABLES T  ");
		sb.append("\n      INNER JOIN WAE_TERA_COLUMNS C  ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID  ");
		sb.append("\n        AND T.DATABASENAME  = C.DATABASENAME   ");
		sb.append("\n        AND T.TABLENAME  = C.TABLENAME    ");
		sb.append("\n        AND C.COLUMNTYPE IS NOT NULL    ");
		sb.append("\n      INNER JOIN (   ");
		sb.append(getdbconnsql());
		sb.append("\n                  ) S ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
		sb.append("\n        AND UPPER(T.DATABASENAME ) = UPPER(S.DB_CONN_TRG_PNM)  ");
		sb.append("\n        AND UPPER(T.CREATORNAME) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n       LEFT OUTER JOIN WAE_TERA_INDICES I   ");
		sb.append("\n         ON T.DATABASENAME = I.DATABASENAME  ");
		sb.append("\n        AND T.TABLENAME = I.TABLENAME  ");
		sb.append("\n        AND C.COLUMNNAME = I.COLUMNNAME  ");
		sb.append("\n        AND I.INDEXTYPE  IN ('P', 'K')  ");
		sb.append("\n  WHERE T.DB_CONN_TRG_ID = '"+dbConnTgtId+"'     ");


		return setExecuteUpdate_Org(sb.toString());

	}

	private String getdbconnsql() {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n            SELECT T.DB_CONN_TRG_ID, T.DB_CONN_TRG_PNM, S.DB_SCH_ID, S.DB_SCH_PNM ");
		strSQL.append("\n              FROM WAA_DB_CONN_TRG T ");
		strSQL.append("\n                   INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n                      ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n                     AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                     AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
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
		sb.append("\n INSERT INTO WAT_DBC_IDX   ");
		sb.append("\n SELECT S.DB_SCH_ID        ");
		sb.append("\n      , T.TABLENAME   AS DBC_TBL_NM   ");
		sb.append("\n      , I.INDEXNAME    AS DBC_IDX_NM    ");
		sb.append("\n      , S.DB_CONN_TRG_ID   ");
		sb.append("\n      , NULL AS DBC_IDX_KOR_NM   ");
		sb.append("\n      , 1      AS VERS     ");
		sb.append("\n      , NULL   AS REG_TYP  ");
		sb.append("\n      , SYSDATE AS REG_DTM ");
		sb.append("\n      , NULL   AS REG_USER ");
		sb.append("\n      , NULL   AS UPD_DTM  ");
		sb.append("\n      , NULL   AS UPD_USER ");
		sb.append("\n      , NULL   AS DESCN    ");
		sb.append("\n      , NULL   AS DBC_TBL_SPAC_NM     ");
		sb.append("\n      , NULL   AS DDL_IDX_ID  ");
		sb.append("\n      , NULL   AS PDM_IDX_ID  ");
//		 index type as P (primary index),Q (partition primary index), S (secondary index), U (unique), K (primary key).
		sb.append("\n      , CASE WHEN I.INDEXTYPE = 'P' OR  I.INDEXTYPE = 'Q' THEN 'Y' ELSE 'N' END PK_YN  ");
		sb.append("\n      , CASE WHEN I.INDEXTYPE = 'U' THEN 'Y' ELSE 'N' END UK_YN  ");
		sb.append("\n      , COUNT(I.COLUMNNAME) AS COL_CNT   ");
		sb.append("\n      , '' AS IDX_SIZE      ");
		sb.append("\n      , '' AS BF_IDX_EACNT  ");
		sb.append("\n      , '' AS BF_IDX_SIZE   ");
		sb.append("\n      , '' AS ANA_DTM       ");
		sb.append("\n      , '' AS CRT_DTM       ");
		sb.append("\n      , '' AS CHG_DTM       ");
		sb.append("\n      , '' AS IDX_DQ_EXP_YN ");
		sb.append("\n      , '' AS SGMT_BYTE_SIZE ");
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
		sb.append("\n FROM WAE_TERA_TABLES T  ");
		sb.append("\n      INNER JOIN WAE_TERA_COLUMNS C  ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID  ");
		sb.append("\n        AND T.DATABASENAME  = C.DATABASENAME   ");
		sb.append("\n        AND T.TABLENAME  = C.TABLENAME    ");
		sb.append("\n        AND C.COLUMNTYPE IS NOT NULL    ");
		sb.append("\n      INNER JOIN (   ");
		sb.append(getdbconnsql());
		sb.append("\n                  ) S ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
		sb.append("\n        AND UPPER(T.DATABASENAME ) = UPPER(S.DB_CONN_TRG_PNM)  ");
		sb.append("\n        AND UPPER(T.CREATORNAME) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n      INNER JOIN WAE_TERA_INDICES I   ");
		sb.append("\n         ON T.DATABASENAME = I.DATABASENAME  ");
		sb.append("\n        AND T.TABLENAME = I.TABLENAME  ");
		sb.append("\n        AND C.COLUMNNAME = I.COLUMNNAME  ");
		sb.append("\n  WHERE T.DB_CONN_TRG_ID = '"+dbConnTgtId+"'     ");
		sb.append("\n  GROUP BY  S.DB_SCH_ID , T.TABLENAME,I.INDEXNAME ,S.DB_CONN_TRG_ID, I.INDEXTYPE ,I.COLUMNNAME");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatidxcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_IDX_COL        ");
		sb.append("\n SELECT S.DB_SCH_ID      ");
		sb.append("\n      , T.TABLENAME  AS DBC_TBL_NM ");
		sb.append("\n      , I.COLUMNNAME AS DBC_IDX_COL_NM  ");
		sb.append("\n      , I.INDEXNAME  AS DBC_IDX_NM ");
		sb.append("\n      , NULL AS DBC_IDX_COL_KOR_NM ");
		sb.append("\n      , 1      AS VERS     ");
		sb.append("\n      , NULL   AS REG_TYP  ");
		sb.append("\n      , SYSDATE AS REG_DTM ");
		sb.append("\n      , NULL   AS REG_USER ");
		sb.append("\n      , NULL   AS UPD_DTM  ");
		sb.append("\n      , NULL   AS UPD_USER ");
		sb.append("\n      , NULL   AS DESCN    ");
		sb.append("\n      , NULL   AS DDL_IDX_COL_ID  ");
		sb.append("\n      , NULL   AS PDM_IDX_COL_ID  ");
		sb.append("\n      , I.COLUMNPOSITION  AS ORD ");
		sb.append("\n      , NULL AS SORT_TYPE  ");
		sb.append("\n      , CASE C.COLUMNTYPE WHEN 'BF' THEN 'BYTE'         ");
		sb.append("\n                          WHEN 'BV' THEN 'VARBYTE'         ");
		sb.append("\n                          WHEN 'CF' THEN 'CHAR'         ");
		sb.append("\n                          WHEN 'CV' THEN 'VARCHAR'         ");
		sb.append("\n                          WHEN 'D' THEN 'DECIMAL'         ");
		sb.append("\n                          WHEN 'DA' THEN 'DATE'         ");
		sb.append("\n                          WHEN 'F' THEN 'FLOAT'         ");
		sb.append("\n                          WHEN 'I1' THEN 'BYTEINT'         ");
		sb.append("\n                          WHEN 'I2' THEN 'SMALLINT'         ");
		sb.append("\n                          WHEN 'I8' THEN 'BIGINT'         ");
		sb.append("\n                          WHEN 'I' THEN 'INTEGER'         ");
		sb.append("\n                          WHEN 'AT' THEN 'TIME'         ");
		sb.append("\n                          WHEN 'TS' THEN 'TIMESTAMP'         ");
		sb.append("\n                          WHEN 'TZ' THEN 'TIME WITH TIME ZONE'         ");
		sb.append("\n                          WHEN 'SZ' THEN 'TIMESTAMP WITH TIME ZONE'         ");
		sb.append("\n                          WHEN 'YR' THEN 'INTERVAL YEAR'         ");
		sb.append("\n                          WHEN 'YM' THEN 'INTERVAL YEAR TO MONTH'         ");
		sb.append("\n                          WHEN 'MO' THEN 'INTERVAL MONTH'         ");
		sb.append("\n                          WHEN 'DY' THEN 'INTERVAL DAY'         ");
		sb.append("\n                          WHEN 'DH' THEN 'INTERVAL DAY TO HOUR'         ");
		sb.append("\n                          WHEN 'DM' THEN 'INTERVAL DAY TO MINUTE'         ");
		sb.append("\n                          WHEN 'DS' THEN 'INTERVAL DAY TO SECOND'         ");
		sb.append("\n                          WHEN 'HR' THEN 'INTERVAL HOUR'         ");
		sb.append("\n                          WHEN 'HM' THEN 'INTERVAL HOUR TO MINUTE'         ");
		sb.append("\n                          WHEN 'HS' THEN 'INTERVAL HOUR TO SECOND'         ");
		sb.append("\n                          WHEN 'MI' THEN 'INTERVAL MINUTE'         ");
		sb.append("\n                          WHEN 'MS' THEN 'INTERVAL MINUTE TO SECOND'         ");
		sb.append("\n                          WHEN 'SC' THEN 'INTERVAL SECOND'         ");
		sb.append("\n                          WHEN 'BO' THEN 'BLOB'         ");
		sb.append("\n                          WHEN 'CO' THEN 'CLOB'         ");
		sb.append("\n                          WHEN 'PD' THEN 'PERIOD(DATE)'         ");
		sb.append("\n                          WHEN 'PM' THEN 'PERIOD(TIMESTAMP WITH TIME ZONE)'         ");
		sb.append("\n                          WHEN 'PS' THEN 'PERIOD(TIMESTAMP)'         ");
		sb.append("\n                          WHEN 'PT' THEN 'PERIOD(TIME)'         ");
		sb.append("\n                          WHEN 'PZ' THEN 'PERIOD(TIME WITH TIME ZONE)'         ");
		sb.append("\n                          WHEN 'UT' THEN 'User Defined DataType'         ");
		sb.append("\n                          WHEN '++' THEN 'TD_ANYTYPE'         ");
		sb.append("\n                          WHEN 'N' THEN 'NUMBER'         ");
		sb.append("\n                          WHEN 'A1' THEN 'ARRAY'         ");
		sb.append("\n                          WHEN 'AN' THEN 'ARRAY (multidimensional)'         ");
		sb.append("\n                          WHEN 'JN' THEN 'JSON'         ");
		sb.append("\n                          WHEN 'VA' THEN 'TD_VALIST'         ");
		sb.append("\n                          WHEN 'XM' THEN 'XML'         ");
		sb.append("\n                          ELSE '<Unknown>'   END     ");
		sb.append("\n        AS DATA_TYPE        ");
		sb.append("\n      , NULL AS DATA_PNUM   ");
		sb.append("\n      , C.COLUMNLENGTH  AS LENGTH  ");
		sb.append("\n      , NULL AS DATA_PNT           ");
		sb.append("\n      , '' AS IDXCOL_DQ_EXP_YN              ");
		sb.append("\n      , '' AS DDL_IDX_COL_LNM_ERR_EXS       ");
		sb.append("\n      , '' AS DDL_IDX_COL_ORD_ERR_EXS       ");
		sb.append("\n      , '' AS DDL_IDX_COL_SORT_TYPE_ERR_EXS ");
		sb.append("\n      , '' AS DDL_IDX_COL_EXTNC_EXS         ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_EXS           ");
		sb.append("\n      , '' AS DDL_IDX_COL_ERR_CONTS         ");
		sb.append("\n      , '' AS PDM_IDX_COL_LNM_ERR_EXS       ");
		sb.append("\n      , '' AS PDM_IDX_COL_ORD_ERR_EXS       ");
		sb.append("\n      , '' AS PDM_IDX_COL_SORT_TYPE_ERR_EXS ");
		sb.append("\n      , '' AS PDM_IDX_COL_EXTNC_EXS         ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_EXS           ");
		sb.append("\n      , '' AS PDM_IDX_COL_ERR_CONTS         ");
		sb.append("\n FROM WAE_TERA_TABLES T  ");
		sb.append("\n      INNER JOIN WAE_TERA_COLUMNS C  ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = C.DB_CONN_TRG_ID  ");
		sb.append("\n        AND T.DATABASENAME  = C.DATABASENAME   ");
		sb.append("\n        AND T.TABLENAME  = C.TABLENAME    ");
		sb.append("\n        AND C.COLUMNTYPE IS NOT NULL    ");
		sb.append("\n      INNER JOIN (   ");
		sb.append(getdbconnsql());
		sb.append("\n                  ) S ");
		sb.append("\n         ON T.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID  ");
		sb.append("\n        AND UPPER(T.DATABASENAME ) = UPPER(S.DB_CONN_TRG_PNM)  ");
		sb.append("\n        AND UPPER(T.CREATORNAME) = UPPER(S.DB_SCH_PNM)    ");
		sb.append("\n      INNER JOIN WAE_TERA_INDICES I   ");
		sb.append("\n         ON T.DATABASENAME = I.DATABASENAME  ");
		sb.append("\n        AND T.TABLENAME = I.TABLENAME  ");
		sb.append("\n        AND C.COLUMNNAME = I.COLUMNNAME  ");
		sb.append("\n  WHERE T.DB_CONN_TRG_ID = '"+dbConnTgtId+"'     ");

		return setExecuteUpdate_Org(sb.toString());

	}

  	private int[] executeBatch() throws SQLException
	{
		return executeBatch(false);
	}

  	private int[] executeBatch(boolean execYN) throws SQLException
	{
		int[] i = pst_org.executeBatch();

		if(execYN) {
			pst_org.clearBatch();
		} else {
			rs.close();
			pst_org.close();
		}

		return i;
	}

  	private boolean getSaveResult(int cnt) throws SQLException
	{
		boolean result = false;
		pst_org.addBatch();
		result = true;

		return result;
	}

  	private int setExecuteUpdate_Org(String query_tgt) throws SQLException
	{
  		logger.debug(query_tgt);
		pst_org = con_org.prepareStatement(query_tgt);
		int cnt = pst_org.executeUpdate();
		pst_org.close();

		return cnt;
	}

  	private ResultSet getResultSet_Org(String query_org) throws SQLException
	{
  		logger.debug(query_org);
		pst_org = con_org.prepareStatement(query_org);
		rs = pst_org.executeQuery();

		return rs;
	}

  	private ResultSet getResultSet(String query_tgt) throws SQLException
	{
  		logger.debug(query_tgt);
  		pst_tgt = con_tgt.prepareStatement(query_tgt);
		pst_tgt.setFetchSize(10000);
  		rs = pst_tgt.executeQuery();

		return rs;
	}
}