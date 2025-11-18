package kr.wise.meta.ddltsf.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WamDdlIdxCol;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlTsfIdxColMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlTsfIdxCol record);

    int insertSelective(WaqDdlTsfIdxCol record);

    WaqDdlTsfIdxCol selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlTsfIdxCol record);

    int updateByPrimaryKey(WaqDdlTsfIdxCol record);

	/** @param saveVo yeonho */
	int insertByRqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param saveVo yeonho */
	int deleteByrqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> selectDdlTsfIdxColRqstList(WaqMstr search);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param checkmap4 yeonho */
	int checkNotChgData(Map<String, Object> checkmap4);

	/** @param rqstno
	/** @return yeonho */
	int updateWaqId(String rqstno);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> selectTsfIdxColList(WamDdlIdxCol search);

	/** @param ddlIdxColId
	/** @return yeonho */
	WaqDdlTsfIdxCol selectDdlTsfIdxColInfo(String ddlIdxColId);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfIdxCol> selectTsfIdxColChangeList(WamDdlIdxCol search);
}