package kr.wise.advisor.prepare.textcluster.service;

import kr.wise.commons.cmm.CommonVo;

public class WadClstData extends CommonVo {
    private String clstId;

    private Integer clstSno;

    private Integer colCnt;

    private String srcNm;

    private String clstNm;

    private Integer clstScr;
    
    private String clstYn;

	public String getClstId() {
		return clstId;
	}

	public void setClstId(String clstId) {
		this.clstId = clstId;
	}

	public Integer getClstSno() {
		return clstSno;
	}

	public void setClstSno(Integer clstSno) {
		this.clstSno = clstSno;
	}

	public Integer getColCnt() {
		return colCnt;
	}

	public void setColCnt(Integer colCnt) {
		this.colCnt = colCnt;
	}

	public String getSrcNm() {
		return srcNm;
	}

	public void setSrcNm(String srcNm) {
		this.srcNm = srcNm;
	}

	public String getClstNm() {
		return clstNm;
	}

	public void setClstNm(String clstNm) {
		this.clstNm = clstNm;
	}

	public Integer getClstScr() {
		return clstScr;
	}

	public void setClstScr(Integer clstScr) {
		this.clstScr = clstScr;
	}

	public String getClstYn() {
		return clstYn;
	}

	public void setClstYn(String clstYn) {
		this.clstYn = clstYn;
	}
    
}