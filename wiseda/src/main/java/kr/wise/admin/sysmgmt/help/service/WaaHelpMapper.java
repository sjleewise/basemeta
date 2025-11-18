package kr.wise.admin.sysmgmt.help.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface WaaHelpMapper {
    int deleteByPrimaryKey(String helpId);

    int insert(WaaHelp record);

    int insertSelective(WaaHelp record);

    WaaHelp selectByPrimaryKey(String helpId);

    int updateByPrimaryKeySelective(WaaHelp record);

    int updateByPrimaryKeyWithBLOBs(WaaHelp record);

    int updateByPrimaryKey(WaaHelp record);

	List<WaaHelpVO> selectAllHelpMstrList(WaaHelpVO vo);

	WaaHelpVO selectHelpMasterInf(WaaHelp vo);
	
	int insertHelpMastetInf(WaaHelpVO saveVO);

	int updateHelpMastetInf(WaaHelpVO saveVO);
	
	int updateHelpId(WaaHelpVO saveVO);
	
	int updateHelpIdNull(WaaHelpVO saveVO);

	int deleteHelp(String[] ids);

	int updateHelpIdReset(String[] ids);

	WaaHelpVO selectHelpCtnt(String helpId);
}