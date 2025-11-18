package kr.wise.dq.profile.reac.sqlgenerator.oracle;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OracleSqlGenerator {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	public SqlGeneratorVO getSql(Map<String, Object> sqlGenMap)  {
		
		SqlGeneratorVO sqlVO = new SqlGeneratorVO();
		
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
		WamPrfMstrVO prfMstrVO = (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 종류
		String prfKndCd = prfMstrVO.getPrfKndCd();
		
		
		//컬럼분석
		if(prfKndCd.equals("PC01")){
			//컬럼분석 상세
			WamPrfColAnaVO prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
			
			//컬럼분석 sql 생성기
			OraPC01Sql oraPC01Sql = new OraPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = oraPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = oraPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = oraPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = oraPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = oraPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = oraPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = oraPC01Sql.getErrorPatternSqlforjava();
			}
			
			dateYnSql  = oraPC01Sql.getDateYnSql();
			telYnSql   = oraPC01Sql.getTelYnSql();
			spaceRtSql = oraPC01Sql.getSpaceRtSql();
			crlfYnSql  = oraPC01Sql.getCrlfYnSql();
			alphaYnSql  = oraPC01Sql.getAlphaYnSql();
			dataFmtSql  = oraPC01Sql.getDataFmtSql();
			numYnSql    = oraPC01Sql.getNumYnSql();
			hundRtSql   = oraPC01Sql.getHundRtSql();
			cntRtSql    = oraPC01Sql.getCntRtSql();
			
			//컬럼분석
			sqlVO.setNullCountSql(nullCountSql);
			sqlVO.setSpaceCountSql(spaceCountSql);
			sqlVO.setMinMaxSql(minMaxSql);
			sqlVO.setMinMaxLenSql(minMaxLenSql);
			//sqlVO.setUserPatSql(userPatSql);
			
			sqlVO.setDateYnSql(dateYnSql); 			
			sqlVO.setTelYnSql(telYnSql);      
			sqlVO.setSpaceRtSql(spaceRtSql);  
			sqlVO.setCrlfYnSql(crlfYnSql);
			sqlVO.setAlphaYnSql(alphaYnSql);
			sqlVO.setDataFmtSql(dataFmtSql);
			sqlVO.setNumYnSql(numYnSql);
			sqlVO.setHundRtSql(hundRtSql);
			sqlVO.setCntRtSql(cntRtSql); 			
		}
		
		//유효값분석
		if(prfKndCd.equals("PC02")){
			//유효값분석 sql 생성기
			OraPC02Sql oraPC02Sql = new OraPC02Sql(sqlGenMap);
			
			totalCountSql = oraPC02Sql.getTotalCountSql();
			errDataSql = oraPC02Sql.getErrorDataSql();
			errCountSql = oraPC02Sql.getErrorCountSql();
			errPatternSql = oraPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			OraPC03Sql oraPC03Sql = new OraPC03Sql(sqlGenMap);
			
			totalCountSql = oraPC03Sql.getTotalCountSql();
			errDataSql = oraPC03Sql.getErrorDataSql();
			errCountSql = oraPC03Sql.getErrorCountSql();
			errPatternSql = oraPC03Sql.getErrorPatternSql();
			datePatSql = oraPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			OraPC04Sql oraPC04Sql = new OraPC04Sql(sqlGenMap);
			
			totalCountSql = oraPC04Sql.getTotalCountSql();
			errDataSql = oraPC04Sql.getErrorDataSql();
			errCountSql = oraPC04Sql.getErrorCountSql();
			errPatternSql = oraPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			OraPC05Sql oraPC05Sql = new OraPC05Sql(sqlGenMap);
			
			totalCountSql = oraPC05Sql.getTotalCountSql();
			errDataSql = oraPC05Sql.getErrorDataSql();
			errCountSql = oraPC05Sql.getErrorCountSql();
			errPatternSql = oraPC05Sql.getErrorPatternSql();
			userPatSql = oraPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			OraPT01Sql oraPT01Sql = new OraPT01Sql(sqlGenMap);
			
			totalCountSql = oraPT01Sql.getTotalCountSql();
			errDataSql = oraPT01Sql.getErrorDataSql();
			errCountSql = oraPT01Sql.getErrorCountSql();
			errPatternSql = oraPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			OraPT02Sql oraPT02Sql = new OraPT02Sql(sqlGenMap);
			
			totalCountSql = oraPT02Sql.getTotalCountSql();
			errDataSql = oraPT02Sql.getErrorDataSql();
			errCountSql = oraPT02Sql.getErrorCountSql();
			errPatternSql = oraPT02Sql.getErrorPatternSql();
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
