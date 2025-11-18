package kr.wise.commons.code.service;

import java.util.List;

import kr.wise.commons.cmm.CommonVo;

/**
 * WISE META, DQ 제품 전용 공통코드
 * @author insomnia (장명수)
 *
 */
public class WaaCommDcd extends CommonVo {

	private String commDcdId;	//공통코드 ID

    private String commDcd;		//공통구분코드

    private String commDcdNm;	//공통구분명

    private String uppCommDcdId; //상위공통코드 ID


    private String sysCdYn;		//시스템 코드 여부

    private List<WaaCommDtlCd> commDtlCds; //상세코드 리스트

    private List<CodeListVo> codeLists;		//공통코드 리스트
    
    private String commDtlCd;
    
    private String commDtlCdNm;
    
    

	public String getCommDtlCd() {
		return commDtlCd;
	}

	public void setCommDtlCd(String commDtlCd) {
		this.commDtlCd = commDtlCd;
	}

	public String getCommDtlCdNm() {
		return commDtlCdNm;
	}

	public void setCommDtlCdNm(String commDtlCdNm) {
		this.commDtlCdNm = commDtlCdNm;
	}

	public String getCommDcdId() {
        return commDcdId;
    }

    public void setCommDcdId(String commDcdId) {
        this.commDcdId = commDcdId;
    }

    public String getCommDcd() {
        return commDcd;
    }

    public void setCommDcd(String commDcd) {
        this.commDcd = commDcd;
    }

    public String getCommDcdNm() {
        return commDcdNm;
    }

    public void setCommDcdNm(String commDcdNm) {
        this.commDcdNm = commDcdNm;
    }

    public String getUppCommDcdId() {
        return uppCommDcdId;
    }

    public void setUppCommDcdId(String uppCommDcdId) {
        this.uppCommDcdId = uppCommDcdId;
    }

    public String getSysCdYn() {
        return sysCdYn;
    }

    public void setSysCdYn(String sysCdYn) {
        this.sysCdYn = sysCdYn;
    }

	public List<WaaCommDtlCd> getCommDtlCds() {
		return commDtlCds;
	}

	public void setCommDtlCds(List<WaaCommDtlCd> commDtlCds) {
		this.commDtlCds = commDtlCds;
	}



	public List<CodeListVo> getCodeLists() {
		return codeLists;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaCommDcd [commDcdId=");
		builder.append(commDcdId);
		builder.append(", commDcd=");
		builder.append(commDcd);
		builder.append(", commDcdNm=");
		builder.append(commDcdNm);
		builder.append(", uppCommDcdId=");
		builder.append(uppCommDcdId);
		builder.append(", sysCdYn=");
		builder.append(sysCdYn);
		builder.append(", commDtlCds=");
		builder.append(commDtlCds);
		builder.append(", codeLists=");
		builder.append(codeLists);
		builder.append("]");
		return builder.toString();
	}

	public void setCodeLists(List<CodeListVo> codeLists) {
		this.codeLists = codeLists;
	}



}