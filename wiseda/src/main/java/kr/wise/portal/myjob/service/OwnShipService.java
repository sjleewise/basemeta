package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;


public interface OwnShipService {

	//데이터 리스트
	public List<TblChangeVO> selectTblChangeParam(Map<String, Object> param)throws Exception;
	//데이터 갯수
	public TblChangeVO selectTblChangeSub(Map<String, Object> param)throws Exception;
	//담당자ID 및 담당자 변경
	public void ownershipUpdate(Map<String, String> param)throws Exception;
	//변경 후 데이터 리스트
	public List<TblChangeVO> selectTblUpdateParam(Map<String, Object> param)throws Exception;
	//변경 후 데이터 갯수
	public TblChangeVO selectTblUpdateSub(Map<String, Object> param)throws Exception;
	
	public int ownerShipUpdate(Map<String, Object> param);

	
}
