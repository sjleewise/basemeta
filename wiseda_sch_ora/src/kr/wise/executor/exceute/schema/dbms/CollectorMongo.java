/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CollectorOracle.java
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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;
import org.bson.BSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.sun.corba.se.spi.ior.ObjectId;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CollectorOracle.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 6:07:16
 * </PRE>
 */
public class CollectorMongo {

	private static final Logger logger = Logger.getLogger(CollectorMongo.class);

	private Connection con_org = null;
	private MongoClient con_tgt = null;

	private PreparedStatement pst_org = null;
	private PreparedStatement pst_tgt = null;

	private ResultSet rs = null;

	private List<TargetDbmsDM> targetDblist;

	private String dbName = null;
	private String dbNm = null;
	private String schemaName = null;
	private String schemaId = null;

	private String dbType = null;

	private int execCnt = 100; 

	private HashMap<String, String> resultMap = new LinkedHashMap<String,String>();

	public CollectorMongo() {

	}

	/** insomnia */
	public CollectorMongo(Connection source, MongoClient target, List<TargetDbmsDM> lsitdm) {
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
			int lidx = targetDb.getConnect_string().lastIndexOf("/");
			this.dbName = targetDb.getConnect_string().substring(lidx+1);
			this.dbNm = targetDb.getDbms_enm();
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


			con_org.commit();

		}

		result = true;

