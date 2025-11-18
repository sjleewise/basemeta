package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDmngInfotpMapMapper {
    int deleteByPrimaryKey(@Param("dmngId") String dmngId, @Param("infotpId") String infotpId);

    int insert(WaaDmngInfotpMap record);

    int insertSelective(WaaDmngInfotpMap record);

    WaaDmngInfotpMap selectByPrimaryKey(@Param("dmngId") String dmngId, @Param("infotpId") String infotpId);

    int updateByPrimaryKeySelective(WaaDmngInfotpMap record);

    int updateByPrimaryKey(WaaDmngInfotpMap record);
    
    List<WaaDmngInfotpMap> selectList(WaaDmngInfotpMap record); 
    
    int updateExpDtm(WaaDmngInfotpMap record);
}