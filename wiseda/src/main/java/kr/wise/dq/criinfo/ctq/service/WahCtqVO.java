package kr.wise.dq.criinfo.ctq.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WahCtqVO extends CommonVo {
    private String ctqId;

    private Date expDtm;

    private Date strDtm;

    private String ctqLnm;

    private String ctqPnm;

    private Short ctqLvl;

    private String uppCtqId;
    
    private String uppCtqLnm;

    private String objDescn;

    private Integer objVers;

    private String regTypCd;

    private Date frsRqstDtm;

    private String frsRqstUserId;

    private Date rqstDtm;

    private String rqstUserId;

    private Date aprvDtm;

    private String aprvUserId;
    
    private float goal;
    
    private float weight;

    public String getCtqId() {
        return ctqId;
    }

    public void setCtqId(String ctqId) {
        this.ctqId = ctqId;
    }

    public Date getExpDtm() {
        return expDtm;
    }

    public void setExpDtm(Date expDtm) {
        this.expDtm = expDtm;
    }

    public Date getStrDtm() {
        return strDtm;
    }

    public void setStrDtm(Date strDtm) {
        this.strDtm = strDtm;
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

    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getFrsRqstDtm() {
        return frsRqstDtm;
    }

    public void setFrsRqstDtm(Date frsRqstDtm) {
        this.frsRqstDtm = frsRqstDtm;
    }

    public String getFrsRqstUserId() {
        return frsRqstUserId;
    }

    public void setFrsRqstUserId(String frsRqstUserId) {
        this.frsRqstUserId = frsRqstUserId;
    }

    public Date getRqstDtm() {
        return rqstDtm;
    }

    public void setRqstDtm(Date rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public Date getAprvDtm() {
        return aprvDtm;
    }

    public void setAprvDtm(Date aprvDtm) {
        this.aprvDtm = aprvDtm;
    }

    public String getAprvUserId() {
        return aprvUserId;
    }

    public void setAprvUserId(String aprvUserId) {
        this.aprvUserId = aprvUserId;
    }

	public String getUppCtqLnm() {
		return uppCtqLnm;
	}

	public void setUppCtqLnm(String uppCtqLnm) {
		this.uppCtqLnm = uppCtqLnm;
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
		builder.append("WahCtqVO [ctqId=").append(ctqId)
				.append(", expDtm=").append(expDtm).append(", strDtm=")
				.append(strDtm).append(", ctqLnm=").append(ctqLnm)
				.append(", ctqPnm=").append(ctqPnm)
				.append(", ctqLvl=").append(ctqLvl)
				.append(", uppCtqId=").append(uppCtqId)
				.append(", uppCtqLnm=").append(uppCtqLnm)
				.append(", objDescn=").append(objDescn).append(", objVers=")
				.append(objVers).append(", regTypCd=").append(regTypCd)
				.append(", frsRqstDtm=").append(frsRqstDtm)
				.append(", frsRqstUserId=").append(frsRqstUserId)
				.append(", rqstDtm=").append(rqstDtm).append(", rqstUserId=")
				.append(rqstUserId).append(", aprvDtm=").append(aprvDtm)
				.append(", aprvUserId=").append(aprvUserId).append("]");
		return builder.toString();
	}
    
    
}