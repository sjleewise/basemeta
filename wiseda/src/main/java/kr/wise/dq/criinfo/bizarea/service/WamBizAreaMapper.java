package kr.wise.dq.criinfo.bizarea.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamBizAreaMapper {
    int deleteByPrimaryKey(String bizAreaId);

    int insert(WamBizAreaVO record);

    int insertSelective(WamBizAreaVO record);

    WamBizAreaVO selectByPrimaryKey(String bizAreaId);

    int updateByPrimaryKeySelective(WamBizAreaVO record);

    int updateByPrimaryKey(WamBizAreaVO record);
	
    List<WamBizAreaVO> selectBizAreaList(WamBizAreaVO search);
    
   //wam테이블 삭제
    int deleteWamBizArea(WaqMstr mstVO);

	//wam테이블 적재
    int insertWamBizArea(WaqMstr mstVO);

	List<WamBizAreaVO> selectBizAreaListPop(WamBizAreaVO search);
    
}