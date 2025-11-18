package kr.wise.commons.damgmt.schedule.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahShdMstrMapper {
	
	int updateWahShd(@Param("wahTblNm") String wahTblNm,  @Param("shdId") String shdId);
	
	int insertSelective(WamShd record);
	
	int insertJobSelective(WamShd record);
	  
	  
    int deleteByPrimaryKey(@Param("shdId") String shdId, @Param("expDtm") Date expDtm);

    int insert(WahShdMstr record);

//    int insertSelective(WahShdMstr record);

    WahShdMstr selectByPrimaryKey(@Param("shdId") String shdId, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahShdMstr record);

    int updateByPrimaryKey(WahShdMstr record);
}