package kr.wise.meta.ddl.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlPartMain;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlPartMainMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insert(WaqDdlPartMain record);

    int insertSelective(WaqDdlPartMain record);

    WaqDdlPartMain selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqDdlPartMain record);

    int updateByPrimaryKey(WaqDdlPartMain record);

	List<WaqDdlPartMain> selectList(WaqMstr search);

	int deleteByrqstSno(WaqDdlPartMain saveVo);

	int updateCheckInit(String rqstNo);

	int checkDupMainPart(Map<String, Object> checkmap2);

	int checkNotExistMainPart(Map<String, Object> checkmap2);

	int checkDupMainPartPnm(Map<String, Object> checkmap2);

	int checkNotChgData(Map<String, Object> checkmap2);

	int checkNonSubPart(Map<String, Object> checkmap2);

	int deleteByDdlPart(WaqDdlPart saveVo);

	List<WaqDdlPartMain> selectWaqC(String rqstno);

	int updateidByKey(WaqDdlPartMain savevo);

	int updateWaqId(String rqstno);

	int insertByRqstSno(WaqDdlPart saveVo);
	
	int insertByTsfRqstSno(WaqDdlPart saveVo);
	
	int insertByTsfDelRqstSno(WaqDdlPart saveVo);

	int checksubPartTypCdbyRange(Map<String, Object> checkmap2);

	int checkMainPartVal(Map<String, Object> checkmap2);
	
	int checkCountPartValbyRange(Map<String, Object> checkmap2);

	int updateTableSpace(String rqstNo);
	
	int updateBaseInfo(String rqstNo);
	
	int updateDdlMainPartId(String rqstNo);
	
	int updateTblSpacIdByAccDbmsYn(String rqstNo);
	
	int updateTblSpacId(String rqstNo);
	
	int checkNonTblSpac(Map<String, Object> checkmap2);

	int updateWAHbyTbl(String rqstNo);

	int insertWAHbyTbl(String rqstNo);

	int deleteWAMbyTbl(String rqstNo);
	
}