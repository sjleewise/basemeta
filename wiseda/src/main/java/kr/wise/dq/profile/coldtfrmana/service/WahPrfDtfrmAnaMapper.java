package kr.wise.dq.profile.coldtfrmana.service;

import java.util.Date;
import kr.wise.dq.profile.coldtfrmana.service.WahPrfDtfrmAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfDtfrmAnaMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int insert(WahPrfDtfrmAnaVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfDtfrmAnaVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfDtfrmAnaVO record);

    int updateByPrimaryKey(WahPrfDtfrmAnaVO record);
}