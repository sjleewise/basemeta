package kr.wise.commons.damgmt.db.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbmsDataType extends CommonVo {
    private String dbmsDataTypeId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String dataTypeNm;

    private String dbmsTypCd;

    private Integer maxDataLen;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    private String dbmsTypCdNm;
    
    
    /**
	 * @return the dbmsTypCdNm
	 */
	public String getDbmsTypCdNm() {
		return dbmsTypCdNm;
	}

	/**
	 * @param dbmsTypCdNm the dbmsTypCdNm to set
	 */
	public void setDbmsTypCdNm(String dbmsTypCdNm) {
		this.dbmsTypCdNm = dbmsTypCdNm;
	}

	public String getDbmsDataTypeId() {
        return dbmsDataTypeId;
    }

    public void setDbmsDataTypeId(String dbmsDataTypeId) {
        this.dbmsDataTypeId = dbmsDataTypeId;
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

    public String getDataTypeNm() {
        return dataTypeNm;
    }

    public void setDataTypeNm(String dataTypeNm) {
        this.dataTypeNm = dataTypeNm;
    }

    public String getDbmsTypCd() {
        return dbmsTypCd;
    }

    public void setDbmsTypCd(String dbmsTypCd) {
        this.dbmsTypCd = dbmsTypCd;
    }

    public Integer getMaxDataLen() {
        return maxDataLen;
    }

    public void setMaxDataLen(Integer maxDataLen) {
        this.maxDataLen = maxDataLen;
    }

	/** yeonho */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbmsDataType [dbmsDataTypeId=")
				.append(dbmsDataTypeId).append(", dataTypeNm=")
				.append(dataTypeNm).append(", dbmsTypCd=").append(dbmsTypCd)
				.append(", maxDataLen=").append(maxDataLen).append("]");
		return builder.toString() + super.toString();
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