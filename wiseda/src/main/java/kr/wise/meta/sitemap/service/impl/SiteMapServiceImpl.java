package kr.wise.meta.sitemap.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.meta.sitemap.service.SiteMapService;
import kr.wise.meta.sitemap.service.SiteMapMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service ("SiteMapService")
public class SiteMapServiceImpl implements SiteMapService {

	private final Logger log = LoggerFactory.getLogger(getClass());


    @Inject 
    SiteMapMapper siteMapMapper;

	public List<MenuManageVO> selectMenuListForSiteMap(MenuManageVO searchVO) {
		return siteMapMapper.selectMenuListForSiteMap(searchVO);
		
	}
	
	public List<MenuManageVO> selectFavoriteList(MenuManageVO searchVO){
		return siteMapMapper.selectFavoriteList(searchVO);
	}
	
	public int saveFavoriteMenu(String menuId, String userId, String userGroupId){
		return siteMapMapper.saveFavoriteMenu(menuId, userId, userGroupId);
	}
	
	public int deleteFavoriteMenu(String userId, String userGroupId){
		return siteMapMapper.deleteFavoriteMenu(userId, userGroupId);
	}
}