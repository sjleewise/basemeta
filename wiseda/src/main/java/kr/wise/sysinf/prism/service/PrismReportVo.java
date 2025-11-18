package kr.wise.sysinf.prism.service;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.wise.rest.client.AdapterCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrismReportVo {
	
	private String researchID          ;
	
	private String seqNo               ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportTitle         ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String alternative         ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String subTitle            ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String URL				   ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportOpenYn        ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportOpenDetailYn  ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String[] fileName            ;
	
	private String[] fileSize            ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportOpenLaw       ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportOpenScopeYn   ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String reportOpenScope     ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String tableContents       ;

	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String tableContentsMore   ;

	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String summary             ;

	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String summaryMore         ;

	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String keyword             ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String contributor         ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String createdDate         ;
	
	private String issuedYear          ;
	
	private String submittedDate       ;
	
	
	

	
	public String getResearchID() {
		return researchID;
	}
	public void setResearchID(String researchID) {
		this.researchID = researchID;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getAlternative() {
		return alternative;
	}
	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getReportOpenYn() {
		return reportOpenYn;
	}
	public void setReportOpenYn(String reportOpenYn) {
		this.reportOpenYn = reportOpenYn;
	}
	public String getReportOpenDetailYn() {
		return reportOpenDetailYn;
	}
	public void setReportOpenDetailYn(String reportOpenDetailYn) {
		this.reportOpenDetailYn = reportOpenDetailYn;
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
	public String getReportOpenLaw() {
		return reportOpenLaw;
	}
	public void setReportOpenLaw(String reportOpenLaw) {
		this.reportOpenLaw = reportOpenLaw;
	}
	public String getReportOpenScopeYn() {
		return reportOpenScopeYn;
	}
	public void setReportOpenScopeYn(String reportOpenScopeYn) {
		this.reportOpenScopeYn = reportOpenScopeYn;
	}
	public String getReportOpenScope() {
		return reportOpenScope;
	}
	public void setReportOpenScope(String reportOpenScope) {
		this.reportOpenScope = reportOpenScope;
	}
	public String getTableContents() {
		return tableContents;
	}
	public void setTableContents(String tableContents) {
		this.tableContents = tableContents;
	}
	public String getTableContentsMore() {
		return tableContentsMore;
	}
	public void setTableContentsMore(String tableContentsMore) {
		this.tableContentsMore = tableContentsMore;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSummaryMore() {
		return summaryMore;
	}
	public void setSummaryMore(String summaryMore) {
		this.summaryMore = summaryMore;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrismReportVo [researchID=").append(researchID)
				.append(", seqNo=").append(seqNo).append(", reportTitle=")
				.append(reportTitle).append(", alternative=")
				.append(alternative).append(", subTitle=").append(subTitle)
				.append(", URL=").append(URL).append(", reportOpenYn=")
				.append(reportOpenYn).append(", reportOpenDetailYn=")
				.append(reportOpenDetailYn).append(", fileName=")
				.append(Arrays.toString(fileName)).append(", fileSize=")
				.append(Arrays.toString(fileSize)).append(", reportOpenLaw=")
				.append(reportOpenLaw).append(", reportOpenScopeYn=")
				.append(reportOpenScopeYn).append(", reportOpenScope=")
				.append(reportOpenScope).append(", tableContents=")
				.append(tableContents).append(", tableContentsMore=")
				.append(tableContentsMore).append(", summary=").append(summary)
				.append(", summaryMore=").append(summaryMore)
				.append(", keyword=").append(keyword).append(", contributor=")
				.append(contributor).append(", createdDate=")
				.append(createdDate).append(", issuedYear=").append(issuedYear)
				.append(", submittedDate=").append(submittedDate).append("]");
		return builder.toString();
	}

	
	

}
