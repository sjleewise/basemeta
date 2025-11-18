package kr.wise.advisor.prepare.domain.service;

import kr.wise.advisor.prepare.domain.service.WadDmnPdt;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadNstndDmnPdtMapper {
    int deleteByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("dmngLnm") String dmngLnm);

    int insert(WadDmnPdt record);

    int insertSelective(WadDmnPdt record);

    WadNstndDmnPdt selectByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("dmngLnm") String dmngLnm);

    int updateByPrimaryKeySelective(WadNstndDmnPdt record);

    int updateByPrimaryKey(WadNstndDmnPdt record);

	/** @param search
	/** @return insomnia */
	List<WadNstndDmnPdt> selectListbyId(WadNstndDmnPdt search);

	/** @param anlVarId insomnia */
	int deleteByanlVarId(String anlVarId);
}