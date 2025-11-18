package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.stnd.service.WamActnMsg;
@Mapper
public interface WamActnMsgMapper {
    List<WamActnMsg> selectList(WamActnMsg record);
    
    WamActnMsg selectMessageDtl(String msgId);
    
    List<WamActnMsg> selectChangeList(String msgId);
}