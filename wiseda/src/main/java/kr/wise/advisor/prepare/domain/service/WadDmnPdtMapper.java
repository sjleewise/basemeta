package kr.wise.advisor.prepare.domain.service;

import kr.wise.advisor.prepare.domain.service.WadDmnPdt;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadDmnPdtMapper {
    int deleteByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("dmngLnm") String dmngLnm);

    int insert(WadDmnPdt record);

    int insertSelective(WadDmnPdt record);

    WadDmnPdt selectByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("dmngLnm") String dmngLnm);

    int updateByPrimaryKeySelective(WadDmnPdt record);

    int updateByPrimaryKey(WadDmnPdt record);

	/** @param search
	/** @return insomnia */
	List<WadDmnPdt> selectListbyId(WadDmnPdt search);

	/** @param anlVarId insomnia */
	int deleteByanlVarId(String anlVarId);
}