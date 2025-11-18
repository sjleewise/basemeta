package kr.wise.dq.bizrule.service;

import java.util.ArrayList;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqBrDqiMapMapper {
	
    int insertSelective(WaqBrMstr record);

    int updateByPrimaryKeySelective(WaqBrMstr record);

	/** @param record
	/** @return meta */
	int deleteByPrimaryKey(WaqBrMstr record);

	/** @param savevo meta */
	void updatervwStsCd(WaqBrMstr savevo);

	/** @param savevo meta */
	void updateidByKey(WaqBrMstr savevo);

	/** @param rqstno meta */
	void updateWaqCUD(String rqstno);

	/** @param rqstno meta */
	void deleteWAM(String rqstno);

	/** @param rqstno meta */
	void insertWAM(String rqstno);

	/** @param rqstno meta */
	void updateWAH(String rqstno);

	/** @param rqstno meta */
	void insertWAH(String rqstno);

	/** @param rqstNo
	/** @return meta */
	int updateDelId(String rqstNo);

	/** @param rqstNo
	/** @return meta */
	int updateObjInfo(String rqstNo);

	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqBrMstr> list);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	int updateCheckInit(String rqstNo);
	
	int updateVrfCd(WaqMstr mstVo);
}