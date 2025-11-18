package kr.wise.dq.profile.colana.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

public class WaqPrfColAnaVO extends WaqPrfMstrVO {

    private String minMaxValAnaYn;

    private String avgAnaYn;

    private String stdvAnaYn;

    private String vrnAnaYn;

    private String aonlYn;

    private String spacAnaYn;

    private String lenAnaYn;

    private String crdAnaYn;


    public String getMinMaxValAnaYn() {
        return minMaxValAnaYn;
    }

    public void setMinMaxValAnaYn(String minMaxValAnaYn) {
        this.minMaxValAnaYn = minMaxValAnaYn;
    }

    public String getAvgAnaYn() {
        return avgAnaYn;
    }

    public void setAvgAnaYn(String avgAnaYn) {
        this.avgAnaYn = avgAnaYn;
    }

    public String getStdvAnaYn() {
        return stdvAnaYn;
    }

    public void setStdvAnaYn(String stdvAnaYn) {
        this.stdvAnaYn = stdvAnaYn;
    }

    public String getVrnAnaYn() {
        return vrnAnaYn;
    }

    public void setVrnAnaYn(String vrnAnaYn) {
        this.vrnAnaYn = vrnAnaYn;
    }

    public String getAonlYn() {
        return aonlYn;
    }

    public void setAonlYn(String aonlYn) {
        this.aonlYn = aonlYn;
    }

    public String getSpacAnaYn() {
        return spacAnaYn;
    }

    public void setSpacAnaYn(String spacAnaYn) {
        this.spacAnaYn = spacAnaYn;
    }

    public String getLenAnaYn() {
        return lenAnaYn;
    }

    public void setLenAnaYn(String lenAnaYn) {
        this.lenAnaYn = lenAnaYn;
    }

    public String getCrdAnaYn() {
        return crdAnaYn;
    }

    public void setCrdAnaYn(String crdAnaYn) {
        this.crdAnaYn = crdAnaYn;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" WaqPrfColAnaVO [minMaxValAnaYn=")
				.append(minMaxValAnaYn).append(", avgAnaYn=").append(avgAnaYn)
				.append(", stdvAnaYn=").append(stdvAnaYn).append(", vrnAnaYn=")
				.append(vrnAnaYn).append(", aonlYn=").append(aonlYn)
				.append(", spacAnaYn=").append(spacAnaYn).append(", lenAnaYn=")
				.append(lenAnaYn).append(", crdAnaYn=").append(crdAnaYn)
				.append("]");
		return super.toString() +  builder.toString();
	}


    
    
}