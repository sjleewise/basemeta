package kr.wise.dq.measure.profile.service;

import java.util.List;

import kr.wise.dq.dashboard.service.WidgetsChartVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqPrfMstMapper {

	List<WidgetsChartVO> selectWidgetsChart() throws Exception;
}
