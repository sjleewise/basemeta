package kr.wise.commons.sysmgmt.menu.service;


public class WaaUsergMenuAuth extends MenuManageVO {
//    private String menuId;

    private String usergId;
    private String rootMenuId;
    private String rootMenuNm;

//    private Date expDtm;
//
//    private Date strDtm;
//
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

//    public String getMenuId() {
//        return menuId;
//    }
//
//    public void setMenuId(String menuId) {
//        this.menuId = menuId;
//    }

    public String getRootMenuNm() {
		return rootMenuNm;
	}

	public void setRootMenuNm(String rootMenuNm) {
		this.rootMenuNm = rootMenuNm;
	}

	public String getUsergId() {
        return usergId;
    }

    public void setUsergId(String usergId) {
        this.usergId = usergId;
    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }
//
//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
//    }
//
//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getWritDtm() {
//        return writDtm;
//    }
//
//    public void setWritDtm(Date writDtm) {
//        this.writDtm = writDtm;
//    }
//
//    public String getWritUserId() {
//        return writUserId;
//    }
//
//    public void setWritUserId(String writUserId) {
//        this.writUserId = writUserId;
//    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaUsergMenuAuth [usergId=").append(usergId)
				.append("]");
		return builder.toString() + super.toString();
	}

	/**
	 * @return the rootMenuId
	 */
	public String getRootMenuId() {
		return rootMenuId;
	}

	/**
	 * @param rootMenuId the rootMenuId to set
	 */
	public void setRootMenuId(String rootMenuId) {
		this.rootMenuId = rootMenuId;
	}
}