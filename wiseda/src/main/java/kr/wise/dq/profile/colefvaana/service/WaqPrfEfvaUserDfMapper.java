package kr.wise.dq.profile.colefvaana.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfEfvaUserDfMapper  extends CommonRqstMapper { 
    int deleteByPrimaryKey(WaqPrfEfvaUserDfVO record);

    int insertSelective(WaqPrfEfvaUserDfVO record);

    int updateByPrimaryKeySelective(WaqPrfEfvaUserDfVO record);
    
    //변경된 데이터 없음(PRF00)
   	int checkNoChg(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
   	
  	//요청서내 중복자료 검증(PRF01)
  	int checkDupPrfUser(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
  	
    //사용자정의 유효값 미입력
    int chkUserDfReg(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //검증 결과 조회
    List<WaqPrfEfvaUserDfVO> selectPrfExlPc02Lst(WaqMstr search);
    
	/** @param record
	/** @return hokim */
	List<WaqPrfEfvaUserDfVO> getDbcColList(Object record);

	List<WaqPrfEfvaUserDfVO> selectPrfFromMetaPc02Lst(WaqMstr search);

	List<WaqPrfEfvaUserDfVO> selectPrfWatPc02Lst(@Param("dbConnTrgId") String dbConnTrgId, @Param("dbSchId") String dbSchId, @Param("dbcColNm") String dbcColNm, @Param("paramVal") String paramVal); 
    
	int selectEfvaNanKnd(String oldRqstNo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}