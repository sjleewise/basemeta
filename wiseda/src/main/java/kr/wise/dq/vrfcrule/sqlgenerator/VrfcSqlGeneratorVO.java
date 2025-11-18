package kr.wise.dq.vrfcrule.sqlgenerator;
import java.io.Serializable ;


public class VrfcSqlGeneratorVO implements Serializable
{
	//공통
	public String totalCount;		//전체건수
	public String errorCount;		//에러건수
	public String errorData;		//에러 데이터
	public String errorPattern;		//에러 패턴별 건수
	
	//컬럼분석
	public String nullCountSql;		//널건수
	public String spaceCountSql;	//SPACE건수
	public String minMaxSql;		//최대최소값
	public String minMaxLenSql;		//최대최소길이
	
	//날짜분석 용 sql 
	private String datePatSql;
	
	//사용자패턴용 sql
	private String userPatSql;
	
	//일자여부 
	private String dateYnSql;
	//전화변호여부  
	private String telYnSql;
	//공백율  
	private String spaceRtSql;
	//엔터값여부  
	private String crlfYnSql;
	//영문여부  
	private String alphaYnSql;
	//데이터포멧  
	private String dataFmtSql;
	//숫자여부
	private String numYnSql;
	//백단위율
	private String hundRtSql;
	//건수율
	private String cntRtSql;
		
	public String prfKndCd;		
	public String dbConnTrgId;		
	
	public String ruleRelId;		
	public String vrfcId;
	public String vrfcNm;
	public String vrfcRule;
	public String vrfcDescn;
	public String vrfcTyp;
	
	
	public String getErrorData() {
		return errorData;
	}
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}
	public String getErrorPattern() {
		return errorPattern;
	}
	public void setErrorPattern(String errorPattern) {
		this.errorPattern = errorPattern;
	}
	public String getNullCountSql() {
		return nullCountSql;
	}
	public void setNullCountSql(String nullCountSql) {
		this.nullCountSql = nullCountSql;
	}
	public String getSpaceCountSql() {
		return spaceCountSql;
	}
	public void setSpaceCountSql(String spaceCountSql) {
		this.spaceCountSql = spaceCountSql;
	}
	public String getMinMaxSql() {
		return minMaxSql;
	}
	public void setMinMaxSql(String minMaxSql) {
		this.minMaxSql = minMaxSql;
	}
	public String getMinMaxLenSql() {
		return minMaxLenSql;
	}
	public void setMinMaxLenSql(String minMaxLenSql) {
		this.minMaxLenSql = minMaxLenSql;
	}
	
	public String getPrfKndCd() {
		return prfKndCd;
	}
	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDatePatSql() {
		return datePatSql;
	}
	public void setDatePatSql(String datePatSql) {
		this.datePatSql = datePatSql;
	}
	public String getUserPatSql() {
		return userPatSql;
	}
	public void setUserPatSql(String userPatSql) {
		this.userPatSql = userPatSql;
	}
	
	public String getDateYnSql() {
		return dateYnSql;
	}
	public void setDateYnSql(String dateYnSql) {
		this.dateYnSql = dateYnSql;
	}
	public String getTelYnSql() {
		return telYnSql;
	}
	public void setTelYnSql(String telYnSql) {
		this.telYnSql = telYnSql;
	}
	public String getSpaceRtSql() {
		return spaceRtSql;
	}
	public void setSpaceRtSql(String spaceRtSql) {
		this.spaceRtSql = spaceRtSql;
	}
	public String getCrlfYnSql() {
		return crlfYnSql;
	}
	public void setCrlfYnSql(String crlfYnSql) {
		this.crlfYnSql = crlfYnSql;
	}
	public String getAlphaYnSql() {
		return alphaYnSql;
	}
	public void setAlphaYnSql(String alphaYnSql) {
		this.alphaYnSql = alphaYnSql;
	}
	public String getDataFmtSql() {
		return dataFmtSql;
	}
	public void setDataFmtSql(String dataFmtSql) {
		this.dataFmtSql = dataFmtSql;
	}
	public String getNumYnSql() {
		return numYnSql;
	}
	public void setNumYnSql(String numYnSql) {
		this.numYnSql = numYnSql;
	}
	public String getHundRtSql() {
		return hundRtSql;
	}
	public void setHundRtSql(String hundRtSql) {
		this.hundRtSql = hundRtSql;
	}
	public String getCntRtSql() {
		return cntRtSql;
	}
	public void setCntRtSql(String cntRtSql) {
		this.cntRtSql = cntRtSql;
	}
	
	public String getRuleRelId() {
		return ruleRelId;
	}
	public void setRuleRelId(String ruleRelId) {
		this.ruleRelId = ruleRelId;
	}
	public String getVrfcId() {
		return vrfcId;
	}
	public void setVrfcId(String vrfcId) {
		this.vrfcId = vrfcId;
	}
	public String getVrfcNm() {
		return vrfcNm;
	}
	public void setVrfcNm(String vrfcNm) {
		this.vrfcNm = vrfcNm;
	}
	public String getVrfcRule() {
		return vrfcRule;
	}
	public void setVrfcRule(String vrfcRule) {
		this.vrfcRule = vrfcRule;
	}
	public String getVrfcDescn() {
		return vrfcDescn;
	}
	public void setVrfcDescn(String vrfcDescn) {
		this.vrfcDescn = vrfcDescn;
	}
	
	public String getVrfcTyp() {
		return vrfcTyp;
	}
	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}
		  	 
	
}
