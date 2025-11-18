package kr.wise.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.ConnectionHelper;
import kr.wise.executor.dao.ProfileDAO;
import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.ProfileResultPatternDM;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

/**
 * 프로파일 날짜검증 테스트<br>
 * @author insomnia
 */
public class ProfileDateTest {
	
	private Logger logger = Logger.getLogger(getClass());
	
	public static void main(String [] args) {
//		if(args == null || args.length != 1) {
//			System.err.println("[전체 실행] 0 ");
//			System.err.println("[지정 실행] WDQ_TGT_DBMS table is DBMS_ID.");
//		} else {
//			ExecutorDM executorDM = new ExecutorDM();
//			executorDM.setExeId(args [0]);
//			executorDM.setExeNm("0".equals(args) ? "스키마 수집 전체실행" : "스키마 수집");
//			executorDM.setExeUserNm("SYSTEM");
//			executorDM.setSchStaDttm(Utils.getCurrDttm("yyyyMMddHHmmss"));
			
			ProfileDateTest testprofile = new ProfileDateTest();
			try {
				testprofile.execute();
			} catch(Exception e) {
				e.printStackTrace();
			}
//		}
	}

	public void execute() throws Exception {
		// 측정대상
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			// 수집대상 DB정보 조회
			TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
			List<TargetDbmsDM> targetList = targetDbmsDAO.selectTargetDbmsList("O1372984897471");
			
			StringBuilder strSQL = new StringBuilder();
/*			strSQL.append("\n SELECT ");
			strSQL.append("\n       ADPT_YYYYMM ");
			strSQL.append("\n      , COUNT(*) AS COUNT ");
			strSQL.append("\n FROM  NHICADM.TBNYJB39 ");
			strSQL.append("\n WHERE 1 = 1 ");
			strSQL.append("\n AND ADPT_YYYYMM IS NOT NULL ");
			//strSQL.append("\n AND ROWNUM < 10000000  ");
			strSQL.append("\n GROUP BY ADPT_YYYYMM ");*/
			
			/*strSQL.append("\n  SELECT UFIRM_CD AS PATTARN ");
			strSQL.append("\n        ,COUNT(*) AS COUNT ");
			strSQL.append("\n   FROM NHICADM.FBT_IWCTRB_PMNT ");
			strSQL.append("\n  GROUP BY UFIRM_CD ");*/
			
			
//			strSQL.append("\n  SELECT A.JUMIN_NO AS \"PATTERN\" ");
//			strSQL.append("\n    , COUNT(*) AS \"COUNT\" ");
//			strSQL.append("\n  FROM NHICADM.TBZZTZ50 A ");
//			strSQL.append("\n  --WHERE A.JUMIN_NO IS NOT NULL ");
//			strSQL.append("\n  GROUP BY A.JUMIN_NO ");
//			strSQL.append("\n  HAVING A.JUMIN_NO IS NOT NULL ");
			
			strSQL.append(" SELECT A.PATTERN ");
			strSQL.append("     , COUNT(*) AS COUNT ");
			strSQL.append(" FROM ( ");
			strSQL.append(" SELECT ");
			strSQL.append(" TRANSLATE( REG_NO, '0123456789', '9999999999') AS \"PATTERN\" ");
			strSQL.append(" FROM  NHICADM.TBAUBH03 ");
			strSQL.append(" WHERE 1 = 1 ");
			strSQL.append(" AND REG_NO IS NOT NULL ");
			strSQL.append(" ) A ");
			strSQL.append(" GROUP BY A.PATTERN ");


			//[유효패턴분석] TBZZTZ50.JUMIN_NO
			//[유효패턴분석] TBZZTZ53.JUMIN_NO


			
			ProfileDAO profileDAO = new ProfileDAO();
			
			
			// 수집 시작 및 수집정보 저장
			if(targetList.size() == 1) {
				for (TargetDbmsDM targetDbmsDM : targetList) {
					logger.debug(targetDbmsDM);
					
/*					try{
					
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						sdf.setLenient(false);
					
						sdf.parse("20130228");
						
					} catch (ParseException pe) {
						logger.error("date format error");
					} catch (Exception e) {
						logger.error(e.getMessage());
					}*/
//					String strReg = "1234567890\\abcdefghi\\ABCDZ  ㄱㅎㅇㄱㅏㅑㅓㅕㅗㅛㅜㅠㅔㄻㅖㅟㅝㅞㅚㅘㅣ가힣~!@#$%^&*()[]{}';:,.<>?`-=_+|\\/";
//					
//					
//					logger.debug(strReg.replaceAll("[0-9]", "9"));
//					logger.debug(strReg.replaceAll("[a-z]", "a"));
//					logger.debug(strReg.replaceAll("[A-Z]", "A"));
//					logger.debug(strReg.replaceAll("[ ]", "B"));
//					logger.debug(strReg.replaceAll("[ㄱ-ㅎㅏ-ㅣ가-힣]", "C"));
					
					// 대상 DBMS Connection 을 얻는다.
					con = ConnectionHelper.getConnection(targetDbmsDM.getJdbc_driver(), targetDbmsDM.getConnect_string(), targetDbmsDM.getDb_user(), targetDbmsDM.getDb_pwd());
					Map<String, String> map = new HashMap<String, String>();
					map.put("999-99-999", "999-99-999");
					map.put("9999999999", "9999999999");
					ProfileResultPatternDM resultPatternDM = profileDAO.executeValidPatternProfileOfTargetDbms(con, strSQL.toString(), ExecutorConf.getErrDataLimit(), map);
//					pstm = con.prepareStatement(strSQL.toString());
//					
//					pstm.setFetchSize(10000);
//					
//					rs = pstm.executeQuery();
//					
//					int totCnt = 0;
					
//					while(rs.next()) {
////						logger.debug(rs.getString("PATTERN"));
////						logger.debug(rs.getString("COUNT"));
//						
//						strReg = rs.getString("PATTERN");
//						totCnt += rs.getInt("COUNT");
//						
//						strReg = strReg.replaceAll("[0-9]", "9");
//						strReg = strReg.replaceAll("[a-z]", "a");
//						strReg = strReg.replaceAll("[A-Z]", "A");
//						strReg = strReg.replaceAll("[ ]", "B");
//						strReg = strReg.replaceAll("[ㄱ-ㅎㅏ-ㅣ가-힣]", "C");
//						
//						if (strReg.equals("9999999999999")) {
//							
//						} else {
//							logger.debug(strReg);
//							logger.debug(rs.getInt("COUNT"));
//						}
//						
//						//break;
//					}
//					logger.debug(String.format("Total : [%d]", totCnt));
					
					
//					ProfileResultPatternDM resultPatternDM = profileDAO.executePatternQueryOfTargetDbms(con, 
//							QueryUtils.convertRowLimitQuery(targetDbmsDM.getDbms_type_cd(), strSQL.toString(), ExecutorConf.getErrDataLimit(), DQConstant.EXE_TYPE_02_CD, "PC00"), 
//							ExecutorConf.getColDataLimit());
					
					logger.debug(String.format("TotCnt[%s]\nErrCnt[%s]\nErrPatListCnt[%d]", resultPatternDM.getTotalCount().toString(), resultPatternDM.getPatternCount().toString(), resultPatternDM.getPatternList().size()));
					
					//profileDAO.executeDatePatternProfileOfTargetDbms(con, strSQL.toString());
					
					logger.debug("end");
					
				}
//				SchemaManager schemaRun = new SchemaManager(targetList.get(0));
//				schemaRun.launch();
			} else if(targetList.size() > 1) {
//				ExecutorService executor = Executors.newFixedThreadPool(ExecutorConf.getThreadCount());
//				for(TargetDbmsDM dm : targetList) {
//					executor.execute(new SchemaManagerRunnableImpl(dm));	// implements Runnable Class
//				}
//				executor.shutdown();
			} else {
				throw new Exception("수집정보가 존재하지 않습니다.");
			}
		} catch(SQLException e) {
			throw e;
		} catch(Exception e) {
			throw e;
		} finally {
			if(rs   != null) try { rs.close(); } catch(Exception igonred) {}
			if(pstm   != null) try { pstm.close(); } catch(Exception igonred) {}
			if(con   != null) try { con.close(); } catch(Exception igonred) {}
		}
	}
	
}
