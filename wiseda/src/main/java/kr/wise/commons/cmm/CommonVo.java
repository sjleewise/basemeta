package kr.wise.commons.cmm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.wise.commons.helper.IBSDateJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <PRE>
 * 1. ClassName : CommonVo
 * 2. Package  : kr.wise.egmd.rqstmst.model
 * 3. Comment  : 요청 및 공통 컬럼 자바빈 객체
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 1.
 * </PRE>
 */
public class CommonVo {

	/*
	 * IBSheet 전용 속성...
	 */
	private Integer ibsSeq;		//순번

	private String ibsStatus;	//상태(I/U/D/R)

	private String ibsCheck;	//체크박스

	public Integer Level;		//Tree level (index는 0부터 시작)

	public String FontColor;		//그리드 ROW FONT COLOR

	public String BackColor; 		//그리드 ROW 배경색 : #f6f6f6
	
	public int pageNum;		//그리드 페이징처리 페이지 번호
	
	public int onePageRow;		//그리드 페이징처리 페이지 DATA SET 건수	

	/*
	 * PK속성 (요청번호, 요청일련번호, 요청상세일련번호, 만료일시, 시작일시)
	 */
    private String rqstNo; 		// 요청번호

    private Integer rqstSno; 	// 요청일련번호

    private Integer rqstDtlSno; 	// 요청상세일련번호
    
    private Integer rqstDtlDtlSno; 	// 요청상세상세일련번호

	private Date expDtm;		//만료일시

	private Date strDtm;		//시작일시

	/*
     * 요청속성 (요청구분코드, 검토상태코드, 검토내용, 검증코드, 검증비고)
     */

    private String rqstDcd;		//요청구분코드

    private String rvwStsCd;	//검토상태코드

    private String rvwConts;	//검토내용

    private String vrfCd;		//검증코드

    private String vrfRmk;		//검증비고

	/*
	 * 공통속성 (객체설명, 객체버전, 등록유형코드, 최초요청일시, 최초요청자 ID, 요청일시, 요청자 ID, 승인일시, 승인자 ID - 메타테이블
	 *        작성일시, 작성자 ID, 작성자명 - 관리자테이블)
	 */

    private String objDescn;			//객체설명

    private Integer objVers;			//객체버전

    private String regTypCd;			//등록유형코드 ('C', 'U', 'D)

    private Date frsRqstDtm;			//최초요청일시

    private String frsRqstUserId;		//최초요청자 ID

    private String frsRqstUserNm;		//최초요청자

    private Date rqstDtm;				//요청일시

    private String rqstUserId;			//요청자 ID

    private String rqstUserNm;			//요청자

    private Date aprvDtm;				//승인일시

    private String aprvUserId;			//승인자 ID

    private String aprvUserNm;			//승인자

    private Date writDtm;				//작성일시

    private String writUserId; 			//작성자 ID

    private String writUserNm; 			//작성자 명

	private String adminYn; 			//관리자여부
    /**
     * 	기타 항
     * */
    private String fullPath;	//계층형일 경우 전체 경로 (예: 모델>상위주제영역>주제영역)

    private String popType = "W"; 	//팝업호출시 타입을 지정한다. (W:윈도우 오픈 팝업, I:IFrame 형태의 팝업, L:레이어드 팝)

    private String popRqst = "N"; 	//팝업호출시 요청서용 팝업여부를 셋팅한다.(Y:요청서용, N:일반팝업)
    
    private String mstDcd;   //백업테이블 관리 마스터, 서브 구분 코드
    

	private List<Integer> lvlList = new ArrayList<Integer>(); // ORACLE 계층쿼리(START WITH)의 ANSI SQL화에 필요한 항목.
    
    private String searchBgnDe;  //검색 시작일자
    
    private String searchEndDe;  //검색 종료일자
    
    
    // 이관용 변수 (타겟 db정보)
    private String tgtDdlTrgDcd;
    private String tgtDbConnTrgId;    	
	private String tgtDbConnTrgPnm;
	private String tgtDbSchId;
	private String tgtDbSchPnm;
	
	private String objLnm; 
    private String objPnm; 
    private String objDcdNm;
    
    private String srcDdlTrgDcd;
    private String srcDbSchId;
    private String srcDbSchPnm;
    private String srcDbConnTrgPnm;    
	
	private String dbConnTrgId;
	private String dbConnTrgPnm;
	private String dbConnTrgLnm;
	
    
    private String ddlTrgDcd;
	private String dbSchId;
    private String dbSchPnm;
    private String dbSchLnm;
    
    private String scrnDcd; //화면구분
    
    
    private String stndAsrt; //표준구분 멀티사전 사용시
    
