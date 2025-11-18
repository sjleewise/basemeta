package kr.wise.dq.criinfo.dqi.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDqiMapper {
    int deleteByPrimaryKey(String dqiId);

    int insert(WamDqiVO record);

    int insertSelective(WamDqiVO record);

    WamDqiVO selectByPrimaryKey(String dqiId);

    int updateByPrimaryKeySelective(WamDqiVO record);

    int updateByPrimaryKey(WamDqiVO record);
	
    List<WamDqiVO> selectDqiList(WamDqiVO search);
    
   //wam테이블 삭제
    int deleteWamDqi(WaqMstr mstVO);

	//wam테이블 적재
    int insertWamDqi(WaqMstr mstVO);
    
}