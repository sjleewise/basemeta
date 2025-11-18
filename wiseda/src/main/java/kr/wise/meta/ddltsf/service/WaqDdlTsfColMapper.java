package kr.wise.meta.ddltsf.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlTsfColMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlTsfCol record);

    int insertSelective(WaqDdlTsfCol record);

    WaqDdlTsfCol selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlTsfCol record);

    int updateByPrimaryKey(WaqDdlTsfCol record);

	/** @param saveVo yeonho */
	int insertByRqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param saveVo yeonho */
	int deleteByrqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfCol> selectDdlTsfColRqstList(WaqMstr search);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo yeonho */
	int updateDataTypeYn(String rqstNo);

	/** @param rqstNo yeonho */
	int updateNonNullYn(String rqstNo);

	/** @param rqstNo yeonho */
	int updateDefaultYn(String rqstNo);

	/** @param rqstNo yeonho */
	int updateColUpdateYn(String rqstNo);

	/** @param checkmap2 yeonho */
	int checkNotChgData(Map<String, Object> checkmap2);

	/** @param rqstno
	/** @return yeonho */
	int updateWaqId(String rqstno);
}