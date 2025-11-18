package kr.wise.dq.criinfo.ctq.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCtqMapper {
    int deleteByPrimaryKey(String ctqId);

    int insert(WamCtqVO record);

    int insertSelective(WamCtqVO record);

    WamCtqVO selectByPrimaryKey(String ctqId);

    int updateByPrimaryKeySelective(WamCtqVO record);

    int updateByPrimaryKey(WamCtqVO record);
	
    List<WamCtqVO> selectCtqList(WamCtqVO search);
    
   //wam테이블 삭제
    int deleteWamCtq(WaqMstr mstVO);

	//wam테이블 적재
    int insertWamCtq(WaqMstr mstVO);

	List<WamCtqVO> selectCtqListPop(WamCtqVO search);
    
}