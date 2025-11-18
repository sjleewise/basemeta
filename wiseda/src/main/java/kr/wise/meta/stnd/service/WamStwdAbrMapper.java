package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamStwdAbrMapper {
    int insert(WamStwdAbr record);

    int insertSelective(WamStwdAbr record);
    
    List<WamStwdAbr> selectStwdAbbreviatedList(WamStwdAbr record);

	WamStwdAbr checkOverlap(@Param("checkWord")String checkWord,@Param("stndAsrt")String stndAsrt);
	
	int insertStndWordByAbr(WamStwdAbr record);
	
	int delStwdAbrLst(WamStwdAbr record);
}