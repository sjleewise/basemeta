package kr.wise.dq.profile.reac.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaqPrfReacTblMapper  extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfReacTblVO record);

    int updateByPrimaryKeySelective(WaqPrfReacTblVO record);
    
    //부모진단대상 ID UPDATE
    int updatePaDbConnTrgId(@Param("rqstNo") String rqstNo); 
    //부모스키마ID UPDATE
    int updatePaDbSchId(@Param("rqstNo") String rqstNo); 
    
   //미존재 부모진단대상명
    int chkPaDbConnTrg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd); 
	//미존재 부모스키마명
    int chkPaDbSch(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	//미존재 부모테이블명
    int chkPaDbcTbl(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
   //프로파일 상세정보 INFO UPDATE
   int updatePrfDtlInfo(@Param("tblNm") String tblnm, @Param("rqstNo") String rqstNo);
   
   /** 신규ID UPDATE */
  	int updateidByKey(WaqPrfMstrVO savevo);
}