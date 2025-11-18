package kr.wise.portal.dashboard.service;

public class StandardChartVO {
	
	//SUBJ_ID SUBJ_NM CNT CNT_Y CNT_N STDRATE
	private String subjId;
	private String subjNm;
	private int cnt;
	private int cntY;
	private int cntN;
	private float stdRate;
	
	
	public String getSubjId() {
		return subjId;
	}
	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}
	public String getSubjNm() {
		return subjNm;
	}
	public void setSubjNm(String subjNm) {
		this.subjNm = subjNm;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getCntY() {
		return cntY;
	}
	public void setCntY(int cntY) {
		this.cntY = cntY;
	}
	public int getCntN() {
		return cntN;
	}
	public void setCntN(int cntN) {
		this.cntN = cntN;
	}
	public float getStdRate() {
		return stdRate;
	}
	public void setStdRate(float stdRate) {
		this.stdRate = stdRate;
	}
	

}
