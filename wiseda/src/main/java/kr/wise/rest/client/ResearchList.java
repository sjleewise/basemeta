package kr.wise.rest.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contents")
public class ResearchList {
	
	String totalCnt;
	List<ResearchVo> research = new ArrayList<ResearchVo>();

	
	public String getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}
	public List<ResearchVo> getResearch() {
		return research;
	}
	public void setResearch(List<ResearchVo> research) {
		this.research = research;
	}
	
	
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("ResearchList [totalCnt=")
				.append(totalCnt)
				.append(", research=")
				.append(research != null ? research.subList(0,
						Math.min(research.size(), maxLen)) : null).append("]");
		return builder.toString();
	}
	
	

}
