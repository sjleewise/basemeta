package kr.wise.advisor.prepare.outlier.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlResult extends CommonVo {
    private String otlDtcId;

    private Date anaStrDtm;

    private Date anaEndDtm;

    private String anlVarId;

    private Integer anaDgr;

    private Integer anaCnt;

    private Integer esnErCnt;

    private String anaLogId;

    private String anaUserId;

    private String anaTime;
    
    private String daseId  ;	
    private String otlAlgId;
    private String algTypCd;
    private String algLnm  ;
    private String algPnm  ;
    

    public String getOtlDtcId() {
        return otlDtcId;
    }

    public void setOtlDtcId(String otlDtcId) {
        this.otlDtcId = otlDtcId;
    }

    public Date getAnaStrDtm() {
        return anaStrDtm;
    }

    public void setAnaStrDtm(Date anaStrDtm) {
        this.anaStrDtm = anaStrDtm;
    }

    public Date getAnaEndDtm() {
        return anaEndDtm;
    }

    public void setAnaEndDtm(Date anaEndDtm) {
        this.anaEndDtm = anaEndDtm;
    }

    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
    }

    public Integer getAnaDgr() {
        return anaDgr;
    }

    public void setAnaDgr(Integer anaDgr) {
        this.anaDgr = anaDgr;
    }


    public String getAnaLogId() {
        return anaLogId;
    }

    public void setAnaLogId(String anaLogId) {
        this.anaLogId = anaLogId;
    }

    public String getAnaUserId() {
        return anaUserId;
    }

    public void setAnaUserId(String anaUserId) {
        this.anaUserId = anaUserId;
    }

    public String getAnaTime() {
        return anaTime;
    }

    public void setAnaTime(String anaTime) {
        this.anaTime = anaTime;
    }

	/**
	 * @return the anaCnt
	 */
	public Integer getAnaCnt() {
		return anaCnt;
	}

	/**
	 * @param anaCnt the anaCnt to set
	 */
	public void setAnaCnt(Integer anaCnt) {
		this.anaCnt = anaCnt;
	}

	/**
	 * @return the esnErCnt
	 */
	public Integer getEsnErCnt() {
		return esnErCnt;
	}

	/**
	 * @param esnErCnt the esnErCnt to set
	 */
	public void setEsnErCnt(Integer esnErCnt) {
		this.esnErCnt = esnErCnt;
	}

	/**
	 * @return the daseId
	 */
	public String getDaseId() {
		return daseId;
	}

	/**
	 * @param daseId the daseId to set
	 */
	public void setDaseId(String daseId) {
		this.daseId = daseId;
	}

	/**
	 * @return the otlAlgId
	 */
	public String getOtlAlgId() {
		return otlAlgId;
	}

	/**
	 * @param otlAlgId the otlAlgId to set
	 */
	public void setOtlAlgId(String otlAlgId) {
		this.otlAlgId = otlAlgId;
	}

	/**
	 * @return the algTypCd
	 */
	public String getAlgTypCd() {
		return algTypCd;
	}

	/**
	 * @param algTypCd the algTypCd to set
	 */
	public void setAlgTypCd(String algTypCd) {
		this.algTypCd = algTypCd;
	}

	/**
	 * @return the algLnm
	 */
	public String getAlgLnm() {
		return algLnm;
	}

	/**
	 * @param algLnm the algLnm to set
	 */
	public void setAlgLnm(String algLnm) {
		this.algLnm = algLnm;
	}

	/**
	 * @return the algPnm
	 */
	public String getAlgPnm() {
		return algPnm;
	}

	/**
	 * @param algPnm the algPnm to set
	 */
	public void setAlgPnm(String algPnm) {
		this.algPnm = algPnm;
	}
}