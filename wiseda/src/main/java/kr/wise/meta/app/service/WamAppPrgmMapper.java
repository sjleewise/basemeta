package kr.wise.meta.app.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamAppPrgmMapper {
    int deleteByPrimaryKey(String appPrgmId);

    int insert(WamAppPrgm record);

    int insertSelective(WamAppPrgm record);

    int updateByPrimaryKeySelective(WamAppPrgm record);

    int updateByPrimaryKey(WamAppPrgm record);

	List<WamAppPrgm> selectList(WamAppPrgm data);

	WamAppPrgm selectAppPrgmDetail(String appPrgmId);

	List<WamAppPrgm> selectAppPrgmChangeList(String appPrgmId);

}