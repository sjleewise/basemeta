package kr.wise.meta.model.service;

public class R9Mart  {
	//Object
    private Object Model;
    private Object Library;
    private Object Subject_Area;
    private Object Entity;
    private Object Udp;
    private Object Attribute;

    //Entity + Attribute Id,Name는 공통
    private String Id;
    private String Name;
    private String Physical_Name;
    private String Definition;
    private String Type;
    
    //Attribute
    private String Null_Option_Type;
    private String Is_PK;
    private String Is_FK;
    private String Domain_Name;
    private String Logical_Data_Type;
    private String Physical_Data_Type;
    private String Default_Name;
    private String Default_Value;
    
    //Lib
    private String libId;
    private String libName;
    private String modelId;
    private String modelName;
    private String subJectAreaId;
    private String subJectAreaName;
    
    //UDP
    private String Value;

	public Object getModel() {
		return Model;
	}

	public void setModel(Object model) {
		Model = model;
	}

	public Object getLibrary() {
		return Library;
	}

	public void setLibrary(Object library) {
		Library = library;
	}

	public Object getSubject_Area() {
		return Subject_Area;
	}

	public void setSubject_Area(Object subject_Area) {
		Subject_Area = subject_Area;
	}

	public Object getEntity() {
		return Entity;
	}

	public void setEntity(Object entity) {
		Entity = entity;
	}

	public Object getUdp() {
		return Udp;
	}

	public void setUdp(Object udp) {
		Udp = udp;
	}

	public Object getAttribute() {
		return Attribute;
	}

	public void setAttribute(Object attribute) {
		Attribute = attribute;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhysical_Name() {
		return Physical_Name;
	}

	public void setPhysical_Name(String physical_Name) {
		Physical_Name = physical_Name;
	}

	public String getDefinition() {
		return Definition;
	}

	public void setDefinition(String definition) {
		Definition = definition;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getNull_Option_Type() {
		return Null_Option_Type;
	}

	public void setNull_Option_Type(String null_Option_Type) {
		Null_Option_Type = null_Option_Type;
	}

	public String getIs_PK() {
		return Is_PK;
	}

	public void setIs_PK(String is_PK) {
		Is_PK = is_PK;
	}

	public String getIs_FK() {
		return Is_FK;
	}

	public void setIs_FK(String is_FK) {
		Is_FK = is_FK;
	}

	public String getDomain_Name() {
		return Domain_Name;
	}

	public void setDomain_Name(String domain_Name) {
		Domain_Name = domain_Name;
	}

	public String getLogical_Data_Type() {
		return Logical_Data_Type;
	}

	public void setLogical_Data_Type(String logical_Data_Type) {
		Logical_Data_Type = logical_Data_Type;
	}

	public String getPhysical_Data_Type() {
		return Physical_Data_Type;
	}

	public void setPhysical_Data_Type(String physical_Data_Type) {
		Physical_Data_Type = physical_Data_Type;
	}

	public String getDefault_Name() {
		return Default_Name;
	}

	public void setDefault_Name(String default_Name) {
		Default_Name = default_Name;
	}

	public String getDefault_Value() {
		return Default_Value;
	}

	public void setDefault_Value(String default_Value) {
		Default_Value = default_Value;
	}

	public String getLibId() {
		return libId;
	}

	public void setLibId(String libId) {
		this.libId = libId;
	}

	public String getLibName() {
		return libName;
	}

	public void setLibName(String libName) {
		this.libName = libName;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSubJectAreaId() {
		return subJectAreaId;
	}

	public void setSubJectAreaId(String subJectAreaId) {
		this.subJectAreaId = subJectAreaId;
	}

	public String getSubJectAreaName() {
		return subJectAreaName;
	}

	public void setSubJectAreaName(String subJectAreaName) {
		this.subJectAreaName = subJectAreaName;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	@Override
	public String toString() {
		return "R9Mart [Model=" + Model + ", Library=" + Library
				+ ", Subject_Area=" + Subject_Area + ", Entity=" + Entity
				+ ", Udp=" + Udp + ", Attribute=" + Attribute + ", Id=" + Id
				+ ", Name=" + Name + ", Physical_Name=" + Physical_Name
				+ ", Definition=" + Definition + ", Type=" + Type
				+ ", Null_Option_Type=" + Null_Option_Type + ", Is_PK=" + Is_PK
				+ ", Is_FK=" + Is_FK + ", Domain_Name=" + Domain_Name
				+ ", Logical_Data_Type=" + Logical_Data_Type
				+ ", Physical_Data_Type=" + Physical_Data_Type
				+ ", Default_Name=" + Default_Name + ", Default_Value="
				+ Default_Value + ", libId=" + libId + ", libName=" + libName
				+ ", modelId=" + modelId + ", modelName=" + modelName
				+ ", subJectAreaId=" + subJectAreaId + ", subJectAreaName="
				+ subJectAreaName + ", Value=" + Value + "]";
	}
}