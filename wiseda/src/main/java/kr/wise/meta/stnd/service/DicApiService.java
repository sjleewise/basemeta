package kr.wise.meta.stnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.meta.stnd.web.DicApiCtrl.WaqDics;

public interface DicApiService{

	List<WaqDic> postKoreaApi(WaqDics data);

	List<WaqDic> postNaverApi(WaqDics data);
	
	Map<String, String> regDic(List<WaqDic> list) throws Exception;

	List<WaqDic> getDicRqstList(WaqDic data);
//	List<WaqDic> postKoreaApi(List<String> arrayParams);

	Map<String, String> delDicList(List<WaqDic> list) throws Exception;

	
}
