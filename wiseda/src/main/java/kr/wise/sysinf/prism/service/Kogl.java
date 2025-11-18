package kr.wise.sysinf.prism.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.wise.rest.client.AdapterCDATA;


@XmlAccessorType(XmlAccessType.FIELD)
public class Kogl {
	
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String koglOpenYn         ;
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String koglBusinessYn     ;
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String koglWorkUpdateYn   ;
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String koglContent        ;
	public String getKoglOpenYn() {
		return koglOpenYn;
	}
	public void setKoglOpenYn(String koglOpenYn) {
		this.koglOpenYn = koglOpenYn;
	}
	public String getKoglBusinessYn() {
		return koglBusinessYn;
	}
	public void setKoglBusinessYn(String koglBusinessYn) {
		this.koglBusinessYn = koglBusinessYn;
	}
	public String getKoglWorkUpdateYn() {
		return koglWorkUpdateYn;
	}
	public void setKoglWorkUpdateYn(String koglWorkUpdateYn) {
		this.koglWorkUpdateYn = koglWorkUpdateYn;
	}
	public String getKoglContent() {
		return koglContent;
	}
	public void setKoglContent(String koglContent) {
		this.koglContent = koglContent;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Kogl [koglOpenYn=").append(koglOpenYn)
				.append(", koglBusinessYn=").append(koglBusinessYn)
				.append(", koglWorkUpdateYn=").append(koglWorkUpdateYn)
				.append(", koglContent=").append(koglContent).append("]");
		return builder.toString();
	}

	
	

}
