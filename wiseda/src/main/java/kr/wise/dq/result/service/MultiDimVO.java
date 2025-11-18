package kr.wise.dq.result.service;

import java.math.BigDecimal;

import kr.wise.dq.bizrule.service.WamBrResult;


public class MultiDimVO extends WamBrResult{
    private String brId;

    private String brNm;

    private String dbConnTrgId;

    private String dbSchId;

    private String dbcTblNm;

    private String dbcColNm;

    private String bizAreaId;

    private String brCrgpUserId;

    private float agrNv;

    private float glNv;

    private String useYn;

    private String tgtDbConnTrgId;
    
    private String tgtDbConnTrgPnm;

    private String tgtDbSchId;

    private String tgtDbSchLnm;

    private String tgtDbcTblNm;

    private String tgtDbcColNm;

    private String tgtKeyDbcColNm;

    private String tgtKeyDbcColVal;

    private String cntSql;

    private String erCntSql;

    private String anaSql;

    private String tgtCntSql;

    private String tgtErCntSql;

    private String tgtAnaSql;

    private String dbConnTrgLnm;

    private String dqiId;

    private String dqiLnm;
    private String uppDqiLnm;

    private String ctqId;

    private String ctqLnm;

    private String baseDttm;

    private String anaDgrDisp;

    private String erRate;
    
    private BigDecimal anaCnt;

    private String dpmo;

    private String sigma;

    private String erYn;

    private String anaJobId;

    private String anaJobNm;

    private String anaKndCd;

    private String brCrgpUserNm;
    
    private String tgtVrfJoinCd;

    private String dbConnTrgPnm;

    //업무규칙조회 팝업 스키마명
    private String dbSchLnm;
    private String shdJobId;
    private String etcJobNm;
    
    private String dbSchPnm;
    
    private float weight;
    
    private float goal;

    private String archRate;
    
    private String anaCnt2;
    private String erCnt2;
    private String erRate2;
    private String anaCnt3;
    private String erCnt3;
    private String erRate3;
    
    
    
    
    public BigDecimal getAnaCnt() {
		return anaCnt;
	}

	public void setAnaCnt(BigDecimal anaCnt) {
		this.anaCnt = anaCnt;
	}

	public String getArchRate() {
		return archRate;
	}

