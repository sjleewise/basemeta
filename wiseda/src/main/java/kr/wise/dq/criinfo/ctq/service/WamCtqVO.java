package kr.wise.dq.criinfo.ctq.service;

import kr.wise.commons.cmm.CommonVo;

public class WamCtqVO extends CommonVo {
    private String ctqId;

    private String ctqLnm;

    private String ctqPnm;

    private Short ctqLvl;

    private String uppCtqId;
    
    private String uppCtqLnm;

//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
    
    private String rqstDtmDisp;

//    private String rqstUserId;
//    
//    private String rqstUserNm;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;
    
    private String bizruleCnt;
    
    private float goal;
    
    private float weight;
    
    

    
    public String getBizruleCnt() {
		return bizruleCnt;
	}

	public void setBizruleCnt(String bizruleCnt) {
		this.bizruleCnt = bizruleCnt;
	}

	public String getCtqId() {
        return ctqId;
    }

    public void setCtqId(String ctqId) {
        this.ctqId = ctqId;
    }

    public String getCtqLnm() {
        return ctqLnm;
    }

    public void setCtqLnm(String ctqLnm) {
        this.ctqLnm = ctqLnm;
    }

    public String getCtqPnm() {
        return ctqPnm;
    }

    public void setCtqPnm(String ctqPnm) {
        this.ctqPnm = ctqPnm;
    }

    public Short getCtqLvl() {
        return ctqLvl;
    }

    public void setCtqLvl(Short ctqLvl) {
        this.ctqLvl = ctqLvl;
    }

    public String getUppCtqId() {
        return uppCtqId;
    }

    public void setUppCtqId(String uppCtqId) {
        this.uppCtqId = uppCtqId;
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
//
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
//
//	public String getRqstUserNm() {
//		return rqstUserNm;
//	}
//
//	public void setRqstUserNm(String rqstUserNm) {
//		this.rqstUserNm = rqstUserNm;
//	}

	public String getUppCtqLnm() {
		return uppCtqLnm;
	}

	public void setUppCtqLnm(String uppCtqLnm) {
		this.uppCtqLnm = uppCtqLnm;
	}
	
	public String getRqstDtmDisp() {
		return rqstDtmDisp;
	}

	public void setRqstDtmDisp(String rqstDtmDisp) {
		this.rqstDtmDisp = rqstDtmDisp;
	}

	public float getGoal() {
		return goal;
	}

	public void setGoal(float goal) {
		this.goal = goal;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamCtqVO [ctqId=").append(ctqId).append(", ctqLnm=")
				.append(ctqLnm).append(", ctqPnm=").append(ctqPnm)
				.append(", ctqLvl=").append(ctqLvl).append(", uppCtqId=")
				.append(uppCtqId).append(", uppCtqLnm=").append(uppCtqLnm)
				.append(", rqstDtmDisp=").append(rqstDtmDisp)
				.append(", goal=").append(goal)
				.append(", weight=").append(weight).append("]");
		return builder.toString() + super.toString();
	}

	
	
}