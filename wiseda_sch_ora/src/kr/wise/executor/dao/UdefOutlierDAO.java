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

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.commons.util.UtilString;
import kr.wise.executor.dm.ExecutorDM;
import kr.wise.executor.exceute.ai.PythonConf;

import org.apache.log4j.Logger;

import com.opencsv.*;
//import au.com.bytecode.opencsv.*;

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
public class UdefOutlierDAO {
	
	private static final Logger logger = Logger.getLogger(OutlierDAO.class);

	/** @param shdJobId
	/** @return insomnia 
	 * @throws Exception */
	public WadOtlDtcVo getOutlierVo(String otlDtcId) throws Exception {
		StringBuffer strSQL = new StringBuffer();
		
		strSQL.append("\n SELECT A.OTL_DTC_ID, A.DASE_ID, A.OTL_ALG_ID ");
		strSQL.append("\n      , B.ALG_ID, B.ALG_LNM, B.ALG_PNM, B.ALG_TYP_CD ");
		strSQL.append("\n      , DS.DB_SCH_ID, DS.DB_TBL_NM ");
		strSQL.append("\n   FROM WAD_OTL_DTC A ");
		strSQL.append("\n   JOIN WAA_ALG B ");
		strSQL.append("\n 	ON A.OTL_ALG_ID = B.ALG_ID ");
		strSQL.append("\n   JOIN WAD_DATA_SET DS ");
		strSQL.append("\n     ON A.DASE_ID = DS.DASE_ID ");
		strSQL.append("\n WHERE A.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n   AND B.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n   AND DS.REG_TYP_CD IN ('C', 'U') ");
		strSQL.append("\n   AND A.OTL_DTC_ID = ? ");

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
		strSQL.append("\n   FROM WAD_OTL_VAL V ");
		strSQL.append("\n   JOIN WAD_ANA_VAR C ");
		strSQL.append("\n 	ON V.ANL_VAR_ID = C.ANL_VAR_ID ");
		strSQL.append("\n  WHERE V.OTL_DTC_ID = ? ");
		strSQL.append("\n    AND V.REG_TYP_CD IN ('C', 'U')  ");
		strSQL.append("\n    AND C.REG_TYP_CD IN ('C', 'U')  ");
		strSQL.append("\n  ORDER BY V.VAR_SNO  ");
		
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

	/** @param logId
	/** @param exeStaDttm
	/** @param executorDM
	/** @param uodao insomnia 
	 * @throws Exception */
	public void saveResult(String logId, String exeStaDttm, ExecutorDM executorDM) throws Exception {
		//결과파일을 로드하여 저장한다.
		logger.debug("*****Start Outlier Result["+ executorDM.getShdJobId() +"]*****");
		
		String udfOtlDtcId = UtilString.null2Blank(executorDM.getShdJobId() );
		
		Connection con = null; 
		PreparedStatement pstmt = null;
		StringBuffer strSQL = new StringBuffer();
		
		String endDttm = Utils.getCurrDttm("yyyyMMddHHmmss");	// 종료일시
		String anaDgr = UtilString.null2Blank(executorDM.getAnaDgr()); 
		
		String csvFileNm = PythonConf.getPYTHON_UDFOTL_PATH() + udfOtlDtcId + ".csv";	
		
		FileInputStream csvFile = new FileInputStream(csvFileNm);  
		InputStreamReader readFile = new InputStreamReader(csvFile, "EUC-KR");  
		
		CSVReader reader = new CSVReader(readFile); 
		//CSVReader reader = new CSVReader(new FileReader(csvFileNm));
		
		try{
			con = ConnectionHelper.getDAConnection();
			con.setAutoCommit(false);
			
			logger.debug("*****Delete Outlier Result["+ udfOtlDtcId +"]*****");
			//기존 결과를 삭제한다. 			
			strSQL.append("\n DELETE FROM WAD_UOD_RESULT  ");
			strSQL.append("\n  WHERE UDF_OTL_DTC_ID = ?   ");
			
			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, udfOtlDtcId );
			pstmt.execute();
			
			pstmt.close(); pstmt = null;
			
			strSQL.setLength(0);			
			strSQL.append("\n DELETE FROM WAD_UOD_ERR_DATA  ");
			strSQL.append("\n  WHERE UDF_OTL_DTC_ID = ?     ");

			pstmt = con.prepareStatement(strSQL.toString());
			pstmt.setString(1, udfOtlDtcId );
			pstmt.execute();
			
			pstmt.close(); pstmt = null;
			
			String creCompId = "";
			
			ResultSet rsCompId = getCreCompId(con , udfOtlDtcId); 
			
			if(rsCompId.next()){
				
				creCompId = rsCompId.getString("CRE_COMP_ID");
			}
			
			//WAD_UOD_RESULT INSERT 
			pstmt = con.prepareStatement(getWadUodResultSql());
			
			int paramIdx = 1;
			
			pstmt.setString(paramIdx++, udfOtlDtcId );
			pstmt.setString(paramIdx++, creCompId );
			pstmt.setString(paramIdx++, exeStaDttm );
			pstmt.setString(paramIdx++, endDttm );
			pstmt.setString(paramIdx++, anaDgr );
			pstmt.setString(paramIdx++, null );
			pstmt.setString(paramIdx++, null );
			
			pstmt.executeUpdate();
			
			pstmt.close(); pstmt = null;
						
			String[] line = null;
			int rowCnt = 0;
			int errDataSno = 0;
			
			//WAD_UOD_ERR_DATA INSERT 
			pstmt = con.prepareStatement(getWadUodErrDataSql());
			
			logger.debug("sql:" + getWadUodErrDataSql());
			
			PreparedStatement pstmtUpd = null;
			
			while ((line = reader.readNext()) != null) {  
        		            		
				for (int i = 0; i < line.length; i++){
    				
					String colVal = UtilString.null2Blank(line[i]);
					
					if(i == 0){
						
						pstmt.clearParameters();
						
						paramIdx = 1;
						
						pstmt.setString(paramIdx++, udfOtlDtcId);						
						pstmt.setString(paramIdx++, creCompId);
						pstmt.setString(paramIdx++, exeStaDttm);
						pstmt.setInt(paramIdx++, errDataSno);
						pstmt.setString(paramIdx++, null);
						pstmt.setInt(paramIdx++, line.length);		
						pstmt.setString(paramIdx++, colVal);
					}else{
												
						pstmt.setString(paramIdx++, colVal); 											
					} 										
    			}
				
				String lstColVal = UtilString.null2Blank(line[line.length -1]);
				
				//정상이 아닌경우만 INSERT 
				if(!lstColVal.equals("1")) {
					
					errDataSno++;
				
					if(paramIdx < 100){
						
						int iCnt = 100 + 6 - paramIdx + 1;
						
						for(int j = 0; j < iCnt ; j++){ 
						
							pstmt.setString(paramIdx++, null);
						}
					}
					
					int iRtn = pstmt.executeUpdate();
				}
				        		
				rowCnt++;
			}
						
			con.commit();
			
			logger.debug("=====Save Result End.======");
			
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
	private String getWadUodResultSql() {
		
		StringBuffer sb = new StringBuffer(); 
		
		sb.setLength(0);			
		sb.append("\n INSERT INTO WAD_UOD_RESULT        ");
		sb.append("\n (                                 ");
		sb.append("\n    UDF_OTL_DTC_ID                 ");  
		sb.append("\n  , CRE_COMP_ID                    ");
		sb.append("\n  , ANA_STR_DTM                    ");
		sb.append("\n  , ANA_END_DTM                    ");
		sb.append("\n  , ANA_DGR                        ");
		sb.append("\n  , ANA_CNT                        ");
		sb.append("\n  , ESN_ER_CNT                     ");
		sb.append("\n )                                 ");	
		sb.append("\n VALUES                            ");
		sb.append("\n (                                 ");
		sb.append("\n    ?                              ");  
		sb.append("\n  , ?                              ");  
		sb.append("\n  , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		sb.append("\n  , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		sb.append("\n  , ?                              ");
		sb.append("\n  , ?                              ");
		sb.append("\n  , ?                              ");
		sb.append("\n )                                 ");
		
		return sb.toString();
	}
	
	/** @param strSQL insomnia */
	private String getWadUodErrDataSql() {
		
		StringBuffer sb = new StringBuffer(); 
		
		sb.setLength(0);			
		
		sb.append("\n INSERT INTO WAD_UOD_ERR_DATA      ");
		sb.append("\n (                             ");
		sb.append("\n    UDF_OTL_DTC_ID             ");  
		sb.append("\n  , CRE_COMP_ID                ");
		sb.append("\n  , ANA_STR_DTM                ");
		sb.append("\n  , ER_DATA_SNO                ");
		sb.append("\n  , ANA_DGR                    ");
		sb.append("\n  , COL_CNT                    ");
		sb.append("\n  , COL_NM1                    "); 
		sb.append("\n  , COL_NM2                    ");
		sb.append("\n  , COL_NM3                    ");
		sb.append("\n  , COL_NM4                    ");
		sb.append("\n  , COL_NM5                    ");
		sb.append("\n  , COL_NM6                    ");
		sb.append("\n  , COL_NM7                    ");
		sb.append("\n  , COL_NM8                    ");
		sb.append("\n  , COL_NM9                    ");
		sb.append("\n  , COL_NM10                   ");
		sb.append("\n  , COL_NM11                   ");
		sb.append("\n  , COL_NM12                   ");
		sb.append("\n  , COL_NM13                   ");
		sb.append("\n  , COL_NM14                   ");
		sb.append("\n  , COL_NM15                   ");
		sb.append("\n  , COL_NM16                   ");
		sb.append("\n  , COL_NM17                   ");
		sb.append("\n  , COL_NM18                   ");
		sb.append("\n  , COL_NM19                   ");
		sb.append("\n  , COL_NM20                   ");
		sb.append("\n  , COL_NM21                   ");
		sb.append("\n  , COL_NM22                   ");
		sb.append("\n  , COL_NM23                   ");
		sb.append("\n  , COL_NM24                   ");
		sb.append("\n  , COL_NM25                   ");
		sb.append("\n  , COL_NM26                   ");
		sb.append("\n  , COL_NM27                   ");
		sb.append("\n  , COL_NM28                   ");
		sb.append("\n  , COL_NM29                   ");
		sb.append("\n  , COL_NM30                   ");
		sb.append("\n  , COL_NM31                   ");
		sb.append("\n  , COL_NM32                   ");
		sb.append("\n  , COL_NM33                   ");
		sb.append("\n  , COL_NM34                   ");
		sb.append("\n  , COL_NM35                   ");
		sb.append("\n  , COL_NM36                   ");
		sb.append("\n  , COL_NM37                   ");
		sb.append("\n  , COL_NM38                   ");
		sb.append("\n  , COL_NM39                   ");
		sb.append("\n  , COL_NM40                   ");
		sb.append("\n  , COL_NM41                   ");
		sb.append("\n  , COL_NM42                   ");
		sb.append("\n  , COL_NM43                   ");
		sb.append("\n  , COL_NM44                   ");
		sb.append("\n  , COL_NM45                   ");
		sb.append("\n  , COL_NM46                   ");
		sb.append("\n  , COL_NM47                   ");
		sb.append("\n  , COL_NM48                   ");
		sb.append("\n  , COL_NM49                   ");
		sb.append("\n  , COL_NM50                   ");
		sb.append("\n  , COL_NM51                   ");
		sb.append("\n  , COL_NM52                   ");
		sb.append("\n  , COL_NM53                   ");
		sb.append("\n  , COL_NM54                   ");
		sb.append("\n  , COL_NM55                   ");
		sb.append("\n  , COL_NM56                   ");
		sb.append("\n  , COL_NM57                   ");
		sb.append("\n  , COL_NM58                   ");
		sb.append("\n  , COL_NM59                   ");
		sb.append("\n  , COL_NM60                   ");
		sb.append("\n  , COL_NM61                   ");
		sb.append("\n  , COL_NM62                   ");
		sb.append("\n  , COL_NM63                   ");
		sb.append("\n  , COL_NM64                   ");
		sb.append("\n  , COL_NM65                   ");
		sb.append("\n  , COL_NM66                   ");
		sb.append("\n  , COL_NM67                   ");
		sb.append("\n  , COL_NM68                   ");
		sb.append("\n  , COL_NM69                   ");
		sb.append("\n  , COL_NM70                   ");
		sb.append("\n  , COL_NM71                   ");
		sb.append("\n  , COL_NM72                   ");
		sb.append("\n  , COL_NM73                   ");
		sb.append("\n  , COL_NM74                   ");
		sb.append("\n  , COL_NM75                   ");
		sb.append("\n  , COL_NM76                   ");
		sb.append("\n  , COL_NM77                   ");
		sb.append("\n  , COL_NM78                   ");
		sb.append("\n  , COL_NM79                   ");
		sb.append("\n  , COL_NM80                   ");
		sb.append("\n  , COL_NM81                   ");
		sb.append("\n  , COL_NM82                   ");
		sb.append("\n  , COL_NM83                   ");
		sb.append("\n  , COL_NM84                   ");
		sb.append("\n  , COL_NM85                   ");
		sb.append("\n  , COL_NM86                   ");
		sb.append("\n  , COL_NM87                   ");
		sb.append("\n  , COL_NM88                   ");
		sb.append("\n  , COL_NM89                   ");
		sb.append("\n  , COL_NM90                   ");
		sb.append("\n  , COL_NM91                   ");
		sb.append("\n  , COL_NM92                   ");
		sb.append("\n  , COL_NM93                   ");
		sb.append("\n  , COL_NM94                   ");
		sb.append("\n  , COL_NM95                   ");
		sb.append("\n  , COL_NM96                   ");
		sb.append("\n  , COL_NM97                   ");
		sb.append("\n  , COL_NM98                   ");
		sb.append("\n  , COL_NM99                   ");
		sb.append("\n  , COL_NM100                  ");
		sb.append("\n )                                 ");	
		sb.append("\n VALUES                            ");
		sb.append("\n (                                 ");
		sb.append("\n    ?                              ");  
		sb.append("\n  , ?                              ");
		sb.append("\n  , TO_DATE(?, 'YYYYMMDDHH24MISS') ");
		sb.append("\n  , ?                              ");
		sb.append("\n  , ?                              ");	
		sb.append("\n  , ?                              ");	
		
		for(int i = 1 ;i <= 100; i++){
			
			sb.append("\n  , ?                              ");	
		}
		
		sb.append("\n )                                 ");	
		
		return sb.toString();
	}

	private ResultSet getCreCompId(Connection con , String udfOtlDtcId) throws SQLException{
		
		StringBuffer sb = new StringBuffer(); 

		sb.append("\n SELECT UDF_OTL_DTC_ID       ");
		sb.append("\n      , CRE_COMP_ID          ");
		sb.append("\n   FROM WAD_UOD_CRE_COMP     ");
		sb.append("\n  WHERE UDF_OTL_DTC_ID = ?   ");
		sb.append("\n    AND COMP_ID = 'SaveRes'  ");
		
		PreparedStatement pstmt = null;
		
		pstmt = con.prepareStatement(sb.toString());
		
		pstmt.setString(1, udfOtlDtcId);
		
		ResultSet rs = pstmt.executeQuery();
		
		return rs;
	}
	

}
