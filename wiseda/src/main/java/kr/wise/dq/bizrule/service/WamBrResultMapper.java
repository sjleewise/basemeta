package kr.wise.dq.bizrule.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamBrResultMapper {
    int deleteByPrimaryKey(@Param("brId") String brId, @Param("anaStrDtm") Date anaStrDtm);

    int insert(WamBrResult record);

    int insertSelective(WamBrResult record);

    WamBrResult selectByPrimaryKey(@Param("brId") String brId, @Param("anaStrDtm") Date anaStrDtm);

    int updateByPrimaryKeySelective(WamBrResult record);

    int updateByPrimaryKey(WamBrResult record);
}