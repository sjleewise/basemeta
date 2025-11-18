package kr.wise.dq.profile.mstr.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;
import kr.wise.meta.stnd.service.WaqStwd;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPrfMstrMapper  extends CommonRqstMapper {
	
	WaqPrfMstrVO selectPrfKndCd(@Param("rqstNo") String rqstNo);

	int deleteByPrimaryKey(WaqPrfMstrVO record);

    //요청신규등록
    int insertSelective(WaqPrfMstrVO record);

    WaqPrfMstrVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    //요청 수정등록
    int updateByPrimaryKeySelective(WaqPrfMstrVO record);

    /** @param rqstNo  */
    int updateCheckInit(@Param("rqstNo") String rqstNo, @Param("tblNm") String tblnm);
    
    /** @param rqstNo  */
	int checkDupPrf(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
	/** @param rqstNo  */
	int checkNotExistPrf(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //프로파일 마스터 INFO UPDATE
    int updatePrfInfo(@Param("rqstNo") String rqstNo);
    
    //프로파일 상세정보 INFO UPDATE
    int updatePrfDtlInfo(@Param("tblNm") String tblnm, @Param("rqstNo") String rqstNo);
	
    //진단대상DB ID UPDATE
    int updateDbConnTrgId(@Param("rqstNo") String rqstNo);
    
    //진단대상 스키마ID UPDATE
    int updateDbSchId(@Param("rqstNo") String rqstNo);
    
    //진단대상DB 검증
    int chkDbConnTrg(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //진단대상 스키마 검증
    int chkDbSch(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //진단대상 테이블 검증
    int chkDbcTbl(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
    //진단대상 컬럼 검증
    int chkDbcCol(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
    
	//프로파일 엑셀 요청 삭제
	int deletebyRqstSno(@Param("tblnm") String tblnm, @Param("ids") Integer[] ids, @Param("rqstNo") String rqstNo);
	
	/** @param savevo insomnia */
	int updatervwStsCd(WaqPrfMstrVO savevo);
	
	/** @param savevo insomnia */
	int updateDtlrvwStsCd(@Param("tblnm") String tblnm, @Param("rvwStsCd") String rvwStsCd, @Param("rvwConts") String rvwConts, @Param("aprvUserId") String aprvUserId, @Param("rqstNo") String rqstNo, @Param("rqstSno") int rqstSno);
	
	/** 신규일 경우 ID 채번*/
	List<WaqPrfMstrVO> selectWaqC(@Param("rqstNo") String rqstNo);
	
	List<WaqPrfMstrVO> selectWaqList(@Param("rqstNo") String rqstNo);
	
	/**  프로파일 마스터 신규ID UPDATE*/
	int updateidByKey(WaqPrfMstrVO savevo);
	
	/** 프로파일 상세 신규ID UPDATE */
	int updateDtlIdByKey(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo);
	
	//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
	int deleteByPrfNmGroup(@Param("rqstNo") String rqstNo);
	
	 //프로파일 마스터 삭제된 ROW  - > 부모테이블  정보 에서도 삭제
    int deleteByMstrRow(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo); 
    
	//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
	int updateRqstSno(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo);
	
	int updateMstrDtoU(@Param("Utblnm") String Utblnm ,@Param("Mtblnm") String Mtblnm ,@Param("Qtblnm") String Qtblnm, @Param("rqstNo") String rqstNo);

	void updateRqstSnoDqi(@Param("tblnm") String tblnm, @Param("rqstNo") String rqstNo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	int updateDqiId(String rqstNo);

	int checkNotExistDqi(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd);
	
	int checkLowRank(@Param("rqstNo") String rqstNo,  @Param("bizDtlCd") String bizDtlCd, @Param("vrfDtlCd") String vrfDtlCd, @Param("dqiLnm") String dqiLnm, @Param("rqstSno") Integer rqstSno);
}