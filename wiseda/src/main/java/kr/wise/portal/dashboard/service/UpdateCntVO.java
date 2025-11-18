package kr.wise.portal.dashboard.service;

public class UpdateCntVO {

	private String termTypeNm;
	private String curCnt;
	private String insCnt;
	private String updCnt;
	private String delCnt;
	private String page;
	private String auth;
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getTermTypeNm() {
		return termTypeNm;
	}
	public void setTermTypeNm(String termTypeNm) {
		this.termTypeNm = termTypeNm;
	}
	public String getCurCnt() {
		return curCnt;
	}
	public void setCurCnt(String curCnt) {
		this.curCnt = curCnt;
	}
	public String getInsCnt() {
		return insCnt;
	}
	public void setInsCnt(String insCnt) {
		this.insCnt = insCnt;
	}
	public String getUpdCnt() {
		return updCnt;
	}
	public void setUpdCnt(String updCnt) {
		this.updCnt = updCnt;
	}
	public String getDelCnt() {
		return delCnt;
	}
	public void setDelCnt(String delCnt) {
		this.delCnt = delCnt;
	}

}
