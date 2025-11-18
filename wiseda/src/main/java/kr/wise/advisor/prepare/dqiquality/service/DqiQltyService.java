package kr.wise.advisor.prepare.dqiquality.service;

import java.util.List;

public interface DqiQltyService {

	List<DqiQltyVo> getDqiQltyList(DqiQltyVo search);

	List<DqiQltyVo> getDqiQltyBar(DqiQltyVo search);

	List<DqiQltyVo> getDqiQltyPie(DqiQltyVo search);

}
