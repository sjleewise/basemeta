package kr.wise.portal.dashboard.service;

public class ProgramDocumentCountProcVO {
    private String prName;        //업무명	
    private String prId;  	      //업무ID	
	private String cntRequest;    //개발의뢰서	
	private String cntCklist;     //시험점검표	
	private String cntReport;     //결과보고서	
	private String cntDistNormal; //분배요청수(일반)	
	private String cntDistUrgent; //분배요청서(긴급)	
	private String cntOnLine;     //온라인서비스	
	private String cntDatabase;   //데이터베이스	
	private String cntFml;        //FML요청서	
	private String cntBatch;      //배치프로그램	
	private String cntWindow;     //윈도우권한	
	private String cntCode;       //코드등록	
	private String cntWaste;      //폐기요청서	
	private String CntTotal;      //합계
	
	
	public String getCntTotal() {
		return CntTotal;
	}
	public void setCntTotal(String cntTotal) {
		CntTotal = cntTotal;
	}
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
	public String getCntRequest() {
		return cntRequest;
	}
	public void setCntRequest(String cntRequest) {
		this.cntRequest = cntRequest;
	}
	public String getCntCklist() {
		return cntCklist;
	}
	public void setCntCklist(String cntCklist) {
		this.cntCklist = cntCklist;
	}
	public String getCntReport() {
		return cntReport;
	}
	public void setCntReport(String cntReport) {
		this.cntReport = cntReport;
	}
	public String getCntDistNormal() {
		return cntDistNormal;
	}
	public void setCntDistNormal(String cntDistNormal) {
		this.cntDistNormal = cntDistNormal;
	}
	public String getCntDistUrgent() {
		return cntDistUrgent;
	}
	public void setCntDistUrgent(String cntDistUrgent) {
		this.cntDistUrgent = cntDistUrgent;
	}
	public String getCntOnLine() {
		return cntOnLine;
	}
	public void setCntOnLine(String cntOnLine) {
		this.cntOnLine = cntOnLine;
	}
	public String getCntDatabase() {
		return cntDatabase;
	}
	public void setCntDatabase(String cntDatabase) {
		this.cntDatabase = cntDatabase;
	}
	public String getCntFml() {
		return cntFml;
	}
	public void setCntFml(String cntFml) {
		this.cntFml = cntFml;
	}
	public String getCntBatch() {
		return cntBatch;
	}
	public void setCntBatch(String cntBatch) {
		this.cntBatch = cntBatch;
	}
	public String getCntWindow() {
		return cntWindow;
	}
	public void setCntWindow(String cntWindow) {
		this.cntWindow = cntWindow;
	}
	public String getCntCode() {
		return cntCode;
	}
	public void setCntCode(String cntCode) {
		this.cntCode = cntCode;
	}
	public String getCntWaste() {
		return cntWaste;
	}
	public void setCntWaste(String cntWaste) {
		this.cntWaste = cntWaste;
	}
	
}
