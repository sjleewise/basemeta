package kr.wise.meta.ddltsf.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlTsfRelMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("ddlRelPnm") String ddlRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int insert(WaqDdlTsfRel record);

    int insertSelective(WaqDdlTsfRel record);

    WaqDdlTsfRel selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("ddlRelPnm") String ddlRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int updateByPrimaryKeySelective(WaqDdlTsfRel record);

    int updateByPrimaryKey(WaqDdlTsfRel record);

	/** @param saveVo yeonho */
	int insertByRqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param saveVo yeonho */
	int deleteByRqstSnoTsf(WaqDdlTsfTbl saveVo);

	/** @param search
	/** @return yeonho */
	List<WaqDdlTsfRel> selectDdlTsfRelRqstList(WaqMstr search);

	/** @param rqstNo yeonho */
	int updateSubjId(String rqstNo);

	/** @param rqstNo yeonho */
	int updateEntyAttrId(String rqstNo);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param checkmap3 yeonho */
	int checkNotExistPaSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistPaEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistPaAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistChSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistChEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotExistChAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotChgData(Map<String, Object> checkmap3);

	/** @param rqstno
	/** @return yeonho */
	int updateWaqId(String rqstno);
}