    private String isSysAdminYn;
    
    private String rqstResn;
    
    private String stwdKeyDcd;
    
    private String objType;
    
    private String userId;
    
    
    

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

	public String getTgtDbConnTrgId() {
		return tgtDbConnTrgId;
	}

	public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
		this.tgtDbConnTrgId = tgtDbConnTrgId;
	}

	public String getTgtDbSchId() {
		return tgtDbSchId;
	}

	public void setTgtDbSchId(String tgtDbSchId) {
		this.tgtDbSchId = tgtDbSchId;
	}

	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}

	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}

	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}

	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}
    public String getMstDcd() {
		return mstDcd;
	}

	public void setMstDcd(String mstDcd) {
		this.mstDcd = mstDcd;
	}

    public Date getExpDtm() {
        return expDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setExpDtm(Date expDtm) {
        this.expDtm = expDtm;
    }

    public Date getStrDtm() {
        return strDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setStrDtm(Date strDtm) {
        this.strDtm = strDtm;
    }


    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getFrsRqstDtm() {
        return frsRqstDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setFrsRqstDtm(Date frsRqstDtm) {
        this.frsRqstDtm = frsRqstDtm;
    }

    public String getFrsRqstUserId() {
        return frsRqstUserId;
    }

    public void setFrsRqstUserId(String frsRqstUserId) {
        this.frsRqstUserId = frsRqstUserId;
    }

    public Date getRqstDtm() {
        return rqstDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setRqstDtm(Date rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public Date getAprvDtm() {
        return aprvDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setAprvDtm(Date aprvDtm) {
        this.aprvDtm = aprvDtm;
    }

    public String getAprvUserId() {
        return aprvUserId;
    }

    public void setAprvUserId(String aprvUserId) {
        this.aprvUserId = aprvUserId;
    }

    public Date getWritDtm() {
        return writDtm;
    }

    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }

	public Integer getIbsSeq() {
		return ibsSeq;
	}

	public void setIbsSeq(Integer ibsSeq) {
		this.ibsSeq = ibsSeq;
	}

	public String getWritUserNm() {
		return writUserNm;
	}

	public void setWritUserNm(String writUserNm) {
		this.writUserNm = writUserNm;
	}

	public String getIbsStatus() {
		return ibsStatus;
	}

	public void setIbsStatus(String ibsStatus) {
		this.ibsStatus = ibsStatus;
	}

	public String getIbsCheck() {
		return ibsCheck;
	}

	public void setIbsCheck(String ibsCheck) {
		this.ibsCheck = ibsCheck;
	}

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }

    public String getRqstDcd() {
        return rqstDcd;
    }

    public void setRqstDcd(String rqstDcd) {
        this.rqstDcd = rqstDcd;
    }

    public String getRvwStsCd() {
        return rvwStsCd;
    }

    public void setRvwStsCd(String rvwStsCd) {
        this.rvwStsCd = rvwStsCd;
    }

    public String getRvwConts() {
        return rvwConts;
    }

    public void setRvwConts(String rvwConts) {
        this.rvwConts = rvwConts;
    }

    public String getVrfCd() {
        return vrfCd;
    }

    public void setVrfCd(String vrfCd) {
        this.vrfCd = vrfCd;
    }

    public String getVrfRmk() {
        return vrfRmk;
    }

    public void setVrfRmk(String vrfRmk) {
        this.vrfRmk = vrfRmk;
    }

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return Level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		Level = level;
	}

	/**
	 * @return the frsRqstUserNm
	 */
	public String getFrsRqstUserNm() {
		return frsRqstUserNm;
	}

	/**
	 * @param frsRqstUserNm the frsRqstUserNm to set
	 */
	public void setFrsRqstUserNm(String frsRqstUserNm) {
		this.frsRqstUserNm = frsRqstUserNm;
	}

	/**
	 * @return the rqstUserNm
	 */
	public String getRqstUserNm() {
		return rqstUserNm;
	}

	/**
	 * @param rqstUserNm the rqstUserNm to set
	 */
	public void setRqstUserNm(String rqstUserNm) {
		this.rqstUserNm = rqstUserNm;
	}

	/**
	 * @return the aprvUserNm
	 */
	public String getAprvUserNm() {
		return aprvUserNm;
	}

	/**
	 * @param aprvUserNm the aprvUserNm to set
	 */
	public void setAprvUserNm(String aprvUserNm) {
		this.aprvUserNm = aprvUserNm;
	}

	public String getFontColor() {
		return FontColor;
	}

	public void setFontColor(String fontColor) {
		FontColor = fontColor;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getOnePageRow() {
		return onePageRow;
	}

	public void setOnePageRow(int onePageRow) {
		this.onePageRow = onePageRow;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonVo [ibsStatus=").append(ibsStatus)
				.append(", Level=").append(Level).append(", rqstNo=")
				.append(rqstNo).append(", rqstSno=").append(rqstSno)
				.append(", rqstDtlSno=").append(rqstDtlSno)
				.append(", expDtm=").append(expDtm).append(", strDtm=")
				.append(strDtm).append(", rqstDcd=").append(rqstDcd)
				.append(", objDescn=").append(objDescn).append(", objVers=")
				.append(objVers).append(", regTypCd=").append(regTypCd)
				.append(", frsRqstUserId=").append(frsRqstUserId)
				.append(", rqstUserId=").append(rqstUserId)
				.append(", aprvUserId=").append(aprvUserId)
				.append(", writUserId=").append(writUserId)
				.append(", fullPath=").append(fullPath).append(", popType=")
				.append(popType).append(", popRqst=").append(popRqst)
				.append(", lvlList=").append(lvlList)
				.append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", pageNum=").append(pageNum)
				.append(", onePageRow=").append(onePageRow)
				.append(", stndAsrt=").append(stndAsrt)
				.append("]");
		return builder.toString();
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the popType
	 */
	public String getPopType() {
		return popType;
	}

	/**
	 * @param popType the popType to set
	 */
	public void setPopType(String popType) {
		this.popType = popType;
	}

	/**
	 * @return the popRqst
	 */
	public String getPopRqst() {
		return popRqst;
	}

	/**
	 * @param popRqst the popRqst to set
	 */
	public void setPopRqst(String popRqst) {
		this.popRqst = popRqst;
	}

	public List<Integer> getLvlList() {
		return lvlList;
	}

	public void setLvlList(List<Integer> lvlList) {
		this.lvlList = lvlList;
	}

	/**
	 * @return the rqstDtlSno
	 */
	public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	/**
	 * @param rqstDtlSno the rqstDtlSno to set
	 */
	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	
	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
 
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getDdlTrgDcd() { 
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getScrnDcd() {
		return scrnDcd;
	}

	public void setScrnDcd(String scrnDcd) { 
		this.scrnDcd = scrnDcd;
	} 

	public Integer getRqstDtlDtlSno() {
		return rqstDtlDtlSno;
	}

	public void setRqstDtlDtlSno(Integer rqstDtlDtlSno) {
		this.rqstDtlDtlSno = rqstDtlDtlSno;
	}

	public String getBackColor() {
		return BackColor;
	}

	public void setBackColor(String backColor) {
		BackColor = backColor;
	}

	public String getAdminYn() {
		return adminYn;
	}

	public void setAdminYn(String adminYn) {
		this.adminYn = adminYn;
	}

	public String getTgtDdlTrgDcd() {
		return tgtDdlTrgDcd;
	}

	public void setTgtDdlTrgDcd(String tgtDdlTrgDcd) {
		this.tgtDdlTrgDcd = tgtDdlTrgDcd;
	}

	public String getObjLnm() {
		return objLnm;
	}

	public void setObjLnm(String objLnm) {
		this.objLnm = objLnm;
	}

	public String getObjPnm() {
		return objPnm;
	}

	public void setObjPnm(String objPnm) {
		this.objPnm = objPnm;
	}

	public String getObjDcdNm() {
		return objDcdNm;
	}

	public void setObjDcdNm(String objDcdNm) {
		this.objDcdNm = objDcdNm;
	}

	public String getSrcDdlTrgDcd() {
		return srcDdlTrgDcd;
	}

	public void setSrcDdlTrgDcd(String srcDdlTrgDcd) {
		this.srcDdlTrgDcd = srcDdlTrgDcd;
	}

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	public String getSrcDbConnTrgPnm() {
		return srcDbConnTrgPnm;
	}

	public void setSrcDbConnTrgPnm(String srcDbConnTrgPnm) {
		this.srcDbConnTrgPnm = srcDbConnTrgPnm;
	}
    
	public String getIsSysAdminYn() {
		return isSysAdminYn;
	}

	public void setIsSysAdminYn(String isSysAdminYn) {
		this.isSysAdminYn = isSysAdminYn;
	}

	public String getRqstResn() {
		return rqstResn;
	}

	public void setRqstResn(String rqstResn) {
		this.rqstResn = rqstResn;
	}

	public String getStwdKeyDcd() {
		return stwdKeyDcd;
	}

	public void setStwdKeyDcd(String stwdKeyDcd) {
		this.stwdKeyDcd = stwdKeyDcd;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	
	 
	
	
}