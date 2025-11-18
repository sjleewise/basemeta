package kr.wise.meta.stts.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.portal.dashboard.service.StandardChartVO;

@Mapper
public interface StndSttsMapper {

	List<StandardChartVO> selectSttsStandardChart();
	
	WaaBscLvl selectByPrimaryKey(@Param("bscId") String bscId);
	
	List<WaaSubj> selectMetaStsList(WaaSubj search);
}
