package kr.wise.commons.sysmgmt.menu.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface MenuManageMapper {
    int deleteByPrimaryKey(int menuNo);

//    int insert(MenuManageVO record);

//    int insertSelective(MenuManageVO record);

    MenuManageVO selectByPrimaryKey(int menuNo);

    int updateByPrimaryKeySelective(MenuManageVO record);

//    int updateByPrimaryKey(MenuManageVO record);

	List<MenuManageVO> selectMenuList(MenuManageVO searchVO);

	MenuManageVO selectMenuDetail(String menuId);

	int insertMenu(MenuManageVO saveVO);

	int updateMenu(MenuManageVO saveVO);

	List<MenuStatVO> selectMenuStatTot(MenuStatVO searchVO);

	List<MenuStatVO> selectMenuStat(MenuStatVO searchVO);

	int deleteMenu(String menuId);
	
	int updateExpDtm(MenuManageVO record);

	/** @param menuId yeonho */
	int updateFullPath(String menuId);

}