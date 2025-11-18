package kr.wise.dq.model.service;

import kr.wise.commons.cmm.CommonVo;


public class WdqhPdmTbl extends CommonVo {
    private String pdmTblId;

//    private String dbConnTrgId;

//    private String dbSchId;

//    private String expDtm;

//    private String strDtm;

    private String pdmTblPnm;

    private String pdmTblLnm;

    private String crgUserId;

//    private String objDescn;

//    private int objVers;

//    private String regTypCd;

//    private String rqstDtm;

//    private String rqstUserId;

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }

    public String getPdmTblPnm() {
        return pdmTblPnm;
    }

    public void setPdmTblPnm(String pdmTblPnm) {
        this.pdmTblPnm = pdmTblPnm;
    }

    public String getPdmTblLnm() {
        return pdmTblLnm;
    }

    public void setPdmTblLnm(String pdmTblLnm) {
        this.pdmTblLnm = pdmTblLnm;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }
}