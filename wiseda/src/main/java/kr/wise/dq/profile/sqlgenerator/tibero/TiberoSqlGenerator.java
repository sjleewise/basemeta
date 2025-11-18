package kr.wise.dq.profile.sqlgenerator.tibero;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPC01Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPC02Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPC03Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPC04Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPC05Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPT01Sql;
import kr.wise.dq.profile.sqlgenerator.tibero.sql.TiberoPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TiberoSqlGenerator {

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
		
		//프로파일 마스터
		WamPrfMstrVO prfMstrVO = (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 종류
		String prfKndCd = prfMstrVO.getPrfKndCd();
		
		
		//컬럼분석
		if(prfKndCd.equals("PC01")){
			//컬럼분석 상세
			WamPrfColAnaVO prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
			
			//컬럼분석 sql 생성기
			TiberoPC01Sql tiberoPC01Sql = new TiberoPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = tiberoPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = tiberoPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = tiberoPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = tiberoPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = tiberoPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = tiberoPC01Sql.getPatternSql();
			}
			//컬럼분석
			sqlVO.setNullCountSql(nullCountSql);
			sqlVO.setSpaceCountSql(spaceCountSql);
			sqlVO.setMinMaxSql(minMaxSql);
			sqlVO.setMinMaxLenSql(minMaxLenSql);
		}
		
		//유효값분석
		if(prfKndCd.equals("PC02")){
			//유효값분석 sql 생성기
			TiberoPC02Sql tiberoPC02Sql = new TiberoPC02Sql(sqlGenMap);
			
			totalCountSql = tiberoPC02Sql.getTotalCountSql();
			errDataSql = tiberoPC02Sql.getErrorDataSql();
			errCountSql = tiberoPC02Sql.getErrorCountSql();
			errPatternSql = tiberoPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			TiberoPC03Sql tiberoPC03Sql = new TiberoPC03Sql(sqlGenMap);
			
			totalCountSql = tiberoPC03Sql.getTotalCountSql();
			errDataSql = tiberoPC03Sql.getErrorDataSql();
			errCountSql = tiberoPC03Sql.getErrorCountSql();
			errPatternSql = tiberoPC03Sql.getErrorPatternSql();
			datePatSql = tiberoPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			TiberoPC04Sql tiberoPC04Sql = new TiberoPC04Sql(sqlGenMap);
			
			totalCountSql = tiberoPC04Sql.getTotalCountSql();
			errDataSql = tiberoPC04Sql.getErrorDataSql();
			errCountSql = tiberoPC04Sql.getErrorCountSql();
			errPatternSql = tiberoPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			TiberoPC05Sql tiberoPC05Sql = new TiberoPC05Sql(sqlGenMap);
			
			totalCountSql = tiberoPC05Sql.getTotalCountSql();
			errDataSql = tiberoPC05Sql.getErrorDataSql();
			errCountSql = tiberoPC05Sql.getErrorCountSql();
			errPatternSql = tiberoPC05Sql.getErrorPatternSql();
			userPatSql = tiberoPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			TiberoPT01Sql tiberoPT01Sql = new TiberoPT01Sql(sqlGenMap);
			
			totalCountSql = tiberoPT01Sql.getTotalCountSql();
			errDataSql = tiberoPT01Sql.getErrorDataSql();
			errCountSql = tiberoPT01Sql.getErrorCountSql();
			errPatternSql = tiberoPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			TiberoPT02Sql tiberoPT02Sql = new TiberoPT02Sql(sqlGenMap);
			
			totalCountSql = tiberoPT02Sql.getTotalCountSql();
			errDataSql = tiberoPT02Sql.getErrorDataSql();
			errCountSql = tiberoPT02Sql.getErrorCountSql();
			errPatternSql = tiberoPT02Sql.getErrorPatternSql();
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
