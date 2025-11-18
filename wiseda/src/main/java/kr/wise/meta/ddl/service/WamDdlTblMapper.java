package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlTblMapper.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 24. 오후 6:50:53
 * </PRE>
 */ 
@Mapper
public interface WamDdlTblMapper {
    int deleteByPrimaryKey(String ddlTblId);

    int insert(WamDdlTbl record);

    int insertSelective(WamDdlTbl record);

    WamDdlTbl selectByPrimaryKey(@Param("ddlTblId")String ddlTblId, @Param("rqstNo")String rqstNo);

    int updateByPrimaryKeySelective(WamDdlTbl record);

    int updateByPrimaryKeyWithBLOBs(WamDdlTbl record);

    int updateByPrimaryKey(WamDdlTbl record);
    
    List<WamDdlTbl> selectList(WamDdlTbl record);

	List<WamDdlTbl> selectDdlTblChange(String ddlTblId);

	/** @param search
	/** @return meta */
	List<WamDdlTbl> selectDdlTblRqstList(WamDdlTbl search);

	/** @param record
	/** @return meta */
	int updateDdlTblRqstPrc(WamDdlTbl record);

	/** @param search
	/** @return meta */
	List<WamDdlTbl> selectDdlTblHistList(WamDdlTbl search);

	/** @param search
	/** @return meta */
	WamDdlTbl selectDdlTblHistDetail(WamDdlTbl search);
	
	//파티션테이블 리스트 셀렉트
	List<WamDdlTbl> selectPartitionTblList(WamDdlTbl search);
	
	//파티션테이블 여부 저장
	int updatePartTblRqstPrc(WamDdlTbl record);
	
	int updatePartTblRqstPrcWam(WamDdlTbl record);
	
	//개인정보 분리보관 리스트 조회
	List<WamDdlTbl> selectPciYnList(WamDdlTbl search);
	
	//분리보관여부 저장
	int updatePciYnPrc(WamDdlTbl record);
	
	int updatePciYnPrcWam(WamDdlTbl record);

	List<WamDdlTbl> selectDelDdlTsfTblListForRqst(WamDdlTbl search);

	List<WamDdlTbl> selectDdlTsfTblListForRqst(WamDdlTbl search);

	List<WamDdlTbl> selectDdlTblListForPart(WamDdlTbl record);

	List<WamDdlTbl> selectDdlAllObjForGrtList(WamDdlTbl search);  
}