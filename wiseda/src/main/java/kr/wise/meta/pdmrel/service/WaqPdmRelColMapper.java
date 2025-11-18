package kr.wise.meta.pdmrel.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPdmRelColMapper extends CommonRqstMapper{  
    int insert(WaqPdmRelCol record);

    int insertSelective(WaqPdmRelCol record);

    WaqPdmRelCol selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("pdmRelPnm") String pdmRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int updateByPrimaryKeySelective(WaqPdmRelCol record);

    int updateByPrimaryKey(WaqPdmRelCol record);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo meta */
	int updateSubjId(String rqstNo);

	/** @param checkmap3 meta */
	int checkPaSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkPaEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkPaAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkChSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkChEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkChAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotChg(Map<String, Object> checkmap3);

	
	/** @param search
	/** @return meta */
	WaqPdmRelCol selectPdmRelDetail(WaqPdmRelCol search);

	/** @param savevo
	/** @return meta */
	int deleteByPrimaryKey(WaqPdmRelCol savevo);

	/** @param checkmap3 meta */
	int checkNotDtl(Map<String, Object> checkmap3);

	/** @param rqstno
	/** @return meta */
	List<WaqPdmRelCol> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqPdmRelCol savevo);

	/** @param rqstno
	/** @return meta */
	int updateWaqCUD(String rqstno);

	/** @param rqstno
	/** @return meta */
	int updateWaqId(String rqstno);

	/** @param checkmap3 meta */
	int checkNotConformEnty(Map<String, Object> checkmap3);

	/** @param saveVo meta */
	int deleteByrqstSno(WaqPdmTbl saveVo); 

	/** @param saveVo meta */
	int insertByRqstSno(WaqPdmTbl saveVo);

	/** @param checkmap3 meta */
	int checkNotExistRel(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkRequestRel(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkDupRel(Map<String, Object> checkmap3); 

	/** @param savevo
	/** @return meta */
	WaqPdmRelCol selectPdmRelExist(WaqPdmRelCol savevo);
	
	List<WaqPdmRelCol> selectPdmRelColRqstList(WaqMstr search); 

	

	int checkNotChgData(Map<String, Object> checkmap2);

	int updatervwStsCdForCol(String rqstNo);

	int insertDelInit(String rqstNo);

	int updateRqstDcdbyRelMst(String rqstNo);    

	
}