package kr.wise.meta.sitemap.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.portal.totalsearch.service.TotalSearch;

public interface SiteMapService {

	List<MenuManageVO> selectMenuListForSiteMap(MenuManageVO searchVO);
	List<MenuManageVO> selectFavoriteList(MenuManageVO searchVO);
	
	int saveFavoriteMenu(String menuId, String userId, String userGroupId);
	int deleteFavoriteMenu(String userId, String userGroupId);
	
	
}
