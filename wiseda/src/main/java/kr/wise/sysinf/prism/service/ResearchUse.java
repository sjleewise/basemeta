package kr.wise.sysinf.prism.service;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.wise.rest.client.AdapterCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResearchUse {
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String[] fileName  ;
	private String[] fileSize  ;
//	private String fileName  ;
//	private String fileSize  ;
	
	
	public String[] getFileName() {
		return fileName;
	}
	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}
	public String[] getFileSize() {
		return fileSize;
	}
	public void setFileSize(String[] fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ResearchUse [fileName=")
				.append(fileName != null ? Arrays.asList(fileName).subList(0,
						Math.min(fileName.length, maxLen)) : null)
				.append(", fileSize=")
				.append(fileSize != null ? Arrays.asList(fileSize).subList(0,
						Math.min(fileSize.length, maxLen)) : null).append("]");
		return builder.toString();
	}


}
