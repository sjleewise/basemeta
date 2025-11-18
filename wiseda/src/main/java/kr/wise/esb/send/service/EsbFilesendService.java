/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : EsbFilesendService.java
 * 2. Package : kr.wise.esb.send.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.22.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.22. :            : 신규 개발.
 */
package kr.wise.esb.send.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface EsbFilesendService { 

	/** 전송파일 생성 */
	boolean createEsbFilesend(List<HashMap<String, Object>> list, EsbFilesendVO fileVO) throws IOException;

	boolean createEsbFilesend2(List<HashMap<String, Object>> data, EsbFilesendVO fileVO) throws IOException;

	/*int regEsbFileSendList(List<EsbFilesendVO> data);
	
	int regEsbFileSend(EsbFilesendVO data);*/
	
	/** 재전송파일 생성 */ 
	/*boolean createEsbFileResend(List<HashMap<String, Object>> list, EsbFilesendVO fileVO) throws IOException;*/

	/** ESB전송파일목록 삭제  */
	/*int delEsbFileSendList(List<EsbFilesendVO> list);*/
	
	/** 메타데이터 전송현황 차트 조회  */
	/*List<EsbFilesendVO> getMtaSendStatChartDataList(EsbFilesendVO search);*/
	
	/** 목록 조회  */
	/*List<EsbFilesendVO> getMtaSendStatList(EsbFilesendVO search);*/
	
}
