package kr.wise.dq.vrfcrule.sqlgenerator.altibaseV6.sql;

import java.util.HashMap;
import java.util.Map;

import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AltibaseV6PC01Sql {
	
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
	
	
	public AltibaseV6PC01Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 마스터 정보
		if(null != prfMstrVO.getDbSchPnm()){
			this.dbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm();
		}else{
			this.dbcTblNm = prfMstrVO.getDbcTblNm();
		}
		
		this.dbcColNm = prfMstrVO.getDbcColNm();
		this.adtCndSql = prfMstrVO.getAdtCndSql();
		
		this.prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
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
		strQuery.append("\n                INNER JOIN "+dbcTblNm+" B ");
		strQuery.append("\n                   ON B."+dbcColNm+" != A.MINVAL1 ");
		strQuery.append("\n                  AND B."+dbcColNm+" != A.MAXVAL1 ");
		strQuery.append("\n                  AND B."+dbcColNm+" IS NOT NULL" );   
		strQuery.append("\n          GROUP BY A.MINVAL1, A.MAXVAL1 ");
		strQuery.append("\n         ) A ");
		strQuery.append("\n         INNER JOIN "+dbcTblNm+" C ");
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
		
		strQuery.append(" SELECT MAX(LEN) AS MAXLEN ");
		strQuery.append("\n        ,MIN(LEN) AS MINLEN ");
		strQuery.append("\n   FROM (SELECT LENGTH("+dbcColNm+") AS LEN "); 
		strQuery.append("\n           FROM "+dbcTblNm+" ");
		strQuery.append("\n          WHERE 1 = 1 ");
		strQuery.append("\n            AND "+dbcColNm+" IS NOT NULL" );   
		if(null != adtCndSql)
		{
			strQuery.append("\n            AND "+adtCndSql );   
		}
		strQuery.append("\n        ) ");
		
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


	//에러패턴 for java
	public String getErrorPatternSqlforjava() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT " + dbcColNm + " AS PATTERN");
		strQuery.append("\n        ,COUNT("+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );

		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
}
