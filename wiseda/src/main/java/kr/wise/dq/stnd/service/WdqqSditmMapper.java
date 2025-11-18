package kr.wise.dq.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.CodeListVo;

@Mapper
public interface WdqqSditmMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WdqqSditm saveVo);

    int insert(WdqqSditm record);

    int insertSelective(WdqqSditm record);

    WdqqSditm selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WdqqSditm record);

    int updateByPrimaryKey(WdqqSditm record);
    
    int deleteWAM(String rqstno);
    
    int insertWAM(String rqstno);
    
    int updateWaqCUD(String rqstno);
    
    int updateWAH(String rqstno);
    
    int insertWAH(String rqstno);

    int insertByDmnSno(WdqqDmn record);
    int insertByDmnSno2(WaqMstr record);
    
	/** @param saveVo insomnia */
	int deleteByDmnSnoNoChg(WdqqDmn saveVo);

	/** @param saveVo insomnia */
	int deleteByDmnSno(WdqqDmn saveVo);

	/** @param searchVo
	/** @return insomnia */
	WdqqSditm selectStndItemRqstDetail(WdqqSditm searchVo);

	/** @param search
	/** @return insomnia */
	List<WdqqSditm> selectItemRqstListbyMst(WaqMstr search);

	/** @param search
	/** @return insomnia */
	List<Map<String, Object>> selectPersCode();
	
	//등록요청 탭 클릭 확인용
	List<WdqqSditm> selectExistsItemCheck(WaqMstr search);

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
	ArrayList<WdqqSditm> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WdqqSditm> list);

	/** @param savevo
	/** @return insomnia */
	int updatervwStsCd(WdqqSditm savevo);
	
	int updatervwStsCdRejectSwtd(String rqstNo);
	
	int updatervwStsCdRejectDmn(String rqstNo);

	/** @param rqstno
	/** @return insomnia */
	List<WdqqSditm> selectWaqC(@Param("rqstNo") String rqstno);

	/** @param savevo insomnia */
	int updateidByKey(WdqqSditm savevo);

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

	List<WdqqSditm> selectUnuseStndItemList(WdqqSditm data);

}