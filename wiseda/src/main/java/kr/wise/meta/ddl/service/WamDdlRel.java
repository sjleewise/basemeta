package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDdlRel extends CommonVo{
    private String ddlRelId;

    private String paEntyId;

    private String paAttrId;

    private String chEntyId;

    private String chAttrId;

    private String ddlRelPnm;

    private String ddlRelLnm;

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
    
    private String paSubjPnm;
    
    private String paEntyPnm;
    
    private String paAttrPnm;
    
    private String chSubjPnm;
    
    private String chEntyPnm;
    
    private String chAttrPnm;
    
    

    public String getPaSubjPnm() {
		return paSubjPnm;
	}

	public void setPaSubjPnm(String paSubjPnm) {
		this.paSubjPnm = paSubjPnm;
	}

	public String getPaEntyPnm() {
		return paEntyPnm;
	}

	public void setPaEntyPnm(String paEntyPnm) {
		this.paEntyPnm = paEntyPnm;
	}

	public String getPaAttrPnm() {
		return paAttrPnm;
	}

	public void setPaAttrPnm(String paAttrPnm) {
		this.paAttrPnm = paAttrPnm;
	}

	public String getChSubjPnm() {
		return chSubjPnm;
	}

	public void setChSubjPnm(String chSubjPnm) {
		this.chSubjPnm = chSubjPnm;
	}

	public String getChEntyPnm() {
		return chEntyPnm;
	}

	public void setChEntyPnm(String chEntyPnm) {
		this.chEntyPnm = chEntyPnm;
	}

	public String getChAttrPnm() {
		return chAttrPnm;
	}

	public void setChAttrPnm(String chAttrPnm) {
		this.chAttrPnm = chAttrPnm;
	}

	public String getDdlRelId() {
        return ddlRelId;
    }

    public void setDdlRelId(String ddlRelId) {
        this.ddlRelId = ddlRelId;
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

    public String getDdlRelPnm() {
        return ddlRelPnm;
    }

    public void setDdlRelPnm(String ddlRelPnm) {
        this.ddlRelPnm = ddlRelPnm;
    }

    public String getDdlRelLnm() {
        return ddlRelLnm;
    }

    public void setDdlRelLnm(String ddlRelLnm) {
        this.ddlRelLnm = ddlRelLnm;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlRel [ddlRelId=").append(ddlRelId)
				.append(", paEntyId=").append(paEntyId).append(", paAttrId=")
				.append(paAttrId).append(", chEntyId=").append(chEntyId)
				.append(", chAttrId=").append(chAttrId).append(", ddlRelPnm=")
				.append(ddlRelPnm).append(", ddlRelLnm=").append(ddlRelLnm)
				.append(", relTypCd=").append(relTypCd).append(", crdTypCd=")
				.append(crdTypCd).append(", paOptTypCd=").append(paOptTypCd)
				.append(", paSubjId=").append(paSubjId).append(", chSubjId=")
				.append(chSubjId).append(", paSubjPnm=").append(paSubjPnm)
				.append(", paEntyPnm=").append(paEntyPnm)
				.append(", paAttrPnm=").append(paAttrPnm)
				.append(", chSubjPnm=").append(chSubjPnm)
				.append(", chEntyPnm=").append(chEntyPnm)
				.append(", chAttrPnm=").append(chAttrPnm).append("]");
		return builder.toString()+super.toString();
	}
}