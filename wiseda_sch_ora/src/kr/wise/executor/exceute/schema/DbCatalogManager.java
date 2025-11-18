/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbCatalogManager.java
 * 2. Package : wiseitech.wisedq.executor.schema
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 17. 오후 5:48:09
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 17. :            : 신규 개발.
 */
package kr.wise.executor.exceute.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dm.TargetDbmsDM;
import kr.wise.executor.exceute.schema.dbms.CollectorAltibaseV6;
import kr.wise.executor.exceute.schema.dbms.CollectorDb2Udb;
import kr.wise.executor.exceute.schema.dbms.CollectorHanaDb;
import kr.wise.executor.exceute.schema.dbms.CollectorHive;
import kr.wise.executor.exceute.schema.dbms.CollectorInformix;
import kr.wise.executor.exceute.schema.dbms.CollectorMaria;
import kr.wise.executor.exceute.schema.dbms.CollectorMongo;
import kr.wise.executor.exceute.schema.dbms.CollectorMsAccess;
import kr.wise.executor.exceute.schema.dbms.CollectorMssql;
import kr.wise.executor.exceute.schema.dbms.CollectorMysql;
import kr.wise.executor.exceute.schema.dbms.CollectorOracle;
import kr.wise.executor.exceute.schema.dbms.CollectorPostgreSQL;
import kr.wise.executor.exceute.schema.dbms.CollectorSybaseAse;
import kr.wise.executor.exceute.schema.dbms.CollectorSybaseIq;
import kr.wise.executor.exceute.schema.dbms.CollectorTeradata;
import kr.wise.executor.exceute.schema.dbms.CollectorTibero;
import kr.wise.executor.exceute.schema.dbms.cubrid.CollectorCubridService;
import kr.wise.executor.exceute.schema.dbms.cubrid.impl.CollectorCubrid9Impl;
import kr.wise.executor.exceute.schema.dbms.cubrid.impl.CollectorCubridImpl;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DbCatalogManager.java
 * 3. Package  : wiseitech.wisedq.executor.schema
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 17. 오후 5:48:09
 * </PRE>
 */
public class DbCatalogManager {

	private static final Logger logger = Logger.getLogger(DbCatalogManager.class);

	private TargetDbmsDM targetDbmsDM = null;

	private Connection daCon = null;
	private Connection tgtCon = null;
	private PreparedStatement pst_org = null;
	private MongoClient mongoCon = null;

	private final String TYPE_ORACLE    = "ORA";
	private final String TYPE_ALTIBASE  = "ALT";	
	private final String TYPE_MSSQL     = "MSQ";
	private final String TYPE_MYSQL     = "MYS";
	
    private final String TYPE_DB2 		= "DB2"; // DB2 AS400  ->>  그냥 db2로 변경
    private final String TYPE_UDB       = "UDB"; // DB2 UDB
    
    private final String TYPE_SYBASEASE = "SYA";
    private final String TYPE_SYBASEIQ  = "SYQ";
    private final String TYPE_TIBERO    = "TIB";
    private final String TYPE_TERADATA  = "TER";
    private final String TYPE_INFORMIX  = "IFX";
    private final String TYPE_POSTGRES  = "POS";
    private final String TYPE_CUBRID    = "CBR";
    private final String TYPE_MARIA     = "MRA";
    private final String TYPE_MSACCESS  = "MSA";
	private final String TYPE_HANADB    = "HAN";

	private final String TYPE_HIVE = "HIV";
	private final String TYPE_MONGODB = "MDB";

	public DbCatalogManager(TargetDbmsDM targetDbmsDM) {
		this.targetDbmsDM = targetDbmsDM;
	}

