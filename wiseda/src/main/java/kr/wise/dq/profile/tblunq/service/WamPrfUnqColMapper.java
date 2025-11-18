package kr.wise.dq.profile.tblunq.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfUnqColMapper {
    int deleteByPrimaryKey(WamPrfUnqColVO record);

    int insert(WamPrfUnqColVO record);

    int insertSelective(WamPrfUnqColVO record);

    WamPrfUnqColVO selectByPrimaryKey(@Param("prfId") String prfId);
    
    List<WamPrfUnqColVO> selectListByPrimaryKey(@Param("prfId") String prfId);

    int updateByPrimaryKeySelective(WamPrfUnqColVO record);

    int updateByPrimaryKey(WamPrfUnqColVO record);
    
    int insertWamPrfPT02ByPrfId(WamPrfUnqColVO record);

    //프로파일 조회 중복분석
	List<WamPrfMstrCommonVO> profileListUnqAna(WamPrfMstrCommonVO search);
}