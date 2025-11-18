package kr.wise.dq.profile.sqlgenerator.altibaseV6.sql;

import java.util.HashMap;
import java.util.Map;
import kr.wise.commons.util.UtilString;

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
	
	//스키마Id
	private String dbSchId = new String();
	
	//스키마물리명
	private String dbSchPnm = new String();
	
	//테이블명
	private  String dbcTblNm = new String();
	//스키마명 없는 테이블명
	private  String dbcTblNmStr = new String();
	//컬럼명
	private String dbcColNm = new String();
	//데이터타입
	private String dataType = new String();
	//추가조건
	private String adtCndSql = new String();
	//데이터타입
	private  String dbcDataType = new String();
	
	
	public AltibaseV6PC01Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//프로파일 마스터 정보
		if(null != prfMstrVO.getDbSchPnm()){
			this.dbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm();
		}else{
			this.dbcTblNm = prfMstrVO.getDbcTblNm();
		}
		this.dbSchId = prfMstrVO.getDbSchId();
		
		this.dbSchPnm = prfMstrVO.getDbSchPnm();
		
		this.dbcTblNmStr = prfMstrVO.getDbcTblNm();
		
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
	
	
	//일자여부 
	public String getDateYnSql() 
	{		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('DATE','DATETIME','TIMESTAMP') THEN 'Y'                                                          ");           
		sb.append("\n             WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR','CHAR')  THEN                                                               ");
		sb.append("\n                 (SELECT CASE WHEN (ROUND(SUM(WHERE_DATE_CNT) / SUM(TOT_CNT), 0) * 100) > 80 THEN 'Y' ELSE 'N' END WHERE_DATE_YN ");                  
		sb.append("\n                    FROM ( SELECT CASE WHEN SUBSTR(" + dbcColNm + ",1,2) IN ('19','20') THEN 1 ELSE 0 END AS WHERE_DATE_CNT      ");
		sb.append("\n                                , 1 AS TOT_CNT                                                                                   ");
		sb.append("\n                             FROM " + dbcTblNm + "                                                                               ");
		sb.append("\n                            WHERE " + dbcColNm + " IS NOT NULL                                                                   ");
		sb.append("\n                              AND LENGTH(TRIM(" + dbcColNm + ")) >= 2)                                                           ");
		sb.append("\n                          )            ELSE 'N'                                                                                  ");
		sb.append("\n             END AS DATE_YN                                                                                                      ");
		sb.append("\n    FROM DUAL                                                                                                    ");
		
		
		/*
		sb.append("\n   FROM WAT_DBC_COL                                                                                                              ");
		sb.append("\n  WHERE DB_SCH_ID  = '" + dbSchId + "'                                                                                           ");
		sb.append("\n    AND DBC_TBL_NM = '" + dbcTblNmStr + "'                                                                                       ");
		sb.append("\n    AND DBC_COL_NM = '" + dbcColNm + "'                                                                                          ");
		*/	
				
		return sb.toString(); 
	}	
	
	//전화변호여부 
	public String getTelYnSql() 
	{		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR','CHAR') THEN                                                              ");                  
		sb.append("\n 		        (SELECT CASE WHEN (ROUND(SUM(WHERE_TEL_CNT) / SUM(TOT_CNT), 0) * 100) > 80 THEN 'Y' ELSE 'N'                      ");
		sb.append("\n 		                 END WHERE_TEL_YN                                                                                         ");
		sb.append("\n 		          FROM (SELECT CASE WHEN SUBSTR(" + dbcColNm + ",1,2) = '02' THEN 1                                               ");
		sb.append("\n 		                            WHEN SUBSTR(" + dbcColNm + ",1,3) IN ('051','053','032','062','042','052','044','031','033','043','041','063','061','054','055','064','070','080','010','011','016') THEN 1 ");                                     
		sb.append("\n 		                            ELSE 0 END AS WHERE_TEL_CNT                                                                   ");
		sb.append("\n 		                     , 1 AS TOT_CNT                                                                                       ");
		sb.append("\n 		                  FROM " + dbcTblNm + "                                                                                   ");
		sb.append("\n 		                 WHERE " + dbcColNm + " IS NOT NULL                                                                       ");
		sb.append("\n 		                   AND LENGTH(TRIM(" + dbcColNm + ")) >= 2)                                                               ");
		sb.append("\n 		         )                                                                                                                ");
		sb.append("\n 		     ELSE 'N'                                                                                                             ");
		sb.append("\n 		 END AS TEL_YN                                                                                                            ");
		sb.append("\n   FROM DUAL                                                                                                                     ");
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                                             "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                                             ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                                                  ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                                        ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                                       ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                                    ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                                       ");
		*/	
				
		return sb.toString(); 
	}	
	
	//공백율
	public String getSpaceRtSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR') THEN                                                    ");                 
        sb.append("\n                      (SELECT SUM(SPACE_CNT) / SUM(TOT_CNT) * 100 AS SPACE_RT                                   ");
        sb.append("\n                         FROM (SELECT CASE WHEN INSTR(" + dbcColNm + ",' ') > 0 THEN 1 ELSE 0 END AS SPACE_CNT  ");                         
        sb.append("\n                                    ,1 AS TOT_CNT                                                               ");
        sb.append("\n                                 FROM " + dbcTblNm + "                                                          ");
        sb.append("\n                                WHERE " + dbcColNm + " IS NOT NULL))                                            ");
        sb.append("\n             ELSE 0                                                                                             ");
        sb.append("\n         END AS SPACE_RT                                                                                        ");
        sb.append("\n   FROM DUAL                                                                                                    ");
        
        /*
        sb.append("\n   FROM DBA_TABLES A                                                                                            "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                            ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                                 ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                       ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                      ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                   ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                      ");
		*/	
				
		return sb.toString(); 
	}	
	
	//엔터값 여부
	public String getCrlfYnSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR') THEN                                                           ");                  
		sb.append("\n 		        (SELECT CASE WHEN SUM(ENTER_CNT) > 0 THEN 'Y' ELSE 'N' END                                            ");
		sb.append("\n 		           FROM (SELECT CASE WHEN INSTR(" + dbcColNm + ",chr(13)||chr(10)) > 0 THEN 1 ELSE 0 END AS ENTER_CNT ");                          
		sb.append("\n 		                   FROM " + dbcTblNm + "                                                                      ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL)                                                         ");
		sb.append("\n 		        )                                                                                                     ");
		sb.append("\n 		  ELSE 'N'                                                                                                    ");
		sb.append("\n 		END AS CRLF_YN                                                                                                ");
		sb.append("\n   FROM DUAL                                                                                                         ");
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                                 "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                                 ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                                      ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                            ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                           ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                        ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                           ");
		*/	
				
		return sb.toString(); 
	}	
	
	//영문 여부
	public String getAlphaYnSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR') THEN                                               ");            
		sb.append("\n 	             (SELECT CASE WHEN ROUND(SUM(ENG_CNT) / SUM(TOT_CNT), 0) * 100 >= 80 THEN 'Y' ELSE 'N' END     ");          
		sb.append("\n 	                FROM (SELECT CASE WHEN LENGTH(NVL(TRIM(ENG_VALUE),0)) <= 3 THEN 1 ELSE 0 END AS ENG_CNT    ");
		sb.append("\n 	                           , 1 AS TOT_CNT                                                                  ");
		sb.append("\n 	                        FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(TRIM(ENG_VALUE),'a',''),'b',''),'c',''),'d',''),'e',''),'f',''),'g',''),'h',''),'i',''),'j',''),'k',''),'l',''),'m',''),'n',''),'o',''),'p',''),'q',''),'r',''),'s',''),'t',''),'u',''),'v',''),'w',''),'x',''),'y',''),'z','') AS ENG_VALUE                ");                               
		sb.append("\n 	                                 FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(TRIM(" + dbcColNm + "),'A',''),'B',''),'C',''),'D',''),'E',''),'F',''),'G',''),'H',''),'I',''),'J',''),'K',''),'L',''),'M',''),'N',''),'O',''),'P',''),'Q',''),'R',''),'S',''),'T',''),'U',''),'V',''),'W',''),'X',''),'Y',''),'Z','') AS ENG_VALUE  ");                                     
		sb.append("\n 	                                         FROM " + dbcTblNm + "                                                   ");
		sb.append("\n 	                                        WHERE " + dbcColNm + " IS NOT NULL                                   ");
		sb.append("\n 	                                       )                                                                   ");
		sb.append("\n 	                              )                                                                            ");
		sb.append("\n 	                       )                                                                                   ");
		sb.append("\n 	               )                                                                                           ");
		sb.append("\n 	          ELSE 'N'                                                                                         ");
		sb.append("\n 	      END AS ALPHA_YN                                                                                      ");
		sb.append("\n 	  FROM DUAL                                                                                                ");
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                          "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                          ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                               ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                     ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                    ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                 ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                    ");
		*/	
				
		return sb.toString(); 
	}	
	
	//데이터포멧
	public String getDataFmtSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR','VARCHAR2', 'CHAR') THEN                                                   ");                                                         
		sb.append("\n 		        (SELECT CASE WHEN TRAN_DATA1 IN ('99-999-9999','999-999-9999','999-9999-9999','99999999999','99999999','9999-99-99','9999.99.99','9999A99A99A','99999999B99:99:99') THEN TRAN_DATA1  ELSE 'X' END                                                               ");
		sb.append("\n 		           FROM (SELECT TRANSLATE(" + dbcColNm + ",'0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()_-+=|{}[]:;?/<>,.|\" '  ,'9999999999aaaaaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAA~`!@#$%^&*()_-+=|{}[]:;?/<>,.|\"B') AS TRAN_DATA1 ");                            
		sb.append("\n 		                   FROM " + dbcTblNm + "                                                                            ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL)                                                           ");
		sb.append("\n 		          WHERE ROWNUM = 1                                                                                    ");
		sb.append("\n 		         )                                                                                                    ");
		sb.append("\n 		 ELSE 'X'                                                                                                     ");
		sb.append("\n 		END AS DATA_FMT                                                                                               ");
		sb.append("\n 	FROM DUAL                                                                                                         ");
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                          "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                          ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                               ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                     ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                    ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                 ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                    ");
		*/	
				
				
		return sb.toString(); 
	}	
	
	//숫자여부 
	public String getNumYnSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('VARCHAR2','VARCHAR','CHAR') THEN                                                  ");                  
		sb.append("\n 		        (SELECT CASE WHEN MIN(NVL(LENGTH(TRIM(NUM_VALUE)),0)) = 0 THEN 'Y' ELSE 'N' END                       ");
		sb.append("\n 		           FROM (SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(TRIM(" + dbcColNm + "),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9',''),'0',''),'-',''),'/',''),'.',''),'년',''),'월',''),'일',''),':','') AS NUM_VALUE ");                           
		sb.append("\n 		                   FROM " + dbcTblNm + "                                                                      ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL                                                          ");
		sb.append("\n 		                 )                                                                                            ");
		sb.append("\n 		         )                                                                                                    ");
		sb.append("\n 		    ELSE 'N'                                                                                                  ");
		sb.append("\n 		END AS NUM_YN                                                                                                 ");
		sb.append("\n 	FROM DUAL                                                                                                         ");
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                                 "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                                 ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                                      ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                            ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                           ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                        ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                           ");
		*/
		
		
		return sb.toString(); 
	}	
	
	//백단위율
	public String getHundRtSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT CASE WHEN '"+  this.dbcDataType +"' IN ('NUMBER','FLOAT', 'INT','INTEGER','BIGINT','DOUBLE','NUMERIC') THEN                       ");                
		sb.append("\n 		        (SELECT SUM(NUM_CNT) / SUM(TOT_CNT) * 100                                                                  ");
		sb.append("\n 		           FROM (SELECT CASE WHEN SUBSTR(LTRIM(TO_CHAR(" + dbcColNm + ")),-3) = '000' THEN 1 ELSE 0 END AS NUM_CNT ");
		sb.append("\n 		                      , 1 AS TOT_CNT                                                                               ");
		sb.append("\n 		                   FROM " + dbcTblNm + "                                                                           ");
		sb.append("\n 		                  WHERE " + dbcColNm + " IS NOT NULL                                                               ");
		sb.append("\n 		                 )                                                                                                 ");
		sb.append("\n 		         )                                                                                                         ");
		sb.append("\n 		  ELSE 0                                                                                                           ");
		sb.append("\n 		END AS HUND_RT                                                                                                     ");
		sb.append("\n 	FROM DUAL                                                                                                              ");
		
		
		/*
		sb.append("\n   FROM DBA_TABLES A                                                                                                      "); 
        sb.append("\n        INNER JOIN DBA_TAB_COLUMNS B                                                                                      ");
        sb.append("\n           ON B.OWNER = A.OWNER                                                                                           ");
        sb.append("\n          AND B.TABLE_NAME = A.TABLE_NAME                                                                                 ");                      
		sb.append("\n  WHERE A.OWNER       = '" + dbSchPnm + "'                                                                                ");
		sb.append("\n    AND A.TABLE_NAME  = '" + dbcTblNmStr + "'                                                                             ");
		sb.append("\n    AND B.COLUMN_NAME = '" + dbcColNm + "'                                                                                ");
		*/	
				
		return sb.toString(); 
	}	
	
	//건수율
	public String getCntRtSql() 
	{		
		StringBuffer sb = new StringBuffer();
		 
		sb.append("\n SELECT  NVL(SUM(CNT) / SUM(TOT_CNT) * 100,0) AS CNT_RT       ");  
		sb.append("\n   FROM (SELECT " + dbcColNm + "                              ");
		sb.append("\n              , 1 AS CNT                                      ");
		sb.append("\n              , COUNT(*) AS TOT_CNT                           ");
		sb.append("\n           FROM " + dbcTblNm + "                              ");
		sb.append("\n          WHERE " + dbcColNm + " IS NOT NULL                  ");
		sb.append("\n          GROUP BY " + dbcColNm + "                           ");
		sb.append("\n        )                                                     ");
		
		return sb.toString(); 
	}	
}
