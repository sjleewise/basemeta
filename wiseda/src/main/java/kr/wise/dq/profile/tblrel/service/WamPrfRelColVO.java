package kr.wise.dq.profile.tblrel.service;

import java.util.Date;

import kr.wise.commons.helper.IBSDateJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class WamPrfRelColVO extends WamPrfRelTblVO{

    private Integer relColSno;

    private String chTblDbConnTrgId;

    private String chTblDbcSchId;

    private String chTblDbcTblNm;

    private String chTblDbcColNm;
    
    private String chTblDbConnTrgNm;
    
    private String chTblDbcSchNm;
    
    private String chTblDbcTblKorNm;
    
    private String chTblDbcColKorNm;

    private String paTblDbConnTrgId;

    private String paTblDbcSchId;

    private String paTblDbcTblNm;
    
    private String paTblDbcTblKorNm;

    private String paTblDbcColNm;    
    
    private String paTblDbcColKorNm;
    
    public Integer getRelColSno() {
        return relColSno;
    }

    public void setRelColSno(Integer relColSno) {
        this.relColSno = relColSno;
    }

    public String getChTblDbConnTrgId() {
        return chTblDbConnTrgId;
    }

    public void setChTblDbConnTrgId(String chTblDbConnTrgId) {
        this.chTblDbConnTrgId = chTblDbConnTrgId;
    }

    public String getChTblDbcSchId() {
        return chTblDbcSchId;
    }

    public void setChTblDbcSchId(String chTblDbcSchId) {
        this.chTblDbcSchId = chTblDbcSchId;
    }

    public String getChTblDbcTblNm() {
        return chTblDbcTblNm;
    }

    public void setChTblDbcTblNm(String chTblDbcTblNm) {
        this.chTblDbcTblNm = chTblDbcTblNm;
    }

    public String getChTblDbcColNm() {
        return chTblDbcColNm;
    }

    public void setChTblDbcColNm(String chTblDbcColNm) {
        this.chTblDbcColNm = chTblDbcColNm;
    }

    public String getPaTblDbConnTrgId() {
        return paTblDbConnTrgId;
    }

    public void setPaTblDbConnTrgId(String paTblDbConnTrgId) {
        this.paTblDbConnTrgId = paTblDbConnTrgId;
    }

    public String getPaTblDbcSchId() {
        return paTblDbcSchId;
    }

    public void setPaTblDbcSchId(String paTblDbcSchId) {
        this.paTblDbcSchId = paTblDbcSchId;
    }

    public String getPaTblDbcTblNm() {
        return paTblDbcTblNm;
    }

    public void setPaTblDbcTblNm(String paTblDbcTblNm) {
        this.paTblDbcTblNm = paTblDbcTblNm;
    }

    public String getPaTblDbcColNm() {
        return paTblDbcColNm;
    }

    public void setPaTblDbcColNm(String paTblDbcColNm) {
        this.paTblDbcColNm = paTblDbcColNm;
    }

	public String getChTblDbConnTrgNm() {
		return chTblDbConnTrgNm;
	}

	public void setChTblDbConnTrgNm(String chTblDbConnTrgNm) {
		this.chTblDbConnTrgNm = chTblDbConnTrgNm;
	}

	public String getChTblDbcSchNm() {
		return chTblDbcSchNm;
	}

	public void setChTblDbcSchNm(String chTblDbcSchNm) {
		this.chTblDbcSchNm = chTblDbcSchNm;
	}

	public String getChTblDbcTblKorNm() {
		return chTblDbcTblKorNm;
	}

	public void setChTblDbcTblKorNm(String chTblDbcTblKorNm) {
		this.chTblDbcTblKorNm = chTblDbcTblKorNm;
	}

	public String getChTblDbcColKorNm() {
		return chTblDbcColKorNm;
	}

	public void setChTblDbcColKorNm(String chTblDbcColKorNm) {
		this.chTblDbcColKorNm = chTblDbcColKorNm;
	}

	public String getPaTblDbcColKorNm() {
		return paTblDbcColKorNm;
	}

	public void setPaTblDbcColKorNm(String paTblDbcColKorNm) {
		this.paTblDbcColKorNm = paTblDbcColKorNm;
	}

	public String getPaTblDbcTblKorNm() {
		return paTblDbcTblKorNm;
	}

	public void setPaTblDbcTblKorNm(String paTblDbcTblKorNm) {
		this.paTblDbcTblKorNm = paTblDbcTblKorNm;
	}
    
}