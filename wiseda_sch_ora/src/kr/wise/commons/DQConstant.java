package kr.wise.commons;

public class DQConstant {
	
	public static final String CRYPTOGRAPHY_SEOUL_COMMON_KEY = "dnltpdkdlxprtjdnftlapxkrhksfldyd";

	/** 요청종류 - 프로파일 스케쥴 코드 */
	public static final String REQUEST_PRF_CD = "PR";
	/** 요청종류 - 업무규칙 스케쥴 코드 */
	public static final String REQUEST_BRA_CD = "BR";
	
	/** 스키마수집 실행 코드 */
	public static final String EXE_TYPE_01_CD = "SC";
	/** 프로파일 실행 코드 */
	public static final String EXE_TYPE_02_CD = "QP";
	/** 검증룰 실행 코드 */
	public static final String EXE_TYPE_CR_CD = "CR";	
	/** 업무규칙 실행 코드 */
	public static final String EXE_TYPE_03_CD = "QB";
	/** 일반배치 실행 코드 */
	public static final String EXE_TYPE_04_CD = "GN";
	/** DB간 GAP 분석 실행 코드 */
	public static final String EXE_TYPE_05_CD = "DG";
	/** Python 실행 코드 */
	public static final String EXE_TYPE_06_CD = "PY";
	/** 스키마수집 실행 코드명  */
	public static final String EXE_TYPE_01_NM = "스키마 수집";
	/** 프로파일 실행 코드명 */
	public static final String EXE_TYPE_02_NM = "프로파일";
	/** 업무규칙 실행 코드명 */
	public static final String EXE_TYPE_03_NM = "업무규칙";
	/** 일반배치 실행 코드명 */
	public static final String EXE_TYPE_04_NM = "일반배치";
	/** DB간GAP분석 실행 코드명 */
	public static final String EXE_TYPE_05_NM = "DB간GAP분석";
	/** Python 실행 코드명 */
	public static final String EXE_TYPE_06_NM = "Python호출";
	
	
	/** 일반 배치 작업유형 **/
	//DB호출
	public static final String EXE_JOB_01_CD = "DB";
	//EXEC호출
	public static final String EXE_JOB_02_CD = "EX";
	//JAVA호출
	public static final String EXE_JOB_03_CD = "JV";
	//DB호출
	public static final String EXE_JOB_01_NM = "DB호출";
	//EXEC호출
	public static final String EXE_JOB_02_NM = "EXEC호출";
	//JAVA호출
	public static final String EXE_JOB_03_NM = "JAVA호출";
	
	/** 파이썬 실행 유형 **/
	//이상값탐지...
	public static final String PY_OUTLIER_CD 	= "OD";
	//도메인 판별
	public static final String PY_DOMAIN_CD 	= "DC";
	//기초 통계
	public static final String PY_SUMMARY_CD 	= "SM";
	//텍스트 클러스터링
	public static final String PY_TEXT_CD 		= "TC";
	//비표준 도메인 판별
	public static final String PY_NSTND_DOMAIN_CD 	= "ND";
	
	
	 //profile code
	 public static final String G_COLUMN_ANALYSIS	= "PC00";		//컬럼분석
	
	 public static final String G_DISTRIB_ANALYSIS_CODE 	= "PC01";		//분포분석
	 
	 public static final String G_NULL_ANALYSIS_CODE 		= "PC02";		//널분석
	 
	 public static final String G_VALIDITY_ANALYSIS_CODE 	= "PC03";		//유효값분석
	 
	 public static final String G_DATATYPE_ANALYSIS_CODE 	= "PC04";		//데이터형식
	 
	 public static final String G_RANGE_ANALYSIS_CODE 		= "PC05";		//범위분석
	 
	 public static final String G_PATTERN_ANALYSIS_CODE 		= "PC06";		//패턴분석
	 
	 public static final String G_REFRENCE_ANALYSIS_CODE 	= "PT01";		//참조무결성분석
	 
	 public static final String G_UNIQUE_ANALYSIS_CODE 		= "PT02";		//유일성분석
	 
	 //flag code
	 public static final String G_FLAG_TOTAL_COUNT 			= "TC";			//대상건수SQL
	 
	 public static final String G_FLAG_ERROR_COUNT 			= "EC";			//에러건수SQL
	 
	 public static final String G_FLAG_ERROR_DATA 			= "ED";			//대상데이터SQL
	 
	//sybase iq 
	 public static final String PATTERN_FUNCTION_NM 			= "WISEDQ_PTN_ANA";			 // SybaseIQ 패턴분석 function 명
	 
	 public static final String DATATYPE_USER_PATTERN_FUNCTION_NM 			= "WISEDQ_DATETYPE_PTN";			//SybaseIQ 날짜유형 사용자 패턴 function 명
	 
	 public static final String DATATYPE_USER_REPLACE_FUNCTION_NM 			= "WISEDQ_DATETYPE_REPLACE";			//SybaseIQ 날짜유형 REPLACE function 명
	
	 public static final String REP_DB_TYPE = "ORA";
}
