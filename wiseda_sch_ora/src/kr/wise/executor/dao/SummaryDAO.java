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
import kr.wise.advisor.prepare.summary.service.SummaryVo;
import kr.wise.advisor.prepare.summary.service.WadDataSet;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.advisor.prepare.textcluster.service.WadMtcData;
import kr.wise.advisor.prepare.textcluster.service.MtcTgtVo;
import kr.wise.advisor.prepare.textcluster.service.MtcTgtVoColVal;
import kr.wise.advisor.prepare.textcluster.service.TextClusterVo;
import kr.wise.advisor.prepare.textcluster.service.WadAnaVar;
import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilNumber;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.exceute.ai.PythonConf;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SummaryDAO.java
 * 3. Package  : kr.wise.executor.dao
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 1. 16. 오후 2:53:42
 * </PRE>
 */
public class SummaryDAO {
	
	private static final Logger logger = Logger.getLogger(TextMatchDAO.class);
	
	public WadDataSet getWadDataSetVo(String daseId) throws Exception {     
		StringBuffer strSQL = new StringBuffer();
		
		//OBJ_00000078344
		strSQL.append("\n SELECT DASE_ID        "); 
		strSQL.append("\n      , DASE_LNM       ");
		strSQL.append("\n      , DASE_PNM       ");
		strSQL.append("\n      , DB_SCH_ID      ");
		strSQL.append("\n      , DB_TBL_NM      ");
		strSQL.append("\n   FROM WAD_DATA_SET   ");
		strSQL.append("\n  WHERE DASE_ID = ?    ");
	
		logger.debug(strSQL.toString()+"\n["+daseId+"]");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadDataSet mtcvo = null;
		
		try{
			con = ConnectionHelper.getDAConnection();
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, daseId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mtcvo = new WadDataSet();
				
				mtcvo.setDaseId(rs.getString("DASE_ID"));
				mtcvo.setDaseLnm(rs.getString("DASE_LNM"));
				mtcvo.setDasePnm(rs.getString("DASE_PNM"));
				mtcvo.setDbSchId(rs.getString("DB_SCH_ID"));				
				mtcvo.setDbTblNm(rs.getString("DB_TBL_NM"));
						
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
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM, WadDataSet mtcvo) throws Exception {  
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtSum = null;
		PreparedStatement pstmtUpd = null;
		StringBuffer strSQL = new StringBuffer();
		int iDel = 0;
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시 
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			logger.debug("*****Start Summary Result *****");
			//기존 결과를 삭제한다.
			strSQL.append("\n --텍스트클러스트링 소스 결과 삭제 by mtcid");
			strSQL.append("\n DELETE FROM WAD_VAR_SUM                                  ");
			strSQL.append("\n  WHERE ANL_VAR_ID IN (SELECT B.ANL_VAR_ID                "); 
			strSQL.append("\n                         FROM WAD_DATA_SET A              ");
            strSQL.append("\n                              INNER JOIN WAD_ANA_VAR B    ");
            strSQL.append("\n                                 ON B.DASE_ID = A.DASE_ID ");        
			strSQL.append("\n                        WHERE A.DASE_ID = ?               ");
			strSQL.append("\n                      )                                   ");
			
			pstmt = con.prepareStatement(strSQL.toString());
			
			pstmt.setString(1, mtcvo.getDaseId());
			 
			iDel = pstmt.executeUpdate();			
			
			logger.debug("\n iDel:" + iDel);
			
			pstmt.close(); pstmt = null;
			
			
			// INSERT WAD_VAR_SUM
			pstmtSum = con.prepareStatement(insertWAD_VAR_SUM());		
			
			pstmtUpd = con.prepareStatement(updateWAD_VAR_SUM());	
			
			//WAD_VAR_SUM 저장 			
			int iRtn= saveSummary(pstmtSum, pstmtUpd, mtcvo );
			
			logger.debug("\n iRtn: " + iRtn);
			
			con.commit();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			if(con != null) try { con.rollback(); } catch(Exception igonred) {}
			throw new Exception(e); 
		} finally {
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(pstmtSum != null) try { pstmtSum.close(); } catch(Exception igonred) {}
			
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
		
	}
	

	private int saveSummary(PreparedStatement pstmtSum, PreparedStatement pstmtUpd, WadDataSet mtcvo ) throws SQLException, Exception{
		
		int iRtn = 0;
		
		int psidx = 1;
				
		
		String jsonf = PythonConf.getPYTHON_DATA_PATH() + mtcvo.getDaseId()+".json";
		
		logger.debug("*****Result Json File Load:["+ jsonf + "]*****");
		
		//create JsonParser object
		//JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
		JSONParser jsonParser = new JSONParser();  
		
		JSONArray objTgt = (JSONArray) jsonParser.parse(new InputStreamReader(new FileInputStream(jsonf),"UTF8"));  
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<SummaryVo> lstTC =  mapper.convertValue(objTgt,  new TypeReference<List<SummaryVo>>(){} );  
		
		if(lstTC.size() > 0) {
			
			psidx = 1;
			pstmtSum.clearParameters();
			
			pstmtSum.setString(psidx++, mtcvo.getDaseId());

			iRtn = pstmtSum.executeUpdate(); 	 		
		}
				
		logger.debug("\n iRtn1: " + iRtn);
		
		iRtn = 0;
				
		for(SummaryVo tmpVo : lstTC){ 
						
			psidx = 1;
			pstmtUpd.clearParameters();
			
			pstmtUpd.setString(psidx++, tmpVo.getType());   
			pstmtUpd.setInt(psidx++, UtilNumber.str2int(tmpVo.getCount())); 
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getQ2())); 
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getMax()));
			pstmtUpd.setString(psidx++, tmpVo.getTop()!=null?tmpVo.getTop():"" );
			pstmtUpd.setInt(psidx++, UtilNumber.str2int(tmpVo.getFreq()) ); 
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getMin()) );
			pstmtUpd.setInt(psidx++, UtilNumber.str2int(tmpVo.getUnique()) );
			pstmtUpd.setFloat(psidx++,  UtilNumber.str2float(tmpVo.getMean()) );
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getStd()) );
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getQ1()) );
			pstmtUpd.setFloat(psidx++, UtilNumber.str2float(tmpVo.getQ3()) ); 
						
			pstmtUpd.setString(psidx++, mtcvo.getDaseId());
			pstmtUpd.setString(psidx++, mtcvo.getDbSchId());
			pstmtUpd.setString(psidx++, mtcvo.getDbTblNm().toUpperCase()); 
			pstmtUpd.setString(psidx++, tmpVo.getName().toUpperCase());
						
			iRtn += pstmtUpd.executeUpdate(); 
			
		}
		
		logger.debug("\n iRtn2: " + iRtn);
		
		
		return iRtn;
	}
	
	
	private String insertWAD_VAR_SUM(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n INSERT INTO WAD_VAR_SUM           ");
		strSQL.append("\n (  ANL_VAR_ID                     ");
		strSQL.append("\n  , OBJ_DESCN                      ");
		strSQL.append("\n  , OBJ_VERS                       "); 
		strSQL.append("\n  , REG_TYP_CD                     ");
		strSQL.append("\n  , WRIT_DTM                       ");
		strSQL.append("\n  , WRIT_USER_ID                   "); 
		strSQL.append("\n )                                 ");
		strSQL.append("\n SELECT B.ANL_VAR_ID               ");		
		strSQL.append("\n      , ''                         ");
		strSQL.append("\n      , 1                          ");
		strSQL.append("\n      , 'C'                        ");
		strSQL.append("\n      , SYSDATE                    ");
		strSQL.append("\n      , 'meta'                     ");
		strSQL.append("\n  FROM WAD_DATA_SET A              ");
		strSQL.append("\n       INNER JOIN WAD_ANA_VAR B    ");
		strSQL.append("\n          ON B.DASE_ID = A.DASE_ID ");
		strSQL.append("\n WHERE A.DASE_ID = ?               ");
		
		return strSQL.toString();
		
	}
	
	private String updateWAD_VAR_SUM(){ 
		
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n UPDATE WAD_VAR_SUM                             ");
		strSQL.append("\n    SET VAR_TYPE = ?                            ");
        strSQL.append("\n      , TOT_CNT  = ?                            ");
        strSQL.append("\n      , MDN_VAL  = ?                            ");
        strSQL.append("\n      , MAX_VAL  = ?                            ");
        strSQL.append("\n      , TOP_VAL  = ?                            "); //5
        strSQL.append("\n      , FRQ_VAL  = ?                            ");
        strSQL.append("\n      , MIN_VAL  = ?                            ");
        strSQL.append("\n      , UNQ_VAL  = ?                            ");
        strSQL.append("\n      , MN_VAL   = ?                            ");
        strSQL.append("\n      , STD_DVT  = ?                            "); //10
        strSQL.append("\n      , QRT_CNT1 = ?                            ");
        strSQL.append("\n      , QRT_CNT3 = ?                            "); 
		strSQL.append("\n  WHERE ANL_VAR_ID =                            ");
		strSQL.append("\n                    (SELECT ANL_VAR_ID          ");
		strSQL.append("\n                       FROM WAD_ANA_VAR         ");
		strSQL.append("\n                      WHERE DASE_ID   = ?       ");
		strSQL.append("\n                        AND DB_SCH_ID = ?       ");
		strSQL.append("\n                        AND UPPER(DB_TBL_NM) = ?       ");
		strSQL.append("\n                        AND UPPER(DB_COL_NM) = ?       ");
		strSQL.append("\n                     )                          ");
		
		return strSQL.toString();
		
	}
	
}
