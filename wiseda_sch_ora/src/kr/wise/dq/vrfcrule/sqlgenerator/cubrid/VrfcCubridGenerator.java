package kr.wise.dq.vrfcrule.sqlgenerator.cubrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPC01Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPC02Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPC03Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPC04Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPC05Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPT01Sql;
import kr.wise.dq.profile.sqlgenerator.oracle.sql.OraPT02Sql;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.vrfcrule.service.VrfcRuleVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraCdSql;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraFmtSql;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraNNSql;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraRangeSql;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraYmdSql;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql.VrfcOraYnSql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VrfcCubridGenerator {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	public VrfcSqlGeneratorVO getSql(Map<String, Object> sqlGenMap)  {
		
		VrfcSqlGeneratorVO sqlVO = new VrfcSqlGeneratorVO();  
		
		String totalCountSql = null;
		String errCountSql = null;
		String errDataSql = null;
		String errPatternSql = null;
		
		//날짜형식 sql for java (성능향상용 sql)
		String datePatSql = null;
		
		//사용자패턴 sql for java (성능향상용 sql)
		String userPatSql = null;
				
		
		//널건수
		String nullCountSql = null;
		//스페이스건수
		String spaceCountSql = null;
		//최대최소값
		String minMaxSql = null;
		//최대최소길이
		String minMaxLenSql = null;

		//일자여부 
		String dateYnSql = null;
		//전화번호여부 
		String telYnSql = null;
		//공백율 
		String spaceRtSql = null;		
		//엔터값여부 
		String crlfYnSql = null;
		//영문여부 
		String alphaYnSql = null;	
		//데이터포멧 
		String dataFmtSql = null;
		//숫자여부 
		String numYnSql = null;
		//백단위율
		String hundRtSql = null;
		//건수율
		String cntRtSql = null;
				
		
		//프로파일 마스터
		VrfcRuleVO vrfcRuleVO = (VrfcRuleVO) sqlGenMap.get("vrfcVO");
		//프로파일 종류
		String vrfcNm = vrfcRuleVO.getVrfcNm();  
		
		String vrfcTyp = UtilString.null2Blank(vrfcRuleVO.getVrfcTyp());  
		
		logger.debug("\n vrfcNm:" + vrfcNm);
		logger.debug("\n vrfcTyp:" + vrfcTyp);
		
		//컬럼분석
		if(vrfcTyp.equals("YN")){
			//여부
			
			VrfcOraYnSql oraPC02Sql = new VrfcOraYnSql(sqlGenMap);  
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
		
		}else if(vrfcTyp.contains("CD")){
			//코드
			VrfcOraCdSql oraPC02Sql = new VrfcOraCdSql(sqlGenMap);      
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql    = oraPC02Sql.getErrorDataSql();
			errCountSql   = oraPC02Sql.getErrorCountSql();									
			errPatternSql = oraPC02Sql.getErrorPatternSql();
			
			logger.debug("\n " + errCountSql);
			
		}else if(vrfcTyp.contains("DTM")){
			//날짜
			VrfcOraYmdSql oraPC02Sql = new VrfcOraYmdSql(sqlGenMap);    
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
		}else if(vrfcTyp.contains("RNG")){
			//
			VrfcOraRangeSql oraPC02Sql = new VrfcOraRangeSql(sqlGenMap);    
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
		}else if(vrfcTyp.contains("FRM")){
			
			VrfcOraFmtSql oraPC02Sql = new VrfcOraFmtSql(sqlGenMap);  
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
		}else if(vrfcTyp.contains("NN")){
			
			VrfcOraNNSql oraPC02Sql = new VrfcOraNNSql(sqlGenMap);     
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
			
		}else if(vrfcTyp.contains("PT01")){
						
			OraPT01Sql oraPC02Sql = new OraPT01Sql(sqlGenMap);    
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();	
		}		
		
		
//		logger.debug(totalCountSql);
//		logger.debug(errDataSql);
//		logger.debug(errCountSql);
//		logger.debug(errPatternSql);
		
		sqlVO.setTotalCount(totalCountSql);
		sqlVO.setErrorData(errDataSql);
		sqlVO.setErrorCount(errCountSql);
		sqlVO.setErrorPattern(errPatternSql);
		sqlVO.setDatePatSql(datePatSql);
		sqlVO.setUserPatSql(userPatSql);
		
		return sqlVO;
			
		
	}

}
