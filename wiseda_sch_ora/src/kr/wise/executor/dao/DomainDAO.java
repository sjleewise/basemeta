/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DomainDAO.java
 * 2. Package : kr.wise.executor.exceute.dao
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 2. 12. 오후 2:30:49
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 2. 12. :            : 신규 개발.
 */
package kr.wise.executor.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.DmnResult;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.exceute.ai.PythonConf;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DomainDAO.java
 * 3. Package  : kr.wise.executor.exceute.dao
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 2. 12. 오후 2:30:49
 * </PRE>
 */
public class DomainDAO {
	
	private static final Logger logger = Logger.getLogger(DomainDAO.class);

	/** @param logId
	/** @param exeStaDttm
	/** @param executorDM
	/** @param rqstNo insomnia 
	 * @throws Exception */
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM, String rqstNo) throws Exception {
		//결과파일을 로드하여 저장한다.
		logger.debug("*****Start Domain Result["+rqstNo+"]*****");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuffer strSQL = new StringBuffer();
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			String jsonf = PythonConf.getPYTHON_DATA_PATH() +rqstNo+".json";
			//create JsonParser object
			JsonParser jsonParser = new JsonFactory().createParser(new File(jsonf));
			
			List<DmnResult> result = new ArrayList<DmnResult>();
			
			while(jsonParser.nextToken() != JsonToken.END_ARRAY){ //array 시작
				DmnResult dmnres = new DmnResult();
				List<Float> fres = new ArrayList<Float>(); 
				while(jsonParser.nextToken() != JsonToken.END_OBJECT) { //object 시작지점
					String name =  jsonParser.getCurrentName();
					if ("anlVarId".equals(name)) {
						jsonParser.nextToken();
						dmnres.setVarId(jsonParser.getText());
					} else if ("dmnPrb".equals(name)) {
						jsonParser.nextToken();
				        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				        	fres.add(jsonParser.getFloatValue());
				        }
					}
					
				}
				dmnres.setDmnpdt(fres);
				result.add(dmnres);
			}
			
			logger.debug("count dmn :["+result.size()+"]");
			
			jsonParser.close();
			
			String[] arrdmn = {"ID","금액","날짜","내용","명칭","번호","수","연락처","율","코드","플래그"};
			
			strSQL.setLength(0);
			strSQL.append("\n DELETE FROM WAD_DMN_PDT");
			strSQL.append("\n WHERE ANL_VAR_ID = ?");
			
			String delsql = strSQL.toString();
			
			strSQL.setLength(0);
			strSQL.append("\n    INSERT INTO WAD_DMN_PDT ( ");
			strSQL.append("\n        ANL_VAR_ID, DMNG_LNM, DMN_PRB, ");
			strSQL.append("\n        OBJ_VERS, REG_TYP_CD, WRIT_DTM, WRIT_USER_ID) ");
			strSQL.append("\n    VALUES ( ?, ?, ?, 1, 'C', SYSDATE, 'system') ");
			
			String insql = strSQL.toString();
			
			for (DmnResult resvo : result) {
				//기존 자료 먼저 삭제한다....
				pstmt = con.prepareStatement(delsql);
				pstmt.setString(1, resvo.getVarId());
				pstmt.executeUpdate();
				pstmt.close();
				
				//결과 저장...
				int cnt = 0;
				for (Float respdt : resvo.getDmnpdt()) {
					pstmt = con.prepareStatement(insql);
					pstmt.setString(1, resvo.getVarId());
					pstmt.setString(2, arrdmn[cnt]);
					pstmt.setFloat(3, respdt);
					pstmt.executeUpdate();
					pstmt.close();
					cnt++;
//					logger.debug("*****COUNT:["+cnt+"]");
				}
				logger.debug("*****varId:["+resvo.getVarId()+"]");
				
				
			}
			logger.debug("*****End Domain Result Save ["+rqstNo+"]*****");
			
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

}
