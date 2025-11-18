package kr.wise.dq.profile.mstr.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WaqPrfDqiMapMapper {
	
    int insertSelective(WaqPrfMstrVO record);
    
    List<WaqPrfMstrVO> selectDqiExists(WaqPrfMstrVO record);
    
    //프로파일명 key로 삭제
    int deletePrfDqiByPrfNm(WaqPrfMstrVO record);
    
    String selectPrfDqiByPrfNm(WaqPrfMstrVO record);
    
	int updateDqiId(String rqstNo);

	int checkNotExistDqi(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
	int insertWahSelective(WamPrfMstrVO record);
	
	/** @param savevo meta */
	void updatervwStsCd(WamPrfMstrVO savevo);

	/** @param savevo meta */
	void updateidByKey(WamPrfMstrVO savevo);

	/** @param rqstno meta */
	void updateWaqCUD(String rqstno);

	/** @param rqstno meta */
	void deleteWAM(String rqstno);

	/** @param rqstno meta */
	void insertWAM(String rqstno);

	/** @param rqstno meta */
	void updateWAH(String rqstno);

	/** @param rqstno meta */
	void insertWAH(String rqstno);

	/** @param rqstNo
	/** @return meta */
	int updateDelId(String rqstNo);

	/** @param rqstNo
	/** @return meta */
	int updatePrfDqiInfo(String rqstNo);

	String selectRqstNo(String prfId);

	int deleteWAMByPrfId(String prfId);
	
	int insertWAMbyByPrfId(String prfId);
	
	int deleteByDqiNmGroup(String prfId);

	int deleteWAHByPrfId(String prfId);

	int insertWamSelective(WamPrfMstrVO record);
	
	int updateWAQPrfNmByKey(WaqPrfMstrVO record);
	
	int selectPrfDqiCnt(String oldRqstNo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}