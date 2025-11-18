package kr.wise.dq.bizrule.service;

import java.math.BigDecimal;
import java.util.Date;

import kr.wise.dq.criinfo.bizarea.service.WamBizAreaVO;

public class WamBrResult extends WamBizAreaVO{
    private String brId;

    private Date anaStrDtm;

    private Date anaEndDtm;

    private Integer anaDgr;

    private BigDecimal anaCnt;

    private BigDecimal erCnt;

    private String anaLogId;

    private String anaRunUserId;

    private String anaTime;

    private String objNm;
    
    private BigDecimal brCnt;
    
    

    public String getBrId() {
        return brId;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    public Date getAnaStrDtm() {
        return anaStrDtm;
    }

    public void setAnaStrDtm(Date anaStrDtm) {
        this.anaStrDtm = anaStrDtm;
    }

    public Date getAnaEndDtm() {
        return anaEndDtm;
    }

    public void setAnaEndDtm(Date anaEndDtm) {
        this.anaEndDtm = anaEndDtm;
    }

    public Integer getAnaDgr() {
        return anaDgr;
    }

    public void setAnaDgr(Integer anaDgr) {
        this.anaDgr = anaDgr;
    }

    public BigDecimal getAnaCnt() {
        return anaCnt;
    }

    public void setAnaCnt(BigDecimal anaCnt) {
        this.anaCnt = anaCnt;
    }

    public BigDecimal getErCnt() {
        return erCnt;
    }

    public void setErCnt(BigDecimal erCnt) {
        this.erCnt = erCnt;
    }

    public String getAnaLogId() {
        return anaLogId;
    }

    public void setAnaLogId(String anaLogId) {
        this.anaLogId = anaLogId;
    }

    public String getAnaRunUserId() {
        return anaRunUserId;
    }

    public void setAnaRunUserId(String anaRunUserId) {
        this.anaRunUserId = anaRunUserId;
    }

    public String getAnaTime() {
        return anaTime;
    }

    public void setAnaTime(String anaTime) {
        this.anaTime = anaTime;
    }

    public String getObjNm() {
        return objNm;
    }

    public void setObjNm(String objNm) {
        this.objNm = objNm;
    }
    
    

	public BigDecimal getBrCnt() {
		return brCnt;
	}

	public void setBrCnt(BigDecimal brCnt) {
		this.brCnt = brCnt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamBrResult [brId=").append(brId)
				.append(", anaStrDtm=").append(anaStrDtm)
				.append(", anaEndDtm=").append(anaEndDtm).append(", anaDgr=")
				.append(anaDgr).append(", anaCnt=").append(anaCnt)
				.append(", erCnt=").append(erCnt).append(", anaLogId=")
				.append(anaLogId).append(", anaRunUserId=")
				.append(anaRunUserId).append(", anaTime=").append(anaTime)
				.append(", objNm=").append(objNm).append(", brCnt=")
				.append(brCnt).append("]");
		return builder.toString();
	}
}