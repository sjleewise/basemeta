package kr.wise.portal.dashboard.service;

public class ProgramCountDashProcVO {
    private String prName;  //업무명
	private String prId;       //업무ID
	private String cntClient;  //CLIENT 수
	private String cntService; //SERVICE 수
	private String cntBatch;   //BATCH 수
	private String cntFunc;    //FUNCTION 수
	private String cntJsp;     //JSP 수
	private String cntJava;    //JAVA 수
	private String cntEtc;     //기타 수	
	private String cntWaste;   //폐기 수
	private String cntTotal;   //합계
	
	public String getCntTotal() {
		return cntTotal;
	}
	public void setCntTotal(String cntTotal) {
		this.cntTotal = cntTotal;
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
	public String getCntClient() {
		return cntClient;
	}
	public void setCntClient(String cntClient) {
		this.cntClient = cntClient;
	}
	public String getCntService() {
		return cntService;
	}
	public void setCntService(String cntService) {
		this.cntService = cntService;
	}
	public String getCntBatch() {
		return cntBatch;
	}
	public void setCntBatch(String cntBatch) {
		this.cntBatch = cntBatch;
	}
	public String getCntFunc() {
		return cntFunc;
	}
	public void setCntFunc(String cntFunc) {
		this.cntFunc = cntFunc;
	}
	public String getCntJsp() {
		return cntJsp;
	}
	public void setCntJsp(String cntJsp) {
		this.cntJsp = cntJsp;
	}
	public String getCntJava() {
		return cntJava;
	}
	public void setCntJava(String cntJava) {
		this.cntJava = cntJava;
	}
	public String getCntEtc() {
		return cntEtc;
	}
	public void setCntEtc(String cntEtc) {
		this.cntEtc = cntEtc;
	}
	public String getCntWaste() {
		return cntWaste;
	}
	public void setCntWaste(String cntWaste) {
		this.cntWaste = cntWaste;
	}
	
}
