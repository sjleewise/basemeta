package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.stnd.service.WamStdErrMsg;

@Mapper
public interface WamStdErrMsgMapper {
    List<WamStdErrMsg> selectList(WamStdErrMsg record);
    
    WamStdErrMsg selectMessageDtl(String msgId);
    
    List<WamStdErrMsg> selectChangeList(String msgId);
}