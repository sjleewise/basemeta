package kr.wise.meta.subjarea.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaaSubjMapper extends CommonRqstMapper {
	
	int deleteAll();
	
    //int deleteByPrimaryKey(String subjId);
    
    int deleteRegTyp(WaaSubj record);

    //int insert(WaaSubj record);

    int insertSelective(WaaSubj record);

    WaaSubj selectByPrimaryKey(String subjId);
    
    List<WaaSubj> selectList(WaaSubj record);  

    List<WaaSubj> selectListOrderSys(WaaSubj record);

    int updateByPrimaryKeySelective(WaaSubj record);

	int updateExpDtm(WaaSubj record);

	int updateAllSubjNm(@Param("subjId") String subjId);

	List<WaaSubj> selectMetaList(WaaSubj search);

	WaaSubj selectUppSubjBySubjNm(WaaSubj record);

	List<WaaSubj> selectChkSubjExists(WaaSubj subjVo);  

    //int updateByPrimaryKey(WaaSubj record);
	

	//여기부터 주제영역 권한 등록요청
	List<WaaSubj> selectSubjOwnerRqstList(WaqMstr search);
	
	
	int insertRqst(WaaSubj record);
	int updateByPrimaryKeySubjOwner(WaaSubj record);
	int deleteByrqstSno(WaaSubj record);
	
	int updateSubjId(@Param("rqstNo") String rqstNo);
	int updateUserId(@Param("rqstNo") String rqstNo);
	
	int updateCheckInit(String rqstNo);
	
	//요청서내 중복자료 검증(SOW01)
	int checkDup(Map<String, Object> checkmap);
	//미존재 주제영역 (SOW02)
	int checkSubjExists(Map<String, Object> checkmap);
	//미존재 사용자(SOW03)
	int checkUserExists(Map<String, Object> checkmap);
	//사전, PDM, DDL 모든권한이 'N' 경우(SOW04)
	int checkOwnerYn(Map<String, Object> checkmap);
	//표준사전일 경우 주제영역은 1레벨만 사용 가능(SOW05)
	int checkSubjLvlByDic(Map<String, Object> checkmap);
	//물리모델, DDL 일경우 주제영역은 2레벨만 사용 가능(SOW06)
	int checkSubjLvlByModel(Map<String, Object> checkmap);
	//삭제시 존재여부 체크(SOW07)
	int checkSubjOwnerExist(Map<String, Object> checkmap);
	
	//등록된 사용자(SOW00)
	int checkNotChgData(Map<String, Object> checkmap);
	
	int updatervwStsCd(WaaSubj savevo);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
    
    List<WaaSubj> selectSubjOnwerlist(WaaSubj record);
    
    int updateidByKey(WaaSubj record);
    List<WaaSubj> selectWaqC(String rqstno);
	
    List<WaaSubj> selectSubjOwnerByWaq(Map<String, Object> map);
    
    int insertSubjOwnerByWaq(WaaSubj savevo);
    
	List<WaaSubj> selectSubjOnwerDetList(WaaSubj search); 
	
	WaaSubj selectSubjOnwer(WaaSubj record);
	
	int deleteWAMbyADMIN(WaaSubj record);
	int updateWAHbyADMIN(WaaSubj record);
	
	int insertWAMbyADMIN(WaaSubj record);
	int insertWAHbyADMIN(WaaSubj record);

	List<WaaSubj> selectSubjFavoritelist(WaaSubj record);
	
	int insertWAMSF(WaaSubj record);	
	
	int deleteWAMSF(WaaSubj record);
	
	int selectFavoriteCntBySubjId(WaaSubj record);
	
	String selectDbmsTypCd(String subjLnm);
}