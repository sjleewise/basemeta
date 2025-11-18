package kr.wise.dq.profile.colptrana.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfPtrAnaMapper extends CommonRqstMapper  {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfPtrAnaVO record);

    int updateByPrimaryKeySelective(WaqPrfPtrAnaVO record);
    
    /** @param rqstNo  */
	int checkDupPrf(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
    /** @param rqstNo  */
   	int checkNoChg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //프로파일 상세정보 INFO UPDATE
    int updatePrfDtlInfo(@Param("tblNm") String tblnm, @Param("rqstNo") String rqstNo);
    
    /** 신규ID UPDATE */
   	int updateidByKey(WaqPrfMstrVO savevo);
   	
    //검증 결과 조회
    List<WaqPrfPtrAnaVO> selectPrfExlPc05Lst(WaqMstr search);
    
	/** @param record
	/** @return hokim */
	List<WaqPrfPtrAnaVO> getDbcColList(Object record);

	List<WaqPrfEfvaUserDfVO> selectPrfWatPc05Lst(@Param("dbConnTrgId") String dbConnTrgId, @Param("dbSchId") String dbSchId, @Param("dbcColNm") String dbcColNm, @Param("paramVal") String paramVal);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}