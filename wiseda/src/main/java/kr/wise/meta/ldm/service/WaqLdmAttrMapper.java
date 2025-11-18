package kr.wise.meta.ldm.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqLdmAttrMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WaqLdmAttr record);

    int insert(WaqLdmAttr record); 

    int insertSelective(WaqLdmAttr record);

    WaqLdmAttr selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqLdmAttr record);

    int updateByPrimaryKey(WaqLdmAttr record);

	/** @param search
	/** @return insomnia */
	WaqLdmAttr selectLdmColDetail(WaqLdmAttr search); 

	/** @param search
	/** @return insomnia */
	List<WaqLdmAttr> selectLdmAttrRqstList(WaqMstr search);

	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param rqstNo insomnia */
	int updateTblPnmbyRqstSno(String rqstNo);

	/** @param rqstNo insomnia */
	int updateObjEnm(String rqstNo);
	
	/** @param rqstNo insomnia */
	int updateLdmColPnm(String rqstNo);

	/** @param rqstNo insomnia */
	int updateNonObjEnm(String rqstNo);

	/** @param rqstNo insomnia */
	int updateSditm(String rqstNo);

	/** @param checkmap2 insomnia */
	int checkDupCol(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotExistCol(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkRequestCol(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkColNameLength(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotChgData(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNoDataType(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNoColPnm(Map<String, Object> checkmap2);
	
	/** @param checkmap2 insomnia */
	int checkDupColPnm(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkDupColLnm(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNoSditm(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkPkNotNull(Map<String, Object> checkmap2);
	int checkNumberLenDef(Map<String, Object> checkmap2);

	//시스템공통컬럼 not null체크
	int checkCommonColNotNull(Map<String, Object> checkmap2);
	/** @param rqstno
	/** @return insomnia */
	List<WaqLdmAttr> selectWaqC(@Param("rqstNo") String rqstNo);

	/** @param savevo insomnia */
	int updateidByKey(WaqLdmAttr savevo);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(@Param("rqstNo") String rqstNo);

	/** @param saveVo insomnia */
	int insertByRqstSno(WaqLdmEnty saveVo);
	
	/** @param saveVo insomnia */
	int insertByRqstMartCol(WaqLdmEnty saveVo);

	/** @param saveVo insomnia */
	int deleteByrqstSno(WaqLdmEnty saveVo);

	/** @param rqstNo insomnia */
	int insertnoWaqdelCol(String rqstNo);


	/** @param checkmap2 meta */
	int checkPKColOrdDup(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	void checkColOrdDup(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkPKOrdNonul(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkColOrdNonul(Map<String, Object> checkmap2);

	int updatePkOrd(String rqstNo);

	int updateRqstDcdbyTable(String rqstNo);
	
	/**재상신 물리모델컬럼 */
	int insertWaqColRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	//암호화여부 업데이트
	int updateEncYnisNull(String rqstNo);
	
	//마스터 컬럼 미존재 여부 체크
	int checkMstColExists(Map<String,Object> checkmap2);
	//이력 테이블 컬럼 미존재 여부 체크
	int checkHistColExists(Map<String,Object> checkmap2);
	
	//마스터 이력 컬럼 데이터타입 상이
	int checkColDataTypeDiff(Map<String,Object> checkmap2);
	
		//마스터 이력 컬럼 널여부 상이
	int checkColNullDiff(Map<String,Object> checkmap2);
	
	//마스터 이력 컬럼 디폴트값 상이
	int checkColDefDiff(Map<String,Object> checkmap2);

	//컬럼순서 정비
	int updateColOrd(String rqstNo);

	//데이터타입, 길이 체크
	int checkDataLenScal(Map<String, Object> checkmap2);

	int updateStndDateType(Map<String, Object> checkmap2); 

 
 
}