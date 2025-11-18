package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WamErrMsgMapMapper {
    List<WamErrMsgMap> selectList(WamErrMsgMap record);
    
    WamErrMsgMap selectMessageDtl(String msgId);
    
    List<WamErrMsgMap> selectChangeList(String msgId);
}