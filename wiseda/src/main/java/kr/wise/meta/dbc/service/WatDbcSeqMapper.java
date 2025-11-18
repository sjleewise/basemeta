package kr.wise.meta.dbc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatDbcSeqMapper {
    int deleteByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcSeqNm") String dbcSeqNm);

    int insert(WatDbcSeq record);

    int insertSelective(WatDbcSeq record);

    WatDbcSeq selectByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcSeqNm") String dbcSeqNm);

    int updateByPrimaryKeySelective(WatDbcSeq record);

    int updateByPrimaryKey(WatDbcSeq record);
    
	List<WatDbcSeq> selectList(WatDbcSeq search);
}