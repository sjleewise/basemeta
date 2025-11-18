package kr.wise.dq.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
//@Mapper
public interface WdqqDmnMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WdqqDmn record);
    
    int updateWaqCUD(String rqstno);
    
    int deleteWAM(String rqstno);
    
    int insertWAM(String rqstno);
    
    int updateWAH(String rqstno);
    
    int insertWAH(String rqstno);
    
    int insertSelective(WdqqDmn record);

    WdqqDmn selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WdqqDmn record);

    int updateByPrimaryKey(WdqqDmn record);

	/** @param searchVo
	/** @return insomnia */
	WdqqDmn selectDomainRqstDetail(WdqqDmn searchVo);

	/** @param saveVo
	/** @return insomnia */
	int deleteByrqstSno(WdqqDmn saveVo);

	/** @param search
	/** @return insomnia */
	List<WdqqDmn> selectDmnRqstListbyMst(WaqMstr search);

	List<WdqqDmn> selectExistsDmnCheck(WaqMstr search);
	
	/** @param reqmst
	/** @param list insomnia */
	int deleteDmnList(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WdqqDmn> list);

	/** 검증 쿼리 */
	int updateCheckInit(String rqstNo);

	int checkDupDmn(Map<String, Object> checkmap);

	int checkNotExistDmn(Map<String, Object> checkmap);

	int checkLnmSymn(Map<String, Object> checkmap);

	int checkEngUseItem(Map<String, Object> checkmap);

	int checkUseDmn(Map<String, Object> checkmap);

	int ckeckCdDmnInfo(Map<String, Object> checkmap);
	
	int ckeckCdDmng(Map<String, Object> checkmap);

	int ckeckLstCdDmnInfo(Map<String, Object> checkmap);

	int checkExistLstEnty(Map<String, Object> checkmap);

	int checkHasCdValNotCdDmn(Map<String, Object> checkmap);

	int checkCdValDmnInfoLeng(Map<String, Object> checkmap);

	int checkSubj(Map<String, Object> checkmap);

	int checkExistStwd(Map<String, Object> checkmap);

	int checkInfoType(Map<String, Object> checkmap);

	int checkNotExistPrntDmn(Map<String, Object> checkmap);

	int checkChldDmnExist(Map<String, Object> checkmap);

	int checkDmnStwdAsm(Map<String, Object> checkmap);

	int checkDupDmnPnm(Map<String, Object> checkmap);

	int checkDmnPnmMaxLen(Map<String, Object> checkmap);

	int checkDmnLnmMaxLen(Map<String, Object> checkmap);

	int checkDmnPnmStrNum(Map<String, Object> checkmap);
	
	//20150701 이상익 추가
	//도메인물리명 존재여부
	int checkDmnPnmExists(Map<String, Object> checkmap);
	// 물리명기준구분 존재여부
	int checkLNmCriDsExists(Map<String, Object> checkmap);
	// 물리명기준구분 존재여부
	int checkPNmCriDsExists(Map<String, Object> checkmap);

    //단순코드 일 때 코드도메인은 3레벨을 넘을 수 없음  //사용안함
	int checkSimpleCodeLevelCheck(Map<String, Object> checkmap);
	//코드도메인인 경우 유효값은 필수로 있어야함
	int checkCdValExists(Map<String, Object> checkmap);

	//varchar(64,128,256)일 경우는 무조건 암호화 사용
	int checkEncYn(Map<String, Object> checkmap);
	
	//단순코드일경우 길이 15, 복잡코드일경우 25
	int checkCodeLen(Map<String, Object> checkmap);
	
	//등록불가능한 코드값이 있는지 체크
	int checkCodeErr(Map<String, Object> checkmap);
	
	//단순, 복잡코드일 경우 대분류코드 필수
	
	int checkExistsDscd(Map<String, Object> checkmap);
	
	//대분류코드 기존재 여부 체크 
	int checkExistsDscdAlready(Map<String,Object> checkmap);
	
	//대분류코드 등록요청 기존재 여부 체크 
	int checkExistsWaqDscdAlready(Map<String,Object> checkmap);
	
	//대분류코드 길이체크 (길이 : 4)
	int checkDscdLength(Map<String, Object> checkmap);
	
	//대분류코드 주제영역물리명(2자리)+ 2자리
	int checkDscdSubjMapping(Map<String,Object> checkmap);
	
	int ckeckNotDmnLnmCd(Map<String, Object> checkmap);
	
	/** 승인/반려 업데이트 @return insomnia */
	int updatervwStsCd(WdqqDmn savevo);

	int updatervwStsCdRejectSwtd(String rqstNo);

	/** 적재 SQL @return insomnia */
	List<WdqqDmn> selectWaqC(@Param("rqstNo") String rqstNo);

	/** @param savevo
	/** @return insomnia */
	int updateidByKey(WdqqDmn savevo);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WdqqDmn> list);

	/***/
	ArrayList<WdqqDmn> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WdqqDmn> list);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(String rqstno);

	/** @param rqstno
	/** @return insomnia */
	int updateUppDmnId(String rqstno);

	/** @param checkmap insomnia */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param WdqqDmn
	/** @return insomnia */
	List<WdqqDmn> selectListByDmnLnm(WdqqDmn WdqqDmn);

	WdqqDmn selectByDmnLnm(WdqqDmn WdqqDmn);

	/** @param checkmap insomnia */
	int checkRequestDmn(Map<String, Object> checkmap);

	/** @param checkmap yeonho */
	void checkExistLstAttr(Map<String, Object> checkmap);

	List<WdqqDmn> selectListByCdval(WdqqCdVal savevo);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	//인포타입 일괄변경
	int updateSditmInfoTp(String rqstNo);
	int updateSditmInfoTpWah(String rqstNo);
	
	// 도메인 신규신청일 때 동일 용어가 존재하는지 체크
	int checkExistsSditm(Map<String, Object> checkmap);
	
	//테스트변환여부 업데이트
	int updateWahTransYn(String rqstNo);

	int updateRejItemByDmn(WaqMstr mstVo); 

	int updateRejDmnByWrd(WaqMstr mstVo);

	int updateDmnDelInfo(String rqstNo);

	int insertWdqqDmnByCdVal(WdqqCdVal savevo);
	
	List<WdqqDmn> selectDmnCdVal(WdqqDmn data);
	
	int checkDmnCriDsCnfg(Map<String,Object> checkmap);

	String selectDmnId(WdqqDmn WdqqDmn);
	
}