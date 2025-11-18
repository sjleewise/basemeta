package kr.wise.dq.impv.service;

import java.util.Date;

import kr.wise.commons.helper.IBSDateJsonDeserializer;
import kr.wise.dq.bizrule.service.WamBrMstr;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class WaqCsAnaMstr extends WamBrMstr {
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String anaJobId;
    
    private String anaJobNm;

    private Date anaStrDtm;

    private String anaKndCd;

    private String csAnaCd;
    
    private String csAnaCdNm;

    private String csAnaDtls;
    
    private String csAnaIlCd;
    
    private String csAnaIlCdNm;

    private String csAnaStrDtm;

    private String csAnaEndDtm;

    private String imPlCd;
    
    private String imPlCdNm;

    private String imPlDtls;

    private String imPlStrDtm;

    private String imPlEndDtm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;
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
    
    private String brNm;
    
    

    /**
	 * @return the anaJobNm
	 */
	public String getAnaJobNm() {
		return anaJobNm;
	}

	/**
	 * @param anaJobNm the anaJobNm to set
	 */
	public void setAnaJobNm(String anaJobNm) {
		this.anaJobNm = anaJobNm;
	}

	/**
	 * @return the csAnaCdNm
	 */
	public String getCsAnaCdNm() {
		return csAnaCdNm;
	}

	/**
	 * @param csAnaCdNm the csAnaCdNm to set
	 */
	public void setCsAnaCdNm(String csAnaCdNm) {
		this.csAnaCdNm = csAnaCdNm;
	}

	/**
	 * @return the csAnaIlCd
	 */
	public String getCsAnaIlCd() {
		return csAnaIlCd;
	}

	/**
	 * @param csAnaIlCd the csAnaIlCd to set
	 */
	public void setCsAnaIlCd(String csAnaIlCd) {
		this.csAnaIlCd = csAnaIlCd;
	}

	/**
	 * @return the csAnaIlCdNm
	 */
	public String getCsAnaIlCdNm() {
		return csAnaIlCdNm;
	}

	/**
	 * @param csAnaIlCdNm the csAnaIlCdNm to set
	 */
	public void setCsAnaIlCdNm(String csAnaIlCdNm) {
		this.csAnaIlCdNm = csAnaIlCdNm;
	}

	/**
	 * @return the imPlCdNm
	 */
	public String getImPlCdNm() {
		return imPlCdNm;
	}

	/**
	 * @param imPlCdNm the imPlCdNm to set
	 */
	public void setImPlCdNm(String imPlCdNm) {
		this.imPlCdNm = imPlCdNm;
	}

	/**
	 * @return the brNm
	 */
	public String getBrNm() {
		return brNm;
	}

	/**
	 * @param brNm the brNm to set
	 */
	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}

//	public String getRqstNo() {
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

    public String getAnaJobId() {
        return anaJobId;
    }

    public void setAnaJobId(String anaJobId) {
        this.anaJobId = anaJobId;
    }

    public Date getAnaStrDtm() {
        return anaStrDtm;
    }
    
    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setAnaStrDtm(Date anaStrDtm) {
        this.anaStrDtm = anaStrDtm;
    }

    public String getAnaKndCd() {
        return anaKndCd;
    }

    public void setAnaKndCd(String anaKndCd) {
        this.anaKndCd = anaKndCd;
    }

    public String getCsAnaCd() {
        return csAnaCd;
    }

    public void setCsAnaCd(String csAnaCd) {
        this.csAnaCd = csAnaCd;
    }

    public String getCsAnaDtls() {
        return csAnaDtls;
    }

    public void setCsAnaDtls(String csAnaDtls) {
        this.csAnaDtls = csAnaDtls;
    }


    public String getImPlCd() {
        return imPlCd;
    }

    public void setImPlCd(String imPlCd) {
        this.imPlCd = imPlCd;
    }

    public String getImPlDtls() {
        return imPlDtls;
    }

    public void setImPlDtls(String imPlDtls) {
        this.imPlDtls = imPlDtls;
    }

    

    /**
	 * @return the csAnaStrDtm
	 */
	public String getCsAnaStrDtm() {
		return csAnaStrDtm;
	}

	/**
	 * @param csAnaStrDtm the csAnaStrDtm to set
	 */
	public void setCsAnaStrDtm(String csAnaStrDtm) {
		this.csAnaStrDtm = csAnaStrDtm;
	}

	/**
	 * @return the csAnaEndDtm
	 */
	public String getCsAnaEndDtm() {
		return csAnaEndDtm;
	}

	/**
	 * @param csAnaEndDtm the csAnaEndDtm to set
	 */
	public void setCsAnaEndDtm(String csAnaEndDtm) {
		this.csAnaEndDtm = csAnaEndDtm;
	}

	/**
	 * @return the imPlStrDtm
	 */
	public String getImPlStrDtm() {
		return imPlStrDtm;
	}

	/**
	 * @param imPlStrDtm the imPlStrDtm to set
	 */
	public void setImPlStrDtm(String imPlStrDtm) {
		this.imPlStrDtm = imPlStrDtm;
	}

	/**
	 * @return the imPlEndDtm
	 */
	public String getImPlEndDtm() {
		return imPlEndDtm;
	}

	/**
	 * @param imPlEndDtm the imPlEndDtm to set
	 */
	public void setImPlEndDtm(String imPlEndDtm) {
		this.imPlEndDtm = imPlEndDtm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqCsAnaMstr [anaJobId=").append(anaJobId)
				.append(", anaJobNm=").append(anaJobNm).append(", anaStrDtm=")
				.append(anaStrDtm).append(", anaKndCd=").append(anaKndCd)
				.append(", csAnaCd=").append(csAnaCd).append(", csAnaCdNm=")
				.append(csAnaCdNm).append(", csAnaDtls=").append(csAnaDtls)
				.append(", csAnaIlCd=").append(csAnaIlCd)
				.append(", csAnaIlCdNm=").append(csAnaIlCdNm)
				.append(", csAnaStrDtm=").append(csAnaStrDtm)
				.append(", csAnaEndDtm=").append(csAnaEndDtm)
				.append(", imPlCd=").append(imPlCd).append(", imPlCdNm=")
				.append(imPlCdNm).append(", imPlDtls=").append(imPlDtls)
				.append(", imPlStrDtm=").append(imPlStrDtm)
				.append(", imPlEndDtm=").append(imPlEndDtm).append(", brNm=")
				.append(brNm).append("]");
		return builder.toString() + super.toString();
	}

//	public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getRvwStsCd() {
//        return rvwStsCd;
//    }
//
//    public void setRvwStsCd(String rvwStsCd) {
//        this.rvwStsCd = rvwStsCd;
//    }
//
//    public String getRvwConts() {
//        return rvwConts;
//    }
//
//    public void setRvwConts(String rvwConts) {
//        this.rvwConts = rvwConts;
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