package kr.wise.dq.profile.tblunq.service;

import java.util.Date;
import kr.wise.dq.profile.tblunq.service.WahPrfUnqColVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfUnqColMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("dbcColNm") String dbcColNm, @Param("expDtm") Date expDtm);

    int insert(WahPrfUnqColVO record);

    int insertSelective(WamPrfUnqColVO record);

    WahPrfUnqColVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("dbcColNm") String dbcColNm, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfUnqColVO record);

    int updateByPrimaryKey(WahPrfUnqColVO record);
}