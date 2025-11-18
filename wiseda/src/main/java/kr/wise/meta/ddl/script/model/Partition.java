package kr.wise.meta.ddl.script.model;

import java.util.ArrayList;
import java.util.List;

import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

public class Partition extends WaqDdlPart {
	
    
    private ArrayList<DdlPartMainVo> partMainList;
    private ArrayList<WaqDdlPartSub> partSubList;
    
    private List<Column> partKeyList;
    private List<Column> subPartKeyList;
    
    private String idxSpacPnm; //파티션 인덱스 스페이스명...


	public ArrayList<DdlPartMainVo> getPartMainList() {
		return partMainList;
	}

	public void setPartMainList(ArrayList<DdlPartMainVo> partMainList) {
		this.partMainList = partMainList;
	}

	public List<Column> getPartKeyList() {
		return partKeyList;
	}

	public void setPartKeyList(List<Column> partKeyList) {
		this.partKeyList = partKeyList;
	}

	public List<Column> getSubPartKeyList() {
		return subPartKeyList;
	}

	public void setSubPartKeyList(List<Column> subPartKeyList) {
		this.subPartKeyList = subPartKeyList;
	}


	public ArrayList<WaqDdlPartSub> getPartSubList() {
		return partSubList;
	}

	public void setPartSubList(ArrayList<WaqDdlPartSub> partSubList) {
		this.partSubList = partSubList;
	}

	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}

	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}
	    
    
}
