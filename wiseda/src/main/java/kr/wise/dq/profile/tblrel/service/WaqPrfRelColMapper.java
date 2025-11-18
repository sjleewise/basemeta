package kr.wise.dq.profile.tblrel.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfRelColMapper  extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfRelColVO record);

    int updateByPrimaryKeySelective(WaqPrfRelColVO record);
    
    //자식 컬럼 검증
    int chkChDbcCol(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //부모 컬럼 검증
    int chkPaDbcCol(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //요청서내 중복자료 검증(PRF01)
   	int checkDupPrf(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
   	//변경된 데이터 없음(PRF00)
  	int checkNoChg(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
  	
  	
    //프로파일 상세정보 INFO UPDATE
    int updatePrfDtlInfo(@Param("tblNm") String tblnm, @Param("rqstNo") String rqstNo);
    
    /** 신규ID UPDATE */
   	int updateidByKey(WaqPrfMstrVO savevo);
   	
    List<WaqPrfRelColVO> selectPrfExlPt01Lst(WaqMstr search);

	List<WaqPrfRelColVO> selectPrfFromMetaPt01Lst(WaqMstr search); 

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}