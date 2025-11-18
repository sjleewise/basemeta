package kr.wise.portal.dashboard.service;

public class TotalCountVO  {
	
	private String cntName;
	private String totCnt;
	private String page;
	private String auth;
	private String errCnt;
	private String dtm;
	private String errRate;
	private String noExe;
	
	//dqmain화면  프로파일건수 표 출력용
	private String colCnt;
	private String tblCnt;
	private String sumRate;
	private String dqiLnm;
	private String uppDqiNm;
	private int uppDqiCnt;
	
	
	
	public int getUppDqiCnt() {
		return uppDqiCnt;
	}
	public void setUppDqiCnt(int uppDqiCnt) {
		this.uppDqiCnt = uppDqiCnt;
	}
	public String getUppDqiNm() {
		return uppDqiNm;
	}
	public void setUppDqiNm(String uppDqiNm) {
		this.uppDqiNm = uppDqiNm;
	}
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
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
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public String getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}
	public String getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(String errCnt) {
		this.errCnt = errCnt;
	}
	public String getDtm() {
		return dtm;
	}
	public void setDtm(String dtm) {
		this.dtm = dtm;
	}
	public String getErrRate() {
		return errRate;
	}
	public void setErrRate(String errRate) {
		this.errRate = errRate;
	}
	public String getNoExe() {
		return noExe;
	}
	public void setNoExe(String noExe) {
		this.noExe = noExe;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getTblCnt() {
		return tblCnt;
	}
	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}
	public String getSumRate() {
		return sumRate;
	}
	public void setSumRate(String sumRate) {
		this.sumRate = sumRate;
	}
	

}
