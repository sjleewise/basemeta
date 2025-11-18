package kr.wise.admin.ai.algorithm.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaAlg extends CommonVo{
    private String algId;

    private String algLnm;

    private String algPnm;

    private String algTypCd;
    
    //2018.08.21 추가
    //분할 분석 개수
    private long algCnt;


    public String getAlgId() {
        return algId;
    }

    public void setAlgId(String algId) {
        this.algId = algId;
    }

    public String getAlgLnm() {
        return algLnm;
    }

    public void setAlgLnm(String algLnm) {
        this.algLnm = algLnm;
    }

    public String getAlgPnm() {
        return algPnm;
    }

    public void setAlgPnm(String algPnm) {
        this.algPnm = algPnm;
    }

    public String getAlgTypCd() {
        return algTypCd;
    }

    public void setAlgTypCd(String algTypCd) {
        this.algTypCd = algTypCd;
    }

	public long getAlgCnt() {
		return algCnt;
	}

	public void setAlgCnt(long algCnt) {
		this.algCnt = algCnt;
	}

}