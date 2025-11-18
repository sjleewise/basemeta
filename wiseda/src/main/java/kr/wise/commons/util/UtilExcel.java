package kr.wise.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilExcel {

	
	public static String getencodingfilename(String browser, String fileName)
			throws UnsupportedEncodingException {
		String encodedFilename = "";
		if (browser.equals("MSIE")) {
		    encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
		    encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
		    encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
				    sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
				    sb.append(c);
				}
		    }
		    encodedFilename = sb.toString();
		}
		return encodedFilename;
	}
	
	public static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }
	
	
}
