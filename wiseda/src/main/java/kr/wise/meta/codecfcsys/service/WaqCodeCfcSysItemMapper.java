package kr.wise.meta.codecfcsys.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCodeCfcSysItemMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int insertSelective(WaqCodeCfcSysItem record);

    WaqCodeCfcSysItem selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqCodeCfcSysItem record);
    
    
    List<WaqCodeCfcSysItem> selectCodeCfcSysRqstItemList(WaqMstr search);



	/** @param rqstno
	/** @return meta */
	int updateWaqId(String rqstno);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param savevo meta */
	void updateidByKey(WaqCodeCfcSys savevo);

	/** @param saveVo meta */
	int deleteWaq(WaqCodeCfcSys saveVo);

	/** @param saveVo meta */
	void insertByRqstSno(WaqCodeCfcSys saveVo);

	/** @param saveVo
	/** @return meta */
	int deleteByrqstSno(WaqCodeCfcSysItem saveVo);

	/** @param rqstNo meta */
	int updateRqstDcd(String rqstNo);

	/** @param checkmap2 meta */
	int checkDupItmSeq(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkDupItmNm(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkNotEqualFrmLen(Map<String, Object> checkmap2);

	/** @param checkmap2 meta */
	int checkLastSpt(Map<String, Object> checkmap2);

	/** @param rqstNo meta */
	int updateSeqOrd(String rqstNo);

	/** @param reqmst
	/** @return meta */
	int deleteOldCodeCfcSysItemInfo(WaqMstr reqmst);
    

}