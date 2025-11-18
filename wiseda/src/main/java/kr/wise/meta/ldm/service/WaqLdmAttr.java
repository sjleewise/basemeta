package kr.wise.meta.ldm.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqLdmAttr extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private Integer rqstDtlSno;

    private String ldmAttrId;

    private String ldmAttrPnm;

    private String ldmAttrLnm;
   
    private String ldmEntyId;

    private String ldmEntyLnm;

    private String mdlLnm;

    private String uppSubjLnm;

    private String subjId;

    private String subjLnm;

    private String sditmId;

    private Integer attrOrd;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;

    private Integer pkOrd;

    private String nonulYn;

    private String defltVal;

    private String encYn;


    
    
    public String getLdmAttrId() {
		return ldmAttrId;
	}

	public void setLdmAttrId(String ldmAttrId) {
		this.ldmAttrId = ldmAttrId;
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

    
    public String getLdmAttrPnm() {
		return ldmAttrPnm;
	}

	public void setLdmAttrPnm(String ldmAttrPnm) {
		this.ldmAttrPnm = ldmAttrPnm;
	}

	public String getLdmAttrLnm() {
		return ldmAttrLnm;
	}

	public void setLdmAttrLnm(String ldmAttrLnm) {
		this.ldmAttrLnm = ldmAttrLnm;
	}

	public String getLdmEntyId() {
		return ldmEntyId;
	}

	public void setLdmEntyId(String ldmEntyId) {
		this.ldmEntyId = ldmEntyId;
	}
		
	public String getLdmEntyLnm() {
		return ldmEntyLnm;
	}

	public void setLdmEntyLnm(String ldmEntyLnm) {
		this.ldmEntyLnm = ldmEntyLnm;
	}

	public String getMdlLnm() {
        return mdlLnm;
    }

    public void setMdlLnm(String mdlLnm) {
        this.mdlLnm = mdlLnm;
    }

    public String getUppSubjLnm() {
        return uppSubjLnm;
    }

    public void setUppSubjLnm(String uppSubjLnm) {
        this.uppSubjLnm = uppSubjLnm;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public String getSubjLnm() {
        return subjLnm;
    }

    public void setSubjLnm(String subjLnm) {
        this.subjLnm = subjLnm;
    }

    public String getSditmId() {
        return sditmId;
    }

    public void setSditmId(String sditmId) {
        this.sditmId = sditmId;
    }

    

    public Integer getAttrOrd() {
		return attrOrd;
	}

	public void setAttrOrd(Integer attrOrd) {
		this.attrOrd = attrOrd;
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
    
    /**
	 * @return the encYn
	 */
	public String getEncYn() {
		return encYn;
	}

	/**
	 * @param encYn the encYn to set
	 */
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	
	

	/** insomnia */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPdmCol [rqstNo=").append(rqstNo)
				.append(", rqstSno=").append(rqstSno).append(", rqstDtlSno=") 							
				.append(", uppSubjLnm=").append(uppSubjLnm).append(", subjId=")
				.append(subjId).append(", subjLnm=").append(subjLnm)
				.append(", sditmId=").append(sditmId)
				.append(", dataType=").append(dataType)
				.append(", dataLen=").append(dataLen).append(", dataScal=")
				.append(dataScal).append(", pkYn=").append(pkYn)
				.append(", pkOrd=").append(pkOrd).append(", nonulYn=")
				.append(nonulYn).append(", defltVal=").append(defltVal)
				.append("]");
		return builder.toString();
	}

	
}