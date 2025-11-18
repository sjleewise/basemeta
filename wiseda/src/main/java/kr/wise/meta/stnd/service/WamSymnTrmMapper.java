package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WamSymnTrmMapper {

    int deleteByPrimaryKey(String symnId);

    int insert(WamSymnTrm record);

    int insertSelective(WamSymnTrm record);

    WamSymnTrm selectByPrimaryKey(String symnId);

    int updateByPrimaryKeySelective(WamSymnTrm record);

    int updateByPrimaryKey(WamSymnTrm record);
    
    List<WamSymnTrm> selectSymnList(WamSymnTrm record);
    
    int updateWahSymn(WamSymnTrm record);

	List<WamSymnTrm> selectByLnmPnm(@Param("symnLnm") String symnLnm, @Param("symnPnm") String symnPnm);
	
	//동음이의어 관리------------------------
	
//    int deleteByPrimaryKey(String symnId);
//
//    int insert(WamSymnTrm record);
//
//    int insertSelective(WamSymnTrm record);
//
//    WamSymnTrm selectByPrimaryKey(String symnId);
//
//    int updateByPrimaryKeySelective(WamSymnTrm record);
//
//    int updateByPrimaryKey(WamSymnTrm record);
    
    List<WamSymnTrm> selectHmnmList(WamSymnTrm record);
    
//    int updateWahHmnm(WamSymnTrm record);

//	List<WamSymnTrm> selectByLnmPnm(@Param("symnLnm") String symnLnm, @Param("symnPnm") String symnPnm);
	
}
