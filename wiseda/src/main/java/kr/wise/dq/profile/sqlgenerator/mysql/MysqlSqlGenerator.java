package kr.wise.dq.profile.sqlgenerator.mysql;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPC01Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPC02Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPC03Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPC04Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPC05Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPT01Sql;
import kr.wise.dq.profile.sqlgenerator.mysql.sql.MysqlPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MysqlSqlGenerator {

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
			MysqlPC01Sql mysqlPC01Sql = new MysqlPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = mysqlPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = mysqlPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = mysqlPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = mysqlPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = mysqlPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = mysqlPC01Sql.getPatternSql();
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
			MysqlPC02Sql mysqlPC02Sql = new MysqlPC02Sql(sqlGenMap);
			
			totalCountSql = mysqlPC02Sql.getTotalCountSql();
			errDataSql = mysqlPC02Sql.getErrorDataSql();
			errCountSql = mysqlPC02Sql.getErrorCountSql();
			errPatternSql = mysqlPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			MysqlPC03Sql mysqlPC03Sql = new MysqlPC03Sql(sqlGenMap);
			
			totalCountSql = mysqlPC03Sql.getTotalCountSql();
			errDataSql = mysqlPC03Sql.getErrorDataSql();
			errCountSql = mysqlPC03Sql.getErrorCountSql();
			errPatternSql = mysqlPC03Sql.getErrorPatternSql();
			datePatSql = mysqlPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			MysqlPC04Sql mysqlPC04Sql = new MysqlPC04Sql(sqlGenMap);
			
			totalCountSql = mysqlPC04Sql.getTotalCountSql();
			errDataSql = mysqlPC04Sql.getErrorDataSql();
			errCountSql = mysqlPC04Sql.getErrorCountSql();
			errPatternSql = mysqlPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			MysqlPC05Sql mysqlPC05Sql = new MysqlPC05Sql(sqlGenMap);
			
			totalCountSql = mysqlPC05Sql.getTotalCountSql();
			errDataSql = mysqlPC05Sql.getErrorDataSql();
			errCountSql = mysqlPC05Sql.getErrorCountSql();
			errPatternSql = mysqlPC05Sql.getErrorPatternSql();
			userPatSql = mysqlPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			MysqlPT01Sql mysqlPT01Sql = new MysqlPT01Sql(sqlGenMap);
			
			totalCountSql = mysqlPT01Sql.getTotalCountSql();
			errDataSql = mysqlPT01Sql.getErrorDataSql();
			errCountSql = mysqlPT01Sql.getErrorCountSql();
			errPatternSql = mysqlPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			MysqlPT02Sql mysqlPT02Sql = new MysqlPT02Sql(sqlGenMap);
			
			totalCountSql = mysqlPT02Sql.getTotalCountSql();
			errDataSql = mysqlPT02Sql.getErrorDataSql();
			errCountSql = mysqlPT02Sql.getErrorCountSql();
			errPatternSql = mysqlPT02Sql.getErrorPatternSql();
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
