package kr.wise.admin.ai.algorithm.service;

import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaaAlgArgMapper {
    int deleteByPrimaryKey(@Param("algId") String algId, @Param("algArgId") String algArgId);

    int insert(WaaAlgArg record);

    int insertSelective(WaaAlgArg record);

    WaaAlgArg selectByPrimaryKey(@Param("algId") String algId, @Param("algArgId") String algArgId);

    int updateByPrimaryKeySelective(WaaAlgArg record);

    int updateByPrimaryKey(WaaAlgArg record);

	/** @param search
	/** @return insomnia */
	List<WaaAlgArg> selectArgListbyId(WaaAlg search);

	/** @param algId insomnia */
	int deleteByalgId(String algId);

	/** @param search
	/** @return insomnia */
	List<WaaAlgArg> selectArgListbyDtcId(WadOtlResult search);
}