package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.stnd.service.WamChnlErrMsg;
@Mapper
public interface WamChnlErrMsgMapper {
    List<WamChnlErrMsg> selectList(WamChnlErrMsg record);
    
    WamChnlErrMsg selectMessageDtl(String msgId);
    
    List<WamChnlErrMsg> selectChangeList(String msgId);
}