package kr.wise.dq.profile.tblrel.service;

import java.util.Date;
import kr.wise.dq.profile.tblrel.service.WahPrfRelColVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfRelColMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno, @Param("expDtm") Date expDtm);

    int insert(WahPrfRelColVO record);

    int insertSelective(WamPrfRelColVO record);

    WahPrfRelColVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfRelColVO record);

    int updateByPrimaryKey(WahPrfRelColVO record);
    
    //이력테이블 만료
    int updateWahPrf(WamPrfRelColVO record);
    
}