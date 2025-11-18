package kr.wise.advisor.prepare.udefoutlier.script;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WadUodLnkMapper {

	int deleteByPrimaryKey(String udfOtlDtcId);

	int insert(WadUodLnk record);

	int insertSelective(WadUodLnk record);

	WadUodLnk selectByPrimaryKey(String udfOtlDtcId);

	int updateByPrimaryKeySelective(WadUodLnk record);

	int updateByPrimaryKey(WadUodLnk record);

	List<WadUodLnk> selectUdefOutlierList(WadUodLnk search);

	List<WadUodLnk> selectUodLnkList(String udfOtlDtcId);

	List<WadUodLnk> selectUodLnkCompList(String udfOtlDtcId);

	WadUodLnk selectUodLnkCompDetail(WadUodLnk lnkVo);

	WadUodLnk selectUodLnkCompFrom(WadUodLnk lnkVo);

	WadUodLnk selectUodLnkCompTo(WadUodLnk lnkVo);        

}
