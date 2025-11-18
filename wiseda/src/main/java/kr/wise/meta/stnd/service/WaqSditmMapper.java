package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.CodeListVo;

@Mapper
public interface WaqSditmMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WaqSditm saveVo);

    int insert(WaqSditm record);

    int insertSelective(WaqSditm record);

    WaqSditm selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqSditm record);

    int updateByPrimaryKey(WaqSditm record);

    int insertByDmnSno(WaqDmn record);
    int insertByDmnSno2(WaqMstr record);
    
	/** @param saveVo insomnia */
	int deleteByDmnSnoNoChg(WaqDmn saveVo);

	/** @param saveVo insomnia */
	int deleteByDmnSno(WaqDmn saveVo);

	/** @param searchVo
	/** @return insomnia */
	WaqSditm selectStndItemRqstDetail(WaqSditm searchVo);

	/** @param search
	/** @return insomnia */
	List<WaqSditm> selectItemRqstListbyMst(WaqMstr search);

	/** @param search
	/** @return insomnia */
	List<Map<String, Object>> selectPersCode();
	
	//등록요청 탭 클릭 확인용
	List<WaqSditm> selectExistsItemCheck(WaqMstr search);

	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param checkmap insomnia */
	int checkDupSditm(Map<String, Object> checkmap);
	
	/** @param checkmap insomnia */
	int checkNotExistSditm(Map<String, Object> checkmap);

	int checkExistCol(Map<String, Object> checkmap);
	
	/** @param checkmap insomnia */
	int checkLnmSymn(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkExistStwd(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkInfoType(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNotExistDmn(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkSditmStwdAsm(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkDupSditmPnm(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkPersInfoGrd(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkSditmPnmMaxLen(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkSditmLnmMaxLen(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkSditmPnmStrNum(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	ArrayList<WaqSditm> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqSditm> list);

	/** @param savevo
	/** @return insomnia */
	int updatervwStsCd(WaqSditm savevo);
	
	int updatervwStsCdRejectSwtd(String rqstNo);
	
	int updatervwStsCdRejectDmn(String rqstNo);

	/** @param rqstno
	/** @return insomnia */
	List<WaqSditm> selectWaqC(@Param("rqstNo") String rqstno);

	/** @param savevo insomnia */
	int updateidByKey(WaqSditm savevo);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(String rqstno);

	/** @param rqstNo insomnia */
	int updateItemInfoType(String rqstNo);

	/** @param checkmap insomnia */
	int checkRequestDmn(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkDataType(Map<String, Object> checkmap);
	
	int checkObjDesn(Map<String, Object> checkmap);

	/** 재상신 입력 */
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	int updateWahTransYn(String rqstNo);//테스트변환관리 업데이트

	int updateDataTypeByInfotypLnm(String rqstNo);

	int updateRejSditmByWrd(WaqMstr mstVo);

	int insertByCodeDmnSno2(WaqMstr mstVo);
	
	int insertByCodeDmnSno3(WaqMstr mstVo);

	int checkSditmCodeInfoTpChg(Map<String, Object> checkmap);

	int updateSditmDelInfo(String rqstNo);

	List<WaqSditm> selectUnuseStndItemList(WaqSditm data);

	void checkExistUpStndDtl(Map<String, Object> checkmap); 

	int updateEncYn(String rqstNo);

	int updatePersInfoCnvYn(String rqstNo);

	void checkConstCnd(Map<String, Object> checkmap);

	void checkOpenYn(Map<String, Object> checkmap);

	void checkPrsnInfoYn(Map<String, Object> checkmap);

	void checkPriRsn(Map<String, Object> checkmap);
	
}