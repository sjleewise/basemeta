package kr.wise.dq.profile.colefvaana.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaAnaVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfEfvaAnaMapper   extends CommonRqstMapper  {
    int deleteByPrimaryKey(WaqPrfEfvaAnaVO record);

    int insertSelective(WaqPrfEfvaAnaVO record);

    int updateByPrimaryKeySelective(WaqPrfEfvaAnaVO record);
    
    //코드테이블진단대상ID UPDATE
    int updateCdDbConnId(@Param("rqstNo") String rqstNo); 
    
    //코드스키마ID UPDATE
    int updateCdDbSchId(@Param("rqstNo") String rqstNo); 
    
    //코드테이블연계 일경우 코드테이블 정보 미입력 검증
    int chkCdTblInfo(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd); 
    
    // 코드테이블연계 일경우 코드ID은 존재시  코드ID컬럼명 작성여부 검증 
    int chkCdId(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd); 
    
    // 코드테이블연계 일경우 코드ID컬럼명은 존재시 코드ID 작성여부 검증 
    int chkCdIdNm(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd); 
    
   //미존재 코드진단대상명
    int chkCdDbConnTrg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd); 
	
    //미존재 코드스키마명
    int chkCdDbSch(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
    //미존재 코드테이블명
    int chkCdDbcTbl(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //미존재 코드값컬럼명 검증
    int chkCdDbcCol(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //미존재 코드ID컬럼명 검증
    int chkCdIdDbcCol(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    // 메타도메인 검증
    int chkMetaDmn(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //요청서내 중복자료 검증(PRF01)
  	int checkDupPrf(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //프로파일 마스터 삭제된 ROW  - > 부모테이블  정보 에서도 삭제
    int deleteByMstrRow(@Param("rqstNo") String rqstNo); 
    
    /** 신규ID UPDATE */
	int updateidByKey(WaqPrfMstrVO savevo);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}