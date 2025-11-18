package kr.wise.meta.symn.service;

import kr.wise.commons.cmm.CommonVo;
import java.util.Date;

public class WaqSymnItem extends CommonVo{
	
	private String rqstNo;
	private Integer rqstSno;
	/** 유사항목ID */
	private String symnItemId;
	/** 유사항목 논리명 */
	private String symnItemLnm;
	/** 유사항목 물리명 */
	private String symnItemPnm;
	/** 참조항목유형(1:유사항목,2:금지항목) */
	private String symnItemType;
	/** 표준항목ID */
	private String sditmId;
	/** 표준항목 논리명 */
	private String sditmLnm;
	/** 표준항목 물리명 */
	private String sditmPnm;
	
	private String rqstDcd;
	private String rvwStsCd;
	private String rvwConts;
	private String vrfRmk;
	private String vrfCd;
	private String objDescn;
	private Integer objVers;
	private String regTypCd;
	private Date frsRqstDtm;
	private String frsRqstUserId;
	private Date rqstDtm;
	private String rqstUserId;
	private Date aprvDtm;
	private String aprvUserId;
	
	/** 물리명분할 */
	private String pnmCriDs;

	public String getRqstNo() {
		return rqstNo;
	}

	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}

	public Integer getRqstSno() {
		return rqstSno;
	}

	public void setRqstSno(Integer rqstSno) {
		this.rqstSno = rqstSno;
	}

	public String getSymnItemId() {
		return symnItemId;
	}

	public void setSymnItemId(String symnItemId) {
		this.symnItemId = symnItemId;
	}

	public String getSymnItemLnm() {
		return symnItemLnm;
	}

	public void setSymnItemLnm(String symnItemLnm) {
		this.symnItemLnm = symnItemLnm;
	}

	public String getSymnItemPnm() {
		return symnItemPnm;
	}

	public void setSymnItemPnm(String symnItemPnm) {
		this.symnItemPnm = symnItemPnm;
	}

	public String getSymnItemType() {
		return symnItemType;
	}

	public void setSymnItemType(String symnItemType) {
		this.symnItemType = symnItemType;
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

	public String getRqstDcd() {
		return rqstDcd;
	}

	public void setRqstDcd(String rqstDcd) {
		this.rqstDcd = rqstDcd;
	}

	public String getRvwStsCd() {
		return rvwStsCd;
	}

	public void setRvwStsCd(String rvwStsCd) {
		this.rvwStsCd = rvwStsCd;
	}

	public String getRvwConts() {
		return rvwConts;
	}

	public void setRvwConts(String rvwConts) {
		this.rvwConts = rvwConts;
	}

	public String getVrfRmk() {
		return vrfRmk;
	}

	public void setVrfRmk(String vrfRmk) {
		this.vrfRmk = vrfRmk;
	}

	public String getVrfCd() {
		return vrfCd;
	}

	public void setVrfCd(String vrfCd) {
		this.vrfCd = vrfCd;
	}

	public String getObjDescn() {
		return objDescn;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public Integer getObjVers() {
		return objVers;
	}

	public void setObjVers(Integer objVers) {
		this.objVers = objVers;
	}

	public String getRegTypCd() {
		return regTypCd;
	}

	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
	}

	public Date getFrsRqstDtm() {
		return frsRqstDtm;
	}

	public void setFrsRqstDtm(Date frsRqstDtm) {
		this.frsRqstDtm = frsRqstDtm;
	}

	public String getFrsRqstUserId() {
		return frsRqstUserId;
	}

	public void setFrsRqstUserId(String frsRqstUserId) {
		this.frsRqstUserId = frsRqstUserId;
	}

	public Date getRqstDtm() {
		return rqstDtm;
	}

	public void setRqstDtm(Date rqstDtm) {
		this.rqstDtm = rqstDtm;
	}

	public String getRqstUserId() {
		return rqstUserId;
	}

	public void setRqstUserId(String rqstUserId) {
		this.rqstUserId = rqstUserId;
	}

	public Date getAprvDtm() {
		return aprvDtm;
	}

	public void setAprvDtm(Date aprvDtm) {
		this.aprvDtm = aprvDtm;
	}

	public String getAprvUserId() {
		return aprvUserId;
	}

	public void setAprvUserId(String aprvUserId) {
		this.aprvUserId = aprvUserId;
	}

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}

	
}