 package kr.wise.dq.dqrs.handler;

import java.awt.Color;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.dqrs.service.DqrsDbConnTrgVO;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;


public class VrfcrulePoiHandler {
	

	
	public static String getData(DqrsDbConnTrgVO dbConnTrg, String trg) {
		
		if( null == dbConnTrg ){ return "no data"; }
		
		String connTrgLnkUrl = UtilString.null2Blank(dbConnTrg.getConnTrgLnkUrl());
		String port = "";
		String dbIp = "";
		String dbServiceName = "";
		String orgNm = ""; //UtilString.null2Blank(dbConnTrg.getOrgNm());
		String infoSysNm = UtilString.null2Blank(dbConnTrg.getInfoSysNm());
		String dbConnTrgLnm = UtilString.null2Blank(dbConnTrg.getDbConnTrgLnm());
		String dbmsVer = UtilString.null2Blank(dbConnTrg.getDbmsVersCd());
		String dbmsType = UtilString.null2Blank(dbConnTrg.getDbmsTypCd());
		String system = UtilString.null2Blank(dbConnTrg.getFullPath());
//		if(system != ""){ system = system.split("[>]")[0]; }
				
		if("TIBERO".equals(dbmsType.toUpperCase()) || "TIB".equals(dbmsType.toUpperCase())){
	//    	"jdbc:tibero:thin:@{ip}:{port}:{db명}");
			String[] arr = connTrgLnkUrl.split("[:]");
			dbIp = arr[3].substring(1);
			port = arr[4];
			dbServiceName = arr[5];
			dbmsType = "TIBERO";
		}else if("ORACLE".equals(dbmsType.toUpperCase()) || "ORA".equals(dbmsType.toUpperCase())){
	//    	"jdbc:oracle:thin:@{ip}:{port}:{sid}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[3].substring(1);
			if(arr.length == 6){
				port = arr[4];
				dbServiceName = arr[5];				
			}else{
				port = arr[4].split("/")[0];
				dbServiceName = arr[4].split("/")[1];
			}
			dbmsType = "ORACLE";
		}else if("SYBASEIQ".equals(dbmsType.toUpperCase()) || "SYQ".equals(dbmsType.toUpperCase())){
	//    	"jdbc:sybase:Tds:{ip}:{port}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[3];
			port = arr[4].split("[/]")[0];
			dbServiceName = arr[4].split("[/]")[1];
			dbmsType = "SYBASEIQ";
		}else if("SYBASEASE".equals(dbmsType.toUpperCase()) || "SYA".equals(dbmsType.toUpperCase())){
	//    	"jdbc:sybase:Tds:{ip}:{port}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[3];
			port = arr[4].split("[/]")[0];
			dbServiceName = arr[4].split("[/]")[1];
			dbmsType = "SYBASEASE";
		}else if("DB2".equals(dbmsType.toUpperCase()) || "DB2".equals(dbmsType.toUpperCase())){
	//    	"jdbc:as400://{ip}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].split("[/]")[0].substring(2);
			dbServiceName = arr[2].split("[/]")[1];
			dbmsType = "DB2";
		}else if("UNIVERSAL DATABASE".equals(dbmsType.toUpperCase()) || "UDB".equals(dbmsType.toUpperCase())){
	//    	"jdbc:db2://{ip}:{port}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "UNIVERSAL DATABASE";
		}else if("MSSQL".equals(dbmsType.toUpperCase()) || "MSQ".equals(dbmsType.toUpperCase())){
	//    	"jdbc:sqlserver://{ip}:{port};DatabaseName={db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split(";")[0];
			dbServiceName = arr[3].split(";")[1].substring(13);
			dbmsType = "MSSQL";
		}else if("ALTIBASE".equals(dbmsType.toUpperCase()) || "ALT".equals(dbmsType.toUpperCase())){
	//    	"jdbc:Altibase://{ip}:{port}/{sid}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "ALTIBASE";
		}else if("TERADATA".equals(dbmsType.toUpperCase()) || "TER".equals(dbmsType.toUpperCase())){
	//    	"jdbc:teradata://{ip}/CHARSET=ASCII,CLIENT_CHARSET=cp949");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			dbmsType = "TERADATA";
		}else if("MYSQL".equals(dbmsType.toUpperCase()) || "MYS".equals(dbmsType.toUpperCase())){
	//    	"jdbc:mysql://{ip}:{port}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "MYSQL";
		}else if("INFORMIX".equals(dbmsType.toUpperCase()) || "IFX".equals(dbmsType.toUpperCase())){
	//    	"jdbc:informix-sqli://{host}:{port}/{db명}:INFORMIXSERVER={인스턴스명};user:{유져명};password={패스워드}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "INFORMIX";
		}else if("MONGODB".equals(dbmsType.toUpperCase()) || "MDB".equals(dbmsType.toUpperCase())){
	//    	"mongodb://{ip}:{port}/{db명}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[1].substring(2);
			port = arr[2].split("[/]")[0];
			dbServiceName = arr[2].split("[/]")[1];
			dbmsType = "MONGODB";
		}else if("HIVE".equals(dbmsType.toUpperCase()) || "HIV".equals(dbmsType.toUpperCase())){
	//    	"jdbc:hive2://{ip}:{port}/{db명};auth=noSasl");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1].split(";")[0];
			dbmsType = "HIVE";
		}else if("CUBRID".equals(dbmsType.toUpperCase()) || "CBR".equals(dbmsType.toUpperCase())){
	//    	"jdbc:cubrid:{ip}:{port}:{db명}:public::");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2];
			port = arr[3];
			dbServiceName = arr[4];
			dbmsType = "CUBRID";
		}else if("POSTGRESQL".equals(dbmsType.toUpperCase()) || "POS".equals(dbmsType.toUpperCase())){
	//    	"jdbc:postgresql://{ip}:{port}/{dbName}");
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "POSTGRESQL";
		}else if("MARIA".equals(dbmsType.toUpperCase()) || "MRA".equals(dbmsType.toUpperCase())){
	//		"jdbc:mariadb://{ip}:{port}/{dbName}");		
			String[] arr = connTrgLnkUrl.split(":");
			dbIp = arr[2].substring(2);
			port = arr[3].split("[/]")[0];
			dbServiceName = arr[3].split("[/]")[1];
			dbmsType = "MARIA";
	
		}
		
		if(trg == "port"){
			return port;
		}else if(trg == "dbIp"){
			return dbIp;
		}else if(trg == "dbServiceName"){
			return dbServiceName;
		}else if(trg == "orgNm"){
			return orgNm;
		}else if(trg == "infoSysNm"){
			return infoSysNm;
		}else if(trg == "dbConnTrgLnm"){
			return dbConnTrgLnm;
		}else if(trg == "dbmsVer"){
			return dbmsVer;
		}else if(trg == "dbmsType"){
			return dbmsType;
		}else if(trg == "system"){
			return system;
		}else {
			return "";
		}
	}
	
