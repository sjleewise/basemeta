package kr.wise.admin.ai.algorithm.service;

import java.util.List;

import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaAlgMapper {
    int deleteByPrimaryKey(String algId);

    int insert(WaaAlg record);

    int insertSelective(WaaAlg record);

    WaaAlg selectByPrimaryKey(String algId);

    int updateByPrimaryKeySelective(WaaAlg record);

    int updateByPrimaryKey(WaaAlg record);

	/** @param search
	/** @return insomnia */
	List<WaaAlg> selectAlgorithmList(WaaAlg search);
}