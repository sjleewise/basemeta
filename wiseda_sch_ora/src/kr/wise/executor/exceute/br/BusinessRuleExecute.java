package kr.wise.executor.exceute.br;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.DQConstant;
import kr.wise.commons.QueryUtils;
import kr.wise.commons.Utils;
import kr.wise.executor.Executor;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.BusinessRuleDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.BusinessRuleErrorDataDM;
import kr.wise.executor.dm.BusinessRuleResultDM;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class BusinessRuleExecute extends Executor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BusinessRuleExecute.class);

	public BusinessRuleExecute(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	@Override
	protected Boolean execute() throws SQLException, Exception {
		BusinessRuleDAO brDAO = new BusinessRuleDAO();
		
		// 업무규칙 쿼리 정보
		Map<String, String> brInfo = brDAO.selectBusinessRuleInfo(executorDM.getShdJobId());
		
		
		// 검증대상 업무규칙 쿼리 정보
		Map<String, String> brInfoTgt = brDAO.selectBusinessRuleInfoTarget(executorDM.getShdJobId());
		if(brInfoTgt.get("TGT_DBMS_ID") != null) {
			logger.debug("검증대상 존재");
			targetBizRuleExec();
			return new Boolean(false);
		}
				
		
		// 측정대상
		Connection con = null;
		BusinessRuleResultDM brReulstDm = null;
		BusinessRuleErrorDataDM brErrorDataDM = null;
		StringBuffer anaQuery = new StringBuffer();
		TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
		TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms(brInfo.get("DBMS_ID"));
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			
			String cntSql    = convertQueryVariable(brInfo.get("CNT_SQL"), brInfo.get("COL_NM"));
//			String errCntSql = convertQueryVariable(brInfo.get("ERR_CNT_SQL"), brInfo.get("COL_NM"));
			String anaSql    = convertQueryVariable(brInfo.get("ANA_SQL"), brInfo.get("COL_NM"));
			String errCntSql = "SELECT COUNT(1) FROM (\n"+anaSql +"\n) aaa ";
			
			//대상건수, 에러건수 union all
			anaQuery.append("\n " + cntSql);
			anaQuery.append("\n UNION ALL ");
			anaQuery.append("\n " + errCntSql);
			
			
//			// 대상건수 SQL실행 (CNT_SQL)
//			if(cntSql != null) {
//				anaCnt = brDAO.executeCountQueryOfTargetDbms(con, cntSql);
//			}
			
			// 에러건수 SQL실행 (ERR_CNT_SQL)
			if(anaQuery != null ) {
				brReulstDm = brDAO.executeCountQueryOfTargetDbms(con, anaQuery.toString());
			}
			
			// 분석 SQL실행 (ANA_SQL)
			if(anaSql != null) {
				brErrorDataDM = brDAO.executeAnalysisQueryOfTargetDbms(con, 
						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), anaSql, ExecutorConf.getErrDataLimit(),DQConstant.EXE_TYPE_03_CD, ""));
			}
			
		} finally {
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		// 대상건수, 분석건수, 오류건수 결과 저장
		brDAO.saveBusinessRuleResult(logId, exeStaDttm, endDttm, executorDM,  brReulstDm, brErrorDataDM);
//		targetBizRuleexecute();
		
		return new Boolean(false);
	}
	
	
	/** 검증대상 포함하여 실행 */
	public void targetBizRuleExec() throws Exception {
		
		BusinessRuleDAO brDAO = new BusinessRuleDAO();
		
		// 업무규칙 쿼리 정보
		Map<String, String> brInfo = brDAO.selectBusinessRuleInfo(executorDM.getShdJobId());
		Map<String, String> brInfoTgt = brDAO.selectBusinessRuleInfoTarget(executorDM.getShdJobId());
		
		List<Map<String, String>> orgList = null;
		List<Map<String, String>> tgtList = null;
		
		// 측정대상
		Connection con = null;
		BusinessRuleResultDM brReulstDm = null;
		BusinessRuleErrorDataDM brErrorDataDM = null;
		StringBuffer anaQuery = new StringBuffer();
		TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
		TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms(brInfo.get("DBMS_ID"));		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			
			String cntSql    = convertQueryVariable(brInfo.get("CNT_SQL"), brInfo.get("COL_NM"));
//			String errCntSql = convertQueryVariable(brInfo.get("ERR_CNT_SQL"), brInfo.get("COL_NM"));
			String anaSql    = convertQueryVariable(brInfo.get("ANA_SQL"), brInfo.get("COL_NM"));
			String errCntSql = "SELECT COUNT(1) FROM (\n"+anaSql +"\n) aaa ";
			
			//대상건수, 에러건수 union all
			anaQuery.append("\n " + cntSql);
			anaQuery.append("\n UNION ALL ");
			anaQuery.append("\n " + errCntSql);
		
			
			if(anaQuery != null ) {
				brReulstDm = brDAO.executeCountQueryOfTargetDbms(con, anaQuery.toString());
			}
			
			// 분석 SQL실행 (ANA_SQL)
			if(anaSql != null) {
//				orgList = brDAO.executeBraQuery(con, 
//						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), anaSql, ExecutorConf.getErrDataLimit(),DQConstant.EXE_TYPE_03_CD, ""));
				//TODO 건수 제한 없슴... 대용량의 경우 처리 방법을 변경해야함 (20만건 이상일 경우?)
				orgList = brDAO.executeBraQuery(con, anaSql);
			}
			
			//검증대상 측정 시작
			targetDbmsDM = targetDbmsDAO.selectTargetDbms(brInfoTgt.get("TGT_DBMS_ID"));
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			
			cntSql    = convertQueryVariable(brInfoTgt.get("TGT_CNT_SQL"), brInfoTgt.get("TGT_COL_NM"));
			anaSql    = convertQueryVariable(brInfoTgt.get("TGT_ANA_SQL"), brInfoTgt.get("TGT_COL_NM"));
//			errCntSql = convertQueryVariable(brInfoTgt.get("TGT_ERR_CNT_SQL"), brInfoTgt.get("TGT_COL_NM"));
//			errCntSql = "SELECT COUNT(1) FROM (\n"+anaSql +"\n)";
			
			//대상건수, 에러건수 union all
//			anaQuery = new StringBuffer();
//			anaQuery.append("\n " + cntSql);
//			anaQuery.append("\n UNION ALL ");
//			anaQuery.append("\n " + errCntSql);
			
			logger.debug("검증대상 anaQuery:\n"+anaQuery.toString());
			// 에러건수 SQL실행 (ERR_CNT_SQL)
			
			BusinessRuleResultDM brTgtResultDm = new BusinessRuleResultDM();
			//분석건수 조회
			if( cntSql != null) {
				brTgtResultDm = brDAO.executeCountQueryOfTargetDbms(con, cntSql);
			}
			
			// 검증대상 분석 SQL실행 (ANA_SQL)
			if(anaSql != null) {
//				tgtList = brDAO.executeBraQuery(con, 
//						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), anaSql, ExecutorConf.getErrDataLimit(),DQConstant.EXE_TYPE_03_CD, ""));
				//TODO 건수 제한 없슴... 대용량의 경우 처리 방법을 변경해야함 (20만건 이상일 경우?) 
				tgtList = brDAO.executeBraQuery(con, anaSql);
			}
			
			//진다대상과 검증대상간 비교...
			
			//INNER JOIN
			if( ("01").equals(brInfoTgt.get("TGT_VRF_JOIN_CD")) ){
				brErrorDataDM = validOrgTgt_InnerJoin(orgList, tgtList, brInfoTgt.get("TGT_KEY"), brInfoTgt.get("TGT_VAL"));
			}
			//LEFT OUTER JOIN
			else if( ("02").equals(brInfoTgt.get("TGT_VRF_JOIN_CD")) ){
				brErrorDataDM = validOrgTgt_LeftOuterJoin(orgList, tgtList, brInfoTgt.get("TGT_KEY"), brInfoTgt.get("TGT_VAL"));
			}
			//FULL OUTER JOIN
			else if( ("03").equals(brInfoTgt.get("TGT_VRF_JOIN_CD")) ){
				brErrorDataDM = validOrgTgt_FullOuterJoin(orgList, tgtList, brInfoTgt.get("TGT_KEY"), brInfoTgt.get("TGT_VAL"));
				brReulstDm.setTotCnt(brReulstDm.getTotCnt() + brTgtResultDm.getTotCnt());
			}
			//EXISTS
			else if( ("04").equals(brInfoTgt.get("TGT_VRF_JOIN_CD")) ){
				brErrorDataDM = validOrgTgt_Exists(orgList, tgtList, brInfoTgt.get("TGT_KEY"), brInfoTgt.get("TGT_VAL"));
				
			}
			
			
			//brErrorDataDM = validOrgTgt(orgList, tgtList, brInfoTgt.get("TGT_KEY"), brInfoTgt.get("TGT_VAL"));
			
			int cnterr = brErrorDataDM.getPatternList().size();
			
			brReulstDm.setErrCnt((long)cnterr);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		// 대상건수, 분석건수, 오류건수 결과 저장
		brDAO.saveBusinessRuleResult(logId, exeStaDttm, endDttm, executorDM, brReulstDm, brErrorDataDM);
	}
	
	/** 진다대상과 검증대상간 비교... 
	 * @param comval 
	 * @param key 
	 * @return */
	private BusinessRuleErrorDataDM validOrgTgt_InnerJoin(List<Map<String, String>> orgList,
			List<Map<String, String>> tgtList, String key, String comval) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		List<String> columnNames = new ArrayList<String>();
		
		//비교대상 키값 array
		String[] keys = key.split(",");
		int cntkey = keys.length;
		//비교대상 키에 해당하는 값 비교컬럼
		String[] comvals = comval.split(",");
		int cntval = comvals.length;
		
