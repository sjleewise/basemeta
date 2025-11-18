package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.app.service.WaqAppStwd;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Mapper
public interface WaqAppStwdMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqAppStwd record);

    int insertSelective(WaqAppStwd record);

    WaqAppStwd selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqAppStwd record);

    int updateByPrimaryKey(WaqAppStwd record);
    
	List<WaqAppStwd> selectrqstStndWordListbyMst(WaqMstr search);
	
	WaqAppStwd selectRqstStndWord(WaqAppStwd searchVO);
	
	int deleteByrqstSno(WaqAppStwd saveVo);
	
	int deletebyRqstSno(@Param("ids") Integer[] ids, @Param("rqstNo") String rqstNo);
	
	int updatervwStsCd(WaqAppStwd savevo);
	
	List<WaqAppStwd> selectWaqC(@Param("rqstNo") String rqstNo);
	
	int updateidByKey(WaqAppStwd savevo);
	
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqAppStwd> list);
	
	List<WaqAppStwd> selectListByStwdLnm(@Param("rqstNo") String rqstNo, @Param("appStwdLnm") String appStwdLnm);
    
    // 검증쿼리
    int updateCheckInit(String rqstNo);

	int checkRequestStwd(Map<String, Object> checkmap);
	
	int checkDupStwd(Map<String, Object> checkmap);
	
	int checkNotExistStwd(Map<String, Object> checkmap);
	
	int checkLnmExistSpac(Map<String, Object> checkmap);

	int checkPnmExistSpac(Map<String, Object> checkmap);

	int checkLnmMaxLen(Map<String, Object> checkmap);

	int checkPnmMaxLen(Map<String, Object> checkmap);

	int checkNoChg(Map<String, Object> checkmap);
	
	int updateWAM(WaqMstr savevo);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}