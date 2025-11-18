package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlCol extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private Integer rqstDtlSno;

    private String ddlColId;

    private String ddlColPnm;

    private String ddlColLnm;

    private String bfDdlColPnm;

//    private String rqstDcd;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String ddlTblId;

    private String ddlTblPnm;

    private Integer colOrd;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;
    
    private Integer pkOrd;

    private String fkYn;
    
    private String akYn;
    private String nonulYn;

    private String defltVal;

    private String colUpdYn;

    private String dataTypeUpdYn;

    private String nonulUpdYn;

    private String defltValUpdYn;
    
    private String encYn;
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

    public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }

    public Integer getRqstDtlSno() {
        return rqstDtlSno;
    }

    public void setRqstDtlSno(Integer rqstDtlSno) {
        this.rqstDtlSno = rqstDtlSno;
    }

    public String getDdlColId() {
        return ddlColId;
    }

    public void setDdlColId(String ddlColId) {
        this.ddlColId = ddlColId;
    }

    public String getDdlColPnm() {
        return ddlColPnm;
    }

    public void setDdlColPnm(String ddlColPnm) {
        this.ddlColPnm = ddlColPnm;
    }

    public String getDdlColLnm() {
        return ddlColLnm;
    }

    public void setDdlColLnm(String ddlColLnm) {
        this.ddlColLnm = ddlColLnm;
    }

    public String getBfDdlColPnm() {
        return bfDdlColPnm;
    }

    public void setBfDdlColPnm(String bfDdlColPnm) {
        this.bfDdlColPnm = bfDdlColPnm;
    }

//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
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

    public String getDdlTblId() {
        return ddlTblId;
    }

    public void setDdlTblId(String ddlTblId) {
        this.ddlTblId = ddlTblId;
    }

    public String getDdlTblPnm() {
        return ddlTblPnm;
    }

    public void setDdlTblPnm(String ddlTblPnm) {
        this.ddlTblPnm = ddlTblPnm;
    }

    public Integer getColOrd() {
        return colOrd;
    }

    public void setColOrd(Integer colOrd) {
        this.colOrd = colOrd;
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

    public String getPkYn() {
        return pkYn;
    }

    public void setPkYn(String pkYn) {
        this.pkYn = pkYn;
    }

    public Integer getPkOrd() {
        return pkOrd;
    }

    public void setPkOrd(Integer pkOrd) {
        this.pkOrd = pkOrd;
    }

    public String getFkYn() {
		return fkYn;
	}

	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}

	public String getAkYn() {
		return akYn;
	}

	public void setAkYn(String akYn) {
		this.akYn = akYn;
	}

	public String getNonulYn() {
        return nonulYn;
    }

    public void setNonulYn(String nonulYn) {
        this.nonulYn = nonulYn;
    }

    public String getDefltVal() {
        return defltVal;
    }

    public void setDefltVal(String defltVal) {
        this.defltVal = defltVal;
    }

    public String getColUpdYn() {
        return colUpdYn;
    }

    public void setColUpdYn(String colUpdYn) {
        this.colUpdYn = colUpdYn;
    }

    public String getDataTypeUpdYn() {
        return dataTypeUpdYn;
    }

    public void setDataTypeUpdYn(String dataTypeUpdYn) {
        this.dataTypeUpdYn = dataTypeUpdYn;
    }

    public String getNonulUpdYn() {
        return nonulUpdYn;
    }

    public void setNonulUpdYn(String nonulUpdYn) {
        this.nonulUpdYn = nonulUpdYn;
    }

    public String getDefltValUpdYn() {
        return defltValUpdYn;
    }

    public void setDefltValUpdYn(String defltValUpdYn) {
        this.defltValUpdYn = defltValUpdYn;
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