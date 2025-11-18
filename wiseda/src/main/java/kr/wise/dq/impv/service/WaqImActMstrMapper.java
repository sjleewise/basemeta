package kr.wise.dq.impv.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqImActMstrMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqImActMstr record);

    int insertSelective(WaqImActMstr record);

    WaqImActMstr selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqImActMstr record);

    int updateByPrimaryKey(WaqImActMstr record);

	/** @param search
	/** @return meta */
	List<WaqImActMstr> selectImRslList(WaqImActMstr search);

	/** @param record
	/** @return meta */
	WaqImActMstr selectIsNew(WaqImActMstr record);

	/** @param search
	/** @return meta */
	List<WaqImActMstr> selectImRslRqstList(WaqMstr search);

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

	/** @param checkmap
	/** @return meta */
	int checkNoData(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfCd(WaqMstr mstVo);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqImActMstr savevo);


	/** @param search
	/** @return meta */
	List<WaqImActMstr> selectRsl(WaqImActMstr search);

	/** @param search
	/** @return meta */
	List<WaqImActMstr> selectRslList(WaqImActMstr search);

	/**
	 * @param anaJobId
	 * @return
	 */
	List<WaqImActMstr> getImplHstLst(WaqImActMstr mstr);

	/**
	 * @param anaJobId
	 * @return
	 */
	List<WaqImActMstr> getImrslHstLst(WaqImActMstr mstr);
}