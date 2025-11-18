/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutScatterData.java
 * 2. Package : kr.wise.advisor.prepare.outlier.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 28. 오후 4:13:49
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 28. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : OutScatterData.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 28. 오후 4:13:49
 * </PRE>
 */
public class OutScatterData {
	
	List<List<Float>> inlier ;
	List<List<Float>> outlier ;
	/**
	 * @return the inlier
	 */
	public List<List<Float>> getInlier() {
		return inlier;
	}
	/**
	 * @param inlier the inlier to set
	 */
	public void setInlier(List<List<Float>> inlier) {
		this.inlier = inlier;
	}
	/**
	 * @return the outlier
	 */
	public List<List<Float>> getOutlier() {
		return outlier;
	}
	/**
	 * @param outlier the outlier to set
	 */
	public void setOutlier(List<List<Float>> outlier) {
		this.outlier = outlier;
	}
	
}
