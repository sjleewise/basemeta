
package kr.wise.commons.util;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @version 1.0<br>
 * 클래스 설명 ------------------------------------------------------------------------<br>
 * String 관련 기능성 Method를 제공하는 Util Class<br>
 * <br>
 * ----------------------------------------------------------------------------------<br>
 */
public class UtilString
{
	/**
	 * null을 다른 문자로 변환해 준다.
	 *
	 * @param nullStr
	 * @param rplStr
	 * @return String
	 */
	public static final String replaceNull(String nullStr, String rplStr)
	{
		String result = "";
		if (nullStr == null || nullStr.trim().length() < 1)
		{
			result = rplStr;
		}
		else
		{
			result = nullStr;
		}
		return result;
	}

	/**
	 * String의 Null Check
	 * @param tmpString
	 * @return null일 경우 true, null이 아닐경우 false
	 */
	public static boolean isNull(String tmpString)
	{
		return UtilObject.isNull(tmpString);
	}

	/**
	 * String[]의 Null Check
	 * @param tmpString String 배열
	 * @return null일 경우 true, null이 아닐경우 false
	 */
	public static boolean isNull(String[] tmpString)
	{
		return UtilObject.isArrayNull(tmpString);
	}

	/**
	 * String의 Blank여부 체크
	 * @param tmpString
	 * @return null, "" 일 경우 true, null이 아니며, Blank String일 경우 false
	 */
	public static boolean isBlank(String tmpString)
	{
		if(isNull(tmpString))
			return true;
		else
		{
			if(tmpString.equals(""))
				return true;
			else
				return false;
		}
	}


	/**
	 * String[]의 Blank여부 체크
	 * @param tmpString Stirng 배열
	 * @return null, "" 일 경우 true, null이 아니며, Blank String일 경우 false
	 */
	public static boolean isBlank(String[] tmpString)
	{
		if(isNull(tmpString))
			return true;
		else
		{
			if(tmpString.length > 0)
				return false;
			else
				return true;
		}
	}


    /**
     * Object가 null일 경우 Blank String으로 반환한다.
     * @param obj
     * @return String 입력 Object가 null일 경우 "" 반환, 이외의 경우에는 입력 Object의 toString() 반환
     */
    public static String null2Blank(Object obj)
    {
        if( !UtilObject.isNull(obj) )
            return(obj.toString());
        else
            return "";
    }


	/**
	 * 특정문자를 변환한다.
	 * @param source source 문자열
	 * @param pattern 바꿀 문자패턴
	 * @param replace 적용할 문자패턴
	 * @return 변환된 문자
	 */
	public static String replace(String source, String pattern, String replace)
    {
        if (source != null)
        {
            final int len = pattern.length();
            StringBuffer sb = new StringBuffer();
            int found = -1;
            int start = 0;

            while ((found = source.indexOf(pattern, start)) != -1)
            {
                sb.append(source.substring(start, found));
                sb.append(replace);
                start = found + len;
            }

            sb.append(source.substring(start));

            return sb.toString();
        }
        else
            return "";
    }

	 /**
      * 스트링을 우측을 기준으로 size크기의 String을 반환한다.
      * @param str 입력String
      * @param size 획득코저하는 사이즈
      * @return 우측을 기준으로 size 만큼의 String
      */
    public static String getRight(String str, int size)
    {
        int tmpStringLength = str.length();

        if(size >= tmpStringLength)
            return str;
        else
            return str.substring(tmpStringLength - size, str.length());
    }

    /**
     * String을 좌측을 기준으로 size크기의 String을 반환한다.
     * @param str 입력String
     * @param size 획득코저하는 사이즈
     * @return 좌측을 기준으로 size 만큼의 String
     */
    public static String getLeft(String str, int size)
    {
        int tmpStringLength = str.length();

        if(size >= tmpStringLength)
            return str;
        else
            return str.substring(0, size);
    }


