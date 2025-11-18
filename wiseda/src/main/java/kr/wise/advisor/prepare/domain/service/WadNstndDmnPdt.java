package kr.wise.advisor.prepare.domain.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WadNstndDmnPdt extends CommonVo {
    private String anlVarId;

    private String dmngLnm;

    private Integer dmnRnk;

    private Float dmnPrb;

    private String objDescn;

    private Integer objVers;

    private String regTypCd;

    private Date writDtm;

    private String writUserId;

    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
    }

    public String getDmngLnm() {
        return dmngLnm;
    }

    public void setDmngLnm(String dmngLnm) {
        this.dmngLnm = dmngLnm;
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

    public Date getWritDtm() {
        return writDtm;
    }

    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }

	/**
	 * @return the dmnRnk
	 */
	public Integer getDmnRnk() {
		return dmnRnk;
	}

	/**
	 * @param dmnRnk the dmnRnk to set
	 */
	public void setDmnRnk(Integer dmnRnk) {
		this.dmnRnk = dmnRnk;
	}

	/**
	 * @return the dmnPrb
	 */
	public Float getDmnPrb() {
		return dmnPrb;
	}

	/**
	 * @param dmnPrb the dmnPrb to set
	 */
	public void setDmnPrb(Float dmnPrb) {
		this.dmnPrb = dmnPrb;
	}
}