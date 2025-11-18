package kr.wise.meta.ddl.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqDdlRelMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("ddlRelPnm") String ddlRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int insert(WaqDdlRel record);

    int insertSelective(WaqDdlRel record);

    WaqDdlRel selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno, @Param("ddlRelPnm") String ddlRelPnm, @Param("paEntyPnm") String paEntyPnm, @Param("paAttrPnm") String paAttrPnm, @Param("chEntyPnm") String chEntyPnm, @Param("chAttrPnm") String chAttrPnm);

    int updateByPrimaryKeySelective(WaqDdlRel record);

    int updateByPrimaryKey(WaqDdlRel record);

	/** @param saveVo meta */
	int insertByRqstSno(WaqDdlTbl saveVo);

	/** @param saveVo meta */
	int deleteByRqstSno(WaqDdlTbl saveVo);

	/** @param rqstNo meta */
	int updateCheckInit(String rqstNo);

	/** @param checkmap3 meta */
	int checkNotChgData(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistPaSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistPaEnty(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistPaAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistChSubj(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistChAttr(Map<String, Object> checkmap3);

	/** @param checkmap3 meta */
	int checkNotExistChEnty(Map<String, Object> checkmap3);

	/** @param rqstNo meta */
	int updateSubjId(String rqstNo);

	/** @param search
	/** @return meta */
	List<WaqDdlRel> selectDdlRelRqstList(WaqMstr search);

	/** @param rqstno
	/** @return meta */
	List<WaqDdlRel> selectWaqC(String rqstno);

	/** @param savevo meta */
	int updateidByKey(WaqDdlRel savevo);

	/** @param rqstno
	/** @return meta */
	int updateWaqId(String rqstno);

	/** @param rqstNo meta */
	int insertDelInit(String rqstNo);

	/** @param savevo
	/** @return meta */
	int deleteByPrimaryKey(WaqDdlRel savevo);


}