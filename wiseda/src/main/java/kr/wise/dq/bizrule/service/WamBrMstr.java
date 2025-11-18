package kr.wise.dq.bizrule.service;


public class WamBrMstr extends WamBrResult{
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

    private String ctqId;

    private String ctqLnm;

    private String baseDttm;

    private String anaDgrDisp;

    private String erRate;

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
    
    private String sortTyp;
    
    public String getSortTyp() {
		return sortTyp;
	}

	public void setSortTyp(String sortTyp) {
		this.sortTyp = sortTyp;
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
	
 
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamBrMstr [brId=").append(brId).append(", brNm=")
				.append(brNm).append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbSchId=").append(dbSchId).append(", dbcTblNm=")
				.append(dbcTblNm).append(", dbcColNm=").append(dbcColNm)
				.append(", bizAreaId=").append(bizAreaId)
				.append(", brCrgpUserId=").append(brCrgpUserId)
				.append(", agrNv=").append(agrNv).append(", glNv=")
				.append(glNv).append(", useYn=").append(useYn)
				.append(", tgtDbConnTrgId=").append(tgtDbConnTrgId)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", tgtDbSchLnm=").append(tgtDbSchLnm)
				.append(", tgtDbcTblNm=").append(tgtDbcTblNm)
				.append(", tgtDbcColNm=").append(tgtDbcColNm)
				.append(", tgtKeyDbcColNm=").append(tgtKeyDbcColNm)
				.append(", tgtKeyDbcColVal=").append(tgtKeyDbcColVal)
				.append(", cntSql=").append(cntSql).append(", erCntSql=")
				.append(erCntSql).append(", anaSql=").append(anaSql)
				.append(", tgtCntSql=").append(tgtCntSql)
				.append(", tgtErCntSql=").append(tgtErCntSql)
				.append(", tgtAnaSql=").append(tgtAnaSql)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dqiId=").append(dqiId).append(", dqiLnm=")
				.append(dqiLnm).append(", ctqId=").append(ctqId)
				.append(", ctqLnm=").append(ctqLnm).append(", baseDttm=")
				.append(baseDttm).append(", anaDgrDisp=").append(anaDgrDisp)
				.append(", erRate=").append(erRate).append(", dpmo=")
				.append(dpmo).append(", sigma=").append(sigma)
				.append(", erYn=").append(erYn).append(", anaJobId=")
				.append(anaJobId).append(", anaJobNm=").append(anaJobNm)
				.append(", anaKndCd=").append(anaKndCd)
				.append(", brCrgpUserNm=").append(brCrgpUserNm)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", archRate=").append(archRate)
				.append(", dbSchLnm=").append(dbSchLnm).append("]");
		return builder.toString();
	}




}