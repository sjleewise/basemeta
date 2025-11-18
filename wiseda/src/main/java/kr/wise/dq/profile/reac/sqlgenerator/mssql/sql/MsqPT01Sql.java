package kr.wise.dq.profile.reac.sqlgenerator.mssql.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsqPT01Sql {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	

	
	private Map<String, Object> sqlGenMap = new HashMap<String, Object>();
	//프로파일 마스터VO
	private  WamPrfMstrVO prfMstrVO = new WamPrfMstrVO();
	//관계분석 VO
	private WamPrfRelTblVO prfDtlVO = new WamPrfRelTblVO();
	//관계컬럼 
	private ArrayList<WamPrfRelColVO> prfRelColVO = new ArrayList<WamPrfRelColVO>();
	
	/*자식테이블*/
	//테이블명
	private  String  chDbcTblNm = new String();
	//컬럼명
	private  String chTblDbcColNm = new String();
	//추가조건
	private String  chAdtCndSql = new String();
	
	/*부모테이블*/
	private  String paDbcTblNm = new String();
	//추가조건
	private String paTblAdtCndSql = new String();
	//컬럼명
	private  String paTblDbcColNm = new String();
	
	//관계컬럼
	private  String relColSql = new String();
	private  String relColAnaSql = new String();
	private  String relColGrpSql = new String();
	
	public MsqPT01Sql(Map<String, Object> sqlGenMap){
		this.sqlGenMap = sqlGenMap;
		this.prfMstrVO =  (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		this.prfDtlVO =  (WamPrfRelTblVO) sqlGenMap.get("prfDtlVO");
		this.prfRelColVO =  prfDtlVO.getWamPrfRelColVO();
		
		//자식테이블정보
		if(null != prfMstrVO.getDbSchPnm()){
			this.chDbcTblNm = prfMstrVO.getDbSchPnm() + "."+ prfMstrVO.getDbcTblNm() ;
		}else{
			this.chDbcTblNm = prfMstrVO.getDbcTblNm();
		}
		//자식테이블 추가조건
		this.chAdtCndSql = prfMstrVO.getAdtCndSql();
		
		//관계테이블정보
		if(null != prfDtlVO.getPaTblDbcSchNm()){
			this.paDbcTblNm = prfDtlVO.getPaTblDbcSchNm() + "."+ prfDtlVO.getPaTblDbcTblNm();
		}else{
			this.paDbcTblNm = prfDtlVO.getPaTblDbcTblNm();
		}
		//부모테이블 추가조건
		this.paTblAdtCndSql = prfDtlVO.getPaTblAdtCndSql();
		
		
		boolean isFirst = true;
		StringBuffer strQuery1 = new StringBuffer();
		StringBuffer strQuery2 = new StringBuffer();
		StringBuffer strQuery3 = new StringBuffer();
		StringBuffer strQuery4 = new StringBuffer();
		StringBuffer strQuery5 = new StringBuffer();
		for(WamPrfRelColVO vo : prfRelColVO ){
			if(isFirst){
				strQuery1.append(vo.getChTblDbcColNm());
				strQuery2.append(vo.getPaTblDbcColNm());
				strQuery3.append("\n           ON C."+vo.getChTblDbcColNm()+" = P."+vo.getPaTblDbcColNm());
				strQuery4.append("\n  WHERE P."+vo.getPaTblDbcColNm()+" IS NULL  ");
				strQuery5.append("C."+vo.getChTblDbcColNm());
				isFirst = false ;
			}else{
	 			strQuery1.append(" ,"+vo.getChTblDbcColNm());
	 			strQuery2.append(" ,"+vo.getPaTblDbcColNm());
	 			strQuery3.append("\n          AND C."+vo.getChTblDbcColNm()+" = P."+vo.getPaTblDbcColNm());
	 			strQuery4.append("\n    AND P."+vo.getPaTblDbcColNm()+" IS NULL  ");
	 			strQuery5.append(" ,C."+vo.getChTblDbcColNm());
			}
		}
		//자식컬럼
		this.chTblDbcColNm = strQuery1.toString();
		this.paTblDbcColNm = strQuery2.toString();
		//관계컬럼
		this.relColSql = strQuery3.toString();
		this.relColAnaSql = strQuery4.toString();
		this.relColGrpSql = strQuery5.toString();
	}
	
	//관계분석 총건수
	public String getTotalCountSql() 
	{
		
		String sqlStr = new String();
		StringBuffer strQuery = new StringBuffer();
		
		strQuery.append(" SELECT COUNT(*) AS ANA_CNT ");
		strQuery.append("\n   FROM "+chDbcTblNm ); 
		strQuery.append("\n  WHERE 1 = 1 ");
		if(null != chAdtCndSql && !chAdtCndSql.equals(""))
		{
			strQuery.append("\n    AND "+chAdtCndSql );   
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
			//관계분석 SQL
			strQuery.append(getRelColSql());
			
			sqlStr = strQuery.toString();
			return sqlStr;
		}
		
		//에러데이터 
		//프로파일 업무규칙 이관시 업무규칙 분석 SQL 로 사용
		public String getErrorDataSql() 
		{
			
			String sqlStr = new String();
			StringBuffer strQuery = new StringBuffer();
			
//			strQuery.append(" SELECT " + chTblDbcColNm);
			strQuery.append(" SELECT " + relColGrpSql);
			//관계분석 SQL
			strQuery.append(getRelColSql());
			
			sqlStr = strQuery.toString();		
			return sqlStr; 
		}
		
		//에러패턴
		public String getErrorPatternSql() 
		{
			String sqlStr = new String();
			StringBuffer strQuery = new StringBuffer();
			
//			strQuery.append(" SELECT " + chTblDbcColNm);
			strQuery.append(" SELECT " + relColGrpSql);
			strQuery.append("\n        , COUNT(*) AS COUNT ");
			//관계분석 SQL
			strQuery.append(getRelColSql());
			
			strQuery.append("\n  GROUP BY "+relColGrpSql);
			
			sqlStr = strQuery.toString();
			return sqlStr; 
		}
		
		//에러패턴
		public String getRelColSql() 
		{
			String sqlStr = new String();
			StringBuffer strQuery = new StringBuffer();
			
			strQuery.append("\n   FROM (SELECT " + chTblDbcColNm );
			strQuery.append("\n           FROM " + chDbcTblNm );
			strQuery.append("\n          WHERE 1 = 1 "  );
			if(null != chAdtCndSql && !chAdtCndSql.equals(""))
			{
				strQuery.append("\n            AND "+chAdtCndSql );   
			}
			strQuery.append("\n         ) C ");
			strQuery.append("\n         LEFT OUTER JOIN (SELECT " + paTblDbcColNm );
			strQuery.append("\n                            FROM "+ paDbcTblNm );
			strQuery.append("\n                           WHERE 1 = 1 " );
			if(null != paTblAdtCndSql && !paTblAdtCndSql.equals(""))
			{
				strQuery.append("\n                             AND "+paTblAdtCndSql );   
			}
			strQuery.append("\n                          ) P  ");
			
			
			strQuery.append("             "+relColSql);
			strQuery.append("  "+relColAnaSql);
			
			sqlStr = strQuery.toString();
			return sqlStr; 
		}
		
	
}
