package kr.wise.commons.damgmt.dmnginfo.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDmngInfotpMap extends CommonVo {
    private String dmngId;

    private String infotpId;

//    private Date expDtm;
//
//    private Date strDtm;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private Date writDtm;
    
    private String infotpChgCanYn;

//	private String writUserId;
    
    private String dmngLnm;
    
    private String dmngPnm;
    
    private String infotpLnm;
    
    private String dataType;
     
    private String cdDmnYn;
    
	private String dataLen;
    
    private String dataScal;
    
    private String uppDmngId;
    
    private String dmngLvl;
    
    public String getCdDmnYn() {
		return cdDmnYn;
	}

	public void setCdDmnYn(String cdDmnYn) {
		this.cdDmnYn = cdDmnYn;
	}

	public void setInfotpChgCanYn(String infotpChgCanYn) {
		this.infotpChgCanYn = infotpChgCanYn;
	}

	public String getInfotpChgCanYn() {
		return infotpChgCanYn;
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

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public String getDataScal() {
		return dataScal;
	}

	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}

	public String getUppDmngId() {
		return uppDmngId;
	}

	public void setUppDmngId(String uppDmngId) {
		this.uppDmngId = uppDmngId;
	}

	public String getDmngLvl() {
		return dmngLvl;
	}

	public void setDmngLvl(String dmngLvl) {
		this.dmngLvl = dmngLvl;
	}


    

    public String getDmngId() {
        return dmngId;
    }

    public void setDmngId(String dmngId) {
        this.dmngId = dmngId;
    }

    public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDmngInfotpMap [dmngId=").append(dmngId)
				.append(", infotpId=").append(infotpId)
				.append(", infotpChgCanYn=").append(infotpChgCanYn)
				.append(", dmngLnm=").append(dmngLnm).append(", dmngPnm=")
				.append(dmngPnm).append(", infotpLnm=").append(infotpLnm)
				.append(", dataType=").append(dataType).append(", cdDmnYn=")
				.append(cdDmnYn).append(", dataLen=").append(dataLen)
				.append(", dataScal=").append(dataScal).append(", uppDmngId=")
				.append(uppDmngId).append(", dmngLvl=").append(dmngLvl)
				.append("]");
		return builder.toString() + super.toString();
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