package kr.wise.commons.rqstmst.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqRqstChgDtls extends CommonVo {
    private String itemNm;
    private String beforeChg;
    private String afterChg;
    
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public String getBeforeChg() {
		return beforeChg;
	}
	public void setBeforeChg(String beforeChg) {
		this.beforeChg = beforeChg;
	}
	public String getAfterChg() {
		return afterChg;
	}
	public void setAfterChg(String afterChg) {
		this.afterChg = afterChg;
	}
    
}