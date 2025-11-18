package kr.wise.meta.effect.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatPgmAstaTblMapper {
    int insert(WatPgmAstaTbl record);

    int insertSelective(WatPgmAstaTbl record);

	/** @param search
	/** @return insomnia */
	List<WatPgmAstaTbl> selectTblCRUD(WatPgmAsta search);
}