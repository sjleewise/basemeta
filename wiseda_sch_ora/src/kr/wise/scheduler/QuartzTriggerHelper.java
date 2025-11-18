/************************************************************************************
 * wiseitech.wisecomp.helper.batch.CTriggerHelper 클래스 수정
 ************************************************************************************/

package kr.wise.scheduler;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import kr.wise.commons.Utils;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

public class QuartzTriggerHelper
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(QuartzTriggerHelper.class);

    public static final int DAY_OF_WEEK = 127;
    public static final long MONTHS = 4095L;
    
    public static final String ONCE = "O";
	public static final String DAY  = "D";
	public static final String WEEK = "W";
	public static final String MONTH = "M";
	public static final String HOUR = "H";  //매시
	public static final String MINUTE = "N";  //매분
	
	public static final String EVERYDAY = "00";
	public static final String WORKDAY  = "01";
	public static final String ANYDAY   = "02";
	public static final String WORKDAY6 = "03";
	
    public static final String[] weeks = {"일", "월", "화", "수", "목", "금", "토"};
    public static final String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    /**
     * 일시적으로 사용되는 Trigger를 생성한다.
     * @param startDateTime 시작일시
     * @return Trigger 해당 시작일자, 시작시간, 시작분이 적용된 Trigger
     */
    public static Trigger getOnceTrigger(Date startDateTime)
    {
        Trigger returnTrigger = new SimpleTrigger();
        returnTrigger.setStartTime(startDateTime);

        returnTrigger.setMisfireInstruction(Trigger.INSTRUCTION_NOOP);
        
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");
        
        return returnTrigger;
    }

    /**
     * 일시적으로 사용되는 Trigger를 생성한다.
     * @param startDate 시작일자
     * @param hour	시작시간 (00-23)
     * @param min	시작분 (0-59)
     * @return Trigger 해당 시작일자, 시작시간, 시작분이 적용된 Trigger
     */
    public static Trigger getOnceTrigger(String startDate, int hour, int min)
    {
        Trigger returnTrigger = null;
        
        if( validateMinute(min) && validateHour(hour))			// hour 및 min validation
        {
            Calendar tmpCalendar = getStartCalendar(startDate, hour, min);

            returnTrigger = new SimpleTrigger();
            returnTrigger.setStartTime(tmpCalendar.getTime());

            returnTrigger.setMisfireInstruction(Trigger.INSTRUCTION_NOOP);
        }
        
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");
        
        return returnTrigger;
    }

    /**
     * 일(日) Type의 Trigger를 생성한다.
     * @param dayType 일 형태(EVERYDAY : 매일, WORKDAY : 영업일(주5일), WORKDAY6 : 영업일(주6일), ANYDAY : 매 X일마다
     * @param days dayType이 ANYDAY일 경우 적용되는 X일 
     * @param startDate 시작일자  ( yyyyMMdd )
     * @param hour	시작시간 (00-23)
     * @param min	시작분 (0-59)
     * @return Trigger 각각의 형태에 일치하는 일(日) Type의 Trigger
     */
    public static Trigger getDayTrigger(String dayType, int days, String startDate, int hour, int min)
    {
        Trigger returnTrigger = null;
        
        if( validateMinute(min) && validateHour(hour) && validateDays(days))	// hour 및 min validation
        {
	        Calendar tmpCalendar = getStartCalendar(startDate, hour, min);
	
	        if(dayType.equals(EVERYDAY))			// 매일
	        {
	            returnTrigger = TriggerUtils.makeDailyTrigger(hour, min);
	            returnTrigger.setStartTime(tmpCalendar.getTime());

				returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
	        }
	        else if(dayType.equals(WORKDAY))		// 영업일 (주5일)
	        {
	            String tmpExpression = "0 "+ min + " " + hour + " ? * 2-6";
	            CronTrigger tmpTrigger = new CronTrigger();
	            try
	            {
	                tmpTrigger.setCronExpression(tmpExpression);
	                tmpTrigger.setStartTime(tmpCalendar.getTime());
	            }
	            catch (ParseException e)
	            {
	                return null; /* never happens... */
	            }
	
	            returnTrigger = tmpTrigger;
	            returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
	        }
	        else if(dayType.equals(WORKDAY6))		// 영업일 (주6일)
	        {
	            String tmpExpression = "0 "+ min + " " + hour + " ? * 2-7";
	            CronTrigger tmpTrigger = new CronTrigger();
	            try
	            {
	                tmpTrigger.setCronExpression(tmpExpression);
	                tmpTrigger.setStartTime(tmpCalendar.getTime());
	            }
	            catch (ParseException e)
	            {
	                return null; /* never happens... */
	            }
	            
	            returnTrigger = tmpTrigger;
	            returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
	        }
	        else if(dayType.equals(ANYDAY))			// 매 며칠마다
	        {
	            returnTrigger = TriggerUtils.makeHourlyTrigger(days*24);
	            returnTrigger.setStartTime(tmpCalendar.getTime());
	
	            returnTrigger.setMisfireInstruction(Trigger.INSTRUCTION_NOOP);
	        }
        }
		
        if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");

        return returnTrigger;
    }

    /**
     * 주 단위의 Trigger를 생성한다.
     * @param weekCycle	주 주기
     * @param daysOfWeek	요일 (적용요일 월~일 복수선택 가능 - 표현 형태 : 월, 수, 토 -> 1010010)
     * @param startDate 시작일자  ( yyyyMMdd )
     * @param hour	시작시간 (00-23)
     * @param min	시작분 (0-59)
     * @return	Trigger 주 단위의 Trigger
     */
    public static Trigger getWeekTrigger(int weekCycle, int daysOfWeek, String startDate, int hour, int min)
    {
        Trigger returnTrigger = null;

        if( validateMinute(min) && validateHour(hour) && 
                validateWeekCycle(weekCycle) && validateDaysOfWeek(daysOfWeek) )	// min, hour, weekCycle, daysOfWeek validate
        {
            Calendar tmpCalendar = getStartCalendar(startDate, hour, min);
            
			String tmpSchdayow = Integer.toString(daysOfWeek);
			String strSchdayow = tmpSchdayow;
			if (tmpSchdayow.length() < 7) {
				for (int s=0 ; s < (7-tmpSchdayow.length()) ; s++) {
					strSchdayow = "0" + strSchdayow;
				}
			}
			
            //String tmpExpression = "0 "+ min + " "+ hour + " ? */" + weekCycle + " "+ convertExpression(Integer.toString(daysOfWeek));
            String tmpExpression = "0 "+ min + " "+ hour + " ? */" + weekCycle + " "+ convertCronExpression(strSchdayow);
            CronTrigger tmpTrigger = new CronTrigger();

	        try
	        {
	            tmpTrigger.setCronExpression(tmpExpression);
	            tmpTrigger.setStartTime(tmpCalendar.getTime());
	        } 
	        catch (Exception ignore) 
	        {
	            return null; /* never happens... */
	        }		
	
			returnTrigger = tmpTrigger;
			returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        }
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");
	
        return returnTrigger;
    }

    /**
     * 월 단위의 Trigger를 생성한다.
     * @param months	적용 월 ( 매월작업시 1~12달 복수선택 가능 - 표현 형태 : 1, 2, 3, 12월 -> 111000000001 )
     * @param dayOfMonth	적용될 일
     * @param startDate 시작일자  ( yyyyMMdd )
     * @param hour	시작시간 (00-23)
     * @param min	시작분 (0-59)
     * @return	Trigger 월 단위의 Trigger
     */
    public static Trigger getMonthTrigger(long months, int dayOfMonth, String startDate, int hour, int min)
    {
        Trigger returnTrigger = null;
        
        //  validation check (days, hour, min)
        if( validateMinute(min) && validateHour(hour) && 
                validateMonths(months) && validateDayOfMonth(dayOfMonth) )	// min, hour, months, dayOfMonth validate
        {
	        Calendar tmpCalendar = getStartCalendar(startDate, hour, min);
	        
			String tmpMonths = Long.toString(months);
			String strMonths = tmpMonths;
			if (tmpMonths.length() < 12) {
				for (int s=0 ; s < (12-tmpMonths.length()) ; s++) {
					strMonths = "0" + strMonths;
				}
			}
			
            String tmpExpression = null;
            // check last day of month
            // 31 - 1,3,5,7,8,10,12
            // 28:29 - 2
            // 30 - 4,6,9,11
            if( setLastDayofMonth(months, dayOfMonth))
            {
                //tmpExpression = "0 " + min + " " + hour + " L " + convertExpression(Long.toString(months)) + " ?";
                tmpExpression = "0 " + min + " " + hour + " L " + convertCronExpression(strMonths) + " ?";
            }
            else
            {
                int tmpStartDay = tmpCalendar.get(Calendar.DAY_OF_MONTH);
                if( tmpStartDay <= dayOfMonth )
                    tmpCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //tmpExpression = "0 " + min + " " + hour + " " + dayOfMonth + " " + convertExpression(Long.toString(months)) + " ?";
                tmpExpression = "0 " + min + " " + hour + " " + dayOfMonth + " " + convertCronExpression(strMonths) + " ?";
            }

	        
	        CronTrigger tmpTrigger = new CronTrigger();
	
	        try 
	        {
	            tmpTrigger.setCronExpression(tmpExpression);
	            tmpTrigger.setStartTime(tmpCalendar.getTime());
	        } 
	        catch (Exception ignore) 
	        {
	            return null; /* never happens... */
	        }		
	
	        returnTrigger = tmpTrigger;
	        returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        }
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");

        return returnTrigger;
    }
    
    /**
     * 매시간 단위의 Trigger를 생성한다.
     * @param startDate 시작일자  ( yyyyMMdd )
     * @param hour
     * @return	Trigger 매 지정된 시간  단위의 Trigger
     */
    public static Trigger getHourTrigger(String startDate, int hour)
    {
        Trigger returnTrigger = null;
        
        //  validation check (days, hour, min)  
			
        Calendar tmpCalendar = getStartCalendar(startDate, hour, 0);
	       
        String tmpExpression = null;
      
        tmpExpression = "0 0 0/"+hour+" * * ?";

        CronTrigger tmpTrigger = new CronTrigger();
	
	        try 
	        {
	            tmpTrigger.setCronExpression(tmpExpression);
	            tmpTrigger.setStartTime(tmpCalendar.getTime());
	        } 
	        catch (Exception ignore) 
	        {
	            return null; /* never happens... */
	        }		
	
	        returnTrigger = tmpTrigger;
	        returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");

        return returnTrigger;
    }
    
    /**
     * 매분 단위의 Trigger를 생성한다.
     * @param startDate 시작일자  ( yyyyMMdd )
     * @param min
     * @return	Trigger 매 지정된 분단위의 Trigger
     */
    public static Trigger getMinuteTrigger(String startDate, int min)
    {
        Trigger returnTrigger = null;
        
        //  validation check (days, hour, min)  
			
        Calendar tmpCalendar = getStartCalendar(startDate, 0, min);
	       
        String tmpExpression = null;
      
        tmpExpression = "0 0/"+min+" * * * ?";

        CronTrigger tmpTrigger = new CronTrigger();
	
	        try 
	        {
	            tmpTrigger.setCronExpression(tmpExpression);
	            tmpTrigger.setStartTime(tmpCalendar.getTime());
	        } 
	        catch (Exception ignore) 
	        {
	            return null; /* never happens... */
	        }		
	
	        returnTrigger = tmpTrigger;
	        returnTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        
		if( !Utils.isNull(returnTrigger))
            logger.debug("StartTime ["+returnTrigger.getStartTime()+"]");

        return returnTrigger;
    }
    
    /**
     * 일수가 해당월의 말일(Last Day of Month) 여부를 체크한다.
     * @param months 적용월
     * @param dayOfMonth 적용될 일
     * @return 말일(Last Day of Month) 여부
     */
    private static boolean setLastDayofMonth(long months, int dayOfMonth)
    {
        months = Long.parseLong(new Long(months).toString(), 2);
        
        long february   = 1024L;

        long april      = 256L;
        long june       = 64L;
        long september  = 8L;
        long november   = 2L;
        
        if( (february & months) != february )       // 2월
        {
            if(dayOfMonth >= 28)
                return true;
        }
        // 4,6,9,11월
        else if( (april & months) == april || (june & months) == june || 
                 (september & months) == september || (november & months) == november )
        {
            if(dayOfMonth >= 30)
                return true;
        }
        else
        {
            if(dayOfMonth >= 31)
                return true;
        }
        
        return false;
    }
    
	/**
	 * 시작일에 시/분을 적용한 Calendar를 반환한다.
     * @param startDate	시작일자 ( yyyyMMdd )
     * @param hour	시작시간
     * @param min	시작분
     * @return Calendar 시작 일/시/분이 적용
     */
    private static Calendar getStartCalendar(String startDate, int hour, int min) {
        Calendar tmpCalendar = Calendar.getInstance();
//        tmpCalendar.setTime(startDate);

        int year  = Integer.parseInt(startDate.substring(0, 4));
        int month = Integer.parseInt(startDate.substring(4, 6));
        int date  = Integer.parseInt(startDate.substring(6, 8));
        
        tmpCalendar.set(year, month-1, date);
        tmpCalendar.set(Calendar.HOUR_OF_DAY, hour);
        tmpCalendar.set(Calendar.MINUTE, min);
        tmpCalendar.set(Calendar.SECOND, 0);
        
        return tmpCalendar;
    }
    
    /**
     * Months, Day of Week의 표현을 Quartz에서 사용 가능 양식(Cron 방식)으로 변환시킨다.
     * QRTZ_CronTrigger 일 : 1, 월 : 2, 화 : 3, 수 : 4, 목 : 5, 금 : 6, 토 : 7 
     *                  1월 : 1, 2월 : 2, 3월 : 3, 4월 : 4.... 
     * TICSCH1 일 : 0000001, 월 : 0000010, 화 : 0000100, 수 : 0001000, 목 : 0010000, 금 : 0100000, 토 : 1000000
     *        1월 : 000000000001, 2월 : 000000000010, 3월 : 000000000100, 4월 : 000000001000....
     * @param tmpWeekString
     * @return
     */
    private static String convertCronExpression(String tmpString)
	{
		String returnString = "";

		boolean isMulti = false;
		
		for(int i=tmpString.length() ; i > 0 ; i--)
		{
			char tmpChar = tmpString.charAt(i-1);
			
			if(tmpChar == '1')
			{
				if (!isMulti) {
					returnString += Integer.toString(tmpString.length() - i + 1);
					isMulti = true;
				} else {
					returnString += ","+Integer.toString(tmpString.length() - i + 1);
				}
			}
		}
		return returnString;
	}    

    /**
     * 분에 대한 입력값 검증을 한다.
     * @param minute 분
     * @return boolean 0 ~ 59 : true, 이외의 경우 : false
     */
    public static boolean validateMinute(int minute)
    {
        if (minute < 0 || minute > 59) 
            return false;
        
        return true;
    }
    
    /**
     * 시(時)에 대한 입력값 검증을 한다.
     * @param hour 시
     * @return boolean 0 ~ 23 : true, 이외의 경우 : false
     */
    public static boolean validateHour(int hour)
    {
        if (hour < 0 || hour > 23) 
            return false;
        
        return true;
    }

    /**
     * 일(日)에 대한 입력값 검증을 한다.
     * @param days 일
     * @return boolean 0 ~ 365 : true, 이외의 경우 : false
     */
    public static boolean validateDays(int days)
    {
        if( days < 0 || days > 365)
            return false;
        
        return true;
    }
    
    /**
     * 주 주기에 대한 입력값 검증을 한다.
     * @param weekCycle 주 주기
     * @return boolean 1 ~ 51 : true, 이외의 경우 : false
     */
    public static boolean validateWeekCycle(int weekCycle)
    {
        if( weekCycle < 1 || weekCycle > 51)
            return false;

        return true;
    }
    
    /**
     * 요일에 대한 입력값 검증을 한다.
     * @param daysOfWeek 요일 (표현 형태 : 월, 수, 토 -> 1010010)
     * @return boolean 0000001 ~ 1111111 : true, 이외의 경우 : false
     */
    public static boolean validateDaysOfWeek(int daysOfWeek)
    {
        daysOfWeek = Integer.parseInt(new Integer(daysOfWeek).toString(), 2);
        
        if( (DAY_OF_WEEK & daysOfWeek) != daysOfWeek )
            return false;
        
        return true;
    }

    /**
     * 달 일수에 대한 입력값 검증을 한다
     * @param dayOfMonth	달 일수
     * @return boolean 0 ~ 31 : true, 이외의 경우 : false
     */
    public static boolean validateDayOfMonth(int dayOfMonth)
    {
        if ((dayOfMonth < 0 || dayOfMonth > 31)) 
            return false;
        
        return true;
    }
    
    /**
     * 월에 대한 입력값 검증을 한다.
     * @param months 월 (1~12달 복수선택 가능, 표현 형태 : 1, 2, 3, 12 : 111000000001 )
     * @return booelan 000000000000 ~ 11111111111 : true, 이외의 경우 : false
     */
    public static boolean validateMonths(long months)
    {
        months = Long.parseLong(new Long(months).toString(), 2);
        
        if( (MONTHS & months) != months )
            return false;
        
        return true;
    }
}
