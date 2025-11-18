package kr.wise.dq.gap.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface StndGapMapper {

	List<StndGapVO> getDBStndItemGapList(StndGapVO search);
	
	List<StndGapVO> getDBDmnGapList(StndGapVO search);
	
	List<StndGapVO> getStndItemGapList(StndGapVO search);
	
	List<StndGapVO> getDmnGapList(StndGapVO search);

	int insertGapResult(StndGapVO saveVo);         

	Map<String,String> selectDBStndItemGapResult (); 
	
	
	Map<String,String> selectDBStndItemGapUneqResult (); 
	
	Map<String,String> selectDBDmnGapResult      ();
	Map<String,String> selectDBDmnGapUneqResult      ();
	
	Map<String,String> selectStndItemGapResult   ();
	Map<String,String> selectStndItemGapUneqResult   ();
	
	Map<String,String> selectDmnGapResult        ();
	Map<String,String> selectDmnGapUneqResult        ();
	
	List<StndGapVO> getColDefGapList(StndGapVO search);

	int regColDefGapList(StndGapVO saveVO);
	
	List<StndGapVO> selectGapResultList(StndGapVO search);
	

	List<StndGapVO> getMtaColGapList(StndGapVO search);

	int regMtaColGapList(StndGapVO saveVO);
}
