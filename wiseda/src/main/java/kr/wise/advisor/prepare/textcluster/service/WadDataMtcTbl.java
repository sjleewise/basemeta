package kr.wise.advisor.prepare.textcluster.service;

import kr.wise.commons.cmm.CommonVo;

public class WadDataMtcTbl extends CommonVo {
    private String mtcId;

    private String srcDbConnTrgId;
    private String srcDbConnTrgNm;

    private String srcDbcSchId;
    private String srcDbcSchNm;

    private String srcDbcTblNm;
    private String srcDbcTblLnm;

    private String srcAdtCndSql;

    private String tgtDbConnTrgId;
    private String tgtDbConnTrgNm;

    private String tgtDbcSchId;
    private String tgtDbcSchNm;

    private String tgtDbcTblNm;
    private String tgtDbcTblLnm;

    private String tgtAdtCndSql;
    
    private long iPageNo;
    private long iPageSize;


    public String getMtcId() {
        return mtcId;
    }

    public void setMtcId(String mtcId) {
        this.mtcId = mtcId;
    }

    public String getSrcDbConnTrgId() {
        return srcDbConnTrgId;
    }

    public void setSrcDbConnTrgId(String srcDbConnTrgId) {
        this.srcDbConnTrgId = srcDbConnTrgId;
    }

    public String getSrcDbcSchId() {
        return srcDbcSchId;
    }

    public void setSrcDbcSchId(String srcDbcSchId) {
        this.srcDbcSchId = srcDbcSchId;
    }

    public String getSrcDbcTblNm() {
        return srcDbcTblNm;
    }

    public void setSrcDbcTblNm(String srcDbcTblNm) {
        this.srcDbcTblNm = srcDbcTblNm;
    }

    public String getSrcAdtCndSql() {
        return srcAdtCndSql;
    }

    public void setSrcAdtCndSql(String srcAdtCndSql) {
        this.srcAdtCndSql = srcAdtCndSql;
    }

    public String getTgtDbConnTrgId() {
        return tgtDbConnTrgId;
    }

    public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
        this.tgtDbConnTrgId = tgtDbConnTrgId;
    }

    public String getTgtDbcSchId() {
        return tgtDbcSchId;
    }

    public void setTgtDbcSchId(String tgtDbcSchId) {
        this.tgtDbcSchId = tgtDbcSchId;
    }

    public String getTgtDbcTblNm() {
        return tgtDbcTblNm;
    }

    public void setTgtDbcTblNm(String tgtDbcTblNm) {
        this.tgtDbcTblNm = tgtDbcTblNm;
    }

    public String getTgtAdtCndSql() {
        return tgtAdtCndSql;
    }

    public void setTgtAdtCndSql(String tgtAdtCndSql) {
        this.tgtAdtCndSql = tgtAdtCndSql;
    }

	/**
	 * @return the srcDbConnTrgNm
	 */
	public String getSrcDbConnTrgNm() {
		return srcDbConnTrgNm;
	}

	/**
	 * @param srcDbConnTrgNm the srcDbConnTrgNm to set
	 */
	public void setSrcDbConnTrgNm(String srcDbConnTrgNm) {
		this.srcDbConnTrgNm = srcDbConnTrgNm;
	}

	/**
	 * @return the srcDbcSchNm
	 */
	public String getSrcDbcSchNm() {
		return srcDbcSchNm;
	}

	/**
	 * @param srcDbcSchNm the srcDbcSchNm to set
	 */
	public void setSrcDbcSchNm(String srcDbcSchNm) {
		this.srcDbcSchNm = srcDbcSchNm;
	}

	/**
	 * @return the srcDbcTblLnm
	 */
	public String getSrcDbcTblLnm() {
		return srcDbcTblLnm;
	}

	/**
	 * @param srcDbcTblLnm the srcDbcTblLnm to set
	 */
	public void setSrcDbcTblLnm(String srcDbcTblLnm) {
		this.srcDbcTblLnm = srcDbcTblLnm;
	}

	/**
	 * @return the tgtDbConnTrgNm
	 */
	public String getTgtDbConnTrgNm() {
		return tgtDbConnTrgNm;
	}

	/**
	 * @param tgtDbConnTrgNm the tgtDbConnTrgNm to set
	 */
	public void setTgtDbConnTrgNm(String tgtDbConnTrgNm) {
		this.tgtDbConnTrgNm = tgtDbConnTrgNm;
	}

	/**
	 * @return the tgtDbcSchNm
	 */
	public String getTgtDbcSchNm() {
		return tgtDbcSchNm;
	}

	/**
	 * @param tgtDbcSchNm the tgtDbcSchNm to set
	 */
	public void setTgtDbcSchNm(String tgtDbcSchNm) {
		this.tgtDbcSchNm = tgtDbcSchNm;
	}

	/**
	 * @return the tgtDbcTblLnm
	 */
	public String getTgtDbcTblLnm() {
		return tgtDbcTblLnm;
	}

	/**
	 * @param tgtDbcTblLnm the tgtDbcTblLnm to set
	 */
	public void setTgtDbcTblLnm(String tgtDbcTblLnm) {
		this.tgtDbcTblLnm = tgtDbcTblLnm;
	}

	public long getiPageNo() {
		return iPageNo;
	}

	public void setiPageNo(long iPageNo) {
		this.iPageNo = iPageNo;
	}

	public long getiPageSize() {
		return iPageSize;
	}

	public void setiPageSize(long iPageSize) {
		this.iPageSize = iPageSize;
	}

}