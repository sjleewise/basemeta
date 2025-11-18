package kr.wise.dq.profile.sqlgenerator.mssql.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsqPT02Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//중복컬럼 
	private List<WamPrfUnqColVO> prfUnqColVO = new ArrayList<WamPrfUnqColVO>();
	
	//테이블명
	private  String  dbcTblNm = new String();
	//중복컬럼
	private  String dbcColNm = new String();
	//추가조건
	private String  adtCndSql = new String();
	
	
	public MsqPT02Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		this.prfUnqColVO =  (List<WamPrfUnqColVO>) sqlGenMap.get("prfDtlVO");
		
		//테이블정보
		if(null != prfMstrVO.getDbSchPnm()){
			this.dbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm() ;
		}else{
			this.dbcTblNm = prfMstrVO.getDbcTblNm();
		}
		//추가조건
		this.adtCndSql = prfMstrVO.getAdtCndSql();
		
		
		boolean isFirst = true;
		StringBuffer strQuery = new StringBuffer();
		for(WamPrfUnqColVO vo : prfUnqColVO ){
			if(isFirst){
				strQuery.append(vo.getDbcColNm());
				isFirst = false ;
			}else{
				strQuery.append(" ,"+vo.getDbcColNm());
			}
		}
		//중복컬럼
		this.dbcColNm = strQuery.toString();
	}
	
	//관계분석 총건수
	public String getTotalCountSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT(*) AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm ); 
		strQuery.append("\n  WHERE 1 = 1 ");
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
		
		strQuery.append(" SELECT SUM(COUNT(*)) AS ERR_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm ); 
		strQuery.append("\n  WHERE 1 = 1 ");
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}		
		strQuery.append("\n  GROUP BY "+dbcColNm);
		strQuery.append("\n  HAVING COUNT(*) > 1 ");
		
		sqlStr = strQuery.toString();
		return sqlStr;
	}
	
	//에러데이터 
	//프로파일 업무규칙 이관시 업무규칙 분석 SQL 로 사용
	public String getErrorDataSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT "+dbcColNm );
		strQuery.append("\n        ,COUNT(*) AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm ); 
		strQuery.append("\n  WHERE 1 = 1 ");
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}		
		strQuery.append("\n  GROUP BY "+dbcColNm);
		strQuery.append("\n  HAVING COUNT(*) > 1 ");
		
		sqlStr = strQuery.toString();		
		return sqlStr; 
	}
	
	//에러패턴
	public String getErrorPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT "+dbcColNm );
		strQuery.append("\n        ,COUNT(*) AS COUNT ");
		strQuery.append("\n   FROM "+dbcTblNm ); 
		strQuery.append("\n  WHERE 1 = 1 ");
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}		
		strQuery.append("\n  GROUP BY "+dbcColNm);
		strQuery.append("\n  HAVING COUNT(*) > 1 ");
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
}
