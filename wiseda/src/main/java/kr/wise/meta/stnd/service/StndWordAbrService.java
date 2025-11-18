package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;

public interface StndWordAbrService {

	public String delSpec(String inLine);
	
	public String getConsonant(String input);
	
	public ArrayList generateInitEnm(String orglEnm, String consEnm, String Length, String rule,String stndAsrtArr) throws Exception;
	
	public boolean isNumber(String number);
	
	public boolean isExist(String[] termInitEnm, String genEnm, int tmpi);
	
	public String selectDic(String checkWord, String stndAsrt) throws Exception;
	
	public ArrayList getStringtoArray(String inEnm);
	
	public boolean isVowel(char cinput);
	
	public int regStndWordByAbr(WaqMstr waqMstr, ArrayList<WamStwdAbr> list);  
	
	List<WamStwdAbr> selectStwdAbbreviatedList(WamStwdAbr searchVO);
	
	Map<String, String> delStwdAbrLst(List<WamStwdAbr> list)  throws Exception;

}
