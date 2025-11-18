package kr.wise.commons;

import javax.inject.Inject;

import org.springframework.context.MessageSource;

public class WiseConfig {


	@Inject
	private MessageSource message;

	//RSA 암호화 상
	public static final int KEY_SIZE = 1024;


	//게시물 페이지 처리 - 페이지당 게시물수
	public static final int PAGEUNIT = 10;
	//게시물 페이지 처리 - 페이지 리스트 수
	public static final int PAGESIZE = 10;
	//댓글 페이지 처리 - 페이지당 댓글 수 
	public static final int SUBPAGEUNIT = 5;
	//댓글 페이지 처리 - 페이지 리스트 수 
	public static final int SUBPAGESIZE = 5;
	//게시물 페이지 클릭시 호출 함수(javascript)
	public static final String FN_PAGE = "select_noticeList";
	//댓글 페이지 클릭시 호출 함수(javascript) 
	public static final String FN_SUB_PAGE = "select_commentList";

	//제품 구분-메타
	public static final String META = "META";
	public static final String DQ = "DQ";
	public static final String PORTAL = "PORTAL";

	//연계시스템 URL
	public static final String URL_META     = "/main.do";
	public static final String URL_DQ 		= "/dqmain.do";
//	public static final String URL_META 	= "http://10.1.23.13:8080/nhicmeta/";
//	public static final String URL_DQ 		= "http://10.1.23.13:8080/wisedq_nhis/";
	public static final String URL_ENCAP 	= "http://10.1.60.56:31591/metaapp/";
	public static final String URL_SVN 		= "http://10.1.60.58/";

	//목록성코드 NM
	public static final String PRJLIST = "CODE_LIST_PRJ";

	//R9연계시 제품군
//	public static final String R9_DISTRIBUTOR = "GENESIS";   // GENESIS , SOFTVERK
	//public static final String R9_DISTRIBUTOR = "r9_distributor";   // GENESIS , SOFTVERK
	
	public static final String R9_DISTRIBUTOR = "r9_distributor";   // GENESIS , SOFTVERK 
	
	//소프트베르크 제품연계시 URL
//	public static final String MART_IP = "localhost";
//	public static final String MART_PORT = "18170";
//	public static final String MART_WASHOME = "erwin_mart9";
//	public static final String MART_REPORT = "json";	// json, xml, gzip
//	public static final String MART_ID = "admin";
//	public static final String MART_PW = "erwin";
//	public static final String MART_HEADERKEY = "111";
//	public static final String MART_HEADERVALUE = "222";
//	public static final String MART_UDP_USE_YN = "N";
	public static final String MART_IP          = "mart_ip";
	public static final String MART_PORT        = "mart_port";
	public static final String MART_WASHOME     = "mart_washome";
	public static final String MART_REPORT      = "mart_report";	// json, xml, gzip
	public static final String MART_ID          = "mart_id";
	public static final String MART_PW          = "mart_pw";
	public static final String MART_HEADERKEY   = "mart_headerkey";
	public static final String MART_HEADERVALUE = "mart_headervalue";
	public static final String MART_UDP_USE_YN  = "mart_udp_use_yn";
	
	//OS 유형
//    public static final String OS_TYPE = EgovProperties.getProperty("Globals.OsType");
    //DB 유형
//    public static final String DB_TYPE = EgovProperties.getProperty("Globals.DbType");
    //메인 페이지
//    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");
    //ShellFile 경로
//    public static final String SHELL_FILE_PATH = EgovProperties.getPathProperty("Globals.ShellFilePath");
    //퍼로퍼티 파일 위치
//    public static final String CONF_PATH = EgovProperties.getPathProperty("Globals.ConfPath");
    //Server정보 프로퍼티 위치
//    public static final String SERVER_CONF_PATH = EgovProperties.getPathProperty("Globals.ServerConfPath");
    //Client정보 프로퍼티 위치
//    public static final String CLIENT_CONF_PATH = EgovProperties.getPathProperty("Globals.ClientConfPath");
    //파일포맷 정보 프로퍼티 위치
//    public static final String FILE_FORMAT_PATH = EgovProperties.getPathProperty("Globals.FileFormatPath");

    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";
	//파일업로드 경로
	public static String UPLOAD_PATH = "upload.dir";
//	public static final String UPLOAD_PATH = "/usr/ssw/wmeta/nhisportal/upload_files";
	//파일업로드 허용 확장자
	public static final String ALLOW_EXT = ",hwp,doc,ppt,xls,pdf,jpg,pptx,docx,xlsx,txt,zip,rar,jpeg,gif,png,erwin,fcu,fuc,cfu,cuf,";

	//스케줄러_HOME 경로
	public static String SCHDULER_PATH = "schduler.dir";
	
	//DB 종류
	public static String DB_KIND = "db.kind";
	
	//Quaart등록 SHELL
	public static String SCHDULER_CMD = "schduler.sh";
	
	//기술표준원 코드분류체계이관 SHELL
	public static String CODECFCSYS_CMD = "codecfcsys.cmd";
	
	//엑셀템플릿 경로
	public static String EXCEL_RPT_PATH = "excel.rpt.dir";
	
	//암호화 적용여부 
	public static String SECURITY_APPLY = "Y";
	
	//salt 값 
	public static String SALT_KEY = "salt.key";
	
	//aes 값
	public static String AES_KEY = "aes.key";
	
	//salt 적용 여부
	public static String ENC_YN = "Y";
	
	//범정부연계 파일 전송 경로
	public static String GOV_FILE_PATH = "gov.file.path";

	//메일발송요청 XML파일경로
//	public static final String MAIL_REQUEST_PATH = EgovProperties.getPathProperty("Globals.MailRequestPath");
	//메일발송응답 XML파일경로
//	public static final String MAIL_RESPONSE_PATH = EgovProperties.getPathProperty("Globals.MailRResponsePath");

	public WiseConfig() {
		//UPLOAD_PATH = message.getMessage("upload.dir", null, Locale.getDefault());
	}

	// G4C 연결용 IP (localhost)
//	public static final String LOCAL_IP = EgovProperties.getProperty("Globals.LocalIp");

}
