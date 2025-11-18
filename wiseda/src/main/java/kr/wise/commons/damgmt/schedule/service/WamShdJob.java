package kr.wise.commons.damgmt.schedule.service;


public class WamShdJob extends WamShd{

    private String shdJobId; //WamShd에 정의

    private Integer shdJobSno; //WamShd에 정의
    
    
    private String shdJobNm;
    
    private String shdJobDtls;
    
    private String shdJobKndCd;
    
    private String etcJobNm;

    private String etcJobDtls;

    private String etcJobKndCd;
    
    private String dbConnTrgId;

    private String dbConnTrgLnm;

    private String regYn;
    
    private String exeYn;

    private String commDtlCd;

    private String commDtlCdNm;

    private String commDcdId;

    private String dbcTblNm;

    private String objNm;

    private String prfKndCd;

    private String prfId;

    private String brId;

    private String brNm;

    private String bizAreaId;
    
    private String pyKndCd;
    
    private String sim;
    
    private String dbSchId;
    

	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getShdJobId() {
		return shdJobId;
	}

	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}

	public Integer getShdJobSno() {
		return shdJobSno;
	}

	public void setShdJobSno(Integer shdJobSno) {
		this.shdJobSno = shdJobSno;
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

	public String getEtcJobKndCd() {
		return etcJobKndCd;
	}

	public void setEtcJobKndCd(String etcJobKndCd) {
		this.etcJobKndCd = etcJobKndCd;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getRegYn() {
		return regYn;
	}

	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}

	public String getCommDtlCd() {
		return commDtlCd;
	}

	public void setCommDtlCd(String commDtlCd) {
		this.commDtlCd = commDtlCd;
	}

	public String getCommDtlCdNm() {
		return commDtlCdNm;
	}

	public void setCommDtlCdNm(String commDtlCdNm) {
		this.commDtlCdNm = commDtlCdNm;
	}

	public String getCommDcdId() {
		return commDcdId;
	}

	public void setCommDcdId(String commDcdId) {
		this.commDcdId = commDcdId;
	}

	public String getDbcTblNm() {
		return dbcTblNm;
	}

	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}

	public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public String getPrfId() {
		return prfId;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public String getBrId() {
		return brId;
	}

	public void setBrId(String brId) {
		this.brId = brId;
	}

	public String getBrNm() {
		return brNm;
	}

	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}

	public String getBizAreaId() {
		return bizAreaId;
	}

	public void setBizAreaId(String bizAreaId) {
		this.bizAreaId = bizAreaId;
	}

	public String getExeYn() {
		return exeYn;
	}

	public void setExeYn(String exeYn) {
		this.exeYn = exeYn;
	}

	public String getShdJobNm() {
		return shdJobNm;
	}

	public void setShdJobNm(String shdJobNm) {
		this.shdJobNm = shdJobNm;
	}

	public String getShdJobDtls() {
		return shdJobDtls;
	}

	public void setShdJobDtls(String shdJobDtls) {
		this.shdJobDtls = shdJobDtls;
	}

	public String getShdJobKndCd() {
		return shdJobKndCd;
	}

	public void setShdJobKndCd(String shdJobKndCd) {
		this.shdJobKndCd = shdJobKndCd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamShdJob [shdJobId=").append(shdJobId)
				.append(", shdJobSno=").append(shdJobSno).append(", etcJobNm=")
				.append(etcJobNm).append(", etcJobDtls=").append(etcJobDtls)
				.append(", etcJobKndCd=").append(etcJobKndCd)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", regYn=").append(regYn).append(", commDtlCd=")
				.append(commDtlCd).append(", commDtlCdNm=").append(commDtlCdNm)
				.append(", commDcdId=").append(commDcdId).append(", dbcTblNm=")
				.append(dbcTblNm).append(", objNm=").append(objNm)
				.append(", prfKndCd=").append(prfKndCd).append(", prfId=")
				.append(prfId).append(", brId=").append(brId).append(", brNm=")
				.append(brNm).append(", bizAreaId=").append(bizAreaId)
				.append("]");
		return builder.toString();
	}

	/**
	 * @return the pyKndCd
	 */
	public String getPyKndCd() {
		return pyKndCd;
	}

	/**
	 * @param pyKndCd the pyKndCd to set
	 */
	public void setPyKndCd(String pyKndCd) {
		this.pyKndCd = pyKndCd;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

}