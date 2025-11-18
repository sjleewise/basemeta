package kr.wise.meta.ddl.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper 
public interface WaqDdlIdxColMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlIdxCol record);

    int insertSelective(WaqDdlIdxCol record);

    WaqDdlIdxCol selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlIdxCol record);

    int updateByPrimaryKey(WaqDdlIdxCol record);
    
	/** @param search
	/** @return 유성열 */
	List<WaqDdlIdxCol> selectDdlIdxColRqstList(WaqMstr search);
	
	/** @param search
	/** @return 유성열 */
	WaqDdlIdxCol selectDdlIdxColDetail(WaqDdlIdxCol search);

	/** @param saveVo meta */
	int insertByRqstSno(WaqDdlIdx saveVo);

	/** @param saveVo meta */
	int deleteByrqstSno(WaqDdlIdx saveVo);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param savevo
	/** @return meta */
	int deleteByPrimaryKey(WaqDdlIdxCol savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqDdlIdxCol> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqDdlIdxCol savevo);

	/** @param rqstno
	/** @return meta */
	int updateWaqId(String rqstno);

	/** @param rqstNo meta */
	int updateColId(String rqstNo);

	/** @param rqstNo meta */
	int updateIdxPnm(String rqstNo);

	/** @param checkmap2 meta */
	int checkDupCol(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkNotExistCol(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkRequestCol(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkDupColPnm(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkDupColLnm(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkColOrdDup(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkColOrdNonul(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkNotChgData(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkNotExistDdlCol(Map<String, Object> checkmap2);

	/** @param rqstNo meta */
	int updateColOrd(String rqstNo);

	int insertByRqstSnoTsf(WaqDdlIdx saveVo);

	void insertDelInit(String rqstNo);

	void insertByRqstSno(WaqDdlTbl saveVo);

	void deleteByRqstSno(WaqDdlTbl saveVo);

	void updateDelColIdxColDel(String rqstNo);

	int deleteByRqstDtlSno(WaqDdlIdx saveVo);

	int updateDdlIdxColLnm(String rqstNo);

	int updateDelColByIdx(String rqstNo);

	int insertByRqstSnoTsf(WaqDdlTbl saveVo);

	int updateDdlColId(String rqstno);

	int deleteByRqstSno(WaqDdlIdx saveVo); 

	int insertWaqColRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}