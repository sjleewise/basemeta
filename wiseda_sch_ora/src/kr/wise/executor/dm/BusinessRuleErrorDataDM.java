package kr.wise.executor.dm;

import java.util.List;
import java.util.Map;

public class BusinessRuleErrorDataDM {
	
	/** 컬럼명 배열 */
	private String [] columnNames;
	/** 컬럼명을 key로 하고, 패턴값을 value로 하는 java.util.Map의 java.util.List */
	private List<Map<String, String>> patternList;
	
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
