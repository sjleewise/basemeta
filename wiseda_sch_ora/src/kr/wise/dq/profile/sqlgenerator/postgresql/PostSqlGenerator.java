package kr.wise.dq.profile.sqlgenerator.postgresql;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPC01Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPC02Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPC03Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPC04Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPC05Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPT01Sql;
import kr.wise.dq.profile.sqlgenerator.postgresql.sql.PostPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PostSqlGenerator {

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
			PostPC01Sql postPC01Sql = new PostPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = postPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()).equals("Y")){
				nullCountSql = postPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = postPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minMaxSql = postPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()).equals("Y")){
				minMaxLenSql = postPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()).equals("Y")){
				errPatternSql = postPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = postPC01Sql.getErrorPatternSqlforjava();
			}
			//표준편차분석 STDV_ANA_YN
			if("Y".equals(prfDtlVO.getStdvAnaYn())){
				stddevValSql = postPC01Sql.getStddevSql();
			}
			//분산분석 VRN_ANA_YN
			if("Y".equals(prfDtlVO.getVrnAnaYn())){
				varianceValSql = postPC01Sql.getVarianceSql();
			}
			//평균분석 AVG_ANA_YN
			if("Y".equals(prfDtlVO.getAvgAnaYn())){
				avgValSql = postPC01Sql.getAvgSql();
			}
			//유일값수분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				unqCntSql = postPC01Sql.getUnqCntSql();
			}
			//최소빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				minCntValSql = postPC01Sql.getMinCntValSql();
			}
			//최대빈도값분석
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()).equals("Y")){
				maxCntValSql = postPC01Sql.getMaxCntValSql();
			}
			
			dateYnSql  = postPC01Sql.getDateYnSql();
			telYnSql   = postPC01Sql.getTelYnSql();
			spaceRtSql = postPC01Sql.getSpaceRtSql();
			crlfYnSql  = postPC01Sql.getCrlfYnSql();
			alphaYnSql  = postPC01Sql.getAlphaYnSql();
			dataFmtSql  = postPC01Sql.getDataFmtSql();
			numYnSql    = postPC01Sql.getNumYnSql();
			hundRtSql   = postPC01Sql.getHundRtSql();
			cntRtSql    = postPC01Sql.getCntRtSql();
			
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
			PostPC02Sql postPC02Sql = new PostPC02Sql(sqlGenMap);
			
			totalCountSql = postPC02Sql.getTotalCountSql();
			errDataSql = postPC02Sql.getErrorDataSql();
			errCountSql = postPC02Sql.getErrorCountSql();
			errPatternSql = postPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			PostPC03Sql postPC03Sql = new PostPC03Sql(sqlGenMap);
			
			totalCountSql = postPC03Sql.getTotalCountSql();
			errDataSql = postPC03Sql.getErrorDataSql();
			errCountSql = postPC03Sql.getErrorCountSql();
			errPatternSql = postPC03Sql.getErrorPatternSql();
			datePatSql = postPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			PostPC04Sql postPC04Sql = new PostPC04Sql(sqlGenMap);
			
			totalCountSql = postPC04Sql.getTotalCountSql();
			errDataSql = postPC04Sql.getErrorDataSql();
			errCountSql = postPC04Sql.getErrorCountSql();
			errPatternSql = postPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			PostPC05Sql postPC05Sql = new PostPC05Sql(sqlGenMap);
			
			totalCountSql = postPC05Sql.getTotalCountSql();
			errDataSql = postPC05Sql.getErrorDataSql();
			errCountSql = postPC05Sql.getErrorCountSql();
			errPatternSql = postPC05Sql.getErrorPatternSql();
			userPatSql = postPC05Sql.getErrorPatternSqlforjava();
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			PostPT01Sql postPT01Sql = new PostPT01Sql(sqlGenMap);
			
			totalCountSql = postPT01Sql.getTotalCountSql();
			errDataSql = postPT01Sql.getErrorDataSql();
			errCountSql = postPT01Sql.getErrorCountSql();
			errPatternSql = postPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			PostPT02Sql postPT02Sql = new PostPT02Sql(sqlGenMap);
			
			totalCountSql = postPT02Sql.getTotalCountSql();
			errDataSql = postPT02Sql.getErrorDataSql();
			errCountSql = postPT02Sql.getErrorCountSql();
			errPatternSql = postPT02Sql.getErrorPatternSql();
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
