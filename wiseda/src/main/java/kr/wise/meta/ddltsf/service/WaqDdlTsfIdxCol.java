package kr.wise.meta.ddltsf.service;

import kr.wise.meta.ddl.service.WaqDdlIdxCol;

public class WaqDdlTsfIdxCol extends WaqDdlIdxCol{

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDdlTsfIdxCol []");
		return builder.toString() + super.toString();
	}
//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;
//
//    private String ddlIdxColId;
//
//    private String ddlIdxColPnm;
//
//    private String ddlIdxColLnm;
//
//    private String rqstDcd;
//
//    private String vrfCd;
//
//    private String vrfRmk;
//
//    private String ddlIdxId;
//
//    private String ddlIdxPnm;
//
//    private String ddlColId;
//
//    private Integer ddlIdxColOrd;
//
//    private String sortTyp;
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
//    public Integer getRqstDtlSno() {
//        return rqstDtlSno;
//    }
//
//    public void setRqstDtlSno(Integer rqstDtlSno) {
//        this.rqstDtlSno = rqstDtlSno;
//    }
//
//    public String getDdlIdxColId() {
//        return ddlIdxColId;
//    }
//
//    public void setDdlIdxColId(String ddlIdxColId) {
//        this.ddlIdxColId = ddlIdxColId;
//    }
//
//    public String getDdlIdxColPnm() {
//        return ddlIdxColPnm;
//    }
//
//    public void setDdlIdxColPnm(String ddlIdxColPnm) {
//        this.ddlIdxColPnm = ddlIdxColPnm;
//    }
//
//    public String getDdlIdxColLnm() {
//        return ddlIdxColLnm;
//    }
//
//    public void setDdlIdxColLnm(String ddlIdxColLnm) {
//        this.ddlIdxColLnm = ddlIdxColLnm;
//    }
//
//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getVrfCd() {
//        return vrfCd;
//    }
//
//    public void setVrfCd(String vrfCd) {
//        this.vrfCd = vrfCd;
//    }
//
//    public String getVrfRmk() {
//        return vrfRmk;
//    }
//
//    public void setVrfRmk(String vrfRmk) {
//        this.vrfRmk = vrfRmk;
//    }
//
//    public String getDdlIdxId() {
//        return ddlIdxId;
//    }
//
//    public void setDdlIdxId(String ddlIdxId) {
//        this.ddlIdxId = ddlIdxId;
//    }
//
//    public String getDdlIdxPnm() {
//        return ddlIdxPnm;
//    }
//
//    public void setDdlIdxPnm(String ddlIdxPnm) {
//        this.ddlIdxPnm = ddlIdxPnm;
//    }
//
//    public String getDdlColId() {
//        return ddlColId;
//    }
//
//    public void setDdlColId(String ddlColId) {
//        this.ddlColId = ddlColId;
//    }
//
//    public Integer getDdlIdxColOrd() {
//        return ddlIdxColOrd;
//    }
//
//    public void setDdlIdxColOrd(Integer ddlIdxColOrd) {
//        this.ddlIdxColOrd = ddlIdxColOrd;
//    }
//
//    public String getSortTyp() {
//        return sortTyp;
//    }
//
//    public void setSortTyp(String sortTyp) {
//        this.sortTyp = sortTyp;
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