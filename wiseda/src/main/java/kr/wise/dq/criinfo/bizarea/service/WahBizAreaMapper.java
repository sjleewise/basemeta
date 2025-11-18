package kr.wise.dq.criinfo.bizarea.service;

import java.util.Date;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WahBizAreaMapper {
    int deleteByPrimaryKey(@Param("bizAreaId") String bizAreaId, @Param("expDtm") Date expDtm);

    int insert(WahBizAreaVO record);

    int insertSelective(WahBizAreaVO record);

    WahBizAreaVO selectByPrimaryKey(@Param("bizAreaId") String bizAreaId, @Param("expDtm") Date expDtm);
    
    int updateByPrimaryKeySelective(WahBizAreaVO record);

    int updateByPrimaryKey(WahBizAreaVO record);
    
    //이력테이블 insert
    int insertWahBizArea(WaqMstr mstVO);
	
	//이력테이블 만료
    int updateWahBizArea(WaqMstr mstVO);
    
    //이력조회
    List<WahBizAreaVO> selectHstByBizAreaId(String bizAreaId);

}