package kr.wise.advisor.prepare.udefoutlier.script;

import kr.wise.commons.cmm.CommonVo;
import java.util.Date;

public class WadUodLnk extends CommonVo {

	private String udfOtlDtcId;	
	private Integer lnkSno;
	private String lnkFrom;
	private String lnkTo;
	
	
	public String getUdfOtlDtcId() {
		return udfOtlDtcId;
	}
	public void setUdfOtlDtcId(String udfOtlDtcId) {
		this.udfOtlDtcId = udfOtlDtcId;
	}
	
	public Integer getLnkSno() {
		return lnkSno; 
	}
	public void setLnkSno(Integer lnkSno) {
		this.lnkSno = lnkSno;
	}
	public String getLnkFrom() {
		return lnkFrom;
	}
	public void setLnkFrom(String lnkFrom) {
		this.lnkFrom = lnkFrom;
	}
	public String getLnkTo() {
		return lnkTo;
	}
	public void setLnkTo(String lnkTo) {
		this.lnkTo = lnkTo;
	}
	
	
	
}
