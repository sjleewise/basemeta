package kr.wise.advisor.prepare.summary.service;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.summary.service.WadVarHst;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadVarHstMapper {
    int deleteByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("hstSno") Integer hstSno);

    int insert(WadVarHst record);

    int insertSelective(WadVarHst record);

    WadVarHst selectByPrimaryKey(@Param("anlVarId") String anlVarId, @Param("hstSno") Integer hstSno);

    int updateByPrimaryKeySelective(WadVarHst record);

    int updateByPrimaryKey(WadVarHst record);

	/** @param search
	/** @return insomnia */
	List<WadVarSum> selectHistoListbyId(WadAnaVar search);

	/** @param varId insomnia */
	int deleteByvarId(String varId);
}