package kr.wise.commons.sysmgmt.menu.service;

import java.util.List;

public class WaaUsergMenuAuthVO {
	private String usergId;
	
	/** 메뉴구분코드 */
    private String menuDcd;
    
    private String rootMenuId;
	
	private List<WaaUsergMenuAuth> menulist;

	public String getUsergId() {
		return usergId;
	}

	public void setUsergId(String usergId) {
		this.usergId = usergId;
	}

	public List<WaaUsergMenuAuth> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<WaaUsergMenuAuth> menulist) {
		this.menulist = menulist;
	}

	public String getMenuDcd() {
		return menuDcd;
	}

	public void setMenuDcd(String menuDcd) {
		this.menuDcd = menuDcd;
	}

	public String getRootMenuId() {
		return rootMenuId;
	}

	public void setRootMenuId(String rootMenuId) {
		this.rootMenuId = rootMenuId;
	}

}
