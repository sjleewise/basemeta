package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqPdmRel extends CommonVo {
//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;

	private String pdmRelId;

	private String pdmRelPnm;

	private String pdmRelLnm;
    
	private String paSubjId;
	
	private String paSubjPnm;

	private String paEntyId;

	private String paEntyPnm;
	
	private String paAttrId;

    private String paAttrPnm;

    private String chSubjId;
    
    private String chSubjPnm;
    
    private String chEntyId;

    private String chEntyPnm;
        
    private String chAttrId;
    
    private String chAttrPnm;


    //updateByPrimaryKeySelective 수행을 위해 PNM을 PK로 하는 항목의 이전값을 저장할 변수 선언
    private String bfPdmRelPnm;
    private String bfPaEntyPnm;
    private String bfPaAttrPnm;
    private String bfChEntyPnm;
    private String bfChAttrPnm;
    
    

//    private String vrfCd;
//
//    private String vrfRmk;
//
//    private String rqstDcd;

    public String getBfPdmRelPnm() {
		return bfPdmRelPnm;
	}

	public void setBfPdmRelPnm(String bfPdmRelPnm) {
		this.bfPdmRelPnm = bfPdmRelPnm;
	}

	public String getBfPaEntyPnm() {
		return bfPaEntyPnm;
	}

	public void setBfPaEntyPnm(String bfPaEntyPnm) {
		this.bfPaEntyPnm = bfPaEntyPnm;
	}

	public String getBfPaAttrPnm() {
		return bfPaAttrPnm;
	}

	public void setBfPaAttrPnm(String bfPaAttrPnm) {
		this.bfPaAttrPnm = bfPaAttrPnm;
	}

	public String getBfChEntyPnm() {
		return bfChEntyPnm;
	}

	public void setBfChEntyPnm(String bfChEntyPnm) {
		this.bfChEntyPnm = bfChEntyPnm;
	}

	public String getBfChAttrPnm() {
		return bfChAttrPnm;
	}

	public void setBfChAttrPnm(String bfChAttrPnm) {
		this.bfChAttrPnm = bfChAttrPnm;
	}

	private String relTypCd;

    private String crdTypCd;

    private String paOptTypCd;




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

    public String getPdmRelPnm() {
        return pdmRelPnm;
    }

    public void setPdmRelPnm(String pdmRelPnm) {
        this.pdmRelPnm = pdmRelPnm;
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

    public String getPdmRelId() {
        return pdmRelId;
    }

    public void setPdmRelId(String pdmRelId) {
        this.pdmRelId = pdmRelId;
    }

    public String getPdmRelLnm() {
        return pdmRelLnm;
    }

    public void setPdmRelLnm(String pdmRelLnm) {
        this.pdmRelLnm = pdmRelLnm;
    }

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

    public String getPaSubjPnm() {
        return paSubjPnm;
    }

    public void setPaSubjPnm(String paSubjPnm) {
        this.paSubjPnm = paSubjPnm;
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

    public String getChSubjId() {
        return chSubjId;
    }

    public void setChSubjId(String chSubjId) {
        this.chSubjId = chSubjId;
    }

    public String getChSubjPnm() {
        return chSubjPnm;
    }

    public void setChSubjPnm(String chSubjPnm) {
        this.chSubjPnm = chSubjPnm;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPdmRel [pdmRelId=").append(pdmRelId)
				.append(", pdmRelPnm=").append(pdmRelPnm)
				.append(", pdmRelLnm=").append(pdmRelLnm).append(", paSubjId=")
				.append(paSubjId).append(", paSubjPnm=").append(paSubjPnm)
				.append(", paEntyId=").append(paEntyId).append(", paEntyPnm=")
				.append(paEntyPnm).append(", paAttrId=").append(paAttrId)
				.append(", paAttrPnm=").append(paAttrPnm).append(", chSubjId=")
				.append(chSubjId).append(", chSubjPnm=").append(chSubjPnm)
				.append(", chEntyId=").append(chEntyId).append(", chEntyPnm=")
				.append(chEntyPnm).append(", chAttrId=").append(chAttrId)
				.append(", chAttrPnm=").append(chAttrPnm)
				.append(", bfPdmRelPnm=").append(bfPdmRelPnm)
				.append(", bfPaEntyPnm=").append(bfPaEntyPnm)
				.append(", bfPaAttrPnm=").append(bfPaAttrPnm)
				.append(", bfChEntyPnm=").append(bfChEntyPnm)
				.append(", bfChAttrPnm=").append(bfChAttrPnm)
				.append(", relTypCd=").append(relTypCd).append(", crdTypCd=")
				.append(crdTypCd).append(", paOptTypCd=").append(paOptTypCd)
				.append("]");
		return builder.toString()+super.toString();
	}

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