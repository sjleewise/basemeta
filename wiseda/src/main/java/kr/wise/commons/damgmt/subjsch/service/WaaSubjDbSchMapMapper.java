package kr.wise.commons.damgmt.subjsch.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaSubjDbSchMapMapper {
    int deleteByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("subjId") String subjId, @Param("expDtm") Date expDtm);

    int insert(WaaSubjDbSchMap record);

    int insertSelective(WaaSubjDbSchMap record);

    WaaSubjDbSchMap selectByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("subjId") String subjId);

    int updateByPrimaryKeySelective(WaaSubjDbSchMap record);

    int updateByPrimaryKey(WaaSubjDbSchMap record);

	int updateExpDtm(WaaSubjDbSchMap record);

	List<WaaSubjDbSchMap> selectList(WaaSubjDbSchMap search);
}