package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlGrt;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper 
public interface WaqDdlGrtMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlGrt record);

    int insertSelective(WaqDdlGrt record);

    WaqDdlGrt selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlGrt record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlGrt record);

    int updateByPrimaryKey(WaqDdlGrt record);
    
	/** @param search
	/** @return 유성열 */
	List<WaqDdlGrt> selectDdlGrtListbyMst(WaqMstr search);
	
	/** @param searchVo
	/** @return 유성열 */
	WaqDdlGrt selectDdlGrtDetail(WaqDdlGrt searchVo);

	/** @param saveVo
	/** @return yeonho */
	int deleteByrqstSno(WaqDdlGrt saveVo);

	/** @param rqstNo yeonho */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo yeonho */
	int updateGrtorKeyId(String rqstNo);
	
	int updateGrtedKeyId(String rqstNo);

	/** @param savevo
	/** @return yeonho */
	int updatervwStsCd(WaqDdlGrt savevo);

	/** @param rqstno
	/** @return yeonho */
	List<WaqDdlGrt> selectWaqC(String rqstno);

	/** @param savevo yeonho */
	int updateidByKey(WaqDdlGrt savevo);

	/** @param checkmap yeonho */
	int checkDupGrt(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNotExistGrt(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkRequestGrt(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonDbConnTrg(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNonDbSch(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param reqmst
	/** @param list
	/** @return yeonho */
	List<WaqDdlGrt> selectddlGrtlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlGrt> list);

	/** @param savevo insomnia */
	int updateDdlGrtScriptWaq(WaqDdlGrt savevo);

	WaqDdlGrt selectMaxRqstSno(WaqDdlGrt saveVo);

	void deleteByRqstSno(WaqDdlGrt saveVo);
	
	int updateObjectKeyId(String rqstNo);
	
	int updateObjectGrtYN(String rqstNo);
	
	/** @param checkmap yeonho */
	int checkNotMatchDbms(Map<String, Object> checkmap);
	
	int checkNonObject(Map<String, Object> checkmap);
	
	int checkNotGrt(Map<String, Object> checkmap);
	
	int checkRqstMultiSchema(Map<String, Object> checkmap);
	
	int checkRqstGrantSame(Map<String, Object> checkmap);
	
	int checkRqstGrantMapRole(Map<String, Object> checkmap);
	
	int insertWaqDdlGrtGrtorObjDD(String rqstNo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}