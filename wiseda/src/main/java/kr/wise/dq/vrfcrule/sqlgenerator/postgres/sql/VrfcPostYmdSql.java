package kr.wise.dq.vrfcrule.sqlgenerator.postgres.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VrfcPostYmdSql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  VrfcruleVO vrfcRuleVO = new VrfcruleVO();
	//날짜형식분석 상세
	private WamPrfDtfrmAnaVO prfDtlVO =  new WamPrfDtfrmAnaVO();
	
	private  String vrfcRule = new String();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	private String vrfcNm = new String();
	
	public VrfcPostYmdSql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.vrfcRuleVO =  (VrfcruleVO) sqlGenMap.get("vrfcVO");
		//진단대상 테이블명
		if(null != vrfcRuleVO.getDbSchPnm()){
			this.dbcTblNm = vrfcRuleVO.getDbSchPnm() + "."+ vrfcRuleVO.getDbcTblNm();
		}else{
			this.dbcTblNm = vrfcRuleVO.getDbcTblNm();
		}
		//진단대상 컬럼명
		this.dbcColNm = vrfcRuleVO.getDbcColNm();
		
		this.vrfcNm  = vrfcRuleVO.getVrfcNm();
		
		this.vrfcRule = vrfcRuleVO.getVrfcRule();
		
		//추가조건
		this.adtCndSql = vrfcRuleVO.getAdtCndSql(); 	
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
		if(vrfcRule.equals("YYYY"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMM")) {
			
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMMDD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
		
		}else if(vrfcRule.equals("YYYYMMDDHH24MISS")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;			
		
		}else if(vrfcRule.equals("YYYY-MM-DD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD_Dash()+" ") ;	
			
		}else if(vrfcRule.equals("YYYY-MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM_Dash()+" ") ;	
			
		}else if(vrfcRule.equals("HH:MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_HHMM_COLON()+" ") ;		
			
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
		if(vrfcRule.equals("YYYY")) 
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMM")) {
			
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMMDD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
		
		}else if(vrfcRule.equals("YYYYMMDDHH24MISS")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;		
			
		}else if(vrfcRule.equals("YYYY-MM-DD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD_Dash()+" ") ;	
			
		}else if(vrfcRule.equals("YYYY-MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM_Dash()+" ") ;
			
		}else if(vrfcRule.equals("HH:MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_HHMM_COLON()+" ") ;		
											
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
		strQuery.append("\n        ,COUNT("+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );
		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		
		//날짜검증 SQL
		if(vrfcRule.equals("YYYY"))
		{
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYY()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMM")) {
			
			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM()+" ") ;
			
		}else if(vrfcRule.equals("YYYYMMDD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD()+" ") ;
		
		}else if(vrfcRule.equals("YYYYMMDDHH24MISS")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDDHH24MISS()+" ") ;	
			
		}else if(vrfcRule.equals("YYYY-MM-DD")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMMDD_Dash()+" ") ;	
			
		}else if(vrfcRule.equals("YYYY-MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_YYYYMM_Dash()+" ") ;	
			
		}else if(vrfcRule.equals("HH:MM")) {

			strQuery.append(" " + getErrorCountDateValidCondSql_HHMM_COLON()+" ") ;		
							
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
		.append("\n    AND (CASE WHEN SUBSTRING(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 7,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 8,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 9,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 10,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 11,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 12,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 13,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 14,1) BETWEEN '0' AND '9'  ")		
		.append("\n     	THEN CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '00' AND '23'  ")
		.append("\n	      		  	  THEN CASE WHEN SUBSTRING(" + dbcColNm + ",11,2) BETWEEN '00' AND '59'  ")
		.append("\n	      			            THEN CASE WHEN SUBSTRING(" + dbcColNm + ",13,2) BETWEEN '00' AND '59'  ")
		.append("\n	      			                      THEN   ")
										.append("\n      		 CASE WHEN SUBSTRING(" + dbcColNm + ",5,2) IN ('01','03','05','07','08','10','12')  ")
										.append("\n	  			      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '31'  ")
										.append("\n		   				        THEN 1  ")
										.append("\n		   		                ELSE 0  ")
										.append("\n		   		            END 	 ")
										.append("\n	                  WHEN SUBSTRING(" + dbcColNm + ",5,2) IN ('04','06','09','11')  ")
										.append("\n	      		      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '30'  ")
										.append("\n	      			            THEN 1  ")
										.append("\n	      			 	        ELSE 0  ")
										.append("\n	      			        END  ")
										.append("\n	      		      WHEN SUBSTRING(" + dbcColNm + ",5,2) ='02'  ")
										.append("\n	      		      THEN CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 4)) = 0  ")
										.append("\n	      			            THEN CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 400)) = 0  ")
										.append("\n	      				                  THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
										.append("\n	      				                            THEN 1  ")
										.append("\n	      			     	                        ELSE 0  ")
										.append("\n	      			     	                    END ")
										.append("\n	      		                          ELSE CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC) ,100)) = 0  ")
										.append("\n	      		     		                        THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
										.append("\n	      		     			                              THEN 1  ")
										.append("\n			      		     		                          ELSE 0  ")
										.append("\n			      		     		                      END  ")
										.append("\n	      		          	                        ELSE  ")
										.append("\n			      		          	                     CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
										.append("\n			      		       		                          THEN 1   ")
										.append("\n			      		       		                          ELSE 0  ")
										.append("\n			      		          	                      END ")
										.append("\n	      		     	  	                   END ")
										.append("\n	      		     	             END  ")
										.append("\n	                       ELSE CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
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
		.append("\n    AND (CASE WHEN SUBSTRING(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 7,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 8,1) BETWEEN '0' AND '9'  ")
		.append("\n      	THEN CASE WHEN SUBSTRING(" + dbcColNm + ",5,2) IN ('01','03','05','07','08','10','12')  ")
		.append("\n	  			      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '31'  ")
		.append("\n		   				        THEN 1  ")
		.append("\n		   		                ELSE 0  ")
		.append("\n		   		            END 	 ")
		.append("\n	                  WHEN SUBSTRING(" + dbcColNm + ",5,2) IN ('04','06','09','11')  ")
		.append("\n	      		      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '30'  ")
		.append("\n	      			            THEN 1  ")
		.append("\n	      			 	        ELSE 0  ")
		.append("\n	      			        END  ")
		.append("\n	      		      WHEN SUBSTRING(" + dbcColNm + ",5,2) ='02'  ")
		.append("\n	      		      THEN CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 4)) = 0  ")
		.append("\n	      			            THEN CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 400)) = 0  ")
		.append("\n	      				                  THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
		.append("\n	      				                            THEN 1  ")
		.append("\n	      			     	                        ELSE 0  ")
		.append("\n	      			     	                    END ")
		.append("\n	      		                          ELSE CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 100)) = 0  ")
		.append("\n	      		     		                        THEN CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
		.append("\n	      		     			                              THEN 1  ")
		.append("\n			      		     		                          ELSE 0  ")
		.append("\n			      		     		                      END  ")
		.append("\n	      		          	                        ELSE  ")
		.append("\n			      		          	                     CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '29'  ")
		.append("\n			      		       		                          THEN 1   ")
		.append("\n			      		       		                          ELSE 0  ")
		.append("\n			      		          	                      END ")
		.append("\n	      		     	  	                   END ")
		.append("\n	      		     	             END  ")
		.append("\n	                       ELSE CASE WHEN SUBSTRING(" + dbcColNm + ",7,2) BETWEEN '01' AND '28'  ")
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
		.append("\n    AND (CASE WHEN SUBSTRING(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 5,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 6,1) BETWEEN '0' AND '9'  ")
		.append("\n   THEN CASE WHEN SUBSTRING(" + dbcColNm + ",5,2) IN ('01','02','03','04','05','06','07','08','09','10','11','12')  ")
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
		.append("\n      AND (CASE WHEN SUBSTRING(" + dbcColNm + " , 1,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + " , 2,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 3,1) BETWEEN '0' AND '9'  ")
		.append("\n		AND SUBSTRING(" + dbcColNm + ", 4,1) BETWEEN '0' AND '9'  ")
		.append("\n   THEN  1 ")
		.append("\n   ELSE 0  ")
		.append("\n   END != 1 ")
		.append("\n  ) ")
		;
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	
	private String getErrorCountDateValidCondSql_YYYYMMDD_Dash() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n     AND (CASE WHEN   SUBSTRING(" + dbcColNm + ", 1,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 2,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 3,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 4,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 5,1)  = '-'                ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 6,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 7,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 8,1)  = '-'                ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 9,1)  BETWEEN '0' AND '9'  ");
		sb.append("\n		           AND SUBSTRING(" + dbcColNm + ", 10,1) BETWEEN '0' AND '9'  ");
		sb.append("\n      	THEN CASE WHEN SUBSTRING(" + dbcColNm + ",6,2) IN ('01','03','05','07','08','10','12')  ");
		sb.append("\n	  			      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '31'      ");
		sb.append("\n		   				        THEN 1                                                       ");
		sb.append("\n		   		                ELSE 0                                                       ");
		sb.append("\n		   		            END 	                                                         ");
		sb.append("\n	                  WHEN SUBSTRING(" + dbcColNm + ",6,2) IN ('04','06','09','11')             ");
		sb.append("\n	      		      THEN CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '30'      ");
		sb.append("\n	      			            THEN 1                                                       ");
		sb.append("\n	      			 	        ELSE 0                                                       ");
		sb.append("\n	      			        END                                                              ");
		sb.append("\n	      		      WHEN SUBSTRING(" + dbcColNm + ",6,2) = '02'                               ");
		sb.append("\n	      		      THEN CASE WHEN (MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 4)) = 0              ");
		sb.append("\n	      			            THEN CASE WHEN MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 400) = 0  ");
		sb.append("\n	      				                  THEN CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '29'            ");
		sb.append("\n	      				                            THEN 1                                                             ");
		sb.append("\n	      			     	                        ELSE 0                                                             ");
		sb.append("\n	      			     	                    END                                                                    ");
		sb.append("\n	      		                          ELSE CASE WHEN MOD(CAST(SUBSTRING(" + dbcColNm + ",1,4) AS NUMERIC), 100) = 0                  ");
		sb.append("\n	      		     		                        THEN CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '28'  ");
		sb.append("\n	      		     			                              THEN 1                                                   ");
		sb.append("\n			      		     		                          ELSE 0                                                   ");
		sb.append("\n			      		     		                      END                                                          ");
		sb.append("\n	      		          	                        ELSE                                                               ");
		sb.append("\n			      		          	                     CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '29'  ");
		sb.append("\n			      		       		                          THEN 1                                                   ");
		sb.append("\n			      		       		                          ELSE 0                                                   ");
		sb.append("\n			      		          	                      END                                                          ");
		sb.append("\n	      		     	  	                   END                                                                     ");
		sb.append("\n	      		     	             END                                                                               ");
		sb.append("\n	                       ELSE CASE WHEN SUBSTRING(" + dbcColNm + ",9,2) BETWEEN '01' AND '28'                           ");
		sb.append("\n	             		             THEN 1                                                                            ");
		sb.append("\n	             		             ELSE 0                                                                            ");
		sb.append("\n	             		         END                                                                           ");
		sb.append("\n            		        END                                                                                ");
		sb.append("\n   		          ELSE 0                                                                                   ");
		sb.append("\n   		      END                                                                                          ");
		sb.append("\n   ELSE 0                                                                                                     ");
		sb.append("\n   END != 1                                                                                                   ");
		sb.append("\n )                                                                                                            ");
		
			
		return sb.toString(); 
	}
	
	private String getErrorCountDateValidCondSql_YYYYMM_Dash() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n    AND (CASE WHEN SUBSTRING(" + dbcColNm + ", 1,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 2,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 3,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 4,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 5,1)  = '-'                        ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 6,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 7,1)  BETWEEN '0' AND '9'          ");
		sb.append("\n             THEN CASE WHEN SUBSTRING(" + dbcColNm + ",6,2) IN ('01','02','03','04','05','06','07','08','09','10','11','12')  ");
		sb.append("\n	          	        THEN 1                                                  ");
		sb.append("\n	          	        ELSE 0                                                  ");
		sb.append("\n	          	    END                                                         ");
		sb.append("\n             ELSE 0                                                            ");
		sb.append("\n   END != 1                                                                    ");
		sb.append("\n  )                                                                            ");
		
		return sb.toString(); 
	}
	
	private String getErrorCountDateValidCondSql_HHMM_COLON() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n    AND (CASE WHEN SUBSTRING(" + dbcColNm + ",1,2) IN (  '00','01','02','03','04','05','06','07','08','09','10','11','12'  ");
		sb.append("\n                                                    , '13','14','15','16','17','18','19','20','21','22','23'       ");
		sb.append("\n                                                   )                                                               ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 3,1)  = ':'                                                            ");
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 4,1)  BETWEEN '0' AND '5'                                              ");		
		sb.append("\n		        AND SUBSTRING(" + dbcColNm + ", 5,1)  BETWEEN '0' AND '9'                                              ");				
		sb.append("\n	          	        THEN 1                                                                                      ");
		sb.append("\n	          	        ELSE 0                                                                                      ");		
		sb.append("\n          END != 1                                                                                                 ");
		sb.append("\n        )                                                                                                          ");
		
		return sb.toString(); 
	}

	
}
