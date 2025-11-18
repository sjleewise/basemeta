package kr.wise.dq.profile.sqlgenerator.mssql;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPC01Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPC02Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPC03Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPC04Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPC05Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPT01Sql;
import kr.wise.dq.profile.sqlgenerator.mssql.sql.MsqPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MsSqlServerSqlGenerator {

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
			MsqPC01Sql msqPC01Sql = new MsqPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = msqPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = msqPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = msqPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = msqPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = msqPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = msqPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = msqPC01Sql.getErrorPatternSqlforjava();
			}
			
			dateYnSql  = msqPC01Sql.getDateYnSql();
			telYnSql   = msqPC01Sql.getTelYnSql();
			spaceRtSql = msqPC01Sql.getSpaceRtSql();
			crlfYnSql  = msqPC01Sql.getCrlfYnSql();
			alphaYnSql  = msqPC01Sql.getAlphaYnSql();
//			dataFmtSql  = msqPC01Sql.getDataFmtSql();
			numYnSql    = msqPC01Sql.getNumYnSql();
			hundRtSql   = msqPC01Sql.getHundRtSql();
			cntRtSql    = msqPC01Sql.getCntRtSql();
			
			//컬럼분석
			sqlVO.setNullCountSql(nullCountSql);
			sqlVO.setSpaceCountSql(spaceCountSql);
			sqlVO.setMinMaxSql(minMaxSql);
			sqlVO.setMinMaxLenSql(minMaxLenSql);
//			sqlVO.setUserPatSql(userPatSql);
			
			sqlVO.setDateYnSql(dateYnSql); 			
			sqlVO.setTelYnSql(telYnSql);      
			sqlVO.setSpaceRtSql(spaceRtSql);  
			sqlVO.setCrlfYnSql(crlfYnSql);
			sqlVO.setAlphaYnSql(alphaYnSql);
//			sqlVO.setDataFmtSql(dataFmtSql);
			sqlVO.setNumYnSql(numYnSql);
			sqlVO.setHundRtSql(hundRtSql);
			sqlVO.setCntRtSql(cntRtSql);
		}
		
		//유효값분석
		if(prfKndCd.equals("PC02")){
			//유효값분석 sql 생성기
			MsqPC02Sql msqPC02Sql = new MsqPC02Sql(sqlGenMap);
			
			totalCountSql = msqPC02Sql.getTotalCountSql();
			errDataSql = msqPC02Sql.getErrorDataSql();
			errCountSql = msqPC02Sql.getErrorCountSql();
			errPatternSql = msqPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			MsqPC03Sql msqPC03Sql = new MsqPC03Sql(sqlGenMap);
			
			totalCountSql = msqPC03Sql.getTotalCountSql();
			errDataSql = msqPC03Sql.getErrorDataSql();
			errCountSql = msqPC03Sql.getErrorCountSql();
			errPatternSql = msqPC03Sql.getErrorPatternSql();
			datePatSql = msqPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			MsqPC04Sql msqPC04Sql = new MsqPC04Sql(sqlGenMap);
			
			totalCountSql = msqPC04Sql.getTotalCountSql();
			errDataSql = msqPC04Sql.getErrorDataSql();
			errCountSql = msqPC04Sql.getErrorCountSql();
			errPatternSql = msqPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			MsqPC05Sql msqPC05Sql = new MsqPC05Sql(sqlGenMap);
			
			totalCountSql = msqPC05Sql.getTotalCountSql();
			errDataSql = msqPC05Sql.getErrorDataSql();
			errCountSql = msqPC05Sql.getErrorCountSql();
			errPatternSql = msqPC05Sql.getErrorPatternSql();
			userPatSql = msqPC05Sql.getErrorPatternSqlforjava(); 
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			MsqPT01Sql oraPT01Sql = new MsqPT01Sql(sqlGenMap);
			
			totalCountSql = oraPT01Sql.getTotalCountSql();
			errDataSql = oraPT01Sql.getErrorDataSql();
			errCountSql = oraPT01Sql.getErrorCountSql();
			errPatternSql = oraPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			MsqPT02Sql oraPT02Sql = new MsqPT02Sql(sqlGenMap);
			
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
