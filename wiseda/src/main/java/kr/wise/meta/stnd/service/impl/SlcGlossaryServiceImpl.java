package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.UserDetailsService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.meta.stnd.service.SlcGlossaryM;
import kr.wise.meta.stnd.service.SlcGlossaryMMapper;
import kr.wise.meta.stnd.service.SlcGlossaryService;

@Service("slcGlossaryService")
public class SlcGlossaryServiceImpl implements SlcGlossaryService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private SlcGlossaryMMapper mapper;
	
	
	public List<SlcGlossaryM> getSlcGlossaryList(SlcGlossaryM data) {
		
		return mapper.selectSlcGlossaryList(data);
	}

	public List<SlcGlossaryM> getSlcGlossaryChangeList(String stwdId) {
		// TODO Auto-generated method stub
		return null;
	}

	public SlcGlossaryM getSlcGlossaryDetail(SlcGlossaryM searchVO) {
		
		return mapper.selectByPrimaryKey(searchVO.getSlcGlossaryNm());
	}

	public int regSlcGlossary(ArrayList<SlcGlossaryM> list) {
		int result = 0;
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		for (SlcGlossaryM saveVO : list) {
			result += saveSlcGlossary(saveVO, user);
		}
		
		return result;
	}

	private int saveSlcGlossary(SlcGlossaryM saveVO, LoginVO user) {
		int result = 0;
		
		SlcGlossaryM selVO =  mapper.selectByPrimaryKey(saveVO.getSlcGlossaryNm());
		
		if (selVO != null && saveVO.getSlcGlossaryNm().equals(selVO.getSlcGlossaryNm())) {
			saveVO.setIbsStatus("U");
			saveVO.setUpderId(user.getUniqId());
			result = mapper.updateByPrimaryKeySelective(saveVO);
			
		} else {
			saveVO.setIbsStatus("I");
			saveVO.setRegstpsnId(user.getUniqId());
			result = mapper.insertSelective(saveVO);
		}
		
		return result;
	}

	public int delSlcGlossary(ArrayList<SlcGlossaryM> list) throws Exception {
		int result = 0;
		
		result = mapper.deleteList(list);
		
		return result;
	}

	public List getSlcGlossaryListXls(SlcGlossaryM data) throws Exception {
		
		return mapper.selectSlcGlossaryListXls(data);
	}

}
