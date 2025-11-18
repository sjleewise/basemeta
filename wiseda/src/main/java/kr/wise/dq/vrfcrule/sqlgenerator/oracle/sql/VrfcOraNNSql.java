package kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VrfcOraNNSql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  VrfcruleVO VrfcruleVO = new VrfcruleVO();
	//날짜형식분석 상세
	private WamPrfDtfrmAnaVO prfDtlVO =  new WamPrfDtfrmAnaVO();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	private String vrfcNm = new String();
	
	private String vrfcRule = new String();
	
	private String vrfcDescn = new String();
	
	//DBMS코드
	private String dbmsTypCd = new String();	
	
	public VrfcOraNNSql(Map<String, Object> sqlGenMap) {
		
		this.sqlGenMap = sqlGenMap;
		this.VrfcruleVO =  (VrfcruleVO) sqlGenMap.get("vrfcVO");
		//진단대상 테이블명
		if(null != VrfcruleVO.getDbSchPnm()){
			this.dbcTblNm = VrfcruleVO.getDbSchPnm() + "."+ VrfcruleVO.getDbcTblNm();
		}else{
			this.dbcTblNm = VrfcruleVO.getDbcTblNm();
		}
		//진단대상 컬럼명
		this.dbcColNm = VrfcruleVO.getDbcColNm();
		
		this.vrfcNm    = VrfcruleVO.getVrfcNm();
		
		this.vrfcRule  = VrfcruleVO.getVrfcRule();
		
		vrfcRule = vrfcRule.replace("컬럼", dbcColNm);   
		
		this.vrfcDescn = VrfcruleVO.getVrfcDescn();
		
		this.dbmsTypCd = VrfcruleVO.getDbmsTypCd();			
		
		//추가조건
		this.adtCndSql = UtilString.null2Blank(VrfcruleVO.getAdtCndSql());
	
		
	}
	
	
	//총건수
	public String getTotalCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append("\n SELECT COUNT(*) AS ANA_CNT ");
		strQuery.append("\n   FROM " + dbcTblNm+" OT "); 
		strQuery.append("\n  WHERE 1 = 1               "); 
		
		if(!adtCndSql.equals(""))
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
			
		strQuery.append("\n SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND (  "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}	
		
		if(!adtCndSql.equals(""))
		{
			strQuery.append("\n    AND "+adtCndSql );   
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
		
		strQuery.append("\n SELECT " + dbcColNm);
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}	
		
		
		if(!adtCndSql.equals(""))
		{
			strQuery.append("\n    AND "+adtCndSql );   
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
		
		strQuery.append("\n SELECT " + dbcColNm);
		strQuery.append("\n      , COUNT("+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NULL  " );
			}	
		
		if(!adtCndSql.equals(""))
		{
			strQuery.append("\n    AND "+adtCndSql );   
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
		
		strQuery.append("\n SELECT " + dbcColNm);
		strQuery.append("\n      , COUNT("+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM " + dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NULL OR "+dbcColNm+" = '' )  " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}	
		  		
		
		if(!adtCndSql.equals(""))
        {
            strQuery.append("\n    AND "+ adtCndSql );   
        }

		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	
	
	
}
