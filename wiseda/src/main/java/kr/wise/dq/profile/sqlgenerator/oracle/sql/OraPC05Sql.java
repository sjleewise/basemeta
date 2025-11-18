package kr.wise.dq.profile.sqlgenerator.oracle.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OraPC05Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//패턴분석 사용자정의
	private List<WamPrfPtrAnaVO> prfDtlVO =  new ArrayList<WamPrfPtrAnaVO>();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String(); 
	//추가조건
	private String adtCndSql = new String();
	
	public OraPC05Sql(Map<String, Object> sqlGenMap){
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
		
		//패턴분석 상세정보
		this.prfDtlVO =  (List<WamPrfPtrAnaVO>) sqlGenMap.get("prfDtlVO");
		//패턴분석 코드
	}
	
	
	public static final String G_TO_STRING 	= "9999999999aaaaaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAA~`!@#$%^&*()_-+=|{}[]:;?/<>,.|\"\"B";
	public static final String G_FROM_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()_-+=|{}[]:;?/<>,.|\"\" ";
	public static final String G_TRANS_STRING = "9Aa~`!@#$%^&*()_-+=|{}[]:;?/<>,.|\"\"B";
	
	//총건수
	public String getTotalCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm); 
		strQuery.append("\n  WHERE 1 = 1 ");
		strQuery.append("\n    AND "+dbcColNm+" IS NOT NULL" );
		if(null != adtCndSql && !adtCndSql.isEmpty())
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
		
		strQuery.append(" SELECT COUNT(*) AS ERR_CNT ");
		//패턴분석 SQL
		strQuery.append(getErrorValidPatternCondSql());
		
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
		strQuery.append("\n        ,PATTERN ");
		//패턴분석 SQL
		strQuery.append(getErrorValidPatternCondSql());
		
		sqlStr = strQuery.toString();		
		return sqlStr; 
	}
	
	//에러패턴
	public String getErrorPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT PATTERN ");
		strQuery.append("\n        ,COUNT(PATTERN) AS COUNT ");
		//패턴분석 SQL//패턴분석 SQL
		strQuery.append(getErrorValidPatternCondSql());
		
		strQuery.append("\n  GROUP BY PATTERN ");
		
		sqlStr = strQuery.toString();
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

		if(null != adtCndSql && !adtCndSql.isEmpty())
		{
			strQuery.append("\n                             AND "+adtCndSql);
		}
		
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	public String getErrorValidPatternCondSql()  {
		StringBuffer strQuery = new StringBuffer();
		String sqlStr = new String();
		boolean isFirst = true ;
		
		strQuery.append("\n   FROM (SELECT "+dbcColNm );
		strQuery.append("\n               ,TRANSLATE(TRAN_DATA1,TRAN_DATA2, LPAD('C',TRAN_DATA2_LENGTH, 'C')) AS PATTERN ");
		strQuery.append("\n           FROM (SELECT "+dbcColNm );
		strQuery.append("\n                        ,TRAN_DATA1 ");
		strQuery.append("\n                        ,NVL(REPLACE(TRANSLATE(TRAN_DATA1,'"+G_TRANS_STRING+"', LPAD(' ', "+G_TRANS_STRING.length()+",' ')), ' ', ''),'C') AS TRAN_DATA2 ");
		strQuery.append("\n                        ,LENGTH(NVL(REPLACE(TRANSLATE(TRAN_DATA1,'"+G_TRANS_STRING+"', LPAD(' ', "+G_TRANS_STRING.length()+",' ')), ' ', ''),'C')) AS TRAN_DATA2_LENGTH ");
		strQuery.append("\n                   FROM (SELECT "+dbcColNm);
		strQuery.append("\n                               ,TRANSLATE("+dbcColNm+",'"+G_FROM_STRING+"' ");
		strQuery.append("\n                                                    ,'"+G_TO_STRING+"') AS TRAN_DATA1 ");
		strQuery.append("\n                           FROM "+dbcTblNm );
		strQuery.append("\n                          WHERE 1 = 1 ");
		strQuery.append("\n                             AND "+dbcColNm+" IS NOT NULL" );
		if(null != adtCndSql && !adtCndSql.isEmpty())
		{
			strQuery.append("\n                             AND "+adtCndSql);
		}
		strQuery.append("\n                         ) ");
		strQuery.append("\n                 ) ");
		strQuery.append("\n         ) ");
		
		//사용자 정의 존재할 경우 
		if( 0 < prfDtlVO.size() ){
			int i=0;
			
			for(WamPrfPtrAnaVO patVO : prfDtlVO ){
				if(isFirst && patVO.getUserDfPtr().indexOf("^") != 0){
					strQuery.append("\n  WHERE RTRIM(LTRIM(PATTERN)) NOT IN (");
					strQuery.append("'"+patVO.getUserDfPtr()+"'");
					isFirst = false;
				}else if(patVO.getUserDfPtr().indexOf("^") != 0){
					strQuery.append(", '"+patVO.getUserDfPtr()+"'");
				}
				
				i++;
				if(i == prfDtlVO.size()) strQuery.append(" )");
			}
			
			
		}
		
		//사용자 정의 존재할 경우 
		if( 0 < prfDtlVO.size() ){
			for(WamPrfPtrAnaVO patVO : prfDtlVO ){
				if(isFirst && patVO.getUserDfPtr().indexOf("^") == 0){
					strQuery.append("\n  WHERE NOT REGEXP_LIKE(");
					strQuery.append(dbcColNm + ", '"+patVO.getUserDfPtr()+"'");
					strQuery.append(" )");
					isFirst = false;
				} else if(!isFirst && patVO.getUserDfPtr().indexOf("^") == 0) {
					strQuery.append("\n  AND NOT REGEXP_LIKE(");
					strQuery.append(dbcColNm + ", '"+patVO.getUserDfPtr()+"'");
					strQuery.append(" )");
				}
			}
		}
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
	
	
}
