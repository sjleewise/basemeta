package kr.wise.executor.exceute.schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class DbCatalogHistManager {

	private static final Logger logger = Logger.getLogger(DbCatalogHistManager.class);
	private ResultSet rs = null;
	private Connection con_org = null;

	private PreparedStatement pst_org = null;
	
	private String dbConnTrgId = null;



	/** insomnia */
	public DbCatalogHistManager(Connection source, TargetDbmsDM targetDb) {
		this.con_org = source;
		this.dbConnTrgId = targetDb.getDbms_id();
	}

	
	
	public boolean saveWah() throws Exception {
		boolean result = false;
		int cnt = 0;
		
//테이블 이력 적재  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		cnt = deletewahtblcopy();
		logger.debug("deletewahtblcopy : " + cnt + " OK!!");
		
		//복제 WAH_DBC_TBL_COPY 테이블 insert
		cnt = insertwahtblcopy();
		logger.debug("insertwahtblcopy : " + cnt + " OK!!");
		
		//테이블 이력 "D" update
		cnt = updatewahtblByD();
		logger.debug("updatewahtblByD : " + cnt + " OK!!");
		
		//변경 테이블 이력 만료
		cnt = updatewahtblByExpDtm();
		logger.debug("updatewahtblByExpDtm : " + cnt + " OK!!");
		
		//변경 테이블 이력 insert
		cnt = insertwahtblByU();
		logger.debug("insertwahtblByU : " + cnt + " OK!!");

		//신규 테이블 이력 insert
		cnt = insertwahtblByC();
		logger.debug("insertwahtblByC : " + cnt + " OK!!");
		
		//테이블 버젼정보 update
		cnt = updatewattblByVers();
		logger.debug("updatewattblByVers : " + cnt + " OK!!");
		

// 컬럼 이력 적재  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//복제 WAH_DBC_COL_COPY 테이블 삭제
		cnt = deletewahcolcopy();
		logger.debug("deletewahcolcopy : " + cnt + " OK!!");
		
		//복제 WAH_DBC_COL_COPY 테이블 insert
		cnt = insertwahcolcopy();
		logger.debug("insertwahcolcopy : " + cnt + " OK!!");
		
		//컬럼 이력 'D' UPDATE
		cnt = updatewahcolByD();
		logger.debug("updatewahcolByD : " + cnt + " OK!!");
		
		//WAT_DBC_COL_COBY 이용 변경 컬럼 만료
		cnt = updatewahcolByExpDtm();
		logger.debug("updatewahcolByExpDtm : " + cnt + " OK!!");
		
		//WAT_DBC_COL_COBY 이용 변경 컬럼 insert
		cnt = insertwahcolByU();
		logger.debug("insertwahcolByU : " + cnt + " OK!!");

		//WAT_DBC_COL 이용 DBC 신규 DBC 컬럼 insert
		cnt = insertwahcolByC();
		logger.debug("insertwahcolByC : " + cnt + " OK!!");
		
		cnt = updatewatcolByVers();
		logger.debug("updatewatcolByVers : " + cnt + " OK!!");

		result = true;

		return result;
	}
	
	
	private int updatewahtblByD() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAH_DBC_TBL X   ");                                  
		sb.append("\n    SET X.REG_TYP = 'D' ");                                  
		sb.append("\n  WHERE X.EXP_DTM = TO_DATE('99991231','YYYYMMDD')        ");
		sb.append("\n    AND X.REG_TYP <> 'D' ");
		sb.append("\n    AND NOT EXISTS (SELECT 1                              ");
		sb.append("\n                      FROM WAT_DBC_TBL B                  ");
		sb.append("\n                     WHERE X.DB_SCH_ID = B.DB_SCH_ID      ");
		sb.append("\n                       AND X.DBC_TBL_NM = B.DBC_TBL_NM )  ");
		sb.append("\n    AND X.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		return setExecuteUpdate_Org(sb.toString());
	}
	
	
	private int deletewahtblcopy() throws Exception {
		StringBuffer sb = new StringBuffer();
//		복제테이블 삭제
		sb.append("\n DELETE WAH_DBC_TBL_COPY  ");
		sb.append("\n   WHERE DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		return setExecuteUpdate_Org(sb.toString());
	}
	
	
	
	private int insertwahtblcopy() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_TBL_COPY A  ");
		sb.append("\n SELECT B.* ");
		sb.append("\n   FROM WAH_DBC_TBL B  ");
		sb.append("\n   WHERE B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n     AND B.REG_TYP <> 'D' ");
		sb.append("\n     AND B.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		
		return setExecuteUpdate_Org(sb.toString());
		
	}
	
	
	
	private int updatewahtblByExpDtm() throws Exception {
		StringBuffer sb = new StringBuffer();
		// WAE_ORA_TBL : LAST_DDL_TIME  WAH_DBC_TBL : CHG_DTM 이용
		sb.append("\n UPDATE WAH_DBC_TBL A   ");                                                   
		sb.append("\n    SET A.EXP_DTM = SYSDATE ");                                                   
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ");  
		sb.append("\n    AND A.REG_TYP <> 'D' "); 
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND EXISTS ( SELECT 1    ");
		sb.append("\n                   FROM WAT_DBC_TBL B          ");                          
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID   ");                          
		sb.append("\n                    AND A.DBC_TBL_NM = B.DBC_TBL_NM ");                          
		sb.append("\n                    AND NVL(A.CHG_DTM,SYSDATE) <> NVL(B.CHG_DTM,SYSDATE)  )  "); 
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int insertwahtblByU() throws Exception {
		// WAE_ORA_TBL : LAST_DDL_TIME  WAH_DBC_TBL : CHG_DTM 이용
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_TBL ");
		sb.append("\n SELECT  A.DB_SCH_ID    ");    
		sb.append("\n      ,A.DBC_TBL_NM        ");
		sb.append("\n      ,TO_DATE('99991231','YYYYMMDD') AS EXP_DTM   ");
		sb.append("\n      ,SYSDATE AS STR_DTM           ");
		sb.append("\n      ,A.DBC_TBL_KOR_NM    ");
		sb.append("\n      ,(NVL(B.VERS, 0)+1) AS VERS  ");
		sb.append("\n      ,'U' AS REG_TYP      ");
		sb.append("\n      ,A.REG_DTM           ");
		sb.append("\n      ,A.UPD_DTM           ");
		sb.append("\n      ,A.DESCN             ");
		sb.append("\n      ,A.DB_CONN_TRG_ID    ");
		sb.append("\n      ,A.DBC_TBL_SPAC_NM   ");
		sb.append("\n      ,A.DDL_TBL_ID        ");
		sb.append("\n      ,A.PDM_TBL_ID        ");
		sb.append("\n      ,A.DBMS_TYPE         ");
		sb.append("\n      ,A.SUBJ_ID           ");
		sb.append("\n      ,A.COL_EACNT         ");
		sb.append("\n      ,A.ROW_EACNT         ");
		sb.append("\n      ,A.TBL_SIZE          ");
		sb.append("\n      ,A.DATA_SIZE         ");
		sb.append("\n      ,A.IDX_SIZE          ");
		sb.append("\n      ,A.NUSE_SIZE         ");
		sb.append("\n      ,A.BF_COL_EACNT      ");
		sb.append("\n      ,A.BF_ROW_EACNT      ");
		sb.append("\n      ,A.BF_TBL_SIZE       ");
		sb.append("\n      ,A.BF_DATA_SIZE      ");
		sb.append("\n      ,A.BF_IDX_SIZE       ");
		sb.append("\n      ,A.BF_NUSE_SIZE      ");
		sb.append("\n      ,A.ANA_DTM           ");
		sb.append("\n      ,A.CRT_DTM           ");
		sb.append("\n      ,A.CHG_DTM           ");
		sb.append("\n      ,A.PDM_DESCN         ");
		sb.append("\n      ,A.TBL_DQ_EXP_YN     ");
		sb.append("\n      ,A.DDL_TBL_ERR_EXS   ");
		sb.append("\n      ,A.DDL_TBL_ERR_CD    ");
		sb.append("\n      ,A.DDL_TBL_ERR_DESCN ");
		sb.append("\n      ,A.DDL_COL_ERR_EXS   ");
		sb.append("\n      ,A.DDL_COL_ERR_CD    ");
		sb.append("\n      ,A.DDL_COL_ERR_DESCN ");
		sb.append("\n      ,A.PDM_TBL_ERR_EXS   ");
		sb.append("\n      ,A.PDM_TBL_ERR_CD    ");
		sb.append("\n      ,A.PDM_TBL_ERR_DESCN ");
		sb.append("\n      ,A.PDM_COL_ERR_EXS   ");
		sb.append("\n      ,A.PDM_COL_ERR_CD    ");
		sb.append("\n      ,A.PDM_COL_ERR_DESCN ");
		sb.append("\n      ,A.DDL_TBL_EXTNC_EXS ");
		sb.append("\n      ,A.PDM_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAT_DBC_TBL A        ");
		sb.append("\n        LEFT OUTER JOIN WAH_DBC_TBL_COPY  B ");
		sb.append("\n          ON A.DB_SCH_ID = B.DB_SCH_ID   ");                          
		sb.append("\n         AND A.DBC_TBL_NM = B.DBC_TBL_NM ");                          
		sb.append("\n         AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");          
		sb.append("\n         AND B.REG_TYP <> 'D' "); 
		sb.append("\n  WHERE 1 = 1   ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND EXISTS ( SELECT 1    ");
		sb.append("\n                   FROM WAH_DBC_TBL_COPY B          ");                          
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID   ");                          
		sb.append("\n                    AND A.DBC_TBL_NM = B.DBC_TBL_NM ");                          
		sb.append("\n                    AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");          
		sb.append("\n                    AND B.REG_TYP <> 'D' ");          
		sb.append("\n                    AND NVL(A.CHG_DTM,SYSDATE) <> NVL(B.CHG_DTM,SYSDATE)   "); 
		sb.append("\n                )  "); 
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int insertwahtblByC() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_TBL ");
		sb.append("\n SELECT A.DB_SCH_ID    ");    
		sb.append("\n      ,A.DBC_TBL_NM        ");
		sb.append("\n      ,TO_DATE('99991231','YYYYMMDD') AS EXP_DTM   ");
		sb.append("\n      ,SYSDATE AS STR_DTM  ");
		sb.append("\n      ,A.DBC_TBL_KOR_NM    ");
		sb.append("\n      ,1 AS VERS  ");
		sb.append("\n      ,'C' AS REG_TYP      ");
		sb.append("\n      ,A.REG_DTM           ");
		sb.append("\n      ,A.UPD_DTM           ");
		sb.append("\n      ,A.DESCN             ");
		sb.append("\n      ,A.DB_CONN_TRG_ID    ");
		sb.append("\n      ,A.DBC_TBL_SPAC_NM   ");
		sb.append("\n      ,A.DDL_TBL_ID        ");
		sb.append("\n      ,A.PDM_TBL_ID        ");
		sb.append("\n      ,A.DBMS_TYPE         ");
		sb.append("\n      ,A.SUBJ_ID           ");
		sb.append("\n      ,A.COL_EACNT         ");
		sb.append("\n      ,A.ROW_EACNT         ");
		sb.append("\n      ,A.TBL_SIZE          ");
		sb.append("\n      ,A.DATA_SIZE         ");
		sb.append("\n      ,A.IDX_SIZE          ");
		sb.append("\n      ,A.NUSE_SIZE         ");
		sb.append("\n      ,A.BF_COL_EACNT      ");
		sb.append("\n      ,A.BF_ROW_EACNT      ");
		sb.append("\n      ,A.BF_TBL_SIZE       ");
		sb.append("\n      ,A.BF_DATA_SIZE      ");
		sb.append("\n      ,A.BF_IDX_SIZE       ");
		sb.append("\n      ,A.BF_NUSE_SIZE      ");
		sb.append("\n      ,A.ANA_DTM           ");
		sb.append("\n      ,A.CRT_DTM           ");
		sb.append("\n      ,A.CHG_DTM           ");
		sb.append("\n      ,A.PDM_DESCN         ");
		sb.append("\n      ,A.TBL_DQ_EXP_YN     ");
		sb.append("\n      ,A.DDL_TBL_ERR_EXS   ");
		sb.append("\n      ,A.DDL_TBL_ERR_CD    ");
		sb.append("\n      ,A.DDL_TBL_ERR_DESCN ");
		sb.append("\n      ,A.DDL_COL_ERR_EXS   ");
		sb.append("\n      ,A.DDL_COL_ERR_CD    ");
		sb.append("\n      ,A.DDL_COL_ERR_DESCN ");
		sb.append("\n      ,A.PDM_TBL_ERR_EXS   ");
		sb.append("\n      ,A.PDM_TBL_ERR_CD    ");
		sb.append("\n      ,A.PDM_TBL_ERR_DESCN ");
		sb.append("\n      ,A.PDM_COL_ERR_EXS   ");
		sb.append("\n      ,A.PDM_COL_ERR_CD    ");
		sb.append("\n      ,A.PDM_COL_ERR_DESCN ");
		sb.append("\n      ,A.DDL_TBL_EXTNC_EXS ");
		sb.append("\n      ,A.PDM_TBL_EXTNC_EXS ");
		sb.append("\n   FROM WAT_DBC_TBL A        ");
		sb.append("\n  WHERE 1 = 1   ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND NOT EXISTS (SELECT 1    ");
		sb.append("\n                      FROM WAH_DBC_TBL B          ");                          
		sb.append("\n                     WHERE A.DB_SCH_ID = B.DB_SCH_ID   ");                          
		sb.append("\n                       AND A.DBC_TBL_NM = B.DBC_TBL_NM  ");                          
		sb.append("\n                       AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");                          
		sb.append("\n                       AND B.REG_TYP <> 'D' ) ");                          
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int updatewattblByVers() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_TBL A ");
		sb.append("\n    SET (A.VERS, A.REG_TYP) =  ");    
		sb.append("\n                  ( SELECT B.VERS, B.REG_TYP  ");    
		sb.append("\n                      FROM WAH_DBC_TBL B  ");
		sb.append("\n                     WHERE A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n                       AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n                       AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                       AND NVL(B.REG_TYP, '_') = 'U' ) ");
		sb.append("\n  WHERE 1 = 1   ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND EXISTS  (   SELECT 1  ");    
		sb.append("\n                      FROM WAH_DBC_TBL B  ");
		sb.append("\n                     WHERE A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n                       AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n                       AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                       AND NVL(B.REG_TYP, '_') = 'U' ) ");
		
		return setExecuteUpdate_Org(sb.toString());
	}
	
	
	private int deletewahcolcopy() throws Exception {
		StringBuffer sb = new StringBuffer();
//		복제테이블 삭제
		sb.append("\n DELETE WAH_DBC_COL_COPY  ");
		sb.append("\n   WHERE DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int insertwahcolcopy() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_COL_COPY A  ");
		sb.append("\n SELECT B.* ");
		sb.append("\n   FROM WAH_DBC_COL B  ");
		sb.append("\n   WHERE B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n     AND B.REG_TYP <> 'D' ");
		sb.append("\n     AND B.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		
		return setExecuteUpdate_Org(sb.toString());
		
	}
	
	private int updatewahcolByD() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAH_DBC_COL A   ");                                  
		sb.append("\n    SET A.REG_TYP = 'D' ");                                  
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('99991231','YYYYMMDD')        ");
		sb.append("\n    AND A.REG_TYP <> 'D' ");
		sb.append("\n    AND NOT EXISTS (SELECT 1                              ");
		sb.append("\n                      FROM WAT_DBC_COL B                  ");
		sb.append("\n                     WHERE A.DB_SCH_ID = B.DB_SCH_ID      ");
		sb.append("\n                       AND A.DBC_TBL_NM = B.DBC_TBL_NM    ");
		sb.append("\n                       AND A.DBC_COL_NM = B.DBC_COL_NM )  ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		return setExecuteUpdate_Org(sb.toString());
	}
	
	private int insertwahcolByC() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_COL  ");                                         
		sb.append("\n SELECT   A.DB_SCH_ID     ");                                         
		sb.append("\n         ,A.DBC_TBL_NM    ");                                         
		sb.append("\n         ,A.DBC_COL_NM    ");                                         
		sb.append("\n         ,TO_DATE('99991231','YYYYMMDD') AS EXP_DTM   ");             
		sb.append("\n         ,SYSDATE AS STR_DTM       ");                                
		sb.append("\n         ,A.DBC_COL_KOR_NM         ");                                
		sb.append("\n         ,1 AS VERS                ");                                
		sb.append("\n         ,'C' AS REG_TYP           ");                                
		sb.append("\n         ,A.REG_DTM                ");                                
		sb.append("\n         ,A.UPD_DTM                ");                                
		sb.append("\n         ,A.DESCN                  ");                                
		sb.append("\n         ,A.DDL_COL_ID             ");                                
		sb.append("\n         ,A.PDM_COL_ID             ");                                
		sb.append("\n         ,A.ITM_ID                 ");                                
		sb.append("\n         ,A.DATA_TYPE              ");                                
		sb.append("\n         ,A.DATA_LEN               ");                                
		sb.append("\n         ,A.DATA_PNUM              ");                                
		sb.append("\n         ,A.DATA_PNT               ");                                
		sb.append("\n         ,A.NULL_YN                ");                                
		sb.append("\n         ,A.DEFLT_LEN              ");                                
		sb.append("\n         ,A.DEFLT_VAL              ");                                
		sb.append("\n         ,A.PK_YN                  ");                                
		sb.append("\n         ,A.ORD                    ");                                
		sb.append("\n         ,A.PK_ORD                 ");                                
		sb.append("\n         ,A.COL_DESCN              ");                                
		sb.append("\n         ,A.COL_DQ_EXP_YN          ");                                
		sb.append("\n         ,A.DDL_COL_EXTNC_YN       ");                                
		sb.append("\n         ,A.DDL_ORD_ERR_EXS        ");                                
		sb.append("\n         ,A.DDL_PK_YN_ERR_EXS      ");                                
		sb.append("\n         ,A.DDL_PK_ORD_ERR_EXS     ");                                
		sb.append("\n         ,A.DDL_NULL_YN_ERR_EXS    ");                                
		sb.append("\n         ,A.DDL_DEFLT_ERR_EXS      ");                                
		sb.append("\n         ,A.DDL_CMMT_ERR_EXS       ");                                
		sb.append("\n         ,A.DDL_DATA_TYPE_ERR_EXS  ");                                
		sb.append("\n         ,A.DDL_DATA_LEN_ERR_EXS   ");                                
		sb.append("\n         ,A.DDL_DATA_PNT_ERR_EXS   ");                                
		sb.append("\n         ,A.DDL_COL_ERR_EXS        ");                                
		sb.append("\n         ,A.PDM_COL_EXTNC_EXS      ");                                
		sb.append("\n         ,A.PDM_ORD_ERR_EXS        ");                                
		sb.append("\n         ,A.PDM_PK_YN_ERR_EXS      ");                                
		sb.append("\n         ,A.PDM_PK_ORD_ERR_EXS     ");                                
		sb.append("\n         ,A.PDM_NULL_YN_ERR_EXS    ");                                
		sb.append("\n         ,A.PDM_DEFLT_ERR_EXS      ");                                
		sb.append("\n         ,A.PDM_CMMT_ERR_EXS       ");                                
		sb.append("\n         ,A.PDM_DATA_TYPE_ERR_EXS  ");                                
		sb.append("\n         ,A.PDM_DATA_LEN_ERR_EXS   ");                                
		sb.append("\n         ,A.PDM_DATA_PNT_ERR_EXS   ");                                
		sb.append("\n         ,A.PDM_COL_ERR_EXS        ");                                
		sb.append("\n         ,A.COL_ERR_CONTS          ");                                
		sb.append("\n   FROM WAT_DBC_COL A              ");                                
		sb.append("\n  WHERE 1 = 1                      ");                                
		sb.append("\n    AND NOT EXISTS (SELECT *       ");                                
		sb.append("\n                     FROM WAH_DBC_COL B  ");                              
		sb.append("\n                    WHERE A.DB_SCH_ID = B.DB_SCH_ID   ");                 
		sb.append("\n                      AND A.DBC_TBL_NM = B.DBC_TBL_NM ");                
		sb.append("\n                      AND A.DBC_COL_NM = B.DBC_COL_NM ");                
		sb.append("\n                      AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ");
		sb.append("\n                      AND B.REG_TYP <> 'D' ) ");
		sb.append("\n     AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		
		return setExecuteUpdate_Org(sb.toString());
		
		
	}
	
	private int updatewahcolByExpDtm() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAH_DBC_COL A   ");                                                   
		sb.append("\n    SET A.EXP_DTM = SYSDATE ");                                                   
		sb.append("\n  WHERE A.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ");  
		sb.append("\n    AND A.REG_TYP <> 'D' ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND EXISTS ( SELECT 1    ");
		sb.append("\n                   FROM WAT_DBC_COL B          ");                          
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID   ");                          
		sb.append("\n                    AND A.DBC_TBL_NM = B.DBC_TBL_NM ");                          
		sb.append("\n                    AND A.DBC_COL_NM = B.DBC_COL_NM ");
		sb.append("\n                    AND ( NVL(A.DBC_COL_KOR_NM,'_') <> NVL(B.DBC_COL_KOR_NM,'_')  ");
		sb.append("\n                          OR NVL(A.DATA_TYPE, '_') <> NVL(B.DATA_TYPE, '_')  ");
		sb.append("\n                          OR NVL(A.DATA_LEN, 0) <> NVL(B.DATA_LEN, 0)        ");
		sb.append("\n                          OR NVL(A.DATA_PNUM, 0) <> NVL(B.DATA_PNUM,0)       ");
		sb.append("\n                          OR NVL(A.DATA_PNT, 0) <> NVL(B.DATA_PNT,0)         ");
		sb.append("\n                          OR NVL(A.NULL_YN, '_') <> NVL(B.NULL_YN, '_')      ");
		sb.append("\n                          OR NVL(A.DEFLT_LEN, 0) <> NVL(B.DEFLT_LEN, 0)      ");
		sb.append("\n                          OR NVL(A.DEFLT_VAL, '_') <> NVL(B.DEFLT_VAL, '_')  ");
		sb.append("\n                          OR NVL(A.PK_YN, '_') <> NVL(B.PK_YN, '_')          ");
		sb.append("\n                          OR NVL(A.ORD, 0) <> NVL(B.ORD, 0)                  ");
		sb.append("\n                          OR NVL(A.PK_ORD, 0) <> NVL(B.PK_ORD, 0)            ");
		sb.append("\n                          OR NVL(A.COL_DESCN,'_') <> NVL(B.COL_DESCN,'_') )  ");
		sb.append("\n                        ) ");
		
		return setExecuteUpdate_Org(sb.toString());
		
	}
	
	private int insertwahcolByU() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO WAH_DBC_COL ");
		sb.append("\n SELECT   A.DB_SCH_ID    ");          
		sb.append("\n         ,A.DBC_TBL_NM   ");          
		sb.append("\n         ,A.DBC_COL_NM   ");          
		sb.append("\n         ,TO_DATE('99991231','YYYYMMDD') AS EXP_DTM ");
		sb.append("\n         ,SYSDATE AS STR_DTM  ");            
		sb.append("\n         ,A.DBC_COL_KOR_NM ");         
		sb.append("\n         ,(NVL(B.VERS, 0)+1) AS VERS ");
		sb.append("\n         ,'U' AS REG_TYP  ");
		sb.append("\n         ,A.REG_DTM       ");
		sb.append("\n         ,A.UPD_DTM       ");
		sb.append("\n         ,A.DESCN         ");
		sb.append("\n         ,A.DDL_COL_ID    ");
		sb.append("\n         ,A.PDM_COL_ID    ");
		sb.append("\n         ,A.ITM_ID        ");
		sb.append("\n         ,A.DATA_TYPE     ");
		sb.append("\n         ,A.DATA_LEN      ");
		sb.append("\n         ,A.DATA_PNUM     ");
		sb.append("\n         ,A.DATA_PNT      ");
		sb.append("\n         ,A.NULL_YN       ");
		sb.append("\n         ,A.DEFLT_LEN     ");
		sb.append("\n         ,A.DEFLT_VAL     ");
		sb.append("\n         ,A.PK_YN         ");
		sb.append("\n         ,A.ORD           ");
		sb.append("\n         ,A.PK_ORD        ");
		sb.append("\n         ,A.COL_DESCN     ");
		sb.append("\n         ,A.COL_DQ_EXP_YN ");
		sb.append("\n         ,A.DDL_COL_EXTNC_YN      ");     
		sb.append("\n         ,A.DDL_ORD_ERR_EXS       ");
		sb.append("\n         ,A.DDL_PK_YN_ERR_EXS     ");
		sb.append("\n         ,A.DDL_PK_ORD_ERR_EXS    ");
		sb.append("\n         ,A.DDL_NULL_YN_ERR_EXS   ");
		sb.append("\n         ,A.DDL_DEFLT_ERR_EXS     ");
		sb.append("\n         ,A.DDL_CMMT_ERR_EXS      ");
		sb.append("\n         ,A.DDL_DATA_TYPE_ERR_EXS ");
		sb.append("\n         ,A.DDL_DATA_LEN_ERR_EXS  ");
		sb.append("\n         ,A.DDL_DATA_PNT_ERR_EXS  ");
		sb.append("\n         ,A.DDL_COL_ERR_EXS       ");
		sb.append("\n         ,A.PDM_COL_EXTNC_EXS     ");
		sb.append("\n         ,A.PDM_ORD_ERR_EXS       ");
		sb.append("\n         ,A.PDM_PK_YN_ERR_EXS     ");
		sb.append("\n         ,A.PDM_PK_ORD_ERR_EXS    ");
		sb.append("\n         ,A.PDM_NULL_YN_ERR_EXS   ");
		sb.append("\n         ,A.PDM_DEFLT_ERR_EXS     ");
		sb.append("\n         ,A.PDM_CMMT_ERR_EXS      ");
		sb.append("\n         ,A.PDM_DATA_TYPE_ERR_EXS ");
		sb.append("\n         ,A.PDM_DATA_LEN_ERR_EXS  ");
		sb.append("\n         ,A.PDM_DATA_PNT_ERR_EXS  ");
		sb.append("\n         ,A.PDM_COL_ERR_EXS       ");
		sb.append("\n         ,A.COL_ERR_CONTS         "); 
		sb.append("\n   FROM WAT_DBC_COL A   ");
		sb.append("\n        LEFT OUTER JOIN WAH_DBC_COL_COPY B   ");
		sb.append("\n          ON A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n         AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n         AND A.DBC_COL_NM = B.DBC_COL_NM ");
		sb.append("\n         AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n         AND B.REG_TYP <> 'D' ");
		sb.append("\n  WHERE 1 = 1 ");
		sb.append("\n    AND EXISTS ( SELECT 1 ");
		sb.append("\n                   FROM WAH_DBC_COL_COPY B ");
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n                    AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n                    AND A.DBC_COL_NM = B.DBC_COL_NM ");
		sb.append("\n                    AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                    AND B.REG_TYP <> 'D' ");
		sb.append("\n                    AND ( NVL(A.DBC_COL_KOR_NM,'_') <> NVL(B.DBC_COL_KOR_NM,'_')  ");
		sb.append("\n                          OR NVL(A.DATA_TYPE, '_') <> NVL(B.DATA_TYPE, '_')  ");
		sb.append("\n                          OR NVL(A.DATA_LEN, 0) <> NVL(B.DATA_LEN, 0)        ");
		sb.append("\n                          OR NVL(A.DATA_PNUM, 0) <> NVL(B.DATA_PNUM,0)       ");
		sb.append("\n                          OR NVL(A.DATA_PNT, 0) <> NVL(B.DATA_PNT,0)         ");
		sb.append("\n                          OR NVL(A.NULL_YN, '_') <> NVL(B.NULL_YN, '_')      ");
		sb.append("\n                          OR NVL(A.DEFLT_LEN, 0) <> NVL(B.DEFLT_LEN, 0)      ");
		sb.append("\n                          OR NVL(A.DEFLT_VAL, '_') <> NVL(B.DEFLT_VAL, '_')  ");
		sb.append("\n                          OR NVL(A.PK_YN, '_') <> NVL(B.PK_YN, '_')          ");
		sb.append("\n                          OR NVL(A.ORD, 0) <> NVL(B.ORD, 0)                  ");
		sb.append("\n                          OR NVL(A.PK_ORD, 0) <> NVL(B.PK_ORD, 0)            ");
		sb.append("\n                          OR NVL(A.COL_DESCN,'_') <> NVL(B.COL_DESCN,'_') )  ");
		sb.append("\n                        ) ");
		sb.append("\n     AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		
		return setExecuteUpdate_Org(sb.toString());
		
	}
	
	
	private int updatewatcolByVers() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE WAT_DBC_COL A ");
		sb.append("\n    SET (A.VERS, A.REG_TYP) =  ");    
		sb.append("\n                  ( SELECT B.VERS, B.REG_TYP  ");    
		sb.append("\n                      FROM WAH_DBC_COL B  ");
		sb.append("\n                     WHERE A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n                       AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n                       AND A.DBC_COL_NM = B.DBC_COL_NM ");
		sb.append("\n                       AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                       AND NVL(B.REG_TYP, '_') = 'U' ) ");
		sb.append("\n  WHERE 1 = 1   ");
		sb.append("\n    AND A.DB_SCH_ID IN ( ");
		sb.append("\n                         SELECT S.DB_SCH_ID ");
		sb.append("\n                           FROM WAA_DB_CONN_TRG DB ");
		sb.append("\n                                INNER JOIN WAA_DB_SCH S ");
		sb.append("\n                                   ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		sb.append("\n                                  AND S.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                                  AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                          WHERE DB.DB_CONN_TRG_ID = '"+this.dbConnTrgId+"' ");
		sb.append("\n                            AND DB.REG_TYP_CD IN ('C','U') ");
		sb.append("\n                            AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD')  ) ");
		sb.append("\n    AND EXISTS ( SELECT B.VERS, B.REG_TYP  ");    
		sb.append("\n                   FROM WAH_DBC_COL B  ");
		sb.append("\n                  WHERE A.DB_SCH_ID = B.DB_SCH_ID ");
		sb.append("\n                    AND A.DBC_TBL_NM = B.DBC_TBL_NM ");
		sb.append("\n                    AND A.DBC_COL_NM = B.DBC_COL_NM ");
		sb.append("\n                    AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		sb.append("\n                    AND NVL(B.REG_TYP, '_') = 'U' ) ");
		return setExecuteUpdate_Org(sb.toString());
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
	

}
