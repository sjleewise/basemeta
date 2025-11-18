package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmStwdCnfgMapper {
    int deleteByPrimaryKey(@Param("objId") String objId, @Param("wdSno") Integer wdSno);

    int insert(WdqmStwdCnfg record);

    int insertSelective(WdqmStwdCnfg record);

    WdqmStwdCnfg selectByPrimaryKey(@Param("objId") String objId, @Param("wdSno") Integer wdSno);

    int updateByPrimaryKeySelective(WdqmStwdCnfg record);

    int updateByPrimaryKey(WdqmStwdCnfg record);
    
    List<WdqmStwdCnfg> selectDmnInitList(@Param("dmnId") String dmnId);
    
    List<WdqmStwdCnfg> selectSditmInitList(@Param("sditmId") String sditmId);
}