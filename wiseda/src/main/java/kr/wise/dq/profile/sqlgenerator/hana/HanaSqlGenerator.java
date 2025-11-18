package kr.wise.dq.profile.sqlgenerator.hana;

import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPC01Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPC02Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPC03Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPC04Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPC05Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPT01Sql;
import kr.wise.dq.profile.sqlgenerator.hana.sql.HanaPT02Sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HanaSqlGenerator {

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
			HanaPC01Sql hanPC01Sql = new HanaPC01Sql(sqlGenMap);
			
			//분석건수
			totalCountSql = hanPC01Sql.getTotalCountSql();
			
			//널건수
			if(UtilString.null2Blank(prfDtlVO.getAonlYn()) .equals("Y")){
				nullCountSql = hanPC01Sql.getNullCountSql(); 
				//스페이스건수
				spaceCountSql = hanPC01Sql.getSpaceCountSql(); 
			}
			
			//최대최소값
			if(UtilString.null2Blank(prfDtlVO.getMinMaxValAnaYn()) .equals("Y")){
				minMaxSql = hanPC01Sql.getMinMaxSql();
			}
			//최대최소길이
			if(UtilString.null2Blank(prfDtlVO.getLenAnaYn()) .equals("Y")){
				minMaxLenSql = hanPC01Sql.getMinMaxLenSql();
			}
			//카디널리티
			if(UtilString.null2Blank(prfDtlVO.getCrdAnaYn()) .equals("Y")){
				errPatternSql = hanPC01Sql.getPatternSql();
			}
			//패턴분석
			if("Y".equals(prfDtlVO.getPatAnaYn())){
				userPatSql = hanPC01Sql.getErrorPatternSqlforjava();
			}
			
			dateYnSql  = hanPC01Sql.getDateYnSql();
			telYnSql   = hanPC01Sql.getTelYnSql();
			spaceRtSql = hanPC01Sql.getSpaceRtSql();
			crlfYnSql  = hanPC01Sql.getCrlfYnSql();
			alphaYnSql  = hanPC01Sql.getAlphaYnSql();
//			dataFmtSql  = hanPC01Sql.getDataFmtSql();
			numYnSql    = hanPC01Sql.getNumYnSql();
			hundRtSql   = hanPC01Sql.getHundRtSql();
			cntRtSql    = hanPC01Sql.getCntRtSql();
			
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
			logger.debug("PC02");
			//유효값분석 sql 생성기
			HanaPC02Sql hanPC02Sql = new HanaPC02Sql(sqlGenMap);
			
			totalCountSql = hanPC02Sql.getTotalCountSql();
			errDataSql = hanPC02Sql.getErrorDataSql();
			errCountSql = hanPC02Sql.getErrorCountSql();
			errPatternSql = hanPC02Sql.getErrorPatternSql();
		}
		
		//날짜형식 분석
		if(prfKndCd.equals("PC03")){
			//날짜형식 sql 생성기
			HanaPC03Sql hanPC03Sql = new HanaPC03Sql(sqlGenMap);
			
			totalCountSql = hanPC03Sql.getTotalCountSql();
			errDataSql = hanPC03Sql.getErrorDataSql();
			errCountSql = hanPC03Sql.getErrorCountSql();
			errPatternSql = hanPC03Sql.getErrorPatternSql();
			datePatSql = hanPC03Sql.getErrorPatternSqlforjava();
		}
		
		//범위 분석
		if(prfKndCd.equals("PC04")){
			//날짜형식 sql 생성기
			HanaPC04Sql hanPC04Sql = new HanaPC04Sql(sqlGenMap);
			
			totalCountSql = hanPC04Sql.getTotalCountSql();
			errDataSql = hanPC04Sql.getErrorDataSql();
			errCountSql = hanPC04Sql.getErrorCountSql();
			errPatternSql = hanPC04Sql.getErrorPatternSql();
		}
		
		//패턴 분석
		if(prfKndCd.equals("PC05")){
			//날짜형식 sql 생성기
			HanaPC05Sql hanPC05Sql = new HanaPC05Sql(sqlGenMap);
			
			totalCountSql = hanPC05Sql.getTotalCountSql();
			errDataSql = hanPC05Sql.getErrorDataSql();
			errCountSql = hanPC05Sql.getErrorCountSql();
			errPatternSql = hanPC05Sql.getErrorPatternSql();
			userPatSql = hanPC05Sql.getErrorPatternSqlforjava(); 
		}
		//관계 분석
		if(prfKndCd.equals("PT01")){
			//날짜형식 sql 생성기
			HanaPT01Sql hanPT01Sql = new HanaPT01Sql(sqlGenMap);
			
			totalCountSql = hanPT01Sql.getTotalCountSql();
			errDataSql = hanPT01Sql.getErrorDataSql();
			errCountSql = hanPT01Sql.getErrorCountSql();
			errPatternSql = hanPT01Sql.getErrorPatternSql();
		}
		
		//중복 분석
		if(prfKndCd.equals("PT02")){
			//날짜형식 sql 생성기
			HanaPT02Sql hanPT02Sql = new HanaPT02Sql(sqlGenMap);
			
			totalCountSql = hanPT02Sql.getTotalCountSql();
			errDataSql = hanPT02Sql.getErrorDataSql();
			errCountSql = hanPT02Sql.getErrorCountSql();
			errPatternSql = hanPT02Sql.getErrorPatternSql();
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
