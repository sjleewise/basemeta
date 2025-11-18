package kr.wise.meta.model.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
@Mapper
public interface WaqAsisDmnCdValMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WaqAsisDmnCdVal savevo);

    int insert(WaqAsisDmnCdVal record);

    int insertSelective(WaqAsisDmnCdVal record);

    WaqAsisDmnCdVal selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqAsisDmnCdVal record);

    int updateByPrimaryKey(WaqAsisDmnCdVal record);

	int insertWaqRejected(WaqMstr reqmst, String oldRqstNo);

	List<WaqAsisDmnCdVal> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqAsisDmnCdVal> list);

	WaqAsisDmnCdVal selectDmnCdValDetail(WaqAsisDmnCdVal searchVo);

	List<WaqAsisDmnCdVal> selectDmnCdValListbyMst(WaqMstr search);

	void updateCheckInit(String rqstNo);

	int updatervwStsCd(WaqAsisDmnCdVal savevo);

	List<WaqAsisDmnCdVal> selectWaqC(String rqstno);

	void checkNotChgData(Map<String, Object> checkmap);

}