//		columnNames = Arrays.asList(keys);
//		columnNames.addAll(Arrays.asList(comvals));
		
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i]);
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i]);
		}
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i] + "(TGT)");
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i] + "(TGT)");
		}
		logger.debug("colunm name : "+ columnNames.toString());
		
		for (Map<String, String> orgmap : orgList) {
			//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String orgkey = joinStringMap(orgmap, keys);
			String orgval = joinStringMap(orgmap, comvals);
			
			//검증대상을 for문 처리
			for (Map<String, String> tgtmap : tgtList) {
				//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String tgtkey = joinStringMap(tgtmap, keys);
				String tgtval = joinStringMap(tgtmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				//INNER JOIN
				if (orgkey.equalsIgnoreCase(tgtkey)) {
					//진단대상과 검증대상 비교값이 틀린 경우 업무규칙 에러로 처리한다.
					if (!orgval.equalsIgnoreCase(tgtval)) {
						logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
						Map<String,String> resultmap = new HashMap<String, String>();
						resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
						
						result.add(resultmap);
					}
					break;
				}
				
			}
		}
		
		
		BusinessRuleErrorDataDM businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
		businessRuleErrorDataDM.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
		businessRuleErrorDataDM.setPatternList(result);
		
		return businessRuleErrorDataDM;
		
	}

	/** 진다대상과 검증대상간 비교... 
	 * @param comval 
	 * @param key 
	 * @return */
	private BusinessRuleErrorDataDM validOrgTgt_LeftOuterJoin(List<Map<String, String>> orgList,
			List<Map<String, String>> tgtList, String key, String comval) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		List<String> columnNames = new ArrayList<String>();
		
		//비교대상 키값 array
		String[] keys = key.split(",");
		int cntkey = keys.length;
		//비교대상 키에 해당하는 값 비교컬럼
		String[] comvals = comval.split(",");
		int cntval = comvals.length;
		
