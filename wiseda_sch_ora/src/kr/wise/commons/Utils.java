package kr.wise.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Utils {

	/**
	 * Object의 Null Check
	 * @param tmpObject Null Check할 Object
	 * @return Object가 null이면 true, instance화 되어 있으면 false
	 */
	public static boolean isNull(Object tmpObject)
	{
		if(tmpObject == null)
			return true;
		else
			return false;
	}

	/**
	 * Object 배열의 Null Check
	 * @param tmpObject Null Check할 Object 배열
	 * @return Object배열이 null이면 true, instance화 되어 있으면 false
	 */
	public static boolean isArrayNull(Object[] tmpObject)
	{
		if(tmpObject == null || tmpObject.length < 1 )
			return true;
		else
			return false;
	}
	
	/**
	 * 문자열을 byte 길이로 잘라 리턴한다.
	 * @param src
	 * @param endIndex
	 * @return
	 */
	public static String subStringByByte(String src, int endIndex) {
		if(src == null) {
			return "";
		}
		byte [] temp = src.getBytes();
		
		if(temp.length <= endIndex) {
			return src;
		}
		
		byte [] b = new byte [endIndex];
		System.arraycopy(src.getBytes(), 0, b, 0, endIndex);
		return new String(b);
	}
	
	/**
	 * src 가 null 이거나 숫자값이 아니면 i 를 리턴, 그렇지 않으면 src 를 intValue 로 리턴
	 * @param src
	 * @param i
	 * @return
	 */
	public static int nvlInt(String src, int i) {
		if(src == null) {
			return i;
		} else {
			try {
				return Integer.parseInt(src);
			} catch(Exception e) {
				return i;
			}
		}
	}
	
	/**
	 * java 날짜패턴 yyyy-MM-dd HH:mm:ss
	 * @param pattern
	 * @return
	 */
	public static String getCurrDttm(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * @param dateStr yyyyMMddHHmmss 의 숫자값
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String fomatDate(String dateStr, String pattern) {
        int year = 0;
        int month = 0;
        int day = 1;
        int hour = 0;
        int minute = 0;
        int second = 0;

        switch (dateStr.length()) {
        case (14):
            second = Integer.parseInt(dateStr.substring(13, 14));
        case (12):
            minute = Integer.parseInt(dateStr.substring(10, 12));
        case (10):
            hour = Integer.parseInt(dateStr.substring(8, 10));
        case (8):
            day = Integer.parseInt(dateStr.substring(6, 8));
        case (6):
            month = Integer.parseInt(dateStr.substring(4, 6)) - 1;
        case (4):
            year = Integer.parseInt(dateStr.substring(0, 4));
            break;
        default:
            return "date Length Error";
        }
		Calendar calendar = new GregorianCalendar(year, month, day, hour, minute, second);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}

	public static String join(String [] args) {
		return join(args, "");
	}
	public static String join(String [] args, String delim) {
		if(args == null) return null;
		String str = "";
		for(int i=0; i<args.length; i++) {
			if(args [i] == null) continue;
			str += args [i];
			if(i < (args.length-1)) {
				str += delim;
			}
		}
		return str;
	}

    /**
     * 문자값에서 숫자값 문자열만 반환하도록 변경<br>
     * (숫자가 아닌 모든 문자열을 제거한다.)
     * @param str String
     * @return 숫자 문자열
     */
    public static String getOnlyNumberStr(String str)
    {
		if(str == null) return "";
		return str.replaceAll("[^0-9]", "");
    }
    
    
    /**
     * Object가 null일 경우 Blank String으로 반환한다.
     * @param obj
     * @return String 입력 Object가 null일 경우 "" 반환, 이외의 경우에는 입력 Object의 toString() 반환
     */
    public static String null2Blank(Object obj)
    {
        if( obj != null )
            return(obj.toString());
        else
            return "";
    }

}
