package kr.wise.meta.ddl.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqDdlColMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlCol record);

    int insertSelective(WaqDdlCol record);

    WaqDdlCol selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlCol record);

    int updateByPrimaryKey(WaqDdlCol record);

	/** @param saveVo insomnia */
	int insertByRqstSno(WaqDdlTbl saveVo);

	/** @param saveVo insomnia */
	int deleteByrqstSno(WaqDdlTbl saveVo);

	/** @param search
	/** @return insomnia */
	List<WaqDdlCol> selectDdlColRqstList(WaqMstr search);

	/** @param saveVo insomnia */
	int updateTblNmbyRqstsno(WaqDdlTbl saveVo);

	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo insomnia */
	int insertDelInit(String rqstNo);

	/** @param rqstNo insomnia */
	int updateDataTypeYn(String rqstNo);

	/** @param rqstNo insomnia */
	int updateNonNullYn(String rqstNo);

	/** @param rqstNo insomnia */
	int updateDefaultYn(String rqstNo);

	/** @param rqstNo insomnia */
	int updateColUpdateYn(String rqstNo);

	/** @param checkmap2 insomnia */
	int checkNotChgData(Map<String, Object> checkmap2);

	/** @param rqstno
	/** @return insomnia */
	List<WaqDdlCol> selectWaqC(String rqstno);

	/** @param savevo insomnia */
	int updateidByKey(WaqDdlCol savevo);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(String rqstno);

	int updateInitChgYn(String rqstNo);

	void insertByRqstSnoTsf(WaqDdlTbl saveVo);   

	/**재상신 물리모델컬럼 */
	int insertWaqColRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
}