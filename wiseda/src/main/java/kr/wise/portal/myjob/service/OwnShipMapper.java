package kr.wise.portal.myjob.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface OwnShipMapper {

	//데이터 리스트
	List<TblChangeVO> selectTblChangeParam(Map<String, Object> param) throws Exception;
	//데이터 갯수
	TblChangeVO selectTblChangeSub(Map<String, Object> param) throws Exception;
	//담당자ID 및 담당자 변경
	void ownershipUpdate(Map<String, String> param) throws Exception;
	
	//변경 후 데이터 리스트
	List<TblChangeVO> selectTblUpdateParam(Map<String, Object> param) throws Exception;
	//변경 후 데이터 갯수
	TblChangeVO selectTblUpdateSub(Map<String, Object> param) throws Exception;
	
	int updateOwnShip(Map<String, Object> param);
	
	
	
}
