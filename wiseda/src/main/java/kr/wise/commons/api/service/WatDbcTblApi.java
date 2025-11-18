package kr.wise.commons.api.service;

import java.math.BigDecimal;
import java.util.Date;
import kr.wise.commons.cmm.CommonVo;

public class WatDbcTblApi {
//    private String dbSchId;

    private String dbcTblNm;

    private String dbcTblKorNm;

    private Integer vers;

    private String regTyp;

    private Date regDtm;

    private Date updDtm;

    private String descn;

//    private String dbConnTrgId;

    private String dbcTblSpacNm;

    private String ddlTblId;

    private String pdmTblId;

    private String dbmsType;

    private String subjId;

    private BigDecimal colEacnt;

    private BigDecimal rowEacnt;

    private BigDecimal tblSize;

    private BigDecimal dataSize;

    private BigDecimal idxSize;

    private BigDecimal nuseSize;

    private BigDecimal bfColEacnt;

    private BigDecimal bfRowEacnt;

    private BigDecimal bfTblSize;

    private BigDecimal bfDataSize;

    private BigDecimal bfIdxSize;

    private BigDecimal bfNuseSize;

    private Date anaDtm;

    private Date crtDtm;

    private Date chgDtm;

    private String pdmDescn;

    private String tblDqExpYn;

    private String ddlTblErrExs;

    private String ddlTblErrCd;

    private String ddlTblErrDescn;

    private String ddlColErrExs;

    private String ddlColErrCd;

    private String ddlColErrDescn;

    private String pdmTblErrExs;

    private String pdmTblErrCd;

    private String pdmTblErrDescn;

    private String pdmColErrExs;

    private String pdmColErrCd;

    private String pdmColErrDescn;

    private String ddlTblExtncExs;

    private String pdmTblExtncExs;

    
    private String dbConnTrgLnm;
    
//    private String dbConnTrgPnm;
    
    private String dbSchLnm;
    
//    private String dbSchPnm;
    
    private String subjLnm;
    
//    private String fullPath;
    
    private String ddlTblLnm;
    
    private String ddlTblPnm;
    
    private String pdmTblLnm;
    
    private String pdmTblPnm;
    
    private String dbcColNm;
    
    //의존객체
    private String dpndNm;
    private String dpndObjTyp;
    private String repObjDbSchPnm;
    private String repObjNm;
    private String repObjTyp;
    private String repObjLinkNm;
    private String dpndTyp;
    
    //기타오브젝트
    private String objectType;
    private String objectName;
    private String ddlText;
    
    //시퀀스정보
    private String minval;
    private String maxval;
    private String incby;
    private String cycYn;
    private String ordYn;
    private String cacheSz;
    private String strtwt;
    //인덱스
    private String pkYn;
    private String uqYn;
    //파티션테이블여부
    private String partYn;
    private String depcCnt;
    private String depcYn;
    
    private String dbcIdxNm;
    private String dbcIdxKorNm;
    private String idxTypCd;
    
    private String dbcObjNm;
    
    private String status;
    
    private String ddlEtcId;
    
    private String tblSpacType; 
    private String defltYn; 
    private String tblSpacSize; 
    private String tblSpacRmsUseQnt; 
    private String tblSpacUseQnt; 
    private String tblSpacBgnnSize; 
    private String tblSpacMaxSize; 
    private String tblSpacIncSize; 
    private String tblSpacUseRt;      
    
    private BigDecimal dbcObjCnt;
    private BigDecimal dbcTblCnt;
    private BigDecimal dbcIdxCnt;
    
    private BigDecimal bfIdxEacnt;
    
    
    private String temporary;
    
    private String generated;
    
    private String secondary;
    
    

