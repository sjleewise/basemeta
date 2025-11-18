package kr.wise.executor.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorMng;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.ProfileResultDM;
import kr.wise.executor.dm.ProfileResultPatternDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class ProfileDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProfileDAO.class);
	

	//프로파일정보조회
	public Map<String, String> selectProfileInfo(String prfId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT DB_CONN_TRG_ID  ");
		query.append("\n          , OBJ_NM              ");
		query.append("\n          , PRF_KND_CD         ");
		query.append("\n          , PRF_ID              ");
		query.append("\n   FROM WAM_PRF_MSTR         ");
		query.append("\n  WHERE PRF_ID = ?          ");
		query.append("\n     AND REG_TYP_CD IN ('C','U')  ");
		
		logger.debug(query+"\n" + "PRF_ID["+prfId+"]");

		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, prfId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				map = new HashMap<String, String>();
				map.put("DB_CONN_TRG_ID", rs.getString("DB_CONN_TRG_ID"));
				map.put("OBJ_NM", rs.getString("OBJ_NM"));
				map.put("PRF_KND_CD", rs.getString("PRF_KND_CD"));
				map.put("PRF_ID", rs.getString("PRF_ID"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	

	
	public SqlGeneratorVO sqlGenerator(String prfId ) throws SQLException, Exception {

		Connection con = ConnectionHelper.getDAConnection();
		Connection metaCon = ConnectionHelper.getMetaConnection();
		
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();

		//진단대상 DBMS_TYP_CD 조회
		WamPrfMstrVO  prfmstrVO = getSqlGenDbmsInfoByPrfId(prfId, con);
		
		String prfKndCd    = prfmstrVO.getPrfKndCd();
		String dbConnTrgId = prfmstrVO.getDbConnTrgId();
		String dbmsTypCd   = prfmstrVO.getDbmsTypCd(); 

		logger.debug(" ===== dbms type  : "  + dbmsTypCd);
		logger.debug(" ===== prf knd cd  : " + prfKndCd);

		//프로파일 정보 조회
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!Utils.isNull(prfId)) {
			//컬럼분석 상세조회
			if(prfKndCd.equals("PC01")){
				WamPrfColAnaVO result = getColAnaDtl(prfId, con);
				sqlGenMap.put("prfDtlVO", result);
			}
			//유효값 분석 상세 조회
			else if(prfKndCd.equals("PC02")){
				WamPrfEfvaAnaVO result = getPrfPC02Dtl(prfId, con);
				
				//사용자정의 유효값
				if(result.getEfvaAnaKndCd().equals("USER")){
					List<WamPrfEfvaUserDfVO>  List =  getPrfPC02UserDfLst(prfId, con);
					result.setWamPrfEfvaUserDfVO((ArrayList<WamPrfEfvaUserDfVO>) List);
				}

				//메타정보 조회
				if(result.getEfvaAnaKndCd().equals("META")){
					MetaDmnItfVO metaDmnVO = new MetaDmnItfVO();
					metaDmnVO.setDbcColNm(prfmstrVO.getDbcColNm());
					metaDmnVO = getMetaDmnDtl(metaDmnVO, metaCon);
					
					result.setMetaDmnItfVO(metaDmnVO);
					// O 단일코드 L 목록성코드 C 복합코드
					//단순코드
					if( UtilString.isBlank(metaDmnVO.getCdValTypCd()) || "O".equals(metaDmnVO.getCdValTypCd())){
						
						ArrayList<MetaDmnCdValItfVO> list = getMetaDmnCdValLst(metaDmnVO.getDmnId(), metaCon);
						result.setMetaDmnCdValItfVO(list);
					}
				}
				sqlGenMap.put("prfDtlVO", result);
			}
			//날짜형식 분석 상세 조회
			else if(prfKndCd.equals("PC03")){
				WamPrfDtfrmAnaVO result = getPrfPC03Dtl(prfId, con);
				sqlGenMap.put("prfDtlVO", result);
			}
			//범위 분석 상세 조회
			else if(prfKndCd.equals("PC04")){
				WamPrfRngAnaVO result = getPrfPC04Dtl(prfId, con);
				sqlGenMap.put("prfDtlVO", result);
			}
			//패턴 분석 상세 조회
			else if(prfKndCd.equals("PC05")){
				//사용자정의패턴
				List<WamPrfPtrAnaVO> list =  getPrfPC05UserDfLst(prfId, con);
				sqlGenMap.put("prfDtlVO", list);
			}
			//관계분석
			else if(prfKndCd.equals("PT01")){
				//관계분석 상세
				WamPrfRelTblVO result = getPrfPT01Dtl(prfId, con);
				//관계컬럼
				if(null != result){
					List<WamPrfRelColVO> list =getPrfPT01RelColLst(prfId, con);
					result.setWamPrfRelColVO((ArrayList<WamPrfRelColVO>) list);
				}
				sqlGenMap.put("prfDtlVO", result);
			}

			//중복분석
			else if(prfKndCd.equals("PT02")){
//				WamPrfUnqColVO result = (WamPrfUnqColVO) pt02Service.getPrfPT02Dtl(prfId);
				List<WamPrfUnqColVO> list  = getPrfPT02UnqColLst(prfId, con);
				sqlGenMap.put("prfDtlVO", list);
			}
		}

		sqlGenMap.put("prfMstrVO", prfmstrVO);
		
		//SQL 생성
		SqlGeneratorMng sqlGenMng = new SqlGeneratorMng();
		SqlGeneratorVO sqlVO = sqlGenMng.getSql(sqlGenMap);
		
		logger.debug(" ===== dbms type  : "  + sqlVO.getErrorCount());
		
		sqlVO.setPrfId(prfId);
		sqlVO.setPrfKndCd(prfKndCd);
		sqlVO.setDbConnTrgId(dbConnTrgId);

		con.close();
		metaCon.close();
		
		return sqlVO;

	}
	
	//프로파일 정보
	public WamPrfMstrVO getSqlGenDbmsInfoByPrfId(String prfId, Connection con ) throws SQLException{
		WamPrfMstrVO  prfmstrVO = new WamPrfMstrVO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT M.PRF_ID           ");
		strSQL.append("\n      , M.PRF_KND_CD       ");
		strSQL.append("\n      , M.DBC_TBL_NM       ");
		strSQL.append("\n      , M.OBJ_NM           ");
		strSQL.append("\n      , M.ADT_CND_SQL      ");
		strSQL.append("\n      , DB.DB_CONN_TRG_ID  ");
		strSQL.append("\n      , DB.DB_CONN_TRG_PNM ");
		strSQL.append("\n      , DB.DBMS_TYP_CD     ");
		strSQL.append("\n      , S.DB_SCH_ID        ");
		strSQL.append("\n      , S.DB_SCH_PNM       ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M     ");
		strSQL.append("\n         INNER JOIN WAA_DB_CONN_TRG DB ");
		strSQL.append("\n            ON DB.DB_CONN_TRG_ID = M.DB_CONN_TRG_ID ");
		strSQL.append("\n           AND DB.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n           AND DB.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n         INNER JOIN WAA_DB_SCH S ");
		strSQL.append("\n             ON M.DB_SCH_ID = S.DB_SCH_ID ");
		strSQL.append("\n            AND M.DB_CONN_TRG_ID = S.DB_CONN_TRG_ID ");
		strSQL.append("\n            AND S.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n            AND DB.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n WHERE M.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n   AND M.PRF_ID = ? ");

		logger.debug(" ===== 프로파일 정보 조회 =====");
		logger.debug(strSQL.toString());
		
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			prfmstrVO.setPrfId(rs.getString("PRF_ID"));
			prfmstrVO.setPrfKndCd(rs.getString("PRF_KND_CD"));
			prfmstrVO.setDbcTblNm(rs.getString("DBC_TBL_NM"));
			prfmstrVO.setDbcColNm(rs.getString("OBJ_NM"));
			prfmstrVO.setObjNm(rs.getString("OBJ_NM"));
			prfmstrVO.setAdtCndSql(rs.getString("ADT_CND_SQL"));
			prfmstrVO.setDbConnTrgId(rs.getString("DB_CONN_TRG_ID"));
			prfmstrVO.setDbConnTrgPnm(rs.getString("DB_CONN_TRG_PNM"));
			prfmstrVO.setDbmsTypCd(rs.getString("DBMS_TYP_CD"));
			prfmstrVO.setDbSchId(rs.getString("DB_SCH_ID"));
			prfmstrVO.setDbSchPnm(rs.getString("DB_SCH_PNM"));
		}
		
		return prfmstrVO;
	}
	
	//컬럼분석
	public WamPrfColAnaVO getColAnaDtl(String prfId, Connection con ) throws SQLException{
		WamPrfColAnaVO  resVO = new WamPrfColAnaVO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT A.MIN_MAX_VAL_ANA_YN               ");
		strSQL.append("\n      , A.AVG_ANA_YN                       ");
		strSQL.append("\n      , A.STDV_ANA_YN                      ");
		strSQL.append("\n      , A.VRN_ANA_YN                       ");
		strSQL.append("\n      , A.AONL_YN                          ");
		strSQL.append("\n      , A.SPAC_ANA_YN                      ");
		strSQL.append("\n      , A.LEN_ANA_YN                       ");
		strSQL.append("\n      , A.CRD_ANA_YN                       ");
		strSQL.append("\n      , A.PAT_ANA_YN                       ");
		strSQL.append("\n      , C.DATA_TYPE                        ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M                     ");
		strSQL.append("\n        INNER JOIN WAM_PRF_COL_ANA A       ");
		strSQL.append("\n           ON M.PRF_ID = A.PRF_ID          ");
		strSQL.append("\n        INNER JOIN WAT_DBC_COL C           ");
		strSQL.append("\n           ON C.DB_SCH_ID  = M.DB_SCH_ID   ");
		strSQL.append("\n          AND C.DBC_TBL_NM = M.DBC_TBL_NM  ");
		strSQL.append("\n          AND C.DBC_COL_NM = M.OBJ_NM      ");
		strSQL.append("\n  WHERE M.PRF_ID = ?                       ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C','U')          ");
		
		logger.debug("getColAnaDtl >>>> ");
		logger.debug(strSQL.toString());
		logger.debug(prfId);

		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			resVO.setMinMaxValAnaYn( rs.getString("MIN_MAX_VAL_ANA_YN"));
			resVO.setAvgAnaYn(rs.getString("AVG_ANA_YN"));
			resVO.setStdvAnaYn(rs.getString("STDV_ANA_YN"));
			resVO.setVrnAnaYn(rs.getString("VRN_ANA_YN"));
			resVO.setAonlYn(rs.getString("AONL_YN"));
			resVO.setSpacAnaYn(rs.getString("SPAC_ANA_YN"));
			resVO.setLenAnaYn(rs.getString("LEN_ANA_YN"));
			resVO.setCrdAnaYn(rs.getString("CRD_ANA_YN"));
			resVO.setPatAnaYn(rs.getString("PAT_ANA_YN"));
			resVO.setDataType(rs.getString("DATA_TYPE"));
		}
		
		return resVO;
	}
	
	//유효값 분석
	public WamPrfEfvaAnaVO getPrfPC02Dtl(String prfId, Connection con ) throws SQLException{
		WamPrfEfvaAnaVO  resVO = new WamPrfEfvaAnaVO();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n select A.EFVA_ANA_KND_CD ");
		strSQL.append("\n        ,A.CD_TBL_DB_CONN_TRG_ID ");
		strSQL.append("\n        ,GET_TRG_DB_PNM(A.CD_TBL_DB_CONN_TRG_ID) AS CD_TBL_DB_CONN_TRG_PNM ");
		strSQL.append("\n        ,A.CD_TBL_DB_SCH_ID ");
		strSQL.append("\n        ,GET_DB_SCH_LNM(A.CD_TBL_DB_SCH_ID)  AS CD_TBL_DB_SCH_LNM ");
		strSQL.append("\n        ,GET_DB_SCH_PNM(A.CD_TBL_DB_SCH_ID)  AS CD_TBL_DB_SCH_PNM ");
		strSQL.append("\n        ,A.CD_TBL_DBC_TBL_NM ");
		strSQL.append("\n        ,GET_DBC_TBL_KNM(A.CD_TBL_DB_SCH_ID, A.CD_TBL_DB_CONN_TRG_ID, A.CD_TBL_DBC_TBL_NM) AS CD_TBL_DBC_TBL_KNM ");
		strSQL.append("\n        ,A.CD_TBL_DBC_COL_NM ");
		strSQL.append("\n        ,GET_DBC_COL_KNM(A.CD_TBL_DB_SCH_ID, A.CD_TBL_DBC_TBL_NM, A.CD_TBL_DBC_COL_NM) AS CD_TBL_DBC_COL_KNM ");
		strSQL.append("\n        ,A.CD_TBL_CD_ID_COL_NM ");
		strSQL.append("\n        ,GET_DBC_COL_KNM(A.CD_TBL_DB_SCH_ID, A.CD_TBL_DBC_TBL_NM, A.CD_TBL_CD_ID_COL_NM) AS CD_TBL_CD_ID_COL_KNM ");
		strSQL.append("\n        ,A.CD_TBL_CD_ID ");
		strSQL.append("\n        ,A.CD_TBL_ADT_CND_SQL ");
		strSQL.append("\n   from WAM_PRF_MSTR M ");
		strSQL.append("\n        INNER JOIN WAM_PRF_EFVA_ANA A ");
		strSQL.append("\n            ON M.PRF_ID = A.PRF_ID ");
		strSQL.append("\n  where M.PRF_ID = ? ");
		strSQL.append("\n    and M.REG_TYP_CD IN ('C','U') ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			resVO.setEfvaAnaKndCd(rs.getString("EFVA_ANA_KND_CD"));
			resVO.setCdTblDbConnTrgId(rs.getString("CD_TBL_DB_CONN_TRG_ID"));
			resVO.setCdTblDbConnTrgPdm(rs.getString("CD_TBL_DB_CONN_TRG_PNM"));
			resVO.setCdTblDbSchPdm(rs.getString("CD_TBL_DB_SCH_PNM"));
			resVO.setCdTblDbcTblNm(rs.getString("CD_TBL_DBC_TBL_NM"));
			resVO.setCdTblDbcColNm(rs.getString("CD_TBL_DBC_COL_NM"));
			resVO.setCdTblCdIdColNm(rs.getString("CD_TBL_CD_ID_COL_NM"));
			resVO.setCdTblCdId(rs.getString("CD_TBL_CD_ID"));
			resVO.setCdTblAdtCndSql(rs.getString("CD_TBL_ADT_CND_SQL"));
		}
		return resVO;
	}
	
	//사용자정의유효값
	public List<WamPrfEfvaUserDfVO> getPrfPC02UserDfLst(String prfId, Connection con ) throws SQLException{
		List<WamPrfEfvaUserDfVO>  List = new ArrayList<WamPrfEfvaUserDfVO>();
		WamPrfEfvaUserDfVO resVO = new WamPrfEfvaUserDfVO();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n select USER_DF_EFVA ");
		strSQL.append("\n   from WAM_PRF_EFVA_USER_DF ");
		strSQL.append("\n  where PRF_ID = ? ");
		strSQL.append("\n    and REG_TYP_CD IN ('C','U') ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			resVO = new WamPrfEfvaUserDfVO();
			resVO.setUserDfEfva(rs.getString( "USER_DF_EFVA") ) ;
			List.add( resVO );
		}
		
		return List;
	}
	
	//메타정보조회
	public MetaDmnItfVO getMetaDmnDtl (MetaDmnItfVO metaDmnVO, Connection metaCon ) throws SQLException{
		MetaDmnItfVO resVO = new MetaDmnItfVO();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT  A.SDITM_ID ");
		strSQL.append("\n        ,A.SDITM_LNM ");
		strSQL.append("\n        ,A.SDITM_PNM ");
		strSQL.append("\n        ,G.DMNG_ID ");
		strSQL.append("\n        ,G.DMNG_LNM ");
		strSQL.append("\n        ,G.DMNG_PNM ");
		strSQL.append("\n        ,B.DMN_ID ");
		strSQL.append("\n        ,B.DMN_LNM ");
		strSQL.append("\n        ,B.DMN_PNM ");
		strSQL.append("\n        ,B.LST_ENTY_ID ");
		strSQL.append("\n        ,B.LST_ENTY_PNM ");
		strSQL.append("\n        ,B.LST_ENTY_LNM ");
		strSQL.append("\n        ,B.CD_VAL_TYP_CD ");
		strSQL.append("\n        ,CD.COMM_DTL_CD_NM AS CD_VAL_TYP_NM ");
		//목록성 컬럼 차 후 구현
		strSQL.append("\n        ,PC.PDM_COL_PNM ");
		strSQL.append("\n        ,PC.PDM_COL_LNM ");
		strSQL.append("\n        ,D.INFOTP_LNM ");
		strSQL.append("\n        ,D.DATA_TYPE ");
		strSQL.append("\n        ,D.DATA_LEN ");
		strSQL.append("\n        ,D.DATA_SCAL ");
		strSQL.append("\n FROM WAM_SDITM A ");
		strSQL.append("\n      INNER JOIN WAM_DMN B ");
		strSQL.append("\n         ON A.DMN_ID = B.DMN_ID ");
		strSQL.append("\n      INNER JOIN WAA_DMNG G ");
		strSQL.append("\n         ON A.DMNG_ID = G.DMNG_ID ");
		strSQL.append("\n        AND G.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n      INNER JOIN WAA_DMNG_INFOTP_MAP C ");
		strSQL.append("\n         ON A.DMNG_ID = C.DMNG_ID ");
		strSQL.append("\n        AND A.INFOTP_ID = C.INFOTP_ID ");
		strSQL.append("\n        AND C.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n      INNER JOIN WAA_INFO_TYPE D ");
		strSQL.append("\n         ON C.INFOTP_ID = D.INFOTP_ID ");
		strSQL.append("\n        AND D.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n       LEFT OUTER JOIN WAA_COMM_DTL_CD CD ");
		strSQL.append("\n         ON B.CD_VAL_TYP_CD = CD.COMM_DTL_CD ");
		strSQL.append("\n        AND CD.EXP_DTM = TO_DATE('99991231','YYYYMMDD') ");
		strSQL.append("\n        AND CD.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n        AND COMM_DCD_ID = 'OBJ_00000001017' --코드도메인유형 ");
		strSQL.append("\n       LEFT OUTER JOIN WAM_PDM_COL PC ");
		strSQL.append("\n         ON B.LST_ENTY_ID = PC.PDM_TBL_ID ");
		strSQL.append("\n        AND A.SDITM_ID = PC.SDITM_ID ");
		strSQL.append("\n        AND PC.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n  WHERE SDITM_PNM = RTRIM( ? , '1234567890') ");
		
		
		logger.debug("sql: "+strSQL.toString());
		logger.debug("param :"+metaDmnVO.getDbcColNm());
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = metaCon.prepareStatement(strSQL.toString());
		pstmt.setString(1, metaDmnVO.getDbcColNm());
		
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			resVO.setDmnId(rs.getString("DMN_ID"));
			resVO.setCdValTypCd(rs.getString("CD_VAL_TYP_CD"));
			resVO.setLstEntyPnm(rs.getString("LST_ENTY_PNM"));
			//목록성 컬럼 차후 구현
			resVO.setPdmColPnm(rs.getString("PDM_COL_PNM"));
		}
		
		return resVO;
	}
	
	
	//메타단순코드 목록 조회
	public ArrayList<MetaDmnCdValItfVO> getMetaDmnCdValLst (String dmnId, Connection metaCon ) throws SQLException{
		
		ArrayList<MetaDmnCdValItfVO> list = new ArrayList<MetaDmnCdValItfVO>();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT CD_VAL_ID ");
		strSQL.append("\n          ,CD_VAL ");
		strSQL.append("\n          ,CD_VAL_NM ");
		strSQL.append("\n          ,CD.OBJ_DESCN ");
		strSQL.append("\n  FROM WAM_DMN M ");
		strSQL.append("\n          INNER JOIN WAM_CD_VAL CD ");
		strSQL.append("\n              ON M.DMN_ID = CD.DMN_ID ");
		strSQL.append("\n             AND CD.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n WHERE M.DMN_ID = ? ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n ORDER BY CD.CD_VAL ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = metaCon.prepareStatement(strSQL.toString());
		pstmt.setString(1, dmnId);
		rs = pstmt.executeQuery();
		
		MetaDmnCdValItfVO resVO = new MetaDmnCdValItfVO();
		
		while(rs.next()) {
			resVO = new MetaDmnCdValItfVO();
			resVO.setCdVal(rs.getString( "CD_VAL") ) ;
			list.add( resVO );
		}
		
		return list;
	}
	
	//날짜형식 분석
	public WamPrfDtfrmAnaVO getPrfPC03Dtl(String prfId, Connection con ) throws SQLException{
		WamPrfDtfrmAnaVO  resVO = new WamPrfDtfrmAnaVO();
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT A.DATE_FRM_CD                  ");
		strSQL.append("\n      , A.USER_DATE_FRM                ");
		strSQL.append("\n      , M.ADT_CND_SQL                  ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M                 ");
		strSQL.append("\n        INNER JOIN WAM_PRF_DTFRM_ANA A ");
		strSQL.append("\n            ON M.PRF_ID = A.PRF_ID     ");
		strSQL.append("\n  WHERE M.PRF_ID = ?                   ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C','U')      ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			
		    resVO.setDateFrmCd(rs.getString("DATE_FRM_CD"));
			resVO.setUserDateFrm(rs.getString("USER_DATE_FRM"));
			resVO.setAdtCndSql(rs.getString("ADT_CND_SQL"));
		}
		
		return resVO;
	}
	
	//범위분석
	public WamPrfRngAnaVO getPrfPC04Dtl(String prfId, Connection con ) throws SQLException{
		WamPrfRngAnaVO  resVO = new WamPrfRngAnaVO();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n select A.RNG_OPR1 ");
		strSQL.append("\n        ,A.RNG_EFVA1 ");
		strSQL.append("\n        ,A.RNG_CNC ");
		strSQL.append("\n        ,A.RNG_OPR2 ");
		strSQL.append("\n        ,A.RNG_EFVA2 ");
		strSQL.append("\n  from WAM_PRF_MSTR M ");
		strSQL.append("\n       INNER JOIN  WAM_PRF_RNG_ANA A ");
		strSQL.append("\n          ON M.PRF_ID = A.PRF_ID ");
		strSQL.append("\n where M.PRF_ID = ? ");
		strSQL.append("\n   and M.REG_TYP_CD IN ('C','U') ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			resVO.setRngOpr1(rs.getString("RNG_OPR1"));
			resVO.setRngOpr2(rs.getString("RNG_OPR2"));
			resVO.setRngEfva1(rs.getString("RNG_EFVA1"));
			resVO.setRngEfva2(rs.getString("RNG_EFVA2"));
			resVO.setRngCnc(rs.getString("RNG_CNC"));
		}
		
		return resVO;
	}
	
	//패턴분석 사용자정의패턴 
	public List<WamPrfPtrAnaVO> getPrfPC05UserDfLst(String prfId, Connection con ) throws SQLException{
		List<WamPrfPtrAnaVO> list = new ArrayList<WamPrfPtrAnaVO>();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n select  A.USER_DF_PTR ");
		strSQL.append("\n        ,A.USER_DF_PTR_NM ");
		strSQL.append("\n   from WAM_PRF_MSTR M ");
		strSQL.append("\n        INNER JOIN  WAM_PRF_PTR_USER_DF A ");
		strSQL.append("\n            ON M.PRF_ID = A.PRF_ID ");
		strSQL.append("\n  where M.PRF_ID = ? ");
		strSQL.append("\n    and M.REG_TYP_CD IN ('C','U') ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		WamPrfPtrAnaVO resVO = new WamPrfPtrAnaVO();
		
		while(rs.next()) {
			resVO = new WamPrfPtrAnaVO();
			resVO.setUserDfPtr(rs.getString("USER_DF_PTR"));
			list.add(resVO);
		}
		
		return list;
	}
	
	//관계분석
	public WamPrfRelTblVO getPrfPT01Dtl(String prfId, Connection con ) throws SQLException{
		WamPrfRelTblVO  resVO = new WamPrfRelTblVO();
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT T.PA_TBL_DB_CONN_TRG_ID ");
		strSQL.append("\n         ,GET_TRG_DB_PNM(T.PA_TBL_DB_CONN_TRG_ID) AS PA_TBL_DB_CONN_TRG_PNM ");
		strSQL.append("\n         ,T.PA_TBL_DBC_SCH_ID ");
		strSQL.append("\n         ,GET_DB_SCH_PNM(T.PA_TBL_DBC_SCH_ID) AS PA_TBL_DBC_SCH_PNM ");
		strSQL.append("\n         ,T.PA_TBL_DBC_TBL_NM ");
		strSQL.append("\n         ,GET_DBC_TBL_KNM(T.PA_TBL_DBC_SCH_ID , T.PA_TBL_DB_CONN_TRG_ID, T.PA_TBL_DBC_TBL_NM) AS PA_TBL_DBC_TBL_KOR_NM ");
		strSQL.append("\n         ,T.PA_TBL_ADT_CND_SQL ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M ");
		strSQL.append("\n        INNER JOIN WAM_PRF_REL_TBL T ");
		strSQL.append("\n           ON M.PRF_ID = T.PRF_ID ");
		strSQL.append("\n          AND T.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n WHERE M.PRF_ID = ? ");
		strSQL.append("\n   AND M.REG_TYP_CD IN ('C', 'U') ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			resVO.setPaTblDbcSchNm(rs.getString("PA_TBL_DBC_SCH_PNM"));
			resVO.setPaTblDbcTblNm(rs.getString("PA_TBL_DBC_TBL_NM"));
			resVO.setPaTblAdtCndSql(rs.getString("PA_TBL_ADT_CND_SQL"));
		}
		return resVO;
	}
	
	//관계분석 컬럼
	public List<WamPrfRelColVO> getPrfPT01RelColLst(String prfId, Connection con ) throws SQLException{
		List<WamPrfRelColVO> list = new ArrayList<WamPrfRelColVO>();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT M.PRF_ID ");
		strSQL.append("\n       ,C.CH_TBL_DBC_COL_NM ");
		strSQL.append("\n       ,C.PA_TBL_DBC_COL_NM ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M ");
		strSQL.append("\n          INNER JOIN WAM_PRF_REL_TBL T ");
		strSQL.append("\n              ON M.PRF_ID = T.PRF_ID ");
		strSQL.append("\n             AND T.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n          INNER JOIN WAM_PRF_REL_COL C ");
		strSQL.append("\n               ON M.PRF_ID = C.PRF_ID ");
		strSQL.append("\n              AND C.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n  WHERE M.PRF_ID = ? ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C', 'U') ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		WamPrfRelColVO resVO = new WamPrfRelColVO();
		while(rs.next()) {
			resVO = new WamPrfRelColVO();
			resVO.setChTblDbcColNm(rs.getString("CH_TBL_DBC_COL_NM"));
			resVO.setPaTblDbcColNm(rs.getString("PA_TBL_DBC_COL_NM"));
			list.add(resVO);
		}
		
		return list;
	}
	
	//중복분석 컬럼
	public List<WamPrfUnqColVO> getPrfPT02UnqColLst(String prfId, Connection con ) throws SQLException{
		List<WamPrfUnqColVO> list = new ArrayList<WamPrfUnqColVO>();
		
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.DBC_COL_NM ");
		strSQL.append("\n   FROM WAM_PRF_MSTR M ");
		strSQL.append("\n          INNER JOIN WAM_PRF_UNQ_COL A ");
		strSQL.append("\n              ON M.PRF_ID = A.PRF_ID ");
		strSQL.append("\n             AND A.REG_TYP_CD IN ('C','U') ");
		strSQL.append("\n  WHERE M.PRF_ID = ? ");
		strSQL.append("\n    AND M.REG_TYP_CD IN ('C','U') ");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = con.prepareStatement(strSQL.toString());
		pstmt.setString(1, prfId);
		rs = pstmt.executeQuery();
		
		WamPrfUnqColVO resVO = new WamPrfUnqColVO();
		while(rs.next()){
			resVO = new WamPrfUnqColVO();
			resVO.setDbcColNm( rs.getString("DBC_COL_NM") ) ;
			list.add(resVO);
		}
		return list;
	}
	
	
	//날짜형식분석 유형 조회
	public Map<String, String> selectPrfValType(String prfId) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT VALTYPE_CD            ");
		query.append("\n   FROM WDQ_PRF_VALTYPE         ");
		query.append("\n  WHERE PRF_ID = ?          ");
		
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, prfId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map = new HashMap<String, String>();
				map.put("VALTYPE_CD", rs.getString("VALTYPE_CD"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}

	//프로파일 분석 결과 저장
	public void saveProfileResult(String logId, String staDttm, String endDttm, ExecutorDM executorDM, ProfileResultDM resultDM, ProfileResultPatternDM resultPatternDM) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder query = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			//차수 존재시
			if(executorDM.getAnaDgr() != null){
				logger.debug("=== start exe_num profile ===");
				// 삭제할 차수의 ANA_STR_DTM 값을 구한다.
				pstmt = con.prepareStatement(" SELECT TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS ANA_STR_DTM FROM WAM_PRF_RESULT WHERE PRF_ID = ? AND ANA_DGR = ? ");
				pstmt.setString(1, executorDM.getShdJobId());
				pstmt.setBigDecimal(2, executorDM.getAnaDgr());
				rs = pstmt.executeQuery();
				
				List<String> prevStaDttmList = new ArrayList<String>();
				while(rs.next()) {
					prevStaDttmList.add(rs.getString("ANA_STR_DTM"));
				}
				if(rs != null) try { rs.close(); } catch(Exception igonred) {}
				pstmt.close();
				pstmt = null;
				
				// 기존 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_PRF_ERR_DATA WHERE PRF_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				// 기존 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_PRF_ERR_DATA_PKDATA WHERE PRF_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;

				//프로파일 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_PRF_RESULT WHERE PRF_ID = ? AND ANA_DGR = ? ");
				pstmt.setString(1, executorDM.getShdJobId());
				pstmt.setBigDecimal(2, executorDM.getAnaDgr());
				pstmt.executeUpdate();
				pstmt.close();
				
				logger.debug("차수 존재 프로파일 진단결과 데이터 삭제 완료!!");
			}
			//차수미존재시
			else{
				logger.debug("=== start no exe_num profile ===");
				// 삭제할 차수의 ANA_STR_DTM 값을 구한다.
				query = new StringBuilder();
				query.append("\n SELECT TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS ANA_STR_DTM ");
				query.append("\n   FROM WAM_PRF_RESULT ");
				query.append("\n  WHERE PRF_ID = ? ");
				query.append("\n    AND ANA_DGR IS NULL ");
				
				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, executorDM.getShdJobId());
				
				logger.debug("===start select query ===");
				rs = pstmt.executeQuery();
				List<String> prevStaDttmList = new ArrayList<String>();
				while(rs.next()) {
					prevStaDttmList.add(rs.getString("ANA_STR_DTM"));
				}
				if(rs != null) try { rs.close(); } catch(Exception igonred) {}
				pstmt.close();
				pstmt = null;
				
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				query = new StringBuilder();
				query.append("\n DELETE FROM WAM_PRF_ERR_DATA  ");
				query.append("\n  WHERE PRF_ID = ?  ");
				query.append("\n    AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAM_PRF_ERR_DATA ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				query = new StringBuilder();
				query.append("\n DELETE FROM WAM_PRF_ERR_DATA_PKDATA  ");
				query.append("\n  WHERE PRF_ID = ?  ");
				query.append("\n    AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAM_PRF_ERR_DATA_PKDATA ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;

				query = new StringBuilder();
				query.append("\n DELETE FROM WAM_PRF_RESULT  ");
				query.append("\n  WHERE PRF_ID = ?  ");
				query.append("\n     AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')   ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAM_PRF_RESULT ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				logger.debug("차수 미존재 프로파일 진단결과 데이터 삭제 완료!!");
			}
			
			
			pstmt = null;
			query = new StringBuilder();
			
			// 프로파일 결과 저장
			logger.debug("=== start insert profile result ===");
			query.append("\n INSERT INTO WAM_PRF_RESULT ( ");
			query.append("\n          PRF_ID ");
			query.append("\n        , ANA_STR_DTM  ");
			query.append("\n        , ANA_END_DTM ");
			query.append("\n        , ANA_DGR ");
			query.append("\n        , ANA_CNT ");
			query.append("\n        , ESN_ER_CNT ");
			query.append("\n        , ANA_LOG_ID ");
			query.append("\n        , ANA_USER_ID ");
			
			//컬럼분석
			//NULL_CNT
			query.append("\n        , NULL_CNT ");
			//SPACE_CNT
			query.append("\n        , SPACE_CNT ");
			//최대최소값 각3건
			query.append("\n        , MIN_VAL1 ");
			query.append("\n        , MIN_VAL2 ");
			query.append("\n        , MIN_VAL3 ");
			query.append("\n        , MAX_VAL1 ");
			query.append("\n        , MAX_VAL2 ");
			query.append("\n        , MAX_VAL3 ");
			//최대최소길이분석 2건
			query.append("\n        , MIN_LEN   ");
			query.append("\n        , MAX_LEN   ");
			query.append("\n        , DATE_YN   ");
			query.append("\n        , TEL_YN    ");
			query.append("\n        , SPACE_RT  ");
			query.append("\n        , CRLF_YN   ");
			query.append("\n        , ALPHA_YN  ");
			query.append("\n        , DATA_FMT  ");
			query.append("\n        , NUM_YN    ");
			query.append("\n        , HUND_RT   ");
			query.append("\n        , CNT_RT    ");
			
			query.append("\n        , STDDEV_VAL   "); //표준편차
			query.append("\n        , VARIANCE_VAL "); //분산
			query.append("\n        , AVG_VAL      "); //평균
			query.append("\n        , UNQ_CNT      "); //유일값수
			query.append("\n        , MIN_CNT_VAL  "); //최소빈도값
			query.append("\n        , MAX_CNT_VAL  "); //최대빈도값
				
			query.append("\n )  VALUES ( ");
			query.append("\n          ? --PRF_ID ");
			query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM  ");
			query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_END_DTM ");
			query.append("\n        , ? --ANA_DGR ");
			query.append("\n        , ? --ANA_CNT ");
			query.append("\n        , ? --ESN_ER_CNT ");
			query.append("\n        , ? --ANA_LOG_ID ");
			query.append("\n        , ? --ANA_USER_ID ");
			//컬럼분석
			//NULL_CNT
			query.append("\n        , ? --NULL_CNT ");
			//스페이스 건수
			query.append("\n        , ? --SPACE_CNT ");
			//최대최소값 각3건
			query.append("\n        , ? --MIN_VAL1 ");
			query.append("\n        , ? --MIN_VAL2 ");
			query.append("\n        , ? --MIN_VAL3 ");
			query.append("\n        , ? --MAX_VAL1 ");
			query.append("\n        , ? --MAX_VAL2 ");
			query.append("\n        , ? --MAX_VAL3 ");
			//최대최소길이분석 2건
			query.append("\n        , ? --MIN_LEN   ");
			query.append("\n        , ? --MAX_LEN   ");
			query.append("\n        , ? --DATE_YN   ");
			query.append("\n        , ? --TEL_YN    ");
			query.append("\n        , ? --SPACE_RT  ");
			query.append("\n        , ? --CRLF_YN   ");
			query.append("\n        , ? --ALPHA_YN  ");
			query.append("\n        , ? --DATA_FMT  ");
			query.append("\n        , ? --NUM_YN    ");
			query.append("\n        , ? --HUND_RT   ");
			query.append("\n        , ? --CNT_RT    ");
			
			query.append("\n        , ? --STDDEV_VAL   ");  //표준편차  
			query.append("\n        , ? --VARIANCE_VAL  ");  //분산    
			query.append("\n        , ? --AVG_VAL  ");  //평균    
			query.append("\n        , ? --UNQ_CNT    ");  //유일값수  
			query.append("\n        , ? --MIN_CNT_VAL   ");  //최소빈도값 
			query.append("\n        , ? --MAX_CNT_VAL    ");  //최대빈도값 
			
			query.append("\n  ) ");
			
			
			int pIdx = 1;
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(pIdx++, executorDM.getShdJobId());
			pstmt.setString(pIdx++, staDttm);
			pstmt.setString(pIdx++, endDttm);
			pstmt.setBigDecimal(pIdx++, executorDM.getAnaDgr());
			pstmt.setLong(pIdx++, resultDM.getTotCnt());
			pstmt.setBigDecimal(pIdx++, resultDM.getErrCnt());
			pstmt.setString(pIdx++, logId);
			pstmt.setString(pIdx++, executorDM.getRqstUserId());
			//컬럼분석
			pstmt.setBigDecimal(pIdx++, resultDM.getNullCnt());
			pstmt.setBigDecimal(pIdx++, resultDM.getSpaceCnt());
			pstmt.setString(pIdx++, resultDM.getMinVal1());
			pstmt.setString(pIdx++, resultDM.getMinVal2());
			pstmt.setString(pIdx++, resultDM.getMinVal3());
			pstmt.setString(pIdx++, resultDM.getMaxVal1());
			pstmt.setString(pIdx++, resultDM.getMaxVal2());
			pstmt.setString(pIdx++, resultDM.getMaxVal3());
			pstmt.setBigDecimal(pIdx++, resultDM.getMinLen());
			pstmt.setBigDecimal(pIdx++, resultDM.getMaxLen());
			pstmt.setString(pIdx++, resultDM.getDateYn());
			pstmt.setString(pIdx++, resultDM.getTelYn());
			pstmt.setString(pIdx++, resultDM.getSpaceRt());  
			pstmt.setString(pIdx++, resultDM.getCrlfYn());
			pstmt.setString(pIdx++, resultDM.getAlphaYn());
			pstmt.setString(pIdx++, resultDM.getDataFmt());
			pstmt.setString(pIdx++, resultDM.getNumYn());
			pstmt.setString(pIdx++, resultDM.getHundRt());
			pstmt.setString(pIdx++, resultDM.getCntRt());
			
			pstmt.setString(pIdx++, resultDM.getStddevVal());
			pstmt.setString(pIdx++, resultDM.getVarianceVal());
			pstmt.setString(pIdx++, resultDM.getAvgVal());
			pstmt.setString(pIdx++, resultDM.getUnqCnt());
			pstmt.setString(pIdx++, resultDM.getMinCntVal());
			pstmt.setString(pIdx++, resultDM.getMaxCntVal());
			
		
			logger.debug(query.toString());
			logger.debug(resultDM.getErrCnt());
			logger.debug(resultDM.getTotCnt());

			
			pstmt.executeUpdate();
			pstmt.close();
			
			if(resultPatternDM != null && !Utils.null2Blank(executorDM.getErDataLdYn()).equals("N") ) {
				// 오류패턴 저장
				logger.debug("=== start insert profile errdata ===");
				
				//컬럼명 저장
				//컬럼건수
				int colCnt = resultPatternDM.getColumnNames().length;
				StringBuilder insertColNm = new StringBuilder();
				StringBuilder insertColNmVal = new StringBuilder();
				StringBuilder insertColNmIdx = new StringBuilder();
				
				for(int i=0; i<resultPatternDM.getColumnNames().length; i++){
					insertColNm.append("\n, COL_NM").append( String.valueOf(i+1) );
					insertColNmVal.append("\n, '").append(resultPatternDM.getColumnNames()[i]).append("'");
					insertColNmIdx.append("\n, ?");
				}
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_PRF_ERR_DATA ( ");
				query.append("\n        PRF_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ESN_ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --PRF_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , 0 --ESN_ER_DATA_SNO ");
				query.append("\n        , ? --COL_CNT ");
				query.append(insertColNmVal.toString());
				query.append("\n  ) ");
//				logger.debug(query.toString());
				
				
				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, executorDM.getShdJobId());
				pstmt.setString(2, staDttm);
				pstmt.setInt(3, colCnt);
				
				pstmt.executeUpdate();
				
				//데이터 패턴 저장
				pstmt = null;
				query = null;
				query = new StringBuilder();
				query.append("\n INSERT INTO WAM_PRF_ERR_DATA ( ");
				query.append("\n        PRF_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ESN_ER_DATA_SNO ");
				query.append("\n        , COL_CNT ");
				//컬럼명 셋팅
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --PRF_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , ? --ESN_ER_DATA_SNO ");
				query.append("\n        , ? --COL_CNT ");
				//INSERT SQL INDEX 셋팅
				query.append(insertColNmIdx.toString());
				query.append("\n  ) ");
				
				
				pstmt = con.prepareStatement(query.toString());
				
				for(int j=0; j<resultPatternDM.getPatternList().size(); j++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, staDttm);
					pstmt.setInt(3, j+1);
					pstmt.setInt(4, colCnt);
					
					int idx = 5;
					for(int i=0; i<resultPatternDM.getColumnNames().length; i++){
						pstmt.setString(  idx , resultPatternDM.getPatternList().get(j).get(resultPatternDM.getColumnNames() [i]));
						idx++;
					}
					pstmt.executeUpdate();
					//오류데이터 적재건수 와 현재적재건수가 동일하면 break 
					if(  Integer.toString((j+1)).equals(Utils.null2Blank(executorDM.getErDataLdCnt())) ){
						break;
					}
				}
			}
			
			con.commit();
			
		} catch(SQLException sqle) {
			logger.error(sqle);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw sqle;
		} catch(Exception e) {
			logger.error(e);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw e;
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	public void saveProfilePkData(String staDttm, ExecutorDM executorDM, TargetDbmsDM targetDbmsDM) throws Exception{
		Connection con = null;
		Connection trgcon = null;
		PreparedStatement pstmt = null;
		
		try {
			//dq  DBMS Connection
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			
			// 대상 DBMS Connection 을 얻는다.
			trgcon = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			
			String prfId = executorDM.getShdJobId();
			//테이블, PK 컬럼 조회
			TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
			Map<String, String> dbcInfo = targetDbmsDAO.selectPkDbcInfo(con, prfId );
			 
			 //PK 컬럼 존재시
			 if(dbcInfo.size() > 0 ){
			 	//데이터패턴 컬럼 건수
				int pkColCnt = 0; 
			 	pkColCnt =  selectColCnt(con, prfId, staDttm);

			 	if(pkColCnt > 0){
				 	//PK DATA 조회시 사용할 조건 컬럼
			 		Map<String, String>  colMap = selectCol(prfId, staDttm, pkColCnt, con);
				    
				    //PK DATA 조회시 사용할 조건 컬럼값
				    List<Map<String, String>> colValList = selectColData(con, prfId, staDttm, executorDM.getPkDataLdCnt(), pkColCnt);
				    
				    for(int i=0; i<colValList.size(); i++ ){
				    	
				    	ProfileResultPatternDM resultPkDataDM = null;
				    	logger.debug(colValList.get(i).get("COL_NM1"));
				    	
				    	//PK 데이터 조회SQL 생성
					    String pkSelectSql = generateSql(dbcInfo, colMap, colValList.get(i));   

					    //SQL 실행
					    resultPkDataDM = executePkdataQueryOfTargetDbms(trgcon, pkSelectSql ,ExecutorConf.getPkDataLimit());
					  
					    //PK 데이터 INSERT
					    saveProfilePkResult(con, resultPkDataDM, colValList.get(i));
				    }
			 	}
			 }
			 
			 con.commit();
			
		} catch(SQLException sqle) {
			logger.error(sqle);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			if(trgcon   != null) try { trgcon.rollback(); } catch(Exception igonred) {}
			throw sqle;
		} catch(Exception e) {
			logger.error(e);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			if(trgcon   != null) try { trgcon.rollback(); } catch(Exception igonred) {}
			throw e;
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
			if(trgcon   != null) try { trgcon.close(); } catch(Exception igonred) {}
		}
	}
	
	
	//프로파일 분석 결과 저장
	public void saveProfilePkResult(Connection con, ProfileResultPatternDM resultPkDataDM,  Map<String, String> colValMap) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		StringBuilder query = null;
		
		try {
			logger.debug("=== start insert profile pkdata ===");
			
			if(resultPkDataDM != null) {
				//컬럼명 저장
				//컬럼건수
				int colCnt = resultPkDataDM.getColumnNames().length;
				StringBuilder insertColNm = new StringBuilder();
				StringBuilder insertColNmVal = new StringBuilder();
				StringBuilder insertColNmIdx = new StringBuilder();
				
				for(int i=0; i<resultPkDataDM.getColumnNames().length; i++){
					insertColNm.append("\n, COL_NM").append( String.valueOf(i+1) );
					insertColNmVal.append("\n, '").append(resultPkDataDM.getColumnNames()[i]).append("'");
					insertColNmIdx.append("\n, ?");
				}
				
				pstmt = null;
				query = null;
				query = new StringBuilder();
				
				query.append("\n INSERT INTO WAM_PRF_ERR_DATA_PKDATA ( ");
				query.append("\n          PRF_ID ");
				query.append("\n        , ANA_STR_DTM ");
				query.append("\n        , ESN_ER_DATA_SNO ");
				query.append("\n        , ESN_ER_DATA_PK_SNO ");
				query.append("\n        , COL_CNT ");
				query.append(insertColNm.toString());
				query.append("\n ) VALUES ( ");
				query.append("\n        ? --PRF_ID ");
				query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
				query.append("\n        , ? --ESN_ER_DATA_SNO ");
				query.append("\n        , 0 --ESN_ER_DATA_PK_SNO ");
				query.append("\n        , ? --COL_CNT ");
				query.append(insertColNmVal.toString());
				query.append("\n  ) ");

				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, colValMap.get("PRF_ID"));
				pstmt.setString(2, colValMap.get("ANA_STR_DTM"));
				pstmt.setString(3, colValMap.get("ESN_ER_DATA_SNO"));
				pstmt.setInt(4, colCnt);
				pstmt.executeUpdate();
				
//				logger.debug(query.toString());
				
				
				if(resultPkDataDM.getPatternList().size() > 0){
					//데이터 패턴 저장
					pstmt = null;
					query = null;
					query = new StringBuilder();
					query.append("\n INSERT INTO WAM_PRF_ERR_DATA_PKDATA ( ");
					query.append("\n          PRF_ID ");
					query.append("\n        , ANA_STR_DTM ");
					query.append("\n        , ESN_ER_DATA_SNO ");
					query.append("\n        , ESN_ER_DATA_PK_SNO ");
					query.append("\n        , COL_CNT ");
					//컬럼명 셋팅
					query.append(insertColNm.toString());
					query.append("\n ) VALUES ( ");
					query.append("\n        ? --PRF_ID ");
					query.append("\n        , to_date(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
					query.append("\n        , ? --ESN_ER_DATA_SNO ");
					query.append("\n        , ? --ESN_ER_DATA_PK_SNO ");
					query.append("\n        , ? --COL_CNT ");
					//INSERT SQL INDEX 셋팅
					query.append(insertColNmIdx.toString());
					query.append("\n  ) ");
					
//					logger.debug(query.toString());
					
					pstmt = con.prepareStatement(query.toString());
					
					for(int j=0; j<resultPkDataDM.getPatternList().size(); j++) {
						pstmt.setString(1, colValMap.get("PRF_ID"));
						pstmt.setString(2, colValMap.get("ANA_STR_DTM"));
						pstmt.setString(3, colValMap.get("ESN_ER_DATA_SNO"));
						pstmt.setInt(4, j+1);
						pstmt.setInt(5, colCnt);
						
						int idx = 6;
						for(int i=0; i<resultPkDataDM.getColumnNames().length; i++){
							pstmt.setString(idx, resultPkDataDM.getPatternList().get(j).get(resultPkDataDM.getColumnNames() [i]));
							idx++ ;
						}
						pstmt.executeUpdate();
					}
				}
			}
			pstmt .close();
			
		} catch(SQLException sqle) {
			logger.error(sqle);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw sqle;
		} catch(Exception e) {
			logger.error(e);
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw e;
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}

	public long executeCountQueryOfTargetDbms(Connection con, String query) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long cnt = 0L;
		try {
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getLong(1);
			}
			return cnt;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("CountQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("CountQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}
	
	public ResultSet executeQueryOfTargetDbms(Connection con, PreparedStatement pstmt, ResultSet rs, String query) throws SQLException, Exception {
		try {
			logger.debug(query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			return rs;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("ColumnAnalysis Query : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("ColumnAnalysis Query : " + e.getMessage());
		} finally {
//			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
//			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}

	public ProfileResultDM executeAnalysisQueryOfTargetDbms(Connection con, String query) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProfileResultDM profileResultDM = null;
		int rsCnt = 1;
		try {
			pstmt = con.prepareStatement(query);
			
			logger.debug("====================");
			logger.debug(query);
			
			rs = pstmt.executeQuery();
			profileResultDM = new ProfileResultDM();
			
			while(rs.next()) {
				if(rsCnt == 1) {
					profileResultDM.setTotCnt(rs.getLong(1));
				} else if(rsCnt == 2){
					profileResultDM.setErrCnt(rs.getBigDecimal(1));
				}
				rsCnt ++;
			}
			
//			logger.debug("전체건수 :: "+profileResultDM.getTotCnt());
//			logger.debug("오류건수 :: "+profileResultDM.getErrCnt());
			
			
			return profileResultDM;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("AnalysisQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("AnalysisQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}

	public ProfileResultPatternDM executePatternQueryOfTargetDbms(Connection con, String query, int colLimit) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProfileResultPatternDM profileResultPatternDM = null;
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setFetchSize(10000);
//			logger.debug(String.format("fetchsize[%d]", pstmt.getFetchSize()));
			
			logger.debug("건수 조회 " + query);
			
			rs = pstmt.executeQuery();
			int colCnt = rs.getMetaData().getColumnCount();
			String [] columnNames = new String [colCnt];
			for(int i=0; i<colCnt; i++) {
				columnNames [i] = rs.getMetaData().getColumnLabel(i+1).trim();
			}
			List<Map<String, String>> patternList = new ArrayList<Map<String, String>>();
			int n = 0;
			long totCnt = 0L;
			int patCnt = 0;
			while(rs.next()) {
				n = 0;
				patCnt ++;
				totCnt += rs.getLong("COUNT");
				logger.debug(totCnt);
				//코드수집(컬럼분석의 경우 colLimit를 넘지 않도록 수정...
				if (patCnt <= colLimit) {
					Map<String, String> colMap = new HashMap<String, String>();
					for(int i=0; i<colCnt; i++) {
						colMap.put(columnNames [n++], rs.getString(columnNames [i]));
					}
					patternList.add(colMap);
				}
			}

			profileResultPatternDM = new ProfileResultPatternDM();
			profileResultPatternDM.setColumnNames(columnNames);
			profileResultPatternDM.setPatternList(patternList);
			profileResultPatternDM.setTotalCount(totCnt);
			profileResultPatternDM.setPatternCount(new BigDecimal(patCnt));
			
//			logger.debug(profileResultPatternDM.getTotalCount()+":"+profileResultPatternDM.getPatternCount());
			
			return profileResultPatternDM;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("ResultQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("ResultQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}
	
	
	public ProfileResultPatternDM executePkdataQueryOfTargetDbms(Connection con, String query, int pkLimit) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProfileResultPatternDM profileResultPatternDM = null;
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setFetchSize(10000);
//			logger.debug(String.format("fetchsize[%d]", pstmt.getFetchSize()));
			
			logger.debug(query);
			
			rs = pstmt.executeQuery();
			int colCnt = rs.getMetaData().getColumnCount();
			String [] columnNames = new String [colCnt];
			for(int i=0; i<colCnt; i++) {
				columnNames [i] = rs.getMetaData().getColumnLabel(i+1).trim();
			}
			List<Map<String, String>> patternList = new ArrayList<Map<String, String>>();
			int n = 0;
			int patCnt = 0;
			while(rs.next()) {
				n = 0;
				patCnt ++;
				//코드수집(컬럼분석의 경우 colLimit를 넘지 않도록 수정...
				if (patCnt <= pkLimit) {
					Map<String, String> colMap = new HashMap<String, String>();
					for(int i=0; i<colCnt; i++) {
						colMap.put(columnNames [n++], rs.getString(columnNames [i]));
					}
					patternList.add(colMap);
				}
			}
			
			profileResultPatternDM = new ProfileResultPatternDM();
			profileResultPatternDM.setColumnNames(columnNames);
			profileResultPatternDM.setPatternList(patternList);
			profileResultPatternDM.setPatternCount(new BigDecimal(patCnt));
			
//			logger.debug(profileResultPatternDM.getTotalCount()+":"+profileResultPatternDM.getPatternCount());
			
			return profileResultPatternDM;
		} catch(SQLException sqle) {
			logger.error(sqle + "\n" + query);
			throw new SQLException("ResultQuery : " + sqle.getMessage());
		} catch(Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("ResultQuery : " + e.getMessage());
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
	}
	
	
	
	/**
	 * 날짜 형식에 맞지 않으면 해당 패턴과 카운터를 결과에 저장한다. 전체합계와 에러카운터합계, 에러패턴별 합계도 추가한다.
	 * @param con	: 타겟 DB 접속정보
	 * @param query : 타겟 DB sql
	 * @param limitCnt : 에러 갯수 제한
	 * @param dateFormat : 날짜 포맷
	 * @return 프로파일 결과 DM
	 * @throws SQLException
	 * @throws Exception
	 */
	public ProfileResultPatternDM executeDatePatternProfileOfTargetDbms(Connection con, String query, int limitCnt, String dateFormat) throws SQLException, Exception { 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProfileResultPatternDM profileResultPatternDM = null;
		
		//데이터 포맷 확인...
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);	//yyyyMMddHHmmss
		sdf.setLenient(false);
		
//		ProfileResultPatternDM profileResultPatternDM = null;
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setFetchSize(10000);

			rs = pstmt.executeQuery();
			
			logger.debug(String.format("end query\n[%s]", query));
			
			//컬럼명을 가져온다.
			int colCnt = rs.getMetaData().getColumnCount();
			String [] columnNames = new String [colCnt];
			for(int i=0; i<colCnt; i++) {
				columnNames [i] = rs.getMetaData().getColumnLabel(i+1).trim();
			}
			List<Map<String, String>> patternList = new ArrayList<Map<String, String>>();
			
			int patCnt = 0;	//패턴카운터
			int errPatCnt = 0;	//에러패턴카운터
			long totCnt = 0L;	//전체카운터
			long errCnt = 0L;	//전체에러카운터
			
			while (rs.next()){
				String tmpDate = rs.getString(1);
				long tmpCount = rs.getLong(2);

				patCnt++;
				totCnt += tmpCount;
				
				if(!dateFormat.equals("MM")) {
					int hhIdx = dateFormat.indexOf("HH");
//					if(!checkDateFormat(sdf, tmpDate) && !(hhIdx != -1 && hhIdx == tmpDate.indexOf("24", hhIdx))) {
					if(!checkDateFormat(sdf, tmpDate)) {
						errPatCnt ++;
						errCnt += tmpCount;
						
						if (errPatCnt <= limitCnt) {
							int n = 0;
							Map<String, String> colMap = new HashMap<String, String>();
							for(int i=0; i<colCnt; i++) {
								colMap.put(columnNames [n++], rs.getString(columnNames [i]));
							}
							patternList.add(colMap);
						}
					}
				} else {
//					if(!Pattern.matches("^([1-9]|0[1-9]|1[012])$", tmpDate) || !checkDateFormat(sdf, tmpDate)) {
					if(!checkDateFormat(sdf, tmpDate)) {
						errPatCnt ++;
						errCnt += tmpCount;
						
						if (errPatCnt <= limitCnt) {
							int n = 0;
							Map<String, String> colMap = new HashMap<String, String>();
							for(int i=0; i<colCnt; i++) {
								colMap.put(columnNames [n++], rs.getString(columnNames [i]));
							}
							patternList.add(colMap);
						}
					}
				}
				
				
				//logger.debug("NO["+patCnt+"]PATTERN["+tmpDate+"]COUNT["+tmpCount+"]");
			}
			
			profileResultPatternDM = new ProfileResultPatternDM();
			profileResultPatternDM.setColumnNames(columnNames);
			profileResultPatternDM.setPatternList(patternList);
			profileResultPatternDM.setTotalCount(totCnt);
			profileResultPatternDM.setPatternCount(new BigDecimal(errCnt));
			
//			logger.debug(profileResultPatternDM.getTotalCount()+":"+profileResultPatternDM.getPatternCount());
			
			return profileResultPatternDM;
			
		} catch (Exception e) {
			logger.error(e + "\n" + query);
			throw new SQLException("ResultQuery : " + e.getMessage());
			
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
			
		
	}
	
	private boolean checkDateFormat (SimpleDateFormat sdf, String chkStr) {
		
		try{
			sdf.parse(chkStr); //데이터 포맷 확인용...
			return true;
		}   catch (ParseException e) { //데이터 패턴이 아닌경우 에러정보 등록한다...
			return false;
		}
	}
	
	public Map<String, String> selectDateType(String prfId) throws Exception {
		StringBuilder strSQL = new StringBuilder();
		strSQL.append("\n    SELECT ");
		strSQL.append("\n          GET_CMCD_DTL_NM('DATE_FRM_CD', DATE_FRM_CD) AS COMM_DTL_CD_NM ");
		strSQL.append("\n        , USER_DATE_FRM ");
		strSQL.append("\n   FROM WAM_PRF_DTFRM_ANA ");
		strSQL.append("\n  WHERE PRF_ID = ? ");
		strSQL.append("\n    AND REG_TYP_CD IN ('C','U') ");

		logger.debug( String.format("%s \n param [%s]", strSQL.toString(), prfId));
		
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, prfId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//map = new HashMap<String, String>();
				map.put("COMM_DTL_CD_NM"		, rs.getString("COMM_DTL_CD_NM"));
				map.put("USER_DATE_FRM"		, rs.getString("USER_DATE_FRM"));
			}
			return map;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}
	/**
		 * 유효패턴 분석 : 유효패턴에 해당하지 않으면 해당 패턴과 카운터를 결과에 저장한다. 전체합계와 에러카운터합계, 에러패턴별 합계도 추가한다.
		 * @param con	: 타겟 DB 접속정보
		 * @param query : 타겟 DB sql
		 * @param limitCnt : 에러 갯수 제한
		 * @param validPat : 날짜 포맷
		 * @return 프로파일 결과 DM
		 * @throws SQLException
		 * @throws Exception
		 */
		public ProfileResultPatternDM executeValidPatternProfileOfTargetDbms(Connection con, String query, String totalQuery, int limitCnt, Map<String, String> validPat) throws SQLException, Exception { 
			
			
			
			PreparedStatement pstmt = null;
			PreparedStatement totalPstmt = null;
			ResultSet rs = null;
			ResultSet totalRs = null;
			ProfileResultPatternDM profileResultPatternDM = null;
			boolean bPatyn = false;
			
			try {
				
				if (validPat.isEmpty()) {
					bPatyn = true;
				}
				pstmt = con.prepareStatement(query);
				totalPstmt = con.prepareStatement(totalQuery);
				
				pstmt.setFetchSize(10000);
				totalPstmt.setFetchSize(10000);
	//			logger.debug(String.format("fetchsize[%d]", pstmt.getFetchSize()));
				
				rs = pstmt.executeQuery();
				totalRs = totalPstmt.executeQuery();
				
				logger.debug(String.format("end totalQuery\n[%s]", totalQuery));
				logger.debug(String.format("end query\n[%s]", query));
				
				//컬럼명을 가져온다.
				int colCnt = rs.getMetaData().getColumnCount();
				String [] columnNames = new String [colCnt];
				for(int i=0; i<colCnt; i++) {
					columnNames [i] = rs.getMetaData().getColumnLabel(i+1).trim();
				}
				List<Map<String, String>> patternList = new ArrayList<Map<String, String>>();
				
				int patCnt = 0;	//패턴카운터
				int errPatCnt = 0;	//에러패턴카운터
				long totCnt = 0L;	//전체카운터
				long totCnt2 = 0L;	//전체카운터
				long errCnt = 0L;	//전체에러카운터
				Map<String,Long> tmpErrMap = new HashMap<String, Long>(); //임시저장용.
				
				while(totalRs.next()) {
					long tmpCount = totalRs.getLong(1);
					totCnt2 += tmpCount;
				}
				
				while (rs.next()){
					String strReg = rs.getString(1);
					long tmpCount = rs.getLong(2);
					String strOrg = strReg;
	
					//patCnt++;
					//전체카운터 추가...
					totCnt += tmpCount;
					
					//각 패턴으로 변환 : [숫자-9, 영소-a, 영대-A, Space-B, 한글-C]
					strReg = strReg.replaceAll("[0-9]", "9");
					strReg = strReg.replaceAll("[a-z]", "a");
					strReg = strReg.replaceAll("[A-Z]", "A");
					strReg = strReg.replaceAll("[ ]", "B");
					strReg = strReg.replaceAll("[ㄱ-ㅎㅏ-ㅣ가-힣]", "C");
					
					//유효패턴이 없는 경우는 해당 패턴을 조사한다....
					//해당 패턴이 있는지 확인한다. 아닐경우에 에러패턴 추가처리
					if(bPatyn || !validPat.containsKey(strReg)) {
						boolean pflag = false;
						
						for(String key : validPat.keySet()) {
							String val = validPat.get(key);
							pflag = true;
							
							if(Pattern.matches(val, strOrg)) {
								//logger.debug("strOrg >>> " + strOrg + ", FLAG >>> " + Pattern.matches(val, strOrg));
								pflag = false;
								break;
							}
						}
						
						if(pflag) {
							//전체 에러카운터 추가...
							errCnt += tmpCount;
							
							//임시 에러카운터 맵에 있는지 확인...
							if(tmpErrMap.containsKey(strReg)) {
								//기존 에러카운터+현재 에러카운터
								tmpCount += tmpErrMap.get(strReg);
								tmpErrMap.put(strOrg, tmpCount);
							} else {
								//에러패턴카운터 추가...
								errPatCnt ++;
								if(errPatCnt <= limitCnt) {
									tmpErrMap.put(strOrg, tmpCount);
								}
							}
						}
					}
					
//					logger.debug("NO["+patCnt+"]PATTERN["+strReg+"]COUNT["+tmpCount+"]");
				}

				for( String key : tmpErrMap.keySet() ){
					Map<String, String> colMap = new HashMap<String, String>();
		            colMap.put(columnNames[0], key);
		            colMap.put(columnNames[1], tmpErrMap.get(key).toString());
		            patternList.add(colMap);
		        }
				
				profileResultPatternDM = new ProfileResultPatternDM();
				profileResultPatternDM.setColumnNames(columnNames);
				profileResultPatternDM.setPatternList(patternList);
				profileResultPatternDM.setTotalCount(totCnt2);
				profileResultPatternDM.setPatternCount(new BigDecimal(errCnt));
				
				logger.debug(profileResultPatternDM.getTotalCount()+":"+profileResultPatternDM.getPatternCount());
				
				return profileResultPatternDM;
				
			} catch (Exception e) {
				logger.error(e + "\n" + query);
				throw new SQLException("ResultQuery : " + e.getMessage());
				
			} finally {
				if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
				if(totalRs    != null) try { totalRs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				if(totalPstmt != null) try { totalPstmt.close(); } catch(Exception igonred) {}
			}
		}
	
		//유효패턴 분석 패턴 리스트 조회
		public Map<String, String> selectValidPattern(String prfId) throws Exception {
			StringBuilder strSQL = new StringBuilder();
			
			strSQL.append("\n    SELECT A.USER_DF_PTR");
			strSQL.append("\n     FROM WAM_PRF_MSTR M ");
			strSQL.append("\n             INNER JOIN  WAM_PRF_PTR_USER_DF A ");
			strSQL.append("\n                 ON M.PRF_ID = A.PRF_ID ");
			strSQL.append("\n                AND A.REG_TYP_CD IN ('C','U')   ");
			strSQL.append("\n    WHERE M.PRF_ID = ? ");
			strSQL.append("\n       AND M.REG_TYP_CD IN ('C','U')   ");
			
			logger.debug( String.format("%s \n param [%s]", strSQL.toString(), prfId));
			
			Map<String, String> map = new HashMap<String, String>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = ConnectionHelper.getDAConnection();
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, prfId);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					map.put(rs.getString("USER_DF_PTR")		, rs.getString("USER_DF_PTR"));
				}
				return map;
			} finally {
				if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
			
		}	
	
		
		//조건 컬럼 건수
		public int  selectColCnt(Connection con, String prfId, String exeStaDttm) throws SQLException{
			
			//패턴 컬럼 건수 조회
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n SELECT COL_CNT ");
			strSQL.append("\n  FROM WAM_PRF_ERR_DATA ");
			strSQL.append("\n WHERE PRF_ID = '").append(prfId).append("' ");
			strSQL.append("\n    AND ANA_STR_DTM = TO_DATE('").append(exeStaDttm).append("','YYYY-MM-DD HH24:MI:SS') ");
			strSQL.append("\n    AND ESN_ER_DATA_SNO = 0 ");
			
//			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			int colCnt = 0;
			
			try{
//				con = ConnectionHelper.getDAConnection();
				pstmt = con.prepareStatement(strSQL.toString());
				rs = pstmt.executeQuery();
				//데이터패턴이 존재할 경우
				if(rs.next()){
					colCnt = rs.getInt("COL_CNT");
				}
			}catch(Exception e){
				logger.error(e.toString());
			}finally {
				if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
//				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
			return colCnt;
		}
		
		//조건 컬럼 조회
		public Map<String, String>  selectCol(String prfId, String exeStaDttm, int colCnt, Connection con) throws SQLException{
			
			Map<String, String> map = new HashMap<String, String>();
			
			//조건에 들어갈 컬럼  조회
			StringBuffer strSQL2 = new StringBuffer();
			strSQL2.append("\n SELECT * ");
			strSQL2.append("\n  FROM WAM_PRF_ERR_DATA ");
			strSQL2.append("\n WHERE PRF_ID = '").append(prfId).append("' ");
			strSQL2.append("\n    AND ANA_STR_DTM = TO_DATE('").append(exeStaDttm).append("','YYYY-MM-DD HH24:MI:SS') ");
			strSQL2.append("\n    AND ESN_ER_DATA_SNO = 0 ");
			
//			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			String colNm = new String();
			
			try{
//				con = ConnectionHelper.getDAConnection();
				pstmt = con.prepareStatement(strSQL2.toString());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					//프로파일 오류데이터 테이블의 마지막 컬럼은 항상 COUNT(패턴건수) 이기 때면에 
					//for(int idx = 1; idx<=colCnt; idx++){ 미사용 -> 마지막 컬럼명 제거
					for(int idx = 1; idx<colCnt; idx++){
						colNm = "COL_NM"+idx;
						map.put(colNm, rs.getString(colNm) );
					}
				}
			}catch(Exception e){
				logger.error(e.toString());
			}finally {
				if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
//				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
			return map;
		}
		
		//조건 컬럼값 조회
		public List<Map<String, String>>  selectColData(Connection con, String prfId, String exeStaDttm, BigDecimal PkDataLdCnt, int colCnt) throws SQLException{
			
			List<Map<String, String>> colValList = new ArrayList<Map<String, String>>();
			
			StringBuffer selectCol = new StringBuffer();
			selectCol.append("\n        PRF_ID");
			selectCol.append("\n        ,TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS ANA_STR_DTM ");
			selectCol.append("\n        ,ESN_ER_DATA_SNO");
			String colNm = "COL_NM";
			for(int idx = 1; idx<=colCnt; idx++){
				colNm = "COL_NM"+idx;
				selectCol.append("\n        ,").append(colNm);
			}
			//조건에 들어갈 컬럼값  조회
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n SELECT ").append(selectCol);
			strSQL.append("\n  FROM WAM_PRF_ERR_DATA ");
			strSQL.append("\n WHERE PRF_ID = ? ");
			strSQL.append("\n    AND ANA_STR_DTM = TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') ");
			strSQL.append("\n    AND ESN_ER_DATA_SNO > 0 ");
			strSQL.append("\n    AND ESN_ER_DATA_SNO <= ? ");
			
//			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			colNm = new String();
			Map<String, String> map = new HashMap<String, String>();
			
			try{
//				con = ConnectionHelper.getDAConnection();
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, prfId);
				pstmt.setString(2, exeStaDttm);
				pstmt.setBigDecimal(3, PkDataLdCnt);
				rs = pstmt.executeQuery();
				
				//데이터패턴이 존재할 경우
				while(rs.next()) {
					map = new HashMap<String, String>();
					map.put("PRF_ID", rs.getString("PRF_ID") );
					map.put("ANA_STR_DTM", rs.getString("ANA_STR_DTM") );
					map.put("ESN_ER_DATA_SNO", rs.getString("ESN_ER_DATA_SNO") );
					for(int idx = 1; idx<=colCnt; idx++){
						colNm = "COL_NM"+idx;
						map.put(colNm, rs.getString(colNm) );
					}
					colValList.add(map);
				}
				
			}catch(Exception e){
				logger.error(e.toString());
			}finally {
				if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
//				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
			return colValList;
		}
		
		
		//PK컬럼 조회 SQL 생성
		public String generateSql(  Map<String, String> dbcInfo,   Map<String, String>  colmap,  Map<String, String> colValMap){
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("\n SELECT ").append(dbcInfo.get("DBC_COL_NM"));
			strSQL.append("\n   FROM ").append(dbcInfo.get("DBC_TBL_NM"));
			strSQL.append("\n  WHERE 1 = 1 ");
			//검색조건
			String colNm = new String();
			for(int i=1; i<=colmap.size(); i++){
				colNm = "COL_NM" + i;
				strSQL.append("\n    AND ").append(colmap.get(colNm)).append(" = '").append(colValMap.get(colNm)).append("'");
			}
			return strSQL.toString();
			
		}



		/** @param exeStaDttm
		/** @param executorDM
		/** @param resultPatternDM1 insomnia 
		 * @throws Exception */
		public void saveProfilePattern(String exeStaDttm, ExecutorDM executorDM, ProfileResultPatternDM resultPatternDM1) throws Exception {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			StringBuilder query = null;
			ResultSet rs = null;
			
			try {
				con = ConnectionHelper.getDAConnection();
				con.setAutoCommit(false);
				
				// 기존 패턴 데이터 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAM_PRF_PTR_DATA WHERE PRF_ID = ? ");
				pstmt.setString(1, executorDM.getShdJobId());
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				
				//패턴 데이터 적재
				if(resultPatternDM1 != null ) {
					//데이터 패턴 저장
					pstmt = null;
					query = null;
					query = new StringBuilder();
					query.append("\n INSERT INTO WAM_PRF_PTR_DATA ( ");
					query.append("\n        PRF_ID ");
					query.append("\n        , ANA_STR_DTM ");
					query.append("\n        , ESN_ER_DATA_SNO ");
					query.append("\n        , PTR_DATA ");
					query.append("\n        , PTR_CNT ");
					query.append("\n ) VALUES ( ");
					query.append("\n        ? --PRF_ID ");
					query.append("\n        , TO_DATE(?, 'YYYYMMDDHH24MISS') --ANA_STR_DTM ");
					query.append("\n        , ? --ESN_ER_DATA_SNO ");
					query.append("\n        , ? --PTR_DATA ");
					query.append("\n        , ? --PTR_CNT ");
					query.append("\n  ) ");
					
					pstmt = con.prepareStatement(query.toString());
					
					for(int j=0; j<resultPatternDM1.getPatternList().size(); j++) {
						pstmt.setString(1, executorDM.getShdJobId());
						pstmt.setString(2, exeStaDttm);
						pstmt.setInt(3, j+1);
						pstmt.setString(4, resultPatternDM1.getPatternList().get(j).get(resultPatternDM1.getColumnNames() [0]));
						if (resultPatternDM1.getPatternList().get(j).get(resultPatternDM1.getColumnNames() [1]) != null) {
							pstmt.setInt(5, Integer.parseInt(resultPatternDM1.getPatternList().get(j).get(resultPatternDM1.getColumnNames() [1])));
						}
						
						pstmt.executeUpdate();
						//오류데이터 적재건수 와 현재적재건수가 동일하면 break 
						if(  Integer.toString((j+1)).equals(Utils.null2Blank(executorDM.getErDataLdCnt())) ){
							break;
						}
					}
				}
				
			} catch(SQLException sqle) {
				logger.error(sqle);
				if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
				throw sqle;
			} catch(Exception e) {
				logger.error(e);
				if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
				throw e;
			} finally {
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
		}



		/** @param targetDbmsDM insomnia */
		public void regColProfile(TargetDbmsDM targetDbmsDM) throws Exception {
			Connection con = null;
			PreparedStatement pstmt = null;
			StringBuilder query = null;
			ResultSet rs = null;
			
			try {
				con = ConnectionHelper.getDAConnection();
				con.setAutoCommit(false);
				//RQST_ID 채번...
				String rqstId = null;
				
				query = new StringBuilder();
				query.append("SELECT 'REQP'||LPAD(RQST_ID.NEXTVAL, 11, '0') AS REQ_ID FROM DUAL");
				pstmt = con.prepareStatement(query.toString());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					rqstId = rs.getString("REQ_ID");
				}
				
				pstmt.close();
				//프로파일 마스터 등록....
				query.setLength(0);
				query.append("\n INSERT INTO WAM_PRF_MSTR (PRF_ID, PRF_KND_CD, PRF_NM, DB_CONN_TRG_ID, DB_SCH_ID, DBC_TBL_NM, OBJ_NM, RQST_NO ");
				query.append("\n                         , OBJ_VERS, REG_TYP_CD, FRS_RQST_DTM, FRS_RQST_USER_ID, RQST_DTM, RQST_USER_ID ) ");
				query.append("\n SELECT 'OBJP'||LPAD(RQST_ID.NEXTVAL, 11, '0') AS PRF_ID ");
				query.append("\n 	 , 'PC01' AS PRF_KND_CD ");
				query.append("\n 	 , 'PC01|'||DB.DB_CONN_TRG_PNM||'|'||SCH.DB_SCH_PNM||'|'||COL.DBC_TBL_NM||'|'||COL.DBC_COL_NM AS PRF_NM --\"진단대상명\" ");
				query.append("\n      , DB.DB_CONN_TRG_ID ");
				query.append("\n 	 , SCH.DB_SCH_ID       ");
				query.append("\n 	 , COL.DBC_TBL_NM      ");
				query.append("\n 	 , COL.DBC_COL_NM  AS OBJ_NM    ");
				query.append("\n      , ? AS RQST_NO ");
				query.append("\n      , 1 AS OBJ_VERS ");
				query.append("\n      , 'C' AS REG_TYP_CD ");
				query.append("\n      , SYSDATE ");
				query.append("\n      , 'SYSTEM' ");
				query.append("\n      , SYSDATE ");
				query.append("\n      , 'SYSTEM' ");
				query.append("\n FROM WAA_DB_CONN_TRG DB ");
				query.append("\n INNER JOIN WAA_DB_SCH SCH ");
				query.append("\n    ON DB.DB_CONN_TRG_ID = SCH.DB_CONN_TRG_ID ");
				query.append("\n   AND DB.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD') ");
				query.append("\n   AND SCH.EXP_DTM = TO_DATE('99991231', 'YYYYMMDD') ");
				query.append("\n INNER JOIN WAT_DBC_COL COL ");
				query.append("\n    ON SCH.DB_SCH_ID = COL.DB_SCH_ID ");
				query.append("\n WHERE 1=1 ");
				query.append("\n   AND DB.DB_CONN_TRG_ID = ? ");
				query.append("\n   AND (SCH.COL_PRF_YN = 'Y' OR DMN_PDT_YN = 'Y') ");
				query.append("\n 	-- 기등록건 제외 ");
				query.append("\n   AND (DB.DB_CONN_TRG_ID,SCH.DB_SCH_ID,COL.DBC_TBL_NM,COL.DBC_COL_NM) ");
				query.append("\n 	  NOT IN (SELECT DB_CONN_TRG_ID,DB_SCH_ID,DBC_TBL_NM,OBJ_NM ");
				query.append("\n 		        FROM WAM_PRF_MSTR ");
				query.append("\n 		       WHERE PRF_KND_CD='PC01' ");
				query.append("\n 		     ) ");
				
				pstmt = con.prepareStatement(query.toString());
				
				pstmt.setString(1, rqstId);
				pstmt.setString(2, targetDbmsDM.getDbms_id());
				
				logger.debug(query.toString());
				logger.debug("RQST_ID:["+rqstId+"], DBMS_ID:["+targetDbmsDM.getDbms_id()+"]");
				
				pstmt.executeUpdate();
				pstmt.close();
				
				//프로파일 컬럼분석 등록...
				query.setLength(0);
				query.append("\n INSERT INTO WAM_PRF_COL_ANA (PRF_ID, MIN_MAX_VAL_ANA_YN, AVG_ANA_YN, STDV_ANA_YN, VRN_ANA_YN, AONL_YN, SPAC_ANA_YN, LEN_ANA_YN, CRD_ANA_YN, RQST_NO ");
				query.append("\n                             ,OBJ_VERS, REG_TYP_CD, FRS_RQST_DTM, FRS_RQST_USER_ID, RQST_DTM, RQST_USER_ID,  PAT_ANA_YN ) ");
				query.append("\n SELECT PRF_ID, 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', RQST_NO ");
				query.append("\n      , 1, 'C', SYSDATE, 'SYSTEM', SYSDATE, 'SYSTEM', 'Y' ");
				query.append("\n   FROM WAM_PRF_MSTR ");
				query.append("\n  WHERE RQST_NO = ? ");

				pstmt = con.prepareStatement(query.toString());
				pstmt.setString(1, rqstId);
				
				logger.debug(query.toString());
				logger.debug("RQST_ID:["+rqstId+"]");
				
				pstmt.executeUpdate();
				
				con.commit();
				
				
			} catch(SQLException sqle) {
				logger.error(sqle);
				if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
				throw sqle;
			} catch(Exception e) {
				logger.error(e);
				if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
				throw e;
			} finally {
				if(rs!= null) try { rs.close(); } catch(Exception igonred) {}
				if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				if(con   != null) try { con.close(); } catch(Exception igonred) {}
			}
			
		}
	
}
