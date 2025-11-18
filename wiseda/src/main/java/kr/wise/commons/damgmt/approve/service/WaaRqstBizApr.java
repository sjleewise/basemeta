package kr.wise.commons.damgmt.approve.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaRqstBizApr extends CommonVo{

	/** 업무구분코드 */
    private String bizDcd;

    /** 결재레벨 */
    private Integer aprLvl;
    
    /** 이전업무구분코드 */
    private String bizOldDcd;

    /** 이전결재레벨 */
    private Integer aprOldLvl;
    
    /** 시스템구분코드 */
    private String sysDcd;

//    private Date expDtm;
//
//    private Date strDtm;

    /** 결재그룹ID */
    private String aprgId;

    /** 결재방식코드 */
    private String aprFrmlCd;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getBizDcd() {
        return bizDcd;
    }

    public void setBizDcd(String bizDcd) {
        this.bizDcd = bizDcd;
    }

    public Integer getAprLvl() {
        return aprLvl;
    }

    public void setAprLvl(Integer aprLvl) {
        this.aprLvl = aprLvl;
    }

    
    
    public String getBizOldDcd() {
		return bizOldDcd;
	}

	public void setBizOldDcd(String bizOldDcd) {
		this.bizOldDcd = bizOldDcd;
	}

	public Integer getAprOldLvl() {
		return aprOldLvl;
	}

	public void setAprOldLvl(Integer aprOldLvl) {
		this.aprOldLvl = aprOldLvl;
	}

	public String getSysDcd() {
        return sysDcd;
    }

    public void setSysDcd(String sysDcd) {
        this.sysDcd = sysDcd;
    }

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

    public String getAprgId() {
        return aprgId;
    }

    public void setAprgId(String aprgId) {
        this.aprgId = aprgId;
    }

    public String getAprFrmlCd() {
        return aprFrmlCd;
    }

    public void setAprFrmlCd(String aprFrmlCd) {
        this.aprFrmlCd = aprFrmlCd;
    }

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaRqstBizApr [bizDcd=").append(bizDcd).append(bizOldDcd).append(bizOldDcd)
				.append(", aprLvl=").append(aprLvl).append(", aprOldLvl=").append(aprOldLvl)
				.append(", sysDcd=").append(sysDcd).append(", aprgId=").append(aprgId)
				.append(", aprFrmlCd=").append(aprFrmlCd).append("]");
		return builder.toString();
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