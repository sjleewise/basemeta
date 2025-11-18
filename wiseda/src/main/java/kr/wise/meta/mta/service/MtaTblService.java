/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaTblService.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.02.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.02. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.meta.mta.service.WamMtaTbl;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.mta.service.WamMtaCol;

public interface MtaTblService {
	
	/** 메타데이터조회 - DB정의서 목록 조회 (기존 DB접속정보 ) */
	public List<WaaDbConnTrgVO> getDbConnTrgList(WaaDbConnTrgVO search);
	
	/** 메타데이터조회 - DB정의서(DBMS)에 대한 스키마 grid_sub에서 조회 - IBSheet JSON */
	List<WaaDbSch> getDbSchList(String dbConnTrgId);
	
	/** 메타데이터조회 - DB정의서(DBMS) 상세정보 조회 */
	WaaDbConnTrgVO getDbDefnInfoDetail(String dbConnTrgId);
	
	/** 테이블정의서 정보 상세 조회  */
	WamMtaTbl selectMtaTblInfoDetail(String mtaTblId);

	/** 테이블정의서 목록 조회 */
	List<WamMtaTbl> getMtaTblList(WamMtaTbl data);
	
	/** 테이블정의서에 대한 컬럼정보 조회 */
	List<WamMtaCol> getMtaColList(WamMtaCol data);
	
	/** 정보시스템 상세 조회(단일) */
	WaaInfoSys getInfoSysInfoDetail(String orgCd, String infoSysCd);

	/** 보유DB변경(DDL) */
	//int regDbChgList(ArrayList<WamMtaTbl> list, WamMtaTbl record);

	//int regDbChg(WamMtaTbl record) throws Exception;

	List<WatDbcTbl> getWatTblList(WatDbcTbl data);

	public List<WamMtaExl> getMtaExlLst(WamMtaExl data);

	public List<WamMtaExl> getMtaExlRqst(WamMtaExl data);

	public int regList(ArrayList<WamMtaExl> list) throws Exception;  
}