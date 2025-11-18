package kr.wise.meta.ddl.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlPartSubMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlPartSub record);

    int insertSelective(WaqDdlPartSub record);

    WaqDdlPartSub selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlPartSub record);

    int updateByPrimaryKey(WaqDdlPartSub record);

	int updateCheckInit(String rqstNo);

	int deleteByrqstSno(WaqDdlPartSub saveVo);

	List<WaqDdlPartSub> selectList(WaqMstr search);

	int checkDupSubPart(Map<String, Object> checkmap2);

	int checkNotExistSubPart(Map<String, Object> checkmap2);

	int checkNotChgData(Map<String, Object> checkmap2);

	int checkNonMainPartPnm(Map<String, Object> checkmap2);
	
	int deleteByDdlPart(WaqDdlPart saveVo);

	int deleteByPartMain(WaqDdlPartMain saveVo);

	List<WaqDdlPartSub> selectWaqC(String rqstno);

	int updateidByKey(WaqDdlPartSub savevo);

	int updateWaqId(String rqstno);
	
	int updateWaqId2(String rqstno);

	int insertByRqstSno(WaqDdlPart saveVo);
	
	int insertByTsfRqstSno(WaqDdlPart saveVo);
	
	int insertByTsfDelRqstSno(WaqDdlPart saveVo);
	
	int updateRqstSnoByPart(String rqstNo);
	int updateRqstSnoByPart2(String rqstNo);
	
	int updateBaseInfo(String rqstno);
	
	int updateDdlSubPartId(String rqstno);

	int updateWAHbyTbl(String rqstNo);

	int insertWAHbyTbl(String rqstNo);

	int deleteWAMbyTbl(String rqstNo);

	int checkSubPartVal(Map<String, Object> checkmap3);
}