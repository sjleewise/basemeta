package kr.wise.commons.sysmgmt.menu.service;

import java.math.BigDecimal;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ComtnmenuinfoMapper {
    int deleteByPrimaryKey(BigDecimal menuNo);

    int insert(Comtnmenuinfo record);

    int insertSelective(Comtnmenuinfo record);

    Comtnmenuinfo selectByPrimaryKey(BigDecimal menuNo);

    int updateByPrimaryKeySelective(Comtnmenuinfo record);

    int updateByPrimaryKey(Comtnmenuinfo record);
}