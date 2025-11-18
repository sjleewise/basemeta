/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierDAO.java
 * 2. Package : kr.wise.executor.dao
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 1. 16. 오후 2:53:42
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 1. 16. :            : 신규 개발.
 */
package kr.wise.executor.dao;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.exceute.ai.PythonConf;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : OutlierDAO.java
 * 3. Package  : kr.wise.executor.dao
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 1. 16. 오후 2:53:42
 * </PRE>
 */
public class OutlierDAO {
	
	private static final Logger logger = Logger.getLogger(OutlierDAO.class);

	/** @param shdJobId
	/** @return insomnia 
	 * @throws Exception */
	public WadOtlDtcVo getOutlierVo(String otlDtcId) throws Exception {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT A.OTL_DTC_ID, A.DASE_ID, A.OTL_ALG_ID        ");
		strSQL.append("\n      , B.ALG_ID, B.ALG_LNM, B.ALG_PNM, B.ALG_TYP_CD ");
		strSQL.append("\n      , DS.DB_SCH_ID, DS.DB_TBL_NM, B.ALG_CNT     ");
		strSQL.append("\n   FROM WAD_OTL_DTC A                  ");
		strSQL.append("\n        INNER JOIN WAA_ALG B           ");
		strSQL.append("\n 	        ON A.OTL_ALG_ID = B.ALG_ID  ");
		strSQL.append("\n        INNER JOIN WAD_DATA_SET DS     ");
		strSQL.append("\n           ON A.DASE_ID = DS.DASE_ID   ");
		strSQL.append("\n WHERE A.REG_TYP_CD IN ('C', 'U')      ");
		strSQL.append("\n   AND B.REG_TYP_CD IN ('C', 'U')      ");
		strSQL.append("\n   AND DS.REG_TYP_CD IN ('C', 'U')     ");
		strSQL.append("\n   AND A.OTL_DTC_ID = ?                ");

		logger.debug(strSQL.toString()+"\n["+otlDtcId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadOtlDtcVo otlvo = null;
		try{
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, otlDtcId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				otlvo = new WadOtlDtcVo();
				otlvo.setOtlDtcId(rs.getString("OTL_DTC_ID"));
				otlvo.setDaseId(rs.getString("DASE_ID"));
				otlvo.setOtlAlgId(rs.getString("OTL_ALG_ID"));
				otlvo.setAlgId(rs.getString("ALG_ID"));
				otlvo.setAlgLnm(rs.getString("ALG_LNM"));
				otlvo.setAlgPnm(rs.getString("ALG_PNM"));
				otlvo.setAlgTypCd(rs.getString("ALG_TYP_CD"));
				otlvo.setDbSchId(rs.getString("DB_SCH_ID"));
				otlvo.setDbTblNm(rs.getString("DB_TBL_NM"));
				otlvo.setAlgCnt(rs.getInt("ALG_CNT"));
				
				otlvo.setVarlist(getOtlVarList(otlDtcId, con));
				otlvo.setArglist(getOtlArgList(otlDtcId, con));
			}
			
			return otlvo;
		}catch(Exception e){
			logger.error(e);
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}		
	}
	
	

