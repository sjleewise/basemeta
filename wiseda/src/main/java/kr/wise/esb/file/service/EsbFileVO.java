/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : EsbFileVO.java
 * 2. Package : kr.wise.esb.file.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.22.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.22. :            : 신규 개발.
 */
package kr.wise.esb.file.service;

public class EsbFileVO {

	//파일구분(M:메타)
	private String fileDcd;
	
	//파일 상세 구분 (테이블:T 컬럼:C)
	private String fileDtlCd;

	private String fileName;
	
	private String orgCd;
	
	private String sysCd;
	
	//확장자
	private String fileExt;
	
	private String[] fileHeader;
	
	private String[] csvFileHeader;
	
	
	public String[] getFileHeader() {
		return fileHeader;
	}

	public void setFileHeader(String[] fileHeader) {
		this.fileHeader = fileHeader;
	}

	public String[] getCsvFileHeader() {
		return csvFileHeader;
	}

	public void setCsvFileHeader(String[] csvFileHeader) {
		this.csvFileHeader = csvFileHeader;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getSysCd() {
		return sysCd;
	}

	public void setSysCd(String sysCd) {
		this.sysCd = sysCd;
	}

	public String getFileDcd() {
		return fileDcd;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileDcd(String fileDcd) {
		this.fileDcd = fileDcd;
	}

	public String getFileDtlCd() {
		return fileDtlCd;
	}

	public void setFileDtlCd(String fileDtlCd) {
		this.fileDtlCd = fileDtlCd;
	}
}