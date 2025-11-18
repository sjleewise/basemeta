package kr.wise.commons.util;

import java.io.UnsupportedEncodingException;

/**
 * @version 1.0
 * 클래스 설명 ------------------------------------------------------------------------
 * Character Encoding 관련 기능성 Method를 제공하는 Util Class<br>
 * ----------------------------------------------------------------------------------
 */
public class UtilEncoder
{
    /** EUC_KR Character Set <code>EUC_KR</code> */
    public static String EUC_KR    = "EUC_KR";
    /** KSC5601 Character Set <code>KSC5601</code> */
    public static String KSC5601   = "KSC5601";
    /** ISO8859 Character Set <code>ISO8859_1</code> */
    public static String ISO8859_1 = "8859_1";

    /**
     * 문자열을 Encode시킨다.
     * @param str Encoding String
     * @param fromCharSet 현재 Character Set
     * @param toCharSet 변경할 Character Set
     * @return Edncoding되어진 Sting
     */
    public static String encode(String str, String fromCharSet, String toCharSet)
    {
        if (UtilString.isBlank(str)) 
            return null;

        try
        {
            return new String(str.getBytes(fromCharSet), toCharSet);
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * ISO_8859_1로 Encode 한다.
     * @param ko Encoding할 String
     * @return ISO_8859_1로 Encoding된 String
     */
    public static String to8859_1(String ko)
    {
        if (UtilString.isBlank(ko)) 
            return null;
        try
        {
            return new String(ko.getBytes(EUC_KR), ISO8859_1);
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * EUC_KR로 Encode 한다.
     * @param en Encoding할 String
     * @return EUC_KR로 Encoding된 String
     */
    public static String toEUCKR(String en)
    {
        if (UtilString.isBlank(en)) 
            return null;

        try
        {
            return new String(en.getBytes(ISO8859_1), EUC_KR);
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * KSC5601로 Encode 한다.
     * @param en Encoding할 String
     * @return KSC5601로 Encoding된 String
     */
    public static String toKSC5601(String en)
    {
        if (UtilString.isBlank(en)) 
            return null;

        try
        {
            return new String(en.getBytes(ISO8859_1), KSC5601);

        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }
}