package kr.wise.commons.user.service;

import java.util.List;
import java.util.Map;

public interface WaaUsersService {
    public int deleteByPrimaryKey(String userId);

    public int insert(WaaUsers record);

    public int insertSelective(WaaUsers record);

    public WaaUsers selectByPrimaryKey(String userId);

    public int updateByPrimaryKeySelective(WaaUsers record);

    public int updateByPrimaryKey(WaaUsers record);
    
    public List<WaaUsers> selectUserList(Map<String, Object> param);

	public int selectTotCountList(Map<String, Object> param);

	public List<WaaUsers> selectUserList2(Map<String, Object> param); 
}