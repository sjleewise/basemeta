package kr.wise.dq.profile.colefvaana.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WamPrfEfvaUserDfMapper {
    int deleteByPrimaryKey(WamPrfEfvaUserDfVO record);

    int insert(WamPrfEfvaUserDfVO record);

    int insertSelective(WamPrfEfvaUserDfVO record);

    List<WamPrfEfvaUserDfVO> selectByPrimaryKey(@Param("prfId") String prfId);

    int updateByPrimaryKeySelective(WamPrfEfvaUserDfVO record);

    int updateByPrimaryKey(WamPrfEfvaUserDfVO record);
    
    /*삭제*/
    int deleteWamPrfPC02UserDf(String rqstNo);
    
    /*등록*/
    int insertWamPrfPC02UserDf(WaqMstr mstVO);
    
    /*등록*/
    int insertWamPrfPC02UserDfByPrfId(WamPrfEfvaUserDfVO record);
}