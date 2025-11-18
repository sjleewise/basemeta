package kr.wise.dq.criinfo.anatrg.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDbSchMapper {

	List<WaaDbSch> selectList(WaaDbSch search);

	//DBMS관리 곽효신
	List<WaaDbSch> selectconntrglist(WaaDbSch search);

	List<WaaDbSch> selectListByFK(String dbConnTrgId);

	WaaDbSch selectByPrimaryKey(String dbSchId);

	int updateExpDtm(String dbSchId);

	int insertSelective(WaaDbSch record);

	/** @param search
	/** @return insomnia */
	List<WaaDbSch> selectSchemaList(WaaDbSch search);

	List<WaaDbSch> selectDevSubjSchemaList(WaaDbSch search); 

	WaaDbSch selectBySchPnm(@Param("dbConnTrgId") String dbConnTrgId, @Param("dbSchPnm") String dbSchPnm);
}