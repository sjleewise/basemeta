package kr.wise.sysinf.prism.service;

import javax.xml.bind.annotation.XmlRootElement;

import kr.wise.rest.client.ResearchVo;

@XmlRootElement(name = "contents")
public class ResearchDetail {
	
	private ResearchVo research;
	
	private ContractVo contract;
	
	private ReportVo report;
	
	private ResearchUse use;
	
	private Kogl kogl;

	public ResearchVo getResearch() {
		return research;
	}

	public void setResearch(ResearchVo research) {
		this.research = research;
	}

	public ContractVo getContract() {
		return contract;
	}

	public void setContract(ContractVo contract) {
		this.contract = contract;
	}

	public ReportVo getReport() {
		return report;
	}

	public void setReport(ReportVo report) {
		this.report = report;
	}

	public ResearchUse getUse() {
		return use;
	}

	public void setUse(ResearchUse use) {
		this.use = use;
	}

	public Kogl getKogl() {
		return kogl;
	}

	public void setKogl(Kogl kogl) {
		this.kogl = kogl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResearchDetail [research=").append(research)
				.append(", contract=").append(contract).append(", report=")
				.append(report).append(", use=").append(use).append(", kogl=")
				.append(kogl).append("]");
		return builder.toString();
	}
	
	

}