	public void setArchRate(String archRate) {
		this.archRate = archRate;
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

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public String getTgtDbSchLnm() {
		return tgtDbSchLnm;
	}

	public void setTgtDbSchLnm(String tgtDbSchLnm) {
		this.tgtDbSchLnm = tgtDbSchLnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getBrCrgpUserNm() {
		return brCrgpUserNm;
	}

	public void setBrCrgpUserNm(String brCrgpUserNm) {
		this.brCrgpUserNm = brCrgpUserNm;
	}

	/**
	 * @return the anaJobNm
	 */
	public String getAnaJobNm() {
		return anaJobNm;
	}

	/**
	 * @param anaJobNm the anaJobNm to set
	 */
	public void setAnaJobNm(String anaJobNm) {
		this.anaJobNm = anaJobNm;
	}

	/**
	 * @return the anaKndCd
	 */
	public String getAnaKndCd() {
		return anaKndCd;
	}

	/**
	 * @param anaKndCd the anaKndCd to set
	 */
	public void setAnaKndCd(String anaKndCd) {
		this.anaKndCd = anaKndCd;
	}

	/**
	 * @return the anaJobId
	 */
	public String getAnaJobId() {
		return anaJobId;
	}

	/**
	 * @param anaJobId the anaJobId to set
	 */
	public void setAnaJobId(String anaJobId) {
		this.anaJobId = anaJobId;
	}

	@Override
	public String getBrId() {
        return brId;
    }

    @Override
	public void setBrId(String brId) {
        this.brId = brId;
    }

    public String getBrNm() {
        return brNm;
    }

    public void setBrNm(String brNm) {
        this.brNm = brNm;
    }

    public String getDbConnTrgId() {
        return dbConnTrgId;
    }

    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getDbcColNm() {
        return dbcColNm;
    }

    public void setDbcColNm(String dbcColNm) {
        this.dbcColNm = dbcColNm;
    }

    @Override
	public String getBizAreaId() {
        return bizAreaId;
    }

    @Override
	public void setBizAreaId(String bizAreaId) {
        this.bizAreaId = bizAreaId;
    }

    public String getBrCrgpUserId() {
        return brCrgpUserId;
    }

    public void setBrCrgpUserId(String brCrgpUserId) {
        this.brCrgpUserId = brCrgpUserId;
    }

    public float getAgrNv() {
        return agrNv;
    }

    public void setAgrNv(float agrNv) {
        this.agrNv = agrNv;
    }

    public float getGlNv() {
        return glNv;
    }

    public void setGlNv(float glNv) {
        this.glNv = glNv;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getTgtDbConnTrgId() {
        return tgtDbConnTrgId;
    }

    public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
        this.tgtDbConnTrgId = tgtDbConnTrgId;
    }

    public String getTgtDbSchId() {
        return tgtDbSchId;
    }

    public void setTgtDbSchId(String tgtDbSchId) {
        this.tgtDbSchId = tgtDbSchId;
    }

    public String getTgtDbcTblNm() {
        return tgtDbcTblNm;
    }

    public void setTgtDbcTblNm(String tgtDbcTblNm) {
        this.tgtDbcTblNm = tgtDbcTblNm;
    }

    public String getTgtDbcColNm() {
        return tgtDbcColNm;
    }

    public void setTgtDbcColNm(String tgtDbcColNm) {
        this.tgtDbcColNm = tgtDbcColNm;
    }

    public String getTgtKeyDbcColNm() {
        return tgtKeyDbcColNm;
    }

    public void setTgtKeyDbcColNm(String tgtKeyDbcColNm) {
        this.tgtKeyDbcColNm = tgtKeyDbcColNm;
    }

    public String getTgtKeyDbcColVal() {
        return tgtKeyDbcColVal;
    }

    public void setTgtKeyDbcColVal(String tgtKeyDbcColVal) {
        this.tgtKeyDbcColVal = tgtKeyDbcColVal;
    }

    public String getCntSql() {
        return cntSql;
    }

    public void setCntSql(String cntSql) {
        this.cntSql = cntSql;
    }

    public String getErCntSql() {
        return erCntSql;
    }

    public void setErCntSql(String erCntSql) {
        this.erCntSql = erCntSql;
    }

    public String getAnaSql() {
        return anaSql;
    }

    public void setAnaSql(String anaSql) {
        this.anaSql = anaSql;
    }

    public String getTgtCntSql() {
        return tgtCntSql;
    }

    public void setTgtCntSql(String tgtCntSql) {
        this.tgtCntSql = tgtCntSql;
    }

    public String getTgtErCntSql() {
        return tgtErCntSql;
    }

    public void setTgtErCntSql(String tgtErCntSql) {
        this.tgtErCntSql = tgtErCntSql;
    }

    public String getTgtAnaSql() {
        return tgtAnaSql;
    }

    public void setTgtAnaSql(String tgtAnaSql) {
        this.tgtAnaSql = tgtAnaSql;
    }

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
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

	public String getBaseDttm() {
		return baseDttm;
	}

	public void setBaseDttm(String baseDttm) {
		this.baseDttm = baseDttm;
	}

	public String getAnaDgrDisp() {
		return anaDgrDisp;
	}

	public void setAnaDgrDisp(String anaDgrDisp) {
		this.anaDgrDisp = anaDgrDisp;
	}

	public String getErRate() {
		return erRate;
	}

	public void setErRate(String erRate) {
		this.erRate = erRate;
	}

	public String getDpmo() {
		return dpmo;
	}

	public void setDpmo(String dpmo) {
		this.dpmo = dpmo;
	}

	public String getSigma() {
		return sigma;
	}

	public void setSigma(String sigma) {
		this.sigma = sigma;
	}

	public String getErYn() {
		return erYn;
	}

	public void setErYn(String erYn) {
		this.erYn = erYn;
	}

	public String getTgtVrfJoinCd() {
		return tgtVrfJoinCd;
	}

	public void setTgtVrfJoinCd(String tgtVrfJoinCd) {
		this.tgtVrfJoinCd = tgtVrfJoinCd;
	}
	
	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}

	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}

