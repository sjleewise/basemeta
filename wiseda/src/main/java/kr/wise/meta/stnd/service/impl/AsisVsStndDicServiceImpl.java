package kr.wise.meta.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.stnd.service.AsisColItemMap;
import kr.wise.meta.stnd.service.AsisColItemMapMapper;
import kr.wise.meta.stnd.service.AsisVsStndDicService; 


@Service("asisVsStndDicService")
public class AsisVsStndDicServiceImpl implements AsisVsStndDicService {

	
	@Inject
	private AsisColItemMapMapper mapper;
	
	public List<AsisColItemMap> getAsisColvsItemList(AsisColItemMap data) {
		
		return mapper.selectAsisColvsItem(data);
	}

	@Override
	public List<AsisColItemMap> getAsisColvsItemListUseModel(AsisColItemMap data) {
		// TODO Auto-generated method stub
		return mapper.selectAsisColvsItemUseModel(data);
	}

}
