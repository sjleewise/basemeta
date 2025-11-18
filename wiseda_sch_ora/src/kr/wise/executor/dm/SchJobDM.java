package kr.wise.executor.dm;

public class SchJobDM {
	private String shdJobId     ;
	private String shdJobSno   ;
	//일반배치
	private String etcJobDtls    ;
	private String etcJobKndCd    ;
	
	public String getShdJobId() {
		return shdJobId;
	}
	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}
	public String getShdJobSno() {
		return shdJobSno;
	}
	public void setShdJobSno(String shdJobSno) {
		this.shdJobSno = shdJobSno;
	}
	public String getEtcJobDtls() {
		return etcJobDtls;
	}
	public void setEtcJobDtls(String etcJobDtls) {
		this.etcJobDtls = etcJobDtls;
	}
	public String getEtcJobKndCd() {
		return etcJobKndCd;
	}
	public void setEtcJobKndCd(String etcJobKndCd) {
		this.etcJobKndCd = etcJobKndCd;
	}
	
}
