/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : WaaBizInfo.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 13. 오후 12:35:33
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 13. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WaaBizInfo.java
 * 3. Package  : kr.wise.commons.rqstmst.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 13. 오후 12:35:33
 * </PRE>
 */
public class WaaBizInfo {

    private String bizDcd;		//업무구분코드

    private String bizDtlCd;	//업무상세구분코드

    private String menuId;     //업무별 요청 메뉴

    private String progrmFileNm; //프로그램 명

    private String url;			//프로그램 경로

    private String TblNm;		//테이블 명

    private String ColNm;		//컬럼
    private String ColLnm;		//논리컬럼명

    
	public String getColLnm() {
		return ColLnm;
	}

	public void setColLnm(String colLnm) {
		ColLnm = colLnm;
	}

	/**
	 * @return the bizDcd
	 */
	public String getBizDcd() {
		return bizDcd;
	}

	/**
	 * @param bizDcd the bizDcd to set
	 */
	public void setBizDcd(String bizDcd) {
		this.bizDcd = bizDcd;
	}

	/**
	 * @return the bizDtlCd
	 */
	public String getBizDtlCd() {
		return bizDtlCd;
	}

	/**
	 * @param bizDtlCd the bizDtlCd to set
	 */
	public void setBizDtlCd(String bizDtlCd) {
		this.bizDtlCd = bizDtlCd;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the progrmFileNm
	 */
	public String getProgrmFileNm() {
		return progrmFileNm;
	}

	/**
	 * @param progrmFileNm the progrmFileNm to set
	 */
	public void setProgrmFileNm(String progrmFileNm) {
		this.progrmFileNm = progrmFileNm;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tblNm
	 */
	public String getTblNm() {
		return TblNm;
	}

	/**
	 * @param tblNm the tblNm to set
	 */
	public void setTblNm(String tblNm) {
		TblNm = tblNm;
	}

	/**
	 * @return the colNm
	 */
	public String getColNm() {
		return ColNm;
	}

	/**
	 * @param colNm the colNm to set
	 */
	public void setColNm(String colNm) {
		ColNm = colNm;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaBizInfo [bizDcd=").append(bizDcd)
				.append(", bizDtlCd=").append(bizDtlCd).append(", menuId=")
				.append(menuId).append(", progrmFileNm=").append(progrmFileNm)
				.append(", url=").append(url).append(", TblNm=").append(TblNm)
				.append(", ColNm=").append(ColNm)
				.append(", ColLnm=").append(ColLnm)
				.append("]");
		return builder.toString();
	}




}
