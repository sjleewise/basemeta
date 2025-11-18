package kr.wise.dq.metadmn.service;

public class MetaDmnCdValItfVO extends MetaDmnItfVO {
    //코드유효값
    private String cdValId;

    private String cdVal;

    private String cdValNm;

	public String getCdValId() {
		return cdValId;
	}

	public void setCdValId(String cdValId) {
		this.cdValId = cdValId;
	}

	public String getCdVal() {
		return cdVal;
	}

	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}

	public String getCdValNm() {
		return cdValNm;
	}

	public void setCdValNm(String cdValNm) {
		this.cdValNm = cdValNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetaDmnCdValItfVO [cdValId=").append(cdValId)
				.append(", cdVal=").append(cdVal).append(", cdValNm=")
				.append(cdValNm).append("]");
		return builder.toString();
	}
    
    
    
}