package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCdValMapper {
    int deleteByPrimaryKey(String cdValId);

    int insert(WamCdVal record);

    int insertSelective(WamCdVal record);

    WamCdVal selectByPrimaryKey(String cdValId);

    int updateByPrimaryKeySelective(WamCdVal record);

    int updateByPrimaryKey(WamCdVal record);
    
    List<WamDmn> selectDmnValueList(@Param("dmnId") String dmnId);
    
    List<WamCdVal> selectDmnValueListMsgPop(WamCdVal record);
    
    List<WamDmn> selectDmnValueChangeList(@Param("dmnId") String dmnId);
    
    List<WamCdVal> selectSimpleCodeLst(WamCdVal record);
    
    List<WamCdVal> selectComplexCodeLst(WamCdVal record);

	List<WamDmn> selectGovDmnValueList(@Param("dmnId") String dmnId);
}