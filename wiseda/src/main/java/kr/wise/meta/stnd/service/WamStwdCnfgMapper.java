package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamStwdCnfgMapper {
    int deleteByPrimaryKey(@Param("objId") String objId, @Param("wdSno") Integer wdSno);

    int insert(WamStwdCnfg record);

    int insertSelective(WamStwdCnfg record);

    WamStwdCnfg selectByPrimaryKey(@Param("objId") String objId, @Param("wdSno") Integer wdSno);

    int updateByPrimaryKeySelective(WamStwdCnfg record);

    int updateByPrimaryKey(WamStwdCnfg record);
    
    List<WamStwdCnfg> selectDmnInitList(@Param("dmnId") String dmnId);
    
    List<WamStwdCnfg> selectSditmInitList(@Param("sditmId") String sditmId);
}