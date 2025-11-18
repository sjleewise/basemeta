package kr.wise.commons.sysmgmt.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapVo;
import kr.wise.commons.sysmgmt.menu.service.WaaUsergMenuAuth;
import kr.wise.commons.sysmgmt.menu.service.WaaUsergMenuAuthMapper;
import kr.wise.commons.sysmgmt.menu.service.WaaUsergMenuAuthVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("UsergMenuMapService")
public class UsergMenuMapServiceImpl implements UsergMenuMapService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, UsergMenuMapVo> menumap;
	
	private Map<String, WaaUsergMenuAuth> menulist;
	
	private Map<String, WaaUsergMenuAuthVO> topmenumap;

	private Map<String, WaaUsergMenuAuthVO> submenumap;

	@Inject
	WaaUsergMenuAuthMapper waaUsergMenuAuthMapper;
	
	@Value("#{configure['wiseda.langDcd']}")     
	private String langDcd;

	public List<WaaUsergMenuAuth> selectMenuList(WaaUsergMenuAuth search) {
		return waaUsergMenuAuthMapper.getMenuWithUserg(search);
	}



	private int regMenuAuth(WaaUsergMenuAuth record) {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		//기존 데이터를 모두 지우고 신규로 등록한다. 모두 'C', OBJ_VERS 1로 설정...
		record.setRegTypCd("C");
		record.setObjVers(1);

		record.setWritUserId(user.getUniqId());
		result = waaUsergMenuAuthMapper.insertSelective(record);
		return result;

	}

	public int menuReglist(ArrayList<WaaUsergMenuAuth> list, String usergId) {
		int result = 0;


		logger.debug("usergId : {}", usergId);

		//사용자그룹에 대한 모든 메뉴권한 정보를 삭제한다...
		if(StringUtils.hasText(usergId)) {
			waaUsergMenuAuthMapper.deleteByUsergId(usergId);
		}

		for (WaaUsergMenuAuth record : list) {
			// 체크 상태(권한부여)인것만 저장한다...
			if (record.getIbsCheck().equals("1")) {

				//사용자그룹 ID 매핑...
				record.setUsergId(usergId);
				result += regMenuAuth(record);
			}
		}
		
		//메뉴 메모리 재갱신 
		this.init();
		
		return result;
	}



	/** insomnia */
	public List<WaaUsergMenuAuth> getTopMenuList(String servletPath) {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String usergId = user.getUsergId();

		WaaUsergMenuAuth searchvo = new WaaUsergMenuAuth();
		searchvo.setUsergId(usergId);
		searchvo.setFilePath(servletPath);

		return waaUsergMenuAuthMapper.selectTopMenuList(searchvo);

	}



	/** insomnia */
	@Override
	public Map<String, Object> getMenuMap(String servletPath) {
		List<WaaUsergMenuAuth> toplist ;
		List<WaaUsergMenuAuth> sublist ;
		Map<String, Object> map = new HashMap<String, Object>();

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String usergId = user.getUsergId();

		WaaUsergMenuAuth searchvo = new WaaUsergMenuAuth();
		searchvo.setUsergId(usergId);
		searchvo.setFilePath(servletPath);

		WaaUsergMenuAuth reqmenu = waaUsergMenuAuthMapper.selectRequestMenu(searchvo);

		if(reqmenu != null) {
			map.put("REQ_MENU", reqmenu);
			//탑메뉴 리스트를 가져온다.
			searchvo.setMenuDcd(reqmenu.getMenuDcd());
			toplist = waaUsergMenuAuthMapper.selectTopMenuList(searchvo);
			map.put("TOP_MENU", toplist);

//			logger.error("reqmenu.getRootMenuId() ::  "+reqmenu.getRootMenuId());
			//서브메뉴 리스트를 가져온다.
			searchvo.setRootMenuId(reqmenu.getRootMenuId());
			sublist = waaUsergMenuAuthMapper.selectSubMenuList(searchvo);
			map.put("SUB_MENU", sublist);
		}

		return map;
	}

	
	public Map<String, Object> getMenuMap2(String servletPath) {
		List<WaaUsergMenuAuth> toplist = null;
		List<WaaUsergMenuAuth> sublist ;
		Map<String, Object> map = new HashMap<String, Object>();
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String usergId = user.getUsergId();

		//1. 메뉴가 존재하는지 체크한다.
		if (this.menulist.containsKey(servletPath)) {
			WaaUsergMenuAuth reqmenu = this.menulist.get(servletPath);
			String menudcd = reqmenu.getMenuDcd();
			String rootid = reqmenu.getRootMenuId();
			
			//2. 해당 메뉴에 권한이 있는지 체크한다. 
			if ( !"dir".equals(reqmenu.getFileNm()) && !"link".equals(reqmenu.getFileNm()) && 
					this.menumap.containsKey(usergId+"|"+reqmenu.getMenuId())) {
				map.put("ACCEPTYN", "Y");
				map.put("REQ_MENU", reqmenu);
				//탑메뉴 리스트를 가져온다.
				if (this.topmenumap.containsKey(usergId+"|"+menudcd)){
					toplist = this.topmenumap.get(usergId+"|"+menudcd).getMenulist();
					map.put("TOP_MENU", toplist);
				}
				
				Map<String, Object> submap = new HashMap<String, Object>();
				if(toplist != null){
					for (WaaUsergMenuAuth topvo : toplist) {
						rootid = topvo.getRootMenuId();
						List<WaaUsergMenuAuth> topsublist = new ArrayList<WaaUsergMenuAuth>() ;
						
						//메인메뉴별 서브메뉴 리스트를 가져온다.
						if (this.submenumap.containsKey(usergId+"|"+menudcd+"|"+rootid)) {
							topsublist = this.submenumap.get(usergId+"|"+menudcd+"|"+rootid).getMenulist();
						}
						submap.put(rootid, topsublist);
					}
				}
				
				map.put("SUB_MENU", submap);
				
			} else {
				//메뉴권한이 없슴
				map.put("ACCEPTYN", "N");
			}
		} else {
			//메뉴에 없는 경우는 일단 패스한다....
			map.put("ACCEPTYN", "Y");
		}

		return map;
	}


	
	@PostConstruct
	public void init() {
		logger.debug("menu init start");
		// confing file 셋팅 언어코드
		init_menulist();

		init_menumap();

	}
	


	private void init_menulist() {
		
		logger.debug("init menulist");
		
		if(this.menulist !=null ) this.menulist = null;
		
		List<WaaUsergMenuAuth> menulistall = waaUsergMenuAuthMapper.selectMenuList(langDcd);
		
		this.menulist = new HashMap<String, WaaUsergMenuAuth>();
		
		//메뉴전체를 맵에 담는다. 키-url 
		for (WaaUsergMenuAuth menuvo : menulistall) {
			this.menulist.put(menuvo.getFilePath(), menuvo);
		}
		
		logger.debug("menulist:{} ", this.menulist.size() );
	}



	private void init_menumap() {
		
		logger.debug("init menumap");
		
		if(menumap !=null ) menumap = null;
		
		//메뉴 전체 리스트를 매퍼를 통해 리스트에 담는다.
		List<UsergMenuMapVo> menuList = waaUsergMenuAuthMapper.selectMenuMapAll();
		
		//사용자 그룹별 메뉴 맵을 초기화 한다.
		menumap = new HashMap<String, UsergMenuMapVo>();

		//사용자그룹별 메뉴를 저장한다. key-usergid|menuid
		for (UsergMenuMapVo menumapvo : menuList) {
			menumap.put(menumapvo.getUsergId()+"|"+menumapvo.getMenuId(), menumapvo);
		}
		
		logger.debug("menumap:{} ", menumap.size() );
		
		init_topmenu(langDcd);
		
		init_submenu(langDcd);
	}
	
	
	private void init_submenu(String pLangDcd) {
		logger.debug("init submenu");
		if(this.submenumap !=null ) this.submenumap = null;
		
		//사용자 그룹별 서브메뉴리스트를 담는다. 키-usergid|menudcd|rootmenuid
		this.submenumap = new HashMap<String, WaaUsergMenuAuthVO>();
		List<WaaUsergMenuAuthVO> submenulist = waaUsergMenuAuthMapper.selectSubMenuListAll(pLangDcd);
		
		for (WaaUsergMenuAuthVO submenuvo : submenulist) {
			this.submenumap.put(submenuvo.getUsergId()+"|"+submenuvo.getMenuDcd()+"|"+submenuvo.getRootMenuId(), submenuvo);
		}
		
		logger.debug("submenumap:{} ", this.submenumap.size() );
	}



	private void init_topmenu(String pLangDcd) {
		logger.debug("init topmenu");
		if(topmenumap !=null ) topmenumap = null;
		
		//사용자 그룹별 탑메뉴리스트를 담는다 키-usergid|menudcd
		topmenumap = new HashMap<String, WaaUsergMenuAuthVO>();
		
		List<WaaUsergMenuAuthVO> topmenulist = waaUsergMenuAuthMapper.selectTopMenuListAll(pLangDcd);
		for (WaaUsergMenuAuthVO topmenuvo : topmenulist) {
			topmenumap.put(topmenuvo.getUsergId()+"|"+topmenuvo.getMenuDcd(), topmenuvo	);
		}
		
		logger.debug("topmenumap:{} ", topmenumap.size() );
	}	
	
	//언어별 menu init
	public void setLangDcdMenu(String pLangDcd) {
		logger.debug("setLangDcdMenu  init start");
		logger.debug("langDcd :: "+langDcd);
		logger.debug("pLangDcd :: "+pLangDcd);
		
		if(pLangDcd.isEmpty()){
			pLangDcd = langDcd;
		}
		
		init_menulistByLangDcd(pLangDcd);
		
		init_menumapByLangDcd(pLangDcd);
		
	}
	
	private void init_menulistByLangDcd(String pLangDcd) {
		
		logger.debug("init menulist");
		
		if(this.menulist !=null ) this.menulist = null;
		
		List<WaaUsergMenuAuth> menulistall = waaUsergMenuAuthMapper.selectMenuList(pLangDcd);
		
		this.menulist = new HashMap<String, WaaUsergMenuAuth>();
		
		//메뉴전체를 맵에 담는다. 키-url 
		for (WaaUsergMenuAuth menuvo : menulistall) {
			this.menulist.put(menuvo.getFilePath(), menuvo);
		}
		
		logger.debug("menulist:{} ", this.menulist.size() );
	}
	
	private void init_menumapByLangDcd(String pLangDcd) {
		
		logger.debug("init menumap");
		
		if(menumap !=null ) menumap = null;
		
		//메뉴 전체 리스트를 매퍼를 통해 리스트에 담는다.
		List<UsergMenuMapVo> menuList = waaUsergMenuAuthMapper.selectMenuMapAll();
		
		//사용자 그룹별 메뉴 맵을 초기화 한다.
		menumap = new HashMap<String, UsergMenuMapVo>();

		//사용자그룹별 메뉴를 저장한다. key-usergid|menuid
		for (UsergMenuMapVo menumapvo : menuList) {
			menumap.put(menumapvo.getUsergId()+"|"+menumapvo.getMenuId(), menumapvo);
		}
		
		logger.debug("menumap:{} ", menumap.size() );
		
		init_topmenu(pLangDcd);
		
		init_submenu(pLangDcd);
	}
	
	
}
