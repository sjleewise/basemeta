package kr.wise.commons.damgmt.approve.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaAprgUserMapper {
    int deleteByPrimaryKey(@Param("aprgId") String aprgId, @Param("userId") String userId);

    int insert(WaaAprgUser record);

    int insertSelective(WaaAprgUser record);

    WaaAprgUser selectByPrimaryKey(@Param("aprgId") String aprgId, @Param("userId") String userId);
    
    List<WaaAprgUser> selectListById(@Param("aprgId") String aprgId, @Param("userNm") String userNm);

    int updateByPrimaryKeySelective(WaaAprgUser record);

    int updateByPrimaryKey(WaaAprgUser record);
    
    int updateExpDtm(WaaAprgUser record);
}