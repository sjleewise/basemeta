package kr.wise.meta.sitemap.service;

import java.util.List;

import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface SiteMapMapper {
    
	List<MenuManageVO> selectMenuListForSiteMap(MenuManageVO searchVO);
	List<MenuManageVO> selectFavoriteList(MenuManageVO searchVO);
	int saveFavoriteMenu( @Param("menuId") String menuId, @Param("userId") String userId, @Param("userGroupId") String userGroupId);
	int deleteFavoriteMenu( @Param("userId") String userId, @Param("userGroupId") String userGroupId);
	
}