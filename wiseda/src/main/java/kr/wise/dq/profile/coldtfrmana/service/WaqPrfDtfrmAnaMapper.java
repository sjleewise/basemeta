package kr.wise.dq.profile.coldtfrmana.service;

import java.util.Collection;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfDtfrmAnaMapper  extends CommonRqstMapper  {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqPrfDtfrmAnaVO record);

    WaqPrfDtfrmAnaVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqPrfDtfrmAnaVO record);
    
    //날짜형식분석 변경 대상 검증
   	int checkNoChg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo, @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
    //검증 결과 조회
    List<WaqPrfDtfrmAnaVO> selectPrfExlPc03Lst(WaqMstr search);
    
    /** 신규ID UPDATE */
	int updateidByKey(WaqPrfMstrVO savevo);

	List<WaqPrfDtfrmAnaVO> getDbcColList(Object record);

	List<WaqPrfDtfrmAnaVO> selectPrfFromMetaPc03Lst(WaqMstr search);

	List<WaqPrfDtfrmAnaVO> selectPrfWatPc03Lst(@Param("dbConnTrgId") String dbConnTrgId, @Param("dbSchId") String dbSchId, @Param("dbcColNm") String dbcColNm, @Param("paramVal") String paramVal); 

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}