package kr.wise.dq.dashboard.service;

/* 데이터 종합현황 차트 */
public class DbcAllErrChartVO {
	private int noErr;       //에러아님
	private float noErrPst;  //에러아닌 퍼센트
	private int total;       //전체
	private int anaActCnt;   //전체
	private float errCnt;    //에러
	private int errRate;     //에러 퍼센트
	
	public int getAnaActCnt() {
		return anaActCnt;
	}
	public void setAnaActCnt(int anaActCnt) {
		this.anaActCnt = anaActCnt;
	}
	public float getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(float errCnt) {
		this.errCnt = errCnt;
	}
	public int getErrRate() {
		return errRate;
	}
	public void setErrRate(int errRate) {
		this.errRate = errRate;
	}
	public int getNoErr() {
		return noErr;
	}
	public void setNoErr(int noErr) {
		this.noErr = noErr;
	}
	public float getNoErrPst() {
		return noErrPst;
	}
	public void setNoErrPst(float noErrPst) {
		this.noErrPst = noErrPst;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
