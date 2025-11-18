package kr.wise.advisor.prepare.dqiquality.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqiQltyMapper {

	List<DqiQltyVo> selectDqiQltyList(DqiQltyVo search);

	List<DqiQltyVo> selectDqiQltyBar(DqiQltyVo search);

	List<DqiQltyVo> selectDqiQltyPie(DqiQltyVo search);

}
