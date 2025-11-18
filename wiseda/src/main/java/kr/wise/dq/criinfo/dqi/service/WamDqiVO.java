package kr.wise.dq.criinfo.dqi.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDqiVO extends CommonVo {
    private String dqiId;

    private String dqiLnm;

    private String dqiPnm;

    private Short dqiLvl;

    private String uppDqiId;
    
    private String uppDqiLnm;

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
    
    private String vrfcTyp;
	
	private float goal;
	
	private float weight;
    

    
    public String getBizruleCnt() {
		return bizruleCnt;
	}

	public void setBizruleCnt(String bizruleCnt) {
		this.bizruleCnt = bizruleCnt;
	}

	public String getDqiId() {
        return dqiId;
    }

    public void setDqiId(String dqiId) {
        this.dqiId = dqiId;
    }

    public String getDqiLnm() {
        return dqiLnm;
    }

    public void setDqiLnm(String dqiLnm) {
        this.dqiLnm = dqiLnm;
    }

    public String getDqiPnm() {
        return dqiPnm;
    }

    public void setDqiPnm(String dqiPnm) {
        this.dqiPnm = dqiPnm;
    }

    public Short getDqiLvl() {
        return dqiLvl;
    }

    public void setDqiLvl(Short dqiLvl) {
        this.dqiLvl = dqiLvl;
    }

    public String getUppDqiId() {
        return uppDqiId;
    }

    public void setUppDqiId(String uppDqiId) {
        this.uppDqiId = uppDqiId;
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

	public String getUppDqiLnm() {
		return uppDqiLnm;
	}

	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
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

	public String getRqstDtmDisp() {
		return rqstDtmDisp;
	}

	public void setRqstDtmDisp(String rqstDtmDisp) {
		this.rqstDtmDisp = rqstDtmDisp;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	

	public String getVrfcTyp() {
		return vrfcTyp;
	}

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}

	@Override
	public String toString() {
		return "WamDqiVO [dqiId=" + dqiId + ", dqiLnm=" + dqiLnm + ", dqiPnm=" + dqiPnm + ", dqiLvl=" + dqiLvl
				+ ", uppDqiId=" + uppDqiId + ", uppDqiLnm=" + uppDqiLnm + ", rqstDtmDisp=" + rqstDtmDisp
				+ ", bizruleCnt=" + bizruleCnt + ", vrfcTyp=" + vrfcTyp + ", goal=" + goal + ", weight=" + weight + "]";
	}

	
	
	
}