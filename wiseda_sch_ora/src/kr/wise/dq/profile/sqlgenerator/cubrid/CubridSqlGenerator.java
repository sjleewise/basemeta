package kr.wise.dq.profile.sqlgenerator.cubrid;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPC01Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPC02Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPC03Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPC04Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPC05Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPT01Sql;
import kr.wise.dq.profile.sqlgenerator.cubrid.sql.CubridPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CubridSqlGenerator {

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
		
		//(2021.10) 추가 - 큐브리드의 경우 year/section/div가 예약어라 컬럼명이 year/section/div일 경우 그냥 부르면 오류발생
		if(prfMstrVO.getDbcColNm().toUpperCase().equals("YEAR") || prfMstrVO.getDbcColNm().toUpperCase().equals("SECTION") || prfMstrVO.getDbcColNm().toUpperCase().equals("DIV")){
			prfMstrVO.setDbcColNm("\""+prfMstrVO.getDbcColNm()+"\"");
		}

		//프로파일 종류
		String prfKndCd = prfMstrVO.getPrfKndCd();
		
		//컬럼분석
		if(prfKndCd.equals("PC01")){
			//컬럼분석 상세
			WamPrfColAnaVO prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
			
			//컬럼분석 sql 생성기
			CubridPC01Sql cubridPC01Sql = new CubridPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = cubridPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()).equals("Y")){
				nullCountSql = cubridPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = cubridPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minMaxSql = cubridPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()).equals("Y")){
				minMaxLenSql = cubridPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()).equals("Y")){
				errPatternSql = cubridPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = cubridPC01Sql.getErrorPatternSqlforjava();
			}
			//표준편차분석 STDV_ANA_YN
			if("Y".equals(prfDtlVO.getStdvAnaYn())){
				stddevValSql = cubridPC01Sql.getStddevSql();
			}
			//분산분석 VRN_ANA_YN
			if("Y".equals(prfDtlVO.getVrnAnaYn())){
				varianceValSql = cubridPC01Sql.getVarianceSql();
			}
			//평균분석 AVG_ANA_YN
			if("Y".equals(prfDtlVO.getAvgAnaYn())){
				avgValSql = cubridPC01Sql.getAvgSql();
			}
			//유일값수분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				unqCntSql = cubridPC01Sql.getUnqCntSql();
			}
			//최소빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minCntValSql = cubridPC01Sql.getMinCntValSql();
			}
			//최대빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				maxCntValSql = cubridPC01Sql.getMaxCntValSql();
			}
			
			dateYnSql  = cubridPC01Sql.getDateYnSql();
			telYnSql   = cubridPC01Sql.getTelYnSql();
			spaceRtSql = cubridPC01Sql.getSpaceRtSql();
			crlfYnSql  = cubridPC01Sql.getCrlfYnSql();
//			alphaYnSql  = cubridPC01Sql.getAlphaYnSql();
			dataFmtSql  = cubridPC01Sql.getDataFmtSql();
//			numYnSql    = cubridPC01Sql.getNumYnSql();
			hundRtSql   = cubridPC01Sql.getHundRtSql();
			cntRtSql    = cubridPC01Sql.getCntRtSql();
			
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
			CubridPC02Sql cubridPC02Sql = new CubridPC02Sql(sqlGenMap);
			
			totalCountSql = cubridPC02Sql.getTotalCountSql();
			errDataSql = cubridPC02Sql.getErrorDataSql();
			errCountSql = cubridPC02Sql.getErrorCountSql();
			errPatternSql = cubridPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			CubridPC03Sql cubridPC03Sql = new CubridPC03Sql(sqlGenMap);
			
			totalCountSql = cubridPC03Sql.getTotalCountSql();
			errDataSql = cubridPC03Sql.getErrorDataSql();
			errCountSql = cubridPC03Sql.getErrorCountSql();
			errPatternSql = cubridPC03Sql.getErrorPatternSql();
			datePatSql = cubridPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			CubridPC04Sql cubridPC04Sql = new CubridPC04Sql(sqlGenMap);
			
			totalCountSql = cubridPC04Sql.getTotalCountSql();
			errDataSql = cubridPC04Sql.getErrorDataSql();
			errCountSql = cubridPC04Sql.getErrorCountSql();
			errPatternSql = cubridPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			CubridPC05Sql cubridPC05Sql = new CubridPC05Sql(sqlGenMap);
			
			totalCountSql = cubridPC05Sql.getTotalCountSql();
			errDataSql = cubridPC05Sql.getErrorDataSql();
			errCountSql = cubridPC05Sql.getErrorCountSql();
			errPatternSql = cubridPC05Sql.getErrorPatternSql();
			userPatSql = cubridPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			CubridPT01Sql cubridPT01Sql = new CubridPT01Sql(sqlGenMap);
			
			totalCountSql = cubridPT01Sql.getTotalCountSql();
			errDataSql = cubridPT01Sql.getErrorDataSql();
			errCountSql = cubridPT01Sql.getErrorCountSql();
			errPatternSql = cubridPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			CubridPT02Sql cubridPT02Sql = new CubridPT02Sql(sqlGenMap);
			
			totalCountSql = cubridPT02Sql.getTotalCountSql();
			errDataSql = cubridPT02Sql.getErrorDataSql();
			errCountSql = cubridPT02Sql.getErrorCountSql();
			errPatternSql = cubridPT02Sql.getErrorPatternSql();
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
