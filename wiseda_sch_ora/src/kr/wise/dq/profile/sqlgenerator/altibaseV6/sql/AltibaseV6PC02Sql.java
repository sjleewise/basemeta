package kr.wise.dq.profile.sqlgenerator.altibaseV6.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AltibaseV6PC02Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//유효값분석 상세
	private WamPrfEfvaAnaVO prfDtlVO =  new WamPrfEfvaAnaVO();
	
	//테이블명
	private  String dbcTblNm = new String();
	//컬럼명
	private String dbcColNm = new String();
	//추가조건
	private String adtCndSql = new String();
	
	
	public AltibaseV6PC02Sql(Map<String, Object> sqlGenMap){
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
		
		//유효값분석 상세정보
		this.prfDtlVO =  (WamPrfEfvaAnaVO) sqlGenMap.get("prfDtlVO");
	}
	
	
	//유효값분석 총건수
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
		
		strQuery.append(" SELECT COUNT(M."+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n           FROM " +dbcTblNm);
		strQuery.append("\n          WHERE 1 = 1 ");
		strQuery.append("\n            AND "+dbcColNm+" IS NOT NULL" );   
		if(null != adtCndSql){
			strQuery.append("\n            AND "+adtCndSql );
		}
		strQuery.append("\n          ) M ");
		//사용자정의 유효값
		if(prfDtlVO.getEfvaAnaKndCd().equals("USER")){
			strQuery.append(getUserDfValSql());
		}
		//코드테이블 
		if(prfDtlVO.getEfvaAnaKndCd().equals("CTBL")){
			strQuery.append(getCodeTblSql());
		}
		//메타연계
		if(prfDtlVO.getEfvaAnaKndCd().equals("META")){
			strQuery.append(getMetaCodeSql());
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
		
		strQuery.append(" SELECT M."+dbcColNm+" ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n           FROM " +dbcTblNm);
		strQuery.append("\n          WHERE 1 = 1 ");
		strQuery.append("\n            AND "+dbcColNm+" IS NOT NULL" );
		if(null != adtCndSql){
			strQuery.append("\n            AND "+adtCndSql );
		}
		strQuery.append("\n          ) M ");
		//사용자정의 유효값
		if(prfDtlVO.getEfvaAnaKndCd().equals("USER")){
			strQuery.append(getUserDfValSql());
		}
		//코드테이블 
		if(prfDtlVO.getEfvaAnaKndCd().equals("CTBL")){
			strQuery.append(getCodeTblSql());
		}
		//메타연계
		if(prfDtlVO.getEfvaAnaKndCd().equals("META")){
//			strQuery.append(getMetaCodeSql());
// 업무규칙 등록시 시스템변수 사용
			strQuery.append("\n    AND  $SYS_COND_DMN_VV "); 
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
		
		strQuery.append(" SELECT  M."+dbcColNm+" ");
		strQuery.append("\n        ,COUNT( M."+dbcColNm+") AS COUNT ");
		strQuery.append("\n   FROM " + "(SELECT " +dbcColNm); 
		strQuery.append("\n           FROM " +dbcTblNm);
		strQuery.append("\n          WHERE 1 = 1 ");
		strQuery.append("\n            AND "+dbcColNm+" IS NOT NULL" );
		if(null != adtCndSql){
			strQuery.append("\n            AND "+adtCndSql );
		}
		strQuery.append("\n          ) M ");
		//사용자정의 유효값
		if(prfDtlVO.getEfvaAnaKndCd().equals("USER")){
			strQuery.append(getUserDfValSql());
		}
		//코드테이블 
		if(prfDtlVO.getEfvaAnaKndCd().equals("CTBL")){
			strQuery.append(getCodeTblSql());
		}
		//메타연계
		if(prfDtlVO.getEfvaAnaKndCd().equals("META")){
			strQuery.append(getMetaCodeSql());
		}
		strQuery.append("\n  GROUP BY " + dbcColNm + " ");
		
		sqlStr = strQuery.toString();
		
		return sqlStr; 
	}
	
	//사용자정의유효값
	public String getUserDfValSql(){
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		boolean isFirst = true ;
		strQuery.append("\n  WHERE M. " + dbcColNm + " NOT IN (");
		
		//사용자정의유효값
		for (WamPrfEfvaUserDfVO userDfVO : prfDtlVO.getWamPrfEfvaUserDfVO()) {
			if(userDfVO.getUserDfEfva().indexOf(",") != -1) {
				if(isFirst){
					if(userDfVO.getUserDfEfva().lastIndexOf(",") == 1)
						userDfVO.getUserDfEfva().substring(0, userDfVO.getUserDfEfva().length());
					
					strQuery.append("'" + userDfVO.getUserDfEfva().replaceAll(",", "','") + "'");

					isFirst = false;
				}else{
					if(userDfVO.getUserDfEfva().lastIndexOf(",") == 1)
						userDfVO.getUserDfEfva().substring(0, userDfVO.getUserDfEfva().length());
					
					strQuery.append(",'" + userDfVO.getUserDfEfva().replaceAll(",", "','") + "'");
				}
			} else {
				if(isFirst){
					strQuery.append("'"+userDfVO.getUserDfEfva()+"'");
					isFirst = false;
				}else{
					strQuery.append(",'"+userDfVO.getUserDfEfva()+"'");
				}
			}
		}
		strQuery.append(" )	");
		
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
	
	//코드테이블 사용
	public String getCodeTblSql(){
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		//코드테이블 명
		String cdTblDbcTblNm = new String();
		if(null != prfDtlVO.getCdTblDbSchPdm()){
			cdTblDbcTblNm = prfDtlVO.getCdTblDbSchPdm() + "."+ prfDtlVO.getCdTblDbcTblNm();
		}else{
			cdTblDbcTblNm = prfDtlVO.getCdTblDbcTblNm();
		}
		
		strQuery.append("\n        LEFT OUTER JOIN " + cdTblDbcTblNm + "  CD");
		strQuery.append("\n          ON M." + dbcColNm + " = CD." + prfDtlVO.getCdTblDbcColNm() );
		if(null != prfDtlVO.getCdTblCdId()){
			strQuery.append("\n         AND CD." + prfDtlVO.getCdTblCdIdColNm() + " = '" + prfDtlVO.getCdTblCdId() + "'"  );
		}
		if(null != prfDtlVO.getCdTblAdtCndSql()){
			strQuery.append("\n         AND " +  prfDtlVO.getCdTblAdtCndSql() );
		}
		strQuery.append("\n  WHERE CD." + prfDtlVO.getCdTblDbcColNm() + " IS NULL");
		
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
	
	//메타도메인 연계
	public String getMetaCodeSql(){
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		String metaCdValTypCd = prfDtlVO.getMetaDmnItfVO().getCdValTypCd();
		
		// O 단일코드 L 목록성코드 C 복합코드
		//목록성코드 :  LEFT OUTER JOIN 
		if(metaCdValTypCd.equals("L") ){
			//목록테이블
			String lstEntyPnm = prfDtlVO.getMetaDmnItfVO().getLstEntyPnm();
			//목록테이블컬럼
			String pdmColPnm =  prfDtlVO.getMetaDmnItfVO().getPdmColPnm();
			
			strQuery.append("\n        LEFT OUTER JOIN " + lstEntyPnm + "  CD");
			strQuery.append("\n          ON M." + dbcColNm + " = CD." + pdmColPnm );
			strQuery.append("\n  WHERE CD." + prfDtlVO.getCdTblDbcColNm() + " IS NULL");
		}
			
		//공통코드 : NOT IN 
		if(UtilString.isBlank(metaCdValTypCd) ||  "O".equals(metaCdValTypCd) ){
			boolean isFirst = true ;
			
			//도메인 유효값이 없을 경우....
			if (prfDtlVO.getMetaDmnCdValItfVO() == null || prfDtlVO.getMetaDmnCdValItfVO().isEmpty()) {
				strQuery.append("\n  WHERE M." + dbcColNm + " IS NOT NULL");
			} else {
				strQuery.append("\n  WHERE M." + dbcColNm + " NOT IN (");
				
				//메타유효값
				for (MetaDmnCdValItfVO metaCodeVO : prfDtlVO.getMetaDmnCdValItfVO()) {
					if(isFirst){
						strQuery.append("'"+metaCodeVO.getCdVal()+"'");
						isFirst = false;
					}else{
						strQuery.append(",'"+metaCodeVO.getCdVal()+"'");
					}
				}
			strQuery.append(" )	");
			}
		}
		
		
		
		sqlStr = strQuery.toString();
		return sqlStr; 
	}
	
}
