/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : WiseMetaConfig.java
 * 2. Package : kr.wise.common
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 24. 오전 11:06:34
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons;

import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 1. ClassName : WiseMetaConfig
 * 2. Package  : kr.wise.common
 * 3. Comment  : 메타에서 사용할 공통 상수를 정의한다.
 * 				 각 종류별 enum class 형태로 제공한다.
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 24.
 * </PRE>
 */
@Component
public class WiseMetaConfig {

	/**
	 * <PRE>
	 * 1. ClassName : IBSAction
	 * 2. Package  : kr.wise.common
	 * 3. Comment  : IBS 처리 액션 종류... View로 리턴시 ETC.action 에 담아서 처리한다....
	 * 				 View에서는 액션에 따라 postProcessIBS(code)에서 처리한다...
	 * 4. 작성자   : insomnia(장명수)
	 * 5. 작성일   : 2013. 4. 24.
	 * </PRE>
	 */
	public  enum IBSAction {
		REG_LIST("REG_LIST"), 	//IBS 리스트 처리
		REG("REG"),				//IBS 단건 처리
		DEL("DEL"),				//IBS 삭제 처리
		DEL_DTL("DEL_DTL"),		//IBS sub-grid 삭제 처리
		SEARCH("SEARCH"),		//IBS 조회 처리
		CONNTEST("CONNTEST");	//IBS 접속테스트	
		
		
		private IBSAction(String code) {
			this.action = code;
		}

