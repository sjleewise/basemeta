package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamMsgMapper{
      
    List<WamMsg> selectList(WamMsg record);
    
    WamMsg selectMessageDtl(String msgId);
    
    List<WamMsg> selectChangeList(String msgId);
}