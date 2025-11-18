package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmCdValMapper {
    int deleteByPrimaryKey(String cdValId);

    int insert(WdqmCdVal record);

    int insertSelective(WdqmCdVal record);

    WdqmCdVal selectByPrimaryKey(String cdValId);

    int updateByPrimaryKeySelective(WdqmCdVal record);

    int updateByPrimaryKey(WdqmCdVal record);
    
    List<WdqmDmn> selectDmnValueList(@Param("dmnId") String dmnId);
    
    List<WdqmCdVal> selectDmnValueListMsgPop(WdqmCdVal record);
    
    List<WdqmDmn> selectDmnValueChangeList(@Param("dmnId") String dmnId);
    
    List<WdqmCdVal> selectSimpleCodeLst(WdqmCdVal record);
    
    List<WdqmCdVal> selectComplexCodeLst(WdqmCdVal record);
}