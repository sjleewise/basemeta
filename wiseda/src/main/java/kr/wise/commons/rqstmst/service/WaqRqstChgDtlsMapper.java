package kr.wise.commons.rqstmst.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqRqstChgDtlsMapper {

    List<WaqRqstChgDtls> selectStwdChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDmnChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectItemChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectPdmTblChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectPdmColChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlTblChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlColChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectTblMapChgList(WaqMstr search);
	
	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectCodMapChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectBizAreaChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectCtqChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDqiChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectBizruleChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectImPlChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectImRslChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectCodeCfcSysChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectPdmRelChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlIdxColChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlIdxChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlRelChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlTsfIdxChgList(WaqMstr search);

	/** @param search
	/** @return yeonho */
	List<WaqRqstChgDtls> selectDdlTsfTblChgList(WaqMstr search);
	
	//메시지코드 변경사항 조회
	List<WaqRqstChgDtls> selectMsgChgList(WaqMstr search);

	//유효값 변경사항 조회
	List<WaqRqstChgDtls> selectCdValChgList(WaqMstr search);
	
	//코드이관 변경사항 조회
	List<WaqRqstChgDtls> selectCodeTsfChgList(WaqMstr search);
	
	//메시지이관 변경사항 조회
	List<WaqRqstChgDtls> selectMsgTsfChgList(WaqMstr search);
	
	//이관 대상 컬럼 변경사항 조회
	List<WaqRqstChgDtls> selectDdlColTsfChgList(WaqMstr search);
	
	//이관대상 인덱스 컬럼 변경사항 조회
	List<WaqRqstChgDtls> selectDdlIdxColTsfChgList(WaqMstr search);
	
	List<WaqRqstChgDtls> selectLdmAttrChgList(WaqMstr search);

	List<WaqRqstChgDtls> selectLdmEntyChgList(WaqMstr search);
	
	//메시지코드 변경사항 조회 표준
	List<WaqRqstChgDtls> selectMsgStdChgList(WaqMstr search);
	
	//메시지코드 변경사항 조회 채널
	List<WaqRqstChgDtls> selectMsgChnlChgList(WaqMstr search);
	
	//메시지코드 변경사항 조회 대외
	List<WaqRqstChgDtls> selectMsgMapChgList(WaqMstr search);

	//조치코드 변경사항 조회 
	List<WaqRqstChgDtls> selectMsgCodeChgList(WaqMstr search);
	
	//ASIS유효값 변경사항 조회 
	List<WaqRqstChgDtls> selectAsisDmnCdValChgList(WaqMstr search);
	
	//APP단어 변경사항 조회 
	List<WaqRqstChgDtls> selectAppStwdChgList(WaqMstr search);
	
	//APP용어 변경사항 조회 
	List<WaqRqstChgDtls> selectAppSditmChgList(WaqMstr search);
	
	//DDL시퀀스 변경사항 조회
	List<WaqRqstChgDtls> selectDdlSeqChgList(WaqMstr search);

	/** 유사항목(유사용어) 변경사항 조회 */
	List<WaqRqstChgDtls> selectSymnItemChgList(WaqMstr search);
	
	List<WaqRqstChgDtls> selectInfoTypeChgList(WaqMstr search);
	
	List<WaqRqstChgDtls> selectDmngChgList(WaqMstr search);

	List<WaqRqstChgDtls> selectDdlGrtChgList(WaqMstr search);

	List<WaqRqstChgDtls> selectDdlPartChgList(WaqMstr search);
	
	
	
	
	
}