//		columnNames = Arrays.asList(keys);
//		columnNames.addAll(Arrays.asList(comvals));
		
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i]);
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i]);
		}
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i] + "(TGT)");
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i] + "(TGT)");
		}
		logger.debug("colunm name : "+ columnNames.toString());
		
		String noOrgNotMatchYn = "N";
		
		for (Map<String, String> orgmap : orgList) {
			//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String orgkey = joinStringMap(orgmap, keys);
			String orgval = joinStringMap(orgmap, comvals);
			
			//검증대상을 for문 처리
			for (Map<String, String> tgtmap : tgtList) {
				//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String tgtkey = joinStringMap(tgtmap, keys);
				String tgtval = joinStringMap(tgtmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				//INNER JOIN
				if (orgkey.equalsIgnoreCase(tgtkey)) {
					//진단대상과 검증대상 비교값이 틀린 경우 업무규칙 에러로 처리한다.
					if (!orgval.equalsIgnoreCase(tgtval)) {
						logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
						Map<String,String> resultmap = new HashMap<String, String>();
						resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
						
						result.add(resultmap);
					}
					noOrgNotMatchYn = "Y";
					break;
				}else {
					noOrgNotMatchYn = "N";
				}
			}
			
			if(noOrgNotMatchYn.equals("N")){
//				logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
				Map<String,String> resultmap = new HashMap<String, String>();
				Map<String,String> tgtmap = new HashMap<String, String>();
				resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
				result.add(resultmap);
			}
			
		}
		
		
		BusinessRuleErrorDataDM businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
		businessRuleErrorDataDM.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
		businessRuleErrorDataDM.setPatternList(result);
		
		return businessRuleErrorDataDM;
		
	}
	
	/** 진다대상과 검증대상간 비교... 
	 * @param comval 
	 * @param key 
	 * @return */
	private BusinessRuleErrorDataDM validOrgTgt_Exists(List<Map<String, String>> orgList,
			List<Map<String, String>> tgtList, String key, String comval) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		List<String> columnNames = new ArrayList<String>();
		
		//비교대상 키값 array
		String[] keys = key.split(",");
		int cntkey = keys.length;
		//비교대상 키에 해당하는 값 비교컬럼
		String[] comvals = comval.split(",");
		int cntval = comvals.length;
		
