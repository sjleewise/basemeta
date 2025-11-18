package kr.wise.commons.damgmt.db.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDbMapMapper {
    int deleteByPrimaryKey(WaaDbMap record);

    int insertSelective(WaaDbMap record);

    List<WaaDbMap> selectByPrimaryKey(WaaDbMap record);
    
    int updateExpDtm(WaaDbMap record);

	/** @param search
	/** @return insomnia */
	List<WaaDbMap> selectDbMapList(WaaDbMap search);
	
	WaaDbMap selectDbMapDefaultOne(WaaDbMap search);
}