	public static CellStyle setBsCellStyle(XSSFWorkbook workbook, String align) {
	    
		CellStyle style = workbook.createCellStyle(); 
		
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		
		if(align.equals("L")) {
			style.setAlignment(CellStyle.ALIGN_LEFT);
		}else if(align.equals("C")) {
			style.setAlignment(CellStyle.ALIGN_CENTER);
		}else if(align.equals("R")) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);	
		}
		
		XSSFFont font = workbook.createFont();
		
//		font.setFontName("굴림체");	
		font.setFontName("맑은 고딕");	
		font.setFontHeightInPoints((short)11); 
	
		style.setFont(font);
		
						
	    return style;
	}
	
	public static CellStyle setValAnaResultThCellStyle(XSSFWorkbook workbook, String align, boolean isWrapText) {
	    
		XSSFCellStyle style = workbook.createCellStyle(); 
		
		style.setWrapText(isWrapText);
		
		style.setBorderTop(BorderStyle.THIN);
//		style.setBorderColor(BorderSide.TOP, new XSSFColor(new Color(124,124,124)));//#7C7C7C
		style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);
		style.setBorderBottom(BorderStyle.THIN);
//		style.setBorderColor(BorderSide.BOTTOM, new XSSFColor(new Color(150,150,150)));//#969696
		style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);
		
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		if(align.equals("L")) {
//			style.setAlignment(CellStyle.ALIGN_LEFT);
//			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setAlignment((short) 2); // 가로 중앙 정렬
			style.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		}else if(align.equals("C")) {
//			style.setAlignment(CellStyle.ALIGN_CENTER);
//			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setAlignment((short) 2); // 가로 중앙 정렬
			style.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		}else if(align.equals("R")) {
//			style.setAlignment(CellStyle.ALIGN_RIGHT);
//			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setAlignment((short) 2); // 가로 중앙 정렬
			style.setVerticalAlignment((short) 1); // 세로 중앙 정렬
		}
		
		XSSFColor color = new XSSFColor(new Color(221,221,221));//#DDDDDD
//		style.setFillForegroundColor(color);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index); // 배경색
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFFont font = workbook.createFont();
		
		font.setFontName("맑은 고딕");	
		font.setFontHeightInPoints((short)11); 
		font.setBold(true);
		style.setFont(font);
		
						
	    return style;
	}
	
	public static CellStyle setValAnaResultTdCellStyle(XSSFWorkbook workbook, String align, boolean isWrapText, int fontSize) {
	    
		XSSFCellStyle style = workbook.createCellStyle(); 
		
		style.setWrapText(isWrapText);
		
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderColor(BorderSide.TOP, new XSSFColor(new Color(150,150,150)));
		
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderColor(BorderSide.BOTTOM, new XSSFColor(new Color(150,150,150)));
		
		style.setBorderLeft(BorderStyle.NONE);
		style.setBorderRight(BorderStyle.NONE);
		
		if(align.equals("L")) {
			style.setAlignment(CellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
		}else if(align.equals("C")) {
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
		}else if(align.equals("R")) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
		}
		
		XSSFColor color = new XSSFColor(new Color(255,255,255));
		style.setFillForegroundColor(color);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFFont font = workbook.createFont();
		
		font.setFontName("맑은 고딕");	
		font.setFontHeightInPoints((short)fontSize);
		font.setColor(new XSSFColor(new Color(51, 51, 51)));
		style.setFont(font);
		
						
	    return style;
	}
	
	public static CellStyle setValAnaResultTdCellStyle(XSSFWorkbook workbook, String align, boolean isWrapText) {
		return setValAnaResultTdCellStyle( workbook,  align,  isWrapText, 11);
	}

}
