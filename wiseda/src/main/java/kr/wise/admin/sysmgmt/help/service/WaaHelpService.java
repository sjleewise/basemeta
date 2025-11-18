package kr.wise.admin.sysmgmt.help.service;

import java.util.List;

public interface WaaHelpService {

	List<WaaHelpVO> selectAllHelpMstrList(WaaHelpVO vo) throws Exception;

	public WaaHelpVO selectHelpMasterInf(WaaHelp help) throws Exception;

	public int saveHelp(WaaHelpVO saveVO, String saction) throws Exception;
	
	int delHelp(WaaHelpVO delVO) throws Exception;

	WaaHelpVO selectHelpCtnt(String helpId);

}
