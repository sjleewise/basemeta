package kr.wise.meta.codecfcsys.service;

import kr.wise.commons.cmm.CommonVo;


public class WaqCodeCfcSys extends CommonVo {

    private String codeCfcSysId;

    private String codeCfcSysLnm;

    private String codeCfcSysPnm;

    private String codeCfcSysCd;

    private String codeCfcSysFrm;

    private String crgUserId;

    private String crgUserNm;

    public String getCodeCfcSysId() {
        return codeCfcSysId;
    }

    public void setCodeCfcSysId(String codeCfcSysId) {
        this.codeCfcSysId = codeCfcSysId;
    }

    public String getCodeCfcSysLnm() {
        return codeCfcSysLnm;
    }

    public void setCodeCfcSysLnm(String codeCfcSysLnm) {
        this.codeCfcSysLnm = codeCfcSysLnm;
    }

    public String getCodeCfcSysPnm() {
        return codeCfcSysPnm;
    }

    public void setCodeCfcSysPnm(String codeCfcSysPnm) {
        this.codeCfcSysPnm = codeCfcSysPnm;
    }

    public String getCodeCfcSysCd() {
        return codeCfcSysCd;
    }

    public void setCodeCfcSysCd(String codeCfcSysCd) {
        this.codeCfcSysCd = codeCfcSysCd;
    }

    public String getCodeCfcSysFrm() {
        return codeCfcSysFrm;
    }

    public void setCodeCfcSysFrm(String codeCfcSysFrm) {
        this.codeCfcSysFrm = codeCfcSysFrm;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

    public String getCrgUserNm() {
        return crgUserNm;
    }

    public void setCrgUserNm(String crgUserNm) {
        this.crgUserNm = crgUserNm;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqCodeCfcSys [codeCfcSysId=").append(codeCfcSysId)
				.append(", codeCfcSysLnm=").append(codeCfcSysLnm)
				.append(", codeCfcSysPnm=").append(codeCfcSysPnm)
				.append(", codeCfcSysCd=").append(codeCfcSysCd)
				.append(", codeCfcSysFrm=").append(codeCfcSysFrm)
				.append(", crgUserId=").append(crgUserId)
				.append(", crgUserNm=").append(crgUserNm).append("]");
		return builder.toString()+super.toString();
	}

}