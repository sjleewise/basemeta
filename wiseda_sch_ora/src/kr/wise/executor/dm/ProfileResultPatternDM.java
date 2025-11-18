package kr.wise.executor.dm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProfileResultPatternDM {
	
	/** 컬럼명 배열 */
	private String [] columnNames;
	/** 컬럼명을 key로 하고, 패턴값을 value로 하는 java.util.Map의 java.util.List */
	private List<Map<String, String>> patternList;
	
	
	private BigDecimal patternCount;
	private Long totalCount;
	

	public BigDecimal getPatternCount() {
		return patternCount;
	}

	public void setPatternCount(BigDecimal patternCount) {
		this.patternCount = patternCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}


	/**
	 * 컬럼명 배열
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * 컬럼명 배열
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * 컬럼명을 key로 하고, 패턴값을 value로 하는 java.util.Map의 java.util.List
	 * @return the patternList
	 */
	public List<Map<String, String>> getPatternList() {
		return patternList;
	}

	/**
	 * 컬럼명을 key로 하고, 패턴값을 value로 하는 java.util.Map의 java.util.List
	 * @param patternList the patternList to set
	 */
	public void setPatternList(List<Map<String, String>> patternList) {
		this.patternList = patternList;
	}
}
