package kr.wise.dq.profile.reac.service;

import kr.wise.dq.profile.mstr.service.WahPrfMstrVO;

public class WahPrfReacTblVO extends WahPrfMstrVO {

    private String paTblDbConnTrgId;

    private String paTblDbcSchId;

    private String paTblDbcTblNm;

    private String paTblAdtCndSql;

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
}