/** 
 * Package              : wiseitech.wisecomp.util
 * Classname            : CDateUtil.java
 * Initial date         : 2004. 12. 29
 * Author               : 김형배
 * Update date/Modifier : 2001.12.1/공통팀
 * Description          : 
 * Version information  : v 1.0
 *
 * Copyright notice     : Copyright (C)2001 by wiseItech Co. Ltd.
 *                        All right reserved.
 */

package kr.wise.commons.util;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @version 1.0
 * 클래스 설명 ------------------------------------------------------------------------
 * Date관련 각종 기능성 Method를 제공하는 Util Class<br>
 * ----------------------------------------------------------------------------------
 */
public class UtilDate
{
    public static final long MILLISECONDS_IN_HOUR = 60L * 60L * 1000L;
    
    private static final MessageFormat sDashForm = new MessageFormat("{0}-{1}-{2}");
    private static final MessageFormat sCommaForm = new MessageFormat("{0}.{1}.{2}");
    private static final MessageFormat sSlashForm = new MessageFormat("{0}/{1}/{2}");
    private static final MessageFormat sBlankForm = new MessageFormat("{0}{1}{2}");

    private static final SimpleDateFormat dDashForm = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dCommaForm = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat dSlashForm = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat dBlankForm = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat dSecondForm = new SimpleDateFormat("yyyy-MM-dd H:m:s");
    
    public static Calendar getCurrentCalendar()
    {
        return Calendar.getInstance();
    }
    
    
    /**
     * 현재 일자을 YYYYMMDD 형식으로 반환한다.
     * @return 현재일자(YYYMMDD)
     */
    public static String getCurrentDate()
    {
        Calendar cal = getCurrentCalendar();

        String currentYear = String.valueOf(cal.get(Calendar.YEAR));
        String currentMonth = String.valueOf((cal.get(Calendar.MONTH) + 1));
        String currentDay = String.valueOf(cal.get(Calendar.DATE));

        if (Integer.parseInt(currentMonth) < 10) currentMonth = "0"
                + currentMonth;

        if (Integer.parseInt(currentDay) < 10) currentDay = "0" + currentDay;

        String vCurrentDate = currentYear + currentMonth + currentDay;

        return vCurrentDate;
    }
    
    /**
     * 현재 일자을 YYYY-MM-DD HH:MI:SS 형식으로 반환한다.
     * @return 현재일자(YYYYMMDDHHMISS)
     */
    public static String getCurrentDateHms()
    {
    	Calendar cal = getCurrentCalendar();
    	
    	String currentYear = String.valueOf(cal.get(Calendar.YEAR));
    	String currentMonth = String.valueOf((cal.get(Calendar.MONTH) + 1));
    	String currentDay = String.valueOf(cal.get(Calendar.DATE));
    	String currentHour = String.valueOf(cal.get(Calendar.HOUR));
    	String currentMin = String.valueOf(cal.get(Calendar.MINUTE));
    	String currentSec = String.valueOf(cal.get(Calendar.SECOND));
    	
    	if (Integer.parseInt(currentMonth) < 10) currentMonth = "0"
    			+ currentMonth;
    	
    	if (Integer.parseInt(currentDay) < 10) currentDay = "0" + currentDay;
    	
    	String vCurrentDate = currentYear +"-"+ currentMonth +"-"+ currentDay +" "+currentHour+":"+currentMin+":"+currentSec;
    	
    	return vCurrentDate;
    }
    
    /**
     * 현재 일자에 일정 Day를 합한 일자를 YYYYMMDD 형식으로 반환한다.
     * @param day 추가할 일자
     * @return day가 추가된 일자(YYYYMMDD)
     */
    public static String addDay(int day)
    {
        Calendar cal = getCurrentCalendar();
        cal.add(Calendar.DATE, day);

        Date tmpDate = new Date(cal.getTimeInMillis());
        return date2Str(tmpDate, ' ');
    }
    
