package kr.wise.dq.dashboard.service;

public class DqdashSystemVO {
	
	private String bizareaNm;   //대상업무(dataDQList)
    private String tblCnt;         //테이블(dataDQList)
    private String colCnt;         //컬럼(dataDQList)
    private String braCnt;         //업무규칙(dataDQList)
    private String anaCnt;         //오류발생업무규칙(dataDQList)
	private String errCnt;         //오류갯수(dataDQList)
	private float errRate;      //오류율(dataDQList)
	private String nonPrc;       //미진행(dataDQListSub)
	private String errPrc;       //원인등록(dataDQListSub)
	private String impPrc;       //개선안등록(dataDQListSub)
	private String impEnd;       //완료(dataDQListSub)
	
	
	private String csCnt;       //개선계획완료
	private String imCnt;       //개선결과완료
	
	public String getBizareaNm() {
		return bizareaNm;
	}
	public void setBizareaNm(String bizareaNm) {
		this.bizareaNm = bizareaNm;
	}
	public String getTblCnt() {
		return tblCnt;
	}
	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getBraCnt() {
		return braCnt;
	}
	public void setBraCnt(String braCnt) {
		this.braCnt = braCnt;
	}
	public String getAnaCnt() {
		return anaCnt;
	}
	public void setAnaCnt(String anaCnt) {
		this.anaCnt = anaCnt;
	}
	public String getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(String errCnt) {
		this.errCnt = errCnt;
	}
	public float getErrRate() {
		return errRate;
	}
	public void setErrRate(float errRate) {
		this.errRate = errRate;
	}
	public String getNonPrc() {
		return nonPrc;
	}
	public void setNonPrc(String nonPrc) {
		this.nonPrc = nonPrc;
	}
	public String getErrPrc() {
		return errPrc;
	}
	public void setErrPrc(String errPrc) {
		this.errPrc = errPrc;
	}
	public String getImpPrc() {
		return impPrc;
	}
	public void setImpPrc(String impPrc) {
		this.impPrc = impPrc;
	}
	public String getImpEnd() {
		return impEnd;
	}
	public void setImpEnd(String impEnd) {
		this.impEnd = impEnd;
	}
	public String getCsCnt() {
		return csCnt;
	}
	public void setCsCnt(String csCnt) {
		this.csCnt = csCnt;
	}
	public String getImCnt() {
		return imCnt;
	}
	public void setImCnt(String imCnt) {
		this.imCnt = imCnt;
	}

}
