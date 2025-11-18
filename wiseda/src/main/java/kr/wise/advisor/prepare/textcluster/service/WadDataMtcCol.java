package kr.wise.advisor.prepare.textcluster.service;

public class WadDataMtcCol extends WadDataMtcTbl {
    private String mtcId;

    private Integer mtcColSno;

    private String srcDbcColNm;

    private String tgtDbcColNm;


    public String getMtcId() {
        return mtcId;
    }

    public void setMtcId(String mtcId) {
        this.mtcId = mtcId;
    }

    public Integer getMtcColSno() {
        return mtcColSno;
    }

    public void setMtcColSno(Integer mtcColSno) {
        this.mtcColSno = mtcColSno;
    }

    public String getSrcDbcColNm() {
        return srcDbcColNm;
    }

    public void setSrcDbcColNm(String srcDbcColNm) {
        this.srcDbcColNm = srcDbcColNm;
    }

    public String getTgtDbcColNm() {
        return tgtDbcColNm;
    }

    public void setTgtDbcColNm(String tgtDbcColNm) {
        this.tgtDbcColNm = tgtDbcColNm;
    }

}