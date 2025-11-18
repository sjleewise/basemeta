package kr.wise.dq.profile.reac.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.helper.IBSDateJsonDeserializer;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

public class WamPrfReacTblVO extends WamPrfMstrVO {

    private String paTblDbConnTrgId;

    private String paTblDbcSchId;

    private String paTblDbcTblNm;

    private String paTblAdtCndSql;
    
    private String paTblDbConnTrgNm;
    
    private String paTblDbcSchNm;

    private String paTblDbcTblKorNm;

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

    public String getPaTblAdtCndSql() {
        return paTblAdtCndSql;
    }

    public void setPaTblAdtCndSql(String paTblAdtCndSql) {
        this.paTblAdtCndSql = paTblAdtCndSql;
    }

	public String getPaTblDbConnTrgNm() {
		return paTblDbConnTrgNm;
	}

	public void setPaTblDbConnTrgNm(String paTblDbConnTrgNm) {
		this.paTblDbConnTrgNm = paTblDbConnTrgNm;
	}

	public String getPaTblDbcSchNm() {
		return paTblDbcSchNm;
	}

	public void setPaTblDbcSchNm(String paTblDbcSchNm) {
		this.paTblDbcSchNm = paTblDbcSchNm;
	}

	public String getPaTblDbcTblKorNm() {
		return paTblDbcTblKorNm;
	}

	public void setPaTblDbcTblKorNm(String paTblDbcTblKorNm) {
		this.paTblDbcTblKorNm = paTblDbcTblKorNm;
	}
    
}