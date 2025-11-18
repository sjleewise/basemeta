package kr.wise.dq.profile.tblunq.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;
import kr.wise.dq.profile.tblunq.service.WaqPrfUnqColVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfUnqColMapper  extends CommonRqstMapper  {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfUnqColVO record);

    int updateByPrimaryKeySelective(WaqPrfUnqColVO record);
    
    /** @param rqstNo  */
	int checkDupPrf(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
    /** @param rqstNo  */
   	int checkNoChg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //진단대상 컬럼 검증
    int chkDbcCol(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //프로파일 상세정보 INFO UPDATE
    int updatePrfDtlInfo(@Param("tblNm") String tblnm, @Param("rqstNo") String rqstNo);
    
    /** 신규ID UPDATE */
   	int updateidByKey(WaqPrfMstrVO savevo);
   	
    //검증 결과 조회
    List<WaqPrfUnqColVO> selectPrfExlPt02Lst(WaqMstr search);

    //진단대상 컬럼 존재여부
    int chkDbcColYN(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}