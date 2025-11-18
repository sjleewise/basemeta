package kr.wise.dq.profile.reac.sqlgenerator.tibero.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TiberoPC03Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//날짜형식분석 상세
	private WamPrfDtfrmAnaVO prfDtlVO =  new WamPrfDtfrmAnaVO();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	
	public TiberoPC03Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		//진단대상 테이블명
		if(null != prfMstrVO.getDbSchPnm()){
			this.dbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm();
		}else{
			this.dbcTblNm = prfMstrVO.getDbcTblNm();
		}
		//진단대상 컬럼명
		this.dbcColNm = prfMstrVO.getDbcColNm();
		//추가조건
		this.adtCndSql = prfMstrVO.getAdtCndSql();
		
		//날짜형식분석 상세정보
		this.prfDtlVO =  (WamPrfDtfrmAnaVO) sqlGenMap.get("prfDtlVO");
	}
	
	
	//총건수
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
	
	//에러건수
	public String getErrorCountSql() 
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

		//날짜검증 SQL
		if(prfDtlVO.getDateFrmCd().equals("01"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("02"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("03"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
		}else{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
		}
		
		sqlStr = strQuery.toString();
		
		return sqlStr;
	}
	
	//에러데이터 
	//프로파일 업무규칙 이관시 업무규칙 분석 SQL 로 사용
	public String getErrorDataSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT " + dbcColNm);
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );
		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		
		//날짜검증 SQL
		if(prfDtlVO.getDateFrmCd().equals("01"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("02"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("03"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
		}else{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
		}
		
		sqlStr = strQuery.toString();		
		logger.debug(sqlStr);
		
		return sqlStr; 
	}
	
	//에러패턴
	public String getErrorPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT " + dbcColNm);
		strQuery.append("\n        ,COUNT("+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );
		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		
		//날짜검증 SQL
		if(prfDtlVO.getDateFrmCd().equals("01"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("02"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
			
		}else if(prfDtlVO.getDateFrmCd().equals("03"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
		}else{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
		}
		
		
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}

	//에러패턴 for java
	public String getErrorPatternSqlforjava() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT " + dbcColNm);
		strQuery.append("\n        ,COUNT("+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );
		
		if(null != adtCndSql)
        {
            strQuery.append("\n    AND "+ adtCndSql );   
        }

		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	
	
	
	//YYYYMMDDHH24MISS
	public String getErrorCountDateValidCondSql_YYYYMMDDHH24MISS() {
		StringBuffer strQuery = new StringBuffer();
		String sqlStr = new String();
		
		strQuery
		.append("\n    AND (CASE WHEN SUBSTR(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 7,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 8,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 9,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 10,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 11,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 12,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 13,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 14,1) BETWEEN '0' AND '9'  ")		
		.append("\n     	THEN CASE WHEN SUBSTR(" + dbcColNm + ",9,2) BETWEEN '00' AND '23'  ")
		.append("\n	      		  	  THEN CASE WHEN SUBSTR(" + dbcColNm + ",11,2) BETWEEN '00' AND '59'  ")
		.append("\n	      			            THEN CASE WHEN SUBSTR(" + dbcColNm + ",13,2) BETWEEN '00' AND '59'  ")
		.append("\n	      			                      THEN   ")
										.append("\n      		 CASE WHEN SUBSTR(" + dbcColNm + ",5,2) IN ('01','03','05','07','08','10','12')  ")
										.append("\n	  			      THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '31'  ")
										.append("\n		   				        THEN 1  ")
										.append("\n		   		                ELSE 0  ")
										.append("\n		   		            END 	 ")
										.append("\n	                  WHEN SUBSTR(" + dbcColNm + ",5,2) IN ('04','06','09','11')  ")
										.append("\n	      		      THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '30'  ")
										.append("\n	      			            THEN 1  ")
										.append("\n	      			 	        ELSE 0  ")
										.append("\n	      			        END  ")
										.append("\n	      		      WHEN SUBSTR(" + dbcColNm + ",5,2) ='02'  ")
										.append("\n	      		      THEN CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4), 4)) = 0  ")
										.append("\n	      			            THEN CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4), 400)) = 0  ")
										.append("\n	      				                  THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
										.append("\n	      				                            THEN 1  ")
										.append("\n	      			     	                        ELSE 0  ")
										.append("\n	      			     	                    END ")
										.append("\n	      		                          ELSE CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4) ,100)) = 0  ")
										.append("\n	      		     		                        THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
										.append("\n	      		     			                              THEN 1  ")
										.append("\n			      		     		                          ELSE 0  ")
										.append("\n			      		     		                      END  ")
										.append("\n	      		          	                        ELSE  ")
										.append("\n			      		          	                     CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
										.append("\n			      		       		                          THEN 1   ")
										.append("\n			      		       		                          ELSE 0  ")
										.append("\n			      		          	                      END ")
										.append("\n	      		     	  	                   END ")
										.append("\n	      		     	             END  ")
										.append("\n	                       ELSE CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
										.append("\n	             		             THEN 1  ")
										.append("\n	             		             ELSE 0  ")
										.append("\n	             		         END ")
										.append("\n            		        END  ")
										.append("\n   		          ELSE 0  ")
										.append("\n   		      END   ")		
		.append("\n	      			                      ELSE 0  ")
		.append("\n	      			                  END  ")
		.append("\n	      			            ELSE 0  ")
		.append("\n	      			        END  ")
		.append("\n	      		      ELSE 0  ")
		.append("\n	      	      END  ")
		.append("\n   ELSE 0  ")
		.append("\n   END != 1 ")
		.append("\n ) ")
		;


		sqlStr = strQuery.toString() ;
		
		return sqlStr; 
	}
	
	//YYYYMMDD
	public String getErrorCountDateValidCondSql_YYYYMMDD() {
		StringBuffer strQuery = new StringBuffer();
		String sqlStr = new String();

		strQuery
		.append("\n    AND (CASE WHEN SUBSTR(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 7,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 8,1) BETWEEN '0' AND '9'  ")
		.append("\n      	THEN CASE WHEN SUBSTR(" + dbcColNm + ",5,2) IN ('01','03','05','07','08','10','12')  ")
		.append("\n	  			      THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '31'  ")
		.append("\n		   				        THEN 1  ")
		.append("\n		   		                ELSE 0  ")
		.append("\n		   		            END 	 ")
		.append("\n	                  WHEN SUBSTR(" + dbcColNm + ",5,2) IN ('04','06','09','11')  ")
		.append("\n	      		      THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '30'  ")
		.append("\n	      			            THEN 1  ")
		.append("\n	      			 	        ELSE 0  ")
		.append("\n	      			        END  ")
		.append("\n	      		      WHEN SUBSTR(" + dbcColNm + ",5,2) ='02'  ")
		.append("\n	      		      THEN CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4), 4)) = 0  ")
		.append("\n	      			            THEN CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4), 400)) = 0  ")
		.append("\n	      				                  THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
		.append("\n	      				                            THEN 1  ")
		.append("\n	      			     	                        ELSE 0  ")
		.append("\n	      			     	                    END ")
		.append("\n	      		                          ELSE CASE WHEN (MOD(SUBSTR(" + dbcColNm + ",1,4), 100)) = 0  ")
		.append("\n	      		     		                        THEN CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
		.append("\n	      		     			                              THEN 1  ")
		.append("\n			      		     		                          ELSE 0  ")
		.append("\n			      		     		                      END  ")
		.append("\n	      		          	                        ELSE  ")
		.append("\n			      		          	                     CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
		.append("\n			      		       		                          THEN 1   ")
		.append("\n			      		       		                          ELSE 0  ")
		.append("\n			      		          	                      END ")
		.append("\n	      		     	  	                   END ")
		.append("\n	      		     	             END  ")
		.append("\n	                       ELSE CASE WHEN SUBSTR(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
		.append("\n	             		             THEN 1  ")
		.append("\n	             		             ELSE 0  ")
		.append("\n	             		         END ")
		.append("\n            		        END  ")
		.append("\n   		          ELSE 0  ")
		.append("\n   		      END   ")
		.append("\n   ELSE 0  ")
		.append("\n   END != 1 ")
		.append("\n ) ")
		;

		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	//YYYYMM
	public String getErrorCountDateValidCondSql_YYYYMM(){
		StringBuffer strQuery = new StringBuffer();
		String sqlStr = new String();
		
		strQuery
		.append("\n    AND (CASE WHEN SUBSTR(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n   THEN CASE WHEN SUBSTR(" + dbcColNm + ",5,2) IN ('01','02','03','04','05','06','07','08','09','10','11','12')  ")
		.append("\n		        THEN 1 ")
		.append("\n		        ELSE 0 ")
		.append("\n		    END ")
		.append("\n   ELSE 0  ")
		.append("\n   END != 1 ")
		.append("\n  ) ")
		;
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}	
	
	//YYYY
	public String getErrorCountDateValidCondSql_YYYY(){
		StringBuffer strQuery = new StringBuffer();
		String sqlStr = new String();
		
		strQuery
		.append("\n      AND (CASE WHEN SUBSTR(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTR(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n   THEN  1 ")
		.append("\n   ELSE 0  ")
		.append("\n   END != 1 ")
		.append("\n  ) ")
		;
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
}
