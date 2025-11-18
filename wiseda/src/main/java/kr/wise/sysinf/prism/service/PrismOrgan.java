package kr.wise.sysinf.prism.service;

import java.util.List;

public class PrismOrgan {
    private String organId;

    private String organName;

    private String organType;

    private String organLevel;
    
    private String aggrYn;
    
    private List<PrismResearchMst> reslist;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganType() {
        return organType;
    }

    public void setOrganType(String organType) {
        this.organType = organType;
    }

    public String getOrganLevel() {
        return organLevel;
    }

    public void setOrganLevel(String organLevel) {
        this.organLevel = organLevel;
    }

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("PrismOrgan [organId=")
				.append(organId)
				.append(", organName=")
				.append(organName)
				.append(", organType=")
				.append(organType)
				.append(", organLevel=")
				.append(organLevel)
				.append(", aggrYn=")
				.append(aggrYn)
				.append(", reslist=")
				.append(reslist != null ? reslist.subList(0,
						Math.min(reslist.size(), maxLen)) : null).append("]");
		return builder.toString();
	}

	public List<PrismResearchMst> getReslist() {
		return reslist;
	}

	public void setReslist(List<PrismResearchMst> reslist) {
		this.reslist = reslist;
	}

	public String getAggrYn() {
		return aggrYn;
	}

	public void setAggrYn(String aggrYn) {
		this.aggrYn = aggrYn;
	}
    
    
}