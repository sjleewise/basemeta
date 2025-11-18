package kr.wise.meta.mapping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.mapping.service.WaqCodMap;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCodMapMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqCodMap record);

    int insertSelective(WaqCodMap record);

    WaqCodMap selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqCodMap record);

    int updateByPrimaryKey(WaqCodMap record);
    
    /** @param searchVo
	/** @return 유성열 */
    WaqCodMap selectCodMapDetail(WaqCodMap searchVo);
    
    /** @param search
	/** @return 유성열 */
	List<WaqCodMap> selectCodMapListbyMst(WaqMstr search);
	
	/** @param searchVo
	/** @return 유성열 */
	List<WaqCodMap> selectTgtCdDmnListbyNm(WaqCodMap searchVo);
	
	/** @param saveVo
	/** @return 유성열 */
	int deleteByrqstSno(WaqCodMap saveVo);
	
	/** @param rqstNo 유성열 */
	int updateCheckInit(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtDmnInfo(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateTgtInfoByCnvsType(String rqstNo);
	
	/** @param rqstno
	/** @return 유성열 */
	int updateSrcInfoByCnvsType(String rqstNo);
	
	/** @param checkmap 유성열 */
	int checkDupCodMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistCodMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkRequestCodMap(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtCdVal(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistTgtDmn(Map<String, Object> checkmap);
	
	/** @param checkmap */
	int checkNotExistSrcCdVal(Map<String, Object> checkmap);
	
	/** @param checkmap */
	int checkNotExistSrcDmn(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkRqstUser(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotChgData(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkNotExistSrcInfo(Map<String, Object> checkmap);
	
	int checkNotExistSrcInfo1(Map<String, Object> checkmap);
	
	/** @param checkmap 유성열 */
	int checkAppCrgp(Map<String, Object> checkmap);
	
	/** @param savevo
	/** @return 유성열 */
	int updatervwStsCd(WaqCodMap savevo);
	
	/** @param rqstno
	/** @return 유성열 */
	List<WaqCodMap> selectWaqC(@Param("rqstNo") String rqstNo);
	
	/** @param savevo 유성열 */
	int updateidByKey(WaqCodMap savevo);
	
	/** @param reqmst
	/** @param list
	/** @return 유성열 */
	List<WaqCodMap> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqCodMap> list);

	
}