	public String getSecondary() {
		return secondary;
	}

	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}

	public String getGenerated() {
		return generated;
	}

	public void setGenerated(String generated) {
		this.generated = generated;
	}

	public String getTemporary() {
		return temporary;
	}

	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}

	public BigDecimal getBfIdxEacnt() {
		return bfIdxEacnt;
	}

	public void setBfIdxEacnt(BigDecimal bfIdxEacnt) {
		this.bfIdxEacnt = bfIdxEacnt;
	}

	public BigDecimal getDbcTblCnt() {
		return dbcTblCnt;
	}

	public void setDbcTblCnt(BigDecimal dbcTblCnt) {
		this.dbcTblCnt = dbcTblCnt;
	}

	public BigDecimal getDbcIdxCnt() {
		return dbcIdxCnt;
	}

	public void setDbcIdxCnt(BigDecimal dbcIdxCnt) {
		this.dbcIdxCnt = dbcIdxCnt;
	}

	public BigDecimal getDbcObjCnt() {
		return dbcObjCnt;
	}

	public void setDbcObjCnt(BigDecimal dbcObjCnt) {
		this.dbcObjCnt = dbcObjCnt;
	}

	public String getTblSpacType() {
		return tblSpacType;
	}

	public void setTblSpacType(String tblSpacType) {
		this.tblSpacType = tblSpacType;
	}

	public String getDefltYn() {
		return defltYn;
	}

	public void setDefltYn(String defltYn) {
		this.defltYn = defltYn;
	}

	public String getTblSpacSize() {
		return tblSpacSize;
	}

	public void setTblSpacSize(String tblSpacSize) {
		this.tblSpacSize = tblSpacSize;
	}

	public String getTblSpacRmsUseQnt() {
		return tblSpacRmsUseQnt;
	}

	public void setTblSpacRmsUseQnt(String tblSpacRmsUseQnt) {
		this.tblSpacRmsUseQnt = tblSpacRmsUseQnt;
	}

	public String getTblSpacUseQnt() {
		return tblSpacUseQnt;
	}

	public void setTblSpacUseQnt(String tblSpacUseQnt) {
		this.tblSpacUseQnt = tblSpacUseQnt;
	}

	public String getTblSpacBgnnSize() {
		return tblSpacBgnnSize;
	}

	public void setTblSpacBgnnSize(String tblSpacBgnnSize) {
		this.tblSpacBgnnSize = tblSpacBgnnSize;
	}

	public String getTblSpacMaxSize() {
		return tblSpacMaxSize;
	}

	public void setTblSpacMaxSize(String tblSpacMaxSize) {
		this.tblSpacMaxSize = tblSpacMaxSize;
	}

	public String getTblSpacIncSize() {
		return tblSpacIncSize;
	}

	public void setTblSpacIncSize(String tblSpacIncSize) {
		this.tblSpacIncSize = tblSpacIncSize;
	}

	public String getTblSpacUseRt() {
		return tblSpacUseRt;
	}

	public void setTblSpacUseRt(String tblSpacUseRt) {
		this.tblSpacUseRt = tblSpacUseRt;
	}

	public String getDbcObjNm() {
		return dbcObjNm;
	}

	public void setDbcObjNm(String dbcObjNm) {
		this.dbcObjNm = dbcObjNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDdlEtcId() {
		return ddlEtcId;
	}

	public void setDdlEtcId(String ddlEtcId) {
		this.ddlEtcId = ddlEtcId;
	}

	public String getDpndNm() {
		return dpndNm;
	}

	public void setDpndNm(String dpndNm) {
		this.dpndNm = dpndNm;
	}

	public String getDpndObjTyp() {
		return dpndObjTyp;
	}

	public void setDpndObjTyp(String dpndObjTyp) {
		this.dpndObjTyp = dpndObjTyp;
	}

	public String getRepObjDbSchPnm() {
		return repObjDbSchPnm;
	}

	public void setRepObjDbSchPnm(String repObjDbSchPnm) {
		this.repObjDbSchPnm = repObjDbSchPnm;
	}

	public String getRepObjNm() {
		return repObjNm;
	}

	public void setRepObjNm(String repObjNm) {
		this.repObjNm = repObjNm;
	}

	public String getRepObjTyp() {
		return repObjTyp;
	}

	public void setRepObjTyp(String repObjTyp) {
		this.repObjTyp = repObjTyp;
	}

	public String getRepObjLinkNm() {
		return repObjLinkNm;
	}

	public void setRepObjLinkNm(String repObjLinkNm) {
		this.repObjLinkNm = repObjLinkNm;
	}

	public String getDpndTyp() {
		return dpndTyp;
	}

	public void setDpndTyp(String dpndTyp) {
		this.dpndTyp = dpndTyp;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDdlText() {
		return ddlText;
	}

	public void setDdlText(String ddlText) {
		this.ddlText = ddlText;
	}

	public String getMinval() {
		return minval;
	}

	public void setMinval(String minval) {
		this.minval = minval;
	}

	public String getMaxval() {
		return maxval;
	}

	public void setMaxval(String maxval) {
		this.maxval = maxval;
	}

	public String getIncby() {
		return incby;
	}

	public void setIncby(String incby) {
		this.incby = incby;
	}

	public String getCycYn() {
		return cycYn;
	}

	public void setCycYn(String cycYn) {
		this.cycYn = cycYn;
	}

	public String getOrdYn() {
		return ordYn;
	}

	public void setOrdYn(String ordYn) {
		this.ordYn = ordYn;
	}

	public String getCacheSz() {
		return cacheSz;
	}

	public void setCacheSz(String cacheSz) {
		this.cacheSz = cacheSz;
	}

	public String getStrtwt() {
		return strtwt;
	}

	public void setStrtwt(String strtwt) {
		this.strtwt = strtwt;
	}

	public String getPkYn() {
		return pkYn;
	}

	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}

	public String getUqYn() {
		return uqYn;
	}

	public void setUqYn(String uqYn) {
		this.uqYn = uqYn;
	}

	public String getPartYn() {
		return partYn;
	}

	public void setPartYn(String partYn) {
		this.partYn = partYn;
	}

	public String getDepcCnt() {
		return depcCnt;
	}

	public void setDepcCnt(String depcCnt) {
		this.depcCnt = depcCnt;
	}

	public String getDepcYn() {
		return depcYn;
	}

	public void setDepcYn(String depcYn) {
		this.depcYn = depcYn;
	}

	public String getDbcIdxNm() {
		return dbcIdxNm;
	}

	public void setDbcIdxNm(String dbcIdxNm) {
		this.dbcIdxNm = dbcIdxNm;
	}

	public String getDbcIdxKorNm() {
		return dbcIdxKorNm;
	}

	public void setDbcIdxKorNm(String dbcIdxKorNm) {
		this.dbcIdxKorNm = dbcIdxKorNm;
	}

	public String getIdxTypCd() {
		return idxTypCd;
	}

	public void setIdxTypCd(String idxTypCd) {
		this.idxTypCd = idxTypCd;
	}

	/**
	 * @return the dbConnTrgLnm
	 */
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	/**
	 * @param dbConnTrgLnm the dbConnTrgLnm to set
	 */
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

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

	/**
	 * @return the subjLnm
	 */
	public String getSubjLnm() {
		return subjLnm;
	}

	/**
	 * @param subjLnm the subjLnm to set
	 */
	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	/**
	 * @return the ddlTblLnm
	 */
	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	/**
	 * @param ddlTblLnm the ddlTblLnm to set
	 */
	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	/**
	 * @return the pdmTblLnm
	 */
	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	/**
	 * @param pdmTblLnm the pdmTblLnm to set
	 */
	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

//	public String getDbSchId() {
//        return dbSchId;
//    }
//
//    public void setDbSchId(String dbSchId) {
//        this.dbSchId = dbSchId;
//    }

    public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getDbcTblKorNm() {
        return dbcTblKorNm;
    }

    public void setDbcTblKorNm(String dbcTblKorNm) {
        this.dbcTblKorNm = dbcTblKorNm;
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

//    public String getDbConnTrgId() {
//        return dbConnTrgId;
//    }
//
//    public void setDbConnTrgId(String dbConnTrgId) {
//        this.dbConnTrgId = dbConnTrgId;
//    }

    public String getDbcTblSpacNm() {
        return dbcTblSpacNm;
    }

    public void setDbcTblSpacNm(String dbcTblSpacNm) {
        this.dbcTblSpacNm = dbcTblSpacNm;
    }

    public String getDdlTblId() {
        return ddlTblId;
    }

    public void setDdlTblId(String ddlTblId) {
        this.ddlTblId = ddlTblId;
    }

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }

    public String getDbmsType() {
        return dbmsType;
    }

    public void setDbmsType(String dbmsType) {
        this.dbmsType = dbmsType;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public BigDecimal getColEacnt() {
        return colEacnt;
    }

    public void setColEacnt(BigDecimal colEacnt) {
        this.colEacnt = colEacnt;
    }

    public BigDecimal getRowEacnt() {
        return rowEacnt;
    }

    public void setRowEacnt(BigDecimal rowEacnt) {
        this.rowEacnt = rowEacnt;
    }

    public BigDecimal getTblSize() {
        return tblSize;
    }

    public void setTblSize(BigDecimal tblSize) {
        this.tblSize = tblSize;
    }

    public BigDecimal getDataSize() {
        return dataSize;
    }

    public void setDataSize(BigDecimal dataSize) {
        this.dataSize = dataSize;
    }

    public BigDecimal getIdxSize() {
        return idxSize;
    }

    public void setIdxSize(BigDecimal idxSize) {
        this.idxSize = idxSize;
    }

    public BigDecimal getNuseSize() {
        return nuseSize;
    }

    public void setNuseSize(BigDecimal nuseSize) {
        this.nuseSize = nuseSize;
    }

    public BigDecimal getBfColEacnt() {
        return bfColEacnt;
    }

    public void setBfColEacnt(BigDecimal bfColEacnt) {
        this.bfColEacnt = bfColEacnt;
    }

    public BigDecimal getBfRowEacnt() {
        return bfRowEacnt;
    }

    public void setBfRowEacnt(BigDecimal bfRowEacnt) {
        this.bfRowEacnt = bfRowEacnt;
    }

    public BigDecimal getBfTblSize() {
        return bfTblSize;
    }

    public void setBfTblSize(BigDecimal bfTblSize) {
        this.bfTblSize = bfTblSize;
    }

    public BigDecimal getBfDataSize() {
        return bfDataSize;
    }

    public void setBfDataSize(BigDecimal bfDataSize) {
        this.bfDataSize = bfDataSize;
    }

    public BigDecimal getBfIdxSize() {
        return bfIdxSize;
    }

    public void setBfIdxSize(BigDecimal bfIdxSize) {
        this.bfIdxSize = bfIdxSize;
    }

    public BigDecimal getBfNuseSize() {
        return bfNuseSize;
    }

    public void setBfNuseSize(BigDecimal bfNuseSize) {
        this.bfNuseSize = bfNuseSize;
    }

    public Date getAnaDtm() {
        return anaDtm;
    }

    public void setAnaDtm(Date anaDtm) {
        this.anaDtm = anaDtm;
    }

    public Date getCrtDtm() {
        return crtDtm;
    }

    public void setCrtDtm(Date crtDtm) {
        this.crtDtm = crtDtm;
    }

    public Date getChgDtm() {
        return chgDtm;
    }

    public void setChgDtm(Date chgDtm) {
        this.chgDtm = chgDtm;
    }

    public String getPdmDescn() {
        return pdmDescn;
    }

    public void setPdmDescn(String pdmDescn) {
        this.pdmDescn = pdmDescn;
    }

    public String getTblDqExpYn() {
        return tblDqExpYn;
    }

    public void setTblDqExpYn(String tblDqExpYn) {
        this.tblDqExpYn = tblDqExpYn;
    }

    public String getDdlTblErrExs() {
        return ddlTblErrExs;
    }

    public void setDdlTblErrExs(String ddlTblErrExs) {
        this.ddlTblErrExs = ddlTblErrExs;
    }

    public String getDdlTblErrCd() {
        return ddlTblErrCd;
    }

    public void setDdlTblErrCd(String ddlTblErrCd) {
        this.ddlTblErrCd = ddlTblErrCd;
    }

    public String getDdlTblErrDescn() {
        return ddlTblErrDescn;
    }

    public void setDdlTblErrDescn(String ddlTblErrDescn) {
        this.ddlTblErrDescn = ddlTblErrDescn;
    }

    public String getDdlColErrExs() {
        return ddlColErrExs;
    }

    public void setDdlColErrExs(String ddlColErrExs) {
        this.ddlColErrExs = ddlColErrExs;
    }

    public String getDdlColErrCd() {
        return ddlColErrCd;
    }

    public void setDdlColErrCd(String ddlColErrCd) {
        this.ddlColErrCd = ddlColErrCd;
    }

    public String getDdlColErrDescn() {
        return ddlColErrDescn;
    }

    public void setDdlColErrDescn(String ddlColErrDescn) {
        this.ddlColErrDescn = ddlColErrDescn;
    }

    public String getPdmTblErrExs() {
        return pdmTblErrExs;
    }

    public void setPdmTblErrExs(String pdmTblErrExs) {
        this.pdmTblErrExs = pdmTblErrExs;
    }

    public String getPdmTblErrCd() {
        return pdmTblErrCd;
    }

    public void setPdmTblErrCd(String pdmTblErrCd) {
        this.pdmTblErrCd = pdmTblErrCd;
    }

    public String getPdmTblErrDescn() {
        return pdmTblErrDescn;
    }

    public void setPdmTblErrDescn(String pdmTblErrDescn) {
        this.pdmTblErrDescn = pdmTblErrDescn;
    }

    public String getPdmColErrExs() {
        return pdmColErrExs;
    }

    public void setPdmColErrExs(String pdmColErrExs) {
        this.pdmColErrExs = pdmColErrExs;
    }

    public String getPdmColErrCd() {
        return pdmColErrCd;
    }

    public void setPdmColErrCd(String pdmColErrCd) {
        this.pdmColErrCd = pdmColErrCd;
    }

    public String getPdmColErrDescn() {
        return pdmColErrDescn;
    }

    public void setPdmColErrDescn(String pdmColErrDescn) {
        this.pdmColErrDescn = pdmColErrDescn;
    }

    public String getDdlTblExtncExs() {
        return ddlTblExtncExs;
    }

    public void setDdlTblExtncExs(String ddlTblExtncExs) {
        this.ddlTblExtncExs = ddlTblExtncExs;
    }

    public String getPdmTblExtncExs() {
        return pdmTblExtncExs;
    }

    public void setPdmTblExtncExs(String pdmTblExtncExs) {
        this.pdmTblExtncExs = pdmTblExtncExs;
    }

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

//	public String getDbConnTrgPnm() {
//		return dbConnTrgPnm;
//	}
//
//	public void setDbConnTrgPnm(String dbConnTrgPnm) {
//		this.dbConnTrgPnm = dbConnTrgPnm;
//	}

//	public String getDbSchPnm() {
//		return dbSchPnm;
//	}
//
//	public void setDbSchPnm(String dbSchPnm) {
//		this.dbSchPnm = dbSchPnm;
//	}

//	public String getFullPath() {
//		return fullPath;
//	}
//
//	public void setFullPath(String fullPath) {
//		this.fullPath = fullPath;
//	}

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}
}