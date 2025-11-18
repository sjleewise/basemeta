package kr.wise.meta.model.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WamAsisDmnCdValMapper {
    List<WamAsisDmnCdVal> selectList(WamAsisDmnCdVal record);
    
    WamAsisDmnCdVal selectDtl(String dmnId);
    
    List<WamAsisDmnCdVal> selectChangeList(String dmnId);
}