package kr.wise.commons.damgmt.approve.service;


public class WaaAprPrc extends WaaRqstAprp {
//    private String bizDcd;
//
//    private Short aprLvl;
//
//    private String userId;
//
//    private String rqstNo;
//
//    private String sysDcd;
//
//    private Date aprvDtm;
	/** 결재사유 */
    private String aprResn;

//    private String rvwStsCd;

//    private String aprFrmlCd;

//    private String aprgId;

//    private String abdDaprDcd;
    /** 원결재자 */
    private String ogAprpId;
    private String ogAprpNm;

//    private String objDescn;

//    private Integer objVers;

//    private String regTypCd;

//    private Date writDtm;

//    private String writUserId;

//    private Date updDtm;

//    private String updUserId;

    private String groupUserId;

    
    private String aprgNm;
    
    
    
//    public String getBizDcd() {
//        return bizDcd;
//    }
//
//    public void setBizDcd(String bizDcd) {
//        this.bizDcd = bizDcd;
//    }
//
//    public Short getAprLvl() {
//        return aprLvl;
//    }
//
//    public void setAprLvl(Short aprLvl) {
//        this.aprLvl = aprLvl;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public String getSysDcd() {
//        return sysDcd;
//    }
//
//    public void setSysDcd(String sysDcd) {
//        this.sysDcd = sysDcd;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }

    public String getAprgNm() {
		return aprgNm;
	}

	public void setAprgNm(String aprgNm) {
		this.aprgNm = aprgNm;
	}

	public String getAprResn() {
        return aprResn;
    }

    public void setAprResn(String aprResn) {
        this.aprResn = aprResn;
    }

//    public String getRvwStsCd() {
//        return rvwStsCd;
//    }
//
//    public void setRvwStsCd(String rvwStsCd) {
//        this.rvwStsCd = rvwStsCd;
//    }
//
//    public String getAprFrmlCd() {
//        return aprFrmlCd;
//    }
//
//    public void setAprFrmlCd(String aprFrmlCd) {
//        this.aprFrmlCd = aprFrmlCd;
//    }
//
//    public String getAprgId() {
//        return aprgId;
//    }
//
//    public void setAprgId(String aprgId) {
//        this.aprgId = aprgId;
//    }
//
//    public String getAbdDaprDcd() {
//        return abdDaprDcd;
//    }
//
//    public void setAbdDaprDcd(String abdDaprDcd) {
//        this.abdDaprDcd = abdDaprDcd;
//    }
//
    public String getOgAprpId() {
        return ogAprpId;
    }

    public void setOgAprpId(String ogAprpId) {
        this.ogAprpId = ogAprpId;
    }
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
//    public Date getWritDtm() {
//        return writDtm;
//    }
//
//    public void setWritDtm(Date writDtm) {
//        this.writDtm = writDtm;
//    }
//
//    public String getWritUserId() {
//        return writUserId;
//    }
//
//    public void setWritUserId(String writUserId) {
//        this.writUserId = writUserId;
//    }
//
//    public Date getUpdDtm() {
//        return updDtm;
//    }
//
//    public void setUpdDtm(Date updDtm) {
//        this.updDtm = updDtm;
//    }
//
//    public String getUpdUserId() {
//        return updUserId;
//    }
//
//    public void setUpdUserId(String updUserId) {
//        this.updUserId = updUserId;
//    }

	/**
	 * @return the ogAprpNm
	 */
	public String getOgAprpNm() {
		return ogAprpNm;
	}

	/**
	 * @param ogAprpNm the ogAprpNm to set
	 */
	public void setOgAprpNm(String ogAprpNm) {
		this.ogAprpNm = ogAprpNm;
	}

	/**
	 * @return the groupUserId
	 */
	public String getGroupUserId() {
		return groupUserId;
	}

	/**
	 * @param groupUserId the groupUserId to set
	 */
	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}
}