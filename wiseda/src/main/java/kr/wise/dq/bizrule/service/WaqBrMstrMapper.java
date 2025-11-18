package kr.wise.dq.bizrule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.criinfo.ctq.service.WaqCtqVO;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqBrMstrMapper extends CommonRqstMapper {

    int insertSelective(WaqBrMstr record);

    WaqBrMstr selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqBrMstr record);

	/** @param search
	/** @return meta */
	List<WaqBrMstr> selectrqstBizruleListbyMst(WaqMstr search);


	/** @param record
	/** @return meta */
	int deleteByPrimaryKey(WaqBrMstr record);

	/** @param rqstNo
	/** @return meta */
	int updateCheckInit(String rqstNo);
	
	/** @param rqstNo
	/** @return meta */
	int updateDbConnId(String rqstNo);

	/** @param rqstNo
	/** @return meta */
	int updateObjInfo(String rqstNo);

	/** @param checkmap
	/** @return meta */
	int checkNoChg(Map<String, Object> checkmap);

	/** @param checkmap
	/** @return meta */
	int checkDupBizrule(Map<String, Object> checkmap);

	/** @param checkmap
	/** @return meta */
	int checkNotExistBizrule(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfCd(WaqMstr mstVo);

	/** @param checkmap
	/** @return meta */
	int checkNoConnTrg(Map<String, Object> checkmap);

	/** @param checkmap
	/** @return meta */
	int checkNoCrgpUserId(Map<String, Object> checkmap);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqBrMstr savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqBrMstr> selectWaqC(String rqstno);

	/** @param savevo
	/** @return meta */
	int updateidByKey(WaqBrMstr savevo);

	/** @param rqstNo
	/** @return meta */
	int updateDbSchId(String rqstNo);

	/** @param checkmap
	/** @return meta */
	int checkNoDbcTbl(Map<String, Object> checkmap);

	/** @param checkmap
	/** @return meta */
	int checkNoDbcCol(Map<String, Object> checkmap);

	/** @param rqstNo
	/** @return meta */
	int updateDelId(String rqstNo);


	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqBrMstr> list);

	int updateBizAreaId(String rqstNo);

	int updateDqiId(String rqstNo);

	int updateCtqId(String rqstNo);
	
	//담당자ID UPDATE
	int updateBrCrgpUserId(String rqstNo);

	int checkNotExistBizarea(Map<String, Object> checkmap);

	int checkNotExistDqi(Map<String, Object> checkmap);

	int checkNotExistCtq(Map<String, Object> checkmap);

	int updateTgtDbConnId(String rqstNo);

	int checkNoTgtInfo(Map<String, Object> checkmap);
	
	int checkTtgConnTrg(Map<String, Object> checkmap);

	int checkNoTgtDbcTbl(Map<String, Object> checkmap);

	int checkNoTgtDbcCol(Map<String, Object> checkmap);
	
	int checkDupCrgpUserNm(Map<String, Object> checkmap);

	void checkErrorSql(Map<String, Object> checkmap);

	void updateVrf(Map<String, Object> checkmap);

	List<WaqBrMstr> selectConDbList(String dbConnTrgId);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );

	int updateUseYn(String rqstNo);
}