package kr.wise.commons.sysmgmt.menu.service;
import kr.wise.commons.cmm.CommonVo;

public class MenuManageVO extends CommonVo {




	/** 메뉴ID */
    private String menuId;

    /** 만료일시 */
//    private Date expDtm;
//
//    /** 시작일시 */
//    private Date strDtm;

    /** 메뉴명 */
    private String menuNm;

    /** 상위메뉴ID */
    private String uppMenuId;

    /** 메뉴레벨 */
    private Integer menuLvl;

    /** 파일경로 */
    private String filePath;

    /** 파일명 */
    private String fileNm;

    /** 화면출력여부 */
    private String dispYn;

    /** 화면출력순서 */
    private Integer dispOrd;

    /** 관리자메뉴여부 */
    private String mngrMenuYn;

    /** 객체설명 */
//    private String objDescn;
//
//    /** 객체버전 */
//    private Integer objVers;
//
//    /** 등록구분코드 */
//    private String regTypCd;
//
//    /** 작성시각 */
//    private Date writDtm;
//
//    /** 작성자 */
//    private String writUserId;

    /** 이미지경로 */
    private String imgPath;

    /** 메뉴구분코드 */
    private String menuDcd;

    private String uppMenuNm;

    /** 즐겨찾기 여부 YN 이상익 추가 */
    private String favoriteYn;
	private String userId;
    private String userGroupId;
    private String helpId;
    
    /** 영문메뉴명 */
    private String menuNmEn;
    /** 영문 full path */
    private String fullPathEn;
    
    public String getMenuNmEn() {
		return menuNmEn;
	}

	public void setMenuNmEn(String menuNmEn) {
		this.menuNmEn = menuNmEn;
	}

	public String getFullPathEn() {
		return fullPathEn;
	}

	public void setFullPathEn(String fullPathEn) {
		this.fullPathEn = fullPathEn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}



	public String getFavoriteYn() {
		return favoriteYn;
	}

	public void setFavoriteYn(String favoriteYn) {
		this.favoriteYn = favoriteYn;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getMenuDcd() {
		return menuDcd;
	}

	public void setMenuDcd(String menuDcd) {
		this.menuDcd = menuDcd;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

//	public Date getExpDtm() {
//		return expDtm;
//	}
//
//	public void setExpDtm(Date expDtm) {
//		this.expDtm = expDtm;
//	}
//
//	public Date getStrDtm() {
//		return strDtm;
//	}
//
//	public void setStrDtm(Date strDtm) {
//		this.strDtm = strDtm;
//	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getUppMenuId() {
		return uppMenuId;
	}

	public void setUppMenuId(String uppMenuId) {
		this.uppMenuId = uppMenuId;
	}

	public Integer getMenuLvl() {
		return menuLvl;
	}

	public void setMenuLvl(Integer menuLvl) {
		this.menuLvl = menuLvl;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getDispYn() {
		return dispYn;
	}

	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}

	public Integer getDispOrd() {
		return dispOrd;
	}

	public void setDispOrd(Integer dispOrd) {
		this.dispOrd = dispOrd;
	}

	public String getMngrMenuYn() {
		return mngrMenuYn;
	}

	public void setMngrMenuYn(String mngrMenuYn) {
		this.mngrMenuYn = mngrMenuYn;
	}

	public String getUppMenuNm() {
		return uppMenuNm;
	}

	public void setUppMenuNm(String uppMenuNm) {
		this.uppMenuNm = uppMenuNm;
	}

	public String getHelpId() {
		return helpId;
	}

	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuManageVO [menuId=").append(menuId)
				.append(", menuNm=").append(menuNm).append(", uppMenuId=")
				.append(uppMenuId).append(", menuLvl=").append(menuLvl)
				.append(", filePath=").append(filePath).append(", fileNm=")
				.append(fileNm).append(", dispYn=").append(dispYn)
				.append(", dispOrd=").append(dispOrd).append(", mngrMenuYn=")
				.append(mngrMenuYn).append(", imgPath=").append(imgPath)
				.append(", menuDcd=").append(menuDcd).append(", uppMenuNm=")
				.append(uppMenuNm).append(", helpId=").append(helpId).append("]");
		return builder.toString()+super.toString();
	}


//	public String getObjDescn() {
//		return objDescn;
//	}
//
//	public void setObjDescn(String objDescn) {
//		this.objDescn = objDescn;
//	}
//
//	public Integer getObjVers() {
//		return objVers;
//	}
//
//	public void setObjVers(Integer objVers) {
//		this.objVers = objVers;
//	}
//
//	public String getRegTypCd() {
//		return regTypCd;
//	}
//
//	public void setRegTypCd(String regTypCd) {
//		this.regTypCd = regTypCd;
//	}
//
//	public Date getWritDtm() {
//		return writDtm;
//	}
//
//	public void setWritDtm(Date writDtm) {
//		this.writDtm = writDtm;
//	}
//
//	public String getWritUserId() {
//		return writUserId;
//	}
//
//	public void setWritUserId(String writUserId) {
//		this.writUserId = writUserId;
//	}

	}





