package kr.wise.dq.profile.colrngana.service;

import java.util.Collection;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfRngAnaMapper  extends CommonRqstMapper  {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfRngAnaVO record);

    WaqPrfRngAnaVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqPrfRngAnaVO record);
    
    //범위분석 변경 대상 검증
   	int checkNoChg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //연결자 존재할경우 범위연산자2, 범위유효값2 값 존재
   	int checkRngOpr2(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //범위연산자2 존재할 경우 연결자, 범위유효값2 값 존재
   	int checkRngCnc(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //범위연산자2, 범위유효값2 존재할 경우 연결자  존재
   	int checkRngCnc2(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //검증 결과 조회
    List<WaqPrfRngAnaVO> selectPrfExlPc04Lst(WaqMstr search);
    
    /** 신규ID UPDATE */
	int updateidByKey(WaqPrfMstrVO savevo);

	List<WaqPrfRngAnaVO> getDbcColList(Object record); 

	List<WaqPrfRngAnaVO> selectPrfFromMetaPc04Lst(WaqMstr search);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}