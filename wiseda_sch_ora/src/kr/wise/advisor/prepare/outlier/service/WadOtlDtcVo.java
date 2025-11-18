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
	
    private String algId;

    private String algLnm;

    private String algPnm;

    private String algTypCd;
    
    //2018.08.21 추가
    //분할 분석 개수
    private long algCnt;

	
	private List<WadOtlArg> arglist ; //알고리즘 파라미터목록
	
	private List<WadOtlVal> varlist ; //이상값 탐지 변수목록

	/**
	 * @return the algId
	 */
	public String getAlgId() {
		return algId;
	}

	/**
	 * @param algId the algId to set
	 */
	public void setAlgId(String algId) {
		this.algId = algId;
	}

	/**
	 * @return the algLnm
	 */
	public String getAlgLnm() {
		return algLnm;
	}

	/**
	 * @param algLnm the algLnm to set
	 */
	public void setAlgLnm(String algLnm) {
		this.algLnm = algLnm;
	}

	/**
	 * @return the algPnm
	 */
	public String getAlgPnm() {
		return algPnm;
	}

	/**
	 * @param algPnm the algPnm to set
	 */
	public void setAlgPnm(String algPnm) {
		this.algPnm = algPnm;
	}

	/**
	 * @return the algTypCd
	 */
	public String getAlgTypCd() {
		return algTypCd;
	}

	/**
	 * @param algTypCd the algTypCd to set
	 */
	public void setAlgTypCd(String algTypCd) {
		this.algTypCd = algTypCd;
	}

	/**
	 * @return the arglist
	 */
	public List<WadOtlArg> getArglist() {
		return arglist;
	}

	/**
	 * @param arglist the arglist to set
	 */
	public void setArglist(List<WadOtlArg> arglist) {
		this.arglist = arglist;
	}

	/**
	 * @return the varlist
	 */
	public List<WadOtlVal> getVarlist() {
		return varlist;
	}

	/**
	 * @param varlist the varlist to set
	 */
	public void setVarlist(List<WadOtlVal> varlist) {
		this.varlist = varlist;
	}

	public long getAlgCnt() {
		return algCnt;
	}

	public void setAlgCnt(long algCnt) {
		this.algCnt = algCnt;
	}



	
	

}
