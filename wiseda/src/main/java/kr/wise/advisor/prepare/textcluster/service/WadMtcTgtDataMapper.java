package kr.wise.advisor.prepare.textcluster.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadMtcTgtDataMapper {
    int deleteByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcSno") Integer mtcSno, @Param("tgtDtlSno") Integer tgtDtlSno);

    int insert(WadMtcTgtData record);

    int insertSelective(WadMtcTgtData record);

    WadMtcTgtData selectByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcSno") Integer mtcSno, @Param("tgtDtlSno") Integer tgtDtlSno);

    int updateByPrimaryKeySelective(WadMtcTgtData record);

    int updateByPrimaryKey(WadMtcTgtData record);

	/** @param tgtsave
	/** @param tgtcolnms
	/** @param tgtcolval
	/** @return insomnia */
	int insertList(@Param("savevo") WadMtcTgtData tgtsave, @Param("colnms") List<String> tgtcolnms, @Param("colvals") List<String> tgtcolval);

	/** @param mtcId insomnia */
	int deleteByiD(String mtcId);

	/** @param tgtdata
	/** @return insomnia */
	int insertClustData(WadMtcTgtData tgtdata);

	int insertClstData(WadClstData clstdata);

	int updateClstYn(WadClstData clstvo);
}