	/**
	 * String을 XML로 변환하기 위해 특수문자를 XML 형식으로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String str2XML(String strString)
	{
        String result = convert2APOS(convert2QUOT(convert2GT(convert2LT(convert2AMP(strString)))));
        return result;
    }

	/**
	 * XML형식으로 변환되어 있는 특수문자를 원본으로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String xml2Str(String strString)
	{
	    String result = reverse2APOS(reverse2QUOT(reverse2GT(reverse2LT(reverse2AMP(strString)))));
        return result;
	}


    /**
     * 특수문자를 XML에 맞도록 변환해주는 메소드
     * @param srcText 변환할 문자열
	 * @return 변화된 문자열
     */
    public static String str2Html(String srcText)
    {
        if(isBlank(srcText))
            return "";
        else
        {
            String strip = "";
            strip = "&";
            srcText = replace(srcText, strip, "&amp;");
            strip = "<";
            srcText = replace(srcText, strip, "&lt;");
            strip = ">";
            srcText = replace(srcText, strip, "&gt;");
            strip = "\\n";
            srcText = replace(srcText, strip, "<br>");
            strip = "\"";
            srcText = replace(srcText, strip, "&quot;");
            strip = "'";
            srcText = replace(srcText, strip, "&apos;");
            strip = " ";
            srcText = replace(srcText, strip, "&nbsp;");
            return srcText;
        }
    }


	/**
	 * '<' 를 &lt; 로 변환
	 * @param strString String
	 * @return String
	 */
	public static String convert2LT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "<", "&lt;");
	}

	/**
	 * '&lt'를 '<'로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String reverse2LT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&lt;", "<");
	}

	/**
	 * >를 &gt;로 변환
	 * @param strString
	 * @return String
	 */
	public static String convert2GT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, ">", "&gt;");
	}

	/**
	 * '&gt;'를 '>'로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String reverse2GT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&gt;", ">");
	}

	/**
	 * & 를 HTML &amp; 로 변환
	 * @param strString 변환할 문자열
	 * @return String 변화된 문자열
	 */
	public static String convert2AMP(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&", "&amp;");
	}

	/**
	 * '&amp;'를 '&'로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String reverse2AMP(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&amp;", "&");

	}
	/**
	 * '\r'을 <br> 로 변환해주는 메소드
	 * @param strString String
	 * @return String
	 */
	public static String convert2BR(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "\\n", "<br>");
	}

	/**
	 * ' 를 &apos; 로 변환
	 * @param strString
	 * @return String
	 */
	public static String convert2APOS(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "'", "&apos;");
	}

	/**
	 * '&apos;'를 '''로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String reverse2APOS(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&apos;", "'");
	}

	/**
	 * '"' 를 &quot; 로 변환
	 * @param strString
	 * @return String
	 */
	public static String convert2QUOT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "\"", "&quot;");
	}


	/**
	 * '&quot;'를 '\"'로 변환
	 * @param strString 변환할 문자열
	 * @return 변화된 문자열
	 */
	public static String reverse2QUOT(String strString)
	{
	    if(isBlank(strString))
	        return "";
	    else
	        return replace(strString, "&quot;", "\"");
	}




