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

import kr.wise.advisor.prepare.histogram.service.HistogramVo;
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
public class HistogramDAO {
	
	private static final Logger logger = Logger.getLogger(TextMatchDAO.class);
	
	public WadAnaVar getHistoramVo(String mtcId) throws Exception {   
		StringBuffer strSQL = new StringBuffer();
		
		//OBJ_00000065901
		strSQL.append("\n SELECT A.ANL_VAR_ID      				"); 
		strSQL.append("\n      , A.DASE_ID         				");
		strSQL.append("\n      , A.DB_SCH_ID       				");
		strSQL.append("\n      , A.DB_TBL_NM       				");
		strSQL.append("\n      , A.DB_COL_NM       				");
		strSQL.append("\n      , B.VAR_TYPE						");
		strSQL.append("\n   FROM WAD_ANA_VAR A    				");
		strSQL.append("\n     INNER JOIN WAD_VAR_SUM B    		");
		strSQL.append("\n   	ON A.ANL_VAR_ID = B.ANL_VAR_ID  ");
		strSQL.append("\n  WHERE A.ANL_VAR_ID = ?  				");
	
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
				mtcvo.setVarType(rs.getString("VAR_TYPE"));
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
		PreparedStatement pstmtHst = null;
		
		
		StringBuffer strSQL = new StringBuffer();
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시 
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			logger.debug("*****Start Histogram Result["+mtcvo.getAnlVarId()+"]*****");
			//기존 결과를 삭제한다.
			strSQL.append("\n --히스토그램  소스 결과 삭제 by mtcid");
			strSQL.append("\n DELETE FROM WAD_VAR_HST ");
			strSQL.append("\n  WHERE ANL_VAR_ID = ?   ");
			
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, mtcvo.getAnlVarId());
			
			pstmt.execute();
			
			pstmt.close(); pstmt = null;
			
			//WAD_VAR_HST INSERT
			pstmtHst = con.prepareStatement(insertWAD_VAR_HST());	
			
			
			//WAD_VAR_HST 저장  			
			int iRtn= saveHistogram(pstmtHst,  mtcvo );
			
			logger.debug("\n iRtn: " + iRtn);  
			
			
			con.commit();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			if(con != null) try { con.rollback(); } catch(Exception igonred) {}
			throw new Exception(e); 
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(pstmtHst != null) try { pstmtHst.close(); } catch(Exception igonred) {}
			
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}
	

	private int saveHistogram(PreparedStatement pstmtHst, WadAnaVar mtcvo ) throws SQLException, Exception{
		
		int iRtn = 0;
		
		int psidx = 1;
		
				
		String jsonf = PythonConf.getPYTHON_DATA_PATH() + mtcvo.getAnlVarId()+"_histo.json";
		
		logger.debug("*****Result Json File Load:["+ jsonf + "]*****");
		
		//create JsonParser object
		//JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
		JSONParser jsonParser = new JSONParser();  
		
		JSONObject objTgt = (JSONObject) jsonParser.parse(new InputStreamReader(new FileInputStream(jsonf),"UTF8"));  
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		HistogramVo hstVo=  mapper.convertValue(objTgt,  new TypeReference<HistogramVo>(){} ); 
		
		int iHstSno = 0;
		
		String[] arrSctVal = null;
		String[] arrStrVal = null;
		String[] value = null;
		String[] key = null;

		if(mtcvo.getVarType().equals("numeric")) {
			arrSctVal = hstVo.getSctVal();
			arrStrVal = hstVo.getStrVal();
			
			logger.debug("\n arrSctVal:" + arrSctVal.length);
			logger.debug("\n arrStrVal:" + arrStrVal.length);
			
			for(int i = 0; i < arrSctVal.length; i++) {
				
				psidx = 1;
				pstmtHst.clearParameters();
				
				pstmtHst.setString(psidx++, mtcvo.getAnlVarId());
				pstmtHst.setInt(psidx++, iHstSno);
				pstmtHst.setString(psidx++, arrSctVal[i]); 		
				pstmtHst.setString(psidx++, "");
				pstmtHst.setString(psidx++, arrStrVal[i]);
				pstmtHst.setString(psidx++, arrStrVal[i+1]);
				
				iRtn += pstmtHst.executeUpdate(); 
				
				iHstSno++;			
			}
		} else if(mtcvo.getVarType().equals("categorical")) {
			value = hstVo.getValue();
			key = hstVo.getKey();
			
			//for(int i = 0; i < value.length; i++) {
				
				psidx = 1;
				pstmtHst.clearParameters();
				
				pstmtHst.setString(psidx++, mtcvo.getAnlVarId());
				pstmtHst.setInt(psidx++, iHstSno);
				pstmtHst.setString(psidx++, ""); 		
				pstmtHst.setString(psidx++, "");
				pstmtHst.setString(psidx++, "");
				pstmtHst.setString(psidx++, "");
				
				iRtn += pstmtHst.executeUpdate(); 
				
				iHstSno++;			
			//}
		}  else {
			value = hstVo.getValue();
			key = hstVo.getKey();
			
			//for(int i = 0; i < value.length; i++) {
				
				psidx = 1;
				pstmtHst.clearParameters();
				
				pstmtHst.setString(psidx++, mtcvo.getAnlVarId());
				pstmtHst.setInt(psidx++, iHstSno);
				pstmtHst.setString(psidx++, ""); 		
				pstmtHst.setString(psidx++, "");
				pstmtHst.setString(psidx++, "");
				pstmtHst.setString(psidx++, "");
				
				iRtn += pstmtHst.executeUpdate(); 
				
				iHstSno++;			
			//}
		}
		
		return iRtn;
	}
	
	
	private String insertWAD_VAR_HST(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n INSERT INTO WAD_VAR_HST    ");
		strSQL.append("\n (   ANL_VAR_ID             ");
        strSQL.append("\n   , HST_SNO                ");
        strSQL.append("\n   , SCT_VAL                ");
        strSQL.append("\n   , SCT_NM                 ");
        strSQL.append("\n   , SCT_STR_VAL            ");
        strSQL.append("\n   , SCT_END_VAL            ");
        strSQL.append("\n   , OBJ_DESCN              ");
        strSQL.append("\n   , OBJ_VERS               ");
        strSQL.append("\n   , REG_TYP_CD             ");
        strSQL.append("\n   , WRIT_DTM               "); //10
        strSQL.append("\n   , WRIT_USER_ID           ");
		strSQL.append("\n ) VALUES (                 ");
		strSQL.append("\n     ?                      ");
		strSQL.append("\n   , ?                      ");
		strSQL.append("\n   , ?                      ");
		strSQL.append("\n   , ?                      ");
		strSQL.append("\n   , ?                      ");
		strSQL.append("\n   , ?                      ");
		strSQL.append("\n   , ''                     ");
		strSQL.append("\n   , 1                      ");
		strSQL.append("\n   , 'C'                    ");
		strSQL.append("\n   , SYSDATE                "); //10
		strSQL.append("\n   , 'meta'                 "); 				
		strSQL.append("\n )                          ");
		
		return strSQL.toString();
		
	}
	
	
	
}
