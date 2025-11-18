/** 
 * Package              : wiseitech.wisecomp.util.dm
 * Classname            : CClassUtil.java
 * Initial date         : 2005. 2. 4.
 * Author               : 김형배
 * Update date/Modifier : 2001.12.1/공통팀
 * Description          : 
 * Version information  : v 1.0
 *
 * Copyright notice     : Copyright (C)2001 by wiseItech Co. Ltd.
 *                        All right reserved.
 */

package kr.wise.commons.util;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * @version 1.0
 * 클래스 설명 ------------------------------------------------------------------------
 * Reflect 기능을 이용하여 Object instance의 Method를 사용하는 invoke 기능을 사용하는 Class
 * 통상 set method를 호출하여 Object instance의 내부 데이타를 setting한다.
 * ----------------------------------------------------------------------------------
 */
public class UtilClass
{
   
    /**
     * Object에 있는 method을 invoke시켜 리턴 Object를 반환한다.(get Method에 사용)
     * @param method Method
     * @param obj Object
     * @return setting된 Object
     */
    public static Object invoke(Method method, Object obj)
    {
        Object tmpObject = null;
        
        try
        {
            return method.invoke(obj, null);
        }
        catch (Exception e)
        {
            // logger.error("메소드 호출시에 에러 발생 : ", e);
        }
        
        return tmpObject;
    }
    
    
    /**
	 * Object에 있는 method을 invoke시켜 Object를 Setting한다.(set Method에 사용)
	 * HttpRequest에 저장되어 있는 value들을 Dm의 정해진 field에 설정하는 메소드(setMethod에 사용)
	 * @param method Method
	 * @param object Object
	 * @param value Object[]
	 */
   	public static void invoke(Method method, Object object, Object[] value)
	{
		Class[] parameterTypes = method.getParameterTypes();
		Object[] parameters = new Object[1];

		if (parameterTypes.length != 1)
		{
			// logger.error("메소드의 파라미터 수가 맞지 않습니다 : ");
			return;
		}
		
		boolean nullFlag = nullFlagCheck(value);

		try
		{
			if( !nullFlag )								// null Check
			{
				if( !parameterTypes[0].isArray())		// 배열 여부 Check
				{
					// 1. 데이터형이 java.sql.Date인 경우
					if ( parameterTypes[0].getName().equals("java.sql.Date") )
						parameters[0] = convertToDate(value[0]);
					// 2. 데이터형이 java.sql.Time인 경우
					else if( parameterTypes[0].getName().equals("java.sql.Time") )
						parameters[0] = convertToTime(value[0]);
					// 3. 데이터형이 java.sql.Timestamp인 경우
					else if( parameterTypes[0].getName().equals("java.sql.Timestamp") )
						parameters[0] = convertToTimestamp(value[0]);
					else if( parameterTypes[0].getName().equals("java.math.BigDecimal") 
							|| parameterTypes[0].getName().equals("java.math.BigInteger")
							|| parameterTypes[0].getName().equals("java.lang.Double")
							|| parameterTypes[0].getName().equals("java.lang.Float")
							|| parameterTypes[0].getName().equals("java.lang.Integer")
							|| parameterTypes[0].getName().equals("java.lang.Long")
							|| parameterTypes[0].getName().equals("java.lang.Short")
							|| parameterTypes[0].getName().equals("double")
							|| parameterTypes[0].getName().equals("float")
							|| parameterTypes[0].getName().equals("int")
							|| parameterTypes[0].getName().equals("long")
							|| parameterTypes[0].getName().equals("short") )
					{
						value[0] = UtilNumber.getOnlyNumberStr((String) value[0]);
						parameters[0] = returnObjectValue(value[0], parameterTypes[0]);
					}
					else
					{
						parameters[0] = returnObjectValue(value[0], parameterTypes[0]);
					}
				}
				else									// 배열일 경우
				{
					// 1. 데이터형이 [Ljava.sql.Date인 경우
					if ( parameterTypes[0].getName().equals("[Ljava.sql.Date;") )
						parameters[0] = convertToDate(value);
					// 2. 데이터형이 java.sql.Time인 경우
					else if( parameterTypes[0].getName().equals("[Ljava.sql.Time;") )
						parameters[0] = convertToTime(value);
					// 3. 데이터형이 java.sql.Timestamp인 경우
					else if( parameterTypes[0].getName().equals("[Ljava.sql.Timestamp;") )
						parameters[0] = convertToTimestamp(value);
					else if( parameterTypes[0].getName().equals("[Ljava.math.BigDecimal;") 
							|| parameterTypes[0].getName().equals("[Ljava.math.BigInteger;")
							|| parameterTypes[0].getName().equals("[Ljava.lang.Double;")
							|| parameterTypes[0].getName().equals("[Ljava.lang.Float;")
							|| parameterTypes[0].getName().equals("[Ljava.lang.Integer;")
							|| parameterTypes[0].getName().equals("[Ljava.lang.Long;")
							|| parameterTypes[0].getName().equals("[Ljava.lang.Short;")
							|| parameterTypes[0].getName().equals("[D")		// double
							|| parameterTypes[0].getName().equals("[F")		// float
							|| parameterTypes[0].getName().equals("[I")		// int
							|| parameterTypes[0].getName().equals("[J")		// long
							|| parameterTypes[0].getName().equals("[S") )	// short
					{
						for(int i=0; i<value.length; i++)
							value[i] = UtilNumber.getOnlyNumberStr((String) value[i]);

						parameters[0] = returnObjectValue(value, parameterTypes[0]);
					}
					else
					{
						parameters[0] = returnObjectValue(value, parameterTypes[0]);
					}
				}
				
				try
				{
					method.invoke(object, parameters);
				}
				catch(Exception e)
				{
					// logger.error("메소드 호출시에 에러 발생 : ", e);
				}
			}
		}
		catch (Exception e)
		{
//			logger.error("Dm에 Data 입력 중 오류 ", e);
		}
	}

