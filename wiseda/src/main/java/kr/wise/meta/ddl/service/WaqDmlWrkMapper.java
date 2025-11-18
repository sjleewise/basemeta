package kr.wise.meta.ddl.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaqDmlWrkMapper extends CommonRqstMapper  {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDmlWrk record);

    int insertSelective(WaqDmlWrk record);

    WaqDmlWrk selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDmlWrk record);

    int updateByPrimaryKeyWithBLOBs(WaqDmlWrk record);

    int updateByPrimaryKey(WaqDmlWrk record);

	int deleteByrqstSno(WaqDmlWrk saveVo);

	void updateCheckInit(String rqstNo);

	List<WaqDmlWrk> selectWaqC(String rqstno);

	int updatervwStsCd(WaqDmlWrk savevo);

	void updateidByKey(WaqDmlWrk savevo);

	List<WaqDmlWrk> selectDdlListbyMst(WaqMstr search);

	WaqDmlWrk selectDdlTblDetail(WaqDmlWrk searchVo);

}