package kr.wise.dq.measure.bizrule.service;

public class DqJobVO {

	private int totCnt;
	private String dbmsEnm;    //시스템명
	private String tblNm;      //테이블명
	private String colNm;      //컬럼명
	private String braNm;      //업무규칙명
	private String chasu;      //차수
	private String anaActCnt;  //분석건수
	private String errcnt;     //오류건수
    private String errStatus;  //원인분석현황
    private String impStatus;  //개선활동현황  
    private String userKnm;    //담당자
    private String objId;      //업무ID
    private String userId;	   //사용자ID
    
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getUserKnm() {
		return userKnm;
	}
	public void setUserKnm(String userKnm) {
		this.userKnm = userKnm;
	}
	public int getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}
	public String getDbmsEnm() {
		return dbmsEnm;
	}
	public void setDbmsEnm(String dbmsEnm) {
		this.dbmsEnm = dbmsEnm;
	}
	public String getTblNm() {
		return tblNm;
	}
	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}
	public String getColNm() {
		return colNm;
	}
	public void setColNm(String colNm) {
		this.colNm = colNm;
	}
	public String getBraNm() {
		return braNm;
	}
	public void setBraNm(String braNm) {
		this.braNm = braNm;
	}
	public String getChasu() {
		return chasu;
	}
	public void setChasu(String chasu) {
		this.chasu = chasu;
	}
	public String getAnaActCnt() {
		return anaActCnt;
	}
	public void setAnaActCnt(String anaActCnt) {
		this.anaActCnt = anaActCnt;
	}
	public String getErrcnt() {
		return errcnt;
	}
	public void setErrcnt(String errcnt) {
		this.errcnt = errcnt;
	}
	public String getErrStatus() {
		return errStatus;
	}
	public void setErrStatus(String errStatus) {
		this.errStatus = errStatus;
	}
	public String getImpStatus() {
		return impStatus;
	}
	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}
	
}
