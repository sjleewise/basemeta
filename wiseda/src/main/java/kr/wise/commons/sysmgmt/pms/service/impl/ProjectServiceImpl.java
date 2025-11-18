package kr.wise.commons.sysmgmt.pms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.pms.service.ProjectMapper;
import kr.wise.commons.sysmgmt.pms.service.ProjectService;
import kr.wise.commons.sysmgmt.pms.service.ProjectVO;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	private final  Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ProjectMapper projectMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 

	public List<ProjectVO> selectProjectList(ProjectVO searchVO) {
		return projectMapper.selectProjectList(searchVO);
	}

	public ProjectVO selectProjectDetail(ProjectVO searchVO) {
		return projectMapper.selectProjectDetail(searchVO.getPrjId());
	}



	public int deleteProject(ProjectVO delVO) {
		
		log.debug("delVO:{}", delVO);
		
		String[] ids = delVO.getPrjId().split(",");
		
		delVO.setPrjIds(ids);

		return projectMapper.deleteProject(delVO);
	}

	@Override
	public int regProject(ArrayList<ProjectVO> list) throws Exception {
		int result = 0;
		for (ProjectVO projectVO : list) {
			result += saveProject(projectVO);
		}

		return result;
	}

	@Override
	public int saveProject(ProjectVO record) throws Exception {
		log.debug("{}", record);
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;



		//프로젝트ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(!StringUtils.hasText(record.getPrjId())) {  // 신규...


			record.setPrjId(objectIdGnrService.getNextStringId());
			record.setOrglType("C");
			record.setOrglUser(user.getUniqId());
			record.setUpdtUser(user.getUniqId());
			result = projectMapper.insertSelective(record);
			
		} else if(StringUtils.hasText(record.getPrjId())) {
			//프로젝트ID 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			ProjectVO tmpVO = projectMapper.selectProjectDetail(record.getPrjId());
			if (tmpVO == null || !tmpVO.getPrjId().equals(record.getPrjId())) {
				record.setPrjId(objectIdGnrService.getNextStringId());
				record.setOrglType("C");
				record.setOrglUser(user.getUniqId());
				record.setUpdtUser(user.getUniqId());
				result = projectMapper.insertSelective(record);
			} else {
//				record.setOrglType("U");
				record.setUpdtUser(user.getUniqId());
				result = projectMapper.updateProject(record);
			}

		}


		
		
		return result;
	}

	@Override
	public int deleteProject(ArrayList<ProjectVO> list) {
		int result = 0;
		for (ProjectVO projectVO : list) {
			String id = projectVO.getPrjId();
			if (id != null && !"".equals(id)) {
				result += projectMapper.deleteProject(projectVO);
			}
		}
		return result;
	}

}
