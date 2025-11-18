package kr.wise.commons.sysmgmt.menu.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaUsergMenuAuthMapper {
    int deleteByPrimaryKey(@Param("menuId") String menuId, @Param("usergId") String usergId);

    int insert(WaaUsergMenuAuth record);

    int insertSelective(MenuManageVO record);

    WaaUsergMenuAuth selectByPrimaryKey(@Param("menuId") String menuId, @Param("usergId") String usergId);

    int updateByPrimaryKeySelective(WaaUsergMenuAuth record);

    int updateByPrimaryKey(WaaUsergMenuAuth record);

	List<WaaUsergMenuAuth> getMenuWithUserg(WaaUsergMenuAuth search);

	void deleteByUsergId(@Param("usergId") String usergId);

	/** @param searchvo
	/** @return insomnia */
	List<WaaUsergMenuAuth> selectTopMenuList(WaaUsergMenuAuth searchvo);

	/** @param searchvo
	/** @return insomnia */
	WaaUsergMenuAuth selectRequestMenu(WaaUsergMenuAuth searchvo);

	/** @param searchvo
	/** @return insomnia */
	List<WaaUsergMenuAuth> selectSubMenuList(WaaUsergMenuAuth searchvo);

	List<WaaUsergMenuAuthVO> selectSubMenuListAll(@Param("langDcd") String langDcd);
	
	List<WaaUsergMenuAuthVO> selectTopMenuListAll(@Param("langDcd") String langDcd);
	
	//
	List<WaaUsergMenuAuth> selectMenuList(@Param("langDcd") String langDcd);
	
	List<UsergMenuMapVo> selectMenuMapAll();
}