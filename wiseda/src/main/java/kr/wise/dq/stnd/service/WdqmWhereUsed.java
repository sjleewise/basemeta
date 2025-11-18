package kr.wise.dq.stnd.service;

public class WdqmWhereUsed {
	
	private String objType;
	
	private String objId;
	
	private String objNm;
	
	private String objLnm;
	
	private String objPnm;
	
	private String ofDomain;

	private String reMark;
	
	public String getReMark() {
		return reMark;
	}

	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public String getObjLnm() {
		return objLnm;
	}

	public void setObjLnm(String objLnm) {
		this.objLnm = objLnm;
	}

	public String getObjPnm() {
		return objPnm;
	}

	public void setObjPnm(String objPnm) {
		this.objPnm = objPnm;
	}

	public String getOfDomain() {
		return ofDomain;
	}

	public void setOfDomain(String ofDomain) {
		this.ofDomain = ofDomain;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamWhereUsed [objType=").append(objType)
				.append(", objId=").append(objId).append(", objNm=")
				.append(objNm).append(", objLnm=").append(objLnm)
				.append(", objPnm=").append(objPnm).append(", ofDomain=")
				.append(ofDomain).append("]");
		return builder.toString();
	}
	
	
	
}
