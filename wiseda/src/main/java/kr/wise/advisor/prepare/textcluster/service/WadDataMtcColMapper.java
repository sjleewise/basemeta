package kr.wise.advisor.prepare.textcluster.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WadDataMtcColMapper {
    int deleteByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcColSno") Integer mtcColSno);

    int insert(WadDataMtcCol record);

    int insertSelective(WadDataMtcCol record);

    WadDataMtcCol selectByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcColSno") Integer mtcColSno);

    int updateByPrimaryKeySelective(WadDataMtcCol record);

    int updateByPrimaryKey(WadDataMtcCol record);

	/** @param mtcId
	/** @return insomnia */
	List<WadDataMtcCol> selectColList(String mtcId);

	int deleteByMtcId(String mtcId); 
}