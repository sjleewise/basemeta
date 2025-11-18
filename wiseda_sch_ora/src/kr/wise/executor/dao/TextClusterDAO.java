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
import kr.wise.advisor.prepare.textcluster.service.TextClusterVo;
import kr.wise.advisor.prepare.textcluster.service.WadAnaVar;
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
public class TextClusterDAO {
	
	private static final Logger logger = Logger.getLogger(TextMatchDAO.class);
	
	private Integer execCnt = 1000;
	
	public WadAnaVar getTextClusterVo(String mtcId) throws Exception {   
		StringBuffer strSQL = new StringBuffer();
		
		//OBJ_00000065901
		strSQL.append("\n SELECT ANL_VAR_ID      "); 
		strSQL.append("\n      , DASE_ID         ");
		strSQL.append("\n      , DB_SCH_ID       ");
		strSQL.append("\n      , DB_TBL_NM       ");
		strSQL.append("\n      , DB_COL_NM       ");
		strSQL.append("\n   FROM WAD_ANA_VAR     ");
		strSQL.append("\n  WHERE ANL_VAR_ID = ?  ");
	
		logger.debug(strSQL.toString()+"\n["+mtcId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadAnaVar mtcvo = null;
		
		try{
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mtcvo = new WadAnaVar();
				
				mtcvo.setAnlVarId(rs.getString("ANL_VAR_ID"));			
				mtcvo.setDbSchId(rs.getString("DB_SCH_ID"));
				mtcvo.setDbTblNm(rs.getString("DB_TBL_NM"));
				mtcvo.setDbColNm(rs.getString("DB_COL_NM")); 				
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

	/** @param logId
	/** @param exeStaDttm
	/** @param executorDM
	/** @param dtcvo insomnia 
	 * @throws Exception */
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM, WadAnaVar mtcvo) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtSrc = null;
		PreparedStatement pstmtTgt = null;
		StringBuffer strSQL = new StringBuffer();
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시 
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			logger.debug("*****Start Text Match Result["+mtcvo.getAnlVarId()+"]*****");
			//기존 결과를 삭제한다.
			
			strSQL.append("\n --텍스트클러스트링 소스 결과 삭제 by clstid");
			strSQL.append("\n DELETE FROM WAD_CLST_DATA");
			strSQL.append("\n  WHERE CLST_ID = ? ");
			
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcvo.getAnlVarId());
			
			pstmt.execute();
			
			pstmt.close(); pstmt = null;
			
			pstmtSrc = con.prepareStatement(insertWAD_CLST_DATA());
			
			//WAD_MTC_DATA, WAD_MTC_TGT_DATA 저장 
			
			int iRtn= saveTextCluster(pstmtSrc, pstmtTgt,  mtcvo );
			
			logger.debug("\n iRtn: " + iRtn);  
			
			
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
	

	private int saveTextCluster(PreparedStatement pstmtSrc, PreparedStatement pstmtTgt, WadAnaVar mtcvo ) throws SQLException, Exception{
		
		int iSrcRtn = 0;
		
		int psidx = 1;
		
		int clstScore = 0;
		
		int clstCount = 0;
		
		String srcColNm1 = "";
		
		String tgtColNm1 = "";
		
		
		String jsonf = PythonConf.getPYTHON_DATA_PATH() + mtcvo.getAnlVarId()+".json";
		
		logger.debug("*****Result Json File Load:["+ jsonf + "]*****");
		
		//create JsonParser object
		//JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
		JSONParser jsonParser = new JSONParser();  
		
		JSONArray objTgt = (JSONArray) jsonParser.parse(new InputStreamReader(new FileInputStream(jsonf),"UTF8"));  
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<TextClusterVo> lstTC =  mapper.convertValue(objTgt,  new TypeReference<List<TextClusterVo>>(){} ); 
		
		int iMtcSno = 1;
		
		for(TextClusterVo tmpVo : lstTC){
			
			srcColNm1 = UtilString.null2Blank(tmpVo.getRecommand());
			
			String[] arrResult = tmpVo.getResult();
			int[] score = tmpVo.getScore();
			int[] count = tmpVo.getCount();
			
			for(int i = 0 ; i < arrResult.length; i++){
				psidx = 1;
				pstmtSrc.clearParameters(); 
				
				tgtColNm1 = UtilString.null2Blank(arrResult[i]); 	

				clstScore = score[i];
		        clstCount = count[i];

		        pstmtSrc.setString(psidx++, mtcvo.getAnlVarId());	//CLST_ID
				pstmtSrc.setInt(psidx++, iMtcSno);					//CLST_SNO
				pstmtSrc.setInt(psidx++, clstCount); 				//COL_CNT
				pstmtSrc.setString(psidx++, tgtColNm1);				//SRC_NM
		        pstmtSrc.setString(psidx++, srcColNm1);				//CLST_NM
		        pstmtSrc.setInt(psidx++, clstScore);					//CLST_SCR
				
				iSrcRtn += pstmtSrc.executeUpdate();
				iMtcSno++;
			}
		}
		
		logger.debug("\n iSrcRtn:" + iSrcRtn);
		
		return iSrcRtn;
	}
	
	
	private String insertWAD_CLST_DATA(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n INSERT INTO WAD_CLST_DATA   ");
		strSQL.append("\n (  CLST_ID                  ");
		strSQL.append("\n  , CLST_SNO                 ");
		strSQL.append("\n  , COL_CNT                  ");
		strSQL.append("\n  , SRC_NM                   ");
		strSQL.append("\n  , CLST_NM                  ");
		strSQL.append("\n  , CLST_SCR                 ");
		strSQL.append("\n ) VALUES (                  ");
		strSQL.append("\n     ?                       ");
		strSQL.append("\n   , ?                       ");
		strSQL.append("\n   , ?                       ");
		strSQL.append("\n   , ?                       ");
		strSQL.append("\n   , ?                       ");
		strSQL.append("\n   , ?                       ");
		strSQL.append("\n )                           ");
		
		return strSQL.toString();
		
	}

	public String getSimVal(String mtcId) throws Exception {   
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT CLST_SIM      ");
		strSQL.append("\n   FROM WAD_CLST_SIM     ");
		strSQL.append("\n  WHERE SHD_JOB_ID = ?  ");
	
		logger.debug(strSQL.toString()+"\n["+mtcId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sim = "";
		
		try{
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sim = rs.getString("CLST_SIM");
			}
			
			return sim;
		}catch(Exception e){
			logger.error(e);
			throw e;
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}		
	}
}
