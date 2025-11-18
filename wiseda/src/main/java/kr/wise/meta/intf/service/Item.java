package kr.wise.meta.intf.service;

public class Item {
	
	String name;
	int length;
	String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String raw() {
		StringBuffer padded = new StringBuffer(this.value);
		while(padded.toString().getBytes().length < this.length){
			padded.append(' ');
		}
		return padded.toString();
	}
	
	
}
