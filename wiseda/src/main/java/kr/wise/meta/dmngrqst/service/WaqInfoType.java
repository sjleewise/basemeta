package kr.wise.meta.dmngrqst.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaqInfoType extends CommonVo{
    private String rqstNo;

    private String stndAsrt;

    private String infotpId;

    private String rqstDcd;

    private String rvwStsCd;

    private String rvwConts;

    private String vrfCd;

    private String vrfRmk;

    private String infotpLnm;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String objDescn;

    private Integer objVers;

    private Date writDtm;

    private String writUserId;
    
    private String dmngLnm;
    
    private String dmngPnm;
    
    private String dmngId;
    
    private String dmngLvl;
    
    

    public String getDmngId() {
		return dmngId;
	}

	public void setDmngId(String dmngId) {
		this.dmngId = dmngId;
	}

	public String getDmngLvl() {
		return dmngLvl;
	}

	public void setDmngLvl(String dmngLvl) {
		this.dmngLvl = dmngLvl;
	}

	public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

	public String getDmngPnm() {
		return dmngPnm;
	}

	public void setDmngPnm(String dmngPnm) {
		this.dmngPnm = dmngPnm;
	}

	public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public String getStndAsrt() {
        return stndAsrt;
    }

    public void setStndAsrt(String stndAsrt) {
        this.stndAsrt = stndAsrt;
    }

    public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
    }

    public String getRqstDcd() {
        return rqstDcd;
    }

    public void setRqstDcd(String rqstDcd) {
        this.rqstDcd = rqstDcd;
    }

    public String getRvwStsCd() {
        return rvwStsCd;
    }

    public void setRvwStsCd(String rvwStsCd) {
        this.rvwStsCd = rvwStsCd;
    }

    public String getRvwConts() {
        return rvwConts;
    }

    public void setRvwConts(String rvwConts) {
        this.rvwConts = rvwConts;
    }

    public String getVrfCd() {
        return vrfCd;
    }

    public void setVrfCd(String vrfCd) {
        this.vrfCd = vrfCd;
    }

    public String getVrfRmk() {
        return vrfRmk;
    }

    public void setVrfRmk(String vrfRmk) {
        this.vrfRmk = vrfRmk;
    }

    public String getInfotpLnm() {
        return infotpLnm;
    }

    public void setInfotpLnm(String infotpLnm) {
        this.infotpLnm = infotpLnm;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getDataLen() {
        return dataLen;
    }

    public void setDataLen(Integer dataLen) {
        this.dataLen = dataLen;
    }

    public Integer getDataScal() {
        return dataScal;
    }

    public void setDataScal(Integer dataScal) {
        this.dataScal = dataScal;
    }

    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public Date getWritDtm() {
        return writDtm;
    }

    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }
}