package kr.wise.meta.ddltsf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlTsfTblMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlTsfTbl record);

    int insertSelective(WaqDdlTsfTbl record);

    WaqDdlTsfTbl selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlTsfTbl record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlTsfTbl record);

    int updateByPrimaryKey(WaqDdlTsfTbl record);

	/** @param search
	/** @return insomnia */
	List<WamDdlTsfObj> selectDdlTsfList(WaaDbMapVo search);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectDdlTsfTblList(WaqDdlTsfTbl search);

	/** @param saveVo
	/** @return yeonho */
	int deleteByrqstSno(WaqDdlTsfTbl saveVo);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectDdlListByMst(WaqMstr search);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo yeonho */
	int updateDbSchId(String rqstNo);

	/** @param rqstNo yeonho */
	int updateTblSpaceId(String rqstNo);

	/** @param rqstNo yeonho */
	int updateTblId(String rqstNo);

	/** @param searchVo
	/** @return yeonho */
	WaqDdlTsfTbl selectDdlTsfTblDetail(WaqDdlTsfTbl searchVo);

	/** @param searchVo
	/** @return yeonho */
	WaqDdlTsfTbl selectDdlTsfIdxDetail(WaqDdlTsfTbl searchVo);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectDdlTblList(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list);

	/** @param checkmap yeonho */
	int checkDupTbl(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNotExistTbl(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonSrcDbmsID(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonTgtDbmsID(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonExistDdlTbl(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonExistTblSpac(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkRequestTbl(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonPdmTbl(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectDdlIdxList(@Param("reqmst")WaqMstr reqmst,
			@Param("list")ArrayList<WaqDdlTsfTbl> list);

	/** @param savevo
	/** @return yeonho */
	int updatervwStsCd(WaqDdlTsfTbl savevo);

	/** @param rqstno
	/** @return yeonho */
	List<WaqDdlTsfTbl> selectWaqC(String rqstno);

	/** @param checkmap yeonho */
	int checkColErr(Map<String, Object> checkmap);



}