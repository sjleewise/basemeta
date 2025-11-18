package kr.wise.dq.profile.sqlgenerator.sybaseIQ;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPC01Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPC02Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPC03Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPC04Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPC05Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPT01Sql;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.sql.SybaseIQPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SybaseIQSqlGenerator {

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
			SybaseIQPC01Sql sybaseIQPC01Sql = new SybaseIQPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = sybaseIQPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()).equals("Y")){
				nullCountSql = sybaseIQPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = sybaseIQPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minMaxSql = sybaseIQPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()).equals("Y")){
				minMaxLenSql = sybaseIQPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()).equals("Y")){
				errPatternSql = sybaseIQPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = sybaseIQPC01Sql.getErrorPatternSqlforjava();
			}
			//표준편차분석 STDV_ANA_YN
			if("Y".equals(prfDtlVO.getStdvAnaYn())){
				stddevValSql = sybaseIQPC01Sql.getStddevSql();
			}
			//분산분석 VRN_ANA_YN
			if("Y".equals(prfDtlVO.getVrnAnaYn())){
				varianceValSql = sybaseIQPC01Sql.getVarianceSql();
			}
			//평균분석 AVG_ANA_YN
			if("Y".equals(prfDtlVO.getAvgAnaYn())){
				avgValSql = sybaseIQPC01Sql.getAvgSql();
			}
			//유일값수분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				unqCntSql = sybaseIQPC01Sql.getUnqCntSql();
			}
			//최소빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minCntValSql = sybaseIQPC01Sql.getMinCntValSql();
			}
			//최대빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				maxCntValSql = sybaseIQPC01Sql.getMaxCntValSql();
			}
			
			dateYnSql  = sybaseIQPC01Sql.getDateYnSql();
			telYnSql   = sybaseIQPC01Sql.getTelYnSql();
			spaceRtSql = sybaseIQPC01Sql.getSpaceRtSql();
			crlfYnSql  = sybaseIQPC01Sql.getCrlfYnSql();
			alphaYnSql  = sybaseIQPC01Sql.getAlphaYnSql();
			dataFmtSql  = sybaseIQPC01Sql.getDataFmtSql();
			numYnSql    = sybaseIQPC01Sql.getNumYnSql();
			hundRtSql   = sybaseIQPC01Sql.getHundRtSql();
			cntRtSql    = sybaseIQPC01Sql.getCntRtSql();
			
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
			SybaseIQPC02Sql sybaseIQPC02Sql = new SybaseIQPC02Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPC02Sql.getTotalCountSql();
			errDataSql = sybaseIQPC02Sql.getErrorDataSql();
			errCountSql = sybaseIQPC02Sql.getErrorCountSql();
			errPatternSql = sybaseIQPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			SybaseIQPC03Sql sybaseIQPC03Sql = new SybaseIQPC03Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPC03Sql.getTotalCountSql();
			errDataSql = sybaseIQPC03Sql.getErrorDataSql();
			errCountSql = sybaseIQPC03Sql.getErrorCountSql();
			errPatternSql = sybaseIQPC03Sql.getErrorPatternSql();
			datePatSql = sybaseIQPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			SybaseIQPC04Sql sybaseIQPC04Sql = new SybaseIQPC04Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPC04Sql.getTotalCountSql();
			errDataSql = sybaseIQPC04Sql.getErrorDataSql();
			errCountSql = sybaseIQPC04Sql.getErrorCountSql();
			errPatternSql = sybaseIQPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			SybaseIQPC05Sql sybaseIQPC05Sql = new SybaseIQPC05Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPC05Sql.getTotalCountSql();
			errDataSql = sybaseIQPC05Sql.getErrorDataSql();
			errCountSql = sybaseIQPC05Sql.getErrorCountSql();
			errPatternSql = sybaseIQPC05Sql.getErrorPatternSql();
			userPatSql = sybaseIQPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			SybaseIQPT01Sql sybaseIQPT01Sql = new SybaseIQPT01Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPT01Sql.getTotalCountSql();
			errDataSql = sybaseIQPT01Sql.getErrorDataSql();
			errCountSql = sybaseIQPT01Sql.getErrorCountSql();
			errPatternSql = sybaseIQPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			SybaseIQPT02Sql sybaseIQPT02Sql = new SybaseIQPT02Sql(sqlGenMap);
			
			totalCountSql = sybaseIQPT02Sql.getTotalCountSql();
			errDataSql = sybaseIQPT02Sql.getErrorDataSql();
			errCountSql = sybaseIQPT02Sql.getErrorCountSql();
			errPatternSql = sybaseIQPT02Sql.getErrorPatternSql();
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