	public void launch() throws Exception {
		boolean result = false;

		try{
			daCon  = ConnectionHelper.getDAConnection();
			//mongodb일 경우 분기
			if(!TYPE_MONGODB.equals(targetDbmsDM.getDbms_type_cd()))
				tgtCon = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			else
				mongoCon = ConnectionHelper.getMongoConnection(targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());

			List<TargetDbmsDM> lsitdm = selectTargetDbmsList(targetDbmsDM.getDbms_id());

			daCon.setAutoCommit(false);

			  // INFORMIX 접속 URL 생성
			  // DBMS 관리 접속URL에 ID, PW 관리 안함  
			  if("IFX".equals(this.targetDbmsDM.getDbms_type_cd())){
				// jdbc:informix-sqli://10.228.32.109:9088/atmsd:informixserver=;user=informix;password=Wkehd0(
				String sConnTrgLnkUrl = this.targetDbmsDM.getConnect_string();
				if(sConnTrgLnkUrl.toUpperCase().indexOf("USER") < 0   ){
				sConnTrgLnkUrl = sConnTrgLnkUrl+";user="+this.targetDbmsDM.getDb_user()+";password="+this.targetDbmsDM.getDb_pwd();
			}
				this.tgtCon = ConnectionHelper.getConnection(this.targetDbmsDM.getJdbc_driver(), sConnTrgLnkUrl, this.targetDbmsDM.getDb_user(), this.targetDbmsDM.getDb_pwd());
				  
			}else {
				this.tgtCon = ConnectionHelper.getConnection(this.targetDbmsDM.getJdbc_driver(), this.targetDbmsDM.getConnect_string(), this.targetDbmsDM.getDb_user(), this.targetDbmsDM.getDb_pwd());
			}		
			  
			//DBMS 별 버전  
			String dbmsVersCd = UtilString.null2Blank(targetDbmsDM.getDbms_vers_cd());  
			
			//DBMS Type에 따라 처리기능 호출한다.
			if (TYPE_ORACLE.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorOracle collector = new CollectorOracle(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			}
			//ALTIBASE 스키마 수집
			else if (TYPE_ALTIBASE.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorAltibaseV6 collector = new CollectorAltibaseV6(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			}
			//MYSQL 스키마 수집
			else if (TYPE_MYSQL.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorMysql collector = new CollectorMysql(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			}
			
			else if (TYPE_MARIA.equals(targetDbmsDM.getDbms_type_cd()) ) {
				CollectorMaria collector = new CollectorMaria(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				
				deleteWat();
				result = collector.saveWat(targetDbmsDM);
				
				daCon.commit();
			}
			
			 else if (TYPE_MSACCESS.equals(targetDbmsDM.getDbms_type_cd())) {
				
				CollectorMsAccess collector = new CollectorMsAccess(daCon, tgtCon, lsitdm);  
				result = collector.doProcess();  
				
				deleteWat();
				result = collector.saveWat(targetDbmsDM); 
				
				daCon.commit();
				
			}
				
			 else if (TYPE_HANADB.equals(targetDbmsDM.getDbms_type_cd())) {
					
					CollectorHanaDb collector = new CollectorHanaDb(daCon, tgtCon, lsitdm);  
					result = collector.doProcess();  
					
					deleteWat();
					result = collector.saveWat(targetDbmsDM); 
					
					daCon.commit();
				
			}
			
			else if (TYPE_TIBERO.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorTibero collector = new CollectorTibero(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			} else if (TYPE_MSSQL.equals(targetDbmsDM.getDbms_type_cd())) {

				//타겟 DB에서 WAE에 적재한다.
				CollectorMssql collector = new CollectorMssql(daCon, tgtCon, lsitdm);
				result = collector.doProcess(); 
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM); 
				daCon.commit();
			}
			
			else if (TYPE_UDB.equals(this.targetDbmsDM.getDbms_type_cd())||TYPE_DB2.equals(this.targetDbmsDM.getDbms_type_cd()))
			{
				CollectorDb2Udb collector = new CollectorDb2Udb(this.daCon, this.tgtCon, this.targetDbmsDM);
				result = collector.doProcess();
				deleteWat();
				result = collector.saveWat(this.targetDbmsDM);
				this.daCon.commit();
			}
			
			else if (TYPE_SYBASEASE.equals(this.targetDbmsDM.getDbms_type_cd()))
			{
				CollectorSybaseAse collector = new CollectorSybaseAse(this.daCon, this.tgtCon, this.targetDbmsDM);
				result = collector.doProcess();
				deleteWat();
				result = collector.saveWat(this.targetDbmsDM);
				this.daCon.commit();
			}
			else if (TYPE_SYBASEIQ.equals(this.targetDbmsDM.getDbms_type_cd()))
			{
			    CollectorSybaseIq collector = new CollectorSybaseIq(this.daCon, this.tgtCon, this.targetDbmsDM);
			    result = collector.doProcess();
			    deleteWat();
			    result = collector.saveWat(this.targetDbmsDM);
			    this.daCon.commit();
			}
			else if (TYPE_TERADATA.equals(this.targetDbmsDM.getDbms_type_cd()))
			{
				CollectorTeradata collector = new CollectorTeradata(this.daCon, this.tgtCon, this.targetDbmsDM);
				result = collector.doProcess();
				deleteWat();
				result = collector.saveWat(this.targetDbmsDM);
				this.daCon.commit();
			}
			else if (TYPE_INFORMIX.equals(this.targetDbmsDM.getDbms_type_cd()))
			{
				CollectorInformix collector = new CollectorInformix(this.daCon, this.tgtCon, this.targetDbmsDM);
				result = collector.doProcess();
				deleteWat();
				result = collector.saveWat(this.targetDbmsDM);
				this.daCon.commit();
				
			}else if (TYPE_POSTGRES.equals(this.targetDbmsDM.getDbms_type_cd())) { 
				
					CollectorPostgreSQL collector = new CollectorPostgreSQL(this.daCon, this.tgtCon, lsitdm);
					result = collector.doProcess();
					deleteWat();
					result = collector.saveWat(this.targetDbmsDM);
					this.daCon.commit();	
					
			}else if (TYPE_CUBRID.equals(this.targetDbmsDM.getDbms_type_cd())) { 
				
				CollectorCubridService collector = null;
				
				if(dbmsVersCd.equals("C09")){ 
					logger.debug("c09 : "+dbmsVersCd);
					collector = new CollectorCubrid9Impl(this.daCon, this.tgtCon, lsitdm);   
				}else if(dbmsVersCd.equals("C08")){
					logger.debug("c08 : "+dbmsVersCd);
					collector = new CollectorCubrid9Impl(this.daCon, this.tgtCon, lsitdm);  
				}else{//C10
					logger.debug("c10 : "+dbmsVersCd);
					collector = new CollectorCubridImpl(this.daCon, this.tgtCon, lsitdm);  
				}
				
				result = collector.doProcess();
				
				deleteWat();
				
				result = collector.saveWat(this.targetDbmsDM);
				
				this.daCon.commit();			
			}	
			//HIVE 스키마 수집
			else if (TYPE_HIVE.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorHive collector = new CollectorHive(daCon, tgtCon, lsitdm);
				result = collector.doProcess();
				logger.debug("doProcess end");
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			}
			//MONGODB 스키마 수집
			else if (TYPE_MONGODB.equals(targetDbmsDM.getDbms_type_cd())) {
				//타겟 DB에서 WAE에 적재한다.
				CollectorMongo collector = new CollectorMongo(daCon, mongoCon, lsitdm);
				result = collector.doProcess();
				logger.debug("doProcess end");
				//WAT 삭제 (DB인스턴스만)
				deleteWat();
				//WAE에서 WAT로 적재한다.
				result = collector.saveWat(targetDbmsDM);
				daCon.commit();
			}	      

			//분석 실시 (DB 인스턴스 별로 갭분석 한다.)
			if (result) {
				//GAP 분석 실행
				analysysGap();
				daCon.commit();
				result = true;
			}
			
//			이력 적재
			if(result){
				DbCatalogHistManager histManager = new DbCatalogHistManager(daCon, targetDbmsDM);
				// histManager.saveWah();
				daCon.commit();
			}
			
			
		} catch(Exception e) {
			daCon.rollback();
			logger.error(e);
			throw e;
		} finally {
//			if(colRs   != null) try { colRs.close(); } catch(Exception igonred) {}
//			if(tblRs   != null) try { tblRs.close(); } catch(Exception igonred) {}
			if(pst_org != null) {try { pst_org.close(); } catch(Exception igonred) {}}
//			if(tblStmt != null) try { tblStmt.close(); } catch(Exception igonred) {}
			if(tgtCon  != null) {try { tgtCon.close(); } catch(Exception igonred) {}}
			if(daCon   != null) {try { daCon.close(); } catch(Exception igonred) {}}
		}
	}


	/** DBC 갭분석 실시한다.  insomnia
	 * @throws Exception */
	private void analysysGap() throws Exception {
		//각종 ID 업데이트
		int result = 0;

		//WAT 테이블 ID 업데이트
		result = updatewattblmetaid();
//		result = updatewattblmetaid2();
		logger.debug("updatewattblmetaid2 : " + result + " OK!!");
//		result = updatewattblmetaid3();
//		logger.debug("updatewattblmetaid3 : " + result + " OK!!");

		//WAT 컬럼 ID 업데이트
		result = updatewatcolmetaid();
		logger.debug("updatewatcolmetaid : " + result + " OK!!");
//		result = updatewatcolmetaid2();
//		logger.debug("updatewatcolmetaid2 : " + result + " OK!!");
//		result = updatewatcolmetaid3();
//		logger.debug("updatewatcolmetaid3 : " + result + " OK!!");

		//WAT 테이블 에러 업데이트
//		result = initWatTblErr();
		result = updateWatTblErr();
		logger.debug("updateWatTblErr : " + result + " OK!!");
		result = updateWatColErr();
		logger.debug("updateWatColErr : " + result + " OK!!");
		
		//WAT 인덱스 ID 업데이트
		result = updateWatIdxMetaId();
		logger.debug("updateWatIdxMetaId : " + result + " OK!!");

		//WAT 인덱스 컬럼 ID 업데이트
		result = updateWatIdxColMetaId();
		logger.debug("updateWatIdxColMetaId : " + result + " OK!!");
		
		//WAT 인덱스컬럼 에러 업데이트
		result = updateWatIdxColErr();
		logger.debug("updateWatIdxColErr : " + result + " OK!!");
		
		//WAT 인덱스 에러 업데이트
		result = updateWatIdxErr();
		logger.debug("updateWatIdxErr : " + result + " OK!!");
		
	}
	
	
	
	/** @return insomnia
	 * @throws Exception */
	private int updateWatColErr() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n    BEGIN ");
		strSQL.append("\n    --DDL 컬럼 오류 초기화 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET DDL_COL_ERR_EXS = 'N' ");
		strSQL.append("\n         , PDM_COL_ERR_EXS = 'N' ");
		strSQL.append("\n         , COL_ERR_CONTS = NULL ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --* DDL 컬럼 오류여부 업데이트 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n       AND ( NVL(DDL_COL_EXTNC_YN, 'N') != 'Y'    -- 컬럼에서는 'Y'가 정상, 'N'오류 ");
		strSQL.append("\n           OR DDL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR DDL_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n      ) ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --* PDM 컬럼 오류여부 업데이트 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n       AND ( NVL(PDM_COL_EXTNC_EXS, 'N') != 'Y'    -- 컬럼에서는 'Y'가 정상, 'N'오류 ");
		strSQL.append("\n           OR PDM_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n           OR PDM_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n      ) ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --* COLUMN ERROR COMMNET 생성 ");
		strSQL.append("\n    --* DDL COLUMN ");
		strSQL.append("\n    --* DDL 컬럼 미존재 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_컬럼미존재]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND NVL(DDL_COL_EXTNC_YN, 'N') != 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL순서에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_순서에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDLPK여부에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_PK여부에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDLPK순서에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_PK순서에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL널여부에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_널여부에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDLDEFAULT에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_DEFAULT에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL데이터타입에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_데이터타입에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL데이터길이에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_데이터길이에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL데이터소수점에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[DDL_데이터소수점에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND DDL_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델컬럼존재유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델컬럼미존재]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND NVL(PDM_COL_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델순서에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델순서에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델PK여부에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델PK여부에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델PK순서에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델PK순서에러유무]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델널여부에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델널여부에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델DEFAULT에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델DEFAULT에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델데이터타입에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델데이터타입에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델데이터길이에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델데이터길이에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --물리모델데이터소수점에러유무 ");
		strSQL.append("\n    UPDATE WAT_DBC_COL BB ");
		strSQL.append("\n       SET COL_ERR_CONTS = COL_ERR_CONTS || '[물리모델데이터소수점에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    ) ");
		strSQL.append("\n      AND PDM_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    END; ");

		return setExecuteUpdate_Org(strSQL.toString());
	}


	/** @return insomnia
	 * @throws Exception */
	private int updateWatTblErr() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n    BEGIN ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL ");
		strSQL.append("\n            SET   DDL_TBL_ERR_EXS = NULL ");
		strSQL.append("\n                , DDL_TBL_ERR_CD  = NULL ");
		strSQL.append("\n                , DDL_TBL_ERR_DESCN  = NULL ");
		strSQL.append("\n                , DDL_COL_ERR_EXS  = NULL ");
		strSQL.append("\n                , DDL_COL_ERR_CD  = NULL ");
		strSQL.append("\n                , DDL_COL_ERR_DESCN  = NULL ");
		strSQL.append("\n                , PDM_TBL_ERR_EXS  = NULL ");
		strSQL.append("\n                , PDM_TBL_ERR_CD  = NULL ");
		strSQL.append("\n                , PDM_TBL_ERR_DESCN  = NULL ");
		strSQL.append("\n                , PDM_COL_ERR_EXS  = NULL ");
		strSQL.append("\n                , PDM_COL_ERR_CD  = NULL ");
		strSQL.append("\n                , PDM_COL_ERR_DESCN  = NULL ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL 테이블 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL ");
		strSQL.append("\n           SET DDL_TBL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_TBL_ERR_CD = 'N' ");
		strSQL.append("\n             , DDL_TBL_ERR_DESCN = DDL_TBL_ERR_DESCN || '[DDL테이블미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND NVL(DDL_TBL_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --PDM 테이블 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL ");
		strSQL.append("\n           SET PDM_TBL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_TBL_ERR_CD = 'N' ");
		strSQL.append("\n             , PDM_TBL_ERR_DESCN = PDM_TBL_ERR_DESCN || '[PDM테이블미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND NVL(PDM_TBL_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL 컬럼 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_컬럼미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND NVL(A.DDL_COL_EXTNC_YN, 'N') != 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL순서에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_순서에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDLPK여부에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_PK여부에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDLPK여부에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_PK순서에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL널여부에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_널여부에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDLDEFAULT에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_DEFAULT에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL데이터타입에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_데이터타입에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL데이터길이에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_데이터길이에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL데이터소수점에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET DDL_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , DDL_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_COL_ERR_DESCN = DDL_COL_ERR_DESCN|| '[DDL_데이터소수점에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.DDL_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --PDM 컬럼 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델컬럼미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND NVL(A.PDM_COL_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델순서에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델순서에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델PK여부에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델PK여부에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_PK_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델PK순서에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델PK순서에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_PK_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델널여부에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델널여부에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_NULL_YN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델DEFAULT에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델DEFAULT에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_DEFLT_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델데이터타입에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델데이터타입에러유무]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_DATA_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델데이터길이에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델데이터길이에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_DATA_LEN_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n       --물리모델데이터소수점에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_TBL BB ");
		strSQL.append("\n           SET PDM_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n             , PDM_COL_ERR_CD = '' ");
		strSQL.append("\n             , PDM_COL_ERR_DESCN = PDM_COL_ERR_DESCN|| '[물리모델데이터소수점에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_TBL_NM = BB.DBC_TBL_NM ");
		strSQL.append("\n                  AND A.PDM_DATA_PNT_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n    END; ");

		return setExecuteUpdate_Org(strSQL.toString());
	}


	/**  insomnia
	 * @throws Exception */
	private int updatewatcolmetaid() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_COL T                                                            ");
		sb.append("\n    SET (PDM_COL_ID, DDL_COL_ID, ITM_ID                                        ");
		sb.append("\n       	, DDL_COL_EXTNC_YN ");
		sb.append("\n       	, DDL_ORD_ERR_EXS ");
		sb.append("\n       	, DDL_PK_YN_ERR_EXS ");
		sb.append("\n       	, DDL_PK_ORD_ERR_EXS ");
		sb.append("\n       	, DDL_NULL_YN_ERR_EXS ");
		sb.append("\n       	, DDL_DEFLT_ERR_EXS ");
		sb.append("\n       	, DDL_CMMT_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_TYPE_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_LEN_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_PNT_ERR_EXS ");
		sb.append("\n       	, PDM_COL_EXTNC_EXS ");
		sb.append("\n       	, PDM_ORD_ERR_EXS ");
		sb.append("\n       	, PDM_PK_YN_ERR_EXS ");
		sb.append("\n       	, PDM_PK_ORD_ERR_EXS ");
		sb.append("\n       	, PDM_NULL_YN_ERR_EXS ");
		sb.append("\n       	, PDM_DEFLT_ERR_EXS ");
		sb.append("\n       	, PDM_CMMT_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_TYPE_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_LEN_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_PNT_ERR_EXS ");

		sb.append("\n    	) = ( ");
		sb.append("\n               SELECT C.PDM_COL_ID, B.DDL_COL_ID, C.SDITM_ID  ");
		sb.append("\n               	, 'Y' AS DDL_COL_EXTNC_YN ");
		sb.append("\n               	, CASE WHEN NVL(T.ORD, 0) = NVL(B.COL_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_YN, 'N') = NVL(B.PK_YN, 'N') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_ORD, 0 ) = NVL(B.PK_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.NULL_YN, 'Y') = NVL(B.NONUL_YN, 'N'  ) THEN 'Y' ELSE 'N' END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DEFLT_VAL, '_') = NVL(B.DEFLT_VAL, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n					, CASE WHEN NVL(REPLACE(REPLACE(T.DEFLT_VAL, CHR(10), ''), CHR(13), ' '), '_') = NVL(REPLACE(REPLACE(B.DEFLT_VAL, CHR(10), ''), CHR(13), ' '), '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DESCN, '_') = NVL(B.OBJ_DESCN, '_') THEN 'N' ELSE 'Y' END ");
		//NUMERIC 과 NUMBER 동일하게 봐야 한다..
		sb.append("\n                , CASE WHEN UPPER(NVL(T.DATA_TYPE, '_')) = 'NUMERIC' THEN (CASE WHEN UPPER(NVL(B.DATA_TYPE, '_')) = 'NUMBER' THEN 'N'  END ) ");
		sb.append("\n               	       ELSE (CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(B.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ) END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(B.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN T.DATA_PNUM IS NULL THEN ");
		sb.append("\n                   		CASE WHEN NVL(T.DATA_LEN, 0) = NVL(B.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n                 		ELSE ");
		sb.append("\n                   		CASE WHEN NVL(T.DATA_PNUM, 0) = NVL(B.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n                 	  END ");
		sb.append("\n               	, CASE WHEN NVL(T.DATA_PNT, 0) = NVL(B.DATA_SCAL, 0) THEN 'N' ELSE 'Y' END ");

		sb.append("\n               	, CASE WHEN C.PDM_TBL_ID IS NOT NULL THEN 'Y' ELSE 'N' END AS PDM_COL_EXTNC_YN ");
		sb.append("\n               	, CASE WHEN NVL(T.ORD, 0) = NVL(C.COL_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_YN, 'N') = NVL(C.PK_YN, 'N') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_ORD, 0 ) = NVL(C.PK_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.NULL_YN, 'Y') = NVL(C.NONUL_YN, 'N'  ) THEN 'Y' ELSE 'N' END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DEFLT_VAL, '_') = NVL(C.DEFLT_VAL, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n					, CASE WHEN NVL(REPLACE(REPLACE(T.DEFLT_VAL, CHR(10), ''), CHR(13), ' '), '_') = NVL(REPLACE(REPLACE(C.DEFLT_VAL, CHR(10), ''), CHR(13), ' '), '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DESCN, '_') = NVL(C.OBJ_DESCN, '_') THEN 'N' ELSE 'Y' END ");
		//NUMERIC 과 NUMBER 동일하게 봐야 한다..
		sb.append("\n                , CASE WHEN UPPER(NVL(T.DATA_TYPE, '_')) = 'NUMERIC' THEN (CASE WHEN UPPER(NVL(C.DATA_TYPE, '_')) = 'NUMBER' THEN 'N'  END ) ");
		sb.append("\n               	       ELSE (CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(C.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ) END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(C.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN T.DATA_PNUM IS NULL THEN ");
		sb.append("\n               	    CASE WHEN NVL(T.DATA_LEN, 0) = NVL(C.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	  ELSE ");
		sb.append("\n               	    CASE WHEN NVL(T.DATA_PNUM, 0) = NVL(C.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	  END ");
		sb.append("\n               	, CASE WHEN NVL(T.DATA_PNT, 0) = NVL(C.DATA_SCAL, 0) THEN 'N' ELSE 'Y' END ");

		sb.append("\n               FROM WAM_DDL_TBL A                           ");
		sb.append("\n              INNER JOIN WAM_DDL_COL B                      ");
		sb.append("\n                 ON A.DDL_TBL_ID = B.DDL_TBL_ID             ");
		sb.append("\n                AND B.REG_TYP_CD IN ('C', 'U')              ");
		sb.append("\n               LEFT OUTER JOIN WAM_PDM_COL C                ");
		sb.append("\n                 ON C.PDM_TBL_ID = A.PDM_TBL_ID             ");
		sb.append("\n                AND B.DDL_COL_PNM = C.PDM_COL_PNM           ");
		sb.append("\n                AND C.REG_TYP_CD IN ('C', 'U')              ");
		sb.append("\n              WHERE A.REG_TYP_CD IN ('C', 'U')              ");
		sb.append("\n                AND T.DB_SCH_ID = A.DB_SCH_ID               ");
		sb.append("\n                AND T.DBC_TBL_NM = A.DDL_TBL_PNM            ");
		sb.append("\n                AND T.DBC_COL_NM = B.DDL_COL_PNM            ");
		sb.append("\n           )                                                ");
		sb.append("\n  WHERE EXISTS (                                                                    ");
		sb.append("\n                 SELECT 1                                                           ");
		sb.append("\n                   FROM WAM_DDL_TBL A                                               ");
		sb.append("\n                  INNER JOIN WAM_DDL_COL B                                          ");
		sb.append("\n                     ON A.DDL_TBL_ID = A.DDL_TBL_ID                                 ");
		sb.append("\n                    AND B.REG_TYP_CD IN ('C', 'U')                                  ");
		sb.append("\n                  WHERE A.REG_TYP_CD IN ('C','U')                                   ");
		sb.append("\n                    AND T.DB_SCH_ID = A.DB_SCH_ID                                   ");
		sb.append("\n                    AND T.DBC_TBL_NM = A.DDL_TBL_PNM                                ");
		sb.append("\n                    AND T.DBC_COL_NM = B.DDL_COL_PNM                                ");
		sb.append("\n               )                                                                    ");
		sb.append("\n   AND T.DB_SCH_ID IN ( 									");
		sb.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A 			");
		sb.append("\n         JOIN WAA_DB_SCH B 								");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID 			");
		sb.append("\n          AND B.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		sb.append("\n          AND A.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n   ) 														");
		return setExecuteUpdate_Org(sb.toString());

	}
	
	/**  insomnia
	 * @throws Exception */
	private int updatewatcolmetaid2() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_COL T                                                            ");
		sb.append("\n    SET (DDL_COL_ID                                        ");
		sb.append("\n       	, DDL_COL_EXTNC_YN ");
		sb.append("\n       	, DDL_ORD_ERR_EXS ");
		sb.append("\n       	, DDL_PK_YN_ERR_EXS ");
		sb.append("\n       	, DDL_PK_ORD_ERR_EXS ");
		sb.append("\n       	, DDL_NULL_YN_ERR_EXS ");
		sb.append("\n       	, DDL_DEFLT_ERR_EXS ");
		sb.append("\n       	, DDL_CMMT_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_TYPE_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_LEN_ERR_EXS ");
		sb.append("\n       	, DDL_DATA_PNT_ERR_EXS ");

		sb.append("\n    	) = ( ");
		sb.append("\n               SELECT B.DDL_COL_ID  ");
		sb.append("\n               	, 'Y' AS DDL_COL_EXTNC_YN ");
		sb.append("\n               	, CASE WHEN NVL(T.ORD, 0) = NVL(B.COL_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_YN, 'N') = NVL(B.PK_YN, 'N') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_ORD, 0 ) = NVL(B.PK_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.NULL_YN, 'Y') = NVL(B.NONUL_YN, 'N'  ) THEN 'Y' ELSE 'N' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DEFLT_VAL, '_') = NVL(B.DEFLT_VAL, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DESCN, '_') = NVL(B.OBJ_DESCN, '_') THEN 'N' ELSE 'Y' END ");
		//NUMERIC 과 NUMBER 동일하게 봐야 한다..
		sb.append("\n                , CASE WHEN UPPER(NVL(T.DATA_TYPE, '_')) = 'NUMERIC' THEN (CASE WHEN UPPER(NVL(B.DATA_TYPE, '_')) = 'NUMBER' THEN 'N'  END ) ");
		sb.append("\n               	       ELSE (CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(B.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ) END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(B.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN T.DATA_PNUM IS NULL THEN ");
		sb.append("\n                   		CASE WHEN NVL(T.DATA_LEN, 0) = NVL(B.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n                 		ELSE ");
		sb.append("\n                   		CASE WHEN NVL(T.DATA_PNUM, 0) = NVL(B.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n                 	  END ");
		sb.append("\n               	, CASE WHEN NVL(T.DATA_PNT, 0) = NVL(B.DATA_SCAL, 0) THEN 'N' ELSE 'Y' END ");

		sb.append("\n               FROM WAM_DDL_TBL A                           ");
		sb.append("\n              INNER JOIN WAM_DDL_COL B                      ");
		sb.append("\n                 ON A.DDL_TBL_ID = B.DDL_TBL_ID             ");
		sb.append("\n                AND B.REG_TYP_CD IN ('C', 'U')              ");
		sb.append("\n              WHERE A.REG_TYP_CD IN ('C', 'U')              ");
		sb.append("\n                AND T.DB_SCH_ID = A.DB_SCH_ID               ");
		sb.append("\n                AND T.DBC_TBL_NM = A.DDL_TBL_PNM            ");
		sb.append("\n                AND T.DBC_COL_NM = B.DDL_COL_PNM            ");
		sb.append("\n           )                                                ");
		sb.append("\n  WHERE EXISTS (                                                                    ");
		sb.append("\n                 SELECT 1                                                           ");
		sb.append("\n                   FROM WAM_DDL_TBL A                                               ");
		sb.append("\n                  INNER JOIN WAM_DDL_COL B                                          ");
		sb.append("\n                     ON A.DDL_TBL_ID = A.DDL_TBL_ID                                 ");
		sb.append("\n                    AND B.REG_TYP_CD IN ('C', 'U')                                  ");
		sb.append("\n                  WHERE A.REG_TYP_CD IN ('C','U')                                   ");
		sb.append("\n                    AND T.DB_SCH_ID = A.DB_SCH_ID                                   ");
		sb.append("\n                    AND T.DBC_TBL_NM = A.DDL_TBL_PNM                                ");
		sb.append("\n                    AND T.DBC_COL_NM = B.DDL_COL_PNM                                ");
		sb.append("\n               )                                                                    ");
		sb.append("\n   AND T.DB_SCH_ID IN ( 									");
		sb.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A 			");
		sb.append("\n         JOIN WAA_DB_SCH B 								");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID 			");
		sb.append("\n          AND B.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		sb.append("\n          AND A.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n   ) 														");
		return setExecuteUpdate_Org(sb.toString());

	}
	
