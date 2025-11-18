package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.meta.gap.service.WatDbcGapIdx;

@Mapper
public interface WatDbcGapIdxMapper {

    List<WatDbcGapIdx> getGapIdxList(WatDbcGapIdx search);

}