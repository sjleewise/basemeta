package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamSymnMapper {
    int deleteByPrimaryKey(String symnId);

    int insert(WamSymn record);

    int insertSelective(WamSymn record);

    WamSymn selectByPrimaryKey(String symnId);

    int updateByPrimaryKeySelective(WamSymn record);

    int updateByPrimaryKey(WamSymn record);
    
    List<WamSymn> selectSymnList(WamSymn record);
    
    int updateWahSymn(WamSymn record);

	List<WamSymn> selectByLnmPnm(@Param("symnLnm") String symnLnm, @Param("symnPnm") String symnPnm);

	List<WamSymn> selectSymnChangeList(String symnId);

	int selectSymnCnt(WamSymn saveVO);
    
    
}