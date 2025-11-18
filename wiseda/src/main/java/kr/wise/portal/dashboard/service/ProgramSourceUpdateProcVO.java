package kr.wise.portal.dashboard.service;

public class ProgramSourceUpdateProcVO {
	private String prName;    //업무명
	private String prId;      //업무ID
	private String cntNew;    //신규 수
	private String cntModify; //수정 수
	private String cntDelete; //삭제 수
	private String cntTotal;  //총합계

	public String getPrName() {
		return prName;
	}
	public void setPrName(String prName) {
		this.prName = prName;
	}
	public String getPrId() {
		return prId;
	}
	public void setPrId(String prId) {
		this.prId = prId;
	}
	public String getCntNew() {
		return cntNew;
	}
	public void setCntNew(String cntNew) {
		this.cntNew = cntNew;
	}
	public String getCntModify() {
		return cntModify;
	}
	public void setCntModify(String cntModify) {
		this.cntModify = cntModify;
	}
	public String getCntDelete() {
		return cntDelete;
	}
	public void setCntDelete(String cntDelete) {
		this.cntDelete = cntDelete;
	}
	public String getCntTotal() {
		return cntTotal;
	}
	public void setCntTotal(String cntTotal) {
		this.cntTotal = cntTotal;
	}
	
}
