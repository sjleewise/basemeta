/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DmnResult.java
 * 2. Package : kr.wise.executor.dm
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 2. 12. 오후 3:45:08
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 2. 12. :            : 신규 개발.
 */
package kr.wise.executor.dm;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmnResult.java
 * 3. Package  : kr.wise.executor.dm
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 2. 12. 오후 3:45:08
 * </PRE>
 */
public class DmnResult {
	
	String varId;
	
	List<Float> dmnpdt;

	/**
	 * @return the varId
	 */
	public String getVarId() {
		return varId;
	}

	/**
	 * @param varId the varId to set
	 */
	public void setVarId(String varId) {
		this.varId = varId;
	}

	/**
	 * @return the dmnpdt
	 */
	public List<Float> getDmnpdt() {
		return dmnpdt;
	}

	/**
	 * @param dmnpdt the dmnpdt to set
	 */
	public void setDmnpdt(List<Float> dmnpdt) {
		this.dmnpdt = dmnpdt;
	}

}
