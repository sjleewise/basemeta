package kr.wise.commons.sysmgmt.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UsergMenuMapService {

	List<WaaUsergMenuAuth> selectMenuList(WaaUsergMenuAuth search);

	int menuReglist(ArrayList<WaaUsergMenuAuth> list, String usergId);

	/** @param servletPath
	/** @return insomnia */
	List<WaaUsergMenuAuth> getTopMenuList(String servletPath);

	/** @param servletPath
	/** @return insomnia */
	Map<String, Object> getMenuMap(String servletPath);
	
	Map<String, Object> getMenuMap2(String servletPath);
	
	void setLangDcdMenu(String pLangDcd);

}
