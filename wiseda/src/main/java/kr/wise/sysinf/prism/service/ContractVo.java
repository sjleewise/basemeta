package kr.wise.sysinf.prism.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.wise.rest.client.AdapterCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContractVo {
	
/*    <researchOrganName>&lt;![CDATA[]]&gt;</researchOrganName>
    <researcherName>&lt;![CDATA[]]&gt;</researcherName>
    <contractDate>20140225</contractDate>
    <contractType>&lt;![CDATA[?????? ?³???½]]&gt;</contractType>
    <contractCost>5000000</contractCost>*/
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String researchOrganName;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String researcherName	;
	private String contractDate		;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String contractType		;
	
	private String contractCost		;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractVo [researchOrganName=")
				.append(researchOrganName).append(", researcherName=")
				.append(researcherName).append(", contractDate=")
				.append(contractDate).append(", contractType=")
				.append(contractType).append(", contractCost=")
				.append(contractCost).append("]");
		return builder.toString();
	}

	public String getResearchOrganName() {
		return researchOrganName;
	}

	public void setResearchOrganName(String researchOrganName) {
		this.researchOrganName = researchOrganName;
	}

	public String getResearcherName() {
		return researcherName;
	}

	public void setResearcherName(String researcherName) {
		this.researcherName = researcherName;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractCost() {
		return contractCost;
	}

	public void setContractCost(String contractCost) {
		this.contractCost = contractCost;
	}

}
