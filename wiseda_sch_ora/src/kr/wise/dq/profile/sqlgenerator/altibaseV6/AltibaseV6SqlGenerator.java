package kr.wise.dq.profile.sqlgenerator.altibaseV6;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PC01Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PC02Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PC03Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PC04Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PC05Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PT01Sql;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.sql.AltibaseV6PT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AltibaseV6SqlGenerator {

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
			AltibaseV6PC01Sql altiV6PC01Sql = new AltibaseV6PC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = altiV6PC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = altiV6PC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = altiV6PC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = altiV6PC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = altiV6PC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = altiV6PC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = altiV6PC01Sql.getErrorPatternSqlforjava();
			}
			
			dateYnSql  = altiV6PC01Sql.getDateYnSql();
			telYnSql   = altiV6PC01Sql.getTelYnSql();
			spaceRtSql = altiV6PC01Sql.getSpaceRtSql();
			crlfYnSql  = altiV6PC01Sql.getCrlfYnSql();
			alphaYnSql  = altiV6PC01Sql.getAlphaYnSql();
			dataFmtSql  = altiV6PC01Sql.getDataFmtSql();
			numYnSql    = altiV6PC01Sql.getNumYnSql();
			hundRtSql   = altiV6PC01Sql.getHundRtSql();
			cntRtSql    = altiV6PC01Sql.getCntRtSql();
			
			//컬럼분석
			sqlVO.setNullCountSql(nullCountSql);
			sqlVO.setSpaceCountSql(spaceCountSql);
			sqlVO.setMinMaxSql(minMaxSql);
			sqlVO.setMinMaxLenSql(minMaxLenSql);
			
			
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
			AltibaseV6PC02Sql altiV6PC02Sql = new AltibaseV6PC02Sql(sqlGenMap);
			
			totalCountSql = altiV6PC02Sql.getTotalCountSql();
			errDataSql = altiV6PC02Sql.getErrorDataSql();
			errCountSql = altiV6PC02Sql.getErrorCountSql();
			errPatternSql = altiV6PC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			AltibaseV6PC03Sql altiV6PC03Sql = new AltibaseV6PC03Sql(sqlGenMap);
			
			totalCountSql = altiV6PC03Sql.getTotalCountSql();
			errDataSql = altiV6PC03Sql.getErrorDataSql();
			errCountSql = altiV6PC03Sql.getErrorCountSql();
			errPatternSql = altiV6PC03Sql.getErrorPatternSql();
			datePatSql = altiV6PC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			AltibaseV6PC04Sql altiV6PC04Sql = new AltibaseV6PC04Sql(sqlGenMap);
			
			totalCountSql = altiV6PC04Sql.getTotalCountSql();
			errDataSql = altiV6PC04Sql.getErrorDataSql();
			errCountSql = altiV6PC04Sql.getErrorCountSql();
			errPatternSql = altiV6PC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			AltibaseV6PC05Sql altiV6PC05Sql = new AltibaseV6PC05Sql(sqlGenMap);
			
			totalCountSql = altiV6PC05Sql.getTotalCountSql();
			errDataSql = altiV6PC05Sql.getErrorDataSql();
			errCountSql = altiV6PC05Sql.getErrorCountSql();
			errPatternSql = altiV6PC05Sql.getErrorPatternSql();
			userPatSql = altiV6PC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			AltibaseV6PT01Sql altiV6PT01Sql = new AltibaseV6PT01Sql(sqlGenMap);
			
			totalCountSql = altiV6PT01Sql.getTotalCountSql();
			errDataSql = altiV6PT01Sql.getErrorDataSql();
			errCountSql = altiV6PT01Sql.getErrorCountSql();
			errPatternSql = altiV6PT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			AltibaseV6PT02Sql altiV6PT02Sql = new AltibaseV6PT02Sql(sqlGenMap);
			
			totalCountSql = altiV6PT02Sql.getTotalCountSql();
			errDataSql = altiV6PT02Sql.getErrorDataSql();
			errCountSql = altiV6PT02Sql.getErrorCountSql();
			errPatternSql = altiV6PT02Sql.getErrorPatternSql();
		}
		
		
		logger.debug(totalCountSql);
		logger.debug(errDataSql);
		logger.debug(errCountSql);
		logger.debug(errPatternSql);
		
		sqlVO.setTotalCount(totalCountSql);
		sqlVO.setErrorData(errDataSql);
		sqlVO.setErrorCount(errCountSql);
		sqlVO.setErrorPattern(errPatternSql);
		sqlVO.setDatePatSql(datePatSql);
		sqlVO.setUserPatSql(userPatSql);
		
		return sqlVO;
			
		
	}

}
