package kr.wise.dq.criinfo.anatrg.service;

import java.math.BigDecimal;
import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class AnaTrgColVO extends CommonVo {
    private String dbSchId;

    private String dbcTblNm;

    private String dbcColNm;
    
    private String objNm;

    private String dbcColKorNm;

    private Integer vers;

    private String regTyp;

    private Date regDtm;

    private Date updDtm;

    private String descn;

    private String ddlColId;

    private String pdmColId;

    private String itmId;

    private String dataType;

    private Integer dataLen;

    private BigDecimal dataPnum;

    private BigDecimal dataPnt;

    private String nullYn;

    private Integer defltLen;

    private String defltVal;

    private String pkYn;

    private Integer ord;

    private Integer pkOrd;

    private String colDescn;

    private String colDqExpYn;

    private String ddlColExtncYn;

    private String ddlOrdErrExs;

    private String ddlPkYnErrExs;

    private String ddlPkOrdErrExs;

    private String ddlNullYnErrExs;

    private String ddlDefltErrExs;

    private String ddlCmmtErrExs;

    private String ddlDataTypeErrExs;

    private String ddlDataLenErrExs;

    private String ddlDataPntErrExs;

    private String ddlColErrExs;

    private String pdmColExtncExs;

    private String pdmOrdErrExs;

    private String pdmPkYnErrExs;

    private String pdmPkOrdErrExs;

    private String pdmNullYnErrExs;

    private String pdmDefltErrExs;

    private String pdmCmmtErrExs;

    private String pdmDataTypeErrExs;

    private String pdmDataLenErrExs;

    private String pdmDataPntErrExs;

    private String pdmColErrExs;

    private String colErrConts;
    

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getDbcColNm() {
        return dbcColNm;
    }

    public void setDbcColNm(String dbcColNm) {
        this.dbcColNm = dbcColNm;
    }

    public String getDbcColKorNm() {
        return dbcColKorNm;
    }

    public void setDbcColKorNm(String dbcColKorNm) {
        this.dbcColKorNm = dbcColKorNm;
    }

    public Integer getVers() {
        return vers;
    }

    public void setVers(Integer vers) {
        this.vers = vers;
    }

    public String getRegTyp() {
        return regTyp;
    }

    public void setRegTyp(String regTyp) {
        this.regTyp = regTyp;
    }

    public Date getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(Date regDtm) {
        this.regDtm = regDtm;
    }

    public Date getUpdDtm() {
        return updDtm;
    }

    public void setUpdDtm(Date updDtm) {
        this.updDtm = updDtm;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getDdlColId() {
        return ddlColId;
    }

    public void setDdlColId(String ddlColId) {
        this.ddlColId = ddlColId;
    }

    public String getPdmColId() {
        return pdmColId;
    }

    public void setPdmColId(String pdmColId) {
        this.pdmColId = pdmColId;
    }

    public String getItmId() {
        return itmId;
    }

    public void setItmId(String itmId) {
        this.itmId = itmId;
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

    public BigDecimal getDataPnum() {
        return dataPnum;
    }

    public void setDataPnum(BigDecimal dataPnum) {
        this.dataPnum = dataPnum;
    }

    public BigDecimal getDataPnt() {
        return dataPnt;
    }

    public void setDataPnt(BigDecimal dataPnt) {
        this.dataPnt = dataPnt;
    }

    public String getNullYn() {
        return nullYn;
    }

    public void setNullYn(String nullYn) {
        this.nullYn = nullYn;
    }

    public Integer getDefltLen() {
        return defltLen;
    }

    public void setDefltLen(Integer defltLen) {
        this.defltLen = defltLen;
    }

    public String getDefltVal() {
        return defltVal;
    }

    public void setDefltVal(String defltVal) {
        this.defltVal = defltVal;
    }

    public String getPkYn() {
        return pkYn;
    }

    public void setPkYn(String pkYn) {
        this.pkYn = pkYn;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Integer getPkOrd() {
        return pkOrd;
    }

    public void setPkOrd(Integer pkOrd) {
        this.pkOrd = pkOrd;
    }

    public String getColDescn() {
        return colDescn;
    }

    public void setColDescn(String colDescn) {
        this.colDescn = colDescn;
    }

    public String getColDqExpYn() {
        return colDqExpYn;
    }

    public void setColDqExpYn(String colDqExpYn) {
        this.colDqExpYn = colDqExpYn;
    }

    public String getDdlColExtncYn() {
        return ddlColExtncYn;
    }

    public void setDdlColExtncYn(String ddlColExtncYn) {
        this.ddlColExtncYn = ddlColExtncYn;
    }

    public String getDdlOrdErrExs() {
        return ddlOrdErrExs;
    }

    public void setDdlOrdErrExs(String ddlOrdErrExs) {
        this.ddlOrdErrExs = ddlOrdErrExs;
    }

    public String getDdlPkYnErrExs() {
        return ddlPkYnErrExs;
    }

    public void setDdlPkYnErrExs(String ddlPkYnErrExs) {
        this.ddlPkYnErrExs = ddlPkYnErrExs;
    }

    public String getDdlPkOrdErrExs() {
        return ddlPkOrdErrExs;
    }

    public void setDdlPkOrdErrExs(String ddlPkOrdErrExs) {
        this.ddlPkOrdErrExs = ddlPkOrdErrExs;
    }

    public String getDdlNullYnErrExs() {
        return ddlNullYnErrExs;
    }

    public void setDdlNullYnErrExs(String ddlNullYnErrExs) {
        this.ddlNullYnErrExs = ddlNullYnErrExs;
    }

    public String getDdlDefltErrExs() {
        return ddlDefltErrExs;
    }

    public void setDdlDefltErrExs(String ddlDefltErrExs) {
        this.ddlDefltErrExs = ddlDefltErrExs;
    }

    public String getDdlCmmtErrExs() {
        return ddlCmmtErrExs;
    }

    public void setDdlCmmtErrExs(String ddlCmmtErrExs) {
        this.ddlCmmtErrExs = ddlCmmtErrExs;
    }

    public String getDdlDataTypeErrExs() {
        return ddlDataTypeErrExs;
    }

    public void setDdlDataTypeErrExs(String ddlDataTypeErrExs) {
        this.ddlDataTypeErrExs = ddlDataTypeErrExs;
    }

    public String getDdlDataLenErrExs() {
        return ddlDataLenErrExs;
    }

    public void setDdlDataLenErrExs(String ddlDataLenErrExs) {
        this.ddlDataLenErrExs = ddlDataLenErrExs;
    }

    public String getDdlDataPntErrExs() {
        return ddlDataPntErrExs;
    }

    public void setDdlDataPntErrExs(String ddlDataPntErrExs) {
        this.ddlDataPntErrExs = ddlDataPntErrExs;
    }

    public String getDdlColErrExs() {
        return ddlColErrExs;
    }

    public void setDdlColErrExs(String ddlColErrExs) {
        this.ddlColErrExs = ddlColErrExs;
    }

    public String getPdmColExtncExs() {
        return pdmColExtncExs;
    }

    public void setPdmColExtncExs(String pdmColExtncExs) {
        this.pdmColExtncExs = pdmColExtncExs;
    }

    public String getPdmOrdErrExs() {
        return pdmOrdErrExs;
    }

    public void setPdmOrdErrExs(String pdmOrdErrExs) {
        this.pdmOrdErrExs = pdmOrdErrExs;
    }

    public String getPdmPkYnErrExs() {
        return pdmPkYnErrExs;
    }

    public void setPdmPkYnErrExs(String pdmPkYnErrExs) {
        this.pdmPkYnErrExs = pdmPkYnErrExs;
    }

    public String getPdmPkOrdErrExs() {
        return pdmPkOrdErrExs;
    }

    public void setPdmPkOrdErrExs(String pdmPkOrdErrExs) {
        this.pdmPkOrdErrExs = pdmPkOrdErrExs;
    }

    public String getPdmNullYnErrExs() {
        return pdmNullYnErrExs;
    }

    public void setPdmNullYnErrExs(String pdmNullYnErrExs) {
        this.pdmNullYnErrExs = pdmNullYnErrExs;
    }

    public String getPdmDefltErrExs() {
        return pdmDefltErrExs;
    }

    public void setPdmDefltErrExs(String pdmDefltErrExs) {
        this.pdmDefltErrExs = pdmDefltErrExs;
    }

    public String getPdmCmmtErrExs() {
        return pdmCmmtErrExs;
    }

    public void setPdmCmmtErrExs(String pdmCmmtErrExs) {
        this.pdmCmmtErrExs = pdmCmmtErrExs;
    }

    public String getPdmDataTypeErrExs() {
        return pdmDataTypeErrExs;
    }

    public void setPdmDataTypeErrExs(String pdmDataTypeErrExs) {
        this.pdmDataTypeErrExs = pdmDataTypeErrExs;
    }

    public String getPdmDataLenErrExs() {
        return pdmDataLenErrExs;
    }

    public void setPdmDataLenErrExs(String pdmDataLenErrExs) {
        this.pdmDataLenErrExs = pdmDataLenErrExs;
    }

    public String getPdmDataPntErrExs() {
        return pdmDataPntErrExs;
    }

    public void setPdmDataPntErrExs(String pdmDataPntErrExs) {
        this.pdmDataPntErrExs = pdmDataPntErrExs;
    }

    public String getPdmColErrExs() {
        return pdmColErrExs;
    }

    public void setPdmColErrExs(String pdmColErrExs) {
        this.pdmColErrExs = pdmColErrExs;
    }

    public String getColErrConts() {
        return colErrConts;
    }

    public void setColErrConts(String colErrConts) {
        this.colErrConts = colErrConts;
    }

	public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnaTrgColVO [dbSchId=").append(dbSchId)
				.append(", dbcTblNm=").append(dbcTblNm).append(", dbcColNm=")
				.append(dbcColNm).append(", dbcColKorNm=").append(dbcColKorNm)
				.append(", vers=").append(vers).append(", regTyp=")
				.append(regTyp).append(", regDtm=").append(regDtm)
				.append(", updDtm=").append(updDtm).append(", descn=")
				.append(descn).append(", ddlColId=").append(ddlColId)
				.append(", pdmColId=").append(pdmColId).append(", itmId=")
				.append(itmId).append(", dataType=").append(dataType)
				.append(", dataLen=").append(dataLen).append(", dataPnum=")
//				.append(dataPnum).append(", dataPnt=").append(dataPnt)
				.append(", nullYn=").append(nullYn).append(", defltLen=")
				.append(defltLen).append(", defltVal=").append(defltVal)
				.append(", pkYn=").append(pkYn).append(", ord=").append(ord)
				.append(", pkOrd=").append(pkOrd).append(", colDescn=")
				.append(colDescn).append(", colDqExpYn=").append(colDqExpYn)
				.append(", ddlColExtncYn=").append(ddlColExtncYn)
				.append(", ddlOrdErrExs=").append(ddlOrdErrExs)
				.append(", ddlPkYnErrExs=").append(ddlPkYnErrExs)
				.append(", ddlPkOrdErrExs=").append(ddlPkOrdErrExs)
				.append(", ddlNullYnErrExs=").append(ddlNullYnErrExs)
				.append(", ddlDefltErrExs=").append(ddlDefltErrExs)
				.append(", ddlCmmtErrExs=").append(ddlCmmtErrExs)
				.append(", ddlDataTypeErrExs=").append(ddlDataTypeErrExs)
				.append(", ddlDataLenErrExs=").append(ddlDataLenErrExs)
				.append(", ddlDataPntErrExs=").append(ddlDataPntErrExs)
				.append(", ddlColErrExs=").append(ddlColErrExs)
				.append(", pdmColExtncExs=").append(pdmColExtncExs)
				.append(", pdmOrdErrExs=").append(pdmOrdErrExs)
				.append(", pdmPkYnErrExs=").append(pdmPkYnErrExs)
				.append(", pdmPkOrdErrExs=").append(pdmPkOrdErrExs)
				.append(", pdmNullYnErrExs=").append(pdmNullYnErrExs)
				.append(", pdmDefltErrExs=").append(pdmDefltErrExs)
				.append(", pdmCmmtErrExs=").append(pdmCmmtErrExs)
				.append(", pdmDataTypeErrExs=").append(pdmDataTypeErrExs)
				.append(", pdmDataLenErrExs=").append(pdmDataLenErrExs)
				.append(", pdmDataPntErrExs=").append(pdmDataPntErrExs)
				.append(", pdmColErrExs=").append(pdmColErrExs)
				.append(", colErrConts=").append(colErrConts).append("]");
		return super.toString() + builder.toString();
	}
    
    
}