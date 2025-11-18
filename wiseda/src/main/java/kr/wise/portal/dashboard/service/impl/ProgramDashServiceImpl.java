package kr.wise.portal.dashboard.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.dashboard.service.ProgramCountDashProcVO;
import kr.wise.portal.dashboard.service.ProgramDashMapper;
import kr.wise.portal.dashboard.service.ProgramDashService;
import kr.wise.portal.dashboard.service.ProgramDocumentCountProcVO;
import kr.wise.portal.dashboard.service.ProgramSourceUpdateProcVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("programDashService")
@Transactional
public class ProgramDashServiceImpl implements ProgramDashService {
    
	@Inject
	ProgramDashMapper programDashMapper;

	//데이터 리스트 
	public List<ProgramCountDashProcVO> getProcAssist()throws Exception {
		
		return programDashMapper.getProcAssist();
	}

	public List<ProgramSourceUpdateProcVO> getProcAssistUpdate(Map<String, String> map)throws Exception {
		
		return programDashMapper.getProcAssistUpdate(map);
	}

	public List<ProgramDocumentCountProcVO> getProgramProcAssistRequest(Map<String, String> map)throws Exception {
		
		return programDashMapper.getProgramProcAssistRequest(map);
	}

	
	
}
