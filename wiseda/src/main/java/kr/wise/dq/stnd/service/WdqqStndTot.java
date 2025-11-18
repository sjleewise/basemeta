package kr.wise.dq.stnd.service;

import java.util.ArrayList;

import kr.wise.commons.cmm.CommonVo;

public class WdqqStndTot extends CommonVo {

	private ArrayList<WdqqStwd> word; 
	
	private ArrayList<WdqqSditm> item;
	
	private ArrayList<WdqqDmn> dmn;

	public ArrayList<WdqqStwd> getWord() {
		return word;
	}

	public void setWord(ArrayList<WdqqStwd> word) {
		this.word = word;
	}

	public ArrayList<WdqqSditm> getItem() {
		return item;
	}

	public void setItem(ArrayList<WdqqSditm> item) { 
		this.item = item;
	}

	public ArrayList<WdqqDmn> getDmn() {
		return dmn;
	}

	public void setDmn(ArrayList<WdqqDmn> dmn) {
		this.dmn = dmn;
	}

	
	
}