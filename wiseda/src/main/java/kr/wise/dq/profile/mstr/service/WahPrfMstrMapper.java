package kr.wise.dq.profile.mstr.service;

import java.util.Date;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.mstr.service.WahPrfMstrVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPrfMstrMapper {
    int deleteByPrimaryKey(@Param("prfId") String prfId, @Param("prfKndCd") String prfKndCd, @Param("expDtm") Date expDtm);

    int insert(WahPrfMstrVO record);

    int insertSelective(WamPrfMstrVO record);

    WahPrfMstrVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("prfKndCd") String prfKndCd, @Param("expDtm") Date expDtm);

    int updateByPrimaryKeySelective(WahPrfMstrVO record);

    int updateByPrimaryKey(WahPrfMstrVO record);
    
    /*프로파일 상세 이력 테이블 EXP_DTM 만료*/
//    int updateWahPrfExpDtm(String waqTblNm, String wahTblNm, @Param("rqstNo") String rqstNo);
    int updateWahPrfExpDtm(@Param("waqTblNm") String waqTblNm, @Param("wahTblNm") String wahTblNm, @Param("rqstNo") String rqstNo);
    
    /*만료*/
//    int updateWahPrf(@Param("wahTblNm") String wahTblNm,  @Param("prfId") String prfId);
    
    /*만료*/
    int updateWahPrf(@Param("wahTblNm") String wahTblNm,  @Param("prfId") String prfId, @Param("prfKndCd") String prfKndCd, @Param("subPrfObjNm") String subPrfObjNm);
    
    /*등록*/
    int insertWahPrfMstr(WaqMstr mstrVo);
}