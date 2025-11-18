package kr.wise.portal.dashboard.service;

public class DqMainChartVO {
	private String brAnaDgr;
	private int brCnt;
	private int brAnaCnt;
	private int brErrCnt;
	private int csCnt;
	private int imCnt;
	private String brQs;
	
	private String prfAnaDgr;
	private int prfCnt;
	private int prfAnaCnt;
	private int prfErrCnt;
	private String prfQs;
	private String schLnm;
	private String errRate;
	
	public String getBrAnaDgr() {
		return brAnaDgr;
	}
	public void setBrAnaDgr(String brAnaDgr) {
		this.brAnaDgr = brAnaDgr;
	}
	public int getBrCnt() {
		return brCnt;
	}
	public void setBrCnt(int brCnt) {
		this.brCnt = brCnt;
	}
	public int getBrAnaCnt() {
		return brAnaCnt;
	}
	public void setBrAnaCnt(int brAnaCnt) {
		this.brAnaCnt = brAnaCnt;
	}
	public int getBrErrCnt() {
		return brErrCnt;
	}
	public void setBrErrCnt(int brErrCnt) {
		this.brErrCnt = brErrCnt;
	}
	public int getCsCnt() {
		return csCnt;
	}
	public void setCsCnt(int csCnt) {
		this.csCnt = csCnt;
	}
	public int getImCnt() {
		return imCnt;
	}
	public void setImCnt(int imCnt) {
		this.imCnt = imCnt;
	}
	public String getBrQs() {
		return brQs;
	}
	public void setBrQs(String brQs) {
		this.brQs = brQs;
	}
	public String getPrfAnaDgr() {
		return prfAnaDgr;
	}
	public void setPrfAnaDgr(String prfAnaDgr) {
		this.prfAnaDgr = prfAnaDgr;
	}
	public int getPrfCnt() {
		return prfCnt;
	}
	public void setPrfCnt(int prfCnt) {
		this.prfCnt = prfCnt;
	}
	public int getPrfAnaCnt() {
		return prfAnaCnt;
	}
	public void setPrfAnaCnt(int prfAnaCnt) {
		this.prfAnaCnt = prfAnaCnt;
	}
	public int getPrfErrCnt() {
		return prfErrCnt;
	}
	public void setPrfErrCnt(int prfErrCnt) {
		this.prfErrCnt = prfErrCnt;
	}
	public String getPrfQs() {
		return prfQs;
	}
	public void setPrfQs(String prfQs) {
		this.prfQs = prfQs;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DqMainChartVO [brAnaDgr=").append(brAnaDgr)
				.append(", brCnt=").append(brCnt).append(", brAnaCnt=")
				.append(brAnaCnt).append(", brErrCnt=").append(brErrCnt)
				.append(", csCnt=").append(csCnt).append(", imCnt=")
				.append(imCnt).append(", brQs=").append(brQs)
				.append(", prfAnaDgr=").append(prfAnaDgr).append(", prfCnt=")
				.append(prfCnt).append(", prfAnaCnt=").append(prfAnaCnt)
				.append(", prfErrCnt=").append(prfErrCnt).append(", prfQs=")
				.append(prfQs)
				.append(", schLnm=").append(schLnm).append("]");
		return builder.toString();
	}
	public String getSchLnm() {
		return schLnm;
	}
	public void setSchLnm(String schLnm) {
		this.schLnm = schLnm;
	}
	public String getErrRate() {
		return errRate;
	}
	public void setErrRate(String errRate) {
		this.errRate = errRate;
	}
	
	
	
	
	
}
