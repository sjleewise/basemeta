package kr.wise.meta.ddl.script.model;

import kr.wise.meta.ddletc.service.WaqDdlEtc;

public class EtcObject extends WaqDdlEtc{
	
	/** The catalog of this sequence as read from the database. */
    private String catalog = null;
    /** The sequence's schema. */
    private String schema = null;
    /** The sequence's name. */
    private String name = null;

    /** The type of the database. */
    private String type = null;
    
    /** The type name of the database. */
    private String typeNm = null;
        
    /** The version of the database. */
    private String version;
    
    private String aprvDtm2;
    private String rqstDtm2;
    
    public EtcObject() {
		super();
	}

	public String getCatalog() {
		return catalog;
	}
	
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTypeNm() {
		return typeNm;
	}
	
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getAprvDtm2() {
		return aprvDtm2;
	}
	
	public void setAprvDtm2(String aprvDtm2) {
		this.aprvDtm2 = aprvDtm2;
	}
	
	public String getRqstDtm2() {
		return rqstDtm2;
	}
	
	public void setRqstDtm2(String rqstDtm2) {
		this.rqstDtm2 = rqstDtm2;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EtcObject [catalog=").append(catalog)
				.append(", schema=").append(schema).append(", name=")
				.append(name).append(", type=").append(type).append(", typeNm=").append(typeNm)
				.append(", version=").append(version).append(", aprvDtm2=")
				.append(aprvDtm2).append(", rqstDtm2=").append(rqstDtm2)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
}
