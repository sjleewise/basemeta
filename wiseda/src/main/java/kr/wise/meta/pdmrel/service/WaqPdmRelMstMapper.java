package kr.wise.meta.pdmrel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqPdmRelMstMapper extends CommonRqstMapper{  
    int insert(WaqPdmRelMst record);

    int insertSelective(WaqPdmRelMst record);

    WaqPdmRelMst selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("pdmRelPnm") String pdmRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int updateByPrimaryKeySelective(WaqPdmRelMst record);

    int updateByPrimaryKey(WaqPdmRelMst record);

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
	List<WaqPdmRelMst> selectPdmRelRqstList(WaqMstr search);

	/** @param search
	/** @return meta */
	WaqPdmRelMst selectPdmRelDetail(WaqPdmRelMst search);

	/** @param savevo
	/** @return meta */
	int deleteByPrimaryKey(WaqPdmRelMst savevo);

	/** @param checkmap3 meta */
	int checkNotDtl(Map<String, Object> checkmap3);

	/** @param rqstno
	/** @return meta */
	List<WaqPdmRelMst> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqPdmRelMst savevo);

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
	int insertByRqstSno(WaqPdmRelMst saveVo);

	/** @param checkmap3 meta */
	int checkNotExistRel(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkRequestRel(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkDupRel(Map<String, Object> checkmap3);

	/** @param savevo
	/** @return meta */
	WaqPdmRelMst selectPdmRelExist(WaqPdmRelMst savevo);

	WaqPdmRelMst selectExistsRqstSno(WaqPdmRelMst savevo);

	WaqPdmRelMst selectMaxRqstSno(WaqPdmRelMst savevo);

	int checkNotChgData(Map<String, Object> checkmap);

	int checkColErr(Map<String, Object> checkmap);

	int updatervwStsCd(WaqPdmRelMst savevo);

	List<WaqPdmRelMst> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqPdmRelMst> list);   

	
}