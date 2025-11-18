package kr.wise.meta.ddl.script.model;

import kr.wise.meta.ddl.service.WaqDdlGrt;


public class Grant extends WaqDdlGrt
{
 
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
    
    private String gtdCatalog = null;
    private String gtdSchema = null;
    
    private String rvkSelectYn;
	
	private String rvkInsertYn;
	
	private String rvkUpdateYn;
	
	private String rvkDeleteYn;
	
	private String rvkExecuteYn;
    
    public Grant() {
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
		builder.append("Sequence [catalog=").append(catalog)
				.append(", schema=").append(schema).append(", name=")
				.append(name).append(", type=").append(type)
				.append(", version=").append(version).append(", aprvDtm2=")
				.append(aprvDtm2).append(", rqstDtm2=").append(rqstDtm2)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	public String getGtdCatalog() {
		return gtdCatalog;
	}

	public void setGtdCatalog(String gtdCatalog) {
		this.gtdCatalog = gtdCatalog;
	}

	public String getGtdSchema() {
		return gtdSchema;
	}

	public void setGtdSchema(String gtdSchema) {
		this.gtdSchema = gtdSchema;
	}

	public String getRvkSelectYn() {
		return rvkSelectYn;
	}

	public void setRvkSelectYn(String rvkSelectYn) {
		this.rvkSelectYn = rvkSelectYn;
	}

	public String getRvkInsertYn() {
		return rvkInsertYn;
	}

	public void setRvkInsertYn(String rvkInsertYn) {
		this.rvkInsertYn = rvkInsertYn;
	}

	public String getRvkUpdateYn() {
		return rvkUpdateYn;
	}

	public void setRvkUpdateYn(String rvkUpdateYn) {
		this.rvkUpdateYn = rvkUpdateYn;
	}

	public String getRvkDeleteYn() {
		return rvkDeleteYn;
	}

	public void setRvkDeleteYn(String rvkDeleteYn) {
		this.rvkDeleteYn = rvkDeleteYn;
	}

	public String getRvkExecuteYn() {
		return rvkExecuteYn;
	}

	public void setRvkExecuteYn(String rvkExecuteYn) {
		this.rvkExecuteYn = rvkExecuteYn;
	}

}
