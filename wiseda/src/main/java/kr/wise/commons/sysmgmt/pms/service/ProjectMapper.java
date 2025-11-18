package kr.wise.commons.sysmgmt.pms.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface ProjectMapper {
    int insert(ProjectVO record);

    int insertSelective(ProjectVO record);

	List<ProjectVO> selectProjectList(ProjectVO searchVO);

	ProjectVO selectProjectDetail(String prjId);

	int insertProject(ProjectVO saveVO);

	int updateProject(ProjectVO saveVO);

	int deleteProject(ProjectVO delVO);
}