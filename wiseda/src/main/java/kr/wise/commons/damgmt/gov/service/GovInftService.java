package kr.wise.commons.damgmt.gov.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface GovInftService {
	
	// 범정부연계 통합 조회
	public List<WaaGovInft> getTotList(WaaGovInft search) ;
	
	// 범정부연계 DB
	public List<WaaGovInft> getDbList(WaaGovInft search) ;
	
	// 범정부연계 테이블
	public List<WaaGovInft> getTblList(WaaGovInft search) ;
	
	// 범정부연계 컬럼
	public List<WaaGovInft> getColList(WaaGovInft search) ;
	
	// 범정부연계 파일 다운로드
	public void govInftCsvDown(WaaGovInft search, String filepath, HttpServletResponse response) throws Exception;
	public void govInftMapCsvDown(WaaGovInft search, String filepath, HttpServletResponse response) throws Exception;

	//주제영역-업무분류체계 매핑
	public List<WaaGovInft> getSubjBisMapList(WaaGovInft search);

	public int regSubjBisMap(ArrayList<WaaGovInft> list) throws Exception;
}
