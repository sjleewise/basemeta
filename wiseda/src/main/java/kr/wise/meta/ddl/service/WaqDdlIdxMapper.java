package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlIdx;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper 
public interface WaqDdlIdxMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlIdx record);

    int insertSelective(WaqDdlIdx record);

    WaqDdlIdx selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlIdx record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlIdx record);

    int updateByPrimaryKey(WaqDdlIdx record);
    
	/** @param search
	/** @return 유성열 */
	List<WaqDdlIdx> selectDdlIdxListbyMst(WaqMstr search);
	
	/** @param searchVo
	/** @return 유성열 */
	WaqDdlIdx selectDdlIdxDetail(WaqDdlIdx searchVo);

	/** @param saveVo
	/** @return meta */
	int deleteByrqstSno(WaqDdlIdx saveVo);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo meta */
	int updateKeyId(String rqstNo);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqDdlIdx savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqDdlIdx> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqDdlIdx savevo);

	/** @param checkmap meta */
	int checkDupIdx(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNotExistIdx(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkRequestIdx(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNonDbConnTrg(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNonDbSch(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNonDdlTbl(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNonIdxSpac(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNonExistCol(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkColErr(Map<String, Object> checkmap);

	/** @param reqmst
	/** @param list
	/** @return meta */
	List<WaqDdlIdx> selectddlidxlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlIdx> list);

	/** @param checkmap meta */
	int checkIdxPnmConst(Map<String, Object> checkmap);
	
	/** @param savevo insomnia */
	int updateDdlIdxScriptWaq(WaqDdlIdx savevo);

	int insertByRqstSnoTsf(WaqDdlIdx saveVo);

	int deleteByrqstSno(WaqDdlTbl saveVo);

	int insertTsfIdxSelective(WaqDdlIdx saveVo);

	List<WaqDdlIdx> selectDdlTsfIdxListForRqst(WaqDdlIdx search); 

	List<WaqDdlIdx> selectTsfDdlIdxList(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlIdx> list);

	List<WaqDdlIdx> selecttDdlTsfIdxRqstList(WaqMstr search);

	WaqDdlIdx selectExistsRqstSno(WaqDdlIdx saveVo);

	WaqDdlIdx selectMaxRqstSno(WaqDdlIdx saveVo);

	int insertByRqstSno(WaqDdlTbl saveVo);

	void deleteByRqstSno(WaqDdlTbl saveVo);

	void updateDelColIdxDel(String rqstNo);

	int checkInsNotChgData(Map<String, Object> checkmap);

	int updateIdxSpacPnm(String rqstNo);

	int checkRequestDdlTbl(Map<String, Object> checkmap);

	int insertByRqstSnoTsf(WaqDdlTbl saveVo);

	int updateWaqDdlTblId(String rqstno);

    int insertDelInit(String rqstNo);

	List<WaqDdlIdx> selectDelDdlTsfIdxListForRqst(WaqDdlIdx search);

	int deleteByRqstSno(WaqDdlIdx saveVo);    
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );

	int updateUkIdxYn(String rqstNo);
}