	/**  insomnia
	 * @throws Exception */
	private int updatewatcolmetaid3() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_COL T                                                            ");
		sb.append("\n    SET (PDM_COL_ID, ITM_ID                                        ");
		sb.append("\n       	, PDM_COL_EXTNC_EXS ");
		sb.append("\n       	, PDM_ORD_ERR_EXS ");
		sb.append("\n       	, PDM_PK_YN_ERR_EXS ");
		sb.append("\n       	, PDM_PK_ORD_ERR_EXS ");
		sb.append("\n       	, PDM_NULL_YN_ERR_EXS ");
		sb.append("\n       	, PDM_DEFLT_ERR_EXS ");
		sb.append("\n       	, PDM_CMMT_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_TYPE_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_LEN_ERR_EXS ");
		sb.append("\n       	, PDM_DATA_PNT_ERR_EXS ");

		sb.append("\n    	) = ( ");
		sb.append("\n               SELECT C.PDM_COL_ID, C.SDITM_ID  ");
		sb.append("\n               	, CASE WHEN C.PDM_TBL_ID IS NOT NULL THEN 'Y' ELSE 'N' END AS PDM_COL_EXTNC_YN ");
		sb.append("\n               	, CASE WHEN NVL(T.ORD, 0) = NVL(C.COL_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_YN, 'N') = NVL(C.PK_YN, 'N') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.PK_ORD, 0 ) = NVL(C.PK_ORD, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.NULL_YN, 'Y') = NVL(C.NONUL_YN, 'N'  ) THEN 'Y' ELSE 'N' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DEFLT_VAL, '_') = NVL(C.DEFLT_VAL, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN NVL(T.DESCN, '_') = NVL(C.OBJ_DESCN, '_') THEN 'N' ELSE 'Y' END ");
		//NUMERIC 과 NUMBER 동일하게 봐야 한다..
		sb.append("\n                , CASE WHEN UPPER(NVL(T.DATA_TYPE, '_')) = 'NUMERIC' THEN (CASE WHEN UPPER(NVL(C.DATA_TYPE, '_')) = 'NUMBER' THEN 'N'  END ) ");
		sb.append("\n               	       ELSE (CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(C.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ) END ");
//		sb.append("\n               	, CASE WHEN NVL(T.DATA_TYPE, '_') = NVL(C.DATA_TYPE, '_') THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	, CASE WHEN T.DATA_PNUM IS NULL THEN ");
		sb.append("\n               	    CASE WHEN NVL(T.DATA_LEN, 0) = NVL(C.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	  ELSE ");
		sb.append("\n               	    CASE WHEN NVL(T.DATA_PNUM, 0) = NVL(C.DATA_LEN, 0) THEN 'N' ELSE 'Y' END ");
		sb.append("\n               	  END ");
		sb.append("\n               	, CASE WHEN NVL(T.DATA_PNT, 0) = NVL(C.DATA_SCAL, 0) THEN 'N' ELSE 'Y' END ");

