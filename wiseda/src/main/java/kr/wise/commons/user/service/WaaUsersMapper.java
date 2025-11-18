package kr.wise.commons.user.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaUsersMapper {
    int deleteByPrimaryKey(String userId);

    int insert(WaaUsers record);

    int insertSelective(WaaUsers record);

    WaaUsers selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(WaaUsers record);

    int updateByPrimaryKey(WaaUsers record);
    
    List<WaaUsers> selectUserList(Map<String, Object> param);

    int selectTotCountList(Map<String, Object> param);

	List<WaaUsers> selectUserList2(Map<String, Object> param); 
}