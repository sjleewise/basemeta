package kr.wise.admin.sysmgmt.help.service;

import java.util.Date;

public class WaaHelp {
    private String helpId;

    private String scrNm;

    private String scrUrl;

    private String useYn;

    private String menuId;

    private String regTypCd;

    private Date writDtm;

    private String writUserId;

    private Date expDtm;

    private Date strDtm;

    private String helpCtnt;
    
    private String option = "";

    public String getHelpId() {
        return helpId;
    }

    public void setHelpId(String helpId) {
        this.helpId = helpId;
    }

    public String getScrNm() {
        return scrNm;
    }

    public void setScrNm(String scrNm) {
        this.scrNm = scrNm;
    }

    public String getScrUrl() {
        return scrUrl;
    }

    public void setScrUrl(String scrUrl) {
        this.scrUrl = scrUrl;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getWritDtm() {
        return writDtm;
    }

    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }

    public Date getExpDtm() {
        return expDtm;
    }

    public void setExpDtm(Date expDtm) {
        this.expDtm = expDtm;
    }

    public Date getStrDtm() {
        return strDtm;
    }

    public void setStrDtm(Date strDtm) {
        this.strDtm = strDtm;
    }

    public String getHelpCtnt() {
        return helpCtnt;
    }

    public void setHelpCtnt(String helpCtnt) {
        this.helpCtnt = helpCtnt;
    }

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
}