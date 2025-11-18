package kr.wise.meta.intf.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("EraScrnItmService")
public class EraScrnItmServiceImpl implements EraScrnItmService {

	
	@Inject
	private EraScrnItmMapper eraScrnItmMapper;
	
	@Override
	public List<EraScrnItm> getEraScrnItmOne(String vcb, String langCode) {
		
		List<EraScrnItm> list = new ArrayList<EraScrnItm>();
		
		list = eraScrnItmMapper.selectEraScrnItmOne(vcb, langCode);
		
		return list;
	}


	@Override
	public List<EraScrnItm> getEraScrnItmList(List<EraScrnItm> data, String langCode) {
		
		List<EraScrnItm> list = new ArrayList<EraScrnItm>();
		System.out.println(langCode);
		for(EraScrnItm row : data){
			System.out.println(row.getVcb());
			System.out.println(row.getVcbId());
			list.addAll(eraScrnItmMapper.selectEraScrnItm(row.getVcb(), row.getVcbId(),	langCode));
		}
		
		return list;
	}

}
