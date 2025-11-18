package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaInfoType extends CommonVo{
    private String infotpId;

//    private Date expDtm;

//    private Date strDtm;

    private String infotpLnm;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

//    private String objDescn;

//    private Integer objVers;

    private Date writDtm;

    private String writUserId;
    
    private String oraDataType;

    private String myDataType;
    
    private String msDataType;
    
    


	public String getOraDataType() {
		return oraDataType;
	}

	public void setOraDataType(String oraDataType) {
		this.oraDataType = oraDataType;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getMsDataType() {
		return msDataType;
	}

	public void setMsDataType(String msDataType) {
		this.msDataType = msDataType;
	}

	public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
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