package kr.wise.commons.sysmgmt.menu.service;

import java.math.BigDecimal;

public class Comtnmenuinfo {
    private BigDecimal menuNo;

    private String menuNm;

    private String progrmFileNm;

    private BigDecimal upperMenuNo;

    private Integer menuOrdr;

    private String menuDc;

    private String relateImagePath;

    private String relateImageNm;

    public BigDecimal getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(BigDecimal menuNo) {
        this.menuNo = menuNo;
    }

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

    public BigDecimal getUpperMenuNo() {
        return upperMenuNo;
    }

    public void setUpperMenuNo(BigDecimal upperMenuNo) {
        this.upperMenuNo = upperMenuNo;
    }

    public Integer getMenuOrdr() {
        return menuOrdr;
    }

    public void setMenuOrdr(Integer menuOrdr) {
        this.menuOrdr = menuOrdr;
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
}