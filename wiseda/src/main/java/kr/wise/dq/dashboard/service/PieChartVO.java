package kr.wise.dq.dashboard.service;

public class PieChartVO {
	
	private int ruleCnt;
	private int notprcCnt;
	private float notprcPst;
	private int finCnt;
	private float finPst;
	private int prcCnt;
	private float prcPst;
	private String exeNum;
	private String dbmsID;
	
	public float getNotprcPst() {
		return notprcPst;
	}
	public void setNotprcPst(float notprcPst) {
		this.notprcPst = notprcPst;
	}
	public float getFinPst() {
		return finPst;
	}
	public void setFinPst(float finPst) {
		this.finPst = finPst;
	}
	public float getPrcPst() {
		return prcPst;
	}
	public void setPrcPst(float prcPst) {
		this.prcPst = prcPst;
	}
	public String getExeNum() {
		return exeNum;
	}
	public void setExeNum(String exeNum) {
		this.exeNum = exeNum;
	}
	public String getDbmsID() {
		return dbmsID;
	}
	public void setDbmsID(String dbmsID) {
		this.dbmsID = dbmsID;
	}
	public int getRuleCnt() {
		return ruleCnt;
	}
	public void setRuleCnt(int ruleCnt) {
		this.ruleCnt = ruleCnt;
	}
	public int getNotprcCnt() {
		return notprcCnt;
	}
	public void setNotprcCnt(int notprcCnt) {
		this.notprcCnt = notprcCnt;
	}
	public int getFinCnt() {
		return finCnt;
	}
	public void setFinCnt(int finCnt) {
		this.finCnt = finCnt;
	}
	public int getPrcCnt() {
		return prcCnt;
	}
	public void setPrcCnt(int prcCnt) {
		this.prcCnt = prcCnt;
	}
	
}
