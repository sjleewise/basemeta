package kr.wise.rest.client;

import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResearchVo {

	private String seq              ;
	private String researchID       ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String researchName     ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String researchOutline  ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String organName        ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String URL              ;
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String bizName1         ;
    
	private String brmBizId         ;
    
	@XmlJavaTypeAdapter(AdapterCDATA.class)
    private String bizName2         ;
    
	private String parentId         ;
    
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String bizName3         ;
    
    private String superId          ;
    
    private String issuedYear       ;
    
    private String researchStartDate;
    
    private String researchEndDate  ;
    
    private String supplyOrg        ;
    
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String researchOrganName;
    
    private String submittedDate    ;
    
    private String updateOption     ;
    
    /** ResearchDetail 항목 추가... */
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String chargePersonDepartment ;

    private String chargePersonPhoneNo	  ; 
    
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String brmBizName         ;
	
    
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getResearchID() {
		return researchID;
	}
	public void setResearchID(String researchID) {
		this.researchID = researchID;
	}
	public String getResearchName() {
		return researchName;
	}
	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}
	public String getResearchOutline() {
		return researchOutline;
	}
	public void setResearchOutline(String researchOutline) {
		this.researchOutline = researchOutline;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getBizName1() {
		return bizName1;
	}
	public void setBizName1(String bizName1) {
		this.bizName1 = bizName1;
	}
	public String getBrmBizId() {
		return brmBizId;
	}
	public void setBrmBizId(String brmBizId) {
		this.brmBizId = brmBizId;
	}
	public String getBizName2() {
		return bizName2;
	}
	public void setBizName2(String bizName2) {
		this.bizName2 = bizName2;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBizName3() {
		return bizName3;
	}
	public void setBizName3(String bizName3) {
		this.bizName3 = bizName3;
	}
	public String getSuperId() {
		return superId;
	}
	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getIssuedYear() {
		return issuedYear;
	}
	public void setIssuedYear(String issuedYear) {
		this.issuedYear = issuedYear;
	}
	public String getResearchStartDate() {
		return researchStartDate;
	}
	public void setResearchStartDate(String researchStartDate) {
		this.researchStartDate = researchStartDate;
	}
	public String getResearchEndDate() {
		return researchEndDate;
	}
	public void setResearchEndDate(String researchEndDate) {
		this.researchEndDate = researchEndDate;
	}
	public String getSupplyOrg() {
		return supplyOrg;
	}
	public void setSupplyOrg(String supplyOrg) {
		this.supplyOrg = supplyOrg;
	}
	public String getResearchOrganName() {
		return researchOrganName;
	}
	public void setResearchOrganName(String researchOrganName) {
		this.researchOrganName = researchOrganName;
	}
	public String getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getUpdateOption() {
		return updateOption;
	}
	public void setUpdateOption(String updateOption) {
		this.updateOption = updateOption;
	}
	public String getChargePersonDepartment() {
		return chargePersonDepartment;
	}
	public void setChargePersonDepartment(String chargePersonDepartment) {
		this.chargePersonDepartment = chargePersonDepartment;
	}
	public String getChargePersonPhoneNo() {
		return chargePersonPhoneNo;
	}
	public void setChargePersonPhoneNo(String chargePersonPhoneNo) {
		this.chargePersonPhoneNo = chargePersonPhoneNo;
	}
	public String getBrmBizName() {
		return brmBizName;
	}
	public void setBrmBizName(String brmBizName) {
		this.brmBizName = brmBizName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResearchVo [seq=").append(seq).append(", researchID=")
				.append(researchID).append(", researchName=")
				.append(researchName).append(", researchOutline=")
				.append(researchOutline).append(", organName=")
				.append(organName).append(", URL=").append(URL)
				.append(", bizName1=").append(bizName1).append(", brmBizId=")
				.append(brmBizId).append(", bizName2=").append(bizName2)
				.append(", parentId=").append(parentId).append(", bizName3=")
				.append(bizName3).append(", superId=").append(superId)
				.append(", issuedYear=").append(issuedYear)
				.append(", researchStartDate=").append(researchStartDate)
				.append(", researchEndDate=").append(researchEndDate)
				.append(", supplyOrg=").append(supplyOrg)
				.append(", researchOrganName=").append(researchOrganName)
				.append(", submittedDate=").append(submittedDate)
				.append(", updateOption=").append(updateOption)
				.append(", chargePersonDepartment=")
				.append(chargePersonDepartment)
				.append(", chargePersonPhoneNo=").append(chargePersonPhoneNo)
				.append(", brmBizName=").append(brmBizName).append("]");
		return builder.toString();
	}
	
	
    
    
}
