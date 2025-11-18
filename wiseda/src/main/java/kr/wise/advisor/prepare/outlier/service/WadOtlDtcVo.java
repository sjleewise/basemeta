/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : WadOtlDtcVo.java
 * 2. Package : kr.wise.advisor.prepare.outlier.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 27. 오전 10:59:52
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 27. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.service;

import java.util.List;

import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WadOtlDtcVo.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 27. 오전 10:59:52
 * </PRE>
 */
public class WadOtlDtcVo extends WadOtlDtc {
	
	private WaaAlg alg ;	//이상값 탐지 알고리즘 정보
	
	private List<WaaAlgArg> arglist ; //알고리즘 파라미터목록
	
	private List<WadAnaVar> varlist ; //이상값 탐지 변수목록

	/**
	 * @return the alg
	 */
	public WaaAlg getAlg() {
		return alg;
	}

	/**
	 * @param alg the alg to set
	 */
	public void setAlg(WaaAlg alg) {
		this.alg = alg;
	}

	/**
	 * @return the arglist
	 */
	public List<WaaAlgArg> getArglist() {
		return arglist;
	}

	/**
	 * @param arglist the arglist to set
	 */
	public void setArglist(List<WaaAlgArg> arglist) {
		this.arglist = arglist;
	}

	/**
	 * @return the varlist
	 */
	public List<WadAnaVar> getVarlist() {
		return varlist;
	}

	/**
	 * @param varlist the varlist to set
	 */
	public void setVarlist(List<WadAnaVar> varlist) {
		this.varlist = varlist;
	}
	
	

}
