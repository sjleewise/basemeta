package kr.wise.dq.profile.tblrel.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

public class WaqPrfRelTblVO extends WaqPrfMstrVO {

    private String paTblDbConnTrgNm;

    private String paTblDbcSchNm;

    private String paTblDbcTblNm;

    private String paTblDbConnTrgId;

    private String paTblDbcSchId;

    private String paTblAdtCndSql;

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

    public String getPaTblDbcTblNm() {
        return paTblDbcTblNm;
    }

    public void setPaTblDbcTblNm(String paTblDbcTblNm) {
        this.paTblDbcTblNm = paTblDbcTblNm;
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

    public String getPaTblAdtCndSql() {
        return paTblAdtCndSql;
    }

    public void setPaTblAdtCndSql(String paTblAdtCndSql) {
        this.paTblAdtCndSql = paTblAdtCndSql;
    }
}