package kr.wise.meta.ddltsf.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.meta.ddl.service.WamDdlIdx;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper 
public interface WaqDdlTsfIdxMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlTsfIdx record);

    int insertSelective(WaqDdlTsfTbl saveVo);

    WaqDdlTsfIdx selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlTsfTbl saveVo);

    int updateByPrimaryKeyWithBLOBs(WaqDdlTsfIdx record);

    int updateByPrimaryKey(WaqDdlTsfIdx record);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo yeonho */
	int updateDdlIdx(String rqstNo);

	/** @param rqstNo yeonho */
	int updateDbSchId(String rqstNo);

	/** @param rqstNo yeonho */
	int updateTblSpaceId(String rqstNo);

	/** @param saveVo
	/** @return yeonho */
	int deleteByrqstSno(WaqDdlTsfTbl saveVo);

	/** @param saveVo yeonho */
	int insertByRqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param checkmap3 yeonho */
	int checkDupIdx(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistIdx(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkRequestIdx(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNonSrcDbSch(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNonTgtDbSch(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNonDdlTbl(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNonIdxSpac(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNonExistCol(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotChgData(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkColErr(Map<String, Object> checkmap3);

	/** @param savevo
	/** @return yeonho */
	int updatervwStsCd(WaqDdlTsfTbl savevo);

	/** @param ddlIdxId
	/** @return yeonho */
	WaqDdlTsfIdx selectDdlTsfIdxInfo(String ddlIdxId);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdx> selectTsfIdxChangeList(WamDdlIdx search);

	WaqDdlTsfIdx selectDdlTsfIdxDetail(WaqDdlTsfIdx searchVo);

	
}