package kr.wise.commons.code.service;

import java.util.List;

//@Mapper
public interface WaaCommDcdMapper {

	int deleteAll();

	int deleteByPrimaryKey(String commDcdId);

	int deleteDtlByPrimaryKey(String commDcdId);

    int insert(WaaCommDcd record);

    int insertSelective(WaaCommDcd record);

    WaaCommDcd selectByPrimaryKey(String commDcdId);

    WaaCommDcd selectSearch(WaaCommDcd record);

    List<WaaCommDcd> selectList(WaaCommDcd record);

    List<WaaCommDcd> selectListCollect(WaaCommDcd record);

    List<WaaCommDcd> selectCodeList(WaaCommDcd record);

    int updateByPrimaryKeySelective(WaaCommDcd record);

    int updateByPrimaryKey(WaaCommDcd record);
}