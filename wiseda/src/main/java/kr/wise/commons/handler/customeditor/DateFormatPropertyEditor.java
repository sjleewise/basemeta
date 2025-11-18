package kr.wise.commons.handler.customeditor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class DateFormatPropertyEditor extends PropertyEditorSupport {

	
	public void setAsText(String text1) throws IllegalArgumentException {
		//System.out.println("1:" + text);
		String text = text1;
		if(StringUtils.hasText(text)) {
			text = text.replaceAll("[-]", "");
		}
		//System.out.println("2:"+ text);
		super.setValue(text);
	}
	
	
}
