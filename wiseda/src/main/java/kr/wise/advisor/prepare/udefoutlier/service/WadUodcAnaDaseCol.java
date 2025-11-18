package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.Date;

public class WadUodcAnaDaseCol extends WadUodcAnaDase {
    private String anaDaseId;

    private String anaColSno;

    private String anaVarId;

    private String colPnm;
    
    private String colLnm;
    
    
    public String getAnaDaseId() {
        return anaDaseId;
    }

    public void setAnaDaseId(String anaDaseId) {
        this.anaDaseId = anaDaseId;
    }

    public String getAnaColSno() {
        return anaColSno;
    }

    public void setAnaColSno(String anaColSno) {
        this.anaColSno = anaColSno;
    }

    public String getAnaVarId() {
        return anaVarId;
    }

    public void setAnaVarId(String anaVarId) {
        this.anaVarId = anaVarId;
    }

    public String getColPnm() {
        return colPnm;
    }

    public void setColPnm(String colPnm) {
        this.colPnm = colPnm;
    }

	public String getColLnm() {
		return colLnm;
	}

	public void setColLnm(String colLnm) {
		this.colLnm = colLnm;
	}

   
}