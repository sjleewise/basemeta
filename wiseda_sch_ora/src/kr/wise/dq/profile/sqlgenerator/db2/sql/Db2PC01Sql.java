package kr.wise.dq.profile.sqlgenerator.db2.sql;

import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Db2PC01Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//컬럼분석 VO
	private WamPrfColAnaVO prfDtlVO = new WamPrfColAnaVO();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	//데이터타입
	private String dataType = new String();
	
	public Db2PC01Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 마스터 정보
		if(null != prfMstrVO.getDbSchPnm()){
			this.dbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm();
		}else{
			this.dbcTblNm = prfMstrVO.getDbcTblNm();
		}
//		this.dbcTblNm = prfMstrVO.getDbcTblNm();
		
		this.dbcColNm = prfMstrVO.getDbcColNm();
		this.adtCndSql = prfMstrVO.getAdtCndSql();
		
		this.prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
		this.dataType = UtilString.null2Blank(prfDtlVO.getDataType());
	}
	
	
	//컬럼분석 총건수
	public String getTotalCountSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );   
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	//널분석
	public String getNullCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT(1) AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" ");
		strQuery.append("\n  WHERE "+dbcColNm+" IS NULL ");
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		
		sqlStr =strQuery.toString();
		return sqlStr; 
	}	
	
	//스페이스건수 분석
	public String getSpaceCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT("+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" ");
		strQuery.append("\n  WHERE RTRIM(LTRIM("+dbcColNm+")) IS NULL ");
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		
		sqlStr =strQuery.toString();
		return sqlStr; 
	}	
	
	//최대최소값 
	public String getMinMaxSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT MINVAL1 ,MINVAL2 ,MIN(C."+dbcColNm+") AS MINVAL3  ");
		strQuery.append("\n        ,MAXVAL1 ,MAXVAL2 ,MAX(C."+dbcColNm+") AS MAXVAL3 ");
		strQuery.append("\n   FROM (SELECT A.MINVAL1, MIN(B."+dbcColNm+") AS MINVAL2 ");
		strQuery.append("\n               ,A.MAXVAL1, MAX(B."+dbcColNm+") AS MAXVAL2 ");
		strQuery.append("\n           FROM (SELECT MIN("+dbcColNm+") AS MINVAL1, MAX("+dbcColNm+") AS MAXVAL1 ");
		strQuery.append("\n                   FROM "+dbcTblNm+" ");
		strQuery.append("\n                  WHERE "+dbcColNm+" IS NOT NULL" );   
		strQuery.append("\n                ) A ");                                       
		strQuery.append("\n                LEFT OUTER JOIN "+dbcTblNm+" B ");
		strQuery.append("\n                   ON B."+dbcColNm+" != A.MINVAL1 ");
		strQuery.append("\n                  AND B."+dbcColNm+" != A.MAXVAL1 ");
		strQuery.append("\n                  AND B."+dbcColNm+" IS NOT NULL" );   
		strQuery.append("\n          GROUP BY A.MINVAL1, A.MAXVAL1 ");
		strQuery.append("\n         ) A ");
		strQuery.append("\n         LEFT OUTER JOIN "+dbcTblNm+" C ");
		strQuery.append("\n            ON C."+dbcColNm+" != A.MINVAL1 ");
		strQuery.append("\n           AND C."+dbcColNm+" != A.MAXVAL1 ");
		strQuery.append("\n           AND C."+dbcColNm+" != A.MINVAL2 ");
		strQuery.append("\n           AND C."+dbcColNm+" != A.MAXVAL2 ");
		strQuery.append("\n           AND C."+dbcColNm+" IS NOT NULL" );   
		strQuery.append("\n  GROUP BY A.MINVAL1, A.MINVAL2, A.MAXVAL1, A.MAXVAL2 ");
		
		sqlStr =strQuery.toString();
		return sqlStr; 
	}	
	
	//길이분석 
	public String getMinMaxLenSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT MAX(A.COLLEN) AS MAXLEN ");
		strQuery.append("\n        ,MIN(A.COLLEN) AS MINLEN ");
		strQuery.append("\n   FROM (SELECT LENGTH("+dbcColNm+") AS COLLEN "); 
		strQuery.append("\n           FROM "+dbcTblNm+" ");
		strQuery.append("\n          WHERE 1 = 1 ");
		strQuery.append("\n            AND "+dbcColNm+" IS NOT NULL" );   
		if(null != adtCndSql)
		{
			strQuery.append("\n            AND "+adtCndSql );   
		}
		strQuery.append("\n        ) A");
		
		sqlStr =strQuery.toString();
		return sqlStr; 
	}
	
	//카디널리티 
	public String getPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT "+dbcColNm+" ");
		strQuery.append("\n        ,COUNT("+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" ");
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );   
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		strQuery.append("\n  GROUP BY "+dbcColNm+" ");

		sqlStr =strQuery.toString();
		return sqlStr; 
	}	
	
	//표준편차
	public String getStddevSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  dataType +"' IN ('NUMBER','FLOAT', 'INT','INTEGER','BIGINT','DOUBLE','NUMERIC', 'DECIMAL') THEN           ");                  
		sb.append("\n 		         (SELECT TO_CHAR(STDDEV(" + dbcColNm + ")) AS STDDEV_VAL                   ");            
		sb.append("\n 		                   FROM " + dbcTblNm + "                                  ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL                      ");
		sb.append("\n 		                 )                                                        ");
		sb.append("\n 		    ELSE '숫자형 타입이 아닙니다.'                                               ");
		sb.append("\n 		END AS STDDEV_VAL                                                         ");
		sb.append("\n 	FROM SYSIBM.SYSDUMMY1                                                                     ");

		return sb.toString(); 
	}
	
	//분산
	public String getVarianceSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  dataType +"' IN ('NUMBER','FLOAT', 'INT','INTEGER','BIGINT','DOUBLE','NUMERIC', 'DECIMAL') THEN           ");                  
		sb.append("\n 		         (SELECT TO_CHAR(VARIANCE(" + dbcColNm + ")) AS VARIANCE_VAL               ");            
		sb.append("\n 		                   FROM " + dbcTblNm + "                                  ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL                      ");
		sb.append("\n 		                 )                                                        ");
		sb.append("\n 		    ELSE '숫자형 타입이 아닙니다.'                                               ");
		sb.append("\n 		END AS VARIANCE_VAL                                                       ");
		sb.append("\n 	FROM SYSIBM.SYSDUMMY1                                                                     ");
		
		return sb.toString(); 
	}
	
	//평균
	public String getAvgSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  dataType +"' IN ('NUMBER','FLOAT', 'INT','INTEGER','BIGINT','DOUBLE','NUMERIC', 'DECIMAL') THEN           ");                  
		sb.append("\n 		         (SELECT TO_CHAR(AVG(" + dbcColNm + ")) AS AVG_VAL               ");            
		sb.append("\n 		                   FROM " + dbcTblNm + "                                  ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL                      ");
		sb.append("\n 		                 )                                                        ");
		sb.append("\n 		    ELSE '숫자형 타입이 아닙니다.'                                               ");
		sb.append("\n 		END AS AVG_VAL                                                       ");
		sb.append("\n 	FROM SYSIBM.SYSDUMMY1                                                                     ");
		
		return sb.toString(); 
	}
	
	//유일값 수
	public String getUnqCntSql() 
	{		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT COUNT(*) AS UNQ_CNT                                   ");
		sb.append("\n   FROM (                                                     ");
		sb.append("\n          SELECT 1                                            ");
		sb.append("\n            FROM " + dbcTblNm + "                             ");
		sb.append("\n           WHERE " + dbcColNm +" IS NOT NULL                  ");
		sb.append("\n           GROUP BY " + dbcColNm +"                           ");
		sb.append("\n           HAVING COUNT(" + dbcColNm + ") = 1                 ");
		sb.append("\n         )                                                    ");
		
		return sb.toString(); 
	}
	
	//최소빈도값
	public String getMinCntValSql() 
	{		
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT  " + dbcColNm + " AS MIN_CNT_VAL                                  ");
		sb.append("\n   FROM (SELECT  " + dbcColNm + " , COUNT( " + dbcColNm + " ) AS CNT      ");
		sb.append("\n           FROM  " + dbcTblNm + "                                         ");
		sb.append("\n          WHERE  " + dbcColNm + "  IS NOT NULL                            ");
		sb.append("\n          GROUP BY  " + dbcColNm + " )                                    ");
		sb.append("\n  WHERE CNT = (SELECT MIN(CNT2)                                           ");
		sb.append("\n                 FROM (SELECT COUNT( " + dbcColNm + " ) AS CNT2           ");
		sb.append("\n                         FROM  " + dbcTblNm + "                           ");
		sb.append("\n                        WHERE  " + dbcColNm + "  IS NOT NULL              ");
		sb.append("\n                        GROUP BY  " + dbcColNm + " )                      ");
		sb.append("\n              )                                                           ");
		sb.append("\n  fetch first 1 row only                                                        ");

		return sb.toString(); 
	}
	
	//최대빈도값
	public String getMaxCntValSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT  " + dbcColNm + " AS MAX_CNT_VAL                                  ");
		sb.append("\n   FROM (SELECT  " + dbcColNm + " , COUNT( " + dbcColNm + " ) AS CNT      ");
		sb.append("\n           FROM  " + dbcTblNm + "                                         ");
		sb.append("\n          WHERE  " + dbcColNm + "  IS NOT NULL                            ");
		sb.append("\n          GROUP BY  " + dbcColNm + " )                                    ");
		sb.append("\n  WHERE CNT = (SELECT MAX(CNT2)                                           ");
		sb.append("\n                 FROM (SELECT COUNT( " + dbcColNm + " ) AS CNT2           ");
		sb.append("\n                         FROM  " + dbcTblNm + "                           ");
		sb.append("\n                        WHERE  " + dbcColNm + "  IS NOT NULL              ");
		sb.append("\n                        GROUP BY  " + dbcColNm + " )                      ");
		sb.append("\n              )                                                           ");
		sb.append("\n  fetch first 1 row only                                                        ");
		
		return sb.toString(); 
	}
	
}
