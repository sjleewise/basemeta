package kr.wise.commons.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.user.service.WaaUsers;
import kr.wise.commons.user.service.WaaUsersMapper;
import kr.wise.commons.user.service.WaaUsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("waaUsersService")
public class WaaUsersServiceImpl implements WaaUsersService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaUsersMapper	waaUsersMapper;

	public int deleteByPrimaryKey(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(WaaUsers record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insertSelective(WaaUsers record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public WaaUsers selectByPrimaryKey(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public int updateByPrimaryKeySelective(WaaUsers record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKey(WaaUsers record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<WaaUsers> selectUserList(Map<String, Object> param) {

		return waaUsersMapper.selectUserList(param);
	}
	
	public int selectTotCountList(Map<String, Object> param) {
		
		return waaUsersMapper.selectTotCountList(param);
	}

	public List<WaaUsers> selectUserList2(Map<String, Object> param) {
		return waaUsersMapper.selectUserList2(param);
	}

}
