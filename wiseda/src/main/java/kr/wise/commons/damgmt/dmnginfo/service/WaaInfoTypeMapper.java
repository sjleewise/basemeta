package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaInfoTypeMapper {
    int deleteByPrimaryKey(String infotpId);

    int insert(WaaInfoType record);

    int insertSelective(WaaInfoType record);

    WaaInfoType selectByPrimaryKey(String infotpId);

    int updateByPrimaryKeySelective(WaaInfoType record);

    int updateByPrimaryKey(WaaInfoType record);

    List<WaaInfoType> selectList(WaaInfoType record);

    List<WaaInfoType> selectInfotpList();


    int deleteRegTypCd(WaaInfoType record);

    int updateExpDtm(WaaInfoType record);

	WaaInfoType selectByName(WaaInfoType record);
}