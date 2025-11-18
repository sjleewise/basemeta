package kr.wise.dq.profile.reac.service;

public class WaqPrfReacColVO extends WaqPrfReacTblVO{

    private Integer relColSno;

    private String chTblDbcColNm;

    private String paTblDbcColNm;


    public Integer getRelColSno() {
        return relColSno;
    }

    public void setRelColSno(Integer relColSno) {
        this.relColSno = relColSno;
    }

    public String getChTblDbcColNm() {
        return chTblDbcColNm;
    }

    public void setChTblDbcColNm(String chTblDbcColNm) {
        this.chTblDbcColNm = chTblDbcColNm;
    }

    public String getPaTblDbcColNm() {
        return paTblDbcColNm;
    }

    public void setPaTblDbcColNm(String paTblDbcColNm) {
        this.paTblDbcColNm = paTblDbcColNm;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfRelColVO [relColSno=").append(relColSno)
				.append(", chTblDbcColNm=").append(chTblDbcColNm)
				.append(", paTblDbcColNm=").append(paTblDbcColNm).append("]");
		return builder.toString();
	}

}