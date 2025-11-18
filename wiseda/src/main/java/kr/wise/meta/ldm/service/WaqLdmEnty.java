package kr.wise.meta.ldm.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqLdmEnty extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private String ldmEntyId;

    private String ldmEntyPnm;

    private String ldmEntyLnm;

   

    private String mdlLnm;

    private String uppSubjLnm;

    private String subjId;

    private String subjLnm;

    private String stdAplYn;

    private String partTblYn;

    private String dwTrgTblYn;

    private String crgUserId;

    private String crgUserNm;

    private String fullPath;
   
        
    public String getLdmEntyId() {
		return ldmEntyId;
	}

	public void setLdmEntyId(String ldmEntyId) {
		this.ldmEntyId = ldmEntyId;
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

   
    
    

    public String getLdmEntyPnm() {
		return ldmEntyPnm;
	}

	public void setLdmEntyPnm(String ldmEntyPnm) {
		this.ldmEntyPnm = ldmEntyPnm;
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

    public String getStdAplYn() {
        return stdAplYn;
    }

    public void setStdAplYn(String stdAplYn) {
        this.stdAplYn = stdAplYn;
    }

    public String getPartTblYn() {
        return partTblYn;
    }

    public void setPartTblYn(String partTblYn) {
        this.partTblYn = partTblYn;
    }

    public String getDwTrgTblYn() {
        return dwTrgTblYn;
    }

    public void setDwTrgTblYn(String dwTrgTblYn) {
        this.dwTrgTblYn = dwTrgTblYn;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

    public String getCrgUserNm() {
        return crgUserNm;
    }

    public void setCrgUserNm(String crgUserNm) {
        this.crgUserNm = crgUserNm;
    }


	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPdmTbl [rqstNo=").append(rqstNo)
				.append(", rqstSno=").append(rqstSno).append(", ldmEntyId=")
				.append(ldmEntyId).append(", ldmEntyPnm=").append(ldmEntyPnm)
				.append(", pdmTblLnm=").append(ldmEntyLnm)				
				.append(", mdlLnm=").append(mdlLnm).append(", uppSubjLnm=")
				.append(uppSubjLnm).append(", subjId=").append(subjId)
				.append(", subjLnm=").append(subjLnm).append(", stdAplYn=")
				.append(stdAplYn).append(", partTblYn=").append(partTblYn)
				.append(", dwTrgTblYn=").append(dwTrgTblYn)
				.append(", crgUserId=").append(crgUserId)
				.append(", crgUserNm=").append(crgUserNm).append(", ctdFcy=")
				;
		return builder.toString();
	}

}