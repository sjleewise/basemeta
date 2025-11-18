/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : EsbFilesendServiceImpl.java
 * 2. Package : kr.wise.esb.send.service.impl
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.22.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.22. :            : 신규 개발.
 */
package kr.wise.esb.send.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVWriter;
import kr.wise.commons.util.UtilString;
import kr.wise.esb.EsbConfig;
/*import kr.wise.esb.send.service.EsbFilesendMapper;*/
import kr.wise.esb.send.service.EsbFilesendService;
import kr.wise.esb.send.service.EsbFilesendVO;

@Service("esbFilesendService")
public class EsbFilesendServiceImpl implements EsbFilesendService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
/*	@Inject
	private EsbFilesendMapper esbFilesendMapper;*/
	
	@Inject
	private MessageSource message;
	
	@Override
	public boolean createEsbFilesend(List<HashMap<String,Object>> data, EsbFilesendVO fileVO) throws IOException {
		
		boolean result = false;
		int regResult = 0;
		
		//전송 재전송여부 확인 (재전송이면 fileVO그대로 사용 하고 filename만 재 생성, 신규전송이면 전체 생성 )
		boolean isResend = !UtilString.isBlank(fileVO.getOccrrSn());
		logger.info("=== esbResnd Mode :"+isResend);

		EsbFilesendVO saveEsbFileVO = createEsbFile2(data, fileVO);
		
		/*if(saveEsbFileVO != null) {
			regResult = regEsbFileSend(saveEsbFileVO);
		}*/
		logger.debug("========= saveEsbFileVo" + saveEsbFileVO);
		if(regResult > 0) {
			result = true;
		}else {
			result = false;
		}
		
		return result;
	}
	
	

/*	@Override
	public int regEsbFileSendList(List<EsbFilesendVO> data) {
		
		int result = 0;
		
		for(EsbFilesendVO saveData : data) {
			result += regEsbFileSend(saveData);
		}
		
		if(result > 0) {
			result = 0;
		}else {
			result = -1;
		}
		
		return result;
	}*/

