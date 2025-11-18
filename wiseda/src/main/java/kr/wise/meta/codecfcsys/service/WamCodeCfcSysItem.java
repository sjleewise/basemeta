package kr.wise.meta.codecfcsys.service;

import java.util.Date;

public class WamCodeCfcSysItem extends WamCodeCfcSys {
//    private String codeCfcSysId;
//
//    private String codeCfcSysCd;

    private Integer codeCfcSysItemSeq;

    private String codeCfcSysItemCd;

    private String codeCfcSysItemFrm;

    private Short codeCfcSysItemLen;

    private String codeCfcSysItemSpt;

    private String codeCfcSysItemRefTbl;

    private String codeCfcSysItemRefCol;
    
    private String codeCfcSysItemRefId;
    

//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;
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

//    public String getCodeCfcSysId() {
//        return codeCfcSysId;
//    }
//
//    public void setCodeCfcSysId(String codeCfcSysId) {
//        this.codeCfcSysId = codeCfcSysId;
//    }
//
//    public String getCodeCfcSysCd() {
//        return codeCfcSysCd;
//    }
//
//    public void setCodeCfcSysCd(String codeCfcSysCd) {
//        this.codeCfcSysCd = codeCfcSysCd;
//    }

    public Integer getCodeCfcSysItemSeq() {
        return codeCfcSysItemSeq;
    }

    public void setCodeCfcSysItemSeq(Integer codeCfcSysItemSeq) {
        this.codeCfcSysItemSeq = codeCfcSysItemSeq;
    }

    public String getCodeCfcSysItemCd() {
        return codeCfcSysItemCd;
    }

    public void setCodeCfcSysItemCd(String codeCfcSysItemCd) {
        this.codeCfcSysItemCd = codeCfcSysItemCd;
    }

    public String getCodeCfcSysItemFrm() {
        return codeCfcSysItemFrm;
    }

    public void setCodeCfcSysItemFrm(String codeCfcSysItemFrm) {
        this.codeCfcSysItemFrm = codeCfcSysItemFrm;
    }

    public Short getCodeCfcSysItemLen() {
        return codeCfcSysItemLen;
    }

    public void setCodeCfcSysItemLen(Short codeCfcSysItemLen) {
        this.codeCfcSysItemLen = codeCfcSysItemLen;
    }

    public String getCodeCfcSysItemSpt() {
        return codeCfcSysItemSpt;
    }

    public void setCodeCfcSysItemSpt(String codeCfcSysItemSpt) {
        this.codeCfcSysItemSpt = codeCfcSysItemSpt;
    }

    public String getCodeCfcSysItemRefTbl() {
        return codeCfcSysItemRefTbl;
    }

    public void setCodeCfcSysItemRefTbl(String codeCfcSysItemRefTbl) {
        this.codeCfcSysItemRefTbl = codeCfcSysItemRefTbl;
    }

    public String getCodeCfcSysItemRefCol() {
        return codeCfcSysItemRefCol;
    }

    public void setCodeCfcSysItemRefCol(String codeCfcSysItemRefCol) {
        this.codeCfcSysItemRefCol = codeCfcSysItemRefCol;
    }

	public String getCodeCfcSysItemRefId() {
		return codeCfcSysItemRefId;
	}

	public void setCodeCfcSysItemRefId(String codeCfcSysItemRefId) {
		this.codeCfcSysItemRefId = codeCfcSysItemRefId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamCodeCfcSysItem [codeCfcSysItemSeq=")
				.append(codeCfcSysItemSeq).append(", codeCfcSysItemCd=")
				.append(codeCfcSysItemCd).append(", codeCfcSysItemFrm=")
				.append(codeCfcSysItemFrm).append(", codeCfcSysItemLen=")
				.append(codeCfcSysItemLen).append(", codeCfcSysItemSpt=")
				.append(codeCfcSysItemSpt).append(", codeCfcSysItemRefTbl=")
				.append(codeCfcSysItemRefTbl).append(", codeCfcSysItemRefCol=")
				.append(codeCfcSysItemRefCol).append(", codeCfcSysItemRefId=")
				.append(codeCfcSysItemRefId).append("]");
		return builder.toString() + super.toString();
	}

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