	public String getShdJobId() {
		return shdJobId;
	}

	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}

	public String getEtcJobNm() {
		return etcJobNm;
	}

	public void setEtcJobNm(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}
	
 
	
	public String getUppDqiLnm() {
		return uppDqiLnm;
	}

	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
	}

	public String getAnaCnt2() {
		return anaCnt2;
	}

	public void setAnaCnt2(String anaCnt2) {
		this.anaCnt2 = anaCnt2;
	}

	public String getErCnt2() {
		return erCnt2;
	}

	public void setErCnt2(String erCnt2) {
		this.erCnt2 = erCnt2;
	}

	public String getErRate2() {
		return erRate2;
	}

	public void setErRate2(String erRate2) {
		this.erRate2 = erRate2;
	}

	public String getAnaCnt3() {
		return anaCnt3;
	}

	public void setAnaCnt3(String anaCnt3) {
		this.anaCnt3 = anaCnt3;
	}

	public String getErCnt3() {
		return erCnt3;
	}

	public void setErCnt3(String erCnt3) {
		this.erCnt3 = erCnt3;
	}

	public String getErRate3() {
		return erRate3;
	}

	public void setErRate3(String erRate3) {
		this.erRate3 = erRate3;
	}

	@Override
	public String toString() {
		return "MultiDimVO [brId=" + brId + ", brNm=" + brNm + ", dbConnTrgId="
				+ dbConnTrgId + ", dbSchId=" + dbSchId + ", dbcTblNm="
				+ dbcTblNm + ", dbcColNm=" + dbcColNm + ", bizAreaId="
				+ bizAreaId + ", brCrgpUserId=" + brCrgpUserId + ", agrNv="
				+ agrNv + ", glNv=" + glNv + ", useYn=" + useYn
				+ ", tgtDbConnTrgId=" + tgtDbConnTrgId + ", tgtDbConnTrgPnm="
				+ tgtDbConnTrgPnm + ", tgtDbSchId=" + tgtDbSchId
				+ ", tgtDbSchLnm=" + tgtDbSchLnm + ", tgtDbcTblNm="
				+ tgtDbcTblNm + ", tgtDbcColNm=" + tgtDbcColNm
				+ ", tgtKeyDbcColNm=" + tgtKeyDbcColNm + ", tgtKeyDbcColVal="
				+ tgtKeyDbcColVal + ", cntSql=" + cntSql + ", erCntSql="
				+ erCntSql + ", anaSql=" + anaSql + ", tgtCntSql=" + tgtCntSql
				+ ", tgtErCntSql=" + tgtErCntSql + ", tgtAnaSql=" + tgtAnaSql
				+ ", dbConnTrgLnm=" + dbConnTrgLnm + ", dqiId=" + dqiId
				+ ", dqiLnm=" + dqiLnm + ", uppDqiLnm=" + uppDqiLnm
				+ ", ctqId=" + ctqId + ", ctqLnm=" + ctqLnm + ", baseDttm="
				+ baseDttm + ", anaDgrDisp=" + anaDgrDisp + ", erRate="
				+ erRate + ", dpmo=" + dpmo + ", sigma=" + sigma + ", erYn="
				+ erYn + ", anaJobId=" + anaJobId + ", anaJobNm=" + anaJobNm
				+ ", anaKndCd=" + anaKndCd + ", brCrgpUserNm=" + brCrgpUserNm
				+ ", tgtVrfJoinCd=" + tgtVrfJoinCd + ", dbConnTrgPnm="
				+ dbConnTrgPnm + ", dbSchLnm=" + dbSchLnm + ", shdJobId="
				+ shdJobId + ", etcJobNm=" + etcJobNm + ", dbSchPnm="
				+ dbSchPnm + ", weight=" + weight + ", goal=" + goal
				+ ", archRate=" + archRate + ", anaCnt=" + anaCnt + "]";
	}




}