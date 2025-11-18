package kr.wise.meta.stts.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.meta.stts.service.StndSttsMapper;
import kr.wise.meta.stts.service.StndSttsService;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.portal.dashboard.service.StandardChartVO;


@Service("StndSttsService")
public class StndSttsServiceImpl implements StndSttsService {


	@Inject
	private StndSttsMapper stndSttsMapper;
	
	
	@Override
	public List<StandardChartVO> selectSttsStandardChart() {
		return stndSttsMapper.selectSttsStandardChart();
	}
	
	@Override
	public WaaBscLvl selectStsInfoList(String bscId) throws Exception {
				
		WaaBscLvl data = stndSttsMapper.selectByPrimaryKey(bscId);
		
		return data; 
	}
	
	@Override
	public List<WaaSubj> getMetaStsSubjList(WaaSubj search) {
		return stndSttsMapper.selectMetaStsList(search);
	}
	
}