    /**
     * value가 null 일 경우 nullFlag를 return 해 주는 method
     * 
     * @param value Object
     * @return boolean
     */
    private static boolean nullFlagCheck(Object[] value)
    {
    	boolean result = false;
    	String strMethod = "nullFlagCheck";
    	try
    	{
    		// 최초 null Check
    		if( UtilObject.isArrayNull(value))
    			result = true;
    		else
    		{
    			for(int i=0; i<value.length; i++)
    			{
    				result = nullFlagCheck(value[i]);
    				
    				if(!result)
    					break;
    			}
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("Value null 체크 중 오류", e);
    	}
    
    	return result;
    }

    private static boolean nullFlagCheck(Object value) throws Exception
    {
    	boolean result = false;
    
    	result = UtilString.isBlank((String)value);
    	return result;
    }

    /**
     * String type의 날짜 포멧을 받아서 해당하는 형식을 검사하여 Date로 반환
     * 
     * @param value Object
     * @return Date
     */
    private static Date convertToDate(Object value)
    {
    	Date result = null;
    	try
    	{
//    		result = CDateUtil.str2Date( (String)value);
    		result = (Date)UtilDate.str2Date((String)value);
    	}
    	catch (Exception e)
    	{
    		// logger.error("날짜 포멧을 받아서 해당하는 형식을 검사하여 Date로 변환 중 오류", e);
    	}
    
    	return result;
    }

    /**
     * String type의 날짜 포멧을 받아서 해당하는 형식을 검사하여 Date[]로 반환
     * 
     * @param value String[]
     * @return Date[]
     */
    private static Date[] convertToDate(Object[] value)
    {
    	Date[] result = new Date[value.length];
    	try
    	{
    		for(int i=0; i<result.length; i++)
    		{
    			result[i] = convertToDate( value[i] ); 
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("날짜 포멧을 받아서 해당하는 형식을 검사하여 Date로 변환 중 오류", e);
    	}
    
    	return result;
    }

    /**
     * Object type의 parameter를 Time로 변환
     * 
     * @param input Object
     * @return Time
     */
    private static Time convertToTime(Object input)
    {
    	Time result = null;
    	try
    	{
    		if (((String) input).trim().length() == 8 || ((String) input).trim().length() == 10)
    		{
    			Date date = null;
    			date = convertToDate(input);
    			result = new Time(date.getTime());
    		}
    		else
    		{
    			result = Time.valueOf((String) input);
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("parameter를 Time로 변환 중 오류", e);
    	}
    	return result;
    }

    /**
     * Object type의 parameter를 Time[]로 변환
     * 
     * @param input Object[]
     * @return Time[]
     */
    private static Time[] convertToTime(Object[] input)
    {
    	Time[] result = new Time[input.length];
    	try
    	{
    		for(int i=0; i<result.length; i++)
    		{
    			result[i] = convertToTime(input[i]);
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("parameter를 Time로 변환 중 오류", e);
    	}
    	return result;
    }

    /**
     * Object type의 parameter를 TimeStamp로 변환
     * 
     * @param input Object
     * @return Timestamp
     */
    private static Timestamp convertToTimestamp(Object input)
    {
    	Timestamp result = null;
    	try
    	{
    		if (((String) input).trim().length() == 8 || ((String) input).trim().length() == 10)
    		{
    			Date date = null;
    			date = convertToDate(input);
    			result = new Timestamp(date.getTime());
    		}
    		else
    		{
    			result = Timestamp.valueOf((String) input);
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("parameter를 TimeStamp로 변환 중 오류", e);
    	}
    	return result;
    }

    /**
     * Object type의 parameter를 Timestamp[]로 변환
     * 
     * @param input Object[]
     * @return Timestamp[]
     */
    private static Timestamp[] convertToTimestamp(Object[] input)
    {
    	Timestamp[] result = new Timestamp[input.length];
    	try
    	{
    		for(int i=0; i<result.length; i++)
    		{
    			result[i] = convertToTimestamp(input[i]);
    		}
    	}
    	catch (Exception e)
    	{
    		// logger.error("parameter를 TimeStamp로 변환 중 오류", e);
    	}
    	return result;
    }
    
    /**
     * args2를 Integer나 BigDecimal로 변환, 반환하는 메소드
     * 
     * @param clazz
     * @param value
     * @return Object
     * @exception Exception
     */
    private static Object returnObjectValue(Object value, Class clazz) throws Exception
    {
    	Object result = ConvertUtils.convert(value.toString(), clazz);
    	return result;
    }

    private static Object returnObjectValue(Object[] value, Class clazz) throws Exception
    {
    	Object result = ConvertUtils.convert( (String[])value, clazz);
    	return result;
    }
    
}
