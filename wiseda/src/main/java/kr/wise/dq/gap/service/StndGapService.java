package kr.wise.dq.gap.service;

import java.util.List;
import java.util.Map;


public interface StndGapService {

	List<StndGapVO> getDBStndItemGap(StndGapVO search);
	
	List<StndGapVO> getDBDmnGap(StndGapVO search);
	
	List<StndGapVO> getStndItemGap(StndGapVO search);
	
	List<StndGapVO> getDmnGap(StndGapVO search);

	//saveDmnGapList  saveStndItemGapList saveDBDmnGapList saveDBStndItemGapList
	//표준준수율 저장
	int saveGapResult(StndGapVO saveVo) throws Exception;

	
	Map<String,String> getDBStndItemGapResult();
	
	Map<String,String> getDBStndItemGapUneqResult();

	Map<String,String>  getDBDmnGapResult();
	Map<String,String>  getDBDmnGapUneqResult();

	Map<String,String>  getStndItemGapResult();
	Map<String,String>  getStndItemGapUneqResult();

	Map<String,String>  getDmnGapResult();
	Map<String,String>  getDmnGapUneqResult();

	List<StndGapVO> getColDefGapList(StndGapVO search);

	int regColDefGapList(StndGapVO saveVO);
	

	List<StndGapVO> getMtaColGapList(StndGapVO search);

	int regMtaColGapList(StndGapVO saveVO);
	List<StndGapVO>  getGapResultList(StndGapVO search);
	
}
