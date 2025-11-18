package kr.wise.meta.ldm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqLdmEntyMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqLdmEnty record);

    int insertSelective(WaqLdmEnty record);

    WaqLdmEnty selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqLdmEnty record);

    int updateByPrimaryKey(WaqLdmEnty record);

	/** @param searchVo
	/** @return insomnia */
	WaqLdmEnty selectLdmTblDetail(WaqLdmEnty searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqLdmEnty> selectLdmEntyListbyMst(WaqMstr search);

	/** @param saveVo
	/** @return insomnia */
	int deleteByrqstSno(WaqLdmEnty saveVo);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	List<WaqLdmEnty> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqLdmEnty> list);
	
	/** @param reqmst
	/** @param list
	/** @return insomnia */
	List<WaqLdmEnty> selectmartlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqLdmEnty> list);


	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param checkmap insomnia */
	int checkDupTbl(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNotExistTbl(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNonSubjID(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int updateStndApplybySubj(String rqstNo);

	/** @param rqstNo insomnia */
	int updateStndApplybyCol(String rqstNo);

	/** @param checkmap insomnia */
	int checkRequestTbl(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkTblNameLength(Map<String, Object> checkmap);
	
	/** @param checkmap shshin */
	int checkColCnt(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkColErr(Map<String, Object> checkmap);

	/** @param savevo
	/** @return insomnia */
	int updatervwStsCd(WaqLdmEnty savevo);

	/** @param rqstno
	/** @return insomnia */
	List<WaqLdmEnty> selectWaqC(@Param("rqstNo") String rqstNo);

	/** @param savevo insomnia */
	int updateidByKey(WaqLdmEnty savevo);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(@Param("rqstNo") String rqstNo);

	/** @param checkmap meta */
	int checkRelErr(Map<String, Object> checkmap);
	
	/** @param checkmap meta */
	int checkTblName(Map<String, Object> checkmap);

	int updateLdmTblIdForDdltbl(); 

	int checkSubjDbSch(Map<String, Object> checkmap);
	
	/** 모델 재상신 */
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	int checkObjdesc(Map<String, Object> checkmap);
	
	/** 시스템공통컬럼 존재유무 체크(ibk신용정보 전용)*/
	int checkCommonCol(Map<String, Object> checkmap);

	//마스터테이블 존재여부 확인
    int checkMstTblExists(Map<String, Object> checkmap);
    
    	//이력테이블 존재여부 확인
    int checkHistTblExists(Map<String, Object> checkmap);

	String selectSubjDbmsTypCd(String rqstNo); 
}