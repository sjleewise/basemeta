package kr.wise.dq.criinfo.dqi.service;

import java.util.Date;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WahDqiMapper {
    int deleteByPrimaryKey(@Param("dqiId") String dqiId, @Param("expDtm") Date expDtm);

    int insert(WahDqiVO record);

    int insertSelective(WahDqiVO record);

    WahDqiVO selectByPrimaryKey(@Param("dqiId") String dqiId, @Param("expDtm") Date expDtm);
    
    int updateByPrimaryKeySelective(WahDqiVO record);

    int updateByPrimaryKey(WahDqiVO record);
    
    //이력테이블 insert
    int insertWahDqi(WaqMstr mstVO);
	
	//이력테이블 만료
    int updateWahDqi(WaqMstr mstVO);
    
    //이력조회
    List<WahDqiVO> selectHstByDqiId(String dqiId);

}