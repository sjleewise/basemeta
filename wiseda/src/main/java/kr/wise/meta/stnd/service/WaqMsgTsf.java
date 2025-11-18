package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;


public class WaqMsgTsf extends CommonVo{	

	private String msgId;

    private String msgCd;

    private String msgConts;

    private String useYn;
    
    private String pnmCriDs;
    
    private String encYn;

    private String typDivCd;
    
    private String bizDivCd;
    
    private String sysDivCd;

    public String getSysDivCd() {
		return sysDivCd;
	}

	public void setSysDivCd(String sysDivCd) {
		this.sysDivCd = sysDivCd;
	}

	public String getTypDivCd() {
		return typDivCd;
	}

	public void setTypDivCd(String typDivCd) {
		this.typDivCd = typDivCd;
	}

	public String getBizDivCd() {
		return bizDivCd;
	}

	public void setBizDivCd(String bizDivCd) {
		this.bizDivCd = bizDivCd;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgCd() {
		return msgCd;
	}

	public void setMsgCd(String msgCd) {
		this.msgCd = msgCd;
	}

	public String getMsgConts() {
		return msgConts;
	}

	public void setMsgConts(String msgConts) {
		this.msgConts = msgConts;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}



}