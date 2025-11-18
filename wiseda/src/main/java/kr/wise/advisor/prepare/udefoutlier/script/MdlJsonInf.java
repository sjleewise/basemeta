package kr.wise.advisor.prepare.udefoutlier.script;


import java.util.ArrayList;

public class MdlJsonInf  {

	private String name;
	
	private ArrayList<NodeDataArray> nodeDataArray;
	private ArrayList<LinkDataArray> linkDataArray;
	
	
	public ArrayList<NodeDataArray> getNodeDataArray() {
		return nodeDataArray;
	}
	public void setNodeDataArray(ArrayList<NodeDataArray> nodeDataArray) {
		this.nodeDataArray = nodeDataArray;
	}
	public ArrayList<LinkDataArray> getLinkDataArray() {
		return linkDataArray;
	}
	public void setLinkDataArray(ArrayList<LinkDataArray> linkDataArray) {
		this.linkDataArray = linkDataArray;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