//		columnNames = Arrays.asList(keys);
//		columnNames.addAll(Arrays.asList(comvals));
		
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i]);
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i]);
		}
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i] + "(TGT)");
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i] + "(TGT)");
		}
		logger.debug("colunm name : "+ columnNames.toString());
		
		String noOrgNotMatchYn = "N";
		
		for (Map<String, String> orgmap : orgList) {
			//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String orgkey = joinStringMap(orgmap, keys);
			String orgval = joinStringMap(orgmap, comvals);
			
			//검증대상을 for문 처리
			for (Map<String, String> tgtmap : tgtList) {
				//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String tgtkey = joinStringMap(tgtmap, keys);
				String tgtval = joinStringMap(tgtmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				//INNER JOIN
				if (orgkey.equalsIgnoreCase(tgtkey)) {
//					//진단대상과 검증대상 비교값이 틀린 경우 업무규칙 에러로 처리한다.
//					if (!orgval.equalsIgnoreCase(tgtval)) {
//						logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
//						Map<String,String> resultmap = new HashMap<String, String>();
//						resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
//						
//						result.add(resultmap);
//					}
					noOrgNotMatchYn = "Y";
					break;
				}else {
					noOrgNotMatchYn = "N";
				}
			}
			
			if(noOrgNotMatchYn.equals("N")){
//				logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
				Map<String,String> resultmap = new HashMap<String, String>();
				Map<String,String> tgtmap = new HashMap<String, String>();
				resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
				result.add(resultmap);
			}
				
		}
		
		
		BusinessRuleErrorDataDM businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
		businessRuleErrorDataDM.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
		businessRuleErrorDataDM.setPatternList(result);
		
		return businessRuleErrorDataDM;
		
	}
	
	
	/** 진다대상과 검증대상간 비교... 
	 * @param comval 
	 * @param key 
	 * @return */
	private BusinessRuleErrorDataDM validOrgTgt_FullOuterJoin(List<Map<String, String>> orgList,
			List<Map<String, String>> tgtList, String key, String comval) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		List<String> columnNames = new ArrayList<String>();
		
		//비교대상 키값 array
		String[] keys = key.split(",");
		int cntkey = keys.length;
		//비교대상 키에 해당하는 값 비교컬럼
		String[] comvals = comval.split(",");
		int cntval = comvals.length;
		
