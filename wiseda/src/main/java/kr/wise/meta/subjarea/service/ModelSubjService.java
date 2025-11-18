package kr.wise.meta.subjarea.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;

public interface ModelSubjService {
	
	public List<WaaSubj> getSubjList(WaaSubj search);
	
	public WaaSubj findSubj(WaaSubj search);
	
	public int regSubjList(List<WaaSubj> list) throws Exception;
	
	public int delSubjList(List<WaaSubj> list) throws Exception;

	public List<WaaSubj> getMetaSubjList(WaaSubj search);
	
	// dbmsTypCd
	public String getDbmsTypCd(String subjLnm);
	
	//주제영역권한신청 190425
	public List<WaaSubj> getsubjOnwerlist(WaaSubj search);
	
	public List<WaaSubj> getsubjOnwerDetList(WaaSubj search);
	
	public int regSubjOwnerList(List<WaaSubj> list) throws Exception;
	public int delSubjOwnerList(List<WaaSubj> list) throws Exception;
	
	//관심주제영역권한
	public List<WaaSubj> getsubjFavoritelist(WaaSubj search);
	
	//관심주제영역권한등록 
	public int registerSubjFavorite(WaqMstr mstVo, List<?> reglist) throws Exception;
	
	public int saveSubjFavoriteRqst(WaaSubj mstVo) throws Exception;

	public int delSubjFavorite(WaqMstr reqmst, ArrayList<WaaSubj> list) throws Exception;
	
	public int subjFavoriteCntBySubjId(WaaSubj search) throws Exception;

}
