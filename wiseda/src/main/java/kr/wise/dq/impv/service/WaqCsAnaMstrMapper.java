package kr.wise.dq.impv.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.bizrule.service.WamBrMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCsAnaMstrMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqCsAnaMstr record);

    int insertSelective(WaqCsAnaMstr record);

    WaqCsAnaMstr selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqCsAnaMstr record);

    int updateByPrimaryKey(WaqCsAnaMstr record);

	/** @param record
	/** @return meta */
	int deleteByPrimaryKey(WaqCsAnaMstr record);

	/** @param rqstNo
	/** @return meta */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo
	/** @return meta */
	int updateObjInfo(String rqstNo);

	/** @param checkmap
	/** @return meta */
	int checkNoChg(Map<String, Object> checkmap);

	/** @param checkmap
	/** @return meta */
	int checkDupJobId(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfCd(WaqMstr mstVo);

	/** @param search
	/** @return meta */
	List<WaqCsAnaMstr> selectImPlRqstList(WaqMstr search);



	/** @param record
	/** @return meta */
	WaqCsAnaMstr selectIsNew(WaqCsAnaMstr record);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqCsAnaMstr savevo);

	/** @param checkmap
	/** @return meta */
	int checkNoData(Map<String, Object> checkmap);

	/** @param search
	/** @return meta */
	List<WamBrMstr> selectErrBizrule(WamBrMstr search);
}