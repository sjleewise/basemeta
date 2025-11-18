package kr.wise.meta.ddletc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WaqDdlTbl;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WaqDdlEtcMapper.java
 * 3. Package  : kr.wise.meta.ddletc.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 29. 오후 3:03:03
 * </PRE>
 */ 
@Mapper
public interface WaqDdlEtcMapper {

	int insertSelective(WaqDdlEtc saveVo);

	int updateByPrimaryKeySelective(WaqDdlEtc saveVo);

	int deleteByrqstSno(WaqDdlEtc saveVo);

	List<WaqDdlEtc> selectDdlEtcRqstList(WaqMstr search);

	int updateCheckInit(String rqstNo);

	int checkDupTbl(Map<String, Object> checkmap);

	int checkNotExistTbl(Map<String, Object> checkmap);

	int checkRequestTbl(Map<String, Object> checkmap);

	int updateDbSchId(String rqstNo);

	WaqDdlEtc selectDdlEtcRqstDetail(WaqDdlEtc searchVo);
	
	int updatervwStsCd(WaqDdlEtc savevo);

	List<WaqDdlEtc> selectWaqC(String rqstno);

	int updateidByKey(WaqDdlEtc savevo);

	int updateWaqCUD(String rqstno);

	int deleteWAM(String rqstno);

	int insertWAM(String rqstno);

	int updateWAH(String rqstno);

	int insertWAH(String rqstno);

	ArrayList<WaqDdlEtc> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlEtc> list);        

	int checkNotChgData(Map<String, Object> checkmap);

	int updateDdlScriptWaq(WaqDdlEtc savevo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
}