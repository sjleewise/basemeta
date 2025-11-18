package kr.wise.meta.dbc.service;

import java.math.BigDecimal;
import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WatDbcIdxCol extends CommonVo {
    private String dbSchId;

    private String dbcTblNm;

    private String dbcIdxColNm;

    private String dbcIdxNm;

    private String dbcIdxColKorNm;

    private Integer vers;

    private String regTyp;

    private Date regDtm;

    private String regUser;

    private Date updDtm;

    private String updUser;

    private String descn;

    private String ddlIdxColId;

    private String pdmIdxColId;

    private Integer ord;

    private String sortType;

    private String dataType;

    private BigDecimal dataPnum;

    private Integer dataLen;

    private BigDecimal dataPnt;

    private String idxcolDqExpYn;

    private String ddlIdxColExtncExs;

    private String ddlOrdErrExs;

    private String pdmIdxColExtncExs;

    private String pdmOrdErrExs;

    private String ddlIdxColErrExs;

    private String pdmIdxColErrExs;

    private String idxColErrConts;
    
    private String dbSchLnm;
    
    

    /**
	 * @return the dbSchLnm
	 */
	public String getDbSchLnm() {
		return dbSchLnm;
	}

	/**
	 * @param dbSchLnm the dbSchLnm to set
	 */
	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

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

    public String getDbcIdxColNm() {
        return dbcIdxColNm;
    }

    public void setDbcIdxColNm(String dbcIdxColNm) {
        this.dbcIdxColNm = dbcIdxColNm;
    }

    public String getDbcIdxNm() {
        return dbcIdxNm;
    }

    public void setDbcIdxNm(String dbcIdxNm) {
        this.dbcIdxNm = dbcIdxNm;
    }

    public String getDbcIdxColKorNm() {
        return dbcIdxColKorNm;
    }

    public void setDbcIdxColKorNm(String dbcIdxColKorNm) {
        this.dbcIdxColKorNm = dbcIdxColKorNm;
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

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public Date getUpdDtm() {
        return updDtm;
    }

    public void setUpdDtm(Date updDtm) {
        this.updDtm = updDtm;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getDdlIdxColId() {
        return ddlIdxColId;
    }

    public void setDdlIdxColId(String ddlIdxColId) {
        this.ddlIdxColId = ddlIdxColId;
    }

    public String getPdmIdxColId() {
        return pdmIdxColId;
    }

    public void setPdmIdxColId(String pdmIdxColId) {
        this.pdmIdxColId = pdmIdxColId;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public BigDecimal getDataPnum() {
        return dataPnum;
    }

    public void setDataPnum(BigDecimal dataPnum) {
        this.dataPnum = dataPnum;
    }

    public Integer getDataLen() {
        return dataLen;
    }

    public void setDataLen(Integer dataLen) {
        this.dataLen = dataLen;
    }

    public BigDecimal getDataPnt() {
        return dataPnt;
    }

    public void setDataPnt(BigDecimal dataPnt) {
        this.dataPnt = dataPnt;
    }

    public String getIdxcolDqExpYn() {
        return idxcolDqExpYn;
    }

    public void setIdxcolDqExpYn(String idxcolDqExpYn) {
        this.idxcolDqExpYn = idxcolDqExpYn;
    }

    public String getDdlIdxColExtncExs() {
        return ddlIdxColExtncExs;
    }

    public void setDdlIdxColExtncExs(String ddlIdxColExtncExs) {
        this.ddlIdxColExtncExs = ddlIdxColExtncExs;
    }

    public String getDdlOrdErrExs() {
        return ddlOrdErrExs;
    }

    public void setDdlOrdErrExs(String ddlOrdErrExs) {
        this.ddlOrdErrExs = ddlOrdErrExs;
    }

    public String getPdmIdxColExtncExs() {
        return pdmIdxColExtncExs;
    }

    public void setPdmIdxColExtncExs(String pdmIdxColExtncExs) {
        this.pdmIdxColExtncExs = pdmIdxColExtncExs;
    }

    public String getPdmOrdErrExs() {
        return pdmOrdErrExs;
    }

    public void setPdmOrdErrExs(String pdmOrdErrExs) {
        this.pdmOrdErrExs = pdmOrdErrExs;
    }

    public String getDdlIdxColErrExs() {
        return ddlIdxColErrExs;
    }

    public void setDdlIdxColErrExs(String ddlIdxColErrExs) {
        this.ddlIdxColErrExs = ddlIdxColErrExs;
    }

    public String getPdmIdxColErrExs() {
        return pdmIdxColErrExs;
    }

    public void setPdmIdxColErrExs(String pdmIdxColErrExs) {
        this.pdmIdxColErrExs = pdmIdxColErrExs;
    }

    public String getIdxColErrConts() {
        return idxColErrConts;
    }

    public void setIdxColErrConts(String idxColErrConts) {
        this.idxColErrConts = idxColErrConts;
    }
}