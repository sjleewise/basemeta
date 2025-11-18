package kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql;

import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;

import kr.wise.dq.vrfcrule.service.VrfcRuleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VrfcOraYnSql {  
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	//DBMS코드
	private String dbmsTypCd = new String();
	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  VrfcRuleVO VrfcRuleVO = new VrfcRuleVO();
	//유효값분석 상세
	private WamPrfEfvaAnaVO prfDtlVO =  new WamPrfEfvaAnaVO();
	
	private  String vrfcRule = new String();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	
	public VrfcOraYnSql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.VrfcRuleVO =  (VrfcRuleVO) sqlGenMap.get("vrfcVO");
		this.dbmsTypCd = VrfcRuleVO.getDbmsTypCd();	
		this.vrfcRule = UtilString.null2Blank(this.VrfcRuleVO.getVrfcRule()).replaceAll(",","','");  
		
		//진단대상 테이블명
		if(null != VrfcRuleVO.getDbSchPnm()){
			this.dbcTblNm = VrfcRuleVO.getDbSchPnm() + "."+ VrfcRuleVO.getDbcTblNm();
		}else{
			this.dbcTblNm = VrfcRuleVO.getDbcTblNm();
		}
		//진단대상 컬럼명
		this.dbcColNm = VrfcRuleVO.getDbcColNm();
		//추가조건
		this.adtCndSql = UtilString.null2Blank(VrfcRuleVO.getAdtCndSql());
		
		//유효값분석 상세정보
		this.prfDtlVO =  (WamPrfEfvaAnaVO) sqlGenMap.get("prfDtlVO");
		
		/*System.out.println(prfDtlVO.getMetaDmnCdValItfVO().size());
		System.out.println(prfDtlVO.getEfvaAnaKndCd());
		
		for (MetaDmnCdValItfVO vo : prfDtlVO.getMetaDmnCdValItfVO()) {
			System.out.println(vo.getCdVal());
		}*/
		
	}
	
	
	//유효값분석 총건수
	public String getTotalCountSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT("+dbcColNm+") AS ANA_CNT ");
		strQuery.append("\n   FROM "+dbcTblNm+" OT ");
		strQuery.append("\n  WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	   
		  		
		
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
		
		strQuery.append(" SELECT COUNT(M."+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n                FROM " +dbcTblNm+" OT");
		strQuery.append("\n               WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	  
		  		
		strQuery.append("\n                 AND "+dbcColNm+" NOT IN ('" + vrfcRule  + "') ");
				
		if(!adtCndSql.equals("")){
			strQuery.append("\n            AND "+ adtCndSql );
		}
		strQuery.append("\n          ) M ");
		
		
		sqlStr = strQuery.toString();
		
		return sqlStr;
	}
	
	//에러데이터 
	//프로파일 업무규칙 이관시 업무규칙 분석 SQL 로 사용
	public String getErrorDataSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT M."+dbcColNm+" ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm);  
		strQuery.append("\n                FROM " +dbcTblNm+ " OT ");
		strQuery.append("\n               WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	
		  		
		strQuery.append("\n                 AND "+dbcColNm+" NOT IN ('" + vrfcRule  + "') ");
		
		if(!adtCndSql.equals("")){
			strQuery.append("\n            AND "+adtCndSql );
		}
		strQuery.append("\n          ) M ");
				
		sqlStr = strQuery.toString();		
		//logger.debug(sqlStr);
		
		return sqlStr; 
	}
	
	//에러패턴
	public String getErrorPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
				
		strQuery.append(" SELECT  M."+dbcColNm+" ");
		strQuery.append("\n        ,COUNT( M."+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n                FROM " +dbcTblNm+ " OT ");
		strQuery.append("\n               WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	
		  		
		strQuery.append("\n                 AND "+dbcColNm+" NOT IN ('" + vrfcRule  + "') ");
		
		if(!adtCndSql.equals("")){ 
			strQuery.append("\n            AND "+adtCndSql );
		}
		strQuery.append("\n          ) M ");
		
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	
	
}
