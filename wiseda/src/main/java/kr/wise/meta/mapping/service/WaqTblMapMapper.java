package kr.wise.meta.mapping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.mapping.service.WaqTblMap;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqTblMapMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqTblMap record);

    int insertSelective(WaqTblMap record);

    WaqTblMap selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqTblMap record);

    int updateByPrimaryKey(WaqTblMap record);
    
	/** @param saveVo
	/** @return 유성열 */
	int deleteByrqstSno(WaqTblMap saveVo);
	
	/** @param searchVo
	/** @return 유성열 */
	WaqTblMap selectTblMapDetail(WaqTblMap searchVo);
	
	/** @param rqstNo 유성열 */
	int updateCheckInit(String rqstNo);
	
	/** @param checkmap 유성열 */
	int checkNotExistTblMap(Map<String, Object> checkmap);

	int checkNotExistColMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtTbl(Map<String, Object> checkmap);

	/** @param checkmap  */
	int checkNotExistSrcTbl(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkDupTblMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkRequestTblMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotChgData(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkAppCrgp(Map<String, Object> checkmap);
	
	/** @param search
	/** @return 유성열 */
	List<WaqTblMap> selectTblMapListbyMst(WaqMstr search);
	
	/** @param search
	/** @return 유성열 */
	List<WaqTblMap> selectTgtTblListbyNm(WaqTblMap search);
	
	/** @param savevo
	/** @return 유성열 */
	int updatervwStsCd(WaqTblMap savevo);
	
	/** @param rqstno
	/** @return 유성열 */
	List<WaqTblMap> selectWaqC(@Param("rqstNo") String rqstNo);
	
	/** @param savevo 유성열 */
	int updateidByKey(WaqTblMap savevo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateMapDfId(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateSrcInfotoBlank(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfo(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtDbInfo(String rqstNo);
	
	/** @param reqmst
	/** @param list
	/** @return 유성열 */
	List<WaqTblMap> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqTblMap> list);

	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfoForXlsC(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfoForXlsU(String rqstNo);

	int updateTgtTblId(String rqstNo);

}