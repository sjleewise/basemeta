/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : WiseBizException.java
 * 2. Package : kr.wise.commons.cmm.exception
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 11. 오후 3:20:11
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 11. :            : 신규 개발.
 */
package kr.wise.commons.cmm.exception;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WiseBizException.java
 * 3. Package  : kr.wise.commons.cmm.exception
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 11. 오후 3:20:11
 * </PRE>
 */
public class WiseBizException extends RuntimeException {

	/** insomnia */
	private static final long serialVersionUID = 8193380948117574875L;


	private String errCode;
	private String errMsg;

	/**
	 * @return the errCode
	 */
	public String getErrCode() {
		return errCode;
	}
	/**
	 * @param errCode the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}
	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	/** insomnia */
	public WiseBizException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	/** insomnia */
	public WiseBizException(String string) {
		this.errCode = "NON";
		this.errMsg = string;
	}


    public String toString() {
        String s = getClass().getName();
        String message = this.errMsg;
        return (message != null) ? (s + ": " + message) : s;
    }


}
