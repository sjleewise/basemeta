package kr.wise.dq.profile.colefvaana.service;


public class WamPrfEfvaUserDfVO extends WamPrfEfvaAnaVO  {

    private Integer rqstDtlSno;

    private String userDfEfva;

    private String userDfEfvaNm;



    public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	public String getUserDfEfva() {
        return userDfEfva;
    }

    public void setUserDfEfva(String userDfEfva) {
        this.userDfEfva = userDfEfva;
    }

    public String getUserDfEfvaNm() {
        return userDfEfvaNm;
    }

    public void setUserDfEfvaNm(String userDfEfvaNm) {
        this.userDfEfvaNm = userDfEfvaNm;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPrfEfvaUserDfVO [rqstDtlSno=").append(rqstDtlSno)
				.append(", userDfEfva=").append(userDfEfva)
				.append(", userDfEfvaNm=").append(userDfEfvaNm).append("]");
		return builder.toString();
	}

    
}