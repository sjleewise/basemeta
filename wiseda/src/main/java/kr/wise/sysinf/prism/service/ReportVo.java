package kr.wise.sysinf.prism.service;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.wise.rest.client.AdapterCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReportVo {
	
/*	  <report>
	    <title>&lt;![CDATA[PI?´???¨ ?½?????¸? ????²? ?°? ????????¸??´?????? ?????½]]&gt;</title>
	    <fileName>&lt;![CDATA[??°????³´?³????.pdf]]&gt;</fileName>
	    <fileSize>766778437</fileSize>
	    <fileName>&lt;![CDATA[??°????³´?³????.pdf]]&gt;</fileName>
	    <fileSize>766778437</fileSize>
	    <issuedYear>2014</issuedYear>
	    <submittedDate>20140321</submittedDate>
	  </report>*/
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String title           ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String fileName []       ;
	
	private String fileSize []       ;

	//	private String fileName        ;

	//	private String fileSize        ;
	
	private String issuedYear      ;
	
	private String submittedDate   ;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public String getIssuedYear() {
		return issuedYear;
	}
	public void setIssuedYear(String issuedYear) {
		this.issuedYear = issuedYear;
	}
	public String getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ReportVo [title=")
				.append(title)
				.append(", fileName=")
				.append(fileName != null ? Arrays.asList(fileName).subList(0,
						Math.min(fileName.length, maxLen)) : null)
				.append(", fileSize=")
				.append(fileSize != null ? Arrays.asList(fileSize).subList(0,
						Math.min(fileSize.length, maxLen)) : null)
				.append(", issuedYear=").append(issuedYear)
				.append(", submittedDate=").append(submittedDate).append("]");
		return builder.toString();
	}
	
	
	

}
