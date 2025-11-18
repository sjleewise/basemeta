package kr.wise.meta.ddltsf.service;

import kr.wise.meta.ddl.service.WaqDdlRel;

public class WaqDdlTsfRel extends WaqDdlRel {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDdlTsfRel []");
		return builder.toString()+super.toString();
	}
//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;
//
//    private String ddlRelPnm;
//
//    private String paEntyPnm;
//
//    private String paAttrPnm;
//
//    private String chEntyPnm;
//
//    private String chAttrPnm;
//
//    private String ddlRelId;
//
//    private String ddlRelLnm;
//
//    private String vrfCd;
//
//    private String vrfRmk;
//
//    private String rqstDcd;
//
//    private String relTypCd;
//
//    private String crdTypCd;
//
//    private String paOptTypCd;
//
//    private String paSubjId;
//
//    private String paSubjPnm;
//
//    private String paEntyId;
//
//    private String paAttrId;
//
//    private String chSubjId;
//
//    private String chSubjPnm;
//
//    private String chEntyId;
//
//    private String chAttrId;
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
//
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
//    public String getDdlRelPnm() {
//        return ddlRelPnm;
//    }
//
//    public void setDdlRelPnm(String ddlRelPnm) {
//        this.ddlRelPnm = ddlRelPnm;
//    }
//
//    public String getPaEntyPnm() {
//        return paEntyPnm;
//    }
//
//    public void setPaEntyPnm(String paEntyPnm) {
//        this.paEntyPnm = paEntyPnm;
//    }
//
//    public String getPaAttrPnm() {
//        return paAttrPnm;
//    }
//
//    public void setPaAttrPnm(String paAttrPnm) {
//        this.paAttrPnm = paAttrPnm;
//    }
//
//    public String getChEntyPnm() {
//        return chEntyPnm;
//    }
//
//    public void setChEntyPnm(String chEntyPnm) {
//        this.chEntyPnm = chEntyPnm;
//    }
//
//    public String getChAttrPnm() {
//        return chAttrPnm;
//    }
//
//    public void setChAttrPnm(String chAttrPnm) {
//        this.chAttrPnm = chAttrPnm;
//    }
//
//    public String getDdlRelId() {
//        return ddlRelId;
//    }
//
//    public void setDdlRelId(String ddlRelId) {
//        this.ddlRelId = ddlRelId;
//    }
//
//    public String getDdlRelLnm() {
//        return ddlRelLnm;
//    }
//
//    public void setDdlRelLnm(String ddlRelLnm) {
//        this.ddlRelLnm = ddlRelLnm;
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
//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getRelTypCd() {
//        return relTypCd;
//    }
//
//    public void setRelTypCd(String relTypCd) {
//        this.relTypCd = relTypCd;
//    }
//
//    public String getCrdTypCd() {
//        return crdTypCd;
//    }
//
//    public void setCrdTypCd(String crdTypCd) {
//        this.crdTypCd = crdTypCd;
//    }
//
//    public String getPaOptTypCd() {
//        return paOptTypCd;
//    }
//
//    public void setPaOptTypCd(String paOptTypCd) {
//        this.paOptTypCd = paOptTypCd;
//    }
//
//    public String getPaSubjId() {
//        return paSubjId;
//    }
//
//    public void setPaSubjId(String paSubjId) {
//        this.paSubjId = paSubjId;
//    }
//
//    public String getPaSubjPnm() {
//        return paSubjPnm;
//    }
//
//    public void setPaSubjPnm(String paSubjPnm) {
//        this.paSubjPnm = paSubjPnm;
//    }
//
//    public String getPaEntyId() {
//        return paEntyId;
//    }
//
//    public void setPaEntyId(String paEntyId) {
//        this.paEntyId = paEntyId;
//    }
//
//    public String getPaAttrId() {
//        return paAttrId;
//    }
//
//    public void setPaAttrId(String paAttrId) {
//        this.paAttrId = paAttrId;
//    }
//
//    public String getChSubjId() {
//        return chSubjId;
//    }
//
//    public void setChSubjId(String chSubjId) {
//        this.chSubjId = chSubjId;
//    }
//
//    public String getChSubjPnm() {
//        return chSubjPnm;
//    }
//
//    public void setChSubjPnm(String chSubjPnm) {
//        this.chSubjPnm = chSubjPnm;
//    }
//
//    public String getChEntyId() {
//        return chEntyId;
//    }
//
//    public void setChEntyId(String chEntyId) {
//        this.chEntyId = chEntyId;
//    }
//
//    public String getChAttrId() {
//        return chAttrId;
//    }
//
//    public void setChAttrId(String chAttrId) {
//        this.chAttrId = chAttrId;
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