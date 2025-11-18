package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatDbcGapTblMapper {

	/** @param search
	/** @return yeonho */
	List<WatDbcGapTbl> getDbGapList(WatDbcGapTbl search);
}