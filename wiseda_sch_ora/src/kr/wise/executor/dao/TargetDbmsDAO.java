package kr.wise.executor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.UtilEncryption;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class TargetDbmsDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TargetDbmsDAO.class);

	public TargetDbmsDM selectTargetDbms(String dbmsId) throws SQLException, Exception {
		return selectTargetDbmsList(dbmsId).get(0);
	}

	public List<TargetDbmsDM> selectTargetDbmsList(String dbmsId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT  DB.DB_CONN_TRG_ID       ");
		query.append("\n        ,DB.DB_CONN_TRG_PNM      ");
		query.append("\n        ,DB.DBMS_TYP_CD          ");
		query.append("\n        ,DB.DB_CONN_AC_ID        ");
		query.append("\n        ,DB.DB_CONN_AC_PWD       ");
		query.append("\n        ,S.DB_SCH_ID             ");
		query.append("\n        ,S.DB_SCH_PNM            ");
		query.append("\n        ,DB.CONN_TRG_DRVR_NM     ");
		query.append("\n        ,DB.CONN_TRG_LNK_URL     ");
		query.append("\n        ,S.DMN_PDT_YN            ");
		query.append("\n        ,S.COL_PRF_YN            ");
		query.append("\n        ,DB.CONN_TRG_DB_LNK_CHRW ");
		query.append("\n      , DB.DBMS_VERS_CD         ");
		query.append("\n   FROM WAA_DB_CONN_TRG DB       ");
		query.append("\n        INNER JOIN WAA_DB_SCH S  ");
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
				dm.setDb_user(rs.getString("DB_CONN_AC_ID"));
//				String password = UtilEncryption.decrypt(rs.getString("DB_CONN_AC_PWD"));
				String password = rs.getString("DB_CONN_AC_PWD");//암호화 주석처리
				dm.setDb_pwd(password);
				dm.setDbSchId(rs.getString("DB_SCH_ID"));
				dm.setDbc_owner_nm(rs.getString("DB_SCH_PNM"));
				dm.setJdbc_driver(rs.getString("CONN_TRG_DRVR_NM"));
				dm.setConnect_string(rs.getString("CONN_TRG_LNK_URL"));
				dm.setDmnPdtYn(rs.getString("DMN_PDT_YN"));
				dm.setColPrfYn(rs.getString("COL_PRF_YN"));
				dm.setDbLinkNm(rs.getString("CONN_TRG_DB_LNK_CHRW"));
				dm.setDbms_vers_cd(rs.getString("DBMS_VERS_CD"));
				
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
	
	
	public List<TargetDbmsDM> selectTrgDbmsByTrgDcd(String trgDcd) throws SQLException, Exception {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.DB_CONN_TRG_ID ") ;
		strSQL.append("\n        ,A.DB_CONN_TRG_PNM ") ;
		strSQL.append("\n        ,A.DBMS_TYP_CD ") ;
		strSQL.append("\n        ,A.DB_CONN_AC_ID ") ;
		strSQL.append("\n        ,A.DB_CONN_AC_PWD ") ;
		strSQL.append("\n        ,A.CONN_TRG_DRVR_NM ") ;
		strSQL.append("\n        ,A.CONN_TRG_LNK_URL ") ;
		strSQL.append("\n        ,B.DB_SCH_PNM ") ;
		strSQL.append("\n        ,B.DB_SCH_ID ") ;
		strSQL.append("\n   FROM WAA_DB_CONN_TRG A ") ;
		strSQL.append("\n        INNER JOIN WAA_DB_SCH B ") ;
		strSQL.append("\n           ON A.DB_CONN_TRG_ID = B.DB_CONN_TRG_ID ") ;
		strSQL.append("\n          AND B.EXP_DTM = TO_DATE( '9999-12-31', 'YYYY-MM-DD') ") ;
		strSQL.append("\n          AND B.REG_TYP_CD IN ('C', 'U') ") ;
		strSQL.append("\n WHERE A.EXP_DTM = TO_DATE( '9999-12-31', 'YYYY-MM-DD') ") ;
		strSQL.append("\n   AND A.REG_TYP_CD IN ('C', 'U') ") ;
		strSQL.append("\n   AND DDL_TRG_DCD =  '").append(trgDcd).append("' ");
//		strSQL.append("\n   AND CD_SND_TRG_YN = 'Y' --코드?�송?�?�여부 ") ;
//		strSQL.append("\n   AND CD_AUTO_SND_YN = 'Y' -- 코드?�동?�송?��? ") ;
//		logger.debug(strSQL);
		
		List<TargetDbmsDM> list = new ArrayList<TargetDbmsDM>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TargetDbmsDM dm = new TargetDbmsDM();
				dm.setDbms_id(rs.getString("DB_CONN_TRG_ID"));
				dm.setDbms_enm(rs.getString("DB_CONN_TRG_PNM"));
				dm.setDbms_type_cd(rs.getString("DBMS_TYP_CD"));
				dm.setDb_user(rs.getString("DB_CONN_AC_ID"));
				String password = UtilEncryption.decrypt(rs.getString("DB_CONN_AC_PWD"));
				dm.setDb_pwd(password);
//				dm.setDb_pwd(rs.getString("DB_CONN_AC_PWD"));
				dm.setDbc_owner_nm(rs.getString("DB_SCH_PNM"));
				dm.setJdbc_driver(rs.getString("CONN_TRG_DRVR_NM"));
				dm.setConnect_string(rs.getString("CONN_TRG_LNK_URL"));
				dm.setDbSchId(rs.getString("DB_SCH_ID"));
				
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


	public Map<String, String> selectPkDbcInfo(Connection con, String prfId) throws SQLException, Exception {

		Map<String, String> map = new HashMap<String, String>();

		String pkTblNm = new String();
		StringBuffer pkColNm = new StringBuffer();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT T.DBC_TBL_NM, C.DBC_COL_NM ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M ");
		strSQL.append("\n        INNER JOIN WAT_DBC_TBL T ");
		strSQL.append("\n           ON M.DB_CONN_TRG_ID = T.DB_CONN_TRG_ID ");
		strSQL.append("\n          AND M.DB_SCH_ID = T.DB_SCH_ID ");
		strSQL.append("\n          AND M.DBC_TBL_NM = T.DBC_TBL_NM ");
		strSQL.append("\n          AND T.REG_TYP IN ('C','U') ");
		strSQL.append("\n        INNER JOIN WAT_DBC_COL C ");
		strSQL.append("\n           ON T.DB_SCH_ID = C.DB_SCH_ID ");
		strSQL.append("\n          AND T.DBC_TBL_NM = C.DBC_TBL_NM ");
		strSQL.append("\n          AND C.PK_YN = 'Y' ");
		strSQL.append("\n          AND C.REG_TYP IN ('C','U') ");
		strSQL.append("\n  WHERE M.PRF_ID = '").append(prfId).append("' ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n  ORDER BY C.PK_ORD ");
		logger.debug(strSQL);

		List<TargetDbmsDM> list = new ArrayList<TargetDbmsDM>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			pstmt = con.prepareStatement(strSQL.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				//?�이블명
				pkTblNm = rs.getString("DBC_TBL_NM");
				//컬럼?�보
				pkColNm.append(", ").append(rs.getString("DBC_COL_NM"));
			}

			if(!Utils.null2Blank(pkTblNm).equals("") ){
				map.put("DBC_TBL_NM", pkTblNm);
				map.put("DBC_COL_NM",  pkColNm.toString().substring(2, pkColNm.toString().length()));
			}
			
			return map;

		} finally {
			if(rs    != null) {	try { rs.close(); } catch(Exception igonred) {}}
			if(pstmt != null)	 {	try { pstmt.close(); } catch(Exception igonred) {}	}
		}
	}

	/** @param dbSchId insomnia 
	 * @throws Exception 
	 * @throws  */
	public TargetDbmsDM getTargetDbmsDMbySchID(String dbSchId) throws Exception {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n  SELECT ");
		strSQL.append("\n         DB.DB_CONN_TRG_PNM ");
		strSQL.append("\n       , DB.DB_CONN_TRG_LNM ");
		strSQL.append("\n       , DB.DBMS_TYP_CD ");
		strSQL.append("\n       , DB.DBMS_VERS_CD ");
		strSQL.append("\n       , DB.CONN_TRG_DB_LNK_CHRW ");
		strSQL.append("\n       , DB.CONN_TRG_LNK_URL ");
		strSQL.append("\n       , DB.CONN_TRG_DRVR_NM ");
		strSQL.append("\n       , DB.DB_CONN_AC_ID ");
		strSQL.append("\n       , DB.DB_CONN_AC_PWD ");
		strSQL.append("\n       , S.DB_SCH_ID ");
		strSQL.append("\n       , S.DB_SCH_PNM ");
		strSQL.append("\n       , S.DB_SCH_LNM ");
		strSQL.append("\n  FROM WAA_DB_CONN_TRG DB ");
		strSQL.append("\n       INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n          ON DB.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n         AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n         AND S.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n  WHERE DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n    AND DB.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n    AND S.DB_SCH_ID = ? ");

		logger.debug(strSQL.toString()+"["+dbSchId+"]");
		
		TargetDbmsDM dm = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, dbSchId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
//				TargetDbmsDM dm = new TargetDbmsDM();
				dm = new TargetDbmsDM();
//				dm.setDbms_id(rs.getString("DB_CONN_TRG_ID"));
				dm.setDbLinkNm(rs.getString("CONN_TRG_DB_LNK_CHRW"));
				dm.setDbms_enm(rs.getString("DB_CONN_TRG_PNM"));
				dm.setDbms_type_cd(rs.getString("DBMS_TYP_CD"));
				dm.setDb_user(rs.getString("DB_CONN_AC_ID"));
				String password = UtilEncryption.decrypt(rs.getString("DB_CONN_AC_PWD"));
				dm.setDb_pwd(password);
				dm.setDbc_owner_nm(rs.getString("DB_SCH_PNM"));
				dm.setJdbc_driver(rs.getString("CONN_TRG_DRVR_NM"));
				dm.setConnect_string(rs.getString("CONN_TRG_LNK_URL"));
				dm.setDbSchId(rs.getString("DB_SCH_ID"));
			}
		
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
		
		return dm;
		
	}

}