    /**
     * 현재 일자에 일정 Day를 뺀 일자를 YYYYMMDD 형식으로 반환한다.
     * @param day 뺄 일자
     * @return day가 빠진 일자(YYYYMMDD)
     */
    public static String minusDay(int day)
    {
        return addDay(-1 * day);
    }
    
    /**
     * 기준 일자에 일정 Day를 합한 일자를 YYYYMMDD 형식으로 반환한다.
     * @param targetDate 기준일자
     * @param day 추가할 일자
     * @return 기준일자에 day가 추가된 일자(YYYYMMDD)
     */
    public static String addDay(String targetDate, int day)
    {
        Calendar cal = str2Calendar(targetDate);
        cal.add(Calendar.DATE, day);

        Date tmpDate = new Date(cal.getTimeInMillis());

        if(targetDate.indexOf('-') != -1)
            return date2Str(tmpDate, '-');
        else if(targetDate.indexOf('.') != -1)
            return date2Str(tmpDate, '.');
        else if(targetDate.indexOf('/') != -1)
            return date2Str(tmpDate, '/');
        else
            return date2Str(tmpDate, ' ');
    }

    /**
     * 기준 일자에 일정 Day를 뺀 일자를 YYYYMMDD 형식으로 반환한다.
     * @param targetDate 기준일자
     * @param day 뺄 일자
     * @return 기준일자에 day가 빠진 일자(YYYYMMDD)
     */
    public static String minusDay(String targetDate, int day)
    {
        return addDay(-1 * day);
    }
    
    /**
     * 날짜 Format을 변경기능을 제공
     * maskType으로 '-', '.', '/', ''의 Format을 지원
     * @param cToStr Format을 변경코저하는 일자 String
     * @param maskType 변경코저 하는 maskType 
     * @return 변환된 String
     */
    public static String str2Str(String cToStr, char maskType)
    {
    	String returnString = "";
    	
    	if (cToStr != null && !cToStr.equals(""))
    	{
    		if(cToStr.length() > 8)
    		{
    			cToStr = UtilString.deleteChar(cToStr, '-');
    			cToStr = UtilString.deleteChar(cToStr, '.');
    			cToStr = UtilString.deleteChar(cToStr, '/');
    		}
    		
    		String[] tmpStrings = {cToStr.substring(0, 4),cToStr.substring(4, 6),cToStr.substring(6, 8)};
    
    		switch(maskType)
    		{
    			case '-' :
    				returnString = sDashForm.format(tmpStrings);
    				break;
    				
    			case '.' :
    				returnString = sCommaForm.format(tmpStrings);
    				break;
    
    			case '/' :
    				returnString = sSlashForm.format(tmpStrings);
    				break;
    				
    			default :
    				returnString = sBlankForm.format(tmpStrings);
    				break;
    		}
    	}
    
    	return returnString;
    }


    /**
     * 날짜 String을 java.sql.Date 형식으로 변환하는 기능 제공
     * @param cToStr Date형으로 변환할 날짜 String
     * @return Date Object
     */
    public static Date str2Date(String cToStr)
    {
    	if ( !UtilString.isBlank(cToStr) )
    	{
    		String cTemp = str2Str(cToStr, '-');
//    		Date cToDate = Date.valueOf(cTemp);
    		java.sql.Date cToDate = java.sql.Date.valueOf(cTemp);
    		return cToDate;
    	}
    
    	return null;
    }

    
    /**
     * 날짜 String을 Calendar 형식으로 변환하는 기능을 제공
     * @param cToStr Date형으로 변환할 날짜 String
     * @return 변환된 Calendar
     */
    public static Calendar str2Calendar(String cToStr)
    {
        Date tmpDate = str2Date(cToStr);
        
        if( !UtilObject.isNull(tmpDate) )
        {
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTime(tmpDate);
            
            return tmpCalendar;
        }
        
        return null;
    }

