package kr.wise.advisor.prepare.textcluster.service;

import kr.wise.advisor.prepare.textcluster.service.WadMtcData;
import kr.wise.commons.cmm.annotation.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WadMtcDataMapper {
    int deleteByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcSno") Integer mtcSno);

    int insert(WadMtcData record);

    int insertSelective(WadMtcData record);

    WadMtcData selectByPrimaryKey(@Param("mtcId") String mtcId, @Param("mtcSno") Integer mtcSno);

    int updateByPrimaryKeySelective(WadMtcData record);

    int updateByPrimaryKey(WadMtcData record);

	/** @param srcsave
	/** @param srccolnms
	/** @param srccolval
	/** @return insomnia */
	int insertList(@Param("savevo") WadMtcData srcsave, @Param("colnms") List<String> srccolnms, @Param("colvals") List<String> srccolval);

	/** @param mtcId
	/** @return insomnia 
	 * @param tgtcolnms 
	 * @param srccolnms */
	List<WadMtcData> selectListbyId(@Param("mtcId") String mtcId, @Param("srccolnms") List<String> srccolnms, @Param("tgtcolnms") List<String> tgtcolnms, @Param("iPageNo") long iPageNo, @Param("iPageSize") long iPageSize);

	/** @param search
	/** @param srccolnms
	/** @param tgtcolnms
	/** @return insomnia */
	List<WadMtcData> selectListbySno(@Param("search") WadMtcData search, @Param("srccolnms") List<String> srccolnms, @Param("tgtcolnms") List<String> tgtcolnms);

	/** @param mtcId insomnia */
	int deleteByiD(String mtcId);

	/** @param srcdata
	/** @return insomnia */
	int insertClustData(WadMtcData srcdata);

	/** @param anlVarId
	/** @return insomnia */
	List<WadMtcData> selectClustList(String anlVarId);

	List<WadClstData> selectClstList(String anlVarId);
}