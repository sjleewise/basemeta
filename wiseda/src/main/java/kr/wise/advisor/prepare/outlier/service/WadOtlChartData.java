/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : WadOtlChartData.java
 * 2. Package : kr.wise.advisor.prepare.outlier.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 27. 오후 11:52:48
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 27. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WadOtlChartData.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 27. 오후 11:52:48
 * </PRE>
 */
public class WadOtlChartData {

	String colId;
	String colNm;
	
	List<Float> colVal;

	/**
	 * @return the colId
	 */
	public String getColId() {
		return colId;
	}

	/**
	 * @param colId the colId to set
	 */
	public void setColId(String colId) {
		this.colId = colId;
	}

	/**
	 * @return the colNm
	 */
	public String getColNm() {
		return colNm;
	}

	/**
	 * @param colNm the colNm to set
	 */
	public void setColNm(String colNm) {
		this.colNm = colNm;
	}

	/**
	 * @return the colVal
	 */
	public List<Float> getColVal() {
		return colVal;
	}

	/**
	 * @param colVal the colVal to set
	 */
	public void setColVal(List<Float> colVal) {
		this.colVal = colVal;
	}
	
	
}
