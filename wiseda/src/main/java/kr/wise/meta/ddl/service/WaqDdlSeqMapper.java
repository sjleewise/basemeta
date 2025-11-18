package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface WaqDdlSeqMapper extends CommonRqstMapper{
    
	int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqDdlSeq record);

    int insertSelective(WaqDdlSeq record);

    WaqDdlSeq selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDdlSeq record);

    int updateByPrimaryKeyWithBLOBs(WaqDdlSeq record);

    int updateByPrimaryKey(WaqDdlSeq record);
    
    List<WaqDdlSeq> selectDdlSeqListbyMst(WaqMstr search);
    
    WaqDdlSeq selectDdlSeqDetail(WaqDdlSeq searchVo);
    
    int deleteByrqstSno(WaqDdlSeq saveVo);
    
    int updateDbmsId(String rqstNo);
    
    int updateTsfInfo(String rqstNo);
    
    int updateCheckInit(String rqstNo);
    
    int checkDupSeq(Map<String, Object> checkmap);
    
    int checkNotExistSeq(Map<String, Object> checkmap);
    
    int checkNonDbmsID(Map<String, Object> checkmap);
    
    int checkRequestSeq(Map<String, Object> checkmap);
    
    int checkNotChgData(Map<String, Object> checkmap);
    
    int checkSeqPnmEnt(Map<String, Object> checkmap);
    int checkSeqPnmCol(Map<String, Object> checkmap);
    int checkSeqPnmSubj(Map<String, Object> checkmap);
    
    int checkExistsTbl(Map<String, Object> checkmap);
    int checkExistsCol(Map<String, Object> checkmap);
    int checkExistsL1L3Cd(Map<String, Object> checkmap);
    int checkExistsGrtLst(Map<String, Object> checkmap);
    
    int checkAplRegDd(Map<String, Object> checkmap);
    
    int checkExistsTsfSeq(Map<String, Object> checkmap);
    
    int updateDdlScriptWaq(WaqDdlSeq savevo);
    
    int updatervwStsCd(WaqDdlSeq savevo);
    
    int updatervwStsCdByTbl(WaqDdlTbl savevo);
    
    int updateidByKey(WaqDdlSeq savevo);
    
    List<WaqDdlSeq> selectWaqC(String rqstno);
    
    List<WaqDdlSeq> selectwamseqlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlSeq> list);
    
    //DDL이관대상조회
    List<WaqDdlSeq> selectDdlTsfList(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("dbmsInfo") WaqDdlTsfTbl dbmsInfo);
//    List<WaqDdlSeq> selectDdlTsfList(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("tgtDbSchId") String tgtDbSchId);
    // 명명규칙 엔티티기반인 SEQ 추출
    List<WaqDdlSeq> selectDdlTsfListByTbl(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("dbmsInfo") WaqDdlTsfTbl dbmsInfo);
//    List<WaqDdlSeq> selectDdlTsfListByTbl(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqDdlTsfTbl> list, @Param("tgtDbSchId") String tgtDbSchId);
    
    int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
    
    int checkRealSrMngNo(Map<String, Object> checkmap);
    
	//운영이관 시 체크카드여부 테이블의 적용요청일자 는 매월둘째주금요일 임(DT018)
	int checkAplReqDtByDdlTrgDcd(Map<String, Object> checkmap);
	
	//운영이관시 적용요청구분,적용요청일자 필수 (DT019)
	// DDL_TRG_DCD = 'R' APL_REQ_TYP_CD ,APL_REQ_DT
	int checkAplInfoByDdlTrgDcd(Map<String, Object> checkmap);
	//계정계이고 운영이관일 경우 적용요청일자는 매주 금요일(DT020)
	int checkAplReqDtByDdlTrgDcdReal(Map<String, Object> checkmap);	
	
	//INIT유형 필수 체크 DS014
	int checkSeqInitCdExists(Map<String, Object> checkmap);
	//INIT유형 8, 9 일경우 업무영향도 필수 체크 DS015
	int checkSeqBizIfncByInitCd(Map<String, Object> checkmap);
	
	//운영이관시 sr번호,프로젝트번호 갱신 대상 조회	
	List<WaqDdlSeq> selectSrMngNoPrjMngNo(WaqMstr mstVo);
	//운영이관시 개발,테스트 sr번호, 프로젝트번호 update 
	int updateWamSrMngNoPrjMngNoByKey(WaqDdlSeq saveVo);
	int updateWahSrMngNoPrjMngNoByKey(WaqDdlSeq saveVo);
	
	//운영이관시 적용요청일자는 항상 금요일 이다. 화 목도 추가 될수 있다?(DT022)
	int checkAplReqDt(Map<String, Object> checkmap);	
	//운영이관시 적용요청일자가 수, 목, 금 일경우 차주 금요일이다...(DT023)
	int checkAplReqDt2(Map<String, Object> checkmap);	
	
	//엔티티기반 아닐경우
	int checkAplReqDt3(Map<String, Object> checkmap);	
	//엔티티기반 아닐경우
	int checkAplReqDt4(Map<String, Object> checkmap);

	//시퀀스명명규칙 위반 - NH캐피탈
	int checkSeqPnm(Map<String, Object> checkmap);

	int checkSeqTblPnm(Map<String, Object> checkmap);

	List<WamDdlSeq> selectDelDdlTsfSeqListForRqst(WamDdlSeq search);

	List<WamDdlSeq> selectDdlTsfSeqListForRqst(WamDdlSeq search);

	List<WaqDdlSeq> selectTsfDdlSeqList(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDdlSeq> list);

	int updateDbmsPnm(String rqstNo);
}