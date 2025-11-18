package kr.wise.advisor.prepare.udefoutlier.daseimp.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.commons.cmm.CommonVo;

public class WadUodcDaseImp extends WadUod{ 
    private String udfOtlDtcId;

    private String creCompId; 

    private String dbConnTrgId;
    
    private String dbSchId;

    private String dbcTblNm;

    private String anaDaseId;
    
    private String anaDaseNm;

    private String daseId;

    private String impDataDcd;
    
    private String filePath;
    
    private String fileName;

    
    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUdfOtlDtcId() {
        return udfOtlDtcId;
    }

    public void setUdfOtlDtcId(String udfOtlDtcId) {
        this.udfOtlDtcId = udfOtlDtcId;
    }

    public String getCreCompId() {
        return creCompId;
    }

    public void setCreCompId(String creCompId) {
        this.creCompId = creCompId;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getAnaDaseId() {
        return anaDaseId;
    }

    public void setAnaDaseId(String anaDaseId) {
        this.anaDaseId = anaDaseId;
    }

    public String getDaseId() {
        return daseId;
    }

    public void setDaseId(String daseId) {
        this.daseId = daseId;
    }

    public String getImpDataDcd() {
        return impDataDcd;
    }

    public void setImpDataDcd(String impDataDcd) {
        this.impDataDcd = impDataDcd;
    } 

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
    
	public String getAnaDaseNm() {
		return anaDaseNm;
	}

	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
    
    
	public String toString() {
		String str = "udfOtlDtcId >>> " + this.udfOtlDtcId + " creCompId >>> " + this.creCompId + " dbConnTrgId >>> " + this.dbConnTrgId;
		str += " dbSchId >>> " + this.dbSchId + " dbcTblNm >>> " + this.dbcTblNm + " anaDaseId >>> " + this.anaDaseId;
		str += " anaDaseNm >>> " + this.anaDaseNm + " daseId >>> " + this.daseId + " impDataDcd >>> " + this.impDataDcd;
		
		return str;
	}
    
}