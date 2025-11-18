package kr.wise.commons.sysmgmt.menu.service;

import kr.wise.commons.cmm.SearchVO;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MenuStatVO.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.service
 * 4. Comment  : 메뉴 통계용 VO
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 30. 오전 6:28:47
 * </PRE>
 */ 
public class MenuStatVO extends SearchVO {
	
	/** 메뉴정보 */
	/** 메뉴번호 */
    private String menuNo;

    /** 메뉴순서 */
    private Integer menuOrdr;

    
    /** 상위메뉴번호 */
    private Integer upperMenuNo;

    /** 메뉴명 */
    private String menuNm;
    
    /** 메뉴패스 */
    private String menuPath;

    /** 프로그램파일명 */
    private String progrmFileNm;
    
    /** 접속일자 */
    private String statsDate;
    
    /** 접속수 */
    private Integer statsCo;
    
	/** 기간구분 */
	private String pdKind;

    /** 메뉴 설명 */
    private String menuDc;

    /** 관련이미지 경로 */
    private String relateImagePath;

    /** 관련이미지명 */
    private String relateImageNm;
    
	/** 사용자ID */
	private String loginId;

	/** 사용자명 */
	private String loginNm;


    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getProgrmFileNm() {
        return progrmFileNm;
    }

    public void setProgrmFileNm(String progrmFileNm) {
        this.progrmFileNm = progrmFileNm;
    }


    public String getMenuDc() {
        return menuDc;
    }

    public void setMenuDc(String menuDc) {
        this.menuDc = menuDc;
    }

    public String getRelateImagePath() {
        return relateImagePath;
    }

    public void setRelateImagePath(String relateImagePath) {
        this.relateImagePath = relateImagePath;
    }

    public String getRelateImageNm() {
        return relateImageNm;
    }

    public void setRelateImageNm(String relateImageNm) {
        this.relateImageNm = relateImageNm;
    }

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public Integer getMenuOrdr() {
		return menuOrdr;
	}

	public void setMenuOrdr(Integer menuOrdr) {
		this.menuOrdr = menuOrdr;
	}

	public Integer getUpperMenuNo() {
		return upperMenuNo;
	}

	public void setUpperMenuNo(Integer upperMenuNo) {
		this.upperMenuNo = upperMenuNo;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getStatsDate() {
		return statsDate;
	}

	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}

	public Integer getStatsCo() {
		return statsCo;
	}

	public void setStatsCo(Integer statsCo) {
		this.statsCo = statsCo;
	}

	public String getPdKind() {
		return pdKind;
	}

	public void setPdKind(String pdKind) {
		this.pdKind = pdKind;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginNm() {
		return loginNm;
	}

	public void setLoginNm(String loginNm) {
		this.loginNm = loginNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuStatVO [menuNo=").append(menuNo)
				.append(", menuOrdr=").append(menuOrdr)
				.append(", upperMenuNo=").append(upperMenuNo)
				.append(", menuNm=").append(menuNm).append(", menuPath=")
				.append(menuPath).append(", progrmFileNm=")
				.append(progrmFileNm).append(", statsDate=").append(statsDate)
				.append(", statsCo=").append(statsCo).append(", pdKind=")
				.append(pdKind).append(", menuDc=").append(menuDc)
				.append(", relateImagePath=").append(relateImagePath)
				.append(", relateImageNm=").append(relateImageNm)
				.append(", loginId=").append(loginId).append(", loginNm=")
				.append(loginNm).append("]");
		return builder.toString();
	}
}