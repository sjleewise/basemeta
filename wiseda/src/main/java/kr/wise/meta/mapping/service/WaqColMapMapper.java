package kr.wise.meta.mapping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.mapping.service.WaqColMap;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqColMapMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqColMap record);

    int insertSelective(WaqColMap record);

    WaqColMap selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqColMap record);

    int updateByPrimaryKey(WaqColMap record);
    
	/** @param searchVo
	/** @return 유성열 */
	WaqColMap selectColMapDetail(WaqColMap searchVo);
	
	/** @param search
	/** @return 유성열 */
	List<WaqColMap> selectColMapListbyMst(WaqMstr search);
	
	/** @param search
	/** @return 유성열 */
	List<WaqColMap> selectTgtColListbyNm(WaqColMap search);
	
	/** @param saveVo
	/** @return 유성열 */
	int deleteByrqstSno(WaqColMap saveVo);
	
	/** @param rqstNo 유성열 */
	int updateCheckInit(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateSrcInfotoBlank(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfo(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfoForXlsC(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtSubjInfoForXlsU(String rqstNo);
	
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtDbInfo(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtColInfo(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtTblId(String rqstNo);
	
	/** @param checkmap 유성열 */
	int checkNotExistColMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtCol(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtTbl(Map<String, Object> checkmap);

	/** @param checkmap */
	int checkNotExistSrcCol(Map<String, Object> checkmap);

	/** @param checkmap */
	int checkNotExistMapJoin(Map<String, Object> checkmap);
	
	/** @param checkmap */
	int checkNotExistSrcTbl(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkDupTblMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkRequestTblMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotChgData(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkAppCrgp(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkExistOmittedTgtCol(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistMapDfId(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtMapTbl(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistAsisMapTbl(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkExistOmittedSrcInfo(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkOmittedDefltVal(Map<String, Object> checkmap);
	
	/** @param savevo
	/** @return 유성열 */
	int updatervwStsCd(WaqColMap savevo);
	
	/** @param savevo
	/** @return 유성열 */
	int updateidByKey(WaqColMap savevo);
	
	/** @param rqstno
	/** @return 유성열 */
	List<WaqColMap> selectWaqC(@Param("rqstNo") String rqstNo);
	
	/** @param reqmst
	/** @param list
	/** @return 유성열 */
	List<WaqColMap> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqColMap> list);

	
}