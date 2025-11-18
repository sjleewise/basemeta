package kr.wise.meta.model.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPdmRelMapper extends CommonRqstMapper{
    int insert(WaqPdmRel record);

    int insertSelective(WaqPdmRel record);

    WaqPdmRel selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("pdmRelPnm") String pdmRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int updateByPrimaryKeySelective(WaqPdmRel record);

    int updateByPrimaryKey(WaqPdmRel record);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo yeonho */
	int updateSubjId(String rqstNo);

	/** @param checkmap3 yeonho */
	int checkPaSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkPaEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkPaAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkChSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkChEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkChAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkNotChg(Map<String, Object> checkmap3);

	/** @param search
	/** @return yeonho */
	List<WaqPdmRel> selectPdmRelRqstList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	WaqPdmRel selectPdmRelDetail(WaqPdmRel search);

	/** @param savevo
	/** @return yeonho */
	int deleteByPrimaryKey(WaqPdmRel savevo);

	/** @param checkmap3 yeonho */
	int checkNotDtl(Map<String, Object> checkmap3);

	/** @param rqstno
	/** @return yeonho */
	List<WaqPdmRel> selectWaqC(String rqstno);

	/** @param savevo yeonho */
	int updateidByKey(WaqPdmRel savevo);

	/** @param rqstno
	/** @return yeonho */
	int updateWaqCUD(String rqstno);

	/** @param rqstno
	/** @return yeonho */
	int updateWaqId(String rqstno);

	/** @param checkmap3 yeonho */
	int checkNotConformEnty(Map<String, Object> checkmap3);

	/** @param saveVo yeonho */
	int deleteByrqstSno(WaqPdmTbl saveVo);

	/** @param saveVo yeonho */
	int insertByRqstSno(WaqPdmTbl saveVo);

	/** @param checkmap3 yeonho */
	int checkNotExistRel(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkRequestRel(Map<String, Object> checkmap3);

	/** @param checkmap3 yeonho */
	int checkDupRel(Map<String, Object> checkmap3);

	/** @param savevo
	/** @return yeonho */
	WaqPdmRel selectPdmRelExist(WaqPdmRel savevo);
}