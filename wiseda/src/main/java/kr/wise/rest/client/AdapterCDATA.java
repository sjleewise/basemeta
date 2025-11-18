package kr.wise.rest.client;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.util.StringUtils;

public class AdapterCDATA extends XmlAdapter<String, String> {

	@Override
	public String marshal(String arg0) throws Exception {
		return "<![CDATA[" + arg0 + "]]>";
	}

	@Override
	public String unmarshal(String arg0) throws Exception {
//		return arg0.replace( "<![CDATA[", "" ).replace("]]>", "");
		if(StringUtils.hasText(arg0)){
			return stripCDATA(arg0);
		} else {
			return arg0;
		}
		
	}
	
	//처음과 마지막을 검사해서 CDATA 제거한다....
	public static String stripCDATA(String s) {
	    s = s.trim();
	    if (s.startsWith("<![CDATA[")) {
	      s = s.substring(9);
	      int i = s.indexOf("]]>");
	      if (i == -1) {
	        throw new IllegalStateException(
	            "argument starts with <![CDATA[ but cannot find pairing ]]&gt;");
	      }
	      s = s.substring(0, i).trim();
	    }
	    return s;
	 }
	
	

}
