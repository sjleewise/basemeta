package kr.wise.meta.gap.service;

import java.math.BigDecimal;

import kr.wise.commons.cmm.CommonVo;

public class WatDbcGapIdx extends CommonVo {
    private String srcDbSchId;

    private String srcTblPnm;

    private String srcIdxPnm;

    private String srcIdxLnm;

    private String srcIdxSpacNm;

    private String srcPkYn;

    private String srcUqYn;

    private BigDecimal srcColEacnt;

    private String srcDdlIdxId;

    private String tgtDbSchId;

    private String tgtTblPnm;

    private String tgtIdxPnm;

    private String tgtIdxLnm;

    private String tgtIdxSpacNm;

    private String tgtPkYn;

    private String tgtUqYn;

    private BigDecimal tgtColEacnt;

    private String tgtDdlIdxId;

    private String srcIdxExtncExs;

    private String tgtIdxExtncExs;

    private String idxSpacNmErrExs;

    private String pkYnErrExs;

    private String uqYnErrExs;

    private String colEacntErrExs;

    private String idxErrExs;

    private String idxErrDescn;

    private String idxColErrExs;

    private String idxColErrDescn;
    
    private String gapStatus;

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

    public String getSrcIdxPnm() {
        return srcIdxPnm;
    }

    public void setSrcIdxPnm(String srcIdxPnm) {
        this.srcIdxPnm = srcIdxPnm;
    }

    public String getSrcIdxLnm() {
        return srcIdxLnm;
    }

    public void setSrcIdxLnm(String srcIdxLnm) {
        this.srcIdxLnm = srcIdxLnm;
    }

    public String getSrcIdxSpacNm() {
        return srcIdxSpacNm;
    }

    public void setSrcIdxSpacNm(String srcIdxSpacNm) {
        this.srcIdxSpacNm = srcIdxSpacNm;
    }

    public String getSrcPkYn() {
        return srcPkYn;
    }

    public void setSrcPkYn(String srcPkYn) {
        this.srcPkYn = srcPkYn;
    }

    public String getSrcUqYn() {
        return srcUqYn;
    }

    public void setSrcUqYn(String srcUqYn) {
        this.srcUqYn = srcUqYn;
    }

    public BigDecimal getSrcColEacnt() {
        return srcColEacnt;
    }

    public void setSrcColEacnt(BigDecimal srcColEacnt) {
        this.srcColEacnt = srcColEacnt;
    }

    public String getSrcDdlIdxId() {
        return srcDdlIdxId;
    }

