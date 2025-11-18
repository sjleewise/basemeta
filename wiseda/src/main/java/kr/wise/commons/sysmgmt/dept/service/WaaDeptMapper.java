package kr.wise.commons.sysmgmt.dept.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaDeptMapper {

	int deleteAll();

	int deleteByPrimaryKey(String DeptId);

    int insert(WaaDept record);

    int insertSelective(WaaDept record);

    WaaDept selectByPrimaryKey(String DeptId);

    int updateByPrimaryKeySelective(WaaDept record);

    int updateByPrimaryKey(WaaDept record);

    int deleteRegTypCd(WaaDept record);

    List<WaaDept> selectList(WaaDept record);

	void updateAllDeptNm(@Param("deptId") String deptId);
	
	int updateExpDtm(WaaDept record);

	WaaDept selectByUppDeptNm(WaaDept record);

	WaaDept selectDeptIdByDeptNm(@Param("deptNm")String deptNm);

	int selectDeptCnt(WaaDept waaDept);


}