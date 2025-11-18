package kr.wise.meta.ddl.script.model;

import java.util.ArrayList;

import kr.wise.meta.ddl.service.WaqDdlPartMain;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

public class DdlPartMainVo extends WaqDdlPartMain {

	private ArrayList<WaqDdlPartSub> partSubList;

	public ArrayList<WaqDdlPartSub> getPartSubList() {
		return partSubList;
	}

	public void setPartSubList(ArrayList<WaqDdlPartSub> partSubList) {
		this.partSubList = partSubList;
	}
	
}
