package kr.wise.meta.gap.service;

import kr.wise.commons.cmm.CommonVo;

public class WatDbcGapTbl extends CommonVo {
    private String srcDbSchId;

    private String srcTblPnm;

    private String srcTblLnm;

    private String srcDbmsType;

    private String srcConnTrgId;

    private String tgtDbSchId;

    private String tgtTblPnm;

    private String tgtTblLnm;

    private String tgtDbmsType;

    private String tgtConnTrgId;

    private String srcTblExtncExs;

    private String tgtTblExtncExs;

    private String tblErrExs;

    private String tblErrDescn;
    
    private String idxErrExs;
    
    private String idxErrDescn;

    private String colErrExs;

    private String colErrDescn;
    
    private String srcDbConnTrgId;
    
    private String tgtDbConnTrgId;

    
    private String gapStatus;
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;

    public String getGapStatus() {
		return gapStatus;
	}

	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}

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

    public String getSrcTblLnm() {
        return srcTblLnm;
    }

    public void setSrcTblLnm(String srcTblLnm) {
        this.srcTblLnm = srcTblLnm;
    }

    public String getSrcDbmsType() {
        return srcDbmsType;
    }

    public void setSrcDbmsType(String srcDbmsType) {
        this.srcDbmsType = srcDbmsType;
    }

    public String getSrcConnTrgId() {
        return srcConnTrgId;
    }

    public void setSrcConnTrgId(String srcConnTrgId) {
        this.srcConnTrgId = srcConnTrgId;
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

    public String getTgtTblLnm() {
        return tgtTblLnm;
    }

    public void setTgtTblLnm(String tgtTblLnm) {
        this.tgtTblLnm = tgtTblLnm;
    }

    public String getTgtDbmsType() {
        return tgtDbmsType;
    }

    public void setTgtDbmsType(String tgtDbmsType) {
        this.tgtDbmsType = tgtDbmsType;
    }

    public String getTgtConnTrgId() {
        return tgtConnTrgId;
    }

    public void setTgtConnTrgId(String tgtConnTrgId) {
        this.tgtConnTrgId = tgtConnTrgId;
    }

    public String getSrcTblExtncExs() {
        return srcTblExtncExs;
    }

    public void setSrcTblExtncExs(String srcTblExtncExs) {
        this.srcTblExtncExs = srcTblExtncExs;
    }

    public String getTgtTblExtncExs() {
        return tgtTblExtncExs;
    }

    public void setTgtTblExtncExs(String tgtTblExtncExs) {
        this.tgtTblExtncExs = tgtTblExtncExs;
    }

    public String getTblErrExs() {
        return tblErrExs;
    }

    public void setTblErrExs(String tblErrExs) {
        this.tblErrExs = tblErrExs;
    }

    public String getTblErrDescn() {
        return tblErrDescn;
    }

    public void setTblErrDescn(String tblErrDescn) {
        this.tblErrDescn = tblErrDescn;
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

	public String getSrcDbConnTrgId() {
		return srcDbConnTrgId;
	}

	public void setSrcDbConnTrgId(String srcDbConnTrgId) {
		this.srcDbConnTrgId = srcDbConnTrgId;
	}

	public String getTgtDbConnTrgId() {
		return tgtDbConnTrgId;
	}

	public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
		this.tgtDbConnTrgId = tgtDbConnTrgId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WatDbcGapTbl [srcDbSchId=").append(srcDbSchId)
				.append(", srcTblPnm=").append(srcTblPnm)
				.append(", srcTblLnm=").append(srcTblLnm)
				.append(", srcDbmsType=").append(srcDbmsType)
				.append(", srcConnTrgId=").append(srcConnTrgId)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", tgtTblPnm=").append(tgtTblPnm)
				.append(", tgtTblLnm=").append(tgtTblLnm)
				.append(", tgtDbmsType=").append(tgtDbmsType)
				.append(", tgtConnTrgId=").append(tgtConnTrgId)
				.append(", srcTblExtncExs=").append(srcTblExtncExs)
				.append(", tgtTblExtncExs=").append(tgtTblExtncExs)
				.append(", tblErrExs=").append(tblErrExs)
				.append(", tblErrDescn=").append(tblErrDescn)
				.append(", colErrExs=").append(colErrExs)
				.append(", colErrDescn=").append(colErrDescn)
				.append(", srcDbConnTrgId=").append(srcDbConnTrgId)
				.append(", tgtDbConnTrgId=").append(tgtDbConnTrgId)
				.append(", gapStatus=").append(gapStatus).append("]");
		return builder.toString()+super.toString();
	}

}