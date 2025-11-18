package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.app.service.WaqAppSditm;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Mapper
public interface WaqAppSditmMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(WaqAppSditm saveVo);

    int insert(WaqAppSditm record);

    int insertSelective(WaqAppSditm record);

    WaqAppSditm selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqAppSditm record);

    int updateByPrimaryKey(WaqAppSditm record);
    
    WaqAppSditm selectStndItemRqstDetail(WaqAppSditm searchVo);
    
    List<WaqAppSditm> selectItemRqstListbyMst(WaqMstr search);
    
	int updatervwStsCd(WaqAppSditm savevo);
	
	List<WaqAppSditm> selectWaqC(@Param("rqstNo") String rqstno);

	ArrayList<WaqAppSditm> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqAppSditm> list);
	
	int updateidByKey(WaqAppSditm savevo);
	
    int updateCheckInit(String rqstNo);

    int updateLnmPnm(String rqstNo);
    
	int checkRequestDmn(Map<String, Object> checkmap);
	
	int checkDupSditm(Map<String, Object> checkmap);
	
	int checkNotExistSditm(Map<String, Object> checkmap);
	
	int checkLnmSymn(Map<String, Object> checkmap);

	int checkExistStwd(Map<String, Object> checkmap);

	int checkDupSditmPnm(Map<String, Object> checkmap);

	int checkSditmPnmMaxLen(Map<String, Object> checkmap);

	int checkSditmLnmMaxLen(Map<String, Object> checkmap);

	int checkSditmPnmStrNum(Map<String, Object> checkmap);

	int checkNotChgData(Map<String, Object> checkmap);
	
	int checkDataLen(Map<String, Object> checkmap);
	
	int checkDataType(Map<String, Object> checkmap);
}