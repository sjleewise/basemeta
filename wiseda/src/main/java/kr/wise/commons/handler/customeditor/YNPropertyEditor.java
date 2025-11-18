package kr.wise.commons.handler.customeditor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class YNPropertyEditor extends PropertyEditorSupport {

	
	public void setAsText(String text) throws IllegalArgumentException {
		//System.out.println("1:" + text);
		
		String resstr = "";
		
		if(StringUtils.hasText(text)) {
			if ("1".equals(text)) resstr = "Y";
			else if ("0".equals(text)) resstr = "N";
		}
		//System.out.println("2:"+ text);
		super.setValue(resstr);
	}
	
	
}
