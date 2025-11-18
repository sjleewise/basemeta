package kr.wise.advisor.prepare.udefoutlier.service;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class WadUod extends CommonVo {

	private String udfOtlDtcId;
	private String udfOtlDtcNm;
	private String mdlJsonInf;
	private String anaDaseNm;
	private String compDcd;
	
	private Date anaStrDtm;
	private Date anaEndDtm;
	
	public String getUdfOtlDtcId() {
		return udfOtlDtcId;
	}
	public void setUdfOtlDtcId(String udfOtlDtcId) {
		this.udfOtlDtcId = udfOtlDtcId;
	}
	public String getUdfOtlDtcNm() {
		return udfOtlDtcNm;
	}
	public void setUdfOtlDtcNm(String udfOtlDtcNm) {
		this.udfOtlDtcNm = udfOtlDtcNm;
	}
	
	public String getMdlJsonInf() {
		return mdlJsonInf;
	}
	public void setMdlJsonInf(String mdlJsonInf) {
		this.mdlJsonInf = mdlJsonInf;
	}
	
	public String getAnaDaseNm() {
		return anaDaseNm;
	}
	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
	
	public String getCompDcd() {
		return compDcd;
	}
	public void setCompDcd(String compDcd) {
		this.compDcd = compDcd;
	}
	public Date getAnaStrDtm() {
		return anaStrDtm;
	}
	
	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setAnaStrDtm(Date anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}
	
	public Date getAnaEndDtm() {
		return anaEndDtm;
	}
	
	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setAnaEndDtm(Date anaEndDtm) {
		this.anaEndDtm = anaEndDtm;
	} 
	
	public String removeComma(String param) {
		if (param.length() > 0 && param.charAt(param.length()-1)==',') {
			return param.substring(0, param.length()-1);
		}
		
		return param;
	}
	
}
