package kr.wise.meta.intf.service;

public class GroupWareSendVO {
	private String sysWhlTgrLen;
	private String sysHerLen;
	private String sysProMdaLen;
	private String sysTgrVrsInf;
	
	private String sendId;
	private String sendName;
	private String recvIds;
	
	private String subject;
	private String contents;
	private String url;
	private String msgGubun;
	private String destGubun;
	private String ccRecvIds;
	private String bccRecvIds;
	private String rsrvFlag;
	private String rsrvDate;
	private String attFlag;
	private String att;
	private String bizDcd;
	private String rqstNo;
	
	private String msgKey;

	public String getRqstNo() {
		return rqstNo;
	}
	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}
	public String getBizDcd() {
		return bizDcd;
	}
	public void setBizDcd(String bizDcd) {
		this.bizDcd = bizDcd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRsrvFlag() {
		return rsrvFlag;
	}
	public void setRsrvFlag(String rsrvFlag) {
		this.rsrvFlag = rsrvFlag;
	}
	public String getRsrvDate() {
		return rsrvDate;
	}
	public void setRsrvDate(String rsrvDate) {
		this.rsrvDate = rsrvDate;
	}
	public String getAttFlag() {
		return attFlag;
	}
	public void setAttFlag(String attFlag) {
		this.attFlag = attFlag;
	}
	public String getAtt() {
		return att;
	}
	public void setAtt(String att) {
		this.att = att;
	}
	public String getSysWhlTgrLen() {
		return sysWhlTgrLen;
	}
	public void setSysWhlTgrLen(String sysWhlTgrLen) {
		this.sysWhlTgrLen = sysWhlTgrLen;
	}
	public String getSysHerLen() {
		return sysHerLen;
	}
	public void setSysHerLen(String sysHerLen) {
		this.sysHerLen = sysHerLen;
	}
	public String getSysProMdaLen() {
		return sysProMdaLen;
	}
	public void setSysProMdaLen(String sysProMdaLen) {
		this.sysProMdaLen = sysProMdaLen;
	}
	public String getSysTgrVrsInf() {
		return sysTgrVrsInf;
	}
	public void setSysTgrVrsInf(String sysTgrVrsInf) {
		this.sysTgrVrsInf = sysTgrVrsInf;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMsgGubun() {
		return msgGubun;
	}
	public void setMsgGubun(String msgGubun) {
		this.msgGubun = msgGubun;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getDestGubun() {
		return destGubun;
	}
	public void setDestGubun(String destGubun) {
		this.destGubun = destGubun;
	}
	public String getRecvIds() {
		return recvIds;
	}
	public void setRecvIds(String recvIds) {
		this.recvIds = recvIds;
	}
	public String getCcRecvIds() {
		return ccRecvIds;
	}
	public void setCcRecvIds(String ccRecvIds) {
		this.ccRecvIds = ccRecvIds;
	}
	public String getBccRecvIds() {
		return bccRecvIds;
	}
	public void setBccRecvIds(String bccRecvIds) {
		this.bccRecvIds = bccRecvIds;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
