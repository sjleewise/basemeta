package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmStwdAbrMapper {
    int insert(WdqmStwdAbr record);

    int insertSelective(WdqmStwdAbr record);
    
    List<WdqmStwdAbr> selectStwdAbbreviatedList(WdqmStwdAbr record);

	WdqmStwdAbr checkOverlap(@Param("checkWord")String checkWord,@Param("stndAsrt")String stndAsrt);
	
	int insertStndWordByAbr(WdqmStwdAbr record);
	
	int delStwdAbrLst(WdqmStwdAbr record);
}