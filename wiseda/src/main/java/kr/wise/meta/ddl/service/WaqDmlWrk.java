package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDmlWrk extends CommonVo {
//    private String rqstNo;

//    private Integer rqstSno;

    private String dmlWrkId;    //dml 요청ID

//    private String rqstDcd;

//    private String rvwStsCd;

//    private String rvwConts;

//    private String vrfCd;

//    private String vrfRmk;

    private String rqstSubj;	//요청서 제목

    private String rqstNm;

    private String reqRsnCd;

    private String workObjtCd;

    private String jobDvdCd;

    private String workDvdCd;

    private String dbSchIds;

    private String prcDt;

    private String prcDbaId;

    private String prcDbaNm;

    private String atchFileId;

//    private String objDescn;

//    private Integer objVers;

//    private String regTypCd;

//    private Date frsRqstDtm;

//    private String frsRqstUserId;

//    private Date rqstDtm;

//    private String rqstUserId;

//    private Date aprvDtm;

//    private String aprvUserId;

    private String rqstDtlNm;

    private String uiChk;

    private String dbScrt;

    private String etcNm;




	public String getDmlWrkId() {
		return dmlWrkId;
	}

	public void setDmlWrkId(String dmlWrkId) {
		this.dmlWrkId = dmlWrkId;
	}

	public String getRqstSubj() {
		return rqstSubj;
	}

	public void setRqstSubj(String rqstSubj) {
		this.rqstSubj = rqstSubj;
	}

	public String getRqstNm() {
		return rqstNm;
	}

	public void setRqstNm(String rqstNm) {
		this.rqstNm = rqstNm;
	}

	public String getReqRsnCd() {
		return reqRsnCd;
	}

	public void setReqRsnCd(String reqRsnCd) {
		this.reqRsnCd = reqRsnCd;
	}

	public String getWorkObjtCd() {
		return workObjtCd;
	}

	public void setWorkObjtCd(String workObjtCd) {
		this.workObjtCd = workObjtCd;
	}

	public String getJobDvdCd() {
		return jobDvdCd;
	}

	public void setJobDvdCd(String jobDvdCd) {
		this.jobDvdCd = jobDvdCd;
	}

	public String getWorkDvdCd() {
		return workDvdCd;
	}

	public void setWorkDvdCd(String workDvdCd) {
		this.workDvdCd = workDvdCd;
	}

	public String getDbSchIds() {
		return dbSchIds;
	}

	public void setDbSchIds(String dbSchIds) {
		this.dbSchIds = dbSchIds;
	}

	public String getPrcDt() {
		return prcDt;
	}

	public void setPrcDt(String prcDt) {
		this.prcDt = prcDt;
	}

	public String getPrcDbaId() {
		return prcDbaId;
	}

	public void setPrcDbaId(String prcDbaId) {
		this.prcDbaId = prcDbaId;
	}

	public String getPrcDbaNm() {
		return prcDbaNm;
	}

	public void setPrcDbaNm(String prcDbaNm) {
		this.prcDbaNm = prcDbaNm;
	}

	public String getAtchFileId() {
		return atchFileId;
	}

	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}

	public String getRqstDtlNm() {
		return rqstDtlNm;
	}

	public void setRqstDtlNm(String rqstDtlNm) {
		this.rqstDtlNm = rqstDtlNm;
	}

	public String getUiChk() {
		return uiChk;
	}

	public void setUiChk(String uiChk) {
		this.uiChk = uiChk;
	}

	public String getDbScrt() {
		return dbScrt;
	}

	public void setDbScrt(String dbScrt) {
		this.dbScrt = dbScrt;
	}

	public String getEtcNm() {
		return etcNm;
	}

	public void setEtcNm(String etcNm) {
		this.etcNm = etcNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDmlWrk [dmlWrkId=").append(dmlWrkId)
				.append(", rqstSubj=").append(rqstSubj).append(", rqstNm=")
				.append(rqstNm).append(", reqRsnCd=").append(reqRsnCd)
				.append(", workObjtCd=").append(workObjtCd)
				.append(", jobDvdCd=").append(jobDvdCd).append(", workDvdCd=")
				.append(workDvdCd).append(", dbSchIds=").append(dbSchIds)
				.append(", prcDt=").append(prcDt).append(", prcDbaId=")
				.append(prcDbaId).append(", prcDbaNm=").append(prcDbaNm)
				.append(", atchFileId=").append(atchFileId)
				.append(", rqstDtlNm=").append(rqstDtlNm).append(", uiChk=")
				.append(uiChk).append(", dbScrt=").append(dbScrt)
				.append(", etcNm=").append(etcNm).append("]");
		return builder.toString();
	}


}