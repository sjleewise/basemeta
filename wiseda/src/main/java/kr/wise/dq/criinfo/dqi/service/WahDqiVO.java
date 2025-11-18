package kr.wise.dq.criinfo.dqi.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WahDqiVO extends CommonVo {
    private String dqiId;

    private Date expDtm;

    private Date strDtm;

    private String dqiLnm;

    private String dqiPnm;

    private Short dqiLvl;

    private String uppDqiId;
    
    private String uppDqiLnm;

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

    public String getDqiId() {
        return dqiId;
    }

    public void setDqiId(String dqiId) {
        this.dqiId = dqiId;
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

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WahDqiVO [dqiId=").append(dqiId)
				.append(", expDtm=").append(expDtm).append(", strDtm=")
				.append(strDtm).append(", dqiLnm=").append(dqiLnm)
				.append(", dqiPnm=").append(dqiPnm)
				.append(", dqiLvl=").append(dqiLvl)
				.append(", uppDqiId=").append(uppDqiId)
				.append(", uppDqiLnm=").append(uppDqiLnm)
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