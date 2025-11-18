package kr.wise.meta.codecfcsys.service;

public class WaqCodeCfcSysItem extends WaqCodeCfcSys {

    private Integer codeCfcSysItemSeq;

    private String codeCfcSysItemCd;

    private String codeCfcSysItemFrm;

    private Short codeCfcSysItemLen;

    private String codeCfcSysItemSpt;

    private String codeCfcSysItemRefTbl;

    private String codeCfcSysItemRefCol;
    
    private String codeCfcSysItemRefId;

    public Integer getCodeCfcSysItemSeq() {
        return codeCfcSysItemSeq;
    }

    public void setCodeCfcSysItemSeq(Integer codeCfcSysItemSeq) {
        this.codeCfcSysItemSeq = codeCfcSysItemSeq;
    }

    public String getCodeCfcSysItemCd() {
        return codeCfcSysItemCd;
    }

    public void setCodeCfcSysItemCd(String codeCfcSysItemCd) {
        this.codeCfcSysItemCd = codeCfcSysItemCd;
    }

    public String getCodeCfcSysItemFrm() {
        return codeCfcSysItemFrm;
    }

    public void setCodeCfcSysItemFrm(String codeCfcSysItemFrm) {
        this.codeCfcSysItemFrm = codeCfcSysItemFrm;
    }

    public Short getCodeCfcSysItemLen() {
        return codeCfcSysItemLen;
    }

    public void setCodeCfcSysItemLen(Short codeCfcSysItemLen) {
        this.codeCfcSysItemLen = codeCfcSysItemLen;
    }

    public String getCodeCfcSysItemSpt() {
        return codeCfcSysItemSpt;
    }

    public void setCodeCfcSysItemSpt(String codeCfcSysItemSpt) {
        this.codeCfcSysItemSpt = codeCfcSysItemSpt;
    }

    public String getCodeCfcSysItemRefTbl() {
        return codeCfcSysItemRefTbl;
    }

    public void setCodeCfcSysItemRefTbl(String codeCfcSysItemRefTbl) {
        this.codeCfcSysItemRefTbl = codeCfcSysItemRefTbl;
    }

    public String getCodeCfcSysItemRefCol() {
        return codeCfcSysItemRefCol;
    }

    public void setCodeCfcSysItemRefCol(String codeCfcSysItemRefCol) {
        this.codeCfcSysItemRefCol = codeCfcSysItemRefCol;
    }

	public String getCodeCfcSysItemRefId() {
		return codeCfcSysItemRefId;
	}

	public void setCodeCfcSysItemRefId(String codeCfcSysItemRefId) {
		this.codeCfcSysItemRefId = codeCfcSysItemRefId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqCodeCfcSysItem [codeCfcSysItemSeq=")
				.append(codeCfcSysItemSeq).append(", codeCfcSysItemCd=")
				.append(codeCfcSysItemCd).append(", codeCfcSysItemFrm=")
				.append(codeCfcSysItemFrm).append(", codeCfcSysItemLen=")
				.append(codeCfcSysItemLen).append(", codeCfcSysItemSpt=")
				.append(codeCfcSysItemSpt).append(", codeCfcSysItemRefTbl=")
				.append(codeCfcSysItemRefTbl).append(", codeCfcSysItemRefCol=")
				.append(codeCfcSysItemRefCol).append(", codeCfcSysItemRefId=")
				.append(codeCfcSysItemRefId).append("]");
		return builder.toString() + super.toString();
	}
    
    
}