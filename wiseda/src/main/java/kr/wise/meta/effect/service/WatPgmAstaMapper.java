package kr.wise.meta.effect.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatPgmAstaMapper {
    int insert(WatPgmAsta record);

    int insertSelective(WatPgmAsta record);

	/** @param search
	/** @return insomnia */
	List<WatPgmAsta> selectProgramEffectList(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	WatPgmAsta selectProgramEffectDetail(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	List<WatPgmAsta> selectRelProgramList(WatPgmAsta search);

	/** @param search
	/** @return insomnia */
	List<WatPgmAstaFuc> selectRelFuncList(WatPgmAsta search);
}