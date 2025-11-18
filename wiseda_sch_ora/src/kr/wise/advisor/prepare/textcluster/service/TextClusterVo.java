package kr.wise.advisor.prepare.textcluster.service;

import java.util.ArrayList;

import kr.wise.commons.cmm.CommonVo;

public class TextClusterVo  {
    
	private String recommand;
	
	private String[] result;
	
	private int[] score;
	
	private int[] count;

	public String getRecommand() {
		return recommand;
	}

	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}

	public String[] getResult() {
		return result;
	}

	public void setResult(String[] result) {
		this.result = result;
	}

	public int[] getScore() {
		return score;
	}

	public void setScore(int[] score) {
		this.score = score;
	}

	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	} 
		
	
	
}