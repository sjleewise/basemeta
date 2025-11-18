package kr.wise.dq.profile.colana.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfColAnaMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WaqPrfColAnaVO record);

    int insertSelective(WaqPrfColAnaVO record);

    WaqPrfColAnaVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqPrfColAnaVO record);
    
    //컬럼분석 변경 대상 검증
	int checkNoChg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
	//컬럼분석 상세정보 검증
	int checkPc01Info(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);

    //컬럼프로파일 검증 결과 조회
    List<WaqPrfColAnaVO> selectPrfExlPc01Lst(WaqMstr search);
    
    /** 신규ID UPDATE */
	int updateidByKey(WaqPrfMstrVO savevo);

	/** 검증결과 조회 */
	List<WaqPrfColAnaVO> selectProfileRqstList(WaqMstr search);
	
	/** @param record
	/** @return hokim */
	List<WaqPrfColAnaVO> getDbcColList(Object record);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}