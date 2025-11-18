package kr.wise.dq.gap.service;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.meta.model.service.WamPdmCol;

public class StndGapVO extends WamPdmCol
{
	    private String sditmId;

	    private String sditmLnm;

	    private String sditmPnm;
	    
	    private String notsditmLnm;
	    
	    private String notsditmPnm;

	    private String lnmCriDs;

	    private String pnmCriDs;

		private String dmnId;

	    private String dmngId;
	    
	    private String dmngLnm;

	    private String infotpId;
	    
	    private String infotpLnm;

	    private String infotpChgYn;

	    private String encYn;

	    private String stndInfoYn;
	    
	    private String bzwkFld;
	    private String ownrOrg;
	    private String admnStndCd;
	    
	    private String saveFrm;
	    private String exprsnFrm;
	    private String unit;
	    
	    private String alwVal;
	    
	    private String gapStatus;
	    
	    private String dmnDataType;
	    private String dmnDataLen ;
	    private String dmnDataScal;

	    private String anaCd      ;
	    private String anaDtm     ;
	    private String totColCnt  ;
	    private String colAplyCnt ;
	    private String colAplyRt  ;
	    private String colErrCnt  ;
	    private String colErrRt   ;
	    
	    
	    
	    
	    
		public String getAnaCd() {
			return anaCd;
		}

		public void setAnaCd(String anaCd) {
			this.anaCd = anaCd;
		}

		public String getAnaDtm() {
			return anaDtm;
		}

		public void setAnaDtm(String anaDtm) {
			this.anaDtm = anaDtm;
		}

		public String getTotColCnt() {
			return totColCnt;
		}

		public void setTotColCnt(String totColCnt) {
			this.totColCnt = totColCnt;
		}

		public String getColAplyCnt() {
			return colAplyCnt;
		}

		public void setColAplyCnt(String colAplyCnt) {
			this.colAplyCnt = colAplyCnt;
		}

		public String getColAplyRt() {
			return colAplyRt;
		}

		public void setColAplyRt(String colAplyRt) {
			this.colAplyRt = colAplyRt;
		}

		public String getColErrCnt() {
			return colErrCnt;
		}

		public void setColErrCnt(String colErrCnt) {
			this.colErrCnt = colErrCnt;
		}

		public String getColErrRt() {
			return colErrRt;
		}

		public void setColErrRt(String colErrRt) {
			this.colErrRt = colErrRt;
		}

		public String getDmnDataType() {
			return dmnDataType;
		}

		public void setDmnDataType(String dmnDataType) {
			this.dmnDataType = dmnDataType;
		}

		public String getDmnDataLen() {
			return dmnDataLen;
		}

		public void setDmnDataLen(String dmnDataLen) {
			this.dmnDataLen = dmnDataLen;
		}

		public String getDmnDataScal() {
			return dmnDataScal;
		}

		public void setDmnDataScal(String dmnDataScal) {
			this.dmnDataScal = dmnDataScal;
		}

		public String getGapStatus() {
			return gapStatus;
		}

		public void setGapStatus(String gapStatus) {
			this.gapStatus = gapStatus;
		}

		public String getSditmId() {
			return sditmId;
		}

		public void setSditmId(String sditmId) {
			this.sditmId = sditmId;
		}

		public String getSditmLnm() {
			return sditmLnm;
		}

		public void setSditmLnm(String sditmLnm) {
			this.sditmLnm = sditmLnm;
		}

		public String getSditmPnm() {
			return sditmPnm;
		}

		public void setSditmPnm(String sditmPnm) {
			this.sditmPnm = sditmPnm;
		}

		public String getNotsditmLnm() {
			return notsditmLnm;
		}

		public void setNotsditmLnm(String notsditmLnm) {
			this.notsditmLnm = notsditmLnm;
		}

		public String getNotsditmPnm() {
			return notsditmPnm;
		}

		public void setNotsditmPnm(String notsditmPnm) {
			this.notsditmPnm = notsditmPnm;
		}

		public String getLnmCriDs() {
			return lnmCriDs;
		}

		public void setLnmCriDs(String lnmCriDs) {
			this.lnmCriDs = lnmCriDs;
		}

		public String getPnmCriDs() {
			return pnmCriDs;
		}

		public void setPnmCriDs(String pnmCriDs) {
			this.pnmCriDs = pnmCriDs;
		}

		public String getDmnId() {
			return dmnId;
		}

		public void setDmnId(String dmnId) {
			this.dmnId = dmnId;
		}

		public String getDmngId() {
			return dmngId;
		}

		public void setDmngId(String dmngId) {
			this.dmngId = dmngId;
		}

		public String getDmngLnm() {
			return dmngLnm;
		}

		public void setDmngLnm(String dmngLnm) {
			this.dmngLnm = dmngLnm;
		}

		public String getInfotpId() {
			return infotpId;
		}

		public void setInfotpId(String infotpId) {
			this.infotpId = infotpId;
		}

		public String getInfotpLnm() {
			return infotpLnm;
		}

		public void setInfotpLnm(String infotpLnm) {
			this.infotpLnm = infotpLnm;
		}

		public String getInfotpChgYn() {
			return infotpChgYn;
		}

		public void setInfotpChgYn(String infotpChgYn) {
			this.infotpChgYn = infotpChgYn;
		}

		public String getEncYn() {
			return encYn;
		}

		public void setEncYn(String encYn) {
			this.encYn = encYn;
		}

		public String getStndInfoYn() {
			return stndInfoYn;
		}

		public void setStndInfoYn(String stndInfoYn) {
			this.stndInfoYn = stndInfoYn;
		}

		public String getBzwkFld() {
			return bzwkFld;
		}

		public void setBzwkFld(String bzwkFld) {
			this.bzwkFld = bzwkFld;
		}

		public String getOwnrOrg() {
			return ownrOrg;
		}

		public void setOwnrOrg(String ownrOrg) {
			this.ownrOrg = ownrOrg;
		}

		public String getAdmnStndCd() {
			return admnStndCd;
		}

		public void setAdmnStndCd(String admnStndCd) {
			this.admnStndCd = admnStndCd;
		}

		public String getSaveFrm() {
			return saveFrm;
		}

		public void setSaveFrm(String saveFrm) {
			this.saveFrm = saveFrm;
		}

		public String getExprsnFrm() {
			return exprsnFrm;
		}

		public void setExprsnFrm(String exprsnFrm) {
			this.exprsnFrm = exprsnFrm;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getAlwVal() {
			return alwVal;
		}

		public void setAlwVal(String alwVal) {
			this.alwVal = alwVal;
		}

    
}
