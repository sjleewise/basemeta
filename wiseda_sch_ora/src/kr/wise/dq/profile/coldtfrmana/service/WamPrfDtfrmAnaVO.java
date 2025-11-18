package kr.wise.dq.profile.coldtfrmana.service;


import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

public class WamPrfDtfrmAnaVO extends WamPrfMstrVO {

    private String dateFrmCd;

    private String userDateFrm;

    public String getDateFrmCd() {
        return dateFrmCd;
    }

    public void setDateFrmCd(String dateFrmCd) {
        this.dateFrmCd = dateFrmCd;
    }

    public String getUserDateFrm() {
        return userDateFrm;
    }

    public void setUserDateFrm(String userDateFrm) {
        this.userDateFrm = userDateFrm;
    }
}