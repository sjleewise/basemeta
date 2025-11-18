package kr.wise.dq.dashboard.service;

public class WidgetsChartVO {

	private String bizareaNm;
	private float errRate;
	private int seq;
	private String anaActCnt;
	private String errCnt;
	
	public String getAnaActCnt() {
		return anaActCnt;
	}
	public void setAnaActCnt(String anaActCnt) {
		this.anaActCnt = anaActCnt;
	}
	public String getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(String errCnt) {
		this.errCnt = errCnt;
	}
	public String getBizareaNm() {
		return bizareaNm;
	}
	public void setBizareaNm(String bizareaNm) {
		this.bizareaNm = bizareaNm;
	}
	public float getErrRate() {
		return errRate;
	}
	public void setErrRate(float errRate) {
		this.errRate = errRate;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	
}
