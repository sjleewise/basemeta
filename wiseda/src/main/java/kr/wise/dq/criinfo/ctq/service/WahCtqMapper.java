package kr.wise.dq.criinfo.ctq.service;

import java.util.Date;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WahCtqMapper {
    int deleteByPrimaryKey(@Param("ctqId") String ctqId, @Param("expDtm") Date expDtm);

    int insert(WahCtqVO record);

    int insertSelective(WahCtqVO record);

    WahCtqVO selectByPrimaryKey(@Param("ctqId") String ctqId, @Param("expDtm") Date expDtm);
    
    int updateByPrimaryKeySelective(WahCtqVO record);

    int updateByPrimaryKey(WahCtqVO record);
    
    //이력테이블 insert
    int insertWahCtq(WaqMstr mstVO);
	
	//이력테이블 만료
    int updateWahCtq(WaqMstr mstVO);
    
    //이력조회
    List<WahCtqVO> selectHstByCtqId(String ctqId);

	List<WamCtqWhereUsed> selectWhereusedByCtqId(String sCtqId);

}