    /**
     * java.sqlDate를 날짜 String으로 변환하는 기능 제공
     * maskType으로 '-', '.', '/', ''의 Format을 지원 (':'일시에는 시:분:초 포함) 
     * @param cToDate 날짜 String으로 변경코저하는 Date Object
     * @param maskType 변경코저 하는 maskType
     * @return 날짜 String
     */
    public static String date2Str(Date cToDate, char maskType)
    {
    	String returnString = "";
    	
    	if (cToDate != null)
    	{
    		switch(maskType)
    		{
    			case '-' :
    				returnString = dDashForm.format(cToDate);
    				break;
    				
    			case '.' :
    				returnString = dCommaForm.format(cToDate);
    				break;
    
    			case '/' :
    				returnString = dSlashForm.format(cToDate);
    				break;
    				
    			case ':' :
    			    returnString = dSecondForm.format(cToDate);
    			    break;
    				
    			default :
    				returnString = dBlankForm.format(cToDate);
    				break;
    		}
    	}
    
    	return returnString;
    }


    /**
     * Date를 Calendar로 변환한다.
     * Date(yyyy-mm-dd) -> Calendar
     * @param cToDate Date
     * @return 변환된 Calendar
     */
    public static final Calendar date2Calendar(Date cToDate)
    {
    	if (cToDate != null)
    	{
    		java.util.Calendar dateToCal = java.util.Calendar.getInstance();
    		dateToCal.setTime(cToDate);
    		dateToCal.add(java.util.Calendar.DATE, 0);
    		return dateToCal;
    	}
    	return null;
    }


    /**
     * Date를 TimeStamp로 변환한다.
     * Date(yyyy-mm-dd) -> TimeStamp
     * @param cToDate Date
     * @return Timestamp
     */
    public static final Timestamp date2Timestamp(Date cToDate)
    {
    	if (cToDate != null)
    	{
    		Timestamp tsDate = new Timestamp(cToDate.getTime());
    		return tsDate;
    	}
    	return null;
    }


    /**
     * 시작일자와 종료일자 사이의 일수를 구한다.<BR>
     * 일자 형식은 "YYYYMMDD"로 한다.<BR>
     * 예를 들어 20020501 ~ 20020510 = 9 <BR>
     * @param   sFromDate    : 시작일 YYYYMMDD<BR>
     * @param   sToDate      : 종료일 YYYYMMDD<BR>
     * @return  long         : 일수간격<BR>
     */
    public static long daysBetween(String sFromDate, String sToDate)
    {
        Date fDate = str2Date(sFromDate);
        Date tDate = str2Date(sToDate);
        
        return daysBetween(fDate, tDate);
    }
    
    /**
     * 시작일자와 종료일자 사이의 일수를 구한다.
     * @param d1 시작일자
     * @param d2 종료일자
     * @return 시작일자와 종료일자 사이 일수
     */
    public static long daysBetween(Date d1, Date d2)
    {
        return ((d2.getTime() - d1.getTime() + MILLISECONDS_IN_HOUR) / (MILLISECONDS_IN_HOUR * 24));
    }   
    
    
    /**
     * 두개의 날짜를 String으로 입력받아 비교하는 메소드
     * stdDate > compDate : true, stdDate <= compDate : false;
     * @param stdDate 기준Date
     * @param compDate 비교Date
     * @return boolean (stdDate > compDate : true, stdDate <= compDate : false)
     */
    public static boolean compareDate(String stdDate, String compDate)
    {
       Calendar cd1 = str2Calendar(stdDate);
       Calendar cd2 = str2Calendar(compDate);
       
       if (cd1.after(cd2))
          return true;
       else
          return false;
    } 
    
    
    
    
    /**
     * Timestampe를 String으로 변환한다.
     * @param tmValue
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String timestamp2Str(Timestamp tmValue)
    {
    	SimpleDateFormat timeKeyFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return timeKeyFormat.format( tmValue);
    }
}