	/** @param otlDtcId
	/** @param con
	/** @return insomnia */
	private List<WadOtlArg> getOtlArgList(String otlDtcId, Connection con) {
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("\n SELECT R.ALG_ARG_ID, R.ARG_VAL ");
		strSQL.append("\n      , P.ARG_LNM, P.ARG_PNM, P.ARG_DATA_TYPE, P.ARG_DEFLT_VAL ");
		strSQL.append("\n   FROM WAD_OTL_ARG R ");
		strSQL.append("\n   LEFT OUTER JOIN WAA_ALG_ARG P ");
		strSQL.append("\n	 ON R.ALG_ARG_ID = P.ALG_ARG_ID ");
//		strSQL.append("\n	--AND A.OTL_ALG_ID = P.ALG_ID ");
		strSQL.append("\n WHERE R.OTL_DTC_ID = ? ");
		strSQL.append("\n   AND R.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n   AND P.REG_TYP_CD IN ('C', 'U') ");
		
		logger.debug(strSQL.toString()+"\n["+otlDtcId+"]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<WadOtlArg> list = new ArrayList<WadOtlArg>();
		
		try {
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, otlDtcId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WadOtlArg otlval = new WadOtlArg();
				otlval.setAlgArgId(rs.getString("ALG_ARG_ID"));
				otlval.setArgVal(rs.getString("ARG_VAL"));
				otlval.setArgLnm(rs.getString("ARG_LNM"));
				otlval.setArgPnm(rs.getString("ARG_PNM"));
				otlval.setArgDataType(rs.getString("ARG_DATA_TYPE"));
				otlval.setArgDefltVal(rs.getString("ARG_DEFLT_VAL"));
				list.add(otlval);
			}
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
		return list;
	}

	/** @param otlDtcId
	/** @return insomnia */
	private List<WadOtlVal> getOtlVarList(String otlDtcId, Connection con) {
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT C.ANL_VAR_ID, C.DB_TBL_NM, C.DB_COL_NM ");
		//strSQL.append("\n      , V.OTL_ADD_CND                          ");
		strSQL.append("\n      , V.COND_CD                          	");
		strSQL.append("\n      , V.OPR_CD                          		");
		strSQL.append("\n      , V.OPR_VAL                          	");
		strSQL.append("\n   FROM WAD_OTL_VAL V                          ");
		strSQL.append("\n        INNER JOIN WAD_ANA_VAR C               ");
		strSQL.append("\n 	        ON V.ANL_VAR_ID = C.ANL_VAR_ID      ");
		strSQL.append("\n  WHERE V.OTL_DTC_ID = ?                       ");
		//strSQL.append("\n    AND V.REG_TYP_CD IN ('C', 'U')             ");
		strSQL.append("\n    AND C.REG_TYP_CD IN ('C', 'U')             ");
		strSQL.append("\n    AND V.ANA_VAR_CHK = '1'                    ");
		strSQL.append("\n  ORDER BY V.VAR_SNO                           ");
		
		logger.debug(strSQL.toString()+"\n["+otlDtcId+"]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<WadOtlVal> list = new ArrayList<WadOtlVal>();
		
		try {
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, otlDtcId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				WadOtlVal otlval = new WadOtlVal();
				
				otlval.setAnlVarId(rs.getString("ANL_VAR_ID"));
				otlval.setDbTblNm(rs.getString("DB_TBL_NM"));
				otlval.setDbColNm(rs.getString("DB_COL_NM"));
				//otlval.setOtlAddCnd(rs.getString("OTL_ADD_CND")); 
				otlval.setCondCd(rs.getString("COND_CD")); 
				otlval.setOprCd(rs.getString("OPR_CD")); 
				otlval.setOprVal(rs.getString("OPR_VAL")); 
				
				list.add(otlval); 
			}
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
		return list;
	}
	
	/** @param otlDtcId
	/** @return insomnia 
	 * @throws Exception 
	 * @throws SQLException */
	public String getOtlVarAddCndStr(String otlDtcId) throws SQLException, Exception {
		StringBuffer strSQL = new StringBuffer();
		
		//strSQL.append("\n SELECT V.OTL_ADD_CND                ");
		strSQL.append("\n SELECT   V.COND_CD                			");
		strSQL.append("\n 		 , V.OPR_CD                				");
		strSQL.append("\n 		 , V.OPR_VAL                			");
		strSQL.append("\n 		 , C.DB_COL_NM                			");
		strSQL.append("\n   FROM WAD_OTL_VAL V              			");
		strSQL.append("\n        INNER JOIN WAD_ANA_VAR C               ");
		strSQL.append("\n 	        ON V.ANL_VAR_ID = C.ANL_VAR_ID      ");
		strSQL.append("\n  WHERE V.OTL_DTC_ID = ?           			");
		//strSQL.append("\n    AND V.OTL_ADD_CND IS NOT NULL    ");
		strSQL.append("\n    AND V.COND_CD IS NOT NULL    				");
		strSQL.append("\n    AND V.OPR_CD IS NOT NULL    				");
		strSQL.append("\n    AND V.OPR_VAL IS NOT NULL    				");
		strSQL.append("\n  ORDER BY V.VAR_SNO               			");
		
		logger.debug(strSQL.toString()+"\n["+otlDtcId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String addCond = "";
		
		con = ConnectionHelper.getDAConnection();
		
		List<WadOtlVal> list = new ArrayList<WadOtlVal>();
		
		try {
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, otlDtcId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
								
				//addCond += UtilString.null2Blank(rs.getString("OTL_ADD_CND")) + " ";
				addCond += UtilString.null2Blank(rs.getString("COND_CD")) + " ";
				addCond += UtilString.null2Blank(rs.getString("DB_COL_NM")) + " ";
				addCond += UtilString.null2Blank(rs.getString("OPR_CD")) + " ";
				addCond += UtilString.null2Blank(rs.getString("OPR_VAL")) + " ";
			}
			
			addCond = addCond.trim();
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
		
		return addCond;

	}

	/** @param logId
	/** @param exeStaDttm
	/** @param executorDM
	/** @param dtcvo insomnia 
	 * @throws Exception */
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM, WadOtlDtcVo dtcvo) throws Exception {
		//결과파일을 로드하여 저장한다.
		logger.debug("*****Start Outlier Result["+dtcvo.getOtlDtcId()+"]*****");
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder query = null;
		StringBuffer strSQL = new StringBuffer();
		ResultSet rs = null;
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			String sAnaDgr = UtilString.null2Blank(executorDM.getAnaDgr());
			
			//차수 존재시
			if(sAnaDgr != null){
				logger.debug("=== start exe_num profile ===");
				// 삭제할 차수의 ANA_STR_DTM 값을 구한다.
				pstmt = con.prepareStatement(" SELECT TO_CHAR(ANA_STR_DTM, 'YYYYMMDDHH24MISS') AS ANA_STR_DTM FROM WAD_OTL_RESULT WHERE OTL_DTC_ID = ? AND ANA_DGR = ? ");
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
				pstmt = con.prepareStatement(" DELETE FROM WAD_OTL_DATA WHERE OTL_DTC_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				/*
				// 기존 오류패턴 및 오류패턴정보, 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAD_OTL_DATA_PKDATA WHERE OTL_DTC_ID = ? AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				*/

				//프로파일 결과 삭제
				pstmt = con.prepareStatement(" DELETE FROM WAD_OTL_RESULT WHERE OTL_DTC_ID = ? AND ANA_DGR = ? ");
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
				query.append("\n   FROM WAD_OTL_RESULT ");
				query.append("\n  WHERE OTL_DTC_ID = ? ");
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
				query.append("\n DELETE FROM WAD_OTL_DATA  ");
				query.append("\n  WHERE OTL_DTC_ID = ?  ");
				query.append("\n    AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAD_OTL_DATA ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				/*
				// 기존 업무규칙 오류패턴 및 오류패턴정보, 결과 삭제
				query = new StringBuilder();
				query.append("\n DELETE FROM WAD_OTL_DATA_PKDATA  ");
				query.append("\n  WHERE OTL_DTC_ID = ?  ");
				query.append("\n    AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')  ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAD_OTL_DATA_PKDATA ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				*/

				query = new StringBuilder();
				query.append("\n DELETE FROM WAD_OTL_RESULT  ");
				query.append("\n  WHERE OTL_DTC_ID = ?       ");
				query.append("\n    AND ANA_STR_DTM = TO_DATE(?, 'YYYYMMDDHH24MISS')   ");
				pstmt = con.prepareStatement(query.toString());
				
				logger.debug("===start delete WAD_OTL_RESULT ===");
				for(int i=0; i<prevStaDttmList.size(); i++) {
					pstmt.setString(1, executorDM.getShdJobId());
					pstmt.setString(2, prevStaDttmList.get(i));
					pstmt.executeUpdate();
				}
				pstmt.close();
				pstmt = null;
				
				logger.debug("차수 미존재 프로파일 진단결과 데이터 삭제 완료!!");
			}
			
			
			
			pstmt.close(); pstmt = null;
			
			//이상탐지 결과파일.json을 로드하여 DB에 저장한다.
			String algNm = dtcvo.getAlgPnm();
			
			//단변량일 경우...
			if ("Box Plot".equals(algNm)) {
				for (WadOtlVal valvo : dtcvo.getVarlist()) {
					String jsonf = PythonConf.getPYTHON_DATA_PATH()+valvo.getAnlVarId()+".json";
					
					logger.debug("*****Result Json File Load:["+valvo.getAnlVarId()+".json]*****");
					
					//create JsonParser object
					JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
					
					List<Float> outlist = new ArrayList<Float>();
					int count = 0;
					
					//loop through the JsonTokens
					while(jsonParser.nextToken() != JsonToken.END_OBJECT){
						String name = jsonParser.getCurrentName();
						
						if("mean".equals(name) ){
							jsonParser.nextToken(); 
						} else if ("q1".equals(name)) {
							jsonParser.nextToken(); 
						} else if ("q2".equals(name)) {
							jsonParser.nextToken(); 
						} else if ("q3".equals(name)) {
							jsonParser.nextToken(); 
						} else if ("max".equals(name)) {
							jsonParser.nextToken(); 
						} else if ("min".equals(name)) {
							jsonParser.nextToken(); 
						} else if ("count".equals(name)) {
							jsonParser.nextToken();
							count = jsonParser.getIntValue();
						} else if ("outlier".equals(name)) {
							jsonParser.nextToken();
					        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
					        	outlist.add(jsonParser.getFloatValue());
					        }
						}
					}
					
					jsonParser.close();
					
//					logger.debug(outlist);
					//결과를 저장한다....
					logger.debug("*****Save Result :["+valvo.getAnlVarId()+"]*****");
					WadOtlResult resvo = new WadOtlResult();
					resvo.setOtlDtcId(dtcvo.getOtlDtcId());
					
					if(!sAnaDgr.equals("")) {
						resvo.setAnaDgr( executorDM.getAnaDgr().intValue() );
					}
										
					resvo.setAnlVarId(valvo.getAnlVarId());
					resvo.setAnaCnt(count);
					if (outlist != null && !outlist.isEmpty())  {
						
						if(count == 0){
							resvo.setEsnErCnt(0);
						}else{
							resvo.setEsnErCnt(outlist.size());
						}						
					}	
//					resvo.setAnaUserId(userid);
//					resvo.setIbsStatus("I");
					
					getOuterResultSql(strSQL);
					
					pstmt = con.prepareStatement(strSQL.toString()); 
					
					int pidx = 1;
					
					pstmt.setString(pidx++, resvo.getOtlDtcId());
					pstmt.setString(pidx++, exeStaDttm);
					pstmt.setString(pidx++, endDttm);
					pstmt.setString(pidx++, resvo.getAnlVarId());
					
					if(resvo.getAnaDgr() == null){
						pstmt.setInt(pidx++, java.sql.Types.NULL);
					}else{
						pstmt.setInt(pidx++, resvo.getAnaDgr());
					}
					
					if (resvo.getAnaCnt() == null) {
						pstmt.setNull(pidx++, java.sql.Types.NULL);
					} else {
						pstmt.setInt(pidx++, resvo.getAnaCnt());
					}
					if (resvo.getEsnErCnt() == null) {
						pstmt.setNull(pidx++, java.sql.Types.NULL);
					} else {
						pstmt.setInt(pidx++, resvo.getEsnErCnt());
					}
					
					pstmt.setString(pidx++, logId);
					
					pstmt.execute();
					pstmt.close(); pstmt = null;
					
					//아웃라이어 데이터를 저장한다...
					logger.debug("*****Save Outlier Data :["+valvo.getAnlVarId()+"]*****");
					if (outlist != null && !outlist.isEmpty()) {
						strSQL.setLength(0);
						strSQL.append("\n INSERT INTO WAD_OTL_DATA (OTL_DTC_ID, ANA_STR_DTM, OTL_SNO, ");
						strSQL.append("\n   ANL_VAR_ID, COL_CNT, COL_NM1 ");
						strSQL.append("\n   ) ");
						strSQL.append("\n VALUES ( ?, TO_DATE(?, 'YYYYMMDDHH24MISS'), ? ");
						strSQL.append("\n        , ?, ?, ? ");
						strSQL.append("\n   ) ");
						
						pstmt  = con.prepareStatement(strSQL.toString());
						int psidx = 1;
						pstmt.setString(psidx++, dtcvo.getOtlDtcId());
						pstmt.setString(psidx++, exeStaDttm);
						pstmt.setInt(psidx++, 0);
						pstmt.setString(psidx++, valvo.getAnlVarId());
						pstmt.setInt(psidx++, 1);
						pstmt.setString(psidx++, valvo.getDbColNm());
						
						pstmt.execute();
						//pstmt.close(); pstmt = null;

					}
					
					int cntout = 1;
					for (Float outdata : outlist) {
						int psidx = 1;
						pstmt.setString(psidx++, dtcvo.getOtlDtcId());
						pstmt.setString(psidx++, exeStaDttm);
						pstmt.setInt(psidx++, cntout++);
						pstmt.setString(psidx++, valvo.getAnlVarId());
						pstmt.setInt(psidx++, 1);
						pstmt.setString(psidx++, outdata.toString());
						
						pstmt.execute();
					}
					
//					pstmt.close(); pstmt = null;

					
					
				}
			//} else if("RRGR".equals(algNm)){	
				//Robust Regression
				
				
				
			} else { //다변량일 경우...
				String jsonf = PythonConf.getPYTHON_DATA_PATH()+dtcvo.getOtlDtcId()+".json";
				
				logger.debug("*****Result Json File Load:["+dtcvo.getOtlDtcId()+".json]*****");
				//컬럼명 sql
				List<String> colnms = new ArrayList<String>();
//				List<String> colval = new ArrayList<String>();
				
				List<Map<String, String>> outdata = new ArrayList<Map<String, String>> ();
				
				//컬럼목록을 받아 처리한다.
				for (WadOtlVal valvo : dtcvo.getVarlist()) {
					colnms.add(valvo.getDbColNm());
				}
				colnms.add("outlier");
				
				
				//create JsonParser object
				JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
//				jsonParser.readValuesAs(TypeReference<List<Map>>()) ;
				
				int outcount = 0; //아웃라이어 갯수...
				int totcnt = 0; //아웃라이어 갯수...
				
				//loop through the JsonTokens
				while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
					String name1 = jsonParser.getCurrentName();
					if("count".equals(name1)) {
						jsonParser.nextToken();
						totcnt = jsonParser.getIntValue();
					} else if ("value".equals(name1)) {
						while(jsonParser.nextToken() != JsonToken.END_ARRAY){
							Map<String, String> outmap = new HashMap<String, String>();
							while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
								String name = jsonParser.getCurrentName();
								for (String colnm : colnms) {
									if(colnm.equalsIgnoreCase(name)) {
										jsonParser.nextToken();
//								colval.add(jsonParser.getText());
										outmap.put(colnm, jsonParser.getText());
										break;
									}
								}
							}
							//아웃라이어인 경우에만 결과값을 추가한다.
							if("-1.0".equals(outmap.get("outlier")) || "1.0".equals(outmap.get("outlier"))) {
								outcount++;
								outdata.add(outmap);
							}
							
						}
						
					}
				}
				
				
				jsonParser.close();
				
//				logger.debug(outdata);
				logger.debug("****Count Outlier:["+outdata.size()+"]*****" );
				
				//결과를 저장한다....
				logger.debug("*****Save Result :["+dtcvo.getOtlDtcId()+"]*****");
				WadOtlResult resvo = new WadOtlResult();
				resvo.setOtlDtcId(dtcvo.getOtlDtcId());
				resvo.setAnlVarId("XXXX");
			
				if(!sAnaDgr.equals("")) {
					resvo.setAnaDgr( executorDM.getAnaDgr().intValue() );
				}
			
				resvo.setAnaCnt(totcnt);
				if (outdata != null && !outdata.isEmpty())  
					resvo.setEsnErCnt(outdata.size());
//				resvo.setAnaUserId(userid);
//				resvo.setIbsStatus("I");
				
				getOuterResultSql(strSQL);
				
				pstmt = con.prepareStatement(strSQL.toString());
				int pidx = 1;
				pstmt.setString(pidx++, resvo.getOtlDtcId());
				pstmt.setString(pidx++, exeStaDttm);
				pstmt.setString(pidx++, endDttm);
				pstmt.setString(pidx++, resvo.getAnlVarId());
				
				if(resvo.getAnaDgr() == null){
					pstmt.setInt(pidx++, java.sql.Types.NULL);
				}else{
					pstmt.setInt(pidx++, resvo.getAnaDgr());
				}
				
				if (resvo.getAnaCnt() == null) {
					pstmt.setNull(pidx++, java.sql.Types.NULL);
				} else {
					pstmt.setInt(pidx++, resvo.getAnaCnt());
				}
				if (resvo.getEsnErCnt() == null) {
					pstmt.setNull(pidx++, java.sql.Types.NULL);
				} else {
					pstmt.setInt(pidx++, resvo.getEsnErCnt());
				}
				pstmt.setString(pidx++, logId);
				
				pstmt.execute();
				pstmt.close(); pstmt = null;
				
				//아웃라이어 데이터를 저장한다...
				logger.debug("*****Save Outlier Data :["+dtcvo.getOtlDtcId()+"]*****");
				if (outdata != null && !outdata.isEmpty()) {
					strSQL.setLength(0);
					strSQL.append("\n INSERT INTO WAD_OTL_DATA (OTL_DTC_ID, ANA_STR_DTM, OTL_SNO, ");
					strSQL.append("\n   ANL_VAR_ID, COL_CNT ");
					for(int i=1; i<colnms.size();i++) {
						strSQL.append(", COL_NM"+i);
					}
					strSQL.append("\n   ) VALUES ( ");
					strSQL.append("\n  ?, TO_DATE(?, 'YYYYMMDDHH24MISS'), ? ");
					strSQL.append("\n  , ?, ? ");
					for(int i=1; i<colnms.size();i++) {
						strSQL.append(", ?");
					}
					strSQL.append("\n   ) ");
					
					pstmt  = con.prepareStatement(strSQL.toString());
					int psidx = 1;
					pstmt.setString(psidx++, dtcvo.getOtlDtcId());
					pstmt.setString(psidx++, exeStaDttm);
					pstmt.setInt(psidx++, 0);
					pstmt.setString(psidx++, resvo.getAnlVarId());
					pstmt.setInt(psidx++, colnms.size()-1);
					for(String colnm :colnms) {
						if(!"outlier".equals(colnm))
							pstmt.setString(psidx++, colnm);
					}
					
					pstmt.execute();
					//pstmt.close(); pstmt = null;

				}
				
				int cntout = 1;
				for (Map<String, String> outmap : outdata) {
					int psidx = 1;
					pstmt.setString(psidx++, dtcvo.getOtlDtcId());
					pstmt.setString(psidx++, exeStaDttm);
					pstmt.setInt(psidx++, cntout++);
					pstmt.setString(psidx++, resvo.getAnlVarId());
					pstmt.setInt(psidx++, colnms.size()-1);
					for(String colnm :colnms) {
						if(!"outlier".equals(colnm))
							pstmt.setString(psidx++, outmap.get(colnm));
					}
					pstmt.execute();
				}
			}
			
			
			con.commit();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			if(con   != null) try { con.rollback(); } catch(Exception igonred) {}
			throw new Exception(e); 
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}

	/** @param strSQL insomnia */
	private void getOuterResultSql(StringBuffer strSQL) {
		strSQL.setLength(0);
		strSQL.append("\n  INSERT INTO WAD_OTL_RESULT                         ");
		strSQL.append("\n  (                                                  ");
		strSQL.append("\n    OTL_DTC_ID, ANA_STR_DTM, ANA_END_DTM, ANL_VAR_ID ");
		strSQL.append("\n  , ANA_DGR, ANA_CNT, ESN_ER_CNT, ANA_LOG_ID         ");
		strSQL.append("\n  )                                ");
		strSQL.append("\n  VALUES                           ");
		strSQL.append("\n  ( ?                              ");
		strSQL.append("\n  , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		strSQL.append("\n  , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		strSQL.append("\n  , ?                              "); 
		strSQL.append("\n  , ?                              ");
		strSQL.append("\n  , ?                              ");
		strSQL.append("\n  , ?                              ");
		strSQL.append("\n  , ?                              "); //8
		strSQL.append("\n  )                                ");
	}
	
	

}
