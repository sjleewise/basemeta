package kr.wise.portal.dashboard.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ProgramDashMapper {

	List<ProgramCountDashProcVO> getProcAssist()throws Exception;
	
	List<ProgramSourceUpdateProcVO> getProcAssistUpdate(Map<String, String> map)throws Exception;

	List<ProgramDocumentCountProcVO> getProgramProcAssistRequest(Map<String, String> map)throws Exception;

}
