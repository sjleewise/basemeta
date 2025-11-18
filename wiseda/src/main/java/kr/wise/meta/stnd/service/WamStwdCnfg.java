package kr.wise.meta.stnd.service;

public class WamStwdCnfg extends WamStwd{
    private String objId;

    private Integer wdSno;

    private String stwdId;

    private String rqstNo;

    private Integer rqstSno;
    


    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Integer getWdSno() {
        return wdSno;
    }

    public void setWdSno(Integer wdSno) {
        this.wdSno = wdSno;
    }

    public String getStwdId() {
        return stwdId;
    }

    public void setStwdId(String stwdId) {
        this.stwdId = stwdId;
    }

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }
}