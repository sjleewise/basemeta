package kr.wise.commons.sysmgmt.menu.service;

import java.util.ArrayList;
import java.util.List;

public interface MenuManageService {

	List<MenuManageVO> selectMenuList(MenuManageVO searchVO);

	MenuManageVO selectMenuDetail(String menuId);

	public int saveMenu(MenuManageVO record, String saction) throws Exception ;
	
	public int regMenu(ArrayList<MenuManageVO> saveVO) throws Exception;

	List<MenuStatVO> selectMenuStatTot(MenuStatVO searchVO);

	List<MenuStatVO> selectMenuStat(MenuStatVO searchVO);

	public int deleteMenu(ArrayList<MenuManageVO> record) throws Exception;

}
