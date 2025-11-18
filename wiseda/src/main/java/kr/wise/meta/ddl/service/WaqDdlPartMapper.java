package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlPartMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlPart record);

    int insertSelective(WaqDdlPart record);

    WaqDdlPart selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlPart record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlPart record);

    int updateByPrimaryKey(WaqDdlPart record);

	WaqDdlPart selectDetail(WaqDdlPart searchVo);

	List<WaqDdlPart> selectDdlPartList(WaqMstr mstvo);

	int deleteByrqstSno(WaqDdlPart saveVo);

	int updateKeyId(String rqstNo);
	
	int updateDbConnTrgId(String rqstNo);
	int updateDbSchId(String rqstNo);
	int updateDdlTblId(String rqstNo);
	int updateDdlPartId(String rqstNo);
	
	int updateCheckInit(String rqstNo);

	int checkDupPart(Map<String, Object> checkmap);

	int checkNotExistPart(Map<String, Object> checkmap);

	int checkRequestPart(Map<String, Object> checkmap);

	int checkNonDbConnTrg(Map<String, Object> checkmap);

	int checkNonDbSch(Map<String, Object> checkmap);

	int checkNonDdlTbl(Map<String, Object> checkmap);

	int checkNonTblSpac(Map<String, Object> checkmap);

	int checkNonExistMainPart(Map<String, Object> checkmap);

	int checkNotChgData(Map<String, Object> checkmap);

	int checkMainPartErr(Map<String, Object> checkmap);

	int checkNonMainPartKeyCol(Map<String, Object> checkmap);

	int checkNonSubPartKeyCol(Map<String, Object> checkmap);

	int checkDupPartKeyCol(Map<String, Object> checkmap);

	int checkNonExistSubPart(Map<String, Object> checkmap);

	int checkExistsRqstDdlTbl(Map<String, Object> checkmap);

	int checkSubPartErr(Map<String, Object> checkmap);
	
	int checkExistsTsfPart(Map<String, Object> checkmap);

	int updatervwStsCd(WaqDdlPart savevo);
	
	int updatervwStsCdByTbl(WaqDdlTbl savevo);

	int updatervwStsCdAll(WaqMstr mstVo);

	List<WaqDdlPart> selectWaqC(String rqstno);

	int updateidByKey(WaqDdlPart savevo);

	List<WaqDdlPart> selectDdlPartListWam(WaqDdlPart search);

	List<WaqDdlPart> selectddlpartlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlPart> list);

	int updateDdlPartScriptWaq(WaqDdlPart savevo);

	int checkNonSubPartKey(Map<String, Object> checkmap);

	int checkPartKeybyList(Map<String, Object> checkmap);

	int checkSubPartKeybyList(Map<String, Object> checkmap);
	
	int checkAplRegDd(Map<String, Object> checkmap);
	
	int checkSubjOwner(Map<String, Object> checkmap);

	int updateDdlPartRqstPrc(WamDdlTbl record);

	WaqDdlPart selectbyMainPart(WaqDdlPartMain savevo);

	WaqDdlPart selectbySubPart(WaqDdlPartSub savevo);
	
	int updateTblSpacIdByAccDbmsYn(String rqstNo);
	
	int updateTblSpacId(String rqstNo);
	
    //DDL이관대상조회
    List<WaqDdlPart> selectDdlTsfList(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("dbmsInfo") WaqDdlTsfTbl dbmsInfo);
//    List<WaqDdlPart> selectDdlTsfList(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("tgtDbSchId") String tgtDbSchId);
    
    List<WaqDdlPart> selectDdlTsfListByTbl(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("dbmsInfo") WaqDdlTsfTbl dbmsInfo);
//    List<WaqDdlPart> selectDdlTsfListByTbl(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("tgtDbSchId") String tgtDbSchId);
    
    int updateWaqId(String rqstno);

	int checkCountPartValbyRange(Map<String, Object> checkmap);

    int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
    int insertWaqMainPartRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
    int insertWaqSubPartRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );

	int updateWAHbyTbl(String rqstNo);

	int insertWAHbyTbl(String rqstNo);

	int deleteWAMbyTbl(String rqstNo);

	int checkNonMainPartType(Map<String, Object> checkmap);

	int updateDelMainPart(String rqstNo);

	int updateDelSubPart(String rqstNo);

	int checkSubPartTypCd(Map<String, Object> checkmap);
	
	int ddlTrgCdcUpdateTemp();
	
	int ddlIdxTrgCdcUpdateTemp();
	int ddlIdxHTrgCdcUpdateTemp();
	
	int ddlIdxHSrcDbSchIdUpdateTemp();
	int ddlIdxSrcDbSchIdUpdateTemp();
	int ddlPartSrcDbSchIdUpdateTemp();
	int ddlPartHInsertTemp();
	int ddlPartHSrcDbSchIdUpdateTemp();
	int ddlPartMainHInsertTemp();
	int ddlPartSubHInsertTemp();
	
	int checkRealSrMngNo(Map<String, Object> checkmap);
	//DDL테이블 CUD여부 update
	int updateCudYn(String rqstNo);
	
	int checkCudYn(Map<String, Object> checkmap); 	
	
	//운영이관 시 체크카드여부 테이블의 적용요청일자 는 매월둘째주금요일 임(DT018)
	int checkAplReqDtByDdlTrgDcd(Map<String, Object> checkmap);
	//운영이관시 적용요청구분,적용요청일자 필수 (DT019)
	// DDL_TRG_DCD = 'R' APL_REQ_TYP_CD ,APL_REQ_DT
	int checkAplInfoByDdlTrgDcd(Map<String, Object> checkmap);
	//계정계이고 운영이관일 경우 적용요청일자는 매주 금요일(DT020)
	int checkAplReqDtByDdlTrgDcdReal(Map<String, Object> checkmap);	
	
	//운영이관시 sr번호,프로젝트번호 갱신 대상 조회
	List<WaqDdlPart> selectSrMngNoPrjMngNo(WaqMstr mstVo);
	//운영이관시 개발,테스트 sr번호, 프로젝트번호 update 
	int updateWamSrMngNoPrjMngNoByKey(WaqDdlPart saveVo);
	int updateWahSrMngNoPrjMngNoByKey(WaqDdlPart saveVo);
	
	//운영이관시 적용요청일자는 항상 금요일 이다. 화 목도 추가 될수 있다?(DT022)
	int checkAplReqDt(Map<String, Object> checkmap);	
	//운영이관시 적용요청일자가 수, 목, 금 일경우 차주 금요일이다...(DT023)
	int checkAplReqDt2(Map<String, Object> checkmap);		
	
	String selectPartColumnLength(WaqDdlPart search);
	
}