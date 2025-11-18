package kr.wise.commons.code.service;

import kr.wise.commons.cmm.CommonVo;


public class WaaCommDtlCd extends CommonVo  {
    private String commDtlCdId;		//상세코드ID

    private String commDtlCd;		//상세코드

    private String commDtlCdNm;		//상세코드명

    private String commDcdId;		//구분코드

    private String uppCommDtlCdId;	//상위상세코드 ID

    private Integer dispOrd;		//출력순서

    private String sltDcdId;		//선택된 공통코드ID -곽효신

    private String useYn;		//사용여부

    /**
	 * @return the sltDcdId
	 */
	public String getSltDcdId() {
		return sltDcdId;
	}

	/**
	 * @param sltDcdId the sltDcdId to set
	 */
	public void setSltDcdId(String sltDcdId) {
		this.sltDcdId = sltDcdId;
	}

	public String getCommDtlCdId() {
        return commDtlCdId;
    }

    public void setCommDtlCdId(String commDtlCdId) {
        this.commDtlCdId = commDtlCdId;
    }

    public String getCommDtlCd() {
        return commDtlCd;
    }

    public void setCommDtlCd(String commDtlCd) {
        this.commDtlCd = commDtlCd;
    }

    public String getCommDtlCdNm() {
        return commDtlCdNm;
    }

    public void setCommDtlCdNm(String commDtlCdNm) {
        this.commDtlCdNm = commDtlCdNm;
    }

    public String getCommDcdId() {
        return commDcdId;
    }

    public void setCommDcdId(String commDcdId) {
        this.commDcdId = commDcdId;
    }

    public String getUppCommDtlCdId() {
        return uppCommDtlCdId;
    }

    public void setUppCommDtlCdId(String uppCommDtlCdId) {
        this.uppCommDtlCdId = uppCommDtlCdId;
    }

    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
    }
    
	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaCommDtlCd [commDtlCdId=");
		builder.append(commDtlCdId);
		builder.append(", commDtlCd=");
		builder.append(commDtlCd);
		builder.append(", commDtlCdNm=");
		builder.append(commDtlCdNm);
		builder.append(", commDcdId=");
		builder.append(commDcdId);
		builder.append(", uppCommDtlCdId=");
		builder.append(uppCommDtlCdId);
		builder.append(", dispOrd=");
		builder.append(dispOrd);
		builder.append(", sltDcdId=");
		builder.append(sltDcdId);
		builder.append("]");
		return builder.toString();
	}

}