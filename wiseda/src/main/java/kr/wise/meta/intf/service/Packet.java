package kr.wise.meta.intf.service;

import java.util.ArrayList;

public class Packet {
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public void addItem(Item item){
		StringBuffer result = new StringBuffer();
		result.append(item.raw());
		this.items.add(item);
	}
	
	public Item getItem(int index){
		return this.items.get(index);
	}
	
	public String raw() {
		StringBuffer result = new StringBuffer();
		for(Item item:items){
			result.append(item.raw());
		}
		return result.toString();
	}
	
	public byte[] toByte()
	{
	//	byte[] array = items.toArray(new byte[items.size()]);
		byte[] array = new byte[items.size()];
		System.out.println("items size : " + items.size());
		Byte[] bytes = (Byte[])items.toArray();
		
		for(int i=0; i<bytes.length; i++)
		{
			array[i] = bytes[i].byteValue();
		}
		
		return array;
	}
	
}
