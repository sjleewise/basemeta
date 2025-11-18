package kr.wise.meta.stnd.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.stnd.service.CommStndMapper;
import kr.wise.meta.stnd.service.CommStndService;

@Service("commStndService")
public class CommStndServiceImpl implements CommStndService{
	
	@Inject
	CommStndMapper commStndMapper;

	@Override
	public List<HashMap<String, String>> getCommSditmList(HashMap<String, String> search) throws Exception {
		// TODO Auto-generated method stub
		return commStndMapper.selectCommSditmList(search);
	}

	@Override
	public List<HashMap<String, String>> getCommDmnList(HashMap<String, String> search)  throws Exception {
		// TODO Auto-generated method stub
		return commStndMapper.selectCommDmnList(search);
	}

	@Override
	public List<HashMap<String, String>> getCommStwdList(HashMap<String, String> search)  throws Exception {
		// TODO Auto-generated method stub
		return commStndMapper.selectCommStwdList(search);
	}

}