/*	@Override
	public int regEsbFileSend(EsbFilesendVO data) {
		int result = esbFilesendMapper.insertEsbFilesend(data);
		return result;
	}*/
	
	/** 파일 생성 시 필요한 정보 생성 
	 * 파일생성필요정보 : 컬럼헤더
	 * 파일정보 : 파일명
	 *  */
	private EsbFilesendVO createFileInfo(EsbFilesendVO fileVO) {
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmssS"); 
		Date d = new Date();
		
		String fileDcd = fileVO.getFileGb();
		String createFileNm = "";
		String createTime = dt.format(d)+"0";
		String fileExt = fileVO.getFileExt();
		
		
		if(EsbConfig.MTA.equals(fileDcd)) {
			
			//파일명 : M + 기관코드(7) + 시스템정보(4) + 압축정보(N or Y) + 압축파일개수(3자리-없음:000) + 파일생성시간(18) + 확장자(.포함)
			//createFileNm = fileDcd + fileVO.getSrcOrgCd() + fileVO.getSrcSysCd() + "N" + "000" + createTime + "." + fileExt;//fileVO.getFileExt();
			createFileNm = "MTA" + createTime + "." + fileExt;
			logger.debug("fileDcd ====== "+fileDcd);
			logger.debug("fileVO.getSrcOrgCd() ====== "+fileVO.getSrcOrgCd());
			logger.debug("fileVO.getSrcSysCd() ====== "+fileVO.getSrcSysCd());
			logger.debug("createTime ====== "+createTime);
			logger.debug("fileExt ====== "+fileExt);
			logger.debug("createFileNm ====== "+createFileNm);
			fileVO.setFileName(createFileNm);
			
			logger.info("=== create Esb fileName:"+createFileNm);
			
			if(EsbConfig.MTA_DBCON.equals(fileVO.getFileDtlCd())) {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_DBCON);
			}else if(EsbConfig.MTA_TBL.equals(fileVO.getFileDtlCd())) {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_TBL);
			}else if(EsbConfig.MTA_COL.equals(fileVO.getFileDtlCd())){
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_COL);
			}else if(EsbConfig.MTA_TOTAL.equals(fileVO.getFileDtlCd())) {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_TOTAL);
			}else if(EsbConfig.MTA_INFOSYS.equals(fileVO.getFileDtlCd())) {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_INFOSYS);
			}
 		}
		
		return fileVO;
	}
	
	/*private EsbFileVO createFileInfo(EsbFilesendVO fileVO) {
		//EsbFilesendVO result = new EsbFilesendVO();
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmssS");
		Date d = new Date();
		
		String fileDcd = fileVO.getFileDcd();
		String fileNm = "";
		String createTime = dt.format(d)+"0";
		String fileExt = "csv";
		
		
		if(EsbConfig.MTA.equals(fileDcd)) {
			
			fileNm = fileDcd + fileVO.getOrgCd() + fileVO.getSysCd() + "N" + "000" + createTime + "." + fileExt;//fileVO.getFileExt();
			
			fileVO.setFileName(fileNm);
			
			logger.debug("create fileName:"+fileNm);
			
			if(EsbConfig.MTA_TBL.equals(fileVO.getFileDtlCd())) {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_TBL);
			}else {
				fileVO.setFileHeader(EsbConfig.MTA_CSV_HEADER_COL);
			}
 		}
		
		return fileVO;
	}*/
	
	/** [체크] 해당 경로에 파일 여부 확인  */
	private boolean checkFile(String fileFullPath) {
		boolean checkResult = false;
		
		File file = new File(fileFullPath);
		
		if(file.exists()) {
			checkResult = true;
		}
		
		return checkResult;
	}
	
	/** 업무별 별도 관리된 filePath 조회 */
	private String selectFilePath(String pathType, String fileDcd, String fileDtlCd) {
		String filePath = "";
		
		//메타데이터정보 
		if(EsbConfig.MTA.equals(fileDcd)) {
			
			String mtaFileSendPath = message.getMessage("mtaFileSendPath", null, Locale.getDefault());
			String mtaFileRecvPath = message.getMessage("mtaFileRecvPath", null, Locale.getDefault());
			
			if(EsbConfig.MTA_SEND.equals(pathType)) {
				//filePath = EsbConfig.MTA_FILE_SEND_PATH;
				
				filePath = mtaFileSendPath;
			}else {
				//filePath = EsbConfig.MTA_FILE_RECV_PATH;
				
				filePath = mtaFileRecvPath;
			}
			
			if(EsbConfig.MTA_DBCON.equals(fileDtlCd)) { 			
				filePath += "/" + EsbConfig.MTA_DBCON ;				
			}else if(EsbConfig.MTA_TBL.equals(fileDtlCd)) {
				//filePath += File.separator + EsbConfig.MTA_TBL ;
				filePath += "/" + EsbConfig.MTA_TBL ;
			}else if(EsbConfig.MTA_COL.equals(fileDtlCd)) {
				//filePath += File.separator + EsbConfig.MTA_COL ;
				filePath += "/" + EsbConfig.MTA_COL ;
			}else if(EsbConfig.MTA_TOTAL.equals(fileDtlCd)) {
				filePath += "/" + EsbConfig.MTA_TOTAL ;
			}else if(EsbConfig.MTA_INFOSYS.equals(fileDtlCd)) {
				filePath += "/" + EsbConfig.MTA_INFOSYS;
			}
		}
		
		if(EsbConfig.MTA_SEND.equals(pathType)) {
			
			File file = new File(filePath);
			
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		
		logger.debug("filePath:"+filePath);
		return filePath;
		
	}

	
	/** CSV 파일 생성 (파일명, 경로,  ESB연계테이블 insert를 위한 vo생성 */
	private EsbFilesendVO createEsbFile(List<HashMap<String,Object>> data, EsbFilesendVO fileVO) {
		
		logger.info("=== createEsbFile started ===");
		
		EsbFilesendVO esbSaveVO = new EsbFilesendVO();
		
		String fileDcd = fileVO.getFileGb();
		String fileFullPath = "";
		String infoSysCd = "";
		
		boolean result = false;

		if(EsbConfig.MTA.equals(fileDcd)) {
			String fileSendPath = selectFilePath(EsbConfig.MTA_SEND, fileDcd, fileVO.getFileDtlCd());
			String fileRecvPath = selectFilePath(EsbConfig.MTA_RECV, fileDcd, fileVO.getFileDtlCd());
			
			//esb 연계 정보
			fileVO.setFileExt("csv");
			esbSaveVO = createFileInfo(fileVO);
			fileFullPath = fileSendPath + "/" + esbSaveVO.getFileName();
			
			CSVWriter cw = null;
			
			try {
				
				//Writer writer = new FileWriter(fileFullPath);
				
				//cw = new CSVWriter (writer, ',');
				cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(fileFullPath), "EUC-KR"),',');
				
				String [] fileHeaderText = fileVO.getFileHeader();
				String [] fileData = null;
				int headCnt = fileHeaderText.length;
				
				cw.writeNext(fileHeaderText);
				
				for(Map<String, Object> tmpMap : data) {
					fileData = new String[headCnt];

					for(int i=0; i< headCnt; i++) {
						
						fileData[i]= String.valueOf(tmpMap.get(fileHeaderText[i]));
					}
					
					//첫번째 info_sys_cd 를 대표로 입력 함
					for(String key : tmpMap.keySet()) {
						if("info_sys_cd".equals(key) && UtilString.isBlank(infoSysCd)) {
							String value= String.valueOf(tmpMap.get("info_sys_cd"));
							
							if(!UtilString.isBlank(value)) {
								infoSysCd = value;
							}
						}
					}
					
					cw.writeNext(fileData);
				}
				
				cw.close();
				
				
				result = checkFile(fileFullPath);
				//result = true;
				if(result) {
					
					esbSaveVO.setFileGb(fileDcd);
					esbSaveVO.setInfoSysCd(infoSysCd);
					esbSaveVO.setTgtOrgCd(EsbConfig.TGT_ORG_CD);
					esbSaveVO.setTgtSysCd(EsbConfig.TGT_SYS_CD);
					esbSaveVO.setFileSendLoc(fileSendPath);
					esbSaveVO.setFileRecvLoc(fileRecvPath);
					
					logger.debug( fileVO.getFileDtlCd()+" File create Success");
				}
				
				//Runtime.getRuntime().exec("chmod 644 " + fileFullPath); 				
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
				logger.debug( fileVO.getFileDtlCd()+" create File Error");
			}
		}
		
		return esbSaveVO;
	}
	
	/** 재전송파일 생성 */
	/*@Override
	public boolean createEsbFileResend(List<HashMap<String, Object>> list, EsbFilesendVO fileVO) throws IOException {
		
		boolean returnResult = false;
		int result = 0;
		
		returnResult = createEsbFilesend(list, fileVO);
		
		if(result > 0) {
			returnResult = true;
		}else {
		}
		
		return returnResult;
	}*/

	/** ESB전송파일목록 삭제  */
	/*@Override
	public int delEsbFileSendList(List<EsbFilesendVO> list) {
		
		int result = 0;
		
		for(EsbFilesendVO delvo : list) {
			
			delvo.setIbsStatus("D");
			
			result += esbFilesendMapper.deleteEsbFilesend(delvo);
		}
		
		return result;
	}*/

	/** 메타데이터 전송현황 차트 조회  */
	/*@Override
	public List<EsbFilesendVO> getMtaSendStatChartDataList(EsbFilesendVO search) {
		return esbFilesendMapper.selectMtaSendStatChartDataList(search);
	}*/

	/** 목록 조회  */
	/*@Override
	public List<EsbFilesendVO> getMtaSendStatList(EsbFilesendVO search) {
		return esbFilesendMapper.selectList(search);
	}*/
	
	
	@Override
	public boolean createEsbFilesend2(List<HashMap<String,Object>> data, EsbFilesendVO fileVO) throws IOException {
		
		boolean result = false;
		int regResult = 0;
		
		//전송 재전송여부 확인 (재전송이면 fileVO그대로 사용 하고 filename만 재 생성, 신규전송이면 전체 생성 )
		boolean isResend = !UtilString.isBlank(fileVO.getOccrrSn());
		logger.info("=== esbResnd Mode :"+isResend);
		
		EsbFilesendVO saveEsbFileVO = createEsbFile2(data, fileVO);
		
		/*if(saveEsbFileVO != null) {
			regResult = regEsbFileSend(saveEsbFileVO);
		}*/
		logger.debug("========= saveEsbFileVo" + saveEsbFileVO);
		if(regResult > 0) {
			result = true;
		}else {
			result = false;
		}
		
		return result;
	}
	/** EXCEL 파일 생성 (파일명, 경로,  ESB연계테이블 insert를 위한 vo생성 */
	private EsbFilesendVO createEsbFile2(List<HashMap<String,Object>> data, EsbFilesendVO fileVO) {
		
		logger.info("=== createEsbFile started ===");
		
		EsbFilesendVO esbSaveVO = new EsbFilesendVO();
		
		String fileDcd = fileVO.getFileGb();
		String fileFullPath = "";
		String infoSysCd = "";
		
		boolean result = false;

		if(EsbConfig.MTA.equals(fileDcd)) {
			String fileSendPath = selectFilePath(EsbConfig.MTA_SEND, fileDcd, fileVO.getFileDtlCd());
			String fileRecvPath = selectFilePath(EsbConfig.MTA_RECV, fileDcd, fileVO.getFileDtlCd());
			
			//esb 연계 정보
			fileVO.setFileExt("xls");
			esbSaveVO = createFileInfo(fileVO);
			fileFullPath = fileSendPath + "/" + esbSaveVO.getFileName();
			Workbook wb = null;
			try {
				wb = new HSSFWorkbook();
				Sheet sheet = wb.createSheet("Sheet1");
				
				String [] fileHeaderText = fileVO.getFileHeader();
				String [] fileData = null;
				int headCnt = fileHeaderText.length;

				Row header = sheet.createRow(0);
				for(int i = 0; i < headCnt; i++) {
					header.createCell(i).setCellValue(fileHeaderText[i]);
				}
				int rowNum = 1;
				for(Map<String, Object> tmpMap : data) {
					fileData = new String[headCnt];

					for(int i=0; i< headCnt; i++) {
						fileData[i]= String.valueOf(tmpMap.get(fileHeaderText[i]));
					}
					
					//첫번째 info_sys_cd 를 대표로 입력 함
					for(String key : tmpMap.keySet()) {
						if("info_sys_cd".equals(key) && UtilString.isBlank(infoSysCd)) {
							String value= String.valueOf(tmpMap.get("info_sys_cd"));
							
							if(!UtilString.isBlank(value)) {
								infoSysCd = value;
							}
						}
					}
					Row row = sheet.createRow(rowNum++);
					for(int i = 0; i < headCnt; i++) {
						row.createCell(i).setCellValue(fileData[i]);
					}
				}
				
				FileOutputStream out = new FileOutputStream(fileFullPath);
				wb.write(out);
				out.close();
				wb.close();
				
				result = checkFile(fileFullPath);
				
				if(result) {
					
					esbSaveVO.setFileGb(fileDcd);
					esbSaveVO.setInfoSysCd(infoSysCd);
					esbSaveVO.setTgtOrgCd(EsbConfig.TGT_ORG_CD);
					esbSaveVO.setTgtSysCd(EsbConfig.TGT_SYS_CD);
					esbSaveVO.setFileSendLoc(fileSendPath);
					esbSaveVO.setFileRecvLoc(fileRecvPath);
					
					logger.debug( fileVO.getFileDtlCd()+" File create Success");
				}
				
				//Runtime.getRuntime().exec("chmod 644 " + fileFullPath); 				
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
				logger.debug( fileVO.getFileDtlCd()+" create File Error");
			}
		}
		
		return esbSaveVO;
	}
	
}

 