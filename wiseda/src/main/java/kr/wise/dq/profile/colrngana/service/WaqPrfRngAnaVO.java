package kr.wise.dq.profile.colrngana.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;


public class WaqPrfRngAnaVO extends WaqPrfMstrVO {

    private String rngOpr1;

    private String rngEfva1;

    private String rngCnc;

    private String rngOpr2;

    private String rngEfva2;

	public String getRngOpr1() {
		return rngOpr1;
	}

	public void setRngOpr1(String rngOpr1) {
		this.rngOpr1 = rngOpr1;
	}

	public String getRngEfva1() {
		return rngEfva1;
	}

	public void setRngEfva1(String rngEfva1) {
		this.rngEfva1 = rngEfva1;
	}

	public String getRngCnc() {
		return rngCnc;
	}

	public void setRngCnc(String rngCnc) {
		this.rngCnc = rngCnc;
	}

	public String getRngOpr2() {
		return rngOpr2;
	}

	public void setRngOpr2(String rngOpr2) {
		this.rngOpr2 = rngOpr2;
	}

	public String getRngEfva2() {
		return rngEfva2;
	}

	public void setRngEfva2(String rngEfva2) {
		this.rngEfva2 = rngEfva2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfRngAnaVO [rngOpr1=").append(rngOpr1)
				.append(", rngEfva1=").append(rngEfva1).append(", rngCnc=")
				.append(rngCnc).append(", rngOpr2=").append(rngOpr2)
				.append(", rngEfva2=").append(rngEfva2).append("]");
		return builder.toString();
	}

    
}