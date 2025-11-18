package kr.wise.dq.vrfcrule.sqlgenerator.oracle.sql;

import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VrfcOraCdSql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//컬럼분석 VO
	private WamPrfColAnaVO prfDtlVO = new WamPrfColAnaVO();
	
	//프로파일 마스터VO
	private  VrfcruleVO VrfcruleVO = new VrfcruleVO();
	
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
	
	//코드SQL
	private String codeSql = new String();
	
	//DBMS코드
	private String dbmsTypCd = new String();	
	
	//추가조건
	private String adtCndSql = new String();
	
	private String vrfcNm = new String();
	
	private String vrfcRule = new String();
	
	private String vrfcDescn = new String();
	
	private String codeIdColNm = new String();
	
	private String cdTypCd = new String();
	
	
	public VrfcOraCdSql(Map<String, Object> sqlGenMap){
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
		
		this.vrfcDescn = VrfcruleVO.getVrfcDescn();
		
		this.dbmsTypCd = VrfcruleVO.getDbmsTypCd();			
		
		//추가조건
		this.adtCndSql = UtilString.null2Blank(VrfcruleVO.getAdtCndSql());
		
		//코드 SQL
		this.codeSql =   VrfcruleVO.getCdSql();
		
		this.codeIdColNm =   VrfcruleVO.getCdIdColNm();
		
		this.prfDtlVO =  (WamPrfColAnaVO) sqlGenMap.get("prfDtlVO");
		
		this.cdTypCd =   VrfcruleVO.getCdTypCd();
		
		String sTemp = "";
		
		if(cdTypCd.equals("LC")) { //목록성코드
			sTemp += "\n SELECT 코드 " ;  
			sTemp += "\n   FROM ( ";
			sTemp += "\n   "+ codeSql;
			sTemp += "\n   ) C ";
			
		}else if(cdTypCd.equals("CC")) { //공통코드
			sTemp += "\n SELECT 코드 " ;  
			sTemp += "\n   FROM ( ";
			sTemp += "\n   "+ codeSql;
			sTemp += "\n   ) C ";
			sTemp += "\n WHERE 코드분류 = '" + this.vrfcRule + "' ";
		}
		codeSql = sTemp;
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
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
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
		strQuery.append("\n  FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n               FROM " +dbcTblNm+" OT ");
		strQuery.append("\n              WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	 
		  		
		strQuery.append("\n                AND " + dbcColNm + " NOT IN (" ); 
		strQuery.append("\n                                            " + codeSql );
		strQuery.append("\n                                            )" );
		
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
		strQuery.append("\n                FROM " +dbcTblNm+ " OT");
		strQuery.append("\n               WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	
		  		
		strQuery.append("\n                 AND " + dbcColNm + " NOT IN (" ); 
		strQuery.append("\n                                            " + codeSql );
		strQuery.append("\n                                             )" );
				
		if(!adtCndSql.equals("")){
			strQuery.append("\n            AND "+adtCndSql );
		}
		
		strQuery.append("\n          ) M ");
		
		sqlStr = strQuery.toString();		

		
		return sqlStr; 
	}
	
	//에러패턴
	public String getErrorPatternSql() 
	{
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		

		strQuery.append("   SELECT M." + dbcColNm + " ");
		strQuery.append("\n      , COUNT( M."+dbcColNm+") AS \"COUNT\" ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n                FROM " +dbcTblNm+" OT ");
		strQuery.append("\n               WHERE 1 = 1 ");
		if(dbmsTypCd.equals("ORA") || dbmsTypCd.equals("TIB")) {
			strQuery.append("\n    AND ( "+dbcColNm+" IS NOT NULL OR "+dbcColNm+" != '' )  " );
			}else if(dbmsTypCd.equals("MRA") || dbmsTypCd.equals("MYS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL AND "+dbcColNm+" != ''   " );
			}else if(dbmsTypCd.equals("POS")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL  " );
			}else if(dbmsTypCd.equals("CBR")) {
				strQuery.append("\n    AND  "+dbcColNm+" IS NOT NULL   " );
			}	
		  		
		strQuery.append("\n                 AND " + dbcColNm + " NOT IN (" ); 
		strQuery.append("\n                                            " + codeSql );
		strQuery.append("\n                                             )" );
				
		if(!adtCndSql.equals("")){
			strQuery.append("\n            AND "+adtCndSql );
		}
		
		strQuery.append("\n          ) M ");
		//사용자정의 유효값
		
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	
	
}
