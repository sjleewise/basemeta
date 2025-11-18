package kr.wise.dq.dashboard.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface PieChartMapper {

	List<PieChartVO> selectPieChart() throws Exception;

}