		return result;

	}
	
	private String evaluate(String key,String value) {
		String type = "OBJECT"; 	//default
		
		if(key.equals("_id"))
			return "ID";
		
		if(value == null || value.equals(""))
			return "EMPTY";
		
		if(Pattern.matches("[0-9]*\\.?[0-9]*", value))
			return "NUMBER";
		
		//패턴 추후 추가
		
		//yyyy-mm-dd
		if(Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$", value))
			return "DATE";
		
		//yyyy-mm-dd HH:MM
		if(Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$", value))
			return "DATE";
		
		//yyyy-mm-dd hh:ii:ss
		if(Pattern.matches("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$", value))
			return "DATE";
		
		//yyyy-mm-dd 오전 HH:MM
		if(Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])[ ㄱ-ㅎ가-힣 ]*(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$", value))
			return "DATE";
		
		//yyyy-mm-dd 오전 h:ii:ss
		if(Pattern.matches("^\\d{4}[- /.]*(0[1-9]|1[012])[- /.]*(0[1-9]|[12][0-9]|3[01])[ ㄱ-ㅎ가-힣 ]*([0-9]|1[1-2]):([0-5][0-9]):([0-5][0-9])$", value))
			return "DATE";
		
		//yyyy-mm-dd 오전 HH:MM:ss
		if(Pattern.matches("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])[ ㄱ-ㅎ가-힣 ]*(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", value))
			return "DATE";
		
		//영문, 숫자 조합
		if(Pattern.matches("^[a-zA-Z0-9\\(\\)\\_\\-\\.\\,\\+\\*\\!\\@\\#\\$\\%\\^\\&\\/\\s\\:\\;\\~\\·]*$", value))
			return "STRING";
		
		//한글, 숫자 조합
		if(Pattern.matches("^[ㄱ-ㅎ가-힣0-9\\(\\)\\_\\-\\.\\,\\+\\*\\!\\@\\#\\$\\%\\^\\&\\/\\s\\:\\;\\~\\·]*$", value))
			return "STRING";
		
		return type;
	}

	private int setParam(BSONObject document, Set<String> keySet, String tblNm, int count) throws SQLException {
		int rsCnt = 1;
		int rsGetCnt = 1;
		int cnt = count;
		
		for(String key:keySet) {
			String value =document.get(key).toString();
			String type = evaluate(key,value);
			
			pst_org.setString(rsGetCnt++, dbNm);
			pst_org.setString(rsGetCnt++, schemaName);
			pst_org.setString(rsGetCnt++, tblNm);
			pst_org.setString(rsGetCnt++, key);
			pst_org.setString(rsGetCnt++, type);
			
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
		
		return cnt;
	}
	
	private int setParameter(String tblNm,int count) throws SQLException{
		int rsCnt=1;
		int rsGetCnt=1;
		int cnt=count;
		Set<String> resultKeys = resultMap.keySet();
		
		for(String key:resultKeys){
			String type = resultMap.get(key);
			
			pst_org.setString(rsGetCnt++,dbNm);
			pst_org.setString(rsGetCnt++,schemaName);
			pst_org.setString(rsGetCnt++, tblNm);
			pst_org.setString(rsGetCnt++, key);
			pst_org.setString(rsGetCnt++, type);
			
			getSaveResult(rsCnt);
			if(rsCnt==execCnt)
			{
				executeBatch(true);
				rsCnt=0;
			}
			rsCnt++;
			rsGetCnt=1;
			cnt++;
		}
		
		return cnt;
	}
	
	private void getKeyValue(BSONObject document){
		Set<String> keySet = document.keySet();
		
		for(String key:keySet){
			Object value = document.get(key);
			
			if(value instanceof BasicDBList){
				inCaseArray(key,(BasicDBList)value);		//Value가 Array인 경우
			}
			else if(value instanceof BasicDBObject){
				inCaseJSON(key,(BasicDBObject)value);		//Value가 JSON인 경우
			}
			else if(value instanceof Number){
				resultMap.put(key, "Number");				//Value가 Number인 경우
			}
			else if(value instanceof ObjectId){
				resultMap.put(key,"id");					//Value가 id인 경우
			}
			else if(value instanceof String){
				resultMap.put(key, "String");				//Value가 String인 경우
			}
			else if(value instanceof Date){
				resultMap.put(key, "Date");					//Value가 날짜인 경우
			}
			else{
				resultMap.put(key,"String");				//나머지 타입들
			}
		}
	}
	
	private void getKeyValue(String parent,BSONObject document){		//key값과 같이 리턴해준다
		Set<String> keySet = document.keySet();
		
		for(String key : keySet){
			Object value = document.get(key);
			
			if(value instanceof BasicDBList){
				inCaseArray(parent+key,(BasicDBList)value);		//Value가 Array인 경우
			}
			else if(value instanceof BasicDBObject){
				inCaseJSON(parent+key,(BasicDBObject)value);	//Value가 JSON인 경우
			}
			else if(value instanceof Number){
				resultMap.put(parent+key,"Number");				//Value가 Number인 경우
			}
			else if(value instanceof ObjectId){
				resultMap.put(parent+key,"id");					//Value가 id인 경우
			}
			else if(value instanceof String){
				resultMap.put(parent+key, "String");			//Value가 String인 경우
			}
			else if(value instanceof Date){
				resultMap.put(parent+key,"Date");				//Value가 날짜인 경우
			}
			else{
				resultMap.put(parent+key,"String");				//나머지 타입들
			}
			
		}
	}
	
	private void inCaseArray(String key,BasicDBList value){		//Array의 경우 . 으로 구분
		for(Object elem :value){
			if(elem instanceof BasicDBObject){
				getKeyValue(key+".",(BasicDBObject)elem);		//배열의 원소가 JSON인 경우
			}
			else if(elem instanceof BasicDBList){
				inCaseArray(key+".",(BasicDBList)elem);			//배열의 원소가 Array인 경우
			}
			else		
				resultMap.put(key, "Array");					//배열의 원소가 일반 Value인 경우
		}
	}
	
	private void inCaseJSON(String key,BasicDBObject value){	//JSON의 경우 - 으로 구분
		getKeyValue(key+"-",value);
	}
	
	/**
	 * DBC DBA_TAB_COLUMNS 저장
	 * @return int 수행결과 건수
	 * @throws SQLException
	 */
	private int insertDBA_TAB_COLUMNS() throws SQLException
	{
		StringBuffer sb = new StringBuffer();

		int cnt = 0;
		
		DBCollection collection = null;
		DBCursor cursor = null;
		DB db = null;
		Set<String> collectionNames = null;
		
		db = con_tgt.getDB(dbName);
		
		collectionNames = db.getCollectionNames();
		
		Iterator<String> it = collectionNames.iterator(); // Iterator(반복자) 생성
		
		int rsCnt = 1;
		int rsGetCnt = 1;
		
		sb.append("\nINSERT INTO WAE_MONGO_COL (         ");
		sb.append("\n        DB_NM                        ");
		sb.append("\n        ,SCH_NM                     ");
		sb.append("\n        ,TBL_NM                ");
		sb.append("\n        ,COL_NM               ");
		sb.append("\n        ,DATA_TYPE                 ");
		sb.append("\n) VALUES (                                ");
		sb.append("\n  ?,?,?,?,?                               ");
		sb.append("\n)                                         ");
		
		pst_org = con_org.prepareStatement(sb.toString());
		pst_org.clearParameters();
		
//		while(it.hasNext()) {
//			String tblNm = it.next();
//			
//			collection = db.getCollection(tblNm);
//
//			cursor= collection.find();
//			
//			BSONObject document = cursor.next();
//			
//			//key값들만 저장
//			Set<String> keySet = document.keySet();
//			
//			cnt = setParam(document, keySet, tblNm, cnt);
//			
//			Set<String> keySet2;
//			
//			while(cursor.hasNext()) {
//				document=cursor.next();
//				keySet2 = document.keySet();
//
//				if(!keySet.containsAll(keySet2)) {
//					keySet2.removeAll(keySet);
//					cnt = setParam(document, keySet2, tblNm, cnt);
//				}
//
//			}
//		}
		
		while(it.hasNext()) {
			resultMap.clear();
			String tblNm = it.next();			
			collection = db.getCollection(tblNm);
			cursor= collection.find();			
			BSONObject document;
			
			while(cursor.hasNext()) {
				document=cursor.next();
				getKeyValue(document);
			}
			setParameter(tblNm,cnt);
		}
		
		executeBatch(true);

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

		int cnt = 0;
		
		DB db = null;
		Set<String> collectionNames = null;
		
		db = con_tgt.getDB(dbName);
		
		collectionNames = db.getCollectionNames();
		
		Iterator<String> it = collectionNames.iterator(); // Iterator(반복자) 생성

		
		
		sb.append("\nINSERT INTO WAE_MONGO_TBL (    ");
		sb.append("\n      DB_NM                     ");
		sb.append("\n      , SCH_NM                     ");
		sb.append("\n      , TBL_NM                     ");
		sb.append("\n) VALUES (                              ");
		sb.append("\n  ?,?,?                                 ");
		sb.append("\n)                                       ");

		pst_org = con_org.prepareStatement(sb.toString());
		pst_org.clearParameters();
		
		int rsGetCnt = 1;
		int rsCnt = 1;
		
		while (it.hasNext()) {
			pst_org.setString(rsGetCnt++, dbNm);
			pst_org.setString(rsGetCnt++, schemaName);
			pst_org.setString(rsGetCnt++, it.next());
			
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
		
		executeBatch(true);
		
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

		sb.setLength(0);

		sb.append("\nINSERT INTO WAE_MONGO_SCH (       ");
		sb.append("\n   DB_NM                       ");
		sb.append("\n     ,SCH_NM                         ");
		sb.append("\n     ,DB_SCH_ID                         ");
		sb.append("\n     ,PWD                        ");
		sb.append("\n) VALUES (                        ");
		sb.append("\n  ?,?,?,?            ");
		sb.append("\n)                                 ");

		pst_org = con_org.prepareStatement(sb.toString());
		pst_org.clearParameters();
		
		int rsGetCnt = 1;
		
		pst_org.setString(rsGetCnt++, dbNm);
		pst_org.setString(rsGetCnt++, schemaName);
		pst_org.setString(rsGetCnt++, schemaId);
		pst_org.setString(rsGetCnt++, "");
		
		getSaveResult(1);
		
		executeBatch(true);
		
		
		if(pst_tgt != null) pst_tgt.close();
		if(pst_org != null) pst_org.close();
		if(rs != null) rs.close();

		return 1;
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
		sb.append("\nINSERT INTO WAE_MONGO_DB (");
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
		sb.append("\n  AND  UPPER(A.DB_CONN_TRG_PNM)   =    UPPER('").append(dbNm).append("')") ;

		return setExecuteUpdate_Org(sb.toString());
	}

	/**  insomnia */
	private int deleteSchema() throws Exception {
		int result = 0 ;

		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  DELETE FROM WAE_MONGO_DB ");
		strSQL.append("\n   WHERE DB_NM = '"+dbNm+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MONGO_DB : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MONGO_SCH ");
		strSQL.append("\n   WHERE DB_NM = '"+dbNm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		//result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MONGO_SCH : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MONGO_COL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbNm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_MONGO_COL : " + result);

		strSQL.setLength(0);
		strSQL.append("\n  DELETE FROM WAE_MONGO_TBL ");
		strSQL.append("\n   WHERE DB_NM = '"+dbNm+"' ");
//		strSQL.append("\n     AND SCH_NM = '"+schemaName+"' ");

		result = setExecuteUpdate_Org(strSQL.toString());
		logger.debug("delete WAE_ORA_TBL : " + result);
		
		return result;
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

		try {
			pst_org.addBatch();
		} catch(Exception e) {
			System.out.println(e);
		}

		result = true;

		return result;
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
		//System.out.println("안되는겨?");
		int[] i = pst_org.executeBatch();
		//System.out.println("되는겨?");

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
		sb.append("\n      , A.TBL_NM              AS DBC_TBL_NM                            ");
		sb.append("\n      , A.TBL_NM              AS DBC_TBL_KOR_NM                        ");
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
		sb.append("\n      , ''                    AS ROW_EACNT                             ");
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
		sb.append("\n   FROM WAE_MONGO_TBL A                                                 ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MONGO_DB B                                             ");
		sb.append("\n     ON A.DB_NM = B.DB_NM                                              ");
		sb.append("\n   LEFT OUTER JOIN (                                                   ");
		sb.append("\n                    SELECT DB_NM, SCH_NM, TBL_NM, COUNT(*) AS COL_CNT  ");
		sb.append("\n                      FROM WAE_MONGO_COL                                ");
		sb.append("\n                     GROUP BY DB_NM, SCH_NM, TBL_NM                    ");
		sb.append("\n                   ) E                                                 ");
		sb.append("\n     ON A.DB_NM = E.DB_NM                                              ");
		sb.append("\n    AND A.SCH_NM = E.SCH_NM                                            ");
		sb.append("\n    AND A.TBL_NM = E.TBL_NM                                            ");
		sb.append("\n    WHERE A.DB_NM = '"+dbName+"'                                        ");

		return setExecuteUpdate_Org(sb.toString());

	}

	/**  insomnia
	 * @throws Exception */
	private int insertwatcol() throws Exception {
//		String tdbnm = targetDbmsDM.getDbms_enm();

		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAT_DBC_COL                                   ");
		sb.append("\n SELECT S.DB_SCH_ID                                ");
		sb.append("\n      , A.TBL_NM                                              ");
		sb.append("\n      , A.COL_NM                                              ");
		sb.append("\n      , A.COL_NM                                              ");
		sb.append("\n      , '1'                                                   ");
		sb.append("\n      , NULL                                                   ");
		sb.append("\n      , SYSDATE                                               ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , DATA_TYPE                                             ");
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
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n      , ''                                                    ");
		sb.append("\n   FROM WAE_MONGO_COL A                                        ");
		sb.append("\n  INNER JOIN (                                ");
		sb.append(getdbconnsql());
		sb.append("\n  ) S                               ");
		sb.append("\n     ON A.DB_NM = S.DB_CONN_TRG_PNM                   ");
		sb.append("\n    AND A.SCH_NM = S.DB_SCH_PNM                             ");
		sb.append("\n  INNER JOIN WAE_MONGO_DB B                                     ");
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

}
