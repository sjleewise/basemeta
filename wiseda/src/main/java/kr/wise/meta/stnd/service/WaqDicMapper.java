package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaqDicMapper{
	//List<WamDic> selectDicList(WamDic data);
	
	int insertDic(WaqDic savevo);

	List<WaqDic> selectDicList(WaqDic data);

	int delDicList(WaqDic savevo);
	
	
}