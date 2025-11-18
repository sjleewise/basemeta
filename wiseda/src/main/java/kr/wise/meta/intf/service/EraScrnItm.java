package kr.wise.meta.intf.service;

public class EraScrnItm {
    private String vcbId;

    private String vcb;

    private String langCode;

    private String vcbLang;

    private String objDescn;

    private String useYn;
    
    private String bizDivCd;

    private String searchval;
    private String searchcon; /* A:%%, F:뒤%, B:앞%, E:= */
    
	public String getVcbId() {
		return vcbId;
	}
	public void setVcbId(String vcbId) {
		this.vcbId = vcbId;
	}
	public String getVcb() {
		return vcb;
	}
	public void setVcb(String vcb) {
		this.vcb = vcb;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getVcbLang() {
		return vcbLang;
	}
	public void setVcbLang(String vcbLang) {
		this.vcbLang = vcbLang;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getBizDivCd() {
		return bizDivCd;
	}
	public void setBizDivCd(String bizDivCd) {
		this.bizDivCd = bizDivCd;
	}
	public String getSearchval() {
		return searchval;
	}
	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}
	public String getSearchcon() {
		return searchcon;
	}
	public void setSearchcon(String searchcon) {
		this.searchcon = searchcon;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EraScrnItm [vcbId=").append(vcbId).append(", vcb=")
				.append(vcb).append(", langCode=").append(langCode)
				.append(", vcbLang=").append(vcbLang).append(", objDescn=")
				.append(objDescn).append(", useYn=").append(useYn)
				.append(", bizDivCd=").append(bizDivCd).append(", searchval=")
				.append(searchval).append(", searchcon=").append(searchcon)
				.append("]");
		return builder.toString();
	}
    
    
}