//		columnNames = Arrays.asList(keys);
//		columnNames.addAll(Arrays.asList(comvals));
		
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i]);
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i]);
		}
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i] + "(TGT)");
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i] + "(TGT)");
		}
		logger.debug("colunm name : "+ columnNames.toString());
		
		
		String noOrgNotMatchYn = "N";
		String noTgtNotMatchYn = "N";
		
		for (Map<String, String> orgmap : orgList) {
			//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String orgkey = joinStringMap(orgmap, keys);
			String orgval = joinStringMap(orgmap, comvals);
			
			//검증대상을 for문 처리
			for (Map<String, String> tgtmap : tgtList) {
				//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String tgtkey = joinStringMap(tgtmap, keys);
				String tgtval = joinStringMap(tgtmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				//INNER JOIN
				if (orgkey.equalsIgnoreCase(tgtkey)) {
					//진단대상과 검증대상 비교값이 틀린 경우 업무규칙 에러로 처리한다.
					if (!orgval.equalsIgnoreCase(tgtval)) {
//						logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
						Map<String,String> resultmap = new HashMap<String, String>();
						resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
						
						result.add(resultmap);
					}
					noOrgNotMatchYn = "Y";
					break;
				}else {
					noOrgNotMatchYn = "N";
				}
			}
			
			if(noOrgNotMatchYn.equals("N")){
//				logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
				Map<String,String> resultmap = new HashMap<String, String>();
				Map<String,String> tgtmap = new HashMap<String, String>();
				resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
				result.add(resultmap);
			}
			
		}
		
		//검증대상을 for문 처리
		for (Map<String, String> tgtmap : tgtList) {
			//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String tgtkey = joinStringMap(tgtmap, keys);
			String tgtval = joinStringMap(tgtmap, comvals);
			for (Map<String, String> orgmap : orgList) {
				//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String orgkey = joinStringMap(orgmap, keys);
				String orgval = joinStringMap(orgmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				if (orgkey.equalsIgnoreCase(tgtkey)) {
					noTgtNotMatchYn = "Y";
					break;
				}else {
					noTgtNotMatchYn = "N";
				}
			}
			
			if(noTgtNotMatchYn.equals("N")){
//				logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
				Map<String,String> resultmap = new HashMap<String, String>();
				Map<String,String> orgmap = new HashMap<String, String>();
				resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
				result.add(resultmap);
			}
			
		}
		
		BusinessRuleErrorDataDM businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
		businessRuleErrorDataDM.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
		businessRuleErrorDataDM.setPatternList(result);
		
		return businessRuleErrorDataDM;
		
	}
	/** 진다대상과 검증대상간 비교... 
	 * @param comval 
	 * @param key 
	 * @return */
	private BusinessRuleErrorDataDM validOrgTgt(List<Map<String, String>> orgList,
			List<Map<String, String>> tgtList, String key, String comval) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		List<String> columnNames = new ArrayList<String>();
		
		//비교대상 키값 array
		String[] keys = key.split(",");
		int cntkey = keys.length;
		//비교대상 키에 해당하는 값 비교컬럼
		String[] comvals = comval.split(",");
		int cntval = comvals.length;
		
//		columnNames = Arrays.asList(keys);
//		columnNames.addAll(Arrays.asList(comvals));
		
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i]);
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i]);
		}
		for(int i=0;i<cntkey;i++) {
			columnNames.add(keys[i] + "(TGT)");
		}
		for(int i=0;i<cntval;i++) {
			columnNames.add(comvals[i] + "(TGT)");
		}
		logger.debug("colunm name : "+ columnNames.toString());
		
		for (Map<String, String> orgmap : orgList) {
			//진단대상 key값과 비교값을 "|" 문자로 각각 조인한다.
			String orgkey = joinStringMap(orgmap, keys);
			String orgval = joinStringMap(orgmap, comvals);
			
			//검증대상을 for문 처리
			for (Map<String, String> tgtmap : tgtList) {
				//검즈대상 key값과 비교값을 "|" 문자로 각각 조인한다.
				String tgtkey = joinStringMap(tgtmap, keys);
				String tgtval = joinStringMap(tgtmap, comvals);
				
				//진단대상과 검증대상 키 값이 동일한 경우 발생...
				//INNER JOIN
				if (orgkey.equalsIgnoreCase(tgtkey)) {
					//진단대상과 검증대상 비교값이 틀린 경우 업무규칙 에러로 처리한다.
					if (!orgval.equalsIgnoreCase(tgtval)) {
						logger.debug("\nerror data : "+orgkey + ":"+orgval + ":" + tgtkey + ":" + tgtval);
						Map<String,String> resultmap = new HashMap<String, String>();
						resultSetMap(orgmap, tgtmap, resultmap, keys, comvals);
						
						result.add(resultmap);
					}
					break;
				}
				
			}
		}
		
		
		BusinessRuleErrorDataDM businessRuleErrorDataDM = new BusinessRuleErrorDataDM();
		businessRuleErrorDataDM.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
		businessRuleErrorDataDM.setPatternList(result);
		
		return businessRuleErrorDataDM;
		
	}
	
	/** 오류발생 결과를 resultmap에 셋팅 : 진단대상 map내용과 검증대상 map내용을 합쳐서 결과 리턴  */
	private void resultSetMap(Map orgmap, Map tgtmap, Map resultmap,  String[] keys, String[] vals) {
		
		int cntkey = keys.length;
		int cntval = vals.length;
		
		for (int i=0; i<cntkey; i++) {
			resultmap.put(keys[i], orgmap.get(keys[i]));
		}
		for (int i=0; i<cntval; i++) {
			resultmap.put(vals[i], orgmap.get(vals[i]));
		}
		for (int i=0; i<cntkey; i++) {
			resultmap.put(keys[i]+"(TGT)", tgtmap.get(keys[i]));
		}
		for (int i=0; i<cntval; i++) {
			resultmap.put(vals[i]+"(TGT)", tgtmap.get(vals[i]));
		}
		
	}
	
	/** map의 내용을 조인케릭터로 합쳐서 반환 */
	private String joinStringMap(Map map, String[] keys) {
		
		int cntkey = keys.length;
		String result = "";
		
		for (int i=0; i<cntkey; i++) {
			result += map.get(keys[i]) + "|";
		}
		
		return result;
	}

	/** 타겟 검증 DB 호출 */
	public void targetBizRuleexecute() throws Exception {
		BusinessRuleDAO brDAO = new BusinessRuleDAO();
		
		// 업무규칙 쿼리 정보
		Map<String, String> brInfo = brDAO.selectBusinessRuleInfoTarget(executorDM.getShdJobId());
		if(brInfo.get("TGT_DBMS_ID") == null) {
			logger.debug("검증대상 없슴");
			return;
		}
		
		// 측정대상
		Connection con = null;
		BusinessRuleResultDM brReulstDm = null;
		BusinessRuleErrorDataDM brErrorDataDM = null;
		StringBuffer anaQuery = new StringBuffer();
		TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
		TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms(brInfo.get("TGT_DBMS_ID"));
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
			
			String cntSql    = convertQueryVariable(brInfo.get("TGT_CNT_SQL"), brInfo.get("TGT_COL_NM"));
			String errCntSql = convertQueryVariable(brInfo.get("TGT_ERR_CNT_SQL"), brInfo.get("TGT_COL_NM"));
			String anaSql    = convertQueryVariable(brInfo.get("TGT_ANA_SQL"), brInfo.get("TGT_COL_NM"));
			
			//대상건수, 에러건수 union all
			anaQuery.append("\n " + cntSql);
			anaQuery.append("\n UNION ALL ");
			anaQuery.append("\n " + errCntSql);
			
			logger.debug("검증대상 anaQuery:\n"+anaQuery.toString());
//			// 대상건수 SQL실행 (CNT_SQL)
//			if(cntSql != null) {
//				anaCnt = brDAO.executeCountQueryOfTargetDbms(con, cntSql);
//			}
			
			// 에러건수 SQL실행 (ERR_CNT_SQL)
			if(errCntSql != null && cntSql != null) {
				brReulstDm = brDAO.executeCountQueryOfTargetDbms(con, anaQuery.toString());
			}
			
			// 분석 SQL실행 (ANA_SQL)
			if(anaSql != null) {
				brErrorDataDM = brDAO.executeAnalysisQueryOfTargetDbms(con, 
						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), anaSql, ExecutorConf.getErrDataLimit(),DQConstant.EXE_TYPE_03_CD, ""));
			}
			
		} finally {
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		// 대상건수, 분석건수, 오류건수 결과 저장
		logger.debug("검증대상 완료시간:"+endDttm);
		brDAO.saveBizRullResultTgt(logId, executorDM.getShdJobId(), executorDM.getAnaDgr(), exeStaDttm, endDttm, executorDM.getShdStaDtm(), brReulstDm.getTotCnt(), brReulstDm.getErrCnt(), brErrorDataDM);
		
	}
	
	public String convertQueryVariable(String query, String columnNm) throws SQLException, Exception {
		logger.debug(query);
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
		logger.debug(convertQuery);
		return convertQuery;
	}

}
