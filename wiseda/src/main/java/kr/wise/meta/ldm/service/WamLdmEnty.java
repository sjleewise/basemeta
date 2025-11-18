package kr.wise.meta.ldm.service;

import kr.wise.commons.cmm.CommonVo;

public class WamLdmEnty extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private String ldmEntyId;

    private String ldmEntyLnm;

    private String ldmAttrLnm;
    
    private String subjId;
    
    private String subjLnm;

    private String stdAplYn;
   
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

   
	public String getLdmEntyLnm() {
		return ldmEntyLnm;
	}

	public void setLdmEntyLnm(String ldmEntyLnm) {
		this.ldmEntyLnm = ldmEntyLnm;
	}

	
    public String getStdAplYn() {
        return stdAplYn;
    }

    public void setStdAplYn(String stdAplYn) {
        this.stdAplYn = stdAplYn;
    }

    

    public String getSubjId() {
		return subjId;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
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
	

	public String getLdmAttrLnm() {
		return ldmAttrLnm;
	}

	public void setLdmAttrLnm(String ldmAttrLnm) {
		this.ldmAttrLnm = ldmAttrLnm;
	}

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPdmTbl [rqstNo=").append(rqstNo)
				.append(", rqstSno=").append(rqstSno).append(", ldmEntyId=")
				.append(ldmEntyId)
				.append(", pdmTblLnm=").append(ldmEntyLnm)				
				.append(", mdlLnm=")
				.append(", stdAplYn=").append(stdAplYn)				
				.append(", crgUserId=").append(crgUserId)
				.append(", crgUserNm=").append(crgUserNm).append(", ctdFcy=")
				;
		return builder.toString();
	}

}