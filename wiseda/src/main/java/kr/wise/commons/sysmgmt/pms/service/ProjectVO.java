package kr.wise.commons.sysmgmt.pms.service;

import java.util.Date;

public class ProjectVO {
    private String prjId;
    
    private String[] prjIds;

    private String prjNm;

    private String pmNm;

    private String pmTel;

    private String description;

    private String staDttm;
    
    private String endDttm;

    private String orglUser;

    private Date orglDttm;

    private String updtUser;
    
    private String updtUserNm;

    private Date aprvDttm;

    private String useYn;

    private String orglType;

    
    
    public String getUpdtUserNm() {
		return updtUserNm;
	}

	public void setUpdtUserNm(String updtUserNm) {
		this.updtUserNm = updtUserNm;
	}

	public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getPrjNm() {
        return prjNm;
    }

    public void setPrjNm(String prjNm) {
        this.prjNm = prjNm;
    }

    public String getPmNm() {
        return pmNm;
    }

    public void setPmNm(String pmNm) {
        this.pmNm = pmNm;
    }

    public String getPmTel() {
        return pmTel;
    }

    public void setPmTel(String pmTel) {
        this.pmTel = pmTel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    

	public String getStaDttm() {
		return staDttm;
	}

	public void setStaDttm(String staDttm) {
		this.staDttm = staDttm;
	}

	public String getEndDttm() {
		return endDttm;
	}

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public String getOrglUser() {
        return orglUser;
    }

    public void setOrglUser(String orglUser) {
        this.orglUser = orglUser;
    }

    public Date getOrglDttm() {
        return orglDttm;
    }

    public void setOrglDttm(Date orglDttm) {
        this.orglDttm = orglDttm;
    }

    public String getUpdtUser() {
        return updtUser;
    }

    public void setUpdtUser(String updtUser) {
        this.updtUser = updtUser;
    }

    public Date getAprvDttm() {
        return aprvDttm;
    }

    public void setAprvDttm(Date aprvDttm) {
        this.aprvDttm = aprvDttm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOrglType() {
        return orglType;
    }

    public void setOrglType(String orglType) {
        this.orglType = orglType;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectVO [prjId=").append(prjId).append(", prjNm=")
				.append(prjNm).append(", pmNm=").append(pmNm)
				.append(", pmTel=").append(pmTel).append(", description=")
				.append(description).append(", staDttm=").append(staDttm)
				.append(", endDttm=").append(endDttm).append(", orglUser=")
				.append(orglUser).append(", orglDttm=").append(orglDttm)
				.append(", updtUser=").append(updtUser).append(", aprvDttm=")
				.append(aprvDttm).append(", useYn=").append(useYn)
				.append(", orglType=").append(orglType).append("]");
		return builder.toString();
	}

	public String[] getPrjIds() {
		return prjIds;
	}

	public void setPrjIds(String[] prjIds) {
		this.prjIds = prjIds;
	}

}