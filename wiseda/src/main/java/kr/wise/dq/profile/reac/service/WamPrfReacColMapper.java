package kr.wise.dq.profile.reac.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WamPrfReacColMapper {
    int deleteByPrimaryKey(WamPrfReacColVO record);

    int insert(WamPrfReacColVO record);

    int insertSelective(WamPrfReacColVO record);

    WamPrfReacColVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("relColSno") Integer relColSno);

    int updateByPrimaryKeySelective(WamPrfReacColVO record);

    int updateByPrimaryKey(WamPrfReacColVO record);
    
    int insertWamPrfReacByPrfId(WamPrfReacColVO record);
    
    //관계컬럼 조회
    List<WamPrfReacColVO> selectListByPrimaryKey(@Param("prfId") String prfId);

	List<WamPrfMstrCommonVO> profileListReacAna(WamPrfMstrCommonVO search);
}