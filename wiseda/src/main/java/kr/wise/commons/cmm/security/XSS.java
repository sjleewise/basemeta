package kr.wise.commons.cmm.security;

public class XSS {
	
    /**
     * XSS ���� ó��.
     * 
     * @param data
     * @return
     */
    public static String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        ret = ret.replaceAll("<(I|i)(M|m)(G|g)", "&lt;img");
        ret = ret.replaceAll("</(I|i)(M|m)(G|g)", "&lt;img");

        ret = ret.replaceAll("<(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
        ret = ret.replaceAll("</(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
        
        ret = ret.replaceAll("(A|a)(L|l)(E|e)(R|r)(T|t)", "");

        return ret;
    }

}
