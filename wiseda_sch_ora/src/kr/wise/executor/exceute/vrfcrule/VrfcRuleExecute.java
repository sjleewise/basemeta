package kr.wise.executor.exceute.vrfcrule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.QueryUtils;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;
import kr.wise.executor.Executor;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.BusinessRuleDAO; 
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dao.VrfcRuleDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.ProfileResultDM;
import kr.wise.executor.dm.ProfileResultPatternDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class VrfcRuleExecute extends Executor {
	private static final Logger logger = Logger.getLogger(VrfcRuleExecute.class);
	
	public VrfcRuleExecute(String logId, ExecutorDM executorDM){
		super(logId, executorDM);
	}
	
	protected Boolean execute() throws SQLException, Exception {

		VrfcRuleDAO vrfcRuleDAO = new VrfcRuleDAO(); 
		
		logger.debug("\n excute start ");
		
		//SQL 제너레이터
		VrfcSqlGeneratorVO vrfcSqlGeneratorVO = vrfcRuleDAO.sqlGenerator(executorDM.getShdJobId());
		
		String ruleRelId   = vrfcSqlGeneratorVO.getRuleRelId();
		String vrfcNm      = UtilString.null2Blank(vrfcSqlGeneratorVO.getVrfcNm());
		String vrfcRule    = UtilString.null2Blank(vrfcSqlGeneratorVO.getVrfcRule());
		String vrfcTyp     = UtilString.null2Blank(vrfcSqlGeneratorVO.getVrfcTyp());
		
		String prfKndCd    = UtilString.null2Blank(vrfcSqlGeneratorVO.getPrfKndCd());
		String dbConnTrgId = vrfcSqlGeneratorVO.getDbConnTrgId();
		
		logger.debug("\n tot:" + vrfcSqlGeneratorVO.getTotalCount());
		logger.debug("\n errcnt:" + vrfcSqlGeneratorVO.getErrorCount());
		logger.debug("\n errdata:" + vrfcSqlGeneratorVO.getErrorData());
		logger.debug("\n errpat:" + vrfcSqlGeneratorVO.getErrorPattern());
//		logger.debug(sqlGeneratorVO.getNullCountSql());
//		logger.debug(sqlGeneratorVO.getMinMaxLenSql());
//		logger.debug(sqlGeneratorVO.getMinMaxSql());
//		logger.debug(sqlGeneratorVO.getSpaceCountSql());
		
		// 측정대상
		Connection con = null;
		ProfileResultDM resultDM = null;
		ProfileResultPatternDM resultPatternDM = null;
		ProfileResultPatternDM resultPatternDM1 = null;
		StringBuffer anaQuery = new StringBuffer();

		TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
		TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms( dbConnTrgId );
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());

			//대상건수와 분석건수를 합친다.
			if("FRM".equals(vrfcTyp)){
				anaQuery.append("\n " + vrfcSqlGeneratorVO.getTotalCount());
			}else{
				anaQuery.append("\n " + vrfcSqlGeneratorVO.getTotalCount());
				anaQuery.append("\n UNION ALL "                   );
				anaQuery.append("\n " + vrfcSqlGeneratorVO.getErrorCount());
			}
			
			resultDM = vrfcRuleDAO.executeAnalysisQueryOfTargetDbms(con, anaQuery.toString());
			
			if(vrfcTyp.equals("FRM")){  
				//형식 SQL 
				resultPatternDM = vrfcRuleDAO.executeFrmPatternQueryOfTargetDbms(con, vrfcSqlGeneratorVO.getErrorPattern(), vrfcSqlGeneratorVO, ExecutorConf.getErrDataLimit());
															
				resultDM.setErrCnt(resultPatternDM.getPatternCount());
				
			}else if(vrfcTyp.equals("DTM")){  
				//형식 SQL 
//				resultPatternDM = vrfcRuleDAO.executeDatePatternProfileOfTargetDbms(con, vrfcSqlGeneratorVO.getDatePatSql(), ExecutorConf.getErrDataLimit(), prfKndCd);
				// 패턴별건수 SQL실행 (PAT_SQL)
				resultPatternDM = vrfcRuleDAO.executePatternQueryOfTargetDbms(con, 
						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), vrfcSqlGeneratorVO.getErrorPattern(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, prfKndCd), 
						ExecutorConf.getColDataLimit());				
				
				resultDM.setErrCnt(resultPatternDM.getPatternCount());
				
			}else{
			
				resultPatternDM = vrfcRuleDAO.executePatternQueryOfTargetDbms(con, 
						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), vrfcSqlGeneratorVO.getErrorPattern(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, prfKndCd), 
						ExecutorConf.getColDataLimit()); 	
			}	
						
						
		} catch (Exception e) {
			logger.error(String.format("execute error: OBJ_ID[%s]", ruleRelId)); 
			throw new Exception(e);
		}
		
		finally {
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
		
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
				
		// 대상건수, 분석건수, 오류건수 결과 저장 
		logger.debug(resultDM.toString());
		vrfcRuleDAO.saveProfileResult(logId,  exeStaDttm, endDttm, executorDM, resultDM, resultPatternDM);
		
		//PK 데이터 적재
		if(Utils.null2Blank(executorDM.getPkDataLdYn()).equals("Y") ){
			logger.debug(" ==== profile pkdata load start ==== ");
			vrfcRuleDAO.saveProfilePkData(exeStaDttm, executorDM, targetDbmsDM);
		}
		
		
		return new Boolean(false);
	}
	
	
	/*private boolean getMetaDmnVv(String columnNm) throws SQLException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("\n SELECT A.ITEM_ENM, A.VV_GEN_TYPE, A.DMN_ID, B.DMN_ENM, C.VALID_VAL ");
		query.append("\n   FROM V_DQ_DATAELMT A, V_DQ_CODE_DOMAIN B, V_DQ_DOMAIN_VV C  ");
		query.append("\n  WHERE A.DMN_ID = B.DMN_ID   ");
		query.append("\n    AND B.DMN_ID = C.DMN_ID   ");
		query.append("\n    AND A.ITEM_ENM = ?   ");
		query.append("\n  ORDER BY C.DISP_ORD   ");
		
		logger.debug(query);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionHelper.getMetaConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, columnNm);
			
			rs = pstmt.executeQuery();
			if(rs.equals(null)){
				return false;
			}else{
				return true;
			}
		} finally {
			if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
	public String convertQueryVariable(String query, String columnNm) throws SQLException, Exception {
		if(query == null || "".equals(query)) {
			return null;
		}
		if(query.indexOf("$") < 0) {
			return query;
		}

		String convertQuery = query;
		BusinessRuleDAO brDAO = new BusinessRuleDAO();
		
		if(query.indexOf("$SYS_COND") >= 0) {	// 시스템 변수
			List<Map<String, String>> sVarVal = brDAO.getSystemVarVal(columnNm);
			for(Map<String, String> map : sVarVal) {
				Iterator<String> keys = map.keySet().iterator();
				while(keys.hasNext()) {
					String key = keys.next();
					convertQuery = convertQuery.replaceAll("\\$"+key, map.get(key));
				}
			}
		}
		
		List<Map<String, String>> gVarVal = brDAO.getGlovalVarVal();
		for(Map<String, String> map : gVarVal) {
			Iterator<String> keys = map.keySet().iterator();
			while(keys.hasNext()) {
				String key = keys.next();
				convertQuery = convertQuery.replaceAll("\\$"+key, map.get(key));
			}
		}
		return convertQuery;
	}*/
}
