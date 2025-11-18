package kr.wise.meta.gap.service;

import kr.wise.meta.dbc.service.WatDbcCol;

public class WatDbcGapCol extends WatDbcCol{
    private String srcDbSchId;

    private String srcTblPnm;

    private String srcColPnm;

    private String srcColLnm;

    private String tgtDbSchId;

    private String tgtTblPnm;

    private String tgtColPnm;

    private String tgtColLnm;

    private String srcColExtncExs;

    private String tgtColExtncExs;

    private String colErrExs;

    private String colErrDescn;

    private String ordErrExs;

    private String pkYnErrExs;

    private String pkOrdErrExs;

    private String nullYnErrExs;

    private String defltErrExs;

    private String cmmtErrExs;

    private String dataTypeErrExs;

    private String dataLenErrExs;

    private String dataPntErrExs;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;

    public String getSrcDbSchId() {
        return srcDbSchId;
    }

    public void setSrcDbSchId(String srcDbSchId) {
        this.srcDbSchId = srcDbSchId;
    }

    public String getSrcTblPnm() {
        return srcTblPnm;
    }

    public void setSrcTblPnm(String srcTblPnm) {
        this.srcTblPnm = srcTblPnm;
    }

    public String getSrcColPnm() {
        return srcColPnm;
    }

    public void setSrcColPnm(String srcColPnm) {
        this.srcColPnm = srcColPnm;
    }

    public String getSrcColLnm() {
        return srcColLnm;
    }

    public void setSrcColLnm(String srcColLnm) {
        this.srcColLnm = srcColLnm;
    }

    public String getTgtDbSchId() {
        return tgtDbSchId;
    }

    public void setTgtDbSchId(String tgtDbSchId) {
        this.tgtDbSchId = tgtDbSchId;
    }

    public String getTgtTblPnm() {
        return tgtTblPnm;
    }

    public void setTgtTblPnm(String tgtTblPnm) {
        this.tgtTblPnm = tgtTblPnm;
    }

    public String getTgtColPnm() {
        return tgtColPnm;
    }

    public void setTgtColPnm(String tgtColPnm) {
        this.tgtColPnm = tgtColPnm;
    }

    public String getTgtColLnm() {
        return tgtColLnm;
    }

    public void setTgtColLnm(String tgtColLnm) {
        this.tgtColLnm = tgtColLnm;
    }

    public String getSrcColExtncExs() {
        return srcColExtncExs;
    }

    public void setSrcColExtncExs(String srcColExtncExs) {
        this.srcColExtncExs = srcColExtncExs;
    }

    public String getTgtColExtncExs() {
        return tgtColExtncExs;
    }

    public void setTgtColExtncExs(String tgtColExtncExs) {
        this.tgtColExtncExs = tgtColExtncExs;
    }

    public String getColErrExs() {
        return colErrExs;
    }

    public void setColErrExs(String colErrExs) {
        this.colErrExs = colErrExs;
    }

    public String getColErrDescn() {
        return colErrDescn;
    }

    public void setColErrDescn(String colErrDescn) {
        this.colErrDescn = colErrDescn;
    }

    public String getOrdErrExs() {
        return ordErrExs;
    }

    public void setOrdErrExs(String ordErrExs) {
        this.ordErrExs = ordErrExs;
    }

    public String getPkYnErrExs() {
        return pkYnErrExs;
    }

    public void setPkYnErrExs(String pkYnErrExs) {
        this.pkYnErrExs = pkYnErrExs;
    }

    public String getPkOrdErrExs() {
        return pkOrdErrExs;
    }

    public void setPkOrdErrExs(String pkOrdErrExs) {
        this.pkOrdErrExs = pkOrdErrExs;
    }

    public String getNullYnErrExs() {
        return nullYnErrExs;
    }

    public void setNullYnErrExs(String nullYnErrExs) {
        this.nullYnErrExs = nullYnErrExs;
    }

    public String getDefltErrExs() {
        return defltErrExs;
    }

    public void setDefltErrExs(String defltErrExs) {
        this.defltErrExs = defltErrExs;
    }

    public String getCmmtErrExs() {
        return cmmtErrExs;
    }

    public void setCmmtErrExs(String cmmtErrExs) {
        this.cmmtErrExs = cmmtErrExs;
    }

    public String getDataTypeErrExs() {
        return dataTypeErrExs;
    }

    public void setDataTypeErrExs(String dataTypeErrExs) {
        this.dataTypeErrExs = dataTypeErrExs;
    }

    public String getDataLenErrExs() {
        return dataLenErrExs;
    }

    public void setDataLenErrExs(String dataLenErrExs) {
        this.dataLenErrExs = dataLenErrExs;
    }

    public String getDataPntErrExs() {
        return dataPntErrExs;
    }

    public void setDataPntErrExs(String dataPntErrExs) {
        this.dataPntErrExs = dataPntErrExs;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatDbcGapCol [srcDbSchId=").append(srcDbSchId)
				.append(", srcTblPnm=").append(srcTblPnm)
				.append(", srcColPnm=").append(srcColPnm)
				.append(", srcColLnm=").append(srcColLnm)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", tgtTblPnm=").append(tgtTblPnm)
				.append(", tgtColPnm=").append(tgtColPnm)
				.append(", tgtColLnm=").append(tgtColLnm)
				.append(", srcColExtncExs=").append(srcColExtncExs)
				.append(", tgtColExtncExs=").append(tgtColExtncExs)
				.append(", colErrExs=").append(colErrExs)
				.append(", colErrDescn=").append(colErrDescn)
				.append(", ordErrExs=").append(ordErrExs)
				.append(", pkYnErrExs=").append(pkYnErrExs)
				.append(", pkOrdErrExs=").append(pkOrdErrExs)
				.append(", nullYnErrExs=").append(nullYnErrExs)
				.append(", defltErrExs=").append(defltErrExs)
				.append(", cmmtErrExs=").append(cmmtErrExs)
				.append(", dataTypeErrExs=").append(dataTypeErrExs)
				.append(", dataLenErrExs=").append(dataLenErrExs)
				.append(", dataPntErrExs=").append(dataPntErrExs).append("]");
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
}