		//상수 String
		private String action;

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}



	}

	/**
	 * <PRE>
	 * 1. ClassName : RqstAction
	 * 2. Package  : kr.wise.common
	 * 3. Comment  :
	 * 4. 작성자   : insomnia(장명수)
	 * 5. 작성일   : 2013. 4. 24.
	 * </PRE>
	 */
	public enum RqstAction {
		REGISTER("REGISTER"), 	//임시저장
		CHECK("CHECK"), 		//검증
		SUBMIT("SUBMIT"),		//등록요청 (라이트 버전은 안씀...)
		APPROVE("APPROVE");		//승인    (실제 등록 기능)

		private String action;

		private RqstAction(String code) {
			this.action = code;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}
	}

	public enum CodeListAction {
		/** 시스템 영역 리스트 */
		sysarea("sysarea"),

		/** 결재 그룹 리스트 */
		approvegroup("approvegroup"),

		/** 도메인 그룹 리스트 */
		dmng("dmng"),

		/** 인포타입 그룹 리스트 */
		infotp("infotp"),

		/** 도메인 인포타입 그룹 리스트 */
		dmnginfotp("dmnginfotp"),

		/** 사용자 그룹 리스트 */
		usergroup("usergroup"),

		/** 사용자 그룹명 리스트 */
		userglnm("userglnm"),

		/** DBMS TYPE 리스트 */
		dbmstypvers("dbmstypvers"),

		/** 프로젝트 리스트 */
		CODE_LIST_PRJ("CODE_LIST_PRJ"),

		/** 공통 상위 코드 리스트 */
		CL_CODE("CL_CODE"),

		/** 공통 코드 리스트 - 전자정부용 */
		COM_CODE("COM_CODE"),

		/** 공통 코드 리스트 - 제품용 */
		COM_DCD("COM_DCD"),

		/** 게시판 속성 코드 리스트 */
		bbsAttrbCode("bbsAttrbCode"),

		/** 게시판 유형 코드 리스트 */
		bbsTyCode("bbsTyCode"),

		/** 게시판 템플릿 유형 코드 리스트 */
		tmplatId("tmplatId"),

		/** 게시판 업무 구분 코드 리스트 */
		cateCode("cateCode"),

		/** 진단대상 DBMS 코드 리스트 */
		connTrgDbms("connTrgDbms"),

		/** 실행차수 리스트 */
		anaDgr("anaDgr"),
		
		/** 실행차수 리스트 */
		bizrAnaDgr("bizrAnaDgr"),

		/** 업무규칙 실행차수 리스트  - 스케줄 관리용*/
		brAnaDgr("brAnaDgr"),
		
		/** 이상값탐지 실행차수 리스트  - 스케줄 관리용*/
		otlAnaDgr("otlAnaDgr"),
		
		/** 사용자정의 이상값탐지 실행차수 리스트  - 스케줄 관리용*/
		uodAnaDgr("uodAnaDgr"),

		/** 프로파일 실행차수 리스트 - 스케줄 관리용*/
		prfAnaDgr("prfAnaDgr"),
		
		/** 텍스트매칭 실행차수 리스트  - 스케줄 관리용*/
		tmAnaDgr("tmAnaDgr"),

		/** 표준단어 약어생성 임시저장 코드 리스트 */
		abrTempList("abrTempList"),
		
		/** 표준항목 자동분할 ID 리스트 */
		dvRqstNo("dvRqstNo"),
		
		dicRqstNo("dicRqstNo"),
		
		/** 표준도메인 자동분할 ID 리스트 */
		dvDmnRqstNo("dvDmnRqstNo"),

		/** DBMS 데이터타입 코드 리스트 */
		dbmsDataType("dbmsDataType"),

		/** DBMS 데이터타입 코드 리스트(double_select) */
		dbmsDataTyp("dbmsDataTyp"),

		/** 주제영역명 리스트 */
		subj("subj"),
		
		/** 주제영역명 리스트 LEVEL1 */
        subjLvl1("subjLvl1"), 

        /** 주제영역명 리스트 LEVEL2 */
        subjLvl2("subjLvl2"), 
        
        /** 주제영역명 리스트 LEVEL3 */
        subjLvl3("subjLvl3"), 

		/** DB스키마명 리스트 */
		dbSch("dbSch"),
		
		/**개발 DB스키마명 리스트 */
        devDbSch("devDbSch"),

		/** 진단대상, 스키마명 리스트(PNM 반환) (double_select) */
		connTrgSch("connTrgSch"),


		/** 진단대상, 스키마명 리스트(ID 반환) (double_select) */
		connTrgSchId("connTrgSchId"),
		
		connTrgSchId2("connTrgSchId2"), /**메타관리대상만 */
		
		connTrgSchIdCodeTsf("connTrgSchIdCodeTsf"),  /**코드이관대상만 */

		/** 프로그램영향도 언어 타입 insomnia */
		LangType("LangType"),
		
		/** 도메인그룹 최하위차수 DATA 조회 */
		dmngLowDgr("dmngLowDgr"),
		
		/** 주제영역/물리테이블 조회 (yeonho)*/
		subjPdmTbl("subjPdmTbl"), 
		
		/** 스키마명(ibSheet DoubleSelect용) yeonho */
		dbSchForDoubleSelectIBS("dbSchForDoubleSelectIBS"),
		
		/** 진단대상명(DB_ROLE에 등록된 DBMS만 가져오는) yeonho */
		dbConnTrgIdInDbRole("dbConnTrgIdInDbRole"),
		
		/** Role명 (ibSheet DoubleSelect용) yeonho */
		roleNmForDoubleSelectIBS("roleNmForDoubleSelectIBS"),
		
		/** DBMS/ROLE명 (DoubleSelect) yeonho */
		dbRoleNm("dbRoleNm"),
		
		/** 스키마명 only yeonho */
		dbSchLnm("dbSchLnm"),
		
		/** 분류체계유형 yeonho */
		clSystmTyCode("clSystmTyCode"),
		
		/** 분류체계코드항목명 yeonho */
		clSystmTyIemCode("clSystmTyIemCode"),
		
		/** 테이블스페이스(인덱스, 테이블 구분) */
		tblSpac("tblSpac"),
		
		tblSpacId("tblSpacId"),
		
		idxSpacId("idxSpacId"),
		
		tblSpacPnm("tblSpacPnm"),
		
		idxSpacPnm("idxSpacPnm"),
		
		/** DB간 GAP  */
		dbGapSrcShdId("dbGapSrcShdId"),
		
		dbGapTgtShdId("dbGapTgtShdId"),
		
		devConnTrgSchId("devConnTrgSchId"),
		
		devConnTrgSchId2("devConnTrgSchId2"),
		
		infoTpCodeListIBS("infoTpCodeListIBS"),
				
		codedmnginfotp("codedmnginfotp"),

		chnlTpDtls("chnlTpDtls"),
		
		bizDtls("bizDtls"),
		
		/**도메인그룹 전체경로*/
		dmngLowDgrStndAsrtFullPath("dmngLowDgrStndAsrtFullPath"),
		/**표준분류>인포타입명*/
		infotpStndAsrtFullPath("infotpStndAsrtFullPath"),
		
		/**주제영역권한**/
		subjLnmByLvl1("subjLnmByLvl1"),
		sysareaNsubjLnmByLvl1("sysareaNsubjLnmByLvl1"),
		subjLnmByLVL2("subjLnmByLVL2"),
		SubjLstByLvl0Lvl1("SubjLstByLvl0Lvl1"),
		deptNm("deptNm"),
		subjLnmByLVL1Pnm("subjLnmByLVL1Pnm"),
		subjLnmByLVL2Pnm("subjLnmByLVL2Pnm"),
		
		//운영,테스트 db만
		prodConnTrgSchId("prodConnTrgSchId"),
		
		//mssql의 경우 db명.소유자로 관리하므로 앞의 db명만 호출한다
		connTrgSchNameSplit("connTrgSchNameSplit"),
				
		orgCd("orgCd"),
		
		infoSysCd("infoSysCd"),
		/** 진단대상, 스키마명 리스트(ID 반환) (double_select) */
		connTrgSchIdHive("connTrgSchIdHive"), 
		
		/** 범정부 메타 등록차수 리스트 - 범정부 메타용*/
		mtaDgr("mtaDgr"),
		/** 범정부 메타 등록차수 리스트 - 범정부 메타용*/
		mtaDgrSel("mtaDgrSel"),
		
		/** 검증룰 실행차수 리스트  - 스케줄 관리용*/
		vrfcAnaDgr("vrfcAnaDgr")
		;
		
		


		private String action;

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		private CodeListAction(String code) {
			this.action = code;

		}



	}


}
