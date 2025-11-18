package kr.wise.dq.profile.coldtfrmana.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;


public class WaqPrfDtfrmAnaVO extends WaqPrfMstrVO {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfDtfrmAnaVO [dateFrmCd=").append(dateFrmCd)
				.append(", userDateFrm=").append(userDateFrm).append("]");
		return builder.toString() + super.toString();
	}
    

}