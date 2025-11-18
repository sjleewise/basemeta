package kr.wise.commons.handler.mybatis;

import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelResultHandler implements ResultHandler{

	private SXSSFWorkbook workbook = null;
	private Sheet sheet = null;
	private BufferedOutputStream out = null;
	private int rowSeq = 0;
	
	/**
	 * sheet명과 해더를 생성
	 * @param workbook
	 * @param sheetName
	 * @param header
	 */
	public ExcelResultHandler(SXSSFWorkbook workbook, String sheetName, String[] header, BufferedOutputStream out){
		this.workbook = workbook;
		this.sheet =  workbook.createSheet(sheetName);
		this.setHeader(header);
		this.out = out;
	}
	
	/**
	 * 헤더를 설정한다.
	 * @param header
	 */
	private void setHeader(String[] header) {
		Row row = sheet.createRow(rowSeq++);
		int i = 0;
		for(String text : header){
			Cell cell = row.createCell(i++);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(text);
		}
	}

	/**
	 * 건단위로 엑셀Row를 생성한다.
	 */
	public void handleResult(ResultContext context){
		this.addRow((LinkedHashMap)context.getResultObject());		
	}
	
	/**
	 * 조회된 row를 엑셀row로 변경
	 * @param result
	 */
	private void addRow(LinkedHashMap result){
		try {
			Row row = sheet.createRow(rowSeq++);
			int i = 0;
			Iterator it =  result.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				Cell cell = row.createCell(i++);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String)result.get(key));
			}
//			System.out.println(rowSeq + " : " + row.toString());
//			workbook.write(out);
//			out.flush();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
