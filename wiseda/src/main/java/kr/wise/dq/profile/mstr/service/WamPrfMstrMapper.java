package kr.wise.dq.profile.mstr.service;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPrfMstrMapper {
	
    int deleteByPrimaryKey(WamPrfMstrVO record);
    
    int deleteByPrfId(@Param("wamTblNm") String wamTblNm, @Param("prfId") String prfId);

    int insert(WamPrfMstrVO record);

    int insertSelective(WamPrfMstrVO record);

    WamPrfMstrVO selectByPrimaryKey(@Param("prfId") String prfId, @Param("prfKndCd") String prfKndCd);
    
    WamPrfMstrCommonVO selectPrfMstrByPrfId(@Param("prfId") String prfId, @Param("prfKndCd") String prfKndCd);

    int updateByPrimaryKeySelective(WamPrfMstrVO record);

    int updateByPrimaryKey(WamPrfMstrVO record);
    
    //프로파일 C, U 조회
    String selectProfileRegTypCd(WamPrfMstrVO search);
    
//    int deleteWamPrfSub(String wahTblNm, String wamTblNm, @Param("rqstNo") String rqstNo);
    int deleteWamPrfSub(@Param("wahTblNm") String wahTblNm, @Param("wamTblNm") String wamTblNm, @Param("rqstNo") String rqstNo);
    
    /*삭제*/
    int deleteWamPrfMstr(WaqMstr mstrVo);
    
    /*등록*/
    int insertWamPrfMst(WaqMstr mstrVo);
    
    /*등록*/
    int insertWamPrfMstByPrfId(String prfId);
    
    
    //프로파일 SQL 생성기 DB타입, 프로파일종류 조회
    WamPrfMstrVO selectSqlGenDbmsInfoByPrfId(@Param("prfId") String prfId);

	int updateErrCntSql(WamPrfMstrVO saveVo);


}