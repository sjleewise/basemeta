package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.user.WaaUserg;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaUsergMapper {
	
    int deleteByPrimaryKey(String usergId);

    //int insert(WaaUserg record);

    int insertSelective(WaaUserg record);

    WaaUserg selectByPrimaryKey(String usergId);

    int updateByPrimaryKeySelective(WaaUserg record);

    //int updateByPrimaryKey(WaaUserg record);
    
    List<WaaUserg> selectList(WaaUserg record); 
    
    int deleteAll();
    
    int deleteRegTypCd(WaaUserg record);

	int selectUsergCnt(WaaUserg waaUserg);

}