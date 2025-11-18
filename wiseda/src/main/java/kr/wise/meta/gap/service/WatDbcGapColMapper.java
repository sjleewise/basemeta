package kr.wise.meta.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatDbcGapColMapper {

	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapColList(WatDbcGapCol search);

	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapSrcList(WatDbcGapCol search);

	/** @param search
	/** @return yeonho */
	List<WatDbcGapCol> getGapTgtList(WatDbcGapCol search);
}