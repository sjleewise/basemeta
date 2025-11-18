package kr.wise.dq.profile.colefvaana.service;

public class WaqPrfEfvaUserDfVO extends WaqPrfEfvaAnaVO {

    private Integer rqstDtlSno;
	 
    private String userDfEfva;

    private String userDfEfvaNm;

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

    public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" \n WaqPrfEfvaUserDfVO [")
				.append(", userDfEfva=").append(userDfEfva)
				.append(", userDfEfvaNm=").append(userDfEfvaNm).append("]");
		return super.toString() + builder.toString();
	}

    
}