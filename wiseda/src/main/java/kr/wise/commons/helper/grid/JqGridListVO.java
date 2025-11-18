package kr.wise.commons.helper.grid;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : JqGridListVO.java
 * 3. Package  : kr.wise.commons.helper.grid
 * 4. Comment  : jqgrid 용 결과를 담는 공통 vo 클래스로 json으로 변환해서 보냄...
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 17. 오전 10:18:25
 * </PRE>
 */ 
public class JqGridListVO<T> {
	
	
	/** 결과 리스트 **/
	private List<T> rows; 

	/** 현재 페이지 **/
	private int page;

	/** 전체 페이지 **/
	private int total;

	/** 조회 레코드 수 **/
	private int records;
	
	public JqGridListVO() {
		// TODO Auto-generated constructor stub
	}
	
	public JqGridListVO(List<T> rows, int page, int total, int records) {
		this.rows = rows;
		this.page = page;
		this.total = total;
		this.records = records;
	}

	public JqGridListVO(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JqGridListVO [page=").append(page).append(", total=")
				.append(total).append(", records=").append(records).append("]");
		return builder.toString();
	}


}