		sb.append("\n               FROM WAM_PDM_TBL A                           ");
		sb.append("\n              INNER JOIN WAA_SUBJ_DB_SCH_MAP SUBJSCHMAP              ");
		sb.append("\n                 ON SUBJSCHMAP.SUBJ_ID = A.SUBJ_ID                  ");
		sb.append("\n                AND SUBJSCHMAP.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD')  ");
		sb.append("\n                AND SUBJSCHMAP.REG_TYP_CD IN ('C', 'U')                   ");
		sb.append("\n              INNER JOIN WAM_PDM_COL C                      ");
		sb.append("\n                 ON C.PDM_TBL_ID = A.PDM_TBL_ID                                 ");
		sb.append("\n                AND C.REG_TYP_CD IN ('C', 'U')                                  ");
		sb.append("\n              WHERE A.REG_TYP_CD IN ('C', 'U')              "); //WAT_DBC_COL T
		sb.append("\n                AND T.DB_SCH_ID = SUBJSCHMAP.DB_SCH_ID               ");
		sb.append("\n                AND T.DBC_TBL_NM = A.PDM_TBL_PNM            ");
		sb.append("\n                AND T.DBC_COL_NM = C.PDM_COL_PNM            ");
		sb.append("\n           )                                                ");
		sb.append("\n  WHERE EXISTS (                                                                    ");
		sb.append("\n                 SELECT 1                                                           ");
		sb.append("\n               FROM WAM_PDM_TBL A                                         ");
		sb.append("\n              INNER JOIN WAA_SUBJ_DB_SCH_MAP SUBJSCHMAP                   ");
		sb.append("\n                 ON SUBJSCHMAP.SUBJ_ID = A.SUBJ_ID                        ");
		sb.append("\n                AND SUBJSCHMAP.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD')  ");
		sb.append("\n                AND SUBJSCHMAP.REG_TYP_CD IN ('C', 'U')                   ");
		sb.append("\n              INNER JOIN WAM_PDM_COL C                                    ");
		sb.append("\n                 ON C.PDM_TBL_ID = A.PDM_TBL_ID                           ");
		sb.append("\n                AND C.REG_TYP_CD IN ('C', 'U')                            ");
		sb.append("\n              WHERE A.REG_TYP_CD IN ('C', 'U')                            "); //WAT_DBC_COL T
		sb.append("\n                AND T.DB_SCH_ID = SUBJSCHMAP.DB_SCH_ID                    ");
		sb.append("\n                AND T.DBC_TBL_NM = A.PDM_TBL_PNM                          ");
		sb.append("\n                AND T.DBC_COL_NM = C.PDM_COL_PNM                          ");
		sb.append("\n               )                                                          ");
		sb.append("\n   AND T.DB_SCH_ID IN ( 									");
		sb.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A 			");
		sb.append("\n         JOIN WAA_DB_SCH B 								");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID 			");
		sb.append("\n          AND B.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		sb.append("\n          AND A.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n   ) 														");
		return setExecuteUpdate_Org(sb.toString());

	}


	/**  insomnia
	 * @throws Exception */
	private int updatewattblmetaid() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_TBL A                                                              ");
		sb.append("\n    SET (PDM_TBL_ID, DDL_TBL_ID, SUBJ_ID, DDL_TBL_EXTNC_EXS , PDM_TBL_EXTNC_EXS ) =   ");
		sb.append("\n                                  (                                                   ");
		sb.append("\n                                     SELECT C.PDM_TBL_ID, B.DDL_TBL_ID, C.SUBJ_ID     ");
		sb.append("\n                                         , 'Y', CASE WHEN C.PDM_TBL_ID IS NOT NULL THEN 'Y' ELSE 'N' END     ");
		sb.append("\n                                       FROM WAM_DDL_TBL B                             ");
		sb.append("\n                                       LEFT OUTER JOIN WAM_PDM_TBL C                  ");
		sb.append("\n                                         ON B.PDM_TBL_ID = C.PDM_TBL_ID               ");
		sb.append("\n                                        AND C.REG_TYP_CD IN ('C', 'U')                ");
		sb.append("\n                                      WHERE B.REG_TYP_CD IN ('C', 'U')                ");
		sb.append("\n                                        AND A.DB_SCH_ID = B.DB_SCH_ID                 ");
		sb.append("\n                                        AND A.DBC_TBL_NM = B.DDL_TBL_PNM              ");
		sb.append("\n                                   )                                                  ");
		sb.append("\n  WHERE EXISTS (                                                                      ");
		sb.append("\n                 SELECT 1                                                             ");
		sb.append("\n                   FROM WAM_DDL_TBL B                                                 ");
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID                                     ");
		sb.append("\n                    AND A.DBC_TBL_NM = B.DDL_TBL_PNM                                  ");
		sb.append("\n               )                                                                      ");
		sb.append("\n   AND A.DB_CONN_TRG_ID  =  '"+tdbid+"'                                  ");
		return setExecuteUpdate_Org(sb.toString());

	}
	
	/**  insomnia
	 * @throws Exception */
	private int updatewattblmetaid2() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_TBL A                                                                 ");
		sb.append("\n    SET (DDL_TBL_ID, DDL_TBL_EXTNC_EXS ) =                                            ");
		sb.append("\n                                  (                                                   ");
		sb.append("\n                                     SELECT B.DDL_TBL_ID                              ");
		sb.append("\n                                         , 'Y'                                        ");
		sb.append("\n                                       FROM WAM_DDL_TBL B                             ");
		sb.append("\n                                      WHERE B.REG_TYP_CD IN ('C', 'U')                ");
		sb.append("\n                                        AND A.DB_SCH_ID = B.DB_SCH_ID                 ");
		sb.append("\n                                        AND A.DBC_TBL_NM = B.DDL_TBL_PNM              ");
		sb.append("\n                                   )                                                  ");
		sb.append("\n  WHERE EXISTS (                                                                      ");
		sb.append("\n                 SELECT 1                                                             ");
		sb.append("\n                   FROM WAM_DDL_TBL B                                                 ");
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID                                     ");
		sb.append("\n                    AND A.DBC_TBL_NM = B.DDL_TBL_PNM                                  ");
		sb.append("\n               )                                                                      ");
		sb.append("\n   AND A.DB_CONN_TRG_ID  =  '"+tdbid+"'                                               ");
		return setExecuteUpdate_Org(sb.toString());

	}
	
	/**  insomnia
	 * @throws Exception */
	private int updatewattblmetaid3() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_TBL A                                                                          ");
		sb.append("\n    SET (PDM_TBL_ID, SUBJ_ID, PDM_TBL_EXTNC_EXS ) =                                            ");
		sb.append("\n                                  (                                                            ");
		sb.append("\n                                     SELECT C.PDM_TBL_ID, C.SUBJ_ID                            ");
		sb.append("\n                                         , 'Y'                                                 ");
		sb.append("\n                                       FROM WAM_PDM_TBL C                                      ");
		sb.append("\n                                      INNER JOIN WAA_SUBJ_DB_SCH_MAP SUBJSCHMAP                ");
		sb.append("\n                                      ON SUBJSCHMAP.SUBJ_ID = C.SUBJ_ID                        ");
		sb.append("\n                                     AND SUBJSCHMAP.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD')  ");
		sb.append("\n                                     AND SUBJSCHMAP.REG_TYP_CD IN ('C', 'U')                   ");
		sb.append("\n                                      WHERE C.REG_TYP_CD IN ('C', 'U')                         ");
		sb.append("\n                                        AND A.DB_SCH_ID = SUBJSCHMAP.DB_SCH_ID                 ");
		sb.append("\n                                        AND A.DBC_TBL_NM = C.PDM_TBL_PNM                       ");
		sb.append("\n                                   )                                                           ");
		sb.append("\n  WHERE EXISTS (                                                                               ");
		sb.append("\n                 SELECT 1                                                                      ");
		sb.append("\n                                       FROM WAM_PDM_TBL C                                      ");
		sb.append("\n                                      INNER JOIN WAA_SUBJ_DB_SCH_MAP SUBJSCHMAP                ");
		sb.append("\n                                      ON SUBJSCHMAP.SUBJ_ID = C.SUBJ_ID                        ");
		sb.append("\n                                     AND SUBJSCHMAP.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD')  ");
		sb.append("\n                                     AND SUBJSCHMAP.REG_TYP_CD IN ('C', 'U')                   ");
		sb.append("\n                                      WHERE C.REG_TYP_CD IN ('C', 'U')                         ");
		sb.append("\n                                        AND A.DB_SCH_ID = SUBJSCHMAP.DB_SCH_ID                 ");
		sb.append("\n                                        AND A.DBC_TBL_NM = C.PDM_TBL_PNM                       ");
		sb.append("\n               )                                                                               ");
		sb.append("\n   AND A.DB_CONN_TRG_ID  =  '"+tdbid+"'                                                        ");
		return setExecuteUpdate_Org(sb.toString());

	}
	
	


	/**  insomnia
	 * @throws Exception */
	private int updateWatIdxMetaId() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_IDX T                                   ");
		sb.append("\n    SET ( DDL_IDX_ID, DDL_IDX_CMPS_CONTS, DDL_IDX_EXTNC_EXS )               ");
		sb.append("\n        = (                                             ");
		sb.append("\n           SELECT A.DDL_IDX_ID, A.SCRT_INFO ,'Y'            ");
		sb.append("\n             FROM WAM_DDL_IDX A                         ");
		sb.append("\n            INNER JOIN WAM_DDL_TBL B                    ");
		sb.append("\n               ON A.DDL_TBL_ID = B.DDL_TBL_ID           ");
		sb.append("\n              AND B.REG_TYP_CD IN ('C', 'U')            ");
		sb.append("\n            WHERE A.REG_TYP_CD IN ('C', 'U')            ");
		sb.append("\n              AND T.DB_SCH_ID = B.DB_SCH_ID             ");
		sb.append("\n              AND T.DBC_IDX_NM = A.DDL_IDX_PNM          ");
		sb.append("\n          )                                             ");
		sb.append("\n  WHERE EXISTS (                                        ");
		sb.append("\n                SELECT 1                                ");
		sb.append("\n                  FROM WAM_DDL_IDX A                    ");
		sb.append("\n                 INNER JOIN WAM_DDL_TBL B               ");
		sb.append("\n                    ON A.DDL_TBL_ID = B.DDL_TBL_ID      ");
		sb.append("\n                 WHERE A.REG_TYP_CD IN ('C', 'U')       ");
		sb.append("\n                   AND T.DB_SCH_ID = B.DB_SCH_ID        ");
		sb.append("\n                   AND T.DBC_IDX_NM = A.DDL_IDX_PNM     ");
		sb.append("\n               )                                        ");
		sb.append("\n   AND T.DB_SCH_ID IN ( 									");
		sb.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A 			");
		sb.append("\n         JOIN WAA_DB_SCH B 								");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID 			");
		sb.append("\n          AND B.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		sb.append("\n          AND A.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n   ) 														");

		return setExecuteUpdate_Org(sb.toString());

	}
	
	
	/** @return insomnia
	 * @throws Exception */
	private int updateWatIdxErr() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n    BEGIN ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX ");
		strSQL.append("\n            SET   DDL_IDX_ERR_EXS = NULL ");
		strSQL.append("\n                , DDL_IDX_ERR_CD  = NULL ");
		strSQL.append("\n                , DDL_IDX_ERR_DESCN  = NULL ");
		strSQL.append("\n                , DDL_IDX_COL_ERR_EXS  = NULL ");
		strSQL.append("\n                , DDL_IDX_COL_ERR_CD  = NULL ");
		strSQL.append("\n                , DDL_IDX_COL_ERR_DESCN  = NULL ");
		strSQL.append("\n                , PDM_IDX_ERR_EXS  = NULL ");
		strSQL.append("\n                , PDM_IDX_ERR_CD  = NULL ");
		strSQL.append("\n                , PDM_IDX_ERR_DESCN  = NULL ");
		strSQL.append("\n                , PDM_IDX_COL_ERR_EXS  = NULL ");
		strSQL.append("\n                , PDM_IDX_COL_ERR_CD  = NULL ");
		strSQL.append("\n                , PDM_IDX_COL_ERR_DESCN  = NULL ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL 인덱스 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX ");
		strSQL.append("\n           SET DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , DDL_IDX_ERR_CD = 'N' ");
		strSQL.append("\n             , DDL_IDX_ERR_DESCN = DDL_IDX_ERR_DESCN || '[DDL인덱스미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND NVL(DDL_IDX_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n        ; ");
		
		strSQL.append("\n        --DDL 인덱스 테이블 스페이스명에러 ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX  BB");
		strSQL.append("\n           SET BB.DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , DDL_IDX_ERR_CD = 'N' ");
		strSQL.append("\n             , BB.DDL_IDX_ERR_DESCN = BB.DDL_IDX_ERR_DESCN || '[DDL인덱스_스페이스명에러]' ");
		strSQL.append("\n        WHERE BB.DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS (SELECT 1  ");
		strSQL.append("\n                             FROM WAM_DDL_IDX A   ");
		strSQL.append("\n                                     INNER JOIN WAA_TBL_SPAC B   ");
		strSQL.append("\n                                        ON A.IDX_SPAC_ID = B.DB_TBL_SPAC_ID  ");
		strSQL.append("\n                                       AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ");
		strSQL.append("\n                                       AND B.REG_TYP_CD IN ('C','U')  ");
		strSQL.append("\n                                       AND B.TBL_SPAC_TYP_CD = 'I'  ");
		strSQL.append("\n                           WHERE A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                              AND BB.DDL_IDX_ID = A.DDL_IDX_ID  ");
		strSQL.append("\n                              AND NVL(BB.DBC_TBL_SPAC_NM, '_') != NVL(B.DB_TBL_SPAC_PNM,'_') ");
		strSQL.append("\n                          ) ");
		strSQL.append("\n          AND BB.DDL_IDX_ID IS NOT NULL  ");
		strSQL.append("\n        ; ");
		strSQL.append("\n        --DDL 인덱스 인덱스유형GAP]  ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX A ");
		strSQL.append("\n           SET A.DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_CD = 'N' ");
		strSQL.append("\n             , A.DDL_IDX_ERR_DESCN = A.DDL_IDX_ERR_DESCN || '[DDL인덱스_유형에러]' ");
		strSQL.append("\n        WHERE A.DB_SCH_ID IN ( SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                                                 INNER JOIN WAA_DB_SCH B ");
		strSQL.append("\n                                                     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                                                   AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                                                   AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                                        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                                           AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                                           AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                                         ) ");
		strSQL.append("\n          AND EXISTS (SELECT 1 FROM WAM_DDL_IDX M  ");
		strSQL.append("\n                           WHERE A.DDL_IDX_ID = M.DDL_IDX_ID  ");
		strSQL.append("\n                              AND ( NVL(A.PK_YN, 'N') != NVL(M.PK_IDX_YN,'N') ");
		strSQL.append("\n                                      OR  NVL(A.UQ_YN, 'N') != NVL(M.UK_IDX_YN,'N')  )");
		strSQL.append("\n                              AND M.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                          ) ");
		strSQL.append("\n          AND A.DDL_IDX_ID IS NOT NULL  ");
		strSQL.append("\n        ; ");
		
		
		
