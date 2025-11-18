package kr.wise.dq.profile.sqlgenerator.db2;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PC01Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PC02Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PC03Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PC04Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PC05Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PT01Sql;
import kr.wise.dq.profile.sqlgenerator.db2.sql.Db2PT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Db2SqlGenerator {

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
		
		//표준편차
		String stddevValSql = null;
		//분산
		String varianceValSql = null;
		//평균
		String avgValSql = null;
		//유일값수
		String unqCntSql = null;
		//최소빈도값
		String minCntValSql = null;
		//최대빈도값
		String maxCntValSql = null;
		
		//프로파일 마스터
		WamPrfMstrVO prfMstrVO = (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 종류
		String prfKndCd = prfMstrVO.getPrfKndCd();
		
		
		//컬럼분석
		if(prfKndCd.equals("PC01")){
			//컬럼분석 상세
			WamPrfColAnaVO prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
			
			//컬럼분석 sql 생성기
			Db2PC01Sql Db2PC01Sql = new Db2PC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = Db2PC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = Db2PC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = Db2PC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = Db2PC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = Db2PC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = Db2PC01Sql.getPatternSql();
			}
			
			//표준편차분석 STDV_ANA_YN
			if("Y".equals(prfDtlVO.getStdvAnaYn())){
				stddevValSql = Db2PC01Sql.getStddevSql();
			}
			//분산분석 VRN_ANA_YN
			if("Y".equals(prfDtlVO.getVrnAnaYn())){
				varianceValSql = Db2PC01Sql.getVarianceSql();
			}
			//평균분석 AVG_ANA_YN
			if("Y".equals(prfDtlVO.getAvgAnaYn())){
				avgValSql = Db2PC01Sql.getAvgSql();
			}
			//유일값수분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				unqCntSql = Db2PC01Sql.getUnqCntSql();
			}
			//최소빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minCntValSql = Db2PC01Sql.getMinCntValSql();
			}
			//최대빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				maxCntValSql = Db2PC01Sql.getMaxCntValSql();
			}
			
			//컬럼분석
			sqlVO.setNullCountSql(nullCountSql);
			sqlVO.setSpaceCountSql(spaceCountSql);
			sqlVO.setMinMaxSql(minMaxSql);
			sqlVO.setMinMaxLenSql(minMaxLenSql);
			
			sqlVO.setStddevValSql(stddevValSql);
			sqlVO.setVarianceValSql(varianceValSql);
			sqlVO.setAvgValSql(avgValSql);
			sqlVO.setUnqCntSql(unqCntSql);
			sqlVO.setMinCntValSql(minCntValSql);
			sqlVO.setMaxCntValSql(maxCntValSql);
		}
		
		//유효값분석
		if(prfKndCd.equals("PC02")){
			//유효값분석 sql 생성기
			Db2PC02Sql Db2PC02Sql = new Db2PC02Sql(sqlGenMap);
			
			totalCountSql = Db2PC02Sql.getTotalCountSql();
			errDataSql = Db2PC02Sql.getErrorDataSql();
			errCountSql = Db2PC02Sql.getErrorCountSql();
			errPatternSql = Db2PC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			Db2PC03Sql Db2PC03Sql = new Db2PC03Sql(sqlGenMap);
			
			totalCountSql = Db2PC03Sql.getTotalCountSql();
			errDataSql = Db2PC03Sql.getErrorDataSql();
			errCountSql = Db2PC03Sql.getErrorCountSql();
			errPatternSql = Db2PC03Sql.getErrorPatternSql();
			datePatSql = Db2PC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			Db2PC04Sql Db2PC04Sql = new Db2PC04Sql(sqlGenMap);
			
			totalCountSql = Db2PC04Sql.getTotalCountSql();
			errDataSql = Db2PC04Sql.getErrorDataSql();
			errCountSql = Db2PC04Sql.getErrorCountSql();
			errPatternSql = Db2PC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			Db2PC05Sql Db2PC05Sql = new Db2PC05Sql(sqlGenMap);
			
			totalCountSql = Db2PC05Sql.getTotalCountSql();
			errDataSql = Db2PC05Sql.getErrorDataSql();
			errCountSql = Db2PC05Sql.getErrorCountSql();
			errPatternSql = Db2PC05Sql.getErrorPatternSql();
			userPatSql = Db2PC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			Db2PT01Sql Db2PT01Sql = new Db2PT01Sql(sqlGenMap);
			
			totalCountSql = Db2PT01Sql.getTotalCountSql();
			errDataSql = Db2PT01Sql.getErrorDataSql();
			errCountSql = Db2PT01Sql.getErrorCountSql();
			errPatternSql = Db2PT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			Db2PT02Sql Db2PT02Sql = new Db2PT02Sql(sqlGenMap);
			
			totalCountSql = Db2PT02Sql.getTotalCountSql();
			errDataSql = Db2PT02Sql.getErrorDataSql();
			errCountSql = Db2PT02Sql.getErrorCountSql();
			errPatternSql = Db2PT02Sql.getErrorPatternSql();
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
