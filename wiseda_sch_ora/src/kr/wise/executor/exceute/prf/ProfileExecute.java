package kr.wise.executor.exceute.prf;

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
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.executor.Executor;
import kr.wise.executor.ExecutorConf;
import kr.wise.executor.dao.BusinessRuleDAO;
import kr.wise.executor.dao.ProfileDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.dm.ProfileResultDM;
import kr.wise.executor.dm.ProfileResultPatternDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

public class ProfileExecute extends Executor {
	private static final Logger logger = Logger.getLogger(ProfileExecute.class);
	
	public ProfileExecute(String logId, ExecutorDM executorDM){
		super(logId, executorDM);
	}
	
	protected Boolean execute() throws SQLException, Exception {

		ProfileDAO profileDAO = new ProfileDAO();
		
		//SQL 제너레이터
		SqlGeneratorVO sqlGeneratorVO = profileDAO.sqlGenerator(executorDM.getShdJobId());
		
		String prfId = sqlGeneratorVO.getPrfId();
		String prfKndCd = sqlGeneratorVO.getPrfKndCd();
		String dbConnTrgId = sqlGeneratorVO.getDbConnTrgId();
		
		logger.debug(sqlGeneratorVO.getTotalCount());
		logger.debug(sqlGeneratorVO.getErrorCount());
		logger.debug(sqlGeneratorVO.getErrorData());
		logger.debug(sqlGeneratorVO.getErrorPattern());
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
			if("PC01".equals(prfKndCd)){
				anaQuery.append("\n " + sqlGeneratorVO.getTotalCount());
			}else{
				anaQuery.append("\n " + sqlGeneratorVO.getTotalCount());
				anaQuery.append("\n UNION ALL "                   );
				anaQuery.append("\n " + sqlGeneratorVO.getErrorCount());
				anaQuery.append("\n ORDER BY 1 DESC" );  //DB2는 숫자가 작은 값이 먼저 나오기 떄문에 역으로 정렬 20210912
			}
			
			// 날짜형식분석, 패턴분석이 아닐경우 분석선수, 오류건수 SQL 실행
//			if( !"PC03".equals(prfKndCd) && !"PC05".equals(prfKndCd)) {
			if( !"PC05".equals(prfKndCd)) {
				logger.debug("건수sql :" +  anaQuery.toString());
				resultDM = profileDAO.executeAnalysisQueryOfTargetDbms(con, anaQuery.toString());
			}
			
			//컬럼분석
			if("PC01".equals(prfKndCd)){
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
//				logger.debug("!!!!!!!!!"+sqlGeneratorVO.getNullCountSql()+"!!!!!!!!!!");
				//카디널리티 SQL 실행
				if(null != sqlGeneratorVO.getErrorPattern()){
					resultPatternDM = profileDAO.executePatternQueryOfTargetDbms(con, QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), sqlGeneratorVO.getErrorPattern(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, prfKndCd), 
							ExecutorConf.getColDataLimit());
				}
				
				//NULL분석 SQL 실행
				if(null != sqlGeneratorVO.getNullCountSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getNullCountSql());
					while(rs.next()){
						resultDM.setNullCnt( rs.getBigDecimal(1) );
					}
					
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//SPACE 건수 분석 SQL 실행
				if(null != sqlGeneratorVO.getSpaceCountSql()){
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getSpaceCountSql());
					while(rs.next()){
						resultDM.setSpaceCnt( rs.getBigDecimal(1) );
					}
					
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//최대최소값분석 SQL 실행
				if(null != sqlGeneratorVO.getMinMaxSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getMinMaxSql());
					while(rs.next()){
						resultDM.setMinVal1(rs.getString("MINVAL1"));
						resultDM.setMinVal2(rs.getString("MINVAL2"));
						resultDM.setMinVal3(rs.getString("MINVAL3"));						
						resultDM.setMaxVal1(rs.getString("MAXVAL1"));
						resultDM.setMaxVal2(rs.getString("MAXVAL2"));
						resultDM.setMaxVal3(rs.getString("MAXVAL3"));
					}
					
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				
				//길이분석 SQL 실행
				if(null != sqlGeneratorVO.getMinMaxLenSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getMinMaxLenSql());
					while(rs.next()){
						resultDM.setMinLen(rs.getBigDecimal("MINLEN"));
						resultDM.setMaxLen(rs.getBigDecimal("MAXLEN"));  
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//패턴분석
				if(null != sqlGeneratorVO.getUserPatSql()) {
					int limitcnt = 100;
					Map<String, String> validPat = new HashMap<String, String>();
					resultPatternDM1 = profileDAO.executeValidPatternProfileOfTargetDbms(con, sqlGeneratorVO.getUserPatSql(), sqlGeneratorVO.getTotalCount(), limitcnt, validPat);
				}
				
				//일자여부 
				if(null != sqlGeneratorVO.getDateYnSql()){
					
					logger.debug("\n "+ sqlGeneratorVO.getDateYnSql());					
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getDateYnSql());
					while(rs.next()){
						resultDM.setDateYn(rs.getString("DATE_YN"));						 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//전화번호여부 
				if(null != sqlGeneratorVO.getTelYnSql()){
					
					logger.debug("\n "+ sqlGeneratorVO.getTelYnSql());
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getTelYnSql());
					while(rs.next()){
						resultDM.setTelYn(rs.getString("TEL_YN"));						 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//공백율
				if(null != sqlGeneratorVO.getSpaceRtSql()){
					
					logger.debug("\n "+ sqlGeneratorVO.getSpaceRtSql());
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getSpaceRtSql());
					while(rs.next()){
						resultDM.setSpaceRt(rs.getString("SPACE_RT"));		 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//엔터값여부 
				if(null != sqlGeneratorVO.getCrlfYnSql()){
					
					logger.debug("\n "+ sqlGeneratorVO.getCrlfYnSql());
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getCrlfYnSql());
					while(rs.next()){
						resultDM.setCrlfYn(rs.getString("CRLF_YN"));		 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//영문여부 
				if(null != sqlGeneratorVO.getAlphaYnSql()){
					
					logger.debug("\n "+ sqlGeneratorVO.getAlphaYnSql());
					
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getAlphaYnSql());
					while(rs.next()){
						resultDM.setAlphaYn(rs.getString("ALPHA_YN"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//데이터포멧
				if(null != sqlGeneratorVO.getDataFmtSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getDataFmtSql());
					while(rs.next()){
						resultDM.setDataFmt(rs.getString("DATA_FMT"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//숫자여부
				if(null != sqlGeneratorVO.getNumYnSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getNumYnSql());
					while(rs.next()){
						resultDM.setNumYn(rs.getString("NUM_YN"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//백단위율
				if(null != sqlGeneratorVO.getHundRtSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getHundRtSql());
					while(rs.next()){
						resultDM.setHundRt(rs.getString("HUND_RT"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//건수율
				if(null != sqlGeneratorVO.getCntRtSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getCntRtSql());
					while(rs.next()){
						resultDM.setCntRt(rs.getString("CNT_RT"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//표준편차
				if(null != sqlGeneratorVO.getStddevValSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getStddevValSql());
					while(rs.next()){
						resultDM.setStddevVal(rs.getString("STDDEV_VAL"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//분산
				if(null != sqlGeneratorVO.getVarianceValSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getVarianceValSql());
					while(rs.next()){
						resultDM.setVarianceVal(rs.getString("VARIANCE_VAL"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//평균
				if(null != sqlGeneratorVO.getAvgValSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getAvgValSql());
					while(rs.next()){
						resultDM.setAvgVal(rs.getString("AVG_VAL"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//유일값수
				if(null != sqlGeneratorVO.getUnqCntSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getUnqCntSql());
					while(rs.next()){
						resultDM.setUnqCnt(rs.getString("UNQ_CNT"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//최소빈도값
				if(null != sqlGeneratorVO.getMinCntValSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getMinCntValSql());
					while(rs.next()){
						resultDM.setMinCntVal(rs.getString("MIN_CNT_VAL"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
				
				//최대빈도값
				if(null != sqlGeneratorVO.getMaxCntValSql()){
					rs = profileDAO.executeQueryOfTargetDbms(con, pstmt, rs, sqlGeneratorVO.getMaxCntValSql());
					while(rs.next()){
						resultDM.setMaxCntVal(rs.getString("MAX_CNT_VAL"));	 	 				 
					}
					if(rs    != null) try { rs.close(); } catch(Exception igonred) {}
					if(pstmt != null) try { pstmt.close(); } catch(Exception igonred) {}
				}
			
			}
			//날짜 분석
			else if ("PC03".equals(prfKndCd))  {
				logger.debug(String.format("=== start PC03 OBJ_ID [%s]===", prfId ));
				//날짜포맷의 경우 날짜 포맷에 해당하는 변수 셋팅
				String dateFormat = null;
				String userDateFrm = null;
				//날짜포멧 정보 조회
				Map<String, String> tmp = profileDAO.selectDateType( prfId );
				if(tmp.isEmpty()) {
					throw new Exception("날짜 포맷 정보가 없습니다.");
				} else {
//					userDateFrm =  tmp.get("USER_DATE_FRM"); //사용자 정의 날짜 텍스트
//					String tmpCd = tmp.get("DATE_FRM_CD");
					
					String tmpCd = tmp.get("COMM_DTL_CD_NM");
					dateFormat = tmpCd;
					
//					if("01".equals(tmpCd)) {
//						dateFormat = "yyyyMMddHHmmss";
//					} else if ("02".equals(tmpCd)) {
//						dateFormat = "yyyyMMdd";
//					} else if ( "03".equals(tmpCd)) {
//						dateFormat = "yyyyMM";
//					} else if ("04".equals(tmpCd)) {
//						dateFormat = "yyyy";
//					} else if ("06".equals(tmpCd)) {
//						dateFormat = "yyyy-MM-dd kkmm";
//					} else if ("07".equals(tmpCd)) {
//						dateFormat = "yyyy-MM-dd";
//					} else if ("08".equals(tmpCd)) {
//						dateFormat = "MM";
//					} else if ("09".equals(tmpCd)) {
//						dateFormat = "yyyy-MM";
//					} else if ("10".equals(tmpCd)) {
//						dateFormat = "yyyyMMddHH";
//					} else if ("11".equals(tmpCd)) {
//						dateFormat = "yyyyMMddHHmm";
//					} else if ("12".equals(tmpCd)) {
//						dateFormat = "HH";
//					} else if ("13".equals(tmpCd)) {
//						dateFormat = "HHmm";
//					} else if ("14".equals(tmpCd)) {
//						dateFormat = "HH:mm";
//					} else if ("15".equals(tmpCd)) {
//						dateFormat = "HHmmss";
//					} if ("18".equals(tmpCd)) {
//						dateFormat = "yyyy-MM-dd HHmmss";
//					} else {
//						throw new Exception("날짜 포맷 정보가 없습니다.");
//					}
				}
				
				resultPatternDM = profileDAO.executeDatePatternProfileOfTargetDbms(con, sqlGeneratorVO.getDatePatSql(), ExecutorConf.getErrDataLimit(), dateFormat);
				resultDM = new ProfileResultDM();
				resultDM.setTotCnt(resultPatternDM.getTotalCount());
				resultDM.setErrCnt(resultPatternDM.getPatternCount());
				// 패턴별건수 SQL실행 (PAT_SQL)
//				resultPatternDM = profileDAO.executePatternQueryOfTargetDbms(con, 
//						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), sqlGeneratorVO.getErrorPattern(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, prfKndCd), 
//						ExecutorConf.getColDataLimit());
			}
			//유효패턴 분석
			else if ("PC05".equals(prfKndCd)) { 
				logger.debug(String.format("=== start PC05 OBJ_ID [%s]===", prfId));
				
				//유효패턴 정보
				Map<String, String> validPat = null;			
				validPat = profileDAO.selectValidPattern(prfId);
				if(validPat.isEmpty()) {
					logger.debug("===유효패턴이 없으므로 패턴 조사를 하도록 함 ===");
				} else {
					//유효패턴 리스트
					logger.debug("=== validPatternInfo ===");
					for( String key : validPat.keySet() ){
			            logger.debug( String.format("\nkey[ %s], value[%s]", key, validPat.get(key)) );
			        }
				}
				
				int limitcnt;
				if (validPat.isEmpty()) {
					limitcnt = ExecutorConf.getColDataLimit();
				} else {
					limitcnt = ExecutorConf.getErrDataLimit();
				}
				resultPatternDM = profileDAO.executeValidPatternProfileOfTargetDbms(con, sqlGeneratorVO.getUserPatSql(), sqlGeneratorVO.getTotalCount(), limitcnt, validPat);
				String A = sqlGeneratorVO.getTotalCount();
				
				resultDM = new ProfileResultDM();
				resultDM.setTotCnt(resultPatternDM.getTotalCount());
				resultDM.setErrCnt(resultPatternDM.getPatternCount());
			}
			//컬럼분석, 날짜형식분석, 패턴분석 제외
			else{
				logger.debug("=== start "+prfKndCd+" ===");
				// 패턴별건수 SQL실행 (PAT_SQL)
				resultPatternDM = profileDAO.executePatternQueryOfTargetDbms(con, 
						QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), sqlGeneratorVO.getErrorPattern(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, prfKndCd), 
						ExecutorConf.getColDataLimit());
			}
			
		} catch (Exception e) {
			logger.error(String.format("execute error: OBJ_ID[%s]", prfId));
			throw new Exception(e);
		}
		
		finally {
			if(con != null) try { con.close(); } catch(Exception igonred) {}
		}
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		
		//패턴 데이터 적재
		if("PC01".equals(prfKndCd) && null != sqlGeneratorVO.getUserPatSql()) {
			logger.debug(" ==== profile pattern load start ==== ");
			profileDAO.saveProfilePattern(exeStaDttm, executorDM, resultPatternDM1);
		}
		// 대상건수, 분석건수, 오류건수 결과 저장
//		logger.debug("=== start log save ===");
		logger.debug(resultDM.toString());
		profileDAO.saveProfileResult(logId,  exeStaDttm, endDttm, executorDM, resultDM, resultPatternDM);
		
		//PK 데이터 적재
		if(Utils.null2Blank(executorDM.getPkDataLdYn()).equals("Y") ){
			logger.debug(" ==== profile pkdata load start ==== ");
			profileDAO.saveProfilePkData(exeStaDttm, executorDM, targetDbmsDM);
		}
		
		
		return new Boolean(false);
	}
	
	
	private boolean getMetaDmnVv(String columnNm) throws SQLException, Exception {
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
	}
}
