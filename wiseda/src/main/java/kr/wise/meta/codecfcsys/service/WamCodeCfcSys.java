package kr.wise.meta.codecfcsys.service;

import kr.wise.commons.cmm.CommonVo;

public class WamCodeCfcSys extends CommonVo{
    private String codeCfcSysId;

    private String codeCfcSysLnm;

    private String codeCfcSysPnm;

    private String codeCfcSysCd;

    private String codeCfcSysFrm;

    private String crgUserId;
    
    private String crgUserNm;

//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

    public String getCrgUserNm() {
		return crgUserNm;
	}

	public void setCrgUserNm(String crgUserNm) {
		this.crgUserNm = crgUserNm;
	}

	public String getCodeCfcSysId() {
        return codeCfcSysId;
    }

    public void setCodeCfcSysId(String codeCfcSysId) {
        this.codeCfcSysId = codeCfcSysId;
    }

    public String getCodeCfcSysLnm() {
        return codeCfcSysLnm;
    }

    public void setCodeCfcSysLnm(String codeCfcSysLnm) {
        this.codeCfcSysLnm = codeCfcSysLnm;
    }

    public String getCodeCfcSysPnm() {
        return codeCfcSysPnm;
    }

    public void setCodeCfcSysPnm(String codeCfcSysPnm) {
        this.codeCfcSysPnm = codeCfcSysPnm;
    }

    public String getCodeCfcSysCd() {
        return codeCfcSysCd;
    }

    public void setCodeCfcSysCd(String codeCfcSysCd) {
        this.codeCfcSysCd = codeCfcSysCd;
    }

    public String getCodeCfcSysFrm() {
        return codeCfcSysFrm;
    }

    public void setCodeCfcSysFrm(String codeCfcSysFrm) {
        this.codeCfcSysFrm = codeCfcSysFrm;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamCodeCfcSys [codeCfcSysId=").append(codeCfcSysId)
				.append(", codeCfcSysLnm=").append(codeCfcSysLnm)
				.append(", codeCfcSysPnm=").append(codeCfcSysPnm)
				.append(", codeCfcSysCd=").append(codeCfcSysCd)
				.append(", codeCfcSysFrm=").append(codeCfcSysFrm)
				.append(", crgUserId=").append(crgUserId)
				.append(", crgUserNm=").append(crgUserNm).append("]");
		return builder.toString()+super.toString();
	}

//    public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }
//
//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }
    
}