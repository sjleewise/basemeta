package kr.wise.executor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.UniqueKeyGenerator;
import kr.wise.commons.Utils;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.executor.dm.SchJobDM;
import kr.wise.executor.dm.SchMstDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class ScheduleDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ScheduleDAO.class);

	public SchMstDM selectSchMst(String schId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT S.SHD_ID                  ");
		query.append("\n        ,S.SHD_LNM                ");
		query.append("\n        ,S.SHD_PNM                ");
		query.append("\n        ,S.SHD_KND_CD             ");
		query.append("\n        ,S.SHD_USE_YN             ");
		query.append("\n        ,S.SHD_BPR_YN             ");
		query.append("\n        ,S.ER_DATA_LD_YN          ");
		query.append("\n        ,S.ER_DATA_LD_CNT         ");
		query.append("\n        ,S.PK_DATA_LD_YN          ");
		query.append("\n        ,S.PK_DATA_LD_CNT         ");
		query.append("\n        ,S.ANA_DGR                ");
		query.append("\n        ,S.ANA_DGR_AUTO_INC_YN    ");
		query.append("\n        ,S.SHD_TYP_CD             ");
		query.append("\n        ,TO_CHAR(S.SHD_STR_DTM, 'YYYYMMDD') AS SHD_STR_DTM  ");
		query.append("\n        ,S.SHD_STR_HR             ");
		query.append("\n        ,S.SHD_STR_MNT            ");
		query.append("\n        ,S.SHD_DLY                ");
		query.append("\n        ,S.SHD_DLY_VAL            ");
		query.append("\n        ,S.SHD_WKL                ");
		query.append("\n        ,S.SHD_WKL_VAL            ");
		query.append("\n        ,S.SHD_MNY                ");
		query.append("\n        ,S.SHD_MNY_VAL            ");
		query.append("\n        ,S.OBJ_DESCN              ");
		query.append("\n        ,S.RQST_USER_ID           ");
		query.append("\n        ,S.APRV_USER_ID           ");
		query.append("\n        ,S.RQST_NO           ");
		query.append("\n   FROM WAM_SHD_MSTR S            ");
		query.append("\n  WHERE S.REG_TYP_CD IN ('C','U') ");
		query.append("\n    AND S.SHD_ID = ?              ");
		
		logger.debug(query);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, schId);
			
			rs = pstmt.executeQuery();
			SchMstDM dm = null;
			if(rs.next()) {
				dm = new SchMstDM();
				dm.setShdId(rs.getString("SHD_ID"));
				dm.setShdLnm(rs.getString("SHD_LNM"));
				dm.setShdPnm(rs.getString("SHD_PNM"));
				
				dm.setShdKndCd(rs.getString("SHD_KND_CD"));
				dm.setShdUseYn(rs.getString("SHD_USE_YN"));
				dm.setShdBprYn(rs.getString("SHD_BPR_YN"));
				//오류데이터적재
				dm.setErDataLdYn(rs.getString("ER_DATA_LD_YN"));
				dm.setErDataLdCnt(rs.getBigDecimal("ER_DATA_LD_CNT"));
				//PK데이터적재
				dm.setPkDataLdYn(rs.getString("PK_DATA_LD_YN"));
				dm.setPkDataLdCnt(rs.getBigDecimal("PK_DATA_LD_CNT"));
				//분석차수
				dm.setAnaDgr(rs.getBigDecimal("ANA_DGR"));				
				dm.setAnaDgrAutoIncYn(rs.getString("ANA_DGR_AUTO_INC_YN"));
				
				//스케줄유형코드 : 한번만, 매일, 매주 매달 등등
				dm.setShdTypCd(rs.getString("SHD_TYP_CD"));
				
				//스케줄시작시간
				dm.setShdStrDtm(rs.getString("SHD_STR_DTM"));
				dm.setShdStrHr(rs.getString("SHD_STR_HR"));
				dm.setShdStrMnt(rs.getString("SHD_STR_MNT"));
				//매일
				dm.setShdDly(rs.getString("SHD_DLY"));
				dm.setShdDlyVal(rs.getInt("SHD_DLY_VAL"));
				//매주
				dm.setShdWkl(rs.getInt("SHD_WKL"));
				dm.setShdWklVal(rs.getInt("SHD_WKL_VAL"));
				//매달
				dm.setShdMny(rs.getLong("SHD_MNY"));
				dm.setShdMnyVal(rs.getInt("SHD_MNY_VAL"));
				
				dm.setObjDescn(rs.getString("OBJ_DESCN"));
				dm.setRqstUserId(rs.getString("RQST_USER_ID"));
				dm.setAprvUserId(rs.getString("APRV_USER_ID"));
				
				dm.setRqstNo(rs.getString("RQST_NO"));
			}
			
			return dm;
		}catch(SQLException se){
			logger.error(se);
			throw se;
		}catch(Exception e){
			logger.error(e);
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	public List<SchJobDM> selectSchJobList(String shdId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT S.SHD_ID             ");
		query.append("\n           , J.SHD_JOB_ID     ");
		query.append("\n           , J.SHD_JOB_SNO   ");
		query.append("\n           , J.ETC_JOB_DTLS   ");
		query.append("\n           , J.ETC_JOB_KND_CD   ");
		query.append("\n   FROM WAM_SHD_MSTR S ");    
		query.append("\n           INNER JOIN WAM_SHD_JOB J ");
		query.append("\n                ON S.SHD_ID = J.SHD_ID ");
		query.append("\n              AND J.REG_TYP_CD IN ('C','U')       ");
		query.append("\n  WHERE S.SHD_ID = ?       ");
		query.append("\n     AND S.REG_TYP_CD IN ('C','U')       ");
		query.append("\n  ORDER BY J.SHD_JOB_SNO       ");
		
		logger.debug(query);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchJobDM> list = new ArrayList<SchJobDM>();
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, shdId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SchJobDM dm = new SchJobDM();
				dm.setShdJobId(rs.getString("SHD_JOB_ID"));
				dm.setShdJobSno(rs.getString("SHD_JOB_SNO"));
				//일반배치
				dm.setEtcJobDtls(rs.getString("ETC_JOB_DTLS"));
				dm.setEtcJobKndCd(rs.getString("ETC_JOB_KND_CD"));
				
				list.add(dm);
			}
			
			return list;
		}catch(SQLException se){
			logger.error(se);
			throw se;
		}catch(Exception e){
			logger.error(e);
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	
	public void updateAnaDgr(String schId) throws SQLException, Exception  {
		
		StringBuilder query = new StringBuilder();
		
		query.append("\n UPDATE WAM_SHD_MSTR             ");
		query.append("\n    SET ANA_DGR = (ANA_DGR + 1)  ");
		query.append("\n  WHERE SHD_ID = ?               ");
		query.append("\n    AND SHD_KND_CD IN ( '").append(DQConstant.EXE_TYPE_02_CD).append("' ,'").append(DQConstant.EXE_TYPE_03_CD).append("', 'PY', 'UO' )  ");
		
		logger.debug(query);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, schId);
			
			pstmt.executeUpdate();
			
		}catch(SQLException se){
			logger.error(se);
			throw se;
		}catch(Exception e){
			logger.error(e);
			throw e;
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}
	
	public String insertSchMst(Connection con, String shdKndCd) throws SQLException, Exception {
        
	    StringBuilder query = new StringBuilder();
        
	    query.append("\n INSERT INTO WAM_SHD_MSTR    ");
        query.append("\n (  SHD_ID                   ");
        query.append("\n  , SHD_LNM                  ");
        query.append("\n  , SHD_KND_CD               ");
        query.append("\n  , SHD_USE_YN               ");
        query.append("\n  , SHD_BPR_YN               ");
        query.append("\n  , ER_DATA_LD_YN            ");
        query.append("\n  , ER_DATA_LD_CNT           ");
        query.append("\n  , PK_DATA_LD_YN            ");
        query.append("\n  , PK_DATA_LD_CNT           ");
        query.append("\n  , ANA_DGR                  ");
        query.append("\n  , ANA_DGR_AUTO_INC_YN      ");
        query.append("\n  , SHD_TYP_CD               ");
        query.append("\n  , SHD_STR_DTM              ");
        query.append("\n  , SHD_STR_HR               ");
        query.append("\n  , SHD_STR_MNT              "); 
        query.append("\n  , REG_TYP_CD               ");
        query.append("\n  , OBJ_VERS                 ");
        query.append("\n  , OBJ_DESCN                ");
        query.append("\n  , RQST_USER_ID             ");
        query.append("\n  , APRV_USER_ID             ");
        query.append("\n  )                          ");
        query.append("\n SELECT ? AS SHD_ID               ");
        query.append("\n      , ? AS SHD_LNM              ");
        query.append("\n      , ? AS SHD_KND_CD           ");
        query.append("\n      , ? AS SHD_USE_YN           ");
        query.append("\n      , ? AS SHD_BPR_YN           ");
        query.append("\n      , ? AS ER_DATA_LD_YN        ");
        query.append("\n      , ? AS ER_DATA_LD_CNT       ");
        query.append("\n      , ? AS PK_DATA_LD_YN        ");
        query.append("\n      , ? AS PK_DATA_LD_CNT       ");
        query.append("\n      , ? AS ANA_DGR              ");
        query.append("\n      , ? AS ANA_DGR_AUTO_INC_YN  ");
        query.append("\n      , ? AS SHD_TYP_CD           ");
        query.append("\n      , SYSDATE AS SHD_STR_DTM    ");
        query.append("\n      , ? AS SHD_STR_HR           ");
        query.append("\n      , ? AS SHD_STR_MNT          ");  
        query.append("\n      , 'C' AS REG_TYP_CD         ");
        query.append("\n      , 1   AS OBJ_VERS           ");
        query.append("\n      , ? AS OBJ_DESCN            ");
        query.append("\n      , ? AS RQST_USER_ID         ");
        query.append("\n      , ? AS APRV_USER_ID         ");
        query.append("\n  FROM DUAL                       ");
        
              
        logger.debug(query);
        
       
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int iRtn = 0;
        
        try{
            
            pstmt = con.prepareStatement(query.toString());
            
            String schId = UniqueKeyGenerator.getKey();
            String curDate = Utils.getCurrDttm("yyyy-MM-dd HH:mm:ss");
            
            schId = "T" + schId; 
            
            int iParam = 1;
            
            pstmt.setString(iParam++, schId);
            pstmt.setString(iParam++, "[프로파일] 실시간 분석실행(" + curDate + ")");
            pstmt.setString(iParam++, shdKndCd);
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "1000");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "0");
            pstmt.setString(iParam++, ""); //차수
            pstmt.setString(iParam++, ""); 
            pstmt.setString(iParam++, "O"); 
            //SYSDATE
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "ONLINE");
            pstmt.setString(iParam++, "meta"); 
            pstmt.setString(iParam++, "meta");
            
            iRtn = pstmt.executeUpdate();
                                          
            return schId;
            
        }catch(SQLException se){
            logger.error(se);
            throw se;
        }catch(Exception e){
            logger.error(e);
            throw e;
        } finally {
            if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
            if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
           
        }
    }
	
	public int insertSchJob(Connection con, String shdId, WamPrfMstrVO mstVo) throws SQLException, Exception {
        
        StringBuilder query = new StringBuilder();
        
        query.append("\n INSERT INTO WAM_SHD_JOB          ");
        query.append("\n (  SHD_ID                        ");
        query.append("\n  , SHD_JOB_ID                    ");
        query.append("\n  , SHD_JOB_SNO                   ");         
        query.append("\n  , OBJ_VERS                      ");
        query.append("\n  , REG_TYP_CD                    ");
        query.append("\n  , FRS_RQST_DTM                  ");
        query.append("\n  , FRS_RQST_USER_ID              ");
        query.append("\n  , RQST_DTM                      ");
        query.append("\n  , RQST_USER_ID                  ");        
        query.append("\n  , ETC_JOB_NM                    ");        
        query.append("\n  )                               ");
        query.append("\n SELECT ? AS SHD_ID               ");
        query.append("\n      , PRF_ID AS SHD_JOB_ID          ");
        query.append("\n      , 1      AS SHD_JOB_SNO         ");       
        query.append("\n      , 1      AS OBJ_VERS            "); 
        query.append("\n      , 'C'    AS REG_TYP_CD          ");
        query.append("\n      , SYSDATE AS FRS_RQST_DTM       ");
        query.append("\n      , 'meta'  AS FRS_RQST_USER_ID   ");
        query.append("\n      , SYSDATE AS RQST_DTM           ");
        query.append("\n      , 'meta'  AS RQST_USER_ID       ");                
        query.append("\n      , '[' ||GET_CMCD_DTL_NM('PRF_KND_CD', PRF_KND_CD) || '] ' || OBJ_NM AS ETC_JOB_NM   ");        
        query.append("\n  FROM WAM_PRF_MSTR                   ");
        query.append("\n WHERE PRF_ID = ?                     ");
        
              
        logger.debug(query);
               
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int iRtn = 0;
        
        try{
           
            pstmt = con.prepareStatement(query.toString());
                       
            String curDate = Utils.getCurrDttm("yyyy-MM-dd HH:mm:ss");
            
            int iParam = 1;
            
            pstmt.setString(iParam++, shdId);
            pstmt.setString(iParam++, mstVo.getPrfId()); 
            
            iRtn = pstmt.executeUpdate();
                    
            return iRtn;
            
        }catch(SQLException se){
            logger.error(se);
            throw se;
        }catch(Exception e){
            logger.error(e);
            throw e;
        } finally {
            if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
            if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}            
        }
    }

	/** @param targetDbmsDM insomnia 
	 * @return */
	public String regSchMst(TargetDbmsDM targetDbmsDM) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			StringBuilder query = new StringBuilder();
			query.append("\n INSERT INTO WAM_SHD_MSTR    ");
	        query.append("\n (  SHD_ID                   ");
	        query.append("\n  , SHD_LNM                  ");
	        query.append("\n  , SHD_PNM                  ");
	        query.append("\n  , SHD_KND_CD               ");
	        query.append("\n  , SHD_USE_YN               ");
	        query.append("\n  , SHD_BPR_YN               ");
	        query.append("\n  , ER_DATA_LD_YN            ");
	        query.append("\n  , ER_DATA_LD_CNT           ");
	        query.append("\n  , PK_DATA_LD_YN            ");
	        query.append("\n  , PK_DATA_LD_CNT           ");
	        query.append("\n  , ANA_DGR                  ");
	        query.append("\n  , ANA_DGR_AUTO_INC_YN      ");
	        query.append("\n  , SHD_TYP_CD               ");
	        query.append("\n  , SHD_STR_DTM              ");
	        query.append("\n  , SHD_STR_HR               ");
	        query.append("\n  , SHD_STR_MNT              "); 
	        query.append("\n  , REG_TYP_CD               ");
	        query.append("\n  , OBJ_VERS                 ");
	        query.append("\n  , OBJ_DESCN                ");
	        query.append("\n  , RQST_USER_ID             ");
	        query.append("\n  , APRV_USER_ID             ");
	        query.append("\n  , RQST_NO             ");
	        query.append("\n  )                          ");
	        query.append("\n SELECT ? AS SHD_ID               ");
	        query.append("\n      , ? AS SHD_LNM              ");
	        query.append("\n      , 'AUTO_DMN' AS SHD_PNM              ");
	        query.append("\n      , ? AS SHD_KND_CD           ");
	        query.append("\n      , ? AS SHD_USE_YN           ");
	        query.append("\n      , ? AS SHD_BPR_YN           ");
	        query.append("\n      , ? AS ER_DATA_LD_YN        ");
	        query.append("\n      , ? AS ER_DATA_LD_CNT       ");
	        query.append("\n      , ? AS PK_DATA_LD_YN        ");
	        query.append("\n      , ? AS PK_DATA_LD_CNT       ");
	        query.append("\n      , ? AS ANA_DGR              ");
	        query.append("\n      , ? AS ANA_DGR_AUTO_INC_YN  ");
	        query.append("\n      , ? AS SHD_TYP_CD           ");
	        query.append("\n      , SYSDATE AS SHD_STR_DTM    ");
	        query.append("\n      , ? AS SHD_STR_HR           ");
	        query.append("\n      , ? AS SHD_STR_MNT          ");  
	        query.append("\n      , 'C' AS REG_TYP_CD         ");
	        query.append("\n      , 1   AS OBJ_VERS           ");
	        query.append("\n      , ? AS OBJ_DESCN            ");
	        query.append("\n      , ? AS RQST_USER_ID         ");
	        query.append("\n      , ? AS APRV_USER_ID         ");
	        query.append("\n      , ? AS RQST_NO         ");
	        query.append("\n  FROM DUAL                       ");
			
	        
	        pstmt = con.prepareStatement(query.toString());
	        String schId = UniqueKeyGenerator.getKey();
            String curDate = Utils.getCurrDttm("yyyy-MM-dd HH:mm:ss");
            
            schId = "T" + schId; 
            
            int iParam = 1;
            
            pstmt.setString(iParam++, schId);
            pstmt.setString(iParam++, "[프로파일] 실시간 분석실행(" + curDate + ")");
            pstmt.setString(iParam++, "QP");
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "1000");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "0");
            pstmt.setString(iParam++, ""); //차수
            pstmt.setString(iParam++, ""); 
            pstmt.setString(iParam++, "O"); 
            //SYSDATE
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "ONLINE");
            pstmt.setString(iParam++, "meta"); 
            pstmt.setString(iParam++, "meta");
            pstmt.setString(iParam++, targetDbmsDM.getDbms_id());
            
            pstmt.executeUpdate();
            
            pstmt.close();
            
            //컬럼 프로파일 스케줄 잡 등록....
            query.setLength(0);
            query.append("\n INSERT INTO WAM_SHD_JOB          ");
            query.append("\n (  SHD_ID                        ");
            query.append("\n  , SHD_JOB_ID                    ");
            query.append("\n  , SHD_JOB_SNO                   ");         
            query.append("\n  , OBJ_VERS                      ");
            query.append("\n  , REG_TYP_CD                    ");
            query.append("\n  , FRS_RQST_DTM                  ");
            query.append("\n  , FRS_RQST_USER_ID              ");
            query.append("\n  , RQST_DTM                      ");
            query.append("\n  , RQST_USER_ID                  ");        
            query.append("\n  , ETC_JOB_NM                    ");        
            query.append("\n  )                               ");
            query.append("\n SELECT ? AS SHD_ID               ");
            query.append("\n      , PRF_ID AS SHD_JOB_ID          ");
            query.append("\n      , ROWNUM     AS SHD_JOB_SNO         ");       
            query.append("\n      , 1      AS OBJ_VERS            "); 
            query.append("\n      , 'C'    AS REG_TYP_CD          ");
            query.append("\n      , SYSDATE AS FRS_RQST_DTM       ");
            query.append("\n      , 'meta'  AS FRS_RQST_USER_ID   ");
            query.append("\n      , SYSDATE AS RQST_DTM           ");
            query.append("\n      , 'meta'  AS RQST_USER_ID       ");                
            query.append("\n      , '[컬럼분석] ' || DBC_TBL_NM||'.'||OBJ_NM AS ETC_JOB_NM   ");
            
            query.append("\n   FROM WAM_PRF_MSTR ");
            query.append("\n  WHERE 1=1 ");
            query.append("\n    AND PRF_KND_CD = 'PC01' ");
            query.append("\n    AND DB_SCH_ID IN ( ");
            query.append("\n        SELECT DB_SCH_ID FROM WAA_DB_SCH ");
            query.append("\n         WHERE 1=1 ");
            query.append("\n           AND DB_CONN_TRG_ID = ? ");
            query.append("\n           AND (COL_PRF_YN = 'Y' OR DMN_PDT_YN = 'Y') ");
            query.append("\n           AND EXP_DTM = TO_DATE('99991231', 'YYYYMMDD') ");
            query.append("\n           AND REG_TYP_CD IN ('C', 'U') ");
            query.append("\n    ) ");
            
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, schId);
            pstmt.setString(2, targetDbmsDM.getDbms_id());
            
            logger.debug(query.toString());
            logger.debug("SCH_ID:["+schId+"]");
            logger.debug("DB_ID:["+targetDbmsDM.getDbms_id()+"]");
            
            pstmt.executeUpdate();
            
            
            con.commit();
            
            return schId;
		}catch(SQLException se){
            logger.error(se);
            if (con != null) con.rollback();
            throw se;
        }catch(Exception e){
            logger.error(e);
            if (con != null) con.rollback();
            throw e;
        } finally {
            if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
            if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}            
            if(con != null) try { con.close(); } catch(Exception igonred) {}            
        }
		
	}

	/** @param dbmsId insomnia 
	 * @return 
	 * @throws Exception */
	public String regSchMstDmn(String dbmsId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ConnectionHelper.getDAConnection();
			
			StringBuilder query = new StringBuilder();
			query.append("\n INSERT INTO WAM_SHD_MSTR    ");
	        query.append("\n (  SHD_ID                   ");
	        query.append("\n  , SHD_LNM                  ");
	        query.append("\n  , SHD_PNM                  ");
	        query.append("\n  , SHD_KND_CD               ");
	        query.append("\n  , SHD_USE_YN               ");
	        query.append("\n  , SHD_BPR_YN               ");
	        query.append("\n  , ER_DATA_LD_YN            ");
	        query.append("\n  , ER_DATA_LD_CNT           ");
	        query.append("\n  , PK_DATA_LD_YN            ");
	        query.append("\n  , PK_DATA_LD_CNT           ");
	        query.append("\n  , ANA_DGR                  ");
	        query.append("\n  , ANA_DGR_AUTO_INC_YN      ");
	        query.append("\n  , SHD_TYP_CD               ");
	        query.append("\n  , SHD_STR_DTM              ");
	        query.append("\n  , SHD_STR_HR               ");
	        query.append("\n  , SHD_STR_MNT              "); 
	        query.append("\n  , REG_TYP_CD               ");
	        query.append("\n  , OBJ_VERS                 ");
	        query.append("\n  , OBJ_DESCN                ");
	        query.append("\n  , RQST_USER_ID             ");
	        query.append("\n  , APRV_USER_ID             ");
	        query.append("\n  , RQST_NO             ");
	        query.append("\n  )                          ");
	        query.append("\n SELECT ? AS SHD_ID               ");
	        query.append("\n      , ? AS SHD_LNM              ");
	        query.append("\n      , 'AUTO_DMN' AS SHD_PNM              ");
	        query.append("\n      , ? AS SHD_KND_CD           ");
	        query.append("\n      , ? AS SHD_USE_YN           ");
	        query.append("\n      , ? AS SHD_BPR_YN           ");
	        query.append("\n      , ? AS ER_DATA_LD_YN        ");
	        query.append("\n      , ? AS ER_DATA_LD_CNT       ");
	        query.append("\n      , ? AS PK_DATA_LD_YN        ");
	        query.append("\n      , ? AS PK_DATA_LD_CNT       ");
	        query.append("\n      , ? AS ANA_DGR              ");
	        query.append("\n      , ? AS ANA_DGR_AUTO_INC_YN  ");
	        query.append("\n      , ? AS SHD_TYP_CD           ");
	        query.append("\n      , SYSDATE AS SHD_STR_DTM    ");
	        query.append("\n      , ? AS SHD_STR_HR           ");
	        query.append("\n      , ? AS SHD_STR_MNT          ");  
	        query.append("\n      , 'C' AS REG_TYP_CD         ");
	        query.append("\n      , 1   AS OBJ_VERS           ");
	        query.append("\n      , ? AS OBJ_DESCN            ");
	        query.append("\n      , ? AS RQST_USER_ID         ");
	        query.append("\n      , ? AS APRV_USER_ID         ");
	        query.append("\n  FROM DUAL                       ");
			
	        
	        pstmt = con.prepareStatement(query.toString());
	        String schId = UniqueKeyGenerator.getKey();
            String curDate = Utils.getCurrDttm("yyyy-MM-dd HH:mm:ss");
            
            schId = "T" + schId; 
            
            int iParam = 1;
            
            pstmt.setString(iParam++, schId);
            pstmt.setString(iParam++, "[도메인판별] 실시간 분석실행(" + curDate + ")");
            pstmt.setString(iParam++, "PY");
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "Y");
            pstmt.setString(iParam++, "1000");
            pstmt.setString(iParam++, "N");
            pstmt.setString(iParam++, "0");
            pstmt.setString(iParam++, ""); //차수
            pstmt.setString(iParam++, ""); 
            pstmt.setString(iParam++, "O"); 
            //SYSDATE
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "00");
            pstmt.setString(iParam++, "ONLINE");
            pstmt.setString(iParam++, "meta"); 
            pstmt.setString(iParam++, "meta");
            
            pstmt.executeUpdate();
            
            pstmt.close();
            
            //컬럼 프로파일 스케줄 잡 등록....
            query.setLength(0);
            query.append("\n INSERT INTO WAM_SHD_JOB          ");
            query.append("\n (  SHD_ID                        ");
            query.append("\n  , SHD_JOB_ID                    ");
            query.append("\n  , SHD_JOB_SNO                   ");         
            query.append("\n  , OBJ_VERS                      ");
            query.append("\n  , REG_TYP_CD                    ");
            query.append("\n  , FRS_RQST_DTM                  ");
            query.append("\n  , FRS_RQST_USER_ID              ");
            query.append("\n  , RQST_DTM                      ");
            query.append("\n  , RQST_USER_ID                  ");        
            query.append("\n  , ETC_JOB_NM                    ");        
            query.append("\n  )                               ");
            query.append("\n SELECT ? AS SHD_ID               ");
            query.append("\n      , PRF_ID AS SHD_JOB_ID          ");
            query.append("\n      , ROWNUM     AS SHD_JOB_SNO         ");       
            query.append("\n      , 1      AS OBJ_VERS            "); 
            query.append("\n      , 'C'    AS REG_TYP_CD          ");
            query.append("\n      , SYSDATE AS FRS_RQST_DTM       ");
            query.append("\n      , 'meta'  AS FRS_RQST_USER_ID   ");
            query.append("\n      , SYSDATE AS RQST_DTM           ");
            query.append("\n      , 'meta'  AS RQST_USER_ID       ");                
            query.append("\n      , '[도메인판별] ' || DBC_TBL_NM||'.'||OBJ_NM AS ETC_JOB_NM   ");
            
            query.append("\n   FROM WAM_PRF_MSTR ");
            query.append("\n  WHERE 1=1 ");
            query.append("\n    AND PRF_KND_CD = 'PC01' ");
            query.append("\n    AND DB_SCH_ID IN ( ");
            query.append("\n        SELECT DB_SCH_ID FROM WAA_DB_SCH ");
            query.append("\n         WHERE 1=1 ");
            query.append("\n           AND DB_CONN_TRG_ID = ? ");
            query.append("\n           AND (COL_PRF_YN = 'Y' OR DMN_PDT_YN = 'Y') ");
            query.append("\n           AND EXP_DTM = TO_DATE('99991231', 'YYYYMMDD') ");
            query.append("\n           AND REG_TYP_CD IN ('C', 'U') ");
            query.append("\n    ) ");
            
            pstmt = con.prepareStatement(query.toString());
            pstmt.setString(1, schId);
            pstmt.setString(2, dbmsId);
            
            logger.debug(query.toString());
            logger.debug("SCH_ID:["+schId+"]");
            logger.debug("DB_ID:["+dbmsId+"]");
            
            pstmt.executeUpdate();
            
            
            con.commit();
            
            return schId;
		}catch(SQLException se){
            logger.error(se);
            if (con != null) con.rollback();
            throw se;
        }catch(Exception e){
            logger.error(e);
            if (con != null) con.rollback();
            throw e;
        } finally {
            if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
            if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}            
            if(con != null) try { con.close(); } catch(Exception igonred) {}            
        }
		
	}
}
