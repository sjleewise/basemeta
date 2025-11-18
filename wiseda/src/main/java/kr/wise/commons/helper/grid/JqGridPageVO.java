package kr.wise.commons.helper.grid;

public class JqGridPageVO {
	
	//_search=false&nd=1387270885005&rows=20&page=1&sidx=progrmFileNm&sord=desc
	
	/** 페이지당 레코드 수 **/
	private int rows = 10;

	/** 현재 페이지 **/
	private int page = 1;

	/** 소트 대상 컬럼명 **/
	private String sidx = "";

	/** 소트 방식 (asc, desc) **/
	private String sord = "";
	
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JqGridPageVO [rows=").append(rows).append(", page=")
				.append(page).append(", sidx=").append(sidx).append(", sord=")
				.append(sord).append("]");
		return builder.toString();
	}
	
	
	

}
