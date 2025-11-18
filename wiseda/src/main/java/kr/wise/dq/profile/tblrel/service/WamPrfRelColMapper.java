package kr.wise.dq.profile.tblrel.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfRelColMapper {
    int deleteByPrimaryKey(WamPrfRelColVO record);

    int insert(WamPrfRelColVO record);

    int insertSelective(WamPrfRelColVO record);

    WamPrfRelColVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno);

    int updateByPrimaryKeySelective(WamPrfRelColVO record);

    int updateByPrimaryKey(WamPrfRelColVO record);
    
    int insertWamPrfPT01ByPrfId(WamPrfRelColVO record);
    
    //관계컬럼 조회
    List<WamPrfRelColVO> selectListByPrimaryKey(@Param("prfId") String prfId);

	List<WamPrfMstrCommonVO> profileListRelAna(WamPrfMstrCommonVO search);
}