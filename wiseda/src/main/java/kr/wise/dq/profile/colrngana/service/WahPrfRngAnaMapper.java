package kr.wise.dq.profile.colrngana.service;

import java.util.Date;
import kr.wise.dq.profile.colrngana.service.WahPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfRngAnaMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfRngAnaVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfRngAnaVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfRngAnaVO record);

    int updateByPrimaryKey(WahPrfRngAnaVO record);
}