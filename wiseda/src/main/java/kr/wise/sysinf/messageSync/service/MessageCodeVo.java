/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : SimpleCodeVo.java
 * 2. Package : 
 * 3. Comment :
 * 4. 작성자  : 이상익 
 * 5. 작성일  : 2015-10-30
 * 6. 변경이력 :
 *    
 */
package kr.wise.sysinf.messageSync.service;


public class MessageCodeVo {


     private String msgCd     ; 
     private String sysDvcd     ; 
     private String msgPtrnDvcd     ; 
     private String msgBzwrDvcd   ; 
     private String msgCntn   ; 
     private String useYn   ; 
     private String gapStatus;
   
   
	public String getGapStatus() {
		return gapStatus;
	}



	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}



	public String getMsgCd() {
		return msgCd;
	}



	public void setMsgCd(String msgCd) {
		this.msgCd = msgCd;
	}



	public String getSysDvcd() {
		return sysDvcd;
	}



	public void setSysDvcd(String sysDvcd) {
		this.sysDvcd = sysDvcd;
	}



	public String getMsgPtrnDvcd() {
		return msgPtrnDvcd;
	}



	public void setMsgPtrnDvcd(String msgPtrnDvcd) {
		this.msgPtrnDvcd = msgPtrnDvcd;
	}



	public String getMsgBzwrDvcd() {
		return msgBzwrDvcd;
	}



	public void setMsgBzwrDvcd(String msgBzwrDvcd) {
		this.msgBzwrDvcd = msgBzwrDvcd;
	}



	public String getMsgCntn() {
		return msgCntn;
	}



	public void setMsgCntn(String msgCntn) {
		this.msgCntn = msgCntn;
	}



	public String getUseYn() {
		return useYn;
	}



	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageCodeVo [msgCd=");
		builder.append(msgCd);
		builder.append(", msgPtrnDvcd=");
		builder.append(msgPtrnDvcd);
		builder.append(", msgBzwrDvcd=");
		builder.append(msgBzwrDvcd);
		builder.append("]");
		return builder.toString();  
	}
}
