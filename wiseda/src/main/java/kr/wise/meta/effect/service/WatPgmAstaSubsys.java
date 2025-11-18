package kr.wise.meta.effect.service;

public class WatPgmAstaSubsys {
    private String requestId;

    private Short objId;

    private String objKnm;

    private String objPath;

    private Short parentId;

    private Short leafOrder;

    private Short ukObjId;

    private Short ukPrntObjId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Short getObjId() {
        return objId;
    }

    public void setObjId(Short objId) {
        this.objId = objId;
    }

    public String getObjKnm() {
        return objKnm;
    }

    public void setObjKnm(String objKnm) {
        this.objKnm = objKnm;
    }

    public String getObjPath() {
        return objPath;
    }

    public void setObjPath(String objPath) {
        this.objPath = objPath;
    }

    public Short getParentId() {
        return parentId;
    }

    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    public Short getLeafOrder() {
        return leafOrder;
    }

    public void setLeafOrder(Short leafOrder) {
        this.leafOrder = leafOrder;
    }

    public Short getUkObjId() {
        return ukObjId;
    }

    public void setUkObjId(Short ukObjId) {
        this.ukObjId = ukObjId;
    }

    public Short getUkPrntObjId() {
        return ukPrntObjId;
    }

    public void setUkPrntObjId(Short ukPrntObjId) {
        this.ukPrntObjId = ukPrntObjId;
    }
}