///////////////////////////////////////////////////////////

	/**
	 * 스트링을 특정 문자를 기준으로 나누어 Vector형태로 반환한다.
	 * @param strString : input string
	 * @param strDelimeter : delimeter character
	 * @return Vector : result string
	 */
	public static Vector getSplitVector(String strString, String strDelimeter)
	{
		Vector vResult = new Vector();
		int nCount = 0, nLastIndex = 0;
		try
		{
			nLastIndex = strString.indexOf(strDelimeter);

			if (nLastIndex == -1)
			{
				vResult.add(0, strString);
			}
			else
			{
				while ((strString.indexOf(strDelimeter) > -1))
				{
					nLastIndex = strString.indexOf(strDelimeter);
					vResult.add(nCount, strString.substring(0, nLastIndex));
					strString = strString.substring(nLastIndex + strDelimeter.length(), strString.length());
					nCount++;
				}
				vResult.add(nCount, strString);
			}
		}
		catch (Exception e)
		{
			return null;
		}
		return vResult;
	}

	/**
	 * String을 특정 문자를 기준으로 나누어 배열형태로 반환한다.
	 * @param strString : input string
	 * @param strDelimeter : delimeter character
	 * @return String[]
	 */
	public static String[] getSplitArray(String strString, String strDelimeter)
	{
//	    StringTokenizer strToken = new StringTokenizer(strString, strDelimeter );
//
//	    String arrStr[] = new String[ strToken.countTokens() ];
//	    int i=0;
//	    while(strToken.hasMoreTokens())
//	    {
//	        arrStr[i] = strToken.nextToken();
//	        i++;
//	    }
//	    return arrStr;

        return (String[])(getSplitVector(strString, strDelimeter).toArray(new String[0]));
	}

	/**
	 * 입력 String에 있는 특정문자를 삭제해준다.
	 * @param strString input String
	 * @param strChar special character
	 * @return String 특정문자를 제거한 문자
	 */
	public static String deleteChar(String strString, char strChar)
	{
    	if ( isBlank(strString) )
    		return "";

    	strString = strString.trim();
    	byte[] source = strString.getBytes();
    	byte[] result = new byte[source.length];
    	int j = 0;
    	for (int i = 0; i < source.length; i++)
    	{
    		if (source[i] != (byte)strChar )
    			result[j++] = source[i];
    	}
    	return new String(result).trim();
	}




	/**
	 * 바꾸고자 하는 스트링의 인덱스 모음을 구한다.
	 * @param str String
	 * @param word String
	 * @return Vector tempindexArray
	 */
	public static Vector getSelectedTextIndex(String str, String word)
	{
		int index = 0;
		int fromIndex = 0;
		Vector tempIndexArray = new Vector();
		do
		{
			index = str.indexOf(word, fromIndex);
			if (index != -1)
			{
				tempIndexArray.add(new Integer(index));
				fromIndex = index + word.length();
			}
		}
		while (index != -1);
		return tempIndexArray;
	}


    /**
     * 왼쪽(Left)에 문자열을 끼어 넣는다.
     * width는 문자열의 전체 길이를 나타내며 chPad는 끼어 넣을 char
     * @param str 적용할 문자열
     * @param width 전체 문자열 크기
     * @param chPad pad 적용할 char
     * @return leftpad된 String
     */
    public static String setLeftPad(String str, int width, char chPad)
    {
        StringBuffer paddedValue = new StringBuffer();

        for (int i = str.length(); i < width; i++)
            paddedValue.append(chPad);

        paddedValue.append(str);

        return (paddedValue.toString()).substring(0, width);
    }

    /**
     * 오른쪽(right)에 문자열을 끼어 넣는다.
     * width는 문자열의 전체 길이를 나타내며, chPad는 끼어 넣을 char
     * @param str 적용할 문자열
     * @param width 전체 길이
     * @param chPad 삽입할 char
     * @return String
     */
    public static String setRightPad(String str, int width, char chPad)
    {
        if(str.length() >= width)
            return str.substring(0, width);

        StringBuffer paddingValue = new StringBuffer();
        for (int i = str.length(); i < width; i++)
            paddingValue.append(chPad);

        return str+paddingValue.toString();
    }

    /**
     * 한글이 포함되어 있는지 여부를 확인하여, 한글 포함 갯수를 반환한다.
     * @param str 한글 포함여부를 확인하고자 하는 String
     * @return 포함 한글 갯수
     */
    public static int checkHangul(String str)
    {
        int cnt = 0;

        if( isBlank(str) )
            return 0;

        int index = 0;

        while(index < str.length())
        {
            if(str.charAt(index++) >= 256)
                cnt++;
        }

        return cnt;
    }

    public static String byteSubString(String src, int beginIndex, int endIndex)
    {
        if( UtilString.isBlank(src))
            return "";

        byte[] value = src.getBytes();

        if (beginIndex < 0)
    	    throw new StringIndexOutOfBoundsException(beginIndex);

    	if (endIndex > value.length)
    	    throw new StringIndexOutOfBoundsException(endIndex);

    	if (beginIndex > endIndex)
    	    throw new StringIndexOutOfBoundsException(endIndex - beginIndex);

        byte[] tmpByte = new byte[endIndex - beginIndex];
        System.arraycopy(value, beginIndex, tmpByte, 0, tmpByte.length);

        return new String(tmpByte);
    }

	public static String[] getSplit(String strString, String strDelimeter)
	{
	    StringTokenizer strToken = new StringTokenizer(strString, strDelimeter );

	    String arrStr[] = new String[ strToken.countTokens()];
	    int i=0;
	    while(strToken.hasMoreTokens())
	    {
	        arrStr[i] = strToken.nextToken();
	        i++;
	    }
	    return arrStr;
	}


	 /**
     * 특정문자를 인텍스 기준으로 하여  해당 문자 갯수만큼 검색하여 인덱스값을 리턴한다.
     * @param str index값을 확인하고자하는  String
     *        targetIndex 인덱스로 쓰기 위한 문자
     *        count 인덱스로 사용하는 문자를 검색할 수
     * @return strIndex 특정문자 인덱스에 해당하는 인덱스값
     */
	public static int getStrIndex(String str, char targetIndex, int count){
		int targetIndexCnt = 0; //타겟 문자의 검색 갯수
		int strIndex = 0; // String index값
		for(int i=0; i<= str.length()-1;i++)
		{
			if(str.charAt(i) == targetIndex)
			{
				targetIndexCnt++;
			}
			if(targetIndexCnt == count)
			{
				break;
			}
			strIndex++;
        }
		return strIndex;
	}
	
	public static int checkSpChar(String str)
    {
        int cnt = 0;

        if( isBlank(str) )
            return 0;

        int index = 0;

        while(index < str.length())
        {
            if((str.charAt(index) >= 32 && str.charAt(index) <= 64) || (str.charAt(index) >= 91 && str.charAt(index) <= 96))
                cnt++;
            index++;
        }

        return cnt;
    }
	
	/**
     * DataSet의 해당 key 값에 저장되어 있는 값이 String형을 경우
     * String으로 변환하여 돌려 준다.
     * 저장 값이 null이 거나, String이 아니면 빈 문자를 돌려 준다.
     * @param key key값
     * @return key값에 해당하는 String형 값
     */
    public static String getString(Object key) {  
        Object strObj = key;
        if(strObj == null) {
            return "";
        }
        
        if (strObj instanceof String) {
            return (String) strObj;
        }
        
        if (strObj == null || !(strObj instanceof String)) {
            return String.valueOf(strObj);
        }
        
        return "";
    }
    
    /**
     * DataSet의 해당 key 값에 저장되어 있는 값이 String 배열 일 경우
     * String 배열로로 변환하여 돌려 준다. 단, String일 경우 크기가 1인
     * String배열을 생성하여 돌려 둔다.
     * 저장 값이 null이 거나, String배열 또는 String 형이 아니면 null을 돌려 준다. 
     * @param key 
     * @return
     */
    public static String[] getStrings(String key) {
        String[] strs;
        Object strObj = key;
        if (strObj == null) {
            return null;
        } else {
            if (strObj instanceof String) {
                strs = new String[1];
                strs[0] = (String)strObj;
            } else if (strObj instanceof String[]) {
                strs = (String[])strObj;
            } else {
                return null;
            }
            
            return strs;
        }
    }
   
    
    public static double getDouble(Object key) {
        Object dblObj = key;
        if(dblObj instanceof Float) {
                return ((Float) dblObj).doubleValue() ;
        } else if(dblObj instanceof Double) {
                return ((Double) dblObj).doubleValue() ;
        } else if(dblObj instanceof String) {
                return Double.parseDouble((String) dblObj) ;
        } else if(dblObj instanceof Integer) {
                return ((Integer) dblObj).doubleValue() ;
        } else if(dblObj instanceof Long) {
                return ((Long) dblObj).doubleValue() ;
        } else return -1 ;  
    }
    
    public static long getLong(Object key) {
        Object lngObj = key;
        
        if(lngObj instanceof Integer) {
                return ((Integer) lngObj).longValue() ;
        } else if(lngObj instanceof Long) {
                return ((Long) lngObj).longValue() ;
        } else if(lngObj instanceof Float) {
                return ((Float) lngObj).longValue() ;
        } else if(lngObj instanceof Double) {
                return ((Double) lngObj).longValue() ;
        } else if(lngObj instanceof String) {
                return Long.parseLong((String)lngObj) ;
        } else {
                return -1 ;                     // 데이타 컨버전 에러
        }
    }
    
    public static int getInteger(Object key) {
        Object intObj = key;
        
        if(intObj instanceof String) {
                return Integer.parseInt((String)intObj) ;
        } else if(intObj instanceof Integer) {
                return ((Integer) intObj).intValue() ;
        } else if(intObj instanceof Long) {
                return ((Long) intObj).intValue() ;
        } else if(intObj instanceof Float) {
                return ((Float) intObj).intValue() ;
        } else if(intObj instanceof Double) {
                return ((Double) intObj).intValue() ;
        } else {
                throw new NumberFormatException();
        }
    }
    
    public static float getFloat(Object key) {
        Object fltObj = key;
        
        if(fltObj instanceof Float) {
                return ((Float) fltObj).floatValue() ;
        } else if(fltObj instanceof Double) {
                return ((Double) fltObj).floatValue() ;
        } else if(fltObj instanceof String) {
                return Float.parseFloat((String) fltObj) ;
        } else if(fltObj instanceof Integer) {
                return ((Integer) fltObj).floatValue() ;
        } else if(fltObj instanceof Long) {
                return ((Long) fltObj).floatValue() ;
        } else {
                return -1 ;                     // 데이타 컨버전 에러
        }
    }

    /*
    public String getCLOB(Object key) {
        Object fltObj = key;
        StringBuffer body1 = new StringBuffer();
        try
        {
            java.io.Reader input = ((oracle.sql.CLOB)fltObj).getCharacterStream();
            char[] buffer = new char[1024];
            int byteRead = 0;
            
            while((byteRead=input.read(buffer,0,1024))!=-1)
            {
             body1.append(buffer,0,byteRead);
             logger.debug(">>>>"+ body1.toString());
            }
            input.close();
        }catch(Exception ex){
            return "";
        }
        return body1.toString();
    }

    public String getBLOB(Object key) {
        Object fltObj = this.get(key);
        StringBuffer body1 = new StringBuffer();
        try
        {
            java.io.BufferedInputStream input = new java.io.BufferedInputStream(((oracle.sql.BLOB)fltObj).getBinaryStream());
            if ( input != null)
            {
                InputStreamReader isr =  new InputStreamReader(input, "utf-8");
                BufferedReader reader = new BufferedReader(isr);
                body1.append(reader.readLine());
            }    
            
            input.close();
        }catch(Exception ex){
            return "";
        }
        return body1.toString();
    }

    public java.sql.Timestamp getTimestamp(Object key) {
        Object fltObj = key;
        if(fltObj != null) return (java.sql.Timestamp) fltObj ;
        else return null ;
    }
    
    public java.sql.Date getDate(Object key) {
        Object fltObj = key;
        if(fltObj != null) return (java.sql.Date) fltObj ;
        else return null ;
    }
    */

}