    public void setSrcDdlIdxId(String srcDdlIdxId) {
        this.srcDdlIdxId = srcDdlIdxId;
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

    public String getTgtIdxPnm() {
        return tgtIdxPnm;
    }

    public void setTgtIdxPnm(String tgtIdxPnm) {
        this.tgtIdxPnm = tgtIdxPnm;
    }

    public String getTgtIdxLnm() {
        return tgtIdxLnm;
    }

    public void setTgtIdxLnm(String tgtIdxLnm) {
        this.tgtIdxLnm = tgtIdxLnm;
    }

    public String getTgtIdxSpacNm() {
        return tgtIdxSpacNm;
    }

    public void setTgtIdxSpacNm(String tgtIdxSpacNm) {
        this.tgtIdxSpacNm = tgtIdxSpacNm;
    }

    public String getTgtPkYn() {
        return tgtPkYn;
    }

    public void setTgtPkYn(String tgtPkYn) {
        this.tgtPkYn = tgtPkYn;
    }

    public String getTgtUqYn() {
        return tgtUqYn;
    }

    public void setTgtUqYn(String tgtUqYn) {
        this.tgtUqYn = tgtUqYn;
    }

    public BigDecimal getTgtColEacnt() {
        return tgtColEacnt;
    }

    public void setTgtColEacnt(BigDecimal tgtColEacnt) {
        this.tgtColEacnt = tgtColEacnt;
    }

    public String getTgtDdlIdxId() {
        return tgtDdlIdxId;
    }

    public void setTgtDdlIdxId(String tgtDdlIdxId) {
        this.tgtDdlIdxId = tgtDdlIdxId;
    }

    public String getSrcIdxExtncExs() {
        return srcIdxExtncExs;
    }

    public void setSrcIdxExtncExs(String srcIdxExtncExs) {
        this.srcIdxExtncExs = srcIdxExtncExs;
    }

    public String getTgtIdxExtncExs() {
        return tgtIdxExtncExs;
    }

    public void setTgtIdxExtncExs(String tgtIdxExtncExs) {
        this.tgtIdxExtncExs = tgtIdxExtncExs;
    }

    public String getIdxSpacNmErrExs() {
        return idxSpacNmErrExs;
    }

    public void setIdxSpacNmErrExs(String idxSpacNmErrExs) {
        this.idxSpacNmErrExs = idxSpacNmErrExs;
    }

    public String getPkYnErrExs() {
        return pkYnErrExs;
    }

    public void setPkYnErrExs(String pkYnErrExs) {
        this.pkYnErrExs = pkYnErrExs;
    }

    public String getUqYnErrExs() {
        return uqYnErrExs;
    }

    public void setUqYnErrExs(String uqYnErrExs) {
        this.uqYnErrExs = uqYnErrExs;
    }

    public String getColEacntErrExs() {
        return colEacntErrExs;
    }

    public void setColEacntErrExs(String colEacntErrExs) {
        this.colEacntErrExs = colEacntErrExs;
    }

    public String getIdxErrExs() {
        return idxErrExs;
    }

    public void setIdxErrExs(String idxErrExs) {
        this.idxErrExs = idxErrExs;
    }

    public String getIdxErrDescn() {
        return idxErrDescn;
    }

    public void setIdxErrDescn(String idxErrDescn) {
        this.idxErrDescn = idxErrDescn;
    }

    public String getIdxColErrExs() {
        return idxColErrExs;
    }

    public void setIdxColErrExs(String idxColErrExs) {
        this.idxColErrExs = idxColErrExs;
    }

    public String getIdxColErrDescn() {
        return idxColErrDescn;
    }

    public void setIdxColErrDescn(String idxColErrDescn) {
        this.idxColErrDescn = idxColErrDescn;
    }

	public String getGapStatus() {
		return gapStatus;
	}

	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatDbcGapIdx [srcDbSchId=").append(srcDbSchId)
				.append(", srcTblPnm=").append(srcTblPnm)
				.append(", srcIdxPnm=").append(srcIdxPnm)
				.append(", srcIdxLnm=").append(srcIdxLnm)
				.append(", srcIdxSpacNm=").append(srcIdxSpacNm)
				.append(", srcPkYn=").append(srcPkYn).append(", srcUqYn=")
				.append(srcUqYn).append(", srcColEacnt=").append(srcColEacnt)
				.append(", srcDdlIdxId=").append(srcDdlIdxId)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", tgtTblPnm=").append(tgtTblPnm)
				.append(", tgtIdxPnm=").append(tgtIdxPnm)
				.append(", tgtIdxLnm=").append(tgtIdxLnm)
				.append(", tgtIdxSpacNm=").append(tgtIdxSpacNm)
				.append(", tgtPkYn=").append(tgtPkYn).append(", tgtUqYn=")
				.append(tgtUqYn).append(", tgtColEacnt=").append(tgtColEacnt)
				.append(", tgtDdlIdxId=").append(tgtDdlIdxId)
				.append(", srcIdxExtncExs=").append(srcIdxExtncExs)
				.append(", tgtIdxExtncExs=").append(tgtIdxExtncExs)
				.append(", idxSpacNmErrExs=").append(idxSpacNmErrExs)
				.append(", pkYnErrExs=").append(pkYnErrExs)
				.append(", uqYnErrExs=").append(uqYnErrExs)
				.append(", colEacntErrExs=").append(colEacntErrExs)
				.append(", idxErrExs=").append(idxErrExs)
				.append(", idxErrDescn=").append(idxErrDescn)
				.append(", idxColErrExs=").append(idxColErrExs)
				.append(", idxColErrDescn=").append(idxColErrDescn).append("]");
		return builder.toString();
	}

}