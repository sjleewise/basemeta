package kr.wise.meta.ddl.service;


public class WamDdlIdxCol extends WamDdlIdx {

	private String ddlIdxColId;

    private String ddlIdxColPnm;

    private String ddlIdxColLnm;

//    private String ddlIdxId;

    private String ddlColId;

    private Integer ddlIdxColOrd;

    private String sortTyp;
    
    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;

    private Integer pkOrd;

    private String nonulYn;

    private String defltVal;
    
    private String fullPath;

//    private String rqstNo;
//
//    private Integer rqstSno;

//    private Integer rqstDtlSno;

//    private String objDescn;
//
//    private Integer objVers;
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

//    private String ddlIdxLnm;
    
    
//    public String getDdlIdxLnm() {
//		return ddlIdxLnm;
//	}
//
//	public void setDdlIdxLnm(String ddlIdxLnm) {
//		this.ddlIdxLnm = ddlIdxLnm;
//	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
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

	public String getDdlIdxColId() {
        return ddlIdxColId;
    }

    public void setDdlIdxColId(String ddlIdxColId) {
        this.ddlIdxColId = ddlIdxColId;
    }

    public String getDdlIdxColPnm() {
        return ddlIdxColPnm;
    }

    public void setDdlIdxColPnm(String ddlIdxColPnm) {
        this.ddlIdxColPnm = ddlIdxColPnm;
    }

    public String getDdlIdxColLnm() {
        return ddlIdxColLnm;
    }

    public void setDdlIdxColLnm(String ddlIdxColLnm) {
        this.ddlIdxColLnm = ddlIdxColLnm;
    }

//    public String getDdlIdxId() {
//        return ddlIdxId;
//    }
//
//    public void setDdlIdxId(String ddlIdxId) {
//        this.ddlIdxId = ddlIdxId;
//    }

    public String getDdlColId() {
        return ddlColId;
    }

    public void setDdlColId(String ddlColId) {
        this.ddlColId = ddlColId;
    }

    public Integer getDdlIdxColOrd() {
        return ddlIdxColOrd;
    }

    public void setDdlIdxColOrd(Integer ddlIdxColOrd) {
        this.ddlIdxColOrd = ddlIdxColOrd;
    }

    public String getSortTyp() {
        return sortTyp;
    }

    public void setSortTyp(String sortTyp) {
        this.sortTyp = sortTyp;
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

//    public Integer getRqstDtlSno() {
//        return rqstDtlSno;
//    }
//
//    public void setRqstDtlSno(Integer rqstDtlSno) {
//        this.rqstDtlSno = rqstDtlSno;
//    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlIdxCol [ddlIdxColId=").append(ddlIdxColId)
				.append(", ddlIdxColPnm=").append(ddlIdxColPnm)
				.append(", ddlIdxColLnm=").append(ddlIdxColLnm)
				.append(", ddlColId=").append(ddlColId)
				.append(", ddlIdxColOrd=").append(ddlIdxColOrd)
				.append(", sortTyp=").append(sortTyp).append(", dataType=")
				.append(dataType).append(", dataLen=").append(dataLen)
				.append(", dataScal=").append(dataScal).append(", pkYn=")
				.append(pkYn).append(", pkOrd=").append(pkOrd)
				.append(", nonulYn=").append(nonulYn).append(", defltVal=")
				.append(defltVal).append(", fullPath=").append(fullPath)
				.append("]");
		return builder.toString()+super.toString();
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