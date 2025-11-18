package kr.wise.sysinf.prism.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import kr.wise.rest.client.ResearchVo;

@XmlRootElement(name = "contents")
public class PrismReportList {
	
	String totalCnt;
	List<PrismReportVo> report = new ArrayList<PrismReportVo>();
	
	
	public String getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
	public List<PrismReportVo> getReport() {
		return report;
	}
	public void setReport(List<PrismReportVo> report) {
		this.report = report;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrismReportList [totalCnt=").append(totalCnt)
				.append("]");
		return builder.toString();
	}
	
	

}