//		strSQL.append("\n        --DDL 인덱스 한글명 상이  ");
//		strSQL.append("\n        UPDATE WAT_DBC_IDX A ");
//		strSQL.append("\n           SET A.DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_CD = 'N' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_DESCN = A.DDL_IDX_ERR_DESCN || '[DDL인덱스_한글명GAP]' ");
//		strSQL.append("\n        WHERE A.DB_SCH_ID IN ( SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
//		strSQL.append("\n                                                 INNER JOIN WAA_DB_SCH B ");
//		strSQL.append("\n                                                     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
//		strSQL.append("\n                                                   AND B.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                                                   AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n                                        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
//		strSQL.append("\n                                           AND A.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                                           AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n                                         ) ");
//		strSQL.append("\n          AND EXISTS (SELECT 1 FROM WAM_DDL_IDX M  ");
//		strSQL.append("\n                           WHERE A.DDL_IDX_ID = M.DDL_IDX_ID  ");
//		strSQL.append("\n                              AND NVL(A.DBC_IDX_KOR_NM, 'N') != NVL(M.DDL_IDX_LNM,'N') ");
//		strSQL.append("\n                              AND M.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                          ) ");
//		strSQL.append("\n          AND A.DDL_IDX_ID IS NOT NULL  ");
//		strSQL.append("\n        ; ");
		
