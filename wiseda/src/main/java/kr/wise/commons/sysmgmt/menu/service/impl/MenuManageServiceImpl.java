package kr.wise.commons.sysmgmt.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.menu.service.MenuManageMapper;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.menu.service.MenuManageVO;
import kr.wise.commons.sysmgmt.menu.service.MenuStatVO;
import kr.wise.commons.sysmgmt.program.service.ProgrmManageMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("menuManageService")
public class MenuManageServiceImpl implements MenuManageService{
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private MenuManageMapper menuMapper;
	
	@Inject
	private ProgrmManageMapper progrmMapper;

	public List<MenuManageVO> selectMenuList(MenuManageVO searchVO) {
		return menuMapper.selectMenuList(searchVO);
	}

	public MenuManageVO selectMenuDetail(String menuId) {
		return menuMapper.selectMenuDetail(menuId);
	}

	
	
	public int saveMenu(MenuManageVO record, String sAction) throws Exception {
		log.debug("MenuManageServiceImpl.Java saveMenu Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
//		//엑셀 업로드시 File명에 대한 경로를 찾아서 맵핑.
//		String tmpFileNm = record.getFileNm();
//		if(StringUtils.hasText(tmpFileNm)){
//			String tmpFilePath = progrmMapper.selectProgrmUrl(tmpFileNm);
//			if(StringUtils.hasText(tmpFilePath)){
//				record.setFilePath(tmpFilePath);
//			} else {
//				record.setFileNm(null);
//				record.setFilePath(null);
//			}
//		}
				
		//상위메뉴가 없을경우 최상위 메뉴레벨로 설정(0레벨)
		if(!StringUtils.hasText(record.getUppMenuId())) {
			record.setMenuLvl(0);
		} else {
		//상위메뉴명을 적었을경우, 상위메뉴의 레벨+1 처리
			MenuManageVO tmpVO = menuMapper.selectMenuDetail(record.getUppMenuId());
			if(tmpVO != null && tmpVO.getMenuId().equals(record.getUppMenuId())) {
				record.setMenuLvl(tmpVO.getMenuLvl() + 1);
			} else {
			//메뉴명이 서로 불일치할 경우 최상위메뉴레벨 설정
				record.setUppMenuId(null);
				record.setMenuLvl(0);
			}
		}
			
		
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getMenuId())) {  // 신규...
			
			
			record.setMenuId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if("U".equals(tmpStatus) || "D".equals(tmpStatus) || StringUtils.hasText(record.getMenuId())) {
			if ("I".equals(tmpStatus)) {
				record.setRegTypCd("U");
			} else {
				record.setRegTypCd(tmpStatus);
			}
			
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			//result = menuMapper.updateByPrimaryKeySelective(record);
		} 
		
		menuMapper.updateExpDtm(record);
		record.setWritUserId(user.getUniqId());
		
		result = menuMapper.insertMenu(record);
		
		menuMapper.updateFullPath(record.getMenuId());
		return result;
	}
	
	
	public int regMenu(ArrayList<MenuManageVO> list) throws Exception {
		
		
//		String tmpid = null;
		//=====================================
		int result = 0;
		for (MenuManageVO record : list) {
			
//			//레벨이 0이상이며, 상위ID가 없을경우, 이전 레코드의 ID를 셋팅한다.
//			if( UtilString.isBlank(record.getUppMenuId()) && record.getMenuLvl() > 0) {
//				record.setUppMenuId(tmpid);
//			}
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
								
				
				
				result += saveMenu(record, null);
			}
//			//해당 레코드의 주제영역ID를 임시로 저장한다...
//			tmpid = record.getMenuId();
			menuMapper.updateFullPath(record.getMenuId());
		}
		return result;
	}

	public List<MenuStatVO> selectMenuStatTot(MenuStatVO searchVO) {
		searchVO.setSearchBgnDe(StringUtils.replace(searchVO.getSearchBgnDe(), "-", ""));
		searchVO.setSearchEndDe(StringUtils.replace(searchVO.getSearchEndDe(), "-", ""));
		return menuMapper.selectMenuStatTot(searchVO);
	}

	public List<MenuStatVO> selectMenuStat(MenuStatVO searchVO) {
		searchVO.setSearchBgnDe(StringUtils.replace(searchVO.getSearchBgnDe(), "-", ""));
		searchVO.setSearchEndDe(StringUtils.replace(searchVO.getSearchEndDe(), "-", ""));
		return menuMapper.selectMenuStat(searchVO);
	}

	@Override
	public int deleteMenu(ArrayList<MenuManageVO> record) throws Exception {
		int result = 0;
		
		for (MenuManageVO MenuManageVO : record) {
			String id = MenuManageVO.getMenuId();
			if (id != null && !"".equals(id)) {
				MenuManageVO.setIbsStatus("D");
				result += menuMapper.deleteMenu(id);
			}
		}

		return result;
	}


	
	/*public int deleteMenu(ArrayList<MenuManageVO> list) throws Exception {
		int result = 0;
		for (MenuManageVO MenuManageVO : list) {
			int id = MenuManageVO.getMenuNo();
			if (true) {
				result += menuMapper.deleteByPrimaryKey(id);
			}
		}

		return result;
	}*/
}