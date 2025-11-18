package kr.wise.executor.dm;

import java.math.BigDecimal;

public class ExecutorDM {
	//스케줄ID
	private String shdId;
	//스케줄종류 : 스키마, 프로파일, 업무규칙 
	private String shdKndCd;
	//스케줄유형
	private String shdTypCd;
	//작업ID
	private String shdJobId;
	//작업명
	private String shdJobNm;
	//시작시간
	private String shdStaDtm;
	//분석차수
	private BigDecimal anaDgr;
	//요청자
	private String rqstUserId;
	//승인자
	private String aprvUserId;
	
	//분석차수 자동증가
	private String anaDgrAutoIncYn;
	
	//PK데이터적재
	private String pkDataLdYn;
	private BigDecimal pkDataLdCnt;
	
	//에러데이터적재
	private String erDataLdYn;
	private BigDecimal erDataLdCnt;
	
	//일반배치
	//일반배치 종류 EXEC호출, JAVA호출, DB호출
	private String etcJobKndCd;
	//일반배치작업명
	private String etcJobNm;
	//일반배치작업내역 실제 실행되는 script
	private String etcJobDtls;
		
	
	@Override
	public ExecutorDM clone() throws CloneNotSupportedException {
		ExecutorDM executorDM = new ExecutorDM();
		executorDM.setShdId(shdId);
		executorDM.setShdKndCd(shdKndCd);
		executorDM.setShdTypCd(shdTypCd);
		executorDM.setShdJobId(shdJobId);
		executorDM.setShdJobNm(shdJobNm);
		executorDM.setShdStaDtm(shdStaDtm);
		executorDM.setRqstUserId(rqstUserId);
		executorDM.setAprvUserId(aprvUserId);
		executorDM.setAnaDgr(anaDgr);
		
		//일반배치
		executorDM.setEtcJobKndCd(etcJobKndCd);
		executorDM.setEtcJobNm(etcJobNm);
		executorDM.setEtcJobDtls(etcJobDtls);
		
		//분석차수 자동증가
		executorDM.setAnaDgrAutoIncYn(anaDgrAutoIncYn);
		
		//PK데이터적재
		executorDM.setPkDataLdYn(pkDataLdYn);
		executorDM.setPkDataLdCnt(pkDataLdCnt);
		
		//에러데이터적재
		executorDM.setErDataLdYn(erDataLdYn);
		executorDM.setErDataLdCnt(erDataLdCnt);
		
		return executorDM;
	}

	public String getShdId() {
		return shdId;
	}

	public void setShdId(String shdId) {
		this.shdId = shdId;
	}

	public String getShdKndCd() {
		return shdKndCd;
	}

	public void setShdKndCd(String shdKndCd) {
		this.shdKndCd = shdKndCd;
	}

	public String getShdJobId() {
		return shdJobId;
	}

	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}

	public String getShdJobNm() {
		return shdJobNm;
	}

	public void setShdJobNm(String shdJobNm) {
		this.shdJobNm = shdJobNm;
	}

	public String getShdTypCd() {
		return shdTypCd;
	}

	public void setShdTypCd(String shdTypCd) {
		this.shdTypCd = shdTypCd;
	}

	public String getShdStaDtm() {
		return shdStaDtm;
	}

	public void setShdStaDtm(String shdStaDtm) {
		this.shdStaDtm = shdStaDtm;
	}

	public String getRqstUserId() {
		return rqstUserId;
	}

	public void setRqstUserId(String rqstUserId) {
		this.rqstUserId = rqstUserId;
	}

	public String getAprvUserId() {
		return aprvUserId;
	}

	public void setAprvUserId(String aprvUserId) {
		this.aprvUserId = aprvUserId;
	}

	public BigDecimal getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(BigDecimal anaDgr) {
		this.anaDgr = anaDgr;
	}

	public String getEtcJobKndCd() {
		return etcJobNm;
	}

	public void setEtcJobKndCd(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}

	public String getEtcJobNm() {
		return etcJobNm;
	}

	public void setEtcJobNm(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}

	public String getEtcJobDtls() {
		return etcJobDtls;
	}

	public void setEtcJobDtls(String etcJobDtls) {
		this.etcJobDtls = etcJobDtls;
	}

	public String getAnaDgrAutoIncYn() {
		return anaDgrAutoIncYn;
	}

	public void setAnaDgrAutoIncYn(String anaDgrAutoIncYn) {
		this.anaDgrAutoIncYn = anaDgrAutoIncYn;
	}

	public String getPkDataLdYn() {
		return pkDataLdYn;
	}

	public void setPkDataLdYn(String pkDataLdYn) {
		this.pkDataLdYn = pkDataLdYn;
	}

	public BigDecimal getPkDataLdCnt() {
		return pkDataLdCnt;
	}

	public void setPkDataLdCnt(BigDecimal pkDataLdCnt) {
		this.pkDataLdCnt = pkDataLdCnt;
	}

	public String getErDataLdYn() {
		return erDataLdYn;
	}

	public void setErDataLdYn(String erDataLdYn) {
		this.erDataLdYn = erDataLdYn;
	}

	public BigDecimal getErDataLdCnt() {
		return erDataLdCnt;
	}

	public void setErDataLdCnt(BigDecimal erDataLdCnt) {
		this.erDataLdCnt = erDataLdCnt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExecutorDM [shdId=").append(shdId)
				.append(", shdKndCd=").append(shdKndCd).append(", shdTypCd=")
				.append(shdTypCd).append(", shdJobId=").append(shdJobId)
				.append(", shdJobNm=").append(shdJobNm).append(", shdStaDtm=")
				.append(shdStaDtm).append(", anaDgr=").append(anaDgr)
				.append(", rqstUserId=").append(rqstUserId)
				.append(", aprvUserId=").append(aprvUserId)
				.append(", anaDgrAutoIncYn=").append(anaDgrAutoIncYn)
				.append(", pkDataLdYn=").append(pkDataLdYn)
				.append(", pkDataLdCnt=").append(pkDataLdCnt)
				.append(", erDataLdYn=").append(erDataLdYn)
				.append(", erDataLdCnt=").append(erDataLdCnt)
				.append(", etcJobKndCd=").append(etcJobKndCd)
				.append(", etcJobNm=").append(etcJobNm).append(", etcJobDtls=")
				.append(etcJobDtls).append("]");
		return builder.toString();
	}

}
