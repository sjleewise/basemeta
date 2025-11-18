package kr.wise.dq.result.yrdqireg.service;

import java.util.ArrayList;
import java.util.List;


public interface YrDqiRegService {
	
	List<YrDqiRegVO> getRegTbl(YrDqiRegVO search);
	
	int regYrDqiTbl(ArrayList<YrDqiRegVO> list) throws Exception; 
	
	int delYrDqi(ArrayList<YrDqiRegVO> list) throws Exception;

	int checkDup(YrDqiRegVO list) throws Exception;
}
