package kr.wise.commons.damgmt.approve.service;


public class WaaRqstAprp extends WaaRqstBizApr {

	/** 시스템구분코드 */
//	private String sysDcd;

	/** 업무구분코드 */
//	private String bizDcd;

	/**	결재레벨 */
//	private Short aprLvl;

	/**	결재자ID */
    private String userId;

    /**	결재방식코드 */
//    private String aprFrmlCd;

    /**	결재그룹ID */
//    private String aprgId;

    /** 전결대결구분코드 */
    private String abdDaprDcd;

    /**	대체결재자ID */
    private String sbsAprpId;
    private String sbsAprpNm;

    /** 대결시작일자 */
    private String daprStrDt;

    /** 대결종료일자 */
    private String daprEndDt;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
//
//    public String getSysDcd() {
//        return sysDcd;
//    }
//
//    public void setSysDcd(String sysDcd) {
//        this.sysDcd = sysDcd;
//    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }
//
//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
//    }

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

    public String getSbsAprpId() {
        return sbsAprpId;
    }

    public void setSbsAprpId(String sbsAprpId) {
        this.sbsAprpId = sbsAprpId;
    }

    public String getAbdDaprDcd() {
        return abdDaprDcd;
    }

    public void setAbdDaprDcd(String abdDaprDcd) {
        this.abdDaprDcd = abdDaprDcd;
    }

    public String getDaprStrDt() {
        return daprStrDt;
    }

    public void setDaprStrDt(String daprStrDt) {
        this.daprStrDt = daprStrDt;
    }

    public String getDaprEndDt() {
        return daprEndDt;
    }

    public void setDaprEndDt(String daprEndDt) {
        this.daprEndDt = daprEndDt;
    }

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaRqstAprp [userId=").append(userId)
				.append(", abdDaprDcd=").append(abdDaprDcd)
				.append(", sbsAprpId=").append(sbsAprpId)
				.append(", sbsAprpNm=").append(sbsAprpNm)
				.append(", daprStrDt=").append(daprStrDt)
				.append(", daprEndDt=").append(daprEndDt).append("]");
		return builder.toString()+super.toString();
	}

	/**
	 * @return the sbsAprpNm
	 */
	public String getSbsAprpNm() {
		return sbsAprpNm;
	}

	/**
	 * @param sbsAprpNm the sbsAprpNm to set
	 */
	public void setSbsAprpNm(String sbsAprpNm) {
		this.sbsAprpNm = sbsAprpNm;
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
}