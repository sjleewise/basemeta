package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.meta.gap.service.WatDbcGapIdxCol;

@Mapper
public interface WatDbcGapIdxColMapper {
    
    List<WatDbcGapIdxCol> getGapIdxColSrcList(WatDbcGapIdx search);
    
    List<WatDbcGapIdxCol> getGapIdxColTgtList(WatDbcGapIdx search);
}