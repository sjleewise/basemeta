package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.stnd.service.WaqActnMsg;
@Mapper
public interface WaqActnMsgMapper extends CommonRqstMapper{
    int insert(WaqActnMsg record);

	int insertSelective(WaqActnMsg record);

	int deleteByPrimaryKey(WaqActnMsg saveVo);

	List<WaqActnMsg> selectMsgRqstListbyMst(WaqMstr search);
	
	WaqActnMsg selectMsgRqstDetail(WaqActnMsg searchVo);
	
    WaqActnMsg selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);
    
    int updateByPrimaryKeySelective(WaqActnMsg record);

    int updateByPrimaryKey(WaqActnMsg record);
    
	ArrayList<WaqActnMsg> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqActnMsg> list);

	int updatervwStsCd(WaqActnMsg savevo);

	List<WaqActnMsg> selectWaqC(@Param("rqstNo") String rqstno);

	int updateidByKey(WaqActnMsg savevo);

	int updateCheckInit(String rqstNo);
	
	
	int checkDupMsg(Map<String, Object> checkmap);
	
	int checkNotExistMsg(Map<String, Object> checkmap);

	int checkNotChgData(Map<String, Object> checkmap);

	int checkRequestMsg(Map<String, Object> checkmap);
}