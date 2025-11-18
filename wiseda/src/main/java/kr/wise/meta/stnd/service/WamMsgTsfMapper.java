package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamMsgTsfMapper{
      
    List<WamMsgTsf> selectList(WamMsgTsf record);
    
    WamMsgTsf selectMessageTsfDtl(WamMsgTsf record);
    
    List<WamMsgTsf> selectChangeTsfList(WamMsgTsf record);
}