//		strSQL.append("\n        --DDL 인덱스 설명 상이  ");
//		strSQL.append("\n        UPDATE WAT_DBC_IDX A ");
//		strSQL.append("\n           SET A.DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_CD = 'N' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_DESCN = A.DDL_IDX_ERR_DESCN || '[DDL인덱스_설명GAP]' ");
//		strSQL.append("\n        WHERE A.DB_SCH_ID IN ( SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
//		strSQL.append("\n                                                 INNER JOIN WAA_DB_SCH B ");
//		strSQL.append("\n                                                     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
//		strSQL.append("\n                                                   AND B.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                                                   AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n                                        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
//		strSQL.append("\n                                           AND A.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                                           AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n                                         ) ");
//		strSQL.append("\n          AND EXISTS (SELECT 1 FROM WAM_DDL_IDX M  ");
//		strSQL.append("\n                           WHERE A.DDL_IDX_ID = M.DDL_IDX_ID  ");
//		strSQL.append("\n                              AND NVL(A.DESCN, 'N') != NVL(M.OBJ_DESCN,'N') ");
//		strSQL.append("\n                              AND M.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                          ) ");
//		strSQL.append("\n          AND A.DDL_IDX_ID IS NOT NULL  ");
//		strSQL.append("\n        ; ");
		
		strSQL.append("\n        --DDL 인덱스 컬럼건수 상이  ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX A ");
		strSQL.append("\n           SET A.DDL_IDX_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , A.DDL_IDX_ERR_CD = 'N' ");
		strSQL.append("\n             , A.DDL_IDX_ERR_DESCN = A.DDL_IDX_ERR_DESCN || '[DDL인덱스_컬럼건수GAP]' ");
		strSQL.append("\n        WHERE A.DB_SCH_ID IN ( SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                                                 INNER JOIN WAA_DB_SCH B ");
		strSQL.append("\n                                                     ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                                                   AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                                                   AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                                        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                                           AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                                           AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                                         ) ");
		strSQL.append("\n          AND EXISTS (SELECT 1  ");
		strSQL.append("\n                            FROM WAM_DDL_IDX M  ");
		strSQL.append("\n                                    INNER JOIN (SELECT DDL_IDX_ID, COUNT(DDL_IDX_COL_ID) AS  DDL_IDX_COL_CNT ");
		strSQL.append("\n                                                      FROM WAM_DDL_IDX_COL   ");
		strSQL.append("\n                                                     WHERE REG_TYP_CD IN ('C','U')    ");
		strSQL.append("\n                                                    GROUP BY DDL_IDX_ID ) C ");
		strSQL.append("\n                                        ON M.DDL_IDX_ID = C.DDL_IDX_ID ");
		strSQL.append("\n                           WHERE A.DDL_IDX_ID = M.DDL_IDX_ID  ");
		strSQL.append("\n                              AND NVL(A.COL_EACNT, 0) != NVL(C.DDL_IDX_COL_CNT,0) ");
		strSQL.append("\n                              AND M.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                          ) ");
		strSQL.append("\n          AND A.DDL_IDX_ID IS NOT NULL  ");
		strSQL.append("\n        ; ");
		
		
		//여기부터 입니다.
