package kr.wise.dq.result.yrdqireg.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface YrDqiRegMapper {
	
	List<YrDqiRegVO> selectList(YrDqiRegVO search);
	
	List<YrDqiRegVO> regList(YrDqiRegVO search);
	
	int insertSelective(YrDqiRegVO search);

	int deleteByPrimaryKeySelective(YrDqiRegVO delVo);
	
	int updateByPrimaryKeySelective(YrDqiRegVO search);
	
	int cntToUpdate(YrDqiRegVO search);
	
	int cntDup(YrDqiRegVO search);
}
