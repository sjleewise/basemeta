package kr.wise.meta.codecfcsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.codecfcsys.service.WaqCodeCfcSys;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCodeCfcSysMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insertSelective(WaqCodeCfcSys record);

    WaqCodeCfcSys selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqCodeCfcSys record);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSys> selectCodeCfcSysListbyMst(WaqMstr search);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqCodeCfcSys savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqCodeCfcSys> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqCodeCfcSys savevo);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param reqmst
	/** @param list
	/** @return meta */
	List<WaqCodeCfcSys> selectwamlist(@Param("reqmst")WaqMstr reqmst, @Param("list")ArrayList<WaqCodeCfcSys> list);

	/** @param checkmap meta */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkRequestCcs(Map<String, Object> checkmap);



	/** @param checkmap meta */
	int checkItemErr(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkNotExistCcs(Map<String, Object> checkmap);

	/** @param checkmap meta */
	int checkDupCcs(Map<String, Object> checkmap);

	/** @param reqmst
	/** @return meta */
	int deleteOldCodeCfcSysInfo(WaqMstr reqmst);


}