//		strSQL.append("\n        --DDL 인덱스 컬럼 한글명 오류 여부 ");
//		strSQL.append("\n        UPDATE WAT_DBC_IDX BB ");
//		strSQL.append("\n           SET DDL_IDX_COL_ERR_EXS = 'Y' ");
////		strSQL.append("\n             , DDL_IDX_COL_ERR_CD = '' ");
//		strSQL.append("\n             , DDL_IDX_COL_ERR_DESCN = DDL_IDX_COL_ERR_DESCN|| '[DDL인덱스_컬럼미존재]' ");
//		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
//		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
//		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
//		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
//		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
//		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
//		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
//		strSQL.append("\n        ) ");
//		strSQL.append("\n          AND EXISTS ( ");
//		strSQL.append("\n                SELECT 1 FROM WAT_DBC_IDX_COL A ");
//		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
//		strSQL.append("\n                  AND A.DBC_IDX_NM = BB.DBC_IDX_NM ");
//		strSQL.append("\n                  AND NVL(A.DDL_IDX_COL_LNM_ERR_EXS, 'N') != 'Y' ");
//		strSQL.append("\n          ) ");
//		strSQL.append("\n        ; ");
		
		
		strSQL.append("\n        --DDL 인덱스 컬럼 존재 여부 ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX BB ");
		strSQL.append("\n           SET DDL_IDX_COL_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , DDL_IDX_COL_ERR_CD = '' ");
		strSQL.append("\n             , DDL_IDX_COL_ERR_DESCN = DDL_IDX_COL_ERR_DESCN|| '[DDL인덱스_컬럼미존재]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_IDX_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_IDX_NM = BB.DBC_IDX_NM ");
		strSQL.append("\n                  AND NVL(A.DDL_IDX_COL_EXTNC_EXS, 'N') != 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		
		
		strSQL.append("\n        --DDL인덱스 컬럼순서에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX BB ");
		strSQL.append("\n           SET DDL_IDX_COL_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , DDL_IDX_COL_ERR_CD = 'N' ");
		strSQL.append("\n             , DDL_IDX_COL_ERR_DESCN = DDL_IDX_COL_ERR_DESCN|| '[DDL인덱스_컬럼순서에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_IDX_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_IDX_NM = BB.DBC_IDX_NM ");
		strSQL.append("\n                  AND A.DDL_IDX_COL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		
		strSQL.append("\n        --DDL인덱스 컬럼 정렬 에러유무 ");
		strSQL.append("\n        UPDATE WAT_DBC_IDX BB ");
		strSQL.append("\n           SET DDL_IDX_COL_ERR_EXS = 'Y' ");
//		strSQL.append("\n             , DDL_IDX_COL_ERR_CD = 'N' ");
		strSQL.append("\n             , DDL_IDX_COL_ERR_DESCN = DDL_IDX_COL_ERR_DESCN|| '[DDL인덱스_컬럼정렬에러]' ");
		strSQL.append("\n        WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n                 SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n                 JOIN WAA_DB_SCH B ");
		strSQL.append("\n                   ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                  AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n                WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n                  AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n                  AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        ) ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1 FROM WAT_DBC_IDX_COL A ");
		strSQL.append("\n                WHERE A.DB_SCH_ID  = BB.DB_SCH_ID ");
		strSQL.append("\n                  AND A.DBC_IDX_NM = BB.DBC_IDX_NM ");
		strSQL.append("\n                  AND A.DDL_IDX_COL_SORT_TYPE_ERR_EXS = 'Y' ");
		strSQL.append("\n          ) ");
		strSQL.append("\n        ; ");
		strSQL.append("\n    END; ");

		return setExecuteUpdate_Org(strSQL.toString());
	}
	

	/**  insomnia
	 * @return
	 * @throws Exception */
	private int updateWatIdxColMetaId() throws Exception {
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_IDX_COL T                                                           ");
		sb.append("\n    SET (DDL_IDX_COL_ID, DDL_IDX_COL_EXTNC_EXS ) = (      ");
		sb.append("\n                          SELECT B.DDL_IDX_COL_ID  , 'Y'                                 ");
		sb.append("\n                            FROM WAM_DDL_IDX A                                      ");
		sb.append("\n                           INNER JOIN WAM_DDL_IDX_COL B                             ");
		sb.append("\n                              ON A.DDL_IDX_ID = B.DDL_IDX_ID                        ");
		sb.append("\n                           INNER JOIN WAM_DDL_TBL C                                 ");
		sb.append("\n                              ON A.DDL_TBL_ID = C.DDL_TBL_ID                        ");
		sb.append("\n                             AND C.REG_TYP_CD IN ('C', 'U')                         ");
		sb.append("\n                           WHERE T.DB_SCH_ID = C.DB_SCH_ID                          ");
		sb.append("\n                             AND T.DBC_IDX_NM = A.DDL_IDX_PNM                       ");
		sb.append("\n                             AND T.DBC_IDX_COL_NM = B.DDL_IDX_COL_PNM               ");
		sb.append("\n                         )                                                          ");
		sb.append("\n  WHERE EXISTS (                                                                    ");
		sb.append("\n                SELECT B.DDL_IDX_COL_ID                                             ");
		sb.append("\n                  FROM WAM_DDL_IDX A                                                ");
		sb.append("\n                 INNER JOIN WAM_DDL_IDX_COL B                                       ");
		sb.append("\n                    ON A.DDL_IDX_ID = B.DDL_IDX_ID                                  ");
		sb.append("\n                 INNER JOIN WAM_DDL_TBL C                                           ");
		sb.append("\n                    ON A.DDL_TBL_ID = C.DDL_TBL_ID                                  ");
		sb.append("\n                   AND C.REG_TYP_CD IN ('C', 'U')                                   ");
		sb.append("\n                 WHERE T.DB_SCH_ID = C.DB_SCH_ID                                    ");
		sb.append("\n                   AND T.DBC_IDX_NM = A.DDL_IDX_PNM                                 ");
		sb.append("\n                   AND T.DBC_IDX_COL_NM = B.DDL_IDX_COL_PNM                         ");
		sb.append("\n               )                                                                    ");
		sb.append("\n   AND T.DB_SCH_ID IN ( 									");
		sb.append("\n       SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A 			");
		sb.append("\n         JOIN WAA_DB_SCH B 								");
		sb.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID 			");
		sb.append("\n          AND B.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n        WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		sb.append("\n          AND A.REG_TYP_CD IN ('C','U') 					");
		sb.append("\n          AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		sb.append("\n   ) 														");
		return setExecuteUpdate_Org(sb.toString());

	}

	
	/** @return insomnia
	 * @throws Exception */
	private int updateWatIdxColErr() throws Exception {

		String tdbid = targetDbmsDM.getDbms_id();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n    BEGIN ");
		strSQL.append("\n    --DDL 인덱스 컬럼 오류 초기화 ");
		strSQL.append("\n    UPDATE WAT_DBC_IDX_COL BB ");
		strSQL.append("\n       SET DDL_IDX_COL_LNM_ERR_EXS = NULL ");
		strSQL.append("\n            , DDL_IDX_COL_ORD_ERR_EXS = NULL ");
		strSQL.append("\n            , DDL_IDX_COL_SORT_TYPE_ERR_EXS = NULL ");
		strSQL.append("\n            , DDL_IDX_COL_ERR_EXS = NULL ");
		strSQL.append("\n            , DDL_IDX_COL_ERR_CONTS = NULL ");
		strSQL.append("\n            , PDM_IDX_COL_LNM_ERR_EXS = NULL ");
		strSQL.append("\n            , PDM_IDX_COL_ORD_ERR_EXS = NULL ");
		strSQL.append("\n            , PDM_IDX_COL_SORT_TYPE_ERR_EXS = NULL ");
		strSQL.append("\n            , PDM_IDX_COL_ERR_EXS = NULL ");
		strSQL.append("\n            , PDM_IDX_COL_ERR_CONTS = NULL ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n             ) ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL 인덱스 컬럼  미존재 ");
		strSQL.append("\n    UPDATE WAT_DBC_IDX_COL BB ");
		strSQL.append("\n       SET  BB.DDL_IDX_COL_EXTNC_EXS = 'N' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_CONTS = BB.DDL_IDX_COL_ERR_CONTS || '[DDL인덱스_컬럼미존재]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n              ) ");
		strSQL.append("\n     AND NVL(BB.DDL_IDX_COL_ID, 'N') != 'Y' ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL 인덱스 컬럼순서  오류 ");
		strSQL.append("\n    UPDATE WAT_DBC_IDX_COL BB ");
		strSQL.append("\n       SET  BB.DDL_IDX_COL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_CONTS = BB.DDL_IDX_COL_ERR_CONTS || '[DDL인덱스_컬럼순서에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n              ) ");
		strSQL.append("\n          AND BB.DDL_IDX_COL_ID IS NOT NULL ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1");
		strSQL.append("\n                 FROM WAM_DDL_IDX_COL A ");
		strSQL.append("\n                WHERE A.DDL_IDX_COL_ID = BB.DDL_IDX_COL_ID ");
		strSQL.append("\n                   AND NVL(A.DDL_IDX_COL_ORD, 0) != NVL(BB.ORD, 0) ");
		strSQL.append("\n                   AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n          ) ");
		strSQL.append("\n    ; ");
		strSQL.append("\n    --DDL 인덱스 컬럼정렬  오류 ");
		strSQL.append("\n    UPDATE WAT_DBC_IDX_COL BB ");
		strSQL.append("\n       SET  BB.DDL_IDX_COL_ORD_ERR_EXS = 'Y' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_EXS = 'Y' ");
		strSQL.append("\n            , BB.DDL_IDX_COL_ERR_CONTS = BB.DDL_IDX_COL_ERR_CONTS || '[DDL인덱스_컬럼정렬에러]' ");
		strSQL.append("\n    WHERE DB_SCH_ID IN ( ");
		strSQL.append("\n             SELECT B.DB_SCH_ID FROM WAA_DB_CONN_TRG A ");
		strSQL.append("\n             JOIN WAA_DB_SCH B ");
		strSQL.append("\n               ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n              AND B.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' ");
		strSQL.append("\n              AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n              AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n              ) ");
		strSQL.append("\n          AND BB.DDL_IDX_COL_ID IS NOT NULL ");
		strSQL.append("\n          AND EXISTS ( ");
		strSQL.append("\n                SELECT 1");
		strSQL.append("\n                 FROM (SELECT DDL_IDX_COL_ID   ");
		strSQL.append("\n                                   , (CASE WHEN SORT_TYP = '1' THEN 'ASC'  ");
		strSQL.append("\n                                            WHEN SORT_TYP = '2' THEN 'DESC'  ");
		strSQL.append("\n                                     END) AS SORT_TYP ");
		strSQL.append("\n                           FROM WAM_DDL_IDX_COL  ");
		strSQL.append("\n                          WHERE REG_TYP_CD IN ('C','U') ) A ");
		strSQL.append("\n                WHERE A.DDL_IDX_COL_ID = BB.DDL_IDX_COL_ID ");
		strSQL.append("\n                   AND NVL(A.SORT_TYP, '_') != NVL(BB.SORT_TYPE, '_') ");
		strSQL.append("\n          ) ");
		strSQL.append("\n    ; ");
		//DDL 인덱스 컬럼 한글명 오류
		strSQL.append("\n    END; ");

		return setExecuteUpdate_Org(strSQL.toString());
	}

	

	/** @param targetDbmsDM2 insomnia
	 * @throws Exception */
	private void deleteWat() throws Exception {
		int result = 0;
		String tdbid = targetDbmsDM.getDbms_id();

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

	public List<TargetDbmsDM> selectTargetDbmsList(String dbmsId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		
		query.append("\n SELECT DB.DB_CONN_TRG_ID       ");
		query.append("\n      , DB.DB_CONN_TRG_PNM      ");
		query.append("\n      , DB.DBMS_TYP_CD          ");
		query.append("\n      , DB.DBMS_VERS_CD         ");
		query.append("\n      , DB.DB_CONN_AC_ID        ");
		query.append("\n      , DB.DB_CONN_AC_PWD       ");
		query.append("\n      , S.DB_SCH_ID             ");
		query.append("\n      , S.DB_SCH_PNM            ");
		query.append("\n      , DB.CONN_TRG_DRVR_NM     ");
		query.append("\n      , DB.CONN_TRG_LNK_URL     ");
		query.append("\n      , S.COL_PRF_YN            ");
		query.append("\n      , S.DMN_PDT_YN            ");
		query.append("\n   FROM WAA_DB_CONN_TRG DB      ");
		query.append("\n        INNER JOIN WAA_DB_SCH S ");
		query.append("\n           ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		query.append("\n          AND S.REG_TYP_CD IN ('C','U') ");
		query.append("\n          AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		query.append("\n  WHERE DB.DB_CONN_TRG_ID = '").append(dbmsId).append("' ");
		query.append("\n    AND DB.REG_TYP_CD IN ('C','U')      ");
		query.append("\n    AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");

		logger.info(query);

		List<TargetDbmsDM> list = new ArrayList<TargetDbmsDM>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				TargetDbmsDM dm = new TargetDbmsDM();
				
				dm.setDbms_id(rs.getString("DB_CONN_TRG_ID"));
				dm.setDbms_enm(rs.getString("DB_CONN_TRG_PNM"));
				dm.setDbms_type_cd(rs.getString("DBMS_TYP_CD"));
				dm.setDbms_vers_cd(rs.getString("DBMS_VERS_CD")); 				
				dm.setDb_user(rs.getString("DB_CONN_AC_ID"));
				dm.setDb_pwd(rs.getString("DB_CONN_AC_PWD"));
				dm.setDbSchId(rs.getString("DB_SCH_ID"));
				dm.setDbc_owner_nm(rs.getString("DB_SCH_PNM"));
				dm.setJdbc_driver(rs.getString("CONN_TRG_DRVR_NM"));
				dm.setConnect_string(rs.getString("CONN_TRG_LNK_URL"));
				dm.setColPrfYn(rs.getString("COL_PRF_YN"));
				dm.setDmnPdtYn(rs.getString("DMN_PDT_YN"));

				list.add(dm);
			}
			return list;
		} finally {
			if(rs    != null) {
				try { rs.close(); } catch(Exception igonred) {}
			}
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	
	public String dbConnTrgSql(){
		String tdbid = targetDbmsDM.getDbms_id();
		StringBuilder strSQL = new StringBuilder();
		strSQL.append("\n                                   SELECT B.DB_SCH_ID 	");
		strSQL.append("\n                                     FROM WAA_DB_CONN_TRG A 		");
		strSQL.append("\n                                             INNER JOIN WAA_DB_SCH B 	");
		strSQL.append("\n                                                 ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ");
		strSQL.append("\n                                               AND B.REG_TYP_CD IN ('C','U') 					");
		strSQL.append("\n                                               AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		strSQL.append("\n                                   WHERE A.DB_CONN_TRG_ID = '"+tdbid+"' 				");
		strSQL.append("\n                                      AND A.REG_TYP_CD IN ('C','U') 					");
		strSQL.append("\n                                      AND A.EXP_DTM = TO_DATE('99991231','YYYYMMDD') 	");
		return strSQL.toString();
	}
	
	
	
	
	
	/**
	 * 타겟데이터의 SQL을 받아 저장한다.
	 * @param String 저장 SQL
	 * @return boolean 저장결과값
	 * @throws SQLException
	 */
	private int setExecuteUpdate_Org(String query_tgt) throws Exception
	{
		logger.debug(query_tgt);
		pst_org = daCon.prepareStatement(query_tgt);

		int cnt = pst_org.executeUpdate();

		pst_org.close();

		return cnt;
	}

}
