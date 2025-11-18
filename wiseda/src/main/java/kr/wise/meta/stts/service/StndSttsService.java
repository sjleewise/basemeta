package kr.wise.meta.stts.service;

import java.util.List;

import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.portal.dashboard.service.StandardChartVO;

public interface StndSttsService {

	public List<StandardChartVO> selectSttsStandardChart();
	
	WaaBscLvl selectStsInfoList(String bscId)
			throws Exception;
	
	public List<WaaSubj> getMetaStsSubjList(WaaSubj search);
	
}
