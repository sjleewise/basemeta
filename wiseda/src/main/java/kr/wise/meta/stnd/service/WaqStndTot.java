package kr.wise.meta.stnd.service;

import java.util.ArrayList;

import kr.wise.commons.cmm.CommonVo;

public class WaqStndTot extends CommonVo {

	private ArrayList<WaqStwd> word; 
	
	private ArrayList<WaqSditm> item;
	
	private ArrayList<WaqDmn> dmn;

	public ArrayList<WaqStwd> getWord() {
		return word;
	}

	public void setWord(ArrayList<WaqStwd> word) {
		this.word = word;
	}

	public ArrayList<WaqSditm> getItem() {
		return item;
	}

	public void setItem(ArrayList<WaqSditm> item) { 
		this.item = item;
	}

	public ArrayList<WaqDmn> getDmn() {
		return dmn;
	}

	public void setDmn(ArrayList<WaqDmn> dmn) {
		this.dmn = dmn;
	}

	
	
}