package kr.wise.meta.model.service;


public class WamPdmRel extends WaqPdmRel {
    private String pdmRelId;

    private String paEntyId;

    private String paAttrId;

    private String chEntyId;

    private String chAttrId;

    private String pdmRelPnm;

    private String pdmRelLnm;

    private String relTypCd;

    private String crdTypCd;

    private String paOptTypCd;

    private String paSubjId;

    private String chSubjId;

//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;
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

    public String getPdmRelId() {
        return pdmRelId;
    }

    public void setPdmRelId(String pdmRelId) {
        this.pdmRelId = pdmRelId;
    }

    public String getPaEntyId() {
        return paEntyId;
    }

    public void setPaEntyId(String paEntyId) {
        this.paEntyId = paEntyId;
    }

    public String getPaAttrId() {
        return paAttrId;
    }

    public void setPaAttrId(String paAttrId) {
        this.paAttrId = paAttrId;
    }

    public String getChEntyId() {
        return chEntyId;
    }

    public void setChEntyId(String chEntyId) {
        this.chEntyId = chEntyId;
    }

    public String getChAttrId() {
        return chAttrId;
    }

    public void setChAttrId(String chAttrId) {
        this.chAttrId = chAttrId;
    }

    public String getPdmRelPnm() {
        return pdmRelPnm;
    }

    public void setPdmRelPnm(String pdmRelPnm) {
        this.pdmRelPnm = pdmRelPnm;
    }

    public String getPdmRelLnm() {
        return pdmRelLnm;
    }

    public void setPdmRelLnm(String pdmRelLnm) {
        this.pdmRelLnm = pdmRelLnm;
    }

    public String getRelTypCd() {
        return relTypCd;
    }

    public void setRelTypCd(String relTypCd) {
        this.relTypCd = relTypCd;
    }

    public String getCrdTypCd() {
        return crdTypCd;
    }

    public void setCrdTypCd(String crdTypCd) {
        this.crdTypCd = crdTypCd;
    }

    public String getPaOptTypCd() {
        return paOptTypCd;
    }

    public void setPaOptTypCd(String paOptTypCd) {
        this.paOptTypCd = paOptTypCd;
    }

    public String getPaSubjId() {
        return paSubjId;
    }

    public void setPaSubjId(String paSubjId) {
        this.paSubjId = paSubjId;
    }

    public String getChSubjId() {
        return chSubjId;
    }

    public void setChSubjId(String chSubjId) {
        this.chSubjId = chSubjId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPdmRel [pdmRelId=").append(pdmRelId)
				.append(", paEntyId=").append(paEntyId).append(", paAttrId=")
				.append(paAttrId).append(", chEntyId=").append(chEntyId)
				.append(", chAttrId=").append(chAttrId).append(", pdmRelPnm=")
				.append(pdmRelPnm).append(", pdmRelLnm=").append(pdmRelLnm)
				.append(", relTypCd=").append(relTypCd).append(", crdTypCd=")
				.append(crdTypCd).append(", paOptTypCd=").append(paOptTypCd)
				.append(", paSubjId=").append(paSubjId).append(", chSubjId=")
				.append(chSubjId).append("]");
		return builder.toString() + super.toString();
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
//    public Integer getRqstDtlSno() {
//        return rqstDtlSno;
//    }
//
//    public void setRqstDtlSno(Integer rqstDtlSno) {
//        this.rqstDtlSno = rqstDtlSno;
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