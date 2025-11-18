package kr.wise.commons.sysmgmt.log.connlog.service;

import java.util.List;

import kr.wise.commons.stat.service.StatsVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface LoginLogMapper {

	void logInsertLoginLog(LoginLog loinLog);

	LoginLog selectLoginLog(LoginLog loginLog);

	List selectLoginLogInf(LoginLog loinLog);

	int selectLoginLogInfCnt(LoginLog loinLog);

	List<LoginLog> selectLoginLogList(LoginLog searchVO);

	List<StatsVO> selectConnStatList(LoginLog searchVO);

	List<StatsVO> selectConnStatTot(LoginLog searchVO);

}
