package kr.wise.dq.impv.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCsAnaMstrMapper {
    int deleteByPrimaryKey(@Param("anaJobId") String anaJobId, @Param("anaStrDtm") Date anaStrDtm);

    int insert(WamCsAnaMstr record);

    int insertSelective(WamCsAnaMstr record);

    WamCsAnaMstr selectByPrimaryKey(@Param("anaJobId") String anaJobId, @Param("anaStrDtm") Date anaStrDtm);

    int updateByPrimaryKeySelective(WamCsAnaMstr record);

    int updateByPrimaryKey(WamCsAnaMstr record);
}