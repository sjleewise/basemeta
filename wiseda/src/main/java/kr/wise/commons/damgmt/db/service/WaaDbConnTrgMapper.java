package kr.wise.commons.damgmt.db.service;

import java.util.HashMap;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.codemng.service.WaaCdRule;


@Mapper
public interface WaaDbConnTrgMapper {
    int deleteByPrimaryKey(String dbConnTrgId);

    int deleteRegTyp(WaaDbConnTrgVO record);

    int insert(WaaDbConnTrgVO record);

    int insertSelective(WaaDbConnTrgVO record);
    
    List<WaaDbConnTrgVO> selectHstListDqDbms(String dbConnTrgId);
    
    //DBMS관리 곽효신
    List<WaaDbConnTrgVO> getDbSchList(String dbConnTrgId);

    WaaDbConnTrgVO selectByPrimaryKeyDqDbms(String dbConnTrgId);

    List<WaaDbConnTrgVO> selectList(WaaDbConnTrgVO record);
    
    WaaDbConnTrgVO selectByPrimaryKey(String dbConnTrgId);
    
    List<WaaDbConnTrgVO> selectListDqDbms(WaaDbConnTrgVO record); 
    List<WaaDbConnTrgVO> selectPopTrgDbmslst(WaaDbConnTrgVO record); 

    int updateByPrimaryKeySelective(WaaDbConnTrgVO record);

    int updateByPrimaryKey(WaaDbConnTrgVO record);

	int updateExpDtm(WaaDbConnTrgVO record);

	/** @param record yeonho */
	int updateConnTest(WaaDbConnTrgVO record);

	List<HashMap<String, Object>> selectEsbDbConnTrg();

	List<WaaDbConnTrgVO> selectMtaDbDefnList(WaaDbConnTrgVO search);

	WaaDbConnTrgVO selectDbDefnInfo(String dbConnTrgId);   
	
	WaaDbConnTrgVO selectDbmsInfo(WaaCdRule search); 

	//검증룰 종합현황 poidown시 필요한 dbms정보
	WaaDbConnTrgVO selectByDbms(WaaDbConnTrgVO search);

	int selectCntDBMS(WaaDbConnTrgVO waaDbConnTrg);

	String selectDbmsTypCdById(String dbConnTrgId);

	WaaDbConnTrgVO selectByDbConnTrgPnm(String dbConnTrgPnm);
}