package kr.wise.meta.code.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class CodeGapDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CodeGapDAO.class);

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private int pstmtIdx = 1;
	
	public void analysysGap(TargetDbmsDM trgDM) throws SQLException, Exception {

		try {
			//meta rep db connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			//코드도메인 ID update
			updateWatCodeMetaId(con,  trgDM);
			//코드값 ID update
			updateWatCodeValMetaId(con,  trgDM);
			
			//코드 GAP 분석
			analysysCodeGap(con,  trgDM);
			
			con.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
		}finally {
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception igonred) {}
			}
			if(con   != null) {
				try { con.close(); } catch(Exception igonred) {}
			}
		}
	}
	
	
	public void updateWatCodeMetaId(Connection con, TargetDbmsDM trgDM) throws SQLException{
		
		//도메인ID UPDATE
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append("\n UPDATE WAT_CODE A ") ;
		updateSQL.append("\n    SET (A.DMN_ID, A.DMN_EXTNC_EXS) =  ") ;
		updateSQL.append("\n                    (SELECT B.DMN_ID ") ;
		updateSQL.append("\n                              ,'Y' ") ;
		updateSQL.append("\n                      FROM WAM_DMN B ") ;
		updateSQL.append("\n                              INNER JOIN WAA_DMNG C ") ;
		updateSQL.append("\n                                  ON B.DMNG_ID = C.DMNG_ID ") ;
		updateSQL.append("\n                                 AND C.CD_DMN_YN = 'Y' ") ; //코드도메인만
		updateSQL.append("\n                                 AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD')") ;
		updateSQL.append("\n                     WHERE UPPER(A.CODE_PNM) = UPPER(B.DMN_PNM) ") ;
		updateSQL.append("\n                       AND B.REG_TYP_CD IN ('C','U') ) ") ;
		updateSQL.append("\n WHERE A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		updateSQL.append("\n    AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		
		pstmt = null;
		pstmt = con.prepareStatement(updateSQL.toString());
		pstmt.executeUpdate();
		
		//META 코드 도메인ID INSERT
		StringBuffer selectMetaDmnSQL = new StringBuffer();
		selectMetaDmnSQL.append("\n SELECT A.* ") ;
		selectMetaDmnSQL.append("\n          ,CD.CD_VAL_CNT ") ;
		selectMetaDmnSQL.append("\n   FROM WAM_DMN A ") ;
		selectMetaDmnSQL.append("\n        INNER JOIN WAA_DMNG B ") ;
		selectMetaDmnSQL.append("\n           ON A.DMNG_ID = B.DMNG_ID ") ;
		selectMetaDmnSQL.append("\n          AND B.CD_DMN_YN = 'Y' ") ;
		selectMetaDmnSQL.append("\n          AND B.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
		selectMetaDmnSQL.append("\n          LEFT OUTER JOIN (SELECT DMN_ID,COUNT(CD_VAL_ID) AS CD_VAL_CNT  ") ;
		selectMetaDmnSQL.append("\n                                  FROM WAM_CD_VAL  ") ;
		selectMetaDmnSQL.append("\n                                 WHERE REG_TYP_CD IN ('C','U') ") ;
		selectMetaDmnSQL.append("\n                                 GROUP BY DMN_ID ) CD ") ;
		selectMetaDmnSQL.append("\n          ON A.DMN_ID = CD.DMN_ID ") ;
		selectMetaDmnSQL.append("\n         LEFT OUTER JOIN WAT_CODE C ") ;
		selectMetaDmnSQL.append("\n           ON UPPER(A.DMN_PNM) = UPPER(C.CODE_PNM) ") ;
		selectMetaDmnSQL.append("\n   WHERE C.CODE_PNM IS NULL ") ;
		
		
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("\n INSERT INTO WAT_CODE ( ") ;
		insertSQL.append("\n		DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSQL.append("\n		,DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ;                                               
		insertSQL.append("\n		,CODE_EACNT      --    NUMBER (22), ") ;                                                          
		insertSQL.append("\n		,DMN_ID          -- VARCHAR2 (15), ") ;            
		insertSQL.append("\n		,CODE_EXTNC_EXS  ") ;            
		insertSQL.append("\n		,DMN_EXTNC_EXS   ") ;            
		insertSQL.append("\n )VALUES( ") ;
		insertSQL.append("\n		 ? -- DB_CONN_TRG_ID  --    VARCHAR2 (15) NOT NULL, ") ;
		insertSQL.append("\n		,? -- DB_SCH_ID       --    VARCHAR2 (15) NOT NULL, ") ;                                               
		insertSQL.append("\n		,? -- CODE_EACNT      --    NUMBER (22), ") ;                                                          
		insertSQL.append("\n		,? -- DMN_ID          -- VARCHAR2 (15), ") ;                                                          
		insertSQL.append("\n		,'N' ") ;                                                          
		insertSQL.append("\n		,'Y' ") ;                                                          
		insertSQL.append("\n  ) ") ;
		
		pstmt = null;
		rs = null;
		pstmt = con.prepareStatement(selectMetaDmnSQL.toString());
		rs = pstmt.executeQuery();
		
		//meta rep db insert
		pstmt = null;
		pstmt = con.prepareStatement(insertSQL.toString());
		while(rs.next()) {
			pstmtIdx = 1;
			pstmt.setString(pstmtIdx++, trgDM.getDbms_id());
			pstmt.setString(pstmtIdx++, trgDM.getDbSchId());
			pstmt.setInt(pstmtIdx++, rs.getInt("CD_VAL_CNT"));
			pstmt.setString(pstmtIdx++, rs.getString("DMN_ID"));
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		
		
	}
	
	public void updateWatCodeValMetaId(Connection con, TargetDbmsDM trgDM) throws SQLException{
		
		//도메인ID, 코드값ID UPDATE
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append("\n UPDATE WAT_CODE_VAL A ") ;
		updateSQL.append("\n    SET (A.DMN_ID, A.CD_VAL_ID, A.DMN_VAL_EXTNC_EXS ) =  ") ;
		updateSQL.append("\n                    (SELECT B.DMN_ID ") ;
		updateSQL.append("\n                              ,CD.CD_VAL_ID ") ;
		updateSQL.append("\n                              ,'Y' ") ;
		updateSQL.append("\n                      FROM WAM_DMN B ") ;
		updateSQL.append("\n                              INNER JOIN WAA_DMNG C ") ;
		updateSQL.append("\n                                  ON B.DMNG_ID = C.DMNG_ID ") ;
		updateSQL.append("\n                                 AND C.CD_DMN_YN = 'Y' ") ;  //코드도메인만
		updateSQL.append("\n                                 AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD')") ;
		updateSQL.append("\n                              INNER JOIN WAM_CD_VAL CD ") ;
		updateSQL.append("\n                                  ON B.DMN_ID = CD.DMN_ID ") ;
		updateSQL.append("\n                     WHERE RTRIM(LTRIM(UPPER(A.CODE_PNM))) = RTRIM(LTRIM(UPPER(B.DMN_PNM))) ") ;
		updateSQL.append("\n                       AND RTRIM(LTRIM(UPPER(A.CODE_VAL))) = RTRIM(LTRIM(UPPER(CD.CD_VAL))) ") ;
		updateSQL.append("\n                       AND B.REG_TYP_CD IN ('C','U') ) ") ;
		updateSQL.append("\n WHERE A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
		updateSQL.append("\n    AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
		
		pstmt = null;
		pstmt = con.prepareStatement(updateSQL.toString());
		pstmt.executeUpdate();
		
	}
	
		public void analysysCodeGap(Connection con, TargetDbmsDM trgDM) throws SQLException{
		
			/*
			 *코드값GAP 
			 * */
			//메타코드도메인 코드값 여부 UPDATE
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE_VAL  ") ;
			strSQL.append("\n      SET DMN_VAL_EXTNC_EXS = 'N'  ") ;
			strSQL.append("\n           ,DMN_VAL_ERR_EXS = 'Y'  ") ;
			strSQL.append("\n           ,DMN_VAL_ERR_DESCN = DMN_VAL_ERR_DESCN || '[메타코드도메인 코드값 미존재]'  ") ;
			strSQL.append("\n  WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
//			strSQL.append("\n     AND DMN_ID IS NOT NULL ");
			strSQL.append("\n     AND CD_VAL_ID IS NULL ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
			
			//코드값 GAP  코드코드 테이블 UPDATE
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = A.DMN_VAL_ERR_DESCN ||  '[메타코드도메인 코드값 미존재]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                       FROM (SELECT DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM FROM  WAT_CODE_VAL B ") ;
			strSQL.append("\n                                WHERE B.DMN_VAL_ERR_EXS = 'Y' ");
			strSQL.append("\n                                GROUP BY DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM ) B");
			strSQL.append("\n                       WHERE A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ") ;
			strSQL.append("\n                          AND A.DB_SCH_ID = B.DB_SCH_ID ") ;
			strSQL.append("\n                          AND A.CODE_PNM = B.CODE_PNM ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
				
			//메타 코드값 한글명 != 접속DB 코드값한글명
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE_VAL A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.CODE_VAL_LNM_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = CODE_VAL_ERR_DESCN || '[코드값한글명 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.DMN_VAL_LNM_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = DMN_VAL_ERR_DESCN || '[코드값한글명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_CD_VAL B ") ;
			strSQL.append("\n                       INNER JOIN WAM_DMN D ") ;
			strSQL.append("\n                           ON B.DMN_ID = D.DMN_ID ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON D.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND A.CODE_VAL = B.CD_VAL ") ;
			strSQL.append("\n                   AND RTRIM(LTRIM(NVL(A.CODE_VAL_LNM, '_'))) != RTRIM(LTRIM(NVL(B.CD_VAL_NM, '_'))) ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CD_VAL_ID IS NOT NULL ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
			//코드값 GAP  코드코드 테이블 UPDATE
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = A.CODE_VAL_ERR_DESCN ||  '[코드값한글명 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = A.DMN_VAL_ERR_DESCN ||  '[코드값한글명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                       FROM (SELECT DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM FROM  WAT_CODE_VAL B ") ;
			strSQL.append("\n                                WHERE B.CODE_VAL_LNM_ERR_EXS = 'Y' ");
			strSQL.append("\n                                GROUP BY DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM ) B");
			strSQL.append("\n                       WHERE A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ") ;
			strSQL.append("\n                          AND A.DB_SCH_ID = B.DB_SCH_ID ") ;
			strSQL.append("\n                          AND A.CODE_PNM = B.CODE_PNM ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
				
			/*	
			//메타 코드값 순서 != 접속DB 코드값 순서
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE_VAL A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.CODE_VAL_ORD_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = A.CODE_VAL_ERR_DESCN ||  '[코드값순서 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ORD_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = DMN_VAL_ERR_DESCN || '[코드값순서 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_CD_VAL B ") ;
			strSQL.append("\n                       INNER JOIN WAM_DMN D ") ;
			strSQL.append("\n                           ON B.DMN_ID = D.DMN_ID ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON D.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND A.CODE_VAL = B.CD_VAL ") ;
			strSQL.append("\n                   AND NVL(A.CODE_VAL_ORD, 0) != NVL(B.DISP_ORD, 0) ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CD_VAL_ID IS NOT NULL ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
			
			//코드값 GAP  코드코드 테이블 UPDATE
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = A.CODE_VAL_ERR_DESCN ||  '[코드값순서 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = A.DMN_VAL_ERR_DESCN ||  '[코드값순서 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                       FROM (SELECT DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM FROM  WAT_CODE_VAL B ") ;
			strSQL.append("\n                                WHERE B.CODE_VAL_ORD_ERR_EXS = 'Y' ");
			strSQL.append("\n                                GROUP BY DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM ) B");
			strSQL.append("\n                       WHERE A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ") ;
			strSQL.append("\n                          AND A.DB_SCH_ID = B.DB_SCH_ID ") ;
			strSQL.append("\n                          AND A.CODE_PNM = B.CODE_PNM ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			*/
			
			
			/*
			//메타 코드값 설명 != 접속DB 코드값 설명
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE_VAL A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.CODE_VAL_DESCN_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = A.CODE_VAL_ERR_DESCN ||  '[코드값설명 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n         ,A.DMN_VAL_DESCN_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = DMN_VAL_ERR_DESCN || '[코드값설명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_CD_VAL B ") ;
			strSQL.append("\n                       INNER JOIN WAM_DMN D ") ;
			strSQL.append("\n                           ON B.DMN_ID = D.DMN_ID ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON D.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND A.CODE_VAL = B.CD_VAL ") ;
			strSQL.append("\n                   AND NVL(A.CODE_VAL_DESCN, 0) != NVL(B.OBJ_DESCN, 0) ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CD_VAL_ID IS NOT NULL ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
			
			//코드값 GAP  코드코드 테이블 UPDATE
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = A.CODE_VAL_ERR_DESCN ||  '[코드값설명 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = A.DMN_VAL_ERR_DESCN ||  '[코드값설명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                       FROM (SELECT DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM FROM  WAT_CODE_VAL B ") ;
			strSQL.append("\n                                WHERE B.CODE_VAL_DESCN_ERR_EXS = 'Y' ");
			strSQL.append("\n                                GROUP BY DB_CONN_TRG_ID, DB_SCH_ID, CODE_PNM ) B");
			strSQL.append("\n                       WHERE A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ") ;
			strSQL.append("\n                          AND A.DB_SCH_ID = B.DB_SCH_ID ") ;
			strSQL.append("\n                          AND A.CODE_PNM = B.CODE_PNM ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			logger.debug(strSQL.toString());
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			
			*/
		
			/*
			 *코드GAP 
			 * */
			//메타코드도메인 존재여부 UPDATE
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE  ") ;
			strSQL.append("\n      SET DMN_EXTNC_EXS = 'N'  ") ;
			strSQL.append("\n           ,DMN_ERR_EXS = 'Y'  ") ;
			strSQL.append("\n           ,DMN_ERR_DESCN = DMN_ERR_DESCN || '[메타코드도메인 미존재]'  ") ;
			strSQL.append("\n  WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			strSQL.append("\n     AND DMN_ID IS NULL ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
		
			//메타 코드도메인 존재 접속DB 코드 미존재
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE  ") ;
			strSQL.append("\n      SET CODE_ERR_EXS = 'Y'  ") ;
			strSQL.append("\n           ,CODE_ERR_DESCN = CODE_ERR_DESCN || '[코드 미존재]'  ") ;
			strSQL.append("\n  WHERE DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			strSQL.append("\n     AND CODE_EXTNC_EXS = 'N' ");
			strSQL.append("\n     AND DMN_EXTNC_EXS = 'Y' ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
		
		
			//메타 코드도메인 코드값건수 !=  접속DB 코드값 건수
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_VAL_ERR_DESCN = CODE_VAL_ERR_DESCN || '[코드값건수 GAP]' ") ;
			strSQL.append("\n         ,A.DMN_VAL_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.DMN_VAL_ERR_DESCN = DMN_VAL_ERR_DESCN || '[코드값건수 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM (SELECT DMN_ID, COUNT(1) AS CD_CNT ") ;
			strSQL.append("\n                          FROM WAM_CD_VAL ") ;
			strSQL.append("\n                         WHERE REG_TYP_CD IN ('C','U') ") ;
			strSQL.append("\n                         GROUP BY DMN_ID) B ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND NVL(A.CODE_EACNT, 0) != NVL(B.CD_CNT, 0) ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CODE_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DMN_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
		
			//메타 코드도메인 한글명 != 접속DB 코드한글명
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		,A.CODE_ERR_DESCN = '[코드한글명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_DMN B ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON B.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND NVL(A.CODE_LNM, '_') != NVL(B.DMN_LNM, '_') ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CODE_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DMN_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
		
			//메타 코드도메인 설명 != 접속DB 코드설명
/*			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		,A.CODE_ERR_DESCN = '[코드설명 GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_DMN B ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON B.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND NVL(A.CODE_DESCN, '_') != NVL(B.OBJ_DESCN, '_') ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CODE_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DMN_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
*/
		
			//메타 코드도메인 목록엔티티 != 접속DB 코드 목록엔티티
			strSQL = new StringBuffer();
			strSQL.append("\n UPDATE WAT_CODE A ") ;
			strSQL.append("\n    SET A.CODE_ERR_EXS = 'Y' ") ;
			strSQL.append("\n		    ,A.CODE_ERR_DESCN = '[목록엔티티(컬럼) GAP]' ") ;
			strSQL.append("\n  WHERE EXISTS (SELECT 1 ") ;
			strSQL.append("\n                  FROM WAM_DMN B ") ;
			strSQL.append("\n                       INNER JOIN WAA_DMNG C ") ;
			strSQL.append("\n                          ON B.DMNG_ID = C.DMNG_ID ") ;
			strSQL.append("\n                         AND C.CD_DMN_YN = 'Y' ") ;
			strSQL.append("\n                         AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ") ;
			strSQL.append("\n                 WHERE A.DMN_ID = B.DMN_ID ") ;
			strSQL.append("\n                   AND ( NVL(A.CODE_TBL, '_') != NVL(B.LST_ENTY_PNM, '_') ") ;
			strSQL.append("\n                           OR NVL(A.CODE_COL, '_') != NVL(B.LST_ATTR_PNM, '_') ) ") ;
			strSQL.append("\n               ) ") ;
			strSQL.append("\n     AND A.CODE_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DMN_EXTNC_EXS = 'Y' ");
			strSQL.append("\n     AND A.DB_CONN_TRG_ID =  '").append(trgDM.getDbms_id()).append("' ");
			strSQL.append("\n     AND A.DB_SCH_ID =     '").append(trgDM.getDbSchId()).append("' ");
			pstmt = null;
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
		
		
	}
	
}
