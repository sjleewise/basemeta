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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.advisor.prepare.textcluster.service.WadMtcData;
import kr.wise.advisor.prepare.textcluster.service.MtcTgtVo;
import kr.wise.advisor.prepare.textcluster.service.MtcTgtVoColVal;
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
public class TextMatchDAO {
	
	private static final Logger logger = Logger.getLogger(TextMatchDAO.class);
	
	public WadDataMtcTbl getTextMatchVo(String mtcId) throws Exception {  
		StringBuffer strSQL = new StringBuffer();
		
        strSQL.append("\n SELECT A.MTC_ID                       ");
        strSQL.append("\n 	   , A.SRC_DB_CONN_TRG_ID           ");
        strSQL.append("\n 	   , A.SRC_DBC_SCH_ID               ");
        strSQL.append("\n 	   , A.SRC_DBC_TBL_NM               ");
        strSQL.append("\n 	   , A.TGT_DB_CONN_TRG_ID           ");
        strSQL.append("\n 	   , A.TGT_DBC_SCH_ID               ");
        strSQL.append("\n 	   , A.TGT_DBC_TBL_NM               "); 
        strSQL.append("\n 	   , B.MTC_COL_SNO                  ");
        strSQL.append("\n 	   , B.SRC_DBC_COL_NM               ");
        strSQL.append("\n 	   , B.TGT_DBC_COL_NM               ");
        strSQL.append("\n   FROM WAD_DATA_MTC_TBL A             ");
        strSQL.append("\n        INNER JOIN WAD_DATA_MTC_COL B  ");
        strSQL.append("\n           ON A.MTC_ID = B.MTC_ID      ");
        strSQL.append("\n  WHERE A.MTC_ID = ?                   ");
	   
        
		logger.debug(strSQL.toString()+"\n["+mtcId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadDataMtcTbl mtcvo = null;
		
		try{
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mtcvo = new WadDataMtcTbl();
				
				mtcvo.setMtcId(rs.getString("MTC_ID"));
				mtcvo.setSrcDbConnTrgId(rs.getString("SRC_DB_CONN_TRG_ID"));
				mtcvo.setSrcDbcSchId(rs.getString("SRC_DBC_SCH_ID"));
				mtcvo.setSrcDbcTblNm(rs.getString("SRC_DBC_TBL_NM"));
				mtcvo.setTgtDbConnTrgId(rs.getString("TGT_DB_CONN_TRG_ID"));
				mtcvo.setTgtDbcSchId(rs.getString("TGT_DBC_SCH_ID"));
				mtcvo.setTgtDbcTblNm(rs.getString("TGT_DBC_TBL_NM")); 				
				
				mtcvo.setColList(getColList(mtcId, con)); 
				
			}
			
			return mtcvo;
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
	/** @return insomnia */
	private List<WadDataMtcCol> getColList(String mtcId, Connection con) {
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT A.MTC_ID                       ");
        strSQL.append("\n 	   , A.SRC_DB_CONN_TRG_ID           ");
        strSQL.append("\n 	   , A.SRC_DBC_SCH_ID               ");
        strSQL.append("\n 	   , A.SRC_DBC_TBL_NM               ");
        strSQL.append("\n 	   , A.TGT_DB_CONN_TRG_ID           ");
        strSQL.append("\n 	   , A.TGT_DBC_SCH_ID               ");
        strSQL.append("\n 	   , A.TGT_DBC_TBL_NM               "); 
        strSQL.append("\n 	   , B.MTC_COL_SNO                  ");
        strSQL.append("\n 	   , B.SRC_DBC_COL_NM               ");
        strSQL.append("\n 	   , B.TGT_DBC_COL_NM               ");
        strSQL.append("\n   FROM WAD_DATA_MTC_TBL A             ");
        strSQL.append("\n        INNER JOIN WAD_DATA_MTC_COL B  ");
        strSQL.append("\n           ON A.MTC_ID = B.MTC_ID      ");
        strSQL.append("\n  WHERE A.MTC_ID = ?                   ");
		
		logger.debug(strSQL.toString()+"\n["+ mtcId+"]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<WadDataMtcCol> list = new ArrayList<WadDataMtcCol>();
		
		try {
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				WadDataMtcCol mtccol = new WadDataMtcCol();
				
				mtccol.setMtcColSno(rs.getString("MTC_COL_SNO"));
				mtccol.setSrcDbcColNm(rs.getString("SRC_DBC_COL_NM"));
				mtccol.setTgtDbcColNm(rs.getString("TGT_DBC_COL_NM"));
				
				list.add(mtccol);
			}
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
		}
		return list;

	}

	

	/** @param logId
	/** @param exeStaDttm
	/** @param executorDM
	/** @param dtcvo insomnia 
	 * @throws Exception */
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM, WadDataMtcTbl mtcvo) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtSrc = null;
		PreparedStatement pstmtTgt = null;
		StringBuffer strSQL = new StringBuffer();
		
		logger.debug("dgr >>> " + executorDM.getAnaDgr());
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			//차수 존재시
			if(executorDM.getAnaDgr() != null) {
				logger.debug("*****Start Text Match Result["+mtcvo.getMtcId()+"]*****");
				//기존 결과를 삭제한다.
				strSQL.append("\n --텍스트매칭 소스 결과 삭제 by mtcid");
				strSQL.append("\n DELETE FROM WAD_MTC_DATA");
				strSQL.append("\n  WHERE MTC_ID = ? ");
				strSQL.append("\n    AND ANA_DGR = ? ");
				
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, mtcvo.getMtcId());
				pstmt.setBigDecimal(2, executorDM.getAnaDgr());
				
				pstmt.execute();
				
				pstmt.close(); pstmt = null;
				
				strSQL.setLength(0);
				strSQL.append("\n --텍스트매칭 타겟 결과 삭제 by mtcid");
				strSQL.append("\n DELETE FROM WAD_MTC_TGT_DATA");
				strSQL.append("\n  WHERE MTC_ID = ? ");
				strSQL.append("\n    AND ANA_DGR = ? ");
	
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, mtcvo.getMtcId());
				pstmt.setBigDecimal(2, executorDM.getAnaDgr());
				
