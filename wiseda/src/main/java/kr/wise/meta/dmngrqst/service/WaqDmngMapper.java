package kr.wise.meta.dmngrqst.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.dmngrqst.service.WaqDmng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaqDmngMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Short rqstSno);

    int insert(WaqDmng record);

    int insertSelective(WaqDmng record);

    WaqDmng selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Short rqstSno);

    int updateByPrimaryKeySelective(WaqDmng record);

    int updateByPrimaryKey(WaqDmng record);

	void updateCheckInit(String rqstNo);

	void checkNotExistDmng(Map<String, Object> checkmap);

	void checkDupDmng(Map<String, Object> checkmap);

	void checkRequestDmng(Map<String, Object> checkmap);

	void checkNotChgData(Map<String, Object> checkmap);

	void checkInfoTypeErr(Map<String, Object> checkmap);

	void checkExistsUppDmng(Map<String, Object> checkmap);
	
	void checkDmngLvl(Map<String, Object> checkmap);
	
	int updatervwStsCd(WaqDmng savevo);

	int insertWaqRejected(WaqMstr reqmst, String oldRqstNo);

	List<WaqDmng> selectWaqC(String rqstno);

	void updateidByKey(WaqDmng savevo);

	int updateWaqId(String rqstno);

	WaqDmng selectDmngDetail(WaqDmng searchVo);

	List<WaqDmng> selectDmngListbyMst(WaqMstr search);

	List<WaqDmng> selectwamlist(WaqMstr reqmst, ArrayList<WaqDmng> list);

	boolean checkEmptyRqst(String rqstNo);

	int deleteByrqstSno(WaqDmng saveVo);
}