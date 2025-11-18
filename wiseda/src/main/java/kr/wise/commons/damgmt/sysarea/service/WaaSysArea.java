package kr.wise.commons.damgmt.sysarea.service;

import kr.wise.commons.cmm.CommonVo;


public class WaaSysArea extends CommonVo  {
	
    private String sysAreaId; 		//시스템영역 ID
    
    private String sysAreaLnm;		//시스템영역 논리명

    private String sysAreaPnm;		//시스템영역 물리명

    private String sysAreaAbrNm;	//시스템영역 약어

    private String stdAplYn;		//표준적용 여

    private String lecyDcd;			//레거시 코드 여부

    private String crgUserId;		//담당사용자 ID
    
    private String crgUserNm;		//담당사용자명
    
    


    public String getCrgUserNm() {
		return crgUserNm;
	}

	public void setCrgUserNm(String crgUserNm) {
		this.crgUserNm = crgUserNm;
	}

	public String getSysAreaId() {
        return sysAreaId;
    }

    public void setSysAreaId(String sysAreaId) {
        this.sysAreaId = sysAreaId;
    }

    public String getSysAreaLnm() {
        return sysAreaLnm;
    }

    public void setSysAreaLnm(String sysAreaLnm) {
        this.sysAreaLnm = sysAreaLnm;
    }

    public String getSysAreaPnm() {
        return sysAreaPnm;
    }

    public void setSysAreaPnm(String sysAreaPnm) {
        this.sysAreaPnm = sysAreaPnm;
    }

    public String getSysAreaAbrNm() {
        return sysAreaAbrNm;
    }

    public void setSysAreaAbrNm(String sysAreaAbrNm) {
        this.sysAreaAbrNm = sysAreaAbrNm;
    }

    public String getStdAplYn() {
        return stdAplYn;
    }

    public void setStdAplYn(String stdAplYn) {
        this.stdAplYn = stdAplYn;
    }

    public String getLecyDcd() {
        return lecyDcd;
    }

    public void setLecyDcd(String lecyDcd) {
        this.lecyDcd = lecyDcd;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaSysArea [sysAreaId=");
		builder.append(sysAreaId);
		builder.append(", sysAreaLnm=");
		builder.append(sysAreaLnm);
		builder.append(", sysAreaPnm=");
		builder.append(sysAreaPnm);
		builder.append(", sysAreaAbrNm=");
		builder.append(sysAreaAbrNm);
		builder.append(", stdAplYn=");
		builder.append(stdAplYn);
		builder.append(", lecyDcd=");
		builder.append(lecyDcd);
		builder.append(", crgUserId=");
		builder.append(crgUserId);
		builder.append("]");
		return builder.toString()+super.toString();
	}


}