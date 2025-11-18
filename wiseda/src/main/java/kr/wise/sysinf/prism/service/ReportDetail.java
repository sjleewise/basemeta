package kr.wise.sysinf.prism.service;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "contents")
public class ReportDetail {
	
	private PrismReportVo report;

	public PrismReportVo getReport() {
		return report;
	}

	public void setReport(PrismReportVo report) {
		this.report = report;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportDetail [report=").append(report).append("]");
		return builder.toString();
	}
	
	
	

}