				pstmt.execute();
				
				pstmt.close(); pstmt = null;
				
				pstmtSrc = con.prepareStatement(insertWAD_MTC_DATA());			
				
				pstmtTgt = con.prepareStatement(insertWAD_MTC_TGT_DATA());	 
				
				//WAD_MTC_DATA 저장 
				int iSrc = saveMtcSrc( pstmtSrc,  mtcvo, executorDM.getAnaDgr(), exeStaDttm, endDttm );  
				logger.debug("\n iSrc: " + iSrc);  
							
				//WAD_MTC_TGT_DATA 저장 
				int iTgt = saveMtcTgt( pstmtTgt,  mtcvo, executorDM.getAnaDgr(), exeStaDttm, endDttm );
				logger.debug("\n iTgt: " + iTgt); 
			}
			//차수 미존재시
			else {
				logger.debug("*****Start Text Match Result["+mtcvo.getMtcId()+"]*****");
				//기존 결과를 삭제한다.
				strSQL.append("\n --텍스트매칭 소스 결과 삭제 by mtcid");
				strSQL.append("\n DELETE FROM WAD_MTC_DATA");
				strSQL.append("\n  WHERE MTC_ID = ? ");
				strSQL.append("\n    AND ANA_DGR IS NULL ");
				
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, mtcvo.getMtcId());
				
				pstmt.execute();
				
				pstmt.close(); pstmt = null;
				
				strSQL.setLength(0);
				strSQL.append("\n --텍스트매칭 타겟 결과 삭제 by mtcid");
				strSQL.append("\n DELETE FROM WAD_MTC_TGT_DATA");
				strSQL.append("\n  WHERE MTC_ID = ? ");
				strSQL.append("\n    AND ANA_DGR IS NULL ");
	
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setString(1, mtcvo.getMtcId());
				pstmt.execute();
				
				pstmt.close(); pstmt = null;
				
				pstmtSrc = con.prepareStatement(insertWAD_MTC_DATA());			
				
				pstmtTgt = con.prepareStatement(insertWAD_MTC_TGT_DATA());	 
				
				//WAD_MTC_DATA 저장 
				int iSrc = saveMtcSrc( pstmtSrc,  mtcvo, null, exeStaDttm, endDttm );  
				
				logger.debug("\n iSrc: " + iSrc);  
							
				//WAD_MTC_TGT_DATA 저장 
				int iTgt = saveMtcTgt( pstmtTgt,  mtcvo, null, exeStaDttm, endDttm );
				
				logger.debug("\n iTgt: " + iTgt);  
			}
			
			con.commit();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			if(con != null) try { con.rollback(); } catch(Exception igonred) {}
			throw new Exception(e); 
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(pstmtSrc != null) try { pstmtSrc.close(); } catch(Exception igonred) {}
			if(pstmtTgt != null) try { pstmtTgt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}
	
	private int saveMtcSrc(PreparedStatement pstmtSrc, WadDataMtcTbl mtcvo, BigDecimal anaDgr, String exeStaDttm, String endDttm ) throws SQLException, Exception{
		logger.debug("parameter anaDgr >>> " + anaDgr);
		int iRtn = 0;
		
		String srcColNm1 = "";
		String srcColNm2 = "";
		String srcColNm3 = "";
		String srcColNm4 = "";
		String srcColNm5 = "";
		String srcColNm6 = "";
		String srcColNm7 = "";
		String srcColNm8 = "";
		String srcColNm9 = "";
		String srcColNm10 = "";
			
		//==================================================
		List<WadDataMtcCol> colList = mtcvo.getColList(); 
		
		int iColCnt = 1;
					
		for(WadDataMtcCol tmpCol : colList )	{
			
			if(iColCnt == 1){
				srcColNm1 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 2){
				srcColNm2 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 3){
				srcColNm3 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 4){
				srcColNm4 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 5){
				srcColNm5 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 6){
				srcColNm6 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 7){
				srcColNm7 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 8){
				srcColNm8 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 9){
				srcColNm9 = tmpCol.getSrcDbcColNm(); 
			}else if(iColCnt == 10){
				srcColNm10 = tmpCol.getSrcDbcColNm(); 
			}
			
			iColCnt++;
		}
		
		int psidx =1;
		
		iColCnt = iColCnt -1;
		
		psidx = 1;
		pstmtSrc.setString(psidx++, mtcvo.getMtcId());
		pstmtSrc.setInt(psidx++, 0);
		pstmtSrc.setBigDecimal(psidx++, anaDgr);
		pstmtSrc.setString(psidx++, exeStaDttm);
		pstmtSrc.setString(psidx++, endDttm);
		pstmtSrc.setInt(psidx++, iColCnt); 		
		pstmtSrc.setString(psidx++, srcColNm1);
		pstmtSrc.setString(psidx++, srcColNm2);
		pstmtSrc.setString(psidx++, srcColNm3);
		pstmtSrc.setString(psidx++, srcColNm4);
		pstmtSrc.setString(psidx++, srcColNm5);
		pstmtSrc.setString(psidx++, srcColNm6);
		pstmtSrc.setString(psidx++, srcColNm7);
		pstmtSrc.setString(psidx++, srcColNm8);
		pstmtSrc.setString(psidx++, srcColNm9);
		pstmtSrc.setString(psidx++, srcColNm10);

		iRtn += pstmtSrc.executeUpdate(); 
		//========================================
		
		String jsonf = PythonConf.getPYTHON_DATA_PATH() + mtcvo.getMtcId() + "_1" +".json";
		
		logger.debug("*****Result Json File Load:["+ jsonf + "]*****"); 
		
		//create JsonParser object
		//JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf)); 
		JSONParser jsonParser = new JSONParser();  
		
		//JSONArray objSrc = (JSONArray) jsonParser.parse(new FileReader(jsonf));  
		JSONArray objSrc = (JSONArray) jsonParser.parse(new InputStreamReader(new FileInputStream(jsonf),"UTF8"));  
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<MtcTgtVoColVal> lstMtch   =  mapper.convertValue(objSrc,  new TypeReference<List<MtcTgtVoColVal>>(){} ); 
		
		int iMtcSno = 1;
		
		for(MtcTgtVoColVal tmpColValVo : lstMtch){
			
			String[] arrColVal = tmpColValVo.getCol_val(); 
			
	        srcColNm1 = ""; 
	        srcColNm2 = "";
	        srcColNm3 = "";
	        srcColNm4 = "";
	        srcColNm5 = "";
	        srcColNm6 = "";
	        srcColNm7 = "";
	        srcColNm8 = "";
	        srcColNm9 = "";
	        srcColNm10 = "";
			
			for(int i = 0 ; i < arrColVal.length; i++){ 
				
				if(i == 0){
					srcColNm1 = arrColVal[i];
				}else if(i == 1){
					srcColNm2 = arrColVal[i];
				}else if(i == 2){
					srcColNm3 = arrColVal[i];
				}else if(i == 3){
					srcColNm4 = arrColVal[i];
				}else if(i == 4){
					srcColNm5 = arrColVal[i];
				}else if(i == 5){
					srcColNm6 = arrColVal[i];
				}else if(i == 6){
					srcColNm7 = arrColVal[i];
				}else if(i == 7){
					srcColNm8 = arrColVal[i];
				}else if(i == 8){
					srcColNm9 = arrColVal[i];
				}else if(i == 9){
					srcColNm10 = arrColVal[i];
				}
			}
			
			String sSimilarity =  tmpColValVo.getSimilarity();
			
			//logger.debug("\n sSimilarity: " + sSimilarity);
			
		    psidx = 1;
			pstmtSrc.setString(psidx++, mtcvo.getMtcId());
			pstmtSrc.setInt(psidx++, iMtcSno); 	
			pstmtSrc.setBigDecimal(psidx++, anaDgr);
			pstmtSrc.setString(psidx++, exeStaDttm);
			pstmtSrc.setString(psidx++, endDttm);
			pstmtSrc.setInt(psidx++, iColCnt);
			pstmtSrc.setString(psidx++, srcColNm1);
			pstmtSrc.setString(psidx++, srcColNm2);
			pstmtSrc.setString(psidx++, srcColNm3);
			pstmtSrc.setString(psidx++, srcColNm4);
			pstmtSrc.setString(psidx++, srcColNm5);
			pstmtSrc.setString(psidx++, srcColNm6);
			pstmtSrc.setString(psidx++, srcColNm7);
			pstmtSrc.setString(psidx++, srcColNm8);
			pstmtSrc.setString(psidx++, srcColNm9);
			pstmtSrc.setString(psidx++, srcColNm10);

			iRtn += pstmtSrc.executeUpdate(); 
			
			iMtcSno++;
		}
		
		return iRtn;
	}

	
	private int saveMtcTgt(PreparedStatement pstmtTgt, WadDataMtcTbl mtcvo, BigDecimal anaDgr, String exeStaDttm, String endDttm ) throws SQLException, Exception{
		
		int iRtn = 0;
		
		String tgtColNm1 = "";
		String tgtColNm2 = "";
		String tgtColNm3 = "";
		String tgtColNm4 = "";
		String tgtColNm5 = "";
		String tgtColNm6 = "";
		String tgtColNm7 = "";
		String tgtColNm8 = "";
		String tgtColNm9 = "";
		String tgtColNm10 = "";
			
		//==================================================
		List<WadDataMtcCol> colList = mtcvo.getColList(); 
		
		int iColCnt = 1;
					
		for(WadDataMtcCol tmpCol : colList )	{
			
			if(iColCnt == 1){
				tgtColNm1 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 2){
				tgtColNm2 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 3){
				tgtColNm3 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 4){
				tgtColNm4 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 5){
				tgtColNm5 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 6){
				tgtColNm6 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 7){
				tgtColNm7 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 8){
				tgtColNm8 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 9){
				tgtColNm9 = tmpCol.getTgtDbcColNm(); 
			}else if(iColCnt == 10){
				tgtColNm10 = tmpCol.getTgtDbcColNm(); 
			}
			
			iColCnt++;
		}
		
		int psidx =1;
		
		iColCnt = iColCnt -1;
		
		psidx = 1;
		pstmtTgt.setString(psidx++, mtcvo.getMtcId());
		pstmtTgt.setInt(psidx++, 0);
		pstmtTgt.setInt(psidx++, 0);
		pstmtTgt.setString(psidx++, "");
		pstmtTgt.setBigDecimal(psidx++, anaDgr);
		pstmtTgt.setString(psidx++, exeStaDttm);
		pstmtTgt.setString(psidx++, endDttm);
		pstmtTgt.setInt(psidx++, iColCnt);
		pstmtTgt.setString(psidx++, tgtColNm1);
		pstmtTgt.setString(psidx++, tgtColNm2);
		pstmtTgt.setString(psidx++, tgtColNm3);
		pstmtTgt.setString(psidx++, tgtColNm4);
		pstmtTgt.setString(psidx++, tgtColNm5);
		pstmtTgt.setString(psidx++, tgtColNm6);
		pstmtTgt.setString(psidx++, tgtColNm7);
		pstmtTgt.setString(psidx++, tgtColNm8);
		pstmtTgt.setString(psidx++, tgtColNm9);
		pstmtTgt.setString(psidx++, tgtColNm10);
		
		
		iRtn += pstmtTgt.executeUpdate(); 
		//========================================
		
		String jsonf = PythonConf.getPYTHON_DATA_PATH() + mtcvo.getMtcId() + "_2" +".json";
		
		logger.debug("*****Result Json File Load:["+ jsonf + "]*****"); 
		
		//create JsonParser object
		//JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
		JSONParser jsonParser = new JSONParser();  
		
		//JSONArray objTgt = (JSONArray) jsonParser.parse(new FileReader(jsonf));
		
		JSONArray objTgt = (JSONArray) jsonParser.parse(new InputStreamReader(new FileInputStream(jsonf),"UTF8"));   
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<MtcTgtVo> lstMtch   =  mapper.convertValue(objTgt,  new TypeReference<List<MtcTgtVo>>(){} ); 
		
		int iMtcSno = 1;
		
		for(MtcTgtVo tmpVo : lstMtch){
			
			ArrayList<MtcTgtVoColVal> lstColVal = tmpVo.getTxtMch();
			
			int iTgtDtlSno = 1;
			
			for(MtcTgtVoColVal tmpColValVo : lstColVal){
				
				String[] arrColVal = tmpColValVo.getCol_val(); 
				
		        tgtColNm1 = ""; 
		        tgtColNm2 = "";
		        tgtColNm3 = "";
		        tgtColNm4 = "";
		        tgtColNm5 = "";
		        tgtColNm6 = "";
		        tgtColNm7 = "";
		        tgtColNm8 = "";
		        tgtColNm9 = "";
		        tgtColNm10 = "";
				
				for(int i = 0 ; i < arrColVal.length; i++){ 
					
					if(i == 0){
						tgtColNm1 = arrColVal[i];
					}else if(i == 1){
						tgtColNm2 = arrColVal[i];
					}else if(i == 2){
						tgtColNm3 = arrColVal[i];
					}else if(i == 3){
						tgtColNm4 = arrColVal[i];
					}else if(i == 4){
						tgtColNm5 = arrColVal[i];
					}else if(i == 5){
						tgtColNm6 = arrColVal[i];
					}else if(i == 6){
						tgtColNm7 = arrColVal[i];
					}else if(i == 7){
						tgtColNm8 = arrColVal[i];
					}else if(i == 8){
						tgtColNm9 = arrColVal[i];
					}else if(i == 9){
						tgtColNm10 = arrColVal[i];
					}
				}
				
				String sSimilarity =  tmpColValVo.getSimilarity();
				
				//logger.debug("\n sSimilarity: " + sSimilarity);
				
			    psidx = 1;
				pstmtTgt.setString(psidx++, mtcvo.getMtcId());
				pstmtTgt.setInt(psidx++, iMtcSno);
				pstmtTgt.setInt(psidx++, iTgtDtlSno);
				pstmtTgt.setString(psidx++, sSimilarity);
				pstmtTgt.setBigDecimal(psidx++, anaDgr);
				pstmtTgt.setString(psidx++, exeStaDttm);
				pstmtTgt.setString(psidx++, endDttm);
				pstmtTgt.setInt(psidx++, iColCnt);
				pstmtTgt.setString(psidx++, tgtColNm1);
				pstmtTgt.setString(psidx++, tgtColNm2);
				pstmtTgt.setString(psidx++, tgtColNm3);
				pstmtTgt.setString(psidx++, tgtColNm4);
				pstmtTgt.setString(psidx++, tgtColNm5);
				pstmtTgt.setString(psidx++, tgtColNm6);
				pstmtTgt.setString(psidx++, tgtColNm7);
				pstmtTgt.setString(psidx++, tgtColNm8);
				pstmtTgt.setString(psidx++, tgtColNm9);
				pstmtTgt.setString(psidx++, tgtColNm10);
				
				iRtn += pstmtTgt.executeUpdate(); 
				
				iTgtDtlSno++;
			}
			
			iMtcSno++;
		}
		
		return iRtn;
	}
	
	
	
	
	private String insertWAD_MTC_DATA(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n INSERT INTO WAD_MTC_DATA           ");
		strSQL.append("\n (  MTC_ID                          ");
		strSQL.append("\n  , MTC_SNO                         ");
		strSQL.append("\n  , ANA_DGR                         ");
		strSQL.append("\n  , ANA_STR_DTM                     ");
		strSQL.append("\n  , ANA_END_DTM                     ");
		strSQL.append("\n  , COL_CNT                         ");
		strSQL.append("\n  , SRC_COL_NM1                     ");
		strSQL.append("\n  , SRC_COL_NM2                     ");
		strSQL.append("\n  , SRC_COL_NM3                     ");
		strSQL.append("\n  , SRC_COL_NM4                     ");
		strSQL.append("\n  , SRC_COL_NM5                     ");
		strSQL.append("\n  , SRC_COL_NM6                     ");
		strSQL.append("\n  , SRC_COL_NM7                     ");
		strSQL.append("\n  , SRC_COL_NM8                     ");
		strSQL.append("\n  , SRC_COL_NM9                     ");
		strSQL.append("\n  , SRC_COL_NM10                    ");
		strSQL.append("\n ) VALUES (                         ");
		strSQL.append("\n     ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		strSQL.append("\n   , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n )                                  ");
		
		
		return strSQL.toString();
		
	}
	
	private String insertWAD_MTC_TGT_DATA(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n INSERT INTO WAD_MTC_TGT_DATA       ");
		strSQL.append("\n (  MTC_ID                          ");
		strSQL.append("\n  , MTC_SNO                         ");		
		strSQL.append("\n  , TGT_DTL_SNO                     ");
		strSQL.append("\n  , MTC_PRB                         ");
		strSQL.append("\n  , ANA_DGR                         ");
		strSQL.append("\n  , ANA_STR_DTM                     ");
		strSQL.append("\n  , ANA_END_DTM                     ");
		strSQL.append("\n  , COL_CNT                         ");
		strSQL.append("\n  , TGT_COL_NM1                     ");
		strSQL.append("\n  , TGT_COL_NM2                     ");
		strSQL.append("\n  , TGT_COL_NM3                     ");
		strSQL.append("\n  , TGT_COL_NM4                     ");
		strSQL.append("\n  , TGT_COL_NM5                     ");
		strSQL.append("\n  , TGT_COL_NM6                     ");
		strSQL.append("\n  , TGT_COL_NM7                     ");
		strSQL.append("\n  , TGT_COL_NM8                     ");
		strSQL.append("\n  , TGT_COL_NM9                     ");
		strSQL.append("\n  , TGT_COL_NM10                    ");
		strSQL.append("\n ) VALUES (                         ");
		strSQL.append("\n     ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		strSQL.append("\n   , TO_DATE(?, 'YYYYMMDDHH24MISS') "); 
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              "); 
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              ");
		strSQL.append("\n   , ?                              "); 
		strSQL.append("\n )                                  ");
		
		return strSQL.toString();
		
	}

	
}
