package kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql;

import java.util.HashMap;
import java.util.Map;

import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OraPC04Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//범위분석 상세
	private WamPrfRngAnaVO prfDtlVO =  new WamPrfRngAnaVO();
	
	
	//DBMS코드
	private String dbmsTypCd = new String();	
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	public OraPC04Sql(Map<String, Object> sqlGenMap){
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
		
		this.dbmsTypCd = prfMstrVO.getDbmsTypCd();			
		
		//범위분석 상세정보
		this.prfDtlVO =  (WamPrfRngAnaVO) sqlGenMap.get("prfDtlVO");
		//범위분석 코드
	}
	
	
	//총건수
	public String getTotalCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
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

		strQuery.append(" SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL" ) ;
			}	
		  		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		//범위분석 SQL
		strQuery.append("\n    AND ( " + getRangeSql() +" )");   
		
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
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL" ) ;
			}	
		  		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		//범위분석 SQL
		strQuery.append("\n    AND ( " + getRangeSql() +" )");   
		
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
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL" ) ;
			}	
		  		
		if(null != adtCndSql)
		{
			strQuery.append("\n    AND "+adtCndSql );   
		}
		//범위분석 SQL
		strQuery.append("\n    AND ( " + getRangeSql() +" )");   
		
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	public String getRangeSql(){
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		//범위분석 코드
		String rngOprCd1 = "";
		String rngCncCd = "";
		String rngOprCd2 = "";
		
		if(prfDtlVO.getRngOpr1().equals("01")){
			rngOprCd1 = " >= ";
		}else if(prfDtlVO.getRngOpr1().equals("02")){
			rngOprCd1 = " <= ";
		}else if(prfDtlVO.getRngOpr1().equals("03")){
			rngOprCd1 = " > ";
		}else if(prfDtlVO.getRngOpr1().equals("04")){
			rngOprCd1 = " < ";
		}

		//연결자 존재 할경우
		if(null != prfDtlVO.getRngCnc()){
			
			if(prfDtlVO.getRngCnc().equals("01")){
				rngCncCd = " OR ";
			}else if(prfDtlVO.getRngCnc().equals("02")){
				rngCncCd = " AND ";
			}
			
			if(prfDtlVO.getRngOpr2().equals("01")){
				rngOprCd2 = " >= ";
			}else if(prfDtlVO.getRngOpr2().equals("02")){
				rngOprCd2 = " <= ";
			}else if(prfDtlVO.getRngOpr2().equals("03")){
				rngOprCd2 = " > ";
			}else if(prfDtlVO.getRngOpr2().equals("04")){
				rngOprCd2 = " < ";
			}
		}
		
		
		if(null != prfDtlVO.getRngCnc()){
			strQuery.append( dbcColNm + " " + rngOprCd1 + " '"+ prfDtlVO.getRngEfva1() + "' " + rngCncCd + " " + dbcColNm + " " + rngOprCd2 + " '"+ prfDtlVO.getRngEfva2() + "' ");
		}else{
			strQuery.append( dbcColNm + " " + rngOprCd1 + " '"+ prfDtlVO.getRngEfva1() + "' ");
		}
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
	
}
