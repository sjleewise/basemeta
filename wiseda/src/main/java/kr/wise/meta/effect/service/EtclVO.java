/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : EtclVO.java.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 4. 16:34:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열 : 2014. 7. 4. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : EtclVO.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 4. 16:34:00
 * </PRE>
 */
public class EtclVO extends CommonVo {
   
    private String objId;
    private String of_field;
    private String fieldId;
    private String fieldNm;
    private String taskId;
    private String taskNm;
    private String taskType;
    private String taskTypeNm;
    private String desctiption;
    private String valid;
    private String enable;
    private String reusable;
    private String mappingId;
    private String taskLastSaved;
    private String termsMappingNm;
    private String termsLastSaved;
    private String srcId;     
    private String srcFieldId;
    private String srcTblNm;  
    private String srcFieldNm;
    private String tgtId;     
    private String tgtFieldId;
    private String tgtTblNm;  
    private String tgtFieldNm;

    
    
    
    
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getOf_field() {
		return of_field;
	}
	public void setOf_field(String of_field) {
		this.of_field = of_field;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldNm() {
		return fieldNm;
	}
	public void setFieldNm(String fieldNm) {
		this.fieldNm = fieldNm;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskNm() {
		return taskNm;
	}
	public void setTaskNm(String taskNm) {
		this.taskNm = taskNm;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskTypeNm() {
		return taskTypeNm;
	}
	public void setTaskTypeNm(String taskTypeNm) {
		this.taskTypeNm = taskTypeNm;
	}
	public String getDesctiption() {
		return desctiption;
	}
	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getReusable() {
		return reusable;
	}
	public void setReusable(String reusable) {
		this.reusable = reusable;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getTaskLastSaved() {
		return taskLastSaved;
	}
	public void setTaskLastSaved(String taskLastSaved) {
		this.taskLastSaved = taskLastSaved;
	}
	
	
	public String getTermsMappingNm() {
		return termsMappingNm;
	}
	public void setTermsMappingNm(String termsMappingNm) {
		this.termsMappingNm = termsMappingNm;
	}
	public String getTermsLastSaved() {
		return termsLastSaved;
	}
	public void setTermsLastSaved(String termsLastSaved) {
		this.termsLastSaved = termsLastSaved;
	}
	
	
	
	public String getSrcId() {
		return srcId;
	}
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}
	public String getSrcFieldId() {
		return srcFieldId;
	}
	public void setSrcFieldId(String srcFieldId) {
		this.srcFieldId = srcFieldId;
	}
	public String getSrcTblNm() {
		return srcTblNm;
	}
	public void setSrcTblNm(String srcTblNm) {
		this.srcTblNm = srcTblNm;
	}
	public String getSrcFieldNm() {
		return srcFieldNm;
	}
	public void setSrcFieldNm(String srcFieldNm) {
		this.srcFieldNm = srcFieldNm;
	}
	public String getTgtId() {
		return tgtId;
	}
	public void setTgtId(String tgtId) {
		this.tgtId = tgtId;
	}
	public String getTgtFieldId() {
		return tgtFieldId;
	}
	public void setTgtFieldId(String tgtFieldId) {
		this.tgtFieldId = tgtFieldId;
	}
	public String getTgtTblNm() {
		return tgtTblNm;
	}
	public void setTgtTblNm(String tgtTblNm) {
		this.tgtTblNm = tgtTblNm;
	}
	public String getTgtFieldNm() {
		return tgtFieldNm;
	}
	public void setTgtFieldNm(String tgtFieldNm) {
		this.tgtFieldNm = tgtFieldNm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EtclVO [objId=").append(objId).append(", of_field=")
				.append(of_field).append(", fieldId=").append(fieldId)
				.append(", fieldNm=").append(fieldNm).append(", taskId=")
				.append(taskId).append(", taskNm=").append(taskNm)
				.append(", taskType=").append(taskType).append(", taskTypeNm=")
				.append(taskTypeNm).append(", desctiption=")
				.append(desctiption).append(", valid=").append(valid)
				.append(", enable=").append(enable).append(", reusable=")
				.append(reusable).append(", mappingId=").append(mappingId)
				.append(", taskLastSaved=").append(taskLastSaved)
				.append(", termsMappingNm=").append(termsMappingNm)
				.append(", termsLastSaved=").append(termsLastSaved)
				.append(", srcId=").append(srcId).append(", srcFieldId=")
				.append(srcFieldId).append(", srcTblNm=").append(srcTblNm)
				.append(", srcFieldNm=").append(srcFieldNm).append(", tgtId=")
				.append(tgtId).append(", tgtFieldId=").append(tgtFieldId)
				.append(", tgtTblNm=").append(tgtTblNm).append(", tgtFieldNm=")
				.append(tgtFieldNm).append("]");
		return builder.toString();
	}
	
	

}
