package kr.wise.meta.intf.service;

public class EraDataelmt {
	
    private String originTermnm;

    private String originTermnmen;

    private String termnm;

    private String termnmen;

    private String domainnm;

    private String domainnmen;
    
    private String infotype;

    private String dataType;

    private String ldataType;

	private String description;

	private String modelnm;

	private String subjectnm;
	
    private String inentitynm; 
	
	/////////////////////////////////// request..
	private String checktermnm;
	private String checktermnmen;
    private String searchval;
    private String searchcon; /* A:%%, F:뒤%, B:앞%, E:= */
    
    public String getLdataType() {
    	return ldataType;
    }
    
    public void setLdataType(String ldataType) {
    	this.ldataType = ldataType;
    }

    public String getInentitynm() {
		return inentitynm;
	}

	public void setInentitynm(String inentitynm) {
		this.inentitynm = inentitynm;
	}

	public String getIntablenm() {
		return intablenm;
	}

	public void setIntablenm(String intablenm) {
		this.intablenm = intablenm;
	}

	public String getIncolumnseq() {
		return incolumnseq;
	}

	public void setIncolumnseq(String incolumnseq) {
		this.incolumnseq = incolumnseq;
	}

	public String getIntermnm() {
		return intermnm;
	}

	public void setIntermnm(String intermnm) {
		this.intermnm = intermnm;
	}

	public String getIntermnmen() {
		return intermnmen;
	}

	public void setIntermnmen(String intermnmen) {
		this.intermnmen = intermnmen;
	}

	public String getIninfortype() {
		return ininfortype;
	}

	public void setIninfortype(String ininfortype) {
		this.ininfortype = ininfortype;
	}

	public String getIndomainnm() {
		return indomainnm;
	}

	public void setIndomainnm(String indomainnm) {
		this.indomainnm = indomainnm;
	}

	public String getIndatatype() {
		return indatatype;
	}

	public void setIndatatype(String indatatype) {
		this.indatatype = indatatype;
	}

	public String getInattributeid() {
		return inattributeid;
	}

	public void setInattributeid(String inattributeid) {
		this.inattributeid = inattributeid;
	}

	private String intablenm;
    private String incolumnseq;
    private String intermnm; 
    private String intermnmen;
    private String ininfortype;    
    private String indomainnm; 
    private String indatatype;
    private String inldatatype;
    
    
    public String getInldatatype() {
		return inldatatype;
	}

	public void setInldatatype(String inldatatype) {
		this.inldatatype = inldatatype;
	}

	private String inattributeid;
	

	public String getChecktermnm() {
		return checktermnm;
	}

	public void setChecktermnm(String checktermnm) {
		this.checktermnm = checktermnm;
	}

	public String getChecktermnmen() {
		return checktermnmen;
	}

	public void setChecktermnmen(String checktermnmen) {
		this.checktermnmen = checktermnmen;
	}

	public String getSearchval() {
		return searchval;
	}

	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}

	public String getSearchcon() {
		return searchcon;
	}

	public void setSearchcon(String searchcon) {
		this.searchcon = searchcon;
	}
	/////////////////////////////////// request..

    public String getOriginTermnm() {
        return originTermnm;
    }

    public void setOriginTermnm(String originTermnm) {
        this.originTermnm = originTermnm;
    }

    public String getOriginTermnmen() {
        return originTermnmen;
    }

    public void setOriginTermnmen(String originTermnmen) {
        this.originTermnmen = originTermnmen;
    }

    public String getTermnm() {
        return termnm;
    }

    public void setTermnm(String termnm) {
        this.termnm = termnm;
    }

    public String getTermnmen() {
        return termnmen;
    }

    public void setTermnmen(String termnmen) {
        this.termnmen = termnmen;
    }

    public String getDomainnm() {
        return domainnm;
    }

    public void setDomainnm(String domainnm) {
        this.domainnm = domainnm;
    }

    public String getDomainnmen() {
        return domainnmen;
    }

    public void setDomainnmen(String domainnmen) {
        this.domainnmen = domainnmen;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	public String getModelnm() {
		return modelnm;
	}

	public void setModelnm(String modelnm) {
		this.modelnm = modelnm;
	}

	public String getSubjectnm() {
		return subjectnm;
	}

	public void setSubjectnm(String subjectnm) {
		this.subjectnm = subjectnm;
	}
}